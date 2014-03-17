package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;
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
 * @author Thuntn
 * @date: 19 Sep 2013
 */
public class Forum_Forum_Bookmark extends ForumBase{

	ManageAccount magAc;
	ForumManageCategory mngCat;
	ForumManageForum mngFru;
	ForumManageTopic mngTopic;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		magAc = new ManageAccount(driver);
		mngCat = new ForumManageCategory(driver);
		mngFru = new ForumManageForum(driver);
		mngTopic = new ForumManageTopic(driver);

		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Bookmark a category
	 * CaseID 71112
	 */
	@Test
	public void test01_BookmarkCategory() {
		String category = "Category_71112";
		String[] permission = {};
		
		info("Bookmark a category");
		mngCat.addNewCategoryInForum(category, "1", 0, permission, category, 0, permission);
		
		//Add bookmark for a category
		goToForumHome();
		addBookmarksItem(category);

		//Delete data
		click(By.linkText(category));
		mngCat.deleteCategoryInForum(category);

	}

	/** Bookmark a forum
	 * CaseID 71114
	 */
	@Test
	public void test02_BookmarkForum() {
		String category = "Category_71114";
		String forum = "Forum_71114";
		info("Bookmark a forum");
		mngFru.addCategoryForum(category, forum);

		//Add bookmark for a forum
		goToForumHome();
		addBookmarksItem(forum);
		
		//Delete data
		click(By.linkText(category));
		mngCat.deleteCategoryInForum(category);

	}
	/** Bookmark a topic
	 * CaseID 71115
	 */
	@Test
	public void test03_BookmarkTopic() {
		String category = "Category_71115";
		String forum = "Forum_71115";
		String topic = "Topic_71115";

		info("Bookmark a topic");
		mngTopic.addCategoryForumTopic(category, forum, topic, topic);
		
		//Add bookmark for a topic
		goToForumHome();
		addBookmarksItem(topic);

		//Delete data
		click(By.linkText(category));
		mngCat.deleteCategoryInForum(category);

	}
}
