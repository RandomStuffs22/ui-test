package org.exoplatform.selenium.platform.forum.functional.forum.category;

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
 *
 * @author chinhdtt
 * @date 21 Nov 2013
 *
 */

public class Forum_Forum_Category_WatchAndUnwatch extends ForumBase{
	ManageAccount magAc;
	ForumManageCategory cat;
	ForumManageForum forum;
	ForumManageTopic topic;
	ManageAlert alert; 

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		magAc = new ManageAccount(driver);
		cat = new ForumManageCategory(driver);
		forum = new ForumManageForum(driver);
		topic = new ForumManageTopic(driver);
		button = new Button(driver);
		magAc.signIn(DATA_USER1, DATA_PASS);
		alert = new ManageAlert(driver); 
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Check send notify when add new topic into a forum that is being watched
	 * Test caseID 73309	
	 * Step 1: Create category, forum
	 * Step 2: Watch forum
	 * Step 3: Add topic into forum/category that is being watched
	 */

	@Test
	public void test01_CheckSendNotifyWhenAddNewTopicIntoAForumThatIsBeingWatched() {
		/*Declare variables*/ 
		String catName = "New Category 001"; 
		String order = "1";
		int chooseRestricted = 1;
		String[] restricted = {DATA_USER1}; 
		String description = "Description of Category 001";
		int setPermission = 2; 
		String[] userGroup = {DATA_USER1}; 
		String[] addForum = {"Title of forum 001", "1", "Open", "Unlocked", "Description of forum 001"}; 	
		String title = "Title topic 001"; 
		String message = "Topic 001"; 

		/* Step 1: Create category, forum*/
		//- Login by the administrator to create new category, forum
		magAc.updateUserProfile(null,null, null, EMAIL_ADDRESS2);
		goToForums(); 
		info("Create New Category");
		cat.goToAddCategory(); 
		cat.inputDataCategoryInForum(catName, order, chooseRestricted, restricted,
				description, setPermission, userGroup, true);
		button.save();
		info("Create New Forum");
		forum.goToAddForum(); 
		forum.inputDataInAddForumTab_addForum(catName, addForum); 
		button.save(); 

		/*Step 2: Watch forum*/
		//- Select [Watch] icon 
		forum.watchItem(true);

		/* Step 3: Add topic into forum/category that is being watched*/
		// - Add new topic into above forum
		info("Create New Topic");
		click(By.linkText(catName));
		click(By.linkText(addForum[0])); 
		topic.quickStartTopic(title, message); 
		//Check email
		goToMail(EMAIL_ADDRESS2, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}", addForum[0]).replace("${topic}", title)), message);
		// Clean data test
		switchToParentWindow();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);	
	} 

	/** Check send notify after adding new topic into forum/category which is watching  and it's required for approval
	 * Test caseID 73320
	 * Step 1: Create category, forum
	 * Step 2: Watch forum/category
	 * Step 3: Add topic into forum/category that is being watched
	 */

	@Test
	public void test02_CheckSendNotifyAfterAddingNewTopicIntoForumCategoryWhichIsWatchingItIsRequiredForAndApproval() {
		/*Declare variables*/ 
		String catName = "New Category 002"; 
		String order = "1";
		int chooseRestricted = 1;
		String[] restricted = {DATA_USER4}; 
		String description = "Description of Category 002";
		int setPermission = 0; 
		String[] userGroup = {""}; 
		String[] addForum = {"Title of forum 002", "1", "Open", "Unlocked", "Description of forum 002"}; 	
		String title = "Title topic 002"; 
		String message = "Topic 002"; 
		String topicEmail = "Topic email 002"; 

		/* Step 1: Create category, forum */
		//- Login by the administrator to create new category, forum
		//- Create forum with checked Moderate Topics option
		goToForums(); 
		info("Create New Category");
		cat.goToAddCategory(); 
		cat.inputDataCategoryInForum(catName, order, chooseRestricted, restricted,
				description, setPermission, userGroup, true);
		button.save();

		info("Create New Forum checked Moderate Topics option");
		forum.goToAddForum(); 
		forum.inputDataInAddForumTab_addForum(catName, addForum); 
		forum.inputDataInModerOptionTab_addForum(true, EMAIL_ADDRESS1, topicEmail, true);
		button.save(); 
		magAc.signOut();

		//		 Step 2: Watch forum/category 
		// - Login by the normal user
		// - On created category and select [Watch]
		info("Login by the normal user");
		magAc.signIn(DATA_USER4, DATA_PASS);
		magAc.updateUserProfile(null,null, null, EMAIL_ADDRESS1);
		goToForums(); 
		click(By.linkText(catName));
		cat.watchItem(true);
		alert.acceptAlert();

		//		Step 3: Add topic into forum/category that is being watched
		// - Add new topic into above forum
		click(By.linkText(addForum[0]));
		info("Create New Topic");
		topic.startTopic(title, message, null, 0, userGroup, true, true);
		alert.cancelAlert();
		magAc.signOut();
		magAc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		click(By.linkText(title)); 
		topic.approveTopic();
		//Check email
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}", addForum[0]).replace("${topic}", title)), message);
		//Clean data test
		switchToParentWindow();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);	
	} 

	/** Check send notify after adding new topic and it's in pending for censor
	 * Test caseID 73327
	 * Step 1: Create category, forum
	 * Step 2: Watch forum/category
	 * Step 3: Add topic into forum/category that is being watched
	 */	
	@Test
	public void test03_CheckSendNotifyAfterAddingNewTopicAndItIsInPendingForCensor() {
		/*Declare variables*/ 
		String catName = "New Category 003"; 
		String order = "1"; 
		int chooseRestricted = 1;  
		String[] restricted = {DATA_USER4}; 
		String description = "Description of Category 003";
		int setPermission = 0; 
		String[] userGroup = {DATA_USER4}; 
		String[] addForum = {"Title of forum 003", "1", "Open", "Unlocked", "Description of forum 003"}; 	
		String message = "Topic 003"; 
		String key = "Test"; 

		/* Step 1: Create category, forum */
		//- Login by the administrator to create new category, forum
		//- Define Censored word from Administration form	
		goToForums(); 
		info("Create New Category");
		cat.goToAddCategory(); 
		cat.inputDataCategoryInForum(catName, order, chooseRestricted, restricted,
				description, setPermission, userGroup, true);
		button.save(); 
		info("Create New Forum");
		forum.goToAddForum(); 
		forum.inputDataInAddForumTab_addForum(catName, addForum); 
		button.save(); 
		goToForums(); 
		setCensorKeywords(key);
		magAc.signOut();

		/* Step 2: Watch forum/category */
		//- Login by the normal user
		//- On created category select [Watch]
		info("Login by the normal user");
		magAc.signIn(DATA_USER4, DATA_PASS);
		magAc.updateUserProfile(null,null, null, EMAIL_ADDRESS1);
		goToForums();
		click(By.linkText(catName));
		cat.watchItem(true);
		alert.acceptAlert();

		/* Step 3: Add topic into forum/category that is being watched*/
		// - Add new topic with censor content into above forum
		click(By.linkText(addForum[0]));
		info("Create New Topic");
		topic.startTopic(key, message, null, 0, userGroup, true, true,false);
		alert.cancelAlert();
		magAc.signOut();
		magAc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		click(By.linkText(catName));
		click(By.linkText(addForum[0]));
		topic.censorTopic(key);
		//Check email
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}", addForum[0]).replace("${topic}", key)), message);
		//Clean data test
		switchToParentWindow();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);						
	}

	/** Check send notify after move topic in forum/category that is being watched
	 * Test caseID 73341
	 * Step 1: Create category, forum
	 * Step 2: Watch forum
	 * Step 3: Add topic into forum/category that is being watched
	 */

	@Test
	public void test04_CheckSendNotifyAfterMoveTopicInForumCategoryThatIsBeingWatched() {
		/*Declare variables*/ 
		String catName = "New Category 004"; 
		String order = "1"; 
		int chooseRestricted = 1;  
		String[] restricted = {DATA_USER1}; 
		String description = "Description of Category 004";
		int setPermission = 2; 
		String[] userGroup = {DATA_USER1}; 
		String[] addForum1 = {"Title of forum 004", "1", "Open", "Unlocked", "Description of forum 004"}; 	
		String[] addForum2 = {"Title of forum 005", "1", "Open", "Unlocked", "Description of forum 005"}; 	
		String title = "Title topic 004"; 
		String message = "Topic 004"; 
		String[] permission = {};

		/* Step 1: Create category, forum*/
		//- Login by the administrator to create new category, forum, topics
		magAc.updateUserProfile(null,null, null, EMAIL_ADDRESS2);
		goToForums(); 
		info("Create New Category");
		cat.goToAddCategory(); 
		cat.inputDataCategoryInForum(catName, order, chooseRestricted, restricted,
				description, setPermission, userGroup, true);
		button.save();
		info("Create New Forum");
		forum.addForum(catName, addForum1, true, "", "", false,0, permission);
		click(By.linkText(catName)); 
		forum.addForum(catName, addForum2, true, "", "", false,0, permission);
		info("Create New Topic");
		topic.quickStartTopic(title, message);

		/*Step 2: Watch forum*/
		//- Select [Watch] icon 
		click(By.linkText(catName)); 
		click(By.linkText(addForum1[0]));
		forum.watchItem(true);

		/* Step 3: Move topic */
		// - Move topic in category which watching to a destination forum
		click(By.linkText(catName)); 
		click(By.linkText(addForum2[0]));
		click(forum.ELEMENT_TOPIC_LINK.replace("${topic}", title));
		topic.moveTopic(title, addForum1[0]); 
		goToMail(EMAIL_ADDRESS2, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}", addForum1[0]).replace("${topic}", title)), title);
		//Clean data test
		switchToParentWindow();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName, true);
	} 
}
