package org.exoplatform.selenium.platform.plf.functional.navigation.topnavigation;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.*;

	/**
	* @author chinhdtt
	* @date 24 Mar 2014
	*/
	public class PLF_Navigation_TopNavigation_Profile extends PlatformBase{
		ManageAccount acc;
		NavigationToolbar nav;

		@BeforeMethod
		public void beforeMethods(){	
			initSeleniumTest();
			driver.get(baseUrl);
			acc = new ManageAccount(driver, this.plfVersion);
			nav = new NavigationToolbar(driver, this.plfVersion);				
			acc.signIn(DATA_USER1, DATA_PASS);		
		}

		@AfterMethod
		public void afterMethods() {
			info("Logout portal");
			driver.manage().deleteAllCookies();
			driver.quit();
		}

	/**
	* Case ID:76657.
	* Test Case Name: Show profile menu items.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : 
	* Generated by chinhdtt at 2014/03/24 10:43:33
	*/
	@Test
	public  void test01_ShowProfileMenuItems() {
		info("Test 1: Show profile menu items");
		/*
		- Connect to Intranet
		*Input Data: 
		*Expected Outcome: 
		- The top navigation bar is displayed
		- The button profile is displayed		*/
		info("Check navigation bar");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*
		- Mouse over on the button "Profile"
		*Input Data: 
		*Expected Outcome: 
		- The list of items is displayed in the following order:* My Profile* My Activity Stream* My Connections*My Wiki * My Dashboard* Settings* Change Language* Logout
		- A light separator between My Dashboard and Settings, and between Settings and Logout is displayed		*/ 
		mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
		info("List Items");
		waitForAndGetElement(ELEMENT_MY_PROFILE_LINK);
		waitForAndGetElement(ELEMENT_MY_ACTIVITY_STREAM);
		waitForAndGetElement(ELEMENT_MY_CONNECTIONS);
		waitForAndGetElement(ELEMENT_MY_WIKI);
		waitForAndGetElement(ELEMENT_DASHBROARD_LINK);
		waitForAndGetElement(ELEMENT_MY_SETTING);
		waitForAndGetElement(ELEMENT_CHANGE_LANGUAGE_LINK);
		waitForAndGetElement(ELEMENT_SIGN_OUT_LINK);
		info("Lines between menu");
		waitForAndGetElement(By.xpath(ELEMENT_LINE_BETWEEN_MENU.replace("${index}", "1")));
		waitForAndGetElement(By.xpath(ELEMENT_LINE_BETWEEN_MENU.replace("${index}", "2")));
 	}

	/**
	* Case ID:76658.
	* Test Case Name: Show the profile button.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : 
	* Generated by chinhdtt at 2014/03/24 10:43:33
	*/
	@Test
	public  void test02_ShowTheProfileButton() {
		info("Test 2: Show the profile button");
		/*
		- Connect to Intranet
		*Input Data: 
		*Expected Outcome: 
		- The top navigation bar is displayed
		- The profile button is displayed:* user's avatar* First name and Last name		*/ 
		info("Check navigation bar");
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);
		waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK);
		waitForAndGetElement(ELEMENT_NAVIGATION_ACCOUNT_AVATAR);
		assert waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK).getText().equalsIgnoreCase("John Smith");
 	}
}