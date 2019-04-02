package com.hinet;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import static java.time.MonthDay.now;

public class DB {
	public static boolean isWorkin;
	private Connection connection;
	private String content;
	private int user_id;

	public DB(String content,int user_id) throws SQLException, ClassNotFoundException {

		this.content=content;
		this.user_id=user_id;

		Class.forName("org.sqlite.JDBC");
		String Path = System.getProperty("user.dir");
		String url = "jdbc:sqlite:" + Path + "/issues_db.db";
		// create a connection to the database .
		connection = DriverManager.getConnection(url);
		// Send  Date To DB
		try {
			insert(content,user_id);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println(e);
		}
	}

	public DB() {
	}

	private int insert(String content, int user_id) throws Exception {

		System.out.println("============= To DB ==========");
		int numRowsInserted = 0;
		PreparedStatement ps = null;
		try {
			String sql = "INSERT INTO issue_data(data_content, user_id,created_at) VALUES('" + content + "','" + user_id + "','" + new Date() + "')";
			System.out.println(sql);
			ps = connection.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new Exception("Can't insert Data to DB",e);
		} finally {
			close(ps);
		}
		return numRowsInserted;
	}

//	public ArrayList<String> getData() {
//		System.out.println("=================== DB To API ================");
//		Statement stmt = null;
//		ResultSet rs = null;
//		String sql = "SELECT * from datatb ";
//		try {
//			String Path = System.getProperty("user.dir");
//			String url = "jdbc:sqlite:" + Path + "/whats.db";
//			// create a connection to the database .
//			connection = DriverManager.getConnection(url);
//			stmt = connection.createStatement();
//			rs = stmt.executeQuery(sql);
//
//			// loop through the result set
//			while (rs.next()) {
//				int DBFlag = rs.getInt(6);
//				if (DBFlag == 1) {
////                        data_to_send.add(rs.getString("phone"));
////                        data_to_send.add(rs.getString("chat"));
////                        data_to_send.add(rs.getString("chat_time"));
////                        data_to_send.add(rs.getString("chat_type"));
//					try {
//						String urlParameters = "mobile=" + rs.getString("phone") + "&chat=" + rs.getString("chat") + "&chat_time=" + rs.getString("chat_time") + "&chat_type=" + rs.getString("chat_type") + "";
//						byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
//						int postDataLength = postData.length;
//						String request = loadURL() + "/APP/postit";
//						URL url1 = new URL(request);
//						HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
//						System.out.println(request + urlParameters);
//						conn.setDoOutput(true);
//						conn.setInstanceFollowRedirects(false);
//						conn.setRequestMethod("POST");
//						conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//						conn.setRequestProperty("charset", "utf-8");
//						conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
//						conn.setUseCaches(false);
//						try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
//							wr.write(postData);
//						}
//						System.out.println("Server Status: " + String.valueOf(conn.getResponseCode()));
//					} catch (Exception w) {
//						w.getMessage();
//					}
//				}
//			}
//			/**
//			 *          change message flag to 0
//			 *          to not send them again
//			 */
//			String updateflagquery = "UPDATE datatb SET flag=0  ";
//			stmt.executeUpdate(updateflagquery);
//		} catch (SQLException e) {
//			e.getMessage();
//		} finally {
//			close(stmt);
//		}
//		return data_to_send;
//	}
	public static void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	private String loadURL() {
//		InputStream input = getClass().getClassLoader().getResourceAsStream("APILinks.properties");
//		Properties properties = new Properties();
//		if (input != null) {
//			try {
//				properties.load(input);
//				PostURL = properties.getProperty("apit_postit");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return PostURL;
//	}

}


