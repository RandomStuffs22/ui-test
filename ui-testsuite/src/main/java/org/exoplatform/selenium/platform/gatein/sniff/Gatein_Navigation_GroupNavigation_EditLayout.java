package org.exoplatform.selenium.platform.gatein.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.GroupNavigation;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 6 Aug 2013
 * @author lientm
 */

public class Gatein_Navigation_GroupNavigation_EditLayout extends GroupNavigation {
	
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
		
		magAc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 70563 + 70564 + 70565 -> Add, edit and remove application when edit layout of group's page
	 * 
	 */
	@Test
	public void test01_AddEditRemoveAppWhenEditLayout_GroupPage(){
		String pageName = "Sniffgroupnavigationlayout01";
		
		info("Add new page of group");
		navTool.goToSiteExplorer();
		pageE.createNewPageEmptyLayout(pageName);
		
		info("Add application when edit layout of page");
		navTool.goToEditPageEditor();
		click(ELEMENT_CATEGORY_CONTENT);
		dragAndDropToObject(ELEMENT_CONTENTS_LIST_VIEWER_PORTLET, ELEMENT_DROP_TARGET_NO_LAYOUT);
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_CLV_PORTLET);
		click(ELEMENT_SWITCH_VIEW_MODE);
		pageE.finishEditLayout();
		
		info("Edit application when edit layout of page");
		navTool.goToEditPageEditor();
		pageE.selectCLVPath("General Drives/Sites Management/acme", "documents");
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForTextPresent("offices.jpg");
		waitForTextPresent("metro.pdf");
		waitForTextPresent("conditions.doc");
		click(ELEMENT_SWITCH_VIEW_MODE);
		pageE.finishEditLayout();
		
		info("Remove application when edit layout of page");
		navTool.goToEditPageEditor();
		pageE.removePortlet(ELEMENT_FRAME_CONTAIN_PORTLET, ELEMENT_DELETE_PORTLET_ICON);
		waitForElementNotPresent(ELEMENT_CLV_PORTLET);
		
