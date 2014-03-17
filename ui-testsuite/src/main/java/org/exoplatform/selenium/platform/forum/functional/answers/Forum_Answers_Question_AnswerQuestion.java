package org.exoplatform.selenium.platform.forum.functional.answers;
import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.forum.AnswerBase;
import org.exoplatform.selenium.platform.forum.AnswerManageAnwser;
import org.exoplatform.selenium.platform.forum.AnswerManageCategory;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 07/11/2013
 *
 */
public class Forum_Answers_Question_AnswerQuestion extends AnswerBase{

	ManageAccount magAcc;
	AnswerManageCategory magCat;
	AnswerManageQuestion magQuest;
	AnswerManageAnwser magAns;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		magCat = new AnswerManageCategory(driver);
		magQuest = new AnswerManageQuestion(driver);
		magAns = new AnswerManageAnwser(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
	}
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Answer question in Manage questions form by answer icon
	 * CaseID 72261
	 * Step 1: Create category and question
	 */
	@Test
	public void test01_AnswerQuestionInManageQuestionsFormByAnswerIcon() {
		/*Declare variables*/
		String categoryName = "Category72261";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question72261";
		String questionContent = "Add new question for category";
		/*Step 1: Create category and question*/
		//- Create a category
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);

		//- Create question in that category with [Approved] and [Activated] are checked		
		//- Category and question are created successfully. This question is visible for everyone
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		magQuest.editQuestion(1, questionName, null, questionName, questionContent, null, null, true, true, false, null);
		info("Normal user can view this question");
		magQuest.viewQuestionWithOtherUser(userType.DEVELOPER,categoryName,questionName,true);
		
		/*Clear data*/
		info("-- Clear data --");
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Answer question in Manage questions form
	 * CaseID 72262
	 * Step 2: Open Manage questions form
	 * Step 3: Show list of questions that have not been answered
	 * Step 4: Show Answer  question form
	 * Step 5: Answer question
	 */
	@Test
	public void test02_AnswerQuestionInManageQuestionsForm() {
		/*Declare variables*/
		String categoryName = "Category72262";
		String description = "Add new category for answer";	
		String questionName1 = "Answer_Question722621";
		String questionName2 = "Answer_Question722622";
		String questionContent = "Add new question for category";
		String answerContent = "Answer72262";
		String language = "English";

		/*Create data*/
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName1, questionContent, null, false, null);
		magQuest.submitQuestion(null, questionName2, questionContent, null, false, null);

		/* Step 2: Open Manage questions form */
		//- Login by administrator or moderator
		//- Click [Manage questions] button
		//- [Manage questions] screen is displayed, list all existing questions
		magQuest.goToManageQuestions();

		/* Step 3: Show list of questions that have not been answered */
		//- Click [Pending questions] tab
		//- All questions that waiting for being answered are displayed in list
		magQuest.goToOpenQuestionTab();

		/* Step 4: Show Answer  question form */
		//- Click [Answer] icon corresponding to the question you want to answer
		//- Answer question form appears with the default language
		/* Step 5: Answer question */
		//- Update valid data in fields
		//- Click [Save] button
		//- Answer the question successfully.
		magAns.answerQuestion(3, questionName1, language, answerContent, true, true, false, null, false, null);

		/*Clear data*/
		info("-- Clear data --");
		goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Answer question with valid entry
	 * CaseID 72415
	 * Step 1: Create category and question
	 * Step 2: Show Answer question form
	 * Step 3: Answer question
	 */
	@Test
	public void test03_AnswerQuestionWithValidEntry() {
		/*Declare variables*/
		String categoryName = "Category72415";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question72415";
		String questionContent = "Add new question for category";
		String answerContent = "Answer72415";

		/* Step 1: Create category and question */
		//- Create a category
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);

		//- Create question in that category with [Approved] and [Activated] are checked
		//- Category and question are created successfully. This question is visible for everyone
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		magQuest.editQuestion(1, questionName, null, questionName, questionContent, null, null, true, true, false, null);

