package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.forum.AnswerManageAnwser;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.exoplatform.selenium.platform.forum.FAQ;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author lientm
 * @date 11 Sep 2013
 */
public class Forum_FAQ extends FAQ {

	ManageAccount magAc;
	AnswerManageCategory magCat;
	AnswerManageQuestion magQuest;
	AnswerManageAnwser magAns;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		magAc = new ManageAccount(driver);
		magCat = new AnswerManageCategory(driver);
		magQuest = new AnswerManageQuestion(driver);
		magAns = new AnswerManageAnwser(driver);
		button = new Button(driver);
		pageE = new PageEditor(driver);
		magAc.signIn(DATA_USER1, DATA_PASS);
		goToAnswer();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 68919 + 68920 -> Browser category and view question detail
	 * 
	 */
	@Test
	public void test01_BrowseCategoryQuestion(){
		String categoryName = "FAQ category 01";
		String description = "Add new category for answer";	
		String questionName = "FAQ question 01";
		String questionContent = "Add new question for category";
		String answerContent = "FAQ answer 01";
		String elementQuest = ELEMENT_FAQ_QUESTION.replace("${quest}", questionName);
		
		info("Add new category and new question, answer");
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		click(By.linkText(questionName));
		magAns.answerQuestion(2, questionName, null, answerContent, true, true, false, null, false, null);
		
		goToFaq();
		info("Click category to view question");
		click(ELEMENT_FAQ_CATEGORY.replace("${cat}", categoryName));
		waitForAndGetElement(elementQuest);
		
		info("Click question to view answer");
		goToFQAHome();
		click(elementQuest);
		waitForAndGetElement(ELEMENT_FAQ_ANSWER.replace("${ans}", answerContent));
		
		deleteFaqPortlet();
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/**caseId: 68921 -> Setting FAQ portlet
	 * 
	 */
	@Test
	public void test02_SettingFAQPortlet(){
		String categoryName = "FAQ category 02";
		String description = "Add new category for answer";	
		String questionName = "FAQ question 02";
		String questionContent = "Add new question for category";
		String answerContent = "FAQ answer 02";
		String categoryName2 = "FAQ category 02_2";
		String data = "<div>\n <h2>Edit Template</h2>\n </div>";
		
		info("Add new category and new question, answer");
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		click(By.linkText(questionName));
		magAns.answerQuestion(2, questionName, null, answerContent, true, true, false, null, false, null);
		
		goToAnwserHome();
		magCat.addNewCategoryInAnswer(categoryName2, null, description, 0, null, true, false);
		
		goToFaq();
		goToEditFaqPortlet();
		settingDisplayCategoryScope(categoryName2, false);
		settingFAQTemplate(data);
		button.close();
		pageE.finishEditLayout();
		
		waitForAndGetElement(By.xpath("//h2[text()='Edit Template']"));
		waitForElementNotPresent(ELEMENT_FAQ_CATEGORY.replace("${cat}", categoryName2));
		
		deleteFaqPortlet();
		magCat.deleteCategoryInAnswer(categoryName, false);
		magCat.deleteCategoryInAnswer(categoryName2);
	}
}
