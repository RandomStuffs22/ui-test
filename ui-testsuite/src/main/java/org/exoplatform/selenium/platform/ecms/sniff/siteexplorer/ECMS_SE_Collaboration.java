package org.exoplatform.selenium.platform.ecms.sniff.siteexplorer;

/**
 * @author lientm
 * @date 22/5/2013
 */
import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ECMS_SE_Collaboration extends PlatformBase {
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	EcmsBase ecms;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;
	Button button;
	PageEditor pageE;

	public final String DATA_USER = "john";
	//public final String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver,this.plfVersion);
		navToolBar = new NavigationToolbar(driver,this.plfVersion);
		actBar = new ActionBar(driver,this.plfVersion);
		ecms = new EcmsBase(driver,this.plfVersion);
		cTemplate = new ContentTemplate(driver,this.plfVersion);
		cMenu = new ContextMenu(driver,this.plfVersion);
		siteExp = new SitesExplorer(driver,this.plfVersion);
		pageE = new PageEditor(driver,this.plfVersion);
		button = new Button(driver,this.plfVersion);

		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseID: 65853 + 67864 + 67865: 
	 * Add + Edit + Delete comment for document/uploaded file
	 */
	@Test
	public void test38_39_40_AddEditDeleteComment_Document(){
		String file = "File_document_name_add_comment_38";
		String fileContent = "File_document_content_sniff_38";
		String oldComment = "Comment to file document";
		String newComment = "New comment to file document";
		By elementFile = By.linkText(file);

		navToolBar.goToSiteExplorer();
		click(By.linkText("acme"));
		click(By.linkText("documents"));
		click(By.linkText("metro.pdf"));
		actBar.addItem2ActionBar("comment", actBar.ELEMENT_ADD_COMMENT_LINK);
		navToolBar.goToSiteExplorer();
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		actBar.goToAddNewContent();

		info("Create new file document and add comment");
		cTemplate.createNewFile(file, fileContent, file);
		actBar.addComment(oldComment);

		info("Edit comment");
		ecms.goToNode(elementFile);
		actBar.editComment(oldComment, newComment);

		info("Delete comment");
		ecms.goToNode(elementFile);
		actBar.deleteComment(newComment);

		cMenu.deleteData(elementFile);
	}

	@Test
	public void test38_39_40_AddEditDeleteComment_UploadedFile(){
		String fileUpload = "ECMS_Admin_ManageCategories_Display.jpg";
		String oldComment = "Comment to uploaded file";
		String newComment = "New comment to upload file";
		By elementUploadFile = By.linkText(fileUpload);

		navToolBar.goToSiteExplorer();
		click(By.linkText("acme"));
		click(By.linkText("documents"));
		click(By.linkText("metro.pdf"));
		actBar.addItem2ActionBar("comment", actBar.ELEMENT_ADD_COMMENT_LINK);
		navToolBar.goToSiteExplorer();
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		info("Upload new file and add comment");
		actBar.uploadFile("TestData/" + fileUpload);
		ecms.goToNode(elementUploadFile);
		actBar.addComment(oldComment);

		info("Edit comment");
		ecms.goToNode(elementUploadFile);
		actBar.editComment(oldComment, newComment);

		info("Delete comment");
		ecms.goToNode(elementUploadFile);
		actBar.deleteComment(newComment);

		cMenu.deleteData(elementUploadFile);
	}

	/**CaseId: 65860 + 65868 + 65869
	 * Add - Edit - Delete tag for document/uploaded file
	 */
	@Test
	public void test45_46_47_AddEditDeleteTag_Document(){
		String file = "File_document_name_add_tag_45";
		String fileContent = "File_document_content_add_tag_45";
		String[] tagName = {"Tag_1"};
		String[] newTag = {"New_tag_1"};
		By elementFile = By.linkText(file);

		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();

		info("Create new file document and add comment");
		cTemplate.createNewFile(file, fileContent, file);
		siteExp.addTagForNode(tagName);

		info("Edit tag");
		siteExp.goToEditTag();
		siteExp.editTag(tagName[0], newTag[0]);
		button.closeWindow();

		info("Delete tag");
		siteExp.goToEditTag();
		siteExp.deleteTag(newTag);

		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteData(elementFile);
	}

	@Test
	public void test45_46_47_AddEditDelete_UploadedFile(){
		String fileUpload = "ECMS_Admin_ManageCategories_Display.jpg";
		String[] tagName = {"Tag_2"};
		String[] newTag = {"New_tag_2"};
		By elementUploadFile = By.linkText(fileUpload);

		navToolBar.goToSiteExplorer();

		info("Upload new file and add tag");
		actBar.uploadFile("TestData/" + fileUpload);
		ecms.goToNode(elementUploadFile);
		siteExp.addTagForNode(tagName);

		info("Edit tag");
		siteExp.goToEditTag();
		siteExp.editTag(tagName[0], newTag[0]);
		button.closeWindow();

		info("Delete tag");
		siteExp.goToEditTag();
		siteExp.deleteTag(newTag);

		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteData(elementUploadFile);
	}


	/**CaseId: 65882
	 * Vote for a document or uploaded file
	 */
	@Test
	public void test48_VoteForDocument(){
		String file = "File_document_name_vote_48";
		String fileContent = "File_document_content_vote_48";
		By elementFile = By.linkText(file);

		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();

		info("Create new file document and add comment");
		cTemplate.createNewFile(file, fileContent, file);
		actBar.voteDocument(3);
		waitForAndGetElement(By.xpath(ecms.ELEMENT_VOTE_RATING_INFOR.replace("${rate}", "3.0")));

		cMenu.deleteData(elementFile);
	}

	@Test
	public void test48_VoteForUploadedFile(){
		String fileUpload = "Winter.jpg";
		By elementUploadFile = By.linkText(fileUpload);

		navToolBar.goToSiteExplorer();

		info("Upload new file and vote");
		actBar.uploadFile("TestData/" + fileUpload);
		ecms.goToNode(elementUploadFile);
		actBar.voteDocument(2);
		waitForAndGetElement(By.xpath(ecms.ELEMENT_VOTE_RATING_INFOR.replace("${rate}", "2.0")));

		cMenu.deleteData(elementUploadFile);
	}


	/** CaseId: 65823 -> Add translation for document/uploaded file
	 * create new documents or upload files in different language
	 * public these documents or upload files
	 * make translation between them
	 * check translation is applied in a content detail portlet
	 */
	@Test
	public void test41_AddTranslation_Document(){
		String fileEnglish = "File_document_English";
		String fileContentEnglish = "File_document_content_English";
		By elementFileEnglish = By.linkText(fileEnglish);
		String fileFrench = "File_document_French";
		String fileContentFrench = "File_document_content_French";
		By elementFileFrench = By.linkText(fileFrench);
		String eContentDetail ;
		if(this.plfVersion.equalsIgnoreCase("4.1"))
			eContentDetail = ELEMENT_CONTENT_IN_CONTENT_DETAIL_PORTLET_41;
		else
			eContentDetail = ELEMENT_CONTENT_IN_CONTENT_DETAIL_PORTLET;


		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();

		info("Create new file with English language then public its");		
		cTemplate.createNewFile(fileEnglish, fileContentEnglish, fileEnglish);
		actBar.publishDocument();
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		info("Create new file with French language then public its");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileFrench, fileContentFrench, fileFrench);
		cTemplate.editLanguageForDocument(fileFrench, "fr");
		actBar.publishDocument();

		info("Add translation for file English to file French");
		ecms.goToNode(elementFileEnglish);
		actBar.addTranslationForDocument("", fileFrench);
		click(siteExp.ELEMENT_SITEBAR_RELATION);
		waitForAndGetElement(By.xpath(ecms.ELEMENT_TRANSLATION_IN_RELATION_TAB.replace("${fileName}", fileFrench)));

		info("Add a Content Detail portlet to file English");
		navToolBar.goToEditPageEditor();
		pageE.addContentDetailEmptyLayout();
		pageE.addContentPathForContentDetailPortlet("General Drives/Sites Management/" + fileEnglish);
		//click(ELEMENT_PAGE_EDIT_FINISH_OTHER);
		//waitForElementNotPresent(ELEMENT_PAGE_EDIT_FINISH_OTHER, 60000);
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForElementNotPresent(ELEMENT_PAGE_FINISH_BUTTON, 60000);
		waitForAndGetElement(By.xpath(eContentDetail.replace("${contentName}", fileEnglish)));

		info("Change view language to French");
		magAcc.changeLanguageForUser("French");

		info("When change language, the French file will display replace English file");
		waitForAndGetElement(By.xpath(eContentDetail.replace("${contentName}", fileFrench)));

		info("Delete data");
		magAcc.changeLanguageForUser("Anglais");
		navToolBar.goToEditPageEditor();
		pageE.removePortlet(ELEMENT_CONTENT_DETAIL_IN_LAYOUT, ELEMENT_CONTENT_DETAIL_DELETE_ICON);
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteData(elementFileFrench);
		cMenu.deleteData(elementFileEnglish);
	}

	@Test
	public void test41_AddTranslation_UploadedFile(){
		String fileEnglish = "English.docx";
		By elementFileEnglish = By.linkText(fileEnglish);
		String fileFrench = "French.docx";
		By elementFileFrench = By.linkText(fileFrench);
		String eContentDetail ;
		if(this.plfVersion.equalsIgnoreCase("4.1"))
			eContentDetail = ELEMENT_CONTENT_IN_CONTENT_DETAIL_PORTLET_41;
		else
			eContentDetail = ELEMENT_CONTENT_IN_CONTENT_DETAIL_PORTLET;

		navToolBar.goToSiteExplorer();

		info("Upload 2 file");
		actBar.uploadFile("TestData/" + fileEnglish);
		actBar.uploadFile("TestData/" + fileFrench);

		info("Public 2 files");
		ecms.goToNode(elementFileEnglish);
		actBar.publishDocument();
		ecms.goToNode(elementFileFrench);
		actBar.publishDocument();

		info("Set language = french for file french.docx");
		cTemplate.editLanguageForDocument(fileFrench, "fr");

		info("Add translation for file English to file French");
		ecms.goToNode(elementFileEnglish);
		actBar.addTranslationForDocument("", fileFrench);
		click(siteExp.ELEMENT_SITEBAR_RELATION);
		waitForAndGetElement(By.xpath(ecms.ELEMENT_TRANSLATION_IN_RELATION_TAB.replace("${fileName}", fileFrench)));

		info("Add a Content Detail portlet to file English");
		navToolBar.goToEditPageEditor();
		pageE.addContentDetailEmptyLayout();
		pageE.addContentPathForContentDetailPortlet("General Drives/Sites Management/" + fileEnglish);
		//click(ELEMENT_PAGE_EDIT_FINISH_OTHER);
		//waitForElementNotPresent(ELEMENT_PAGE_EDIT_FINISH_OTHER, 60000);
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForElementNotPresent(ELEMENT_PAGE_FINISH_BUTTON, 60000);
		waitForAndGetElement(By.xpath(eContentDetail.replace("${contentName}", fileEnglish)));

		info("Change view language to French");
		magAcc.changeLanguageForUser("French");

		info("When change language, the French file will display replace English file");
		waitForAndGetElement(By.xpath(eContentDetail.replace("${contentName}", fileFrench)));

		info("Delete data");
		magAcc.changeLanguageForUser("Anglais");
		navToolBar.goToEditPageEditor();
		pageE.removePortlet(ELEMENT_CONTENT_DETAIL_IN_LAYOUT, ELEMENT_CONTENT_DETAIL_DELETE_ICON);
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteData(elementFileFrench);
		cMenu.deleteData(elementFileEnglish);
	}
}