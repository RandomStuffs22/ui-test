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
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;

/**
 * 
 * @author ThaoPTH
 * @date 30 Aug 2013
 */
public class Forum_Answers_Questions extends AnswerBase {

	ManageAccount magAc;
	AnswerManageCategory magCat;
	AnswerManageQuestion magQuest;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magCat = new AnswerManageCategory(driver);
		magQuest = new AnswerManageQuestion(driver, this.plfVersion);
		magAc.signIn(DATA_USER1, DATA_PASS);
		button = new Button(driver);
		goToAnswer();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 70980 + 70981 + 78982 -> add question, edit question and delete question
	 * 
	 */
	@Test
	public void test01_AddEditDeleteQuestion(){
		String categoryName = "Category1";
		String description = "Add new category for answer";	
		String questionName = "Question1";
		String newQuestionName = "New Question name 1";
		String questionContent = "Add new question for category";
		String questionNewContent = "New content for question";

		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		magQuest.editQuestion(1, questionName, null, newQuestionName, questionNewContent, null, null, true, true, false, null);
				
		magQuest.deleteQuestion(1, newQuestionName);
		
		goToAnwserHome();
		
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**CaseId: 70986 + 70988 -> edit question and delete question in manage question form
	 * 
	 */
	@Test
	public void test02_EditDeleteQuestionInManageAnswerForm() {
		String categoryName = "Category2";
		String description = "Description for category2";
		String questionName = "Question2";
		String newQuestioName = "New question name 2";
		String questionContent = "Content of question 2";
		String questionNewContent = "New content of question2";
		
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		magQuest.goToManageQuestions();
		
		magQuest.editQuestion(3, questionName, null, newQuestioName, questionNewContent, null, null, true, true, false, null);
			
		magQuest.deleteQuestion(3, newQuestioName);
		
		click(magQuest.ELEMENT_MANAGE_QUESTION_CLOSE_BUTTON);

		goToAnwserHome();
		
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/** CaseId: 71094: Active/Deactive Question
	 * 
	 */
	@Test
	public void test03_ActiveDeactiveQuestion() {
		String categoryName = "Category3";
		String description = "Description for category3";
		String questionName = "Question3";
		String questionContent = "Content of question 3";
		
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		magQuest.goToManageQuestions();
		
		info("Deactive question");
		
		magQuest.activeQuestion(questionName, false);
		button.close();
		
		magAc.signOut();
		
		info("Normal user cannot view this question");
		
		viewQuestionWithDemoUser(categoryName,questionName,false);
		
		magAc.signIn(DATA_USER1, DATA_PASS);
		
		goToAnswer();

		magQuest.goToManageQuestions();
		
		info("Active question");
		
		magQuest.activeQuestion(questionName, true);
		button.close();
		
		magAc.signOut();
		
		info("Normal user can view this question");
		
		viewQuestionWithDemoUser(categoryName,questionName,true);
		
		info("Clear data");
		
		magAc.signIn(DATA_USER1, DATA_PASS);
		
		goToAnswer();
				
		magCat.deleteCategoryInAnswer(categoryName);	
	}
	
	/**
	 * CaseId: 71095: Approve/Disapprove question
	 */
	@Test
	public void test04_ApproveDisapproveQuestion() {
		String categoryName = "Category4";
		String description = "Description for category4";
		String questionName = "Question4";
		String questionContent = "Content of question 4";
		
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		magQuest.goToManageQuestions();
		
		info("Disapprove question");
		
		magQuest.approveQuestion(questionName, false);
		button.close();
		magAc.signOut();
		
		info("Normal user cannot view this question");
		
		viewQuestionWithDemoUser(categoryName,questionName,false);
		
		magAc.signIn(DATA_USER1, DATA_PASS);
		
		goToAnswer();

		magQuest.goToManageQuestions();
		
		info("Active question");
		
		magQuest.approveQuestion(questionName, true);
		button.close();
		
		magAc.signOut();
		
		info("Normal user can view this question");
		
		viewQuestionWithDemoUser(categoryName,questionName,true);
		
		info("Clear data");
			
		magAc.signIn(DATA_USER1, DATA_PASS);
		
		goToAnswer();
				
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**
	 * CaseId: 70983: Move question
	 */
	@Test
	public void test05_MoveQuestion () {
		String categoryName1 = "Category5";
		String description1 = "Description for category5";
		String questionName = "Question5";
		String questionContent = "Content of question 5";
		String categoryName2 = "Category6";
		String description2 = "Description for category6";
		String[] userGroup1 = {DATA_USER4};
		
		info("Add category and question");
		
		magCat.addNewCategoryInAnswer(categoryName1, null, description1, 2, userGroup1, true, false);
		
		magQuest.quickAddCategoryAndQuestion(categoryName2, description2, questionName, questionContent);
		
		info("Move question to another cateogory");
		
		magQuest.moveQuestion(1, questionName, categoryName1);
		
		info("Check question in new category");
		
		goToAnwserHome();
		
		magCat.openCategoryInAnswer(categoryName1);

		waitForTextPresent(questionName);
		
		info("Clear data");
		
		magCat.deleteCategoryInAnswer(categoryName1);
		
		magCat.deleteCategoryInAnswer(categoryName2);		
	}
	
	/**
	 * CaseId: 68959: Send question to friend
	 */
	@Test (groups = {"email"})
	public void test06_SendQuestion() {
		String categoryName = "Category6";
		String description = "Description for category6";
		String questionName = "Question6";
		String questionContent = "Content of question 6";
		String email = "exomailtest01@gmail.com";
		By mail = By.xpath("//b[text()= 'Question6']");
		String contentMail = "Hi,/You may be interested in this question:/Question Question6/Details/Content of question 6/Click here for more details.";
		
		info("Add category and question");
		
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		info("Send question");
		
		magQuest.sendQuestion(1, questionName, null, null, email, null, null);
		
		info ("Get windownHandle of current browser");
		
	    String handlesBefore = driver.getWindowHandle();
	    
	  //check mail content
	    
	    goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
	    
	    waitForAndGetElement(mail,150000);
	    
		click(mail);
		
	    checkContentMail(contentMail);
	    
	    info("delete mail");
	    
		click(ELEMENT_DELETE_MAIL_2);
		
		Utils.pause(2000);
		
	    //Switch back to previous browser to delete category
		
		driver.switchTo().window(handlesBefore);
		
		info("Clear data");
		
		magCat.deleteCategoryInAnswer(categoryName);	
	}
	
	/**
	 * CaseId: 68958: Vote question
	 */
	@Test
	public void test07_VoteQuestion() {
		String categoryName = "Category7";
		String description = "Description for category7";
		String questionName = "Question7";
		String questionContent = "Content of question 7";
		By questionLink = By.linkText("Question7");
		
		info("Add category and question");
		
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		click(questionLink);
		
		magQuest.rateQuestion(5);
		
		info("Clear data");
		
		goToAnwserHome();
		
		magCat.deleteCategoryInAnswer(categoryName);		
	}
	
	public void viewQuestionWithDemoUser(String categoryName, String questionName, boolean view){
		magAc.signIn(DATA_USER4, DATA_PASS);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		if (view){
			waitForAndGetElement(By.linkText(questionName));
		}else {
			waitForElementNotPresent(By.linkText(questionName));	
		}
		magAc.signOut();
	}
}