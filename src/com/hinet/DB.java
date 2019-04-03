package com.hinet;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DB implements Initializable {
	public static boolean isWorkin;
	private Connection connection;
	private String content;
	private int user_id;
	private ArrayList<Integer> data;
	private int getUser_id;
	private String getContent;
	private String getuser_id;
	private String getCreateat;

	@FXML
	TableView<DBData> data_tbl;
	@FXML
	TableColumn<DBData, String> tbl_id;
	@FXML
	TableColumn<DBData, String> tbl_content;

	@FXML
	TableColumn<DBData, String> tbl_date;

	@FXML
	TableColumn<DBData, String> tbl_user;

	@FXML
	DatePicker date_tf;
	@FXML
	TextField content_tf, user_tf;
	DBData dbData;
	private ObservableList<DBData> dbDataObservableList = FXCollections.observableArrayList();

	public DB() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	private int insert() throws Exception {
		dbDataObservableList.clear();
		getData();
		System.out.println("============= To DB ==========");
		int numRowsInserted = 0;

		PreparedStatement ps = null;
		try {
			content = content_tf.getText();
			LocalDate temp = date_tf.getValue();
			user_id = Integer.parseInt(user_tf.getText());
			String sql = "INSERT INTO issue_data(data_content, user_id,created_at) VALUES('" + content + "','" + user_id + "','" + temp + "')";
			System.out.println(sql);
			ps = connection.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText("Success ,Every Thing is Set");
			alert.setContentText("Congrats , Data has been Entered Successfully!");
			alert.showAndWait();

			display();
		} catch (RuntimeException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error ,There is an error");
			alert.setContentText("Enter Data at the TextFileds Before!");

			alert.showAndWait();
		} finally {
			close(ps);
		}
		return numRowsInserted;
	}

	public void getData() {
		System.out.println("=================== Out of DB ================");

		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * from issue_data ";
		try {
			String Path = System.getProperty("user.dir");
			String url = "jdbc:sqlite:" + Path + "/issues_db.db";
			// create a connection to the database .
			connection = DriverManager.getConnection(url);
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			// loop through the result set
			while (rs.next()) {
				dbDataObservableList.add(new DBData(rs.getInt("id"), rs.getString("data_content"), rs.getString("user_id"), rs.getString("created_at")));
			}
		} catch (SQLException e) {
			e.getMessage();
		} finally {
			close(stmt);
		}

	}


	@FXML
	private void deleteRow() throws Exception {
		System.out.println("=================== Delete Row ================");
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * from issue_data ";
		try {
			String Path = System.getProperty("user.dir");
			String url = "jdbc:sqlite:" + Path + "/issues_db.db";
			// create a connection to the database .
			dbData = data_tbl.getSelectionModel().getSelectedItem();
			int id = dbData.getId();
			connection = DriverManager.getConnection(url);
			stmt = connection.createStatement();
			String delSql = "delete from issue_data Where id =" + id + "";
			stmt.executeUpdate(delSql);
			dbDataObservableList.clear();
			getData();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText("Success ,Every Thing is Set");
			alert.setContentText(" Data has been Deleted Successfully!");
			alert.showAndWait();
		} catch (RuntimeException w) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error ,There is an error");
			alert.setContentText("choose Data to Delete First!");

			alert.showAndWait();
		}
	}

	@FXML
	private void display() {
		dbDataObservableList.clear();
		data_tbl.setItems(dbDataObservableList);
		getData();
		tbl_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		tbl_content.setCellValueFactory(new PropertyValueFactory<>("content"));
		tbl_user.setCellValueFactory(new PropertyValueFactory<>("user_id"));
		tbl_date.setCellValueFactory(new PropertyValueFactory<>("create_date"));
		data_tbl.setItems(dbDataObservableList);
	}

	@FXML
	private void update() {
		System.out.println("=================== UPDATE ================");
		dbDataObservableList.clear();
		getData();
		display();

		Statement stmt = null;
		ResultSet rs = null;

		try {
			try {
				String temp_content = content_tf.getText();
				String temp_user = user_tf.getText();
				String temp_date = date_tf.getValue().toString();

				String Path = System.getProperty("user.dir");
				String url = "jdbc:sqlite:" + Path + "/issues_db.db";

				// create a connection to the database .
				connection = DriverManager.getConnection(url);
				dbData = data_tbl.getSelectionModel().getSelectedItem();
				System.out.println(dbData);
				int id = dbData.getId();
				String tempsql = "UPDATE issue_data SET data_content = '" + temp_content + "',user_id = '" + temp_user + "',created_at = '" + temp_date + "' where id='" + id + "'";

				stmt = connection.createStatement();
				stmt.executeUpdate(tempsql);


			} catch (NullPointerException  | SQLException e) {
				e.printStackTrace();
//				Alert alert = new Alert(Alert.AlertType.ERROR);
//				alert.setTitle("Error");
//				alert.setHeaderText("Error ,There is an error");
//				alert.setContentText("Please Press Show button First!");
//
//				alert.showAndWait();
			}
		}finally {
			close(stmt);
		}
	}

	public static void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


}


