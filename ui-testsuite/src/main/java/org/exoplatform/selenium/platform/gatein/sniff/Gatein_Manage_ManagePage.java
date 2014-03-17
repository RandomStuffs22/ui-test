package org.exoplatform.selenium.platform.gatein.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.DashBoard;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 25 July 2013
 * @author lientm
 */
public class Gatein_Manage_ManagePage extends DashBoard {
	
	ManageAccount magAc;
	NavigationToolbar navTool;
	PageManagement pageMag;
	PageEditor pageE;
	Button but;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		pageMag = new PageManagement(driver);
		pageE = new PageEditor(driver);
		but = new Button(driver);
		
		magAc.signIn("john", DATA_PASS);
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
		navTool.goToManagePages();
		
		info("Search page with Type");
		pageMag.searchPageInManagementPage(PageType.PORTAL, null, true);
		//waitForAndGetElement("//*[contains(text(), 'portal::intranet::Register')]");
		waitForElementNotPresent("//*[contains(text(), 'group::/')]");
		
		info("Search page with Type and Title");
		pageMag.searchPageInManagementPage(PageType.GROUP, "Site Map", true);
		waitForAndGetElement("//*[contains(text(), 'group::/platform/guests::sitemap')]");
		
		info("Search page with Type and Site");
		pageMag.searchPageInManagementPage(PageType.PORTAL, "", true, "acme");
		waitForAndGetElement("//*[contains(text(), 'portal::acme::overview')]");
		waitForElementNotPresent("//*[contains(text(), 'portal::intranet::')]");
		
		info("Search page with Title, Site name, Type");
		pageMag.searchPageInManagementPage(PageType.PORTAL, "Register", true, "intranet");
		waitForAndGetElement("//*[contains(text(), 'portal::intranet::Register')]");
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
	}
	
	/**CaseId: 68893 + 68862 -> Add page for portal from Wizard and edit page from Edit -> Page -> Layout
	 * 
	 */
	@Test
	public void test03_AddEditPageForPortal_WiZard(){
		String nodeName = "SniffManagePageName03";
		
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
	}
	
	/**CaseId: 70420 + 70419 + 68853 -> Add, edit and delete page for group in Page management
	 * 
	 */
	@Test
	public void test04_AddEditDeletePageForGroup_InPageManagement(){
		String pageName = "SniffManagePageName04";
		String pageTitle = "SniffManagePageTitle04";
		String ownerId = "/organization/management/executive-board";
		String groupPath = "Platform /Content Management ";
		String membership = "*";
		
		navTool.goToManagePages();
		
		info("Add page for portal");
		pageMag.addNewPageAtManagePages(PageType.GROUP, pageName, pageTitle, true, null, groupPath, membership, "Page Configs", ELEMENT_PAGE_LAYOUT_OPTION_EMPTY, true, ownerId);
		
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
	}
	
	/**CaseId: 68871 + 68872 -> Add new page for group by Wizard and edit page
	 * 
	 */
	@Test
	public void test05_AddEditPageForGroup_Wizard(){
		String nodeName = "SniffManagePageName05";
		
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
	}
	
	/**CaseId: 68856 + 68857 -> Add new page and edit page for user
	 * 
	 */
	@Test
	public void test06_AddPageForUser(){
		String nodeName = "SniffManagePageName06";
		String displayName = "SniffManagePage06";
		
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
	}
	
	public void editCLVPortletAndSwitchViewMode(){
		pageE.selectCLVPath("General Drives/Sites Management/acme", "documents");
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
}
