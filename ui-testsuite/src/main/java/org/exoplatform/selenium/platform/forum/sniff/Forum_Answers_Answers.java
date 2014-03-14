package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
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
 * @author lientm
 * @date 26 Aug 2013
 */
public class Forum_Answers_Answers extends AnswerBase {

	ManageAccount magAc;
	AnswerManageCategory magCat;
	AnswerManageQuestion magQuest;
	AnswerManageAnwser magAns;
	AnswerManageComment magCom;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magCat = new AnswerManageCategory(driver);
		magQuest = new AnswerManageQuestion(driver, this.plfVersion);
		magAns = new AnswerManageAnwser(driver, this.plfVersion);
		magCom = new AnswerManageComment(driver, this.plfVersion);
		
		magAc.signIn(DATA_USER1, DATA_PASS);
		goToAnswer();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 70955 + 70957 + 70958 -> answer a question, edit and delete answer
	 * 
	 */
	@Test
	public void test01_AddEditDeleteAnswerForQuestion(){
		String categoryName = "Category70955";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question1";
		String questionContent = "Add new question for category";
		String answerContent = "Answer70955";
		String answerContentEdit = "Update answer content";
		info("Answer a question, edit and delete answer");
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		info("Answer this question");
		click(By.linkText(questionName));
		magAns.answerQuestion(2, questionName, null, answerContent, true, true, false, null, false, null);
		
		info("Edit answer");
		magAns.editAnswer(answerContent, null, answerContentEdit, false, false, false, null, false, null);
		
		info("Delete answer");
		magAns.deleteAnswer(answerContentEdit);
		
		goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**CaseId: 70985 -> Answer a question in Manage Question form
	 * 
	 */
	@Test
	public void test02_AnswerInManageQuestion(){
		String categoryName = "CategoryAnswer2";
		String description = "Add new category for answer";
		
		String questionName = "Answer_Question2";
		String questionContent = "Add new question for category";	
		String answerContent = "AnswerQuestion2";
		info("Answer a question in Manage Question form");
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		info("Go to manage question -> open question tab");
		magQuest.goToManageQuestions();
		magQuest.goToOpenQuestionTab();
		magAns.answerQuestion(3, questionName, null, answerContent, true, true, false, null, false, null);
		waitForElementNotPresent(magQuest.ELEMENT_QUESTION_IN_OPEN_TAB.replace("${question}", questionName));
		click(magQuest.ELEMENT_MANAGE_QUESTION_CLOSE_BUTTON);
		
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**CaseId: 70963 + 70969 + 70970 + 70971 -> Add, edit, delete and promote comment for question
	 * 
	 */
	@Test
	public void test03_AddEditPromoteDeleteComment(){
		String categoryName = "CategoryAnswer3";
		String description = "Add new category for answer";
		
		String questionName = "Answer_Question3";
		String questionContent = "Add new question for category";	
		String comment1 = "CommentQuestion3_1";
		String newComment1 = "CommentQuestion3_1 Update";
		String comment2 = "CommentQuestion3_2";
		info("Add, edit, delete and promote comment for question");
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		click(By.linkText(questionName));
		magCom.addComment4Question(questionName, comment1);
		magCom.addComment4Question(questionName, comment2);
		
		info("Edit comment 1");
		magCom.editComment4Question(comment1, newComment1);
		
		info("Delete comment1");
		magCom.deleteCommentInQuestion(newComment1);
		
		info("Promote as answer comment 2");
		magCom.promoteAsAnswer(comment2);
				
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**
	 * CaseId: 70947 -> Deactive, active an answer
	 */
	@Test
	public void test04_ActiveDeactiveAnswer(){
		String categoryName = "CategoryAnswer4";
		String description = "Add new category for answer";
		String questionName = "Answer_Question4";
		String questionContent = "Add new question for category";
		String answerContent = "AnswerQuestion4";
		
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		info("Answer this question");
		click(By.linkText(questionName));
		magAns.answerQuestion(2, questionName, null, answerContent, true, true, false, null, false, null);
		
		info("Deactive answer");
		magAns.activeAnswer(answerContent, false);
		
		info("Normal user cannot view this answer");
		magAns.viewAnswerWithOtherUser(userType.DEVELOPER,categoryName, questionName, answerContent, false);
		
		info("Active answer");
		magAc.signIn(DATA_USER1, DATA_PASS);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		click(By.linkText(questionName));
		magAns.activeAnswer(answerContent, true);
		
		info("Normal user can view active answer");
		magAns.viewAnswerWithOtherUser(userType.DEVELOPER,categoryName, questionName, answerContent, true);
		
		magAc.signIn(DATA_USER1, DATA_PASS);
		goToAnswer();
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**
	 * CaseId: 70952 -> Disapprove, approve an answer
	 */
	@Test
	public void test05_DisapproveApproveAnAnswer(){
		String categoryName = "CategoryAnswer5";
		String description = "Add new category for answer";
		String questionName = "Answer_Question5";
		String questionContent = "Add new question for category";
		String answerContent = "AnswerQuestion5";
		
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		info("Answer this question");
		click(By.linkText(questionName));
		magAns.answerQuestion(2, questionName, null, answerContent, true, true, false, null, false, null);
		
		info("Disappove answer");
		magAns.approveAnswer(answerContent, false);
		
		info("Normal user cannot view this answer");
		magAns.viewAnswerWithOtherUser(userType.DEVELOPER,categoryName, questionName, answerContent, false);
		
		info("Approve answer");
		magAc.signIn(DATA_USER1, DATA_PASS);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		click(By.linkText(questionName));
		magAns.approveAnswer(answerContent, true);
		
		info("Normal user can view approved answer");
		magAns.viewAnswerWithOtherUser(userType.DEVELOPER,categoryName, questionName, answerContent, true);
		
		magAc.signIn(DATA_USER1, DATA_PASS);
		goToAnswer();
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**
	 * CaseId: 68962 -> Rate and sort Answer
	 */
	@Test
	public void test06_RateAndSortAnswer(){
		String categoryName = "CategoryAnswer6";
		String description = "Add new category for answer";
		String questionName = "Answer_Question6";
		String questionContent = "Add new question for category";
		String answerContent1 = "AnswerQuestion6_1";
		String answerContent2 = "AnswerQuestion6_2";
		String answerContent3 = "AnswerQuestion6_3";
		
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		info("add 3 Answer for this question");
		click(By.linkText(questionName));
		magAns.answerQuestion(2, questionName, null, answerContent1, true, true, false, null, false, null);
		magAns.answerQuestion(2, questionName, null, answerContent2, true, true, false, null, false, null);
		magAns.answerQuestion(2, questionName, null, answerContent3, true, true, false, null, false, null);
		
		info("Rate answer");
		magAns.rateAnswer(answerContent3, true);
		magAns.rateAnswer(answerContent2, false);
		
		info("Sort by rate");
		click(magAns.ELEMENT_SORT_BY_RATE);
		waitForAndGetElement(magAns.ELEMENT_ANSWER_POSITION_IN_LIST.replace("${answer}", answerContent3).replace("${no}", "1"));
		waitForAndGetElement(magAns.ELEMENT_ANSWER_POSITION_IN_LIST.replace("${answer}", answerContent1).replace("${no}", "2"));
		waitForAndGetElement(magAns.ELEMENT_ANSWER_POSITION_IN_LIST.replace("${answer}", answerContent2).replace("${no}", "3"));

		magCat.deleteCategoryInAnswer(categoryName);
	}
}
