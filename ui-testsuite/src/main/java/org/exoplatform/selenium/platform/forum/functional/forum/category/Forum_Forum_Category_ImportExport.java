package org.exoplatform.selenium.platform.forum.functional.forum.category;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
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
 * @date 21 Nov 2013
 */
public class Forum_Forum_Category_ImportExport extends ForumBase {
  ManageAccount magAc;
  ForumManageCategory magCat;
  ForumManageForum magForum;
  ForumManageTopic magTopic;
  ForumManagePost magPost;


  @BeforeMethod
  public void setUpBeforeTest(){
    getDriverAutoSave();
    magAc = new ManageAccount(driver);
    magCat = new ForumManageCategory(driver);
    magForum = new ForumManageForum(driver);
    magTopic = new ForumManageTopic(driver);

    magAc.signIn("john", DATA_PASS);
    goToForums();

  }

  @AfterMethod

  public void afterTest(){
    driver.manage().deleteAllCookies();
    driver.quit();
  }
  
  /**CaseId: 72669 and 72283 -> Functional-Forum-Forum-Category-Export/Import categories: with mode Export_All
   * 
   */
  @Test
  
  public void test01_ExportImportCategory_modeExportAll(){
    String catName1 = "Category_01_72669";
    String[] restricted1 = {"demo"};
    String description1 = "Add new category 01_72669";

    String catName2 = "Category_02_72669";
    String[] restricted2 = {"demo"};
    String description2 = "Add new category 02_72669";
    
    String forumName1 = "Forum_01_72669";
    String forumName2 = "Forum_02_72669";
    String forumName3 = "Forum_03_72669";
    String forumName4 = "Forum_04_72669";
    
    String topicName1 = "Topic_01_72669";
    String topicName2 = "Topic_02_72669";
    String topicName3 = "Topic_03_72669";
    String topicName4 = "Topic_04_72669";
    String topicName5 = "Topic_05_72669";
    String topicName6 = "Topic_06_72669";
   
    String fileName = "FileExportAllCategories";
    
    magCat.addNewCategoryInForum(catName1, "1", 1, restricted1, description1, 0, null);
    magForum .quickAddForum(forumName1);
    magTopic .quickStartTopic(topicName1,"Start topic_01_72669");
    magTopic .quickStartTopic(topicName2,"Start topic_02_72669");

    click(By.linkText(catName1));
    magForum .quickAddForum(forumName2);
    magTopic .quickStartTopic(topicName3,"Start topic_03_72669");
    magTopic .quickStartTopic(topicName4,"Start topic_04_72669");
  
    goToForumHome();
    magCat.addNewCategoryInForum(catName2, "2", 1, restricted2, description2, 0, null);
    magForum .quickAddForum(forumName3);
    magTopic .quickStartTopic(topicName5,"Start topic_05_72669");
    
    click(By.linkText(catName2));
    magForum .quickAddForum(forumName4);
    magTopic .quickStartTopic(topicName6,"Start topic_06_72669");

    goToForumHome();
    magCat .exportCategoryInForum(fileName, true);
    Utils.pause(3000);
    assert checkFileExisted("TestOutput/" + fileName + ".zip"); 

    /* Delete all categories, Forum and topic before running Import test case */
    click(By.linkText(catName1));
    magCat .deleteCategoryInForum(catName1);
    click(By.linkText(catName2));
    magCat .deleteCategoryInForum(catName2);

    magCat .importCategoryInForum("TestOutput/" + fileName + ".zip");
    info("Import all categories with the file exported all.");
    Utils.pause(500);
    deleteFile("TestOutput/" + fileName + ".zip");

    /* Delete all categories, Forum and topic before exit test case */
    click(By.linkText(catName1));
    magCat .deleteCategoryInForum(catName1);
    click(By.linkText(catName2));
    magCat .deleteCategoryInForum(catName2);
  }

  /**CaseId: 72565 and 72443 -> Functional-Forum-Forum-Category-Export/Import categories: with mode Only Categories
   * 
   */
  @Test
  public void test02_ExportImportCategory_modeOnlyCategories(){
	String catName1 = "Category_01_72565";
	String[] restricted1 = {"demo"};
	String description1 = "Add new category 01_72565";

	String catName2 = "Category_02_72565";
	String[] restricted2 = {"demo"};
	String description2 = "Add new category 02_72565";
	    
	String forumName1 = "Forum_01_72565";
	String forumName2 = "Forum_02_72565";
	String forumName3 = "Forum_03_72565";
	String forumName4 = "Forum_04_72565";
	    
	String topicName1 = "Topic_01_72565";
	String topicName2 = "Topic_02_72565";
	String topicName3 = "Topic_03_72565";
	String topicName4 = "Topic_04_72565";
	String topicName5 = "Topic_05_72565";
	String topicName6 = "Topic_06_72565";
	    
    String fileName1 = "FileExportCategoriesOnly";   
    
    magCat.addNewCategoryInForum(catName1, "1", 1, restricted1, description1, 0, null);
    magForum .quickAddForum(forumName1);
    magTopic .quickStartTopic(topicName1,"Start topic_01_72565");
    magTopic .quickStartTopic(topicName2,"Start topic_02_72565");

    click(By.linkText(catName1));
    magForum .quickAddForum(forumName2);
    magTopic .quickStartTopic(topicName3,"Start topic_03_72565");
    magTopic .quickStartTopic(topicName4,"Start topic_04_72565");
  
    goToForumHome();
    magCat.addNewCategoryInForum(catName2, "2", 1, restricted2, description2, 0, null);
    magForum .quickAddForum(forumName3);
    magTopic .quickStartTopic(topicName5,"Start topic_05_72565");
    
    click(By.linkText(catName2));
    magForum .quickAddForum(forumName4);
    magTopic .quickStartTopic(topicName6,"Start topic_06_72565");

    goToForumHome();
    magCat .exportCategoryInForum(fileName1, false);
    Utils.pause(3000);
    assert checkFileExisted("TestOutput/" + fileName1 + ".zip");

    /* Delete all categories, Forum and topic before running Import test case*/
    click(By.linkText(catName1));
    magCat .deleteCategoryInForum(catName1);
    click(By.linkText(catName2));
    magCat .deleteCategoryInForum(catName2);

    magCat .importCategoryInForum("TestOuput/"+fileName1 + ".zip");
    info("Import all categories with the file exported at mode Only_Category.");
    Utils.pause(500);
    deleteFile("TestOutput/" + fileName1 + ".zip");

    /* Delete all categories, Forum and topic before exit test case */
    click(By.linkText(catName1));
    magCat .deleteCategoryInForum(catName1);
    click(By.linkText(catName2));
    magCat .deleteCategoryInForum(catName2);
  }

/* *CaseId: 72819 -> Functional-Forum-Forum-Category-Import without file uploaded.
   * This is medium test case, so it is not required to executed.
   *
  @Test
  public void test03_ImportCategory_NoFile(){
	  magCat .importCategoryInForum();
      Utils.pause(500);
  }  

  **CaseId: 72442 -> Functional-Forum-Forum-Category-Export with blank file name.
   * This is medium test case, so it is not required to executed.
   *
  @Test
  public void test04_ExportCategory_BlankFileName(){
	  magCat .exportCategoryInForum();
      Utils.pause(500);
  }
*/

}