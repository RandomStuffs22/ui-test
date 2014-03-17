package org.exoplatform.selenium.platform.plf.functional.homepageactivitystream.activitycomposer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.social.Activity;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 22/11/2013
 *
 */
public class PLF_HomePageActivityStream_ActivityComposer_File extends Activity {
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	EcmsBase ecms;
	HomePageActivity activity;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		button = new Button(driver);
		activity = new HomePageActivity(driver);
		actBar = new ActionBar(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Cancel an upload of file ==
	 * Test case ID: 77779
	 * Step 1: Open popup "Select File"
	 * Step 2: Upload a file from local
	 * Step 3: Cancel the upload
	 * PENDING: can't verify progress bar of the uploaded file
	 * --> REASON: Need to upload a large file to server to verify it.
	 */
	@Test(groups = "pending")
	public void test01_CancelAnUploadOfFile(){
		/*Declare variables*/
		//String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		//String driverName = "Personal Drives";
		//String folderPath = "Personal Documents";

		/* Step 1: Open popup "Select File" */
		//- Connect to Intranet
		//- From [Activity Composer], click on [File] button
		//- The pop up "Select File" is displayed

		/* Step 2: Upload a file from local */
		//- Select a drive from [Select drive] list
		//- Click on a folder
		//- Click on the icon [Upload]
		//- Select a file from  local
		//- Click [Open]
		//- The progress bar of the uploaded file is displayed
		//- A cross icon in the end of the bar is displayed
		navToolBar.goToHomePage();
		//selectFile(driverName,true,folderPath,"",uploadFileName,"");

		/* Step 3: Cancel the upload */
		//- Click on the cross [x] icon
		//The upload is canceled
	}

	/**
	 * == Create a new folder from [Select File] pop up ==
	 * Test case ID: 77783,77795,77798
	 * Step 1: Open [Select File] popup
	 * Step 2: Add a folder
	 */
	@Test
	public void test02_OpenSelectFilePopup(){
		/*Declare variables*/
		String folder = "folder77783";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";

		/* Step 1: Open popup "Select File" */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [File]
		//- The pop up [Select File] is displayed
		/* Step 2: Add a folder */
		//- Click on the icon [New Folder]
		//- Enter text into the field
		//- Click on the button [OK]
		//- A pop up to enter a new folder's name is displayed: Please enter new folder's name
		//- The folder is created
		selectFile(driverName,false,folderPath,"","",folder,false);
		button.closeWindow();

		//Clear data
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE,true,true);
	}

