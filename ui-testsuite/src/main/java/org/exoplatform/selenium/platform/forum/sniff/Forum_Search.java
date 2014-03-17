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
 * @author lientm
 * @date 9 Sep 2013
 */
public class Forum_Search extends ForumBase {
	
	ManageAccount magAc;
	ForumManageCategory cat;
	ForumManageForum forum;
	ForumManageTopic magtopic;
	ForumManagePost post;
		
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		magAc = new ManageAccount(driver, this.plfVersion);
		cat = new ForumManageCategory(driver, this.plfVersion);
		forum = new ForumManageForum(driver, this.plfVersion);
		magtopic = new ForumManageTopic(driver, this.plfVersion);
		post = new ForumManagePost(driver, this.plfVersion);
		
		magAc.signIn("john", DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 68936 + 68937 -> Quick Search + Advanced Search
	 * 
	 */
	@Test
	public void test01_QuickSearchAndAdvancedSearch(){
		String catName1 = "Category Search 01";
		String description1 = "Add new category in forum";
		String forumName1 = "Forum Search 01";
		String title1 = "Topic Search 01";
		String message1 = "Create new topic quick search 01";
		String post1 = "Post Search 01";
		String messagePost1 = "Reply topic 1";
		
		String catName2 = "Category Search 02";
		String description2 = "Add new category in forum";
		String forumName2 = "Forum Search 02";
		String title2 = "Topic Search 02";
		String message2 = "Create new topic quick search 02";
		String post2 = "Post Search 02";
		String messagePost2 = "Reply topic 2";
		
		cat.addNewCategoryInForum(catName1, "1", 0, null, description1, 0, null);
		forum.quickAddForum(forumName1);
		magtopic.quickStartTopic(title1, message1);
		click(By.linkText(title1));
		post.postReply(post1, messagePost1, null, null);
		
		goToForumHome();
		cat.addNewCategoryInForum(catName2, "2", 0, null, description2, 0, null);
		forum.quickAddForum(forumName2);
		magtopic.quickStartTopic(title2, message2);
		click(By.linkText(title2));
		post.postReply(post2, messagePost2, null, null);
		
		quickSearch("Search");
		waitForAndGetElement(By.linkText(catName1));
		waitForAndGetElement(By.linkText(catName2));
		waitForAndGetElement(By.linkText(forumName1));
		waitForAndGetElement(By.linkText(forumName2));
		waitForAndGetElement(By.linkText(title1));
		waitForAndGetElement(By.linkText(title2));
		waitForAndGetElement(By.linkText(post1));
		waitForAndGetElement(By.linkText(post2));
		
		info("Advanced search in category");
		advancedSearch("Category", "Search", true);
		waitForAndGetElement(By.linkText(catName1));
		waitForAndGetElement(By.linkText(catName2));
		
		info("Advanced search in forum");
		advancedSearch("Forum", "Forum Search 01", true);
		waitForAndGetElement(By.linkText(forumName1));
		
		info("Advanced search in Topic");
		advancedSearch("Topic", "Topic", false);
		waitForAndGetElement(By.linkText(title1));
		waitForAndGetElement(By.linkText(title2));
		
		info("Advanced search in Post");
		advancedSearch("Post", "Post", true, "john");
		waitForAndGetElement(By.linkText(post1));
		waitForAndGetElement(By.linkText(post2));
		
		goToForumHome();
		click(By.linkText(catName1));
		cat.deleteCategoryInForum(catName1);
		click(By.linkText(catName2));
		cat.deleteCategoryInForum(catName2);
	}
}