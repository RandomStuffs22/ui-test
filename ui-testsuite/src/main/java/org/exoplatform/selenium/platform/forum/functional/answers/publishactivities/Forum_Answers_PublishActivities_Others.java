package org.exoplatform.selenium.platform.forum.functional.answers.publishactivities;

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

import static org.exoplatform.selenium.TestLogger.info;

/**
 * 
 * @author Thuntn
 * @date 07 Nov 2013
 */
public class Forum_Answers_PublishActivities_Others extends AnswerBase{

	ManageAccount acc;
	AnswerManageQuestion mQuest;
	HomePageActivity hpAct;
	AnswerManageAnwser mAns;
	Activity act;
	AnswerManageComment mCom;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		mQuest = new AnswerManageQuestion(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		hpAct = new HomePageActivity(driver);
		navTool = new NavigationToolbar(driver);
		mAns = new AnswerManageAnwser(driver);
		act = new Activity();
		mCom = new AnswerManageComment(driver);
		goToAnswer();
	}
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Open Answers application from Answer's activity
	 * CaseID 77031
	 */
	@Test
	public void test01_OpenAnswerApplicationFromAnswerActivity() {
		String questName = "Question 77069" ;
		info("Open Answers application from Answer's activity");
		mQuest.submitQuestion(null, questName, questName, null, false, null);

		navTool.goToHomePage();
		click(By.linkText(questName));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT.replace("${content}", questName));

		mQuest.deleteQuestion(2, questName);
	}

	/** Open Answer application from "View" action
	 * CaseID 77035
	 */
	@Test
	public void test02_OpenAnswerApplicationFromViewAction() {
		String questName = "Question 77035" ;
		String answerContent = "Answer 77035";
		String commentAnswer = hpAct.MSG_ANSWER_QUESTION.replace("${answer}", answerContent);
		String comment = "comment 77035";
		info("Open Answer application from 'View' action");

		mQuest.submitQuestion(null, questName, questName, null, false, null);
		click(By.linkText(questName));

		mAns.answerQuestion(2, questName, null, answerContent, true, true, false, "", false, "");

		navTool.goToHomePage();
		hpAct.viewActivity(questName, commentAnswer);

		waitForAndGetElement(mAns.ELEMENT_ANSWER_CONTENT.replace("${answer}", answerContent));

		navTool.goToHomePage();
		act.addComment(questName, comment);
		hpAct.viewActivity(questName, comment);
		waitForAndGetElement(mCom.ELEMENT_COMMENT_IN_QUESTION.replace("${comment}", comment));

		mQuest.deleteQuestion(2, questName);

	}

	/** Dislike an Answers activity from like icon
	 * CaseID 77070
	 */
	@Test
	public void test03_DislikeAnswerActivity() {
		String questName = "Question 77070" ;
		info("Dislike an Answers activity from like icon");
		
		//Submit question
		mQuest.submitQuestion(null, questName, questName, null, false, null);

		//Like activity
		navTool.goToHomePage();
		hpAct.likeOrUnlikeActivity(questName);

		//Unlike activity
		navTool.goToHomePage();
		hpAct.likeOrUnlikeActivity(questName);

		//Delete data
		goToAnswer();
		click(By.linkText(questName));
		mQuest.deleteQuestion(2, questName);

	}
}
