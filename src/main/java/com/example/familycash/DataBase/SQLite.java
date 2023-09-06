package com.example.familycash.DataBase;

import com.example.familycash.Front;
import com.example.familycash.Logic.Expenses;
import com.example.familycash.Logic.Income;
import com.example.familycash.Logic.Time;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SQLite {
    public static Connection conn;
    public static Statement statmt;

    public static ResultSet resSet;
    public static void Conn() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:FamilyDATA.db");
        System.out.println("База Подключена!");
        statmt = conn.createStatement();
    }
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists Income (\n" +
                "    id          INTEGER PRIMARY KEY,\n" +
                "    type        TEXT,\n" +
                "    summa       TEXT,\n" +
                "    description TEXT,\n" +
                "    date        TEXT\n" +
                ");\n");
        statmt.execute("CREATE TABLE if not exists Expenses (\n" +
                "    id          INTEGER PRIMARY KEY,\n" +
                "    type        TEXT,\n" +
                "    summa       TEXT,\n" +
                "    description TEXT,\n" +
                "    date        TEXT\n" +
                ");\n");
        statmt.execute("CREATE TABLE if not exists Acc (\n" +
                "    id    INTEGER PRIMARY KEY,\n" +
                "    login TEXT,\n" +
                "    pass  TEXT\n" +
                ");");
        System.out.println("Таблица создана или уже существует.");
    }

    public static boolean checkLogin(String login) throws SQLException {
        String s = "true";
        resSet = statmt.executeQuery("SELECT id FROM Acc WHERE login = '" + login +"';");
        while (resSet.next()){
            s = resSet.getString("id");
        }
        if(s.equals("true"))
            return true;
        else
            return false;
    }
    public static void addUser(String login, String pass) throws  SQLException{
        statmt.execute("INSERT INTO Acc (\n" +
                "                    login,\n" +
                "                    pass\n" +
                "                )\n" +
                "                VALUES (\n" +
                "                    '"+login+"',\n" +
                "                    '"+pass+"'\n" +
                "                );");
    }
    public static String getID(String login, String pass) throws SQLException {
        String id="error";
        resSet = statmt.executeQuery("SELECT * FROM Acc WHERE login = '" + login +
                "' and pass ='" + pass+"';");
        while(resSet.next()){
            id = resSet.getString("id");
        }
        return id;
    }
    public static void addIncome(String type, String summa, String desc, String date) throws SQLException {
        statmt.execute("INSERT INTO 'Income' ('type', 'summa', 'description', 'date', 'acc_id') VALUES ('"+ type+"', '" + summa +
                "', '" + desc + "', '" + date + "', '"+ Front.id+"');");
    }
    public static void addExpenses(String type, String summa, String desc, String date) throws SQLException {
        statmt.execute("INSERT INTO 'Expenses' ('type', 'summa', 'description', 'date', 'acc_id') VALUES ('"+ type+"', '" + summa +
                "', '" + desc + "', '" + date + "', '"+Front.id+"');");
    }
    public static String getLastExpenses() throws SQLException {
            resSet=statmt.executeQuery("select count(id) from Expenses;");
            String ans = resSet.getString("count(id)");
            return ans;
    }
    public static String getLastIncome() throws SQLException {
        resSet=statmt.executeQuery("select count(id) from Income;");
        String ans = resSet.getString("count(id)");
        return ans;
    }

    public static Pane getExpenses(int fl, ScrollPane scrollPane) throws SQLException, ParseException, FileNotFoundException {
        Pane res = new Pane();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new Date();
        String string = formatter.format(date);
        Calendar calendar = Calendar.getInstance();
        Date oldDate = new Date();
        String old;
        String k = getLastExpenses();
        int kol = Integer.parseInt(k);
        System.out.println("kol"+ kol);
        Expenses[] mas = new Expenses[kol];
        if(fl==2) {
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("week:" + old);
        }
        else if(fl==3){
            calendar.add(Calendar.MONTH, -1);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("month:"+ old);
        }
        else if(fl==4){
            calendar.add(Calendar.YEAR, -1);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("year:"+ old);
        }
        else{
            oldDate = Time.oldDate;
            date = Time.newDate;
        }
        int w=0;
        if(fl==1){
            resSet = statmt.executeQuery("SELECT * FROM 'Expenses' WHERE date = '"+string+"' and acc_id='"+Front.id+"';");
            while (resSet.next()){
                String id = resSet.getString("id");
                String type = resSet.getString("type");
                String desc = resSet.getString("description");
                String summa = resSet.getString("summa");
                mas[w] = new Expenses(id,desc,type,summa,string);
                w++;
            }
        }
        else {
            resSet = statmt.executeQuery("SELECT * FROM 'Expenses' WHERE acc_id='"+Front.id+ "';");
            while (resSet.next()) {
                String id = resSet.getString("id");
                String type = resSet.getString("type");
                String desc = resSet.getString("description");
                String summa = resSet.getString("summa");
                String DATA = resSet.getString("date");
                Date thisDate = (Date) formatter.parseObject(DATA);
                if (thisDate.before(date) && thisDate.after(oldDate) || thisDate.equals(oldDate) || thisDate.equals(date)) {
                    mas[w] = new Expenses(id, desc, type, summa, DATA);
                    w++;
                }
            }
        }
        for(int i=0, u=35; i<w; i++, u+=50){
            Text num = new Text();
            num.setText(String.valueOf(i+1));
            num.setLayoutY(u);
            num.setLayoutX(10);
            res.getChildren().add(num);
            num.setFont((Font.font("Jost", 25)));

            Text type = new Text();
            type.setText(mas[i].type);
            type.setLayoutY(u);
            type.setLayoutX(150);
            res.getChildren().add(type);
            type.setFont((Font.font("Jost", 25)));

            Text desc = new Text();
            desc.setText(mas[i].desc);
            desc.setLayoutY(u);
            desc.setLayoutX(350);
            res.getChildren().add(desc);
            desc.setFont((Font.font("Jost", 25)));

            Text summ = new Text();
            summ.setText(mas[i].summ);
            summ.setLayoutY(u);
            summ.setLayoutX(660);
            res.getChildren().add(summ);
            summ.setFont((Font.font("Jost", 25)));

            Text dat = new Text();
            dat.setText(mas[i].date);
            dat.setLayoutY(u);
            dat.setLayoutX(860);
            res.getChildren().add(dat);
            dat.setFont((Font.font("Jost", 20)));

            final String a = mas[i].id;

            FileInputStream Url = new FileInputStream("png/pen.png");
            Image url = new Image(Url);
            ImageView pen = new ImageView(url);

            Button edit = new Button();
            edit.setGraphic(pen);
            edit.setStyle("-fx-background-color: transparent;");
            edit.setLayoutX(1050);
            edit.setLayoutY(u-28);
            res.getChildren().add(edit);

            edit.setOnAction(t ->{
                try {
                    change(a, scrollPane,fl);

                } catch (SQLException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return res;
    }

    public static Pane getIncome(int fl, ScrollPane scrollPane) throws SQLException, ParseException, FileNotFoundException {
        Pane res = new Pane();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new Date();
        String string = formatter.format(date);
        Calendar calendar = Calendar.getInstance();
        Date oldDate = new Date();
        String old;
        String k = getLastIncome();
        int kol = Integer.parseInt(k);
        Income[] mas = new Income[kol];
        if(fl==2) {
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("week:" + old);
        }
        else if(fl==3){
            calendar.add(Calendar.MONTH, -1);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("month:"+ old);
        }
        else if(fl==4){
            calendar.add(Calendar.YEAR, -1);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("year:"+ old);
        }
        else{
            oldDate = Time.oldDate;
            date = Time.newDate;
        }
        int w=0;
        if(fl==1){
            resSet = statmt.executeQuery("SELECT * FROM 'Income' WHERE 'date' = '"+string+" 'and acc_id='"+Front.id+"';");
            while (resSet.next()){
                String id = resSet.getString("id");
                String type = resSet.getString("type");
                String desc = resSet.getString("description");
                String summa = resSet.getString("summa");
                mas[w] = new Income(id,desc,type,summa,string);
                w++;
            }
        }
        else {
            resSet = statmt.executeQuery("SELECT * FROM 'Income' WHERE acc_id='"+Front.id+"';");
            while (resSet.next()) {
                String id = resSet.getString("id");
                String type = resSet.getString("type");
                String desc = resSet.getString("description");
                String summa = resSet.getString("summa");
                String DATA = resSet.getString("date");
                Date thisDate = (Date) formatter.parseObject(DATA);
                if (thisDate.before(date) && thisDate.after(oldDate) || thisDate.equals(oldDate) || thisDate.equals(date)) {
                    mas[w] = new Income(id, desc, type, summa, DATA);
                    w++;
                }
            }
        }
        for(int i=0, u=35; i<w; i++, u+=50){
            //System.out.println("i+"+i);
            //System.out.println(mas[i].id+"|"+mas[i].type+"|"+mas[i].desc+"|"+mas[i].summ+"|"+mas[i].date);
            Text num = new Text();
            num.setText(String.valueOf(i+1));
            num.setLayoutY(u);
            num.setLayoutX(10);
            res.getChildren().add(num);
            num.setFont((Font.font("Jost", 25)));

            Text type = new Text();
            type.setText(mas[i].type);
            type.setLayoutY(u);
            type.setLayoutX(150);
            res.getChildren().add(type);
            type.setFont((Font.font("Jost", 25)));

            Text desc = new Text();
            desc.setText(mas[i].desc);
            desc.setLayoutY(u);
            desc.setLayoutX(350);
            res.getChildren().add(desc);
            desc.setFont((Font.font("Jost", 25)));

            Text summ = new Text();
            summ.setText(mas[i].summ);
            summ.setLayoutY(u);
            summ.setLayoutX(660);
            res.getChildren().add(summ);
            summ.setFont((Font.font("Jost", 25)));

            Text dat = new Text();
            dat.setText(mas[i].date);
            dat.setLayoutY(u);
            dat.setLayoutX(860);
            res.getChildren().add(dat);
            dat.setFont((Font.font("Jost", 20)));

            final String a = mas[i].id;

            FileInputStream Url = new FileInputStream("png/pen.png");
            Image url = new Image(Url);
            ImageView pen = new ImageView(url);

            Button edit = new Button();
            edit.setGraphic(pen);
            edit.setStyle("-fx-background-color: transparent;");
            edit.setLayoutX(1050);
            edit.setLayoutY(u-28);
            res.getChildren().add(edit);

            edit.setOnAction(t ->{
                try {
                    change2(a, scrollPane,fl);

                } catch (SQLException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return res;
    }
    public static Expenses getExpenses_byID(String id) throws SQLException {
        Expenses expenses = null;
        System.out.println("id:"+ id);
        resSet = statmt.executeQuery("Select * from 'Expenses' where id = "+id+" ;");
        while (resSet.next()){
            String type = resSet.getString("type");
            String desc = resSet.getString("description");
            String summa = resSet.getString("summa");
            String DATA = resSet.getString("date");
            expenses = new Expenses(id,desc,type,summa,DATA);
        }
        return expenses;
    }
    public static Income getIncome_byID(String id) throws SQLException {
        Income income = null;
        System.out.println("id:"+ id);
        resSet = statmt.executeQuery("Select * from 'Income' where id = "+id+" ;");
        while (resSet.next()){
            String type = resSet.getString("type");
            String desc = resSet.getString("description");
            String summa = resSet.getString("summa");
            String DATA = resSet.getString("date");
            income = new Income(id,desc,type,summa,DATA);
        }
        return income;
    }
    public static String getResIncome(int fl) throws SQLException, ParseException {
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new Date();
        String string = formatter.format(date);
        Calendar calendar = Calendar.getInstance();
        Date oldDate = new Date();
        String old;
        int summ=0;
        if(fl==2) {
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("week:" + old);
        }
        else if(fl==3){
            calendar.add(Calendar.MONTH, -1);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("month:"+ old);
        }
        else if(fl==4){
            calendar.add(Calendar.YEAR, -1);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("year:"+ old);
        }
        else{
            oldDate = Time.oldDate;
            date = Time.newDate;
        }
        if(fl==1){
            resSet = statmt.executeQuery("SELECT * FROM 'Income' WHERE date = '"+string+"' and acc_id='"+Front.id+"';");
            while (resSet.next()){
                String summa = resSet.getString("summa");
                int o = Integer.parseInt(summa);
                summ+=o;
            }
        }
        else {
            resSet = statmt.executeQuery("SELECT * FROM 'Income' WHERE acc_id='"+Front.id+"';");
            while (resSet.next()) {

                String summa = resSet.getString("summa");
                String DATA = resSet.getString("date");
                Date thisDate = (Date) formatter.parseObject(DATA);
                if (thisDate.before(date) && thisDate.after(oldDate) || thisDate.equals(oldDate) || thisDate.equals(date)) {
                    int o = Integer.parseInt(summa);
                    summ+=o;
                }
            }
        }
        String res = String.valueOf(summ);
        return res;
    }
    public static String getResExpenses(int fl) throws SQLException, ParseException {
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new Date();
        String string = formatter.format(date);
        Calendar calendar = Calendar.getInstance();
        Date oldDate = new Date();
        String old;
        int summ=0;
        if(fl==2) {
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("week:" + old);
        }
        else if(fl==3){
            calendar.add(Calendar.MONTH, -1);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("month:"+ old);
        }
        else if(fl==4){
            calendar.add(Calendar.YEAR, -1);
            oldDate.setTime(calendar.getTime().getTime());
            old = formatter.format(oldDate);
            System.out.println("year:"+ old);
        }
        else{
            oldDate = Time.oldDate;
            date = Time.newDate;
        }
        if(fl==1){
            resSet = statmt.executeQuery("SELECT * FROM 'Expenses' WHERE date = '"+string+"' and acc_id='"+Front.id+"';");
            while (resSet.next()){
                String summa = resSet.getString("summa");
                int o = Integer.parseInt(summa);
                summ+=o;
            }
        }
        else {
            resSet = statmt.executeQuery("SELECT * FROM 'Expenses' WHERE acc_id='"+Front.id+"';");
            while (resSet.next()) {

                String summa = resSet.getString("summa");
                String DATA = resSet.getString("date");
                Date thisDate = (Date) formatter.parseObject(DATA);
                if (thisDate.before(date) && thisDate.after(oldDate) || thisDate.equals(oldDate) || thisDate.equals(date)) {
                    int o = Integer.parseInt(summa);
                    summ+=o;
                }
            }
        }
        String res = String.valueOf(summ);
        return res;
    }
    public static void UpdateExpenses(String id, String type, String summa, String description,
                                           String date) throws SQLException {
        statmt.execute("UPDATE 'Expenses'" +
                "SET  ('type', 'summa', 'description', 'date') " +
                "= ( '" + type + "', '" + summa + "', '" +  description + "', '" + date + "') " +
                "WHERE id =" + id +";");
    }
    public static void UpdateIncome(String id, String type, String summa, String description,
                                      String date) throws SQLException {
        statmt.execute("UPDATE 'Income'" +
                "SET  ('type', 'summa', 'description', 'date') " +
                "= ( '" + type + "', '" + summa + "', '" +  description + "', '" + date + "') " +
                "WHERE id =" + id +";");
    }

    public static void change(String id, ScrollPane scrollPane, int fl) throws SQLException, FileNotFoundException {
        Expenses.editExpenses(id,scrollPane,fl);
    }
    public static void change2(String id, ScrollPane scrollPane, int fl) throws SQLException, FileNotFoundException {
        Income.editIncome(id,scrollPane,fl);
    }

    public static void delExpenses(String id) throws SQLException {
        statmt.execute(" DELETE FROM 'Expenses' WHERE id ='" + id +"';");
    }

    public static void delIncome(String id) throws SQLException {
        statmt.execute(" DELETE FROM 'Income' WHERE id ='" + id +"';");
    }

}
