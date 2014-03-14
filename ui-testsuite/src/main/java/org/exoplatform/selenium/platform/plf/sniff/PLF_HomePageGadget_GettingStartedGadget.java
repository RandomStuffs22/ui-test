package org.exoplatform.selenium.platform.plf.sniff;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.HomePageGadget;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.exoplatform.selenium.platform.social.PeopleProfile;
import org.exoplatform.selenium.platform.social.SpaceManagement;

/**
 * @author HangNTT
 * date: 25/10/2013
 *    
 */
public class PLF_HomePageGadget_GettingStartedGadget extends Activity{
	ManageAccount acc; 
	HomePageGadget homeGad;
	ManageAccount magAcc;
	ManageMember magMember;
	SpaceManagement spaceMag;
	PeopleConnection peoConn; 
	PeopleProfile peoPro;
	HomePageActivity activity;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	ContextMenu cMenu;
	EcmsBase ecms;

	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		acc = new ManageAccount(driver);
		acc.signIn(DATA_USER1, DATA_PASS);
		homeGad = new HomePageGadget(driver);
		magMember = new ManageMember(driver);
		spaceMag = new SpaceManagement(driver);
		peoConn = new PeopleConnection(driver); 
		peoPro = new PeopleProfile(driver);
		activity = new HomePageActivity(driver);
		navToolBar = new NavigationToolbar(driver);
		cMenu= new ContextMenu(driver);
		ecms = new EcmsBase(driver);
		actBar = new ActionBar(driver);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	} 
	
	/**
	 * CaseID 70408
	 * Check Check display of Getting started Gadget
	 * 
	 * CaseID 70409
	 * Perform an action on Getting started Gadget
	 * 
	 * CaseID 70410
	 * Check direction when performing an action
	 * 
	 * CaseID 70411
	 * Remove Getting Started gadget
	 * 
	 * CaseID 70413
	 * Complete this Getting Started gadget
	 * 
	 */
	@Test
	public void test01_GettingStartedGadget() { 

		String uploadFileName = "PLF_Upload_pdffile.pdf";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		String spaceName = "space70413";
		String activity1 = "activity 70413";
		String user1="Mary Williams";
		String user_login1 = DATA_USER2;
		String user2="John Smith"; 
		String file = "ECMS_DMS_SE_Upload_imgfile.jpg";

		waitForAndGetElement(homeGad.ELEMENT_PROFILE_PICTURE);
		waitForAndGetElement(homeGad.ELEMENT_CONNECT_TO_COWORKERS);
		waitForAndGetElement(homeGad.ELEMENT_JOIN_A_SPACE);
		waitForAndGetElement(homeGad.ELEMENT_POST_AN_ACTIVITY);
		waitForAndGetElement(homeGad.ELEMENT_UPLOAD_A_DOCUMENT);

		//Check direction when performing an action
		click(homeGad.ELEMENT_PROFILE_PICTURE);
		waitForAndGetElement(homeGad.ELEMENT_PROFILE_PAGE);
		navToolBar.goToHomePage();
		click(homeGad.ELEMENT_CONNECT_TO_COWORKERS);
		waitForAndGetElement(homeGad.ELEMENT_ALL_PEOPLE);
		navToolBar.goToHomePage();
		click(homeGad.ELEMENT_JOIN_A_SPACE);
		waitForAndGetElement(homeGad.ELEMENT_ALL_SPACE);
		navToolBar.goToHomePage();
		click(homeGad.ELEMENT_POST_AN_ACTIVITY);
		String url = driver.getCurrentUrl();
		assert url.contains("#");
		click(homeGad.ELEMENT_UPLOAD_A_DOCUMENT);
		waitForAndGetElement(homeGad.ELEMENT_PERSONAL_DOCUMENT);
		navToolBar.goToHomePage();
		
		// Perform an action on Getting started Gadget (upload file)
		selectFile(driverName,true,folderPath,"",uploadFileName);
		driver.navigate().refresh();
		waitForAndGetElement(homeGad.ELEMENT_FINISH_UPLOAD_FILE.replace("${status}", "done"));
		//Join a space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		navToolBar.goToHomePage();
		waitForAndGetElement(homeGad.ELEMENT_FINISH_JOIN_TO_SPACE.replace("${status}", "done"));
		// Post an activity
		addActivity(true, activity1, false,"");
		driver.navigate().refresh();
		//Connect to coworkers
		click(homeGad.ELEMENT_CONNECT_TO_COWORKERS);
		click(peoConn.ELEMENT_EVERYONE_TAB);
		peoConn.connectPeople(user1); 
		acc.signOut();
		acc.signIn(user_login1, DATA_PASS); 
		peoConn.acceptInvitation(user2);
		acc.signOut(); 
		acc.signIn(DATA_USER1,DATA_PASS); 
		//Add profile picture
		click(homeGad.ELEMENT_PROFILE_PICTURE);
		peoPro.changeAvatar("TestData/"+file);
		navToolBar.goToHomePage();
		waitForAndGetElement(homeGad.ELEMENT_INPROGRESS_COMPLETE);
		
		//Remove Getting Started gadget
		click(homeGad.ELEMENT_CLOSE_GADGET_GETTING_STARTED);
		waitForElementNotPresent(homeGad.ELEMENT_GETTING_STARTED_GADGET_FORM);
	
		//Clear data
		activity.deleteActivity(activity1);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(uploadFileName, actionType.DELETE,true,true);
	}
}