package org.exoplatform.selenium.platform.ecms.sniff.siteexplorer;

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
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date: 24/05/2013
 * @author lientm
 */
public class ECMS_SE_PublicActivity extends PlatformBase {
	ManageAccount magAcc;
	NavigationToolbar navTool;
	ActionBar actBar;
	ContentTemplate temp;
	Button button;
	ContextMenu cMenu;
	HomePageActivity activity;
	EcmsBase ecms;
	SitesExplorer siteExp;
	NavigationToolbar navToolBar;

	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver,this.plfVersion);
		navTool = new NavigationToolbar(driver,this.plfVersion);
		actBar = new ActionBar(driver,this.plfVersion);
		temp = new ContentTemplate(driver,this.plfVersion);
		button = new Button(driver,this.plfVersion);
		cMenu = new ContextMenu(driver,this.plfVersion);
		activity = new HomePageActivity(driver,this.plfVersion);
		ecms = new EcmsBase(driver,this.plfVersion);
		siteExp = new SitesExplorer(driver,this.plfVersion);
		navToolBar = new NavigationToolbar(driver,this.plfVersion);
		
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	public void createWebContentThenCheckActivity(String name, String sum){
		navTool.goToSiteExplorer();
		
		info("Add new web content ");
		actBar.goToAddNewContent();
		temp.createNewWebContent(name, "", "", sum, "", "", true);
		navTool.goToHomePage(); 					  
		activity.checkInforAfterAddingDocument(name, "uiIcon64x64Templateexo_webContent", "Web Content", "", sum, "0", "", "Draft");
	}
	
	public void uploadFileThenCheckActivity(String fileUpload, String size){
		navToolBar.goToSiteExplorer();
		
		info("Upload new file");
		ecms.uploadFile("TestData/" + fileUpload);
		
		info("Check activity");
		navTool.goToHomePage();
		activity.checkInforAfterAddingDocument(fileUpload, "", "File", size, "", "", "", "");
	}
	
	/**CaseID: 74819 - Check intranet homepage after adding a File content
	 * 
	 */
	@Test
	public void test01_CheckIntranetHomepage_AfterAddingFileDocument(){
		String fileName = "Public_activity_file_document_01";
		String fileContent = "Public_activity_file_document_content_01";
		String desc = "Public_activity_file_document_description_01";
		By elementFile = By.linkText(fileName);
				
		navTool.goToSiteExplorer();
		
		info("Add new file");
		actBar.goToAddNewContent();
		temp.createNewFullFile(fileName, fileContent, fileName, desc, "", "");
		
		info("Make version for this file document");
		cMenu.contextMenuAction(elementFile, cMenu.ELEMENT_MENU_CHECKIN);
		cMenu.contextMenuAction(elementFile, cMenu.ELEMENT_MENU_CHECKOUT);
		
		info("Check activity info is displayed in home page");
		navTool.goToHomePage();
		if(this.plfVersion.equalsIgnoreCase("4.0"))
			activity.checkInforAfterAddingDocument(fileName, "uiIcon64x64FileDefault uiIcon64x64nt_file uiIcon64x64texthtml", "File", "", "", "1", desc, "");
		else //(this.plfVersion.equalsIgnoreCase("4.1"))
			activity.checkInforAfterAddingDocument(fileName, "uiIcon64x64FileHtml uiIcon64x64nt_file", "File", "", "", "1", desc, "");
		//delete data
		navTool.goToSiteExplorer();
		cMenu.deleteData(elementFile);
	}
	
	/**CaseId: 74820 -> Check intranet homepage after adding FreeLayout Web Content
	 * ERROR: Refer ECMS-5342: Content summary in content activity display wrong
	 */
	@Test(groups="error")
	public void test02_CheckIntranetHomePage_afterAddingWebContent(){
		String name = "Public_activity_web_content_02";
		String sum = "line1/line2/line3/line4/line5";
		By elementWeb = By.linkText(name);
		
		navTool.goToSiteExplorer();
		
		info("Add new web content ");
		actBar.goToAddNewContent();
		temp.createNewWebContent(name, "", "", sum, "", "", true);
		
		info("Make version for this file document");
		cMenu.contextMenuAction(elementWeb, cMenu.ELEMENT_MENU_CHECKIN);
		cMenu.contextMenuAction(elementWeb, cMenu.ELEMENT_MENU_CHECKOUT);
		
		info("Check activity in Home page");
		navTool.goToHomePage();
		activity.checkInforAfterAddingDocument(name, "uiIcon64x64Templateexo_webContent", "Web Content", "", sum, "1", "", "Draft");
		
		//delete data
		navTool.goToSiteExplorer();
		cMenu.deleteData(elementWeb);
	}
	
	/** CaseId: 74821 -> Check intranet homepage after adding Product content
	 * ERROR: Refer ECMS-5342: Content summary in content activity display wrong
	 */
	@Test (groups="error")
	public void test03_CheckIntranetHomePage_AfterAddingProduct(){
		String name = "Public_activity_product_03";
		String sum = "line1/line2/line3/line4/line5";
		By elementProduct = By.linkText(name);
		
		navTool.goToSiteExplorer();
		
		info("Add new web content ");
		actBar.goToAddNewContent();
		temp.createFullNewProduct(name, "", sum, "", "", true);
		click(By.linkText("acme"));
		
		info("Make version for this file document");
		cMenu.contextMenuAction(elementProduct, cMenu.ELEMENT_MENU_CHECKIN);
		cMenu.contextMenuAction(elementProduct, cMenu.ELEMENT_MENU_CHECKOUT);
		
		info("Check activity in Home page");
		navTool.goToHomePage();
		activity.checkInforAfterAddingDocument(name, "uiIcon64x64Templateacme_product", "Product", "", sum, "1", "", "Draft");
		
		//delete data
		navTool.goToSiteExplorer();
		cMenu.deleteDocument(elementProduct);
		waitForElementNotPresent(elementProduct);
	}
	
	/**CaseId: 74822 -> Check intranet homepage after Editing title of content
	 * 
	 */
	@Test
	public void test04_CheckIntranetHomePage_AfterEditingTitleOfContent(){
		String name = "Public_activity_file_document_name_04";
		String sum = "Public_activity_file_document_content_04";
		String title = "Public_activity_file_document_title_04";
		String newTitle = "Public_activity_file_document_title_04_Edit";
		By elementFile = By.linkText(name);
		
		navTool.goToSiteExplorer();
		
		info("Add new web content ");
		actBar.goToAddNewContent();
		temp.createNewFile(name, sum, title);
		navTool.goToHomePage();
		if(this.plfVersion.equalsIgnoreCase("4.0"))
			activity.checkInforAfterAddingDocument(name, "uiIcon64x64FileDefault uiIcon64x64nt_file uiIcon64x64texthtml", "File", "", "", "", "", "");
		else// if(this.plfVersion.equalsIgnoreCase("4.1"))
			activity.checkInforAfterAddingDocument(name, "uiIcon64x64FileHtml uiIcon64x64nt_file", "File", "", "", "", "", "");
		
		info("Edit title of content");
		navTool.goToSiteExplorer();
		temp.editFile(name, newTitle, "");
		
		info("Check comment in activity");
		navTool.goToHomePage();
		activity.checkTitleAfterEditing(name, newTitle);
		
		//delete data
		navTool.goToSiteExplorer();
		cMenu.deleteData(elementFile);
	}
	
	/**CaseId: 74824 -> Check intranet homepage after adding tag to a content
	 * 
	 */
	@Test
	public void test05_CheckIntranetHomePage_AfterAddingTagToContent(){
		String name = "Public_activity_web_content_05";
		String sum = "line1/line2/line3";
		By elementWeb = By.linkText(name);
		String tag1[] = {"Activity_Tag1"};
		String tag2[] = {"Activity_Tag2", "Activity_Tag3"};
		String tag[] = {tag1[0], tag2[0], tag2[1]};
		
		createWebContentThenCheckActivity(name, sum);
		
		info("Add a tag for content");
		navTool.goToSiteExplorer();
		ecms.goToNode(elementWeb);
		siteExp.addTagForNode(tag1);
		
		info("Check new comment when add 1 tag for content in activity");
		navTool.goToHomePage();
		activity.checkTagAfterAddingToContent(name, tag1[0], 1);
		
		info("Add 2 tags for content");
		navTool.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForAndGetElement(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		ecms.goToNode(elementWeb);
		siteExp.addTagForNode(tag2);
		
		info("Check new comment when add 2 tags for content in activity");
		navTool.goToHomePage();
		activity.checkTagAfterAddingToContent(name, tag2[0], 1);
		activity.checkTagAfterAddingToContent(name, tag2[1], 1);
		
		//delete data
		navTool.goToSiteExplorer();
		siteExp.goToEditTag();
		siteExp.deleteTag(tag);
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForAndGetElement(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		cMenu.deleteData(elementWeb);		
	}
	
	/**CaseId: 74825 -> Check intranet homepage after deleting a content
	 * 
	 */
	@Test
	public void test06_CheckIntranetHomepage_AfterDeletingContent(){
		String name = "Public_activity_web_content_06";
		String sum = "line1/line2/line3";
		By elementWeb = By.linkText(name);
		
		createWebContentThenCheckActivity(name, sum);
		
		info("Delete web content -> delete activity");
		navTool.goToSiteExplorer();
		cMenu.deleteData(elementWeb);
		navTool.goToHomePage();
		waitForElementNotPresent(activity.ELEMENT_CONTENT_NAME.replace("@{fileName}", name));
	}
	
	/**CaseId: 74826 -> Check intranet homepage after publishing a content
	 * 
	 */
	@Test
	public void test07_CheckIntranetHomePage_AfterPublishingContent(){
		String name = "Public_activity_web_content_07";
		String sum = "line1/line2/line3";
		By elementWeb = By.linkText(name);
		
		createWebContentThenCheckActivity(name, sum);
		
		info("Publish web content");
		navTool.goToSiteExplorer();
		ecms.goToNode(elementWeb);
		actBar.publishDocument();
		
		info("Check activity after publish content");
		navTool.goToHomePage();
		activity.checkInforAfterAddingDocument(name, "uiIcon64x64Templateexo_webContent", "Web Content", "", sum, "1", "", "Published");
		activity.checkStatusAfterPublishAContent(name);
		
		navTool.goToSiteExplorer();
		cMenu.deleteData(elementWeb);
	}
	
	/**CaseId: 74828 -> Check intranet homepage after Uploading a file
	 * 
	 */
	@Test
	public void test08_CheckIntranetHomePage_AfterUploadingFile(){
		String fileUpload = "Sniff_public_activity_08.jpg";
		By elementUploadFile = By.linkText(fileUpload);
		
		uploadFileThenCheckActivity(fileUpload, "103 KB");
		
		//delete data
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementUploadFile);
	}
	
	/**CaseId: 47829 -> Check intranet homepage after adding a category to an uploaded file
	 * 
	 */
	@Test
	public void test09_CheckIntranetHomePage_AfterAddingCategoryToUploadedFile(){
		String fileUpload = "Sniff_public_activity_09.jpg";
		By elementUploadFile = By.linkText(fileUpload);
		String category1 = "Defense";
		String category2 = "Movement";
		String category3 = "Natural Elements";
		
		uploadFileThenCheckActivity(fileUpload, "103 KB");
		
		info("Add a category for uploaded file");
		navToolBar.goToSiteExplorer();
		ecms.goToNode(elementUploadFile);
		actBar.addCategoryForNode("powers", false, "", category1);
		
		info("Check activity after adding a category");
		navTool.goToHomePage();
		activity.checkCategoryAfterAddingToContent(fileUpload, category1);
		
		info("Add 2 category for uploaded file");
		navToolBar.goToSiteExplorer();
		actBar.addCategoryForNode("powers", false, "", category2);
		actBar.addCategoryForNode("powers", false, "", category3);
		
		info("Check activity after adding 2 categories");
		navTool.goToHomePage();
		activity.checkCategoryAfterAddingToContent(fileUpload, category2);
		activity.checkCategoryAfterAddingToContent(fileUpload, category3);
		
		//delete data
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementUploadFile);
	}
	
	/**CaseId: 74830 -> Check intranet homepage after editing Title of an uploaded file
	 * 
	 */
	@Test
	public void test10_CheckIntranetHomePage_AfterEditingTitleOfUploadedFile(){
		String fileUpload = "Sniff_public_activity_10.jpg";
		By elementUploadFile = By.linkText(fileUpload);
		String title = "Title of uploaded file";
		
		uploadFileThenCheckActivity(fileUpload, "103 KB");
		
		info("Edit title of uploaded file");
		navToolBar.goToSiteExplorer();
		ecms.goToNode(elementUploadFile);
		actBar.goToEditDocument(fileUpload);
		ecms.editUploadedFile(fileUpload, "", title, "", "", "");
		
		info("Check activity after edit title of file");
		navTool.goToHomePage();
		activity.checkTitleAfterEditing(fileUpload, title);
		
		//delete data
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementUploadFile);
	}
	
	/**CaseId: 74831 -> Check intranet homepage after deleting an uploaded file
	 * 
	 */
	@Test
	public void test11_CheckIntranetHomePage_AfterDeletingUploadFile(){
		String fileUpload = "Sniff_public_activity_11.jpg";
		By elementUploadFile = By.linkText(fileUpload);
		
		uploadFileThenCheckActivity(fileUpload, "103 KB");
		
		info("Delete uploaded file");
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementUploadFile);
		
		info("Check remove activity after deleting uploaded file");
		navTool.goToHomePage();
		waitForElementNotPresent(activity.ELEMENT_CONTENT_NAME.replace("@{fileName}", fileUpload));
	}
	
	/**CaseID: 75294 -> Edit a content from the Content activity
	 * 
	 */
	@Test
	public void test12_EditAContentFromContentActivity(){
		String name = "Public_activity_web_content_11";
		String sum = "line1/line2/line3";
		By elementWeb = By.linkText(name);
		
		createWebContentThenCheckActivity(name, sum);
		
		info("Click Edit icon in activity");
		activity.goToEditFromContentActivity(name);
		assert getValue(temp.ELEMENT_WEBCONTENT_NAME_TEXTBOX).equalsIgnoreCase(name);
		activity.backToHomePageFromEditContentScreen();
		
		navTool.goToSiteExplorer();
		cMenu.deleteData(elementWeb);
	}
	
	/**CaseId: 75295 -> View a content from the Content activity
	 * 
	 */
	@Test
	public void test13_ViewContentFromContentActivity(){
		String name = "publicactivitywebcontent12";
		String cont = "Public_activity_content_12";
		String sum = "line1/line2/line3/line4/line5";
		By elementWeb = By.linkText(name);
		
		navTool.goToSiteExplorer();
		
		info("Add new web content ");
		actBar.goToAddNewContent();
		temp.createNewWebContent(name, cont, "", sum, "", "", true);
		
		info("Click View icon in activity");
		navTool.goToHomePage();
		activity.goToViewFromContentActivity(name);
		waitForTextPresent(cont);
		activity.backToHomePageFromEditContentScreen();
		
		navTool.goToSiteExplorer();
		actBar.chooseDrive(By.xpath("//*[@data-original-title='Sites Management']"));
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForAndGetElement(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		cMenu.deleteData(elementWeb);		
	}
	
	/**CaseId: 75296 -> View a file from the File activity
	 * 
	 */
	@Test
	public void test14_ViewFileFromFileActivity(){
		String fileUpload = "Sniff_public_activity_14.jpg";
		By elementUploadFile = By.linkText(fileUpload);
		
		uploadFileThenCheckActivity(fileUpload, "103 KB");
		
		info("Go to view from file activity");
		activity.goToViewFromFileActivity(fileUpload);
		
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(elementUploadFile);
	}
	
	/**CaseId: 75297 -> Edit a file from the File activity
	 * 
	 */
	@Test
	public void test15_EditFileFormFileActivity(){
		String fileUpload = "Sniff_public_activity_15.jpg";
		By elementUploadFile = By.linkText(fileUpload);
		
		uploadFileThenCheckActivity(fileUpload, "103 KB");
		
		info("Go to edit from file activity");
		activity.goToEditFromContentActivity(fileUpload);
		activity.backToHomePageFromEditContentScreen();
		
		navTool.goToSiteExplorer();
		cMenu.deleteData(elementUploadFile);
	}
	
	/**CaseId: 75298 -> Update the File activity after moving a file
	 * 
	 */
	@Test
	public void test16_CheckIntranetHomePage_AfterMovingFile(){
		String fileUpload = "landscape08.jpg";
		By elementUploadFile = By.linkText(fileUpload);
		String folder = "contentfolder16";
		By elementFolder = By.linkText(folder);
		
		navToolBar.goToSiteExplorer();
		temp.createNewFolder(folder, folderType.Content);
		
		info("Upload new file");
		ecms.uploadFile("TestData/" + fileUpload);
		
		info("Move file to folder");
		cMenu.cutAndPasteNode(elementUploadFile, elementFolder);
		waitForElementNotPresent(elementUploadFile);
		ecms.goToNode(elementFolder);
		waitForAndGetElement(elementUploadFile);
		
		info("Check activity after moving a file");
		navTool.goToHomePage();
		activity.checkContentAfterMovingANode(fileUpload, "/sites/" + folder + "/", false);
		
		navTool.goToSiteExplorer();
		cMenu.deleteData(elementFolder);	
	}
	
	/**CaseId: 75299 -> Update Content activity after moving a content
	 * ERROR: Refer ECMS-5342: Content summary in content activity display wrong
	 */
	@Test
	public void test17_CheckIntranetHomePage_AfterMovingContent(){
		String name = "publicactivitywebcontent17";
		String sum = "line1/line2/line3";
		By elementWeb = By.linkText(name);
		String folder = "contentfolder17";
		By elementFolder = By.linkText(folder);
		
		navTool.goToSiteExplorer();
		temp.createNewFolder(folder, folderType.Content);
		
		createWebContentThenCheckActivity(name, sum);
		
		info("Move content to folder");
		navTool.goToSiteExplorer();
		cMenu.cutAndPasteNode(elementWeb, elementFolder);
		waitForElementNotPresent(cMenu.ELEMENT_FILE_TITLE.replace("${${titleOfFile}}", name));
		ecms.goToNode(elementFolder);
		waitForAndGetElement(elementWeb);
		
		info("Check activity after moving a file");
		navTool.goToHomePage();
		activity.checkContentAfterMovingANode(name, "/sites/" + folder + "/", true);
		
		navTool.goToSiteExplorer();
		cMenu.deleteData(elementFolder);
	}
}