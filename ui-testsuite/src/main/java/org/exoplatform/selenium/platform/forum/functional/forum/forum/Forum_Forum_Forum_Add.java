package org.exoplatform.selenium.platform.forum.functional.forum.forum;



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
 * @author khanhnt
 *
 */
public class Forum_Forum_Forum_Add extends ForumBase {


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
		fmTopic = new ForumManageTopic(driver);
		fmPost = new ForumManagePost(driver);
		acc = new ManageAccount(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		button = new Button(driver);
		fmForum = new ForumManageForum(driver);
	}

	@AfterMethod
	public void afterTest() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	* Case ID:73080.
	* Test Case Name: Add new forum when don't select auto fill email moderators option.
	* Created by khanhnt at 2013/12/09 14:21:54
	*/
	@Test
	public  void test01_AddNewForumWhenDontSelectAutoFillEmailModeratorsOption() {
		info("Test 1: Add new forum when dont select auto fill email moderators option"); 
		String catName = "Test 1 Add new forum when dont select auto fill email moderators option";
		String[] fmName={"Test 1 new forum",null,null,null,null};
		goToForums();		
		fmCat.addNewCategoryInForum(catName, "1", 0,null, catName, 0,null);
		fmForum.addForum(catName, fmName, false,EMAIL_ADDRESS1 ,EMAIL_ADDRESS2, false, 0, null);
		
		fmForum.deleteForum(fmName[0]);
		fmCat.deleteCategoryInForum(catName);
	
		

 	}



	/**
	* Case ID:73045.
	* Test Case Name: Add new forum When select auto fill email moderators option.
	* Created by khanhnt at 2013/12/09 14:21:54
	*/
	@Test
	public  void test02_AddNewForumWhenSelectAutoFillEmailModeratorsOption() {
		info("Test 2: Add new forum When select auto fill email moderators option"); 
		String catName = "Test 2 Add new forum When select auto fill email moderators option";
		String[] fmName={"Test 2 new forum",null,null,null,null};
		String[] userGroups = {"mary,demo"};
		goToForums();		
		fmCat.addNewCategoryInForum(catName, "1", 0,null, catName, 0,null);
		
		fmForum.goToAddForum();
		info("Create new forum");
		fmForum.inputDataForum(catName, fmName, true,null ,null, false, 1, userGroups);
		
		fmForum.isEmailAutofillInModerator("mary.williams@acme.exoplatform.com, jack.miller@acme.exoplatform.com");
		button.save();
		
		fmForum.deleteForum(fmName[0]);
		fmCat.deleteCategoryInForum(catName);

 	}



	/**
	* Case ID:73313.
	* Test Case Name: Add new forum with send notify mail in case invalid email address entry.
	* Created by khanhnt at 2013/12/09 14:21:54
	*/
	@Test
	public  void test03_AddNewForumWithSendNotifyMailInCaseInvalidEmailAddressEntry() {
		info("Test 3: Add new forum with send notify mail in case invalid email address entry"); 

		String catName = "Test 3 Add new forum with send notify mail in case invalid email address entry";
		String[] fmName={"Test 3 new forum",null,null,null,null};
		String invalidePostEmail = "inlvadiEmail";
		String invalidTopicEmail = "invalidEmail";
		boolean[] permission= {false,false,false,false,false};
		goToForums();		
		fmCat.addNewCategoryInForum(catName, "1", 0,null, catName, 0,null);
		
		fmForum.goToAddForum();
		info("Create new forum");
		fmForum.addForum(catName, fmName, false,invalidePostEmail ,invalidTopicEmail, false, 0,null,permission);
		
		waitForMessage("Please enter a valid email address.");
		click(ELEMENT_OK_INFOR_POPUP);
		button.cancel();
		fmCat.deleteCategoryInForum(catName);
 	}



	/**
	* Case ID:73108.
	* Test Case Name: Add new forum with send notify mail for new Post option.
	* Created by khanhnt at 2013/12/09 14:21:54
	*/
	@Test
	public  void test04_AddNewForumWithSendNotifyMailForNewPostOption() {
		info("Test 4: Add new forum with send notify mail for new Post option"); 

		String catName = "Test 4 new Category";
		String[] fmName={"Test 4 new forum",null,null,null,null};
		String topic = "Test 4 new topic";
		String post = "Test 4 new post";
		
	
		goToForums();		
		fmCat.addNewCategoryInForum(catName, "1", 0,null, catName, 0,null);
		fmForum.addForum(catName, fmName, false,EMAIL_ADDRESS1 ,null, false, 0, null);
		
		
		fmTopic.startTopic(topic, topic, "", 0, null, true, true);
		
		click(By.linkText(topic));
		fmPost.postReply(post, post, "", "");
		
		info("Check if e-mail is sent");
		
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}",
				fmName[0]).replace("${topic}", topic)), post);
		
		switchToParentWindow();
		
		fmPost.deletePost(post);
		goToForumHome();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName, true);
 	}

	


	/**
	* Case ID:73129.
	* Test Case Name: Add new forum with send notify mail for new topic option.
	* Created by khanhnt at 2013/12/09 14:21:54
	*/
	@Test
	public  void test05_AddNewForumWithSendNotifyMailForNewTopicOption() {
		info("Test 5: Add new forum with send notify mail for new topic option"); 

		String catName = "Test 5 new Category";
		String[] fmName={"Test 5 new forum",null,null,null,null};
		String topic = "Test 5 new topic";		
		
		
		
		goToForums();		
		fmCat.addNewCategoryInForum(catName, "1", 0,null, catName, 0,null);
		fmForum.addForum(catName, fmName, false,null ,EMAIL_ADDRESS1, false, 0, null);
		
		
		fmTopic.startTopic(topic, topic, "", 0, null, true, true);
		
		info("Check if e-mail is sent");
		
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}",
				fmName[0]).replace("${topic}", topic)), topic);
		
		switchToParentWindow();
		
		fmForum.deleteForum(fmName[0]);
		fmCat.deleteCategoryInForum(catName);
 	}



	/**
	 * Duplicate with TC 73313
	* Case ID:73149.
	* Test Case Name: Add new forum with send notify mail in case invalid email address entry.
	* Created by khanhnt at 2013/12/09 14:21:54
	*
	@Test
	public  void test06_AddNewForumWithSendNotifyMailInCaseInvalidEmailAddressEntry() {
		info("Test 6: Add new forum with send notify mail in case invalid email address entry"); 

 	}*/



}