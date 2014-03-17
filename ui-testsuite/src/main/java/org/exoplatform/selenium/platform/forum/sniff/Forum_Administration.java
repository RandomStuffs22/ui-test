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
 * @author lientm
 * @date 6 Sep 2013
 */
public class Forum_Administration extends ForumBase {
	
	ManageAccount magAc;
	ForumManageCategory cat;
	ForumManageForum forum;
	ForumManageTopic magtopic;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		setPreferenceRunTime();
		initSeleniumTest();
		magAc = new ManageAccount(driver, this.plfVersion);
		cat = new ForumManageCategory(driver, this.plfVersion);
		forum = new ForumManageForum(driver, this.plfVersion);
		magtopic = new ForumManageTopic(driver, this.plfVersion);
		
		magAc.signIn("john", DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 70926 + 70932 + 70936 -> Add, edit, delete bbCode
	 * 
	 */
	@Test
	public void test01_AddEditDeleteBBCode(){
		String tag = "TAGMOT";
		String replace = "<TAGMOT>{replace}</TAGMOT>";
		String description = "TAGMOT";
		String example = "<TAGMOT>tag01</TAGMOT>";
		
		String tagnew = "TAGMOTUPDATE";
		String replaceUpdate = "<TAGMOTUPDATE>{replace}</TAGMOTUPDATE>";
		String descriptionUpdate = "TAGMOTUPDATE";
		String exampleUpdate = "<TAGMOTUPDATE=option>tag01update</TAGMOTUPDATE>";
		
		info("Add edit delete BBCode");
		goToBBCodeManagement();
		addBBCode(tag, replace, description, example, false);
		editBBcode(tag, tagnew, replaceUpdate, descriptionUpdate, exampleUpdate, true);
		deleteBBcode(tagnew);
		click(ELEMENT_BBCODE_CLOSE_BUTTON);
		waitForElementNotPresent(ELEMENT_BBCODE_CLOSE_BUTTON);
	}
	
	/**CaseId: 68917 -> Ban IP
	 * 
	 */
	@Test
	public void test02_BanIP(){
		String catName = "CategoryBanIP_02";
		String description = "Add new category in forum";
		String forumName = "ForumBanIP_02";
		String topic = "TopicBanIP_02";
		String message = "New topic 02";
		
		info("Add new category, forum, topic with user admin");
		cat.addNewCategoryInForum(catName, "1", 0, null, description, 0, null);
		forum.quickAddForum(forumName);
		magtopic.quickStartTopic(topic, message);
		
		String ip = Utils.getIPOfLocal();
		
		info("Set banIP is localhost");
		setBanIp(ip);
		magAc.signOut();
		
		info("Check banIp with user demo");
		driver.get("http://" + ip + ":8080/portal");
		magAc.signIn("demo", DATA_PASS);
		goToForums();
		click(By.linkText(forumName));
		waitForAndGetElement(ELEMENT_START_TOPIC_DISABLE);
		waitForTextPresent(MSG_BLOCK_CREATE_TOPIC);
		waitForTextPresent(MSG_BLOCK_POST);
		waitForTextPresent(MSG_BLOCK_POST_ATTACHMENT);
		waitForTextPresent(MSG_BLOCK_EDIT_YOUR_POST);
		
		click(By.linkText(topic));
		waitForAndGetElement(ELEMENT_POST_DISABLE);
		waitForTextPresent(MSG_BLOCK_CREATE_TOPIC);
		waitForTextPresent(MSG_BLOCK_POST);
		waitForTextPresent(MSG_BLOCK_POST_ATTACHMENT);
		waitForTextPresent(MSG_BLOCK_EDIT_YOUR_POST);
		magAc.signOut();
		
		driver.get(baseUrl);
		magAc.signIn("john", DATA_PASS);
		goToForums();
		deleteBanIp(ip);
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName);
	}
}