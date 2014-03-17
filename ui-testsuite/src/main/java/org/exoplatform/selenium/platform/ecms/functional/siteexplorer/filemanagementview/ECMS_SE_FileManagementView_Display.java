package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.filemanagementview;


import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.BrowserPreferences;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author: HaKT
 * @date: 29/08/2013
 */
public class ECMS_SE_FileManagementView_Display extends PlatformBase{

	ManageAccount magAc;
	NavigationToolbar navToolBar;
	ActionBar action;
	ContentTemplate contemp;
	ContextMenu conmenu;
	SitesExplorer sExplorer;
	EcmsBase ecmBase;
	BrowserPreferences browserPre;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		navToolBar = new NavigationToolbar(driver);
		magAc = new ManageAccount(driver);
		magAc.signIn("john", DATA_PASS); 

		action = new ActionBar(driver);
		contemp = new ContentTemplate(driver); 
		conmenu = new ContextMenu(driver);
		sExplorer = new SitesExplorer(driver);
		ecmBase = new EcmsBase(driver);
		browserPre = new BrowserPreferences(driver);
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	
	/**
	 * Qmetry ID: 74544
	 * Show "Created on" for a file
	 * Configure New Content on action bar for Admin view
	 * Go to Personal document to create new file
	 * Verify label Created on below file above
	 * Delete this file
	 */
	@Test
	public void test01_ShowCreatedOnForAFile(){

		String DATA_FILE_NAME="File_Case_74544";
		String DATA_FILE_CONTENT="Content of File_Case_74544";

		navToolBar.goToPersonalDocuments();	
		
		action.addItem2ActionBar("addDocument", action.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin"); 
		action.chooseDrive(ecmBase.ELEMENT_PERSONAL_DRIVE); 

		info("Create a file in personal document");
		action.goToAddNewContent();

		contemp.createNewFile(DATA_FILE_NAME, DATA_FILE_CONTENT, null);

		action.chooseDrive(ecmBase.ELEMENT_PERSONAL_DRIVE); 

		info("Verify 'Created on' label");
		//waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CREATEDON_LABEL.replace("${content}",DATA_FILE_NAME)));
		waitForAndGetElement(ecmBase.ELEMENT_VERIFY_DATE_NODE.replace("${namenode}",DATA_FILE_NAME));

		info("Delete file");
		action.actionsOnElement(DATA_FILE_NAME, actionType.DELETE);
	}

	/**
	 * Qmetry ID: 74545
	 * Show "Created on" for a folder
	 * Go to Personal document to create new file
	 * Verify label Created on below folder above
	 * Delete this folder
	 */
	@Test
	public void test02_ShowCreatedOnForAFolder(){
		String DATA_FOLDER_NAME="Folder_Case_74545";

		navToolBar.goToPersonalDocuments();	

		info("Create a folder in personal document");
		contemp.createNewFolder(DATA_FOLDER_NAME, folderType.None);

		info("Verify 'Created on' label");
		//waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CREATEDON_LABEL.replace("${content}", DATA_FOLDER_NAME)));
		waitForAndGetElement(ecmBase.ELEMENT_VERIFY_DATE_NODE.replace("${namenode}", DATA_FOLDER_NAME));
		
		info("Delete folder");
		action.actionsOnElement(DATA_FOLDER_NAME, actionType.DELETE);
	}

	/**
	 * Qmetry ID: 74548
	 * Show "Updated" for a file
	 * Go to Personal document to create new file
	 * Edit this file
	 * Verify label Updated below file above
	 * Delete this file
	 */
	@Test
	public void test03_ShowUpdatedForAFile(){

		String DATA_FILE_NAME="File_Case_74548";
		String DATA_FILE_CONTENT="Content of File_Case_74548";
		String DATA_FILE_NEW_CONTENT="Content of File_Case_74548 edited";

		By VERIFY_UPDATED_LABEL=By.xpath("//*[contains(text(),'"+DATA_FILE_NAME+"')]/../..//p[contains(text(), 'Updated')]");

		navToolBar.goToPersonalDocuments();	

		info("Create a file in personal document");
		action.goToAddNewContent();

		contemp.createNewFile(DATA_FILE_NAME, DATA_FILE_CONTENT, null);

		contemp.editFile(null, "", DATA_FILE_NEW_CONTENT);

		action.chooseDrive(ecmBase.ELEMENT_PERSONAL_DRIVE); 

		info("Verify 'Updated' label");
		waitForAndGetElement(VERIFY_UPDATED_LABEL);

		info("Delete file");
		action.actionsOnElement(DATA_FILE_NAME, actionType.DELETE);
	}

	/**
	 * Qmetry ID: 74550
	 * Displaying in File management view
	 * Go to Personal document
	 * Check displaying: 
	    + list of default folders,
	    + Check font-weight of Name is bold or 700
	    + Check font-weight of remaining part is normal or 400 
	 */
	@Test
	public void test04_DisplayingInFileManagementView(){

		navToolBar.goToPersonalDocuments();	

		info("Verify default folders");
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "Documents")));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "Favorites")));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "Music")));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "Pictures")));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "Public")));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "Videos")));

		info("Check font-weight of Name is bold or 700");
		WebElement element1 = waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "Documents")));
		String name = element1.getCssValue("font-weight");
		info("Font weight of Name:" + name);
		assert name.equalsIgnoreCase("700");

		info("Check font-weight of remaining part is normal or 400");
		WebElement element2 = waitForAndGetElement(By.xpath(ecmBase.ELEMENT_VERIFY_CREATEDON_LABEL.replace("${content}","Documents")));
		//WebElement element2 = waitForAndGetElement(ecmBase.ELEMENT_VERIFY_DATE_NODE.replace("${namenode}", "Documents"));
		String label = element2.getCssValue("font-weight");
		info("Font weight of information under name:" + label);
		assert label.equalsIgnoreCase("400");
	}
	
	/**
	 * Qmetry ID: 74562
	 * ShowChildrenWhenClickArrow
	 * Go to Personal document
	 * Create a web content
	 * Click arrow icon to expand and see child node 
	 */
	@Test
	public void test05_ShowChildrenWhenClickArrow(){
		String Web_Content_Name="WebContent_Case_74562";
		String Web_Content_Cont="Content of WebContent_Case_74562";

		navToolBar.goToPersonalDocuments();	

		info("Create a web content in personal document");
		action.goToAddNewContent();
		contemp.createNewWebContent(Web_Content_Name, Web_Content_Cont, "", "", "", "");

		action.chooseDrive(ecmBase.ELEMENT_PERSONAL_DRIVE); 

		info("Check Webcontent is not expanded");
		waitForElementNotPresent(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "css")));
		waitForElementNotPresent(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "default")));
		waitForElementNotPresent(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "documents")));
		waitForElementNotPresent(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "js")));
		waitForElementNotPresent(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "medias")));

		info("Click arrow icon to view child list");
		//rightClickOnElement(By.xpath(ELEMENT_PERSONAL_DOCUMENT_ARROW_RIGHT.replace("${content}",Web_Content_Name)));
		rightClickOnElement(ecmBase.ELEMENT_ARROW_RIGHT.replace("${nodeName}", Web_Content_Name));

		info("Verify arrow direction is down");
		//waitForAndGetElement(By.xpath(ELEMENT_PERSONAL_DOCUMENT_ARROW_DOWN.replace("${content}",Web_Content_Name)));
		waitForAndGetElement(ecmBase.ELEMENT_ARROW_DOWN.replace("${nodeName}", Web_Content_Name));
		
		info("Verify children list of web content");
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "css")));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "default")));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "documents")));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "js")));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", "medias")));

		info("Delete web content");
		action.actionsOnElement(Web_Content_Name, actionType.DELETE);
	}

	/**
	 * Qmetry ID: 74567
	 * ShowFilePathInAddressBar
	 * Go to Personal document
	 * Create a new file
	 * Verify path bar
	 */
	@Test
	public void test06_ShowFilePathInAddressBar(){
		String DATA_FILE_NAME="File_Case_74567";
		String DATA_FILE_CONTENT="Content of File_Case_74567";

		navToolBar.goToPersonalDocuments();	

		info("Create a file in personal document");
		action.goToAddNewContent();
		contemp.createNewFile(DATA_FILE_NAME, DATA_FILE_CONTENT, null);

		info("Verify file path in address bar");
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_NODE_ADDRESS.replace("${content}",DATA_FILE_NAME)));

		info("Delete file");
		action.chooseDrive(ecmBase.ELEMENT_PERSONAL_DRIVE); 
		action.actionsOnElement(DATA_FILE_NAME, actionType.DELETE);
	}

	/**
	 * Qmetry ID: 74569
	 * ShowFolderPathInAddressBar
	 * Go to Personal document
	 * Create a new folder
	 * Verify path bar
	 */
	@Test
	public void test07_ShowFolderPathInAddressBar(){
		String DATA_FOLDER_NAME="foldercase74569";

		navToolBar.goToPersonalDocuments();	

		info("Create a folder in personal document");
		contemp.createNewFolder(DATA_FOLDER_NAME, folderType.None);

		rightClickOnElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}",DATA_FOLDER_NAME)));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_NODE_ADDRESS.replace("${content}", DATA_FOLDER_NAME)));

		info("Delete folder");
		action.chooseDrive(ecmBase.ELEMENT_PERSONAL_DRIVE);
		action.actionsOnElement(DATA_FOLDER_NAME, actionType.DELETE);
	}

	/**
	 * Qmetry ID: 74580
	 * ShowBreadcrumb
	 * Go to Personal document
	 * Create a new folder
	 * Open it
	 * Verify breadcrumb
	 */
	@Test
	public void test08_ShowBreadcrumb(){
		String DATA_FOLDER_NAME="Folder_Case_74580";
		By Folder_Breadcrumb=By.xpath("//div[@class='breadcrumbLink']//a[@data-original-title='Personal Documents']/..//a[@data-original-title='"+DATA_FOLDER_NAME+"']");

		navToolBar.goToPersonalDocuments();	
		info("Verify breadcrumb of personal document");
		waitForAndGetElement(By.xpath(ecmBase.PERSONAL_DRIVE_BREADCRUMB));

		info("Create a folder in personal document");
		contemp.createNewFolder(DATA_FOLDER_NAME, folderType.None);

		info("Verify breadcrumb of folder in personal document");
		rightClickOnElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}",DATA_FOLDER_NAME)));
		waitForAndGetElement(Folder_Breadcrumb);

		info("Delete folder");
		action.chooseDrive(ecmBase.ELEMENT_PERSONAL_DRIVE);
		action.actionsOnElement(DATA_FOLDER_NAME, actionType.DELETE);
	}

	/**
	 * Qmetry ID: 74583
	 * ClickMoreToSeeChildrenFolder
	 * Go to Personal document
	 * Set up browsing preference to display 5 items per page
	 * Create a new folder
	 * Create more than 5 folders inside it
	 * Open Folder, Click More link
	 */
	@Test
	public void test09_ClickMoreToSeeChildrenFolder(){
		String Parent_Folder_Name="Parent_Folder_Case_74583";
		String Child_Folder_1="Child_Folder_1";
		String Child_Folder_2="Child_Folder_2";
		String Child_Folder_3="Child_Folder_3";
		String Child_Folder_4="Child_Folder_4";
		String Child_Folder_5="Child_Folder_5";
		String Child_Folder_6="Child_Folder_6";

		navToolBar.goToPersonalDocuments();	

		info("Set Preference 5 nodes per page");
		browserPre.setUpPreferenceOption(null, "5");

		info("Create a parent folder in personal document");
		contemp.createNewFolder(Parent_Folder_Name, folderType.None);
		rightClickOnElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", Parent_Folder_Name)));

		info("Create 6 child folders");
		contemp.createNewFolder(Child_Folder_1, folderType.None);

		contemp.createNewFolder(Child_Folder_2, folderType.None);

		contemp.createNewFolder(Child_Folder_3, folderType.None);

		contemp.createNewFolder(Child_Folder_4, folderType.None);

		contemp.createNewFolder(Child_Folder_5, folderType.None);

		contemp.createNewFolder(Child_Folder_6, folderType.None, false);

		action.chooseDrive(ecmBase.ELEMENT_PERSONAL_DRIVE);
		//rightClickOnElement(By.xpath(ELEMENT_PERSONAL_DOCUMENT_ARROW_RIGHT.replace("${content}", Parent_Folder_Name)));
		rightClickOnElement(ecmBase.ELEMENT_ARROW_RIGHT.replace("${nodeName}", Parent_Folder_Name));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", Child_Folder_1)));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", Child_Folder_2)));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", Child_Folder_3)));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", Child_Folder_4)));
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", Child_Folder_5)));
		waitForElementNotPresent(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", Child_Folder_6)));

		click(ecmBase.ELEMENT_VIEW_MORE_BUTTON);
		waitForAndGetElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", Child_Folder_6)));

		info("Delete folder");
		action.actionsOnElement(Parent_Folder_Name, actionType.DELETE);
	}

	/**
	 * Qmetry ID: 74584
	 * ShowSizeOfFileDocument
	 * Go to Personal document
	 * Create a new folder
	 * Open it, create some files in it
	 * Verify size of files
	 */
	@Test
	public void test10_ShowSizeOfFileDocument(){
		String DATA_FOLDER_NAME="Folder_Case_74584";

		String DATA_FILE_NAME="File_Case_74584";
		String DATA_FILE_CONTENT="Content of File_Case_74584";

		By Verify_File_Size = By.xpath("//*[@class='nodeName' and contains(text(),'"+DATA_FILE_NAME+"')]/../..//p[contains(text(),'1 KB')]");

		navToolBar.goToPersonalDocuments();	

		info("Create a folder in personal document");
		contemp.createNewFolder(DATA_FOLDER_NAME, folderType.None);
		info("Open folder");
		rightClickOnElement(By.xpath(ecmBase.ELEMENT_PERSONAL_DOCUMENT_NODE.replace("${content}", DATA_FOLDER_NAME)));

		info("Create a file");
		action.goToAddNewContent();
		contemp.createNewFile(DATA_FILE_NAME, DATA_FILE_CONTENT, null);

		action.chooseDrive(ecmBase.ELEMENT_PERSONAL_DRIVE); 

		info("Click arrow icon to view contentlist");
		//rightClickOnElement(By.xpath(ELEMENT_PERSONAL_DOCUMENT_ARROW_RIGHT.replace("${content}", DATA_FOLDER_NAME)));
		rightClickOnElement(ecmBase.ELEMENT_ARROW_RIGHT.replace("${nodeName}", DATA_FOLDER_NAME));
		waitForAndGetElement(Verify_File_Size);

		info("Delete folder");
		action.actionsOnElement(DATA_FOLDER_NAME, actionType.DELETE);
	}
}
