package org.exoplatform.selenium.platform.wiki.functional.attachment;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*--
 *-- Author: HaKT
 *-- Date: 10 Dec 2012
 **/
/**
 * Migrate to PLF4
 * <li> Update by @author vuna2 </li>
 *
 */
public class Wiki_Attachment_Add extends BasicAction{
	ManageAccount magAcc ;
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
	 * Qmetry ID: 69696
	 * Case 01: Add image file for page
	 * Create a wiki page
	 * Scroll down, click Upload, choose an image
	 * Verify result
	 * * Click Cancel
	 */
	  @Test
      public void test01_AttachAnImageForPage(){

              String DATA_WIKI_PAGE_NAME="Blank_Wiki_Page_With_Image1";
              String DATA_WIKI_PAGE_CONTENT="Blank_Wiki_Page_With_Image_Content";
              String ATTACHMENT_NAME="Winter.jpg";
              String ATTACHMENT_PATH="TestData/"+ATTACHMENT_NAME;
              By ATTACHMENT_FILE_LINK=By.xpath("//*[text()='"+ATTACHMENT_NAME+"']");

              info("Attach one image for wiki page");

              goToAddBlankPage();

              addWikiPageSourceEditor(DATA_WIKI_PAGE_NAME, DATA_WIKI_PAGE_CONTENT);

              attachFileInWiki(ATTACHMENT_PATH, 2);
              
              waitForAndGetElement(ATTACHMENT_FILE_LINK);

              //click(ELEMENT_CANCEL_BUTTON);
              click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
              waitForTextPresent(MESSAGE_CANCEL_CREATE_PAGE);
              click(button.ELEMENT_OK_BUTTON);
      }

	/**
	 * Qmetry Id: 69694
	 * Case 02: Add pdf file for page
	 * Create a wiki page
	 * Scroll down, click Upload, choose a pdf file
	 * Verify result
	 * Click Cancel
	 */
	@Test
	public  void test02_AttachPdfFileForPage(){

		String DATA_WIKI_PAGE_NAME="Blank_Wiki_Page_With_PDFFile";
		String DATA_WIKI_PAGE_CONTENT="Blank_Wiki_Page_With_PDFFile_Content";
		String ATTACHMENT_NAME="KS_Wiki_Attachment_pdffile.pdf";
		String ATTACHMENT_PATH="TestData/"+ATTACHMENT_NAME+"";
		By ATTACHMENT_FILE_LINK=By.xpath("//*[text()='"+ATTACHMENT_NAME+"']");

		info("Attach pdf for wiki page");
		goToAddBlankPage();

		addWikiPageSourceEditor(DATA_WIKI_PAGE_NAME, DATA_WIKI_PAGE_CONTENT);

		attachFileInWiki(ATTACHMENT_PATH, 2);
		waitForAndGetElement(ATTACHMENT_FILE_LINK);

		click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
		//click(ELEMENT_CANCEL_BUTTON);
		waitForTextPresent(MESSAGE_CANCEL_CREATE_PAGE);
		click(button.ELEMENT_OK_BUTTON);
	}


	/**
	 * Qmetry ID: 69695
	 * Case 03: Add txt file for page
	 * Create a wiki page
	 * Scroll down, click Upload, choose a txt file
	 * Verify result
	 * Click Cancel
	 */
	@Test
	public  void test03_AttachTxtFileForPage(){

		String DATA_WIKI_PAGE_NAME="Blank_Wiki_Page_With_txt_File";
		String DATA_WIKI_PAGE_CONTENT="Blank_Wiki_Page_With_txt_File_Content";
		String ATTACHMENT_NAME="KS_WiKi_Attachment_TxtFile.txt";
		String ATTACHMENT_PATH="TestData/"+ATTACHMENT_NAME+"";
		By ATTACHMENT_FILE_LINK=By.xpath("//*[text()='"+ATTACHMENT_NAME+"']");

		info("Attach txt file for wiki page");
		goToAddBlankPage();

		addWikiPageSourceEditor(DATA_WIKI_PAGE_NAME, DATA_WIKI_PAGE_CONTENT);

		attachFileInWiki(ATTACHMENT_PATH, 2);
		waitForAndGetElement(ATTACHMENT_FILE_LINK);

		click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
		//click(ELEMENT_CANCEL_BUTTON);
		waitForTextPresent(MESSAGE_CANCEL_CREATE_PAGE);
		click(button.ELEMENT_OK_BUTTON);
	}

	/**
	 * Qmetry ID: 69698
	 * Case 04: Add office file for page
	 * Create a wiki page
	 * Scroll down, click Upload, choose an office file
	 * Verify result
	 * * Click Cancel
	 */
	@Test
	public  void test04_AttachOfficeFileForPage(){

		String DATA_WIKI_PAGE_NAME="Blank_Wiki_Page_With_Office_File";
		String DATA_WIKI_PAGE_CONTENT="Blank_Wiki_Page_With_Office_File_Content";
		String ATTACHMENT_NAME="KS_Wiki_Attachment_Office_file.doc";
		String ATTACHMENT_PATH="TestData/"+ATTACHMENT_NAME+"";
		By ATTACHMENT_FILE_LINK=By.xpath("//*[text()='"+ATTACHMENT_NAME+"']");

		info("Attach Office file for wiki page");
		goToAddBlankPage();

		addWikiPageSourceEditor(DATA_WIKI_PAGE_NAME, DATA_WIKI_PAGE_CONTENT);

		attachFileInWiki(ATTACHMENT_PATH, 2);
		waitForAndGetElement(ATTACHMENT_FILE_LINK);

		click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
		//click(ELEMENT_CANCEL_BUTTON);
		waitForTextPresent(MESSAGE_CANCEL_CREATE_PAGE);
		click(button.ELEMENT_OK_BUTTON);
	}

	/**
	 * Qmetry ID: 69697
	 * Case 05: Add music file for page
	 * Create a wiki page
	 * Scroll down, click Upload, choose a music file
	 * Verify result
	 * * Click Cancel
	 */
	@Test
	public  void test05_AttachMusicFileForPage(){

		String DATA_WIKI_PAGE_NAME="Blank_Wiki_Page_With_mp3_File";
		String DATA_WIKI_PAGE_CONTENT="Blank_Wiki_Page_With_mp3_File_Content";
		String ATTACHMENT_NAME="KS_Wiki_Attachment_AllMyLove.mp3";
		String ATTACHMENT_PATH="TestData/"+ATTACHMENT_NAME+"";
		By ATTACHMENT_FILE_LINK=By.xpath("//*[text()='"+ATTACHMENT_NAME+"']");

		info("Attach mp3 file for wiki page");
		goToAddBlankPage();

		addWikiPageSourceEditor(DATA_WIKI_PAGE_NAME, DATA_WIKI_PAGE_CONTENT);

		attachFileInWiki(ATTACHMENT_PATH, 2);
		waitForAndGetElement(ATTACHMENT_FILE_LINK);

		click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
		//click(ELEMENT_CANCEL_BUTTON);
		waitForTextPresent(MESSAGE_CANCEL_CREATE_PAGE);
		click(button.ELEMENT_OK_BUTTON);
	}
}