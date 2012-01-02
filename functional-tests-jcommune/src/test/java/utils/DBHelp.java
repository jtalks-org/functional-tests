package utils;

import com.beust.jcommander.Parameter;
import org.testng.annotations.Parameters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
		String sUsername = "testSel";
		String sEmail = "testSel@jtalks.org";
		String aUsername = "adminSel";
		String aEmail = "adminSel@jtalks.org";
		String aUsername2 = "testSel2";
		String aEmail2 = "testSel2@jtalks.org";

		try {
			Statement st = conn.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT USERNAME, EMAIL FROM USER WHERE USERNAME='" + sUsername + "' AND EMAIL='" + sEmail + "'");
			if (!rs.first()) {
				st.executeUpdate("INSERT INTO USER(ID,UUID,USERNAME,ENCODED_USERNAME, PASSWORD, EMAIL, ROLE, LANGUAGE, PAGE_SIZE) " +
						"VALUES(ID, '11111111-1111-1111-1111-111111111111', 'jtdjdytj', 'dtykdkty', 'testSel','ytj@jtalks.org'," +
						" 'ROLE_USER','ENGLISH', 50)");
			}
			;
			rs = st.executeQuery("SELECT USERNAME, EMAIL FROM USER WHERE USERNAME='" + aUsername + "' AND EMAIL='" + aEmail + "'");
			if (!rs.first()) {
				st.executeUpdate("INSERT INTO USER(ID,UUID,USERNAME,ENCODED_USERNAME, PASSWORD,EMAIL, ROLE, LANGUAGE, PAGE_SIZE) " +
						"VALUES(ID, '22222222-2222-2222-2222-222222222222', 'adminSel', 'adminSel', 'adminSel','adminSel@jtalks.org'," +
						" 'ROLE_ADMIN','ENGLISH', 50)");
			}
			rs = st.executeQuery("SELECT USERNAME, EMAIL FROM USER WHERE USERNAME='" + aUsername2 + "' AND EMAIL='" + aEmail2 + "'");
			if (!rs.first()) {
				st.executeUpdate("INSERT INTO USER(ID,UUID,USERNAME,ENCODED_USERNAME, PASSWORD, EMAIL, ROLE, LANGUAGE, PAGE_SIZE) " +
						"VALUES(ID, '33333333-3333-3333-3333-333333333333', 'testSel2', 'testSel2', 'testSel2','testSel2@jtalks.org'," +
						" 'ROLE_USER','ENGLISH', 50)");
			}
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
			st.close();
		}
		catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}


	public static void removeAllTestTopics(Connection conn) {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM TOPIC WHERE TITLE like '" + StringHelp.getTestPrefix() + "%'");
			st.close();
		}
		catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}


}
