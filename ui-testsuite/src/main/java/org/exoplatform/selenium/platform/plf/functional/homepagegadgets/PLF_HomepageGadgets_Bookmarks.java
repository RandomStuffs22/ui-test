package org.exoplatform.selenium.platform.plf.functional.homepagegadgets;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.HomePageGadget;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.exoplatform.selenium.platform.wiki.WikiBase;
import org.exoplatform.selenium.platform.calendar.Task;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author lanltk
 * @date 06 Feb 2014
 */

public class PLF_HomepageGadgets_Bookmarks extends WikiBase{

	ManageAccount magAc;
	NavigationToolbar naviToolbar;
	ForumManageForum magForum;
	PageEditor pagEdit;
	PeopleConnection conn;
	NavigationManagement navMag;
	HomePageGadget hpGadget;
	Button button;
	Task tsk;
	ManageApplications magApp;

	@BeforeMethod
	public void beforeMethods() {
		getDriverAutoSave();
		naviToolbar = new NavigationToolbar(driver, this.plfVersion);
		magForum = new ForumManageForum(driver, this.plfVersion);
		conn = new PeopleConnection(driver, this.plfVersion);
		pagEdit = new PageEditor(driver, this.plfVersion);
		magAc = new ManageAccount(driver, this.plfVersion);
		navMag = new NavigationManagement(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		alert = new ManageAlert(driver, this.plfVersion);
		hpGadget = new HomePageGadget(driver, this.plfVersion);
		tsk = new Task(driver, this.plfVersion);
		magApp = new ManageApplications(driver, this.plfVersion);
		magAc.signIn(DATA_USER1, DATA_PASS);

	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Functional-PLF-HompageGadgets-Bookmarks
	 *  CaseId: 74333, 74334, 74335, 74336, 74337
	 *  CaseId marked as Pending due to low priority and bugs found but not fixed yet:
	 *  74338, 74339, 74340 (Refer https://jira.exoplatform.org/browse/PLF-3053)
	 */
	/*
	 *  CaseId 74333: Check the Discussion link
	 *  CaseId 74334: Check the Wiki link
	 *  CaseId 74335: Check the Document link
	 *  CaseId 74336: Check the Agenda link
	 *  CaseId 74337: Add new bookmark
	 */
	@Test  
	public void test01_BookmarksLink(){	
		String name = "Connection";
		String url = "/portal/intranet/connexions";
		
		//Add Bookmarks gadget to HomePage  
		naviToolbar.goToApplicationRegistry();
		magApp.importApplication();
		naviToolbar.goToHomePage();
		naviToolbar.goToEditLayout();
		click(ELEMENT_CATEGORY_GADGETS);
		dragAndDropToObject(hpGadget.ELEMENT_APPLICATION_BOOKMARKS,hpGadget.ELEMENT_MIDDLE_CONTAINER);
		pagEdit.finishEditLayout();

		//Check existence of Bookmarks gadget
		naviToolbar.goToHomePage();
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		waitForAndGetElement(hpGadget.ELEMENT_BOOKMARKS_GADGET_CONTENT_LIST);
		waitForAndGetElement(hpGadget.ELEMENT_BOOKMARKS_GADGET_ADDBOOKMARK_ICON);

		//Check Discussions Link
		info("Check Forum bookmark:");
		click(By.linkText("Discussions"));
		driver.switchTo().defaultContent();
		waitForAndGetElement(magForum.ELEMENT_FORUM_STATE,DEFAULT_TIMEOUT,0);	
		naviToolbar.goToHomePage();

		//Check Wiki Link
		info("Check Wiki bookmark:");
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		click(By.linkText("Wiki"));
		driver.switchTo().defaultContent();
		waitForAndGetElement(ELEMENT_WIKI_HOME_PAGE);
		naviToolbar.goToHomePage();

		//Check Documents Link
		info("Check Documents bookmark:");
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		click(By.linkText("Documents"));
		driver.switchTo().defaultContent();
		waitForAndGetElement(By.id("UIFileViewCheckBox"), 3000, 1, 2);
		naviToolbar.goToHomePage();

		//Check Agenda Link
		info("Check Calendar bookmark:");	
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		click(By.linkText("Agenda"));
		driver.switchTo().defaultContent();
		waitForAndGetElement(tsk.ELEMENT_CALENDAR_PANEL);
		naviToolbar.goToHomePage();

		//Add new bookmark 
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		hpGadget.addNewBookmarkListGadget(name, url);
		driver.switchTo().defaultContent();

		//Check new bookmark
		info("Check new bookmark:");
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		click(By.linkText(name));
		waitForAndGetElement(conn.ELEMENT_EVERYONE_TAB);
		driver.switchTo().defaultContent();
		naviToolbar.goToHomePage();

		//Delete bookmark
		info("Delete a bookmark:");
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		hpGadget.deleteBookmarkListGadget(name);
		driver.switchTo().defaultContent();
		naviToolbar.goToHomePage();

		/*Clear data*/
		info("Clear data");
		naviToolbar.goToEditPageEditor();
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(pagEdit.ELEMENT_VIEW_PAGE_PROPERTIES);
		pagEdit.removePortlet(hpGadget.ELEMENT_GADGET_PORLET_IN_MIDDLE_HOME_PAGE, hpGadget.ELEMENT_DELETE_ICON_GADGET_PORLET_IN_MIDDLE_HOME_PAGE);
	}
}