		info("Delete page");
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName, false, null, true, "Content Management");	
	}
	
	/**CaseId: 68875 -> Move application when edit layout for group's page
	 * 
	 */
	@Test
	public void test02_MoveAppWhenEditLayout_GroupPage(){
		String pageName = "Sniffgroupnavigationlayout02";
		String ELEMENT_PORTLET_1 = "//*[contains(@id, 'UIPortlet')][1]";
		String ELEMENT_PORTLET_2 = "//*[contains(@id, 'UIPortlet')][2]";
		
		info("Create new page with application");
		navTool.goToSiteExplorer();
		navTool.goToPageCreationWizard();
		Map<String, String> portal = new HashMap<String, String>();
		portal.put("Collaboration/AnswersPortlet", "Collaboration/WikiPortlet");
		pageMag.addNewPageEditor(pageName, "", null, "Collaboration", portal, false, false);

		navTool.goToEditPageEditor();
		waitForAndGetElement(ELEMENT_PORTLET_1 + "//div[contains(text(),'Wiki Portlet')]");
		mouseOver(ELEMENT_PORTLET_2, true);
		dragAndDropToObject(ELEMENT_PORTLET_2 + ELEMENT_PORTLET_DRAG_DROP_ICON, ELEMENT_PORTLET_1);
		waitForAndGetElement(ELEMENT_PORTLET_1 + "//div[contains(text(),'Answers Portlet')]");
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_PORTLET_2 + "//*[@id='UIWikiPortlet']");
		click(ELEMENT_SWITCH_VIEW_MODE);
		pageE.finishEditLayout();
		
		info("Delete page");
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName, false, null, true, "Content Management");	
	}
	
	/**CaseId: 70567 + 70568 + 68874 + 70569
	 * add, edit, move and delete container when edit layout of group's page
	 */
	@Test
	public void test03_AddEditMoveDeleteContainerWhenEditLayout_GroupPage(){
		String pageName = "Sniffgroupnavigationlayout03";
		String columnContainer = ELEMENT_COLUMN_CONTAINER;
		By rowContainer = By.xpath("//*[text()='Container']/../../../..");
		String title = "Container1";

		navTool.goToSiteExplorer();
		pageE.createNewPageEmptyLayout(pageName);
		navTool.goToEditPageEditor();
		
		info("Add container");
//		pageE.addNewContainer("Rows Layout", "oneRow");
		pageE.addNewContainer("Columns Layout", "twoColumns");
		waitForAndGetElement(columnContainer);
//		waitForAndGetElement(rowContainer);
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(columnContainer);
//		waitForAndGetElement(rowContainer);
		click(ELEMENT_SWITCH_VIEW_MODE);

		info("Edit title of container");
		mouseOver(columnContainer, true);
		Utils.pause(10000);
		click(ELEMENT_CONTAINER_COLUMN_EDIT_ICON);
		type(ELEMENT_CONTAINER_TITLE, title, true);
		but.save();
		mouseOver(columnContainer, true);
		waitForAndGetElement(columnContainer + "/ancestor::div//span[text()='" + title + "']");
		
		info("Move container");
		pageE.addNewContainer("Rows Layout", "oneRow");
		
		mouseOver(columnContainer, true);
		dragAndDropToObject(columnContainer + "/ancestor::div//*[@title='Hold this area to drag this table']", rowContainer);
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement("//*[@class='UIRowContainer']//span[text()='" + title + "']/../../../.." + columnContainer);
		
		info("Delete container");
		pageE.removeContainer(ELEMENT_DROP_TARGET_HAS_LAYOUT, ELEMENT_DELETE_CONTAINER_ICON);
		waitForElementNotPresent(ELEMENT_DROP_TARGET_HAS_LAYOUT);	
		pageE.finishEditLayout();
		
		info("Delete page");
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName, false, null, true, "Content Management");	
	}
	
	/**CaseId: 68873 -> View page properties when editing layout for group's page
	 * 
	 */
	@Test
	public void test04_ViewPropertiesWhenEditLayout_GroupPage(){
		String pageName = "Sniffgroupnavigationlayout04";
		String newtitle = "Newtitlepage04";
		
		navTool.goToSiteExplorer();
		pageE.createNewPageEmptyLayout(pageName);
		navTool.goToEditPageEditor();
		
		info("View page's properties");
		click(pageE.ELEMENT_VIEW_PAGE_PROPERTIES);
		waitForAndGetElement(pageE.ELEMENT_OWNERTYPE_SELECTED.replace("${ownerType}", "group"));
		waitForAndGetElement("//*[@id='title' and @value = '" + pageName + "']");
		
		info("Edit properties of page");
		type(pageE.ELEMENT_VIEWPAGE_PAGETITLE, newtitle, true);
		click(ELEMENT_PERMISSION_SETTING_TAB);
		click(ELEMENT_EDIT_PERMISSION_SETTING);
		setEditPermissions("Development ", "*");
		but.save();
		waitForElementNotPresent(ELEMENT_EDIT_PERMISSION_SETTING);
		pageE.finishEditLayout();
		
		navTool.goToEditPageEditor();
		click(pageE.ELEMENT_VIEW_PAGE_PROPERTIES);
		waitForAndGetElement("//*[@id='title' and @value = '" + newtitle + "']");
		click(ELEMENT_PERMISSION_SETTING_TAB);
		click(ELEMENT_EDIT_PERMISSION_SETTING);
		waitForTextPresent("/developers");
		but.cancel();
		waitForElementNotPresent(ELEMENT_EDIT_PERMISSION_SETTING);
		pageE.finishEditLayout();
		
		info("Delete page");
		pageMag.deletePageAtManagePageAndPortalNavigation(newtitle, false, null, true, "Content Management", pageName);
	}
}
