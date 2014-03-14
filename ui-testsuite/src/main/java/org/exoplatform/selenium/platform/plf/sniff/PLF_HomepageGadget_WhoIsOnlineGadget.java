package org.exoplatform.selenium.platform.plf.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.HomePageGadget;
import org.exoplatform.selenium.platform.social.PeopleConnection;

/**
 * 
 * @author havtt
 * @date 01-Nov-2013
 */

public class PLF_HomepageGadget_WhoIsOnlineGadget extends PlatformBase {

	ManageAccount acc;
	HomePageGadget hg;
	NavigationToolbar navToolBar;
	PeopleConnection peopleC;
	
	String User1 = "fqa";
	String User2 = USER_ROOT;
	String Pass1 = DATA_PASS;
	String fullNameUser2="Root Root";
	
	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		acc = new ManageAccount(driver);
		hg = new HomePageGadget(driver);
		peopleC = new PeopleConnection(driver, this.plfVersion);
		navToolBar = new NavigationToolbar(driver);
		acc.signIn(User1, Pass1);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}


	/**
	 * Check display of WhoisOnline gadget
	 * CaseID 70763
	 */
	@Test
	public void test01_checkDisplayOfWhoisOnlineGadget(){
		info("Go to Homepage Intranet by user acc 1");
		navToolBar.goToHomePage();
				
		info("Switch to other browser to login by user acc 2");
		loginWithAnotherAccOnThesameBrowser(User2, Pass1);
	
		info("Confirm if WhoisOnline gadget dislays or not with user1");
		hg=new HomePageGadget(newDriver);
		newDriver.findElement(hg.ELEMENT_WHOISONLINE_GADGET);
		Utils.pause(500);
		newDriver.manage().deleteAllCookies();
		newDriver.quit();
	}
	
	/**
	 * Show information of online users
	 * CaseID 70764
	 */
	@Test
	public void test02_showInfoOfOnlineUser(){
		info("Go to Homepage Intranet by user acc 1");
		navToolBar.goToHomePage();
		
		info("Switch to other browser to login by user acc 2");
		loginWithAnotherAccOnThesameBrowser(User2, Pass1);
		
		info("Confirm if WhoisOnline gadget dislays or not with user1");
		hg=new HomePageGadget(newDriver);
		hg.checkUserInfoOnWhoisOnlineGadget(User1, false, "", false, false);
		newDriver.manage().deleteAllCookies();
		newDriver.quit();
	}
	
	/**
	 * Connect with users from Who's online gadget
	 * CaseID 70764
	 */
	@Test
	public void test03_ConnectUserfromWhoisOnlineGadget(){
		info("Go to Homepage Intranet by user acc 1");
		navToolBar.goToHomePage();
		
		info("Switch to other browser to login by user acc 2");
		loginWithAnotherAccOnThesameBrowser(User2, Pass1);
		hg=new HomePageGadget(newDriver);
		hg.checkUserInfoOnWhoisOnlineGadget(User1, false , "", false, false);
		
		info("User 2 connect with user 1 from Who's Online gadget");
		hg.connectPeoplefromWhoisOnlineGadget(User1);
		acc = new ManageAccount(newDriver);
		acc.signOut();

		info("Check if user 1 received connect invitation from user 2 or not");
		acc.signIn(User1, Pass1);
		Utils.pause(500);
		if(plfVersion == "4.1"){
			
			newDriver.findElement(By.xpath(hg.ELEMENT_SHOW_CONNECTIONS_REQUEST_USER_PLF41.replace("${peopleName}","root")));
		}
		else{// if (plfVersion =="4.0"){
			newDriver.findElement(By.xpath(hg.ELEMENT_SHOW_CONNECTIONS_REQUEST_USER.replace("${nameinvitation}","Root Root")));
		}	
		
		Utils.pause(500);
		
		info("-- Clear data --");
		navToolBar = new NavigationToolbar(newDriver);
		peopleC = new PeopleConnection(newDriver, this.plfVersion);
		navToolBar.goToConnectionPage();
		peopleC.ignoreInvitation(User2);
		newDriver.manage().deleteAllCookies();
		newDriver.quit();
	}

	/**
	 * Access activity stream of other user from Who's online gadget
	 * CaseID 70764
	 */
	@Test
	public void test04_accessASfromWhoisOnlineGadget(){
		info("Go to Homepage Intranet by user acc 1");
		navToolBar.goToHomePage();
		
		info("Switch to other browser to login by user acc 2");
		loginWithAnotherAccOnThesameBrowser(User2, Pass1);
		
		info("User 2 connect with user 1 from Who's Online gadget");
		hg=new HomePageGadget(newDriver);
		hg.accessASfromWhoisOnlineGadget(User1);
		Utils.pause(500);
		
		info("Check if user 1 profile page is displayed or not");
		newDriver.findElement(By.xpath(hg.ELEMENT_PROFILE_TAB_USER_INFO.replace("${acc}",User1)));
		Utils.pause(500);
		newDriver.findElement(By.xpath(hg.ELEMENT_MY_AS_TAB.replace("${acc}",User1)));
		Utils.pause(500);
		newDriver.manage().deleteAllCookies();
		newDriver.quit();
	}
}
