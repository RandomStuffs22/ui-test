package org.exoplatform.selenium.platform.plf.functional.welcomescreens;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author: HaKT
 * @date: 10/19/2013
 */
public class PLF_WelcomeScreens_Login extends PlatformBase {
	//Platform
	ManageAccount magAcc;
	
	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		magAcc = new ManageAccount(driver);
	}

	@AfterMethod
	public void afterMethods() {
		info("Quit");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Connect with the USER_ROOT user ==
	 * Test case ID: 79261, 79299
	 * - Launch the app for the first time (/portal)
	 * - Check the checkbox: "I agree with these terms and conditions"
	 * - Click on the button "Continue"
	 * - In the screen "Account Setup", Input valid data on each fields
	 * - Click on the button "Submit" 
	 * - Logout/Login
	 * - Input Username USER_ROOT
	 * - Use the password submitted in the "Account Setup" screen
	 */
	@Test
	public void test01_02_ConnectWithRootUser(){
		//Method: Term and condition
		//- Launch the app for the first time 
		//- Check the checkbox: "I agree with these terms and conditions"
		//- In the screen "Account Setup", Input valid data on each fields
		//- Click on the button "Submit" 	

		info("Login as root");
		magAcc.signIn(USER_ROOT, PASS_ROOT);		

		info("Verify standing at homepage");
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		waitForAndGetElement(ELEMENT_GETTING_STARTED_GADGET);
		waitForAndGetElement(ELEMENT_GETTING_SUGGESTIONS);
	}

	/**
	 * == Display the headline label of the "Connexion Screen" ==
	 * Test case ID: 79300
	 **/
	@Test
	public void test03_DisplayTheHeadlineLabelOfTheConnexionScreen() {

		waitForAndGetElement(magAcc.ELEMENT_LOGIN_CONTAINER);
		waitForAndGetElement(By.xpath(magAcc.ELEMENT_LOGIN_HEADER_LABEL));
	}

	/**
	 * == The field "Username" is mandatory in the "Connexion Screen" ==
	 * Test case ID: 79301
	 **/
	@Test
	public void test04_UsernameIsMandatoryInTheConnexionScreen() {

		magAcc.signIn("", DATA_PASS, false);
		waitForAndGetElement(By.xpath(magAcc.MESSAGE_LOGIN_FAILED));
	}

	/**
	 * == The field "Password" is mandatory in the "Connexion Screen" ==
	 * Test case ID: 79302
	 **/
	@Test
	public void test05_PasswordIsMandatoryInTheConnexionScreen() {

		magAcc.signIn(USER_ROOT, "", false);
		waitForAndGetElement(By.xpath(magAcc.MESSAGE_LOGIN_FAILED));
	}

	/**
	 * == Autologin after selecting the box "Remember My login" ==
	 * Test case ID: 79303
	 * The issue related: FQA-1506
	 **/
	@Test(groups="pending")
	public void test06_AutologinAfterSelectingTheBoxRememberMyLogin() {

		waitForAndGetElement(By.xpath(magAcc.ELEMENT_REMEMBER_MY_LOGIN_YES));

		magAcc.signIn(USER_ROOT, PASS_ROOT);

//		restartBrowser();

		info("Verify standing at homepage");
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		waitForAndGetElement(ELEMENT_GETTING_STARTED_GADGET);
		waitForAndGetElement(ELEMENT_GETTING_SUGGESTIONS);
	}

	/**
	 * == Autologin without select the box "Remember My login" ==
	 * Test case ID: 79307
	 * The issue related: FQA-1506
	 * 
	 **/
	@Test(groups="pending")
	public void test07_AutologinWithoutSelectingTheBoxRememberMyLogin() {

		info("Click Remember my login to change to No");
		click(By.xpath(magAcc.ELEMENT_REMEMBER_MY_LOGIN_YES));

		magAcc.signIn(USER_ROOT, PASS_ROOT, false, false);

//		restartBrowser();

		info("Verify standing at log in screen");
		waitForAndGetElement(magAcc.ELEMENT_LOGIN_CONTAINER);
	}

	/**
	 * == Display the footer label of the "Connexion Screen" ==
	 * Test case ID: 79304
	 * The issue related: FQA-1507
	 **/
	@Test(groups="pending")
	public void test08_DisplayFooterLabelOfTheConnexionScreen() {
		//		pending this case due to not know how to get build reversion from unzip package name
	}

	/**
	 * == Connect with the wrong username, password ==
	 * Test case ID: 79310
	 **/
	@Test
	public void test09_ConnectWithWrongUsernamePassword() {

		magAcc.signIn("test", "testtest", false);
		waitForAndGetElement(By.xpath(magAcc.MESSAGE_LOGIN_FAILED));
	}

	/**
	 * == Show Connexion Screen whenever unauthenticated users attemp to access platform ==
	 * Test case ID: 79311
	 **/
	@Test
	public void test10_ShowConnexionScreenWheneverUnauthenticatedUsersAttempToAccessPlatform() {
		String SPECIFIC_URL = "http://localhost:8080/portal/intranet/documents";

		driver.get(SPECIFIC_URL);
		waitForAndGetElement(magAcc.ELEMENT_LOGIN_CONTAINER);
	}
}