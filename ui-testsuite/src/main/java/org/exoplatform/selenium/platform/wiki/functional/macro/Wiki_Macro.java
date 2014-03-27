package org.exoplatform.selenium.platform.wiki.functional.macro;

import static org.exoplatform.selenium.TestLogger.info;
import static org.exoplatform.selenium.TestLogger.debug;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.wiki.WikiBase;
import org.exoplatform.selenium.platform.wiki.RichTextMode;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.exoplatform.selenium.platform.wiki.ManageDraft;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author: LanLTK
 * @date: 03/24/2014
 */

public class Wiki_Macro extends ManageDraft{

	ManageAccount magAc;
	Button button;
	WikiBase magWiki;
	RichTextMode magRTM;
	BasicAction magWikiAction;

	@BeforeMethod
	public void setUpBeforeTest(){
	    getDriverAutoSave();
		magAc = new ManageAccount(driver);
		button = new Button(driver);
		magWiki = new WikiBase();
		magRTM = new RichTextMode();
		magWikiAction = new BasicAction();
		magAc.signIn("john", "gtn"); 
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/*
	 * CaseId 69763-78346: Insert Box macro
	 * CaseId 69764-78347: Insert Children macro
	 * CaseId 69765-78348: Insert Code macro
	 * CaseId 69768-78351: Insert Excerpt macro
	 * CaseId 69771-78354: Insert Info Message macro
	 * CaseId 69780-78361: Insert Table of Content macro
	 * CaseId 69782-78362: Insert Tip Message macro
	 * CaseId 69783-78363: Insert Warning Message
	 * CaseId 71253-78510: Insert Success Message
	 * CaseId 71281-78534: Insert JIRA macro
	 * CaseId 71282-78535: Edit JIRA macro
	 * CaseId 78257-78590: Insert macro in a macro (1)
	 * CaseId 78258-78591: Insert macro in a macro (3)
	 * CaseId 78260-78591: Insert macro in a macro (3)
	 * CaseId 94914-99101: Insert Code macro by source
	 * 
	 */
	
	@Test	
	public void test01_Insert_Box_Macro(){
		String title = "Page 69763";
		String content = "";
		
		info("Add new wiki page at Rich Text mode:");
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, content);
		typeEnterInRichText();
		
		// Insert Box macro
		info("Insert Box Message macro:");
		createBoxMacro("Box title","Box content");
		
		//Check the availability of Box macro
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
		waitForAndGetElement("//div[@class='box']");
		info(getText(By.xpath("//div[@class='box']")));
		switchToParentWindow();
		driver.switchTo().defaultContent();
		
		//Save wiki page
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		Utils.pause(1000);
		
		//Delete wiki page before exit test case
		deleteCurrentWikiPage();
	}
	
	
	@Test	
		public void test02_Insert_Children_Macro(){
			String title1 = "Page 69764_1";
			String content1 = "Content 69764_1";
			String title2 = "Page 69764_2";
			String content2 = "Content 69764_2";
			String title3 = "Page 69764_3";
			String content3 = "Content 69764_3";
			String title4 = "Page 69764_4";
			String content4 = "Content 69764_4";
			String descendant = "Yes";
			
			//Data Test: Create Page1 > Page2 > Page3; Page1 > Page4
			info("Add new wiki pages:");
			goToWiki();
			goToAddBlankPage();
			addWikiPageRichText(title1, content1);
			typeEnterInRichText();
			click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
			
			goToAddBlankPage();
			addWikiPageRichText(title2, content2);
			typeEnterInRichText();
			click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
			
			goToAddBlankPage();
			addWikiPageRichText(title3, content3);
			typeEnterInRichText();
			click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
			
			goToWikiPage(title1);
			goToAddBlankPage();
			addWikiPageRichText(title4, content4);
			typeEnterInRichText();
			click(ELEMENT_SAVE_BUTTON_ADD_PAGE);

			//Insert Children macro in Page1
			goToWikiPage(title1);
			mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
			waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
			addWikiPageRichText(title1, content1);
			typeEnterInRichText();
			info("Insert Children macro:");
			createChildrenMacro(descendant);	
			
			//Check the availability of Children macro
			driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
			waitForAndGetElement(By.linkText(title2));
			waitForAndGetElement(By.linkText(title3));
			waitForAndGetElement(By.linkText(title4));
			switchToParentWindow();
			driver.switchTo().defaultContent();
			
			//Save wiki Page with Children macro
			click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
			waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);	
			
