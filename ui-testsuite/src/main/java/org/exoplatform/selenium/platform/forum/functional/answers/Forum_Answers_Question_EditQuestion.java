package org.exoplatform.selenium.platform.forum.functional.answers;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 08/11/2013
 *
 */
public class Forum_Answers_Question_EditQuestion extends AnswerBase {

	ManageAccount magAc;
	AnswerManageCategory magCat;
	AnswerManageQuestion magQuest;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magCat = new AnswerManageCategory(driver);
		magQuest = new AnswerManageQuestion(driver);
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Check activate and inactivate question
	 * CaseID 72260
	 * Step 1: Create categories & question
	 * Step 2: Show question list
	 * Step 3: Inactivate question
	 * Step 4: Activate question
	 */
	@Test
	public void test01_CheckActivateAndInactivateQuestion() {
		/*Declare variables*/
		String categoryName = "Category72260";
		String description = "Add new category for answer";	
		String questionName1 = "Answer_Question722601";
		String questionName2 = "Answer_Question722602";
		String questionContent = "Add new question for category";

		/*Step 1: Create categories & question*/
		//- Login by administrator or moderator
		//- Create categories and questions 
		//- Create categories and questions successfully
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName1, questionContent, null, false, null);
		magQuest.submitQuestion(null, questionName2, questionContent, null, false, null);

		/*Step 2: Show question list*/
		//- Click Manage questions
		magQuest.goToManageQuestions();

		//- Select [All questions] tab
		//- All questions created at step 1 are displayed in list (include answered question/not)
		click(magQuest.ELEMENT_MANAGE_QUESTION_ALL_QUESTIONS_TAB);
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", questionName1));
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", questionName2));

		/*Step 3: Inactivate question*/
		//- Click on 'Yes' link in Activated column to inactivate question
		magQuest.activeQuestion(questionName1, false);
		button.close();

		//- 'Yes' link is changed into 'No'
		//- The question is inactivated. It is  invisible to normal user.
		info("Normal user cannot view this question");
		magQuest.viewQuestionWithOtherUser(userType.DEVELOPER,categoryName,questionName1,false);

		/*Step 4: Activate question*/
		//- Click on 'No' link of the inactivated question in Activated column to activate question
		//- 'No' link is changed into 'Yes'
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
		magQuest.goToManageQuestions();
		magQuest.activeQuestion(questionName1, true);
		button.close();
		
		//- The question is activated. It is visible to normal user (in case it is not checked for approved or unapproved)
		info("Normal user can view this question");
		magQuest.viewQuestionWithOtherUser(userType.DEVELOPER,categoryName,questionName1,true);

		/*Clear data*/
		info("-- Clear data --");
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Check approved and disapprove question
	 * CaseID 72425
	 * Step 1: Create categories & question
	 * Step 2: Show question list
	 * Step 3: Disapprove question
	 * Step 4: Approve question
	 */
	@Test
	public void test02_CheckApprovedAndDisapproveQuestion() {
		/*Declare variables*/
		String categoryName = "Category72425";
		String description = "Add new category for answer";	
		String questionName1 = "Answer_Question724251";
		String questionName2 = "Answer_Question724252";
		String questionContent = "Add new question for category";
		/*Step 1: Create categories & question*/
		//- Login by administrator or moderator
		//- Create categories and questions 
		//- Create categories and questions successfully
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName1, questionContent, null, false, null);
		magQuest.submitQuestion(null, questionName2, questionContent, null, false, null);

		/*Step 2: Show question list*/
		//- Click Manage questions
		magQuest.goToManageQuestions();
		
		//- Select [All questions] tab
		//- All questions created at step 1 are displayed in list (include answered question/not)
		click(magQuest.ELEMENT_MANAGE_QUESTION_ALL_QUESTIONS_TAB);
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", questionName1));
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", questionName2));

		/*Step 3: Disapprove question*/
		//- Click on 'Yes' link in Approved column to disapprove question
		//- 'Yes' link is changed into 'No'
		magQuest.approveQuestion(questionName1, false);
		
		//- The question is disapproved. It is invisible to normal user in case Answers mode is set 'approved'
		info("Normal user cannot view this question");
		magQuest.viewQuestionWithOtherUser(userType.DEVELOPER,categoryName,questionName1,false);
		
		/*Step 4: Approve question*/
		//- Click on 'No' link of the unapproved question in Approved column to approve question
		//- 'No' link is changed into 'Yes'
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
		magQuest.goToManageQuestions();
		magQuest.approveQuestion(questionName1, true);
		
		//- The question is approved. It is visible to normal user (in case it is not checked for activated or inactivated)
		info("Normal user can view this question");
		magQuest.viewQuestionWithOtherUser(userType.DEVELOPER,categoryName,questionName1,true);

