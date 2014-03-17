package org.exoplatform.selenium.platform.wiki.functional.template;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Template;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*--
 *-- Author: HaKT
 *-- Date: 17 Dec 2012
 **/
public class Wiki_Template_Add extends Template{

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
	 * Qmetry ID: 69730
	 * Case 01: Create new template
	 * - Create new Template
	 * - Go to Wiki home
	 * - Select add page from template
	 * - Verify new template is in list
	 * - Delete template
	 */
	@Test
	public void test01_createNewTemplate(){

		String DATA_TEMPLATE_TITLE="New_Template_01";
		String DATA_TEMPLATE_DESC="This is New Template 01";
		String DATA_TEMPLATE_CONTENT="Content of New Template 01";

		info("Create new template");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);

		goToWikiHome();

		goToAddTemplateWikiPage();

		waitForAndGetElement(ELEMENT_NEW_TEMPLATE_LINK.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		click(ELEMENT_CLOSE_TEMPLATE_LIST);

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Qmetry ID: 69722 (Wiki > Basic Action > Add)
	 * Case 08: Create new template with bold effect
	 * - Create new Template
	 * - Go to Wiki home
	 * - Select add page from template
	 * - Verify new template is in list, preview to see bold effect
	 * - Delete template
	 */
	@Test
	public void test08_createNewTemplateWithBoldEffect(){
		String DATA_TEMPLATE_TITLE="New_Template_08";
		String DATA_TEMPLATE_DESC="This is New Template 08";
		String DATA_TEMPLATE_CONTENT="Content_In_Bold";
		String DATA_TEMPLATE_CONTENT_IN_BOLD="**"+DATA_TEMPLATE_CONTENT+"**";

		info("Create new templatewith bold effect");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT_IN_BOLD);

		goToWikiHome();

		goToAddTemplateWikiPage();

		click(By.xpath(ELEMENT_PREVIEW_NEW_TEMPLATE.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE)));

		waitForAndGetElement(VERIFY_BOLD_EFFECT.replace("${TEMPLATE_CONTENT}", DATA_TEMPLATE_CONTENT));

		//click(ELEMENT_CLOSE_TEMPLATE_LIST);
		click(ELEMENT_CLOSE_PREVIEW_WINDOW);

		waitForElementNotPresent(ELEMENT_CLOSE_PREVIEW_WINDOW);

		button.cancel();

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Qmetry ID: 69725 (Wiki > Basic Action > Add)
	 * Case 09: Create new template with italic effect
	 * - Create new Template
	 * - Go to Wiki home
	 * - Select add page from template
	 * - Verify new template is in list, preview to see italics effect
	 * - Delete template
	 */
	@Test
	public void test09_createNewTemplateWithItalicEffect(){

		String DATA_TEMPLATE_TITLE="New_Template_09";
		String DATA_TEMPLATE_DESC="This is New Template 09";
		String DATA_TEMPLATE_CONTENT="Content_In_Italic";
		String DATA_TEMPLATE_CONTENT_IN_ITALIC="//"+DATA_TEMPLATE_CONTENT+"//";

		info("Create new template with italic effect");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT_IN_ITALIC);

		goToWikiHome();

		goToAddTemplateWikiPage();