		/* Step 2: Show Answer question form */
		//- Right click on question and select [Answer question]
		//	Or click Answer question button in the question panel
		//- Answer question form appears
		/* Step 3: Answer question */
		//- Select language to answer
		//- Input valid data in fields
		//- Click [Save] button
		//- Answer the question successfully with correspondingly selected language.
		//- Its icon is changed from non-answered to answered question.
		info("Answer this question");
		magAns.answerQuestion(1, questionName, null, answerContent, true, true, false, null, false, null);

		/*Clear data*/
		info("-- Clear data --");
		goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Answer question in Manage questions form by language link
	 * CaseID 72426
	 * Step 1: Create category and question
	 * Step 2: Open Manage questions form
	 * Step 3: Show list of questions that have not been answered
	 * Step 4: Show Answer  question form
	 */
	@Test
	public void test04_AnswerQuestionInManageQuestionsFormByLanguageLink() {
		/*Declare variables*/
		String categoryName = "Category72426";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question72426";
		String questionContent = "Add new question for category";
		String answerContent = "Answer72426";
		String language = "English";

		/* Step 1: Create category and question */
		//- Create a category
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);

		//- Create question in that category with [Approved] and [Activated] are checked
		//- Category and question are created successfully. This question is visible for everyone
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		magQuest.editQuestion(1, questionName, null, questionName, questionContent, null, null, true, true, false, null);

		/* Step 2: Open Manage questions form */
		//- Login by administrator or moderator
		//- Click [Mange questions] button
		//- [Manage questions] screen is displayed, list all existing questions
		magQuest.goToManageQuestions();

		/* Step 3: Show list of questions that have not been answered */
		//- Click [Pending questions] tab
		//- All questions that waiting for being answered are displayed in list
		magQuest.goToOpenQuestionTab();

		/* Step 4: Show Answer  question form */
		//- Click on the language which you want to answer
		//- Answer question form appears with the selected language which you want to answer
		/* Step 5: Answer question */
		//- Update valid data in fields
		//- Click [Save] button
		//- Answer the question successfully.
		magAns.answerQuestion(4, questionName, language, answerContent, true, true, false, null, false, null);

		/*Clear data*/
		info("-- Clear data --");
		goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Answer question in case leaving blank at mandatory fields
	 * CaseID 72544
	 * Step 1: Create category and question
	 * Step 2: Show Answer question form
	 * Step 3: Answer question
	 */
	@Test
	public void test05_AnswerQuestionInCaseLeavingBlankAtMandatoryFields() {
		/*Declare variables*/
		String categoryName = "Category72544";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question72544";
		String questionContent = "Add new question for category";
		String answerContent = "";

		/* Step 1: Create category and question */
		//- Create a category
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false);

		//- Create question in that category with [Approved] and [Activated] are checked
		//- Category and question are created successfully. This question is visible for everyone
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		magQuest.editQuestion(1, questionName, null, questionName, questionContent, null, null, true, true, false, null);

		/* Step 2: Show Answer question form */
		//- Right click on question and select [Answer question]
		//Or click Answer question button in the question panel
		//- Answer question form appears
		/* Step 3: Answer question */
		//- Leaving blank at mandatory fields
		//- Click [Save] button
		//- Show message that requires user to input data into required fields 
		info("Answer this question");
		magAns.answerQuestion(1, questionName, null, answerContent, true, true, false, null, false, null);

		/*Clear data*/
		info("-- Clear data --");
		goToAnwserHome();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Approve answer while editing
	 * CaseID 72648
	 * Step 1: Create category and question
	 * Step 2: Show Answer question form
	 * Step 3: Answer question
	 * Step 4: Approve answer
	 */
	@Test
	public void test06_ApproveAnswerWhileEditing() {
		/*Declare variables*/
		String categoryName = "Category72648";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question72648";
		String questionContent = "Add new question for category";
		String answerContent = "answerContent72648";

		/* Step 1: Create category and question */
		//- Create a category
		//- Tick on Moderate answers
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false,false,false,true);

