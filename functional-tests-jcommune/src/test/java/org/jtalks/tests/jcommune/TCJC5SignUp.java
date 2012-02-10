package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;


/**
 * This functional test covers SignUp test case TC-JC5
 * http://jtalks.org/display/jcommune/TC-JC5+Sign+Up
 *
 * @author masyan
 */
public class TCJC5SignUp extends JCommuneSeleniumTest {
	String appUrl;
	String existUsername;
	String existEmail;


	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uEmail"})
	private void setupCase(String appUrl, String username, String email) {
		this.appUrl = appUrl;
		this.existUsername = username;
		this.existEmail = email;
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}

	/**
	 * Scenario #1 Check valid registration
	 */
	@Test
	private void uniqueUsernameAndEmailValidationForRegisteringForm() {
		//check exist username
		setUsername(this.existUsername);
		setEmail(this.existEmail);
		String validPassword = StringHelp.getRandomString(6);
		setPassword(validPassword);
		setConfirmPassword(validPassword);
		submitRegistrationForm();
		assertExistById("username.errors");
		assertExistById("email.errors");
	}

	/**
	 * Scenario #2 sign up with empty registering form
	 */
	@Test
	private void signUpWithEmptyRegisteringFormTest() {
		submitRegistrationForm();
		assertExistById("username.errors");
	}

	/**
	 * Scenario #3 Check username validation for registering form
	 */
	@Test
	private void usernameValidationForRegisteringFormTest() {
		//check short username
		setUsername(StringHelp.getRandomString(2));
		submitRegistrationForm();
		assertExistById("username.errors");

		//check long username
		setUsername(StringHelp.getRandomString(21));
		submitRegistrationForm();
		assertExistById("username.errors");

		//check valid username
		setUsername(StringHelp.getRandomString(10));
		submitRegistrationForm();

		assertNotExistById("username.errors");
	}


	/**
	 * Scenario #4  Check email validation for regitering form
	 */
	@Test
	private void emailValidationForRegisteringFormTest() {
		//check not valid email
		setUsername(StringHelp.getRandomString(10));
		setEmail(StringHelp.getRandomString(6));
		submitRegistrationForm();
		assertExistById("email.errors");

		//check valid email
		setEmail(StringHelp.getRandomEmail());
		submitRegistrationForm();

		assertNotExistById("email.errors");
	}


	/**
	 * Scenari #5 Check password validation for registering form
	 */
	@Test
	private void passwordValidationForRegisteringFormTest() {
		//check short password password
		setUsername(StringHelp.getRandomString(10));
		setEmail(StringHelp.getRandomEmail());
		setPassword(StringHelp.getRandomString(2));
		submitRegistrationForm();
		assertExistById("password.errors");

		//check long password
		setPassword(StringHelp.getRandomString(21));
		submitRegistrationForm();
		assertExistById("password.errors");

		//check valid password
		setPassword(StringHelp.getRandomString(6));
		submitRegistrationForm();

		assertNotExistById("password.errors");
	}


	/**
	 * Scenario #6 Check confirm password validation for registering form
	 */
	@Test
	private void confirmPasswordValidationForRegiteringFormTest() {
		//check not equal (another case) confirm password
		setUsername(StringHelp.getRandomString(10));
		setEmail(StringHelp.getRandomEmail());
		String validPassword = StringHelp.getRandomString(6);
		setPassword(validPassword);
		setConfirmPassword(validPassword.toUpperCase());
		submitRegistrationForm();
		assertExistById("passwordConfirm.errors");

		//check not equal (another password) confirm password
		setConfirmPassword(validPassword + "notEquals");
		submitRegistrationForm();
		assertExistById("passwordConfirm.errors");

		//check equal confirm password
		setConfirmPassword(validPassword);
		submitRegistrationForm();
		assertEquals(driver.getCurrentUrl(), appUrl);
	}

	//Utils methods
	private void submitRegistrationForm() {
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void setUsername(String newValue) {
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(newValue);
	}

	private void setEmail(String newValue) {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(newValue);
	}

	private void setPassword(String newValue) {
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(newValue);
	}

	private void setConfirmPassword(String newValue) {
		driver.findElement(By.id("passwordConfirm")).clear();
		driver.findElement(By.id("passwordConfirm")).sendKeys(newValue);
	}
}