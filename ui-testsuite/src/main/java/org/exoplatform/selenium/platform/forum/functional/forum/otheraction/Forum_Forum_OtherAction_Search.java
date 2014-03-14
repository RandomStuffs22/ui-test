package org.exoplatform.selenium.platform.forum.functional.forum.otheraction;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author lanltk
 * @date 13 Jan 2014
 */

public class Forum_Forum_OtherAction_Search extends ForumBase{
	  ManageAccount magAc;
      NavigationToolbar naviToolbar;
	  ForumManageCategory magCat;
	  ForumManageForum magForum;
	  ForumManageTopic magTopic;
	  ForumManagePost magPost;

	  @BeforeMethod
	  public void setUpBeforeTest(){
	    initSeleniumTest();
	    magAc = new ManageAccount(driver, this.plfVersion);
	    naviToolbar = new NavigationToolbar(driver, this.plfVersion);
	    pageE = new PageEditor(driver, this.plfVersion);
	    magCat = new ForumManageCategory(driver, this.plfVersion);
	    magForum = new ForumManageForum(driver, this.plfVersion);
	    magTopic = new ForumManageTopic(driver, this.plfVersion);
	    magPost = new ForumManagePost(driver, this.plfVersion);

	    magAc.signIn(DATA_USER1, DATA_PASS);
	    goToForums();

	  }

	  @AfterMethod

	  public void afterTest(){
	    driver.manage().deleteAllCookies();
	    driver.quit();
	  }
	  
	  /** Functional-Forum-Forum-OtherAction-Search
	   * Simple Search -> CaseId: 73221, 73228, 73235, 73240
	   * Advanced Search -> CaseId: 73329, 73298, 73334, 73299, 73300, 73301, 72934, 72978, 73250
	   */
	  /*
	   * CaseId 73221: Do Simple Search with Category when Scoped in case keyword matched with properties of showing category
	   * CaseId 73228: Do Simple Search with Category when Scoped in case keyword matched with properties of hiding category
	   */

