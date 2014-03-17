package org.exoplatform.selenium.platform.wiki.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/** 
 * @author lientm
 * @date: 5-July-2013
 */
public class Wiki_PublicActivity extends BasicAction {
	ManageAccount magAc;
	NavigationToolbar naTool;
	HomePageActivity activity;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver,this.plfVersion);
		naTool = new NavigationToolbar(driver,this.plfVersion);
		activity = new HomePageActivity(driver,this.plfVersion);

		magAc.signIn("john", DATA_PASS);
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseId: 74745 -> Create new wiki page
	 * 
	 */
	@Test
	public void test01_CreateNewWikiPage(){
		String title = "Wiki_activity_title_01";
		String content = "line1/line2/line3/line4/line5";

		addWikiPageWithContentMultiLine(title, content);

		naTool.goToHomePage();
		activity.checkActivityInfoOfWiki(title, content, "1");

		click(By.linkText(title));
		deleteCurrentWikiPage();
	}

	/**CaseId: 74746 -> Update activity - edit wiki page title
	 * 
	 */
	@Test
	public void test02_EditWikiPageTitle(){
		String title = "Wiki_activity_title_02";
		String content = "Wiki_activity_content_02";
		String newTitle = "Wiki_activity_title_01_update";

		info("Add new wiki page");		
		addBlankWikiPage(title, content, 0);

		info("Edit title of page -> check comment in activity");
		editPageWithCheckPublicActivity(newTitle, null);
		naTool.goToHomePage();
		activity.checkActivityInfoOfWiki(newTitle, content, "2");
		waitForAndGetElement(activity.ELEMENT_WIKI_COMMENT_EDIT_TITLE.replace("${title}", newTitle));

		click(By.linkText(newTitle));
		deleteCurrentWikiPage();
	}

	/**CaseId: 74747
	 * Update activity - edit wiki page with comments
	 */
	@Test
	public void test03_EditWikiPageWithComment(){
		String title = "Wiki_activity_title_03";
		String content = "Wiki_activity_content_03";
		String newContent1 = "Wiki_activity_title_03_update1";
		String newContent2 = "Wiki_activity_content_03_update2";
		String comment = "Edit this page";

		info("Add new wiki page");		
		addBlankWikiPage(title, content, 0);

		info("Edit wiki page with comment");
		editPageWithCheckPublicActivity(null, newContent1, comment);		
		naTool.goToHomePage();
		activity.checkActivityInfoOfWiki(title, newContent1, "2");
		waitForAndGetElement(activity.ELEMENT_ACTIVITY_COMMENT_CONTENT_1.replace("${title}", title).replace("${comment}", comment));
		waitForElementNotPresent(activity.ELEMENT_WIKI_COMMENT_EDIT_CONTENT.replace("${title}", title));

		info("Edit wiki page with no comment");
		click(By.linkText(title));
		editPageWithCheckPublicActivity(null, newContent2);
		naTool.goToHomePage();
		activity.checkActivityInfoOfWiki(title, newContent2, "3");
		waitForAndGetElement(activity.ELEMENT_WIKI_COMMENT_EDIT_CONTENT.replace("${title}", title));

		click(By.linkText(title));
		deleteCurrentWikiPage();
	}

	/**CaseId: 74748
	 * Delete wiki page
	 */
	@Test
	public void test04_DeleteWikiPage(){
		String title = "Wiki_activity_title_04";
		String content = "Wiki_activity_content_04";

		info("Add new wiki page");		
		addBlankWikiPage(title, content, 0);

		naTool.goToHomePage();
		activity.checkActivityInfoOfWiki(title, content, "1");

		click(By.linkText(title));
		deleteCurrentWikiPage();

		naTool.goToHomePage();
		waitForElementNotPresent(activity.ELEMENT_ACTIVITY_WIKI_TITLE.replace("${title}", title));
	}

	/**CaseId: 75292
	 * Update wiki's activity after moving a wiki page
	 */
	@Test
	public void test05_MovePage(){
		String title1 = "Wiki_activity_title_05_1";
		String content1 = "Wiki_activity_content_05_1";
		String title2 = "Wiki_activity_title_05_2";
		String content2 = "Wiki_activity_content_05_2";

		info("Add new 2 wiki page at Wiki Home");		
		addBlankWikiPage(title1, content1, 0);
		goToWikiHome();
		addBlankWikiPage(title2, content2, 0);

		info("Move page 2 to page1");
		movePage(title2, title1);

		naTool.goToHomePage();
		activity.checkCommentAfterMoveWikiPage(title2, "Wiki Home > " + title1 + " > " + title2);

		click(By.linkText(title2));
		click(By.linkText(title1));
		deleteCurrentWikiPage();
	}

	/**CaseId: 75293
	 * Open Wiki page from wiki's activity
	 */
	@Test
	public void test06_OpenWikiPageFromActivity(){
		String title = "Wiki_activity_title_06";
		String content = "Wiki_activity_content_06";

		info("Add new wiki page");		
		addBlankWikiPage(title, content, 0);

		naTool.goToHomePage();
		activity.checkActivityInfoOfWiki(title, content, "1");

		click(By.linkText(title));
		waitForTextPresent("Wiki Home");
		deleteCurrentWikiPage();
	}


	/**CaseId: 109195 -> No comment is added to the activity when edit page not checking Publish activiy
	 *
	 */
	@Test
	public void test07_NoCommentAddedOnActivityStreamWhenNotTickPublishActivity(){
		String title = "Wiki_activity_title_07";
		String content = "Wiki_activity_content_07";
		String newTitle = "Wiki_activity_title_07_update";
		String newContent = "Wiki_activity_content_07_update";

		info("Add new wiki page");	
		addBlankWikiPage(title, content, 0);

		info("Edit title of page -> not check comment in activity");
		editWikiPage(newTitle, newContent, 0);
		naTool.goToHomePage();
		waitForAndGetElement(By.linkText(newTitle));
		waitForElementNotPresent(activity.ELEMENT_WIKI_COMMENT_EDIT_TITLE.replace("${title}", newTitle));
		waitForElementNotPresent(activity.ELEMENT_WIKI_COMMENT_EDIT_CONTENT.replace("${title}", newContent));

		click(By.linkText(newTitle));
		deleteCurrentWikiPage();
	}
}
