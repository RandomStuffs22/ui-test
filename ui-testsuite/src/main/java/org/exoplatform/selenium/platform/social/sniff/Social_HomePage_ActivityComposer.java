package org.exoplatform.selenium.platform.social.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.social.Activity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 02/10/2013
 *
 */
public class Social_HomePage_ActivityComposer extends Activity {
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	HomePageActivity activity;
	ActionBar actBar;

	//ecms
	EcmsBase ecms;
	ContentTemplate conTemp;

	String user1="Mary Williams";

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER1);
		magAcc = new ManageAccount(driver);
		activity = new HomePageActivity(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		conTemp = new ContentTemplate(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Mention a user in activity composer ==
	 * Test case ID: 67900
	 * Step 1: Mentions on User Activity Stream
	 */
	@Test(priority=0)
	public void test01_MentionAUserInActivityComposer(){
		/*Step 1: Mentions on User Activity Stream*/ 
		//Hover the mouse over the name of user and select My Activity streams
		goToActivityStream();

		//Write something 
		//Entering @ followed by the user name
		//The suggestion list is hidden
		//Select name is wrapped in the input
		//Click Share button
		//In the activity stream, mentions are displayed as a link on "Firstname Lastname" to the user's activities page
		mentionActivity(true,"", user1);

		//Clear data
		activity.deleteActivity(user1);
	}

	/**
	 * == Share your status ==
	 * Test case ID: 74711
	 * Step 1: Share your status
	 */
	@Test(priority=1)
	public void test02_ShareYourStatus(){
		//Declare variables
		String activity1 = "activity74711";
		/*Step 1: Share your status*/  
		//- Go to Home page
		navToolBar.goToHomePage();

		//- Input into activity input field and click share
		//- The message will show in Activity Stream
		addActivity(true, activity1, false, "");
		Utils.pause(500);
		driver.navigate().refresh();

		//Clear data
		activity.deleteActivity(activity1);
	}

	/**
	 * == Add a document ==
	 * Test case ID: 74712
	 * Step 1: Go to Select File Dialog
	 * Step 2: Select document
	 * Step 3: Share the document
	 */
	@Test(priority=2)
	public void test03_AddAdocument(){
		//Declare variables
		String file="test03_AddAdocument";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";

		/*Create test data*/
		//Upload a file to the driverName/folderPath
		info("Go to Persional Documents");
		navToolBar.goToPersonalDocuments();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.goToViewMode("Admin");
		actBar.goToAddNewContent();
		conTemp.createNewFile(file, file, "");

		/*Step 1: Go to Select File Dialog*/  
		//- Goto Homepage
		navToolBar.goToHomePage();

		//- In Activity Composer click File (icon)
		//- Select File Dialog shows up
		/*Step 2: Select document*/
		//- Browse the Select File Dialog to choose a document
		//- Click Select
		//- A breadcrumb is displaying the current position of the user in the browsed drive.
		//- the name of the file should be displayed under the activity input field.
		/*Step 3:  Share the document*/
		//- Click Share button
		//- Activity is added into activity stream
		selectFile(driverName,false,folderPath,file,""); 

		//Clear data
		Utils.pause(500);
		driver.navigate().refresh();
		activity.deleteActivity(file);
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(file, actionType.DELETE,true,true);
	}

	/**
	 * == Add New folder and upload file ==
	 * Test case ID: 74713
	 * Step 1: Go to Select File Dialog
	 * Step 2: Create new folder
	 * Step 3: Upload a file
	 * Step 4: Share the document
	 */
	@Test(priority=3)
	public void test04_AddNewFolderAndUploadFile(){
		//Declare variables
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "folder04";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";

		/*Step 1: Go to Select File Dialog*/  
		//- Goto Homepage
		navToolBar.goToHomePage();

		//- In Activity Composer click File (icon)
		//Select File Dialog shows up
		/*Step 2: Create new folder*/
		//- Browse the Select File Dialog to select the path for the folder to be created
		//- Click Add new folder icon
		//- Input folder name and click ok
		//- A breadcrumb is displaying the current position of the user in the browsed drive.
		//- A popup shows up to users to put the folder name
		//- A folder is created and shown in the list
		/*Step 3:  Upload a file*/
		//- Select the folder and click upload icon
		//- Browse and select a file to upload
		//- File Dialog shows up
		//- Progress bar shows the status of uploading
		//- after the file is uploaded, filename is   showed under activity input field 
		/*Step 4:  Share the document*/
		//Click Share button
		//- Activity is added into activity stream
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);

		//Clear data
		activity.deleteActivity(uploadFileName);
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE,true,true);
	}
	
	/**
	 * ==  Add a link ==
	 * Test case ID: 74714
	 * Step 1: Add a link
	 * Step 2: Share the document
	 */
	@Test(priority=4)
	public void test05_AddALink(){
		//Declare variables
		String link = "https://www.google.com.vn/";
		/*Step 1: Add a link*/  
		//- Goto Homepage
		navToolBar.goToHomePage();
		
		//- In Activity Composer click Add Link
		//- Input URL and click add i con
		//- A input text field shows up to users to input the URL
		//- The infomation of URL is shown in featured content parts
		/*Step 2: Share the document*/
		//- Click Share button
		//- Activity is added into activity stream
		addActivity(false, "", true, link);

		//Clear data
		activity.deleteActivity(link);
	}
}
