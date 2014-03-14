package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;

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
 * @date 22 Aug 2013
 */
public class Forum_Answers_Search extends AnswerBase {

	ManageAccount magAc;
	AnswerManageCategory magCat;
	AnswerManageQuestion magQuest;
	
	@BeforeMethod(groups = "error")
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magCat = new AnswerManageCategory(driver);
		magQuest = new AnswerManageQuestion(driver);
		
		magAc.signIn(DATA_USER1, DATA_PASS);
		goToAnswer();
	}

	@AfterMethod(groups = "error")
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 68943 -> Quick search in answer
	 * testcase is having bug (issue FORUM-537)
	 */
	@Test (groups = "error")
	public void test01_QuickSearch(){
		String categoryName = "Answersearch1";
		String description = "Add new category for answer";
		String[] userGroup = {DATA_USER4};
		
		magCat.addNewCategoryInAnswer(categoryName, null, description, 2, userGroup, true, false);

		quickSearchInAnswer(categoryName);
		waitForAndGetElement(By.linkText(categoryName));
		click(ELEMENT_CLOSE_QUICK_SEARCH);
		
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**CaseId: 68944 -> Advanced search
	 * 
	 */
	@Test
	public void test02_AdvancedSearch(){
		String categoryName = "Answersearch2";
		String description = "Add new category for answer";
		String[] userGroup = {DATA_USER4};		
		String questionName = "Questionsearch2";
		String questionContent = "Add new question2 for category";
		
		info("Add new category and new question");
		magCat.addNewCategoryInAnswer(categoryName, null, description, 2, userGroup, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		
		advancedSearchInAnswer(null, 3, null, null, null, true, null, DATA_USER1, null, null, "question2", null, null);
		waitForAndGetElement(By.xpath("//*[@id='ResultQuickSearch']//a[text()='" + questionName + "']"));
		click(ELEMENT_CLOSE_ADVANCE_SEARCH);
		
		magCat.deleteCategoryInAnswer(categoryName);
	}
}