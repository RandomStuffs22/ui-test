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
 * @author lientm
 * @date 30 Aug 2013
 */
public class Forum_Forum_Category extends ForumBase {

	ManageAccount magAc;
	ForumManageCategory magCat;
	ForumManageForum magForum;
	ForumManageTopic magTopic;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		magAc = new ManageAccount(driver);
		magCat = new ForumManageCategory(driver, this.plfVersion);
		magForum = new ForumManageForum(driver, this.plfVersion);
		magTopic = new ForumManageTopic(driver);
		
		magAc.signIn("john", DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 71117 + 71118 + 71119 -> Add, edit and delete category
	 * 
	 */
	@Test
	public void test01_AddEditDeleteCategory(){
		String catName = "CategoryForum_01";
		String[] restricted = {"demo"};
		String description = "Add new category in forum";
		
		String catEdit = "ForumCategory_01Edit";
		String[] restrictedUpdate = {"Platform/Content Management"};
		String descriptionEdit = "Add new category in forum update";
		String[] userGroup = {"Development", "*", "developers"};
		
		magCat.addNewCategoryInForum(catName, "1", 2, restricted, description, 0, null);
		magCat.editCategoryInForum(catEdit, "2", 3, restrictedUpdate, descriptionEdit, 4, userGroup, true, true, true, true);
		magCat.deleteCategoryInForum(catEdit);
	}
	
	/**CaseId: 71120 -> Export, import category
	 * 
	 */
	@Test
	public void test02_ExportImportCategory(){
		String catName = "Category71120";
		String[] restricted = {"demo"};
		String description = "Add new category in forum";
		String forumName = "Forum02";
		String fileName = "Category71120";
		
		magCat.addNewCategoryInForum(catName, "1", 2, restricted, description, 0, null);
		magForum.quickAddForum(forumName);
		magCat.exportCategoryInForum(fileName, false, catName);
		Utils.pause(3000);
		assert checkFileExisted("TestOutput/"+fileName + ".zip");
		
		click(By.linkText(catName));
		magCat.deleteCategoryInForum(catName);
		info("Cut/paste file from TestOutput folder to TestData folder");
		cutPasteFileFromOutputToTestData(fileName + ".zip");
		
		magCat.importCategoryInForum(fileName + ".zip");
		deleteFile(fileName + ".zip");
		click(By.linkText(catName));
		waitForAndGetElement(By.linkText(forumName));
		magCat.deleteCategoryInForum(catName);
	}
	
	/**CaseId: 71121 -> Export, import forums in category
	 * 
	 */
	@Test
	public void test03_ExportImportForum(){
		String catName = "Category71121";
		String[] restricted = {"demo"};
		String description = "Add new category in forum";
		String forumName1 = "Forum03_1";
		String forumName2 = "Forum03_2";
		String fileName = "Category71121";
		
		magCat.addNewCategoryInForum(catName, "1", 2, restricted, description, 0, null);
		magForum.quickAddForum(forumName1);
		click(By.linkText(catName));
		magForum.quickAddForum(forumName2);
		click(By.linkText(catName));
		
		magCat.exportForumsOfCategory(fileName);
		Utils.pause(5000);
		assert checkFileExisted("TestOutput/"+ fileName + ".zip");
		
		info("Delete forums");
		click(By.linkText(forumName1));
		magForum.deleteForum(forumName1);
		click(By.linkText(forumName2));
		magForum.deleteForum(forumName2);
		
		info("Cut/paste file from TestOutput folder to TestData folder");
		cutPasteFileFromOutputToTestData(fileName + ".zip");
		
		magCat.importForums2Category(fileName + ".zip");
		deleteFile(fileName + ".zip");
		waitForAndGetElement(By.linkText(forumName1));
		waitForAndGetElement(By.linkText(forumName2));

		magCat.deleteCategoryInForum(catName);
	}
	
	/**CaseId: 68913 -> Watch/Unwatch category
	 * 
	 */
	@Test (groups = {"email"})
	public void test04_WatchUnwatchCategory(){
		String catName = "Category 68913";
		String description = "Add new category in forum";
		String forumName = "Forum 68913";
		String topic = "Topic 68913";
		String message = "New topic 68913";
		By mail = By.xpath("//*[text()='[" + catName + "][" + forumName + "] " + topic + "']");
		
		magCat.addNewCategoryInForum(catName, "1", 0, null, description, 0, null);
		watchItem(true);
		settingMailForUser();

		magForum.quickAddForum(forumName);
		magTopic.quickStartTopic(topic, message);
		
		String handlesBefore = driver.getWindowHandle();
		goToMail(EMAIL_ADDRESS1,EMAIL_PASS);
		checkAndDeleteMail(mail, REGISTER_MAIL_CONTENT);
		
		driver.switchTo().window(handlesBefore);
		if(plfVersion =="4.1"){
		click(magForum.ELEMENT_CATEGORY_BREAD41.replace("${category}", catName));
		}
		else {
			click(magForum.ELEMENT_CATEGORY_BREAD.replace("${category}", catName));
		}
		Utils.pause(1000);
		watchItem(false);
		magCat.deleteCategoryInForum(catName);
	}
}
