package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Provides all methods of adding and editing pages. 
 * 
 * 
 */
public class BasicAction extends Permission{

	//Dialog dialog = new Dialog(driver);
	//Button button = new Button(driver);
	//ManageAlert magAlert;

	Dialog dialog = new Dialog(driver);
	Button button = new Button(driver);
	ManageAlert magAlert = new ManageAlert(driver);
	ManageMember mMember = new ManageMember(driver, this.plfVersion);

	public final String ELEMENT_RESTRICTED_WIKI = "//*[@id='UIWikiPageInfoArea']//a[@data-original-title='This page is restricted. Click to share.']";
	public final String ELEMENT_MAKE_PUBLIC_BUTTON = "//*[@id='UIWikiPermalinkForm']//button[contains(text(),'Make Public')]";
	public final String ELEMENT_PERMISSION_WINDOW_CLOSE_BUTTON = "//*[@id='UIWikiPopupWindowL1']//a[@title='Close Window']";
	public final By ELEMENT_MEMBERS_TAB = By.xpath("//div[@id='UISpaceSetting']//a[text()='Members']");
	public String ELEMENT_ACTION_USER_ON_SPACE = "//a[text()='${spaceName}']/../../..//button[text()='${action}']";
	
	// Wiki page
	/*===================== Add Page ====================*/	

