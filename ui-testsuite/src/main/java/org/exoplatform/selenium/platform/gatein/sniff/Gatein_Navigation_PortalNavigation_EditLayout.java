package org.exoplatform.selenium.platform.gatein.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PortalManagement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 2 Aug 2013
 * @author lientm
 */

public class Gatein_Navigation_PortalNavigation_EditLayout extends PortalManagement {
	
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
	
	/**CaseId: 68876
	 * Add application into container when edit layout for portal's page
	 */
	@Test
	public void test01_AddAppToContainer_LayoutOfPortalPage(){
		String pageName = "Sniffportalnavigation01";
		String category = "Collaboration";
		String portletId = "Collaboration/AnswersPortlet";
		
		info("Create page for portal with empty layout");
		pageE.createNewPageEmptyLayout(pageName);
		
		info("Go to page edit layout");
		navTool.goToEditPageEditor();
		
		info("Add new container and application to page layout");
		pageE.addNewContainerAndPortlet("Rows Layout", "oneRow", category, portletId, false);
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_ANWSER_PORTLET_IN_VIEW_PAGE);
		click(ELEMENT_SWITCH_VIEW_MODE);
		pageE.finishEditLayout();
		
		info("Delete page");
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName, true, "intranet", false, null);
	}
	
	/**CaseId: 68888 -> Change site's config of portal
	 * 
	 */
	@Test
	public void test02_ChangeSiteConfigOfPortal_FromEditLayout(){
		String portalName = "Sniffportalnavigation02";
		String label = "sniff portal lable 02";
		String description = "sniff portal descriptrion 02";
		
		info("Add new portal");
		navTool.goToPortalSites();
		addNewPortal(portalName, null, null, null, null, "On Demand", true, null, "Platform /Content Management ", "*");
		waitForAndGetElement(ELEMENT_PORTAL.replace("${siteName}", portalName));
		
		info("Edit layout of portal");
		goToPortalEditLayout(portalName);
		click(ELEMENT_SWITCH_PORTAL_CONFIG);
		
		info("Config portal");	
		configPortal(null, label, description, "French", null, "Always", true, null, "Development ", "member");
		pageE.finishEditLayout();
		
		info("Check config portal successfully");
		goToEditSiteConfiguration(portalName);
		assert getValue(ELEMENT_PORTAL_LABEL).equalsIgnoreCase(label);
		assert getValue(ELEMENT_PORTAL_DESCRIPTION).equalsIgnoreCase(description);
		click(ELEMENT_PERMISSION_SETTING_TAB);
		click(ELEMENT_EDIT_PERMISSION_SETTING);
		waitForTextPresent("/developers");
		but.cancel();
		Utils.pause(1000);
		
		deletePortal(portalName);
	}
	
	/**CaseId: 68891 -> Add application into container in layout of portal
	 * 
	 */
	@Test
	public void test03_AddAppToContainer_LayoutOfPortal(){
		String portalName = "Sniffportalnavigation03";
		
		info("Add new portal");
		navTool.goToPortalSites();
		addNewPortal(portalName, null, null, null, null, "On Demand", true, null, "Platform /Content Management ", "*");
		waitForAndGetElement(ELEMENT_PORTAL.replace("${siteName}", portalName));
		
		goToPortalEditLayout(portalName);
		click(ELEMENT_CATEGORY_COLLABORATION);
		dragAndDropToObject(ELEMENT_ANWSER_PORTLET, By.xpath("//*[text() = 'Portal Page']/.."));
		waitForAndGetElement("//*[@class='portletLayoutDecorator' and contains(text(),'Answers Portlet')]");
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		waitForAndGetElement(ELEMENT_ANWSER_PORTLET_IN_VIEW_PAGE);
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		pageE.finishEditLayout();
		
		deletePortal(portalName);
	}
	
	/**CaseId 68892 -> Edit portal's config
	 * 
	 */
	@Test
	public void test04_EditPortalConfig(){
		String portalName = "Sniffportalnavigation04";
		String label = "sniff portal lable 04";
		String description = "sniff portal descriptrion 04";
		
		info("Add new portal");
		navTool.goToPortalSites();
		addNewPortal(portalName, null, null, null, null, "On Demand", true, null, "Platform /Content Management ", "*");
		waitForAndGetElement(ELEMENT_PORTAL.replace("${siteName}", portalName));
		
		info("Config portal");
		editPortal(portalName, label, description, "French", null, "Always", true, null, "Development ", "member");
		Utils.pause(1000);
		
		info("Check config portal successfully");
		goToEditSiteConfiguration(portalName);
		assert getValue(ELEMENT_PORTAL_LABEL).equalsIgnoreCase(label);
		assert getValue(ELEMENT_PORTAL_DESCRIPTION).equalsIgnoreCase(description);
		click(ELEMENT_PERMISSION_SETTING_TAB);
		click(ELEMENT_EDIT_PERMISSION_SETTING);
		waitForTextPresent("/developers");
		but.cancel();
		Utils.pause(1000);
		
		deletePortal(portalName);
	}
	
	/**CaseId: 70587 -> Move application when edit layout for portal's page
	 * 
	 */
	@Test
	public void test05_MoveAppWhenEditLayout_PortalPage(){
		String pageName = "Sniffportalnavigation05";
		String ELEMENT_PORTLET_1 = "//*[contains(@id, 'UIPortlet')][1]";
		String ELEMENT_PORTLET_2 = "//*[contains(@id, 'UIPortlet')][2]";
		
		info("Create new page with application");
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
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName, true, "intranet", false, null);	
	}
	
	/**CaseId: 70588 + 70589 + 70590 -> Add, edit, remove application when edit layout for portal's page
	 * 
	 */
	@Test
	public void test06_AddEditRemoveAppWhenEditLayout_PortalPage() {
		String pageName = "Sniffportalnavigation06";
		
		info("Create new page of portal with empty layout and applications");
		pageE.createNewPageEmptyLayout(pageName);
		
		info("Add application when edit layout of page");
		navTool.goToEditPageEditor();
		click(By.linkText("Content"));
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
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName, true, "intranet", false, null);	
	}
	
	/**CaseId: 70593 + 70594 + 70592 + 70595 
	 * Add container when edit layout for portal's page
	 * edit container when edit layout for portal's page
	 * move container when edit layout for portal's page
	 * delete container when edit layout for portal's page
	 */
	@Test
	public void test07_MoveContainerWhenEditLayout_PortalPage(){
		String pageName = "Sniffportalnavigation07";
		String title = "Container1";
		String newContainerPos = "//*[@class='UIRowContainer']/div[2]";
		
		pageE.createNewPageEmptyLayout(pageName);
		navTool.goToEditPageEditor();
		
		info("Add container");
		pageE.addNewContainer("Rows Layout", "oneRow");
		waitForAndGetElement(ELEMENT_DROP_TARGET_HAS_LAYOUT);

		info("Edit title of container");
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		click(ELEMENT_EDIT_CONTAINER_ICON);
		type(ELEMENT_CONTAINER_TITLE, title, true);
		type(ELEMENT_WIDTH_TEXTBOX, "150px", true);
		type(ELEMENT_HEIGHT_TEXTBOX, "150px", true);
		but.save();
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		if(this.plfVersion.contains("4.0"))
			waitForAndGetElement(ELEMENT_NAME_CONTAINER.replace("${nameContainer}", title));
		else
			waitForAndGetElement(ELEMENT_NAME_CONTAINER_PLF41.replace("${nameContainer}", title));
		WebElement element = waitForAndGetElement(ELEMENT_EDITING_CONTAINER);
		String valueStyle = element.getAttribute("style");
		assert valueStyle.equals("width: 150px; height: 150px;"): "Failed to edit the value of container: " + title;
		
		info("Move container");
		pageE.addNewContainer("Rows Layout", "oneRow");
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", "Container"), DEFAULT_TIMEOUT, 1, 2);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "2").replace("${nameContainer}", title), DEFAULT_TIMEOUT, 1, 2);
		mouseOver(ELEMENT_NAME_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), true);
		if(this.plfVersion.contains("4.0"))
			dragAndDropToObject(ELEMENT_DRAG_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), ELEMENT_PORTLET_LAYOUT_DECORATOR);
		else
			dragAndDropToObject(ELEMENT_DRAG_CURRENT_CONTAINER_PLF41.replace("${nameContainer}", "Container"), newContainerPos);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", title), DEFAULT_TIMEOUT, 1, 2);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", "Container"), DEFAULT_TIMEOUT, 1, 2);
		click(ELEMENT_ABORTEDIT_BUTTON);
		click(pageE.ELEMENT_CONFIRM_YES_BUTTON);
		waitForElementNotPresent(pageE.ELEMENT_VIEW_PAGE_PROPERTIES);
		
		info("Delete container");
		navTool.goToEditPageEditor();
		pageE.addNewContainer("Rows Layout", "oneRow");
		pageE.removeContainer(ELEMENT_NAME_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), ELEMENT_DELETE_CONTAINER_ICON);
		pageE.finishEditLayout();	
		waitForElementNotPresent(pageE.ELEMENT_VIEW_PAGE_PROPERTIES);
		
		info("Delete page");
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName, true, "intranet", false, null);	
	}
}
