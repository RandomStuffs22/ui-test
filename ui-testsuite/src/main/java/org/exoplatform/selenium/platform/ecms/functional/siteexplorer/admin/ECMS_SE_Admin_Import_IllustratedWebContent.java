package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.admin;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Thuntn
 * @date 27/08/2013
 */
public class ECMS_SE_Admin_Import_IllustratedWebContent extends PlatformBase {
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
		actBar = new ActionBar(driver,this.plfVersion);
		cTemplate = new ContentTemplate(driver,this.plfVersion);
		cMenu = new ContextMenu(driver,this.plfVersion);
		siteExp = new SitesExplorer(driver,this.plfVersion);
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
	 * 
	 * CaseID: 75017
	 * Import a Illustrated Web Content into Illustrated Web Content
	 */
	@Test(groups={"error"})
	public void test01_ImportIllustratedIntoIllustrated(){
		String title = "test01_ImportIllustrated";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		
		info("Import a Illustrated Web Content into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Check if the button Import is not shown");
		click(siteExp.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		assert (waitForAndGetElement(actBar.ELEMENT_IMPORT_LINK, DEFAULT_TIMEOUT,0) == null);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75019
	 * Import Accessible Media into Illustrated Web Content
	 */
	@Test
	public void test02_ImportAccessibleMediaIntoIllustrated(){
		String title = "test02_ImportAccessibleMediaIntoIllustrated";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String css="p{color:red;}";
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/sysview3.xml";
		String fileImport = "test02_ImportAccessibleToIllustrated";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Accessible Media into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import Accessible Media into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75024
	 * Import File into Illustrated Web Content
	 */
	@Test
	public void test03_ImportFileIntoIllustrated(){
		String title = "test03_Illustrated";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/test03_ImportFileToIllustrated.xml";
		String fileImport = "test03_ImportFileIntoIllustrated";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import File into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import File into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75018
	 * Import Accessible Breadcrumb into Illustrated Web Content
	 */
	@Test
	public void test04_ImportAccessibleBreadcrumbIntoIllustrated(){
		String title = "test04_ImportAccessibleBreadcrumbIntoIllustrated";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String css="p{color:red;}";
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/test04_AccessibleBreadcrumb.xml";
		String fileImport = "test04_AccessibleBreadcrumb";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Accessible Breadcrumb into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import Accessible Breadcrumb into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75020
	 * Import Accessible Navigation into Illustrated Web Content
	 */
	@Test
	public void test05_ImportAccessibleNavigationIntoIllustrated(){
		String title = "test05_ImportAccessibleNavigationIntoIllustrated";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String css="p{color:red;}";
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/test05_AccessibleNavigation.xml";
		String fileImport = "test05_AccessibleNavigation";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Accessible Navigation into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import Accessible Navigation into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75021
	 * Import Accessible Site Search Box into Illustrated Web Content
	 */
	@Test
	public void test06_ImportAccessibleSiteSearchBoxIntoIllustrated(){
		String title = "test06_ImportAccessibleSiteSearchBox";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String css="p{color:red;}";
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/test06_AccessibleSiteSearchBox.xml";
		String fileImport = "test06_AccessibleSiteSearchBox";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Accessible Site Search Box into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import Accessible Site Search Box into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75022
	 * Import Announcement into Illustrated Web Content
	 */
	@Test
	public void test07_ImportAnnouncementIntoIllustrated(){
		String title = "test07_ImportAnnouncementIntoIllustrated";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String css="p{color:red;}";
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/test07_Announcement.zip";
		String fileImport = "test07_Announcement";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Announcement into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import Announcement into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75023
	 * Import Contact Us into Illustrated Web Content
	 */
	@Test
	public void test08_ImportContactUsIntoIllustrated(){
		String title = "test08_ImportContactUsIntoIllustrated";
		By contactUs = By.xpath("//*[@title='"+title+"']/../../../../ul//*[@class='uiIcon16x16acme_contact_usDefault uiIcon16x16acme_contact_us']");
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String css="p{color:red;}";
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/test08_ContactUs.zip";
		
		info("Import Contact Us into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import Contact Us into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(contactUs);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75025
	 * Import HTML File into Illustrated Web Content
	 */
	@Test
	public void test09_ImportHTMLFileIntoIllustrated(){
		String title = "test09_ImportHTMLFileIntoIllustrated";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/test09_htmlFile.zip";
		String fileImport = "test09_htmlFile";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import HTML File into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import HTML File into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75026
	 * Import WebContent into Illustrated Web Content
	 */
	@Test
	public void test10_ImportWebContentIntoIllustrated(){
		String title = "test10_ImportWebContentIntoIllustrated";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/test10_WebContent.xml";
		String fileImport = "test10_WebContent";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import WebContent into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import WebContent into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75027
	 * Import JS File into Illustrated Web Content
	 */
	@Test
	public void test11_ImportJSFileIntoIllustrated(){
		String title = "test11_ImportJSFileIntoIllustrated";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/test11_JSFile.zip";
		String fileImport = "test11_JSFile";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import JS File into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import JS File into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75028
	 * Import Product into Illustrated Web Content
	 */
	@Test
	public void test12_ImportProductIntoIllustrated(){
		String title = "test12_ImportProductIntoIllustrated";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/test12_Product.zip";
		String fileImport = "test12_Product";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Product into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import Product into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**
	 * CaseID: 75029
	 * Import Web Link into Illustrated Web Content
	 */
	@Test
	public void test13_ImportProductIntoIllustrated(){
		String title = "test13_ImportProductIntoIllustrated";
		String css="p{color:red;}";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		String linkImage = "TestData/test01_ImportIllustrated01.jpg";
		String linkIllustration = "TestData/test01_ImportIllustrated02.png";
		String linkFile = "TestData/test13_WebLink.zip";
		String fileImport = "test13_WebLink";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));
		
		info("Import Web Link into Illustrated Web Content");
		info("Create an Illustrated web content");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(title, title, linkImage, linkIllustration, title, css, "");

		info("Import Web Link into Illustrated Web Content");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
}