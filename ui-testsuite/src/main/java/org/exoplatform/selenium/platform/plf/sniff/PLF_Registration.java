package org.exoplatform.selenium.platform.plf.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.RegistrationPage;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 23/10/2013
 *
 */
public class PLF_Registration extends PlatformBase {
	//Platform
	ManageAccount magAcc;
	RegistrationPage regPage;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		regPage = new RegistrationPage(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Unlock trial version with invalid/valid key ==
	 * Test case ID: 74931
	 * Step 1: Verify submit invalid product key 
	 * Step 2: Verify submit valid product key
	 * PENDING: Need to use trial-evaluation-key-generator tool to generate valid key before running this test case
	 * --> can't do automatic
	 */
	@Test(groups="pending")
	public void test01_UnlockTrialVersionWithInvalidValidKey(){
		/*Declare variables*/
		String invalidKey = "123456";
		String validKey = "";
		/*Step 1: Verify submit invalid product key */
		//- Click buy a susbcription
		// On unlock Evaluation screen :
		//- Enter invalid unlock key
		//- Click Unlock
		regPage.unlockTrialVersion(invalidKey);
		
		//- A message is displayed: Sorry this evaluation key is not valid
		waitForAndGetElement(regPage.ELEMENT_ERROR_UNLOCK_INVALID_KEY);
		
		/*Step 2: Verify submit valid product key */ 
		//On unlock Evaluation screen :
		//- Enter valid unlock key
		regPage.unlockTrialVersion(validKey);
		
		//- Redirects to the original url (the one where the user was when he clicked on Remove banner button, in this case Intranet Home page)
		waitForElementNotPresent(regPage.ELEMENT_BUY_A_SUBCRIPTION);
	}

	/**
	 * == Verify portal page with 0 days left. ==
	 * Test case ID: 74930
	 * Step 1: Verify portal page with 0 day left. 
	 * PENDING: need to change system date before running this case
	 * Refer: https://jira.exoplatform.org/browse/FQA-1358
	 */
	@Test(groups="pending")
	public void test02_VerifyPortalPageWith0DaysLeft(){
		/*Step 1: Verify portal page with 0 day left.*/
		//- Now Clear your browsing data (cookie, cache)
		//- Setup your system date with +30 days from current day
		//- Restart the server 
		//- Go to localhost:8080/portal/ 

		//- Trial portal page is displayed
		//-The counter indicates that you have 0 day left
	}

	/**
	 * == Verify portal page with 15 days left. ==
	 * Test case ID: 74929
	 * Step 1: Verify portal page with 15 day left. 
	 * PENDING: need to change system date before running this case
	 * Refer: https://jira.exoplatform.org/browse/FQA-1358
	 */
	@Test(groups="pending")
	public void test03_VerifyPortalPageWith15DaysLeft(){
		/*Step 1: Verify portal page with 15 day left.*/
		//- Now Clear your browsing data (cookie, cache)
		//- Setup your system date with +15 days from current day
		//- Restart the server 
		//- Go to localhost:8080/portal/ 

		//- Trial portal page is displayed
		//-The counter indicates that you have 15 day left
	}

	/**
	 * == Verify portal page with 29 days left. ==
	 * Test case ID: 74928
	 * Step 1: Verify portal page with 29 day left. 
	 * PENDING: need to change system date before running this case
	 * Refer: https://jira.exoplatform.org/browse/FQA-1358
	 */
	@Test(groups="pending")
	public void test04_VerifyPortalPageWith29DaysLeft(){
		/*Step 1: Verify portal page with 15 day left.*/
		//- Now Clear your browsing data (cookie, cache)
		//- Setup your system date with +1 days from current day
		//- Restart the server 
		//- Go to localhost:8080/portal/ 

		//- Trial portal page is displayed
		//-The counter indicates that you have 29 day left
	}

	/**
	 * == Verify portal screen at the first to start trial package ==
	 * Test case ID: 74925
	 * Step 1: Verify screen At the first start page layout
	 * Step 2: Click subscription plan link
	 * Step 3: Click Request a key link
	 * Step 4: Click  info@exoplatform.com  link
	 * Step 5: Click  www.exoplatform.com link
	 * PENDING: Need to setup email account to run step 4
	 * Refer: https://jira.exoplatform.org/browse/FQA-1359
	 */
	@Test(groups="pending")
	public void test05_VerifyPortalScreenAtTheFirstToStartTrialPackage(){
		/* Step 1: Verify screen At the first start page layout*/
		//- Launch the server with trial package for the first time
		//- Go to http://localhost:8080/portal/
		//- Input valid value to create user
		//- Check evaluation days at the bottom
		//- Display "You have 30 day(s) left in your evaluation".
		waitForAndGetElement(regPage.ELEMENT_REMAIN_TRIAL_DAY.replace("${remainDay}", "30"));
		
		//- Click [By a subscription] at the bottom
		click(regPage.ELEMENT_BUY_A_SUBCRIPTION);

		//-The buy subscription page is shown with 5 main links: 
		//- subscription plan
		waitForAndGetElement(regPage.ELEMENT_SUBCRIPTION_PLAN);
		
		//- Request a key
		waitForAndGetElement(regPage.ELEMENT_REQUEST_KEY_BUTTON);
		
		//- Unlock
		waitForAndGetElement(regPage.ELEMENT_UNLOCK_BUTTON);
		
		//- info@exoplatform.com 
		waitForAndGetElement(regPage.ELEMENT_CONTACT_US);
		
		//- www.exoplatform.com
		waitForAndGetElement(regPage.ELEMENT_WEBSITE);

		/* Step 2: Click subscription plan link*/
		//- Click subscription plan 
		click(regPage.ELEMENT_SUBCRIPTION_PLAN);

		//-The first start page is shown
		//-The page "edition" is displayed
		switchToNewWindow();
		waitForAndGetElement(regPage.ELEMENT_EDITIONS_PAGE);
		
		/* Step 3: Click Request a key link*/
		switchToParentWindow();
		
		//- Click Request a key link
		click(regPage.ELEMENT_REQUEST_KEY_BUTTON);

		//-The first start page is shown
		//-The mailing page is shown
		switchToNewWindow();
		waitForAndGetElement(regPage.ELEMENT_REQUEST_A_KEY_PAGE);

		/* Step 4: Click  info@exoplatform.com  link*/
		switchToParentWindow();
		//- Click  info@exoplatform.com link
		click(regPage.ELEMENT_CONTACT_US);
		
		//- The mail to page is displayed
		
		/* Step 5: Click  www.exoplatform.com link*/
		//- Click  www.exoplatform.com link
		switchToParentWindow();
		click(regPage.ELEMENT_WEBSITE);
		
		//-The exo official site is shown
		switchToNewWindow();
		waitForAndGetElement(By.xpath("//*[contains(text(),'What is eXo Platform?')]"));
	}
}
