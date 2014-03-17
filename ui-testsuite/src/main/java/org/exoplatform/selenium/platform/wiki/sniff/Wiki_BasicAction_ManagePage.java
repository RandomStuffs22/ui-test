package org.exoplatform.selenium.platform.wiki.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.awt.event.KeyEvent;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.wiki.ManageDraft;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 2-July-2013
 * @author lientm
 */

public class Wiki_BasicAction_ManagePage extends ManageDraft{

	ManageAccount magAc;
	Button but;
	NavigationToolbar naTool;
	HomePageActivity activity;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver,this.plfVersion);
		but = new Button(driver);
		naTool = new NavigationToolbar(driver);
		activity = new HomePageActivity(driver);
		magAc.signIn("john", DATA_PASS);
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseID: 68836 + 70041 + 70042 -> Add, Edit and Delete page at Source Editor mode
	 * 
	 */
	@Test
	public void test01_CreatePageSourceEditor(){
		String title = "Wiki_manage_page_title_01";
		String content = "Wiki_manage_page_content_01";
		String newTitle = "Wiki_manage_page_title_01_update";
		String newContent = "Wiki_manage_page_content_01_update";

		info("Add new wiki page at Source Editor");		
		addBlankWikiPage(title, content, 0);

		info("Edit wiki page at Source Editor");
		editWikiPage(newTitle, newContent, 0);

		deleteCurrentWikiPage();
	}

	/**CaseId: 68837 + 70036 + 70037 -> Add, Edit and Delete page at Rich Text mode
	 * pending: add image (product has error)
	 */
	@Test(priority = 0)
	public void test00_CreatePageRichTextEditor(){
		String pageLink = "PageLink";
		String title = "Wiki_manage_page_title_02";
		String content = "Wiki_manage_page_content_02";
		String message = "Color macro";
		String newTitle = "Wiki_manage_page_title_02_update";

		addBlankWikiPage(pageLink, "Create page with RTE", 0);
		goToWikiHome();
		info("Add new wiki page at Rich Text mode");
		goToAddBlankPage();
		addWikiPageRichText(title, content);
		typeEnterInRichText();
		createColorMacro("red", message);
		typeEnterInRichText();
		insertTable2WikiPage("2", "2");
		typeEnterInRichText();
		insertPageLink2WikiPage(true, pageLink, "Link to pageLink", "Go to pageLink");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(By.linkText("Link to pageLink"));
		waitForAndGetElement(By.xpath("//*[@style='color:red;' and contains(text(),'" + message + "')]"));

		info("Edit page");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		addWikiPageRichText(newTitle, null);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);

		deleteCurrentWikiPage();
		click(By.linkText(pageLink));
		deleteCurrentWikiPage();
	}

	/**CaseId: 70058 + 70060 -> Auto save when adding page and delete draft
	 * 
	 */
	@Test
	public void test03_AutoSaveWhenAddingPage(){
		String title = "Wiki_manage_page_title_03";
		String content = "Wiki_manage_page_content_03";

		goToAddBlankPage();
		addWikiPageSourceEditor(title, content);
		Utils.pause(32000);
		assert isElementPresent(ELEMENT_DRAFT_NOTIFY);

		goToManageDraft();
		waitForAndGetElement(ELEMENT_DRAFT_OF_NEW_PAGE.replace("${title}", title));

		deleteDraft(title);
	}

	/**CaseId: 70258 + 70260 + 70259 -> Add, Edit and Delete page from template layout
	 * 
	 */
	@Test
	public void test04_AddPageFromTemplate(){
		String title = "Wiki_manage_page_title_04";
		String newcontent = "Wiki_manage_page_content_04";

		info("Add page with select a template layout");
		addWikiPageFromTemplate(title, 0, "Two-Column_Layout");

		info("Edit content of page");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		type(ELEMENT_CONTENT_WIKI_INPUT, newcontent, false);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);

		deleteCurrentWikiPage();
	}

	/**CaseId: 70261 -> Preview template when adding new page from template
	 * 
	 */
	@Test
	public void test05_PreviewTemplate(){
		By eTemplate = By.xpath(ELEMENT_SELECT_TEMPLATE_LINK.replace("{$template}","Two-Column_Layout"));
		goToAddTemplateWikiPage();	
		click(eTemplate, 2);
		click(but.ELEMENT_SELECT_BUTTON);
		click(ELEMENT_PREVIEW_BUTTON);
		waitForAndGetElement("//*[@class='uiWikiPageTitlePreview' and contains(text(), 'Two-Column Layout')]");
		click(ELEMENT_CLOSE_PREVIEW_WINDOW);
	}

	/**CaseId: 70265 -> Auto Save when adding page from template
	 * 
	 */
	@Test
	public void test06_AutoSaveWhenAddingPageFormTemplate(){
		String title = "Wiki_manage_page_title_06";

		By eTemplate = By.xpath(ELEMENT_SELECT_TEMPLATE_LINK.replace("{$template}","Status_Meeting"));
		goToAddTemplateWikiPage();	
		click(eTemplate, 2);
		click(but.ELEMENT_SELECT_BUTTON);
		addWikiPageSourceEditor(title, null);

		Utils.pause(30000);
		assert isElementPresent(ELEMENT_DRAFT_NOTIFY);

		goToManageDraft();
		waitForAndGetElement(ELEMENT_DRAFT_OF_NEW_PAGE.replace("${title}", title));

		deleteDraft(title);
	}

	/**CaseId: 70336 -> Resume a draft with save as normal
	 * pending because issue WIKI-493
	 */
	@Test (groups = {"error"})
	public void test07_ResumeDraft(){
		String title = "Wiki_manage_page_title_07";
		String content = "Wiki_manage_page_content_07";
		String newTitle = "Wiki_manage_page_title_07_update";
		String newContent = "Wiki_manage_page_content_07_update";
		String draftTitle = ELEMENT_DRAFT_OF_NEW_PAGE.replace("${title}", title);

		goToAddBlankPage();
		addWikiPageSourceEditor(title, content);
		Utils.pause(30000);
		assert isElementPresent(ELEMENT_DRAFT_NOTIFY);

		goToManageDraft();
		waitForAndGetElement(draftTitle);

		click(draftTitle);
		addWikiPageSourceEditor(newTitle, newContent);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);

		goToManageDraft();
		waitForElementNotPresent(draftTitle);

		goToWikiHome();
		click(By.linkText(newTitle));
		deleteCurrentWikiPage();
	}

	/**CaseId: 68838 -> Edit paragraph in page
	 * 
	 */
	@Test
	public void test08_EditParagraph(){
		String title = "Wiki_manage_page_title_08";
		String content = "= Heading1 = \n = Heading2 = \n = Heading3 =";
		String newPara = "== Heading1Update ==";

		info("Add new wiki page");		
		addBlankWikiPage(title, content, 0);

		editParagraph("Heading1", newPara);
		waitForAndGetElement("//h2/*[text()='Heading1Update']");
		waitForAndGetElement("//h1/*[text()='Heading2']");
		waitForAndGetElement("//h1/*[text()='Heading3']");

		deleteCurrentWikiPage();
	}

	/**CaseId: 70059 -> Auto save when editing page
	 * 
	 */
	@Test
	public void test09_AutoSaveWhenEditingPage(){
		String title = "Wiki_manage_page_title_09";
		String content = "Wiki_manage_page_content_09";
		String newTitle = "Wiki_manage_page_title_09_update";
		String newContent = "Wiki_manage_page_content_09_update";
		String newTitle2 = "Wiki_manage_page_title_09_update2";
		String newContent2 = "Wiki_manage_page_content_09_update2";
		String draftTitle1 = ELEMENT_DRAFT_OF_EDIT_PAGE.replace("${title}", newTitle);
		String draftTitle2 = ELEMENT_DRAFT_OF_EDIT_PAGE.replace("${title}", newTitle2);

		info("Add new wiki page");		
		addBlankWikiPage(title, content, 0);

		info("Edit page but not save");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		addWikiPageSourceEditor(newTitle, newContent);
		Utils.pause(30000);
		assert isElementPresent(ELEMENT_DRAFT_NOTIFY);

		info("Edit draft page");
		goToManageDraft();
		waitForAndGetElement(draftTitle1);
		click(draftTitle1);
		addWikiPageSourceEditor(newTitle2, newContent2);
		Utils.pause(30000);
		assert isElementPresent(ELEMENT_DRAFT_NOTIFY);
		goToManageDraft();
		waitForAndGetElement(draftTitle2);

		deleteDraft(newTitle2);
		goToWikiHome();
		click(By.linkText(title));
		deleteCurrentWikiPage();
	}

	/**CaseId: 70280 -> Edit page when publish activity is checked
	 * 
	 */
	@Test
	public void test10_EditPageCheckPublicActivity(){
		String title = "Wiki_manage_page_title_10";
		String content = "Wiki_manage_page_content_10";
		String newTitle = "Wiki_manage_page_title_10_update";
		String newContent = "Wiki_manage_page_content_10_update";

		info("Add new wiki page");		
		addBlankWikiPage(title, content, 0);

		info("Edit title of page -> check comment in activity");
		editPageWithCheckPublicActivity(newTitle, null);
		naTool.goToHomePage();
		waitForAndGetElement(activity.ELEMENT_WIKI_COMMENT_EDIT_TITLE.replace("${title}", newTitle));

		info("Edit content of page -> check comment in activity");
		click(By.linkText(newTitle));
		editPageWithCheckPublicActivity(null, newContent);
		naTool.goToHomePage();
		waitForAndGetElement(activity.ELEMENT_WIKI_COMMENT_EDIT_CONTENT.replace("${title}", newTitle));

		click(By.linkText(newTitle));
		deleteCurrentWikiPage();
	}	

	/**CaseId: 70281 -> Edit page when publish activity is not checked
	 * 
	 */
	@Test
	public void test11_EditPageUncheckPublicActivity(){
		String title = "Wiki_manage_page_title_11";
		String content = "Wiki_manage_page_content_11";
		String newTitle = "Wiki_manage_page_title_11_update";
		String newContent = "Wiki_manage_page_content_11_update";

		info("Add new wiki page");		
		addBlankWikiPage(title, content, 0);

		info("Edit title of page -> check comment in activity");
		editWikiPage(newTitle, null, 0);
		naTool.goToHomePage();
		waitForElementNotPresent(activity.ELEMENT_WIKI_COMMENT_EDIT_TITLE.replace("${title}", newTitle));

		info("Edit content of page -> check comment in activity");
		click(By.linkText(newTitle));
		editWikiPage(null, newContent, 0);
		naTool.goToHomePage();
		waitForElementNotPresent(activity.ELEMENT_WIKI_COMMENT_EDIT_CONTENT.replace("${title}", newTitle));

		click(By.linkText(newTitle));
		deleteCurrentWikiPage();
	}

	/**CaseID: 109190 -> Rename Page
	 *
	 */
	@Test
	public void test12_01_RenamePageInLine(){
		String title = "Wiki_manage_page_title_12_01";
		String content = "Wiki_manage_page_content_12_01";
		String newTitle = "Wiki_manage_page_title_12_01_update";

		info("Add new wiki page");	
		addBlankWikiPage(title, content, 0);

		info("Rename page");
		doubleClickOnElement(ELEMENT_PAGE_TITLE_INFO);
		type(ELEMENT_PAGE_TITLE_EDIT_TEXTBOX, newTitle, true);
		Utils.javaSimulateKeyPress(KeyEvent.VK_ENTER);

		waitForTextPresent(newTitle);
		deleteCurrentWikiPage();	
	}
	@Test
	public void test12_02_RenamePageByEditingPage(){
		String title = "Wiki_manage_page_title_12_02";
		String content = "Wiki_manage_page_content_12_02";
		String newTitle = "Wiki_manage_page_title_12_02_update";

		info("Add new wiki page");	
		addBlankWikiPage(title, content, 0);

		info("Rename page by editing page");

		info("Edit wiki page having attachment");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		addWikiPageSourceEditor(newTitle, content);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForTextPresent(newTitle);
		deleteCurrentWikiPage();	
	}
}