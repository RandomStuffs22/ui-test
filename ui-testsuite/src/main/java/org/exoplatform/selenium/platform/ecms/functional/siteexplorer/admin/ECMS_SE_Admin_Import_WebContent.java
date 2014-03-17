package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.admin;

import org.exoplatform.selenium.platform.PlatformBase;
import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author thuntn
 * @date: 28/08/2013
 */
public class ECMS_SE_Admin_Import_WebContent extends PlatformBase {
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;


	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver,this.plfVersion);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);

		info("Add Import icon to action bar if it does not existed");
		navToolBar.goToSiteExplorer();
		actBar.addItem2ActionBar("importNode", actBar.ELEMENT_IMPORT_LINK);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		//		logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * CaseID: 75003
	 * Import a Web Content into Web Content
	 */
	@Test(groups={"error"})
	public void test01_ImportWebContentToWebContent(){
		String title = "test01_ImportWebContentToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		
		info("Import a Web Content into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Check if the Import button is not shown");
		click(siteExp.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		assert (waitForAndGetElement(actBar.ELEMENT_IMPORT_LINK,DEFAULT_TIMEOUT,0) == null);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75004
	 * Import Accessible Breadcrumb into Web Content
	 */
	@Test
	public void test02_ImportAccessibleBreadcrumbToWebContent(){
		String title = "test02_ImportAccessibleBreadcrumbToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/test02_AccessibleBreadcrumb.xml";
		String fileImport = "test02_AccessibleBreadcrumb";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import a Web Content into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Import a Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75006
	 * Import Accessible Media into Web Content
	 */
	@Test
	public void test03_ImportAccessibleMediaToWebContent(){
		String title = "test03_ImportAccessibleMediaToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/test03_AccessibleMedia.xml";
		String fileImport = "test03_AccessibleMedia";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import a Web Content into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Import Accessible Media");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75007
	 * Import Accessible Navigation into Web Content
	 */
	@Test
	public void test04_ImportAccessibleNavigationToWebContent(){
		String title = "test04_ImportAccessibleNavigationToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/test04_AccessibleNavigation.xml";
		String fileImport = "test04_AccessibleNavigation";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Accessible Navigation into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Import Accessible Navigation");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75008
	 * Import Accessible Site Search Box into Web Content
	 */
	@Test
	public void test05_ImportAccessibleSiteSearchBoxToWebContent(){
		String title = "test05_ImportAccessibleSiteSearchBoxToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/test05_SiteSearchBox.zip";
		String fileImport = "test05_SiteSearchBox";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Accessible Site Search Box into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Import Accessible Site Search Box");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75009
	 * Import Announcement into Web Content
	 */
	@Test
	public void test06_ImportAnnouncementToWebContent(){
		String title = "test06_ImportAnnouncementToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/test06_announcement.zip";
		String fileImport = "test06_announcement";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Announcement into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info(" Import Announcement");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	
	/**
	 * CaseID: 75010
	 * Import Contact Us into Web Content
	 */
	@Test
	public void test07_ImportContactUsToWebContent(){
		String title = "test07_ImportContactUsToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/test08_ContactUs.zip";
		By contactUs = By.xpath("//*[@title='"+title+"']/../../../../ul//*[@class='uiIcon16x16acme_contact_usDefault uiIcon16x16acme_contact_us']");
		
		info("Import Contact Us into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Import Contact Us");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(contactUs);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75011
	 * Import File into Web Content
	 */
	@Test
	public void test08_ImportFileToWebContent(){
		String title = "test08_ImportFileToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/test8_File.xml";
		String fileImport = "test8_File";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import File into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Import File");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75012
	 * Import HTML File into Web Content
	 */
	@Test
	public void test09_ImportHTMLFileToWebContent(){
		String title = "test09_ImportHTMLFileToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/test09_htmlFile.zip";
		String fileImport = "test09_htmlFile";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import HTML File into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Import HTML File");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75013
	 * Import Illustrated WebContent into Web Content
	 */
	@Test
	public void test10_ImportIllustratedToWebContent(){
		String title = "test10_ImportIllustratedToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/sysview2.zip";
		String fileImport = "test01_ImportIllustratedImport";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Illustrated WebContent into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Import Illustrated WebContent");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75014
	 * Import JS File into Web Content
	 */
	@Test
	public void test11_ImportJSFileToWebContent(){
		String title = "test11_ImportJSFileToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/test11_JSFile.zip";
		String fileImport = "test11_JSFile";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import JS File into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Import JS File");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75015
	 * Import Product into Web Content
	 */
	@Test
	public void test12_ImportProductToWebContent(){
		String title = "test12_ImportProductToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/test12_Product.zip";
		String fileImport = "test12_Product";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Product into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Import Product");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75016
	 * Import Web Link into Web Content
	 */
	@Test
	public void test13_ImportWebLinkToWebContent(){
		String title = "test13_ImportWebLinkToWebContent";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_webcontent.jpg";
		String linkFile = "TestData/test13_WebLink.zip";
		String fileImport = "test13_WebLink";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Web Link into Web Content");
		info("Create a web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(title, title, linkImage, title, css, "");

		info("Import Web Link");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
}