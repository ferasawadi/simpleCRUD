package com.hinet;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try {
            new DB("Hey there!",1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
