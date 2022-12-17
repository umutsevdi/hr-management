package com.hr.management.ui.pages.base;

import com.hr.management.ui.client.PageClient;
import com.hr.management.ui.client.view.TeamView;
import com.hr.management.ui.pages.BaseLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Route(value = "/teams")
@PageTitle("Teams | Human Resources")
@Getter
@Log4j2
public class TeamBoard extends BaseLayout {

    Grid<TeamView> grid = new Grid<>(TeamView.class);

    public TeamBoard(PageClient pageClient) {
        super(pageClient);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        getDrawer().setSelectedIndex(1);
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(grid);
        updateList();
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(new ComponentRenderer<>(
                i -> new Anchor("/teams/" + i.getId(),
                        new HorizontalLayout(
                                new Image(i.getProfile(), i.getName()),
                                new Paragraph(i.getName())
                        )
                ))).setHeader("Team");
        grid.addColumn(new ComponentRenderer<>(
                i -> new Anchor("/employees/" + i.getBossId(),
                        i.getBoss().getFirstName() + " " + i.getBoss().getLastName())
        )).setHeader("Boss");
        grid.addColumns("createdDate", "remote", "ipAddress");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        //  grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    }

    private void updateList() {
        List<TeamView> teamViewList = getClient().toView(getClient().getTeamService().findAll());
        grid.setItems(teamViewList);
        log.info(grid.getColumns());
    }

}
