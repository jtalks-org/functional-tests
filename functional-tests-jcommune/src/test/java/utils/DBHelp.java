package utils;

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
	 * @param dbURL    URL database
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
			rs = st.executeQuery("SELECT USERNAME, EMAIL FROM USERS WHERE USERNAME='testSel' ");
			// IMPORTANT. Language should be English and Post count on Page should 5
			if (!rs.first()) {
				st.executeUpdate("INSERT INTO USERS(ID,UUID,USERNAME,ENCODED_USERNAME, PASSWORD, EMAIL, ROLE) " +
						"VALUES(ID, '11111111-1111-1111-1111-111111111111', 'testSel', 'testSel', 'testSel','" + users.get("testSel") + "'," +
						" 'ROLE_USER')");
				rs = st.executeQuery("SELECT ID FROM USERS WHERE USERNAME='testSel'");
				rs.next();
				st.executeUpdate("INSERT INTO JC_USER_DETAILS(USER_ID, LANGUAGE, PAGE_SIZE, REGISTRATION_DATE, ENABLED, POST_COUNT) " +
						"VALUES(" + rs.getLong("ID") + ",'ENGLISH',5, '2012-02-25 13:21:10',  1, 0)");
			}
			rs = st.executeQuery("SELECT USERNAME, EMAIL FROM USERS WHERE USERNAME='adminSel' ");
			if (!rs.first()) {
				st.executeUpdate("INSERT INTO USERS(ID,UUID,USERNAME,ENCODED_USERNAME, PASSWORD,EMAIL, ROLE) " +
						"VALUES(ID, '22222222-2222-2222-2222-222222222222', 'adminSel', 'adminSel', 'adminSel','" + users.get("adminSel") + "'," +
						" 'ROLE_ADMIN')");
				rs = st.executeQuery("SELECT ID FROM USERS WHERE USERNAME='adminSel'");
				rs.next();
				st.executeUpdate("INSERT INTO JC_USER_DETAILS(USER_ID, LANGUAGE, PAGE_SIZE, REGISTRATION_DATE, ENABLED, POST_COUNT) " +
						"VALUES(" + rs.getLong("ID") + ",'ENGLISH',5, '2012-02-25 13:21:10', 1, 0)");
			}
			rs = st.executeQuery("SELECT USERNAME, EMAIL FROM USERS WHERE USERNAME='testSel2'");
			if (!rs.first()) {
				st.executeUpdate("INSERT INTO USERS(ID,UUID,USERNAME,ENCODED_USERNAME, PASSWORD, EMAIL, ROLE) " +
						"VALUES(ID, '33333333-3333-3333-3333-333333333333', 'testSel2', 'testSel2', 'testSel2','" + users.get("testSel2") + "'," +
						" 'ROLE_USER')");
				rs = st.executeQuery("SELECT ID FROM USERS WHERE USERNAME='testSel2'");
				rs.next();
				st.executeUpdate("INSERT INTO JC_USER_DETAILS(USER_ID, LANGUAGE, PAGE_SIZE, REGISTRATION_DATE, ENABLED, POST_COUNT) " +
						"VALUES(" + rs.getLong("ID") + ",'ENGLISH',5, '2012-02-25 13:21:10', 1, 0)");
			}
			rs = st.executeQuery("SELECT USERNAME, EMAIL FROM USERS WHERE USERNAME='testSel3'");
			if (!rs.first()) {
				st.executeUpdate("INSERT INTO USERS(ID,UUID,USERNAME,ENCODED_USERNAME, PASSWORD, EMAIL, ROLE) " +
						"VALUES(ID, '44444444-4444-4444-4444-444444444444', 'testSel3', 'testSel3', 'testSel3','" + users.get("testSel3") + "'," +
						" 'ROLE_USER')");
				rs = st.executeQuery("SELECT ID FROM USERS WHERE USERNAME='testSel3'");
				rs.next();
				st.executeUpdate("INSERT INTO JC_USER_DETAILS(USER_ID, LANGUAGE, PAGE_SIZE, REGISTRATION_DATE, ENABLED, POST_COUNT) " +
						"VALUES(" + rs.getLong("ID") + ",'ENGLISH',5, '2012-04-21 13:21:10', 1, 0)");
			}
			rs = st.executeQuery("SELECT USERNAME, EMAIL FROM USERS WHERE USERNAME='random'");
			if (!rs.first()) {
				st.executeUpdate("INSERT INTO USERS(ID,UUID,USERNAME,ENCODED_USERNAME, PASSWORD, EMAIL, ROLE) " +
						"VALUES(ID, '44444444-4444-5555-4444-444444444444', 'random', 'random', 'random','" + users.get("random") + "'," +
						" 'ROLE_USER')");
				rs = st.executeQuery("SELECT ID FROM USERS WHERE USERNAME='random'");
				rs.next();
				st.executeUpdate("INSERT INTO JC_USER_DETAILS(USER_ID, LANGUAGE, PAGE_SIZE, REGISTRATION_DATE, ENABLED, POST_COUNT) " +
						"VALUES(" + rs.getLong("ID") + ",'ENGLISH',5, '2012-04-21 13:21:10', 1, 0)");
			}
			st.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Util method. Get usernames in parametrs and returns HashMap contain all users with emails from config file (testng.xml).
	 *
	 * @return HashMap contain usernames
	 */
	public static HashMap getUsersFromConfigFile(String username, String email, String username2, String email2, String admin, String adminEmail,
												 String username4, String email4) {
		HashMap<String, String> users = new HashMap<String, String>();
		users.put(username, email);
		users.put(username2, email2);
		users.put(admin, adminEmail);
		users.put(username4, email4);
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
