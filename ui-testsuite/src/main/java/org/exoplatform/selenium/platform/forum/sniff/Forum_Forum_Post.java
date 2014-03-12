package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author thuntn
 * @date 16 Sep 2013
 */
public class Forum_Forum_Post extends ForumBase{
	ManageAccount acc;
	ForumManageCategory mngCat;
	ForumManageForum mngFru;
	ForumManageTopic mngTopic;
	ForumManagePost mngPost;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		mngFru = new ForumManageForum(driver,this.plfVersion);
		mngCat = new ForumManageCategory(driver,this.plfVersion);
		mngTopic = new ForumManageTopic(driver,this.plfVersion);
		mngPost = new ForumManagePost(driver,this.plfVersion);

		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**Add a post, Edit post, delete post
	 * CaseID 93511, 93512, 93513
	 */
	@Test
	public void test01_AddPost() {
		String category = "test01_Category_93511";
		String forum = "test01_forum_93511";
		String topic = "test01_topic_93511";
		String post = "test01_post_93511";
		String newPost = "New test01_post";
		
		info("Add a post, Edit post, delete post");
		mngTopic.addCategoryForumTopic(category, forum, topic, topic);
		click(By.linkText(topic));
		mngPost.postReply(post, post, "", "");
		
		mngPost.editPost(post, newPost, newPost, newPost, "", "");

		mngPost.deletePost(newPost);
		
		goToForumHome();
		click(By.linkText(category));
		mngCat.deleteCategoryInForum(category, true);
		
	}
	
	/**Add a private post
	 * CaseID 93515
	 */
	@Test
	public void test02_AddPrivatePost() {
		String category = "test02_Category_93515";
		String forum = "test02_forum_93515";
		String topic = "test02_topic_93515";
		String post = "test02_post_93515";
		info("Add a private post");
		//Add category, forum, topic
		mngTopic.addCategoryForumTopic(category, forum, topic, topic);
		click(By.linkText(topic));
		
		//Add private post
		goToTopic(DATA_USER2, category, topic);
		mngPost.privatePost(topic, post, post, "", "");
		
		//Check if other user can see private post
		goToTopic(DATA_USER4, category, topic);
		waitForElementNotPresent(mngPost.ELEMENT_POST_CONTENT_TEXT.replace("${post}", post));
		
		//Check if the receiver can see private post
		goToTopic(DATA_USER1, category, topic);
		waitForAndGetElement(mngPost.ELEMENT_POST_CONTENT_TEXT.replace("${post}", post));
		
		//Delete data
		goToForumHome();
		click(By.linkText(category));
		mngCat.deleteCategoryInForum(category, true);
		
	}
	
	/**Quote a post
	 * CaseID 93514
	 */
	@Test
	public void test03_QuotePost() {
		String category = "test03_Category_93514";
		String forum = "test03_forum_93514";
		String topic = "test03_topic_93514";
		String post = "test03_post_93514";
		String titleQuote = "title Quote";
		String contentQuote = "Content Quote";
		info("Quote a post");
		
		//Add category, forum, topic
		mngTopic.addCategoryForumTopic(category, forum, topic, topic);
		click(By.linkText(topic));
		mngPost.postReply(post, post, "", "");
		
		//Add private post
		mngPost.quotePost(post, titleQuote, contentQuote);
		
		//Delete data
		goToForumHome();
		click(By.linkText(category));
		mngCat.deleteCategoryInForum(category, true);
		
	}
	
	public void goToTopic(String user, String category, String topic){
		acc.signOut();
		acc.signIn(user, DATA_PASS);
		goToForums();
		
		click(By.linkText(category));
		click(By.linkText(topic));
		waitForAndGetElement(mngPost.ELEMENT_POST_REPLY_BUTTON);
	}
}
