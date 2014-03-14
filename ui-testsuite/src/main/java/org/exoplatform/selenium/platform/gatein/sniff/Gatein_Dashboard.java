package org.exoplatform.selenium.platform.gatein.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.DashBoard;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date July 2013
 * @author lientm
 */
public class Gatein_Dashboard extends DashBoard {

	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement user;
	PageManagement pageMag;
	PageEditor pageE;
	Button but;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		user = new UserGroupManagement(driver);
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

	/**CaseId: 68855 + 70365
	 * Drag and drop gadget -> delete gadget
	 */
	@Test
	public void test01_DragDropGadget_DeleteGadget(){
		navTool.goToDashboard();
		dragDropGadget("Services Management");
		click(ELEMENT_CLOSE_ADD_GADGET_WINDOW);
		actionOnGadgetOnDashboard("Services Management","Delete Gadget");
	}

	/**CaseId: 68883 + 70365
	 * add new gadget -> delete gadget
	 */
	@Test
	public void test02_AddGadget_DeleteGadget(){
		String url = "http://www.labpixies.com/campaigns/hangman/hangman.xml";
		String name = "Hangman";

		navTool.goToDashboard();
		addNewGadget(url, name);
		click(ELEMENT_CLOSE_ADD_GADGET_WINDOW);
		actionOnGadgetOnDashboard(name, "Delete Gadget");
	}

	/**CaseId: 68884 + 70367 + 70368
	 * Add - rename - delete tab
	 */
	@Test
	public void test03_AddRenameDeleteTab(){
		String name = "Tab 68884";
		String newName = "Tab 68884 update";

		navTool.goToDashboard();
		addNewTabOnDashboard(name, true);
		editTabNameOnDashboard(name, newName);
		deleteTabOnDashboard(newName);
	}

	/**CaseId: 68859
	 * Change container when edit layout for user's page
	 */
	@Test
	public void test04_ChangeContainerWhenEditLayoutUserPage(){
		String pageName = "gateinsniff04";
		String title = "Row1";

		navTool.goToDashboard();
		pageE.createNewPageEmptyLayout(pageName);
		navTool.goToEditPageEditor();

		info("Add container");
		pageE.addNewContainer("Rows Layout", "oneRow");
		waitForAndGetElement(ELEMENT_DROP_TARGET_HAS_LAYOUT);
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_DROP_TARGET_HAS_LAYOUT);
		click(ELEMENT_SWITCH_VIEW_MODE);

		info("Edit container");
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		click(ELEMENT_EDIT_CONTAINER_ICON);
		type(ELEMENT_CONTAINER_TITLE, title, true);
		but.save();
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		waitForAndGetElement("//*[@class='UIRowContainer']//span[text()='" + title + "']");

		info("Delete container");
		pageE.removeContainer(ELEMENT_DROP_TARGET_HAS_LAYOUT, ELEMENT_DELETE_CONTAINER_ICON);
		waitForElementNotPresent(ELEMENT_DROP_TARGET_HAS_LAYOUT);	
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForElementNotPresent(ELEMENT_PAGE_FINISH_BUTTON, 60000);

		deleteTabOnDashboard(pageName);
	}

	/**CaseId: 68860
	 * Change application when edit layout for user's page
	 */
	@Test
	public void test00_ChangeAppWhenEditingPageLayout(){
		String pageName = "gateinsniff05";

		navTool.goToDashboard();
		pageE.createNewPageWithLayout(pageName, 2);
		navTool.goToEditPageEditor();

		info("Add new application to page");
		addContentListPortletForPage();
		click(ELEMENT_SWITCH_VIEW_MODE);

		info("Edit portlet");
		pageE.selectCLVPath("General Drives/Sites Management/acme", "documents");
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForTextPresent("offices.jpg");
		waitForTextPresent("metro.pdf");
		waitForTextPresent("conditions.doc");
		click(ELEMENT_SWITCH_VIEW_MODE);

		info("Move portlet to row 2");
		pageE.movePortletOnContainer(ELEMENT_FRAME_CONTAIN_PORTLET, By.xpath("//*[text()='Content List']/..//*[@class='uiIconDragDrop uiIconWhite']"), ELEMENT_DROP_TARGET_HAS_LAYOUT);

		info("Delete portlet");
		pageE.removePortlet(ELEMENT_FRAME_CONTAIN_PORTLET, ELEMENT_DELETE_PORTLET_ICON);
		waitForTextNotPresent("Sorry, no articles are available.");
		waitForTextNotPresent("offices.jpg");

		deleteTabOnDashboard(pageName);
	}

	/**CaseId: 68861
	 * Add application into container when edit layout for user's page
	 */
	@Test
	public void test06_AddAppIntoContainerWhenEditingPageLayout(){
		String pageName = "gateinsniff06";

		navTool.goToDashboard();
		pageE.createNewPageWithLayout(pageName, 2);
		navTool.goToEditPageEditor();

		info("Add new application to page");
		addContentListPortletForPage();
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForElementNotPresent(ELEMENT_PAGE_FINISH_BUTTON, 60000);

		deleteTabOnDashboard(pageName);
	}

	public void addContentListPortletForPage(){
		click(By.linkText("Content"));
		if (waitForAndGetElement(ELEMENT_CONTAINER_ROW.replace("${No}", "1"), 5000, 0, 2) != null){
			//dragAndDropToObject(ELEMENT_CONTENTS_LIST_VIEWER_PORTLET, By.xpath("//*[@class='UIRowContainer']/div[1]//*[@class='UIRowContainer EmptyContainer']"));
			dragAndDropToObject(ELEMENT_CONTENTS_LIST_VIEWER_PORTLET, ELEMENT_CONTAINER_ROW.replace("${No}", "1"));
			info("Element " + ELEMENT_CONTAINER_ROW + " present");
		}else if (waitForAndGetElement(ELEMENT_CONTAINER_ROW_1, 5000, 0, 2) != null){
			//WebElement element = waitForAndGetElement(ELEMENT_CONTAINER_ROW_1, 5000, 1, 2);
			dragAndDropToObject(ELEMENT_CONTENTS_LIST_VIEWER_PORTLET, ELEMENT_CONTAINER_ROW_1);
			info("Element " +  ELEMENT_CONTAINER_ROW_1 + " present");
		}else{
			dragAndDropToObject(ELEMENT_CONTENTS_LIST_VIEWER_PORTLET, ELEMENT_CONTAINER_ROW_0);
			info("Element " +  ELEMENT_CONTAINER_ROW_0 + " present");
		}
		click(ELEMENT_SWITCH_VIEW_MODE);
		Utils.pause(3000);
		waitForTextPresent("Sorry, no articles are available.");
	}
}