package org.exoplatform.selenium.platform.plf.functional.homepagegadgets;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.HomePageGadget;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.exoplatform.selenium.platform.social.PeopleSearch;

/**
 * @author HaVTT
 * date: 10/02/2014
 *    
 */

public class PLF_HomepageGadgets_WhoIsOnline extends Activity {

	ManageAccount acc;
	HomePageGadget hg;
	NavigationToolbar navToolBar;
	PeopleConnection peopleC;
	PeopleSearch peopleS;

	
	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		acc = new ManageAccount(driver);
		hg = new HomePageGadget(driver);
		peopleC = new PeopleConnection(driver);
		peopleS = new PeopleSearch(driver);
		navToolBar = new NavigationToolbar(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * CaseID 79683
	 * Check the display of online user on Who'sOnline gadget
	 * 
	 * CaseID 79684
	 * Display pop up informations for user who requested a connection
	 * 
	 */
	@Test
	public void test01_checkDisplayOfOnlineUser(){				
		info("USER2: Switch to other window to login");
		loginWithAnotherAccOnThesameBrowser(DATA_USER_MARY, DATA_PASS);
		hg=new HomePageGadget(newDriver);
		newDriver.navigate().refresh();
		
		info("USER2: 79683 - Check pop-up information of online user");
		hg.checkUserInfoOnWhoisOnlineGadget(DATA_USER_JOHN, false, "", false, false);
		
		info("USER2: Go to My Connection");
		navToolBar = new NavigationToolbar(newDriver);
		navToolBar.goToConnectionPage();
		
		info("USER2: Connect with user acc 2");
		peopleC = new PeopleConnection(newDriver);
		peopleC.connectPeople(DATA_USER_JOHN);		
		
		info("USER2: Go to Homepage Intranet");
		navToolBar.goToHomePage();
		
		info("USER2: 79684 - Check pop-up information after sending a connection request");
		hg.checkUserInfoOnWhoisOnlineGadget(DATA_USER_JOHN, false, "", true, false);
		
		
		info("--Cancel Connection--");
		navToolBar.goToConnectionPage();
		peopleC.cancelRequest("john");
		info("--Close the 2nd browser window--");
		Utils.pause(500);
		newDriver.manage().deleteAllCookies();
		newDriver.quit();

	}

	/**
	 * CaseID 79685
	 * Check the popup information of online user who is a connection
	 * 
	 * CaseID 79686
	 * Display pop up informations for user who's invited to connect
	 */
	@Test
	public void test02_checkDisplayOfOnlineConnectedUser(){
		String activity = "HelloWorld79685";
		
		info("USER1: Share a status on activity stream");
		addActivity(true, activity, false,"");
		
		info("USER1: Go to My Connection");
		navToolBar.goToConnectionPage();
		
		info("USER1: Connect with user acc 2");
		peopleC.connectPeople(DATA_USER_MARY);
						
		info("USER2: Switch to other window to login");
		loginWithAnotherAccOnThesameBrowser(DATA_USER_MARY, DATA_PASS);
		hg=new HomePageGadget(newDriver);
		
		info("USER2: 79686 - Check popup information of online user when receiving a connection request");
		hg.checkUserInfoOnWhoisOnlineGadget(DATA_USER_JOHN, true, activity, true, true);
		
		info("USER2: Accept invitation from user acc 1");
		hg.acceptInvitationGadget("John Smith");
	
		info("USER2: 79685 - Check popup information of online user 1 when having a connection");
		hg.checkUserInfoOnWhoisOnlineGadget(DATA_USER_JOHN, true, activity, true, true);
		
		info("Restore data");
		info("--Cancel Connection--");
		peopleC = new PeopleConnection(newDriver);
		navToolBar = new NavigationToolbar(newDriver);
		navToolBar.goToConnectionPage();
		peopleS.searchPeople(true,DATA_USER_JOHN);
		peopleC.removeConnection(DATA_USER_JOHN);
		info("--Close the 2nd browser window--");
		Utils.pause(500);
		newDriver.manage().deleteAllCookies();
		newDriver.quit();
	}
	
	/**
	 * CaseID 79677
	 * Automatic refresh of the Gadget "Who's online?"
	 * 
	 * CaseID 79678
	 * Connect to User from the information pop up
	 * 
	 * CaseID 79682
	 * Display a long title of an activity in pop up for a connected user
	 */
	@Test
	public void test03_checkUpdateOfOnlineUser(){
		String longActivity = "Merry Xmas and Happy New Year 2014 to every body all around the world. We wish you a merry xmas and a happy new year with lots of joy and happiness and luck !!!";
		
		info("USER1: Share a status on activity stream");
		addActivity(true, longActivity, false,"");
		
		info("USER2: Switch to other window to login");
		loginWithAnotherAccOnThesameBrowser(DATA_USER_MARY, DATA_PASS);
		hg=new HomePageGadget(newDriver);
		newDriver.navigate().refresh();
		
		info("USER2: Check updated long status of user acc 1");
		hg.checkTruncatedStatusOnWhoIsOnlineGadget(DATA_USER_JOHN);
		
		info("USER2: Connect to other acc from Who'sOnline");
		hg.connectPeoplefromWhoisOnlineGadget(DATA_USER_JOHN);
		
		info("Restore data");
		info("--Cancel Connection--");
		peopleC = new PeopleConnection(newDriver);
		navToolBar = new NavigationToolbar(newDriver);
		navToolBar.goToConnectionPage();
		peopleS.searchPeople(true,DATA_USER_JOHN);
		peopleC.removeConnection(DATA_USER_JOHN);
		info("--Close the 2nd browser window--");
		Utils.pause(500);
		newDriver.manage().deleteAllCookies();
		newDriver.quit();
	}
	
}
