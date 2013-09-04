package org.exoplatform.selenium.platform.forum;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.forum.ForumPermission;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Migrate from plf3.5
 * @author lientm
 * @date 19 Aug 2013
 */
public class ForumManageForum extends ForumBase {
	
	Button but;
	ManageAlert alert;
	ForumManageCategory cat;
	ForumPermission per;
	
	public ForumManageForum(WebDriver dr){
		driver = dr;
	}

	//--------------------forum home screen-------------------------------------------------
	public String ELEMENT_FORUM = "//*[@class='nameForum']/strong[text()='${forumName}']";
	public String ELEMENT_FORUM_LINK = "//span[@class='NameForum' and text()='${forumName}']";
	public By ELEMENT_DELETE_FORUM = By.xpath("//*[contains(@data-action, 'RemoveForum')]");
	public By ELEMENT_EDIT_FORUM = By.xpath("//*[contains(@href, 'EditForum')]");
	public By ELEMENT_MOVE_FORUM = By.xpath("//*[contains(@href, 'MoveForum')]");
	public By ELEMENT_START_TOPIC = By.xpath("//a[@class='ItemIcon PostnewThreadIcon' and contains(text(),'Start Topic')]");
	public By ELEMENT_CENSOR_TOPIC = By.xpath("//a[@class='ItemIcon SetUnWaiting' and text()='Censor ']");
	public By ELEMENT_MODERATOR_PANEL = By.xpath("//*[@id='uicomponent.id' and @class='UIForumModerator']");
	public By ELEMENT_RULE_PANEL = By.id("UIPostRules");
	
	//-------------------add forum form---------------------------------------------------
	public By ELEMENT_POPUP_ADD_FORUM = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Forum']");
	public By ELEMENT_SELECT_CATERORY = By.id("Category");
	public By ELEMENT_ADD_FORUM_TAB = By.xpath("//div[@class='MiddleTab' and text()='Add Forum']");
	public By ELEMENT_FORUM_TITLE = By.id("ForumTitle");
	public By ELEMENT_FORUM_ORDER = By.id("ForumOrder");
	public By ELEMENT_FORUM_STATE = By.id("ForumState");
	public By ELEMENT_FORUM_STATUS = By.id("ForumStatus");
	public By ELEMENT_FORUM_DESCRIPTION = By.id("Description");
	public By ELEMENT_MODERATOR_TAB = By.xpath("//div[@class='MiddleTab' and text()='Moderation Options']");
	public By ELEMENT_FORUM_MODERATOR = By.id("Moderator");
	public By ELEMENT_FORUM_AUTO_FILL = By.id("AutoAddEmailNotify");
	public By ELEMENT_NOTIFY_ADD_POST = By.id("NotifyWhenAddPost");
	public By ELEMENT_NOTIFY_ADD_TOPIC = By.id("NotifyWhenAddTopic");
	public By ELEMENT_MODERATE_THREAD = By.id("ModerateThread");
	
	//------------------move forum form---------------------------------------------------
	public By ELEMENT_POPUP_MOVE_FORUM = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Move Forum']");

	//------------------Export forum form---------------------------------------------------
	public By ELEMENT_EXPORT_FORUM_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Export Forums']");
	public By ELEMENT_EXPORT_FORUM_COMPRESS = By.id("createZip");
	
	/*-------------------------------------Common function--------------------------------*/
	
	/** function: go to add forum
	 * @author lientm
	 */
	public void goToAddForum(){
		info("---Go to add new forum---");
		click(ELEMENT_ADD_FORUM);
		waitForAndGetElement(ELEMENT_POPUP_ADD_FORUM);
	}

	/**function: input data in add forum tab in add forum popup
	 * @author lientm
	 * @param addForum: is array of 5 strings with
	 * 			@addForum[0] = title of forum
	 * 			@addForum[1] = order of forum
	 *          @addForum[2] = Open/Closed
	 *          @addForum[3] = Locked/Unlocked
	 *          @addForum[4] = description of forum
	 */
	public void inputDataInAddForumTab_addForum(String catName, String[] addForum){
		info("Input data in add forum tab");
		if (catName != null){
			select(ELEMENT_SELECT_CATERORY, catName);
		}	
		if (addForum.length > 0){
			if (addForum[0] != null){
				type(ELEMENT_FORUM_TITLE, addForum[0], true);
			}
			if (addForum[1] != null){
				type(ELEMENT_FORUM_ORDER, addForum[1], true);
			}
			if (addForum[2] != null){
				select(ELEMENT_FORUM_STATE, addForum[2]);
			}
			if (addForum[3] != null){
				select(ELEMENT_FORUM_STATUS, addForum[3]);
			}
			if (addForum[4] != null){
				type(ELEMENT_FORUM_DESCRIPTION, addForum[4], true);
			}
		}
	}

	/**
	 * function: input data in Moderations Option tab in add forum popup
	 * @param autofill
	 * @param postEmail
	 * @param topicEmail
	 * @param moderateTopic
	 */
	public void inputDataInModerOptionTab_addForum(boolean autofill, String postEmail, String topicEmail, boolean moderateTopic){
		info("Input data in Moderations Option tab");
		click(ELEMENT_MODERATOR_TAB);

		if (autofill){
			check(ELEMENT_FORUM_AUTO_FILL, 2);
		} else {
			uncheck(ELEMENT_FORUM_AUTO_FILL, 2);
			if (postEmail != null) {
				type(ELEMENT_NOTIFY_ADD_POST, postEmail, true);
			}
			if (topicEmail != null){
				type(ELEMENT_NOTIFY_ADD_TOPIC, topicEmail, true);
			}
		}
		if (moderateTopic){
			check(ELEMENT_MODERATE_THREAD, 2);
		}else {
			uncheck(ELEMENT_MODERATE_THREAD, 2);
		}
	}

