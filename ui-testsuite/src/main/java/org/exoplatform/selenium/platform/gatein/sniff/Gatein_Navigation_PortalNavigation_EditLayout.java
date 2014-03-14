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
	
	/**CaseId: 68876
	 * Add application into container when edit layout for portal's page
	 */
	@Test
	public void test01_AddAppToContainer_LayoutOfPortalPage(){
		String pageName = "Sniffportalnavigation01";
		String category = "Collaboration";
		String portletId = "Collaboration/AnswersPortlet";
		
		info("Create page for portal with empty layout");
		navTool.goToPageCreationWizard();
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
		mouseOver("//div[contains(text(),'Answers Portlet')]",true);
		waitForAndGetElement("//div[@class='CONTROL-PORTLET CONTROL-BLOCK uiInfoBar']/span[text()='Answers Portlet']");
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
		dragAndDropToObject(ELEMENT_PORTLET_2 + ELEMENT_PORTLET_DRAG_DROP_ICON, ELEMENT_PORTLET_1);
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
		String columnContainer = ELEMENT_COLUMN_CONTAINER;
		By rowContainer = By.xpath("//*[text()='Container']/../../../..");
		String title = "Container1";
		
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
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName, true, "intranet", false, null);	
	}
}
