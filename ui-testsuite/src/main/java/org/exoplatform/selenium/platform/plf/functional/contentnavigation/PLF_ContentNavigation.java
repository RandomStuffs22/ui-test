package org.exoplatform.selenium.platform.plf.functional.contentnavigation;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.BrandingManagement;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageView;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.openqa.selenium.By;
import org.testng.annotations.*;

/**
 * @author chinhdtt
 *
 */
public class PLF_ContentNavigation extends PlatformBase{
	ManageAccount acc; 
	HomePageActivity home;
	NavigationToolbar nav; 
	String user = "John Smith";
	PageManagement mPage; 
	EcmsBase acme; 
	ActionBar act; 
	BrandingManagement brMag; 
	ContentTemplate temCont; 
	PageEditor pEdit; 
	ManageView mnView;
	ECMainFunction ecMain;
	Dialog dialog;
	
	@BeforeMethod
	public void beforeMethods(){	
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver, this.plfVersion);
		home = new HomePageActivity(driver, this.plfVersion);
		nav = new NavigationToolbar(driver, this.plfVersion);	
		mPage = new PageManagement(driver, this.plfVersion);
		acme = new EcmsBase(driver, this.plfVersion);
		act = new ActionBar(driver, this.plfVersion);
		brMag = new BrandingManagement(driver, this.plfVersion);
		temCont = new ContentTemplate(driver);
		acc.signIn(DATA_USER1, DATA_PASS);	
		mnView = new ManageView(driver,this.plfVersion);
		ecMain = new ECMainFunction(driver);
		pEdit = new PageEditor(driver, this.plfVersion);
		button = new Button(driver,this.plfVersion);
		dialog = new Dialog(driver);

	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Case ID:74343.
	 * Test Case Name: Add a page.
	 * Pre-Condition: Install "acme" extension before starting server
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2013/12/27 17:42:06
	 */
//	@Test
	public  void test01_AddAPage() {
		info("Test 1: Add a page");
		String nodeName = "Node74343_test3";
		String displayName = "Node74343_2";
		String title = "Content";
		String message = "Sorry, no articles are available.";
		Map<String, String> portletIds= new HashMap<String, String>();
		portletIds.put("Content/ContentListViewerPortlet", "");

		String container = "column";
		Map<String, String> containerIds= new HashMap<String, String>();
		containerIds.put("twoColumns", "");

		/*
		- Go to Edit/Page/+ Add Page
		 *Input Data: 
		 *Expected Outcome: 
		- The pagePage Creation Wizard  is displayed
		- The first step: Select a Navigation Node and create the page		*/

		/*
		- Choose the selected page node on the left arborescence
		- Fill theNode Name: Events
		- Fill theDisplay Name: Events
		- Click on the button Next 
		 *Input Data: 
		 *Expected Outcome: 
		- The page of the second step  Select a Page Layout Template  is displayed		*/

		/*
		- From the list, choose Column Page Configs 
		- SelectTwo Columns Layout
		- Click on the buttonNext 
		 *Input Data: 
		 *Expected Outcome: 
		- The page of the third step  Re
		- arrange the Page Layout and add Portlets to the page 
		- The  Page Editor is displayed on the right of the screen		*/

		/*
		- From Applications tab, choose the Contentscategories
		- Drag and Drop aContent Detail on the two Columns containers 
		- From thePage Editor, click on the icon Finish 
		 *Input Data: 
		 *Expected Outcome: 
		- The page Events  is added under the Acme websiteOn the two columns, the message appears: Sorry, no articles are available 		*/ 
		acme.goToOverviewPage();
		nav.goToPageCreationWizard();
		Utils.pause(5000);
		click(ELEMENT_UP_LEVEL);
		info("Create page");
		mPage.addNewPageEditorWithColum(nodeName, displayName,"",title, portletIds, container, containerIds, false, false);
		Utils.pause(5000);
		waitForTextPresent(message);

		//delete data
		info("Delete page");
		mPage.deletePageAtManagePageAndPortalNavigation(nodeName, true, "acme", false, null,displayName);
	}

