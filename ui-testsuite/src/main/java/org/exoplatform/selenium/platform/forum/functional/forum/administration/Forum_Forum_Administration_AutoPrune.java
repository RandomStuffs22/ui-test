package org.exoplatform.selenium.platform.forum.functional.forum.administration;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author Thuntn
 * @date 19 Nov 2013
 */
public class Forum_Forum_Administration_AutoPrune extends ForumBase {

	ManageAccount magAc;
	ForumManageCategory cat;
	ForumManageForum mForum;
	ForumManageTopic magtopic;

	@BeforeMethod
	public void setUpBeforeTest(){
		setPreferenceRunTime();
		initSeleniumTest();
		magAc = new ManageAccount(driver);
		cat = new ForumManageCategory(driver);
		mForum = new ForumManageForum(driver);
		magtopic = new ForumManageTopic(driver);

		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseId: 72275 
	 * Setting Auto-Prune when input value is number for inactive day field
	 */
	@Test
	public void test01_SettingAutoPruneWhenInputNumberForInactiveDayField(){
		String category = "Category 72275";
		String forum = "Forum 72275";
		String topic = "Topic 72275";
		info("Setting Auto-Prune when input value is number for inactive day field");

		//Create category, forum, topic with simple data
		magtopic.addCategoryForumTopic(category, forum, topic, topic);

		//Set up prune settings
		goToPruneManagement();
		pruneSetting(category, forum, "1", "days", "", "");
		click(ELEMENT_PRUNE_CLOSE_BUTTON);

		//Delete data
		click(mForum.ELEMENT_CATEGORY_BREAD.replace("${category}", category));
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 72559
	 * Setting Auto-Prune when input some text into inactive day field
	 */
	@Test
	public void test02_SettingAutoPruneWhenInputTextForInactiveDayField(){
		String category = "Category 72436";
		String forum = "Forum 72436";
		String topic = "Topic 72436";
		info("Setting Auto-Prune when input some text into inactive day field");

		//Create category, forum, topic with simple data
		magtopic.addCategoryForumTopic(category, forum, topic, topic);

		//Set up prune settings
		goToPruneManagement();
		pruneSetting(category, forum, "prune", "days", "", "");

		waitForMessage(MSG_PRUNE_INVALID_INACTIVE_DAY);
		click(ELEMENT_PRUNE_INVALID_INACTIVE_DAY_OK);

		assert waitForAndGetElement(ELEMENT_PRUNE_ACTIVE_DAY).getAttribute("value").equals("0"):"Not reset the inactive day field";

		click(ELEMENT_PRUNE_CLOSE_BUTTON);

		//Delete data
		click(mForum.ELEMENT_CATEGORY_BREAD.replace("${category}", category));
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 72559 
	 * Setting Auto-Prune when input value is number for Run prune every  field
	 */
	@Test
	public void test03_SettingAutoPruneWhenInputNumberForRunPruneField(){
		String category = "Category 72559";
		String forum = "Forum 72559";
		String topic = "Topic 72559";
		info("Setting Auto-Prune when input value is number for Run prune every field");

		//Create category, forum, topic with simple data
		magtopic.addCategoryForumTopic(category, forum, topic, topic);

		//Set up prune settings
		goToPruneManagement();
		pruneSetting(category, forum, null, null, "1", "days");

		click(ELEMENT_PRUNE_CLOSE_BUTTON);

		//Delete data
		click(mForum.ELEMENT_CATEGORY_BREAD.replace("${category}", category));
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 72663
	 * Setting Auto-Prune when input some text for Run prune every  field
	 */
	@Test
	public void test04_SettingAutoPruneWhenInputTextForRunPruneField(){
		String category = "Category 72663";
		String forum = "Forum 72663";
		String topic = "Topic 72663";
		info("Setting Auto-Prune when input some text for Run prune every  field");

		//Create category, forum, topic with simple data
		magtopic.addCategoryForumTopic(category, forum, topic, topic);

		//Set up prune settings
		goToPruneManagement();
		pruneSetting(category, forum, null, null, "prune", "days");

		waitForMessage(MSG_PRUNE_INVALID_JOB_DAY);
		click(ELEMENT_PRUNE_INVALID_RUN_JOB_OK);
		assert waitForAndGetElement(ELEMENT_PRUNE_JOB_DAY).getAttribute("value").equals("0"):"Not reset the run prune field";
		click(ELEMENT_PRUNE_CLOSE_BUTTON);

		//Delete data
		click(mForum.ELEMENT_CATEGORY_BREAD.replace("${category}", category));
		cat.deleteCategoryInForum(category);
	}
	
	/**CaseId: 72867
	 * Run Prune in case Prune setting have not been defined for forum
	 */
	@Test
	public void test05_RunPruneInCasePruneSettingHaveNotBeenDefinedForForum(){
		String category = "Category 72867";
		String forum = "Forum 72867";
		String topic = "Topic 72867";
		info("Run Prune in case Prune setting have not been defined for forum");

		//Create category, forum, topic with simple data
		magtopic.addCategoryForumTopic(category, forum, topic, topic);

		//Set up prune settings
		goToPruneManagement();

		click(ELEMENT_PRUNE_RUN.replace("${category}", category).replace("{$forum}", forum));
		waitForMessage(MSG_PRUNE_NOT_CONFIG);
		click(ELEMENT_PRUNE_NOT_CONFIG_OK);
		click(ELEMENT_PRUNE_CLOSE_BUTTON);

		//Delete data
		click(mForum.ELEMENT_CATEGORY_BREAD.replace("${category}", category));
		cat.deleteCategoryInForum(category);
	}
}