		/*Clear data*/
		info("-- Clear data --");
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Edit Question in All question tab of Manage questions form
	 * CaseID 72552
	 * Step 1: Create categories & question
	 * Step 2: Show All questions tab in [Manage questions] main screen
	 * Step 3: Show Edit Question form
	 * Step 4: Update information in fields 
	 */
	@Test
	public void test03_EditQuestionInAllQuestionTabOfManageQuestionsForm() {
		/*Declare variables*/
		String categoryName = "Category72552";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question72552";
		String questionContent = "Add new question for category";
		String newQuestionName = "New Answer_Question72552";
		String questionNewContent = "New content for question";
		
		/*Step 1: Create categories & question*/
		//- Login by administrator or moderator
		//- Create categories and questions 
		//- Create categories and questions successfully
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);

		/*Step 2: Show All questions tab in [Manage questions] main screen*/
		//- Login by administrator or moderator
		//- Click [Manage questions] button
		magQuest.goToManageQuestions();
		//- [Manage questions] screen is displayed with All questions tab by default, list all existing questions
		click(magQuest.ELEMENT_MANAGE_QUESTION_ALL_QUESTIONS_TAB);
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", questionName));
		
		/*Step 3: Show Edit Question form*/
		//- Click [Edit] icon corresponding to question you want to edit in list
		//- [Edit question] screen is shown. Informations of selected question are displayed in form.
		/*Step 4: Update information in fields */
		//- Edit values into fields
		//- Change Approved/Activated status by tick on its check box
		//- Click Save button
		//- Question is edited successfully with new value.
		//- Question status is updated if activated and approved check box is ticked or not.
		magQuest.editQuestion(3, questionName, null, newQuestionName, questionNewContent, null, null, false, false, false, null);
		waitForElementNotPresent(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", questionName));
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", newQuestionName));
		assert !waitForAndGetElement(magQuest.ELEMENT_MANAGE_QUESTION_APPROVE.replace("${question}", newQuestionName)).isSelected();
		assert !waitForAndGetElement(magQuest.ELEMENT_MANAGE_QUESTION_ACTIVATE.replace("${question}", newQuestionName)).isSelected();
		click(magQuest.ELEMENT_MANAGE_QUESTION_CLOSE_BUTTON);
		/*Clear data*/
		info("-- Clear data --");
		goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Edit Question in Pending questions tab of Manage questions form
	 * CaseID 72654-72655
	 * Step 1: Create category and question
	 * Step 2: Show Pending question tab of [Manage questions] main screen
	 * Step 3: Show Edit Question form
	 * Step 4: Update information in fields 
	 * ERROR: Refer: https://jira.exoplatform.org/browse/FORUM-686
	 */
	@Test (groups="error")
	public void test04_EditQuestionInPendingQuestionsTabOfManageQuestionsForm() {
		/*Declare variables*/
		String categoryName = "Category72654";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question72654";
		String questionContent = "Add new question for category";
		String newQuestionName = "New Answer_Question72654";
		String questionNewContent = "New content for question";
		
		/*Step 1: Create category and question*/
		//- Login by administrator/ moderator to create category and questions
		//- Category and question are created successfully. 
		magQuest.quickAddCategoryAndQuestion(categoryName, description, questionName, questionContent);
		
		/*Step 2: Show Pending question tab of [Manage questions] main screen*/
		//- Login by administrator or moderator
		//- Click [Manage questions] button
		magQuest.goToManageQuestions();
		
		//- Click [Pending questions] tab
		click(magQuest.ELEMENT_MANAGE_QUESTION_OPEN_QUESTIONS_TAB);
		
		//- Pending questions tab of [Manage questions] screen is displayed, list all existing questions
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_PENDING_QUESTION_TAB_LIST.replace("${question}", questionName));
		
		/*Step 3: Show Edit Question form*/
		//- Click [Edit] icon corresponding to question you want to edit in list
		//- [Edit question] screen is shown. Informations of selected question are displayed in form.
		/*Step 4: Update information in fields */
		//- Edit values into fields
		//- Change Approved/Activated status by tick on its check box
		//- Click Save button
		//- Question is edited successfully with new value.
		//- Question status is updated if activated and approved check box is ticked or not.
		magQuest.editQuestion(3, questionName, null, newQuestionName, questionNewContent, null, null, false, false, false, null);
		waitForElementNotPresent(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", questionName));
		waitForAndGetElement(magQuest.ELEMENT_DELETE_QUESTION_IN_ALL_QUESTION_TAB_LIST.replace("${question}", newQuestionName));
		assert !waitForAndGetElement(magQuest.ELEMENT_MANAGE_QUESTION_APPROVE.replace("${question}", newQuestionName)).isSelected();
		assert !waitForAndGetElement(magQuest.ELEMENT_MANAGE_QUESTION_ACTIVATE.replace("${question}", newQuestionName)).isSelected();
		click(magQuest.ELEMENT_MANAGE_QUESTION_CLOSE_BUTTON);
		
		/*Clear data*/
		info("-- Clear data --");
		goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName);
	}
}
