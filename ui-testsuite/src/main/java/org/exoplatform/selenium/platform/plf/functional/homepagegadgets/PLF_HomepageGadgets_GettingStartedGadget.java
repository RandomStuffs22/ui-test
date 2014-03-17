package org.exoplatform.selenium.platform.plf.functional.homepagegadgets;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.HomePageGadget;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.openqa.selenium.By;
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
 * @author HaVTT
 * date: 13/02/2014
 *    
 */

public class PLF_HomepageGadgets_GettingStartedGadget extends Activity{

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
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
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
	 * CaseID 79575
	 * Display Getting started gadget
	 * 
	 * CaseID 79578
	 * Show default task of Getting started gadget
	 * 
	 */
	@Test(priority=0)
	public void test01_CheckDisplayOfGettingStartedGadget() { 
		
		driver.navigate().refresh();
		
		info("79575: check display of Getting Started Gadget on Homepage");
		waitForAndGetElement(homeGad.ELEMENT_GETTING_STARTED_GADGET_FORM);
		
		info("79578: check display of taks in Getting Started Gadget on Homepage");
		waitForAndGetElement(homeGad.ELEMENT_PROFILE_PICTURE);
		waitForAndGetElement(homeGad.ELEMENT_CONNECT_TO_COWORKERS);
		waitForAndGetElement(homeGad.ELEMENT_JOIN_A_SPACE);
		waitForAndGetElement(homeGad.ELEMENT_POST_AN_ACTIVITY);
		waitForAndGetElement(homeGad.ELEMENT_UPLOAD_A_DOCUMENT);
	}
	

	/**
	 * CaseID 79579
	 * Display Profile page from Getting started gadget
	 * 
	 * CaseID 79580
	 * Display Connections page from Getting started gadget
	 * 
	 * CaseID 79581
	 * Display All Spaces page from Getting started gadget
	 * 
	 * CaseID 79582
	 * Display Home page from Getting started gadget
	 * 
	 * CaseID 79583
	 * Display Documents page from Getting started gadget
	 */
	@Test(priority=1)
	public void test02_CheckRedirectionOfGettingStartedGadget() { 
		
		driver.navigate().refresh();
		waitForAndGetElement(homeGad.ELEMENT_GETTING_STARTED_GADGET_FORM);
		
		info("79579: Display Profile page from Getting started gadget");
		waitForAndGetElement(homeGad.ELEMENT_PROFILE_PICTURE);
		click(homeGad.ELEMENT_PROFILE_PICTURE);
		waitForAndGetElement(peoPro.ELEMENT_CHANGE_AVATAR_LINK);
		
		info("79580: Display Connections page from Getting started gadget");
		navToolBar.goToHomePage();
		waitForAndGetElement(homeGad.ELEMENT_CONNECT_TO_COWORKERS);
		click(homeGad.ELEMENT_CONNECT_TO_COWORKERS);
		waitForAndGetElement(peoConn.ELEMENT_MY_CONNECTIONS_TAB);
		
		info("79581: Display All Spaces page from Getting started gadget");
		navToolBar.goToHomePage();
		waitForAndGetElement(homeGad.ELEMENT_JOIN_A_SPACE);
		click(homeGad.ELEMENT_JOIN_A_SPACE);
		waitForAndGetElement(spaceMag.ELEMENT_ADDNEWSPACE_BUTTON);
		
		info("79583: Display Documents page from Getting started gadget");
		navToolBar.goToHomePage();
		waitForAndGetElement(homeGad.ELEMENT_UPLOAD_A_DOCUMENT);
		click(homeGad.ELEMENT_UPLOAD_A_DOCUMENT);
		driver.navigate().refresh();
		waitForAndGetElement(ecms.ELEMENT_UPLOAD_FILE_LINK);
		
		info("79582: Display Home page from Getting started gadget");
		navToolBar.goToHomePage();
		waitForAndGetElement(homeGad.ELEMENT_POST_AN_ACTIVITY);
		click(homeGad.ELEMENT_POST_AN_ACTIVITY);
		waitForAndGetElement(homeGad.ELEMENT_GETTING_STARTED_GADGET_FORM);
		waitForAndGetElement(ELEMENT_ACTIVITY_STREAM);
	}
	
