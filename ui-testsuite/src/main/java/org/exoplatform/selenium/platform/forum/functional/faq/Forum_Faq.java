package org.exoplatform.selenium.platform.forum.functional.faq;

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
 * @author phuongdt
 * @date 15/11/2013
 *
 */
public class Forum_Faq extends FAQ {

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
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Browse single Category
	 * CaseID 72232
	 * Step 1: Create Categories, questions and answers
	 * Step 2: Browse category
	 */
	@Test
	public void test01_BrowseSingleCategory() {
		/*Declare variables*/
		String categoryName = "FAQ category 72232";
		String description = "Add new category for answer";	
		String questionName = "FAQ question 72232";
		String questionContent = "Add new question for category";
		String answerContent = "FAQ answer 72232";

		/* Step 1: Create Categories, questions and answers */
		//- Goto Answer porlet
		//- Create categories, Questions and Answers
		//Categories, Question, Answer are added successful
		info("Add new category and new question, answer");
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		click(By.linkText(questionName));
		magAns.answerQuestion(2, questionName, null, answerContent, true, true, false, null, false, null);

		/* Step 2: Browse category */
		//- Goto FAQ porlet
		goToFaq();

		//- Added Categories in Answer porlet are displayed in bold text
		waitForAndGetElement(ELEMENT_FAQ_CATEGORY.replace("${cat}", categoryName));
		
		//- Browse category by click on Category's name
		info("Click category to view question");
		click(ELEMENT_FAQ_CATEGORY.replace("${cat}", categoryName));
		
		//- Questions in category is  displayed in link below that category
		waitForAndGetElement(ELEMENT_FAQ_QUESTION.replace("${quest}", questionName));
		waitForAndGetElement(ELEMENT_FAQ_ANSWER.replace("${ans}", answerContent));
		
		/*Clear data*/
		info("-- Clear data --");
		deleteFaqPortlet();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Check view Question
	 * CaseID 72538
	 * Step 1: Create Categories, questions and answers
	 * Step 2: View quesiton
	 */
	@Test
	public void test02_CheckViewQuestion() {
		/*Declare variables*/
		String categoryName = "FAQ category 72538";
		String description = "Add new category for answer";	
		String questionName = "FAQ question 72538";
		String questionContent = "Add new question for category";
		String answerContent = "FAQ answer 72538";
		String elementQuest = ELEMENT_FAQ_QUESTION.replace("${quest}", questionName);

		/* Step 1: Create Categories, questions and answers */
		//- Goto Answer porlet
		//- Add categories, Questions and Answers
		//Categories, Questions, Anwers are added successful
		info("Add new category and new question, answer");
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		click(By.linkText(questionName));
		magAns.answerQuestion(2, questionName, null, answerContent, true, true, false, null, false, null);

		/* Step 2: View quesiton */
		//- Goto FAQ porlet
		goToFaq();

		//- Open Question by click on Question's title
		//- Selected question are displayed in link
		//- Answer of question are on the same page below organized by category with an elegant banner to separate categories
		info("Click category to view question");
		click(ELEMENT_FAQ_CATEGORY.replace("${cat}", categoryName));
		waitForAndGetElement(elementQuest);
		
		info("Click question to view answer");
		goToFQAHome();
		click(elementQuest);
		waitForAndGetElement(ELEMENT_FAQ_ANSWER.replace("${ans}", answerContent));
		
		/*Clear data*/
		info("-- Clear data --");
		deleteFaqPortlet();
		magCat.deleteCategoryInAnswer(categoryName);
	}
	
	/** Check Edit template of FAQ porlet
	 * CaseID 72797
	 * Step 1: Open Edit template form
	 * Step 2: Edit template
	 */
	@Test
	public void test03_CheckEditTemplateOfFAQPorlet() {
		/*Declare variables*/
		String data = "<div>\n <h2>Edit Template 1</h2>\n </div>";

		/*Create data*/
		//- Goto FAQ porlet
		goToFaq();
		
		/* Step 1: Open Edit template form */
		//- Goto Group at Administration at Page Manager â†’ Edit FAQ
		//- Click on Edit template tab
		//Edit template form is shown properties
		/* Step 2: Edit template */
		//- Input valid value in Edit template
		//- Click on Save button
		//- Click Close button
		//- Click Finish button
		//Editing template is updated successful
		waitForElementNotPresent(By.xpath("//h2[text()='Edit Template 1']"));
		goToEditFaqPortlet();
		settingFAQTemplate(data);
		button.close();
		pageE.finishEditLayout();
		waitForAndGetElement(By.xpath("//h2[text()='Edit Template 1']"));
		
		/*Clear data*/
		info("-- Clear data --");
		deleteFaqPortlet();
	}
}
