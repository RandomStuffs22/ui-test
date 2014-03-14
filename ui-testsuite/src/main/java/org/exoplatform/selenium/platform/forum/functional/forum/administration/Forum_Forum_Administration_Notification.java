package org.exoplatform.selenium.platform.forum.functional.forum.administration;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author chinhdtt
 * @date 19 Nov 2013
 * */

public class Forum_Forum_Administration_Notification extends ForumBase{
	ForumManageCategory cat;
	ForumManageForum forum; 
	ForumManageTopic topic;
	ManageAccount acc;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		acc.signIn(DATA_USER1, DATA_PASS);
		cat = new ForumManageCategory(driver);
		forum = new ForumManageForum(driver); 
		button = new Button(driver);
		topic = new ForumManageTopic(driver); 
		alert = new ManageAlert(driver);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Change Content of notification
	 * Test caseID 72272
	 * Step 1: Open Notifications form
	 * Step 2: Change content of notification
	 * Step 3: Check notification mail after making changes in Notification table
	 */

	@Test
	public void test01_ChangeContentOfNotification() {
		/*Declare variables*/
		String subject = "Change category Notification 001";
		String content = "Content of Notification 001";
		String catName = "New Category 001"; 
		String order = "1";
		int chooseRestricted = 1;
		String[] restricted = {DATA_USER1}; 
		String description = "Description of Category 001";
		int setPermission = 0; 		
		String[] userGroup = {DATA_USER1}; 
		String[] addForum = {"Title of forum 001", "1", "Open", "Unlocked", "Description of forum 001"}; 	
		String title = "Title topic 001"; 
		String message = "Topic 001"; 

		/* Step 1: Open Notifications form */
		//- Login by the Administrator
		//- Go to Forum porlet
		//- Click on [Administration] and select [Notifications] in drop down menu
		acc.updateUserProfile(null, null, null,EMAIL_ADDRESS1);
		info("Open Notifications form");
		goToForums(); 

		/* Step 2: Change content of notification */
		//- Make changes about content of notify
		//- Click Save button
		info("Change content of notification");
		changeNotifications(false,subject, content); 

		/* Step 3: Check notification mail after making changes in Notification tab*/
		// - Create new category, 
		// - Create new forum, 
		// - Create topics with topic/post notification option and then add topic/post inside it
		// - Or Add watch on specific forum/topic and then add 
		info("Create New Category");
		cat.goToAddCategory(); 
		cat.inputDataCategoryInForum(catName, order, chooseRestricted, restricted,
				description, setPermission, userGroup, true);
		button.save();
		info("Create New Forum");
		forum.goToAddForum(); 
		forum.inputDataInAddForumTab_addForum(catName, addForum); 
		button.save();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		watchItem(true); 
		alert.acceptAlert();
		topic.quickStartTopic(title, message); 
		
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS); 
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}", addForum[0]).replace("${topic}", title)), content);
		// Clean data test
		switchToParentWindow();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);	
	}

	/** Set notification when Content moved notification field is blank
	 * Test caseID 72738
	 * Step 1: Open Notifications form
	 * Step 2: Leave Template new topic/post notification blank
	 */

	@Test
	public void test02_SetNotificationWhenContentMovedNotificationFieldIsBlank() {
		/*Declare variables*/ 
		String subject = "Change category Notification 002";
		String content = ""; 

		/* Step 1: Open Notifications form */
		//- Login by the Administrator
		//- Go to Forum porlet
		//- Click on [Administration] and select [Notifications] in drop down menu	
		info("Open Notifications form");
		goToForums(); 

		/* Step 2: Leave new post notification field blank */
		// - Remove value default in content moved notification field to blank
		// - Click Save button
		changeNotifications(false, subject, content);
		waitForAndGetElement(MSG_NOTIFY_BLANK);
		click(ELEMENT_OK_INFOR_POPUP);
	} 

	/** Set notification when new post notification field is blank
	 * Test caseID 72660
	 * Step 1: Open Notifications form
	 * Step 2: Leave new post notification field blank
	 * Note: Need to update qmetry at step 2
	 * - Change expected result to: Don't show alert message to notify that this field is required 
	 */	
	@Test
	public void test03_SetNotificationWhenNewPostNotificationFieldIsBlank() {
		/*Declare variables*/ 
		String subject = ""; 
		String content = "Content of Notification 003";

		/* Step 1: Open Notifications form */
		//- Login by the Administrator
		//- Go to Forum porlet
		//- Click on [Administration] and select [Notifications] in drop down menu	
		info("Open Notifications form");
		goToForums(); 

		/*Step 2: Leave new post notification field blank */
		//- Remove value default in new post notification field to blank
		//- Click Save button
		changeNotifications(false, subject, content); 
		goToNotifications();
		assert waitForAndGetElement(ELEMENT_NOTIFY_SUBJECT).getAttribute("value").contains("[$CATEGORY][$FORUM] $TOPIC"); 		
	}

	/** Set notification with adding a prefix to notification subject
	 * Test caseID 73314
	 * Step 1: Open Notifications form
	 * Step 2: Change content of notification
	 * Step 3: Check notification mail with adding prefix to subject
	 */
	@Test
	public void test04_SetNotificationWithAddingAPrefixToNotificationSubject() {
		/*Declare variables*/ 
		String subject = "Change category Notification 004";
		String catName = "New Category 004"; 
		String order = "1";
		int chooseRestricted = 1;
		String[] restricted = {DATA_USER1}; 
		String description = "Description of Category 004";
		int setPermission = 0; 
		String[] userGroup = {DATA_USER1}; 
		String[] addForum = {"Title of forum 004", "1", "Open", "Unlocked", "Description of forum 004"}; 	
		String title = "Title topic 004"; 
		String message = "Topic 004"; 
		String defaultSubject = "[$CATEGORY][$FORUM] $TOPIC";

		/* Step 1: Open Notifications form */
		//- Login by the Administrator
		//- Go to Forum porlet
		//- Click on [Administration] and select [Notifications] in drop down menu	
		acc.updateUserProfile(null, null, null, EMAIL_ADDRESS1);
		info("Open Notifications form");
		goToForums(); 
		goToNotifications(); 

		/*Step 2: Change content of notification */
		//- Tick on “Add a prefix to notification”
		//- Input value as prefix into Notification subject template field
		//- Click Save button
		check (ELEMENT_NOTIFY_PREFIX,2);
		type(ELEMENT_NOTIFY_SUBJECT,subject,false);
		button.save(); 

		/* Step 3: Check notification mail with adding prefix to subject */
		// - Create new category, 
		// - Create new forum, 
		// - Create topics with topic/post notification option and then add topic/post inside it
		// - Or Add watch on specific forum/topic and then add 
		info("Create New Category");
		cat.goToAddCategory(); 
		cat.inputDataCategoryInForum(catName, order, chooseRestricted, restricted,
				description, setPermission, userGroup, true);
		button.save(); 
		info("Create New Forum");
		forum.goToAddForum(); 
		forum.inputDataInAddForumTab_addForum(catName, addForum); 
		button.save(); 
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		info("Create New Topic");
		watchItem(true);
		alert.acceptAlert();
		topic.quickStartTopic(title, message); 

		// - Or Add watch on specific forum/topic and then add 
	
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS); 
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_FORUM_PREFIX.replace("${category}",catName).replace("${forum}", addForum[0]).replace("${topic}", title).replace("${subject}", subject)), message);
		// Clean data test
		switchToParentWindow();
		goToNotifications();
		uncheck (ELEMENT_NOTIFY_PREFIX,2);
		type(ELEMENT_NOTIFY_SUBJECT,defaultSubject,true);
		button.save(); 
		
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);	
	} 

	/** Set notification without adding a prefix to notification subject
	 * Test case 73322
	 * Step 1: Open Notifications form
	 * Step 2: Change content of notification
	 * Step 3: Check notification mail without adding prefix to subject
	 */
	@Test
	public void test05_SetNotificationWithoutAddingAPrefixToNotificationSubject() {
		/*Declare variables*/ 
		String catName = "New Category 005"; 
		String order = "1";
		int chooseRestricted = 1;
		String[] restricted = {DATA_USER1}; 
		String description = "Description of Category 005";
		int setPermission = 0; 
		String[] userGroup = {DATA_USER1}; 
		String[] addForum = {"Title of forum 005", "1", "Open", "Unlocked", "Description of forum 005"}; 	
		String title = "Title topic 005"; 
		String message = "Topic 005"; 

		/* Step 1: Open Notifications form */
		//- Login by the Administrator
		//- Go to Forum porlet
		//- Click on [Administration] and select [Notifications] in drop down menu	
		acc.updateUserProfile(null, null, null, EMAIL_ADDRESS1);
		info("Open Notifications form");
		goToForums(); 
		goToNotifications(); 

		/*Step 2: Change content of notification*/
		//- Untick on “Add a prefix to notificatios”
		//- Click Save button
		uncheck(ELEMENT_NOTIFY_PREFIX,2);
		button.save();

		/*Step 3: Check notification mail without adding prefix to subject*/
		// - Create new category, 
		// - Create new forum, 
		// - Create topics with topic/post notification option and then add topic/post inside it
		// - Or Add watch on specific forum/topic and then add 
		info("Create New Category");
		cat.goToAddCategory(); 
		cat.inputDataCategoryInForum(catName, order, chooseRestricted, restricted,
				description, setPermission, userGroup, true);
		button.save(); 
		info("Create New Forum");
		forum.goToAddForum(); 
		forum.inputDataInAddForumTab_addForum(catName, addForum); 
		button.save(); 
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		watchItem(true);
		alert.acceptAlert();
		info("Create New Topic");
		topic.quickStartTopic(title, message); 
		
		//Check email
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS); 
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}", addForum[0]).replace("${topic}", title)), message);
		// Clean data test
		switchToParentWindow();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);	
	} 
}
