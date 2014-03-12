package org.exoplatform.selenium.platform.forum.functional.forum.topic;

import static org.exoplatform.selenium.TestLogger.info;

import java.awt.event.KeyEvent;

import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.*;

/**
+ * 
+ * @author havtt
+ * @date 22-Jan-2014
+ *
+ */

public class Forum_Forum_Topic_Tag extends ForumBase {

  ManageAccount acc;
  NavigationToolbar navTool;
  ForumManageCategory fmCat;
  ForumManageForum fmForum;
  ForumManageTopic fmTopic;
  ManageAlert alert;

  @BeforeMethod
  public void setUpBeforeTest() {
    initSeleniumTest();
    driver.get(baseUrl);
    fmCat = new ForumManageCategory(driver);
    fmForum = new ForumManageForum(driver);
    fmTopic = new ForumManageTopic(driver);
    alert = new ManageAlert(driver);
    acc = new ManageAccount(driver);
    navTool = new NavigationToolbar(driver);
    acc.signIn(DATA_USER1, DATA_PASS);
  }

  @AfterMethod
  public void afterTest() {
    driver.manage().deleteAllCookies();
    driver.quit();
  }
  
  /**
   * Case ID: 72350 - 73304
   * Tag for topic in case valid data entry
   */
  @Test
  public void test01_AddTagWithValidData() {
	    String titleCat = "Category72350";
		String titleForum = "Forum72350";
		String titleTop = "Topic72350";
		String tag = "tag72350";

		info("Go to forum");
		goToForums();
		
		info("Create category, forum, topic");
		fmTopic.addCategoryForumTopic(titleCat, titleForum, titleTop, titleTop);
		
		info("Add tag for topic");
		click(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		fmTopic.addTagForTopic(tag);

		info("Restore data");
		click(By.linkText(titleCat));
		fmCat.deleteCategoryInForum(titleCat, true); 
  }
  
  /**
   * Case ID: 72499
   * Add new tag in case pressing Enter key to submit
   */
  @Test
  public void test02_AddTagByPressingEnter() {
	    String titleCat = "Category72499";
		String titleForum = "Forum72499";
		String titleTop = "Topic72499";
		String tagName = "tag72499";

		info("Go to forum");
		goToForums();
		
		info("Create category, forum, topic");
		fmTopic.addCategoryForumTopic(titleCat, titleForum, titleTop, titleTop);
		
		info("Add tag for topic by pressing Enter");
		click(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		fmTopic.goToAddTagForTopic();
		type(fmTopic.ELEMENT_ADD_TAG, tagName, true);
		//Press Enter key
		Utils.javaSimulateKeyPress(KeyEvent.VK_ENTER);
		//Verify tag added
		waitForAndGetElement(By.linkText(tagName));

		info("Restore data");
		click(By.linkText(titleCat));
		fmCat.deleteCategoryInForum(titleCat, true); 
  }
  
  /**
   * Case ID: 72609
   * Add blank name Tag for topic
   */
  @Test
  public void test03_AddTagWithBlankName() {
	    String titleCat = "Category72609";
		String titleForum = "Forum72609";
		String titleTop = "Topic72609";
		String tagName = "";

		info("Go to forum");
		goToForums();
		
		info("Create category, forum, topic");
		fmTopic.addCategoryForumTopic(titleCat, titleForum, titleTop, titleTop);
		
		info("Add tag for topic with blank name");
		click(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		fmTopic.goToAddTagForTopic();
		type(fmTopic.ELEMENT_ADD_TAG, tagName, true);
		click(fmTopic.ELEMENT_ADD_TAG_BUTTON);
		//Verify warning msg
		waitForAndGetElement(fmTopic.ELEMENT_WARNING_MSG);

		info("Restore data");
		click(By.linkText(titleCat));
		fmCat.deleteCategoryInForum(titleCat, true); 
  }

  /**
   * Case ID: 72700
   * Untag from topic in Manage tag
   */
  @Test
  public void test04_DelTagFromTagManagement() {
	    String titleCat = "Category72700";
		String titleForum = "Forum72700";
		String titleTop = "Topic72700";
		String tagName = "tag72700";

		info("Go to forum");
		goToForums();
		
		info("Create category, forum, topic");
		fmTopic.addCategoryForumTopic(titleCat, titleForum, titleTop, titleTop);
		
		info("Add tag for topic");
		click(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		fmTopic.addTagForTopic(tagName);
		
		info("Remove tag from Manage Tag");
		//Can be moved to commons
		waitForAndGetElement(By.linkText(tagName));
		//Open screen to view Manage Tag
		click(By.linkText(tagName));
		Utils.pause(3000);
//		waitForAndGetElement(fmTopic.ELEMENT_TOPIC_CHECKBOX.replace("${topic}",titleTop));
		check(fmTopic.ELEMENT_TOPIC_CHECKBOX.replace("${topic}",titleTop),2);
		//choose Untag
		waitForAndGetElement(fmTopic.ELEMENT_MANAGE_TAG);
		click(fmTopic.ELEMENT_MANAGE_TAG);
		click(fmTopic.ELEMENT_UNTAG);
		waitForAndGetElement(fmTopic.MESSAGE_UNTAG);
		click(fmTopic.ELEMENT_UNTAG_CONFIRMATION_BUTTON_OK);
		waitForElementNotPresent(fmTopic.ELEMENT_MANAGE_TAG);

		info("Restore data");
		click(By.linkText(titleCat));
		fmCat.deleteCategoryInForum(titleCat, true); 
  }
  
  /**
   * Case ID: 72775
   * Untag directly from topic
   */
  @Test
  public void test05_UntagFromTopic() {
	    String titleCat = "Category72775";
		String titleForum = "Forum72775";
		String titleTop = "Topic72775";
		String[] tag = {"tag1","tag2"};

		info("Go to forum");
		goToForums();
		
		info("Create category, forum, topic");
		fmTopic.addCategoryForumTopic(titleCat, titleForum, titleTop, titleTop);
		
		info("Add tag for topic");
		click(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		fmTopic.addTagForTopic(tag[0]);
		driver.navigate().refresh();
		
		info("Untag dirrectly from topic");
		fmTopic.unTagDirectly(tag[0]);

		info("Restore data");
		click(By.linkText(titleCat));
		fmCat.deleteCategoryInForum(titleCat, true); 
  }
  
  /**
   * Case ID: 72838
   * Add multi-tag for topic at the same time
   */
  @Test
  public void test06_AddMultiTagAtTheSameTime() {
	    String titleCat = "Category72838";
		String titleForum = "Forum72838";
		String titleTop = "Topic72838";
		String tag = "tag1 tag2 tag3";

		info("Go to forum");
		goToForums();
		
		info("Create category, forum, topic");
		fmTopic.addCategoryForumTopic(titleCat, titleForum, titleTop, titleTop);
		
		info("Add tag for topic");
		click(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		fmTopic.addTagForTopic(tag);

		info("Restore data");
		click(By.linkText(titleCat));
		fmCat.deleteCategoryInForum(titleCat, true); 
  }
  
  /**
   * Case ID: 72838
   * Add suggested tag for topic by administrator
   */
  @Test
  public void test07_AddSuggestedTagByAdmin() {
	    String titleCat = "Category72838";
		String titleForum = "Forum72838";
		String titleTop1 = "Topic728381";
		String titleTop2 = "Topic728382";
		String tag = "helloworld";
		String tagsplit = "hell";
		String[] permission = {};
		
		info("Go to forum");
		goToForums();

		info("Create category, forum, topic");
		fmTopic.addCategoryForumTopic(titleCat, titleForum, titleTop1, titleTop1);
		
		info("Add tag for topic");
		click(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop1));
		fmTopic.addTagForTopic(tag);
		
		info("Create another topic");
		click(ELEMENT_FORUM_BREADCUMB.replace("${forumName}", titleForum));
		click(fmTopic.ELEMENT_START_TOPIC_BUTTON);
		fmTopic.startTopic(titleTop2, titleTop2, "", 0, permission,false, false, false);
		
		info("Add another tag for the second topic");
		click(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop2));
		fmTopic.goToAddTagForTopic();
		type(fmTopic.ELEMENT_ADD_TAG, tagsplit, true);
		
		info("Verify if suggested tag is displayed");
		waitForAndGetElement(fmTopic.ELEMENT_TAG_SUGGESTED.replace("${tagName}",tag));
		click(fmTopic.ELEMENT_TAG_SUGGESTED.replace("${tagName}",tag));
		click(fmTopic.ELEMENT_ADD_TAG_BUTTON);
		//Verify if suggested tag is added or not
		waitForAndGetElement(By.linkText(tag));
		
		info("Restore data");
		click(By.linkText(titleCat));
		fmCat.deleteCategoryInForum(titleCat, true); 
  }
  
  /**
   * Case ID: 72937 - 72981
   * Add suggested tag for topic made by other user
   */
  @Test
  public void test08_AddSuggestedTagMadeByOtherUser() {
	    String titleCat = "Category72937";
		String titleForum = "Forum72937";
		String[] addForum = {titleForum, "1",null,null,titleForum};
		String titleTop1 = "Topic729371";
		String titleTop2 = "Topic729372";
		String tag = "helloworld";
		String tagsplit = "hell";
		String[] permissionCat = {};
		String[] userGroup = {DATA_USER2};

		info("Go to forum");
		goToForums();
		
		info("Create category, forum, topic");
		//Add category
		fmCat.addNewCategoryInForum(titleCat, "1", 0,permissionCat, titleCat, 0,permissionCat);
		//Add forum
		fmForum.addForum(titleCat, addForum, true, "", "", false, 1, userGroup, true);
		//Add topic
		click(fmTopic.ELEMENT_START_TOPIC_BUTTON);
		fmTopic.startTopic(titleTop1, titleTop1, "", 0, userGroup,false, false, false);
		
		info("Add tag for topic");
		click(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop1));
		fmTopic.addTagForTopic(tag);
		acc.signOut();
		
		info("Create another topic by another user");
		acc.signIn(DATA_USER2, DATA_PASS);
		goToForums();
		waitForAndGetElement(ELEMENT_FORUM_ON_HOMEPAGE.replace("${category}", titleCat).replace("${forum}", titleForum)).click();
		click(fmTopic.ELEMENT_START_TOPIC_BUTTON);
		fmTopic.startTopic(titleTop2, titleTop2, "", 0, permissionCat,false, false, false);
		
		info("Add another tag for the second topic");
		click(fmForum.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop2));
		fmTopic.goToAddTagForTopic();
		type(fmTopic.ELEMENT_ADD_TAG, tagsplit, true);
		
		info("Verify if suggested tag is displayed");
		waitForAndGetElement(fmTopic.ELEMENT_TAG_SUGGESTED.replace("${tagName}",tag));
		click(fmTopic.ELEMENT_TAG_SUGGESTED.replace("${tagName}",tag));
		click(fmTopic.ELEMENT_ADD_TAG_BUTTON);
		//Verify if suggested tag is added or not
		waitForAndGetElement(By.linkText(tag));
		
		info("Restore data");
		acc.signOut();
		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		click(By.linkText(titleCat));
		fmCat.deleteCategoryInForum(titleCat, true); 
  }
  
}
