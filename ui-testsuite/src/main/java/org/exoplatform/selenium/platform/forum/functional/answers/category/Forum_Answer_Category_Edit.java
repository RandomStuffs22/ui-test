package org.exoplatform.selenium.platform.forum.functional.answers.category;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageAnwser;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.exoplatform.selenium.platform.forum.ForumPermission;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author thuntn
 * @date 30 Sep 2013
 */
public class Forum_Answer_Category_Edit extends AnswerBase{
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

	/**Edit category in case valid data entry for Moderator field by input
	 * CaseID 72720
	 * This case is fail because of the issue
	 * https://jira.exoplatform.org/browse/FORUM-536
	 */
	@Test(groups={"error"})
	public void test01_EditCategoryWithValidModeratorByInput() {
		String category = "Category 1 72720";
		String newCategory = "New category 72720";
		String[] userGroup = {DATA_USER2};

		info("Edit category in case valid data entry for Moderator field");
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, true, true);
		mCat.editCategoryInAnswer(category, newCategory, null, null, 1, userGroup, true, true);
		assert (getElementFromTextByJquery(category) != null) ;
		mCat.openCategoryInAnswer(category);

		//Check if demo can see the category
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
		mCat.editCategoryInAnswer(newCategory, category, null,null, 0, userGroup, true, true);
		mCat.deleteCategoryInAnswer(category);
	}

	/** Edit category in case valid data entry for Moderator field by select User
	 * CaseID 72720
	 * 
	 */
	@Test
	public void test01_EditCategoryWithValidModeratorBySelectUser() {
		String category = "Category 2 72720";
		String newCategory = "New category 72720";
		String[] userGroup = {DATA_USER2};

		info("Edit category in case valid data entry for Moderator field by select User");

		//Add a category in answer by selecting an user for the Moderator field
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false);

		//Edit category so that mary has not moderator right
		mCat.editCategoryInAnswer(category, newCategory, null, null, 2, userGroup, true, false);

		//Check right of demo
		Acc.userSignIn(userType.DEVELOPER);
		goToAnswer();
		assert (getElementFromTextByJquery(newCategory) == null) ;

		//Check right of mary
		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		assert (getElementFromTextByJquery(newCategory) != null) ;
		mCat.openCategoryInAnswer(newCategory);
		waitForElementNotPresent(mQuest.ELEMENT_MANAGE_QUESTIONS);
		waitForElementNotPresent(mCat.ELEMENT_CATEGORY_BUTTON);

		//Delete data
		Acc.userSignIn(userType.ADMIN);
		goToAnswer();
		mCat.deleteCategoryInAnswer(newCategory);

	}

	/** Edit category in case valid data entry for Moderator field by select group
	 * CaseID 72720
	 * 
	 */
	@Test
	public void test01_EditCategoryWithValidModeratorBySelectGroup() {
		String category = "Category 3 72720";
		String[] userGroup = {"Platform/web-contributors"};
		String newCategory = "New category 72720";

		info("Edit category in case valid data entry for Moderator field by select group");
		//Add a category in answer by selecting a group for the Moderator field
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false);
		mCat.editCategoryInAnswer(category, newCategory, null, null, 3, userGroup, false, true);

		//Check right of demo
		Acc.userSignIn(userType.DEVELOPER);
		goToAnswer();
		assert (getElementFromTextByJquery(newCategory) != null) ;
		mCat.openCategoryInAnswer(newCategory);
		waitForElementNotPresent(mQuest.ELEMENT_MANAGE_QUESTIONS);
		waitForElementNotPresent(mCat.ELEMENT_CATEGORY_BUTTON);

		//Check right of mary and delete data
		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		assert (getElementFromTextByJquery(newCategory) != null) ;
		mCat.openCategoryInAnswer(newCategory);
		waitForAndGetElement(mQuest.ELEMENT_MANAGE_QUESTIONS);
		mCat.editCategoryInAnswer(newCategory, category,null,null, 0, userGroup, true, true);
		mCat.deleteCategoryInAnswer(category);

	}

	/** Edit category in case valid data entry for Moderator field by select membership
	 * CaseID 72720
	 * 
	 */
	@Test
	public void test01_EditCategoryWithValidModeratorBySelectMembership() {
		String category = "Category 4 72720";
		String[] userGroup = {"Development","member"};
		String newCategory = "New category 72720";

		info("Edit category in case valid data entry for Moderator field by select membership");

		//Add a category in answer by selecting a membership for the Moderator field
		mCat.addNewCategoryInAnswer(category, "1", category, 0, userGroup, false, false);
		mCat.editCategoryInAnswer(category, newCategory, null, null, 4, userGroup, false, false);

		//Check right of mary
		Acc.userSignIn(userType.PUBLISHER);
		goToAnswer();

		assert (getElementFromTextByJquery(newCategory) != null) ;
		mCat.openCategoryInAnswer(newCategory);
		waitForElementNotPresent(mQuest.ELEMENT_MANAGE_QUESTIONS);
		waitForElementNotPresent(mCat.ELEMENT_CATEGORY_BUTTON);

		//Check right of John and delete data
		Acc.userSignIn(userType.ADMIN);
		goToAnswer();

		waitForAndGetElement(mQuest.ELEMENT_MANAGE_QUESTIONS);
		mCat.editCategoryInAnswer(newCategory, category, null, null, 0, userGroup, true, true);
		mCat.deleteCategoryInAnswer(category);

	}

	/**Edit category in case invalid data entry for Moderator field
	 * CaseID 72795
	 */
	@Test
	public void test02_EditCategoryWithValidModerator() {
		String category = "Category 72795";
		String[] userGroup = {"test"};

		//		mCat.addNewCategoryInAnswer1(category, "1", category, 1, userGroup, false, false);
		info("Add new category in case invalid data entry for Moderator field");
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, true, true);
		mCat.openCategoryInAnswer(category);
		//Add data into tab Settings
		click(mCat.ELEMENT_CATEGORY_BUTTON);
		click(mCat.ELEMENT_EDIT_CATEGORY_MENU);
		frumPer.configPermission4AnswerCategory(1,userGroup,true,true,true);

		mCat.deleteCategoryInAnswer(category);
	}

	/**Edit category with Require approval on new questions option
	 * CaseID 72854
	 */
	@Test
	public void test03_EditCategoryWithRequireApproveQuestion() {
		String category = "Category 72854";
		String question = "Question 72854";
		String questionContent = "Content of Question 72854";

		info("Edit category with Require approval on new questions option");

		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false, false);
		mCat.editCategoryInAnswer(category, null, null, null, 0, null, false, false,true);

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

	/**Edit category without Require approval on new questions option
	 * CaseID 72906
	 */
	@Test
	public void test04_EditCategoryWithoutRequireApproveQuestion() {
		String category = "Category 72906";
		String question = "Question 72906";
		String questionContent = "Content of Question 72906";

		info("Edit category without Require approval on new questions option");

		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false,true);
		mCat.editCategoryInAnswer(category, null, null, null, 0, null, false, false,false);

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

	/**Edit category with Require approval on new answer option
	 * CaseID 72993
	 */
	@Test
	public void test05_EditCategoryWithRequireApproveAnswer() {
		String category = "Category 72993";
		String question = "Question 72993";
		String questionContent = "Content of Question 72993";
		String answer = "Answer 72993";

		info("Edit category with Require approval on new answer option");
		//Add new category
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false,false,false,false);
		mCat.editCategoryInAnswer(category, null, null, null, 0, null, false, false,false, false, true);

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
		mCat.deleteCategoryInAnswer(category);
	}
	/**Edit category without Require approval on new answer option
	 * CaseID 73030
	 */
	@Test
	public void test06_EditCategoryWithoutRequireApproveAnswer() {
		String category = "Category 73030";
		String question = "Question 73030";
		String questionContent = "Content of Question 73030";
		String answer = "Answer 73030";

		info("Edit category without Require approval on new answer option");
		//Add new category
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false,false,false, true);
		mCat.editCategoryInAnswer(category, null, null, null, 0, null, false, false,false, false, false);

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

	/**Edit category with View Author of Question option
	 * CaseID 73065
	 */
	@Test
	public void test07_EditCategoryWithViewAuthorQuestion() {
		String category = "Category 73065";
		String question = "Question 73065";
		String questionContent = "Content of Question 73065";
		String author = "John Smith";
		String email = "john.smith@acme.exoplatform.com";

		info("Edit category with View Author of Question option");
		//Add new category
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false,false,false, false);
		mCat.editCategoryInAnswer(category, null, null, null, 0, null, false, false,false, true, false);
		//Check when mary submit question in this category
		mCat.openCategoryInAnswer(category);
		mQuest.submitQuestion(null, question, questionContent, null, false, null);
		click(mQuest.ELEMENT_QUESTION_LINK.replace("${question}", question));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_AUTHOR_LABEL.replace("${author}", author ));
		waitForAndGetElement(mQuest.ELEMENT_QUESTION_AUTHOR_EMAIL.replace("${author}", email ));

		//Delete data
		mCat.deleteCategoryInAnswer(category);
	}

	/**Edit category without View Author of Question option
	 * CaseID 73099
	 */
	@Test
	public void test08_EditCategoryWithoutViewAuthorQuestion() {
		String category = "Category 73099";
		String question = "Question 73099";
		String questionContent = "Content of Question 73099";
		String author = "John Smith";
		String email = "john.smith@acme.exoplatform.com";

		info("Edit category without View Author of Question option");
		//Add new category
		mCat.addNewCategoryInAnswer(category, "1", category, 0, null, false, false,false,true, false);
		mCat.editCategoryInAnswer(category, null, null, null, 0, null, false, false,false, false, false);

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


}
