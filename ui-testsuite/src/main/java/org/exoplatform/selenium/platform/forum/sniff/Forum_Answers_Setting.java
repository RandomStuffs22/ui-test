package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author lientm
 * @date 22 Aug 2013
 */
public class Forum_Answers_Setting extends AnswerBase {

	ManageAccount magAc;
	AnswerManageCategory magCat;
	ForumManageCategory forumCat;
	ForumManageForum forum;
	AnswerManageQuestion question;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver, this.plfVersion);
		magCat = new AnswerManageCategory(driver);
		forumCat = new ForumManageCategory(driver, this.plfVersion);
		forum = new ForumManageForum(driver, this.plfVersion);
		question = new AnswerManageQuestion(driver, this.plfVersion);
		pageE = new PageEditor(driver);
		magAc.signIn("john", DATA_PASS);
		goToAnswer();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 68945 + 71099 -> setting for answer portlet
	 * https://jira.exoplatform.org/browse/FORUM-827 (Cannot show forum in Discussion of Answer portlet setting)
	 */
	@Test(groups="error")
	public void test01_SettingAnswerPortlet(){
		//category1 in answer
		String categoryName1 = "Answersetting689451";
		String description1 = "Add new category for answer";
		String[] userGroup1 = {"demo"};
		
		//category2 in answer
		String categoryName2 = "Answersetting689452";
		String description2 = "Add new category2 for answer";
		String[] userGroup2 = {"Platform/Content Management"};
		
		//question in category1
		String questionName = "Questionsetting689451";
		String questionContent = "Question of Questionsetting1";
		
		//category and forum in forum
		String title = "ForumCat689451";
		String descriptionForum = "Add new category for forum";
		String forumName = "Forum689451";
		String content = "Question move";		
	
		info("Add 2 category at root ans submit a question for category1");
		magCat.addNewCategoryInAnswer(categoryName1, null, description1, 2, userGroup1, true, false);
		magCat.addNewCategoryInAnswer(categoryName2, null, description2, 3, userGroup2, true, true, true, true, true);
		magCat.openCategoryInAnswer(categoryName1);
		question.submitQuestion(null, questionName, questionContent, null, false, null);
		
		goToForums();
		forumCat.addNewCategoryInForum(title, null, 0, null, descriptionForum, 0, null);
		forum.quickAddForum(forumName);
		
		goToEditAnswerPortlet();
		info("Setting display mode");
		settingDisplayMode(true, true, true, true, true, true, true, true);
		
		info("Setting display category scope");
		setDisplayCategoryScoping(categoryName2, false);
		
		info("Setting discussion forum");
		settingDiscussion(true, title + "/" + forumName);
		
		info("Setting email template");
		settingEmailTemplate(3, content);
		click(ELEMENT_CLOSE_SETTING_BUTTON);
		pageE.finishEditLayout();
		
		info("Category of answer is not displayed");
		assert magCat.getElementFromTextByJquery(categoryName2) == null;
		
		info("Check question of answer app is discussed in forum");
		magCat.openCategoryInAnswer(categoryName1);
		click(By.linkText(questionName));
		String handlesBefore = driver.getWindowHandle();
		question.goToDiscussInForum();
		waitForAndGetElement(forum.ELEMENT_FORUM.replace("${forumName}", forumName));
		waitForTextPresent(questionName);	
		click(By.linkText(title));
		forumCat.deleteCategoryInForum(title);
		
		//reset data
		driver.switchTo().window(handlesBefore);
		goToEditAnswerPortlet();
		setDisplayCategoryScoping(categoryName2, true);
		settingDiscussion(false, null);
		settingEmailTemplate(3, "");
		click(ELEMENT_CLOSE_SETTING_BUTTON);
		pageE.finishEditLayout();
		
		magCat.deleteCategoryInAnswer(categoryName1);
		magCat.deleteCategoryInAnswer(categoryName2);
	}
}
