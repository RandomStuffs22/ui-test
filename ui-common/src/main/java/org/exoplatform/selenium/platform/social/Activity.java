package org.exoplatform.selenium.platform.social;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;

/**
 * 
 * @author thaopth
 * Date: 09/11/2012
 */

/**
 * Update Common functions for part: Activity (Space and People)
 * -- Delete an activity
 * -- Add/Delete a comment
 * -- Like/unlike an activity
 * -- Edit a title/description of a shared link
 * @author vuna2
 * Date: 12/11/2012
 */

public class Activity extends SocialBase {


	Dialog dialog = new Dialog(driver);
	HomePageActivity hpActivity = new HomePageActivity(driver);

	//=====Element on space home page=======stash@{1}
	// Go to My Spaces > Select a space
	//Or Go to My Activity Stream
	public final By ELEMENT_ACTIVITY_STREAM = By.id("UIUserActivitiesDisplay");
	public final By ELEMENT_SPACE_ACTIVITY_STREAM = By.id("UISpaceActivitiesDisplay");
	public final String ELEMENT_COMMENT_BOX_SPACE_ACTIVITY = "//div[@class='author']/a[contains(text(),'${activityText}')]//ancestor::div[contains(@id,'ContextBox')]/div[contains(@id,'CommentBlockBound')]//p[@class='contentComment']";
	public final String ELEMENT_AVATAR_SPACE_ACTIVITY = "//div[@class='author']/a[contains(text(),'${activityText}')]//ancestor::div[contains(@id,'ContextBox')]//*[@class='avatarXMedium']";
	public final String ELEMENT_DESCRIPTION_SPACE_ACTIVITY = "//div[@class='author']/a[contains(text(),'${activityText}')]//ancestor::div[contains(@id,'ContextBox')]//p[@class='spaceDescription' and contains(text(),'${description}')]"; 
	public final String ELEMENT_NUMBER_MEMBER_SPACE_ACTIVITY = "//div[@class='author']/a[contains(text(),'${activityText}')]//ancestor::div[contains(@id,'ContextBox')]//div[@class='spaceMembers' and contains(text(),'${numbermember}')]";
	public final String ELEMENT_ACTIVITY_AUTHOR_SPACENAME = "//div[@class='author']/a[contains(text(),'${activityText}')]";
	public final String ELEMENT_ACTIVITY_AUTHOR_ACTIVITY = "//*[contains(text(), '${activityText}')]/../../../../..//*[@class='author']";
	public final By ELEMENT_ACTIVITY_DROPDOWN = By.xpath("//div[@class='btn dropdown-toggle']");
	public final String ELEMENT_ACTIVITY_FILTER_OPTION = "//a[@class='OptionItem' and contains(text(),'${filterOption}')]";
	public final String ELEMENT_ACTIVITY_FILTER_CURRENT = "//div[@class='btn dropdown-toggle']/span[contains(text(),'${filterOption}')]";
	public final By ELEMENT_ACTIVITY_TEXTAREA = By.id("composerInput");
	public final By ELEMENT_ACTIVITY_MENTION_USER_MENU = By.xpath("//div[@id='DisplaycomposerInput']/../div[@class='autocomplete-menu']");
	public final String ELEMENT_ACTIVITY_MENTION_USER = ELEMENT_ACTIVITY_MENTION_USER_MENU+"//*[contains(text(),'${name}')]/../div[@class='avatarSmall']";
	public final By ELEMENT_ACTIVITY_WHAT_ARE_YOU_WORKING_LABEL = By.xpath("//div[@id='DisplaycomposerInput']/../div[@class='placeholder']");
	public final By ELEMENT_LINK = By.xpath("//i[@class='uiIconSocUILinkActivityComposer uiIconSocLightGray']");
	public final By ELEMENT_INPUT_LINK_BOX = By.id("InputLink");
	public final By ELEMENT_SELECT_FILE_BUTTON = By.linkText("Select File"); 
	public final By ELEMENT_SELECT_BUTTON = By.xpath("//button[text()='Select']");
	public final By ELEMENT_UPLOAD_BUTTON = By.xpath("//*[@data-original-title='Upload file.']");
	public final By ELEMENT_ATTACH_BUTTON = By.id("AttachButton");
	public final By ELEMENT_SHARE_BUTTON = By.id("ShareButton");
	public final By ELEMENT_NEXT_THUMBNAIL = By.cssSelector("div#NextThumbnail i.uiIconArrowRight.uiIconLightGray");
	public final By ELEMENT_THUMBNAIL = By.id("Thumbnails"); 
	public final By ELEMENT_THUMBNAIL_SHOW = By.cssSelector("div#Thumbnails img.thumbnailShown"); 
	public final By ELEMENT_THUMBNAIL_NEXT = By.xpath("//div[@id='Thumbnails']/img[2]"); 
	public final By ELEMENT_CROSS_BUTTON = By.xpath("//a[@class='uiIconClose uiIconLightGray']"); 
	public final By ELEMENT_SHARE_DISPLAY = By.xpath("//div[@class='uiLinkShareDisplay blastShare']");
	public final By ELEMENT_PICTURE_SHARE = By.xpath("//div[@id='UIThumbnailLeftBox']"); 
	public final By ELEMENT_TITLE_SHARE = By.xpath("//div[@id='UIRightBox']/h5[@id='LinkTitle']"); 
	public final By ELEMENT_URL_SHARE = By.xpath("//div[@id='LinkUrl']");
	public final By ELEMENT_NAME_SPACE_ACTIVITY = By.xpath("//a[@class='spaceName']");
	public final By ELEMENT_ICON_SPACE_ACTIVITY = By.xpath("//i[@class='uiIconSocGroup uiIconSocLightGray']");

