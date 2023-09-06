package com.example.familycash.View;

import com.example.familycash.DataBase.SQLite;
import com.example.familycash.Front;
import com.example.familycash.Logic.Expenses;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;

public class DetailExpensesFront {
    public static Button home = new Button();
    public static Pane getStartFront(int fl) throws FileNotFoundException, SQLException, ParseException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/expenses.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutX(50);
        scrollPane.setLayoutY(200);
        scrollPane.setMaxHeight(500);
        scrollPane.setMaxWidth(1100);
        scrollPane.setMinHeight(500);
        scrollPane.setMinWidth(1100);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        pane.getChildren().add(scrollPane);

        Pane paneScroll = new Pane();
        paneScroll = SQLite.getExpenses(fl, scrollPane);
        scrollPane.setContent(paneScroll);

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
