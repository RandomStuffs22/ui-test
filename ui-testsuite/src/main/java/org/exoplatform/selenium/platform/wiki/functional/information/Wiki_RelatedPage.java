package org.exoplatform.selenium.platform.wiki.functional.information;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * <li>Date: Dec 11, 2012</li>
 * <li>Test cases: Wiki\Info\Related_Page</li>
 */
public class Wiki_RelatedPage extends BasicAction{
	
	ManageAccount magAc;
	Button button;			
	
	public String admin = DATA_USER1;
	public String pass = DATA_PASS;

	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		button = new Button(driver);
		magAc.signIn(admin, pass);
	}

	@AfterMethod
	public void afterMethods(){
		info("-- Finished: test case --");
		driver.quit();
	}

	/**
	 * Qmetry ID: 69691
	 * Case ID 01
	 * <li> Add related page </li>
	 * <li> Step 1: Create new pages </li>
	 * <li> Step 2: Open form to see page's information </li>
	 * <li> Step 3: Add related page </li>
	 */
	@Test
	public void test01_AddRelatedPage(){
		String[][] pageInfo = {{"relatedPage01_1", "relatedPage01_2"}, {"content of page1", "content of page2"}};

		String[][] wikiPath = {{"Wiki Home", "Wiki Home"}, {"Wiki Home/relatedPage01_1", "Wiki Home/relatedPage01_2"}};

		addBlankWikiPageAndRelatePage(2, wikiPath[0], pageInfo, 0, wikiPath[1][0], pageInfo[0][1]);

		deleteWikiPage(wikiPath[1]);
		
		magAc.signOut();
	}
	
	/**
	 * Qmetry ID: 69692
	 * Case ID 02
	 * <li>Add related page when user does not have permission to edit this page</li>
	 * <li>Step 1: Create new pages</li>
	 * <li>Step 2: Set permission for page</li>
	 * <li>Step 3: Open form to see page's information</li>
	 * <li>Step 4: Add related page</li>
	 */
	@Test
	public void test02_AddRelatedPageWhenUserDoesNotHavePermissionToEditThisPage(){		
		String[][] pageInfo = {{"relatedPage02"}, {"content of page"}};

		String[][] wikiPath = {{"Wiki Home"}, {"Wiki Home/relatedPage02"}};

		boolean[] editInfo = {true, false, false};

		addBlankWikiPageAndEditPagePermissions(1, wikiPath[0], pageInfo, 0, editInfo, "any", 2);

		goToPageInfo(ManageAccount.userType.PUBLISHER, "Wiki Home/relatedPage02");

		Utils.captureScreen("FNC_KS_WIKI_INFO_CASE_02");

		waitForElementNotPresent(ELEMENT_ADD_MORE_RELATION_BUTTON);

		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath[1]);
	}
	
	/**
	 * Qmetry ID: 69693
	 * Case ID 03
	 * <li>Add related page when user does not have permission to view selected page</li>
	 * <li>Step 1: Create new pages</li>
	 * <li>Step 2: Set permission for page</li>
	 * <li>Step 3: Open form to see page's information</li>
	 * <li>Step 4: Add related page</li>
	 */
	@Test
	public void test03_AddRelatedPageWhenUserDoesNotHavePermissionToViewSelectedPage(){
		String[][] pageInfo = {{"relatedPage03_1", "relatedPage03_2"}, {"content of page1", "content of page2"}};

		String[][] wikiPath = {{"Wiki Home", "Wiki Home"}, {"Wiki Home/relatedPage03_1", "Wiki Home/relatedPage03_2"}};

		boolean[] editInfo = {false, false, false};

		addBlankWikiPageAndEditPagePermissions(2, wikiPath[0], pageInfo, 0, editInfo, "any", 2);

		goToWikiPage(wikiPath[1][0]);
		
		editPagePermission("any", true, true, false, 2);
		
		goToPageInfo(ManageAccount.userType.AUTHOR, wikiPath[1][0]);

		click(ELEMENT_ADD_MORE_RELATION_BUTTON);

		waitForElementNotPresent(By.xpath(ELEMENT_SELECTED_PAGE.replace("${relatedPage}", pageInfo[0][1])));

		button.cancel();

		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath[1]);
	}

	/**
	 * Qmetry ID: 69810
	 * Case ID 04
	 * <li>View related page</li>
	 * <li>Step 1: Create new pages</li>
	 * <li>Step 2: Add related page</li>
	 * <li>Step 3: View content of related page</li>
	 */
	@Test
	public void test04_ViewRelatedPage(){
		String[][] pageInfo = {{"relatedPage04_1", "relatedPage04_2"}, {"content of page1", "content of page2"}};

		String[][] wikiPath = {{"Wiki Home", "Wiki Home"}, {"Wiki Home/relatedPage04_1", "Wiki Home/relatedPage04_2"}};

		addBlankWikiPageAndRelatePage(2, wikiPath[0], pageInfo, 0, wikiPath[1][0], pageInfo[0][1]);

		Utils.pause(500);
		driver.navigate().refresh();
		Utils.pause(2000);
		//click(ELEMENT_RELATED_PAGE.replace("${relatedPage}", pageInfo[0][1]));
		WebElement eRel = waitForAndGetElement(ELEMENT_RELATED_PAGE.replace("${relatedPage}", pageInfo[0][1]), DEFAULT_TIMEOUT, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", eRel);
		
		waitForTextPresent(pageInfo[1][1]);

		Utils.captureScreen("FNC_KS_WIKI_INFO_CASE_04");

		deleteWikiPage(wikiPath[1]);
	}

	/**
	 * Qmetry ID: 69811
	 * Case ID 05
	 * <li>View related page when user does not have permission to view</li>
	 * <li>Step 1: Create new pages</li>
	 * <li>Step 2: Add related page</li>
	 * <li>Step 3: View content of related page</li>
	 */
	@Test
	public void test05_ViewRelatedPageWhenUserDoesNotHavePermissionToView(){
		String[][] pageInfo = {{"relatedPage05_1", "relatedPage05_2"}, {"content of page1", "content of page2"}};

		String[][] wikiPath = {{"Wiki Home", "Wiki Home"}, {"Wiki Home/relatedPage05_1", "Wiki Home/relatedPage05_2"}};

		boolean[] editInfo = {false, false, false};

		addBlankWikiPageAndEditPagePermissions(2, wikiPath[0], pageInfo, 0, editInfo, "any", 2);

		addRelatedPage(wikiPath[1][0], pageInfo[0][1]);

		goToWikiPage(wikiPath[1][0], ManageAccount.userType.AUTHOR);

		waitForElementNotPresent(ELEMENT_RELATED_PAGE.replace("${relatedPage}", pageInfo[0][1]));

		Utils.captureScreen("FNC_KS_WIKI_INFO_CASE_05");

		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath[1]);
	}

	/**
	 * Qmetry ID: 69742
	 * Case ID 06
	 * <li><Delete page/li>
	 * <li>Step 1: Create new pages</li>
	 * <li>Step 2: Add related page</li>
	 * <li>Step 3: Delete related page</li>
	 */
	@Test
	public void test06_DeletePage(){
		String[][] pageInfo = {{"relatedPage06_1", "relatedPage06_2"}, {"content of page1", "content of page2"}};

		String[][] wikiPath = {{"Wiki Home", "Wiki Home"}, {"Wiki Home/relatedPage06_1", "Wiki Home/relatedPage06_2"}};

		addBlankWikiPageAndRelatePage(2, wikiPath[0], pageInfo, 0, wikiPath[1][0], pageInfo[0][1]);

		removeRelatedPage(true, true, null, pageInfo[0][1]);

		deleteWikiPage(wikiPath[1]);
	}

	/**
	 * Qmetry ID: 69747
	 * Case ID 07
	 * <li>Delete related page when cancel confirm message</li>
	 * <li>Step 1: Create new pages</li>
	 * <li>Step 2: Add related page</li>
	 * <li>Step 3: Delete related page</li>
	 */
	@Test
	public void test07_DeleteRelatedPageWhenCancelConfirmMessage(){
		String[][] pageInfo = {{"relatedPage07_1", "relatedPage07_2"}, {"content of page1", "content of page2"}};

		String[][] wikiPath = {{"Wiki Home", "Wiki Home"}, {"Wiki Home/relatedPage07_1", "Wiki Home/relatedPage07_2"}};

		addBlankWikiPageAndRelatePage(2, wikiPath[0], pageInfo, 0, wikiPath[1][0], pageInfo[0][1]);

		removeRelatedPage(false, true, null, pageInfo[0][1]);

		deleteWikiPage(wikiPath[1]);
	}

	/**
	 * Qmetry ID: 69748
	 * Case ID 08
	 * <li>Delete related page when user does not have permission to edit page</li>
	 * <li>Step 1: Create new pages</li>
	 * <li>Step 2: Add related page</li>
	 * <li>Step 3: Delete related page</li>
	 */
	@Test
	public void test08_DeleteRelatedPageWhenUserDoesNotHavePermissionToEditPage(){
		String[][] pageInfo = {{"relatedPage08_1", "relatedPage08_2"}, {"content of page1", "content of page2"}};

		String[][] wikiPath = {{"Wiki Home", "Wiki Home"}, {"Wiki Home/relatedPage08_1", "Wiki Home/relatedPage08_2"}};

		boolean[] editInfo = {true, false, false};

		addBlankWikiPageAndEditPagePermissions(2, wikiPath[0], pageInfo, 0, editInfo, "any", 2);

		addRelatedPage(wikiPath[1][1], pageInfo[0][0]);

		goToPageInfo(ManageAccount.userType.AUTHOR, wikiPath[1][1]);

		waitForElementNotPresent(ELEMENT_REMOVE_RELATED_PAGE_LINK.replace("${relatedPage}", pageInfo[0][0]));

		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath[1]);
	}
	
}