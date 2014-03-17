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
 * Done 1/1 Test Cases
 *
 */
public class Forum_Forum_Category_Delete extends ForumBase{


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

	/**
	* Case ID:72281.
	* Test Case Name: Delete Category.
	* Created by khanhnt at 2013/11/20 17:40:11
	*/
	@Test
	public  void test01_DeleteCategory() {
		info("Test 1: Delete Category"); 

		String catName = "test 01 Delete Category";
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
		
		acc.signOut();
		acc.signIn(DATA_USER_MARY, DATA_PASS);
				goToForums();
		fmCat.checkUserCanEditDeleteACategory(catName,false);
		
		acc.signOut();
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
				goToForums();
		fmCat.checkUserCanEditDeleteACategory(catName,true);
		
		goToForumHome();
		waitForAndGetElement(By.linkText(catName)).click();
		fmCat.deleteCategoryInForum(catName);
		
 	}



}