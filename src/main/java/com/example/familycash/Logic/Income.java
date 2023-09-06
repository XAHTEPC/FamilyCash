package com.example.familycash.Logic;

import com.example.familycash.DataBase.SQLite;
import com.example.familycash.Front;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
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

public class Income {
    public String id;
    public String desc;
    public String type;
    public String summ;
    public String date;

    static String[] choise = {"Зарплата","Диведенты","Отпускные","Соц выплаты","Подработка","Другое"};

    public Income(String id, String desc, String type, String summ, String date) {
        this.id = id;
        this.desc = desc;
        this.type = type;
        this.summ = summ;
        this.date = date;
    }

    public static void addIncome() throws FileNotFoundException {
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 418, 318);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        FileInputStream Url= new FileInputStream("png/addIncome.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        root_add.getChildren().add(front);

        ObservableList<String> type = FXCollections.observableArrayList(choise);
        ComboBox<String> comboBoxType = new ComboBox<String>(type);
        comboBoxType.setMaxWidth(215);
        comboBoxType.setValue("Зарплата");
        comboBoxType.setMinWidth(215);
        comboBoxType.setBackground(null);
        comboBoxType.setLayoutX(180);
        comboBoxType.setLayoutY(70);
        root_add.getChildren().add(comboBoxType);

        TextField summa = new TextField();
        summa.setBackground(null);
        summa.setLayoutX(180);
        summa.setLayoutY(114);
        summa.setMaxHeight(32);
        summa.setMaxWidth(215);
        root_add.getChildren().add(summa);

        TextField description = new TextField();
        description.setBackground(null);
        description.setLayoutX(180);
        description.setLayoutY(158);
        description.setMaxHeight(32);
        description.setMaxWidth(215);
        root_add.getChildren().add(description);

        TextField DATA = new TextField();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String string = formatter.format(date);
        DATA.setText(string);
        DATA.setLayoutX(180);
        DATA.setBackground(null);
        DATA.setLayoutY(200);
        DATA.setStyle("-fx-background-color: transparent;");
        DATA.setMaxHeight(32);
        DATA.setMaxWidth(202);
        root_add.getChildren().add(DATA);

        Button save = new Button();
        save.setLayoutY(245);
        save.setLayoutX(121);
        save.setPrefSize(150,32);
        save.setBackground(null);
        root_add.getChildren().add(save);

        Text error = new Text("Проверьте данные");
        error.setLayoutY(53);
        error.setLayoutX(180);
        save.setOnAction(t1 ->{
            root_add.getChildren().remove(error);
            String q1 = comboBoxType.getSelectionModel().getSelectedItem();
            String q2 = summa.getText();
            String q3 = description.getText();
            String q4 = DATA.getText();
            if(!q2.isEmpty()&&check2(q2)&&check(q4)) {
                try {
                    SQLite.addIncome(q1,q2,q3,q4);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                newWindow.close();
            }
            else {
                root_add.getChildren().add(error);
            }
        });
        newWindow.setTitle("Добавление дохода");
        newWindow.setScene(scene_add);
        newWindow.show();
    }

    public static void editIncome(String id, ScrollPane scrollPane, int fl) throws FileNotFoundException, SQLException {
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 418, 318);
        Stage newWindow = new Stage();
        Income expenses = SQLite.getIncome_byID(id);
        newWindow.initStyle(StageStyle.DECORATED);
        FileInputStream Url= new FileInputStream("png/editIncome.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        root_add.getChildren().add(front);

        ObservableList<String> type = FXCollections.observableArrayList(choise);
        ComboBox<String> comboBoxType = new ComboBox<String>(type);
        comboBoxType.setMaxWidth(215);
        comboBoxType.setValue(expenses.type);
        comboBoxType.setMinWidth(215);
        comboBoxType.setBackground(null);
        comboBoxType.setLayoutX(180);
        comboBoxType.setLayoutY(70);
        root_add.getChildren().add(comboBoxType);

        TextField summa = new TextField();
        summa.setBackground(null);
        summa.setLayoutX(180);
        summa.setText(expenses.summ);
        summa.setLayoutY(114);
        summa.setMaxHeight(32);
        summa.setMaxWidth(215);
        root_add.getChildren().add(summa);

        TextField description = new TextField();
        description.setBackground(null);
        description.setText(expenses.desc);
        description.setLayoutX(180);
        description.setLayoutY(158);
        description.setMaxHeight(32);
        description.setMaxWidth(215);
        root_add.getChildren().add(description);

        TextField DATA = new TextField();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String string = formatter.format(date);
        DATA.setText(expenses.date);
        DATA.setLayoutX(180);
        DATA.setBackground(null);
        DATA.setLayoutY(200);
        DATA.setStyle("-fx-background-color: transparent;");
        DATA.setMaxHeight(32);
        DATA.setMaxWidth(202);
        root_add.getChildren().add(DATA);

        Button save = new Button();
        save.setLayoutY(247);
        save.setLayoutX(40);
        save.setPrefSize(150,32);
        save.setBackground(null);
        root_add.getChildren().add(save);

        Button del = new Button();
        del.setLayoutY(247);
        del.setLayoutX(210);
        del.setPrefSize(150,32);
        del.setBackground(null);
        root_add.getChildren().add(del);

        Text error = new Text("Проверьте данные");
        error.setLayoutY(53);
        error.setLayoutX(180);
        save.setOnAction(t1 ->{
            root_add.getChildren().remove(error);
            String q1 = comboBoxType.getSelectionModel().getSelectedItem();
            String q2 = summa.getText();
            String q3 = description.getText();
            String q4 = DATA.getText();
            System.out.println("q4:"+ q4);
            if(!q2.isEmpty()&&check2(q2)&&check(q4)) {
                try {
                    SQLite.UpdateIncome(id,q1,q2,q3,q4);
                    Pane pane = SQLite.getIncome(fl,scrollPane);
                    scrollPane.setContent(pane);
                } catch (SQLException | ParseException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                newWindow.close();
            }
            else {
                root_add.getChildren().add(error);
            }
        });
        del.setOnAction(t1 ->{
            try {
                SQLite.delIncome(id);
                Pane pane = SQLite.getIncome(fl,scrollPane);
                scrollPane.setContent(pane);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            newWindow.close();
        });
        newWindow.setTitle("Редактирование дохода");
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
    public static boolean check2(String summ){
        char[] mas = summ.toCharArray();
        boolean fl = true;
        for(int i=0;i<summ.length();i++){
            if(mas[i]>='0'&&mas[i]<='9')
                continue;
            else
                fl = false;
        }
        return fl;
    }
}
