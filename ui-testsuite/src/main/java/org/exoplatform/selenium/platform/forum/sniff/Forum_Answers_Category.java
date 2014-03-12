package org.exoplatform.selenium.platform.forum.sniff;

import  static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author lientm
 * @date 19 Aug 2013
 */

public class Forum_Answers_Category extends AnswerBase {
	
	ManageAccount magAc;
	AnswerManageCategory magCat;
	AnswerManageQuestion magQuest;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magCat = new AnswerManageCategory(driver);
		magQuest = new AnswerManageQuestion(driver, this.plfVersion);
		
		magAc.signIn(DATA_USER1, DATA_PASS);
		goToAnswer();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 70972 + 70973 + 70978: Add, edit, delete category
	 * 
	 */
	@Test
	public void test01_AddEditDeleteCategory(){
		String categoryName = "Answercategory1";
		String description = "Add new category for answer";
		String[] userGroup = {DATA_USER4};
		
		String name_edit = "Category1_Edit";
		String des_edit = "Edit category 1";
		String[] userGroupEdit = {"Platform/Content Management", "", "/platform/web-contributors"};
		
		magCat.addNewCategoryInAnswer(categoryName, null, description, 2, userGroup, true, false);
		
		magCat.openCategoryInAnswer(categoryName);
		magCat.editCategoryInAnswer(null, name_edit, "2", des_edit, 3, userGroupEdit, true, true, true, true, true);
		waitForAndGetElement(ELEMENT_ANSWER_BREADCUMB.replace("${category}", name_edit));
		
		magCat.deleteCategoryInAnswer(name_edit);
	}
	
	/**CaseId: 70979 -> Move category
	 * 
	 */
	@Test
	public void test02_MoveCategory(){
		String categoryName1 = "Answercategory2_1";
		String description1 = "Add new category1 for answer";
		String[] userGroup1 = {DATA_USER4};
		
		String categoryName2 = "Answercategory2_2";
		String description2 = "Add new category2 for answer";
		String[] userGroup2 = {"Platform/Content Management"};
		
		info("Add 2 category at root");
		magCat.addNewCategoryInAnswer(categoryName1, null, description1, 2, userGroup1, true, false);
		magCat.addNewCategoryInAnswer(categoryName2, null, description2, 3, userGroup2, true, true, true, true, true);

		magCat.moveCategory(categoryName1, categoryName2);
		magCat.openCategoryInAnswer(categoryName2);
		waitForTextPresent(categoryName1);
		
		magCat.openCategoryInAnswer(categoryName1);
		magCat.deleteCategoryInAnswer(categoryName1);
		magCat.deleteCategoryInAnswer(categoryName2);
	}
	
	/**CaseId: 68955 -> Export/Import category
	 * 
	 */
	@Test 
	public void test03_ExportImportCategory(){
		String categoryName = "Answercategory3";
		String description = "Add new category for answer";
		String[] userGroup = {DATA_USER4};		
		String questionName = "QuestionCategory3";
		String questionContent = "Question of Answercategory3";
		String fileName = "Answercategory3";
		String fileFull = fileName + ".zip";
		
		info("Add new category and new question");
		magCat.addNewCategoryInAnswer(categoryName, null, description, 2, userGroup, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		
		info("Export category and new question");
		magCat.exportAnswerCategory(fileName);
		Utils.pause(3000);
		assert checkFileExisted("TestOutput/" +fileFull);
		
		info("Delete category and question");
		magCat.deleteCategoryInAnswer(categoryName);
		
		info("Cut/paste file from TestOutput folder to TestData folder");
		cutPasteFileFromOutputToTestData(fileFull);
		
		magCat.importAnswerCategory(fileFull);
		deleteFile(fileFull);
		magCat.openCategoryInAnswer(categoryName);
		waitForAndGetElement(By.linkText(questionName));
		
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**CaseId: 68956 -> Watch/Unwatch category
	 * 
	 */
	@Test
	public void test04_WatchUnwatchCategory(){
		String categoryName = "Answercategory4";
		String description = "Add new category for answer";
		String[] userGroup = {DATA_USER4};		
		String questionName = "QuestionCategory4";
		String questionContent = "Question of Answercategory4";
		
		info("Add new category and new question");
		magCat.addNewCategoryInAnswer(categoryName, null, description, 2, userGroup, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		
		goToAnwserHome();
		magCat.watchAnswerCategory(categoryName, true);
		magCat.watchAnswerCategory(categoryName, false);
		
		magCat.openCategoryInAnswer(categoryName);
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**CaseId: 68954 -> Drag drop category
	 * 
	 */
	@Test
	public void test05_DragDropCategory(){
		String categoryName1 = "Answercategory5_1";
		String description1 = "Add new category1 for answer";
		String[] userGroup1 = {DATA_USER4};
		
		String categoryName2 = "Answercategory5_2";
		String description2 = "Add new category2 for answer";
		String[] userGroup2 = {"Platform/Content Management", "", "/platform/web-contributors"};
		
		info("Add 2 category at root");
		magCat.addNewCategoryInAnswer(categoryName1, null, description1, 2, userGroup1, true, false);
		magCat.addNewCategoryInAnswer(categoryName2, null, description2, 3, userGroup2, true, true, true, true, true);

		magCat.dragDropAnswerCategory(categoryName1, categoryName2);
		waitForElementNotPresent(ELEMENT_CATEGORY_LINK.replace("${category}", categoryName1));
		
		magCat.openCategoryInAnswer(categoryName2);
		waitForAndGetElement(ELEMENT_CATEGORY_LINK.replace("${category}", categoryName1));
		
		magCat.goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName2);
	}
}