			//Delete wiki page before exit test case
			goToWikiPage(title1);
			deleteCurrentWikiPage();
	}
	
	@Test	
	public void test03_Insert_Code_Macro(){
		String title = "Page 69765";
		String content = "";
		String language = "html";
		String macro_title = "Test code macro";
		String macro_content = "<html><head>Cool!</head></html>";
		
		info("Add new wiki page at Rich Text mode:");
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, content);
		typeEnterInRichText();

		//Insert Code macro
		info("Insert Code macro:");
		createCodeMacro(language,macro_title,macro_content);
		
		//Check the availability of Code macro
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
		waitForAndGetElement("//div[@class='box code']");
		info(getText(By.xpath("//div[@class='box code']")));
		switchToParentWindow();
		driver.switchTo().defaultContent();
		
		//Save wiki Page with Code macro
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);	
		
		//Delete wiki page before exit test case
		deleteCurrentWikiPage();
	}
	
	@Test	
	public void test04_Insert_Excerpt_Macro(){
		String title = "Page 69768";
		String content = "";
		String type = "Excerpt";
		String macro_content = "Test Excerpt macro.";
		
		info("Add new wiki page at Rich Text mode:");
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, content);
		typeEnterInRichText();

		//Insert Excerpt macro
		info("Insert Excerpt macro:");
		createMessageMacro(type,macro_content);
		
		//Check the availability of Excerpt macro
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
		waitForAndGetElement("//div[@class='ExcerptClass']");
		info(getText(By.xpath("//div[@class='ExcerptClass']")));
		switchToParentWindow();
		driver.switchTo().defaultContent();
		
		//Save wiki Page
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);	
		
		//Delete wiki page before exit test case
		deleteCurrentWikiPage();
	}
	
	@Test	
	public void test05_Insert_InfoMessage_Macro(){
		String title = "Page 69771";
		String content = "";
		String type = "Info";
		String macro_content = "This is Info Message macro.";
		
		info("Add new wiki page at Rich Text mode:");
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, content);
		typeEnterInRichText();

		//Insert Info Message macro
		info("Insert Info Message macro:");
		createMessageMacro(type,macro_content);
		
		//Check the availability of Info Message macro
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
		waitForAndGetElement("//div[@class='box infomessage']");
		info(getText(By.xpath("//div[@class='box infomessage']")));
		switchToParentWindow();
		driver.switchTo().defaultContent();
		
		//Save wiki Page
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);	
		
		//Delete wiki page before exit test case
		deleteCurrentWikiPage();
	}
	
	@Test	
	public void test06_Insert_TableOfContent_Macro(){
		String title = "Page 69780";
		String content = "=Heading1=\\== Heading2 ==\\=== Heading3 ===";
		
		info("Add new wiki page at Rich Text mode:");
		goToWiki();
		goToAddBlankPage();
		addWikiPageSourceEditor(title,content);
		click(ELEMENT_RICHTEXT_BUTTON);

		//Insert Table of Content macro
		info("Insert Table of Content macro:");
		createTableOfContentsMacro();
		
		//Check the availability of Table Of Content macro
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
		waitForAndGetElement("//*[@title='toc macro']");
		info(getText(By.xpath("//*[@title='toc macro']")));
		switchToParentWindow();
		driver.switchTo().defaultContent();
	
		//Save wiki page
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);	
	}

	@Test	
	public void test07_Insert_TipMessage_Macro(){
		String title = "Page 69782";
		String content = "";
		String type = "Tip";
		String macro_content = "This is Tip Message macro.";
		
		info("Add new wiki page at Rich Text mode:");
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, content);
		typeEnterInRichText();

		// Insert Tip Message macro
		info("Insert Tip Message macro:");
		createMessageMacro(type,macro_content);
		
		//Check the availability of Tip Message macro
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
		waitForAndGetElement("//div[@class='box tipmessage']");
		info(getText(By.xpath("//div[@class='box tipmessage']")));
		switchToParentWindow();
		driver.switchTo().defaultContent();
		
		//Save wiki Page
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);	
		
		//Delete wiki page before exit test case
		deleteCurrentWikiPage();
	}
	
	@Test	
	public void test08_Insert_WarningMessage_Macro(){
		String title = "Page 69783";
		String content = "";
		String type = "Warning";
		String macro_content = "This is Warning Message macro.";
		
		info("Add new wiki page at Rich Text mode:");
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, content);
		typeEnterInRichText();

		// Insert Warning Message
		info("Insert Warning Message macro:");
		createMessageMacro(type,macro_content);
		
		//Check the availability of Warning Message macro
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
		waitForAndGetElement("//div[@class='box warningmessage']");
		info(getText(By.xpath("//div[@class='box warningmessage']")));
		switchToParentWindow();
		driver.switchTo().defaultContent();
		
		//Save wiki Page
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);	
		
		//Delete wiki page before exit test case
		deleteCurrentWikiPage();
	}
	
	@Test	
	public void test09_Insert_SuccessMessage_Macro(){
		String title = "Page 71253";
		String content = "";
		String type = "Success";
		String macro_content = "This is Success Message macro.";
		
		info("Add new wiki page at Rich Text mode:");
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, content);
		typeEnterInRichText();

		//Insert Success Message
		info("Insert Success macro:");
		createMessageMacro(type,macro_content);
		
		//Check the availability of Success Message macro
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
		waitForAndGetElement("//div[@class='box successmessage']");
		info(getText(By.xpath("//div[@class='box successmessage']")));
		switchToParentWindow();
		driver.switchTo().defaultContent();
		
		//Save wiki page
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);	
			
		//Delete wiki page before exit test case
		deleteCurrentWikiPage();
	}
