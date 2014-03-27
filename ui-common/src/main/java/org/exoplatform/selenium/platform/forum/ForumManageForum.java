package org.exoplatform.selenium.platform.forum;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumPermission;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Migrate from plf3.5
 * @author lientm
 * @date 19 Aug 2013
 */
public class ForumManageForum extends ForumBase {

	ForumManageCategory cat;
	ForumPermission per;
	ForumManageTopic topic;
	ManageAccount account;

	public ForumManageForum(WebDriver dr,String...plfVersion){
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
		driver = dr;
		per = new ForumPermission(driver,this.plfVersion);
		cat = new ForumManageCategory(driver,this.plfVersion);
		button = new Button(driver,this.plfVersion);
		alert = new ManageAlert(driver,this.plfVersion);
		account = new ManageAccount(driver,this.plfVersion);
	}

	//--------------------forum home screen-------------------------------------------------
	public String ELEMENT_FORUM = "//*[@class='nameForum']/strong[text()='${forumName}']";
	public String ELEMENT_FORUM_LINK = "//span[@class='NameForum' and text()='${forumName}']";
	public By ELEMENT_DELETE_FORUM = By.xpath("//*[contains(@data-action, 'RemoveForum')]");
	public By ELEMENT_DELETE_FORUM41 = By.xpath("//*[contains(@href, 'RemoveForum')]");
	public By ELEMENT_DELETE_FORUM411 = By.xpath("//i[@class='uiIconDelete']");
	public By ELEMENT_EDIT_FORUM = By.xpath("//*[contains(@href, 'EditForum')]");
	public By ELEMENT_MOVE_FORUM = By.xpath("//*[contains(@href, 'MoveForum')]");
	public By ELEMENT_START_TOPIC = By.xpath("//button[contains(@onclick,'AddTopic')][1]");
	public By ELEMENT_CENSOR_TOPIC =By.linkText("Censor");
	public By ELEMENT_MODERATOR_PANEL = By.xpath("//*[@id='uicomponent.id' and @class='UIForumModerator']");
	public By ELEMENT_RULE_PANEL = By.id("UIPostRules");
	public String ELEMENT_TOPIC_LINK = "//a[contains(.,'${topic}')]";
	public String ELEMENT_CATEGORY_FORUM_BREAD = "//*[text()='${category}']/../../*[text()='${forum}']";
	public By ELEMENT_LOCK_FORUM = By.className("uiIconLockMedium");
	public By ELEMENT_UNLOCK_FORUM = By.className("uiIconUnlockMedium");
	public String ELEMENT_CATEGORY_BREAD = "//a[@data-original-title='${category}']";
	public By ELEMENT_CLOSE_FORUM = By.xpath("//a[contains(@href,'SetCloseForum')]");
	public By ELEMENT_OPEN_FORUM = By.xpath("//a[contains(@href,'SetOpenForum')]");
	public By ELEMENT_NO_FORUM = By.xpath("//td[@class='noticeEmpty' and text()='No Forums']");
	public By ELEMENT_BAN_IP_FORUM = By.xpath("//a[contains(@href,'BanIpForumTools')]"); 
	public final By ELEMENT_MORE_ACTION = By.xpath("//form[@id='UITopicContainer']//*[@data-toggle='dropdown']/*[@class='uiIconSettings uiIconLightGray']");
	public String ELEMENT_FORUM_DESCRIPTION_TEXT = "//span[@class='description' and contains(text(),'${forum}')]"; 

	//-------------------add forum form---------------------------------------------------
	public By ELEMENT_POPUP_ADD_FORUM = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Forum']");
	public By ELEMENT_SELECT_CATERORY = By.name("Category");
	public By ELEMENT_ADD_FORUM_TAB = By.xpath("//div[@class='MiddleTab' and text()='Add Forum']");
	public By ELEMENT_FORUM_TITLE = By.id("ForumTitle");
	public By ELEMENT_FORUM_ORDER = By.id("ForumOrder");
	public By ELEMENT_FORUM_STATE = By.name("ForumState");
	public By ELEMENT_FORUM_STATUS = By.name("ForumStatus");
	public By ELEMENT_FORUM_DESCRIPTION = By.id("Description");
	public By ELEMENT_MODERATOR_TAB = By.linkText("Moderation Options");
	public By ELEMENT_FORUM_MODERATOR = By.id("Moderator");
	public By ELEMENT_FORUM_AUTO_FILL = By.name("AutoAddEmailNotify");
	public By ELEMENT_NOTIFY_ADD_POST = By.id("NotifyWhenAddPost");
	public By ELEMENT_NOTIFY_ADD_TOPIC = By.id("NotifyWhenAddTopic");
	public By ELEMENT_MODERATE_THREAD = By.id("ModerateThread");

	//------------------move forum form---------------------------------------------------
	public By ELEMENT_POPUP_MOVE_FORUM = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Move Forum']");

	//------------------Export forum form---------------------------------------------------
	public By ELEMENT_EXPORT_FORUM_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Export Forums']");
	public By ELEMENT_EXPORT_FORUM_COMPRESS = By.id("createZip");

	//------------------Censor topic-------------------------------------------------------
	public final String MESSAGE_CENSOR = "This post may contain offensive content. It will be displayed after moderation.";
	public final String CENSORED_TITLE = "${title} (Censored)";

	
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

		By FORUM = By.xpath(ELEMENT_FORUM.replace("${forumName}", addForum[0]));

		goToAddForum();
		info("Create new forum");
		inputDataForum(catName, addForum, autofill, postEmail, topicEmail, moderateTopic, type, userGroup, permission);

