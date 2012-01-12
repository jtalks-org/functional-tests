package utils;

import com.beust.jcommander.Parameter;
import org.testng.annotations.Parameters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @author masyan
 */
public class DBHelp {

	/**
	 * Method creates connection to database and return it.
	 *
	 * @param dbURL	URL database
	 * @param dbDriver Database driver (class name)
	 * @param username Database username (with remote connect permission)
	 * @param password Password for username
	 * @return Connection to database
	 */
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

	/**
	 * Method addes users from config file (testng.xml) to database (if are not exists)
	 *
	 * @param conn  Database connection
	 * @param users Hashmap with usernames.
	 */
	public static void setForumUsers(Connection conn, HashMap users) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT USERNAME, EMAIL FROM USER WHERE USERNAME='" + users.get("uUsername") + "' ");
			// IMPORTANT. Language should be English
			if (!rs.first()) {
				st.executeUpdate("INSERT INTO USER(ID,UUID,USERNAME,ENCODED_USERNAME, PASSWORD, EMAIL, ROLE, LANGUAGE, PAGE_SIZE) " +
						"VALUES(ID, '11111111-1111-1111-1111-111111111111', 'testSel', 'testSel', 'testSel','testSel@jtalks.org'," +
						" 'ROLE_USER','ENGLISH', 50)");
			}
			;
			rs = st.executeQuery("SELECT USERNAME, EMAIL FROM USER WHERE USERNAME='" + users.get("aUsername") + "' ");
			if (!rs.first()) {
				st.executeUpdate("INSERT INTO USER(ID,UUID,USERNAME,ENCODED_USERNAME, PASSWORD,EMAIL, ROLE, LANGUAGE, PAGE_SIZE) " +
						"VALUES(ID, '22222222-2222-2222-2222-222222222222', 'adminSel', 'adminSel', 'adminSel','adminSel@jtalks.org'," +
						" 'ROLE_ADMIN','ENGLISH', 50)");
			}
			rs = st.executeQuery("SELECT USERNAME, EMAIL FROM USER WHERE USERNAME='" + users.get("uUsername2") + "' ");
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

	/**
	 * Util method. Get usernames in parametrs and returns HashMap contain all users from config file (testng.xml).
	 *
	 * @param uUsername
	 * @param uUsername2
	 * @param aUsername
	 * @return HashMap contain usernames
	 */
	public static HashMap getUsersFromConfigFile(String uUsername, String uUsername2, String aUsername) {
		HashMap<String, String> users = new HashMap();
		users.put("uUsername", uUsername);
		users.put("uUsername2", uUsername2);
		users.put("aUsername", aUsername);

		return users;
	}

//	public static void removeAllTestPosts(Connection conn) {
//		try {
//			Statement st = conn.createStatement();
//			st.executeUpdate("DELETE FROM POST WHERE POST_CONTENT like '" + StringHelp.getTestPrefix() + "%'");
//			st.close();
//		}
//		catch (SQLException e) {
//			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//		}
//	}
//
//
//	public static void removeAllTestTopics(Connection conn) {
//		try {
//			Statement st = conn.createStatement();
//			st.executeUpdate("DELETE FROM TOPIC WHERE TITLE like '" + StringHelp.getTestPrefix() + "%'");
//			st.close();
//		}
//		catch (SQLException e) {
//			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//		}
//	}


}
