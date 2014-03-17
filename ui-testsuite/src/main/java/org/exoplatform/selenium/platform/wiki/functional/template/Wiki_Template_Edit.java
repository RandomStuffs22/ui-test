package org.exoplatform.selenium.platform.wiki.functional.template;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Template;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*--
 *-- Author: HaKT
 *-- Date: 19 Dec 2012
 **/
public class Wiki_Template_Edit extends Template{

	ManageAccount magAc;
	Button button;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		button = new Button(driver);
		magAc.signIn("john", DATA_PASS); 
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	 * Qmetry ID: 69751
	 * Case 01: Edit created template
	 * - Create new Template
	 * - Edit template
	 * - Verify result
	 * - Delete template
	 */
	@Test
	public void test01_EditCreatedTemplate(){

		String DATA_TEMPLATE_TITLE="Edit_Template_01";
		String DATA_NEW_TEMPLATE_TITLE="Edited_Template_01";
		String DATA_TEMPLATE_DESC="This is Template 01";
		String DATA_TEMPLATE_CONTENT="Content of New Template 01";
		
		info("Edit created template");
	
		goToTemplateManagement();
	
		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);
		
		goToWikiHome();
	
		goToAddTemplateWikiPage();
	
		waitForAndGetElement(ELEMENT_OLD_TEMPLATE_LINK.replace("${OLD_TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));
	
		//click(ELEMENT_CLOSE_TEMPLATE_LIST);
		button.cancel();

		editTemplate(DATA_TEMPLATE_TITLE, DATA_NEW_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);
	
		waitForAndGetElement(ELEMENT_NEW_TEMPLATE_LINK.replace("${TEMPLATE_TITLE}", DATA_NEW_TEMPLATE_TITLE));
		
		goToWikiHome();
		
		deleteTemplate(DATA_NEW_TEMPLATE_TITLE);
	}
}
