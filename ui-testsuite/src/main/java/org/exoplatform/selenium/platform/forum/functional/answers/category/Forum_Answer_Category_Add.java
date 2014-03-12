package org.exoplatform.selenium.platform.forum.functional.answers.category;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageAnwser;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.exoplatform.selenium.platform.forum.ForumPermission;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author thuntn
 * @date 30 Sep 2013
 */
public class Forum_Answer_Category_Add extends AnswerBase{
	ManageAccount Acc;
	AnswerManageCategory mCat;
	AnswerManageQuestion mQuest;
	AnswerManageAnwser mAns;
	ForumPermission frumPer;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		Acc = new ManageAccount(driver);
		mCat = new AnswerManageCategory(driver);
		mQuest = new AnswerManageQuestion(driver);
		mAns = new AnswerManageAnwser(driver);
		frumPer = new ForumPermission(driver);
		Acc.signIn(DATA_USER1, DATA_PASS);
		goToAnswer();
	}
	
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/** Add new category in case valid data entry for Moderator field by inputing value into this field
	 * Check Only user in Restricted Audience has right to view this category
	 * CaseID 72719, 73162
	 * This case is fail because of the issue
	 * https://jira.exoplatform.org/browse/FORUM-536
	 */
	@Test(groups={"error"})
	public void test01_AddCategoryWithValidModeratorByInput() {
		String category = "Category 1 72719";
		String newCategory = "New category 72719";
		String[] userGroup = {DATA_USER2};

		info("Add new category in case valid data entry for Moderator field by inputing value into this field");

		//Add a category in answer by inputing value into the Moderator field
		mCat.addNewCategoryInAnswer(category, "1", category, 1, userGroup, true, true);
		assert (getElementFromTextByJquery(category) != null) ;
		mCat.openCategoryInAnswer(category);

		Acc.userSignIn(userType.DEVELOPER);
		goToAnswer();
		assert (getElementFromTextByJquery(category) == null) ;
		Acc.signOut();

		//Check if mary can access
		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		assert (getElementFromTextByJquery(category) != null) ;
		mCat.openCategoryInAnswer(category);

		//Check moderator right of mary
		waitForAndGetElement(mQuest.ELEMENT_MANAGE_QUESTIONS);
		mCat.editCategoryInAnswer(category, newCategory, "2",newCategory, 1, userGroup, true, true);
		mCat.deleteCategoryInAnswer(newCategory);

	}

	/** Add new category in case valid data entry for Moderator field by select an user into this field
	 * Check Only user in Restricted Audience has right to view this category
	 * CaseID 72719, 73162
	 * 
	 */
	@Test
	public void test01_AddCategoryWithValidModeratorBySelectUser() {
		String category = "Category 2 72719";
		String[] userGroup = {DATA_USER2};

		info("Add new category in case valid data entry for Moderator field by select an user into this field");

		//Add a category in answer by selecting an user for the Moderator field
		mCat.addNewCategoryInAnswer(category, "1", category, 2, userGroup, true, false);
		assert (getElementFromTextByJquery(category) != null) ;
		mCat.openCategoryInAnswer(category);

		//Check right of demo
		Acc.userSignIn(userType.DEVELOPER);
		goToAnswer();
		assert (getElementFromTextByJquery(category) == null) ;

		//Check right of mary
		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		assert (getElementFromTextByJquery(category) != null) ;
		mCat.openCategoryInAnswer(category);
		waitForElementNotPresent(mQuest.ELEMENT_MANAGE_QUESTIONS);
		waitForElementNotPresent(mCat.ELEMENT_CATEGORY_BUTTON);

		//Delete data
		Acc.userSignIn(userType.ADMIN);
		goToAnswer();
		mCat.deleteCategoryInAnswer(category);

	}

	/** Add new category in case valid data entry for Moderator field by select a group into this field
	 * CaseID 72719
	 * 
	 */
	@Test
	public void test01_AddCategoryWithValidModeratorBySelectGroup() {
		String category = "Category 3 72719";
		String[] userGroup = {"Platform/web-contributors"};
		String newCategory = "New category 72719";

		info("Add new category in case valid data entry for Moderator field by select a group into this field");
		//Add a category in answer by selecting a group for the Moderator field
		mCat.addNewCategoryInAnswer(category, "1", category, 3, userGroup, false, true);
		assert (getElementFromTextByJquery(category) != null) ;
		mCat.openCategoryInAnswer(category);

		//Check right of demo
		Acc.userSignIn(userType.DEVELOPER);
		goToAnswer();
		assert (getElementFromTextByJquery(category) != null) ;
		mCat.openCategoryInAnswer(category);
		waitForElementNotPresent(mQuest.ELEMENT_MANAGE_QUESTIONS);
		waitForElementNotPresent(mCat.ELEMENT_CATEGORY_BUTTON);

		//Check right of mary and delete data
		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		assert (getElementFromTextByJquery(category) != null) ;
		mCat.openCategoryInAnswer(category);
		waitForAndGetElement(mQuest.ELEMENT_MANAGE_QUESTIONS);
		mCat.editCategoryInAnswer(category, newCategory, "2",newCategory, 1, userGroup, true, true);
		mCat.deleteCategoryInAnswer(newCategory);

	}

	/** Add new category in case valid data entry for Moderator field by select a membership into this field
	 * CaseID 72719
	 * 
	 */
	@Test
	public void test01_AddCategoryWithValidModeratorBySelectMembership() {
		String category = "Category 4 72719";
		String[] userGroup = {"Development","member"};
		String newCategory = "New category 72719";

		info("Add new category in case valid data entry for Moderator field by select a membership into this field");

		//Add a category in answer by selecting a membership for the Moderator field
		mCat.addNewCategoryInAnswer(category, "1", category, 4, userGroup, false, false);
		assert (getElementFromTextByJquery(category) != null) ;
		mCat.openCategoryInAnswer(category);

		//Check right of mary
		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();

		assert (getElementFromTextByJquery(category) != null) ;
		mCat.openCategoryInAnswer(category);
		waitForElementNotPresent(mQuest.ELEMENT_MANAGE_QUESTIONS);
		waitForElementNotPresent(mCat.ELEMENT_CATEGORY_BUTTON);

		//Check right of John and delete data
		Acc.userSignIn(userType.ADMIN);
		goToAnswer();

		waitForAndGetElement(mQuest.ELEMENT_MANAGE_QUESTIONS);
		mCat.editCategoryInAnswer(category, newCategory, "2",newCategory, 1, userGroup, true, true);
		mCat.deleteCategoryInAnswer(category);

	}
	/** Add new category in case invalid data entry for Moderator field 
	 * CaseID 72794
	 */
	@Test
	public void test02_AddCategoryWithInvalidModerator() {
		String category = "Category 72794";
		String[] userGroup = {"test"};

		info("Add new category in case invalid data entry for Moderator field");
		//Add data into tab Settings
		click(mCat.ELEMENT_CATEGORY_BUTTON);
		click(mCat.ELEMENT_ADD_CATEGORY_LINK);
		mCat.modifyDataInCategory(category, "1", category);

		//Add data in tab Permission
		frumPer.configPermission4AnswerCategory(1,userGroup,true,true,true);

	}

	/**Add new category with Require approval on new questions option
	 * CaseID 72853 
	 */
	@Test
	public void test03_AddCategoryWithRequireApproveQuestion() {
		String category = "Category 72853";
		String question = "Question 72853";
		String questionContent = "Content of Question 72853";

		info("Add new category in case invalid data entry for Moderator field");

		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false,true);

		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		mCat.openCategoryInAnswer(category);
		mQuest.submitQuestion(null, question, questionContent, null, false, null,false);
		waitForMessage(mQuest.MSG_QUESTION_SUBMIT_MODERATOR);
		click(mQuest.ELEMENT_QUESTION_SUBMIT_OK);
		waitForTextPresent(mQuest.MSG_QUESTION_NEED_APPORVE);

		Acc.userSignIn(userType.ADMIN);
		goToAnswer();
		mQuest.goToManageQuestions();
		mQuest.approveQuestion(question, true);

		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		mCat.openCategoryInAnswer(category);
		waitForTextNotPresent(mQuest.MSG_QUESTION_NEED_APPORVE);
		click(mQuest.ELEMENT_QUESTION_LINK.replace("${question}", question));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT.replace("${content}", questionContent));

		Acc.userSignIn(userType.ADMIN);
		goToAnswer();
		mCat.deleteCategoryInAnswer(category);
	}

	/**Add new category with Require approval on new questions option
	 * CaseID 72905
	 */
	@Test
	public void test04_AddCategoryWithoutRequireApproveQuestion() {
		String category = "Category 72905";
		String question = "Question 72905";
		String questionContent = "Content of Question 72905";

		info("Add new category in case invalid data entry for Moderator field");

		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false,false);

		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		mCat.openCategoryInAnswer(category);
		mQuest.submitQuestion(null, question, questionContent, null, false, null);
		click(mQuest.ELEMENT_QUESTION_LINK.replace("${question}", question));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT.replace("${content}", questionContent));

		Acc.userSignIn(userType.ADMIN);
		goToAnswer();
		mCat.deleteCategoryInAnswer(category);
	}

	/**Add new category with Require approval on new answer option
	 * CaseID 72992
	 */
	@Test
	public void test05_AddCategoryWithRequireApproveAnswer() {
		String category = "Category 72992";
		String question = "Question 72992";
		String questionContent = "Content of Question 72992";
		String answer = "Answer 72992";

		info("Add new category with Require approval on new answer option");
		//Add new category
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false,false,false,true);
		mCat.openCategoryInAnswer(category);
		mQuest.submitQuestion(null, question, questionContent, null, false, null);

		//Check when mary submit question in this category
		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		mCat.openCategoryInAnswer(category);
		click(mQuest.ELEMENT_QUESTION_LINK.replace("${question}", question));
		mAns.answerQuestion(2, question, null, answer, true, true, false, null, false, null);
		waitForMessage(mAns.MSG_ANSWER_PENDING);
		click(mAns.ELEMENT_ANSWER_PENDING_OK);
		waitForElementNotPresent(mAns.ELEMENT_ANSWER_CONTENT.replace("${answer}", answer));

		//Delete data
		Acc.userSignIn(userType.ADMIN);
		goToAnswer();
		mCat.openCategoryInAnswer(category);
		click(By.linkText(question));
		waitForAndGetElement(mAns.ELEMENT_ANSWER_CONTENT.replace("${answer}", answer));
		mCat.deleteCategoryInAnswer(category);
	}

	/**Add new category without Require approval on new Answer option
	 * CaseID 73029
	 */
	@Test
	public void test06_AddCategoryWithoutRequireApproveAnswer() {
		String category = "Category 73029";
		String question = "Question 73029";
		String questionContent = "Content of Question 73029";
		String answer = "Answer 73029";

		info("Add new category without Require approval on new Answer option");
		//Add new category
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false,false,false, false);

		//Check when mary submit question in this category
		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		mCat.openCategoryInAnswer(category);
		mQuest.submitQuestion(null, question, questionContent, null, false, null);
		click(mQuest.ELEMENT_QUESTION_LINK.replace("${question}", question));
		mAns.answerQuestion(2, question, null, answer, true, true, false, null, false, null);
		waitForAndGetElement(mAns.ELEMENT_ANSWER_CONTENT.replace("${answer}", answer));

		//Delete data
		Acc.userSignIn(userType.ADMIN);
		goToAnswer();
		mCat.deleteCategoryInAnswer(category);
	}

	/**Add new category with View Author of Question option
	 * CaseID 73064
	 */
	@Test
	public void test07_AddCategoryWithViewAuthorQuestion() {
		String category = "Category 73064";
		String question = "Question 73064";
		String questionContent = "Content of Question 73064";
		String author = "John Smith";
		String email = "john.smith@acme.exoplatform.com";

		info("Add new category with View Author of Question option");
		//Add new category
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false,false,true, false);

		//Check when mary submit question in this category
		mCat.openCategoryInAnswer(category);
		mQuest.submitQuestion(null, question, questionContent, null, false, null);
		click(mQuest.ELEMENT_QUESTION_LINK.replace("${question}", question));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_AUTHOR_LABEL.replace("${author}", author ));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_AUTHOR_EMAIL.replace("${author}", email ));

		//Delete data
		mCat.deleteCategoryInAnswer(category);
	}

	/**Add new category without View Author of Question option
	 * CaseID 73098
	 */
	@Test
	public void test08_AddCategoryWithoutViewAuthorQuestion() {
		String category = "Category 73098";
		String question = "Question 73098";
		String questionContent = "Content of Question 73098";
		String author = "John Smith";
		String email = "john.smith@acme.exoplatform.com";

		info("Add new category without View Author of Question option");
		//Add new category
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false,false,false, false);

		//Check when mary submit question in this category
		mCat.openCategoryInAnswer(category);
		mQuest.submitQuestion(null, question, questionContent, null, false, null);
		click(mQuest.ELEMENT_QUESTION_LINK.replace("${question}", question));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_CONTENT.replace("${content}", questionContent));
		waitForElementNotPresent(mQuest.ELEMENT_QUESTION_AUTHOR_LABEL.replace("${author}", author ));
		waitForElementNotPresent(mQuest.ELEMENT_QUESTION_AUTHOR_EMAIL.replace("${author}", email ));

		//Delete data
		mCat.deleteCategoryInAnswer(category);
	}

	public void goToAnswerPage(userType user){
		Acc.userSignIn(user);

		goToAnswer();
	}
}
