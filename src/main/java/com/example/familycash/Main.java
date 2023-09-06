package com.example.familycash;

import com.example.familycash.DataBase.SQLite;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException, InterruptedException {
        SQLite.Conn();
       // SQLite.CreateDB();
        //System.out.println(Integer.parseInt("111"));
        Front.main(args);
    }
}
