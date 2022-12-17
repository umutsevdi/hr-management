package com.hr.management.ui.pages.parametric;

import com.hr.management.api.service.model.TeamDto;
import com.hr.management.ui.client.PageClient;
import com.hr.management.ui.client.view.EmployeeView;
import com.hr.management.ui.client.view.TeamView;
import com.hr.management.ui.pages.BaseLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.rmi.NoSuchObjectException;

@Route(value = "/teams")
@PageTitle("Team | Human Resources")
@Getter
@Log4j2
public class TeamPage extends BaseLayout implements HasUrlParameter<Long> {

    Long teamId;
    TeamView team;

    Grid<EmployeeView> grid = new Grid<>(EmployeeView.class);

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long value) {
        if (value != null) {
            teamId = value;
        } else {
            UI.getCurrent().navigate("/teams");
        }
    }

    public TeamPage(PageClient pageClient) {
        super(pageClient);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        TeamDto teamDto = getClient().getTeamService().findById(teamId);
        try {
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
        Image avatar = new Image(team.getProfile(), team.getName());
        H1 title = new H1(team.getName() + " " + team.getCreatedDate());
        add(new HorizontalLayout(avatar, title));

    }

}