	//	public final String ELEMENT_COMMENT_LINK = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//*[starts-with(@id, 'CommentLink')]";
	public final String ELEMENT_COMMENT = "//div[@class='ContentBox']//*[contains(text(), '${activityText}')]";
	public final String ELEMENT_SHOW_HIDE_COMMENTS = "/following::*[@class='CommentListInfo']/a[contains(text(), '${inforComment}')]";

	//	public final String ELEMENT_COMMENT_ICON = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//*[@class= 'uiIconComment uiIconLightGray']";
	public final String ELEMENT_COMMENT_BUTTON = "//*[contains(text(), '${activityText}')]/../../../..//button[contains(@id,'CommentButton')]";
	public final String ELEMENT_ACTIVITY_ADD_YOUR_COMMENTLABEL = "//*[contains(text(),'${activityText}')]/../../../..//*[contains(@id,'DisplayCommentTextarea')]/../div[@class='placeholder']";
	public final String ELEMENT_COMMENT_ICON = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//*[@class= 'uiIconComment uiIconLightGray']";
	//	public final String ELEMENT_COMMENT_BUTTON = "//*[contains(text(), '${activityText}')]/..//button[contains(@id,'CommentButton')]";
	public final String ELEMENT_INPUT_COMMENT_TEXT_AREA = "//*[contains(text(), '${activityText}')]/..//*[contains(@id,'DisplayCommentTextarea')]";
	//	public final String ELEMENT_ACTIVITY_ADD_YOUR_COMMENTLABEL = "//*[contains(text(), '${activityText}')]/..//*[contains(@id,'DisplayCommentTextarea')]/../div[@class='placeholder']";
	public final String ELEMENT_MENTION_USER_AVATAR = "//*[@data-display='${userName}']/*[@class='avatarSmall']";
	public final String ELEMENT_COMMENT_DATETIME = "//*[contains(text(), '${activityText}')]/../../../..//*[@class='dateTime'  and contains(text(),'${DATETIME}')]";
	//public final String ELEMENT_COMMENT_DATETIME = "//*[contains(text(),'${activityText}')]/../../../..//div[@class='commentList']/div[contains(@id,'commentContainer')]/../../../../"
	//		+ "	div[@class='author']/../span[contains(@class'dateTime')]/p[@class='contentComment'  and contains(text(),'${commentText}')]";
	public final String ELEMENT_COMMENT_AVATAR = "//*[contains(text(), '${activityText}')]/..//a[@class='avatarXSmall']/img";//"//*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//div[@class='commentList']//a[@class='avatarXSmall']";

