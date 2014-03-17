/**
 * Created by khanhnt at 2:38:23 PM Nov 18, 2013 
 * 
 */
package org.exoplatform.selenium.platform.forum.functional.forum.administration;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author khanhnt
 * @date
 * 
 */
public class Forum_Forum_Administration_BBCode extends ForumManageAdministration{

	ManageAccount acc;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		button = new Button(driver);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Case ID:72274. 
	 * This TC will test to active a BB Code based on its tag name. 
	 * The bb code must be deactived before this test.
	 * Created by khanhnt at Nov 18, 2013
	 */
	@Test
	public void test01_ActivateABBCodeInListAvailableName() {
		info("");
		String tagName = "CODE";
		goToForums();
		goToBBCodeManagement();
		activeBBcode(tagName,true,false);
	}

	/**
	 * Case ID:72810.
	 * This TC will test to add a BB Code with blank example field.
	 * An alert message will show. The method will get it and print out.
	 * Created by khanhnt at Nov 18, 2013
	 */
	@Test
	public void test02_AddABBCodeWhenExampleFieldIsBlank() {
		String tagName = "Tag name test02";
		String replacement = "Char will be replaced";
		String des = "Description of new BB Code";
		String example = "";
		boolean use = false;

		goToForums();
		goToBBCodeManagement();
		addBBCode(tagName,replacement, des,example,use,ADDBBCODE_ACTION.SAVE);
		
	}

	/**
	 * Case ID:72558.
	 * This test will add a BB code with valide data.
	 * Created by khanhnt at Nov 18, 2013
	 */
	@Test
	public void test03_AddABBCodeWhenInputDataValid() {
		String tagName = "Tag name test03";
		String replacement = "Char will be replaced";
		String des = "Description of new BB Code";
		String example = "example";
		boolean use = false;


		goToForums();
		goToBBCodeManagement();
		addBBCode(tagName,replacement, des,example,use,ADDBBCODE_ACTION.SAVE);
		
		waitForAndGetElement(ELEMENT_BBCODE_ADD_BUTTON);
		deleteBBcode(tagName, false,true);
	}

	/**
	 * Case ID:72740.
	 * This TC will test to add a BB Code with blank Replacement field.
	 * An alert message will show. The method will get it and print out.
	 * Created by khanhnt at Nov 18, 2013
	 */
	@Test
	public void test04_AddABBCodeWhenReplacementFieldIsBlank() {
		String tagName = "Tag name test04";
		String replacement = "";
		String des = "Description of new BB Code";
		String example = "example";
		boolean use = false;


		goToForums();
		goToBBCodeManagement();

		addBBCode(tagName,replacement, des,example,use,ADDBBCODE_ACTION.SAVE);
	}


	/**
	 * Case ID:72662.
	 * This TC will test to add a BB Code with blank TagName field.
	 * An alert message will show. The method will get it and print out.
	 * Created by khanhnt at Nov 18, 2013
	 */
	@Test
	public void test05_AddABBCodeWhenTagNameFieldIsBlank() {
		String tagName = "";
		String replacement = "Char will be replaced";
		String des = "Description of new BB Code";
		String example = "example";
		boolean use = false;


		goToForums();
		goToBBCodeManagement();
		addBBCode(tagName,replacement, des,example,use,ADDBBCODE_ACTION.SAVE);
	}

	/**
	 * Case ID:72866.
	 *  This TC will test to input a BB Code with valid data
	 *  Then, cancel it.
	 * Created by khanhnt at Nov 18, 2013
	 */
	@Test
	public void test06_CancelAddingABBCode() {
		String tagName = "Tag name test06";
		String replacement = "Char will be replaced";
		String des = "Description of new BB Code";
		String example = "example";
		boolean use = false;


		goToForums();
		goToBBCodeManagement();

		addBBCode(tagName,replacement, des,example,use,ADDBBCODE_ACTION.CLOSE);
	}

	/**
	 * Case ID:73002.
	 * This TC will cancel deleting a bb code.
	 * Created by khanhnt at Nov 18, 2013
	 */
	@Test
	public void test07_CancelDeletingBBCode() {

		String tagName = "CODE";		
		goToForums();
		goToBBCodeManagement();	
		switchToNewWindow();
		deleteBBcode(tagName, false);		
	}

	/**
	 * Case ID:72435.
	 * This test will deactive a bb code.
	 * Created by khanhnt at Nov 18, 2013
	 */
	@Test
	public void test08_DeactivatedABBCodeInListAvailable() {

		String tagName = "CODE";

		goToForums();
		goToBBCodeManagement();
		activeBBcode(tagName,false,false);
		//actionBBCode(ADDBBCODE_ACTION.SAVE);
	}

	/**
	 * Case ID:73336.
	 * This TC will Delete BB Code.
	 * Created by khanhnt at Nov 18, 2013
	 */
	@Test
	public void test09_DeleteABBCodeWhichIsDefault() {

		String tagName = "CODE";

		goToForums();
		goToBBCodeManagement();
		switchToNewWindow();
		deleteBBcode(tagName, false,true);

	}

	/**
	 * Case ID:73342.
	 * This TC will add a new BB Code and then delete it.
	 * Created by khanhnt at Nov 18, 2013
	 */
	@Test
	public void test10_DeleteBBCodeThatWasInputted() {
		String tagName = "Tag name test10";
		String replacement = "Char will be replaced";
		String des = "Description of new BB Code";
		String example = "example";
		boolean use = false;
		
		goToForums();
		goToBBCodeManagement();

		addBBCode(tagName,replacement, des,example,use,ADDBBCODE_ACTION.SAVE);		
		deleteBBcode(tagName, false,true);
	}

}