/*	
	@Test	
	public void test10_Insert_Edit_JIRA_Macro(){
		String title = "Page 71281_71282";
		String content = "{{jira URL='https://jira.exoplatform.org/' style='table'}} SOC-123 {{/jira}}";
		String newcontent = "{{jira URL='https://jira.exoplatform.org/' style='table'}} ECMS-235 {{/jira}}";
		
		//Add Jira macro in SourceEditor; Can not add from RichTextMode
		info("Add new wiki page at Rich Text mode:");
		goToWiki();
		goToAddBlankPage();
		addWikiPageSourceEditor(title,content);
		click(ELEMENT_RICHTEXT_BUTTON);
	
		//Check the availability of JIRA macro
		//driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
		waitForAndGetElement("//*[@title='jira macro']");
		info(getText(By.xpath("//*[@title='jira macro']")));
		//switchToParentWindow();
		//driver.switchTo().defaultContent();
	
		//Save wiki page
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);	
		
		//Edit Jira macro in SourceEditor
		goToWikiPage(title);
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		addWikiPageSourceEditor(title,newcontent);
		click(ELEMENT_RICHTEXT_BUTTON);
		 
		//Check the availability of JIRA macro
		//driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
		waitForAndGetElement("//*[@title='jira macro']");
		info(getText(By.xpath("//*[@title='jira macro']")));
		//switchToParentWindow();
		//driver.switchTo().defaultContent();
	
		//Save wiki page
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);	
		
		//Delete wiki page before exit test case
		deleteCurrentWikiPage();
	}

	public void test11_Insert_Source_Macro(){
		String title = "Page 94914";
		String content = "== Java with title ==\\{{code language='java' title='HelloWorld.java'}} System.out.println('Hello World'); {{/code}} \\ == Java without title ==\\{{code language='java'}} System.out.println('Hello World'); {{/code}} \\ [[xwiki example>>http://extensions.xwiki.org/xwiki/bin/view/Extension/Code+Macro]] \\ {{code language='html'}} <html> <head>How cool?</head></html>{{/code}}";

	
		//Add source macro in SourceEditor
		info("Add new wiki page at Rich Text mode:");
		goToWiki();
		goToAddBlankPage();
		addWikiPageSourceEditor(title,content);
		click(ELEMENT_RICHTEXT_BUTTON);

		//Check the availability of Code macro
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
		waitForAndGetElement("//*[@title='box code']");
		info(getText(By.xpath("//*[@title='box code']")));
		switchToParentWindow();
		driver.switchTo().defaultContent();

		//Save wiki page
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);	
	
		//Delete wiki page before exit test case
		deleteCurrentWikiPage();
	}
*/
	
}
