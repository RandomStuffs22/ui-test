package org.exoplatform.selenium.platform.wiki.functional.basicaction;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.wiki.ManageDraft;
import org.exoplatform.selenium.platform.wiki.RichTextMode;
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
public class Wiki_BasicAction_Edit extends ManageDraft {
	ManageAccount acc;
	Button button; 
	ManageAlert mAlert; 
	RichTextMode rText;
	

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		button = new Button(driver, this.plfVersion);	
		mAlert = new ManageAlert(driver, this.plfVersion);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
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
	
	/**
	 * Case ID:70784.
	 * Test Case Name: Draft in an outdate version status.
	 * Pre-Condition: Make changes on a page with two usersCreate a page
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/12 08:53:54
	 */
	@Test
	public  void test01_DraftInAnOutdateVersionStatus() {
		info("Test 1: Draft in an outdate version status");
		String title = "Case 70784";
		String content = "Content case 70784";
		String contentUpdateA = "Update content 70784";
		String contentUpdateB = "UserB update";
		String user = "demo";
		String userGroup[] = {user};
		/*
		- Connect with the User A
		- Add a page with name is Test
		- Add a content and save
		 *Input Data: 
		 *Expected Outcome: The page is created		*/
		goToWiki();
		addBlankWikiPage(title, content, 0);
		addPagePermission(0,userGroup);
		editPagePermission(user, true, true);

		/*
		- Connect with the User B who have edit permission on "Test" page
		- Edit the page "Test" and add a content
		 *Input Data: 
		 *Expected Outcome: A draft is saved after 30s		*/
		acc.userSignIn(userType.DEVELOPER);
		goToWikiPage("Wiki Home/"+title);
		Utils.pause(1000);
		info("Edit by UserB");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		addWikiPageSourceEditor(title, contentUpdateB);
		Utils.pause(30000);
		waitForAndGetElement(ELEMENT_DRAFT_NOTIFY);

		/*
		- Connect with the user "A" 
		- Edit the page "Test" and add a content
		- Save the page
		 *Input Data: 
		 *Expected Outcome: A new version of the page is created		*/
		acc.userSignIn(userType.ADMIN);
		goToWikiPage("Wiki Home/"+title);
		Utils.pause(1000);
		editWikiPage(title, contentUpdateA, 0);

		/*
		- Connect with the User B: 
		- Open the page "Test" and click [Edit]
		 *Input Data: 
		 *Expected Outcome: A page is opened in edit mode and a warning message is displayed:Your version is out dated, a version of this content has been updated by another user. You can [view your changes] and continue editing or [delete] your draft. "		*/
		acc.userSignIn(userType.DEVELOPER);
		goToWikiPage("Wiki Home/"+title);
		Utils.pause(1000);
		info("Edit by UserB");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		waitForAndGetElement(ELEMENT_ALERT_MESSAGE_PERMISSION);		

		/*From the message, click on the link [View your changes]
		 *Input Data: 
		 *Expected Outcome: The Comparison view is open. it's possible to click on continue editing or cancel the draft.		*/
		click(ELEMENT_VIEW_CHANGE_LINK);
		waitForAndGetElement(EMEMENT_DRAFT_CHANGE_VIEW);

		/*
		- Close the draft view
		- From the message, click on the link [Continue editing]
		 *Input Data: 
		 *Expected Outcome: The page is opened in the editing status		*/
		click(ELEMENT_DRAFT_CLOSE);
		waitForElementNotPresent(EMEMENT_DRAFT_CHANGE_VIEW);
		click(ELEMENT_DRAFT_CONTINUE_EDIT_LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);

		/*
		- re-open the page and click [Edit]
		- From the message, click on the link [delete]
		 *Input Data: 
		 *Expected Outcome: The draft is deleted from the list" My drafts"		*/ 
		goToWikiPage("Wiki Home/"+title);
		Utils.pause(1000);
		info("Delete draft");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		click(ELEMENT_DELETE_DRAFT_LINK);
		goToManageDraft();
		waitForElementNotPresent(ELEMENT_DELETE_DRAFT.replace("${title}", title));

		//Delete data test
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:70785.
	 * Test Case Name: Edit a page with an existing draft.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/12 08:53:54
	 */
	@Test
	public  void test02_EditAPageWithAnExistingDraft() {
		info("Test 2: Edit a page with an existing draft");
		String title = "Case 70785";
		String content = "Content 70785";
		String contentUpdate = "Update 70785";

		/*
		- Add a wiki page and save
		- Edit the page
		- Input a content
		 *Input Data: 
		 *Expected Outcome: The draft is saved after 30s		*/
		goToWiki();
		addBlankWikiPage(title, content, 0);
		info("Edit the page");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		addWikiPageSourceEditor(title, contentUpdate);
		Utils.pause(30000);
		waitForAndGetElement(ELEMENT_DRAFT_NOTIFY);

		/*
		- From the "Wiki Home", Open again the page
		- Edit the page
		 *Input Data: 
		 *Expected Outcome: A warning message is displayed under the title: "A draft of this page was saved on Month Day, Year HH:MM. You can view your changes and decide to resume the draft or delete it.		*/
		goToWikiPage("Wiki Home/"+title);
		Utils.pause(1000);
		info("Show warning message");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		waitForAndGetElement(ELEMENT_DRAFT_MESSAGE);	

		/*From the message click on the link [View your changes]
		 *Input Data: 
		 *Expected Outcome: The page "Draft changes" is displayed		*/
		click(ELEMENT_VIEW_CHANGE_LINK);
		waitForAndGetElement(EMEMENT_DRAFT_CHANGE_VIEW);

		/*
		- Close the page "Draft changes"
		- Click on the link [resume the draft]
		 *Input Data: 
		 *Expected Outcome: The page is displayed in the edit mode.		*/
		click(ELEMENT_DRAFT_CLOSE);
		waitForElementNotPresent(EMEMENT_DRAFT_CHANGE_VIEW);
		click(ELEMENT_DRAFT_RESUME__LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);

		/*
		- Repeat the step 3
		- Click on the link [Delete]
		 *Input Data: 
		 *Expected Outcome: The draft is deleted.		*/
		goToWikiPage("Wiki Home/"+title);
		Utils.pause(1000);
		info("Detele draft");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		click(ELEMENT_DELETE_DRAFT_LINK);
		goToManageDraft();
		waitForElementNotPresent(ELEMENT_DELETE_DRAFT.replace("${title}", title));

		/*Click open again the page and click edit
		 *Input Data: 
		 *Expected Outcome: The warning message isn't displayed		*/ 
		goToWikiPage("Wiki Home/"+title);
		Utils.pause(1000);
		info("Not show warning message");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_DRAFT_MESSAGE);	

		//Delete data test
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:70790.
	 * Test Case Name: Resume a draft after Browser crash.
	 * Pre-Condition: Add/Edit a page
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/12 08:53:54
	 */
	@Test
	public  void test03_ResumeADraftAfterBrowserCrash() {
		info("Test 3: Resume a draft after Browser crash");
		String title = "Case 70790";
		String content = "Content case 70790";

		/*Edit a wiki page and add a content
		 *Input Data: 
		 *Expected Outcome: The draft is added after 30s		*/
		goToWiki();
		goToAddBlankPage();
		addWikiPageSourceEditor(title, content);
		Utils.pause(30000);
		waitForAndGetElement(ELEMENT_DRAFT_NOTIFY);

		/*Force stop the Browser process from the task manager
		 *Input Data: 
		 *Expected Outcome: The Browser is stopped		*/
		driver.quit();

		/*Open the page againGo to "My drafts"
		 *Input Data: 
		 *Expected Outcome: The draft version appears in the list		*/ 
		setUpBeforeTest();
		goToWiki();
		goToManageDraft();
		waitForAndGetElement(ELEMENT_DELETE_DRAFT.replace("${title}", title));

		//Delete data test
		deleteDraft(title);
	}

	/**
	 * Case ID:70791.
	 * Test Case Name: Resume a draft after close without saving.
	 * Pre-Condition: edit a page
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/12 08:53:54
	 */
	@Test
	public  void test04_ResumeADraftAfterCloseWithoutSaving() {
		info("Test 4: Resume a draft after close without saving");
		String title = "Case 70791";
		String content = "Content case 70791";

		/*
		- Go to Intranet/Wiki
		- Add a page
		- Edit the page
		 *Input Data: 
		 *Expected Outcome: A draft version is saved after 30s		*/
		goToWiki();
		goToAddBlankPage();
		addWikiPageSourceEditor(title, content);
		Utils.pause(30000);
		waitForAndGetElement(ELEMENT_DRAFT_NOTIFY);

		/*Close a window of the browser without saving of the page Open the window of the age again Go to [My drafts]
		 *Input Data: 
		 *Expected Outcome: The draft is displayed in the list		*/
		info("Close window");
		driver.quit();
		Utils.pause(500);
		info("Open and go to Draft");
		setUpBeforeTest();
		goToWiki();
		goToManageDraft();
		waitForAndGetElement(ELEMENT_DELETE_DRAFT.replace("${title}", title));

		/*Click on the link "title" of the draft
		 *Input Data: 
		 *Expected Outcome: The page in edit mode is displayed		*/ 
		click(ELEMENT_DRAFT_TITLE.replace("${title}", title));
		info("Edit mode");
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);

		//Delete data test
		goToManageDraft();
		deleteDraft(title);
	}

