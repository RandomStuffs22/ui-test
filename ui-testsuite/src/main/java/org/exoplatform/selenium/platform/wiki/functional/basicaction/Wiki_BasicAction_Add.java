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
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.wiki.ManageDraft;
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
public class Wiki_BasicAction_Add extends ManageDraft{

	ManageAccount magAcc;
	Dialog dialog;
	Button button;	
	ManageMember mMember;

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		magAcc = new ManageAccount(driver);
		dialog = new Dialog(driver);	
		button = new Button(driver, this.plfVersion);	
		mMember = new ManageMember(driver, this.plfVersion);	
		magAcc.signIn("john", DATA_PASS);
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
		String[] userGroup = {"james"};
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
		magAcc.signIn("james",DATA_PASS);
		goToWiki();
		waitForElementNotPresent(ELEMENT_ADD_PAGE_LINK);

		//Reset data
		magAcc.signOut();
		magAcc.signIn("john",DATA_PASS);
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
	
	/**
	 * Case ID:70776.
	 * Test Case Name: Add a Draft to Wiki Intranet.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test01_AddADraftToWikiIntranet() {
		info("Test 1: Add a Draft to Wiki Intranet");
		String title = "Case 70776";
		String content = "Content 70776";

		/*
		- Login portal
		- Connect to Intranet/Wiki
		- Click [Add page]/ [Blank Page]
		- Add content to the page
		 *Input Data: 
		 *Expected Outcome: 
		- An empty template is displayed
		- The draft is created after 30sA message is displayed "Draft saved at HH:MM"		*/ 
		goToWiki();
		goToAddBlankPage();
		addWikiPageSourceEditor(title, content);
		Utils.pause(30000);
		assert isElementPresent(ELEMENT_DRAFT_NOTIFY);	

