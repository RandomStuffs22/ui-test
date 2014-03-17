package org.exoplatform.selenium.platform.plf.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.social.PeopleProfile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 31/10/2013
 *
 */
public class PLF_Navigation_UserNavigation extends PlatformBase {
	//Platform
	ManageAccount magAcc;
	NavigationToolbar naviToolbar;
	PeopleProfile peoPro;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver, this.plfVersion);
		naviToolbar = new NavigationToolbar(driver, this.plfVersion);
		peoPro = new PeopleProfile(driver, this.plfVersion);

		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == User navigate another user's personal pages ==
	 * Test case ID: 74382
	 * Step 1: Show a list of applications of another user
	 */
	@Test
	public void test01_UserNavigateAnotherUserPersonalPages(){
		/*Declare variables*/
		String user = "Mary Williams";
		
		/*Step 1: Show a list of applications of another user*/ 
		//- Connect to Intranet
		//- Open  personal page of another user, by click on the link of his profile's name  from activity stream, or connection
		naviToolbar.goToConnectionPage();
		peoPro.goToUserProfile(user);

		//- The personal page of user is displayed
		//- The Horizontal toolbar is displayed
		//- The list of applications of space are displayed in the following order:
		//* Profile
		waitForAndGetElement(ELEMENT_MY_PROFILE_TAB);
		//* Activity Stream
		waitForAndGetElement(ELEMENT_MY_ACTIVITY_STREAM_TAB);
		//* Connections
		waitForAndGetElement(ELEMENT_MY_CONNECTIONS_TAB);
		//* Wiki
		waitForAndGetElement(ELEMENT_MY_WIKI_TAB);

		//- Click on each applications, the application will show up in the main page
		//* Profile
		click(ELEMENT_MY_PROFILE_TAB);
		waitForAndGetElement(ELEMENT_PROFILE_BASIC_INFO_FORM);
		//* Activity Stream
		click(ELEMENT_MY_ACTIVITY_STREAM_TAB);
		waitForAndGetElement(ELEMENT_MY_ACTIVITY_STREAM_FORM);
		//* Connections
		click(ELEMENT_MY_CONNECTIONS_TAB);
		waitForAndGetElement(ELEMENT_MY_CONNECTION_FORM);
		//* Wiki
		click(ELEMENT_MY_WIKI_TAB);
		waitForAndGetElement(ELEMEMT_MY_WIKI_FORM);
	}

	/**
	 * == User navigates in his own personal pages ==
	 * Test case ID: 74383
	 * Step 1: Show personal applications 
	 */
	@Test
	public void test02_UserNavigatesInHisOwnPersonalPages(){
		/*Step 1: Show personal applications*/ 
		//- Login as an user
		//- Connect to Intranet
		//- Mouse over on User name on top navigation, the select "My Profile" link
		naviToolbar.goToMyProfile();

		//- The Horizontal toolbar is displayed
		//- The list of applications of space are displayed in the following order:
		//* My Profile
		waitForAndGetElement(ELEMENT_MY_PROFILE_TAB);
		//* My Activity Stream
		waitForAndGetElement(ELEMENT_MY_ACTIVITY_STREAM_TAB);
		//* My Connections
		waitForAndGetElement(ELEMENT_MY_CONNECTIONS_TAB);
		//* My Wiki
		waitForAndGetElement(ELEMENT_MY_WIKI_TAB);
		//* My Dashboard
		waitForAndGetElement(ELEMENT_MY_DASHBOARD_TAB);

		//- Click on each applications, the application will show up in the main page
		//* My Profile
		click(ELEMENT_MY_PROFILE_TAB);
		waitForAndGetElement(ELEMENT_PROFILE_BASIC_INFO_FORM);
		//* My Activity Stream
		click(ELEMENT_MY_ACTIVITY_STREAM_TAB);
		waitForAndGetElement(ELEMENT_MY_ACTIVITY_STREAM_FORM);
		//* My Connections
		click(ELEMENT_MY_CONNECTIONS_TAB);
		waitForAndGetElement(ELEMENT_MY_CONNECTION_FORM);
		//* My Wiki
		click(ELEMENT_MY_WIKI_TAB);
		waitForAndGetElement(ELEMEMT_MY_WIKI_FORM);
		//* My Dashboard
		click(ELEMENT_MY_DASHBOARD_TAB);
		waitForAndGetElement(ELEMENT_MY_DASHBOARD_FORM);
	}
}
