package org.exoplatform.selenium.platform.gatein.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.GroupNavigation;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.openqa.selenium.WebElement;
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
		magAc = new ManageAccount(driver, this.plfVersion);
		navTool = new NavigationToolbar(driver, this.plfVersion);
		pageMag = new PageManagement(driver, this.plfVersion);
		pageE = new PageEditor(driver, this.plfVersion);
		but = new Button(driver, this.plfVersion);
		
		magAc.signIn("john", DATA_PASS);
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
		if(this.plfVersion.contains("4.0"))
			dragAndDropToObject(ELEMENT_PORTLET_2 + ELEMENT_PORTLET_DRAG_DROP_ICON, ELEMENT_PORTLET_1);
		else
			dragAndDropToObject(ELEMENT_PORTLET_2 + ELEMENT_PORTLET_DRAG_DROP_ICON_PLF41, ELEMENT_PORTLET_1);
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
		String containerTitle = "test06ContainerTitle";
		String newContainerPos = "//*[@class='UIRowContainer']/div[2]";
		
		navTool.goToSiteExplorer();
		pageE.createNewPageEmptyLayout(pageName);
		navTool.goToEditPageEditor();
		
		info("Add a container...");
		click(ELEMENT_SWITCH_VIEW_MODE);
		pageE.addNewContainer("Rows Layout", "oneRow");
		waitForAndGetElement(ELEMENT_DROP_TARGET_HAS_LAYOUT);

		info("Edit a container...");
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		click(ELEMENT_EDIT_CONTAINER_ICON);
		type(ELEMENT_CONTAINER_TITLE, containerTitle, true);
		type(ELEMENT_WIDTH_TEXTBOX, "150px", true);
		type(ELEMENT_HEIGHT_TEXTBOX, "150px", true);
		but.save();
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		if(this.plfVersion.contains("4.0"))
			waitForAndGetElement(ELEMENT_NAME_CONTAINER.replace("${nameContainer}", containerTitle));
		else
			waitForAndGetElement(ELEMENT_NAME_CONTAINER_PLF41.replace("${nameContainer}", containerTitle));
		WebElement element = waitForAndGetElement(ELEMENT_EDITING_CONTAINER);
		String valueStyle = element.getAttribute("style");
		assert valueStyle.equals("width: 150px; height: 150px;"): "Failed to edit the value of container: " + containerTitle;

		info("Move a container...");
		pageE.addNewContainer("Rows Layout", "oneRow");
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", "Container"), DEFAULT_TIMEOUT, 1, 2);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "2").replace("${nameContainer}", containerTitle), DEFAULT_TIMEOUT, 1, 2);

		mouseOver(ELEMENT_NAME_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), true);
		if(this.plfVersion.contains("4.0"))
			dragAndDropToObject(ELEMENT_DRAG_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), ELEMENT_PORTLET_LAYOUT_DECORATOR);
		else
			dragAndDropToObject(ELEMENT_DRAG_CURRENT_CONTAINER_PLF41.replace("${nameContainer}", "Container"), newContainerPos);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", containerTitle), DEFAULT_TIMEOUT, 1, 2);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", "Container"), DEFAULT_TIMEOUT, 1, 2);
		click(ELEMENT_ABORTEDIT_BUTTON);
		click(pageE.ELEMENT_CONFIRM_YES_BUTTON);
		waitForElementNotPresent(pageE.ELEMENT_VIEW_PAGE_PROPERTIES);
		
		info("Delete a container...");
		navTool.goToEditPageEditor();
		pageE.addNewContainer("Rows Layout", "oneRow");
		pageE.removeContainer(ELEMENT_NAME_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), ELEMENT_DELETE_CONTAINER_ICON);
		pageE.finishEditLayout();	
		waitForElementNotPresent(pageE.ELEMENT_VIEW_PAGE_PROPERTIES);
		
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
