package org.exoplatform.selenium.platform.forum.functional.forum.usersettings;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.*;


/**
 * @author thuntn
 * @date Jan, 22
 */
public class Forum_Forum_UserSettings extends ForumBase {


	ManageAccount acc;
	ForumManageCategory fmCat;
	ForumManageForum fmForum;
	ForumManageTopic fmTopic;
	ForumManagePost fmPost;

	@BeforeMethod
	public void setUpBeforeTest() {
		initSeleniumTest();
		driver.get(baseUrl);
		fmCat = new ForumManageCategory(driver);
		fmForum = new ForumManageForum(driver);
		fmTopic = new ForumManageTopic(driver,this.plfVersion);
		fmPost = new ForumManagePost(driver);
		acc = new ManageAccount(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		button = new Button(driver);
		goToForums();	
	}

	@AfterMethod
	public void afterTest() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Case ID: 72363
	 * Check My Subscriptions
	 * Case ID: 72509
	 * Remove watches from user setting
	 * Case ID: 72782
	 * Get feed URL from watch RSS list
	 * https://jira.exoplatform.org/browse/FORUM-767
	 * This bug happens in PLF 4.1.0
	 */
	@Test(groups="error")
	public  void test01_02_03_CheckMySubscriptions() {

		String category = "category 72363";
		String forum="Forum 72363";
		String topic = "topic 72363";
		String descTopic = "Description 72363";

		info("Case ID: 72363 - Check My Subscriptions and Case ID: 72509 - Remove watches from user setting"); 

		info("Step 1: Create categories forums, topics");

		fmTopic.addCategoryForumTopic(category, forum, topic, descTopic);
		info("Step 2: Watch some objects");
		fmTopic.watchItem(true);

		click(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", descTopic));
		waitForAndGetElement(fmPost.ELEMENT_POST_REPLY_BUTTON);
		watchItem(true);
		goToForumHome();
		click(By.linkText(category));
		watchItem(true);

		info("Step 3, 5: Show User setting main screen, Check link of watch object");

		openWatchedObject(category);
		waitForAndGetElement(fmCat.ELEMENT_CATEGORY_DESCRIPTION_TEXT.replace("${desc}", category));

		openWatchedObject(forum);
		waitForAndGetElement(fmForum.ELEMENT_FORUM_DESCRIPTION_TEXT.replace("${forum}", forum));
		waitForAndGetElement(fmForum.ELEMENT_START_TOPIC);
		waitForAndGetElement(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", descTopic));

		openWatchedObject(topic);
		waitForAndGetElement(fmPost.ELEMENT_POST_CONTENT_TEXT.replace("${post}", descTopic));

		goToMySubscriptions();
		assert waitForAndGetElement(ELEMENT_FEED_URL.replace("${item}",category),DEFAULT_TIMEOUT,1,2).getAttribute("disabled") != null : "Fail! Can check the RSS checkbox " + category;
		assert waitForAndGetElement(ELEMENT_FEED_URL.replace("${item}",forum),DEFAULT_TIMEOUT,1,2).getAttribute("disabled") != null : "Fail! Can check the RSS checkbox " + forum;
		assert waitForAndGetElement(ELEMENT_FEED_URL.replace("${item}",topic),DEFAULT_TIMEOUT,1,2).getAttribute("disabled") != null : "Fail! Can check the RSS checkbox " + topic;

		info("Case ID: 72509 - Remove watches from user setting");
		goToMySubscriptions();
		deleteWatches(category);
		deleteWatches(forum);
		deleteWatches(topic);
		button.closeWindow();

		click(fmTopic.ELEMENT_CATEGORY_BREAD.replace("${category}", category));

		//Delete data
		fmCat.deleteCategoryInForum(category);

	}

	/**
	 * Case ID: 72617
	 * Check  RSS feed
	 * Case ID: 72706
	 * Remove RSS feed  from user setting
	 */
	//This case has error in PLF 4.1, (cannot right click on category, forum, topic) because the PLF 4.1 is not stable
	@Test
	public  void test04_05_CheckAndRemoveRSSFeed() {

		String category = "category 72617";
		String forum="Forum 72617";
		String topic = "topic 72617";
		String descTopic = "Description 72617";

		info("Case ID: 72617 - Check  RSS feed and Case ID: 72706 - Remove RSS feed  from user setting"); 

		info("Step 1: Create categories forums, topics");
		fmTopic.addCategoryForumTopic(category, forum, topic, descTopic);

		info("Step 2: RSS some objects");
		goToRSS(topic);

		goToForumHome();
		goToRSS(category);

		click(By.linkText(category));
		goToRSS(forum);

		info("Step 3,4, 5: Show User setting main screen, Check watch list, Check link of RSS object");

		openWatchedObject(category);
		waitForAndGetElement(fmCat.ELEMENT_CATEGORY_DESCRIPTION_TEXT.replace("${desc}", category));

		openWatchedObject(forum);
		waitForAndGetElement(fmForum.ELEMENT_FORUM_DESCRIPTION_TEXT.replace("${forum}", forum));
		waitForAndGetElement(fmForum.ELEMENT_START_TOPIC);
		waitForAndGetElement(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", descTopic));

		openWatchedObject(topic);
		waitForAndGetElement(fmPost.ELEMENT_POST_CONTENT_TEXT.replace("${post}", descTopic));

		info("Case ID: 72706 - Remove RSS feed  from user setting");
		goToMySubscriptions();
		deleteWatches(category);
		deleteWatches(forum);
		deleteWatches(topic);
		button.closeWindow();

		click(fmTopic.ELEMENT_CATEGORY_BREAD.replace("${category}", category));

		//Delete data
		fmCat.deleteCategoryInForum(category);

	}

	/**
	 * Case ID: 72781
	 * Get feed URL from RSS list
	 */
	//This case has error in PLF 4.1, (cannot right click on category, forum, topic) because the PLF 4.1 is not stable
	@Test
	public void test06_GetFeedURLFromRSSList() {

		String category = "category 72781 1";
		String forum ="Forum 72781 1";
		String topic = "topic 72781 1";
		String descTopic = "Description 72781 1";
		String category2 = "category 72781 2";
		String forum2 ="Forum 72781 2";
		String topic2 = "topic 72781 2";
		String descTopic2 = "Description 72781 2";
		String url;

		info("Case ID: 72781 - Get feed URL from RSS list and Case ID: 72782 - Get feed URL from watch RSS list"); 

		info("Step 1: Create categories forums, topics");
		fmTopic.addCategoryForumTopic(category, forum, topic, descTopic);
		goToForumHome();
		fmTopic.addCategoryForumTopic(category2, forum2, topic2, descTopic2);

		info("Step 2: RSS some objects");
		goToRSS(topic2);

		goToForumHome();
		goToRSS(category2);

		click(By.linkText(category2));
		goToRSS(forum2);

		goToForumHome();
		goToRSS(category);

		click(By.linkText(category));
		goToRSS(forum);

		click(By.linkText(forum));
		goToRSS(topic);

		info("Step 3, 4, 5: Show User setting main screen, Check RSS list, Check get feed URL");

		goToMySubscriptions();
		check(ELEMENT_FEED_URL.replace("${item}", category),2);
		check(ELEMENT_FEED_URL.replace("${item}", category2),2);
		check(ELEMENT_FEED_URL.replace("${item}", forum),2);
		check(ELEMENT_FEED_URL.replace("${item}", forum2),2);
		check(ELEMENT_FEED_URL.replace("${item}", topic),2);
		check(ELEMENT_FEED_URL.replace("${item}", topic2),2);
		button.save();

		goToMySubscriptions();
		url = waitForAndGetElement(ELEMENT_FEED_URL_TEXTBOX).getAttribute("value");
		button.closeWindow();
		driver.get(url);
		waitForAndGetElement("//*[@id='feedContent']");
		assert getText("//*[@id='feedContent']").contains(topic): "Fail! The topic is not shown";
		assert getText("//*[@id='feedContent']").contains(topic2): "Fail! The topic is not shown";

		//Delete data
		driver.get(baseUrl);
		goToForums();
		click(fmForum.ELEMENT_CATEGORY_BREAD.replace("${category}", category));
		fmCat.deleteCategoryInForum(category);

		click(By.linkText(category2));
		fmCat.deleteCategoryInForum(category2);

	}

	/**
	 * This function is used for ONLY this class
	 * @param object: topic, forum or category
	 */
	public void openWatchedObject(String object){
		goToMySubscriptions();
		click(ELEMENT_MY_SUBSCRIPTION_OBJECT_WATCH.replace("${object}", object));

	}

}