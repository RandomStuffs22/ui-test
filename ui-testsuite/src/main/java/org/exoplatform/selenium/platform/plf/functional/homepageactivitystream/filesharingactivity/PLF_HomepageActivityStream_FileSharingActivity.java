package org.exoplatform.selenium.platform.plf.functional.homepageactivitystream.filesharingactivity;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.openqa.selenium.By;
import org.testng.annotations.*;

/**
 * @author chinhdtt
 *
 */
public class PLF_HomepageActivityStream_FileSharingActivity extends Activity{

	ManageAccount acc; 
	HomePageActivity home; 
	NavigationToolbar nav; 
	ActionBar actBar;	
	EcmsBase ecms; 
	ManageMember mMember; 

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver, this.plfVersion);
		home = new HomePageActivity(driver, this.plfVersion); 
		acc.signIn(DATA_USER1, DATA_PASS);
		nav = new NavigationToolbar(driver, this.plfVersion);	
		actBar = new ActionBar(driver, this.plfVersion);
		ecms = new EcmsBase(driver, this.plfVersion);
		mMember = new ManageMember(driver, this.plfVersion);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();		
		driver.quit();
	}
	/**
	 * Case ID:77157.
	 * Test Case Name: Add File Sharing activity after share a file in portal.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/10 09:50:00
	 */
	@Test
	public  void test01_AddFileSharingActivityAfterShareAFileInPortal() {
		info("Test 1: Add File Sharing activity after share a file in portal");
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "Folder77157";
		/*
		- Connect to Intranet
		- From the activity Composer, click on file and add a file icon
		- Click on Share
		 *Input Data: 
		 *Expected Outcome: 
		- A File Shared activity is added in the activity stream with the file as an attachment		*/ 
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);
		waitForAndGetElement(By.xpath(home.ELEMENT_ACTIVITY.replace("${activityText}", uploadFileName)));

		//delete data
		home.deleteActivity(uploadFileName);
		nav.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE, true, true);	
	}

	/**
	 * Case ID:77166.
	 * Test Case Name: Delete a File sharing activity from activity stream by its user in portal.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/10 09:50:00
	 */
	@Test
	public  void test02_DeleteAFileSharingActivityFromActivityStreamByItsUserInPortal() {
		info("Test 2: Delete a File sharing activity from activity stream by its user in portal");
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "Folder77166";
		/*
		- Connect to Intranet
		- Share a File activity 
		 *Input Data: 
		 *Expected Outcome: 
		- The File sharing activity is displayed in the activity stream		*/
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);
		waitForAndGetElement(By.xpath(home.ELEMENT_ACTIVITY.replace("${activityText}", uploadFileName)));

		/*
		- Move the mouse over the File sharing activity
		 *Input Data: 
		 *Expected Outcome: A (X) icon is displayed in the top right panel of the activity		*/
		mouseOver(By.xpath(home.ELEMENT_ACTIVITY.replace("${activityText}", uploadFileName)), true);
		waitForAndGetElement(By.xpath(home.ELEMENT_ACTIVITY_DELETE.replace("${activityText}", uploadFileName)), DEFAULT_TIMEOUT,1,2);

		/*
		- Click on the (X) icon then click OK in confirmation pop up
		 *Input Data: 
		 *Expected Outcome: The File sharing activity is removed from the activity stream		*/ 
		home.deleteActivity(uploadFileName);
		waitForElementNotPresent(By.xpath(home.ELEMENT_ACTIVITY.replace("${activityText}", uploadFileName)));
		nav.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE, true, true);	
	}

	/**
	 * Case ID:77167.
	 * Test Case Name: Dislike a File sharing activity from intranet.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/10 09:50:00
	 */
	@Test
	public  void test03_DislikeAFileSharingActivityFromIntranet() {
		info("Test 3: Dislike a File sharing activity from intranet");
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "Folder77167";

		/*
		- Connect to Intranet with User A
		- Share a File 
		 *Input Data: 
		 *Expected Outcome: 
		- The File sharing activity is displayed in the activity stream		*/
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);
		waitForAndGetElement(By.xpath(home.ELEMENT_ACTIVITY.replace("${activityText}", uploadFileName)));

		/*
		- Click on like icon
		 *Input Data: 
		 *Expected Outcome: 
		- The File sharing activity is liked by the user, the number of like is updated to +1		*/
		home.likeOrUnlikeActivity(uploadFileName);

		/*
		- Click on like icon
		 *Input Data: 
		 *Expected Outcome: 
		- The File sharing activity is disliked by the user, the number of like is updated to -1		*/ 
		home.likeOrUnlikeActivity(uploadFileName);

		//delete data
		home.deleteActivity(uploadFileName);
		nav.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE, true, true);		
	}

	/**
	 * Case ID:77331.
	 * Test Case Name: Add File Sharing activity after share a file in space.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/10 09:50:00
	 */
	@Test
	public  void test04_AddFileSharingActivityAfterShareAFileInSpace() {
		info("Test 4: Add File Sharing activity after share a file in space");
		String spaceName = "Space 77331";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "Folder77331";

		/*
		- Connect to Intranet
		- Click [join a space]
		- Add new space
		 *Input Data: 
		 *Expected Outcome: New space is added successfully		*/
		mMember.goToMySpacePage();
		mMember.addNewSpace(spaceName, "");

		/*
		- From the activity Composer, click on file and add a file icon
		- Click on Share
		 *Input Data: 
		 *Expected Outcome: 
		- A File Shared activity is added in the activity stream with the file as an attachment		*/ 
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);
		waitForAndGetElement(By.xpath(home.ELEMENT_ACTIVITY.replace("${activityText}", uploadFileName)));

		//delete data
		home.deleteActivity(uploadFileName);
		mMember.goToAllSpaces();
		mMember.deleteSpace(spaceName,300000);
		nav.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE, true, true);	
	}

	/**
	 * Case ID:77332.
	 * Test Case Name: Delete a File sharing activity from activity stream by its user in space.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/10 09:50:00
	 */
	@Test
	public  void test05_DeleteAFileSharingActivityFromActivityStreamByItsUserInSpace() {
		info("Test 5: Delete a File sharing activity from activity stream by its user in space");
		String spaceName = "Space 77332";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "Folder77332";

		/*
		- Connect to Intranet
		- Click [Join a space]
		- Add new space
		 *Input Data: 
		 *Expected Outcome: 
		- New spaced is added successfully		*/
		mMember.goToMySpacePage();
		mMember.addNewSpace(spaceName, "");

		/*
		- Go to [Activities stream]
		- Share a File activity 
		 *Input Data: 
		 *Expected Outcome: 
		- The File sharing activity is displayed in the activity stream		*/
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);
		waitForAndGetElement(By.xpath(home.ELEMENT_ACTIVITY.replace("${activityText}", uploadFileName)));

		/*
		- Move the mouse over the File sharing activity
		 *Input Data: 
		 *Expected Outcome: A (X) icon is displayed in the top right panel of the activity		*/
		mouseOver(By.xpath(home.ELEMENT_ACTIVITY.replace("${activityText}", uploadFileName)), true);
		waitForAndGetElement(By.xpath(home.ELEMENT_ACTIVITY_DELETE.replace("${activityText}", uploadFileName)), DEFAULT_TIMEOUT,1,2);

		/*
		- Click on the (X) icon and click OK in confirmation popup
		 *Input Data: 
		 *Expected Outcome: The File sharing activity is removed from the activity stream		*/ 
		home.deleteActivity(uploadFileName);
		waitForElementNotPresent(By.xpath(home.ELEMENT_ACTIVITY_DELETE.replace("${activityText}", uploadFileName)), DEFAULT_TIMEOUT,1,2);

		//delete data
		mMember.goToAllSpaces();
		mMember.deleteSpace(spaceName,300000);
		nav.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE, true, true);	
	}

	/**
	 * Case ID:77618.
	 * Test Case Name: Dislike a File sharing activity from space.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/10 09:50:00
	 */
	@Test
	public  void test06_DislikeAFileSharingActivityFromSpace() {
		info("Test 6: Dislike a File sharing activity from space");
		String spaceName = "Space 77618";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "Folder77618";

		/*
		- Connect to Intranet with User A
		- Add new space
		 *Input Data: 
		 *Expected Outcome: Add new space successfully		*/
		mMember.goToMySpacePage();
		mMember.addNewSpace(spaceName, "");

		/*
		- Share a File 
		 *Input Data: 
		 *Expected Outcome: 
		- The File sharing activity is displayed in the activity stream		*/
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);
		waitForAndGetElement(By.xpath(home.ELEMENT_ACTIVITY.replace("${activityText}", uploadFileName)));

		/*
		- Click on like icon
		 *Input Data: 
		 *Expected Outcome: 
		- The File sharing activity is liked by the user, the number of like is updated to +1		*/
		home.likeOrUnlikeActivity(uploadFileName);

		/*
		- Click on like icon
		 *Input Data: 
		 *Expected Outcome: 
		- The File sharing activity is disliked by the user, the number of like is updated to -1		*/ 
		Utils.pause(100);
		home.likeOrUnlikeActivity(uploadFileName);

		//delete data
		mMember.goToAllSpaces();
		mMember.deleteSpace(spaceName,300000);
		nav.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE, true, true);	
	}
}