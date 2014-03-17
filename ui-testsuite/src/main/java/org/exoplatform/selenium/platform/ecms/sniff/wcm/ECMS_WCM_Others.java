package org.exoplatform.selenium.platform.ecms.sniff.wcm;

import static org.exoplatform.selenium.TestLogger.info;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.wcm.SEO;

/**
 * Update @author vuna2
 * <li> Fixed the issue FQA-1073 (using Utils.javaSimulateKeyPress for Enter Key) </li>
 * <li> Add case 65873: Show categories and documents </li> 
 */
/**
 * @Date: 2013/05/24
 * @author thuntn
 * <li>ECMS/Sniff/WCM: News, Search, SEO, Draft And Public Content</li>
 */
public class ECMS_WCM_Others extends PlatformBase{
	//Platform
	Button button;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	PageManagement pages;

	//Ecms
	EcmsBase ecms;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;
	PageEditor pEditor;
	SEO seo;
	String acmeURL = DEFAULT_BASEURL + "/acme/"; 

	/**
	 * CaseID 65874 Show draft/public content from page
	 * Step 1: Show draft/public content from page
	 * 
	 */
	@Test
	public void test01_ShowDraftPublicContentFromPage(){
		String node1 = "ShowDraftPublicContentFromPage65874";
		String page = "page65874";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		String contentPath = "General Drives/Sites Management/" + node1;

		info("Show draft/public content from page");
		navToolBar.goToSiteExplorer();
		//Create node
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1, node1, node1, node1);
			
		//Go to overview page, add a SCV page
		click(ecms.ELEMENT_OVERVIEW_LINK);	
		pEditor.addSCVPageAndContentFolderPaths(page, contentPath,true);
				
		//Switch to Edit mode
		ecms.enableEditMode(true);
		mouseOver(ecms.ELEMENT_ACME_TITLE.replace("${content}", node1), true);
		waitForAndGetElement(ecms.ELEMENT_DRAFT_ACME.replace("${content}", node1));
		
		//Publish node1
		navToolBar.goToSiteExplorer();
		actBar.goToNode(node1);
		actBar.publishDocument();
		
		//Verify this content is published
		driver.get(acmeURL +"overview/" +page);
		mouseOver(ecms.ELEMENT_ACME_TITLE.replace("${content}", node1), true);
		waitForAndGetElement(ecms.ELEMENT_PUBLISH_ACME.replace("${content}", node1));
		
		//Switch to public mode
		ecms.enableEditMode(false);
		//waitForTextPresent(node1);
		
		//Delete data
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
		
