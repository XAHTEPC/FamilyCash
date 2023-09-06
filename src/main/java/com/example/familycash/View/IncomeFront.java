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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;

public class IncomeFront {
    public static Button month = new Button();
    public static Button week = new Button();
    public static Button day = new Button();
    public static Button year = new Button();
    public static Button time = new Button();
    public static Button income = new Button();
    public static Button expenses = new Button();

    public static Button addIncome = new Button();
    public static Button addExpenses = new Button();
    public static Button home = new Button();
    public static Button detail = new Button();
    public static Pane getStartFront(int fl) throws FileNotFoundException, SQLException, ParseException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        if(fl==1){
            Url = new FileInputStream("png/incomeDay.png");
        }
        else if(fl==2){
            Url = new FileInputStream("png/incomeWeek.png");
        }
        else if(fl==3){
            Url = new FileInputStream("png/incomeMonth.png");
        }
        else if(fl==4){
            Url = new FileInputStream("png/incomeYear.png");
        }
        else
            Url = new FileInputStream("png/incomeTime.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        Text text = new Text();
        text.setLayoutY(400);
        text.setLayoutX(450);
        text.setFont(Font.font("Joast", 100));
        text.setFill(Color.rgb(72, 104, 134));
        String result = SQLite.getResIncome(fl);
        text.setText(result);
        pane.getChildren().add(text);

        month.setBackground(null);
        month.setLayoutX(537);
        month.setLayoutY(150);
        month.setMaxSize(149,28);
        month.setMinSize(149,28);
        pane.getChildren().add(month);
        month.setOnAction(t1 ->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = IncomeFront.getStartFront(3);
            } catch (FileNotFoundException | SQLException | ParseException e) {
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
                Front.pane = IncomeFront.getStartFront(2);
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
                Front.pane = IncomeFront.getStartFront(1);
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
                Front.pane = IncomeFront.getStartFront(4);
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
                Time.start(5,1);
            } catch (FileNotFoundException e) {
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
        home.setBackground(null);
        home.setLayoutX(25);
        home.setLayoutY(725);
        home.setMaxSize(50,50);
        home.setMinSize(50,50);
        pane.getChildren().add(home);
        home.setOnAction(t1 ->{
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

        detail.setBackground(null);
        detail.setLayoutX(461);
        detail.setLayoutY(510);
        detail.setMaxSize(278,25);
        detail.setMinSize(278,25);
        pane.getChildren().add(detail);
        detail.setOnAction(t1 ->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = DetailIncomeFront.getStartFront(fl);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });

        return pane;
    }
}