	/**
	 * == Display the breadcrumb of a very long path ==
	 * Test case ID: 77785
	 * Step 1: Open [Select File] popup
	 * Step 2: Add a folder with a long name
	 */
	@Test
	public void test03_DisplayTheBreadcrumbOfAVeryLongPath(){
		/*Declare variables*/
		String folder = "this is a long sub-folder 77783";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";

		/* Step 1: Open popup "Select File" */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [File]
		//- The pop up [Select File] is displayed
		/* Step 2: Add a folder with a long name */
		//- Select a drive from the list [Select Drive]
		//- Click on a folder,
		//- Then click on its sub-folder 
		//- Click on icon [New folder]
		//- Input into fields (ex "this is a long sub-folder 3")
		//- Click [OK]
		//- A folder with a long name is created
		selectFile(driverName,false,folderPath,"","",folder,false);
		button.closeWindow();

		//Clear data
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE,true,true);
	}

	/**
	 * == Display the breadcrumb of the path ==
	 * Test case ID: 77788
	 * Step 1: Open [Select File] popup
	 * Step 2: Browse folders
	 * REFER: https://jira.exoplatform.org/browse/COMMONS-259
	 */
	@Test (groups="error")
	public void test04_DisplayTheBreadcrumbOfThePath(){
		/*Declare variables*/
		String folder1 = "folder777881";
		String folder2 = "folder777882";
		String folder3 = "folder777883";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";

		/* Step 1: Open popup "Select File" */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [File]
		//- The pop up [Select File] is displayed
		/* Step 2: Browse folders */
		//- Select a drive from the list [Select Drive]
		//- Click on a folder (called "folder 1"), then click on the sub-folder (call "sub folder 2"], then click on its sub-folder (ex sub-folder 3)
		//- The current position in the browsed drive is displayed in breadcrumb
		//-  Only the folder of the path's root level, plus sub-folder 2, sub-folder 3. the folder 1 is not shown
		selectFile(driverName,false,folderPath,"","",folder1,false);
		button.closeWindow();
		selectFile(driverName,false,folderPath+"/"+folder1,"","",folder2,false);
		button.closeWindow();
		selectFile(driverName,false,folderPath+"/"+folder1+"/"+folder2,"","",folder3,false);
		button.closeWindow();
		goToFolderPath(driverName,folderPath+"/"+folder1+"/"+folder2+"/"+folder3,true);
		button.cancel();

		//Clear data
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder1, actionType.DELETE,true,true);
	}

	/**
	 * == Hide the pop up "Select File" from the icon "x" ==
	 * Test case ID: 77792,77795
	 * Step 1: Open [Select File] popup
	 * Step 2: Close [Select File] popup
	 */
	@Test
	public void test05_HideThePopUpSelectFileFromTheIconX(){
		/*Declare variables*/
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";

		/* Step 1: Open popup "Select File" */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [File]
		//- The pop up [Select File] is displayed
		goToFolderPath(driverName,folderPath);

		/* Step 2: Close [Select File] popup */
		//-  Click on the icon [x] at the top right of the pop up
		//The pop up disappears without any file selected
		button.closeWindow();
		waitForElementNotPresent(ELEMENT_SELECT_FILE_POPUP);
	}

	/**
	 * == Hide the pop up [Select File] from the button [Cancel] ==
	 * Test case ID: 77791,77795
	 * Step 1: Open [Select File] popup
	 * Step 2: Cancel [Select File] popup
	 */
	@Test
	public void test06_HideThePopUpSelectFileFromTheButtonCancel(){
		/*Declare variables*/
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";

		/* Step 1: Open popup "Select File" */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [File]
		//- The pop up [Select File] is displayed
		goToFolderPath(driverName,folderPath);

		/* Step 2: Cancel [Select File] popup */
		//- Click on the button [Cancel]
		//The pop up disappears without any file selected
		button.cancel();
		waitForElementNotPresent(ELEMENT_SELECT_FILE_POPUP);
	}

	/**
	 * == Open a folder from the breadcrumb ==
	 * Test case ID: 77794,77795
	 * Step 1: Open pop up [Select File]
	 * Step 2: Browse folders
	 * Step 3: Open a folder from the breadcrumb
	 */
	@Test
	public void test07_OpenAFolderFromTheBreadcrumb(){
		/*Declare variables*/
		String folder1 = "folder777941";
		String folder2 = "folder777942";
		String folder3 = "folder777943";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";

		/* Step 1: Open popup "Select File" */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [File]
		//- The pop up [Select File] is displayed
		/* Step 2: Browse folders */
		//- Select a drive from the list [Select Drive]
		//- Click on a folder (called "folder 1"), then click on the sub-folder (call "sub folder 2"], then click on its sub-folder (ex sub-folder 3)
		//- The current position in the browsed drive is displayed in breadcrumb
		//-  Only the folder of the path's root level, plus sub-folder 2, sub-folder 3. the folder 1 is not shown
		selectFile(driverName,false,folderPath,"","",folder1,false);
		selectFile(driverName,false,folderPath+"/"+folder1,"","",folder2,false);
		selectFile(driverName,false,folderPath+"/"+folder1+"/"+folder2,"","",folder3,false);
		goToFolderPath(driverName,folderPath+"/"+folder1+"/"+folder2,true);

		/*Step 3: Open a folder from the breadcrumb*/
		//- Click on a folder's name from the breadcrumb
		//- The select folder is openned
		waitForAndGetElement(By.linkText(folder3));
		button.cancel();

		//Clear data
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder1, actionType.DELETE,true,true);
	}

	/**
	 * == Select a file by a double click ==
	 * Test case ID: 77799
	 * Step 1: Open [Select File] popup
	 * Step 2: Select a file by double-clicking
	 * ERROR: Refer https://jira.exoplatform.org/browse/COMMONS-278
	 */
	//@Test (groups="error")
	public void test08_OpenSelectFilePopup(){
		/*Declare variables*/
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "folder77783";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";

		/* Step 1: Open popup "Select File" */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [File]
		//- The pop up [Select File] is displayed
		/* Step 2: Select a file by double-clicking */
		//- Select a drive from [Select Drive] list
		//- Click on a folder, do this until there is a file in the current folder
		//- Double click on that file
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);
		goToFolderPath(driverName,folderPath);
		doubleClickOnElement(By.linkText(uploadFileName));

		//The file is selected and the pop up is closed
		waitForElementNotPresent(ELEMENT_SELECT_FILE_POPUP);
		//Clear data
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE,true,true);
	}

	/**
	 * == Select a file by a simple click ==
	 * Test case ID: 77802
	 * Step 1: Open [Select File] popup
	 * Step 2: Select a file by double-clicking
	 */
	@Test
	public void test09_SelectAFileByASimpleClick(){
		/*Declare variables*/
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "folder77802";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";

		/* Step 1: Open popup "Select File" */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [File]
		//- The pop up [Select File] is displayed
		/* Step 2: Select a file by clicking */
		//- Select a drive from [Select Drive] list
		//- Click on a folder, do this until there is a file in the current folder
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);
		goToFolderPath(driverName,folderPath);

		//- Click on that file
		click(By.linkText(uploadFileName));

		//The name of the file is shown in the breadcrumb
		waitForAndGetElement(ecms.ELEMENT_BREADCUMBSCONTAINER.replace("${fileName}", uploadFileName));
		button.closeWindow();
		waitForElementNotPresent(ELEMENT_SELECT_FILE_POPUP);

		//Clear data
		activity.deleteActivity(uploadFileName);
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE,true,true);
	}

	/**
	 * == Share an attached file ==
	 * Test case ID: 77811
	 * Step 1: Share an attached file
	 */
	@Test
	public void test10_ShareAnAttachedFile(){
		/*Declare variables*/
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "folder77811";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";

		/*Create data*/
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);
		activity.deleteActivity(uploadFileName);

		/* Step 1: Share an attached file */
		//- Connect to Intranet
		//- From the [Activity Composer] box, click on [File] button
		//- Select a drive from [Select drive] list
		//- Click a folder, do this until there is a file in the current folder
		goToFolderPath(driverName,folderPath);

		//- Double click this file
		click(By.linkText(uploadFileName));
		Utils.pause(500);

		//- Click on the button [Share]
		//- The file is added to activity stream
		//- The text "What are working on?" is shown with lighter color
		click(ELEMENT_SELECT_BUTTON);
		click(ELEMENT_SHARE_BUTTON);
		waitForAndGetElement(By.linkText(uploadFileName));

		//Clear data
		activity.deleteActivity(uploadFileName);
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE,true,true);
	}

	/**
	 * == Share an uploaded file ==
	 * == Upload a new file from "Select File" pop up ==
	 * Test case ID: 78684, 77812, 77820
	 * Step 1: Open [Select file] popup
	 * Step 2: Open window for browse file
	 * Step 3: Select a file
	 * Step 4: Share an uploaded file
	 */
	@Test
	public void test11_ShareAnUploadedFile(){
		/*Declare variables*/
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "folder77811";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		/* Step 1: Open [Select file] popup */
		//- Connect to Intranet
		//- From the [Activity Composer] box, click on [File] button
		//- Select a drive from [Select drive] list
		//The popup [Select file] is shown
		/* Step 2: Open window for browse file */
		//- Click on a folder
		//- Click on the icon [Upload]
		//The window to upload from desktop is displayed
		/* Step 3: Select a file */
		//- Double click a file from local
		//- The file upload is showing the progress
		//- It displays the file name and the file's type icon
		/* Step 4: Share an uploaded file */
		//- Double click this file
		//- Click on the button [Share]
		//This file is shared
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);

		//Clear data
		activity.deleteActivity(uploadFileName);
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE,true,true);
	}

	/**
	 * == Show the vertical scroll bar for list of files ==
	 * Test case ID: 77815
	 * Step 1: Open [Select File] popup
	 * Step 2: Open a folder
	 * Step 3: Upload files
	 * Step 4: Add folders
	 */
	@Test
	public void test12_ShowTheVerticalScrollBarForListOfFiles(){
		/*Declare variables*/
		String uploadFileName1 = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String uploadFileName2 = "ECMS_DMS_SE_File.jpg";
		String folder1 = "778151";
		String folder2 = "778152";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		/* Step 1: Open [Select File] popup */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [File]
		//- The pop up [Select File] is displayed
		/* Step 2: Open a folder */
		//- Select a drive from the [Select drive] list
		//- Click on a folder
		//- A drive type is selected
		//- The folder is opened
		/* Step 3: Upload files */
		//- Click on [Upload] icon
		//- Double click a file from local
		//- Do this step many times so that many files are uploaded in the current folder
		//- Files are uploaded
		selectFile(driverName,true,folderPath,"",uploadFileName1,"", false);
		button.closeWindow();
		waitForElementNotPresent(ELEMENT_SELECT_FILE_POPUP);
		selectFile(driverName,true,folderPath,"",uploadFileName2,"", false);

		//- The vertical scroll bar is displayed
		WebElement element = waitForAndGetElement(By.id("ListRecords"));
		String str1 = String.valueOf(((JavascriptExecutor)driver).executeScript("return arguments[0].clientHeight;", element));
		String str = String.valueOf(((JavascriptExecutor)driver).executeScript("return arguments[0].scrollHeight;", element));
		int clientHeight = Integer.parseInt(str1);
		int scrollHeight = Integer.parseInt(str);
		assert clientHeight<scrollHeight;
		button.closeWindow();
		waitForElementNotPresent(ELEMENT_SELECT_FILE_POPUP);
		
		//Clear data
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(uploadFileName1, actionType.DELETE,true,true);
		actBar.actionsOnElement(uploadFileName2, actionType.DELETE,true,true);
		navToolBar.goToHomePage();
		
		/* Step 4: Add folders */
		//- Click on [New folder] icon
		//- Input into fields
		//- Click [OK]
		selectFile(driverName,false,folderPath,"","",folder1,false);
		button.closeWindow();
		waitForElementNotPresent(ELEMENT_SELECT_FILE_POPUP);
		
		//- Do this step many times so that many folders are added in the current folder
		selectFile(driverName,false,folderPath,"","",folder2,false);
		click(By.linkText(folderPath));
		Utils.pause(500);
		//- Folders are added
		//- The vertical scroll bar is displayed
		element = waitForAndGetElement(By.id("ListRecords"));
		str1 = String.valueOf(((JavascriptExecutor)driver).executeScript("return arguments[0].clientHeight;", element));
		str = String.valueOf(((JavascriptExecutor)driver).executeScript("return arguments[0].scrollHeight;", element));
		info(str1);
		info(str);
		clientHeight = Integer.parseInt(str1);
		scrollHeight = Integer.parseInt(str);
		assert clientHeight<scrollHeight;
		button.closeWindow();
		waitForElementNotPresent(ELEMENT_SELECT_FILE_POPUP);
		
		//Clear data
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder1, actionType.DELETE,true,true);
		actBar.actionsOnElement(folder2, actionType.DELETE,true,true);
	}
}
