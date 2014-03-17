package org.exoplatform.selenium.platform.wiki.functional.attachment;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Migrate to PLF4
 * <li> Update By @author vuna2 </li>
 *
 */
public class Wiki_Attachment_Delete extends BasicAction{
	ManageAccount magAcc;
	Button button;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		button = new Button(driver);
		magAcc.signIn("john", DATA_PASS); 
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	 * Qmetry ID: 69741
	 * Case 01: Delete attach file for page
	 * Create a wiki page
	 * Scroll down, click Upload, choose an image
	 * verify attachment link
	 * Delete attachment
	 * Click Cancel
	 */
	@Test
	public  void test01_DeleteAttachment(){

		String DATA_WIKI_PAGE_NAME="Blank_Wiki_Page_With_Image";
		String DATA_WIKI_PAGE_CONTENT="Blank_Wiki_Page_With_Image_Content";
		String ATTACHMENT_NAME="Winter.jpg";
		String ATTACHMENT_PATH="TestData/"+ATTACHMENT_NAME+"";
		By ATTACHMENT_FILE_LINK=By.xpath("//*[text()='"+ATTACHMENT_NAME+"']");

		info("Delete attachment at wiki page");
		goToAddBlankPage();

		info("--Add a wiki page from blank--");
		addWikiPageSourceEditor(DATA_WIKI_PAGE_NAME, DATA_WIKI_PAGE_CONTENT);
		
		attachFileInWiki(ATTACHMENT_PATH, 2);
		waitForAndGetElement(ATTACHMENT_FILE_LINK);
		
		deleteAnAttachment(ATTACHMENT_NAME);
		waitForElementNotPresent(ATTACHMENT_FILE_LINK);
		
	/**	click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
		//click(ELEMENT_CANCEL_BUTTON);
		waitForTextPresent(MESSAGE_CANCEL_CREATE_PAGE);
		click(button.ELEMENT_OK_BUTTON);*/
	}
}