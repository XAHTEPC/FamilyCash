package com.example.familycash.View;

import com.example.familycash.DataBase.SQLite;
import com.example.familycash.Front;
import com.example.familycash.Logic.Diagramma;
import com.example.familycash.Logic.Expenses;
import com.example.familycash.Logic.Income;
import com.example.familycash.Logic.Time;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Year;

public class MainFront {
    public static Button month = new Button();
    public static Button week = new Button();
    public static Button day = new Button();
    public static Button year = new Button();
    public static Button time = new Button();
    public static Button income = new Button();
    public static Button expenses = new Button();

    public static Button addIncome = new Button();
    public static Button addExpenses = new Button();
    public static Pane getStartFront(int fl) throws FileNotFoundException, SQLException, ParseException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        if(fl==1){
            Url = new FileInputStream("png/mainDay.png");
        }
        else if(fl==2){
            Url = new FileInputStream("png/mainWeek.png");
        }
        else if(fl==3){
            Url = new FileInputStream("png/mainMonth.png");
        }
        else if(fl==4){
            Url = new FileInputStream("png/mainYear.png");
        }
        else
            Url = new FileInputStream("png/mainTime.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);
//        if(fl==5){
//            String b = SQLite.getResExpenses(5);
//            String a = SQLite.getResIncome(5);
//            Pane pane1 = Diagramma.getPane(a,b);
//            Front.pane.getChildren().add(pane1);
//        }

        month.setBackground(null);
        month.setLayoutX(537);
        month.setLayoutY(150);
        month.setMaxSize(149,28);
        month.setMinSize(149,28);
        pane.getChildren().add(month);
        month.setOnAction(t1 ->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = MainFront.getStartFront(3);
                String b = SQLite.getResExpenses(3);
                String a = SQLite.getResIncome(3);
                Pane pane1 = Diagramma.getPane(a,b);
                Front.pane.getChildren().add(pane1);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });

        week.setBackground(null);
        week.setLayoutX(295);
        week.setLayoutY(150);
        week.setMaxSize(172,28);
        week.setMinSize(172,28);
        pane.getChildren().add(week);
        week.setOnAction(t1 ->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = MainFront.getStartFront(2);
                String b = SQLite.getResExpenses(2);
                String a = SQLite.getResIncome(2);
                Pane pane1 = Diagramma.getPane(a,b);
                Front.pane.getChildren().add(pane1);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });

        day.setBackground(null);
        day.setLayoutX(100);
        day.setLayoutY(150);
        day.setMaxSize(116,28);
        day.setMinSize(116,28);
        pane.getChildren().add(day);
        day.setOnAction(t1 ->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = MainFront.getStartFront(1);
                String b = SQLite.getResExpenses(1);
                String a = SQLite.getResIncome(1);
                Pane pane1 = Diagramma.getPane(a,b);
                Front.pane.getChildren().add(pane1);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });

        year.setBackground(null);
        year.setLayoutX(756);
        year.setLayoutY(150);
        year.setMaxSize(86,28);
        year.setMinSize(86,28);
        pane.getChildren().add(year);
        year.setOnAction(t1 ->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = MainFront.getStartFront(4);
                String b = SQLite.getResExpenses(4);
                String a = SQLite.getResIncome(4);
                Pane pane1 = Diagramma.getPane(a,b);
                Front.pane.getChildren().add(pane1);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });

        time.setBackground(null);
        time.setLayoutX(921);
        time.setLayoutY(150);
        time.setMaxSize(179,28);
        time.setMinSize(179,28);
        pane.getChildren().add(time);
        time.setOnAction(t1 ->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Time.start(5,3);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        income.setBackground(null);
        income.setLayoutX(150);
        income.setLayoutY(55);
        income.setMaxSize(292,42);
        income.setMinSize(292,42);
        pane.getChildren().add(income);
        income.setOnAction(t1 ->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = IncomeFront.getStartFront(3);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        expenses.setBackground(null);
        expenses.setLayoutX(740);
        expenses.setLayoutY(55);
        expenses.setMaxSize(310,42);
        expenses.setMinSize(310,42);
        pane.getChildren().add(expenses);
        expenses.setOnAction(t1 ->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = ExpensesFront.getStartFront(3);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });
        addExpenses.setBackground(null);
        addExpenses.setLayoutX(1070);
        addExpenses.setLayoutY(55);
        addExpenses.setMaxSize(42,42);
        addExpenses.setMinSize(42,42);
        pane.getChildren().add(addExpenses);
        addExpenses.setOnAction(t1 ->{
            try {
                Expenses.addExpenses();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
        addIncome.setBackground(null);
        addIncome.setLayoutX(462);
        addIncome.setLayoutY(55);
        addIncome.setMaxSize(42,42);
        addIncome.setMinSize(42,42);
        pane.getChildren().add(addIncome);
        addIncome.setOnAction(t1 ->{
            try {
                Income.addIncome();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return pane;
    }
}
