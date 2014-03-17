package org.exoplatform.selenium.platform.wiki.functional.information;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.Set;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Version;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author vuna2
 * <li>Date: Dec 18, 2012</li>
 * <li>Test cases: KS\Wiki\Version\View</li>
 */
public class Wiki_Version_View extends Version{
	ManageAccount magAcc;
	Button button;

	public String admin = "john";
	public String pass = DATA_PASS;

	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		button = new Button(driver);
		magAcc.signIn(admin, pass);
	}

	@AfterMethod
	public void afterMethods(){
		info("-- Finished: test case --");
		//signOut();
		driver.quit();
	}

	/**
	 * Qmetry ID: 69803 
	 * <li>Version/View</li>
	 * Case ID 01
	 * <li>View content of current version</li>
	 * <li>Step 1: Create new page</li>
	 * <li>Step 2: Add version for page</li>
	 * <li>Step 3: Add more version for page</li>
	 * <li>Step 4: Open form to view all version of page</li>
	 * <li>Step 5: View content of current version</li>
	 */
	@Test
	public void test01_ViewContentOfCurrentVersion(){
		String[] dataInfo = {"view content 01", "page content 01",
				"1st edit view content 01", "1st edit page content", 
				"2nd edit view content 01", "2nd edit page content"};

		goToWiki();

		addBlankWikiPage(dataInfo[0], dataInfo[1], 0);

		editWikiPage(dataInfo[2], dataInfo[3], 0);

		editWikiPage(dataInfo[4], dataInfo[5], 0);

	//	click(By.xpath("//*[text()='View Page History']"));
		goToRevisionsPage();
		
		viewVersion("3");

		waitForTextPresent(dataInfo[5]);

		goToWikiHome();

		goToWikiPage(dataInfo[4]);

		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69804
	 * <li>Version/View</li>
	 * Case ID 02
	 * <li>View content of other version</li>
	 * <li>Step 1: Create new page</li>
	 * <li>Step 2: Add version for page</li>
	 * <li>Step 3: Add more version for page</li>
	 * <li>Step 4: Open form to view all version of page</li>
	 * <li>Step 5: View content of other version</li>
	 */
	@Test
	public void test02_ViewContentOfOtherVersion(){
		String[] dataInfo = {"view content 02", "page content 02",
				"1st edit view content 02", "1st edit page content", 
				"2nd edit view content 02", "2nd edit page content"};

		goToWiki();

		addBlankWikiPage(dataInfo[0], dataInfo[1], 0);

		editWikiPage(dataInfo[2], dataInfo[3], 0);

		editWikiPage(dataInfo[4], dataInfo[5], 0);

		goToRevisionsPage();
		
		viewVersion("1");

		waitForTextPresent(dataInfo[1]);

		goToWikiHome();

		goToWikiPage(dataInfo[4]);

		deleteCurrentWikiPage();		
	}

	/**
	 * Qmetry ID: 69699
	 * <li>Version/View</li>
	 * Case ID 03
	 * <li>View content of other version while viewing 1 version</li>
	 * <li>Step 1: Create new page</li>
	 * <li>Step 2: Add version for page</li>
	 * <li>Step 3: Add more version for page</li>
	 * <li>Step 4: Open form to view all version of page</li>
	 * <li>Step 5: View content of 1 version</li>
	 * <li>Step 6: View content of other version</li>
	 */
	@Test
	public void test03_ViewContentOfOtherVersionWhileViewing1Version(){
		String[] dataInfo = {"view content 03", "page content 03",
				"1st edit view content 03", "1st edit page content", 
				"2nd edit view content 03", "2nd edit page content"};

		goToWiki();

		addBlankWikiPage(dataInfo[0], dataInfo[1], 0);

		editWikiPage(dataInfo[2], dataInfo[3], 0);

		editWikiPage(dataInfo[4], dataInfo[5], 0);

	
		goToRevisionsPage();
		
		viewVersion("1");

		waitForTextPresent(dataInfo[1]);

		button.next();

		waitForTextPresent(dataInfo[3]);

		goToWikiHome();

		goToWikiPage(dataInfo[4]);

		deleteCurrentWikiPage();
	} 

	/**
	 * Qmetry ID: 
	 * <li>Version/View</li>
	 * Case ID 04
	 * <li>Back to current version while view content of other version</li>
	 * <li>Step 1: Create new page</li>
	 * <li>Step 2: Add version for page</li>
	 * <li>Step 3: Add more version for page</li>
	 * <li>Step 4: Open form to view all version of page</li>
	 * <li>Step 5: View content of 1 version</li>
	 * <li>Step 6: Back to current version</li>
	 */
	@Test
	public void test04_BackToCurrentVersionWhileViewContentOfOtherVersion(){
		String[] dataInfo = {"view content 04", "page content 04",
				"1st edit view content 04", "1st edit page content", 
				"2nd edit view content 04", "2nd edit page content"};

		goToWiki();

		addBlankWikiPage(dataInfo[0], dataInfo[1], 0);

		editWikiPage(dataInfo[2], dataInfo[3], 0);

		editWikiPage(dataInfo[4], dataInfo[5], 0);

		goToRevisionsPage();
		viewVersion("1");

		waitForTextPresent(dataInfo[1]);

		click(By.linkText("current version."));

		waitForTextPresent(dataInfo[5]);

		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69802
	 * <li>Version/View Change</li>
	 * Case ID 01
	 * <li>View Change of page</li>
	 * <li>Step 1: Create new page</li>
	 * <li>Step 2: Default view change</li>
	 * <li>Step 3: Add version for page</li>
	 * <li>Step 4: Add more version for page</li>
	 * <li>Step 5: View change</li>
	 * <li>Step 6: Change compare version</li>
	 * <li>Step 7: View page history</li>
	 */
	@Test
	public void test01_ViewChangeOfPage(){
		String[] dataInfo = {"view change 01", "page content 01",
				"1st edit view change 01", "1st edit page content", 
				"2nd edit view change 01", "2nd edit page content"};

		goToWiki();

		addBlankWikiPage(dataInfo[0], dataInfo[1], 0);

		waitForElementNotPresent(ELEMENT_VIEW_CHANGE);

		editWikiPage(dataInfo[2], dataInfo[3], 0);

		editWikiPage(dataInfo[4], dataInfo[5], 0);

		viewChange();

		Utils.captureScreen("FNC_KS_WIKI_VERSION_VIEW_CHANGE_01_1");

		changeCompareVersions("1", "2");

		Utils.captureScreen("FNC_KS_WIKI_VERSION_VIEW_CHANGE_01_2");

		viewPageHistory();

		waitForAndGetElement(ELEMENT_RESTORE_LINK.replace("{$version}","2"));

		goToWikiPage(dataInfo[4]);

		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69808
	 * <li>Version/View Link</li>
	 * Case ID 01
	 * <li>View Link of Revisions</li>
	 * <li>Step 1: Create new page</li>
	 * <li>Step 2: Add version for page</li>
	 * <li>Step 3: Click Link Revisions</li>
	 */
	@Test
	public void test01_ViewLinkOfRevisions(){
		String[] dataInfo = {"view link 01", "page content 01",
				"1st edit view link 01", "1st edit page content"};

		goToWiki();

		addBlankWikiPage(dataInfo[0], dataInfo[1], 0);

		editWikiPage(dataInfo[2], dataInfo[3], 0);

		goToRevisionsPage();

		
		goToWiki();
		
		click(By.linkText(dataInfo[2]));
		
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69809
	 * <li>Version/View Link</li>
	 * Case ID 02
	 * <li>View Link of Revisions when edit page content by another user</li>
	 * <li>Step 1: Create new page</li>
	 * <li>Step 2: Add version for page</li>
	 * <li>Step 3: Open another browser</li>
	 * <li>Step 4: Edit page content again</li>
	 * <li>Step 5: Click Link Revisions</li>
	 */
	@Test
	public void test02_ViewLinkOfRevisionsWhenEditPageContentByAnotherUser(){
		String[] dataInfo = {"view link 02", "page content 02",
				"1st edit view link 02", "1st edit page content", 
				"2nd edit view link 02", "2nd edit page content"};

		info("Step 1: Create new Wiki page");
		goToWiki();
		
		addBlankWikiPage(dataInfo[1], dataInfo[2], 0);
		
		editWikiPage(dataInfo[2], dataInfo[3], 0);
		
		info("Step 2: Edit revision of Wiki page");

		Set<Cookie> cookies1 = getBrowserCookies();

		String handlesBefore = driver.getWindowHandle();
		
		openNewBrowser();

		goToWikiPage(dataInfo[2], ManageAccount.userType.PUBLISHER);
		
		goToRevisionsPage();
		
		waitForAndGetElement(ELEMENT_CURRENT_VERSION.replace("${version}", "2"));

		Set<Cookie> cookies2 = getBrowserCookies();

		String handlesAfter = driver.getWindowHandle();

		backToPreviousBrowser(cookies1, handlesBefore);

		editWikiPage(dataInfo[4], dataInfo[5], 0);
		
		info("Step 3: Open new browser to confirm page revision");

		backToPreviousBrowser(cookies2, handlesAfter);

		goToWiki();

		goToWikiPage(dataInfo[4]);

		goToRevisionsPage();

		if(isElementNotPresent(ELEMENT_VERSION_LINK.replace("{$version}","2")))
			waitForAndGetElement(ELEMENT_VERSION_LINK_AUX.replace("{$version}","2"));
		
		waitForAndGetElement(ELEMENT_CURRENT_VERSION.replace("${version}", "3"));

		backToPreviousBrowser(cookies1, handlesBefore);

		deleteCurrentWikiPage();
	}	
}