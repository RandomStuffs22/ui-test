package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.filemanagementview;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * @author: HaKT
 * @date: 04/09/2013
 */
public class ECMS_SE_FileManagementView_Actions_CreateEditDeleteNode extends PlatformBase {
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	ContentTemplate conTemp;
	EcmsBase ecms;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		navToolBar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS); 
		ecms = new EcmsBase(driver);
		actBar = new ActionBar(driver);
		conTemp = new ContentTemplate(driver);

		info("Config New Content on action bar for admin view if it is not available on action bar");
		navToolBar.goToPersonalDocuments();

		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
	}
	
	@AfterMethod
	public void afterTest(){
		//		magAcc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}


	/**CaseId: 67416, 78956, 78960
	 * Add Edit Delete a File document
	 */
	@Test
	public void test01_13_15_AddEditDeleteFileDocument(){
		String File_Name="File_Case_67416_ 78956";
		String File_Content="File_Content_67416";
		String File_Content_Edit="Edited File_Content_67416_To_ 78956";

		info("Add Edit Delete a File document");
		actBar.goToAddNewContent();
		conTemp.createNewFile(File_Name, File_Content, "");

		info("Edit File & verify edited content");
		conTemp.editFile(null, "", File_Content_Edit);
		assert driver.getPageSource().contains(File_Content_Edit);
		info("Delete Fle document");
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(File_Name, actionType.DELETE);
	}

	/**CaseId: 78915, 78960
	 * Add/Delete a new folder
	 */
	@Test
	public void test02_15_AddDeleteNewFolder(){

		String Folder_Name = "Folder_Case-78915";
		info("Add/Delete a new folder");
		conTemp.createNewFolder(Folder_Name, folderType.None);

		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", Folder_Name));

		actBar.actionsOnElement(Folder_Name, actionType.DELETE);
	}

	/**CaseId: 78917
	 * Create JS file
	 */
	@Test
	public void test03_AddNewJsFile(){
		String Js_Name = "Js_Case_78917";
		String Js_Data = "alert(\"Hello! I am an alert box!!\");";
		info("Create JS file");
		actBar.goToAddNewContent();
		click(conTemp.ELEMENT_NEW_JS_FILE_LINK);
		conTemp.createNewJsFile(Js_Name, "0",Js_Data);

		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);

		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", Js_Name));

		actBar.actionsOnElement(Js_Name, actionType.DELETE);
	}

	/**CaseId: 78918
	 * Create a web Link
	 */
	@Test
	public void test04_AddNewWebLink(){

		String Web_Link_Name="Web_link_case_78918";
		String URL = "http://google.com.vn";
		info("reate a web Link");
		actBar.goToAddNewContent();

		click(conTemp.ELEMENT_WEBLINK_LINK);

		conTemp.createNewLink(Web_Link_Name, URL);

		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);

		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", Web_Link_Name));

		actBar.actionsOnElement(Web_Link_Name, actionType.DELETE);
	}

	/**CaseId: 78920
	 * Create an Accessible media
	 */
	@Test
	public void test05_AddNewAccessibleMedia(){
		String name = "AccessibleMedia_78920" ;
		String lang = "en";
		info("Create an Accessible media");
		actBar.goToAddNewContent();
		//		Create new accessible media here
		conTemp.createAccessibleMedia(name,lang,name,name,name);
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);

		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", name));
		actBar.actionsOnElement(name, actionType.DELETE);

	}

	/**CaseId: 78922
	 * Create an Announcement
	 */
	@Test
	public void test06_AddNewAnnoucement(){

		String Announcement_Name = "Announcement_Case_78922";
		String Announcement_Summary ="Accounceent_summary_case_78922";
		info("Create an Announcement");
		actBar.goToAddNewContent();
		conTemp.createNewAnnouncement(Announcement_Name, Announcement_Summary);

		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);

		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", Announcement_Name));

		actBar.actionsOnElement(Announcement_Name, actionType.DELETE);
	}

	/**CaseId: 78923
	 * Create a new css file
	 */
	@Test
	public void test07_AddNewCssFile(){

		String CssFile_Name="CSS_File_case_78923";
		String CSS_Data = "p{color: red;}";
		info("Create a new css file");
		actBar.goToAddNewContent();
		click(conTemp.ELEMENT_CSS_FILE_LINK);
		conTemp.createNewCssFile(CssFile_Name, "", CSS_Data);

		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);

		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", CssFile_Name));

		actBar.actionsOnElement(CssFile_Name, actionType.DELETE);
	}

	/**CaseId: 78925
	 * Add new HTML File document
	 */
	@Test
	public void test08_AddNewHtmlFile(){

		String Html_File_Name="HTML_File_case_78925";
		String Html_content= "Content Of HTML file";

		info("Add new HTML File document");
		actBar.goToAddNewContent();
		conTemp.createNewHtmlFile(Html_File_Name,"", Html_content);

		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);

		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", Html_File_Name));

		actBar.actionsOnElement(Html_File_Name, actionType.DELETE);
	}

	/**CaseId: 78926
	 * Add new Illustrated Web Content
	 */
	@Test
	public void test09_AddNewIllustratedWebContent(){

		String Illustrated_WC_Name="Illustrated_WC_case_78926";
		String Illustrated_WC_content= "Content Of Illustrated_WC";
		String path2Image = "TestData/Winter.jpg";

		info("Add new Illustrated Web Content");
		actBar.goToAddNewContent();
		conTemp.createNewIllustratedWebContent(Illustrated_WC_Name, Illustrated_WC_content, path2Image, "", "", "", "");

		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);

		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", Illustrated_WC_Name));

		actBar.actionsOnElement(Illustrated_WC_Name, actionType.DELETE);
	}

	/**CaseId: 78927
	 * Add new Web Content
	 */
	@Test
	public void test10_AddNewWebContent(){

		String WC_Name="WC_case_78927";
		String WC_content= "Content Of WC";
		info("Add new Web Content");
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(WC_Name, WC_content, "", "", "", "");

		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);

		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", WC_Name));

		actBar.actionsOnElement(WC_Name, actionType.DELETE);
	}

	/**CaseId: 78928
	 * Add new Product
	 */
	@Test
	public void test11_AddNewProduct(){
		String Product_Name="Product_case_78928";
		String Product_Summary="Summary of product case78928";
		String Product_Benefit="Benefit of product case78928";
		String Product_Feature="Features of product case78928";

		info("Add new Product");
		actBar.goToAddNewContent();
		conTemp.createFullNewProduct(Product_Name, "", Product_Summary,Product_Benefit , Product_Feature);

		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);

		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", Product_Name));

		actBar.actionsOnElement(Product_Name, actionType.DELETE);
	}

	/**CaseId: 78930
	 * Add new Contact us
	 */
	@Test
	public void test12_AddNewContactUs(){
		String Your_Name="John Smith";
		String Your_Address="Address of case 78930";
		String Your_Email="exoservice@gmail.com";
		String Your_Phone="1234567890";
		String Your_Message="I like this product";
		String ContactUs_Name = getCurrentDate("yyyy.MM.dd");

		info("Add new Contact us");
		actBar.goToAddNewContent();
		conTemp.createNewContactUs(Your_Name, Your_Address, Your_Email, Your_Phone, Your_Message);

		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);

		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", ContactUs_Name));

		actBar.actionsOnElement(ContactUs_Name, actionType.DELETE);

	}

	/**CaseId: 78957
	 *Edit illustrated web content
	 */
	@Test
	public void test14_EditIllustratedWebContent(){
		String Illustrated_WC_Name="Illustrated_WC_case_78957";
		String Illustrated_WC_content= "Content Of Illustrated_WC";
		String path2Image = "TestData/Winter.jpg";
		String Illustrated_WC_Edited_content= "Edited: Content Of Illustrated_WC";

		info("Edit illustrated web content");
		actBar.goToAddNewContent();
		conTemp.createNewIllustratedWebContent(Illustrated_WC_Name, Illustrated_WC_content, path2Image, "", "", "", "");

		conTemp.editIllustratedWebContent(null, Illustrated_WC_Edited_content, "", "", "", "", "");

		waitForTextPresent(Illustrated_WC_Edited_content);
		info("Delete File document");
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(Illustrated_WC_Name, actionType.DELETE);
	}

	/**CaseId: 78960
	 * Delete Uploaded file
	 */
	@Test
	public void test15_DeleteAnUploadedFile(){
		String FILE_PDF_NAME = "ECMS_DMS_SE_Upload_pdffile.pdf";
		info("Delete Uploaded file");
		conTemp.uploadFile("TestData/" + FILE_PDF_NAME);

		actBar.actionsOnElement(FILE_PDF_NAME, actionType.DELETE);		
	}
}