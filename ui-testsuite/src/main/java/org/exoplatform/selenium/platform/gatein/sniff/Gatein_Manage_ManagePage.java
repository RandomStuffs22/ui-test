package org.exoplatform.selenium.platform.gatein.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PortalManagement;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 25 July 2013
 * @author lientm
 */
public class Gatein_Manage_ManagePage extends PortalManagement {
	
	ManageAccount magAc;
	NavigationToolbar navTool;
	PageManagement pageMag;
	PageEditor pageE;
	Button but;
	ActionBar actBar;
	EcmsBase ecms;
	ContextMenu cMenu;
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver,this.plfVersion);
		navTool = new NavigationToolbar(driver,this.plfVersion);
		pageMag = new PageManagement(driver,this.plfVersion);
		pageE = new PageEditor(driver,this.plfVersion);
		but = new Button(driver,this.plfVersion);
		actBar = new ActionBar(driver,this.plfVersion);
		ecms = new EcmsBase(driver,this.plfVersion);
		cMenu= new ContextMenu(driver,this.plfVersion);
		magAc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 68851 -> Show and Search page
	 * Search Page with title, site name, type
	 * 
	 */
	@Test
	public void test01_ShowAndSearchPage(){
		String portalName = "portal68851";
		
		info("Add new portal");
		Map<String, String> permissions = null;
		String editGroupId = "Platform /Content Management ";
		String editMembership = "*" ;
		navTool.goToPortalSites();
		addNewPortal(portalName, null, null, "French", null, "Always", true, permissions, editGroupId, editMembership);
		
		navTool.goToManagePages();
		
		info("Search page with Type");
		pageMag.searchPageInManagementPage(PageType.PORTAL, null, true);
		//waitForAndGetElement("//*[contains(text(), 'portal::intranet::Register')]");
		waitForElementNotPresent("//*[contains(text(), 'group::/')]");
		
		info("Search page with Type and Title");
		pageMag.searchPageInManagementPage(PageType.GROUP, "Site Map", true);
		waitForAndGetElement("//*[contains(text(), 'group::/platform/guests::sitemap')]");
		
		info("Search page with Type and Site");
		pageMag.searchPageInManagementPage(PageType.PORTAL, "", true, "portal68851");
		waitForAndGetElement("//*[contains(text(), 'portal::"+portalName+"::overview')]");
		waitForElementNotPresent("//*[contains(text(), 'portal::intranet::')]");
		
		info("Search page with Title, Site name, Type");
		pageMag.searchPageInManagementPage(PageType.PORTAL, "Register", true, "intranet");
		waitForAndGetElement("//*[contains(text(), 'portal::intranet::Register')]");
		
		info("Delete portal");
		driver.get(baseUrl);
		navTool.goToPortalSites();
		deletePortal(portalName);
	}
	
	/**CaseId: 68852 + 68862 + 70422 -> Add + edit + delete page of portal using Page Management
	 * 
	 */
	@Test
	public void test02_AddEditDeletePageForPortal_InPageManagement(){
		String pageName = "SniffManagePageName02";
		String pageTitle = "SniffManagePageTitle02";
		String groupPath = "Platform /Content Management ";
		String membership = "*";
		String uploadFileName1 = "offices.jpg";
		String uploadFileName2 = "metro.pdf";
		String uploadFileName3 = "conditions.doc";
		
		info("-- Upload file --");
		navTool.goToSiteExplorer();
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		ecms.uploadFile("TestData/"+uploadFileName1);
		ecms.uploadFile("TestData/"+uploadFileName2);
		ecms.uploadFile("TestData/"+uploadFileName3);
		actBar.goToNode(By.linkText(uploadFileName1));
		actBar.publishDocument();
		actBar.goToNode(By.linkText(uploadFileName2));
		actBar.publishDocument();
		actBar.goToNode(By.linkText(uploadFileName3));
		actBar.publishDocument();
		
		navTool.goToManagePages();
		
		info("Add page for portal");
		pageMag.addNewPageAtManagePages(PageType.PORTAL, pageName, pageTitle, true, null, groupPath, membership, "Page Configs", ELEMENT_PAGE_LAYOUT_OPTION_EMPTY, true);
		
		info("Edit page");
		pageMag.editPageAtManagePages(PageType.PORTAL, pageTitle);
		info("Add container and portlet");
		pageE.addNewContainerAndPortlet("Rows Layout", "oneRow", "Content", "Content/ContentListViewerPortlet");
		
		info("Edit portlet of page");
		pageMag.editPageAtManagePages(PageType.PORTAL, pageTitle);
		editCLVPortletAndSwitchViewMode();
		
		info("View properties of page");
		viewPropertiesPage("portal", pageTitle);
		pageE.finishEditLayout();
		
		info("Delete page");
		pageMag.deletePage(PageType.PORTAL, pageTitle);
		
		info("clear data");
		navTool.goToSiteExplorer();
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName1));
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName2));	
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName3));	
	}
	
	/**CaseId: 68893 + 68862 -> Add page for portal from Wizard and edit page from Edit -> Page -> Layout
	 * 
	 */
	@Test
	public void test03_AddEditPageForPortal_WiZard(){
		String nodeName = "SniffManagePageName03";
		String uploadFileName1 = "offices.jpg";
		String uploadFileName2 = "metro.pdf";
		String uploadFileName3 = "conditions.doc";
		
		info("-- Upload file --");
		navTool.goToSiteExplorer();
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		ecms.uploadFile("TestData/"+uploadFileName1);
		ecms.uploadFile("TestData/"+uploadFileName2);
		ecms.uploadFile("TestData/"+uploadFileName3);
		actBar.goToNode(By.linkText(uploadFileName1));
		actBar.publishDocument();
		actBar.goToNode(By.linkText(uploadFileName2));
		actBar.publishDocument();
		actBar.goToNode(By.linkText(uploadFileName3));
		actBar.publishDocument();
		
		navTool.goToPageCreationWizard();
		Map<String, String> portal = new HashMap<String, String>();
		portal.put("Content/ContentListViewerPortlet", "");
		pageMag.addNewPageEditor(nodeName, "", null, "Content", portal, false, false);
		
		info("Edit page of portal");
		navTool.goToEditPageEditor();
		
		info("Edit portlet and switch view mode");
		editCLVPortletAndSwitchViewMode();
		
		info("View page properties");
		viewPropertiesPage("portal", nodeName);
		pageE.finishEditLayout();
		
		info("Delete portlet");
		navTool.goToEditPageEditor();
		pageE.removePortlet(ELEMENT_FRAME_CONTAIN_PORTLET, ELEMENT_DELETE_PORTLET_ICON);
		waitForTextNotPresent("offices.jpg");
		
		info("Add new container and portlet");
		navTool.goToEditPageEditor();
		pageE.addNewContainerAndPortlet("Rows Layout", "oneRow", "Collaboration", "Collaboration/AnswersPortlet");
		waitForAndGetElement(By.id("UIAnswersPortlet"));
		
		info("Delete page");
		pageMag.deletePageAtManagePageAndPortalNavigation(nodeName, true, "intranet", false, null);
		
		info("clear data");
		navTool.goToSiteExplorer();
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName1));
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName2));	
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName3));	
	}
	
	/**CaseId: 70420 + 70419 + 68853 -> Add, edit and delete page for group in Page management
	 * 
	 */
	@Test
	public void test04_AddEditDeletePageForGroup_InPageManagement(){
		String pageName = "SniffManagePageName04";
		String pageTitle = "SniffManagePageTitle04";
		//String ownerId = "/organization/management/executive-board";
		String groupPath = "Platform /Content Management ";
		String membership = "*";
		String uploadFileName1 = "offices.jpg";
		String uploadFileName2 = "metro.pdf";
		String uploadFileName3 = "conditions.doc";
		
		info("-- Upload file --");
		navTool.goToSiteExplorer();
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		ecms.uploadFile("TestData/"+uploadFileName1);
		ecms.uploadFile("TestData/"+uploadFileName2);
		ecms.uploadFile("TestData/"+uploadFileName3);
		actBar.goToNode(By.linkText(uploadFileName1));
		actBar.publishDocument();
		actBar.goToNode(By.linkText(uploadFileName2));
		actBar.publishDocument();
		actBar.goToNode(By.linkText(uploadFileName3));
		actBar.publishDocument();
		
		navTool.goToManagePages();
		
		info("Add page for portal");
		pageMag.addNewPageAtManagePages(PageType.GROUP, pageName, pageTitle, true, null, groupPath, membership, "Page Configs", ELEMENT_PAGE_LAYOUT_OPTION_EMPTY, true);
		
		info("Edit page");
		pageMag.editPageAtManagePages(PageType.GROUP, pageTitle);
		info("Add container and portlet");
		pageE.addNewContainerAndPortlet("Rows Layout", "oneRow", "Content", "Content/ContentListViewerPortlet");
		
		info("Edit portlet of page");
		pageMag.editPageAtManagePages(PageType.GROUP, pageTitle);
		editCLVPortletAndSwitchViewMode();
		
		info("View properties of page");
		viewPropertiesPage("group", pageTitle);
		pageE.finishEditLayout();
		
		pageMag.deletePage(PageType.GROUP, pageTitle);
		
		info("clear data");
		navTool.goToSiteExplorer();
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName1));
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName2));	
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName3));	
	}
	
	/**CaseId: 68871 + 68872 -> Add new page for group by Wizard and edit page
	 * 
	 */
	@Test
	public void test05_AddEditPageForGroup_Wizard(){
		String nodeName = "SniffManagePageName05";
		String uploadFileName1 = "offices.jpg";
		String uploadFileName2 = "metro.pdf";
		String uploadFileName3 = "conditions.doc";
		
		info("-- Upload file --");
		navTool.goToSiteExplorer();
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		ecms.uploadFile("TestData/"+uploadFileName1);
		ecms.uploadFile("TestData/"+uploadFileName2);
		ecms.uploadFile("TestData/"+uploadFileName3);
		actBar.goToNode(By.linkText(uploadFileName1));
		actBar.publishDocument();
		actBar.goToNode(By.linkText(uploadFileName2));
		actBar.publishDocument();
		actBar.goToNode(By.linkText(uploadFileName3));
		actBar.publishDocument();
		
		navTool.goToSiteExplorer();
		navTool.goToPageCreationWizard();
		Map<String, String> portal = new HashMap<String, String>();
		portal.put("Content/ContentListViewerPortlet", "");
		pageMag.addNewPageEditor(nodeName, "", null, "Content", portal, false, false);
		
		info("Edit page of group");
		navTool.goToEditPageEditor();
		
		info("Edit portlet and switch view mode");
		editCLVPortletAndSwitchViewMode();
		
		info("View page properties");
		viewPropertiesPage("group", nodeName);
		pageE.finishEditLayout();
		
		info("Delete portlet");
		navTool.goToEditPageEditor();
		pageE.removePortlet(ELEMENT_FRAME_CONTAIN_PORTLET, ELEMENT_DELETE_PORTLET_ICON);
		waitForTextNotPresent("offices.jpg");
		
		info("Add new container and portlet");
		navTool.goToEditPageEditor();
		pageE.addNewContainerAndPortlet("Rows Layout", "oneRow", "Collaboration", "Collaboration/AnswersPortlet");
		waitForAndGetElement(By.id("UIAnswersPortlet"));
		
		info("Delete page");
		pageMag.deletePageAtManagePageAndPortalNavigation(nodeName, false, null, true, "Content Management");
		
		info("clear data");
		navTool.goToSiteExplorer();
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName1));
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName2));	
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName3));	
	}
	
	/**CaseId: 68856 + 68857 -> Add new page and edit page for user
	 * 
	 */
	@Test
	public void test06_AddPageForUser(){
		String nodeName = "SniffManagePageName06";
		String displayName = "SniffManagePage06";
		String uploadFileName1 = "offices.jpg";
		String uploadFileName2 = "metro.pdf";
		String uploadFileName3 = "conditions.doc";
		
		info("-- Upload file --");
		navTool.goToSiteExplorer();
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		ecms.uploadFile("TestData/"+uploadFileName1);
		ecms.uploadFile("TestData/"+uploadFileName2);
		ecms.uploadFile("TestData/"+uploadFileName3);
		actBar.goToNode(By.linkText(uploadFileName1));
		actBar.publishDocument();
		actBar.goToNode(By.linkText(uploadFileName2));
		actBar.publishDocument();
		actBar.goToNode(By.linkText(uploadFileName3));
		actBar.publishDocument();
		
		navTool.goToDashboard();
		
		info("Add Page for user");
		navTool.goToPageCreationWizard();
		Map<String, String> portal = new HashMap<String, String>();
		portal.put("Content/ContentListViewerPortlet", "");
		pageMag.addNewPageEditor(nodeName, displayName, null, "Content", portal, false, false);
		
		info("Edit page of user");
		navTool.goToEditPageEditor();
		
		info("Edit portlet and switch view mode");
		editCLVPortletAndSwitchViewMode();
		
		info("View page properties");
		viewPropertiesPage("user", nodeName);
		pageE.finishEditLayout();
		
		info("Delete portlet");
		navTool.goToEditPageEditor();
		pageE.removePortlet(ELEMENT_FRAME_CONTAIN_PORTLET, ELEMENT_DELETE_PORTLET_ICON);
		waitForTextNotPresent("offices.jpg");
		
		info("Add new container and portlet");
		navTool.goToEditPageEditor();
		pageE.addNewContainerAndPortlet("Rows Layout", "oneRow", "Collaboration", "Collaboration/AnswersPortlet");
		waitForAndGetElement(By.id("UIAnswersPortlet"));
		
		info("Delete Page");
		deleteTabOnDashboard(nodeName);
		
		info("clear data");
		navTool.goToSiteExplorer();
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName1));
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName2));	
		actBar.goToNode(By.linkText("intranet"));
		actBar.goToNode(By.linkText("documents"));
		cMenu.deleteData(By.linkText(uploadFileName3));	
	}
	
	public void editCLVPortletAndSwitchViewMode(){
		pageE.selectCLVPath("General Drives/Sites Management/intranet", "documents");
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForTextPresent("offices.jpg");
		waitForTextPresent("metro.pdf");
		waitForTextPresent("conditions.doc");
		click(ELEMENT_SWITCH_VIEW_MODE);
	}
	
	public void viewPropertiesPage(String ownerType, String nodeName){
		click(pageE.ELEMENT_VIEW_PAGE_PROPERTIES);
		waitForAndGetElement(pageE.ELEMENT_OWNERTYPE_SELECTED.replace("${ownerType}", ownerType));
		waitForAndGetElement("//*[@id='title' and @value = '" + nodeName + "']");
		but.cancel();
	}
	public void deleteTabOnDashboard(String currentName){
		alert = new ManageAlert(driver);
		info("--Delete selected page on dashboard--");
		WebElement tab = waitForAndGetElement(By.linkText(currentName), 10000, 0);

		if(tab != null){ 
			mouseOverAndClick(By.linkText(currentName));
		}
		click("//*[@id='UITabPaneDashboard']//*[@class='uiIconClose uiIconLightGray']");
		alert.waitForConfirmation("Really want to remove this dashboard?");
		alert.acceptAlert();
		waitForElementNotPresent("//*[@id='UITabPaneDashboard']//*[text()='${tabName}']".replace("${tabName}", currentName));
	}
}
