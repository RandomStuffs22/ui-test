package org.exoplatform.selenium.platform.forum.functional.answers.publishactivities;

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

import  static org.exoplatform.selenium.TestLogger.info;

/**
 * 
 * @author Thuntn
 * @date 08 Nov 2013
 */
public class Forum_Answers_PublishActivities_Display extends AnswerBase{

	ManageAccount acc;
	AnswerManageCategory mCat;
	AnswerManageQuestion mQuest;
	AnswerManageAnwser mAns;
	AnswerManageComment mCom;

	HomePageActivity hpAct;
	NavigationToolbar navTool;

	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		mCat = new AnswerManageCategory(driver);
		mQuest = new AnswerManageQuestion(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		
		button = new Button(driver);
		hpAct = new HomePageActivity(driver);
		mAns = new AnswerManageAnwser(driver);
		mCom = new AnswerManageComment(driver);
		navTool = new NavigationToolbar(driver);

		goToAnswer();
	}
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Display number of comments and answers
	 * CaseID 77033
	 */
	@Test
	public void test01_DisplayNumberOfCommentsAndAnswers() {

		String questName = "Question 77033";
		String answerContent = "Answer 77033";
		String comment = "Comment 77033";

		info("Display number of comments and answers");

		//Submit a question
		mQuest.submitQuestion(null, questName, questName, null, false, null);
		click(By.linkText(questName));

		//Answer the question
		mAns.answerQuestion(2, questName, null, answerContent, true, true, false, "", false, "");

		//Add comment for question
		mCom.addComment4Question(questName, comment);
		navTool.goToHomePage();

		hpAct.checkAnswerOfQuestion(questName,answerContent);
		hpAct.checkCommentOfQuestion(questName, comment);

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

	/** Display "View" action for a comment from Answer activity
	 * CaseID 77036
	 */
	@Test
	public void test02_DisplayViewActionForCommentFromAnswerActivity() {

		String questName = "Question 77036";
		String comment = "Comment 77036";

		info("Display 'View' action for a comment from Answer activity");

		//Submit a question
		mQuest.submitQuestion(null, questName, questName, null, false, null);
		click(By.linkText(questName));

		//Add comment for question
		mCom.addComment4Question(questName, comment);

		//View comment from activity
		navTool.goToHomePage();
		hpAct.viewActivity(questName, comment);
		waitForAndGetElement(mCom.ELEMENT_COMMENT_IN_QUESTION.replace("${comment}", comment));

		//Delete data
		mQuest.deleteQuestion(2, questName);
	}

	/** Display "View" action for an answer from Answer activity
	 * CaseID 77037
	 */
	@Test
	public void test03_DisplayViewActionForAnswerFromAnswerActivity() {

		String questName = "Question 77037";
		String answerContent = "Answer 77037";
		String commentAnswer = hpAct.MSG_ANSWER_QUESTION.replace("${answer}", answerContent);

		info("Display 'View' action for an answer from Answer activity");

		//Submit a question
		mQuest.submitQuestion(null, questName, questName, null, false, null);
		click(By.linkText(questName));

		//Answer the question
		mAns.answerQuestion(2, questName, null, answerContent, true, true, false, "", false, "");	

		//View comment from activity
		navTool.goToHomePage();
		hpAct.viewActivity(questName, commentAnswer);
		waitForAndGetElement(mAns.ELEMENT_ANSWER_CONTENT.replace("${answer}", answerContent));

		//Delete data
		mQuest.deleteQuestion(2, questName);
	}

	/** Not display Answer's activity after create, update and delete a Category
	 * CaseID 77039, 77040, 77041
	 */
	@Test
	public void test04_NotDisplayAnswerActivityAfterCreateEditDeleteCategory() {
		String categoryName = "Category 77039";
		String newCate = "New category 77040";
		String[] userGroup = {};

		info("Not display Answer's activity after create, update and delete a Category");

		//Add category
		mCat.addNewCategoryInAnswer(categoryName, null, categoryName, 0, null, true, false);

		//Check activity if activity is displayed
		navTool.goToHomePage();
		assert (waitForAndGetElement(ELEMENT_CONTAINS_TEXT.replace("${text}", categoryName),5000,0) == null);

		//Update category
		goToAnswer();
		mCat.editCategoryInAnswer(categoryName, newCate, null, null, 0, userGroup, false, false);

		//Check activity if activity is displayed
		navTool.goToHomePage();
		assert (waitForAndGetElement(ELEMENT_CONTAINS_TEXT.replace("${text}", newCate),5000,0) == null);

		//Delete category
		goToAnswer();
		mCat.deleteCategoryInAnswer(newCate);

		//Check activity if activity is displayed
		navTool.goToHomePage();
		assert (waitForAndGetElement(ELEMENT_CONTAINS_TEXT.replace("${text}", newCate),5000,0) == null);
	}

	/** Not display Answer's activity after export, import a Category
	 * CaseID 77042
	 */
	@Test
	public void test05_NotDisplayAnswerActivityAfterExportCategory() {
		String categoryName = "Category77042";
		String fileName = "Category77042";

		info("Not display Answer's activity after export, import a Category");

		//Add category
		mCat.addNewCategoryInAnswer(categoryName, null, categoryName, 0, null, true, false);
		mCat.openCategoryInAnswer(categoryName);
		mCat.exportAnswerCategory(fileName);

		//View comment from activity
		navTool.goToHomePage();
		assert (waitForAndGetElement(ELEMENT_CONTAINS_TEXT.replace("${text}", categoryName),5000,0) == null);

		//Delete data
		goToAnswer();
		mCat.deleteCategoryInAnswer(categoryName);
		mCat.importAnswerCategory("TestOutput/"+fileName + ".zip");

		navTool.goToHomePage();
		assert (waitForAndGetElement(ELEMENT_CONTAINS_TEXT.replace("${text}", categoryName),5000,0) == null);
		goToAnswer();
		mCat.deleteCategoryInAnswer(categoryName);

		deleteFile("TestOutput/"+fileName + ".zip");
	}

	/** Not display Answer's activity after approve, disapprove a question
	 * CaseID 77044, 77045
	 */
	@Test
	public void test06_NotDisplayAnswerActivityAfterApproveOrDisapproveQuestion() {

		String questName = "Question 77044";

		//Submit question, and answer the question
		mQuest.submitQuestion(null, questName, questName, null, false, null);

		//Check activity before approve or disapprove
		hpAct.checkActivityAfterCreatingQuestion(questName, questName);

		//Disapprove the question
		goToAnswer();
		click(By.linkText(questName));
		mQuest.goToManageQuestions();
		mQuest.approveQuestion(questName, false);
		click(mQuest.ELEMENT_MANAGE_QUESTION_CLOSE_BUTTON);

		//Check activity after approve or disapprove
		hpAct.checkActivityAfterCreatingQuestion(questName, questName);

		//Approve the question
		goToAnswer();
		mQuest.goToManageQuestions();
		mQuest.approveQuestion(questName, true);
		click(mQuest.ELEMENT_MANAGE_QUESTION_CLOSE_BUTTON);

		//Check activity after approve or disapprove
		hpAct.checkActivityAfterCreatingQuestion(questName, questName);

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

	/** Not display Answer's activity after vote up, down an answer
	 * CaseID 77057, 77058
	 */
	@Test
	public void test07_NotDisplayAnswerActivityAfterVoteUpOrDownAnswer() {

		String questName = "Question 77057";
		String answerContent = "Answer 77037";
		String commentAnswer = hpAct.MSG_ANSWER_QUESTION.replace("${answer}", answerContent);

		//Submit question, and answer the question
		mQuest.submitQuestion(null, questName, questName, null, false, null);
		click(By.linkText(questName));
		mAns.answerQuestion(2, questName, null, answerContent, true, true, false, "", false, "");

		//Check activity before vote answer
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questName));
		hpAct.checkAnswerOfQuestion(questName, answerContent);
		waitForAndGetElement(hpAct.ELEMENT_QUESTION_CONTENT.replace("${title}", questName));
		waitForAndGetElement(hpAct.ELEMENT_COMMENT_LAST.replace("${title}", questName).replace("${comment}", commentAnswer));
		
		//Vote down answer
		goToAnswer();
		mAns.rateAnswer(answerContent,false); 

		//Check activity after vote down
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questName));
		hpAct.checkAnswerOfQuestion(questName, answerContent);
		waitForAndGetElement(hpAct.ELEMENT_QUESTION_CONTENT.replace("${title}", questName));
		waitForAndGetElement(hpAct.ELEMENT_COMMENT_LAST.replace("${title}", questName).replace("${comment}", commentAnswer));

		//Vote Up answer
		goToAnswer();
		mAns.rateAnswer(answerContent,true); 

		//Check activity after vote up
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questName));
		hpAct.checkAnswerOfQuestion(questName, answerContent);
		waitForAndGetElement(hpAct.ELEMENT_QUESTION_CONTENT.replace("${title}", questName));
		waitForAndGetElement(hpAct.ELEMENT_COMMENT_LAST.replace("${title}", questName).replace("${comment}", commentAnswer));

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

}