	/**
	 * Case ID:74344.
	 * Test Case Name: Edit The Navigation Form.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2013/12/27 17:42:06
	 */
//	@Test
	public  void test02_EditTheNavigationForm() {
		info("Test 2: Edit The Navigation Form");

		Object[] paras = {true,"Authoring","contentNavigation"};
		nav.goToSiteExplorer();
		click(By.linkText("acme"));
		click(By.linkText("events"));
		click(By.linkText("All"));
		/*
		- Go to Setup menu/Content/Site explorer
		- Navigate under the path: Collaboration/sites/acme/events/All
		- Click on the buttonContent navigation 
		- Fill the fields as following:
		- Visible: Checked
		- Target parent navigation: Click on the a magnifying glass
		- Choose the pageEvents
		 *Input Data: 
		 *Expected Outcome: 
		- The Site explorer is displayed
		- The window Navigation Form  is displayed
		- The  Parent's folder: All
		- The Navigation Selector is displayed
		- The selected page is displayed in the Field Target parent  navigation 		*/
		if(isElementNotPresent(act.ELEMENT_NAVIGATION_LINK)){
			ecMain.goToManageViews();
			mnView.editView("Web", "", false, false, paras);
			nav.goToSiteExplorer();
			click(act.ELEMENT_REFRESH_BUTTON);
			button.ok();
			waitForAndGetElement(act.ELEMENT_NAVIGATION_LINK);
		}	
		click(act.ELEMENT_NAVIGATION_LINK);
		waitForTextPresent("Navigation Form");
		assert waitForAndGetElement(By.id("Node")).getAttribute("value").equalsIgnoreCase("all");
		act.addContentNavigation(false, "home", null, false, "wiki", "documents", true);
		waitForTextNotPresent("Navigation Form");

		/*
		- Clickable: Unchecked
		- Page for the list: Click on the a magnifying glass
		- Choose portal:acme's navigation
		- Choose the path catalog from the list
		- Page for detail: Click on the magnifying glass
		- Choose portal:acme's navigation
		- Choose the path detail from the list
		- Click on the button Save 
		 *Input Data: 
		 *Expected Outcome: 
		- The Page selector is displayed
		- The selected page is displayed in the Field Page for the list 
		- The Page selector is displayed
		- The selected page is displayed in the Field  Page for the list 
		- The Navigation Form  window disappears		*/	



		/*Step 3: Check for changes on the Events page
		 *Input Data: 
		- Go to Acme/Overview hit the menu Events 
		 *Expected Outcome: 
		- The Acme Homepage is displayed
		- The list of catalog is displayed under the menu Events 		*/
		acme.goToOverviewPage();
		if(isElementPresent(By.linkText("Events")))
			click(By.linkText("Events"));
		else if(isElementPresent(By.linkText("Products")))
			click(By.linkText("Products"));
		
		waitForAndGetElement(By.linkText("Power 4 - Flying"));
		waitForAndGetElement(By.linkText("Power 3 - Healing"));
		waitForAndGetElement(By.linkText("Power 2 - Ice"));
		waitForAndGetElement(By.linkText("Power 1 - Fire"));
	}
//	@Test
	public  void test02_EditTheNavigationForm_Visibility() {
		/*Step 1: Visibility of a sub menu Restriction
		 *Input Data: 
		- Go to the Site explorer
		- Navigate under the path: collaboration/contents/live/acme/events/All/Fire
		- Click on the button  Content navigation
		- Uncheck the field  Visible 
		- Click on the button Save 
		 *Expected Outcome: The Navigation Form is displayedFields are set as following:
		- Parent's folder: Fire
		- Visible: Checked
		- Target parent navigation: empty
		- Display order: 1000
		- Clickable: Checked
		- Page for list: empty
		- Page for details: empty		*/
		Object[] paras = {true,"Authoring","contentNavigation"};
		info("Check Visibility of a sub menu Restriction");
		nav.goToSiteExplorer();
		click(By.linkText("acme"));
		click(By.linkText("events"));
		click(By.linkText("All"));
		click(By.linkText("Fire"));
	
		if(isElementNotPresent(act.ELEMENT_NAVIGATION_LINK)){
			ecMain.goToManageViews();
			mnView.editView("Web", "", false, false, paras);
			nav.goToSiteExplorer();
			click(act.ELEMENT_REFRESH_BUTTON);
			button.ok();
			waitForAndGetElement(act.ELEMENT_NAVIGATION_LINK);
		}	
		click(act.ELEMENT_NAVIGATION_LINK);
		waitForTextPresent("Navigation Form");
		assert waitForAndGetElement(By.id("Node")).getAttribute("value").equalsIgnoreCase("Fire");
		act.addContentNavigation(true, "", "", false, "", "", true);

		/*Step 2: Check for changes on the Events page
		 *Input Data: 
		- Go to Acme/Overview
		- Hit the menu  Events? Products?  
		 *Expected Outcome: 
		- The Navigation Form is disappears
		- The Sub menu Fire  is not displayed from the list		*/
		acme.goToOverviewPage();
		if(isElementPresent(By.linkText("Events")))
			click(By.linkText("Events"));
		else if(isElementPresent(By.linkText("Products")))
			click(By.linkText("Products"));
		waitForElementNotPresent(By.linkText("Fire"));
	}

//	@Test
	public  void test02_EditTheNavigationForm_Sort() {
		info("Sort the Earth element");
		Object[] paras = {true,"Authoring","contentNavigation"};
		/*Step 1: Sort the Earth element
		 *Input Data: 
		- Go to Site Explorer
		- Navigate under the path  collaboration/site contents/live/acme/events/All 
		- Select the  Earth Node
		- Click on the button Content Navigation 
		- Set the field  Display order  to 1
		- Click on the button  Save 
		 *Expected Outcome: 
		- The window Navigation Form is displayed 
		- The field  Display order: 1000
		- The window  Navigation Form  disappears		*/
		nav.goToSiteExplorer();
		click(By.linkText("acme"));
		click(By.linkText("events"));
		click(By.linkText("All"));
		click(By.linkText("Earth"));
		if(isElementNotPresent(act.ELEMENT_NAVIGATION_LINK)){
			ecMain.goToManageViews();
			mnView.editView("Web", "", false, false, paras);
			nav.goToSiteExplorer();
			click(act.ELEMENT_REFRESH_BUTTON);
			button.ok();
			waitForAndGetElement(act.ELEMENT_NAVIGATION_LINK);
		}	
		click(act.ELEMENT_NAVIGATION_LINK);
		waitForTextPresent("Navigation Form");
		System.out.println("waitForAndGetElement " + waitForAndGetElement(By.id("Node")).getAttribute("value"));
		assert waitForAndGetElement(By.id("Node")).getAttribute("value").equalsIgnoreCase("earth");
		assert waitForAndGetElement(act.ELEMENT_NAVIGATION_DISPLAY_ORDER).getAttribute("value").equalsIgnoreCase("1000");
		act.addContentNavigation(true,"","1", false, "", "", true);
		waitForTextNotPresent("Navigation Form");
		doubleClickOnElement(act.ELEMENT_SITE_PATH.replace("${title}", "All"));
		/*Step 2: Sort the Water element
		 *Input Data: 
		- Go to Site Explorer
		- Navigate under the path collaboration/site contents/live/acme/events/All 
		- Select the  Water  Node
		- Click on the button Content Navigation 
		- Set the field  Display order  to 2
		- Click on the button  Save 
		 *Expected Outcome: 
		- The window Navigation Form is displayed 
		- The field  Display order: 1000
		- The window  Navigation Form  disappears		*/
		info("Sort the Water element");
		
		/*nav.goToSiteExplorer();
		act.showDrives();
		waitForAndGetElement(act.ELEMENT_COLLABORATION_TAB);
		click(act.ELEMENT_COLLABORATION_TAB);		
		doubleClickOnElement(act.ELEMENT_SITE_PATH.replace("${title}", "sites"));
		doubleClickOnElement(act.ELEMENT_SITE_PATH.replace("${title}", "acme"));
		doubleClickOnElement(act.ELEMENT_SITE_PATH.replace("${title}", "events"));
		doubleClickOnElement(act.ELEMENT_SITE_PATH.replace("${title}", "All"));*/
		
		doubleClickOnElement(act.ELEMENT_SITE_PATH.replace("${title}", "Water"));
		click(act.ELEMENT_NAVIGATION_LINK);
		waitForTextPresent("Navigation Form");
		assert waitForAndGetElement(By.id("Node")).getAttribute("value").equalsIgnoreCase("Water");
		assert waitForAndGetElement(act.ELEMENT_NAVIGATION_DISPLAY_ORDER).getAttribute("value").equalsIgnoreCase("1000");
		act.addContentNavigation(true,"","2", false, "", "", true);
		waitForTextNotPresent("Navigation Form");		

		/*Step 3 : Sort the Air element
		 *Input Data: 
		- Go to Site Explorer
		- Navigate under the path collaboration/site contents/live/acme/events/All 
		- Select the  Air  Node
		- Click on the button Content Navigation 
		- Set the field  Display order  to 3
		- Click on the button  Save 
		 *Expected Outcome: 
		- The window Navigation Form is displayed 
		- The field  Display order: 1000
		- The window  Navigation Form  disappears		*/
		info("Sort the Air element");
		
		
		click(By.linkText("All"));
		click(By.linkText("Air"));
	
	
		click(act.ELEMENT_NAVIGATION_LINK);
		waitForTextPresent("Navigation Form");
		assert waitForAndGetElement(By.id("Node")).getAttribute("value").equalsIgnoreCase("air");
		assert waitForAndGetElement(act.ELEMENT_NAVIGATION_DISPLAY_ORDER).getAttribute("value").contains("1000");
		act.addContentNavigation(true,"","3", false, "", "", true);
		waitForTextNotPresent("Navigation Form");	

		/*Step 4: Check Elements
		 *Input Data: 
		- Go to Acme/Overview Hit the menu Events 
		 *Expected Outcome: 
		- The Sub menu Earth, Water and  Air are sorted		*/
		acme.goToOverviewPage();
		if(isElementPresent(By.linkText("Events")))
			click(By.linkText("Events"));
		else if(isElementPresent(By.linkText("Products")))
			click(By.linkText("Products"));
		waitForAndGetElement(By.linkText("Earth"));
		waitForAndGetElement(By.linkText("Water"));
		waitForAndGetElement(By.linkText("Air"));
	}

//	@Test
	public  void test02_EditTheNavigationForm_GetBackANode() {
		/*Step 1: Get back a node
		 *Input Data: 
		- Go to Site Explorer
		- Navigate under the path  collaboration/site contents/live/acme/events/All/Fire
		- Click on the button  Navigate Content 
		- Fill the fields as follow:
		- Visible: checked
		- Target parent navigation: News
		- Clickable: unchecked
		- Page for list: catalog
		- Page for detail: detail
		- Click on the button  Save 
		 *Expected Outcome: 
		- The window  Navigation Form  is displayed
		- The window  Navigation Form  disappears		*/
		info("Get back a node");
		Object[] paras = {true,"Authoring","contentNavigation"};
		nav.goToSiteExplorer();
		click(By.linkText("acme"));
		click(By.linkText("events"));
		click(By.linkText("All"));
		click(By.linkText("Fire"));
		
		if(isElementNotPresent(act.ELEMENT_NAVIGATION_LINK)){
			ecMain.goToManageViews();
			mnView.editView("Web", "", false, false, paras);
			click(act.ELEMENT_REFRESH_BUTTON);
			button.ok();
			nav.goToSiteExplorer();
			waitForAndGetElement(act.ELEMENT_NAVIGATION_LINK);
		}	
		click(act.ELEMENT_NAVIGATION_LINK);
		waitForTextPresent("Navigation Form");	
		assert waitForAndGetElement(By.id("Node")).getAttribute("value").equalsIgnoreCase("Fire");
		act.addContentNavigation(true, "news", "", false, "", "", true);
		/*Step 2: Check for the Fire node On News Menu
		 *Input Data: Go to Acme/Overview ,hit the menu News 
		 *Expected Outcome: 
		- The  Fire node is attached to menu News 		*/ 
		acme.goToOverviewPage();
		waitForAndGetElement(By.linkText("News"));
		click(By.linkText("News"));
		waitForTextPresent("Fire");
	}
	/**
	 * Case ID:74345.
	 * Test Case Name: Add a new content under  A sub menu.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2013/12/27 17:42:06
	 */
	@Test(groups="pending")
	public  void test03_AddANewContentUnderASubMenu() {
		info("Test 3: Add a new content under  A sub menu");
		String actName = "Add file 74345_test2 ";
		String actType = "exo:populateToMenu";
		String actCycle = "Content Addition";
		String fileUpload = "ECMS_Admin_ManageCategories_Display.jpg";
		
		/*Step 1 : Add a new content under A sub menu
		 *Input Data: 
		- Go to the Site explorer 
		- Navigate under the path: collaboration/site contents/live/acme/events/All/Fire 
		- Click on the button  Manage Action 
		- Go to the tab  Add Action 
		- Fill the fields as following:
		- Create Action of Type: exo:populateToMenu
		- Name: add to home page menu actionlifecycle: Content AdditionisDeep: checkedNavigationNode: newsclickable: checkedpage: catalogchildrenPage: detail
		- Click on the button  Save 
		- Click on the button  Upload 
		- Open the uploaded file
		- Click on the button Manage Publication 
		- Publish the document
		 *Expected Outcome: 
		- The window Manage Actions is displayed
		- The window  Manage Actions disappears
		- The window  Upload file is displayed
		- The document is published		*/
	
		acme.goToOverviewPage();
		nav.goToSiteExplorer();
		click(By.linkText("acme"));
		click(By.linkText("events"));
		click(By.linkText("All"));
		click(By.linkText("Fire"));
		
		act.goToAction();
		Utils.pause(500);
		act.addNewAction(actName, actCycle, actType);
		act.uploadFile("TestData/" +fileUpload);
	
		Utils.pause(500);
		waitForAndGetElement(By.linkText(fileUpload));
		ecMain.goToNode(By.linkText(fileUpload));
		act.publishDocument();
		
		/*Step 2: Check for the added new content under The sub menu
		 *Input Data: Go to Acme/overviewHit the menu  News 
		 *Expected Outcome: The document is added to the menu  Products */ 
		acme.goToOverviewPage();
		waitForAndGetElement(By.linkText("Products"));
		click(By.linkText("Products"));
		click(By.linkText("Fire"));
		waitForAndGetElement(By.linkText(fileUpload));
		//clean data
		nav.goToSiteExplorer();
		click(By.linkText("Fire"));
		act.goToAction();
		act.actionsOnActionsOfNode(actName,"Delete");		
	}

