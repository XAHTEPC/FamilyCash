package com.example.familycash.View;

import com.example.familycash.Front;
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

public class IntroFront {
    static Button enter;
    static Button reg;
    public static PasswordField password;
    public static TextField login;
    public static Pane getStartFront() throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/intro.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        login = new TextField();
        login.setMinWidth(400);
        login.setMaxWidth(400);
        login.setMinHeight(70);
        login.setMaxHeight(70);
        login.setLayoutX(410);
        login.setLayoutY(180);
        login.setPromptText("login");
        login.setFont(Font.font("STXihei", 20));
        login.setBackground(null);
        pane.getChildren().add(login);

        password = new PasswordField();
        password.setPrefSize(400,70);
        password.setBackground(null);
        password.setPromptText("password");
        password.setLayoutX(410);
        password.setLayoutY(300);
        password.setFont(Font.font("STXihei", 20));
        pane.getChildren().add(password);

        enter = new Button();
        enter.setBackground(null);
        enter.setLayoutX(400);
        enter.setLayoutY(390);
        enter.setMaxSize(400,70);
        enter.setMinSize(400,70);
        enter.setOnAction(t->{
            try {
                if(Front.login())
                    Front.mainMenu();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        pane.getChildren().add(enter);

        reg = new Button();
        reg.setBackground(null);
        reg.setLayoutX(400);
        reg.setLayoutY(480);
        reg.setMaxSize(400,70);
        reg.setMinSize(400,70);
        reg.setOnAction(t->{
            try {
                Front.registration();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        pane.getChildren().add(reg);

        return pane;
    }
}
