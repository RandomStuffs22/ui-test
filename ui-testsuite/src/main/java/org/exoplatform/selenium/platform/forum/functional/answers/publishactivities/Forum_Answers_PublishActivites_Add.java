package org.exoplatform.selenium.platform.forum.functional.answers.publishactivities;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageAnwser;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageComment;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.exoplatform.selenium.platform.social.Activity;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author Thuntn
 * @date 05 Nov 2013
 */
public class Forum_Answers_PublishActivites_Add extends AnswerBase{

	ManageAccount acc;
	AnswerManageCategory mCat;
	AnswerManageQuestion mQuest;
	AnswerManageAnwser mAns;
	AnswerManageComment mCom;
	HomePageActivity hpAct;
	Activity act;

	@BeforeMethod
	public void setUpBeforeTest(){
		
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		mCat = new AnswerManageCategory(driver);
		mQuest = new AnswerManageQuestion(driver);
		mAns = new AnswerManageAnwser(driver);
		mCom = new AnswerManageComment(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		hpAct = new HomePageActivity(driver);
		navTool = new NavigationToolbar(driver);
		act = new Activity();
		goToAnswer();
	}
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/** Add an Answer's activity after submit a question
	 * CaseID 77030
	 */
	@Test
	public void test01_AddAnswerActivitiesAfterSubmitQuestion() {
		String questName = "Question 77030" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";
		String answerContent = "Answer 77030";
		String comment = "Comment for this question: excellent";
		info("Add an Answer's activity after submit a question");

		//Submit a question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Answer, comment, rate the question
		click(By.linkText(questName));
		mAns.answerQuestion(2, questName, null, answerContent, true, true, false, "", false, "");
		mCom.addComment4Question(questName, comment);
		mQuest.rateQuestion(4);

		//Check activity 
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questName));
		hpAct.checkNumberOfLineOfContent(getText(hpAct.ELEMENT_QUESTION_CONTENT.replace("${title}", questName)), questContent);
		hpAct.checkAnswerOfQuestion(questName,answerContent);
		hpAct.checkCommentOfQuestion(questName, comment);
		hpAct.checkRateQuestion(questName, 4.0);

		//Delete data 
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

	/** Add an Answer from activity
	 * CaseID 77038
	 */
	@Test
	public void test02_AddAnswerFromActivity() {
		String questName = "Question 77038" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";
		String answerContent = "Answer 77038";

		info("Add an Answer from activity");

		//Submit a question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Open Question from activity
		navTool.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(By.linkText(questName));
		click(hpAct.ELEMENT_ANSWER_ACTIVITY_LINK.replace("${title}", questName));

		//Answer the question
		mAns.answerQuestion(2, questName, null, answerContent, true, true, false, "", false, "");
		navTool.goToHomePage();
		hpAct.checkAnswerOfQuestion(questName,answerContent);

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

	/** Add a comment from activity
	 * CaseID 77034
	 */
	@Test
	public void test03_AddCommentFromActivity() {
		String questName = "Question 77034" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";
		String comment = "Comment 77034";

		info("Add a comment from activity");

		//Submit a question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Add comment for the activity
		navTool.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(By.linkText(questName));
		act.addComment(questName, comment);

		//Check if comment is added to the question
		goToAnswer();
		click(By.linkText(questName));
		waitForAndGetElement(mCom.ELEMENT_COMMENT_IN_QUESTION.replace("${comment}", comment));

		//Delete data
		mQuest.deleteQuestion(2, questName);
	}

	/** Add a comment in Answer's activity after approve an answer
	 * CaseID 77059
	 */
	@Test
	public void test04_AddCommentInAnswerActivityAfterApproveAnswer() {
		String categoryName = "Category 77059";
		String questName = "Question 77059" ;
		String[] userGroup = {};
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";
		String answerContent = "Answer 77059";

		info("Add a comment in Answer's activity after approve an answer");

		//Add category, and submit question
		mCat.addNewCategoryInAnswer(categoryName, null, categoryName, 0, userGroup, false, false,false,false,true);
		mCat.openCategoryInAnswer(categoryName);
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//demo answer the question
		acc.userSignIn(userType.DEVELOPER);
		goToAnswer();
		mCat.openCategoryInAnswer(categoryName);
		click(By.linkText(questName));
		mAns.answerQuestion(2, questName, null, answerContent, true, true, false, "", false, "");

		//Admin approve the answer
		acc.userSignIn(userType.ADMIN);
		goToAnswer();
		mCat.openCategoryInAnswer(categoryName);
		click(By.linkText(questName));
		mAns.approveAnswer(answerContent, true);

		//Check add a comment after approve the answer
		navTool.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(By.linkText(questName));
		waitForAndGetElement(hpAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", questName).replace("${comment}", hpAct.MSG_ANSWER_APPROVE.replace("${answer}", answerContent)));

		//Delete data
		goToAnswer();
		mCat.deleteCategoryInAnswer(categoryName);
	}

	/** Add a comment in Answer's activity after disapprove an answer
	 * CaseID 77060
	 */
	@Test
	public void test05_AddCommentInAnswerActivityAfterDisapproveAnswer() {
		String questName = "Question 77060" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";
		String answerContent = "Answer 77060";

		info("Add a comment in Answer's activity after approve an answer");

		//Add category, and submit question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Answer the question
		click(By.linkText(questName));
		mAns.answerQuestion(2, questName, null, answerContent, true, true, false, "", false, "");

		//Disapprove the answer
		mAns.approveAnswer(answerContent, false);

		//Check add a comment after disapprove the answer
		navTool.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(By.linkText(questName));
		waitForAndGetElement(hpAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", questName).replace("${comment}", hpAct.MSG_ANSWER_DISAPPROVE.replace("${answer}", answerContent)));

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

	/** Add Answer's activity after add a question in another language
	 * CaseID 77049
	 */
	@Test
	public void test06_AddCommentInAnswerActivityAfterAddQuestionInOtherLanguage() {
		String questName = "Question 77049" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";
		String newQuestion = "new Question 77049";

		info("Add Answer's activity after add a question in another language");

		//Submit question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Submit a question in French
		mQuest.editQuestion(1, questName,"French" , newQuestion, newQuestion, null, null, true,true,false,null);

		//Check add a comment after add a question in another language
		navTool.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(By.linkText(questName));
		waitForAndGetElement(hpAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", questName).replace("${comment}", hpAct.MSG_QUESTION_ADD_LANGUAGE.replace("${language}", "French")));

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(1, questName);
	}
}