		click(ELEMENT_PREVIEW_NEW_TEMPLATE.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		waitForAndGetElement(VERIFY_ITALIC_EFFECT.replace("${TEMPLATE_CONTENT}", DATA_TEMPLATE_CONTENT));

		click(ELEMENT_CLOSE_PREVIEW_WINDOW);

		waitForElementNotPresent(ELEMENT_CLOSE_PREVIEW_WINDOW);

		button.cancel();

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Qmetry ID: 69728 (Wiki > Basic Action > Add)
	 * Case 010: Create new template with strike effect
	 * - Create new Template
	 * - Go to Wiki home
	 * - Select add page from template
	 * - Verify new template is in list, preview to see strike effect
	 * - Delete template
	 */
	@Test
	public void test10_createNewTemplateWithStrikeEffect(){

		String DATA_TEMPLATE_TITLE="New_Template_10";
		String DATA_TEMPLATE_DESC="This is New Template 10";
		String DATA_TEMPLATE_CONTENT="Content_In_Strike";
		String DATA_TEMPLATE_CONTENT_IN_STRIKE="--"+DATA_TEMPLATE_CONTENT+"--";

		info("Create new template with strike effect");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT_IN_STRIKE);

		goToWikiHome();

		goToAddTemplateWikiPage();

		click(ELEMENT_PREVIEW_NEW_TEMPLATE.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		waitForAndGetElement(VERIFY_STRIKE_EFFECT.replace("${TEMPLATE_CONTENT}", DATA_TEMPLATE_CONTENT));

		click(ELEMENT_CLOSE_PREVIEW_WINDOW);

		waitForElementNotPresent(ELEMENT_CLOSE_PREVIEW_WINDOW);

		button.cancel();

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Qmetry ID: 69729 (Wiki > Basic Action > Add)
	 * Case 011: Create new template with underline effect
	 * - Create new Template
	 * - Go to Wiki home
	 * - Select add page from template
	 * - Verify new template is in list, preview to see underline effect
	 * - Delete template
	 */
	@Test
	public void test11_createNewTemplateWithUnderlineEffect(){

		String DATA_TEMPLATE_TITLE="New_Template_11";
		String DATA_TEMPLATE_DESC="This is New Template 11";
		String DATA_TEMPLATE_CONTENT="Content_In_Underline";
		String DATA_TEMPLATE_CONTENT_IN_UNDERLINE="__"+DATA_TEMPLATE_CONTENT+"__";

		info("Create new template with underline effect");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT_IN_UNDERLINE);

		goToWikiHome();

		goToAddTemplateWikiPage();

		click(ELEMENT_PREVIEW_NEW_TEMPLATE.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		waitForAndGetElement(VERIFY_UNDERLINE_EFFECT.replace("${TEMPLATE_CONTENT}", DATA_TEMPLATE_CONTENT));

		click(ELEMENT_CLOSE_PREVIEW_WINDOW);

		waitForElementNotPresent(ELEMENT_CLOSE_PREVIEW_WINDOW);

		button.cancel();

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Qmetry ID: 69724 (Wiki > Basic Action > Add)
	 * Case 012: Create new template with heading effect
	 * - Create new Template
	 * - Go to Wiki home
	 * - Select add page from template
	 * - Verify new template is in list, preview to see heading effect
	 * - Delete template
	 */
	@Test
	public void test12_createNewTemplateWithHeadingEffect(){

		String DATA_TEMPLATE_TITLE="New_Template_12";
		String DATA_TEMPLATE_DESC="This is New Template 12";
		String DATA_TEMPLATE_CONTENT="Content_In_Heading_effect";
		String DATA_TEMPLATE_CONTENT_IN_HEADING="="+DATA_TEMPLATE_CONTENT+"=";

		info("Create new template with heading effect");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT_IN_HEADING);

		goToWikiHome();

		goToAddTemplateWikiPage();

		click(ELEMENT_PREVIEW_NEW_TEMPLATE.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		waitForAndGetElement(VERIFY_HEADING_EFFECT.replace("${TEMPLATE_CONTENT}", DATA_TEMPLATE_CONTENT));

		click(ELEMENT_CLOSE_PREVIEW_WINDOW);

		waitForElementNotPresent(ELEMENT_CLOSE_PREVIEW_WINDOW);

		button.cancel();

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Qmetry ID: 69723 (Wiki > Basic Action > Add)
	 * Case 015: Create new template with bullet list effect
	 * - Create new Template
	 * - Go to Wiki home
	 * - Select add page from template
	 * - Verify new template is in list, preview to see bullet list effect
	 * - Delete template
	 */
	@Test
	public void test15_createNewTemplateWithBulletListEffect(){

		String DATA_TEMPLATE_TITLE="New_Template_15";
		String DATA_TEMPLATE_DESC="Bullet_effect_template";
		String DATA_TEMPLATE_CONTENT="aaa/bbb";
		String[] temp = DATA_TEMPLATE_CONTENT.split("/");
		String DATA_TEMPLATE_CONTENT_IN_BULLET="* "+ temp[0] + "\n* " + temp[1];

		info("Create new template with bullet effect");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT_IN_BULLET);

		goToWikiHome();

		goToAddTemplateWikiPage();

		click(ELEMENT_PREVIEW_NEW_TEMPLATE.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		waitForAndGetElement(ELEMENT_VERIFY_BULLET_EFFECT.replace("${temp0}", temp[0]).replace("${temp1}", temp[1]));

		click(ELEMENT_CLOSE_PREVIEW_WINDOW);

		waitForElementNotPresent(ELEMENT_CLOSE_PREVIEW_WINDOW);

		button.cancel();

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Qmetry ID: 69727 (Wiki > Basic Action > Add)
	 * Case 016: Create new template with number list effect
	 * - Create new Template
	 * - Go to Wiki home
	 * - Select add page from template
	 * - Verify new template is in list, preview to see number list effect
	 * - Delete template
	 */
	@Test
	public void test16_createNewTemplateWithNumberListEffect(){

		String DATA_TEMPLATE_TITLE="New_Template_16";
		String DATA_TEMPLATE_DESC="Number_effect_template";
		String DATA_TEMPLATE_CONTENT="aaa/bbb";
		String[] temp = DATA_TEMPLATE_CONTENT.split("/");
		String DATA_TEMPLATE_CONTENT_IN_NUMBER="1. "+ temp[0] + "\n11. " + temp[1];

		info("Create new template with number effect");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT_IN_NUMBER);

		goToWikiHome();

		goToAddTemplateWikiPage();

		click(ELEMENT_PREVIEW_NEW_TEMPLATE.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		waitForAndGetElement(ELEMENT_VERIFY_NUMBER_EFFECT.replace("${temp1}", temp[1]));

		click(ELEMENT_CLOSE_PREVIEW_WINDOW);

		waitForElementNotPresent(ELEMENT_CLOSE_PREVIEW_WINDOW);

		button.cancel();

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Qmetry ID: 69721 (Wiki > Basic Action > Add)
	 * Case 017: Create new template with table effect
	 * - Create new Template
	 * - Go to Wiki home
	 * - Select add page from template
	 * - Verify new template is in list, preview to see table effect
	 * - Delete template
	 */
	@Test
	public void test17_createNewTemplateWithTableEffect(){

		String DATA_TEMPLATE_TITLE="New_Template_17";
		String DATA_TEMPLATE_DESC="Table_effect_template";
		String DATA_TEMPLATE_CONTENT="aaa/bbb/ccc/ddd";
		String[] temp = DATA_TEMPLATE_CONTENT.split("/");
		String DATA_TEMPLATE_CONTENT_IN_TABLE="|=" + temp[0] + "|=" + temp[1] + "\n|" + temp[2] + "|" + temp[3];

		info("Create new template with table effect");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT_IN_TABLE);

		goToWikiHome();

		goToAddTemplateWikiPage();

		click(ELEMENT_PREVIEW_NEW_TEMPLATE.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		waitForAndGetElement(ELEMENT_VERIFY_TABLE_EFFECT.replace("${temp0}", temp[0]).replace("${temp3}", temp[3]));

		click(ELEMENT_CLOSE_PREVIEW_WINDOW);

		waitForElementNotPresent(ELEMENT_CLOSE_PREVIEW_WINDOW);

		button.cancel();

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Qmetry ID: 69726 (Wiki > Basic Action > Add)
	 * Case 018: Create new template with link effect
	 * - Create new Template
	 * - Go to Wiki home
	 * - Select add page from template
	 * - Verify new template is in list, preview to see link effect
	 * - Delete template
	 */
	@Test
	public void test18_createNewTemplateWithLinkEffect(){

		String DATA_TEMPLATE_TITLE="New_Template_18";
		String DATA_TEMPLATE_DESC="Link_effect_template";
		String DATA_TEMPLATE_CONTENT="http://google.com.vn";
		String DATA_TEMPLATE_CONTENT_IN_LINK="[[" + DATA_TEMPLATE_CONTENT + "]]" ;

		info("Create new template with link effect");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT_IN_LINK);

		goToWikiHome();

		goToAddTemplateWikiPage();

		click(ELEMENT_PREVIEW_NEW_TEMPLATE.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		waitForAndGetElement(ELEMENT_VERIFY_LINK_EFFECT.replace("${TEMPLATE_CONTENT}", DATA_TEMPLATE_CONTENT).replace("${TEMPLATE_CONTENT}", DATA_TEMPLATE_CONTENT));

		click(ELEMENT_CLOSE_PREVIEW_WINDOW);

		waitForElementNotPresent(ELEMENT_CLOSE_PREVIEW_WINDOW);

		button.cancel();

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}
}