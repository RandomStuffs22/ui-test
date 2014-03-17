package org.exoplatform.selenium.platform.wiki.functional.information;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Version;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test cases: KS/Wiki/Version/Restore
 * @author vuna2
 * <li>Date: Dec 17, 2012</li>
 */
public class Wiki_Version_Restore extends Version{
	ManageAccount magAcc;
	
	public String admin = "john";
	public String pass = DATA_PASS;

	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		magAcc.signIn(admin, pass);
	}

	@AfterMethod
	public void afterMethods(){
		info("-- user.Logout --");
		//signOut();
		driver.quit();
	}

	/**
	 * Qmetry ID: 69792
	 * Case ID 01
	 * <li>Restore version to current version</li>
	 * <li> Step 1: Create new page </li>
	 * <li> Step 2: Add version for page </li>
	 * <li> Step 3: Add more version for page </li>
	 * <li> Step 4: Open form to view all version of page</li>
	 * <li> Step 5: Restore version to current version</li>
	 */
	@Test
	public void test01_RestoreVersionToCurrentVersion(){
		String[] pageInfo = {"restore 01", "page content 01"};

		goToWiki();

		addBlankWikiPage(pageInfo[0], pageInfo[1], 0);

		editWikiPage("1st edit restore 01", "1st edit page content", 0);

		editWikiPage("2nd edit restore 01", "2nd edit page content", 0);
		
		goToRevisionsPage();
		
		click(ELEMENT_VIEW_PAGE_HISTORY);
		
		restoreVersion("1");

		waitForTextPresent("page content 01");


		
		//waitForAndGetElement(ELEMENT_CURRENT_VERSION.replace("${version}", "4"));
		goToWikiPage("Wiki Home/2nd edit restore 01");
		
		deleteCurrentWikiPage();
	}
}
