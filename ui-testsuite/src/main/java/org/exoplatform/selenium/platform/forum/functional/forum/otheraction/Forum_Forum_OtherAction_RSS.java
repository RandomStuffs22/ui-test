package org.exoplatform.selenium.platform.forum.functional.forum.otheraction;



import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;




	/**
	* @author khanhnt
	*
	*/
	public class Forum_Forum_OtherAction_RSS extends ForumBase{

		ManageAccount acc;
		ForumManageCategory fmCat;
		ForumManageForum fmForum;
		ForumManageTopic fmTopic;
		ForumManagePost fmPost;

		@BeforeMethod
		public void setUpBeforeTest() {
			initSeleniumTest();
			driver.get(baseUrl);
			fmCat = new ForumManageCategory(driver,this.plfVersion);
			fmForum = new ForumManageForum(driver,this.plfVersion);
			fmTopic = new ForumManageTopic(driver,this.plfVersion);
			fmPost = new ForumManagePost(driver,this.plfVersion);
			acc = new ManageAccount(driver,this.plfVersion);
			acc.signIn(DATA_USER_JOHN, DATA_PASS);
			button = new Button(driver,this.plfVersion);
			goToForums();
		}

		@AfterMethod
		public void afterTest() {
			driver.manage().deleteAllCookies();
			driver.quit();
		}


	/**
	* Case ID:72310.
	* Test Case Name: Get RSS of a specific Category.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : Ubuntu 13.04, FF22.
	* Generated by khanhnt at 2013/12/20 08:53:58
	*/
	@Test
	public  void test01_GetRSSOfASpecificCategory() {
		info("Test 1: Get RSS of a specific Category");
		
		String category = "Test 1 new category";
		String descTopic = "Test 1 description";
		String forum = "Test 1 new forum";
		String topic=  "Test 1 new topic";
		String post="Test 1 new Post";
		/*Step 1: Create category, forum, topics, posts
		*Input Data: 
		- Login as root
		- Perform add category, forum, topic, post
		*Expected Outcome: Category, Forum, topic, post are created and displayed in UI		*/
		
		fmTopic.addCategoryForumTopic(category, forum, topic, descTopic);
		click(By.linkText(topic));
		fmPost.quickReply(post);
		/*Step 2: Get RSS one specific category
		*Input Data: Right click on specific category and select RSS on menu
		*Expected Outcome: 
		- RSS feed is displayed content in new tab
		- Category feeds contains all posts which published in all topics in all forums in a category 
		- Category Feeds show: title (title of the category), description (category description), 
		  link (permlink to the category), public date(creation date of the category) 		*/ 
		
		
		goToForumHome();
		
		goToRSS(category);
		switchToNewWindow();
		waitForAndGetElement(ELEMENT_RSS_PAGE);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_TITLE_TEXT).getText(), category);
		waitForAndGetElement(By.linkText(topic));
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(descTopic), true);
		waitForAndGetElement(By.linkText("Re: "+topic));
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(post), true);
		switchToParentWindow();
		click(By.linkText(category));
		fmCat.deleteCategoryInForum(category);
	}



	/**
	* Case ID:72467.
	* Test Case Name: Get RSS of a specific forum.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : Ubuntu 13.04, FF22.
	* Generated by khanhnt at 2013/12/20 08:53:58
	*/
	@Test
	public  void test02_GetRSSOfASpecificForum() {
		info("Test 2: Get RSS of a specific forum");
		String category = "Test 2 new category";
		String descTopic = "Test 2 description";
		String forum = "Test 2 new forum";
		String topic=  "Test 2 new topic";
		String post="Test 2 new Post";
		/*Step 1: Create category, forum, topics, posts
		*Input Data: 
		- Login as root
		- Perform add category, forum, topic, post
		*Expected Outcome: Category, Forum, topic, post are created and displayed in UI		*/
		fmTopic.addCategoryForumTopic(category, forum, topic, descTopic);
		click(By.linkText(topic));
		fmPost.quickReply(post);

		/*Step 2: Get RSS one specific category
		*Input Data: Right click on specific forum and select RSS on menu
		*Expected Outcome: 
		- RSS feed is displayed content in new tab
		- Forum feeds contains all posts which published in all topics in a forum 
		- Forum Feeds show: title (Name of the forum), description (description of the forum), 
		  link (permlink of the forum), public date (creation date of the forum )		*/ 
		goToForumHome();
		click(By.linkText(category));
		goToRSS(forum);
		switchToNewWindow();
		waitForAndGetElement(ELEMENT_RSS_PAGE);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_TITLE_TEXT).getText(), forum);
		waitForAndGetElement(By.linkText(topic));
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(descTopic), true);
		waitForAndGetElement(By.linkText("Re: "+topic));
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(post), true);
		switchToParentWindow();
		goToForumHome();
		click(By.linkText(category));
		fmCat.deleteCategoryInForum(category);
 	}



	/**
	* Case ID:72585.
	* Test Case Name: Get RSS of a specific topic.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : Ubuntu 13.04, FF22.
	* Generated by khanhnt at 2013/12/20 08:53:58
	*/
	@Test
	public  void test03_GetRSSOfASpecificTopic() {
		info("Test 3: Get RSS of a specific topic");
		String category = "Test 3 new category";
		String descTopic = "Test 3 description";
		String forum = "Test 3 new forum";
		String topic=  "Test 3 new topic";
		String post="Test 3 new Post";
		/*Step 1: Create category, forum, topics, posts
		*Input Data: 
		- Login as root
		- Perform add category, forum, topic, post
		*Expected Outcome: Category, Forum, topic, post are created and displayed in UI		*/
		fmTopic.addCategoryForumTopic(category, forum, topic, descTopic);
		click(By.linkText(topic));
		fmPost.quickReply(post);

		/*Step 2: Get RSS one specific category
		*Input Data: Right click on specific topic and select RSS on menu
		*Expected Outcome: 
		- RSS feed is displayed content in new tab
		- Topic feeds contains all posts which published in a topic 
		- Topic Feeds show: title (Title of the topic), description (content of the topic's initial 
		  post), link (permlink to the topic), pubdate (creation date of the topic)		*/ 
		goToForumHome();
		click(By.linkText(forum));
		goToRSS(topic);
		switchToNewWindow();
		waitForAndGetElement(ELEMENT_RSS_PAGE);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_TITLE_TEXT).getText(), topic);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_SUBTITLE_TEXT).getText(), descTopic);
		waitForAndGetElement(By.linkText(topic));
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(descTopic), true);
		waitForAndGetElement(By.linkText("Re: "+topic));
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(post), true);
		switchToParentWindow();
		goToForumHome();
		click(By.linkText(category));
		fmCat.deleteCategoryInForum(category);
 	}



	/**
	* Case ID:72683.
	* Test Case Name: Get RSS of a specific category which without any post.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : Ubuntu 13.04, FF22.
	* Generated by khanhnt at 2013/12/20 08:53:58
	*/
	@Test
	public  void test04_GetRSSOfASpecificCategoryWhichWithoutAnyPost() {
		info("Test 4: Get RSS of a specific category which without any post");
		String catName = "test 04 Category name";
		String order = "0";
		int chooseRestricted = 1;
		String[] restricted = { "john" };
		String description = "Description";
		int setPermission = 0;
		String[] userGroup = null;
		boolean permission = false;
		
		/*Step 1: Create category
		*Input Data: 
		- Login as root
		- Perform add category 
		*Expected Outcome: Category is created and displayed in UI		*/
		fmCat.addNewCategoryInForum(catName, order, chooseRestricted,
				restricted, description, setPermission, userGroup, permission);

		/*Step 2: Get RSS one specific category which without post
		*Input Data: Right click on category which created at step aboveand select RSS on menu
		*Expected Outcome: 
		- RSS feed will display category name with blank content in new tab 		*/ 
		goToForumHome();
		
		goToRSS(catName);
		switchToNewWindow();
		waitForAndGetElement(ELEMENT_RSS_PAGE);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_TITLE_TEXT).getText(), catName);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_SUBTITLE_TEXT).getText(), description);
		waitForElementNotPresent(ELEMENT_FEED_CONTENT);
		switchToParentWindow();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName);
 	}



	/**
	* Case ID:72761.
	* Test Case Name: Get RSS of a specific forum which without any post.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : Ubuntu 13.04, FF22.
	* Generated by khanhnt at 2013/12/20 08:53:58
	*/
	@Test
	public  void test05_GetRSSOfASpecificForumWhichWithoutAnyPost() {
		info("Test 5: Get RSS of a specific forum which without any post");
		String category = "Test 5 new category";
		String forum = "Test 5 new forum";
		/*Step 1: Create category, forum
		*Input Data: 
		- Login as root
		- Perform add category, forum without post
		*Expected Outcome: Category, Forum are created and displayed in UI		*/
		fmForum.addCategoryForum(category, forum);

		/*Step 2: Get RSS one specific forum which without post
		*Input Data: Right click on forum which created at step aboveand select RSS on menu
		*Expected Outcome: 
		- RSS feed will display forum name with blank content in new tab 		*/ 
		goToForumHome();
		click(By.linkText(category));
		goToRSS(forum);
		switchToNewWindow();
		waitForAndGetElement(ELEMENT_RSS_PAGE);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_TITLE_TEXT).getText(), forum);
		waitForElementNotPresent(ELEMENT_FEED_CONTENT);
		switchToParentWindow();
		fmCat.deleteCategoryInForum(category);
 	}



	/**
	* Case ID:72881.
	* Test Case Name: Get RSS a specific forum by anonymous user.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : Ubuntu 13.04, FF22.
	* Generated by khanhnt at 2013/12/20 08:53:58
	* Status: Need to be removed
	*/
	@Test(groups="pending")
	public  void test06_GetRSSASpecificForumByAnonymousUser() {
		info("Test 6: Get RSS a specific forum by anonymous user");
	
	
		/*Step 1: Create category, forum, topic, post
		*Input Data: 
		- Login as root
		- Perform add category, forum, topic, post
		*Expected Outcome: Category, Forum are created and displayed in UI		*/
	

		/*Step 2: Get RSS
		*Input Data: 
		- In Public mode: Goto Forum Page
		- View specify forum and click on â€œRSSâ€ icon in action bar
		*Expected Outcome: 
		- RSS feed is displayed content in new tab
		- Forum feeds should contain all posts in all topics in this forum which published
		- Forum Feeds show: title (Name of the forum), description (description of the forum), 
		  link (permlink of the forum), pubdate (creation date of the forum )		*/ 
 	}



	/**
	* Case ID:72975.
	* Test Case Name: Get RSS of a topic which contains Private post.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : Ubuntu 13.04, FF22.
	* Generated by khanhnt at 2013/12/20 08:53:58
	*/
	@Test
	public  void test07_GetRSSOfATopicWhichContainsPrivatePost() {
		info("Test 7: Get RSS of a topic which contains Private post");
		/*Step 1: Create category, forum, topic, post
		*Input Data: 
		- Login as root
		- Perform add category, forum, topic, post
		*Expected Outcome: Category, Forum are created and displayed in UI		*/
		String category = "Test 7 new category";
		String descTopic = "Test 7 description";
		String forum = "Test 7 new forum";
		String topic=  "Test 7 new topic";
		String post="Test 7 new Post";
		String postMessage="Test 7 new message";

		/*Step 2: Create a private post
		*Input Data: 
		- Create a private post into Topic which added at step above
		*Expected Outcome: Private post is created successful and shown in topic		*/
		fmTopic.addCategoryForumTopic(category, forum, topic, descTopic);
		click(By.linkText(topic));
		fmPost.privatePost(topic, post, postMessage, "", "", "");

		/*Step 3: Get RSS
		*Input Data: 
		- Right click on added topic and select â€œRSSâ€
		*Expected Outcome: 
		- RSS feed is displayed content in new tab 
		- Topic feeds should contain all posts in this topic except Private post
		- Topic Feeds show: title (Title of the topic), description (content of the topic's initial post), link (permlink to the topic), pubdate (creation date of the topic)		*/ 
		goToForumHome();
		click(By.linkText(forum));
		goToRSS(topic);
		switchToNewWindow();
		waitForAndGetElement(ELEMENT_RSS_PAGE);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_TITLE_TEXT).getText(), topic);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_SUBTITLE_TEXT).getText(), descTopic);
		waitForAndGetElement(By.linkText(topic));
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(descTopic), true);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(post), false);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(postMessage), false);
		switchToParentWindow();
		goToForumHome();
		click(By.linkText(category));
		fmCat.deleteCategoryInForum(category);
 	}



	/**
	* Case ID:73015.
	* Test Case Name: Get RSS of a Censored post.
	* Pre-Condition: 
	* Post-Condition: 
	* Status: Fail with FF22 and plfent-4.0.x-20131118.122516-366
	* Bug : https://jira.exoplatform.org/browse/FORUM-747
	* This case is wrong, pls update step later
	* Generated by khanhnt at 2013/12/20 08:53:58
	*/
	@Test
	public  void test08_GetRSSOfACensoredPost() {
		info("Test 8: Get RSS of a Censored post");
		String catName = "Test 8 new category";
		String fmName = "Test 8 new forum";
		String tpName = "Test 8 new topic";
		String censorPost = "censor post";
		String censorText = "censor";
		
		
		/*Step 1: Create category, forum, topic, post
		*Input Data: 
		- Login as root
		- Perform add category, forum, topic, post
		*Expected Outcome: Category, Forum are created and displayed in UI		*/
		fmForum.addCategoryForum(catName, fmName);
		setCensorKeywords(censorText);
		fmTopic.quickStartTopic(tpName, tpName);
		click(By.linkText(tpName));
		/*Step 2: Create a Censored post
		*Input Data: 
		- Create a Censored post into Topic which added at step above
		*Expected Outcome: Censored post is created successful and shown in topic		*/
		fmPost.quickReply(censorPost);
		waitForTextPresent(censorPost);
		/*Step 3: Get RSS
		*Input Data: 
		- Right click on added topic and select â€œRSSâ€
		*Expected Outcome: 
		- RSS feed is displayed content in new tab
		- Topic feeds should contain all posts in this topic except Censored post
		- Topic Feeds show: title (Title of the topic), description (content of the topic's initial post), 
		  link (permlink to the topic), pubdate (creation date of the topic)		*/ 
		goToForumHome();
		click(By.linkText(fmName));
		goToRSS(tpName);
		switchToNewWindow();
		waitForAndGetElement(ELEMENT_RSS_PAGE);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_TITLE_TEXT).getText(), tpName);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_SUBTITLE_TEXT).getText(), tpName);
		waitForAndGetElement(By.linkText(tpName));
		//fail, censor post still appears
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(censorPost), false);
		waitForAndGetElement(By.linkText("Re: "+tpName));
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(tpName), true);
		switchToParentWindow();
		goToForumHome();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName);
 	}



	/**
	* Case ID:73049.
	* Test Case Name: Get RSS of a category after move forum.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : Ubuntu 13.04, FF22.
	* Generated by khanhnt at 2013/12/20 08:53:58
	*/
	@Test
	public  void test09_GetRSSOfACategoryAfterMoveForum() {
		info("Test 9: Get RSS of a category after move forum");
		String catDes = "Destination";
		String catName = "Test 9 new category";
		String fmName = "Test 9 new forum";
		String tpName = "Test 9 new topic";
		/*Step 1: Create category
		*Input Data: 
		- Login as root
		- Create categories 
		*Expected Outcome: New categories are created successful		*/
		/*Step 2: Create forum
		*Input Data: 
		- Create forums in added category 
		*Expected Outcome: New forums is created successful inside categories		*/				
		/*Step 3: Create topic 
		*Input Data: 
		- Create topics into added forum at step above 
		*Expected Outcome: Topics are added successful 		*/
		fmCat.addNewCategoryInForum(catDes, "1", 0, null, catDes, 0, null);
		goToForumHome();
		fmTopic.addCategoryForumTopic(catName, fmName, tpName, tpName);
		/*Step 4: Move forum
		*Input Data: 
		- Open a specify category: select forum and move it to one destination category
		*Expected Outcome: 
		- Selected forum is moved successful and shown properly in destination category		*/
		goToForumHome();
		click(By.linkText(catName));
		click(By.linkText(fmName));
		fmForum.moveForum(fmName, catDes);
		/*Step 5: Check get RSS feed of destination category 
		*Input Data: 
		- Right click on destination category and select â€œRSSâ€
		*Expected Outcome: 
		- RSS feed is displayed content in new tab 
		- Category feeds should contain all posts which published in all forums
		  (includes forum that moved to) of category */
		goToForumHome();
		goToRSS(catDes);
		switchToNewWindow();
		waitForAndGetElement(ELEMENT_RSS_PAGE);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_TITLE_TEXT).getText(), catDes);
		waitForAndGetElement(By.linkText(tpName));
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(tpName), true);
		
		/*Step 6: Check get RSS feed of category which contains forum that moved
		*Input Data: 
		- Right click on category which contains forum that moved and select â€œRSSâ€
		*Expected Outcome: 
		- RSS feed is displayed content in new tab
		- Category feeds should contain all posts which published in all forums (except moved forum) of category		*/ 
		switchToParentWindow();
		goToRSS(catName);
		switchToNewWindow();
		waitForAndGetElement(ELEMENT_RSS_PAGE);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_TITLE_TEXT).getText(), catName);
		waitForElementNotPresent(By.linkText(tpName));
		waitForElementNotPresent(ELEMENT_FEED_CONTENT);
		
		switchToParentWindow();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName);
		click(By.linkText(catDes));
		fmCat.deleteCategoryInForum(catDes);
 	}



	/**
	* Case ID:73084.
	* Test Case Name: Get RSS of a Forum after move topic.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : Ubuntu 13.04, FF22.
	* Generated by khanhnt at 2013/12/20 08:53:58
	*/
	@Test
	public  void test10_GetRSSOfAForumAfterMoveTopic() {
		info("Test 10: Get RSS of a Forum after move topic");
		String catDes = "Destination";
		String fmDes = "Forum1";
		String catName = "Test 10 new category";
		String fmName = "Test 10 new forum";
		String tpName = "Test 10 new topic";
		/*Step 1: Create category
		*Input Data: 
		- Login as root
		- Create categories 
		*Expected Outcome: New categories are created successful		*/
		/*Step 2: Create forum
		*Input Data: 
		- Create forums in added category 
		*Expected Outcome: New forums is created successful inside categories		*/
		/*Step 3: Create topic 
		*Input Data: 
		- Create topics into added forum at step above 
		*Expected Outcome: Topics are added successful inside forums		*/
		fmForum.addCategoryForum(catDes, fmDes);
		goToForumHome();
		fmTopic.addCategoryForumTopic(catName, fmName, tpName, tpName);
		/*Step 4: Move topic
		*Input Data: 
		- Open a forum: select topic and move it to one destination forum
		*Expected Outcome: Selected topic is moved successful and shown properly in destination forum		*/
		click(By.linkText(tpName));
		fmTopic.moveTopic(tpName, fmDes);
		/*Step 5: Check get RSS feed of destination forum
		*Input Data: Right click on destination forum and select â€œRSSâ€
		*Expected Outcome: 
		- RSS feed is displayed content in new tab
		- Forum feeds contains all topics which published in forum(includes topic that moved to)		*/
		goToForumHome();
		click(By.linkText(catDes));
		goToRSS(fmDes);
		switchToNewWindow();
		waitForAndGetElement(ELEMENT_RSS_PAGE);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_TITLE_TEXT).getText(), fmDes);
		waitForAndGetElement(By.linkText(tpName));
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_CONTENT).getText().contains(tpName), true);
		/*Step 6: Check get RSS feed of forum which contains topic that moved
		*Input Data: Right click on forum which contains forum that moved and select â€œRSSâ€
		*Expected Outcome: 
		- RSS feed is displayed content in new tab
		- Topic feeds should contain all topics which published in forum except moved topic		*/ 
		switchToParentWindow();
		goToForumHome();
		click(By.linkText(catName));
		goToRSS(fmName);
		switchToNewWindow();
		waitForAndGetElement(ELEMENT_RSS_PAGE);
		Assert.assertEquals(waitForAndGetElement(ELEMENT_FEED_TITLE_TEXT).getText(), fmName);
		waitForElementNotPresent(By.linkText(tpName));
		waitForElementNotPresent(ELEMENT_FEED_CONTENT);
		
		switchToParentWindow();
		goToForumHome();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName);
		click(By.linkText(catDes));
		fmCat.deleteCategoryInForum(catDes);
 	}



	/**
	* Case ID:73332.
	* Test Case Name: Get RSS a specific category by anonymous user.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : 
	* Generated by khanhnt at 2013/12/20 08:53:58
	* Status: Need to be removed
	*/
	@Test(groups="pending")
	public  void test11_GetRSSASpecificCategoryByAnonymousUser() {
		info("Test 11 Get RSS a specific category by anonymous user");
		/*Step 1: Create category, forum, topic, post
		*Input Data: 
		- Login as root
		- Perform add category, forum, topic, post
		*Expected Outcome: Category, Forum are created and displayed in UI		*/


		/*Step 2: Get RSS
		*Input Data: 
		- In Public mode: Goto Forum Page
		- View specify category and click on "RSS" icon in action bar
		*Expected Outcome: 
		- RSS feed is displayed content in new tab
		- Category feeds contains all posts in all topics in all forums in a category which published
		- Category Feeds show: title (title of the category), description (category description), link (permlink to the category), public date(creation date of the category) 		*/ 

 	}

	/**
	* Case ID:73337.
	* Test Case Name: Get RSS a specific topic by anonymous user.
	* Pre-Condition: 
	* Post-Condition: 
	* Done with OSs and browsers : 
	* Generated by khanhnt at 2013/12/20 11:08:43
	* Status: Need to be removed
	*/
	@Test(groups="pending")
	public  void test12_GetRSSASpecificTopicByAnonymousUser() {
		info("Test 12 Get RSS a specific topic by anonymous user");
		/*Step 1: Create category, forum, topic, post
		*Input Data: 
		- Login as root
		- Perform add category, forum, topic, post
		*Expected Outcome: Category, Forum are created and displayed in UI		*/


		/*Step 2: Get RSS
		*Input Data: 
		- In Public mode: Goto Forum Page
		- View specify topic and click on "RSS" icon in action bar
		*Expected Outcome: 
		- RSS feed is displayed content in new tab
		- Topic feeds should contain all posts in this topic which published
		- Topic Feeds show: title (Title of the topic), description (content of the topic's initial post), link (permlink to the topic), pubdate (creation date of the topic)		*/ 

 	}

}