package com.hr.management.ui.pages.dashboard;

import com.hr.management.ui.client.PageClient;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.*;
import lombok.Getter;

@Route(value = "")
@PageTitle("Human Resources App")
@Getter
public class App extends AppLayout
        implements BeforeEnterObserver, AfterNavigationObserver {

    private final PageClient client;


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }

    public App(PageClient client) {
        this.client = client;

    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        Tab dashboard = new Tab(VaadinIcon.HOME.create(), new Span("Home"));
        Tab teams = new Tab(VaadinIcon.BUILDING.create(), new Span("My Class"));
        Tab hire = new Tab(VaadinIcon.BRIEFCASE.create(), new Span("Jobs"));
        Tabs drawerTab = new Tabs(dashboard, teams, hire);
        drawerTab.setOrientation(Tabs.Orientation.VERTICAL);
        drawerTab.addSelectedChangeListener(selectedChangeEvent -> {
            if (!selectedChangeEvent.getSelectedTab().equals(selectedChangeEvent.getPreviousTab())) {

                Tab selectedTab = selectedChangeEvent.getSelectedTab();
                if (dashboard.equals(selectedTab)) {
                    setContent(new Dashboard(
                            client.getTeamService().findAll()
                    this
                    ));
                } /*else if (profile.equals(selectedTab)) {
                    setContent(new EmployeePage(client,client.toView(
                            client.getEmployeeService().findEmployeeById()
                    )));
                } else if (myClass.equals(selectedTab)) {
                    setContent(new MyClass(user, this));
                } else if (jobs.equals(selectedTab)) {
                    setContent(new JobEntries(user, this));
                } else if (settings.equals(selectedTab)) { //TODO
                    setContent(new Home(user, this));
                } else if (logout.equals(selectedTab)) {
                    AuthenticationService.getSessions().remove("token");
                    UI.getCurrent().navigate(LoginView.class);
                }
                */
                drawerTab.setSelectedTab(selectedChangeEvent.getSelectedTab());
            }
        });
    }

    public void switchTo() {

    }
}
