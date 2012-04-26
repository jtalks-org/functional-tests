package utils.mail;

/**
 * Iterface to mail server
 *
 * @autor masyan
 */
public interface Mail {

	/**
	 * Method for authorize in mail server
	 *
	 * @param username username of mail server
	 * @param password password of mail server
	 */
	public void signIn(String username, String password);

	public String getMailServerUrl();

	public void goToMailServer();
}
