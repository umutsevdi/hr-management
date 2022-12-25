package com.hr.management.ui.pages.parametric;

import com.hr.management.api.service.model.EmployeeDto;
import com.hr.management.api.service.model.EmployeeStatusPastDto;
import com.hr.management.ui.client.PageClient;
import com.hr.management.ui.client.view.EmployeeView;
import com.hr.management.ui.components.EmployeeForm;
import com.hr.management.ui.components.ExperienceForm;
import com.hr.management.ui.pages.BaseLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.rmi.NoSuchObjectException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route(value = "/employees")
@PageTitle("Employee | Human Resources")
@Getter
@Log4j2
public class EmployeePage extends BaseLayout implements HasUrlParameter<Long> {
    Long employeeId;
    EmployeeView employee;

    EmployeeForm employeeForm;

    ExperienceForm experienceData;
    Button save = new Button("Save");
    Button delete = new Button("Delete");

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long value) {
        if (value != null) {
            employeeId = value;
        } else {
            UI.getCurrent().navigate("");
        }
    }

    public EmployeePage(PageClient pageClient) {
        super(pageClient);
        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        try {
            EmployeeDto employeeDto = getClient().getEmployeeService().findEmployeeById(employeeId);
            employee = new EmployeeView(
                    employeeDto,
                    getClient().getTeamService().getTeam(employeeDto),
                    getClient().getEmployeeStatusService().findEmployeeStatusPastsByEmployeeId(employeeDto.getId()),
                    getClient().getEmployeeStatusService().findEmployeeStatusesByEmployeeId(employeeDto.getId())
            );
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
            UI.getCurrent().navigate("/employees");
        }
        if (employee == null) {
            UI.getCurrent().navigate("/employees");
        }
        getDrawer().add(new Tab(employee.getFirstName() + " " + employee.getLastName()));
        getDrawer().setSelectedIndex(this.getDrawer().getComponentCount() - 1);

        employeeForm = new EmployeeForm(false).fillFieldsWith(employee);
        experienceData = new ExperienceForm(getClient().getTeams()).fillFieldsWith(employee);
        Map.Entry<Button, Button> buttons = EmployeeForm.createButtonsLayout();
        save = buttons.getKey();
        delete = buttons.getValue();
        add(new HorizontalLayout(employeeForm, experienceData), new HorizontalLayout(buttons.getKey(), buttons.getValue()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(createSalaryGraph(), createWorkPerformanceGraph());
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setWidthFull();
        add(horizontalLayout);
        add(createSprintGrid());
    }

    public Chart createSalaryGraph() {
        Chart chart = new Chart(ChartType.LINE);
        List<EmployeeStatusPastDto> pastStatus = employee.getPastEmployeeStatus();
        Map<Integer, Double> yearSalaryMap = new HashMap<>(pastStatus.size());
        pastStatus.forEach(i -> yearSalaryMap.putIfAbsent(i.getYear(), i.getMonthlySalary() * 12));
        String[] years =
                yearSalaryMap.keySet().stream().map(String::valueOf).collect(Collectors.toList()).toArray(new String[yearSalaryMap.size()]);
        ListSeries data = new ListSeries(
                "Salary",
                yearSalaryMap.values().toArray(new Double[0]));
        Configuration conf = chart.getConfiguration();
        setupChart(chart, years, data);
        conf.setTitle("Salary Graph");
        conf.setSubTitle("Salary of " + employee.getFirstName() + " " + employee.getLastName() + " over years as " + employee.getTitle());
        return chart;
    }

    public Chart createWorkPerformanceGraph() {
        Chart chart = new Chart(ChartType.LINE);
        List<EmployeeStatusPastDto> pastStatus = employee.getPastEmployeeStatus();
        Map<Integer, EmployeeStatusPastDto> yearStatusMap = new HashMap<>(pastStatus.size());
        pastStatus.forEach(i -> yearStatusMap.putIfAbsent(i.getYear(), i));
        String[] years = yearStatusMap.keySet().stream().map(String::valueOf).collect(Collectors.toList()).toArray(new String[yearStatusMap.size()]);
        ListSeries completedTasks = new ListSeries(
                "Completed Tasks",
                yearStatusMap.values().stream().map(EmployeeStatusPastDto::getCompletedTasks).toArray(Integer[]::new));
        ListSeries awaitingTasks = new ListSeries(
                "Awaiting Tasks",
                yearStatusMap.values().stream().map(EmployeeStatusPastDto::getAwaitingTasks).toArray(Integer[]::new));
        ListSeries unfinishedTasks = new ListSeries(
                "Unfinished Tasks",
                yearStatusMap.values().stream().map(EmployeeStatusPastDto::getUnfinishedTasks).toArray(Integer[]::new));
        ListSeries delayedTasks = new ListSeries(
                "Delayed Tasks",
                yearStatusMap.values().stream().map(EmployeeStatusPastDto::getDelayedTasks).toArray(Integer[]::new));
        Configuration conf = chart.getConfiguration();

        setupChart(chart, years, completedTasks, awaitingTasks, unfinishedTasks, delayedTasks);
        conf.setTitle("Performance Graph");
        conf.setSubTitle("Task performance of " + employee.getFirstName() + " " + employee.getLastName() + " over years as " + employee.getTitle());
        return chart;
    }

    public Grid createSprintGrid() {
        Grid<EmployeeStatusPastDto> grid = new Grid<>(EmployeeStatusPastDto.class);
        grid.setColumns();
        grid.addColumn("year");
        grid.addColumn(new ComponentRenderer<>(i -> new Paragraph(employee.getEmployeeStatus().getTitle()))).setHeader("Title");
        grid.addColumn(new ComponentRenderer<>(i -> new Anchor(
                "/teams/" + i.getTeamId().toString(),
                experienceData.getTeamMap().get(i.getTeamId()).getName()))).setHeader("Team");
        grid.addColumn(new ComponentRenderer<>(i -> new Paragraph(String.valueOf(i.getMonthlySalary() * 12)))).setHeader("Salary");
        grid.addColumns("workingHour", "completedSprints", "workDaysLate", "workDaysOvertime", "averageTeamScore", "awaitingTasks", "completedTasks", "delayedTasks", "unfinishedTasks");
        grid.setItems(employee.getPastEmployeeStatus());
        grid.sort(Collections.singletonList(new GridSortOrder<>(grid.getColumns().get(0), SortDirection.DESCENDING)));
        grid.setWidthFull();
        grid.setMinHeight(40, Unit.PERCENTAGE);
        return grid;
    }

    private void setupChart(Chart chart, String[] years, ListSeries... series) {
        chart.setWidthFull();
        chart.getConfiguration().getxAxis().setCategories(years);
        chart.getConfiguration().getxAxis().getLabels().setFormatter("function() {return this.value}");
        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.setLineWidth(2);
        plotOptions.setShadow(false);
        plotOptions.setAnimation(false);
        plotOptions.setMarker(new Marker(false));
        plotOptions.setDataLabels(new DataLabels(true));
        plotOptions.setEnableMouseTracking(true);

        for (ListSeries serial : series) {
            serial.setPlotOptions(plotOptions);
            chart.getConfiguration().addSeries(serial);
        }
    }


}