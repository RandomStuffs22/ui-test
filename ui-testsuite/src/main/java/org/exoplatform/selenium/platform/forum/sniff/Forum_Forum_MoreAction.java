package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
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
 * @author thuntn
 * @date 13 Sep 2013
 */
public class Forum_Forum_MoreAction extends ForumBase{
	ManageAccount acc;
	ForumManageCategory mngCat;
	ForumManageForum mngFru;
	ForumManageTopic mngTopic;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		mngFru = new ForumManageForum(driver,this.plfVersion);
		mngCat = new ForumManageCategory(driver, this.plfVersion);
		mngTopic = new ForumManageTopic(driver);

		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	 * CaseID 93508
	 * Lock / Unlock a forum
	 */
	@Test
	public void test01_LockUnlockForum() {
		info("Lock / Unlock a forum");
		
		String cate = "test01_category_93508";
		String forum = "test01_forum_93508";
		
		//Add category, forum
		mngFru.addCategoryForum(cate, forum);
		
		//lock the forum
		mngFru.actionOnForum(1);
		goToCategory(DATA_USER_MARY, cate);
		click(By.linkText(forum));
		waitForAndGetElement(ELEMENT_START_TOPIC_DISABLE);
		click(ELEMENT_START_TOPIC_DISABLE);
		assert (waitForAndGetElement(mngTopic.ELEMENT_POPUP_START_TOPIC,5000,0) == null);
 
		//Unlock the forum
		goToCategory(DATA_USER_JOHN, cate);
		click(By.linkText(forum));
		mngFru.actionOnForum(2);
		
		//Check if normal user can start topic
		goToCategory(DATA_USER_MARY, cate);
		click(By.linkText(forum));
		click(mngTopic.ELEMENT_START_TOPIC_BUTTON);
		waitForAndGetElement(mngTopic.ELEMENT_POPUP_START_TOPIC);
		click(mngTopic.ElEMENT_CANCEL_ADD_TOPIC);
		
		//Delete data
		goToCategory(DATA_USER_JOHN, cate);
		mngCat.deleteCategoryInForum(cate, true);

	}
	
	/**
	 * CaseID 93509
	 * Open / Close a forum
	 */
	@Test
	public void test02_OpenCloseForum() {
		info("Open / Close a forum");
		
		String cate = "test02_category_93509";
		String forum = "test02_forum_93509";
		
		//Add category, forum
		mngFru.addCategoryForum(cate, forum);
		
		//Close the forum
		mngFru.actionOnForum(3);
		
		//Check if normal user cannot see the forum
		goToCategory(DATA_USER_MARY, cate);
		waitForElementNotPresent(By.linkText(forum));
		
		//Login as admin to open the forum
		goToCategory(DATA_USER_JOHN, cate);
		click(By.linkText(forum));
		mngFru.actionOnForum(4);
		
		//Check if normal user can see the forum
		goToCategory(DATA_USER_MARY, cate);
		waitForAndGetElement(By.linkText(forum));
		
		//Login as admin to delete data
		goToCategory(DATA_USER_JOHN, cate);
		mngCat.deleteCategoryInForum(cate, true);

	}
	
	/**
	 * CaseID 93510
	 * Ban IP for a forum
	 */
	@Test
	public void test03_BanIPForForum() {
		info("Ban IP for a forum");
		
		String cate = "test03_category_93510";
		String forum = "test03_forum_93510";
		String localIP = Utils.getIPOfLocal();
		
		//Add category, forum
		mngFru.addCategoryForum(cate, forum);
		
		//Ban IP for a forum
		mngFru.banIPForum(localIP);
		
		//Check if normal user with banned IP cannot access this forum
		acc.signOut();
		driver.get("http://" + localIP + ":8080/portal");
		acc.signIn(DATA_USER_MARY, DATA_PASS);
		goToForums();
		click(By.linkText(forum));
		waitForAndGetElement(ELEMENT_START_TOPIC_DISABLE);
		click(ELEMENT_START_TOPIC_DISABLE);
		waitForElementNotPresent(mngTopic.ELEMENT_POPUP_START_TOPIC);
		
		//Delete data
		goToCategory(DATA_USER_JOHN, cate);
		mngCat.deleteCategoryInForum(cate, true);

	}
	
	public void goToCategory(String user, String category){
		acc.signOut();
		acc.signIn(user, DATA_PASS);
		goToForums();
		click(By.linkText(category));
	}

}