		//Delete page
		pages.deletePageAtManagePageAndPortalNavigation(page, true, "acme", false, "");
	}
	
	/**
	 * CaseID 65871 Search content of document/page
	 * Step 1: Search content of document/page
	 * This case is pending because issue FQA-1073
	 * ==> Fixed: Utils.javaSimulateKeyPress 
	 * 
	 */
	@Test
	public void test02_SearchContentDocumentPage(){
		String node1 = "test02 SearchContent 1";
        String node2 = "test02 SearchContent 2";
        String node3 = "test02 SearchContent 3";
        String node4 = "test02 SearchContent 4";
        String page1 = "test02_page3";
        String page2 = "test02_page2";
        String searchText = "test02";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		String contentPath1 = "General Drives/Sites Management/acme/" + node1;
		String contentPath2 = "General Drives/Sites Management/acme/" + node3;

		info("Search content of document/page");	
		navToolBar.goToSiteExplorer();
		//Create node
		actBar.goToNode("acme");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1, node1, node1, node1);
		
		actBar.goToNode("acme");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node2, node2, node2, node2);
		actBar.publishDocument();
		
		actBar.goToNode("acme");
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(node3, node3);
		actBar.publishDocument();
		
		actBar.goToNode("acme");
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(node4, node4);
		
		//Go to overview page, add a SCV page
		click(ecms.ELEMENT_OVERVIEW_LINK);
		
		Utils.pause(1000);
		pEditor.addSCVPageAndContentFolderPaths(page1, contentPath1,true);
		ecms.goToOverviewPage();
		pEditor.addSCVPageAndContentFolderPaths(page2, contentPath2,true);
		type(ecms.ELEMENT_ACME_SEARCH_INPUT,searchText, true);
		waitForAndGetElement(ecms.ELEMENT_ACME_SEARCH_INPUT).sendKeys(Keys.RETURN);
		
		Utils.pause(1000);
		driver.navigate().refresh();
		Utils.pause(1000);
		waitForAndGetElement(ecms.ELEMENT_ACME_SEARCH_RESULT.replace("${result}", node3));
		waitForAndGetElement(ecms.ELEMENT_ACME_SEARCH_RESULT.replace("${result}", node2));
		waitForElementNotPresent(ecms.ELEMENT_ACME_SEARCH_RESULT.replace("${result}", node1));
		waitForElementNotPresent(ecms.ELEMENT_ACME_SEARCH_RESULT.replace("${result}", node4));
		
		//Switch to Edit mode
		ecms.enableEditMode(true);
		type(ecms.ELEMENT_ACME_SEARCH_INPUT,searchText, true);
		waitForAndGetElement(ecms.ELEMENT_ACME_SEARCH_INPUT).sendKeys(Keys.RETURN);
		
		waitForAndGetElement(ecms.ELEMENT_ACME_SEARCH_RESULT.replace("${result}", node3));
		waitForAndGetElement(ecms.ELEMENT_ACME_SEARCH_RESULT.replace("${result}", node2));
		waitForAndGetElement(ecms.ELEMENT_ACME_SEARCH_RESULT.replace("${result}", node1));
		waitForAndGetElement(ecms.ELEMENT_ACME_SEARCH_RESULT.replace("${result}", node4));
		
		//Switch to public mode
		ecms.enableEditMode(false);
		type(ecms.ELEMENT_ACME_SEARCH_INPUT,searchText, true);
		waitForAndGetElement(ecms.ELEMENT_ACME_SEARCH_INPUT).sendKeys(Keys.RETURN);
		waitForElementNotPresent(ecms.ELEMENT_ACME_SEARCH_RESULT.replace("${result}", node1));
		waitForElementNotPresent(ecms.ELEMENT_ACME_SEARCH_RESULT.replace("${result}", node4));
		
		//Delete data
		navToolBar.goToSiteExplorer();
		actBar.goToNode("acme");
		cMenu.deleteDocument(bNode);
		cMenu.deleteDocument(By.linkText(node2));
		cMenu.deleteDocument(By.linkText(node3));
		cMenu.deleteDocument(By.linkText(node4));
		
		//Delete page
		pages.deletePageAtManagePageAndPortalNavigation(page1, true, "acme", false, "");
		pages.deletePageAtManagePageAndPortalNavigation(page2, true, "acme", false, "");
	}
	
	/**
	 * CaseID 65850, 69183 Add SEO metadata with localization
	 * Step 1: Add SEO metadata with localization
	 * 
	 */
	@Test
	public void test03_AddSEOMetadataWithLocalization() {
		String seo1 = "test03 AddSEOMetadata 1";
		
		info("Add SEO metadata with localization");
		
		navToolBar.goToSeoManagement();
		
		seo.inputDataSeo(seo1, seo1, "INDEX", "FOLLOW", true, "Daily", "1");
		button.closeWindow();
		
		driver.navigate().refresh();
		navToolBar.goToSeoManagement();		
		assert waitForAndGetElement(seo.ELEMENT_KEYWORD, DEFAULT_TIMEOUT, 0, 2).getAttribute("content").equals(seo1) : "Keywords of metadata is not correct!";
		assert waitForAndGetElement(seo.ELEMENT_DESCRIPTION, DEFAULT_TIMEOUT, 0, 2).getAttribute("content").equals(seo1) : "Description of metadata is not correct!";
		assert waitForAndGetElement(seo.ELEMENT_ROBOT, DEFAULT_TIMEOUT, 0, 2).getAttribute("content").equals("INDEX, FOLLOW") : "Robot of metadata is not correct!";
		button.closeWindow();
		
		//Go to Sites management/Acme/SEO
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/SEO");
		waitForAndGetElement(siteExp.ELEMENT_SE_NODE.replace("{$node}", "sitemaps"));
		
		//Delete SEO, caseID 69183
		ecms.goToOverviewPage();
		navToolBar.goToSeoManagement();
		seo.deleteSEO("English");
	}
	
	/**
	 * CaseID 65851 Update SEO metadatas with localization
	 * Step 1: Update SEO metadatas with localization
	 * 
	 */
	@Test
	public void test04_UpdateSEOMetadataWithLocalization() {
		String seo1 = "test04 UpdateSEOMetadata 1";
		String seo2 = "test04 UpdateSEOMetadata";
		info("Update SEO metadatas with localization");
		
		navToolBar.goToSeoManagement();
		
		seo.inputDataSeo(seo1, seo1, "INDEX", "FOLLOW", true, "Daily", "1");
		button.closeWindow();
		
		navToolBar.goToSeoManagement();
		seo.inputDataSeo(seo2, seo2, "NOINDEX", "FOLLOW", true, "Hourly", "0.4");
		button.closeWindow();
		
		driver.navigate().refresh();
		navToolBar.goToSeoManagement();		
		assert waitForAndGetElement(seo.ELEMENT_KEYWORD, DEFAULT_TIMEOUT,0,2).getAttribute("content").equals(seo2) : "Keywords of metadata is not correct!";
		assert waitForAndGetElement(seo.ELEMENT_DESCRIPTION, DEFAULT_TIMEOUT,0,2).getAttribute("content").equals(seo2) : "Description of metadata is not correct!";
		assert waitForAndGetElement(seo.ELEMENT_ROBOT, DEFAULT_TIMEOUT,0,2).getAttribute("content").equals("NOINDEX, FOLLOW") : "Robot of metadata is not correct!";
		
		//Delete data
		seo.deleteSEO("English");
	}
	
	/**
	 * CaseID 69184 Manage the title
	 * Step 1: Manage the title
	 * 
	 */
	@Test
	public void test05_ManageTitle() {
		String seo1 = "test05 ManageTitle 1";
		info("Manage the title");
		
		navToolBar.goToSeoManagement();
		type(seo.ELEMENT_TITLE_INPUT, seo1, true);
		button.save();
		driver.navigate().refresh();
		assert driver.getTitle().equals(seo1): "Title is not correct";
		
		//Delete data
		seo.deleteSEO("English");
	}
	
	/**
	 * CaseID 69185 Check SEO Tool tips
	 * Step 1: Check SEO Tool tips
	 * 
	 */
	@Test
	public void test06_CheckSEOToolTips() {
		String descTooltip = "Help searchers: let them know that the following page has a link with what they are looking for.";
		String keyTooltip = "Most important terms in the page must be separated by a comma.";
		String priTooltip = "Valid values range from 0.0 to 1.0.";
				;
		info("Check SEO Tool tips");
		
		navToolBar.goToSeoManagement();
		mouseOver(seo.ELEMENT_DESC_HELP_ICON,true);
		waitForAndGetElement(seo.ELEMENT_SEO_TOOLTIP.replace("${tooltip}",descTooltip));
		
		mouseOver(seo.ELEMENT_KEY_HELP_ICON, true);
		waitForAndGetElement(seo.ELEMENT_SEO_TOOLTIP.replace("${tooltip}",keyTooltip));
		
		mouseOver(seo.ELEMENT_PRI_HELP_ICON, true);
		waitForAndGetElement(seo.ELEMENT_SEO_TOOLTIP.replace("${tooltip}",priTooltip));
		
		button.closeWindow();
	}
	
	/**
	 * Case ID 65873
	 * Show categories and documents
	 * 
	 */
	@Test
	public void test07_ShowCategoriesAndDocuments(){
		String fileName = "test07_Show_Categories_And_Documents";
		String webContentName = "test07_WebContent_Show_Categories_And_Documents";
		//String image = "TestData/Winter";
		
		info("-- Show categories and documents --");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/categories/powers/Defense/Invisibility");
		
		info("-- Add a new content --");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(webContentName, webContentName, "", "", "", "");
		actBar.publishDocument();
		
		actBar.goToNode("acme/categories/powers/Defense/Invisibility");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileName, fileName, fileName);
		actBar.publishDocument();
		
		info("-- Go to Acme/News/Defense/Invisibility --");
		ecms.goToOverviewPage();
		ecms.goToNode("News/Defense");
		//click(ecms.ELEMENT_ACME_TITLE.replace("${content}", "Invisibility"));
		click(ecms.ELEMENT_ACME_TAB_CATEGORY.replace("${content}", "Invisibility"));
		waitForTextPresent(fileName);
		waitForTextPresent(webContentName);
		
		info("-- Restore data --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(By.linkText(fileName));
		cMenu.deleteDocument(By.linkText(webContentName));
	}
	
	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.manage().window().maximize();
		button = new Button(driver);
		magAcc = new ManageAccount(driver);
		pEditor = new PageEditor(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		pages = new PageManagement(driver);
		seo = new SEO(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		driver.get(acmeURL);
		driver.navigate().refresh();
		waitForAndGetElement(ecms.ELEMENT_ACME_WELCOME_TEXT);	
	}

	@AfterMethod
	public void afterMethods() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}