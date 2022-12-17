package com.hr.management.ui.pages;

import com.hr.management.ui.client.PageClient;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import lombok.Getter;

@Getter
public class BaseLayout extends VerticalLayout
        implements BeforeEnterObserver, AfterNavigationObserver {
    private final PageClient client;
    private final Tabs drawer;


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }

    public BaseLayout(PageClient client) {
        this.client = client;
        Tab dashboard = new Tab(VaadinIcon.HOME.create(), new Span("Dashboard"));
        Tab teams = new Tab(VaadinIcon.BUILDING.create(), new Span("Teams"));
        Tab hire = new Tab(VaadinIcon.BRIEFCASE.create(), new Span("Hire"));
        drawer = new Tabs(dashboard, teams, hire);
        drawer.setOrientation(Tabs.Orientation.HORIZONTAL);
        drawer.addSelectedChangeListener(selectedChangeEvent -> {
            if (!selectedChangeEvent.getSelectedTab()
                    .equals(selectedChangeEvent.getPreviousTab())) {

                Tab selectedTab = selectedChangeEvent.getSelectedTab();
                if (dashboard.equals(selectedTab)) {
                    UI.getCurrent().navigate("");
                } else if (teams.equals(selectedTab)) {
                    UI.getCurrent().navigate("teams");
                } else if (hire.equals(selectedTab)) {
                    UI.getCurrent().navigate("hire");
                }
            }
        });
        this.add(new HorizontalLayout(new Anchor("/",
                new HorizontalLayout(
                        new Icon(VaadinIcon.BUILDING_O),
                        new Paragraph("Human Resources System")
                )), drawer));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
    }
}