	/**
	 * CaseID 79584
	 * Perform "Add a profile picture" action from Gadget
	 * 
	 * CaseID 79585
	 * Perform "Connect to coworkers" action from Gadget
	 * 
	 * CaseID 79586
	 * Perform "Join a space" action from Gadget
	 * 
	 * CaseID 79587
	 * Perform "Post an activity" action from Gadget
	 * 
	 * CaseID 79588
	 * Perform "Upload a document" action from Gadget
	 * 
	 * CaseID 79589
	 * Remove the Getting started gadget by performing all actions
	 * 
	 */
	@Test(priority=3)
	public void test03_CheckTaskDoneOnGettingStartedGadget() { 
		
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		String uploadFileName = "PLF_Upload_pdffile.pdf";
		String spaceName = "space79586";
		String activity1 = "activity 79587";
		String file = "ECMS_DMS_SE_Upload_imgfile.jpg";
		
		//Sign in by other user (mary williams)
		acc.signOut();
		acc.signIn(DATA_USER_MARY, DATA_PASS);
		
		info("79588: Upload a document");
		selectFile(driverName,true,folderPath,"",uploadFileName);
		driver.navigate().refresh();
		waitForAndGetElement(homeGad.ELEMENT_FINISH_UPLOAD_FILE.replace("${status}", "done"));
		
		info("79586: Join a space");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		navToolBar.goToHomePage();
		waitForAndGetElement(homeGad.ELEMENT_FINISH_JOIN_TO_SPACE.replace("${status}", "done"));
		
		info("79587: Post an activity");
		addActivity(true, activity1, false,"");
		driver.navigate().refresh();
		
		info("79584: Add a profile picture");
		click(homeGad.ELEMENT_PROFILE_PICTURE);
		peoPro.changeAvatar("TestData/"+file);
		navToolBar.goToHomePage();
		
		info("79585: Connect to co-workers");
		click(homeGad.ELEMENT_CONNECT_TO_COWORKERS);
		click(peoConn.ELEMENT_EVERYONE_TAB);
		peoConn.connectPeople(DATA_USER_JOHN); 
		acc.signOut();
		acc.signIn(DATA_USER_JOHN, DATA_PASS); 
		peoConn.acceptInvitation(DATA_USER_MARY);
		acc.signOut();
		acc.signIn(DATA_USER_MARY, DATA_PASS); 
		
		info("79589: Remove gadget after all actions are done");
		//confirm progress bar
		navToolBar.goToHomePage();
		waitForAndGetElement(homeGad.ELEMENT_CLOSE_GADGET_GETTING_STARTED);
		waitForAndGetElement(homeGad.ELEMENT_INPROGRESS_COMPLETE);
		//Close the gadget
		click(homeGad.ELEMENT_CLOSE_GADGET_GETTING_STARTED);
		driver.navigate().refresh();
		waitForElementNotPresent(homeGad.ELEMENT_GETTING_STARTED_GADGET_FORM);
		
		info("Restore data");
		activity.deleteActivity(activity1);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(uploadFileName, actionType.DELETE,true,true);
		acc.signOut();
		
	}
	
	/**
	 * CaseID 79576
	 * Remove the Getting started gadget by the close button
	 * 
	 * CaseID 79577
	 * Display the close icon
	 * 
	 * CaseID 79595
	 * Verify the remove of Getting started gadget
	 * 
	 * CaseID 79597
	 * Display Getting started gadget for other users in case admin deletes his started gadget
	 * 
	 * CaseID 79596
	 * Upload a document from a drive other than "Personal Documents"
	 * 
	 */
	@Test(priority=2)
	public void test04_RemoveGettingStartedGadget() { 
		String file1 = "ECMS_Admin_ManageCategories_Display.jpg";
		String file2 = "ECMS_Admin_SendMailScript_Template.txt";
		
		info("79577: confirm display [x] close button");
		driver.navigate().refresh();
		mouseOver(By.xpath("//*[@class='gadgetTitle title center']"),true);
//		waitForAndGetElement(homeGad.ELEMENT_X_CLOSE_BUTTON_GADGET_GETTING_STARTED);
		
		info("79576: remove gadget by clicking [x] close button");
		mouseOverAndClick(homeGad.ELEMENT_X_CLOSE_BUTTON_GADGET_GETTING_STARTED);
		
		info("79595: verify gadget is removed");
		driver.navigate().refresh();
		waitForElementNotPresent(homeGad.ELEMENT_GETTING_STARTED_GADGET_FORM);
		
		info("79597: display Getting started gadget for other users in case admin deletes his started gadget");
		acc.signOut();
		acc.signIn(DATA_USER_MARY, DATA_PASS);
		driver.navigate().refresh();
		waitForAndGetElement(homeGad.ELEMENT_GETTING_STARTED_GADGET_FORM);
		
		info("79596: Upload a document from a drive other than [Personal Documents]");
		navToolBar.goToSiteExplorer();
		ecms.uploadMultiFileSerial(file1, file2);
		navToolBar.goToHomePage();
		waitForAndGetElement(homeGad.ELEMENT_GETTING_STARTED_GADGET_FORM);
		waitForAndGetElement(homeGad.ELEMENT_FINISH_UPLOAD_FILE.replace("${status}", ""));
		
		info("Restore data");
		cMenu.deleteData(By.linkText(file1));
		cMenu.deleteData(By.linkText(file2));
		acc.signOut();		
	}

}
