package org.exoplatform.selenium.platform.forum.functional.answers;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageAnwser;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 08/11/2013
 *
 */
public class Forum_Answers_Question_List extends AnswerBase {

	ManageAccount magAc;
	AnswerManageCategory magCat;
	AnswerManageQuestion magQuest;
	AnswerManageAnwser magAns;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magCat = new AnswerManageCategory(driver);
		magQuest = new AnswerManageQuestion(driver);
		magAns = new AnswerManageAnwser(driver);
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Check all existing question list
	 * CaseID 72259
	 * Step 1: Create categories & question
	 * Step 2: Answer some questions
	 * Step 3: Check all existing question list
	 */
	@Test
	public void test01_CheckAllExistingQuestionList() {
		/*Declare variables*/
		String categoryName = "Category72259";
		String description = "Add new category for answer";	
		String questionName1 = "Answer_Question722591";
		String questionName2 = "Answer_Question722592";
		String questionContent = "Add new question for category";
		String answerContent = "Answer72259";

		/*Step 1: Create categories & question*/
		//- Login by administrator or moderator
		//- Create categories and questions (include multi-languages question)
		//- Create categories and questions successfully
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName1, questionContent, null, false, null);
		magQuest.submitQuestion(null, questionName2, questionContent, null, false, null);

		/*Step 2: Answer some questions*/
		//- Answer some questions
		//- Answer successfully
		magAns.answerQuestion(1, questionName1, null, answerContent, true, true, false, null, false, null);

		/*Step 3: Check all existing question list*/
		//- Click Manage questions
		magQuest.goToManageQuestions();
		
		//- Select [All questions] tab
		click(magQuest.ELEMENT_MANAGE_QUESTION_ALL_QUESTIONS_TAB);
		
		//- All questions created at step 1 are displayed in list (include answered question/not)
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", questionName1));
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", questionName2));
		click(magQuest.ELEMENT_MANAGE_QUESTION_CLOSE_BUTTON);

		/*Clear data*/
		info("-- Clear data --");
		goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Check pending question list
	 * CaseID 72424
	 * Step 1: Create categories & question
	 * Step 2: Answer some questions
	 * Step 3: Show list of questions that have not been answered
	 */
	@Test
	public void test02_CheckPendingQuestionList() {
		/*Declare variables*/
		String categoryName = "Category72424";
		String description = "Add new category for answer";	
		String questionName1 = "Answer_Question724241";
		String questionName2 = "Answer_Question724242";
		String questionContent = "Add new question for category";
		String answerContent = "Answer72424";

		/*Step 1: Create categories & question*/
		//- Login by administrator or moderator
		//- Create categories and questions (include multi-languages question)
		//- Create categories and questions successfully
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName1, questionContent, null, false, null);
		magQuest.submitQuestion(null, questionName2, questionContent, null, false, null);

		/*Step 2: Answer some questions*/
		//- Do answer some questions: with some multi-languages questions, do not answer in all supported languages
		//- Answer successfully
		magAns.answerQuestion(1, questionName1, null, answerContent, true, true, false, null, false, null);

		/*Step 3: Show list of questions that have not been answered*/
		//- Click Manage questions
		magQuest.goToManageQuestions();
		
		//- Select [Pending questions] tab
		click(magQuest.ELEMENT_MANAGE_QUESTION_OPEN_QUESTIONS_TAB);
		
		//- List questions that have not been answered or multi-languages questions that have not been answered in all supported language is displayed
		//- The language that has not been answered will be displayed at language column 
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_PENDING_QUESTION_TAB_LIST.replace("${question}", questionName2));
		waitForElementNotPresent(magQuest.ELEMENT_DELETE_QUESTION_IN_PENDING_QUESTION_TAB_LIST.replace("${question}", questionName1));
		click(magQuest.ELEMENT_MANAGE_QUESTION_CLOSE_BUTTON);
		
		/*Clear data*/
		info("-- Clear data --");
		goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName);
	}
}
