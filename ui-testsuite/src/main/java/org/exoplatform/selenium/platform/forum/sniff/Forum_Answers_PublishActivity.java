package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageAnwser;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageComment;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author thuntn
 * @date 29 August 2013
 */
public class Forum_Answers_PublishActivity extends AnswerBase{
	ManageAccount Acc;
	AnswerManageCategory mCat;
	AnswerManageQuestion mQuest;
	AnswerManageAnwser mAns;
	AnswerManageComment mCom;
	HomePageActivity HPAct;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		Acc = new ManageAccount(driver);
		mCat = new AnswerManageCategory(driver);
		mQuest = new AnswerManageQuestion(driver, this.plfVersion);
		mAns = new AnswerManageAnwser(driver, this.plfVersion);
		mCom = new AnswerManageComment(driver, this.plfVersion);
		HPAct = new HomePageActivity(driver, this.plfVersion);
		navTool = new NavigationToolbar(driver);
		Acc.signIn(DATA_USER1, DATA_PASS);
		button = new Button(driver);

		goToAnswer();
	}

	/**CaseId: 74754, 
	 * Create a question
	 */
	@Test
	public void test01_CreateQuestion(){
		String categoryName = "Category74754";
		String description = "Add new category for answer";	
		String questionName = "Question 74754" ;
		String questionContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";
		String answerContent = "AnswerQuestion1";
		String comment = "Comment for this question: good";

		info("Create a question");
		mQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		mAns.answerQuestion(1, questionName, null, answerContent, true, true, false, "", false, "");
		click(By.linkText(questionName));
		mCom.addComment4Question(questionName, comment);

		mQuest.rateQuestion(2);

		info("Check activity on homepage");
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questionName));
		
		HPAct.checkNumberOfLineOfContent(getText(HPAct.ELEMENT_QUESTION_CONTENT.replace("${title}", questionName)), questionContent);
		HPAct.checkRateQuestion(questionName, 2.0);
		HPAct.checkCommentOfQuestion(questionName, comment);
		HPAct.checkAnswerOfQuestion(questionName, answerContent);

		//Delete data
		goToAnswer();
		mCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**CaseId: 74754, 
	 * Create a question
	 */
	@Test
	public void test01_CreateQuestion4LinesInContent(){
		String categoryName = "CategoryAnswer2";
		String description = "Add new category for answer";	
		String questionName = "test01_Question 02" ;
		String questionContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";

		info("Create a question");
		mQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		click(By.linkText(questionName));


		info("Check activity on homepage");
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questionName));
		
		HPAct.checkNumberOfLineOfContent(getText(HPAct.ELEMENT_QUESTION_CONTENT.replace("${title}", questionName)), questionContent);

		//Delete data
		goToAnswer();
		mCat.deleteCategoryInAnswer(categoryName);
	}

	/**CaseId: 74755, 74759, 74758, 74760
	 * Check number of comments and answers
	 * Submit a comment
	 * Submit an answer
	 * Delete a question
	 */
	@Test
	public void test02_CheckNumberOfCommentsAndAnswers(){
		String questionName = "Question02";
		String questionContent = "Content of question 02";
		String answerContent1 = "Answer1Question 02";
		String answerContent2 = "Answer2Question 02";
		String comment = "Comment for this question: good";

		info("Check number of comments and answers");

		//Create a question
		mQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		mAns.answerQuestion(1, questionName, null, answerContent1, true, true, false, "", false, "");
		click(By.linkText(questionName));
		mAns.answerQuestion(2, questionName, null, answerContent2, true, true, false, "", false, "");

		mCom.addComment4Question(questionName, comment);

		info("Check activity on homepage");
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questionName));

		/**click(By.linkText("View all 3 comments."));*/

		HPAct.checkAnswerOfQuestion(questionName, answerContent1, answerContent2);
		HPAct.checkCommentOfQuestion(questionName, comment);

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questionName);
		
		navTool.goToHomePage();
		waitForElementNotPresent(By.linkText(questionName));
		waitForElementNotPresent(HPAct.ELEMENT_QUESTION_CONTENT.replace("${text}", questionContent));
	}

	/**CaseId: 74756
	 * Unactivate a question
	 * Forum-850: Not show comments of answer activity
	 */
	@Test(groups="error")
	public void test03_UnactivateQuestion(){
		String questionName = "Question03";
		String questionContent = "Content of question 03";

		info("Unactivate a question");
		//Create a question
		mQuest.submitQuestion(null, questionName, questionContent, null, false, null);

		//Deactivate the question
		mQuest.goToManageQuestions();
		mQuest.activeQuestion(questionName, false);
		
		info("Check activity on homepage");
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questionName));

		HPAct.checkActivateQuestion(questionName,false);
		//Activate this question to delete
		goToAnswer();
		mQuest.activeQuestion(questionName, true);
		button.close();
		
		//Delete data
		click(By.linkText(questionName));
		mQuest.deleteQuestion(2, questionName);
	}

	/**CaseId: 74757
	 * Activate a question
	 */
	@Test
	public void test04_ActivateQuestion(){
		String questionName = "Question04";
		String questionContent = "Content of question 04";

		info("Activate a question");
		//Create a question
		mQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		mQuest.goToManageQuestions();
		mQuest.activeQuestion(questionName, false);
		mQuest.activeQuestion(questionName, true);
		button.close();

		info("Check activity on homepage");
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questionName));

		HPAct.checkActivateQuestion(questionName);

		goToAnswer();

		//Delete data
		click(By.linkText(questionName));
		mQuest.deleteQuestion(2, questionName);
	}

	/**CaseId: 74761
	 * Edit question title
	 */
	@Test
	public void test05_EditTitleOfQuestion(){
		String questionName = "Question05";
		String questionContent = "Content of question 05";
		String newName= "New Question 05";

		info("Edit question title");
		//Create a question
		mQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		click(By.linkText(questionName));
		mQuest.editQuestion(2, questionName, null, newName, null, null, null, true, true, false, null);

		info("Check activity on homepage");
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(newName));
		HPAct.checkTitleAfterEditQuestion(newName);

		goToAnswer();

		//Delete data
		mQuest.deleteQuestion(2, questionName);
	}

	/**CaseId: 75289
	 * Open Answers application from Answer's activity
	 */
	@Test
	public void test06_OpenAnswersFromActivity(){
		String questionName = "Question06";
		String questionContent = "Content of question 06";

		info("Open Answers application from Answer's activity");

		//Create a question
		mQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		click(By.linkText(questionName));

		info("Check activity on homepage");
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questionName));
		click(By.linkText(questionName));

		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT.replace("${content}", questionContent));
		//Delete data
		mQuest.deleteQuestion(2, questionName);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

}