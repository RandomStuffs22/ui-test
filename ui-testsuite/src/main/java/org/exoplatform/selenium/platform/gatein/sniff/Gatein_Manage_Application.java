package org.exoplatform.selenium.platform.gatein.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PlatformBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 31 July 2013
 * @author lientm
 *
 */
public class Gatein_Manage_Application extends PlatformBase {
	ManageAccount magAc;
	NavigationToolbar navTool;
	PageEditor pageE;
	ManageApplications app;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		pageE = new PageEditor(driver);
		app = new ManageApplications(driver);
		
		magAc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 70392 + 68906 -> Hire + show Import Application icon
	 * 
	 */
	@Test
	public void test01_HireShowImportApplicationIcon(){
	
		info("Hire Import Application icon");
		app.showImportApplication(false);
		
		info("Show Import Application icon");
		app.showImportApplication(true);
	}
	
	/**CaseId: 68905 -> Import Application
	 * 
	 */
	@Test
	public void test02_ImportApplication(){
		navTool.goToApplicationRegistry();
		app.importApplication();
		waitForAndGetElement(app.ELEMENT_CATEGORY_NAME.replace("${categoryName}", "Integration"), 60000);
		waitForAndGetElement(app.ELEMENT_CATEGORY_NAME.replace("${categoryName}", "Navigation"));
		waitForAndGetElement(app.ELEMENT_CATEGORY_NAME.replace("${categoryName}", "System"));
	}
	
	/**CaseId: 70406 + 70407 + 68908 + 68907 
	 * Add new category
	 * Edit category
	 * Add some applications to category
	 * Delete category
	 */
	@Test
	public void test00_ManageCategory(){
		String categoryName = "Gateinsniff_Category03";
		String displayName = "Category 03";
		String categoryDescription = "Add new category";
		
		String newDisplayName = "Category 03 update";
		String newCategoryDescription = "Add new category update";
		String appName1 = "Application03";
		String appName2 = "Advanced Search";
		
		info("Add new category");
		navTool.goToApplicationRegistry();
		app.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, true, null, true);
		
		info("Edit category");
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put("Platform/Content Management", "*");
		app.editCategoryAtManageApplications(categoryName, newDisplayName, newCategoryDescription, false, permissions, true);
		
		info("Add Applications into category");
		app.addApplicationToCategory(categoryName, true, appName1, "Portlet", null, true, null, null);
		app.addApplicationToCategory(categoryName, false, null, "Portlet", appName2, false, "Development", "manager");
		
		info("Delete category");
		app.deleteCategoryAtManageApplications(categoryName, true);
	}
	
	/**CaseId: 68909
	 * View portlet
	 */
	@Test
	public void test04_ViewPortlet(){
		navTool.goToApplicationRegistry();
		click(app.ELEMENT_SHOW_PORTLET_ICON);
		
		info("View calendar portlet");
		click(app.ELEMENT_PORTLET_IN_CATEGORY.replace("${group}", "calendar").replace("${portletName}", "Calendar Portlet"));
		waitForAndGetElement(app.ELEMENT_PORTLET_INFO_NAME.replace("${portletName}", "Calendar Portlet"));
		waitForAndGetElement(app.ELEMENT_PORTLET_INFO_DESCRIPTION.replace("${description}", "Calendar Portlet"));
		waitForAndGetElement(app.ELEMENT_PORTLET_INFO_CATEGORY.replace("${category}", "Collaboration"));
	}
	
	/**CaseId: 68911 + 70397 + 70402 + 70401 + 70400
	 * Add new manual gadget
	 * Edit manual gadget
	 * Refresh gadget
	 * Add gadget to some categories
	 * Delete gadget
	 */
	@Test
	public void test05_ManageManualGadget(){
		String name = "Gateinsniff_gadget05";
		String title = "gadget title 05";
		String source = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Module><ModulePrefs title=\"${title}\" description=\"The hello world gadget.\"/><Content type=\"html\"> <![CDATA[    Hello, world! ]]></Content></Module>";
		String newTitle = "gadget title 05 update";		
		
		navTool.goToApplicationRegistry();
		click(app.ELEMENT_SHOW_GADGET_ICON);
		info("Create new manual gadget");
		app.addManualGadget(name, source.replace("${title}", title));
		
		info("Edit gadget");
		app.editManualGadget(title, source.replace("${title}", newTitle));
		
		info("Refresh gadget");
		click(app.ELEMENT_GADGET_REFRESH_ICON);
		
		info("Add gadget to category");
		app.addGadgetToCategory("Administration/answer");
		
		info("Delete gadget");
		app.deleteGadget(newTitle);
	}
	
	/**CaseId: 70403 + 70405 + 70404
	 * Add new remote gadget
	 * Add gadget to category
	 * Delete gadget 
	 */
	@Test
	public void test06_ManageRemoteGadget(){
		String url = "http://www.labpixies.com/campaigns/memory_game/memory_game.xml";
		String title = "Memory Game";
		
		navTool.goToApplicationRegistry();
		click(app.ELEMENT_SHOW_GADGET_ICON);
		
		info("Create new remote gadget");
		app.addRemoteGadget(url);
		waitForAndGetElement(app.ELEMENT_GADGET_DELETE_ICON.replace("${title}", title));
		
		info("Add gadget to category");
		app.addGadgetToCategory("Administration");
		
		info("Delete gadget");
		app.deleteGadget(title);
	}
}