		button.save();
		boolean verify = permission.length > 4 ? permission[4]:true;
		if (verify){
			waitForAndGetElement(FORUM);
			info("Create forum successfully");
		}
	}

	public void quickAddForum(String forumName){
		String[] addForum = {forumName, null, null, null, null};
		button = new Button(driver);
		By FORUM = By.xpath(ELEMENT_FORUM.replace("${forumName}", addForum[0]));

		goToAddForum();

		info("Create new forum");
		inputDataInAddForumTab_addForum(null, addForum);
		button.save();
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
		if(plfVersion =="4.1"){
			if (isElementPresent(ELEMENT_DELETE_FORUM41)){
			click(ELEMENT_DELETE_FORUM41);
			alert.acceptAlert();
			}else{
			click(ELEMENT_DELETE_FORUM411);
			
			click(ELEMENT_OK_DELETE);
			}
		}
		else{// if (plfVersion =="4.0"){
			click(ELEMENT_DELETE_FORUM);
			click(ELEMENT_OK_DELETE);
		}		
		
		//click(ELEMENT_OK_DELETE);
		//		waitForTextNotPresent(title);
		
		waitForElementNotPresent(By.linkText(title));
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

		By FORUM = By.xpath(ELEMENT_FORUM.replace("${forumName}", addForum[0]));

		goToEditForum();
		info("Edit forum");
		inputDataForum(null, addForum, autofill, postEmail, topicEmail, moderateTopic, type, userGroup, permission);

		button.save();	
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
		waitForAndGetElement(By.xpath(ELEMENT_CATEGORY_FORUM_BREAD.replace("${category}", destination).replace("${forum}", forum)));
		info("Move forum successfully");
	}

	/** function: export a forum
	 * @author lientm
	 * @param fileName: file name export
	 * @param compress: compress or not
	 */
	public void exportAForum(String fileName, boolean compress){
		cat = new ForumManageCategory(driver);
		button = new Button(driver);

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
		button.save();
		waitForElementNotPresent(ELEMENT_EXPORT_FORUM_POPUP);
	}

	/**
	 * Add category, forum with simple data
	 * @param cate
	 * @param forum
	 */
	public void addCategoryForum(String cate, String forum){
		String[] permission = {};
		String[] addForum = {forum, "1",null,null,forum};
		cat.addNewCategoryInForum(cate, "1", 0,permission, cate, 0,permission);
		addForum(cate, addForum, true, "", "", false,0, permission);
	}
	/**
	 * Do actions on forum: lock, unlock, close, open a forum
	 * @param action = 1: lock
	 * 				 = 2: unlock
	 *     			 = 3: close
	 *     			 = 4: open
	 */
	public void actionOnForum(int action){
		topic = new ForumManageTopic(driver);

		click(ELEMENT_MORE_ACTION);
		switch (action) {
		case 1: 
			info("Lock a forum");
			click(ELEMENT_LOCK_FORUM);
			waitForAndGetElement(ELEMENT_START_TOPIC_DISABLE);
			break;
		case 2:
			info("Unlock a forum");
			click(ELEMENT_UNLOCK_FORUM);
			//Check after unlock forum
			waitForAndGetElement(topic.ELEMENT_START_TOPIC_BUTTON);
			break;
		case 3:
			info("Close a forum");
			click(ELEMENT_CLOSE_FORUM);
			waitForAndGetElement(ELEMENT_START_TOPIC_DISABLE);
			break;
		case 4: 
			info("Open a forum");
			click(ELEMENT_OPEN_FORUM);
			waitForAndGetElement(topic.ELEMENT_START_TOPIC_BUTTON);
			break;
		default: break;

		}
	}

	/**
	 * @author thuntn
	 * @param ip
	 */
	public void goToBanIPForum(){
		click(ELEMENT_MORE_ACTION);
		click(ELEMENT_BAN_IP_FORUM);
		waitForAndGetElement(ELEMENT_BAN_IP_POPUP);

	}
	/**Ban IP for a forum
	 * @author thuntn
	 * @param ip
	 */
	public void banIPForum(String...ip){
		info("Ban IP for a forum");
		if (ip.length > 0){

			goToBanIPForum();
			inputBanIP(ip);
			button.cancel();
		}
	}

	/**Check rights of moderator of category for all forums or not
	 * @param user: user name
	 * @param password: password
	 * @param category: name of category
	 * @param forum: name of forum
	 * @param moderator: true, if the user has right of moderator for the category, and vice versa
	 * 
	 */
	public void checkRightOfCategoryForum(String user, String password, String category, String forum, boolean moderator){
		topic = new ForumManageTopic(driver);
		account.signOut();
		account.signIn(user, password);
		
		goToForums();
		click(By.linkText(category));
		click(By.linkText(forum));
		waitForAndGetElement(topic.ELEMENT_START_TOPIC_BUTTON);
		if(moderator){
			click(ELEMENT_MORE_ACTION);
			waitForAndGetElement(ELEMENT_EDIT_FORUM);
			waitForAndGetElement(ELEMENT_CLOSE_FORUM);
			waitForAndGetElement(ELEMENT_LOCK_FORUM);
			info(user + "can edit, close and lock the forum");
		}else{
			waitForElementNotPresent(ELEMENT_MORE_ACTION);
			info(user + "cannot edit, close and lock the forum");
		}

	}

	/**
	 * Determine if the emails are autofill.
	 * Created by khanhnt at Dec 9, 2013
	 * @param email
	 */
	public void isEmailAutofillInModerator(String email){
		click(ELEMENT_MODERATOR_TAB);
		waitForTextPresent(email);
	}
	
}
