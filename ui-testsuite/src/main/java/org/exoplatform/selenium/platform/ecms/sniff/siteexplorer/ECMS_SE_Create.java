package org.exoplatform.selenium.platform.ecms.sniff.siteexplorer;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.exoplatform.selenium.TestLogger.info;

/**
 * @date 20/05/2013
 * @author lientm
 *
 */
public class ECMS_SE_Create extends PlatformBase {
	ContentTemplate template;
	ActionBar actBar;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ContextMenu cMenu;
	EcmsBase ecms;
	ContentTemplate cTemp;
	Button button;
    ManageMember magMember;
	
	public final String file1 = "ECMS_Admin_ManageCategories_Display.jpg";
	public final String file2 = "ECMS_Admin_SendMailScript_Template.txt";
	public final String ELEMENT_LINK_IN_CE = "//*[@id='UIDocumentWorkspace']//img[contains(@src, '${file}')]";
	
	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		template = new ContentTemplate(driver,this.plfVersion);
		actBar = new ActionBar(driver,this.plfVersion);
		magAcc = new ManageAccount(driver,this.plfVersion);
		navToolBar = new NavigationToolbar(driver,this.plfVersion);
		cMenu = new ContextMenu(driver,this.plfVersion);
		cTemp = new ContentTemplate(driver,this.plfVersion);
		button = new Button(driver,this.plfVersion);
		ecms = new EcmsBase(driver,this.plfVersion);
		magAcc.signIn(DATA_USER1, DATA_PASS);
        magMember = new ManageMember(driver,this.plfVersion);
	}

	@AfterMethod
	public void afterMethods() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 67855 + 67856 + 65835
	 * Create + edit + delete (undo) file document
	 */
	@Test
	public void test17_18_19_CreateEditDeleteFileDocument(){
		String file = "File_document_name";
		String content = "File_document_content";
		String title = "File_docment_title";
		By elementFile = By.linkText(file);
		
		String contentEdit = "File_document_content_edit";
		String titleEdit = "File_docment_title_edit";
		
		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();
		
		info("Create new file document");
		template.createNewFile(file, content, title);
		
		info("Edit file document");
		template.editFile(file, titleEdit, contentEdit);
		
		info("Delete file document");
		cMenu.deleteData(elementFile);
		
		info("Restore data");
		actBar.undoDeletion(file);
		waitForAndGetElement(elementFile);
		
		info("Delete file after restore");
		cMenu.deleteData(elementFile);
	}
	
	/**CaseId: 65836 + 67857 + 67858
	 * create - edit - delete a web content
	 */
	@Test
	public void test20_21_22_CreateEditDeleteWebContent(){
		String name = "name65836";
		String content = "content65836";
		By elementWeb = By.linkText(name);
		String contentEdit = "Web_content_edit";
		
		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();
		
		info("Create new web content");
		template.createNewWebContent(name, content, "", "", "", "");
		
		info("Edit web content");
		template.editWebContent(name, contentEdit, "", "", "", "");
		
		info("Delete web content");
		cMenu.deleteData(elementWeb);
		
		info("Restore data");
		actBar.undoDeletion(name);
		waitForAndGetElement(elementWeb);
		
		info("Delete web content after restore");
		cMenu.deleteData(elementWeb);
	}
	
	
	public void uploadMultiFileSerial(String...file){
		
		if (file.length > 0){
			if (isTextNotPresent("Upload")){
				click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			}
			((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; " +
					"arguments[0].style.width = '1px'; arguments[0].style.opacity = 1", waitForAndGetElement(ecms.ELEMENT_UPLOAD_LINK, DEFAULT_TIMEOUT, 1, 2));
			for (int i = 0; i < file.length; i ++){
				type(ecms.ELEMENT_UPLOAD_LINK, Utils.getAbsoluteFilePath("TestData/" + file[i]), false);
			}
			switchToParentWindow();
			String links[] = file[0].split("/");
			int length = links.length;
			waitForAndGetElement(By.xpath("//*[contains(text(),'" + links[length-1]+ "')]"));
			waitForElementNotPresent(By.xpath("//*[@class='loaddingPercent pull-right']"));
		}
	}

	/**CaseId: 65877 + 67860: Upload files then delete in site explorer
	 */
	@Test(groups="pending")
	public void test23_24_UploadDeleteFileInSiteExplorer(){	
		info("Upload files in site explorer");
		navToolBar.goToSiteExplorer();
		uploadMultiFileSerial(file1, file2);
		
		info("Delete uploaded files");
		cMenu.deleteData(By.linkText(file1));
		cMenu.deleteData(By.linkText(file2));
	}
	
	/**CaseId: 75241 + 75244: Upload then delete uploaded files in persional document
	 */
	@Test(groups="pending")
	public void test23_24_UploadDeleteFileInPersonalDocument(){
		info("Upload files in personal document");
		navToolBar.goToPersonalDocuments();
		uploadMultiFileSerial(file1, file2);
		
		info("Delete Uploaded files");
		//actBar.deleteDataInAdminView(file1);
		//actBar.deleteDataInAdminView(file2);
		actBar.actionsOnElement(file1, actionType.DELETE);
		actBar.actionsOnElement(file2, actionType.DELETE);
	}
	
	/**CaseId: 75242 : Upload then delete uploaded files in space document
	 * pending: do not merge the functions to create space
	 */
	
	/**CaseId: 65831 + 67859
	 * Create - delete a content folder
	 */
	@Test
	public void test25_26_CreateDeleteContentFolder(){
		String folder = "Content_folder";
		By elementFolder = By.linkText(folder);
		
		info("Create new content folder");
		navToolBar.goToSiteExplorer();
		template.createNewFolder(folder, folderType.Content);
		
		info("Delete content folder");
		cMenu.deleteData(elementFolder);
		
		info("Restore content folder");
		actBar.undoDeletion(folder);
		waitForAndGetElement(elementFolder);
		
		info("Delete content folder after restored");
		cMenu.deleteData(elementFolder);
	}
	
	/** CaseId: 65846 
	 * Create content link when add new a document that contains FCK Editor
	 * Pending: following issue ECMS-5283
	 */
	//@Test
	public void test27_CheckFCKEditor(){
		//String file1 = "KS_Wiki_Attachment_AllMyLove.mp3";
		String file2 = "Winter.jpg";
		
		String webContent = "Web_content_FCKEditor";
		By elementWeb = By.linkText(webContent);
		
		info("Upload files in site explorer");
		navToolBar.goToSiteExplorer();
		//actBar.uploadFile("TestData/" + file1);
		actBar.uploadFile("TestData/" + file2);
		
		actBar.goToAddNewContent();
		click(cTemp.ELEMENT_WEBCONTENT_LINK);
		type(cTemp.ELEMENT_WEBCONTENT_NAME_TEXTBOX, webContent, true);
		
		//cTemp.addContentLinkInFCKEditor("GeneralDrives_/GeneralDrives_ManagedSites_", file1);
		cTemp.addContentLinkInFCKEditor("GeneralDrives_/GeneralDrives_ManagedSites_", file2);
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		//waitForTextPresent(webContent);
		waitForAndGetElement(elementWeb);
		//ecms.goToNode(elementWeb);
		waitForAndGetElement(By.xpath(ELEMENT_LINK_IN_CE.replace("${file}", file2)));
		//waitForElementPresent(By.xpath(ELEMENT_LINK_IN_CE.replace("${file}", file1)));
		
		info("Delete uploaded files");
		cMenu.deleteData(elementWeb);
		//cMenu.deleteData(By.linkText(file1));
		cMenu.deleteData(By.linkText(file2));
	}

    /**CaseId: 75243 : Delete an uploaded file in Space/Document
     * @author hzekri
     */
    @Test
    public void test28_DeleteUploadedFileInSpaceDocument(){
        //Declare variables
        String spacename = "SpaceTestUploadDelete";
        String spacedesc = "Description Of SpaceTestUploadDelete";

        //Add new space
        magMember.goToMySpacePage();
        magMember.addNewSpace(spacename, spacedesc);

        //Open Documents in this space
        waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
        click(magMember.ELEMENT_DOCUMENTS_TAB);


        info("Upload file in space document");
        actBar.uploadFile("TestData/" + file1, true);

        info("Delete Uploaded file");
        click(actBar.ELEMENT_VIEW_MODE_LINK.replace("${viewName}", "List"));
        actBar.actionsOnElement(file1, actionType.DELETE, false, true);

        // remove space after using it
        magMember.goToMySpacePage();
        magMember.deleteSpace(spacename,300000);
    }
}