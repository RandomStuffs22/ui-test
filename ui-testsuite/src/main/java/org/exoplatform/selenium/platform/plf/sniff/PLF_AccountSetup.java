package org.exoplatform.selenium.platform.plf.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 21/10/2013
 *
 */
public class PLF_AccountSetup extends PlatformBase{
	//Platform
	ManageAccount magAcc;

	@BeforeMethod
	public void beforeMethods() {
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Skip Creating account ==
	 * Test case ID: 94922
	 * Step 1: Start server
	 * Step 2: Accept Term & Condition
	 * Step 3: Set up account
	 * Pending: will not run this test case in sniff because
	 * To run it, we need to restart server with empty database
	 */
	@Test (groups="pending")
	public void test01_SkipCreatingAccount(){
		/*Step 1: Start server*/ 
		//- Start a fresh package
		//Server is started without exception

		/*Step 2: Accept Term & Condition*/ 
		//- Open Browser and type in address: http://<server>:8080/portal
		//- Tick check-box to accept
		//- Click Continue
		//- Term and Condition is accepted 
		//- Move to Setup account screen
		//- Submit button is highlighted in blue, Skip Button is in light grep color

		/*Step 3: Set up account*/ 
		//Click Skip button
		//- Return to login screen
		//- Can log in with root/gtn here to create new account 
		initSeleniumTest(false);
		driver.get(baseUrl);
		info("Login with " + DATA_USER1);
		magAcc = new ManageAccount(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}
	
	/**
	 * == The new user account is an administrator ==
	 * Test case ID: 94921
	 * Step 1: Start server
	 * Step 2: Accept Term & Condition
	 * Step 3: Set up account
	 * Pending: will not run this test case in sniff because
	 * To run it, we need to restart server with empty database
	 */
	@Test (groups="pending")
	public void test02_TheNewUserAccountIsAnAdministrator(){
		/*Step 1: Start server*/ 
		//- Launch the app for the first time (/portal)
		//- Check the checkbox: "I agree with these terms and conditions"
		//- Click on the button "Continue"
		//-In the screen "Account Setup", Input valid data on each fields
		//- Click on the button "Submit" 
		//- Intranet Homepage is displayed
		//- User is looged with the account created 
		//- The user can see the full Administration menu
		//- This user in membership:    *:/platform/administrators
		//    *:/platform/web-contributors
		//    *:/platform:/users
		//    *:/developers
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + "fqa");
		magAcc = new ManageAccount(driver);
		magAcc.signIn("fqa", DATA_PASS);
	}
}