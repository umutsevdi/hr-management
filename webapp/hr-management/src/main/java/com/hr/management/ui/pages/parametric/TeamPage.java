package com.hr.management.ui.pages.parametric;

import com.hr.management.api.service.model.BaseEmployeeStatus;
import com.hr.management.api.service.model.EmployeeDto;
import com.hr.management.api.service.model.TeamDto;
import com.hr.management.ui.client.PageClient;
import com.hr.management.ui.client.view.TeamView;
import com.hr.management.ui.components.CommonComponentUtil;
import com.hr.management.ui.components.TeamForm;
import com.hr.management.ui.pages.BaseLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.ListSeries;
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
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route(value = "/teams")
@PageTitle("Team | Human Resources")
@Getter
@Log4j2
public class TeamPage extends BaseLayout implements HasUrlParameter<Long> {
    Long teamId;
    TeamView team;

    TeamForm teamForm;
    Button save = new Button("Save");
    Button delete = new Button("Delete");

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long value) {
        if (value != null) {
            teamId = value;
        } else {
            UI.getCurrent().navigate("");
        }
    }

    public TeamPage(PageClient pageClient) {
        super(pageClient);
        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        try {
            TeamDto teamDto = getClient().getTeamService().findById(teamId);
            team = new TeamView(
                    teamDto,
                    getClient().getEmployeeService().findEmployeeById(teamDto.getBossId()),
                    getClient().getEmployeeService().findByTeamId(teamDto.getId())
            );
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
            UI.getCurrent().navigate("/teams");
        }
        if (team == null) {
            UI.getCurrent().navigate("/teams");
        }
        getDrawer().add(new Tab(team.getName()));
        getDrawer().setSelectedIndex(this.getDrawer().getComponentCount() - 1);

        teamForm = new TeamForm(false);
        teamForm.fillFieldsWith(team);
        Map.Entry<Button, Button> buttons = CommonComponentUtil.createButtonsLayout();
        save = buttons.getKey();
        delete = buttons.getValue();
        add(teamForm, new HorizontalLayout(buttons.getKey(), buttons.getValue()));
        add(createEmployeeGrid());
        HorizontalLayout horizontalLayout = new HorizontalLayout(createCostGraph(), createWorkPerformanceGraph());
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setWidthFull();
        add(horizontalLayout);
    }

    public Chart createCostGraph() {
        Map<Integer, List<BaseEmployeeStatus>> yearStatusMap = getEmployeeStatus();
        String[] years = yearStatusMap.keySet().stream().map(String::valueOf).collect(Collectors.toList()).toArray(new String[yearStatusMap.size()]);

        ListSeries employeeCount = new ListSeries(
                "Team Members",
                yearStatusMap.values()
                        .stream().map(
                                i -> i.stream().map(BaseEmployeeStatus::getEmployeeId)
                                        .distinct().count())
                        .toArray(Long[]::new));

        ListSeries totalSalaries = new ListSeries(
                "Total Salaries",
                yearStatusMap.values()
                        .stream().map(
                                i -> i.stream().map(BaseEmployeeStatus::getMonthlySalary).mapToLong(j -> j.longValue() * 12).sum()
                        )
                        .toArray(Long[]::new));

        ListSeries averageSalaries = new ListSeries(
                "Average Salaries",
                yearStatusMap.values()
                        .stream().map(
                                i -> i.stream().map(BaseEmployeeStatus::getMonthlySalary).mapToLong(j -> j.longValue() * 12).sum() / i.size()
                        )
                        .toArray(Long[]::new));
        Chart chart = CommonComponentUtil.setupChart(years, employeeCount, totalSalaries, averageSalaries);
        chart.getConfiguration().setTitle("Salary - Employee Graph");
        chart.getConfiguration().setSubTitle("Cost of " + team.getName() + " over years");

        return chart;
    }

    public Chart createWorkPerformanceGraph() {
        Map<Integer, List<BaseEmployeeStatus>> yearStatusMap = getEmployeeStatus();
        Chart chart = new Chart(ChartType.LINE);
        String[] years = yearStatusMap.keySet().stream().map(String::valueOf).collect(Collectors.toList()).toArray(new String[yearStatusMap.size()]);

        /*

        ListSeries completedTasks = new ListSeries(
                "Completed Tasks",
                yearStatusMap.values().stream().map(EmployeeStatusPastDto::getCompletedTasks).toArray(Integer[]::new));

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

         */
        return chart;
    }

    public Grid<EmployeeDto> createEmployeeGrid() {
        Grid<EmployeeDto> grid = new Grid<>(EmployeeDto.class);
        grid.setColumns();
        grid.addColumn(new ComponentRenderer<>(i -> new Anchor("/employees/" + i.getId().toString(),
                        new HorizontalLayout(
                                new Avatar(i.getFirstName() + " " + i.getLastName(), i.getProfile()),
                                new Paragraph(i.getFirstName() + " " + i.getLastName())))))
                .setHeader("Employee");
        grid.addColumns("email", "phoneNumber", "gender");
        grid.sort(Collections.singletonList(new GridSortOrder<>(grid.getColumns().get(0), SortDirection.DESCENDING)));
        grid.setWidthFull();
        grid.setMinHeight(40, Unit.PERCENTAGE);
        grid.setItems(team.getEmployees());
        return grid;
    }

    private Map<Integer, List<BaseEmployeeStatus>> getEmployeeStatus() {
        HashMap<Integer, List<BaseEmployeeStatus>> yearStatusMap = new HashMap<>();
        for (int i = team.getCreatedDate().getYear(); i < LocalDate.now().getYear(); i++) {
            yearStatusMap.put(i,
                    getClient().getEmployeeStatusService()
                            .findEmployeeStatusPastByTeamIdAndYear(teamId, i)
                            .stream().map(j -> (BaseEmployeeStatus) j)
                            .collect(Collectors.toList()));

        }
        yearStatusMap.put(LocalDate.now().plusYears(1L).getYear(),
                getClient().getEmployeeStatusService()
                        .findEmployeeStatusesByTeamId(teamId)
                        .stream().map(i -> (BaseEmployeeStatus) i)
                        .collect(Collectors.toList()));
        return yearStatusMap;
    }
}