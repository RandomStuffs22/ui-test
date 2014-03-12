package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.publishactivities;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author: PhuongDT
 * @date: 16/09/2013
 */
public class ECMS_SE_PublishActivities_FilesActivities  extends PlatformBase {
	//Platform
	ManageAccount magAcc;
	ActionBar actBar;
	NavigationToolbar navToolBar;
	ManageMember magMember;
	HomePageActivity activity;

	//Ecms
	EcmsBase ecms;
	ContentTemplate cTemplate;
	SitesExplorer siteExp;
	ContextMenu cMenu;
	Button btn;

	@BeforeMethod
	public void beforeMethods() {
		getDriverAutoSave();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER1);
		magAcc = new ManageAccount(driver);
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		siteExp = new SitesExplorer(driver);
		navToolBar = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		cMenu= new ContextMenu(driver);
		btn = new Button(driver);
		activity = new HomePageActivity(driver);
		magMember = new ManageMember(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	 * == Not update File activity after creating a new folder ==
	 * Test case ID: 76991
	 * Step 1: 	- Connect to Intranet
				- Open Sites Explorer 
				- Create a new folder
				- Back to the Home page
	 */
	@Test
	public void test01_NotUpdateFileActivityAfterCreatingANewFolder(){
		//Declare variable
		String folder = "folder01";
		By elementFolder = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", folder));

		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		
		//Create a new folder
		info("-- Create parent folder --");
		cTemplate.createNewFolder(folder, folderType.None);
		
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
		
		//No activity is displayed in the activity stream
		info("-- Check activity after adding a folder --");
		waitForElementNotPresent(activity.ELEMENT_CONTENT_NAME.replace("@{fileName}", folder));
		
		/*Clear data*/
		info ("-- Clear data --");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementFolder);	
	}
	
	/**
	 * == Add File activity after uploading a file in a space ==
	 * Test case ID: 76992
	 * Step 1: 	- Connect to Intranet
				- Add new space
				- Open Documents in this space
				- Upload a file
				- Back to the Home page
	 */
	@Test
	public void test02_AddFileActivityAfterUploadingAFileInASpace(){
		//Declare variable
		String spacename = "Space02";
		String spacedesc = "Description Of Space02";
		String file = "ECMS_DMS_SE_Upload_imgfile.jpg";
		
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spacename, spacedesc);
		
		//Open Documents in this space
		waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		click(magMember.ELEMENT_DOCUMENTS_TAB);
				
		//Upload a file
		ecms.uploadFile("TestData/"+file);
		
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
		
		//- A File activity is added to the activity stream
		info("-- A File activity is added to the activity stream --");
		activity.checkInforAfterAddingDocument(file, "", "File", "423 KB", "", "", "", "");
		
