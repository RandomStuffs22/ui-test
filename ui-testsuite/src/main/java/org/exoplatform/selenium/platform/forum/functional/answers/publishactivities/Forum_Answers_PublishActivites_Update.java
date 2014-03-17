package org.exoplatform.selenium.platform.forum.functional.answers.publishactivities;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageAnwser;
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
 * @date 11 Nov 2013
 */
public class Forum_Answers_PublishActivites_Update extends AnswerBase{

	ManageAccount acc;
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
	/** Update Answer's activity after activate or deactivate a question
	 * CaseID 77046, 77047
	 */
	@Test
	public void test01_UpdateAnswerActivitiesAfterActivateOrDeactivateQuestion() {
		String questName = "Question 77046" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";

		info("Add an Answer's activity after submit a question");

		//Submit a question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Check activity before active, deactivate question
		navTool.goToHomePage();
		hpAct.checkActivityAfterCreatingQuestion(questName, questContent);

		goToAnswer();
		mQuest.goToManageQuestions();
		mQuest.activeQuestion(questName, false);
		click(mQuest.ELEMENT_MANAGE_QUESTION_CLOSE_BUTTON);

		//Check activity 
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questName));
		hpAct.checkActivateQuestion(questName, false);

		//Activate a question
		goToAnswer();
		mQuest.goToManageQuestions();
		mQuest.activeQuestion(questName, true);
		click(mQuest.ELEMENT_MANAGE_QUESTION_CLOSE_BUTTON);

		//Check activity 
		navTool.goToHomePage();
		hpAct.checkActivateQuestion(questName, true);

		//Delete data 
		goToAnswer();
		mQuest.deleteQuestion(1, questName);
	}

	/** Update Answer's activity after activate, or unactivate an answer
	 * CaseID 77061, 77062
	 */
	@Test
	public void test02_UpdateAnswerActivitiesAfterActivateOrDeactivateAnswer() {
		String questName = "Question 77061" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";
		String answerContent = "Answer 77061";

		info("Add an Answer's activity after submit a question");

		//Submit a question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Check activity before active, deactivate answer
		navTool.goToHomePage();
		hpAct.checkActivityAfterCreatingQuestion(questName, questContent);

		goToAnswer();
		click(By.linkText(questName));
		mAns.answerQuestion(2, questName, null, answerContent, true, true, false, "", false, "");
		mAns.activeAnswer(answerContent, false);

		//Check activity 
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questName));
		hpAct.checkActivateAnswer(questName, answerContent, false);

		goToAnswer();
		mAns.activeAnswer(answerContent, true);

		//Check activity 
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questName));
		hpAct.checkActivateAnswer(questName, answerContent, true);

		//Delete data 
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

	/** Update Answer's activity after add attachment to a question
	 * CaseID 77048
	 */
	@Test
	public void test03_UpdateAnswerActivityAfterAddAttachmentToQuestion() {
		String questName = "Question 77048" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";
		String filePath = "Question_77048.png";

		info("Update Answer's activity after add attachment to a question");

		//Submit a question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Check activity before attach a file to question
		navTool.goToHomePage();
		hpAct.checkActivityAfterCreatingQuestion(questName, questContent);

		goToAnswer();
		mQuest.editQuestion(1, questName, null, null, questContent, null, null,true,true,true, filePath);

		//Add comment for the activity
		navTool.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(By.linkText(questName));
		waitForAndGetElement(hpAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", questName).replace("${comment}", hpAct.MSG_QUESTION_ADD_ATTACHMENT));

		//Check if comment is added to the question
		goToAnswer();
		click(By.linkText(questName));

		//Delete data
		mQuest.deleteQuestion(2, questName);
	}

	/** Update Answer's activity after rate a question
	 * CaseID 77050
	 */
	@Test
	public void test04_UpdateAnswerActivityAfterRateQuestion() {
		String questName = "Question 77050" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";

		info("Update Answer's activity after rate a question");

		//Submit question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Check activity before rate a question
		navTool.goToHomePage();
		hpAct.checkActivityAfterCreatingQuestion(questName, questContent);
		hpAct.checkRateQuestion(questName, 0.0);

		//Rate a question
		goToAnswer();
		click(By.linkText(questName));
		mQuest.rateQuestion(2);

		//Check rate a question
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questName));
		hpAct.checkRateQuestion(questName, 2.0);

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

	/** Update Answer's activity after edit title of question
	 * CaseID 77051
	 */
	@Test
	public void test05_UpdateAnswerActivityAfterEditTitleOfQuestion() {
		String questName = "Question 77051" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5<br>";
		String newQuestion = "new Question 77051";

		info("Update Answer's activity after edit title of question");

		//Submit question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Check activity before edit the title of a question
		navTool.goToHomePage();
		hpAct.checkActivityAfterCreatingQuestion(questName, questContent);

		//Answer the question
		goToAnswer();
		mQuest.editQuestion(1, questName, null, newQuestion, null, null, null, true, true, false, "");

		//Check activity after edit title of a question
		navTool.goToHomePage();
		hpAct.checkTitleAfterEditQuestion(newQuestion);

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(1, newQuestion);
	}

	/** Update Answer's activity after edit details of question
	 * CaseID 77052
	 */
	@Test
	public void test06_UpdateAnswerActivityAfterEditDetailsOfQuestion() {
		String questName = "Question 77052" ;
		String newDesc = "New description 77052";

		info("Update Answer's activity after edit title of question");

		//Submit question
		mQuest.submitQuestion(null, questName, questName, null, false, null);

		//Check activity before edit the question
		navTool.goToHomePage();
		hpAct.checkActivityAfterCreatingQuestion(questName, questName);

		//Answer the question
		goToAnswer();
		mQuest.editQuestion(1, questName, null, null,newDesc, null,  null, true, true, false, "");

		//Check activity after edit description of question
		navTool.goToHomePage();
		waitForAndGetElement(hpAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", questName)
				.replace("${comment}", hpAct.MSG_QUESTION_UPDATE_DESCRIPTON.replace("${description}", newDesc)));

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(1, questName);
	}

	/** Update Answer's activity after submit an answer
	 * CaseID 77054
	 */
	@Test
	public void test07_UpdateAnswerActivityAfterSubmitAnswer() {
		String questName = "Question 77054" ;
		String questContent = "Content line1<br>line2<br>line3<br>line4<br>line5";
		String answer = "line1<br>line2<br>line3<br>line4<br>line5";

		info("Update Answer's activity after submit an answer");

		//Submit question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Check activity before answer
		navTool.goToHomePage();
		hpAct.checkActivityAfterCreatingQuestion(questName, questContent);

		//Answer the question
		goToAnswer();
		click(By.linkText(questName));
		mAns.answerQuestion(2,questName , null, answer, true, true, false, null, false, null);

		//Check activity after answer a question
		navTool.goToHomePage();
		String commentActual = getText(hpAct.ELEMENT_GET_COMMENT_CONTENT.replace("${title}", questName));
		hpAct.checkNumberOfLineOfContent(commentActual, hpAct.MSG_ANSWER_QUESTION.replace("${answer}", answer));

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

	/** Update Answer's activity after edit an answer
	 * CaseID 77055
	 */
	@Test
	public void test08_UpdateAnswerActivityAfterEditAnswer() {
		String questName = "Question 77055" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5";
		String answer = "Answer 77055";
		String newAnswer = "line1<br/>line2<br/>line3<br/>line4<br/>line5";

		info("Update Answer's activity after submit an answer");

		//Submit question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Check activity before answer
		navTool.goToHomePage();
		hpAct.checkActivityAfterCreatingQuestion(questName, questContent);

		//Answer the question
		goToAnswer();
		click(By.linkText(questName));
		mAns.answerQuestion(2,questName , null, answer, true, true, false, null, false, null);

		//Edit an answer
		mAns.editAnswer(answer, null, newAnswer, true, true, false, null, false, null);

		//Check activity after edit answer of a question
		navTool.goToHomePage();
		String commentActual = getText(hpAct.ELEMENT_GET_COMMENT_CONTENT.replace("${title}", questName));
		hpAct.checkNumberOfLineOfContent(commentActual, hpAct.MSG_ANSWER_EDIT.replace("${answer}", newAnswer));

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

	/** Update Answer's activity after delete an answer
	 * CaseID 77056
	 */
	@Test
	public void test09_UpdateAnswerActivityAfterDeleteAnswer() {
		String questName = "Question 77056" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5";
		String answer = "Answer 77056";

		info("Update Answer's activity after delete an answer");

		//Submit question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Check activity before answer
		navTool.goToHomePage();
		hpAct.checkActivityAfterCreatingQuestion(questName, questContent);

		//Answer the question
		goToAnswer();
		click(By.linkText(questName));
		mAns.answerQuestion(2,questName , null, answer, true, true, false, null, false, null);

		//Delete an answer
		mAns.deleteAnswer(answer);

		//Check activity after delete an answer of a question
		navTool.goToHomePage();
		waitForElementNotPresent(hpAct.ELEMENT_ACTIVITY_COMMENT_CONTENT
				.replace("${title}", questName).replace("${comment}", hpAct.MSG_ANSWER_QUESTION.replace("${answer}", answer)));
		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

	/** Update Answer's activity after submit, edit, delete a comment to question
	 * CaseID 77063, 77064, 77065
	 */
	@Test
	public void test10_UpdateAnswerActivityAfterSubmitComment() {
		String questName = "Question 77063" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5";
		String comment = "Comment 77063";
		String newComment = "New comment 77063";

		info("Update Answer's activity after submit, edit, delete a comment to question");

		//Submit question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Check activity before comment
		navTool.goToHomePage();
		hpAct.checkActivityAfterCreatingQuestion(questName, questContent);

		//Add a comment to the question
		goToAnswer();
		click(By.linkText(questName));
		mCom.addComment4Question(questName, comment);

		//Check activity after comment a question
		navTool.goToHomePage();
		hpAct.checkCommentOfQuestion(questName, comment);

		//Edit a comment
		goToAnswer();
		mCom.editComment4Question(comment, newComment);

		//Check activity after edit a comment
		navTool.goToHomePage();
		hpAct.checkCommentOfQuestion(questName, newComment);

		//Delete comment
		goToAnswer();
		mCom.deleteCommentInQuestion(newComment);

		//Check activity after delete a comment
		navTool.goToHomePage();
		waitForElementNotPresent(hpAct.ELEMENT_QUESTION_COMMENT.replace("${title}", questName).replace("${comment}", newComment));

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}

	/** Update Answer's activity after promote a comment
	 * CaseID 77066
	 */
	@Test
	public void test11_UpdateAnswerActivityAfterPromoteComment() {
		String questName = "Question 77066" ;
		String questContent = "line1<br>line2<br>line3<br>line4<br>line5";
		String comment = "Comment line1<br/>line2<br/>line3<br/>line4<br/>line5";
		String subComment = "Comment line1<br/>line2<br/>line3<br/>line4";

		info("Update Answer's activity after promote a comment");

		//Submit question
		mQuest.submitQuestion(null, questName, questContent, null, false, null);

		//Check activity before comment
		navTool.goToHomePage();
		hpAct.checkActivityAfterCreatingQuestion(questName, questContent);

		//Add a comment to the question
		goToAnswer();
		click(By.linkText(questName));
		mCom.addComment4Question(questName, comment);
		//Promote a comment
		mCom.promoteAsAnswer(comment);

		//Check activity after promote a comment of a question
		navTool.goToHomePage();
		String actualComment = getText(hpAct.ELEMENT_GET_COMMENT_CONTENT.replace("${title}", questName));
		hpAct.checkNumberOfLineOfContent(actualComment, hpAct.MSG_PROMOTE_COMMENT.replace("${comment}", subComment));

		//Delete data
		goToAnswer();
		mQuest.deleteQuestion(2, questName);
	}
}
