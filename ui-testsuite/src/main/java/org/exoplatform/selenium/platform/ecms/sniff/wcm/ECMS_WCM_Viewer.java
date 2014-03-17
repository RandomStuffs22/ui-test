package org.exoplatform.selenium.platform.ecms.sniff.wcm;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.wcm.ContentListPreference;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * June, 2013
 *  
 */
public class ECMS_WCM_Viewer extends PlatformBase{

	Button button;
	PageEditor pEditor;
	PageManagement pMag;
	NavigationToolbar navToolBar;
	UserGroupManagement userGroup;
	ManageAccount magAcc;
	EcmsBase ecms;
	ActionBar actBar;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	ContentListPreference cList;

	String acmeURL = DEFAULT_BASEURL + "/acme";

	@BeforeMethod
	public void beforeMethod() {
		initSeleniumTest();
		driver.get(baseUrl);
		button = new Button(driver);
		userGroup = new UserGroupManagement(driver);
		navToolBar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);	
		pEditor = new PageEditor(driver);
		pMag = new PageManagement(driver);
		ecms = new EcmsBase(driver);
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		cList = new ContentListPreference(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		driver.get(acmeURL);
		driver.navigate().refresh();
		waitForElementNotPresent(ELEMENT_PERSONAL_DOCUMENTS);
	}

	@AfterMethod
	public void afterMethods() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/*===== Single Content Viewer =====*/
	/**
	 * Qmetry ID: 65833
	 * Create Single Content Viewer page 
	 * ================
	 * Qmetry ID: 65833
	 * Edit Single Content Viewer page
	 * 
	 */
	@Test
	public void test01_CreateAndEditSingleContentViewerPage(){	
		String pageName = "Sniff_Ecms_SCV01";
		String contentPath = "General Drives/Sites Management/acme/documents/offices.jpg";
		String editContentPath = "conditions.doc";

		info("-- Create Single Content Viewer page --");
		pEditor.addSCVPageAndContentFolderPaths(pageName, contentPath);
		waitForTextPresent("offices.jpg");

		info("-- Edit Single Content Viewer page --");
		navToolBar.goToEditPageEditor();
		pEditor.openAddContentPathForm();
		click(By.linkText(editContentPath));
		button.save();
		button.close();
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForTextNotPresent("offices.jpg");
		waitForTextPresent("conditions.doc");

		info("-- Restore original data--");
		pMag.deletePageAtManagePageAndPortalNavigation(pageName, true, "acme", false, "");
	}

	/*===== Content List Viewer =====*/
	/**
	 * Qmetry ID: 65829
	 * Create new Content List viewer page with mode [By Folder]
	 * ================
	 * Qmetry ID: 67825
	 * Edit Content List viewer page with mode [By Folder]
	 * 
	 */
	@Test
	public void test02_CreateAndEditContentListViewerPageByFolder(){
		String cName = "CLV_By_Folder_01";
		String cContent = "Web content: CLV By folder";
		String contentPath = "acme/web contents/News";

		String pCLVName = "CLV_By_Folder_02";
		String pCLVPath = "General Drives/Sites Management/acme/web contents";
		String pCLVFolder = "News";
		String editCLVPath = "Events";

		info("-- Create a web content --");
		navToolBar.goToSiteExplorer();
		ecms.goToNode(contentPath);
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(cName, cContent, "", "", "", "");
		actBar.publishDocument();

		info("-- Create a new content list viewer page --");
		ecms.goToOverviewPage();
		pEditor.addCLVPageAndCLVpath(pCLVName, pCLVPath, pCLVFolder);
		//waitForTextPresent(cName);

		info("-- Edit a content list viewer page --");
		navToolBar.goToEditPageEditor();
		pEditor.openAddContentPathForm();
		click(pEditor.ELEMENT_RIGHT_WORKSPACE_NODE.replace("${node}", editCLVPath));
		button.save();
		//wait a second: avoid pop-up blocker 
		waitForTextNotPresent("News");
		button.close();
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForTextNotPresent(cName);

		info("-- Restore original data--");
		pMag.deletePageAtManagePageAndPortalNavigation(pCLVName, true, "acme", false, "");
		navToolBar.goToSiteExplorer();
		//ecms.goToNode(contentPath);
		cMenu.deleteDocument(By.linkText(cName));
	}

