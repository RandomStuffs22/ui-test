package org.exoplatform.selenium.platform.forum.functional.forum.category;



import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.openqa.selenium.By;
import org.testng.annotations.*;



/**
 * @author khanhnt
 * Done 3/7 Test Cases
 *
 */
public class Forum_Forum_Category_Edit extends ForumBase{

	ManageAccount acc;
	ForumManageCategory fmCat;

	@BeforeMethod
	public void setUpBeforeTest() {
		initSeleniumTest();
		driver.get(baseUrl);
		fmCat = new ForumManageCategory(driver);
		acc = new ManageAccount(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		button = new Button(driver);
	}

	@AfterMethod
	public void afterTest() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/*
	 * Case ID:73007.
	 * Test Case Name: Edit Category in case invalid user entry for Moderator field.
	 * Created by khanhnt at 2013/11/21 17:20:14
	 *//*
	@Test
	public  void test01_EditCategoryInCaseInvalidUserEntryForModeratorField() {
 	}*/



	/**
	 * Case ID:72872.
	 * Test Case Name: Edit Category in case invalid user entry for Restricted audience field.
	 * Created by khanhnt at 2013/11/21 17:20:14
	 */
	@Test
	public  void test02_EditCategoryInCaseInvalidUserEntryForRestrictedAudienceField() {
		info("Test 2: Edit Category in case invalid user entry for Restricted audience field"); 

		String catName = "test 02 Edit Category in case invalid "
				+ "user entry for Restricted audience field";
		String order = "0";
		int chooseRestricted = 0;
		String[] restricted = { "" };
		String description = "Description";
		int setPermission = 0;
		String[] userGroup = null;
		boolean permission = false;

		goToForums();
		fmCat.addNewCategoryInForum(catName, order, chooseRestricted,
				restricted, description, setPermission, userGroup, permission);

		fmCat.goToForumHome();
		waitForAndGetElement(By.linkText(catName)).click();

		String[] invalidRestricted = {"invalid"}; 
		fmCat.editCategoryInForum(catName, order, 1, invalidRestricted,
				null, 0, null);


		waitForMessage(fmCat.MESSAGE_RESTRICTED_AUDIENCE_INVALID+invalidRestricted[0]);
		click(ELEMENT_OK_INFOR_POPUP);

		button.cancel();

		fmCat.deleteCategoryInForum(catName);
	}



	/**
	 * Case ID:72922.
	 * Test Case Name: Edit Category in case valid data entry for Restricted audience field.
	 * Created by khanhnt at 2013/11/21 17:20:14
	 */
	@Test
	public  void test03_EditCategoryInCaseValidDataEntryForRestrictedAudienceField() {
		info("Test 3: Edit Category in case valid data entry for Restricted audience field"); 

		String catName = "Test 3: Edit Category in case valid data entry for Restricted audience field";
		String order = "0";
		int chooseRestricted = 0;
		String[] restricted = { "" };
		String description = "Description";
		int setPermission = 0;
		String[] userGroup = null;
		boolean permission = false;

		goToForums();
		fmCat.addNewCategoryInForum(catName, order, chooseRestricted,
				restricted, description, setPermission, userGroup, permission);

		fmCat.goToForumHome();
		waitForAndGetElement(By.linkText(catName)).click();

		String[] validRestricted = { "john,author:/developers,/organization/management" };
		fmCat.editCategoryInForum(catName, order, 1, validRestricted,
				null, 0, null);


		// determine if john who is a member of development group can view the
		// new category
		goToForumHome();
		waitForAndGetElement(By.linkText(catName));

		// determine if mary who is not a of development group can view the new
		// category or not
		acc.signOut();
		acc.signIn(DATA_USER_MARY, DATA_PASS);
		goToForums();
		fmCat.checkRightOfViewCategory(DATA_USER_MARY, DATA_PASS, catName, description, false);

		acc.signOut();
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
		waitForAndGetElement(By.linkText(catName)).click();

		fmCat.deleteCategoryInForum(catName);
	}



	/*
	 * Case ID:73042.
	 * Test Case Name: Edit Category in case valid user entry for Moderator field.
	 * Created by khanhnt at 2013/11/21 17:20:14
	 */
	/*@Test
	public  void test04_EditCategoryInCaseValidUserEntryForModeratorField() {
		info("Test 4: Edit Category in case valid user entry for Moderator field"); 

 	}*/



	/**
	 * Case ID:73077.
	 * Test Case Name: Edit category which deleted by another user.
	 * Bug ID : FORUM-310
	 * Created by khanhnt at 2013/11/21 17:20:14
	 */
	@Test(groups={"error"})
	public  void test05_EditCategoryWhichDeletedByAnotherUser() {
		info("Test 5: Edit category which deleted by another user"); 

		String catName = "Test 5: Edit Category in case valid data entry for Restricted audience field";
		String order = "0";
		int chooseRestricted = 0;
		String[] restricted = { "" };
		String description = "Description";
		int setPermission = 0;
		String[] userGroup = null;
		boolean permission = false;

		goToForums();
		fmCat.addNewCategoryInForum(catName, order, chooseRestricted,
				restricted, description, setPermission, userGroup, permission);

		fmCat.loginInNewWindowToDeleteCategory("root","gtngtn",catName);

		fmCat.goToEditCategoryInForum(false);
		waitForMessage(fmCat.MESSAGE_CATEGORY_NOT_EXISTE);
		click(ELEMENT_OK_INFOR_POPUP);		
	}



	/**
	 * Case ID:72967.
	 * Test Case Name: Edit Category with blank Moderator.
	 * Created by khanhnt at 2013/11/21 17:20:14
	 */
	/*@Test
	public  void test06_EditCategoryWithBlankModerator() {
		info("Test 6: Edit Category with blank Moderator"); 

 	}*/



	/**
	 * Case ID:72817.
	 * Test Case Name: Edit Category with blank Restricted audience.
	 * Created by khanhnt at 2013/11/21 17:20:14
	 */
	/*@Test
	public  void test07_EditCategoryWithBlankRestrictedAudience() {
		info("Test 7: Edit Category with blank Restricted audience"); 

 	}*/



}