		//- Create question in that category with [Approved] and [Activated] are checked
		//- Category and question are created successfully. This question is visible for everyone
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		magQuest.editQuestion(1, questionName, null, questionName, questionContent, null, null, true, true, false, null);

		/* Step 2: Show Answer question form */
		//- Login as demo
		magAcc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		//- Right click on question and select [Answer question]
		//Or click Answer question button in the question panel
		//- Answer question form appears
		/* Step 3: Answer question */
		//- Input valid data in fields
		//- Click [Save] button
		//- Displaying message notice that the answer is pending for moderation
		info("Answer this question");
		magAns.answerQuestion(1, questionName, null, answerContent, true, true, false, null, false, null);
		waitForMessage(magAns.MSG_ANSWER_PENDING);
		click(magAns.ELEMENT_ANSWER_PENDING_OK);
		waitForElementNotPresent(magAns.ELEMENT_ANSWER_CONTENT.replace("${answer}", answerContent));

		/*Step 4: Approve answer*/
		//- Login as root
		magAcc.userSignIn(userType.ADMIN);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		//- Click on [Approved] icon
		//Or edit answer and tick [Approved]  checkbox
		click(By.linkText(questionName));
		magAns.approveAnswer(answerContent, true);

		//- This answer is visible for everyone
		info("Normal user can view approved answer");
		magAns.viewAnswerWithOtherUser(userType.DEVELOPER,categoryName, questionName, answerContent, true);

		/*Clear data*/
		info("-- Clear data --");
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Disapprove answer while editing
	 * CaseID 72628
	 * Step 1: Create category and question
	 * Step 2: Show Answer question form
	 * Step 3: Answer question
	 * Step 4: Disapprove answer
	 */
	@Test
	public void test07_DisapproveAnswerWhileEditing() {
		/*Declare variables*/
		String categoryName = "Category72628";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question72628";
		String questionContent = "Add new question for category";
		String answerContent = "answerContent72628";

		/* Step 1: Create category and question */
		//- Create a category
		//- Tick on Moderate answers
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false,false,false,true);

		//- Create question in that category with [Approved] and [Activated] are checked
		//- Category and question are created successfully. This question is visible for everyone
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		magQuest.editQuestion(1, questionName, null, questionName, questionContent, null, null, true, true, false, null);

		/* Step 2: Show Answer question form */
		//- Login as demo
		magAcc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		//- Right click on question and select [Answer question]
		//Or click Answer question button in the question panel
		//- Answer question form appears
		/* Step 3: Answer question */
		//- Input valid data in fields
		//- Click [Save] button
		//- Displaying message notice that the answer is pending for moderation
		info("Answer this question");
		magAns.answerQuestion(1, questionName, null, answerContent, true, true, false, null, false, null);
		waitForMessage(magAns.MSG_ANSWER_PENDING);
		click(magAns.ELEMENT_ANSWER_PENDING_OK);
		waitForElementNotPresent(magAns.ELEMENT_ANSWER_CONTENT.replace("${answer}", answerContent));

		/*Step 4: Disapprove answer*/
		//- Login as root
		magAcc.userSignIn(userType.ADMIN);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		//- Click on [Approved] icon
		//Or edit answer and tick [Approved]  checkbox
		click(By.linkText(questionName));
		magAns.approveAnswer(answerContent, false);

		//- This answer is visible for everyone
		info("Normal user can view approved answer");
		magAns.viewAnswerWithOtherUser(userType.DEVELOPER,categoryName, questionName, answerContent, false);

		/*Clear data*/
		info("-- Clear data --");
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Hide answer while editing
	 * CaseID 72858
	 * Step 1: Create category and question
	 * Step 2: Open Answer question form
	 * Step 3: Answer question
	 */
	@Test
	public void test08_HideAnswerWhileEditing() {
		/*Declare variables*/
		String categoryName = "Category72858";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question72858";
		String questionContent = "Add new question for category";
		String answerContent = "answerContent72858";

		/* Step 1: Create category and question */
		//- Create a category
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false,false,false,true);

