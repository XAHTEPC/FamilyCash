package com.example.familycash;

import com.example.familycash.DataBase.SQLite;
import com.example.familycash.Logic.Crypto;
import com.example.familycash.Logic.Diagramma;
import com.example.familycash.Logic.Toast;
import com.example.familycash.View.IntroFront;
import com.example.familycash.View.MainFront;
import com.example.familycash.View.RegFront;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Front  extends Application {
    public static String password = "";
    public static String login ="";
    public static String id ="";

    public static Pane pane;
    public static Group root;
    public static Scene scene;


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        root = new Group();
        scene = new Scene(root, 1200, 800);
        stage.initStyle(StageStyle.DECORATED);
        pane = IntroFront.getStartFront();
        root.getChildren().add(pane);
        stage.setTitle("Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException {
        launch();
    }

    public static boolean login() throws SQLException, FileNotFoundException {
        login = IntroFront.login.getText();
        password = IntroFront.password.getText();
        password = Crypto.hash(password);
        id = SQLite.getID(login,password);
        if(id.equals("error")) {
            Toast.show("Неверный логин или пароль", pane, 140, 640);
            IntroFront.password.setText("");
            return false;
        }
        else {
            return true;
        }
    }
    public static void registration() throws FileNotFoundException {
        root.getChildren().remove(pane);
        pane = RegFront.getStartFront();
        root.getChildren().add(pane);
    }
    public static void mainMenu() {
        root.getChildren().removeAll(pane);
        try {
            pane = MainFront.getStartFront(3);
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
        root.getChildren().add(pane);
    }
}