	/**
	 * Case ID:74346.
	 * Test Case Name: Create a new product.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2013/12/27 17:42:06
	 */
	@Test
	public  void test04_CreateANewProduct() {
		info("Test 4: Create a new product");
		String proName = "Product74346";
		String titleName = "NewProduct";
		String images = "Sniff_public_activity_08.jpg";
		String image2 = "ECMS_Admin_ManageCategories_Display.jpg";
		String video = "KS_Wiki_Attachment_AllMyLove.mp3";
		String pathContent = "General Drives/Collaboration/Sites Management/acme/documents/"+titleName ;
		
	
		/*Step 1 : Add/Save/ManageContent publication
		 *Input Data: 
		- Go to the Site explorer
		- Navigate under a valid path
		- Click on the button Add Content 
		- Choose the Products  template
		- Fill the following fields:Name:Title:Illustration image: Browse an imageSummary: Add a textBenefits: Add a textFeatures: Add a text
		- Click on the button Save 
		- Choose the button  Manage Publication 
		- Click on the option  Published 
		- Close
		 *Expected Outcome: 
		- The list of template is displayed
		- The document is saved with the  Draft status
		- The document is published		*/
		
		nav.goToSiteExplorer();
		click(By.linkText("acme"));
		click(By.linkText("documents"));
		
		act.goToAddNewContent();
		temCont.createFullNewProduct(titleName, Utils.getAbsoluteFilePath("TestData/"+images), proName, proName, proName);
		act.publishDocument();
		
		/*Step 2: Add media to the createdDocument
		 *Input Data: 
		- Under the created document
		- Add some images under images
		- Add some video under videos 
		- Publish all contents
		 *Expected Outcome: 
		- All contents added are published		*/
		
		info("Add images to the createdDocument");
		click(By.linkText(titleName));
		click(By.linkText("medias"));
		click(By.linkText("images"));
		act.uploadFile("TestData/" +image2);
		click(By.linkText(image2));
		act.publishDocument();
		click(By.linkText("videos"));
		act.uploadFile("TestData/" +video);
		click(By.linkText(video));
		act.publishDocument();

		nav.goToHomePage();
		
		waitForAndGetElement(By.linkText(titleName));
		waitForAndGetElement(By.linkText(image2));
		
		
		/*Step 3 : Add a page under Acme
		 *Input Data: 
		- Add a page under Acme
		- Drag and drop a content details
		- Click on the button  Finish
		- Edit the content details
		- Choose the content details
		- Click on the button  Save 
		 *Expected Outcome: 
		- A page is added under Acme
		- The content is published on the new page		*/ 
		info("Add page");
		String nodeName = "Node74346_test1";
		String displayName = "Node 74346";
		String title = "Content";
		Map<String, String> portletIds= new HashMap<String, String>();
		portletIds.put("Content/SingleContentViewer", "");

		acme.goToOverviewPage();
		nav.goToPageCreationWizard();
		Utils.pause(5000);
		click(ELEMENT_UP_LEVEL);
		mPage.addNewPageEditor(nodeName, displayName, "", title, portletIds, false, true);
		Utils.pause(5000);
		pEdit.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		pEdit.selectContentPath(pathContent);
		//		pEdit.selectContentPathInEditMode(contentPath, true);
		pEdit.finishEditLayout();
		waitForAndGetElement(By.linkText(titleName));
		

		//delete data
		nav.goToSiteExplorer();
		click(By.linkText("acme"));
		click(By.linkText("documents"));
		rightClickOnElement(By.linkText(titleName));
		click(By.xpath("//*[@class='uiContextMenuContainer']//i[@class='uiIconEcmsDelete']"));
		dialog.deleteInDialog();
		
		
		nav.goToManagePages();
		mPage.deletePage(PageType.PORTAL, nodeName);
		
	}
}