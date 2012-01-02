package utils;

import com.beust.jcommander.Parameter;
import org.testng.annotations.Parameters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author masyan
 */
public class DBHelp {

	public static Connection getConnection(String dbURL, String dbDriver, String username, String password) {
		try {
			Class.forName(dbDriver);
			Connection jdbcConnection = DriverManager.getConnection(dbURL, username, password);
			return jdbcConnection;
		}
		catch (ClassNotFoundException e) {
			return null;
		}
		catch (SQLException e) {
			return null;
		}
	}


	public static void setForumUsers(Connection conn) {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM USER WHERE ID=1 or ID=3");
			st.executeUpdate("INSERT INTO USER(ID,UUID,USERNAME,ENCODED_USERNAME,EMAIL, PASSWORD, ROLE, LANGUAGE, PAGE_SIZE) " +
					"VALUES(1, '11111111-1111-1111-1111-111111111111', 'testSel', 'testSel', 'testSel','testSel@jtalks.org'," +
					" 'ROLE_USER','ENGLISH', 50)");
			st.executeUpdate("INSERT INTO USER(ID,UUID,USERNAME,ENCODED_USERNAME,EMAIL, PASSWORD, ROLE, LANGUAGE, PAGE_SIZE) " +
					"VALUES(3, '22222222-2222-2222-2222-222222222222', 'adminSel', 'adminSel', 'adminSel','adminSel@jtalks.org'," +
					" 'ROLE_ADMIN','ENGLISH', 50)");
			st.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void removeAllTestPosts(Connection conn) {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM POST WHERE POST_CONTENT like '" + StringHelp.getTestPrefix() + "%'");
		}
		catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}


	public static void removeAllTestTopics(Connection conn) {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM TOPIC WHERE TITLE like '" + StringHelp.getTestPrefix() + "%'");
		}
		catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}


}
