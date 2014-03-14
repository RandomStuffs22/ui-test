package org.exoplatform.selenium.platform.wiki.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Template;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 8-July-2013
 * @author lientm
 */
public class Wiki_WikiSetting_ManageTemplate extends Template {
	
	ManageAccount magAc;
	Button but;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		but = new Button(driver);
		magAc.signIn(DATA_USER1, DATA_PASS); 
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 70253 + 70255 + 70262
	 * Add, edit and delete template
	 */
	@Test
	public void test01_AddEditDeleteTemplate(){
		String title = "Wiki template title 01";
		String description = "Global";
		String content = "Wiki template content 01";
		String newTitle = "Wiki template title update 01";
		String newContent = "Wiki template content update 01";

		info("Create new template");
		addTemplate(title, description, content);
		
		info("Edit template");
		editTemplate(title, newTitle, null, newContent);
				
		deleteTemplate(newTitle);
	}
	
	/**CaseId: 70256
	 * Preview template
	 */
	@Test
	public void test02_PreviewTemplate(){
		String title = "Wiki template title 02";
		String description = "Global";
		String content = "Wiki template content 02";
		
		goToTemplateManagement();
		click(ELEMENT_ADD_TEMPLATE_LINK);
		type(ELEMENT_TITLE_TEMPLATE_INPUT,title,true);
		type(ELEMENT_DESC_TEMPLATE_INPUT,description,true);
		type(ELEMENT_CONTENT_TEMPLATE_INPUT,content,true);
		
		info("Preview this template");
		click(ELEMENT_PREVIEW_TEMPLATE);
		waitForTextPresent(content);
		click(ELEMENT_CLOSE_PREVIEW_WINDOW);
		click(ELEMENT_CANCEL_ADD_TEMPLATE);
	}
}
