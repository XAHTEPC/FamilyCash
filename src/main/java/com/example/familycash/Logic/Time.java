package com.example.familycash.Logic;

import com.example.familycash.DataBase.SQLite;
import com.example.familycash.Front;
import com.example.familycash.View.ExpensesFront;
import com.example.familycash.View.IncomeFront;
import com.example.familycash.View.MainFront;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    public static Date oldDate;
    public static Date newDate;
    public static String olddate;
    public static String newdate;

    public Time(Date oldDate, Date newDate, String olddate, String newdate) {
        this.oldDate = oldDate;
        this.newDate = newDate;
        this.olddate = olddate;
        this.newdate = newdate;
    }
    public static void start(int fl, int flag) throws FileNotFoundException {
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 318, 238);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        FileInputStream Url= new FileInputStream("png/Time.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        root_add.getChildren().add(front);

        TextField startDate = new TextField();
        startDate.setBackground(null);
        startDate.setLayoutX(90);
        startDate.setPromptText("30-01-2020");
        startDate.setLayoutY(70);
        startDate.setMaxHeight(32);
        startDate.setMaxWidth(180);
        root_add.getChildren().add(startDate);

        TextField endDate = new TextField();
        endDate.setBackground(null);
        endDate.setPromptText("04-02-2020");
        endDate.setLayoutX(90);
        endDate.setLayoutY(114);
        endDate.setMaxHeight(32);
        endDate.setMaxWidth(180);
        root_add.getChildren().add(endDate);

        Button save = new Button();
        save.setLayoutY(159);
        save.setLayoutX(75);
        save.setPrefSize(150,32);
        save.setBackground(null);
        root_add.getChildren().add(save);
        Text error = new Text("Проверьте данные");
        error.setLayoutY(53);
        error.setLayoutX(180);

        save.setOnAction(t1 ->{
            root_add.getChildren().remove(error);
            String q2 = startDate.getText();
            String q3 = endDate.getText();
            Format formatter = new SimpleDateFormat("dd-MM-yyyy");
            if(!q2.isEmpty()&&!q3.isEmpty()&&check(q2)&&check(q3)) {
                try {
                    oldDate = (Date) formatter.parseObject(q2);
                    newDate = (Date) formatter.parseObject(q3);
                    Front.root.getChildren().remove(Front.pane);
                    if(flag==1)
                        Front.pane = IncomeFront.getStartFront(fl);
                    else if(flag==2)
                        Front.pane = ExpensesFront.getStartFront(fl);
                    else{
                        Front.pane = MainFront.getStartFront(5);
                        String b = SQLite.getResExpenses(5);
                        String a = SQLite.getResIncome(5);
                        Pane pane1 = Diagramma.getPane(a,b);
                        Front.pane.getChildren().add(pane1);
                    }

                    Front.root.getChildren().add(Front.pane);
                } catch (ParseException | FileNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
                newWindow.close();
            }
            else {
                root_add.getChildren().add(error);
            }
        });
        newWindow.setTitle("Задать период");
        newWindow.setScene(scene_add);
        newWindow.show();
    }
    public static boolean check(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        boolean fl = true;
        char[] mas = date.toCharArray();
        fl = mas[0]>='0'&&mas[0]<='9'&&mas[1]>='0'&&mas[1]<='9'&&mas[2]=='-'&&
                mas[3]>='0'&&mas[3]<='9'&&mas[4]>='0'&&mas[4]<='9'&&mas[5]=='-'&&
                mas[6]=='2'&&mas[7]=='0'&&mas[8]>='0'&&mas[8]<='9'&&mas[9]>='0'&&mas[9]<='9';
        if(fl) {
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(date.trim());
            } catch (ParseException pe) {
                return false;
            }
            System.out.println("ok");
            return true;
        }
        else
            return false;
    }
}
