package org.exoplatform.selenium.platform.wiki.functional.template;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Template;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*--
 *-- Author: HaKT
 *-- Date: 19 Dec 2012
 **/
public class Wiki_Template_Other extends Template{

	ManageAccount magAc;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magAc.signIn("john", DATA_PASS);  
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Case 01: Create new page using new template
	 * - Create new Template
	 * - Go to Wiki home
	 * - Add new page with new template
	 * - Verify result
	 * - Delete Page
	 * - Delete template
	 */
	@Test
	public  void test01_createPageUsingNewTemplate(){

		String DATA_TEMPLATE_TITLE="New_Template_ForPage_01";
		String DATA_TEMPLATE_DESC="This is New Template 01";
		String DATA_TEMPLATE_CONTENT="Content of New Template 01";
		String DATA_PAGE_TITLE="Page_Template_01";
		
		info("Create new template");
		
		goToTemplateManagement();
		
		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);
		
		waitForAndGetElement(ELEMENT_NEW_TEMPLATE_LINK.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		goToWikiHome();
		
		addWikiPageFromTemplate(DATA_PAGE_TITLE, 0, DATA_TEMPLATE_TITLE);
	
		waitForAndGetElement(ELEMENT_VERIFY_PAGE_CONTENT.replace("${TEMPLATE_CONTENT}", DATA_TEMPLATE_CONTENT));
	
		//Delete data
		deleteCurrentWikiPage();
		
		deleteTemplate(DATA_TEMPLATE_TITLE);
	}
}