	//	public final String ELEMENT_LIKE_ICON = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//*[starts-with(@id, 'LikeLink')]";
	//	public final String ELEMENT_UNLIKE_ICON = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//*[starts-with(@id, 'UnLikeLink')]";
	public final String ELEMENT_USER_MENTION_BY_OTHER_USER = "//div[@class='author']/a[contains(text(),'${author}')]/../../..//a[contains(text(),'${userName}')]";
	public final String ELEMENT_ACTIVITY_NAME_CONSECUTIVE = "//*[contains(@id,'UIActivitiesContainer')]/div[1]//*[@class='description' and text()='${activityText1}']/../../../../../../div[2]//*[@class='description' and text()='${activityText2}']";
	public final String ELEMENT_COMMENT_LINK_MENTION = "//*[@class='contentComment']/a[contains(text(),'${userName}')]";
	public final String ELEMENT_USER_NAME_LINK_ACTIVITY = "//div[@class='description']/a[contains(text(),'${userName}')]";
	public final String ELEMENT_USER_NAME_LINK_COMMENT = "//div[@class='description']/../*[contains(text(), '${activityText}')]/../../..//p[@class='contentComment']/a[contains(text(),'${userName}')]";
	public final By ELEMENT_MENTION_USER_BUTTON = By.id("mentionButton");
	public final String ELEMENT_DELETE_COMMENT_BUTTON = "//*[contains(text(),'${activityText}')]/../../../..//div[@class='commentList']/div[contains(@id,'commentContainer')]//p[@class='contentComment'  and contains(text(),'${commentText}')]/../../a[contains(@id,'DeleteCommentButton')]";
	public final String ELEMENT_COMMENT_LIST = "//*[contains(text(),'${activityText}')]/../../../..//div[@class='commentList']/div[contains(@id,'commentContainer')]";
	public final String ELEMENT_DELETE_COMMENT_BUTTON_INDEX = "//*[contains(text(), '${activityText}')]/..//div[@class='commentList']/div[contains(@id,'commentContainer')][${index}]//p[@class='contentComment'  and contains(text(),'${commentText}')]/../../a[contains(@id,'DeleteCommentButton')]";
	public final By ELEMENT_MESSAGE_CONFIRM_DELETE_COMMENT = By.xpath("//*[text()='Are you sure you want to delete this comment?']");
	public final String ELEMENT_SHOW_ALL_COMMENTS = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/../*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]/div[contains(@id,'CommentBlockBound')]//a[contains(text(),'View all "+"${inforComment}"+"')]";
	public final String ELEMENT_HIDE_ALL_COMMENTS = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/../*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]/div[contains(@id,'CommentBlockBound')]//a[contains(text(),'Hide all "+"${inforComment}"+"')]";
	public final String ELEMENT_AVATAR_LIST_LIKER_INDEX = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/../*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//div[@class='listPeopleContent']/div[@class='listLiked']/a[@class='avatarXSmall'][${index}]/img";
	public final String ELEMENT_YOU_LIKE_THIS_ACTIVITY = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/../*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//div[@class='listPeopleContent']/*[contains(text(),'You liked this')]";
	public final String ELEMENT_USER_NAME_LIKE_THIS_ACTIVITY = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/../*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//div[@class='listPeopleContent']/*[contains(text(),'${userName} liked this')]";
	public final String ELEMENT_USER_PROFILE_POPUP = "//table[@id='tipName']//a[contains(text(),'${userName}')]";
	public final String ELEMENT_USER_PROFILE_AVATAR = "//table[@id='tipName']//a[@target='parent']/img"; 

	//=====Element on welcome page=======
	public final By ELEMENT_HINT_BLOCK = By.className("hint");
	public final By ELEMENT_CLOSE_HINT_BUTTON = By.xpath("//i[@class='uiIconClose uiIconLightGray']");
	public final By ELEMENT_WELCOME_ACTIVITY = By.id("welcomeActivity");
	public final String ELEMENT_WELCOME_AVATAR = "//div[@class='activityAvatar avatarCircle']//img[@alt='${author}']";
	public final By ELEMENT_WELCOME_DESCRIPTION = By.cssSelector("div.description.clearfix");
	public final By ELEMENT_WELCOME_CONNECTIONS_LINK = By.linkText("Connections");
	public final By ELEMENT_WELCOME_DOCUMENTS_LINK = By.linkText("Documents");
	public final By ELEMENT_WELCOME_WIKIS_LINK = By.linkText("Wikis");
	public final By ELEMENT_WELCOME_FORUMS_LINK = By.linkText("Forums");
	public final By ELEMENT_WELCOME_CALENDAR_LINK = By.linkText("Calendars");
	public final By ELEMENT_WELCOME_SPACES_LINK = By.linkText("Spaces");
	public final By ELEMENT_WELCOME_APPSTORE_LINK = By.linkText("iOS App Store");
	public final By ELEMENT_WELCOME_GOOGLE_LINK = By.linkText("Google Play"); 

	/*public Activity(WebDriver dr){
		driver = dr;
	}*/
	/**
	 * Select filter activity
	 * @author phuongdt
	 * @date 03/10/2013
	 * @param filterOption: All Activities, My Activities, My Spaces, Connections
	 */
	public void selectFileter(String filterOption){
		info("-- Select filter activity --");
		//if(waitForAndGetElement(ELEMENT_ACTIVITY_FILTER_CURRENT.replace("${filterOption}", filterOption), DEFAULT_TIMEOUT, 0)==null){
		if(isElementPresent(ELEMENT_ACTIVITY_DROPDOWN)){
			if(isElementNotPresent(ELEMENT_ACTIVITY_FILTER_CURRENT.replace("${filterOption}", filterOption))){
				click(ELEMENT_ACTIVITY_DROPDOWN,2);
				click(ELEMENT_ACTIVITY_FILTER_OPTION.replace("${filterOption}", filterOption));
				waitForAndGetElement(ELEMENT_ACTIVITY_FILTER_CURRENT.replace("${filterOption}", filterOption));

			}
		}
	}