		//Delete data test
		goToManageDraft();
		deleteDraft(title);
	}

	/**
	 * Case ID:74595.
	 * Test Case Name: Add a Draft to Wiki space.
	 * Pre-Condition: create a space or join a space with an application wiki available
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test02_AddADraftToWikiSpace() {
		info("Test 2: Add a Draft to Wiki space");
		String spaceName = "Space 74595";
		String title = "Case 74595";
		String content = "Content 74595";

		/*
		- Connect to a space and choose the tab wiki
		 *Input Data: 
		 *Expected Outcome: The wiki application is displayed		*/
		mMember.goToAllSpaces();
		mMember.addNewSpace(spaceName, "");
		goToWikiFromSpace(spaceName);

		/*
		- Choose [Add page] 
		-> [Blank page]
		- Add Title
		- Add a content
		 *Input Data: 
		 *Expected Outcome: 
		- <br/>An empty template is displayed
		- The draft is created after 30s<br/>A message is displayed "Draft saved at HH:MM"		*/ 
		goToAddBlankPage();
		addWikiPageSourceEditor(title, content);
		Utils.pause(30000);
		assert isElementPresent(ELEMENT_DRAFT_NOTIFY);

		//Delete data test
		mMember.goToAllSpaces();
		mMember.deleteSpace(spaceName, 300000);
	}

	/**
	 * Case ID:71100.
	 * Test Case Name: Add a page with add new link.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test03_AddAPageWithAddNewLink() {
		info("Test 3: Add a page with add new link");
		String pageLink = "Page Link 71100";
		String title = "Case 71100";
		String label = "Link to New Link";
		String content = "Update content 71100";

		/*Add page
		- Go to Wiki
		- Click Add Page => Blank Page icon in toolbar action
		- Click on Rich TextEditor icon in toolbar
		- Input data valid for Title page and Page's content
		- Click Link in menu
		- Select wiki page
		- Select My recent changes
		- Double click an item to and type the name of the page to be created.
		- Click [Link Setting]
		- Input label and tooltip for link
		- Check or uncheck [Open in new window]
		- Click Create Link
		- Click on Save icon in toolbar
		 *Input Data: 
		 *Expected Outcome: 
		- A new page is added successful and displayed with properties 
		- This page is listed with page containing the link		*/
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, "");
		info("Input new page link");
		insertnewPageLink2WikiPage(pageLink,label, "Go to pageLink", true);
		Utils.pause(500);		
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(By.linkText(title));
		goToWikiPage("Wiki Home/"+title);

		/*
		- Click on name of link
		 *Input Data:  
		 *Expected Outcome: 
		- Show new page with name created above and user can add content for it		*/ 
		click(By.linkText(label));
		if(waitForAndGetElement(ELEMENT_RICHTEXT_BUTTON,5000,0)!=null){
			click(ELEMENT_RICHTEXT_BUTTON);
			waitForAndGetElement(ELEMENT_SOURCE_EDITOR_BUTTON);
		}
		type(ELEMENT_CONTENT_WIKI_INPUT,content,true);
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(By.linkText(pageLink));
		Utils.pause(500);	

		//Delete data test
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();	
	}

	/**
	 * Case ID:71097.
	 * Test Case Name: Add a page with link wiki page existed.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14 
	 */
	@Test 
	public void test04_AddAPageWithLinkWikiPageExisted() {
		info("Test 4: Add a page with link wiki page existed");
		String pageLink = "Page Link 71097";
		String title = "Case 71097";
		String content = "Content case 71097";
		String label = "Link to pageLink";

		/*Add page
		- Go to Wiki
		- Click Add Page => Blank Page icon in toolbar action
		- Click on Rich TextEditor icon in toolbar
		- Input data valid for Title page and Page's content
		- Click Link in menu
		- Select wiki page
		- Select all page tab
		- Choose a page in list and click select
		- Input label and tooltip for link
		- Check or uncheck [Open in new window]
		- Click Create Link
		- Click on Save icon in toolbar
		 *Input Data: 
		 *Expected Outcome: 
		- A new page is added successful and displayed with properties 
		- This page is listed with page containing the link		*/
		goToWiki();
		addBlankWikiPage(pageLink, content, 0);
		Utils.pause(500);
		goToAddBlankPage();
		addWikiPageRichText(title, "");
		insertPageLink2WikiPage(true, pageLink, label, "Go to pageLink", true);
		typeEnterInRichText();
		Utils.pause(500);		
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);

		/*
		- Click on name of link
		 *Input Data: 
		 *Expected Outcome: 
		- Show page successfully		*/ 
		goToWikiPage("Wiki Home/"+title);
		waitForAndGetElement(By.linkText(label));
		click(By.linkText(label));

		//Delete data test
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
		goToWikiPage("Wiki Home/"+pageLink);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:71103.
	 * Test Case Name: Add attached file when exits.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14 
	 */
	@Test 
	public  void test05_AddAttachedFileWhenExits() {
		info("Test 5: Add attached file when exits");
		String attachFile = "AttachFile";
		String title = "Case 71103";
		String content = "Add attached file when exits";
		String link = "Wiki_Sniff_Attachment_01.doc";
		
		/*
		- Add new page or edit a page
		- Click on Rich TextEditor icon in toolbar
		-Click Link in menu and Select Attached File
		 *Input Data: 
		 *Expected Outcome: 
		- Attached File form appear		*/
		goToWiki();
		addBlankWikiPageHasAttachment(attachFile, content, link);

		/*
		- Select an attachment to link to from the list below, by clicking on name of file
		- click Select
		- Input tooltip for file
		- Click Create Link button
		- Click Save Page
		 *Input Data: 
		 *Expected Outcome: Add attach file is added successfully in content of page
		- Page is add/edited successfully		*/
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, "");
		insertAttachFileExits(attachFile, link, "Attach File", true);
		Utils.pause(500);		
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(By.linkText(title));

		/*
		- Click on name of attach file
		 *Input Data: 
		 *Expected Outcome: Show content of attach file		*/ 
		goToWikiPage("Wiki Home/"+title);
		waitForAndGetElement(By.linkText(link));

		//Delete data test
		deleteCurrentWikiPage();
		goToWikiPage("Wiki Home/"+attachFile);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:71108.
	 * Test Case Name: Add attached file with new file.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test06_AddAttachedFileWithNewFile() {
		info("Test 6: Add attached file with new file");
		String file1 = "Wiki_Sniff_Attachment_01.doc";
		String title = "Case 71108";
		String label = "Label 71108";
		String tooltip = "Tooltip 71108";

		/*
		- Add new page or edit a page
		- Click on Rich TextEditor icon in toolbar
		-Click Link in menu and Select Attached File
		 *Input Data: 
		 *Expected Outcome: 
		- Attached File form appear		*/
		goToWiki();
		Utils.pause(500);
		goToAddBlankPage();
		addWikiPageRichText(title, "");

		/*
		- In current page tab: double click an item to automatically select it 
		- Select the path to the file to be attached by click Browser
		- Choose file which want to upload and 
		- Click Link Setting
		- Input tooltip 
		- Click Create Link button
		- Click Save Page
		 *Input Data: 
		 *Expected Outcome: Add attach file is added successfully in content of page
		- Page is add/edited successfully		*/
		info("Attach new file");
		insertAttachNewFile(file1, label, tooltip, true);
		Utils.pause(500);
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(By.linkText(title));

		/*
		- Click on name of attach file
		 *Input Data: 
		 *Expected Outcome: Show content of attach file		*/ 		
		goToWikiPage("Wiki Home/"+title);
		waitForAndGetElement(By.linkText(label));

		//Delete data test
		deleteCurrentWikiPage();	
	}

	/**
	 * Case ID:71104.
	 * Test Case Name: Add email address.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test07_AddEmailAddress() {
		info("Test 7: Add email address");
		String title = "Case 71104";
		String email = "test01@exoplatform.com";
		String label = "EmailAddress";
		String tooltip = "Email Address link";

		/*
		- Add new page or edit a page
		- Click on Rich TextEditor icon in toolbar
		-Click Link in menu and Select Email Address
		 *Input Data: 
		 *Expected Outcome: 
		- Attached File form appear		*/
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, "");

		/*
		- Type the email address you want to link to, e.g. 'example@domain.com'.
		- click Select
		- Input lable or tooltip for mail
		- Check or uncheck Open in new window
		- Click Create Link button
		- Click Save Page
		 *Input Data: 
		 *Expected Outcome: Email address is added successfully in content of page
		- Page is add/edited successfully		*/		
		insertEmailLink2WikiPage(email, label, tooltip, true);
		typeEnterInRichText();
		Utils.pause(500);
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(By.linkText(title));

		/*
		- Move cursor on label of mail
		 *Input Data: 
		 *Expected Outcome: Show mail with format mailto:email_address		*/
		goToWikiPage("Wiki Home/"+title);
		mouseOver(By.linkText(label), true);
		waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));

		//Delete data test		
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:71102.
	 * Test Case Name: Add web page.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test08_AddWebPage() {
		info("Test 8 Add web page");
		String title = "Case 71102";
		String webpage = "google.com";
		String label = "Webpage";
		String tooltip = "Webpage link";

		/*Add page
		- Go to Wiki
		- Click Add Page => Blank Page icon in toolbar action
		- Click on Rich TextEditor icon in toolbar
		- Input data valid for Title page and Page's content
		-Click Link in menu
		- Select web page
		- Type the address of the web page to create the link to. (ex: www.google.com)
		- Input label and tooltip for link
		- Check or uncheck [Open in new window]
		- Click Create Link
		- Click on Save icon in toolbar
		 *Input Data: 
		 *Expected Outcome: 
		- A new page is added successful and displayed with properties 
		- This page is listed with page containing the link		*/
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, "");
		insertwebpageLink2WikiPage(webpage, label, tooltip, true);
		Utils.pause(500);
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		Utils.pause(500);
		waitForAndGetElement(By.linkText(title));

		/*
		- Click on name of link
		 *Input Data:  
		 *Expected Outcome: 
		- Show page successfully		*/ 
		goToWikiPage("Wiki Home/"+title);
		click(By.linkText(label));
		driver.getCurrentUrl().contains(webpage);

		//Delete data test		
		info("close webpage");
		goToWiki();
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:71110.
	 * Test Case Name: Attach image in list.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test09_AttachImageInList() {
		info("Test 13 Attach image in list");
		String title = "Case 71110 Attach image in list";
		String file = "Winter.jpg";
		/*
		- Add new page or edit a page
		- Click on Rich TextEditor icon in toolbar
		-Click Image in menu and Select Attach image
		 *Input Data: 
		 *Expected Outcome: 
		- Attached File form appear		*/
		goToWiki();
		goToAddBlankPage();
		Utils.pause(500);
		addWikiPageRichText(title, "");
		insertImageFile(file,true);
		Utils.pause(500);

		/*
		- Select an image to insert from the list below, by clicking it
		- Click Image Settings
		- Type the width/height of the image
		- Choose the way the image is positioned in the text
		- Choose the way the image is vertically aligned in the line of text
		- Click Insert Image button
		- Click Save Page
		 *Input Data: 
		 *Expected Outcome: Add attach file is added successfully in content of page
		- Page is add/edited successfully		*/ 

		info("Attach image in list");
		mouseOverAndClick(ELEMENT_IMAGE_LINK);
		mouseOverAndClick(ELEMENT_IMAGE_EDIT_LINK);
		editImage(file, "300", "300", "image71110", alignmentType.LEFT,true);
		Utils.pause(500);		
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(By.linkText(title));

		//Delete data test
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();	
	}

	/**
	 * Case ID:70778.
	 * Test Case Name: Autosave a page without Title.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test10_AutosaveAPageWithoutTitle() {
		info("Test 10 Autosave a page without Title");
		String content = "Content 70778";
		String time = ""; 
		String title = "";

		/*
		- Add a page wiki and keep the field "Title" empty
		- Keep the page without changes for 30s
		 *Input Data: 
		 *Expected Outcome: 
		- A draft is saved
		- A page is saved with a generic title within the format: "Untitled_YearMonthDayTime."		*/ 
		goToWiki();
		goToAddBlankPage();
		addWikiPageSourceEditor("", content);
		time = getDate(0,"yyyyMMddHH"); 
		Utils.pause(30000); 		
		assert isElementPresent(ELEMENT_DRAFT_NOTIFY);
		goToManageDraft();
		waitForAndGetElement("//*[@id='UIWikiDraftGrid']//*[contains(text(),'${title}')]".replace("${title}", time));
		title = waitForAndGetElement("//*[@id='UIWikiDraftGrid']//*[contains(text(),'${title}')]".replace("${title}", time)).getText();

		//Delete data test
		goToManageDraft();
		deleteDraft(title);
	}

	/**
	 * Case ID:69720.
	 * Test Case Name: Create new page using existing template.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test11_CreateNewPageUsingExistingTemplate() {
		info("Test 11 Create new page using existing template");
		String title1 = "Case 69720 Two Column layout";
		String title2 = "Case 69720 Three Column layout";
		String title3 = "Case 69720 Status Meeting";
		String title4 = "Case 69720 HOW-TO Guide";
		String title5 = "Case 69720 Leave Planning";

		/*
		- Choose path to add new page
		- Go to Add Page/From Template
		 *Input Data: 
		 *Expected Outcome: Form to create new page is shown		*/
		goToWiki();
		/*
		- Choose template is Two Column layout in list and click Select
		- Put the title for this page
		- Put the content of page
		- Click Preview if you want to see how your page looks like.
		- Click Save
		 *Input Data: 
		 *Expected Outcome: New page is created successful with two column layout		*/
		info("Add wiki template page1");
		addWikiPageFromTemplate(title1, 0, "Two-Column_Layout");
		click(By.linkText(title1));
		waitForAndGetElement(ELEMENT_TWO_LAYOUT_RIGHT);
		waitForAndGetElement(ELEMENT_TWO_LAYOUT_LEFT);

		/*
		- Choose template is Three Column layout in list and click Select
		- Put the title for this page
		- Put the content of page
		- Click Preview if you want to see how your page looks like.
		- Click Save
		 *Input Data: 
		 *Expected Outcome: New page is created successful with three column layout		*/
		info("Add wiki template page2");
		goToWikiHome();
		addWikiPageFromTemplate(title2, 0, "Three-Column_Layout");
		click(By.linkText(title2));
		waitForAndGetElement(ELEMENT_THREE_LAYOUT_RIGHT);
		waitForAndGetElement(ELEMENT_THREE_LAYOUT_MID);
		waitForAndGetElement(ELEMENT_THREE_LAYOUT_LEFT);

		/*
		- Choose template is Status Meeting layout in list and click Select
		- Put the title for this page
		- Put the content of page
		- Click Preview if you want to see how your page looks like.
		- Click Save
		 *Input Data: 
		 *Expected Outcome: New page is created successful with Status Meeting layout		*/
		info("Add wiki template page3");
		goToWikiHome();
		addWikiPageFromTemplate(title3, 0, "Status_Meeting");
		click(By.linkText(title3));
		waitForAndGetElement(EMENENT_STATUS_LAYOUT.replace("${title}", "Work Streams in Progress"));
		waitForAndGetElement(EMENENT_STATUS_LAYOUT.replace("${title}", "Status (R/Y/G)"));
		waitForAndGetElement(EMENENT_STATUS_LAYOUT.replace("${title}", "Actions This Week"));
		waitForAndGetElement(EMENENT_STATUS_LAYOUT.replace("${title}", "Risks/Issues & Mitigation Plans"));
		waitForAndGetElement(EMENENT_STATUS_LAYOUT.replace("${title}", "Dependencies on Other Projects"));
		waitForAndGetElement(EMENENT_STATUS_LAYOUT.replace("${title}", "Dogfood Targets (Milestone, System, Date)"));
		waitForAndGetElement(EMENENT_STATUS_LAYOUT.replace("${title}", "Original Target Release Date"));
		waitForAndGetElement(EMENENT_STATUS_LAYOUT.replace("${title}", "New Expected Release Date"));

		/*
		- Choose template is HOW-TO Guide layout in list and click Select
		- Put the title for this page
		- Put the content of page
		- Click Preview if you want to see how your page looks like.
		- Click Save
		 *Input Data: 
		 *Expected Outcome: New page is created successful with HOW
		-TO Guide layout		*/
		info("Add wiki template page4");
		goToWikiHome();
		addWikiPageFromTemplate(title4, 0, "HOW-TO_Guide");
		click(By.linkText(title4));
		waitForAndGetElement(EMENENT_HOW_LAYOUT.replace("${title}", "Requirements"));
		waitForAndGetElement(EMENENT_HOW_LAYOUT.replace("${title}", "Instructions"));
		waitForAndGetElement(EMENENT_HOW_LAYOUT.replace("${title}", "Tips & Warnings"));
		waitForAndGetElement(EMENENT_HOW_LAYOUT.replace("${title}", "Related"));

		/*
		- Choose template is Leave Planning layout in list and click Select
		- Put the title for this page
		- Put the content of page
		- Click Preview if you want to see how your page looks like.
		- Click Save
		 *Input Data: 
		 *Expected Outcome: New page is created successful with Leave Planning layout		*/ 
		info("Add wiki template page5");
		goToWikiHome();
		addWikiPageFromTemplate(title5, 0, "Leave_Planning");
		click(By.linkText(title5));
		Utils.pause(1000);
		waitForAndGetElement(EMENENT_LEAVE_PLANING_LAYOUT);

		//Delete data test
		deleteCurrentWikiPage();		
		goToWikiPage("Wiki Home/"+title1);
		deleteCurrentWikiPage();
		goToWikiPage("Wiki Home/"+title2);
		deleteCurrentWikiPage();
		goToWikiPage("Wiki Home/"+title3);
		deleteCurrentWikiPage();
		goToWikiPage("Wiki Home/"+title4);
		deleteCurrentWikiPage();		
	}

	/**
	 * Case ID:71096.
	 * Test Case Name: Create Page using Rich Text Editor.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test12_CreatePageUsingRichTextEditor() {
		info("Test 12 Create Page using Rich Text Editor");
		String title = "Case 71096"; 
		String content = "Content case 71096";

		/*Add page
		- Go to Wiki
		- Click Add Page => Blank Page icon in toolbar action
		- Click on Rich TextEditor icon in toolbar
		- Input data valid for Title page and Page's content
		- Click on Save icon in toolbar
		 *Input Data: 
		 *Expected Outcome: 
		- A new page is added successful and displayed with properties 
		- This page is listed in navigation tree		*/ 
		goToWiki();
		addBlankWikiPage(title, content, 1);
		Utils.pause(500);
		waitForAndGetElement(By.linkText(title));

		//Delete data test
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:71113.
	 * Test Case Name: External image.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test13_ExternalImage() {
		info("Test 13 External image");
		String title = "Case 71113";		
		String imageLocation = "http://www.menucool.com/slider/prod/image-slider-4.jpg";		

		/*Add page
		- Go to Wiki
		- Click Add Page => Blank Page icon in toolbar action
		- Click on Rich TextEditor icon in toolbar
		- Input data valid for Title page and Page's content
		- Click Image in menu
		- External image
		- Type or paste the address of the image to insert, e.g. 'www.example.com/image.png'
		- Click Image Settings
		- Type the width/height of the image
		- Choose the way the image is positioned in the text
		- Choose the way the image is vertically aligned in the line of text
		- Click Insert Image button
		- Click Save Page
		 *Input Data: 
		 *Expected Outcome: Add attach file is added successfully in content of page
		- Page is add/edited successfully		*/ 
		goToWiki();
		goToAddBlankPage();
		Utils.pause(500);
		addWikiPageRichText(title, "");
		insertExternalImage(imageLocation, "300", "300", "image71113", alignmentType.LEFT,true);
		Utils.pause(500);		
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(By.linkText(title));

		//Delete data test
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();		
	}

	/**
	 * Case ID:69790.
	 * Test Case Name: Preview a page.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test14_PreviewAPage() {
		info("Test 14 Preview a page");
		String title = "Case 69790 Preview a page";
		/*
		- Choose path to add new page
		- Go to Add Page /Blank Page (or From Template)
		 *Input Data: 
		 *Expected Outcome: Form to create new page is shown		*/
		/*
		- Put the title for this page
		- Put the content of page
		 *Input Data: 
		 *Expected Outcome: All fields is inputed value		*/
		/*Click on Preview 
		 *Input Data: 
		 *Expected Outcome: Content of page is shown		*/
		/*Close Preview
		 *Input Data: 
		 *Expected Outcome: 
		- Wiki page is shown again
		- Content or title are still the same
		- Potential attached files are still attached		*/ 
		info("Add wiki template page");
		goToWiki();
		addWikiPageFromTemplate(title, 0, "Two-Column_Layout");
		waitForAndGetElement(By.linkText(title));

		//Delete data test
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:106576.
	 * Test Case Name: Preview a page with image by another user.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test15_PreviewAPageWithImageByAnotherUser() {
		info("Test 31 Preview a page with image by another user");
		String title = "Case 106576"; 		
		String file = "Winter.jpg";
		/*
		- Connect with the user admin
		- Go to Intranet/Wiki
		- Add a page
		- Insert an image in this wiki page
		- Save
		 *Input Data: 
		 *Expected Outcome: Page is created successfully		*/
		goToWiki();
		goToAddBlankPage();
		Utils.pause(500);
		addWikiPageRichText(title, "");
		insertImageFile(file, true);
		Utils.pause(2000);
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(By.linkText(title));
		/*
		- Connect with normal user
		- Go to Intranet/Wiki
		- Open wiki at step 1
		 *Input Data: 
		 *Expected Outcome: Both content and image of wiki page is well shown		*/ 
		info("An other user view image");
		magAcc.userSignIn(userType.DEVELOPER);
		goToWiki();
		goToWikiPage("Wiki Home/"+title);
		waitForAndGetElement(ELEMENT_CONTENT_WIKI_IMG);

		//Delete data test
		magAcc.userSignIn(userType.ADMIN);
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:74596.
	 * Test Case Name: Resume a draft after loosing a session.
	 * Pre-Condition: edit a page
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 * 
	 * ---Pending this case because can't auto keep the session opened until time expiration
	 */
	@Test (groups = "pending")
	public  void test16_ResumeADraftAfterLoosingASession() {
		info("Test 32 Resume a draft after loosing a session");
		/*
		- Go to Intranet/Wiki
		- Edit /create a wiki page
		 *Input Data: 
		 *Expected Outcome: A draft is created after 30s		*/


		/*keep the session opened until time expiration
		 *Input Data: 
		 *Expected Outcome: The session is expired		*/


		/*Re-connect to the session Go to "My draft"
		 *Input Data: 
		 *Expected Outcome: 
		- the draft is displayed in the list		*/


		/*Click on the link "Title" of the draft
		 *Input Data: 
		 *Expected Outcome: The page is displayed in edit mode and the last saved content is displayed		*/ 

	}

	/**
	 * Case ID:71101.
	 * Test Case Name: Search page to add link.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test17_SearchPageToAddLink() {
		info("Test 17 Search page to add link");
		String pageLink = "Page link 71101"; 
		String title = "Case 71101";
		String content = "Search page to add link";

		/*Add page
		- Go to Wiki
		- Click Add Page => Blank Page icon in toolbar action
		- Click on Rich TextEditor icon in toolbar
		- Input data valid for Title page and Page's content
		-Click Link in menu
		- Select wiki page
		- Select Search tab
		- Type page name which want to search and click Search
		- Choose page which show in list and click Select
		- Input label and tooltip for link
		- Check or uncheck [Open in new window]
		- Click Create Link
		- Click on Save icon in toolbar
		 *Input Data: 
		 *Expected Outcome: 
		- A new page is added successful and displayed with properties 
		- This page is listed with page containing the link		*/
		goToWiki();
		addBlankWikiPage(pageLink, content, 0);
		Utils.pause(500);
		goToAddBlankPage();
		addWikiPageRichText(title, "");
		insertPageLink2WikiPage(true, pageLink, "Link to pageLink", "Go to pageLink", true);
		typeEnterInRichText();
		Utils.pause(500);

		//Delete data test
		click(By.linkText(pageLink));
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:71111.
	 * Test Case Name: Upload new image.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test18_UploadNewImage() {
		info("Test 18 Upload new image");
		String title = "Case 71111 Upload new image";
		String file = "Winter.jpg";

		/*
		- Add new page or edit a page
		- Click on Rich TextEditor icon in toolbar
		-Click Image in menu and Select Attach image
		 *Input Data: 
		 *Expected Outcome: 
		- Attached File form appear		*/
		goToWiki();
		goToAddBlankPage();
		Utils.pause(500);
		addWikiPageRichText(title, "");

		/*
		- Double click an item to automatically select it
		- Select the path to the image to be uploaded.
		- Click Image Settings
		- Type the width/height of the image
		- Choose the way the image is positioned in the text
		- Choose the way the image is vertically aligned in the line of text
		- Click Insert Image button
		- Click Save Page
		 *Input Data: 
		 *Expected Outcome: Add attach file is added successfully in content of page
		- Page is add/edited successfully		*/ 
		insertImageFile(file,true);
		Utils.pause(500);
		info("Upload a image");
		editImage(file, "200", "200", "image71111", alignmentType.LEFT,true);
		Utils.pause(500);		
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(By.linkText(title));

		//Delete data test
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:74597.
	 * Test Case Name: View a draft for another user.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/13 14:10:14
	 */
	@Test
	public  void test19_ViewADraftForAnotherUser() {
		info("Test 19 View a draft for another user");
		String title = "Case 74597";
		String content = "View a draft for another user";

		/*
		- Connect with the user A
		- Go to Intranet/Wiki
		- Add a page
		 *Input Data: 
		 *Expected Outcome: The draft is saved after 30s		*/
		goToWiki();
		goToAddBlankPage();
		addWikiPageSourceEditor(title, content);
		Utils.pause(30000);
		assert isElementPresent(ELEMENT_DRAFT_NOTIFY);
		info("View on Draft page of UserA");
		goToWiki();
		goToManageDraft();
		waitForAndGetElement(ELEMENT_DRAFT_TITLE.replace("${title}", title));

		/*
		- Connect with the user B
		- Go to Intranet/Wiki
		- From the menu "Browse", choose "My drafts"
		 *Input Data: 
		 *Expected Outcome: The draft created by the user A doesn't appears in the list of drafts of the user B		*/ 

		magAcc.userSignIn(userType.DEVELOPER);
		goToWiki();
		goToManageDraft();
		waitForElementNotPresent(ELEMENT_DRAFT_TITLE.replace("${title}", title));		

		//Delete data test
		magAcc.userSignIn(userType.ADMIN);
		goToWiki();
		goToManageDraft();
		deleteDraft(title);
	}
}
