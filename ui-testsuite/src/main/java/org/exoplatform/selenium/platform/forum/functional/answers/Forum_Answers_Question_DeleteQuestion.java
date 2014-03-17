package org.exoplatform.selenium.platform.forum.functional.answers;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.AnswerBase;
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
public class Forum_Answers_Question_DeleteQuestion extends AnswerBase {

	ManageAccount magAc;
	AnswerManageCategory magCat;
	AnswerManageQuestion magQuest;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magCat = new AnswerManageCategory(driver);
		magQuest = new AnswerManageQuestion(driver);
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Delete Question in All questions tab of Mange questions form
	 * CaseID 72263 - 72264
	 * Step 1: Create category and question
	 * Step 2: Open Manage questions form
	 * Step 3:Delete question
	 */
	@Test
	public void test01_02_DeleteQuestionInAllQuestionsTabOfMangeQuestionsForm() {
		/*Declare variables*/
		String categoryName = "Category72263";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question72263";
		String questionContent = "Add new question for category";
		
		/*Step 1: Create category and question*/
		//- Login by administrator/ moderator to create category and questions
		//- Category and question are created successfully. 
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		/*Step 2: Open Manage questions form*/
		//- Click on [Manage question] on main menu
		magQuest.goToManageQuestions();
		
		//- Click on [All questions] tab
		//- All question tab is shown
		click(magQuest.ELEMENT_MANAGE_QUESTION_ALL_QUESTIONS_TAB);
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", questionName));
		
		//Step 3:Delete question
		//- Click on Deletion icon corresponding to the question you want to delete
		//- Click OK in confirm message
		//- Question is deleted successfully and not shown at Category
		magQuest.deleteQuestion(3, questionName);

		/*Clear data*/
		info("-- Clear data --");
		goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Delete Question in Pending questions tab of Manage questions form
	 * CaseID 72427
	 * Step 1: Create category and question
	 * Step 2: Open Manage questions form
	 * Step 3:Delete question
	 */
	@Test
	public void test03_DeleteQuestionInPendingQuestionsTabOfMangeQuestionsForm() {
		/*Declare variables*/
		String categoryName = "Category72427";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question72427";
		String questionContent = "Add new question for category";
		
		/*Step 1: Create category and question*/
		//- Login by administrator/ moderator to create category and questions
		//- Category and question are created successfully. 
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		/*Step 2: Open Manage questions form*/
		//- Click on [Manage question] on main menu
		magQuest.goToManageQuestions();
		
		//- Click on [Open questions] tab
		//- Pending questions tab is shown
		click(magQuest.ELEMENT_MANAGE_QUESTION_OPEN_QUESTIONS_TAB);
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_PENDING_QUESTION_TAB_LIST.replace("${question}", questionName));
		
		//Step 3:Delete question
		//- Click on Deletion icon corresponding to the question you want to delete
		//- Click OK in confirm message  
		//- Question is deleted successfully and not shown at Category  
		magQuest.deleteQuestion(3, questionName);
		
		/*Clear data*/
		info("-- Clear data --");
		goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName);
	}
}
