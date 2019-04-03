package com.hinet;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.time.MonthDay.now;

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
		display();
		System.out.println("============= To DB ==========");
		int numRowsInserted = 0;
		content = content_tf.getText();
		LocalDate temp = date_tf.getValue();
		user_id = Integer.parseInt(user_tf.getText());
		PreparedStatement ps = null;
		try {
			String sql = "INSERT INTO issue_data(data_content, user_id,created_at) VALUES('" + content + "','" + user_id + "','" + temp + "')";
			System.out.println(sql);
			ps = connection.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new Exception("Can't insert Data to DB", e);
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
		} catch (SQLException w) {
			throw new Exception("Cant Delete row");
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

		String temp_content = content_tf.getText();
		String temp_user = user_tf.getText();
		String temp_date = date_tf.getValue().toString();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String Path = System.getProperty("user.dir");
			String url = "jdbc:sqlite:" + Path + "/issues_db.db";

			// create a connection to the database .
			connection = DriverManager.getConnection(url);
			dbData=data_tbl.getSelectionModel().getSelectedItem();
			int id =dbData.getId();
			String tempsql = "UPDATE issue_data SET data_content = '" + temp_content + "',user_id = '" + temp_user + "',created_at = '" + temp_date + "' where id='"+id+"'";

			stmt = connection.createStatement();
			stmt.executeUpdate(tempsql);
			System.out.println(tempsql);

		} catch (SQLException e) {
			e.getMessage();
		} finally {
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


