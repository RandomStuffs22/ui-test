/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.selenium.platform.wiki.functional.basicaction;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Template;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Created by The eXo Platform SAS
 * Author : Hoang Manh Dung
 *          dunghm@exoplatform.com
 * Dec 10, 2012  
 */
public class Wiki_BasicAction_Add extends Template{

	ManageAccount magAcc;
	Dialog dialog;
	Button button;	

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		magAcc = new ManageAccount(driver);
		dialog = new Dialog(driver);
		button = new Button(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 69719
	 * Test Case ID 001
	 * Create new page using Blank Template
	 */
	@Test
	public void test01_AddNewPageWithBlankTemplate(){
		String title = "TestCase 001";
		String content = title + " Content";
		int mode = 0;
		
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);
		info("Create new page successfully");
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69720
	 * Test Case ID 002
	 * Create new page using existing template 
	 */
	@Test
	public void test02_AddNewPageWithTemplate(){
		String title = "TestCase 002";
		int mode = 0;
		String template = "Two-Column_Layout";
		//"Two-Column Layout";
		//Create new page with a blank template
		goToWiki();
		addWikiPageFromTemplate(title, mode, template);
		info("Create new page successfully");
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69667
	 * Test Case ID 003
	 * Add new page when title is blank
	 */
	@Test
	public void test00_AddNewPageWithBlankTitle(){
		//String title = "";
		String content = "TestCase 003 Content";
		int mode = 0;
		String infoMessage = "You are about to save an Untitled page.";

		info("-- Add a new page when Title is blank --");
		//Create new page with a blank template
		goToWiki();
		
		info("-- Create new page when click [Confirm] button --");
		addBlankWikiPage(null, content, mode, false, infoMessage); 
		waitForTextPresent("Untitled");
		
		info("-- Create new page when click [Cancel] button --");
		goToWikiHome();
		goToAddBlankPage();
//		driver.navigate().refresh();
//		Utils.pause(3000);
		addWikiPageSourceEditor(null, content);
		switchToParentWindow();
		Utils.pause(500);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForMessage(infoMessage);
		click(ELEMENT_CANCEL_BUTTON_ADD_PAGE_NULL_TITLE);
		click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
		click(button.ELEMENT_OK_BUTTON);
		Utils.pause(3000);
		
		//Restore data
		goToWikiPage("Untitled");
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69666
	 * Test Case ID 004
	 * Add new page when content is blank
	 */
	@Test
	public void test04_AddNewPageWithBlankContent(){
		String title = "TestCase 004";
		String content = "";
		int mode = 0;    
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69665
	 * Test Case ID 005
	 * Add new page has the same title with existing page
	 */
	@Test
	public void test05_AddNewPageWithSameTitle(){
		String title = "TestCase 005";
		String content = title + " Content";
		int mode = 0;
		String errorMessage = "The page title already exists. Please select another one.";
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);

		//Create a new page with the same title
		addBlankWikiPage(title, content, mode, false, errorMessage);
		//cancel();
		//waitForWikiConfirmation("Are you sure to leave this page?");
		//click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);

		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69668
	 * Test Case ID 006
	 * Add new page when user does not have add page permission on space
	 */
	@Test
	public void test06_AddNewPageWithNotPermission(){    
		int mode = 0;
		String[] userGroup = {DATA_USER3};
		String[] userGroupR = {"any"};
		By ELEMEN_USER = By.xpath("//div[@class='Id' and contains(text(),'" + userGroup[0] + "')]");
		//("//div[@class='Id' and @title='" + userGroup[0] + "']");
		//Add view permission for James
		goToWiki();
		deleteSpacePermission(userGroupR[0]);
		addSpacePermission(mode, userGroup);
		waitForAndGetElement(ELEMEN_USER);

		//Verify that James can't add new page
		magAcc.signOut();
		magAcc.signIn(DATA_USER3,DATA_PASS);
		goToWiki();
		waitForElementNotPresent(ELEMENT_ADD_PAGE_LINK);

		//Reset data
		magAcc.signOut();
		magAcc.signIn(DATA_USER1,DATA_PASS);
		goToWiki();
		deleteSpacePermission(userGroup[0]);
		addSpacePermission(0, userGroupR);
		//check(By.id("EDITPAGE" + userGroupR[0]));
		click(By.id("EDITPAGE" + userGroupR[0]), 2);
		button.save();
		waitForMessage(MSG_PERMISSION_SAVE);
		dialog.closeMessageDialog();
		waitForTextNotPresent(MSG_PERMISSION_SAVE);
	}

	/**
	 * Qmetry ID: 69790
	 * Test Case ID 007
	 * Preview a page
	 */ 
	@Test
	public void test07_PreviewPage(){
		String title = "TestCase 007";
		String content = title + " Content";
		int mode = 0;
		By uiWikiPageTitlePreview = By.xpath("//div[@class='uiWikiPageTitlePreview' and contains(text(),'" + title + "')]");
		By wikiContent = By.xpath("//div[@class='uiWikiContentDisplay']/p[contains(text(),'" + title + "')]");

		goToWiki();
		previewWikiPage(title, content, mode);
		waitForAndGetElement(uiWikiPageTitlePreview);
		waitForAndGetElement(wikiContent);

		//click(button.ELEMENT_CLOSE_WINDOW);
		button.closeWindow();
		waitForElementNotPresent(wikiContent);
		click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
	}

	/**
	 * Qmetry ID: 69700
	 * Test Case ID 008
	 * Cancel creating new wiki page
	 * --Update PLF4: button OK on confirm message is not working
	 * ==> FIXED (@vuna)
	 */ 
	@Test
	public void test08_CancelAddNewPage(){
		String title = "TestCase 008";
		String content = title + " Content";
		int mode = 0;   
		//Cancel creating a new wiki page
		goToWiki();
		addBlankWikiPage(title, content, mode, true);
		waitForTextNotPresent(title);

		//Reset data
		//deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69722
	 * Test Case ID 012
	 * Create new page with Bold effect
	 */  
	@Test
	public void test12_addNewPageWithBold(){
		String title = "TestCase 012";
		String content = "**" +title + " Content**";
		int mode = 0;
		By ELEMENT_CONTENT = By.xpath("//strong[text()='" + content.replace("**", "") + "']");
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);
		waitForAndGetElement(ELEMENT_CONTENT);
		info("Create new page successfully");
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();    
	}

	/**
	 * Qmetry ID: 69725
	 * Test Case ID 013
	 * Create new page with Italic effect
	 */  
	@Test
	public void test13_addNewPageWithItalic(){
		String title = "TestCase 013";
		String content = "//" +title + " Content//";
		int mode = 0;
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);
		waitForAndGetElement(By.xpath("//em[text()='" + content.replace("//", "") + "']"));
		info("Create new page successfully");
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();    
	}

	/**
	 * Qmetry ID: 69728
	 * Test Case ID 014
	 * Create new page with Strike effect
	 */  
	@Test
	public void test14_addNewPageWithStrike(){
		String title = "TestCase 014";
		String content = "--" +title + " Content--";
		int mode = 0;
		By ELEMENT_CONTENT = By.xpath("//del[text()='" + content.replace("--", "") + "']");
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);
		waitForAndGetElement(ELEMENT_CONTENT);
		info("Create new page successfully");
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69729
	 * Test Case ID 015
	 * Create new page with Underline effect
	 */  
	@Test
	public void test15_addNewPageWithUnderline(){
		String title = "TestCase 015";
		String content = "__" +title + " Content__";
		int mode = 0;
		By ELEMENT_CONTENT = By.xpath("//ins[text()='" + content.replace("__", "") + "']");
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);
		waitForAndGetElement(ELEMENT_CONTENT);
		info("Create new page successfully");
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69724
	 * Test Case ID 016
	 * Create new page with Heading effect
	 */  
	@Test
	public void test16_addNewPageWithHeading(){
		String title = "TestCase 016";
		String content = "=" +title + " Content=";
		int mode = 0;
		By ELEMENT_CONTENT = By.xpath("//h1/span[text()='" + content.replace("=", "") + "']");
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);
		waitForAndGetElement(ELEMENT_CONTENT);
		info("Create new page successfully");
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69723
	 * Test Case ID 019
	 * Create new page with Bulleted list effect
	 */  
	@Test
	public void test19_addNewPageWithList(){
		String title = "TestCase 019";
		String content = "* item 1\n";
		content += "** item 2\n";
		content += "*** item 3\n";
		content += "* item 4\n";
		int mode = 0;
		By ELEMENT_CONTENT = By.xpath("//div[@class='uiWikiContentDisplay']//ul/li[text()='item 1']/ul/li[text()='item 2']/ul/li[text()='item 3']/../../../../following::li[text()='item 4']");
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);
		waitForAndGetElement(ELEMENT_CONTENT);
		info("Create new page successfully");
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69727
	 * Test Case ID 020
	 * Create new page with Numbered list effect
	 */  
	@Test
	public void test20_addNewPageWithNumberedList(){
		String title = "TestCase 020";
		String content = "1. item 1\n";
		content += "11. item 2\n";
		content += "111. item 3\n";
		content += "1. item 4\n";
		int mode = 0;
		By ELEMENT_CONTENT = By.xpath("//div[@class='uiWikiContentDisplay']//ol/li[text()='item 1']/ol/li[text()='item 2']/ol/li[text()='item 3']/../../../../following::li[text()='item 4']");
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);
		waitForAndGetElement(ELEMENT_CONTENT);
		info("Create new page successfully");
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69721
	 * Test Case ID 021
	 * Create new page with Table effect
	 */  
	@Test
	public void test21_addNewPageWithTable(){
		String title = "TestCase 021";
		String content = "|=Title 1|=Title 2\n";
		content += "|Word 1|Word 2\n";
		int mode = 0;
		By ELEMENT_CONTENT = By.xpath("//div[@class='uiWikiContentDisplay']//table/tbody/tr/th[text()='Title 1']/../th[text()='Title 2']/../following::tr/td[text()='Word 1']/../td[text()='Word 2']");
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);
		waitForAndGetElement(ELEMENT_CONTENT);
		info("Create new page successfully");
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Qmetry ID: 69726
	 * Test Case ID 022
	 * Create new page with Link effect
	 */
	@Test
	public void test22_addNewPageWithLink(){
		String title = "TestCase 022";
		String content = "[[" + title + " Content]]";
		int mode = 0;
		By ELEMENT_CONTENT = By.xpath("//span[@class='wikicreatelink']/a/span[text()='" + content.replace("[[", "").replace("]]", "") + "']");
		//Create new page with a blank template
		goToWiki();
		addBlankWikiPage(title, content, mode);
		waitForAndGetElement(ELEMENT_CONTENT);
		info("Create new page successfully");
		//Reset data
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}
}