	/** 
	 * Add a blank wiki page in Intranet Wiki or Space Wiki
	 * 
	 * 
	 * @param title 
	 * 				title of the wiki page that need creating. Can not be <code>null</code>
	 * @param content
	 * 				content of the wiki page that need creating. Can not be <code>null</code>
	 * @param mode
	 * 				options to choose whether to create new wiki page with RichText editor or Source editor
	 * 					mode =1: edit a wiki page in richtext
	 * 	 	  			mode =0 : edit a wiki page in source editor
	 * @param option
	 * 				options to cancel or save wiki page. Can be <code>null</code>
	 * 
	 */
	public void addBlankWikiPage(String title, String content, int mode, Object... option){
		info("-- Adding a new wiki page... --");
		//boolean  verify = option.length > 0 ? option[0] : false;
		//boolean ca = (Boolean) (option.length > 0 ? option[0] : false);
		String message = (String) (option.length > 1 ? option[1] : "");	
		goToAddBlankPage();
		Utils.pause(500);
		driver.navigate().refresh();
		Utils.pause(2000);

		info("-- Add a wiki page from blank page --");
		if (mode == 1){ 
			addWikiPageRichText(title, content);
		}
		else{
			addWikiPageSourceEditor(title, content);
		}
		switchToParentWindow();
		Utils.pause(500);
		boolean ca = (Boolean) (option.length > 0 ? option[0] : false);
		if (ca){
			info("-- Cancel Wiki Page --");
			click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
			waitForWikiConfirmation(MESSAGE_CANCEL_CREATE_PAGE);
			//waitForWikiConfirmation("Are you sure to leave this page?");
			waitForElementNotPresent(ELEMENT_TITLE_WIKI_INPUT);
		}else{
			info("-- Saving Wiki Page... --");
			click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
			//waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		}

		if (!message.isEmpty()){ 
			info(message);
			//waitForMessage(message);
			if (waitForAndGetElement(button.ELEMENT_OK_BUTTON, 3000, 0, 2) != null){
				click(button.ELEMENT_OK_BUTTON);
				click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
			}else {
				click(ELEMENT_CONFIRM_BUTTON_ADD_PAGE);
			}
		}

		/*if (verify){
			waitForTextPresent(content);
		}*/
		Utils.pause(1000);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
	}

	/**
	 * Modify Wiki content with Source editor
	 * 
	 * 
	 * @param title
	 * 			updated title of the wiki page. Can not be <code>null</code>
	 * @param content
	 * 			updated content of the wiki page. Can not be <code>null</code>
	 * 
	 */
	public void addWikiPageSourceEditor(String title, String content){
		info("Modify data with source editor");
		if(title != null){
			type(ELEMENT_TITLE_WIKI_INPUT, title, true);
		}	
		if(isElementPresent(ELEMENT_SOURCE_EDITOR_BUTTON)){
			click(ELEMENT_SOURCE_EDITOR_BUTTON);
			waitForAndGetElement(ELEMENT_RICHTEXT_BUTTON);
		}
		Utils.pause(1000);
		if(content != null){
			type(ELEMENT_CONTENT_WIKI_INPUT,content,true);
		}	
		//waitForAndGetElement(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		Utils.pause(1000);
	}

	/**
	 * Modify Wiki content with RichText editor
	 * 
	 * 
	 * @param title
	 * 			updated title of the wiki page. Can not be <code>null</code>
	 * @param content
	 * 			updated content of the wiki page. Can not be <code>null</code>
	 * 
	 */
	public void addWikiPageRichText(String title, String content){

		if(this.plfVersion=="4.0"){
			if(title != null)
				type(ELEMENT_TITLE_WIKI_INPUT, title, true);
			if(waitForAndGetElement(ELEMENT_RICHTEXT_BUTTON,5000,0)!=null){
				click(ELEMENT_RICHTEXT_BUTTON);
				waitForAndGetElement(ELEMENT_SOURCE_EDITOR_BUTTON);
			}
			if (content != null){
				inputDataToFrame(ELEMENT_CONTENT_WIKI_FRAME, content,true);
				Utils.pause(1000);
				driver.switchTo().defaultContent();
			}
			//waitForAndGetElement(ELEMENT_SAVE_BUTTON_ADD_PAGE);
			Utils.pause(1000);
		}
		else if(this.plfVersion == "4.1"){
			if(waitForAndGetElement(ELEMENT_RICHTEXT_BUTTON_PL4_1,5000,0)!=null){
				click(ELEMENT_RICHTEXT_BUTTON_PL4_1);
				waitForAndGetElement(ELEMENT_SOURCE_EDITOR_BUTTON);
			}
			if(title != null)
				type(ELEMENT_TITLE_WIKI_INPUT, title, true);
			if (content != null){
				inputDataToFrame(ELEMENT_CONTENT_WIKI_FRAME, content,true);
				Utils.pause(1000);
				driver.switchTo().defaultContent();
			}
			//waitForAndGetElement(ELEMENT_SAVE_BUTTON_ADD_PAGE);
			Utils.pause(1000);
		}		

	}

	/**
	 * Add a blank Wiki page with Advance options
	 * 
	 * 
	 * @param totalPages 
	 * 				number of created Wiki page. Can not be <code>null</code>
	 * @param wikiParentPath 
	 * 				parent (path) of created Wiki page. Can not be <code>null</code> 
	 * @param titlePage
	 * 				Wiki page name. Can not be <code>null</code>
	 * @param contentPage 
	 * 				Wiki page content. Can not be <code>null</code>  
	 * @param mode
	 * 				options to choose whether wiki page will be edited in RichText mode or Source mode
	 * 					mode = 1 : edit a wiki page in richtext
	 *        			mode = 0 : edit a wiki page in source editor
	 *        
	 * @see WikiBase#goToWiki()
	 */
	public void addBlankWikiPageAdvanceForm(int totalPages, String[] wikiParentPath, String[] titlePage, String[] contentPage, int mode){
		goToWiki();	
		for (int i = 0; i < totalPages; i++){
			goToWikiPage(wikiParentPath[i]);
			addBlankWikiPage(titlePage[i], contentPage[i], mode);
		}
		Utils.pause(1000);
	}

	/** 
	 * Edit a wiki page
	 * 
	 * 
	 * @param title
	 * 				Wiki page name. Can not be <code>null</code>
	 * @param content
	 * 				Wiki page content. Can not be <code>null</code>  
	 * @param mode	
	 * 				Options to choose whether wiki page will be edited in RichText mode or Source mode. Can be <code>null</code>
	 * 					mode =0 : edit a wiki page in source editor
	 * 					mode =1: edit a wiki page in richtext
	 * 
	 */
	public void editWikiPage(String title, String content, int mode, Object... opParams)
	{
		Boolean isEditMultiLine = (Boolean)(opParams.length>0 ? opParams[0]:false);

		info("--Edit a wiki page--");
		Utils.pause(1000);
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		driver.navigate().refresh();
		Utils.pause(2000);
		if(mode == 0){
			if(isEditMultiLine)
				editWikiPageWithContentMultiLine(title, content);
			else
				addWikiPageSourceEditor(title, content);
		}		
		else{
			addWikiPageRichText(title, content);
		}
		//In PLF4, there is no more Minor Edit Option
		/*if (minorEdit) {
			click(ELEMENT_MINOR_EDIT_BUTTON);
			waitForElementNotPresent(ELEMENT_MINOR_EDIT_BUTTON);
		}*/		
		//save();
		switchToParentWindow();
		Utils.pause(500);
		//click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		mouseOverAndClick(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		Utils.pause(2000);
	}

	//===========Delete wiki page ===========//
	/** 
	 * Delete a wiki page
	 * 
	 * 
	 * @param cancel
	 * 			Option to choose if you want to cancel a delete action or not. Can be <code>null</code>
	 * 
	 */
	public void deleteCurrentWikiPage(boolean... cancel)
	{
		boolean ca = cancel.length > 0 ? cancel[0] : false;
		info("--Delete a wiki page--");
		goToDeletePage();
		if (ca){
			click(button.ELEMENT_CANCEL_BUTTON);
			waitForElementNotPresent(button.ELEMENT_CANCEL_BUTTON);
		}else{
			click(button.ELEMENT_OK_BUTTON);
			waitForElementNotPresent(button.ELEMENT_OK_BUTTON);
		}
		Utils.pause(1000);
	}

	/**
	 * Delete a path of Wiki nodes
	 * 
	 * 
	 * @param wikiPath 
	 * 			an element path indicates how to access wiki page (eg, "Wiki home/WikiTest"). Can not be <code>null</code>
	 * 
	 */
	public void deleteWikiPage(String[] wikiPath){
		String[] nodes = null;
		String pageName = "";
		Utils.pause(500);
		for(int i = 0; i < wikiPath.length; i++){
			nodes = wikiPath[i].split("/");
			pageName = nodes[nodes.length-1];
			goToWikiPage(wikiPath[i]);
			deleteCurrentWikiPage();
			waitForTextNotPresent(pageName);
		}
	}

	//=========== Preview wiki page  =========//
	/**
	 * Preview wiki page before saving
	 * 
	 * 
	 * @param title
	 * 			 Page title. Can not be <code>null</code> 
	 * @param content
	 * 			 Page content. Can not be <code>null</code>
	 * @param mode
	 * 			 Option to choose the editor mode. Can be <code>null</code>
	 *             - 1 for RichTextEditor
	 *             - 0 for textarea element
	 *             
	 */
	public void previewWikiPage(String title, String content,int mode){
		mouseOverAndClick(ELEMENT_ADD_PAGE_LINK);
		mouseOverAndClick(ELEMENT_BLANK_PAGE_LINK);
		type(ELEMENT_TITLE_WIKI_INPUT, title, true);
		if ( mode == 1 ){
			click(ELEMENT_RICHTEXT_BUTTON);
			waitForAndGetElement(ELEMENT_SOURCE_EDITOR_BUTTON);
			inputDataToFrame(ELEMENT_CONTENT_WIKI_FRAME, content,true);
			driver.switchTo().defaultContent();
		}
		else {
			type(ELEMENT_CONTENT_WIKI_INPUT,content,true);
		}
		click(ELEMENT_PREVIEW_BUTTON);
		waitForAndGetElement(ELEMENT_PREVIEW_SCREEN);
	}

	/**
	 * Verify a specific confirm message in Wiki 
	 * 
	 * 
	 * @param message
	 * 				message for confirmation. Can not be <code>null</code>
	 * @param isCancel 
	 * 				OK will be click by default, but if isCancel is set true, Cancel button will be click. Can be <code>null</code>
	 *
	 */
	public void waitForWikiConfirmation(String message, boolean...isCancel){
		//By btnOK = By.xpath("//input[@type='button' and @value='OK']");
		//By btnCancel = By.xpath("//input[@type='button' and @value='Cancel']");
		button = new Button(driver);
		By messageLocator = By.xpath("//div[@class='confirmMessage' and contains(text(), '" + message + "')]");
		waitForAndGetElement(messageLocator);
		if(isCancel.length > 0 && (isCancel[0] == true)) 
			//click(btnCancel);
			button.cancel();
		else
			//click(btnOK);
			click(button.ELEMENT_OK_BUTTON);
		waitForElementNotPresent(messageLocator);
	}

	/*================ Related page ============*/
	/**
	 * Add related page(s) to a Wiki page
	 * 
	 * 
	 * @param wikiPath 
	 * 			an element path indicates how to access wiki page (eg, "Wiki home/WikiTest"). Can not be <code>null</code>
	 * @param pageName 
	 * 			name of related page. Can not be <code>null</code>
	 * 
	 */
	public void addRelatedPage(String wikiPath, String pageName, Object...opts){
		String space = (String) (opts.length > 0 ? opts[0] : "");
		Boolean verify = (Boolean) (opts.length > 1 ? opts[1] : true);

		button = new Button(driver);
		//goToWikiPage(wikiPath);
		goToPageInfo(null, wikiPath);
		driver.navigate().refresh();
		Utils.pause(3000);
		//click(ELEMENT_ADD_MORE_RELATION_BUTTON);
		WebElement element = waitForAndGetElement(ELEMENT_ADD_MORE_RELATION_BUTTON);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		Utils.pause(1000);
		if (space != ""){
			click(ELEMENT_SELECT_SPACE);
			if (space == "Intranet"){
				click(ELEMENT_PORTAL_NAME_SELECTED);
			}else {
				click(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", space.toLowerCase()));
			}
		}
		click(By.xpath(ELEMENT_SELECTED_PAGE.replace("${relatedPage}", pageName)));
		Utils.pause(500);
		click(button.ELEMENT_SELECT_BUTTON);
		if (verify){
			waitForAndGetElement(ELEMENT_RELATED_PAGE.replace("${relatedPage}", pageName));
		}
		Utils.pause(500);
	}

	/**
	 * Remove related page(s) of a Wiki page
	 * 
	 * 
	 * @param delete 
	 * 				true - delete a page
	 * 				false - just click on Remove link
	 * @param direct
	 * 				user is currently stay in page info window or change to other window
	 * @param wikiPath 
	 * 				an element path indicates how to access wiki page (eg, "Wiki home/WikiTest")
	 * @param pageName
	 * 				name of related page. Can not be <code>null</code>.
	 * 
	 */
	public void removeRelatedPage(boolean delete, boolean direct, String wikiPath, String pageName){
		magAlert = new ManageAlert(driver);
		if (direct){
			click(By.xpath(ELEMENT_REMOVE_RELATED_PAGE_LINK.replace("${relatedPage}", pageName)));
		}else{
			goToWikiPage(wikiPath);
			goToPageInfo();
			click(By.xpath(ELEMENT_REMOVE_RELATED_PAGE_LINK.replace("${relatedPage}", pageName)));
		}
		if (delete){
			magAlert.acceptAlert();
			waitForElementNotPresent(ELEMENT_RELATED_PAGE.replace("${relatedPage}", pageName));
		}else{
			magAlert.cancelAlert();
			waitForAndGetElement(ELEMENT_RELATED_PAGE.replace("${relatedPage}", pageName));
		}
		Utils.pause(1000);
	}

	//////////
	//==== Common of common functions ====//
	/**
	 * Add blank Wiki page with Related page
	 * 
	 * 
	 * @param totalPages
	 * 				number of created page. Can not be <code>null</code>.
	 * @param wikiParentPath
	 * 				parent (path) of created page. Can not be <code>null</code>. 
	 * @param pageInfo
	 * 				pageInfo[0] = page name
	 * 				pageInfo[1] =  page content
	 * 				Can not be <code>null</code>.
	 * @param mode
	 * 				Option to choose if editor is RichText or Source
	 * 					mode = 1 : edit a wiki page in richtext
	 *        			mode = 0 : edit a wiki page in source editor
	 * @param wikiPath 
	 * 				an element path indicates how to access wiki page (eg, "Wiki home/WikiTest"). Can not be <code>null</code>.
	 * @param pageName
	 * 				name of related page. Can not be <code>null</code>.
	 * 
	 * @see #addBlankWikiPageAdvanceForm(int, String[], String[], String[], int)
	 * @see #addRelatedPage(String, String, Object...)
	 * 
	 */
	public void addBlankWikiPageAndRelatePage(int totalPages, String[] wikiParentPath, String[][] pageInfo, int mode, String wikiPath, String pageName){
		addBlankWikiPageAdvanceForm(totalPages, wikiParentPath, pageInfo[0], pageInfo[1], mode);
		addRelatedPage(wikiPath, pageName);		
	}

	/**
	 * Add a blank wiki page and then edit page permission
	 * 
	 * 
	 * @param totalPages
	 * 			number of created page. Can not be <code>null</code>.
	 * @param wikiParentPath
	 * 			parent (path) of created page. Can not be <code>null</code>. 
	 * @param pageInfo
	 * 				pageInfo[0] = page name
	 * 				pageInfo[1] =  page content
	 * 				Can not be <code>null</code>.
	 * @param mode
	 * 			Option to choose if editor is RichText or Source
	 * 					mode = 1 : edit a wiki page in richtext
	 *        			mode = 0 : edit a wiki page in source editor	 
	 * @param editInfo 
	 * 			viewPage/editPage/deletePermission. Can not be <code>null</code>.        
	 * @param user 
	 * 			users or groups that we want to change their permissions. Can not be <code>null</code>.
	 * 
	 * @see #addBlankWikiPage(String, String, int, Object...)
	 * 
	 */
	public void addBlankWikiPageAndEditPagePermissions(int totalPages, String[] wikiParentPath, String[][] pageInfo, int mode, 
			boolean[] editInfo, String user, Integer... type){
		int notDisplay = 0;
		if (type.length > 0){
			if (!(type[0] instanceof Integer)) {
				throw new IllegalArgumentException("-- Argument should be an Integer --");
			}
			notDisplay = (Integer)type[0];
		}
		addBlankWikiPageAdvanceForm(totalPages, wikiParentPath, pageInfo[0], pageInfo[1], mode);
		editPagePermission(user, editInfo[0], editInfo[1], editInfo[2], notDisplay);
	}

	/**
	 * Remove test data by deleting wiki page
	 * 
	 * 
	 * @param user
	 * 			type: Root, Admin, Author, Developer or Publisher. Can not be <code>null</code>.  
	 * @param wikiPath
	 * 			an element path indicates how to access wiki page (eg, "Wiki home/WikiTest"). Can not be <code>null</code>.  
	 * 
	 * @see #deleteWikiPage(String[])
	 * @see WikiBase#goToWiki()
	 * 
	 */
	public void resetDataByDeleteWikiPage(ManageAccount.userType user, String[] wikiPath){
		magAcc = new ManageAccount(driver,this.plfVersion);
		if (isElementNotPresent(ELEMENT_INPUT_USERNAME)){
			magAcc.signOut();
		}else{
			info("-- User.logIn: " + user);
		}
		magAcc.userSignIn(user);
		goToWiki();
		deleteWikiPage(wikiPath);
	}

	//////////
	/**
	 * Check if a user can view and edit wiki page 
	 * 
	 * 
	 * @param element_page
	 * 			Page that need to be checked.  Can not be <code>null</code>.
	 * @param content
	 * 			Old content of page.  Can not be <code>null</code>.
	 * @param new_content
	 * 			New content of page.  Can not be <code>null</code>.
	 * 
	 * @see #editWikiPage(String, String, int)
	 * @see WikiBase#goToWiki()
	 * 
	 */
	public void checkViewEditPage(By element_page, String content, String new_content){
		goToWiki();
		click(element_page);
		waitForTextPresent(content);
		editWikiPage(null, new_content, 0);
		waitForTextPresent(new_content);
		info("User can view and edit page");
	}

	/**
	 * Check permission default when just add user for wiki page then add Edit page permission
	 * 
	 * 
	 * @param user 
	 * 			User or group to set permission.  Can not be <code>null</code>.
	 * @param type 
	 * 
	 * @throws IllegalArgumentException 
	 * 				if value of variable "type" is not an integer
	 * 
	 * @see #editPagePermission(String, boolean, boolean, Object...)
	 * 
	 */
	public void checkAndEditPagePermission(String user, Integer... type){
		int notDisplay = 0;
		if (type.length > 0){
			if (!(type[0] instanceof Integer)) {
				throw new IllegalArgumentException("-- Argument should be an Integer --");
			}
			notDisplay = (Integer)type[0];
		}

		By EditPage = By.xpath(ELEMENT_EDIT_PAGE_PERMISSIONS.replace("${user}", user)) ;
		//By.xpath(ELEMENT_EDIT_PAGE_CHECK.replace("{$user}", user));
		By ViewPage = By.xpath(ELEMENT_VIEW_PAGE_PERMISSIONS.replace("${user}", user));
		//By.xpath(ELEMENT_VIEW_PAGE_CHECK.replace("{$user}", user));

		info("Check user permission default has view permission but does not have edit page permission");
		goToPagePermission();
		assert !waitForAndGetElement(EditPage, 5000, 1, notDisplay).isSelected();
		assert waitForAndGetElement(ViewPage, 5000, 1, notDisplay).isSelected();
		if (isElementPresent(button.ELEMENT_CLOSE_BUTTON)){
			button.close();
		}else if (isElementPresent(By.xpath("//*[contains(@class, 'popupTitle') and text()='Page Permissions']/..//*[contains(@class, 'uiIconClose')]"))){
			click(By.xpath("//*[contains(@class, 'popupTitle') and text()='Page Permissions']/..//*[contains(@class, 'uiIconClose')]"));
		}else {
			click(button.ELEMENT_CANCEL_BUTTON);
		}
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		info("Add edit page permission for " + user);
		editPagePermission(user, true, true, false, notDisplay);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
	}

	//Wiki page
	/*================= Paragraph =================*/
	/**
	 * Edit paragraph in a Wiki page
	 * 
	 * 
	 * @param paragraphTitle
	 * 				input paragraph title without space character.  Can not be <code>null</code>.
	 * @param paragraphContent
	 * 				input paragraph content with heading followed help tips.  Can not be <code>null</code>.
	 * 
	 */
	public void editParagraph (String paragraphTitle, String paragraphContent) {
		info("-- Editing a paragraph... " + paragraphTitle);
		String ELEMENT_PARAGRAPH_ID = "H"+paragraphTitle;

		mouseOver(By.id(ELEMENT_PARAGRAPH_ID), true);
		//click(By.xpath("//*[@title='Edit section: " + paragraphTitle + "']"), 2);

		WebElement element = waitForAndGetElement(By.xpath("//*[@data-original-title='Edit Section: " + paragraphTitle + "']"));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		Utils.pause(500);
		driver.navigate().refresh();
		Utils.pause(2000);
		type(ELEMENT_CONTENT_WIKI_INPUT, paragraphContent, true);
		switchToParentWindow();
		Utils.pause(500);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
	}

	/**
	 * Add blank wiki page with an attachment
	 * 
	 * 
	 * @param title
	 * 			Title of a wiki page.  Can not be <code>null</code>.
	 * @param content
	 * 			Content of a wiki page.  Can not be <code>null</code>.
	 * @param link
	 * 			Link of attachment which will be inserted into a Wiki page.  Can not be <code>null</code>.
	 * 
	 * @see #addWikiPageSourceEditor(String, String)
	 * 
	 */
	public void addBlankWikiPageHasAttachment(String title, String content, String link){
		goToAddBlankPage();
		info("Add new wiki page having attachment");
		String[] upload = link.split(";");
		addWikiPageSourceEditor(title, content);
		for (int i = 0; i < upload.length; i++){
			attachFileInWiki("TestData/" + upload[i], 2);
		}
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForAndGetElement(By.xpath(ELEMENT_ATTACHMENT_NUMBER.replace("${No}", "" + upload.length)));
	}

	/** Edit wiki page that check public activity
	 * 
	 * 
	 * @param title
	 * 			Title of a wiki page.  Can not be <code>null</code>.
	 * @param content
	 * 			Content of a wiki page.  Can not be <code>null</code>.
	 * @see #addWikiPageSourceEditor(String, String)
	 * 
	 */
	public void editPageWithCheckPublicActivity(String title, String content, String...comment){
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		addWikiPageSourceEditor(title, content);
		if (comment.length > 0){
			type(ELEMENT_COMMENT_TEXTBOX, comment[0], true);
		}
		check(ELEMENT_PUBLISH_ACTIVITY_CHECKBOX, 2);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
	}

	/**
	 * Add new blank wiki page at source editor mode with content including many line
	 * 
	 * 
	 * @param title
	 * 			Title of a wiki page.  Can not be <code>null</code>.
	 * @param content
	 * 			Content of a wiki page.  Can not be <code>null</code>.
	 * 
	 * @see #goToAddBlankPage()
	 * 
	 */
	public void addWikiPageWithContentMultiLine(String title, String content){
		goToAddBlankPage();
		info("Modify data with source editor");
		if(title != null)
			type(ELEMENT_TITLE_WIKI_INPUT, title, true);
		if(this.plfVersion.equalsIgnoreCase("4.1"))
			click(ELEMENT_SOURCE_EDITOR_BUTTON);
		if(content != null){
			String[] line = content.split("/");
			for (int i = 0; i < line.length; i ++){
				type(ELEMENT_CONTENT_WIKI_INPUT, line[i] , false);
				type(ELEMENT_CONTENT_WIKI_INPUT, Keys.ENTER.toString(), false);
			}
		}
		Utils.pause(1000);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
	}

	public void editWikiPageWithContentMultiLine(String title, String content){
		if(title != null)
			type(ELEMENT_TITLE_WIKI_INPUT, title, true);
		if(content != null){
			waitForAndGetElement(ELEMENT_CONTENT_WIKI_INPUT).clear();
			String[] line = content.split("/");
			for (int i = 0; i < line.length; i ++){
				type(ELEMENT_CONTENT_WIKI_INPUT, line[i] , false);
				type(ELEMENT_CONTENT_WIKI_INPUT, Keys.ENTER.toString(), false);
			}
		}
		Utils.pause(1000);
	}

	public void goToMySpacePage(){
		mMember = new ManageMember(driver, this.plfVersion);
		info("--Go to My Space Page--");
		mMember.goToAllSpaces();		
		waitForAndGetElement(ELEMENT_MY_SPACES_LINK);
		click(ELEMENT_MY_SPACES_LINK);
		Utils.pause(700);
	}

	public void goToMembers(String spaceName){
		goToMySpacePage();
		doAction("Edit", spaceName);
		goToMembers();
	}

	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li>
	 * Do a action on space such as Edit, delete, ...
	 * 
	 * @param action : Action name 
	 * @param spaceName : Space name
	 */
	public void doAction(String action, String spaceName){
		By actionLink = By.xpath(ELEMENT_ACTION_USER_ON_SPACE.replace("${spaceName}", spaceName).replace("${action}", action));
		waitForAndGetElement(actionLink, DEFAULT_TIMEOUT,1);
		click(actionLink);
		Utils.pause(1000);
	}

	// Go to Member tab
	public void goToMembers(){
		click(ELEMENT_MEMBERS_TAB);
		waitForAndGetElement(By.id("user"));
	}
}