	/**
	 * Case ID:70793.
	 * Test Case Name: Save a draft of currently saved page.
	 * Pre-Condition: connect with 2 users and edit the same page
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/12 08:53:54
	 */
	@Test
	public  void test05_SaveADraftOfCurrentlySavedPage() {
		info("Test 5: Save a draft of currently saved page");
		String title = "Case 70793";
		String content = "Content 70793";
		String contentUpdate = "Update case 70793";
		String user = "demo";
		String []userGroup = {user};
		String fullName = "John Smith";
		
		/*
		- Connect with the User1
		- Edit a page wiki and add a content
		 *Input Data: 
		 *Expected Outcome: The draft is saved after 30s		*/
		goToWiki();
		addBlankWikiPage(title, content, 0);
		addPagePermission(0,userGroup);
		editPagePermission(user, true, true);	
		info("Edit the page");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		addWikiPageSourceEditor(title, contentUpdate);
		Utils.pause(30000);
		waitForAndGetElement(ELEMENT_DRAFT_NOTIFY);

		/*
		- Connect with user2
		- Open the same page edited by the user 1
		- Click on the icon "Edit"
		 *Input Data: 
		 *Expected Outcome: A warning message is displayed: This Page is currently being edited by Full userName".		*/ 
		acc.userSignIn(userType.DEVELOPER);
		goToWikiPage("Wiki Home/"+title);
		Utils.pause(1000);
		info("Show warning message");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		assert(waitForAndGetElement(ELEMENT_DRAFT_ALERT).getText().contains(ELEMENT_DRAFT_STRING_ALERT.replace("${userName}",fullName)));

		//Delete data test
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:71141.
	 * Test Case Name: Edit attached file.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/12 08:53:54
	 */
	@Test
	public  void test06_EditAttachedFile() {
		info("Test 6: Edit attached file");
		String file1 = "Wiki_Sniff_Attachment_01.doc";
		String title = "Case 71141";
		String label = "Label 71141";
		String tooltip = "Tooltip 71141";

		/*
		- Add new page or edit a page
		- Click on Rich TextEditor icon in toolbar
		- Select link in content
		-Click Link in menu and Select Edit Link
		 *Input Data: 
		 *Expected Outcome: 
		- Attached File form appear		*/
		goToWiki();
		Utils.pause(500);
		goToAddBlankPage();
		addWikiPageRichText(title, "");
		insertAttachNewFile(file1,"","",true);
		Utils.pause(500);		

		/*
		- Choose other attached file
		- click Select
		- Input tooltip for file
		- Click Create Link button
		- Click Save Page
		 *Input Data: 
		 *Expected Outcome: Add attach file is changed successfully in content of page
		- Page is add/edited successfully		*/
		info("Edit attach link");
		editLink(file1,label, tooltip, "", "",true);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:71144.
	 * Test Case Name: Add web page.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/12 08:53:54
	 */
	@Test
	public  void test07_AddWebPage() {
		info("Test 07 Add web page");
		String title = "Case 71144";
		String webpage = "google.com";
		String label = "Webpage";
		String tooltip = "Webpage link";
		String webpageUpdate = "yahoo.com";
		String labelUpdate = "Webpage Update";
		String tooltipUpdate = "Webpage link Update";
		/*
		- Select a page
		- Click on Rich TextEditor icon in toolbar
		- Choose a web page link
		-Click Link in menu
		- Choose Edit link
		- Type orther address of the web page to create the link to. (ex: www.google.com)
		- Input label and tooltip for link
		- Check or uncheck [Open in new window]
		- Click Create Link
		- Click on Save icon in toolbar
		 *Input Data: 
		 *Expected Outcome: 
		- Web page link is changed succesfully		*/
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, "");
		insertwebpageLink2WikiPage(webpage, label, tooltip,true);
		Utils.pause(500);
		info("Edit webpage");
		editLink("",labelUpdate, tooltipUpdate, webpageUpdate,"", true);	
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:71155.
	 * Test Case Name: Edit wiki page link.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/12 08:53:54
	 */
	@Test
	public  void test08_EditWikiPageLink() {
		info("Test 08 Edit wiki page link");
		String pageLink1 = "Page Link1";
		String pageLink2 = "Page Link2";
		String title = "Case 71155";
		String content = "Content case 71155";
		/*
		- Add new page or edit a page
		- Click on Rich TextEditor icon in toolbar
		- Select link in content
		- Click Link in menu and Select Edit Link
		 *Input Data: 
		 *Expected Outcome: 
		- Attached File form appear		*/
		goToWiki();
		addBlankWikiPage(pageLink1, content, 0);
		Utils.pause(500);
		addBlankWikiPage(pageLink2, content, 0);
		Utils.pause(500);
		goToAddBlankPage();
		addWikiPageRichText(title, "");
		insertPageLink2WikiPage(true, pageLink1, "Link to pageLink", "Go to pageLink",true);
		Utils.pause(500);

		/*
		- Choose other page link 
		- click Select
		- Input tooltip for file
		- Click Create Link button
		- Click Save Page
		 *Input Data: 
		 *Expected Outcome: Add attach file is changed successfully in content of page
		- Page is add/edited successfully		*/
		info("Edit page");
		editPageLink2WikiPage(true, pageLink2, "Update link to pageLink", "Go to pageLink",true);

		//Delete data test
		click(By.linkText(pageLink1));
		deleteCurrentWikiPage();		
	}

	/**
	 * Case ID:71159.
	 * Test Case Name: Edit email address.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/12 08:53:54
	 */
	@Test
	public  void test9_EditEmailAddress() {
		info("Test 9 Edit email address");
		String title = "Case 71159";
		String email = "test01@exoplatform.com";
		String label = "EmailAddress";
		String tooltip = "Email Address link";
		String emailUpdate = "test02@exoplatform.com";
		String labelUpdate = "EmailAddress Update";
		String tooltipUpdate = "Email Address link update";
		/*
		- Add new page or edit a page
		- Click on Rich TextEditor icon in toolbar
		- Select email address link in content
		-Click Link in menu and Select Edit Link
		 *Input Data: 
		 *Expected Outcome: 
		- Attached File form appear 		*/
		goToWiki();
		goToAddBlankPage();
		addWikiPageRichText(title, "");
		insertEmailLink2WikiPage(email, label, tooltip, true);
		Utils.pause(500);

		/*
		- Change email address
		- click Select
		- Input tooltip for file
		- Click Create Link button
		- Click Save Page
		 *Input Data: 
		 *Expected Outcome: Add attach file is changed successfully in content of page
		- Page is add/edited successfully		*/
		info("Edit email address");
		editLink("",labelUpdate, tooltipUpdate, "",emailUpdate, true);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:71186.
	 * Test Case Name: Edit image.
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/12 08:53:54
	 */
	@Test
	public  void test10_EditImage() {
		info("Test 10 Edit image");
		String title = "Case 71186";
		String file = "Wiki_Sniff_Attachment_01.jpg";

		/*
		- Add new page or edit a page
		- Click on Rich TextEditor icon in toolbar
		- Choose image in page 
		- Click Image in menu and Edit image
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
		- Select an image to insert from the list below, by clicking it or upload new image
		- Click Image Settings
		- Type the width/height of the image
		- Choose the way the image is positioned in the text
		- Choose the way the image is vertically aligned in the line of text
		- Click Insert Image button
		- Click Save Page
		 *Input Data: 
		 *Expected Outcome: Add attach file is added successfully in content of page
		- Page is add/edited successfully		*/ 
		info("Edit attach image");
		mouseOverAndClick(ELEMENT_IMAGE_LINK);
		mouseOverAndClick(ELEMENT_IMAGE_EDIT_LINK);
		editImage(file, "400", "400", "image71186", alignmentType.LEFT,true);
		Utils.pause(500);		
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);

		//Delete data test
		goToWikiPage("Wiki Home/"+title);
		deleteCurrentWikiPage();
	}

	/**
	 * Case ID:107030.
	 * Test Case Name: Check show message if there are 2 users editing in the same page..
	 * Pre-Condition: 
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/03/12 08:53:54
	 */
	@Test
	public  void test11_CheckShowMessageIfThereAre2UsersEditingInTheSamePage() {
		info("Test 11 Check show message if there are 2 users editing in the same page.");
		String title = "Case 107030";
		String content = "Content 107030";
		String titleUpdate = "Update case 107030";
		String user = "demo";
		String fullName = "John Smith";
		String userGroup[] = {user};
		/*
		- Login as user 1
		- Go to Wiki
		- Create a new page 
		- Input content
		- Save page
		 *Input Data: 
		 *Expected Outcome: Add new page successfully		*/
		goToWiki();
		addBlankWikiPage(title, content, 0);
		addPagePermission(0,userGroup);
		editPagePermission(user, true, true);

		/*
		- Click [Edit] button of that page
		- Edit title & content
		- Wait 30s for auto ->save draft
		 *Input Data: 
		 *Expected Outcome: Page is save to my draft		*/

		info("Edit the page");
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		addWikiPageSourceEditor(titleUpdate, content);
		Utils.pause(30000);
		waitForAndGetElement(ELEMENT_DRAFT_NOTIFY);

		/*
		- Login as user 2
		- Go to Wiki
		- Click on the page user 1 has been created before
		- Click [Edit] button of that page
		- Change title or content for this page
		- Click [Save]
		 *Input Data: 
		 *Expected Outcome: 
		- Show message alert "This page is currently being edited by"user 1"."
		- Edit successfully 		*/
		loginWithAnotherAccOnThesameBrowser("demo", "gtn");
		if(waitForAndGetElement(ELEMENT_WIKI_LINK, 5000,0,0,newDriver)!=null)
			waitForAndGetElement(ELEMENT_WIKI_LINK, 5000,1,0,newDriver).click();
		else
			waitForAndGetElement(ELEMENT_WIKI_LINK_PLF41, 5000,1,0,newDriver).click();
		waitForAndGetElement(By.linkText(title), 5000,1,0,newDriver).click();
		Utils.pause(1000);
		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK, 5000,1,2,newDriver).click();
		info("Show warning message");
		Utils.pause(1000);
		info(waitForAndGetElement(ELEMENT_DRAFT_ALERT, 5000,1,2,newDriver).getText());
		info(ELEMENT_DRAFT_STRING_ALERT.replace("${userName}",fullName));
		assert(waitForAndGetElement(ELEMENT_DRAFT_ALERT, 5000,1,2,newDriver).getText().contains(ELEMENT_DRAFT_STRING_ALERT.replace("${userName}",fullName)));
		
		/*
		- Back to browser 1 and click Save button
		 *Input Data: 
		 *Expected Outcome: 
		- Page is edited successfully and have no exception in console		*/ 
		goToWiki();
		Utils.pause(500);
		info("Draft");
		goToManageDraft();
		info("Open page");
		click(ELEMENT_DRAFT_TITLE.replace("${title}", titleUpdate));
		info("-- Saving Wiki Page... --");
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);

		//Delete data test
		goToWikiPage("Wiki Home/"+titleUpdate);
		deleteCurrentWikiPage();
		info("--Close the 2nd browser window--");
		Utils.pause(500);
		newDriver.manage().deleteAllCookies();
		newDriver.quit();
	}
}