	/**
	 * Qmetry ID: 65832
	 * Create new Content List Viewer page with mode [By Contents]
	 * ================
	 * Qmetry ID: 67824
	 * Edit Content List Viewer page with mode [By Contents] 
	 *  
	 */
	@Test
	public void test03_CreateAndEditListViewerPageByContent(){
		String cName = "CLV_By_Content_01";
		String cContent = "Web content: CLV By Content";
		String contentPath = "acme/web contents/Events";

		String pCLVName = "CLV_By_Content_02";
		String pCLVPath = "General Drives/Sites Management/acme/web contents/News";
		String pCLVContent = "New: Speed";
		String editCLVPath = "General Drives/Sites Management/acme/web contents/Events/" + cName;

		info("-- Create a web content --");
		navToolBar.goToSiteExplorer();
		ecms.goToNode(contentPath);
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(cName, cContent, "", "", "", "");
		actBar.publishDocument();

		info("-- Create a new content list viewer page --");
		ecms.goToOverviewPage();		
		pEditor.addCLVPageAndCLVpath(pCLVName, pCLVPath, pCLVContent, "content");
		//waitForTextPresent(pCLVContent);

		info("-- Edit a content list viewer page --");
		navToolBar.goToEditPageEditor();
		pEditor.selectContentPathInEditMode(editCLVPath, true, true);
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForTextPresent(cName);

		info("-- Restore original data--");
		pMag.deletePageAtManagePageAndPortalNavigation(pCLVName, true, "acme", false, "");
		navToolBar.goToSiteExplorer();
		ecms.goToNode(contentPath);
		cMenu.deleteDocument(By.linkText(cName));
	}

	/**
	 * Qmetry ID: 65843
	 * Edit Preference of Content List Viewer (Documents, Last news ...)
	 * 
	 */
	@Test
	public void test04_EditPreferenceOfContentListViewer(){
		String cName = "Edit_Preference_News";
		String cContent = "Edit Preference of Content List Viewer";
		String contentPath = "acme/web contents/Events";
		String image = "TestData/Winter.jpg";
		String pCLVPath = "General Drives/Sites Management/acme/web contents/Events/"+cName;		

		info("-- Create a new web content --");
		navToolBar.goToSiteExplorer();
		ecms.goToNode(contentPath);
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(cName, cContent, image, "", "", "");
		actBar.publishDocument();
		actBar.openManagePublicationForm();
		String pDate;
		pDate = actBar.getPublishDate(actBar.ELEMENT_FIRST_REVISION_DATE);
		button.close();

		info("-- Open Overview page --");
		ecms.goToOverviewPage();
		//waitForTextNotPresent(pDate);
		ecms.enableEditMode(true);

		info("-- Latest news: Open Content List Preference --");
		cList.goToContentListPreference(ecms.ELEMENT_RSS_ICON, cList.ELEMENT_PREFERENCE_LATEST_NEWS_ICON);
		//Edit properties
		cList.setDisplayOptions(cList.ELEMENT_SHOW_DATE);
		click(cList.ELEMENT_CONTENT_SELECTION_TAB);
		click(ELEMENT_SELECT_CONTENT_PATH_LINK);
		pEditor.selectContentPathInEditMode(pCLVPath, true, false, false);
		waitForAndGetElement(ecms.ELEMENT_CLV_TITLE.replace("${title}", cName), DEFAULT_TIMEOUT, 0, 2);
		waitForAndGetElement(ecms.ELEMENT_CLV_PUBLISH_DATE.replace("${date}", pDate), DEFAULT_TIMEOUT, 0, 2);
		info("-- Restore original data--");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(By.linkText(cName));
	}
} 