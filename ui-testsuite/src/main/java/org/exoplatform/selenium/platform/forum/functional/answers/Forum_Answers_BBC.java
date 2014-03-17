package org.exoplatform.selenium.platform.forum.functional.answers;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.info;

/**
 * 
 * @author Thuntn
 * @date 24 Sep 2013
 */
public class Forum_Answers_BBC extends AnswerBase{

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

		goToAnswer();
	}
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/** Check Bold tag effect
	 * CaseID 72218
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose question with Bold tag
	 */
	@Test
	public void test01_CheckBoldTagEffect() {
		String questName = "Question 72218" ;
		String questContent = "Hello";
		
		info("Check Bold tag effect");
		mQuest.submitQuestion(null, questName, "[b]"+questContent + "[/b]", null, false, null);
		click(By.linkText(questName));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_BOLD.replace("${content}", questContent));
		mQuest.deleteQuestion(2, questName);
	}

	/** Check Italic tag effect
	 * CaseID 72394
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose post with Italic tag
	 */
	@Test
	public void test02_CheckItalicTagEffect() {
		String questName = "Question 72394" ;
		String questContent = "Hello";

		info("Check Italic tag effect");
		mQuest.submitQuestion(null, questName, "[i]"+questContent + "[/i]", null, false, null);
		click(By.linkText(questName));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_ITALIC.replace("${content}", questContent));
		mQuest.deleteQuestion(2, questName);
	}

	/** Check Underline tag effect
	 * CaseID 72530
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose question with Underline tag
	 */
	@Test
	public void test03_CheckUnderlineTagEffect() {
		String questName = "Question 72530" ;
		String questContent = "Hello";

		info("Check Underline tag effect");
		mQuest.submitQuestion(null, questName, "[u]"+questContent + "[/u]", null, false, null);
		click(By.linkText(questName));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_UNDERLINE.replace("${content}", questContent));
		mQuest.deleteQuestion(2, questName);
	}

	/** Check Size tag effect
	 * CaseID 72634
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose question with Size tag
	 */
	@Test
	public void test04_CheckSizeTagEffect() {
		String questName = "Question 72634" ;
		String questContent = "Hello";

		info("Check Size tag effect");
		mQuest.submitQuestion(null, questName, "[size=200]"+questContent + "[/size]", null, false, null);
		click(By.linkText(questName));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_SIZE.replace("${size}", "200").replace("${content}", questContent));
		mQuest.deleteQuestion(2, questName);
	}

	/** Check Color tag effect
	 * CaseID 72718
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose question with Color tag
	 */
	@Test
	public void test05_CheckColorTagEffect() {
		String questName = "Question 72718" ;
		String questContent = "Hello";

		info("Check Color tag effect");
		mQuest.submitQuestion(null, questName, "[color=red]"+questContent + "[/color]", null, false, null);
		click(By.linkText(questName));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_COLOR.replace("${color}", "red").replace("${content}", questContent));
		mQuest.deleteQuestion(2, questName);
	}

	/** Check combined formatting tags
	 * CaseID 72793
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose post with combined tags
	 */
	@Test
	public void test06_CheckCombinedFormatingTag() {
		String questName = "Question 72793" ;
		String questContent = "Hello";

		info("Check combined formatting tags");
		mQuest.submitQuestion(null, questName, "[size=200][color=red][b]"+questContent + "[/b][/color][/size]", null, false, null);
		click(By.linkText(questName));
		waitForAndGetElement("//div[@class='theContent']/p/font[@size='200']/font[@color='red']/strong[text()='"+ questContent+"']");
		mQuest.deleteQuestion(2, questName);
	}

	/** Check Quote tag effect
	 * CaseID 72852
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose question with Quote tag
	 */
	@Test
	public void test07_CheckQuoteTagEffect() {
		String questName = "Question 72852" ;
		String questContent = "Hello";

		info("Check Quote tag effect");
		mQuest.submitQuestion(null, questName, "[quote=test07]"+questContent + "[/quote]", null, false, null);
		click(By.linkText(questName));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_QUOTE.replace("${title}", "test07:").replace("${content}", questContent));
		mQuest.deleteQuestion(2, questName);
	}

	/** Check Code tag effect
	 * CaseID 72904
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose question with Code tag
	 */
	@Test
	public void test08_CheckQuoteTagEffect() {
		String questName = "Question 72904" ;
		String questContent = "Hello";

		info("Check Code tag effect");
		mQuest.submitQuestion(null, questName, "[code]"+questContent + "[/code]", null, false, null);
		click(By.linkText(questName));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_CODE.replace("${content}", questContent));
		mQuest.deleteQuestion(2, questName);
	}

	/** Check Unordered list effect
	 * CaseID 72951
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose question with Unordered list tag
	 */
	@Test
	public void test09_CheckUnorderedListEffect() {
		String questName = "Question 72951" ;
		String[] list = {"item1","item2","item3"};

		info("Check Unordered list effect");
		mQuest.submitQuestion(null, questName, "[list][*]" + list[0] + "[*]" + list[1] + "[*]" + list[2] + "[/list]", null, false, null);
		click(By.linkText(questName));
		for(int i = 0; i < list.length; i ++)
			waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_UNORDER.replace("${item}", list[i]));
		mQuest.deleteQuestion(2, questName);
	}

	/** Check Ordered list effect
	 * CaseID 72991
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose question with Ordered list tag
	 */
	@Test
	public void test10_CheckOrderedListEffect() {
		String questName1 = "Question 1 72991" ;
		String questName2 = "Question 2 72991" ;
		String[] list = {"item1","item2","item3"};
		String[] lista = {"item a","item b","item c"};

		info("Check Ordered list effect");
		mQuest.submitQuestion(null, questName1, "[list=1][*]" + list[0] + "[*]" + list[1] + "[*]" + list[2] + "[/list]", null, false, null);
		mQuest.submitQuestion(null, questName2, "[list=a][*]" + lista[0] + "[*]" + lista[1] + "[*]" + lista[2] + "[/list]", null, false, null);
		click(By.linkText(questName1));
		for(int i = 0; i < list.length; i ++)
			waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_ORDER_NUM.replace("${item}", list[i]));
		click(By.linkText(questName2));
		for(int i = 0; i < list.length; i ++)
			waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_ORDER_LETTER.replace("${item}", lista[i]));
		mQuest.deleteQuestion(2, questName2);
		mQuest.deleteQuestion(1, questName1);
	}

	/** Check Link effect
	 * CaseID 73028
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose question with Link tag
	 */
	@Test
	public void test11_CheckLinkEffect() {
		String questName1 = "Question 1 73028" ;
		String questName2 = "Question 2 73028" ;
		String content1 = "eXo Platform home";
		String content2 = "https://jira.exoplatform.org";
		String url = "http://www.exoplatform.com";

		info("Check Link effect");
		mQuest.submitQuestion(null, questName1, "[url=" + url + "]" + content1 + "[/url]", null, false, null);
		mQuest.submitQuestion(null, questName2, "[url]" + content2 + "[/url]", null, false, null);
		click(By.linkText(questName1));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_LINK.replace("${link}", url).replace("${text}", content1));

		click(By.linkText(questName2));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_LINK.replace("${link}", content2).replace("${text}", content2));

		mQuest.deleteQuestion(2, questName2);
		mQuest.deleteQuestion(1, questName1);
	}

	/** Check Email effect
	 * CaseID 73063
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose question with Email tag
	 */
	@Test
	public void test12_CheckEmailEffect() {
		String questName1 = "Question 1 73063" ;

		info("Check Email effect");
		
		mQuest.submitQuestion(null, questName1, "[email]" + EMAIL_ADDRESS1 + "[/email]", null, false, null);
		click(By.linkText(questName1));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_LINK.replace("${link}", "mailto:"+EMAIL_ADDRESS1).replace("${text}", EMAIL_ADDRESS1));

		mQuest.deleteQuestion(2, questName1);
	}

	/** Check Adding image to a post
	 * CaseID 73097
	 * Step 1: Login and Open Answers application
	 * Step 2: Open Submit question form
	 * Step 3: Compose question with Image tag
	 */
	@Test
	public void test13_CheckAddingImageToPost() {
		String questName1 = "Question 1 73097" ;
		String image = "http://www.google.com/intl/en_ALL/images/logo.gif";

		info("Check Adding image to a post");
		
		mQuest.submitQuestion(null, questName1, "[img]" + image + "[/img]", null, false, null);
		click(By.linkText(questName1));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT_IMAGE.replace("${image}", image));

		mQuest.deleteQuestion(2, questName1);
	}
}
