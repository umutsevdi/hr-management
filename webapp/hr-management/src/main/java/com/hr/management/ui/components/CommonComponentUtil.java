package com.hr.management.ui.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.DataLabels;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Marker;
import com.vaadin.flow.component.charts.model.PlotOptionsLine;

import java.util.Map;

public class CommonComponentUtil {

    public static Map.Entry<Button, Button> createButtonsLayout() {
        Button save = new Button("Save");
        Button delete = new Button("Delete");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addClickShortcut(Key.ENTER);
        return Map.entry(save, delete);
    }

    public static Chart setupChart(String[] years, ListSeries... series) {
        Chart chart = new Chart();
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
        return chart;
    }

}
