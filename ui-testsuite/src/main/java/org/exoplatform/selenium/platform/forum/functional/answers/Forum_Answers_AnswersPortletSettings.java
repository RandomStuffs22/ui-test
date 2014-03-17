package org.exoplatform.selenium.platform.forum.functional.answers;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import  static org.exoplatform.selenium.TestLogger.info;

/**
 * 
 * @author Thuntn
 * @date 04 Nov 2013
 */
public class Forum_Answers_AnswersPortletSettings extends AnswerBase{

	ManageAccount acc;
	AnswerManageCategory mCat;
	AnswerManageQuestion mQuest;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		mCat = new AnswerManageCategory(driver);
		mQuest = new AnswerManageQuestion(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		button = new Button(driver);

		goToAnswer();
	}
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/** Show Category in Answers portlet settings
	 * CaseID 72393
	 */
	@Test
	public void test01_ShowCategoryInAnswersPortletSettings() {
		String questName1 = "Question 72393" ;
		String cateName1 = "Category72393_1";
		
		info("Show Category in Answers portlet settings");
		mQuest.quickAddCategoryAndQuestion(cateName1, "", questName1, questName1);
		setDisplayCategory(cateName1, true);
		
		//Check mary can see category cateName1
		acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		mCat.openCategoryInAnswer(cateName1);
		waitForAndGetElement(By.linkText(questName1));
		
		//Delete data
		acc.userSignIn(userType.ADMIN);
		goToAnswer();
		mCat.deleteCategoryInAnswer(cateName1);
	}
	
	/** Hide Category in Answers portlet settings
	 * CaseID 72217
	 */
	@Test
	public void test02_HideCategoryInAnswersPortletSettings() {
		String questName1 = "Question 72217" ;
		String cateName1 = "Category72217";
		info("Hide Category in Answers portlet settings");
		
		mQuest.quickAddCategoryAndQuestion(cateName1, "", questName1, questName1);
		setDisplayCategory(cateName1, false);
		
		//Check mary cannot see category cateName1
		acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		assert (getElementFromTextByJquery(cateName1)== null);
		
		//Delete data
		acc.userSignIn(userType.ADMIN);
		goToAnswer();
		setDisplayCategory(cateName1, true);
		mCat.deleteCategoryInAnswer(cateName1);
	}

	/** Enable Users post question in root
	 * CaseID 73141
	 */
	@Test
	public void test03_EnableUsersPostQuestionInRoot() {
		
		info("Enable Users post question in root");
		setDisplayMode(true, true, true, true,true,true,true,true);
		
		//Check mary can see Submit button
		acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		waitForAndGetElement(mQuest.ELEMENT_SUBMIT_QUESTION_BUTTON_AUX);
		click(mQuest.ELEMENT_SUBMIT_QUESTION_BUTTON_AUX);
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_NAME);
		button.cancel();
		
	}

	/** Disable Users post question in root
	 * CaseID 73161
	 */
	@Test
	public void test04_DisableUsersPostQuestionInRoot() {
		
		info("Disable Users post question in root");
		setDisplayMode(true, true, true, true,true,true,true,false);
		
		//Check mary cannot see Submit button
		acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		waitForElementNotPresent(mQuest.ELEMENT_SUBMIT_QUESTION_BUTTON_AUX);
	}
}
