package org.exoplatform.selenium.platform.wiki.functional.basicaction;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;

/**
 * 
 * @author thaopth
 * Date: 07/12/2012
 */
public class Wiki_BasicAction_Edit extends BasicAction {
	ManageAccount magAcc;
	
	public String admin = DATA_USER1;
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
		//signOut();
		driver.quit();
	}
	
	/**
	 * Qmetry ID: 69752
	 * Wiki/Basic Action/Edit
	 * Case 01: Edit wiki page
	 */
	@Test
	public void test01_EditPage () {
		//Define data test
		String DATA_WIKI_TITLE = "wikipage01";
		String DATA_WIKI_CONTENT = "test01_content before edit";
		String DATA_WIKI_CONTENT_EDITED = "Wiki content afer editing";

		goToWiki();

		addBlankWikiPage(DATA_WIKI_TITLE, DATA_WIKI_CONTENT, 0);

		editWikiPage(DATA_WIKI_TITLE, DATA_WIKI_CONTENT_EDITED, 0);
		
		deleteCurrentWikiPage();
		waitForTextNotPresent(DATA_WIKI_TITLE);
	}
	
	/**
	 * Qmetry ID: 69753
	 * Wiki/Basic Action/Edit
	 * Case 02: Edit page when the title is the same with existing page
	 */
	@Test
	public void test02_EditPageWithSameArticle () {
		//Define data test
		String DATA_WIKI_TITLE1 = "wikipage02a";
		String DATA_WIKI_TITLE2 = "wikipage02b";
		String DATA_WIKI_CONTENT1 = "Cotent of wiki page 02a";
		String DATA_WIKI_CONTENT2 = "Content of wiki page 02b";

		goToWiki();

		addBlankWikiPage(DATA_WIKI_TITLE1, DATA_WIKI_CONTENT1, 0);

		goToWikiPage("Wiki Home");

		addBlankWikiPage(DATA_WIKI_TITLE2, DATA_WIKI_CONTENT2, 0);
		click(By.linkText(DATA_WIKI_TITLE2));
		
		info("--Edit a wiki page 2--");
		click(ELEMENT_EDIT_PAGE_LINK);

		Utils.pause(500);
		driver.navigate().refresh();
		Utils.pause(2000);
		
		type(ELEMENT_TITLE_WIKI_INPUT,DATA_WIKI_TITLE1,true);
		//save();
		switchToParentWindow();
		Utils.pause(500);
		
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForTextPresent(MESSAGE_PAGE_ALREADY_EXISTS);

		//click(ELEMENT_OK_BUTTON);
		click(ELEMENT_OK_BUTTON_WIKI_PAGE);

		//Clear data
		goToWiki();

		goToWikiPage(DATA_WIKI_TITLE1);
		deleteCurrentWikiPage();
		waitForTextNotPresent(DATA_WIKI_TITLE1);
		
		goToWikiPage(DATA_WIKI_TITLE2);
		deleteCurrentWikiPage();
		waitForTextNotPresent(DATA_WIKI_TITLE1);
	}
	
	/**
	 * Qmetry ID:  
	 * == Pending: refer to issue UI-2507 ==
	 * == ==
	 * KS/wiki/basic action/edit
	 * Edit paragraph when the level of header is equal to  paragraph below
	 * Date: 25/02/2013: Lientm: Edit icon paragraph does not work fine, it is difficult to click its
	 * => FIXED (@vuna)
	 */
	@Test
	public void test03_EditParagraphWhenTheLevelOfHeaderIsEqualToParagraphBelow () {
		
		String DATA_WIKI_TITLE = "Test edit wiki with paragraph1";
		String DATA_WIKI_CONTENT = "= paragraph1 = \n = paragraph2 =";
		String DATA_PARAGRAPH1_NEW = "= test edit paragraph =";

		goToWiki();
		addBlankWikiPage(DATA_WIKI_TITLE, DATA_WIKI_CONTENT, 0, false);

		editParagraph("paragraph1", DATA_PARAGRAPH1_NEW);
	
		waitForAndGetElement(By.xpath("//span[text()='test edit paragraph']"));
		
		//Clear data	
		deleteCurrentWikiPage();	
	}
	
	/**
	 * Qmetry ID:
	 * == Pending: refer to issue UI-2507 ==
	 * == ==
	 * KS/wiki/basic action/edit
	 * Case 04: Edit paragraph when the level of header is greater than paragraph below
	 * Date: 25/02/2013: Lientm: Edit icon paragraph does not work fine, it is difficult to click its
	 */
	@Test
	public void test04_EditParagraphWhenTheLevelOfHeaderGreaterThanParagraphBelow () {
		String DATA_WIKI_TITLE = "Test edit wiki with paragraph1";
		String DATA_WIKI_CONTENT = "= level1 = \n == level2 ==";
		String DATA_PARAGRAPH2_NEW = "== test edit paragraph level2 ==";

		goToWiki();

		addBlankWikiPage(DATA_WIKI_TITLE, DATA_WIKI_CONTENT, 0);

		editParagraph("level2", DATA_PARAGRAPH2_NEW);
	
		waitForAndGetElement(By.xpath("//span[text()='test edit paragraph level2']"));
		
		//Clear data	
		deleteCurrentWikiPage();	
	}
	
	/*
	 * == On PLF 4, there is no more Minor Edit button ==
	 * == This case would be removed ==
	 * KS/Wiki/Basic Action/Edit
	 * Case 05: Minor edit
	 */
	/*
	@Test
	public void test05_MinorEdit () {
	}*/
}