	  @Test  
	  public void test01_SimpleSearchCategory_showinghiding(){
	    String catName1 = "Category 01 73221";
	    String[] restricted1 = {DATA_USER4};
	    String description1 = "Add new category Test01 73221";

	    String catName2 = "Category 02 73221";
	    String[] restricted2 = {DATA_USER4};
	    String description2 = "Add new category Test02 73221";
	    
	    String catName3 = "Category 01 73228";
	    String[] restricted3 = {DATA_USER4};
	    String description3 = "Add new category Test01 73228";
	    
	    String catName4 = "Category 02 73228";
	    String[] restricted4 = {DATA_USER4};
	    String description4 = "Add new category Test02 73228";	    
	    
	    // Create Categories
	    magCat.addNewCategoryInForum(catName1, "1", 1, restricted1, description1, 0, null);
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName2, "2", 1, restricted2, description2, 0, null);
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName3, "3", 1, restricted3, description3, 0, null);
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName4, "4", 1, restricted4, description4, 0, null);
	    
	    // Edit Forum layout for showing/hiding categories
	    goToForumHome();
	    naviToolbar.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		
		info("Setting to show/hire category");
		selectDisplayCategoryAndForum(catName1, true);
		selectDisplayCategoryAndForum(catName2, true);
		selectDisplayCategoryAndForum(catName3, false);
		selectDisplayCategoryAndForum(catName4, false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		// Login by demo user
		magAc.signOut();
		magAc.signIn(DATA_USER4,DATA_PASS);
		goToForums();
		
	    // Simple Search categories in case Categories showed
		info("Simple Search in Categories showed:");
		quickSearch(catName1);
		waitForAndGetElement(By.linkText(catName1));
		waitForElementNotPresent(By.linkText(catName2));
		waitForElementNotPresent(By.linkText(catName3));
		waitForElementNotPresent(By.linkText(catName4));
		goToForumHome();
	    
	    // Simple Search categories in case Categories hidden
		info("Simple Search in Categories hidden:");
		quickSearch(catName3);
		waitForElementNotPresent(By.linkText(catName1));
		waitForElementNotPresent(By.linkText(catName2));
		waitForElementNotPresent(By.linkText(catName3));
		waitForElementNotPresent(By.linkText(catName4));	 
		goToForumHome();

	    // Delete all categories before exit test cases
		magAc.signOut();
		magAc.signIn(DATA_USER1,DATA_PASS);
		info("Delete all categories before exit test cases:");
		
		goToForums();
		naviToolbar.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectDisplayCategoryAndForum(catName3, true);
		selectDisplayCategoryAndForum(catName4, true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
	    click(By.linkText(catName1));
	    magCat .deleteCategoryInForum(catName1);
	    click(By.linkText(catName2));
	    magCat .deleteCategoryInForum(catName2);
	    click(By.linkText(catName3));
	    magCat .deleteCategoryInForum(catName3);
	    click(By.linkText(catName4));
	    magCat .deleteCategoryInForum(catName4);
	  }
	  
	  /*
	   * CaseId 73235: Do Simple Search with Forum when Scoped in case keyword matched with properties of showing Forum
	   * CaseId 73240: Do Simple Search with Forum when Scoped in case keyword matched with properties of hiding Forum
	   */
	  @Test  
	  public void test02_SimpleSearchForum_showinghiding(){
	    String catName1 = "Category 01 73235";
	    String[] restricted1 = {DATA_USER4};
	    String description1 = "Add new category Test01 73235";

	    String catName2 = "Category 02 73235";
	    String[] restricted2 = {DATA_USER4};
	    String description2 = "Add new category Test02 73235";
	    
	    String catName3 = "Category 01 73240";
	    String[] restricted3 = {DATA_USER4};
	    String description3 = "Add new category Test01 73240";
	    
	    String catName4 = "Category 02 73240";
	    String[] restricted4 = {DATA_USER4};
	    String description4 = "Add new category Test02 73240";	  
	    
	    String forumName1 = "Forum 01 73235";
	    String forumName2 = "Forum 02 73235";
	    String forumName3 = "Forum 03 73235";
	    String forumName4 = "Forum 04 73235";
	    String forumName5 = "Forum 05 73240";
	    
	    // Create Categories
	    magCat.addNewCategoryInForum(catName1, "1", 1, restricted1, description1, 0, null);
	    magForum .quickAddForum(forumName1);
	    click(By.linkText(catName1));
	    magForum .quickAddForum(forumName2);
	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName2, "2", 1, restricted2, description2, 0, null);
	    magForum .quickAddForum(forumName3);
	    click(By.linkText(catName2));
	    magForum .quickAddForum(forumName4);
	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName3, "3", 1, restricted3, description3, 0, null);
	    magForum .quickAddForum(forumName5);
	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName4, "4", 1, restricted4, description4, 0, null);
	    
	    // Edit Forum layout for showing/hiding categories
	    goToForumHome();
	    naviToolbar.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		
		info("Setting to show/hide category");
		selectDisplayCategoryAndForum(catName1, true);
		selectDisplayCategoryAndForum(catName1 + "/" + forumName1, true);
		selectDisplayCategoryAndForum(catName1 + "/" + forumName2, true);
		
		selectDisplayCategoryAndForum(catName2, true);
		selectDisplayCategoryAndForum(catName2 + "/" +forumName3, false);
		selectDisplayCategoryAndForum(catName2 + "/" +forumName4, true);
		
		selectDisplayCategoryAndForum(catName3, false);
		selectDisplayCategoryAndForum(catName3 + "/" + forumName5, false);
		selectDisplayCategoryAndForum(catName4, false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		// Login by demo user
		magAc.signOut();
		magAc.signIn(DATA_USER4,DATA_PASS);
		goToForums();
		
	    // Simple Search categories in case Categories showed
		info("Simple Search in Forums showed:");
		quickSearch(forumName1);
		waitForAndGetElement(By.linkText(forumName1));
		waitForElementNotPresent(By.linkText(forumName2));
		waitForElementNotPresent(By.linkText(forumName3));
		waitForElementNotPresent(By.linkText(forumName4));
		waitForElementNotPresent(By.linkText(forumName5));
		goToForumHome();
	    
	    // Simple Search categories in case Categories hidden
		info("Simple Search in Forums hidden:");
		quickSearch(forumName5);
		waitForElementNotPresent(By.linkText(forumName1));
		waitForElementNotPresent(By.linkText(forumName2));
		waitForElementNotPresent(By.linkText(forumName3));
		waitForElementNotPresent(By.linkText(forumName4));
		waitForElementNotPresent(By.linkText(forumName5));	 
		goToForumHome();

	    // Delete all categories before exit test cases
		magAc.signOut();
		magAc.signIn(DATA_USER1,DATA_PASS);
		info("Delete all categories and forums before exit test cases:");
		
		goToForums();
		naviToolbar.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectDisplayCategoryAndForum(catName2 + "/" + forumName3, true);
		selectDisplayCategoryAndForum(catName3, true);
		selectDisplayCategoryAndForum(catName3 + "/" + forumName5, true);
		selectDisplayCategoryAndForum(catName4, true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
	    click(By.linkText(catName1));
	    magCat .deleteCategoryInForum(catName1);
	    click(By.linkText(catName2));
	    magCat .deleteCategoryInForum(catName2);
	    click(By.linkText(catName3));
	    magCat .deleteCategoryInForum(catName3);
	    click(By.linkText(catName4));
	    magCat .deleteCategoryInForum(catName4);
	  }
	  
	  /*
	   * CaseId 73329: Category advanced search when Scoped in case keyword matched with properties of showing category
	   * CaseId 73298: Category advanced search when Scoped in case keyword matched with properties of hiding category
	   */
	  @Test  
	  public void test03_AdvancedSearchCategory_showinghiding(){
	    String catName1 = "Category 01 73329";
	    String[] restricted1 = {DATA_USER4};
	    String description1 = "Add new category Test01 73329";

	    String catName2 = "Category 02 73329";
	    String[] restricted2 = {DATA_USER4};
	    String description2 = "Add new category Test02 73329";
	    
	    String catName3 = "Category 01 73298";
	    String[] restricted3 = {DATA_USER4};
	    String description3 = "Add new category Test01 73298";
	    
	    String catName4 = "Category 02 73298";
	    String[] restricted4 = {DATA_USER4};
	    String description4 = "Add new category Test02 73298";	    
	    
	    // Create Categories
	    magCat.addNewCategoryInForum(catName1, "1", 1, restricted1, description1, 0, null);
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName2, "2", 1, restricted2, description2, 0, null);
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName3, "3", 1, restricted3, description3, 0, null);
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName4, "4", 1, restricted4, description4, 0, null);
	    
	    // Edit Forum layout for showing/hiding categories
	    goToForumHome();
	    naviToolbar.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		
		info("Setting to show/hire category");
		selectDisplayCategoryAndForum(catName1, true);
		selectDisplayCategoryAndForum(catName2, true);
		selectDisplayCategoryAndForum(catName3, false);
		selectDisplayCategoryAndForum(catName4, false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		// Login by demo user
		magAc.signOut();
		magAc.signIn(DATA_USER4,DATA_PASS);
		goToForums();
		
	    // Advanced Search categories in case Categories showed
		info("Advanced Search in Categories showed:");
		advancedSearch("Category","new category",true);
		waitForAndGetElement(By.linkText(catName1));
		waitForAndGetElement(By.linkText(catName2));
		waitForElementNotPresent(By.linkText(catName3));
		waitForElementNotPresent(By.linkText(catName4));
		goToForumHome();
	    
	    // Advanced Search categories in case Categories hidden
		info("Advanced Search in Categories hidden:");
		advancedSearch("Category", catName3, true);
		waitForElementNotPresent(By.linkText(catName1));
		waitForElementNotPresent(By.linkText(catName2));
		waitForElementNotPresent(By.linkText(catName3));
		waitForElementNotPresent(By.linkText(catName4));	 
		goToForumHome();

	    // Delete all categories before exit test cases
		magAc.signOut();
		magAc.signIn(DATA_USER1,DATA_PASS);
		info("Delete all categories before exit test cases:");
		
		goToForums();
		naviToolbar.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectDisplayCategoryAndForum(catName3, true);
		selectDisplayCategoryAndForum(catName4, true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
	    click(By.linkText(catName1));
	    magCat .deleteCategoryInForum(catName1);
	    click(By.linkText(catName2));
	    magCat .deleteCategoryInForum(catName2);
	    click(By.linkText(catName3));
	    magCat .deleteCategoryInForum(catName3);
	    click(By.linkText(catName4));
	    magCat .deleteCategoryInForum(catName4);
	  }
	  
	  /*
	   * CaseId 73334: Forum advanced search when Scoped in case keyword matched with properties of showing forum
	   * CaseId 73299: Forum advanced search when Scoped in case keyword matched with properties of hiding forum
	   */
	  @Test  
	  public void test04_AdvancedSearchForum_showinghiding(){
	    String catName1 = "Category 01 73334";
	    String[] restricted1 = {DATA_USER4};
	    String description1 = "Add new category Test01 73334";

	    String catName2 = "Category 02 73334";
	    String[] restricted2 = {DATA_USER4};
	    String description2 = "Add new category Test02 73334";
	    
	    String catName3 = "Category 01 73299";
	    String[] restricted3 = {DATA_USER4};
	    String description3 = "Add new category Test01 73299";
	    
	    String catName4 = "Category 02 73299";
	    String[] restricted4 = {DATA_USER4};
	    String description4 = "Add new category Test02 73299";	  
	    
	    String forumName1 = "Forum 01 73334";
	    String forumName2 = "Forum 02 73334";
	    String forumName3 = "Forum 03 73334";
	    String forumName4 = "Forum 04 73334";
	    String forumName5 = "Forum 05 73299";
	    
	    // Create Categories
	    magCat.addNewCategoryInForum(catName1, "1", 1, restricted1, description1, 0, null);
	    magForum .quickAddForum(forumName1);
	    click(By.linkText(catName1));
	    magForum .quickAddForum(forumName2);
	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName2, "2", 1, restricted2, description2, 0, null);
	    magForum .quickAddForum(forumName3);
	    click(By.linkText(catName2));
	    magForum .quickAddForum(forumName4);
	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName3, "3", 1, restricted3, description3, 0, null);
	    magForum .quickAddForum(forumName5);
	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName4, "4", 1, restricted4, description4, 0, null);
	    
	    // Edit Forum layout for showing/hiding categories
	    goToForumHome();
	    naviToolbar.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		
		info("Setting to show/hire category");
		selectDisplayCategoryAndForum(catName1, true);
		selectDisplayCategoryAndForum(catName1 + "/" + forumName1, true);
		selectDisplayCategoryAndForum(catName1 + "/" + forumName2, true);
		
		selectDisplayCategoryAndForum(catName2, true);
		selectDisplayCategoryAndForum(catName2 + "/" + forumName3, false);
		selectDisplayCategoryAndForum(catName2 + "/" + forumName4, true);
		
		selectDisplayCategoryAndForum(catName3, false);
		selectDisplayCategoryAndForum(catName3 + "/" + forumName5, false);
		selectDisplayCategoryAndForum(catName4, false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		// Login by demo user
		magAc.signOut();
		magAc.signIn(DATA_USER4,DATA_PASS);
		goToForums();
		
	    // Advanced Search categories in case Categories showed
		info("Advanced Search in Forums showed:");
		advancedSearch("Forum","02 73334",true);
		waitForAndGetElement(By.linkText(forumName2));
		waitForElementNotPresent(By.linkText(forumName1));
		waitForElementNotPresent(By.linkText(forumName3));
		waitForElementNotPresent(By.linkText(forumName4));
		waitForElementNotPresent(By.linkText(forumName5));
		waitForElementNotPresent(By.linkText(catName1));
		waitForElementNotPresent(By.linkText(catName2));
		waitForElementNotPresent(By.linkText(catName3));
		waitForElementNotPresent(By.linkText(catName4));
		goToForumHome();
	    
	    // Advanced Search categories in case Categories hidden
		info("Advanced Search in Forums hidden:");
		advancedSearch("Forum",forumName5,true);
		waitForElementNotPresent(By.linkText(forumName5));
		waitForElementNotPresent(By.linkText(forumName1));
		waitForElementNotPresent(By.linkText(forumName3));
		waitForElementNotPresent(By.linkText(forumName4));
		waitForElementNotPresent(By.linkText(catName1));
		waitForElementNotPresent(By.linkText(catName2));
		waitForElementNotPresent(By.linkText(catName3));
		waitForElementNotPresent(By.linkText(catName4)); 
		goToForumHome();

	    // Delete all categories before exit test cases
		magAc.signOut();
		magAc.signIn(DATA_USER1,DATA_PASS);
		info("Delete all categories and forums before exit test cases:");
		
		goToForums();
		naviToolbar.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectDisplayCategoryAndForum(catName2 + "/" + forumName3, true);
		selectDisplayCategoryAndForum(catName3, true);
		selectDisplayCategoryAndForum(catName3 + "/" +forumName5, true);
		selectDisplayCategoryAndForum(catName4, true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
	    click(By.linkText(catName1));
	    magCat .deleteCategoryInForum(catName1);
	    click(By.linkText(catName2));
	    magCat .deleteCategoryInForum(catName2);
	    click(By.linkText(catName3));
	    magCat .deleteCategoryInForum(catName3);
	    click(By.linkText(catName4));
	    magCat .deleteCategoryInForum(catName4);
	  }
	
	  /*
	   * CaseId 73300: Do Advanced Search with name of attachment in post
	   * CaseId 73301: Do Advanced Search with content of attachment in post
	   */
	  @Test  
	  public void test05_AdvancedSearchPost_Attachment(){
	    String catName1 = "Category 01 73300";
	    String[] restricted1 = {DATA_USER4};
	    String description1 = "Add new category Test01 73300";

	    String catName2 = "Category 02 73300";
	    String[] restricted2 = {DATA_USER4};
	    String description2 = "Add new category Test02 73300";
	    
	    String catName3 = "Category 01 73301";
	    String[] restricted3 = {DATA_USER4};
	    String description3 = "Add new category Test01 73301";
	    
	    String catName4 = "Category 02 73301";
	    String[] restricted4 = {DATA_USER4};
	    String description4 = "Add new category Test02 73301";	    
	    
	    String forumName1 = "Forum 01 73300";
	    String forumName2 = "Forum 02 73300";
	    String forumName3 = "Forum 03 73301";
	    String forumName4 = "Forum 04 73301";
	    
	    String topicName1 = "Topic 01 73300";
	    String topicName2 = "Topic 02 73300";
	    String topicName3 = "Topic 03 73301";
	    String topicName4 = "Topic 04 73301";
	    
	    String post1 = "Reply for Topic 01 73300";
	    String post2 = "Reply for Topic 02 73300";
	    String post3 = "Reply for Topic 03 73301";
	    String post4 = "Reply for Topic 04 73301";
	    
	    String filename1 = "landscape08.jpg";
	    String filename2 = "Delete_multiple_files_folder_2.txt";
	    
	    // Create Categories, forums, topics, posts
	    magCat.addNewCategoryInForum(catName1, "1", 1, restricted1, description1, 0, null);
	    magForum .quickAddForum(forumName1);
	    magTopic .quickStartTopic(topicName1,"Start topic_01_73300");
	    click(By.linkText(topicName1));
	    magPost.postReply(post1,post1,null,null,filename1);
	    	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName2, "2", 1, restricted2, description2, 0, null);
	    magForum .quickAddForum(forumName2);
	    magTopic .quickStartTopic(topicName2,"Start topic_02_73300");
	    click(By.linkText(topicName2));
	    magPost.postReply(post2,post2,"","");
	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName3, "3", 1, restricted3, description3, 0, null);
	    magForum .quickAddForum(forumName3);
	    magTopic .quickStartTopic(topicName3,"Start topic_03_73301");
	    click(By.linkText(topicName3));
	    magPost.postReply(post3,post3,"","",filename2);
	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName4, "4", 1, restricted4, description4, 0, null);
	    magForum .quickAddForum(forumName4);
	    magTopic .quickStartTopic(topicName4,"Start topic_04_73301");
	    click(By.linkText(topicName4));
	    magPost.postReply(post4,post4,"","");    
		
		// Login by demo user
		magAc.signOut();
		magAc.signIn(DATA_USER4,DATA_PASS);
		goToForums();
		
	    // Advanced Search posts with name of attachment
		info("Advanced Search: Posts with name of attachment.");
		advancedSearch("Post", "landscape08", false);
		waitForAndGetElement(By.linkText(post1));
		waitForElementNotPresent(By.linkText(post2));
		waitForElementNotPresent(By.linkText(post3));
		waitForElementNotPresent(By.linkText(post4));
		goToForumHome();
	    
	    // Advanced Search posts with content o attachment
		info("Advanced Search: Posts with content of attachment.");
		advancedSearch("Post", "Text 2", true);
		waitForAndGetElement(By.linkText(post3));
		waitForElementNotPresent(By.linkText(post1));
		waitForElementNotPresent(By.linkText(post2));
		waitForElementNotPresent(By.linkText(post4));	 
		goToForumHome();

	    // Delete all categories before exit test cases
		magAc.signOut();
		magAc.signIn(DATA_USER1,DATA_PASS);
		info("Delete all categories before exit test cases:");
		
		goToForums();		
	    click(By.linkText(catName1));
	    magCat .deleteCategoryInForum(catName1);
	    click(By.linkText(catName2));
	    magCat .deleteCategoryInForum(catName2);
	    click(By.linkText(catName3));
	    magCat .deleteCategoryInForum(catName3);
	    click(By.linkText(catName4));
	    magCat .deleteCategoryInForum(catName4);
	  }

	  /*
	   * CaseId 72934: Category Advance Search with moderator option
	   * CaseId 72978: Category Advance Search by moderator when select moderator from list
	   * CaseId 73250: Forum Advance Search by moderator when select moderator from list
	   */
	  @Test  
	  public void test06_AdvancedSearchCategoryForum_Moderator(){
	    String catName1 = "Category 01 72934";
	    String[] restricted1 = {DATA_USER4};
	    String description1 = "Add new category Test01 72934";

	    String catName2 = "Category 02 72934";
	    String[] restricted2 = {DATA_USER4};
	    String description2 = "Add new category Test02 72934";
	    	    
	    String catName3 = "Category 01 72978";
	    String[] restricted3 = {DATA_USER4};
	    String description3 = "Add new category Test01 72978";
	    
	    String catName4 = "Category 02 72978";
	    String[] restricted4 = {DATA_USER4};
	    String description4 = "Add new category Test02 72978";
	    
	    String catName5 = "Category 01 73250";
	    String[] restricted5 = {DATA_USER4};
	    String description5 = "Add new category Test01 73250";
	    
	    String catName6 = "Category 02 73250";
	    String[] restricted6 = {DATA_USER4};
	    String description6 = "Add new category Test02 73250";
	    
	    String[] userGroup1 = {DATA_USER1};
	    String[] userGroup2 = {DATA_USER4};
	    String[] userGroup3 = {USER_ROOT};
	    
	    String[] forumName1 = {"Forum 01 73250","1","Open","Unlocked","Add new Forum 01 73250"};
	    String[] forumName2 = {"Forum 02 73250","2","Open","Unlocked","Add new Forum 02 73250"};
	    String[] forumName3 = {"Forum 03 73250","3","Open","Unlocked","Add new Forum 03 73250"};
	    String[] forumName4 = {"Forum 04 73250","4","Open","Unlocked","Add new Forum 04 73250"};
	    	    	    
	    // Create Categories, forums, topics, posts
	    magCat.addNewCategoryInForum(catName1, "1", 1, restricted1, description1, 1, userGroup1,true,true,true,true);
	    	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName2, "2", 1, restricted2, description2, 1, userGroup2,true,true,true,true);
	    	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName3, "3", 1, restricted3, description3, 1, userGroup3,true,true,true,true);
	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName4, "4", 1, restricted4, description4,1, userGroup1,true,true,true,true);
		
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName5, "5", 1, restricted5, description5, 1, userGroup2,true,true,true,true);
	    magForum .addForum(catName5,forumName1,true,"","",false,1,userGroup1,true,true,true,true);
	    click(By.linkText(catName5));
	    magForum .addForum(catName5,forumName2,true,"","",false,1,userGroup2,true,true,true,true);
	    
	    goToForumHome();
	    magCat.addNewCategoryInForum(catName6, "6", 1, restricted6, description6, 1, userGroup1,true,true,true,true);
	    magForum .addForum(catName6,forumName3,true,"","",false,1,userGroup2,true,true,true,true);
	    click(By.linkText(catName6));
	    magForum .addForum(catName6,forumName4,false,"","",false,1,userGroup3,true,true,true,true);

		// Login by demo user
		magAc.signOut();
		magAc.signIn(DATA_USER4,DATA_PASS);
		goToForums();
		
	    // Advanced Search categories with moderator option
		info("Advanced Search: Category with moderator.");
		advancedSearch("Category","",true,null,null,null,DATA_USER1);
		waitForAndGetElement(By.linkText(catName1));
		waitForAndGetElement(By.linkText(catName4));
		waitForAndGetElement(By.linkText(catName6));
		waitForElementNotPresent(By.linkText(catName2));
		waitForElementNotPresent(By.linkText(catName3));
		waitForElementNotPresent(By.linkText(catName5));
		goToForumHome();
	    
	    // Advanced Search categories by moderator when select moderator from list
		info("Advanced Search: Category with moderator from list.");
		advancedSearch("Category","",true,null,null,null,"root, demo");
		waitForAndGetElement(By.linkText(catName2));
		waitForAndGetElement(By.linkText(catName3));
		waitForAndGetElement(By.linkText(catName5));
		waitForElementNotPresent(By.linkText(catName1));
		waitForElementNotPresent(By.linkText(catName4));
		waitForElementNotPresent(By.linkText(catName6));
		goToForumHome();
		
	    // Delete all categories before exit test cases
		magAc.signOut();
		magAc.signIn(DATA_USER1,DATA_PASS);
		info("Delete all categories before exit test cases:");
		
		goToForums();		
	    click(By.linkText(catName1));
	    magCat .deleteCategoryInForum(catName1);
	    click(By.linkText(catName2));
	    magCat .deleteCategoryInForum(catName2);
	    click(By.linkText(catName3));
	    magCat .deleteCategoryInForum(catName3);
	    click(By.linkText(catName4));
	    magCat .deleteCategoryInForum(catName4);
	    click(By.linkText(catName5));
	    magCat .deleteCategoryInForum(catName5);
	    click(By.linkText(catName6));
	    magCat .deleteCategoryInForum(catName6);
	  }
	  
}
