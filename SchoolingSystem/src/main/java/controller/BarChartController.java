package controller;

import domain.Student;
import domain.StudentMedie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import service.Service;

import java.awt.event.MouseEvent;

public class BarChartController {
    private Service service = null;
    ObservableList<StudentMedie> studentMedie = FXCollections.observableArrayList();

    @FXML
    private BarChart<String, Float> mediiBarChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    public void setService(Service service) {
        this.service = service;
        initialiaze();
    }

    private void initialiaze() {
        XYChart.Series set = new XYChart.Series();
        for (StudentMedie studentMedie : service.getAllStudentiMedie()) {
            set.getData().add(new XYChart.Data(studentMedie.getNume(), studentMedie.getMedie()));
        }
        mediiBarChart.getData().addAll(set);

        for (final XYChart.Series<String, Float> series : mediiBarChart.getData()) {
            for (final XYChart.Data<String, Float> data : series.getData()) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(data.getYValue().toString());
                Tooltip.install(data.getNode(), tooltip);
            }
        }

        x.setLabel("STUDENT");
        y.setLabel("MEDIE");

        for (XYChart.Series<String, Float> s : mediiBarChart.getData()) {
            for (XYChart.Data<String, Float> d : s.getData()) {
                Tooltip.install(d.getNode(), new Tooltip(String.format("%.2f", d.getYValue())));
            }
        }

        mediiBarChart.setLegendVisible(false);
        mediiBarChart.setBarGap(3);
        mediiBarChart.setCategoryGap(15);

        Node n;
        for(Integer i = 0; i < service.getAllStudentiMedie().size(); i++) {
            n = mediiBarChart.lookup(".data" + i.toString() + ".chart-bar");
            n.setStyle("-fx-bar-fill: orange");
        }

        mediiBarChart.setCursor(Cursor.CROSSHAIR);
    }
}