	/**
	 * function input data when add/edit forum
	 * @param catName
	 * @param addForum
	 * @param autofill
	 * @param postEmail
	 * @param topicEmail
	 * @param moderateTopic
	 * @param type
	 * @param userGroup
	 * @param permission
	 */
	public void inputDataForum(String catName, String[] addForum, boolean autofill, String postEmail, String topicEmail, boolean moderateTopic,
			int type, String[] userGroup, boolean...permission){
		per = new ForumPermission(driver);
		inputDataInAddForumTab_addForum(catName, addForum);
		inputDataInModerOptionTab_addForum(autofill, postEmail, topicEmail, moderateTopic);
		if (type != 0){
			per.configPermission4Forum(type, userGroup, permission);
		}
	}
	
	/**
	 * function add new forum
	 * @param catName
	 * @param addForum
	 * @param autofill
	 * @param postEmail
	 * @param topicEmail
	 * @param moderateTopic
	 * @param type
	 * @param userGroup
	 * @param permission
	 */
	public void addForum(String catName, String[] addForum, boolean autofill, String postEmail, String topicEmail, boolean moderateTopic,
			int type, String[] userGroup, boolean...permission){
		but = new Button(driver);
		By FORUM = By.xpath(ELEMENT_FORUM.replace("${forumName}", addForum[0]));
		
		goToAddForum();
		info("Create new forum");
		inputDataForum(catName, addForum, autofill, postEmail, topicEmail, moderateTopic, type, userGroup, permission);
		but.save();
		boolean verify = permission.length > 4 ? permission[4]:true;
		if (verify){
			waitForAndGetElement(FORUM);
			info("Create forum successfully");
		}
	}
	
	public void quickAddForum(String forumName){
		String[] addForum = {forumName, null, null, null, null};
		but = new Button(driver);
		By FORUM = By.xpath(ELEMENT_FORUM.replace("${forumName}", addForum[0]));
		
		goToAddForum();
		info("Create new forum");
		inputDataInAddForumTab_addForum(null, addForum);
		but.save();
		waitForAndGetElement(FORUM);
		info("Create forum successfully");
	}
  	
	/** function: delete a forum
	 * @author lientm
	 * @param title: title of forum that needs to delete
	 */
	public void deleteForum(String title){
		click(ELEMENT_MORE_ACTION);
		info("Delete forum");
		click(ELEMENT_DELETE_FORUM);
		click(ELEMENT_OK_DELETE);
		waitForTextNotPresent(title);
		info("Delete forum successfully");
	}
	
	/** function: go to edit forum
	 * @author lientm
	 */
	public void goToEditForum(){
		info("Go to edit forum");
		click(ELEMENT_MORE_ACTION);
		click(ELEMENT_EDIT_FORUM);
		waitForAndGetElement(ELEMENT_POPUP_ADD_FORUM);
	}

	/**
	 * 
	 * @param addForum
	 * @param autofill
	 * @param postEmail
	 * @param topicEmail
	 * @param moderateTopic
	 * @param type
	 * @param userGroup
	 * @param permission
	 */
	public void editForum(String[] addForum, boolean autofill, String postEmail, String topicEmail, boolean moderateTopic,
			int type, String[] userGroup, boolean...permission){
		but = new Button(driver);
		By FORUM = By.xpath(ELEMENT_FORUM.replace("${forumName}", addForum[0]));
		
		goToEditForum();
		info("Edit forum");
		inputDataForum(null, addForum, autofill, postEmail, topicEmail, moderateTopic, type, userGroup, permission);
		but.save();	
		boolean verify = permission.length > 4 ? permission[4]:true;
		if (verify){
			waitForAndGetElement(FORUM);
		}
		info("Edit forum successfully");
	}

	/**function: move a forum from a category to another category
	 * @author lientm
	 * @param forum: title of forum that needs to move
	 * @param destination: title of destination category
	 */
	public void moveForum(String forum, String destination){
		info("move forum to category " + destination);
		click(ELEMENT_MORE_ACTION);
		waitForAndGetElement(ELEMENT_MOVE_FORUM);
		click(ELEMENT_MOVE_FORUM);
		waitForAndGetElement(ELEMENT_POPUP_MOVE_FORUM);
		click(By.linkText(destination));
		waitForElementNotPresent(ELEMENT_POPUP_MOVE_FORUM);
		waitForAndGetElement(By.xpath("//*[text()='" + destination + "']/../*[text()='" + forum + "']"));
		info("Move forum successfully");
	}
	
	/** function: export a forum
	 * @author lientm
	 * @param fileName: file name export
	 * @param compress: compress or not
	 */
	public void exportAForum(String fileName, boolean compress){
		cat = new ForumManageCategory(driver);
		but = new Button(driver);
		
		click(ELEMENT_MORE_ACTION);
		click(ELEMENT_EXPORT_FORUM);
		waitForAndGetElement(ELEMENT_EXPORT_FORUM_POPUP);
		info("Export forum");
		type(cat.ELEMENT_EXPORT_CATEGORY_FILE_NAME, fileName, true);
		if (compress){
			check(ELEMENT_EXPORT_FORUM_COMPRESS, 2);
		} else {
			uncheck(ELEMENT_EXPORT_FORUM_COMPRESS, 2);
		}
		but.save();
		waitForElementNotPresent(ELEMENT_EXPORT_FORUM_POPUP);
	}
}
