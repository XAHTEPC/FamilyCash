package com.example.familycash.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;

public class Diagramma {
    public static Pane getPane(String income, String expenses){
        Pane res = new Pane();
        res.setLayoutX(350);
        res.setLayoutY(200);
        res.setPrefSize(300,300);
        int a = Integer.parseInt(income);
        int b = Integer.parseInt(expenses);
        int sum = a+b;
        System.out.println("a:"+a+" b:"+b);
        System.out.println((double)a/sum * 100);
        double A=50.0,B=50.0;
        if(sum!=0) {
            A = (double) (a / sum) * 100;
            B = (double) (b / sum) * 100;
        }
        System.out.println("A:"+A+" B:"+B);
        ObservableList<PieChart.Data> pieChartData;
        if(sum==0){
            pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Доходы", 50),
                            new PieChart.Data("Расходы", 50));

        }
        else
        pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Доходы", (double)a/sum * 100),
                        new PieChart.Data("Расходы", (double)b/sum * 100));
        final PieChart chart = new PieChart(pieChartData);
        res.getChildren().add(chart);
        return res;

    }
}