		/*Clear data*/
		info ("-- Clear data --");
		magMember.goToMySpacePage();
		magMember.deleteSpace(spacename);
	}
	
	/**
	 * == Displaying of file format (image, pdf, office) in the File activity (1) ==
	 * Test case ID: 76994
	 * Step 1: 	- Connect to Intranet
				- Go to SE and  Upload a file: image, pdf or office document (except personal document drive)
				- Go back intranet home page
		PENDING: check  preview of the file is displayed (only first page for documents)
		Refer: https://jira.exoplatform.org/browse/FQA-1250
	 */
	@Test (groups={"pending"})
	public void test03_DisplayingOfFileFormatImagePdfOfficeInTheFileActivity1(){
		//Declare variable
		String file = "KS_Wiki_Attachment_pdffile.pdf";
		By elementfile = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", file));
				
		//Go to SE
		navToolBar.goToSiteExplorer();
				
		//Upload a file: image, pdf or office document (except personal document drive)
		ecms.uploadFile("TestData/"+file);
				
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
				
		//- The File activity is displayed in the activity stream
		info("-- A File activity is added to the activity stream --");
		activity.checkInforAfterAddingDocument(file, "", "File", "1 MB", "", "", "", "");
				
		//- A preview of the file is displayed (only first page for documents)
				
		/*Clear data*/
		info ("-- Clear data --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementfile);	
	}
	
	/**
	 * == Display the content of the File activity ==
	 * Test case ID: 76995
	 * Step 1: 	Create file activity
	 */
	@Test
	public void test04_DisplayTheContentOfTheFileActivity(){
		//Declare variable
		String file = "KS_Wiki_Attachment_pdffile.pdf";
		By elementfile = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", file));
		String title = "Title of uploaded file";
		String desc = "Description of uploaded file";
		
		//Go to SE
		navToolBar.goToSiteExplorer();
		
		//Upload a file: image, pdf or office document (except personal document drive)
		ecms.uploadFile("TestData/"+file);
		
		//File's title if exist, or file's name instead
		//File description if exist
		info("Edit title of uploaded file");
		navToolBar.goToSiteExplorer();
		if(isElementNotPresent(elementfile) && isElementPresent(By.xpath(actBar.ELEMENT_VIEW_MODE_LINK.replace("${viewName}", "Web")))){
			actBar.goToViewMode("Web");
			click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		}
		ecms.goToNode(elementfile);
		actBar.goToEditDocument(file);
		ecms.editUploadedFile(file, "", title, desc, "", "");
		
		//Version (if exist) and file size
		actBar.publishDocument();
		
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
		
		//The File activity is displayed in the activity stream
		info("-- A File activity is added to the activity stream --");
		activity.checkInforAfterAddingDocument(file, "", "File", "1 MB", "", "1", desc, "");
		activity.checkTitleAfterEditing(file, title);
		
		/*Clear data*/
		info ("-- Clear data --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementfile);	
	}
	
	/**
	 * == Edit a file from the File activity from intranet home page ==
	 * Test case ID: 76997
	 * Step 1: Create file activity
	 * Step 2: Edit file from activity stream
	 */
	@Test
	public void test05_EditAFileFromTheFileActivityFromIntranetHomePage(){
		//Declare variable
		String file = "KS_Wiki_Attachment_pdffile.pdf";
		By elementfile = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", file));
				
		/*Step 1: Create file activity*/
		//Go to SE
		navToolBar.goToSiteExplorer();
				
		//Upload a file
		ecms.uploadFile("TestData/"+file);
				
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
				
		//- The File activity is displayed in the activity stream
		info("-- A File activity is added to the activity stream --");
		activity.checkInforAfterAddingDocument(file, "", "File", "1 MB", "", "", "", "");
				
		/*Step 2: Edit file from activity stream*/
		//From the file activity, click on the link "Edit"
		info("Click Edit icon in activity");
		activity.goToEditFromContentActivity(file);
		assert getValue(cTemplate.ELEMENT_WEBCONTENT_NAME_TEXTBOX).equalsIgnoreCase(file);
		activity.backToHomePageFromEditContentScreen();
				
		/*Clear data*/
		info ("-- Clear data --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementfile);	
	}
	
	/**
	 * == Remove the File activity after deleting a file in Content explorer ==
	 * Test case ID: 77000
	 * Step 1: Create file activity
	 * Step 2: Delete File
	 */
	@Test
	public void test06_RemoveTheFileActivityAfterDeletingAFileInContentExplorer(){
		//Declare variable
		String file = "KS_Wiki_Attachment_pdffile.pdf";
		By elementfile = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", file));
				
		/*Step 1: Create file activity*/
		//Go to SE
		navToolBar.goToSiteExplorer();
				
		//Upload a file
		ecms.uploadFile("TestData/"+file);
				
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
				
		//- The File activity is displayed in the activity stream
		info("-- A File activity is added to the activity stream --");
		activity.checkInforAfterAddingDocument(file, "", "File", "1 MB", "", "", "", "");
				
		/*Step 2: Delete File*/
		//Delete the file
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementfile);
		
		//Back to the Homepage
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
		
		//- The File activity is removed from the activity stream
		info("-- The File activity is removed from the activity stream --");
		waitForElementNotPresent(activity.ELEMENT_CONTENT_NAME.replace("@{fileName}", file));
	}
	
	/**
	 * == Delete a File activity from intranet activity stream by owner ==
	 * Test case ID: 77027
	 * Step 1: Create file activity
	 * Step 2: See delete activity icon
	 * Step 3: Delete activity
	 */
	@Test
	public void test07_DeleteAFileActivityFromIntranetActivityStreamByOwner(){
		//Declare variable
		String file = "KS_Wiki_Attachment_pdffile.pdf";
		By elementfile = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", file));
				
		/*Step 1: Create file activity*/
		//Go to SE
		navToolBar.goToSiteExplorer();
				
		//Upload a file
		ecms.uploadFile("TestData/"+file);
				
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
				
		//- The File activity is displayed in the activity stream
		info("-- A File activity is added to the activity stream --");
		activity.checkInforAfterAddingDocument(file, "", "File", "1 MB", "", "", "", "");
				
		/*Step 2: See delete activity icon*/
		/*Step 3: Delete activity*/
		activity.deleteActivity(file);
		
		//The File activity is removed from the activity stream
		info("-- The File activity is removed from the activity stream --");
		waitForElementNotPresent(activity.ELEMENT_CONTENT_NAME.replace("@{fileName}", file));
		
		/*Clear data*/
		info ("-- Clear data --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementfile);
		
		//*[contains(text(), 'KS_Wiki_Attachment_pdffile.pdf')]/../../../../../..//*[contains(@id, 'DeleteActivityButton')]
	}
	
	/**
	 * == Displaying of file format (image, pdf, office) in the File activity (2) ==
	 * Test case ID: 77112
	 * Step 1: 	- Connect to Intranet
				- Go to CE and  Upload a file: image, pdf or office document (except personal document drive)
				- Go back intranet home page
		Verify by comparison width heigh and width: The orientation of the file can be portrait or landscape following its original orientation
	 */
	@Test
	public void test08_DisplayingOfFileFormatImagePdfOfficeInTheFileActivity2(){
		//Declare variable
		String file1 = "portrait08.jpg";
		String file2 = "landscape08.jpg";
		By elementfile1 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", file1));
		By elementfile2 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", file2));
				
		//Go to SE
		navToolBar.goToSiteExplorer();
				
		//Upload a file: image, pdf or office document (except personal document drive)
		ecms.uploadFile("TestData/"+file1);
		ecms.uploadFile("TestData/"+file2);
				
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
				
		//- The File activity is displayed in the activity stream
		info("-- A File activity is added to the activity stream --");
		activity.checkInforAfterAddingDocument(file1, "", "File", "", "", "", "", "");
		activity.checkInforAfterAddingDocument(file2, "", "File", "", "", "", "", "");
		
		//- The orientation of the file can be portrait or landscape following its original orientation
		WebElement element = waitForAndGetElement(By.xpath(ELEMENT_GET_URL_IMAGE.replace("${name}", file1)));
		String src = element.getAttribute("src"); 
		driver.navigate().to(src);
		WebElement image = waitForAndGetElement(By.xpath(ELEMENT_GET_URL_IMAGE.replace("${name}", src)));
		int height = image.getSize().getHeight();
		assert(height==300);
		
		driver.navigate().back();
		element = waitForAndGetElement(By.xpath(ELEMENT_GET_URL_IMAGE.replace("${name}", file2)));
		src = element.getAttribute("src"); 
		driver.navigate().to(src);
		image = waitForAndGetElement(By.xpath(ELEMENT_GET_URL_IMAGE.replace("${name}", src)));
		height = image.getSize().getHeight();
		assert(height==89);
		driver.navigate().back();
				
		/*Clear data*/
		info ("-- Clear data --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementfile1);
		cMenu.deleteData(elementfile2);
	}
	
	/**
	 * == Delete a File activity from space activity stream by owner ==
	 * Test case ID: 77325
	 * Step 1: Create file activity
	 * Step 2: See delete activity icon
	 * Step 3: Delete activity
	 */
	@Test
	public void test09_DeleteAFileActivityFromSpaceActivityStreamByOwner(){
		//Declare variable
		String file = "KS_Wiki_Attachment_pdffile.pdf";
		String spacename = "Space09";
		String spacedesc = "Description Of Space09";
				
		/*Step 1: Create file activity*/
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spacename, spacedesc);
				
		//Open Documents in this space
		waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		click(magMember.ELEMENT_DOCUMENTS_TAB);
					
		//Upload a file
		ecms.uploadFile("TestData/"+file);
				
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
				
		//- The File activity is displayed in the activity stream
		info("-- A File activity is added to the activity stream --");
		activity.checkInforAfterAddingDocument(file, "", "File", "1 MB", "", "", "", "");
				
		/*Step 2: See delete activity icon*/
		/*Step 3: Delete activity*/
		activity.deleteActivity(file);
		
		//The File activity is removed from the activity stream
		info("-- The File activity is removed from the activity stream --");
		waitForElementNotPresent(activity.ELEMENT_CONTENT_NAME.replace("@{fileName}", file));
		
		/*Clear data*/
		info ("-- Clear data --");
		magMember.goToMySpacePage();
		magMember.deleteSpace(spacename);
	}
	
	/**
	 * == Remove the File activity after deleting a file in Space document ==
	 * Test case ID: 77326
	 * Step 1: Create file activity
	 * Step 2: Delete File
	 */
	@Test
	public void test10_RemoveTheFileActivityAfterDeletingAFileInSpaceDocument(){
		//Declare variable
		String file = "KS_Wiki_Attachment_pdffile.pdf";
		By elementfile = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", file));
		String spacename = "Space10";
		String spacedesc = "Description Of Space10";
				
		/*Step 1: Create file activity*/
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spacename, spacedesc);
				
		//Open Documents in this space
		waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		click(magMember.ELEMENT_DOCUMENTS_TAB);
					
		//Upload a file
		ecms.uploadFile("TestData/"+file);
				
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
				
		//- The File activity is displayed in the activity stream
		info("-- A File activity is added to the activity stream --");
		activity.checkInforAfterAddingDocument(file, "", "File", "1 MB", "", "", "", "");
				
		/*Step 2: Delete File*/
		//Delete file from space
		magMember.goToMySpacePage();
		magMember.doAction("Edit", spacename);
		waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		click(magMember.ELEMENT_DOCUMENTS_TAB);
		cMenu.deleteData(elementfile);
		
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
		
		//The File activity is removed from the activity stream
		info("-- The File activity is removed from the activity stream --");
		waitForElementNotPresent(activity.ELEMENT_CONTENT_NAME.replace("@{fileName}", file));
		
		/*Clear data*/
		info ("-- Clear data --");
		magMember.goToMySpacePage();
		magMember.deleteSpace(spacename);
	}
	
	/**
	 * == Add File activity after uploading a file in CE ==
	 * Test case ID: 77327
	 * Step 1: - Go to Sites explorer (except personal drive)
				- Upload a file
				- Back to the Home page
	 */
	@Test
	public void test11_AddFileActivityAfterUploadingAFileInCE(){
		//Declare variable
		String file = "KS_Wiki_Attachment_pdffile.pdf";
		By elementfile = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", file));
						
		//Go to SE
		navToolBar.goToSiteExplorer();
					
		//Upload a file: image, pdf or office document (except personal document drive)
		ecms.uploadFile("TestData/"+file);
						
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
						
		//- The File activity is displayed in the activity stream
		info("-- A File activity is added to the activity stream --");
		activity.checkInforAfterAddingDocument(file, "", "File", "1 MB", "", "", "", "");
						
		/*Clear data*/
		info ("-- Clear data --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementfile);
	}
	
	/**
	 * == Edit a file from the File activity from space ==
	 * Test case ID: 77330
	 * Step 1: Create file activity
	 * Step 2: Edit file from activity stream
	 */
	@Test
	public void test12_EditAFileFromTheFileActivityFromSpace(){
		//Declare variable
		String file = "KS_Wiki_Attachment_pdffile.pdf";
		String spacename = "Space12";
		String spacedesc = "Description Of Space12";
				
		/*Step 1: Create file activity*/
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spacename, spacedesc);
				
		//Open Documents in this space
		waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		click(magMember.ELEMENT_DOCUMENTS_TAB);
					
		//Upload a file
		ecms.uploadFile("TestData/"+file);
				
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();
				
		//- The File activity is displayed in the activity stream
		info("-- A File activity is added to the activity stream --");
		activity.checkInforAfterAddingDocument(file, "", "File", "1 MB", "", "", "", "");
				
		/*Step 2: Edit file from activity stream*/
		//From the file activity, click on the link "Edit"
		info("Click Edit icon in activity");
		activity.goToEditFromContentActivity(file);
		assert getValue(cTemplate.ELEMENT_WEBCONTENT_NAME_TEXTBOX).equalsIgnoreCase(file);
		activity.backToHomePageFromEditContentScreen();
				
		/*Clear data*/
		info ("-- Clear data --");
		magMember.goToMySpacePage();
		magMember.deleteSpace(spacename);
	}
}
