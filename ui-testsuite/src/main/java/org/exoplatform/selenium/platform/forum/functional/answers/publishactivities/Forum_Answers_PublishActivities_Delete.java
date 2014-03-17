package org.exoplatform.selenium.platform.forum.functional.answers.publishactivities;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
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
public class Forum_Answers_PublishActivities_Delete extends AnswerBase{

	ManageAccount acc;
	AnswerManageQuestion mQuest;
	HomePageActivity HPAct;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		mQuest = new AnswerManageQuestion(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		HPAct = new HomePageActivity(driver);
		navTool = new NavigationToolbar(driver);

		goToAnswer();
	}
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/** Delete an Answers activity from activity stream by its user
	 * CaseID 77069
	 */
	@Test
	public void test01_DeleteAnswerActivity() {
		String questName = "Question 77069" ;
		info("Delete an Answers activity from activity stream by its user");
		mQuest.submitQuestion(null, questName, questName, null, false, null);
		
		navTool.goToHomePage();
		HPAct.deleteActivity(questName);
		
		waitForElementNotPresent(By.linkText(questName));
		goToAnswer();
		click(By.linkText(questName));
		mQuest.deleteQuestion(2, questName);
	}

	/** Remove Answer's activity after delete a question
	 * CaseID 77053
	 */
	@Test
	public void test02_DeleteActivityAfterDeleteQuestion() {
		String questName = "Question 77053" ;
		info("Remove Answer's activity after delete a question");
		
		mQuest.submitQuestion(null, questName, questName, null, false, null);
		
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(questName));
		
		goToAnswer();
		click(By.linkText(questName));
		mQuest.deleteQuestion(2, questName);
		
		navTool.goToHomePage();
		waitForElementNotPresent(By.linkText(questName));
	}
}