		//- Create question in that category with [Approved] and [Activated] are checked
		//- Category and question are created successfully. This question is visible for everyone
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		magQuest.editQuestion(1, questionName, null, questionName, questionContent, null, null, true, true, false, null);

		/* Step 2: Open Answer question form */
		//- Login as demo
		magAcc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		//- Right click on question and select [Answer question]
		//Or click Answer question button in the question panel
		//- Answer question form appears
		info("Answer this question");
		magAns.answerQuestion(1, questionName, null, answerContent, true, true, false, null, false, null);
		
		/* Step 3: Answer question */
		//- Login as root
		magAcc.userSignIn(userType.ADMIN);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		click(By.linkText(questionName));
		
		//- Click on [Inactivated] icon
		//Or editing answer and uncheck [Activated] check box
		//- This question is not visible for everyone
		magAns.editAnswer(answerContent, null, answerContent, true, false, false, null, false, null);
		info("Normal user can view approved answer");
		magAns.viewAnswerWithOtherUser(userType.DEVELOPER,categoryName, questionName, answerContent, false);
		
		/*Clear data*/
		info("-- Clear data --");
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
		magCat.deleteCategoryInAnswer(categoryName);
	}

	/** Activate answer while editing
	 * CaseID 73330
	 * Step 1: Create category and question
	 * Step 2: Answer question
	 * Step 3: Inactivate answer
	 * Step 4: check if answer is invisible
	 * Step 5: Activate answer
	 */
	@Test
	public void test09_ActivateAnswerWhileEditing() {
		/*Declare variables*/
		String categoryName = "Category73330";
		String description = "Add new category for answer";	
		String questionName = "Answer_Question73330";
		String questionContent = "Add new question for category";
		String answerContent = "answerContent73330";

		/* Step 1: Create category and question */
		//- Create a category
		magCat.addNewCategoryInAnswer(categoryName, null, description, 0, null, true, false,false,false,true);

		//- Create question in that category with [Approved] and [Activated] are checked
		//- Category and question are created successfully. This question is visible for everyone
		magCat.openCategoryInAnswer(categoryName);
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		magQuest.editQuestion(1, questionName, null, questionName, questionContent, null, null, true, true, false, null);

		/* Step 2: Answer question */
		//- Login as demo
		magAcc.userSignIn(userType.PUBLISHER);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		//- Right click on question and select [Answer question]
		//Or click Answer question button in the question panel
		//- Answer question form appears
		info("Answer this question");
		magAns.answerQuestion(1, questionName, null, answerContent, true, true, false, null, false, null);
		
		/* Step 3: Inactivate answer */
		//- Login as root
		magAcc.userSignIn(userType.ADMIN);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		click(By.linkText(questionName));
		
		//- Click on [Activated] icon
		//Or editing answer and uncheck [Activated] check box
		//- This answer is not visible for everyone
		magAns.editAnswer(answerContent, null, answerContent, true, false, false, null, false, null);
		
		/* Step 4: check if answer is invisible */
		//- Login as demo
		//- Go to answer
		//Cannot see that answer
		info("Normal user can view approved answer");
		magAns.viewAnswerWithOtherUser(userType.DEVELOPER,categoryName, questionName, answerContent, false);
		
		/* Step 5: Activate answer */
		//- Login as root
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
		magCat.openCategoryInAnswer(categoryName);
		click(By.linkText(questionName));
		
		//- Edit that answer, check Activated checkbox
		magAns.editAnswer(answerContent, null, answerContent, true, true, false, null, false, null);
		
		//- Login as demo
		//Can see that answer
		info("Normal user can view approved answer");
		magAns.viewAnswerWithOtherUser(userType.DEVELOPER,categoryName, questionName, answerContent, true);
		
		/*Clear data*/
		info("-- Clear data --");
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToAnswer();
		magCat.deleteCategoryInAnswer(categoryName);
	}
}
