package org.exoplatform.selenium.platform.ecms.wcm;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author vuna2
 *
 */
public class SEO extends EcmsBase{

	public SEO(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	Button button = new Button(driver);
	ManageAlert alt = new ManageAlert(driver);

	//-------SEO Management Form--------------------
	public By ELEMENT_SEO_FORM = By.id("UISEOPopupWindow");
	public By ELEMENT_DESCRIPTION_TEXTAREA = By.id("description");
	public By ELEMENT_KEYWORD_TEXTAREA = By.id("keywords");
	public By ELEMENT_TITLE_INPUT = By.id("title");
	public By ELEMENT_ROBOT_INDEX_COMBOX = By.name("INDEX");
	public By ELEMENT_ROBOT_FOLLOW_COMBOX = By.name("FOLLOW");
	public By ELEMENT_SITE_MAP_CHECKBOX = By.id("sitemap");
	public By ELEMENT_FREQUENCY_COMBOX = By.name("frequency");
	public By ELEMENT_PRIORITY = By.id("priority");
	public By ELEMENT_SEO_MAN_HEADER = By.xpath("//span[text()='SEO Management']");
	public String ELEMENT_SEO_DELETE_ICON = "//a[contains(text(),'${language}')]/../i[@title='Delete']";
	public final By ELEMENT_DESCRIPTION = By.xpath("//meta[@name='description']");
	public final By ELEMENT_KEYWORD = By.xpath("//meta[@name='keywords']");
	public final By ELEMENT_ROBOT = By.xpath("//meta[@name='robots']");
	public final String MSG_SEO_DELETE = "Are you sure you want to delete these SEO data?";
	public final By ELEMENT_DESC_HELP_ICON = By.id("DescriptionHelp");
	public final By ELEMENT_KEY_HELP_ICON = By.xpath("//*[contains(text(), 'Keywords:')]/..//*[@id='DescriptionHelp']");
	public final By ELEMENT_PRI_HELP_ICON = By.id("PriorityHelp");
	public final String ELEMENT_SEO_TOOLTIP = "//*[contains(text(),'${tooltip}')]";


	//-----------SEO management------------------------------------
	/* Function to input data on SEO management Form
	 * @des: data input to description
	 * @key: data input to keyword
	 * @index: option of checkbox robot index
	 * @follow: option of checkbox robot follow
	 * @sitemap: = true: tick, = false: no tick
	 * @fre: option of checkbox frequency
	 * @pri: data input to priority
	 */
	public void inputDataSeo(String des, String key, String index, String follow, boolean sitemap, String fre, String pri){

		waitForAndGetElement(ELEMENT_SEO_FORM);
		waitForAndGetElement(ELEMENT_DESCRIPTION_TEXTAREA).clear();
		waitForAndGetElement(ELEMENT_KEYWORD_TEXTAREA).clear();
		waitForAndGetElement(ELEMENT_PRIORITY).clear();
		
		
		info("Input data on SEO management");
		if (des != ""){
			type(ELEMENT_DESCRIPTION_TEXTAREA, des, true);
		}
		if (key != ""){
			type(ELEMENT_KEYWORD_TEXTAREA, key, true);
		}
		select(ELEMENT_ROBOT_INDEX_COMBOX, index);
		select(ELEMENT_ROBOT_FOLLOW_COMBOX, follow);
		if ((sitemap && waitForAndGetElement(ELEMENT_SITE_MAP_CHECKBOX,5000,0,2).isSelected() == false) || (sitemap == false && waitForAndGetElement(ELEMENT_SITE_MAP_CHECKBOX).isSelected())){
			click(ELEMENT_SITE_MAP_CHECKBOX,2);
		}
		select(ELEMENT_FREQUENCY_COMBOX, fre);
		if (pri != ""){
			type(ELEMENT_PRIORITY, pri, true);
		}
		button = new Button(driver);
		button.save();
		info("finish input data on SEO management");
	}
	
	public void deleteSEO(String language){
		info("-- Deleting Item: --"  + language);
		click(ELEMENT_SEO_DELETE_ICON.replace("${language}", language));
		alt.waitForConfirmation(MSG_SEO_DELETE);
		waitForElementNotPresent(ELEMENT_SEO_DELETE_ICON.replace("${language}", language));
		//click(button.ELEMENT_SAVE_BUTTON);
		button.closeWindow();
		Utils.pause(1000);
	}
}