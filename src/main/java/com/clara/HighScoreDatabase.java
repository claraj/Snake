package com.clara;

import java.sql.*;

public class HighScoreDatabase {
    static String db_url = "jdbc:sqlite:highScore.db";

    // method that creates the database and tables.
    protected static void createTables() {
        try (Connection connection = DriverManager.getConnection(db_url);
             Statement statement = connection.createStatement()) {

            String createCube = "create table IF NOT EXISTS high_scores (id integer PRIMARY KEY, player_name varchar, score integer );";
            statement.execute(createCube);

        } catch (SQLException sqlex) {
            System.out.println("Unable to connect to database because " + sqlex);
        }
    }


    protected static void addScore(String name, double score) {
        try (Connection connection = DriverManager.getConnection(HighScoreDatabase.db_url);
             PreparedStatement psAdd = connection.prepareStatement("INSERT INTO high_scores ('player_name', 'score') VALUES (?,?)")) {
            psAdd.setString(1, name);
            psAdd.setDouble(2, score);
            psAdd.executeUpdate();
        } catch (SQLException sqlex) {
            System.out.println("Error inserting data to database.\n" + sqlex);
        }
    }

    protected static void InsertStartingData(){
        try (Connection connection = DriverManager.getConnection(HighScoreDatabase.db_url);
             Statement statement = connection.createStatement()) {

            String addItem = "INSERT INTO high_scores ('player_name', 'score') VALUES " + "('Null',0)";
            statement.execute(addItem);
        } catch (SQLException sqlex) {
            System.out.println("Error inserting data to database.\n" + sqlex);
        }

    }

    protected static boolean checkForData(){
        try (Connection connection = DriverManager.getConnection(HighScoreDatabase.db_url);
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From high_scores;");

            int rows = 0;

            while (rs.next()) {
                rows ++;
            }

            if (rows == 0){
                return false;
            } else {return true;}

        } catch (SQLException e) {
            System.out.print("Cannot check score: " + e);
            return false;
        }
    }

    protected static int getHighScore(){
        try (Connection connection = DriverManager.getConnection(HighScoreDatabase.db_url);
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select TOP 1 score From high_scores Order By score;");

            int score = 0;

            while (rs.next()) {
                score = rs.getInt("score");
            }

            return score;

        } catch (SQLException e) {
            System.out.print("Cannot retrieve score: " + e);
            return 0;
        }
    }

    protected static String getHighPlayer(){
        try (Connection connection = DriverManager.getConnection(HighScoreDatabase.db_url);
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select TOP 1 player_name From high_scores Order By score;");

            String name = "";

            while (rs.next()) {
                name = rs.getString("player_name");
            }

            return name;

        } catch (SQLException e) {
            System.out.print("Cannot retrieve player name: " + e);
            return null;
        }
    }
}
