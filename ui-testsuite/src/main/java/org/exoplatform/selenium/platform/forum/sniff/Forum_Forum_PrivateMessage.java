package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author Thuntn
 * @date: 19 Sep 2013
 */
public class Forum_Forum_PrivateMessage extends ForumBase{
	ManageAccount magAc;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		magAc = new ManageAccount(driver);

		magAc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**Compose a private message, Delete a message
	 * caseID: 71181, 71191
	 */
	@Test
	public void test01_ComposePrivateMessage() {
		info("Compose a private message, Delete a message");

		String message = "Message 71181";
		String contentMessage = "Content of Message 71181";

		//Compose a message
		composePrivateMessage(DATA_USER2, message, contentMessage);

		//Check if receiver can receive the message
		loginForum(DATA_USER2);
		goToPrivateMessage();
		checkPrivateMessage(message, contentMessage);
		deletePrivateMessage(message);

		//Delete message
		loginForum(DATA_USER1);
		goToPrivateMessage();
		click(ELEMENT_PRIVATE_MESSAGE_SENT_TAB);
		deletePrivateMessage(message);
	}

	/**Reply a private message
	 * caseID: 71189
	 */
	@Test
	public void test02_ReplyPrivateMessage() {
		info("Reply a private message");

		String message = "Message 71189";
		String contentMessage = "Content of Message 71189";
		String replyMessage = "Reply to message 71189";

		//Compose a message
		composePrivateMessage(DATA_USER2, message, contentMessage);

		//user2 reply the message
		loginForum(DATA_USER2);
		replyPrivateMessage(message, replyMessage);

		//Check if user1 can receive the reply message
		loginForum(DATA_USER1);
		goToPrivateMessage();
		checkPrivateMessage("Reply:" + message, replyMessage);

		//Delete data
		deletePrivateMessage("Reply:" + message);
		click(ELEMENT_PRIVATE_MESSAGE_SENT_TAB);
		deletePrivateMessage(message);

		loginForum(DATA_USER2);
		goToPrivateMessage();
		deletePrivateMessage(message);
		click(ELEMENT_PRIVATE_MESSAGE_SENT_TAB);
		deletePrivateMessage("Reply:" + message);
	}

	/**Forward a message
	 * caseID: 71190
	 */
	@Test
	public void test03_ForwardPrivateMessage() {
		info("Forward a message");

		String message = "Message 71190";
		String contentMessage = "Content of Message 71190";
		String fwdMessage = "Forward to message 71190";

		//Compose a message
		composePrivateMessage(DATA_USER4, message, contentMessage);

		//Forward a message
		forwardPrivateMessage(message, DATA_USER2, fwdMessage);

		//Check if the user2 can receive the forward message
		loginForum(DATA_USER2);
		goToPrivateMessage();
		checkPrivateMessage("Forward:" + message, fwdMessage);
		waitForAndGetElement(ELEMENT_PRIVATE_MESSAGE_CONTENT.replace("${message}", contentMessage));

		//Delete data
		deletePrivateMessage("Forward:" + message);

		//Check if demo can receive the message and delete data
		loginForum(DATA_USER4);
		goToPrivateMessage();
		deletePrivateMessage(message);

		//Delete data
		loginForum(DATA_USER1);
		goToPrivateMessage();
		click(ELEMENT_PRIVATE_MESSAGE_SENT_TAB);
		deletePrivateMessage(message);
		deletePrivateMessage("Forward:" + message);
	}

	public void loginForum(String user){
		magAc.signOut();
		magAc.signIn(user, DATA_PASS);
		goToForums();
	}
}
