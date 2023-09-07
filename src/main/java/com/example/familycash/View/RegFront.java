package com.example.familycash.View;

import com.example.familycash.DataBase.SQLite;
import com.example.familycash.Front;
import com.example.familycash.Logic.Crypto;
import com.example.familycash.Logic.Toast;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class RegFront {
    static Button reg;


    public static Pane getStartFront() throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/reg.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);
        TextField input_login = new TextField();
        input_login.setMinWidth(400);
        input_login.setMaxWidth(400);
        input_login.setMinHeight(70);
        input_login.setMaxHeight(70);
        input_login.setLayoutX(400);
        input_login.setLayoutY(130);
        input_login.setPromptText("login");
        input_login.setFont(Font.font("STXihei", 20));
        input_login.setBackground(null);
        pane.getChildren().add(input_login);

        PasswordField passwordField = new PasswordField();
        passwordField.setPrefSize(400, 70);
        passwordField.setBackground(null);
        passwordField.setPromptText("password");
        passwordField.setLayoutX(400);
        passwordField.setLayoutY(250);
        passwordField.setFont(Font.font("STXihei", 20));
        pane.getChildren().add(passwordField);

        PasswordField passwordField2 = new PasswordField();
        passwordField2.setPrefSize(400, 70);
        passwordField2.setBackground(null);
        passwordField2.setPromptText("password");
        passwordField2.setLayoutX(400);
        passwordField2.setLayoutY(370);
        passwordField2.setFont(Font.font("STXihei", 20));
        pane.getChildren().add(passwordField2);

        reg = new Button();
        reg.setBackground(null);
        reg.setLayoutX(400);
        reg.setLayoutY(460);
        reg.setMaxSize(400, 70);
        reg.setMinSize(400, 70);
        pane.getChildren().add(reg);

        reg.setOnAction(t -> {
            Boolean flag = true;
            try {
                flag = SQLite.checkLogin(input_login.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (passwordField.getText().equals(passwordField2.getText()) && !passwordField.getText().isEmpty()
                    && !input_login.getText().isEmpty() && flag) {
                try {
                    String pass = Crypto.hash(passwordField.getText());
                    SQLite.addUser(input_login.getText(), pass);
                    Front.id = SQLite.getID(input_login.getText(),pass);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Front.mainMenu();
            } else if (input_login.getText().isEmpty()) {
                Toast.show("Введите логин!", pane, 60, 670);
            } else if (!flag) {
                Toast.show("Данный логин занят!", pane, 60, 670);
                input_login.setText("");
            } else if (passwordField.getText().isEmpty()) {
                Toast.show("Введите пароль!", pane, 60, 670);
                passwordField.setText("");
                passwordField2.setText("");
            } else {
                Toast.show("Пароли не совпадают!", pane, 60, 670);
                passwordField.setText("");
                passwordField2.setText("");
            }
        });

        Button back = new Button();
        back.setLayoutY(550);
        back.setLayoutX(400);
        back.setMaxSize(400, 70);
        back.setMinSize(400, 70);
        back.setBackground(null);
        pane.getChildren().add(back);
        back.setOnAction(t1 -> {
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = IntroFront.getStartFront();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });

        return pane;
    }
}