	/** Choose folder path in select file/folder function
	 * @author phuongdt
	 * @param driveName
	 * @param folderPath
	 */
	public void goToFolderPath(String driveName, String folderPath,Object...params ){
		Boolean verify = (Boolean) (params.length > 0 ? params[0] : false);
		info("-- Selecting a file to post on activity --");
		waitForAndGetElement(ELEMENT_FILE_LINK);
		click(ELEMENT_FILE_LINK);
		waitForAndGetElement(ELEMENT_SELECT_FILE_POPUP);	
		info("----Select drive----");
		if(waitForAndGetElement(ELEMENT_DRIVER_CURRENT.replace("${driveName}", driveName), DEFAULT_TIMEOUT, 0)==null){
			click(ELEMENT_DRIVER_BOX,2);
			click(ELEMENT_DRIVER_OPTION.replace("${driveName}", driveName));
		}
		info("---Select folder path----");
		String [] paths = folderPath.split("/");
		String lastFolder = paths[paths.length-1];
		String subFolderPath;
		if(paths.length>3)
			subFolderPath = paths[paths.length-2]+"/"+paths[paths.length-1];
		else
			subFolderPath = folderPath.substring(folderPath.indexOf("/")+1);
		for (String path : paths){
			click(By.linkText(path));

		}
		if(verify){
			waitForAndGetElement("//*[@drivename='"+paths[0]+"' and @currentfolder='"+ subFolderPath + "'and text()= '"+lastFolder+"']");
		}
	}
	/**
	 * Select a file to post on activity
	 * @param driveName
	 * @param upload
	 * @param folderPath
	 * @param selectFileName
	 * @param uploadFileLink
	 * @param uploadFileName
	 * @param option: newFolder
	 */
	public void selectFile(String driveName, boolean upload, String folderPath, String selectFileName, String uploadFileName, Object...params) {
		String newFolder = (String) (params.length > 0 ? params[0] : "");
		Boolean shareActivity = (Boolean)(params.length > 1 ? params[1] : true);
		alert = new ManageAlert(driver);
		info("-- Selecting a file to post on activity --");
		for(int repeat=0;; repeat ++){
			if (repeat > 3){
				waitForAndGetElement(ELEMENT_SELECT_FILE_POPUP);
				break;
			}
			click(ELEMENT_FILE_LINK);
			if(waitForAndGetElement(ELEMENT_SELECT_FILE_POPUP,5000,0)!=null)
				break;
			info("Retry...[" + repeat + "]");
		}
		info("----Select drive----");
		if(waitForAndGetElement(ELEMENT_DRIVER_CURRENT.replace("${driveName}", driveName), DEFAULT_TIMEOUT, 0)==null){
			click(ELEMENT_DRIVER_BOX,2);
			click(ELEMENT_DRIVER_OPTION.replace("${driveName}", driveName));
		}
		info("---Select folder path----");
		String [] paths = folderPath.split("/");
		for (String path : paths)
			click(By.linkText(path));
		if(newFolder!=""){
			if(plfVersion.equalsIgnoreCase("4.0")){
				click(ELEMENT_CREATE_FOLDER_BUTTON);
				alert.inputAlertText(newFolder);
				click(By.linkText(newFolder));
			}
			if(plfVersion.equalsIgnoreCase("4.1")){
				click(ELEMENT_CREATE_FOLDER_BUTTON_PLF41);
				alert.inputAlertText(newFolder);
				click(By.linkText(newFolder));
			}
		}

		if (upload && uploadFileName!="")
		{
			info("-- Upload file --");
			WebElement frame = waitForAndGetElement(ELEMENT_UPLOAD_FILE_FRAME_XPATH);
			driver.switchTo().frame(frame);
			WebElement upload2 = waitForAndGetElement(ELEMENT_UPLOAD_IMG_ID, DEFAULT_TIMEOUT,1,2);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload2);
			upload2.sendKeys(Utils.getAbsoluteFilePath("TestData/" +uploadFileName));	
			info("Upload file " + Utils.getAbsoluteFilePath("TestData/" +uploadFileName));
			switchToParentWindow();
			waitForAndGetElement(By.linkText(uploadFileName));
			click(By.linkText(uploadFileName));
			Utils.pause(500);
		}
		else 
		{
			if(selectFileName!=""){
				click(By.linkText(selectFileName));
				Utils.pause(500);
			}
		}
		if(shareActivity){
			click(ELEMENT_SELECT_BUTTON);
			Utils.pause(1000);
			if(upload && uploadFileName!="")
				if (plfVersion.equalsIgnoreCase("4.0")) assert waitForAndGetElement(ELEMENT_FILE_INPUT_DOC).getText().contains(uploadFileName);
			if(plfVersion.equalsIgnoreCase("4.1")) waitForAndGetElement(ELEMENT_FILE_INPUT_DOC);
			else{
				if(selectFileName!=""){
					assert waitForAndGetElement(ELEMENT_FILE_INPUT_DOC).getText().contains(selectFileName);
				}
			}
			click(ELEMENT_SHARE_BUTTON);
			if(upload)
				waitForAndGetElement(By.linkText(uploadFileName));
			else
				waitForAndGetElement(By.linkText(selectFileName));
		}
	}

	/**
	 * Add new activity for space 
	 * @param addText: boolean
	 * @param text: input a text (String)
	 * @param addLink: boolean
	 * @param link: input a link (String)
	 */
	public void addActivity (boolean addText, String text, boolean addLink, String link) {
		hpActivity = new HomePageActivity(driver);
		info("-- Adding an activity to space --");
		//waitForAndGetElement(ELEMENT_ACTIVITY_TEXTBOX, DEFAULT_TIMEOUT,1, 2);
		Utils.pause(3000);
		if (addText) 
		{
			info("----Add text into activity text box-----");
			WebElement inputText = waitForAndGetElement(hpActivity.ELEMENT_ACTIVITY_TEXTBOX);
			WebElement shareButton = waitForAndGetElement(ELEMENT_SHARE_BUTTON);
			WebElement workingLabel = waitForAndGetElement(ELEMENT_ACTIVITY_WHAT_ARE_YOU_WORKING_LABEL);
			((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '';", workingLabel);
			((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '"+text+"';", inputText);
			((JavascriptExecutor)driver).executeScript("arguments[0].disabled = false;", shareButton);
			((JavascriptExecutor)driver).executeScript("arguments[0].className = 'pull-right btn btn-primary';", shareButton);
		}
		if (addLink)
		{
			info("----Click on Link----");
			waitForAndGetElement(ELEMENT_LINK).click();
			info("----Click on Link----");
			waitForAndGetElement(ELEMENT_LINK).click();
			info("----Input link into link box-----");
	
			waitForAndGetElement(ELEMENT_INPUT_LINK_BOX);
			type(ELEMENT_INPUT_LINK_BOX, link, true);
			waitForAndGetElement(ELEMENT_ATTACH_BUTTON);
			info("----Click attach button-----");
			click(ELEMENT_ATTACH_BUTTON);
			waitForAndGetElement(By.id("LinkTitle"));
		}
		waitForAndGetElement(ELEMENT_SHARE_BUTTON);
		info("----Click share button----");
		click(ELEMENT_SHARE_BUTTON);
		Utils.pause(1000);
		info("-- Verify that an activity has been added --");
		if (addText) {
			waitForAndGetElement(By.xpath(ELEMENT_ACTIVITY_AUTHOR_ACTIVITY.replace("${activityText}", text)));
		}
		if (addLink){
			waitForAndGetElement(By.linkText(link));
		}
	}

	//	/**
	//	 * Delete activity 
	//	 * @param activityText: input a String 
	//	 */
	//	public void deleteActivity (String activityText) {
	//		info("-- Deleting an activity --");
	//
	//		By ELEMENT_DELETE_ACTIVITY = By.xpath("//div[@class='ContentBox']/div/h5/span/../../div[contains(text(), '"+activityText+"')]/../h5/span[contains(@id, 'DeleteActivityButton')]");
	//
	//		WebElement elementDeleteActivityID = waitForAndGetElement(ELEMENT_DELETE_ACTIVITY);
	//
	//		String deleteActivityIconID = elementDeleteActivityID.getAttribute("id");		
	//
	//		info("---Click delete activity icon---");
	//
	//		click(By.id(deleteActivityIconID));
	//
	//		magAlert.waitForConfirmation("Are you sure to delete this activity?");
	//
	//		waitForElementNotPresent(ELEMENT_DELETE_ACTIVITY);
	//
	//		Utils.pause(1000);
	//	}

	/**
	 * Add a new comment on activity stream
	 * @param activityText: input a text (String) 
	 * @param contentOfComment: input a comment (String)
	 */
	public void addComment(String activityText, String contentOfComment){
		//Add a new comment following an activity 
		hpActivity = new HomePageActivity(driver);

		click(hpActivity.ELEMENT_ICON_COMMENT.replace("${title}", activityText));

		WebElement commentText = waitForAndGetElement(hpActivity.ELEMENT_COMMENTBOX.replace("${title}", activityText));
		WebElement commentButton = waitForAndGetElement(ELEMENT_COMMENT_BUTTON.replace("${activityText}", activityText));
		WebElement workingLabel = waitForAndGetElement(ELEMENT_ACTIVITY_ADD_YOUR_COMMENTLABEL.replace("${activityText}", activityText));

		((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '';", workingLabel);
		((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '"+contentOfComment+"';", commentText);
		((JavascriptExecutor)driver).executeScript("arguments[0].disabled = false;", commentButton);
		((JavascriptExecutor)driver).executeScript("arguments[0].className = 'btn pull-right btn-primary';", commentButton);
		click(ELEMENT_COMMENT_BUTTON.replace("${activityText}", activityText));
		waitForAndGetElement(ELEMENT_DELETE_COMMENT_BUTTON.replace("${activityText}", activityText).replace("${commentText}", contentOfComment), DEFAULT_TIMEOUT,1,2);

	}


	/**
	 * Delete a comment
	 * @param contentOfComment: input a comment (String)
	 */
	public void deleteComment(String activityText, String commentText){
		info("-- Deleting a comment --");
		button = new Button(driver);
		WebElement deleteButton = waitForAndGetElement(ELEMENT_DELETE_COMMENT_BUTTON.replace("${activityText}", activityText).replace("${commentText}", commentText), DEFAULT_TIMEOUT,1,2);
		String deleteCommentIconID = deleteButton.getAttribute("id");
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("document.getElementById('"+deleteCommentIconID+"').click();");
		waitForAndGetElement(ELEMENT_MESSAGE_CONFIRM_DELETE_COMMENT);
		button.ok();
		waitForElementNotPresent(ELEMENT_DELETE_COMMENT_BUTTON.replace("${activityText}", activityText).replace("${commentText}", commentText));
		Utils.pause(1000);
	}

	/**
	 * Edit a title/description of a shared link
	 * Characters < 50 
	 * 
	 * @param inputLink: String
	 * @param editTitle: boolean
	 * @param titleOfSharedLink: String
	 * @param editDescription: boolean
	 * @param descriptionOfSharedLink: String
	 */
	public void editSharedLink(String inputText, String inputLink, boolean editTitle, String titleOfSharedLink, boolean editDescription, String descriptionOfSharedLink){
		Actions actions = new Actions(driver);
		hpActivity = new HomePageActivity(driver);
		info("-- Editing a shared link --");

		By ELEMENT_INPUT_EDIT_TITLE_LINK = By.xpath("//*[@id='UIActivityComposerContainer_LINK_ACTIVITY_']/div/div/input[@class='InputTitle']");

		By ELEMENT_INPUT_EDIT_DESCRIPTION_LINK = By.xpath("//*[@id='UIActivityComposerContainer_LINK_ACTIVITY_']/div/div/textarea[@class='InputDescription']");

		type(hpActivity.ELEMENT_ACTIVITY_TEXTBOX, inputText, true);

		click(ELEMENT_LINK);

		type(ELEMENT_INPUT_LINK_BOX, inputLink, true);

		click(ELEMENT_ATTACH_BUTTON);

		if (editTitle){			
			click(By.id("LinkTitle"));	

			type(ELEMENT_INPUT_EDIT_TITLE_LINK, titleOfSharedLink, true);

			click(By.id("UIComposer"));

			actions.sendKeys(Keys.ENTER);

			waitForElementNotPresent(ELEMENT_INPUT_EDIT_TITLE_LINK);
		}
		if (editDescription){
			doubleClickOnElement(By.id("LinkDescription"));

			type(ELEMENT_INPUT_EDIT_DESCRIPTION_LINK, descriptionOfSharedLink, true);

			click(By.id("UIComposer"));

			actions.sendKeys(Keys.ENTER);	

			waitForElementNotPresent(ELEMENT_INPUT_EDIT_DESCRIPTION_LINK);
		}
		//Verify that content has been changed
		if (editTitle && editDescription){
			click(ELEMENT_SHARE_BUTTON);

			waitForTextPresent(titleOfSharedLink);

			waitForTextPresent(descriptionOfSharedLink);
		}else if (editTitle && !editDescription){
			click(ELEMENT_SHARE_BUTTON);

			waitForTextPresent(titleOfSharedLink);
		}else if (editDescription && !editTitle){
			click(ELEMENT_SHARE_BUTTON);

			waitForTextPresent(descriptionOfSharedLink);
		}
	}
	///////
	/**
	 * Add plural comments to an activity
	 * @param activityText
	 * @param contentOfCommentList
	 */
	public void addSomeComments(String activityText, String[] contentOfCommentList){
		for(String content : contentOfCommentList){
			addComment(activityText, content);
			click(ELEMENT_COMMENT.replace("${activityText}", activityText) + "/../div[@class='DefaultActivityIcon']/a[1]");
		}
		Utils.pause(1000);
	}

	/**
	 * Show/Hide all comments
	 * @param activityText: String
	 * @param showComment: boolean
	 * @param hideComment: boolean
	 * @param inforComment: String
	 */
	public void showHideComments(String activityText, boolean showComment, boolean hideComment, String inforComment){
		info("-- Show/Hide a comment --");
		if (showComment){
			WebElement elem = waitForAndGetElement(ELEMENT_SHOW_ALL_COMMENTS.replace("${activityText}", activityText).replace("${inforComment}", inforComment));
			elem.click();
			waitForAndGetElement(ELEMENT_HIDE_ALL_COMMENTS.replace("${activityText}", activityText).replace("${inforComment}", inforComment));
		}else if(hideComment){
			WebElement elem = waitForAndGetElement(ELEMENT_HIDE_ALL_COMMENTS.replace("${activityText}", activityText).replace("${inforComment}", inforComment));
			elem.click();
			waitForAndGetElement(ELEMENT_SHOW_ALL_COMMENTS.replace("${activityText}", activityText).replace("${inforComment}", inforComment));
		}
		Utils.pause(1000);
	}

	/**
	 * @author phuongdt
	 * check visibility of activity on page using scroll bar
	 */
	public void waitForActivityPresent(String activityText, boolean isCurrentPosition){
		info("-- Verify Activity "+activityText+"--");
		if(!isCurrentPosition){
			Locatable hoverItem;
			if(this.plfVersion.equals("4.0"))
				hoverItem = (Locatable) driver.findElement(By.xpath("//div[@class='TRContainer ClearFix']"));
			else
				hoverItem = (Locatable) driver.findElement(By.xpath("//div[@class='TRContainer clearfix']"));
			int y = hoverItem.getCoordinates().onPage().getY();
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,"+y+");");
		}
		for (int second = 0;; second++) {
			if(second >8){
				waitForAndGetElement(By.xpath("//*[contains(text(),'"+activityText+"')]"));
				break;
			}
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("window.scrollBy(0,1000)", "");
			if(waitForAndGetElement(By.xpath("//*[contains(text(),'"+activityText+"')]"), DEFAULT_TIMEOUT,0)!=null)
				break;
		}
	}

	/**
	 * @author phuongdt
	 * check invisibility of activity on page using scroll bar
	 */
	public void waitForActivityNotPresent(String activityText, boolean isCurrentPosition){
		info("-- Verify Activity "+activityText+"--");
		if(!isCurrentPosition){
			Locatable hoverItem;
			if(this.plfVersion.equals("4.0"))
				hoverItem = (Locatable) driver.findElement(By.xpath("//div[@class='TRContainer ClearFix']"));
			else
				hoverItem = (Locatable) driver.findElement(By.xpath("//div[@class='TRContainer clearfix']"));
			int y = hoverItem.getCoordinates().onPage().getY();
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,"+y+");");
		}
		for (int second = 0;; second++) {
			if(second >=8){
				break;
			}
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("window.scrollBy(0,1000)", "");
			waitForElementNotPresent(By.xpath("//*[contains(text(),'"+activityText+"')]"));
		}
	}

	/**
	 * @author phuongdt
	 * mention a user in activity or comment of a activity
	 */
	public void mentionActivity(boolean isActivity, String activityText, String userName){
		hpActivity = new HomePageActivity(driver);
		if(isActivity){
			info ("-- Adding a mention activity --");	
			WebElement inputText = waitForAndGetElement(hpActivity.ELEMENT_ACTIVITY_TEXTBOX, DEFAULT_TIMEOUT, 1, 2);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible'", inputText);
			Utils.pause(1000);
			click(ELEMENT_MENTION_USER_BUTTON);
			inputText.sendKeys(userName);
			click(ELEMENT_MENTION_USER_AVATAR.replace("${userName}", userName));
			Utils.pause(1000);
			click(hpActivity.ELEMENT_ACTIVITY_TEXTBOX);
			Utils.pause(1000);			
			click(ELEMENT_SHARE_BUTTON);
			waitForAndGetElement(By.xpath(ELEMENT_USER_NAME_LINK_ACTIVITY.replace("${userName}", userName)));
		}
		else{
			info("-- Adding a mention comment --");
			//Add a new comment following an activity 
			click(hpActivity.ELEMENT_ICON_COMMENT.replace("${title}", activityText));
			if(this.plfVersion.equalsIgnoreCase("4.0")){
				type(hpActivity.ELEMENT_COMMENTBOX.replace("${title}", activityText), "@"+userName, false);
			}
			else if(this.plfVersion.equalsIgnoreCase("4.1")){
				type(hpActivity.ELEMENT_COMMENTBOX_PLF4_1.replace("${title}", activityText), "@"+userName, false);
			}
			Utils.pause(1000);
			click(hpActivity.ELEMENT_COMMENTBOX.replace("${title}", activityText));
			Utils.pause(1000);
			//click("//*[@class='avatarSmall' and text()='"+userName+"']");
			click(ELEMENT_MENTION_USER_AVATAR.replace("${userName}", userName));
			Utils.pause(1000);
			//Click on Comment button
			WebElement commentButton = waitForAndGetElement(ELEMENT_COMMENT_BUTTON.replace("${activityText}", activityText));
			((JavascriptExecutor)driver).executeScript("arguments[0].disabled = false;", commentButton);
			((JavascriptExecutor)driver).executeScript("arguments[0].className = 'pull-right btn btn-primary';", commentButton);
			commentButton.click();
			waitForAndGetElement(ELEMENT_USER_NAME_LINK_COMMENT.replace("${activityText}", activityText).replace("${userName}", userName));
		}
	}
}
