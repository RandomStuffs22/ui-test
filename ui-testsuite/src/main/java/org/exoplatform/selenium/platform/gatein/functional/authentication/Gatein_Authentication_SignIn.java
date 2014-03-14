package org.exoplatform.selenium.platform.gatein.functional.authentication;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author havtt
 * @date 08-Nov-2013
 */
public class Gatein_Authentication_SignIn extends PlatformBase {

	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement user;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		user = new UserGroupManagement(driver);

		magAc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Check displaying portal after signing in successfully by admin account
	 * CaseId: 73517
	 */
	@Test
	public void test01_checkSignInByAdminAcc(){ 
		By ELEMENT_HOMEPAGE_SELECTED = By.xpath("//a[@href='/portal/intranet/home']/parent::li[@class='navItemSelected']");
		String ELEMENT_FULLNAME_USER = "John Smith";

		driver.navigate().refresh();
		info("Refresh homepage after login by admin acc");
		click(ELEMENT_LINK_SETUP);

		info("Check display of Admin bar");
		waitForAndGetElement(ELEMENT_LINK_USERS);
		info("--Check Users Management menu OK--");
		waitForAndGetElement(ELEMENT_APPLICATIONS_LINK);
		info("--Check Applications menu OK--");
		waitForAndGetElement(ELEMENT_MENU_CONTENT_LINK);
		info("--Check Content menu OK--");
		waitForAndGetElement(ELEMENT_LINK_PORTAL);
		info("--Check Portal menu OK--");
		waitForAndGetElement(ELEMENT_MENU_ADMININISTRATION);
		info("--Check Administration menu OK--");

		info("Check selected Homepage in nav toolbar");
		waitForAndGetElement(ELEMENT_HOMEPAGE_SELECTED);

		info("Check display of user acc");
		String content =  waitForAndGetElement(ELEMENT_MENU_MYMENU).getText().trim();
		info(content);
		if (content == ELEMENT_FULLNAME_USER) {
			info("Check display of user acc OK");
		} else { info("Full name of user acc is not displayed correctly"); }

		driver.navigate().refresh();
		info("Check display of all portlets");
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		info("--Check CALENDAR gadget OK--");
		waitForAndGetElement(ELEMENT_GETTING_STARTED_GADGET);
		info("--Check Getting Started gadget OK--");
		Utils.pause(3000);
		waitForAndGetElement(ELEMENT_GETTING_SUGGESTIONS);
		info("--Check SUGGESTIONS gadget OK--");

		driver.navigate().refresh();
	}

	/**
	 * Check displaying portal after signing in successfully by normal account
	 * CaseId: 73586
	 */
	@Test
	public void test02_checkSignInByNormalAcc(){
		info("Logout admin acc");
		magAc.signOut();

		info("Login by normal user acc");
		magAc.signIn(DATA_USER4, DATA_PASS);

		driver.navigate().refresh();
		info("Refresh homepage after login");


		info("Check if Admin bar is displayed or not");
		waitForElementNotPresent(ELEMENT_LINK_SETUP);

	}

	/**
	 * Sign in with blank username and password
	 * CaseId: 73642
	 */
	@Test
	public void test03_checkSignInWithBlankUserandPass(){ 
		info("Logout admin acc");
		magAc.signOut();

		info("Login with blank username and password");
		magAc.signIn("", "",false);

		info("Check error msg");
		waitForAndGetElement(ELEMENT_SIGNIN_FAIL_MSG);

		info("Login again");
		magAc.signIn(DATA_USER1, DATA_PASS,true,false);

	}

	/**
	 * Sign in with unregistered username and password
	 * CaseId: 73692
	 */
	@Test
	public void test04_checkSignInWithUnregisteredUserandPass(){ 
		info("Logout admin acc");
		magAc.signOut();

		info("Login with unregistered username and password");
		magAc.signIn("helloWorld", "123456",false);

		info("Check error msg");
		waitForAndGetElement(ELEMENT_SIGNIN_FAIL_MSG);

		info("Login again");
		magAc.signIn(DATA_USER1, DATA_PASS,true,false);
	}
}
