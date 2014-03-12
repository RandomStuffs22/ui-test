package org.exoplatform.selenium.platform.ecms.contentexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author vuna2
 *
 */
public class ContentTemplate extends EcmsBase{
	public ContentTemplate(WebDriver dr, String...plfVersion) {
		super(dr);
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
	}

	ActionBar actBar = new ActionBar(driver);
	Button button = new Button(driver);
	ManageAlert magAlert = new ManageAlert(driver);

	//Button: Categories > Add Item 
	public final By ELEMENT_CATEGORIES_ADD_ITEM = By.xpath("//*[contains(text(), 'Categories')]/..//*[@title='Add Item']");

	//Select language
	public final By ELEMENT_SELECT_LANGUAGE = By.xpath("//*[@id='UIDocumentForm']//select[@name='content-lang']");

	//Announcement
	public final By ELEMENT_ANNOUNCEMENT_LINK = By.xpath("//*[@class='templateLabel']//*[text()='Announcement']");
	public final By ELEMENT_ANNOUNCEMENT_NAME_TEXTBOX = By.id("name");
	public final By ELEMENT_ANNOUNCEMENT_SUMMARY_FRAME = By.xpath("//*[@id='cke_exo:summary']//iframe");

	//WebLink
	public final By ELEMENT_WEBLINK_LINK = By.xpath("//*[@class='templateLabel']//*[text()='Web Link']");

	//WebContent
	public final By ELEMENT_WEBCONTENT_LINK = By.xpath("//*[@class='templateLabel']//*[text()='Web Content']");
	//public final By ELEMENT_WEBCONTENT_TITLE_TEXTBOX = By.id("title");	
	public final By ELEMENT_WEBCONTENT_NAME_TEXTBOX = By.id("name");	
	public final By ELEMENT_WEBCONTENT_CONTENT_FRAME = By.xpath("//td[contains(@id,'cke_contents_htmlData')]/iframe");
	public final By ELEMENT_WEBCONTENT_CONTENT_FRAME_41 = By.xpath("//div[@id= 'cke_1_contents']/iframe");
	public final By ELEMENT_WEBCONTENT_CONTENT_FRAME_EDIT_41 = By.xpath("//*[contains(@id,'cke_htmlData')]//iframe");
	public final By ELEMENT_WEBCONTENT_ADD_CONTENT_LINK = By.xpath("//*[@title='Insert Content Link']");
	public final By ELEMENT_WEBCONTENT_ILLUSTRATION_TAB = By.xpath("//*[contains(text(),'Illustration')]");
	public final By ELEMENT_WEBCONTENT_UPLOAD_FRAME = By.xpath("//*[contains(@name,'uploadIFrame')]");
	//public final By ELEMENT_WEBCONTENT_FILE_IMAGE = By.name("file");
	public final By ELEMENT_WEBCONTENT_SUMMARY_FRAME = By.xpath("//td[@id='cke_contents_exo:summary']/iframe");
	public final By ELEMENT_WEBCONTENT_SUMMARY_FRAME_41 = By.xpath("//*[@id='cke_exo:summary']//iframe");
	public final By ELEMENT_WEBCONTENT_ADVANCE_TAB = By.xpath("//*[contains(text(),'Advanced')]");
	public final By ELEMENT_WEBCONTENT_CSS_TEXTAREA = By.xpath("//textarea[contains(@id,'ContentCSS')]");
	public final By ELEMENT_WEBCONTENT_JS_TEXTAREA = By.xpath("//textarea[contains(@id,'ContentJS')]");
	public final By ELEMENT_MAXIMIZE_ICON = By.xpath("//*[contains(@class, 'uiIconEcmsExpand')]");
	public final By ELEMENT_MINIMIZE_ICON = By.xpath("//*[contains(@class, 'uiIconEcmsCollapse')]");
	public final By ELEMENT_CHANGE_CONTENT_TYPE = By.xpath("//*[contains(@class, 'ChangeTypeLink')]");

	//Accessible Media
	public final By ELEMENT_ACCESSIBLEMEDIA_LINK = By.xpath("//*[@class='templateLabel']//*[text()='Accessible Media']");


	/*
	 * Added by PhuongDT
	 * Date 30/08/2013
	 */
	//HTML File
	public final By ELEMENT_NEW_HTML_FILE_LINK = By.xpath("//*[@class='templateLabel']//*[text()='HTML File']");
	public final By ELEMENT_HTML_FILE_NAME = By.id("name");
	public final By ELEMENT_HTML_FILE_CONTENT = By.id("contentHtml");
	public final By ELEMENT_HTML_FILE_LANGUAGE = By.className("selectbox");
	public final By ELEMENT_HTML_FILE_CKEDITOR_FRAME = By.xpath("//*[@class='cke_contents']/iframe");
	public final By ELEMENT_HTML_FILE_CKEDITOR_FRAME_41 = By.xpath("//*[@id='cke_contentHtml']//iframe");
	public final By ELEMENT_HTML_FILE_CKEDITOR_FRAME_BODY = By.tagName("body");
	/*End Added*/
	//Contact us document
	public final By ELEMENT_CONTACTUS_LINK = By.xpath("//*[@class='templateLabel']//*[text()='Contact Us']");
	public final By ELEMENT_CONTACTUS_RECIPIENT = By.xpath("//*[@name = 'select_a_recipientFieldName']");
	public final By ELEMENT_CONTACTUS_LANGUAGE = By.xpath("//*[@name = 'content-lang']");
	public final By ELEMENT_CONTACTUS_YOURNAME = By.id("your_nameFieldName");
	public final By ELEMENT_CONTACTUS_YOURADDRESS = By.id("your_addressFieldName");
	public final By ELEMENT_CONTACTUS_EMAIL = By.id("your_email_addressFieldName");
	public final By ELEMENT_CONTACTUS_PHONE = By.id("your_phone_numberFieldName");
	public final By ELEMENT_CONTACTUS_MSG = By.id("your_messageFieldName");

	//Accessible Breadcrumb
	public final By ELEMENT_ACCESSIBLE_BREADCRUMB_LINK = By.xpath("//*[@class='templateLabel']//*[text()='Accessible Breadcrumb']");
	public final By ELEMENT_ACCESSIBLE_NAVIGATION_LINK = By.xpath("//*[@class='templateLabel']//*[text()='Accessible Breadcrumb']");
	public final By ELEMENT_ACCESSIBLE_SEARCH_BOX_LINK = By.xpath("//*[@class='templateLabel']//*[text()='Accessible Site Search Box']");
	public final By ELEMENT_ACCESSIBLE_MAIN_TAB = By.xpath("//*[text()='Main Content']");
	public final By ELEMENT_ACCESSIBLE_MAIN_TAB_NAME = By.id("name");
	public final By ELEMENT_ACCESSIBLE_MAIN_TAB_TITLE = By.id("title");
	public final By ELEMENT_ACCESSIBLE_MAIN_TAB_LANGUAGE = By.className("selectbox");

	//CSS File
	public final By ELEMENT_CSS_FILE_LINK = By.xpath("//*[@class='templateLabel']//*[text()='CSS File']");

	/*End Added*/

	//File
	public final By ELEMENT_NEWFILE_LINK = By.xpath("//*[@class='templateLabel']//*[text()='File']");
	//By.linkText("File");
	public final By ELEMENT_NEWFILE_NAME_TEXTBOX = By.id("name");

	public final By ELEMENT_NEWFILE_CONTENT_FRAME = By.xpath("//*[@id='cke_1_contents']/iframe");
	public final By ELEMENT_NEWFILE_CONTENT_FRAME_41 = By.xpath("//*[@id='cke_contentHtml']//iframe");
	public final By ELEMENT_NEWFILE_TITLE_TEXTBOX = By.id("title0");
	//public final By ELEMENT_NEWFILE_DESC_TEXTBOX = By.id("description0");
	public final By ELEMENT_NEWFILE_DESCRIPTION_TEXTBOX = By.id("description0");
	public final By ELEMENT_NEWFILE_CREATOR_TEXTBOX = By.id("creator0");
	public final By ELEMENT_NEWFILE_SOURCE_TEXTBOX = By.id("source0");
	public final By ELEMENT_NEWFILE_SOURCE_LINK_XPATH = By.xpath("//*[@title='Source']");
	public final By ELEMENT_NEWFILE_SOURCE_TEXTAREA_XPATH = By.xpath("//textarea[@class='cke_source cke_enable_context_menu']");   
	public final By ELEMENT_NEWFILE_FRAME_HTML_TAB_XPATH = By.xpath("//iframe[@class='ECMIframe']");
	public final By ELEMENT_NEWFILE_HTML_TAB_P_XPATH =By.xpath("//html/body/p") ;
	public final By ELEMENT_NEWFILE_TEXT_TAB_XPATH = By.xpath("//*[contains(text(),'View as Plain text')]");
	public final String ELEMENT_NEWFILE_TEXT_TAB_P_CSS = ".textContent>pre";
	public final By ELEMENT_NEWFILE_MIME_COMBOX_ID = By.name("mimetype") ;
	public final By ELEMENT_NEWFILE_TEXTAREA_ID = By.id("contentHtml") ;
	public final By ELEMENT_NEWFILE_TEXTPLAIN = By.id("contentPlain") ;
	public final String ELEMENT_NEWFILE_PRE_CSS = ".content>pre";	

	//Picture on Head Layout
	//exo:pictureOnHeadWebContent is changed to Illustrated Web Content
	public final By ELEMENT_HEAD_LAYOUT_LINK = By.linkText("Illustrated Web Content");
	public final By ELEMENT_HEAD_LAYOUT_NAME_TEXTBOX = By.id("name");
	public final By ELEMENT_HEAD_LAYOUT_TITLE_TEXTBOX = By.id("title");	
	public final By ELEMENT_HEAD_LAYOUT_UPLOAD_FRAME = By.xpath("//iframe[contains(@id,'uploadFrame')]")	;
	public final By ELEMENT_HEAD_LAYOUT_UPLOAD_FILE = By.id("file") ;

	//Product
	public final By ELEMENT_PRODUCT_LINK = By.linkText("Product");
	public final By ELEMENT_PRODUCT_NAME_TEXTBOX = By.id("name") ;

	//public final By ELEMENT_PRODUCT_TITLE_TEXTBOX = By.id("title");
	public final By ELEMENT_PRODUCT_ILLUSTRATION = By.xpath("//input[@name='illustration']");
	public final By ELEMENT_PRODUCT_SUMMARY_FRAME = By.xpath("//*[@id='cke_contents_summary']/iframe");
	public final By ELEMENT_PRODUCT_SUMMARY_FRAME_41 = By.xpath("//*[@id='cke_summary']//iframe");
	public final By ELEMENT_PRODUCT_BENEFIT_FRAME = By.xpath("//*[@id='cke_contents_productBenefits']/iframe");
	public final By ELEMENT_PRODUCT_BENEFIT_FRAME_41 = By.xpath("//*[@id='cke_productBenefits']//iframe");
	public final By ELEMENT_PRODUCT_FEATURE_FRAME = By.xpath("//*[@id='cke_contents_productFeatures']/iframe");
	public final By ELEMENT_PRODUCT_FEATURE_FRAME_41 = By.xpath("//*[@id='cke_productFeatures']//iframe");

	public final By ELEMENT_FREE_CONT_ACCEPT = By.xpath("//form[contains(@id,'EditTextForm')]/a[2]");
	public final By ELEMENT_FREE_CONT_INPUT = By.xpath("//iframe[contains(@title,'Rich text editor, newText')]");
	public final String ELEMENT_FREE_CONT_INLINE = "//div[contains(@id,'CurrentText') and @title='Double-click to edit']";

	public final By ELEMENT_SAMPLE_CONT_ACCEPT = By.xpath("//form[contains(@id,'EditContentForm')]/a[2]");
	public final By ELEMENT_SAMPLE_CONT_INPUT = By.xpath("//textarea[contains(@id,'newContent')]");
	public final String ELEMENT_SAMPLE_CONT_INLINE = "//div[contains(@id,'CurrentContent') and @title='Double-click to edit']";
	public final By ELEMENT_SAMPLE_SUM_ACCEPT = By.xpath("//form[contains(@id,'EditSummaryForm')]/a[2]");
	public final By ELEMENT_SAMPLE_SUM_INPUT = By.xpath("//textarea[contains(@id,'newSummary')]");
	public final String ELEMENT_SAMPLE_SUM_INLINE = "//div[contains(@id,'CurrentSummary') and @title='Double-click to edit']";
	public final By ELEMENT_SAMPLE_TITLE_ACCEPT = By.xpath("//form[contains(@id,'EditTdGtmplTitleForm')]/a[2]");
	public final By ELEMENT_SAMPLE_TITLE_INPUT = By.xpath("//input[contains(@id,'newTdGtmplTitle')]");
	public final String ELEMENT_SAMPLE_TITLE_INLINE = "//div[contains(@id,'CurrentTdGtmplTitle') and @title='Double-click to edit']";

	public final String ELEMENT_MESSAGE_EDIT_INLINE = "An error occurred while modifying the required property.";
	public final String ELEMENT_MESSAGE_EDIT_CLOSE = "The changes you made will be lost if you close this form.";
	public final String ELEMENT_ENABLE_DMS_STRUCTURE = "enableStructure";
	public final By ELEMENT_PIC_IMGWIDTH = By.id("imageWidth");
	public final By ELEMENT_PIC_IMGHEIGHT = By.id("imageHeight");
	public final By ELEMENT_PIC_LANG = By.name("content-lang");
	public final By ELEMENT_PIC_CONT = By.xpath("//iframe[contains(@title,'Rich text editor, htmlData')]");
	public final By ELEMENT_CSS_NAME = By.id("name");
	public final By ELEMENT_CSS_ACTIVE = By.name("activeCSS");
	public final By ELEMENT_CSS_PRIORITY = By.id("CSSpriority");
	public final By ELEMENT_CSS_DATA = By.id("contentHtml");
	public final By ELEMENT_MYSITE_LINK = By.xpath("//a[contains(text(),'My Sites')]");
	public final By ELEMENT_ACME_SITE_LINK = By.linkText("acme");
	public final By ELEMENT_PAGE_TAG_P = By.xpath("//p");

	//JS
	public final By ELEMENT_NEW_JS_FILE_LINK = By.xpath("//*[@class='templateLabel']//*[text()='Javascript File']");
	public final By ELEMENT_JS_NAME = By.id("name");
	public final By ELEMENT_JS_ACTIVE = By.name("activeJS");
	public final By ELEMENT_JS_PRIORITY = By.id("JSpriority");
	public final By ELEMENT_JS_LANGUAGE = By.name("content-lang");
	public final By ELEMENT_JS_DATA = By.id("contentHtml");

	//Data test
	public final String[] DATA_SPECIAL_CHARACTER = {"@","`","~","!","#","$","%","^","&","*","(",")","-","_","+","=","{","}","[","]","|","\\",";",":","'","\"","<",",",".","/","?"};
	public final String[] DATA_SPECIAL_CHARACTER_2 = {"`", "~", "!", "#", "$", "&", "*", "(", ")", "=", "{", "}", "+", ";", "<", ">", "/", "?","\"","\\"};
	public final String DATA_SPECIAL_CHARACTER_STRING = "~`!@#$%^&*()-_=+[]{}\\|;:\",<.>?/";

	//Add New Folder
	public final By ELEMENT_CREATE_FOLDER_BUTTON = By.xpath("//*[text()='Create Folder']");
	public final By ELEMENT_USE_CUSTOM_TYPE_FOLDER = By.id("customTypeCheckBox");
	public final String ELEMENT_VERIFY_FILE_CONTENT = "//*[contains(text(),'${content}')]";

	//Message
	public final String MESSAGE_NAME_REQUIRED_FIELD = "The field \"Name\" is required.";
	public final String MESSAGE_CONTENT_REQUIRED_FIELD = "The field \"Content\" is required.";
	public final String MESSAGE_FIELD_NAME_INVALID_CHARS = "The field 'Name' contains some invalid characters. Please enter another value.";		

	//Web Links
	public final By ELEMENT_LINK_NAME= By.id("name");
	public final By ELEMENT_LINK_URL= By.id("LinkURL");
	public final By ELEMENT_LINK_DESC= By.id("LinkDescription");
	public final By ELEMENT_LINK_LANGUAGE=By.name("content-lang");

	//	Contact Us
	public final By ELEMENT_CONTACT_US_LINK= By.xpath("//*[@class='templateLabel']//*[text()='Contact Us']");
	public final By ELEMENT_YOUR_NAME= By.id("your_nameFieldName");
	public final By ELEMENT_YOUR_ADDRESS= By.id("your_addressFieldName");
	public final By ELEMENT_YOUR_EMAIL= By.id("your_email_addressFieldName");
	public final By ELEMENT_YOUR_PHONE= By.id("your_phone_numberFieldName");
	public final By ELEMENT_YOUR_MESSAGE= By.id("your_messageFieldName");
	public final String ELEMENT_VERIFY_MSG_CONTENT = "//*[contains(text(),'${content}')]";

	//Accessible media
	public final By ELEMENT_ACCESSIBLE_MEDIA_LINK = By.xpath("//i[@data-original-title='Accessible Media']");
	public final By ELEMENT_ACCESSIBLE_MEDIA_ALTERNATIVE_FRAME = By.xpath("//*[@id='cke_contents_alternativeText']/iframe");
	public final By ELEMENT_ACCESSIBLE_MEDIA_TITLE = By.id("title0");
	public final By ELEMENT_ACCESSIBLE_MEDIA_DESC = By.id("description0");

	/*=================== Create a new document/article/file ===================*/
	/* 
	 * Add new article / Kofax / File Plan
	 * == REMOVED IN PLF 4 ==
	 */

	//Template: File > Open [Add Category] Form 
	public void openAddCategoryInFileTemplate(){
		info("-- Opening [Add Category] Form... --");
		if (isElementPresent(ELEMENT_CATEGORIES_ADD_ITEM)){
			click(ELEMENT_CATEGORIES_ADD_ITEM);
		}else{
			click("//*[contains(text(), 'Categories')]/..//*[@data-original-title='Add Item']");
		}
		Utils.pause(500);
	}

	//add new announcement
	public void createNewAnnouncement (String name, String sum){
		click(ELEMENT_ANNOUNCEMENT_LINK);
		type(ELEMENT_ANNOUNCEMENT_NAME_TEXTBOX, name, true);
		inputDataToFrame(ELEMENT_ANNOUNCEMENT_SUMMARY_FRAME,sum,true);
		switchToParentWindow();
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
	}

	//add new Free layout webcontent
	public void createNewWebContent(String name, String cont, String img, String sum, String css, String js, Object...params){
		boolean lines = (Boolean) (params.length > 0 ? params[0]: false);
		String optionLang = (String) (params.length > 1 ? params[1]:"");
		By eWebContentSum ;
		if(this.plfVersion.equalsIgnoreCase("4.1"))
			eWebContentSum = ELEMENT_WEBCONTENT_SUMMARY_FRAME_41;
		else
			eWebContentSum = ELEMENT_WEBCONTENT_SUMMARY_FRAME;
		info("-- Creating a new Web Content --");
		Utils.pause(500);
		click(ELEMENT_WEBCONTENT_LINK);
		type(ELEMENT_WEBCONTENT_NAME_TEXTBOX, name, true);
		if (!optionLang.isEmpty()){
			selectOption(ELEMENT_PIC_LANG, optionLang);
		}
		if (cont != ""){
			info("plf..." + plfVersion);
		if(this.plfVersion.equalsIgnoreCase("4.1"))
		
				inputDataToFrame(ELEMENT_WEBCONTENT_CONTENT_FRAME_41,cont,true);
			else
				inputDataToFrame(ELEMENT_WEBCONTENT_CONTENT_FRAME,cont,true);
			switchToParentWindow();
		}
		if (sum!="" || img !=""){
			click(ELEMENT_WEBCONTENT_ILLUSTRATION_TAB);
			Utils.pause(3000);
			if (img!=""){
				//driver.switchTo().frame(waitForAndGetElement(ELEMENT_WEBCONTENT_UPLOAD_FRAME, 3000, 1, 2));
				//type(ELEMENT_WEBCONTENT_FILE_IMAGE, Utils.getAbsoluteFilePath(img), false);			
				WebElement upload = waitForAndGetElement(ELEMENT_UPLOAD_NAME, DEFAULT_TIMEOUT, 0, 2);
				((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
				upload.sendKeys(Utils.getAbsoluteFilePath(img));
				////
				switchToParentWindow();
				String links[] = img.split("/");
				int length = links.length;
				waitForAndGetElement(By.xpath("//div[contains(text(),'" + links[length-1]+"')]"),DEFAULT_TIMEOUT,1,2);
			}
			if (!lines){
				inputDataToFrame(eWebContentSum, sum);
				switchToParentWindow();
			}else {
				if(waitForAndGetElement(ELEMENT_WEBCONTENT_SUMMARY_FRAME_41,5000,0,1)!=null)
					typeMultiLineInCkeContent(ELEMENT_WEBCONTENT_SUMMARY_FRAME_41, sum);
				else //if(waitForAndGetElement(ELEMENT_WEBCONTENT_SUMMARY_FRAME,5000,0,1)!=null)
					typeMultiLineInCkeContent(ELEMENT_WEBCONTENT_SUMMARY_FRAME, sum);
			}
		}
		if(css!="" || js !=""){
			click(ELEMENT_WEBCONTENT_ADVANCE_TAB);
			type(ELEMENT_WEBCONTENT_CSS_TEXTAREA, css, false);
			type(ELEMENT_WEBCONTENT_JS_TEXTAREA, js, false);
		}
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(3000);
	}

	//Web Content > [Insert content link] 
	public void addContentLinkInFCKEditor(String path, String file){
		String previousWindowHandle = driver.getWindowHandle();
		click(ELEMENT_WEBCONTENT_ADD_CONTENT_LINK);
		switchToNewWindow();

		String[] link = path.split("/");
		for (int i = 0; i < link.length; i ++){
			click(By.id(link[i]));
		}
		click(By.linkText(file));
		driver.switchTo().window(previousWindowHandle);
		Utils.pause(1000);
	}

	//add new file
	public void createNewFile(String name, String cont, String title, Object...params){
		String description = (String) (params.length > 0 ? params[0]: "");
		boolean lines = (Boolean) (params.length > 1 ? params[1]: false);
		boolean isMimeType = (Boolean) (params.length > 2 ? params[2]: false);
		String mimeType = (String) (params.length > 3 ? params[3]: "text/html");

		click(ELEMENT_NEWFILE_LINK);	
		type(ELEMENT_NEWFILE_NAME_TEXTBOX, name, false);
		if (isMimeType){
			selectOption(ELEMENT_NEWFILE_MIME_COMBOX_ID, mimeType);
		}
		Utils.pause(300);
		if (!lines){
			if (waitForAndGetElement(ELEMENT_NEWFILE_CONTENT_FRAME, 5000, 0,2) != null){
				inputDataToFrame(ELEMENT_NEWFILE_CONTENT_FRAME, cont, true);
			}else if (waitForAndGetElement(ELEMENT_NEWFILE_TEXTAREA_ID, 3000, 0) != null){
				type(ELEMENT_NEWFILE_TEXTAREA_ID, cont, true);
			} else if(waitForAndGetElement(ELEMENT_NEWFILE_CONTENT_FRAME_41, 3000, 0) != null){
				inputDataToFrame(ELEMENT_NEWFILE_CONTENT_FRAME_41, cont, true);
			}
			switchToParentWindow();
		}else {
			typeMultiLineInCkeContent(ELEMENT_NEWFILE_CONTENT_FRAME, cont);
		}
		if(mimeType.equalsIgnoreCase("text/plain"))
			type(ELEMENT_NEWFILE_TEXTPLAIN,cont,true);
		type(ELEMENT_NEWFILE_TITLE_TEXTBOX, title, false);
		if (!description.isEmpty()){
			type(ELEMENT_NEWFILE_DESCRIPTION_TEXTBOX, description, false);
		}
		button.saveAndClose();
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_FILE_CONTENT.replace("${content}", cont)), DEFAULT_TIMEOUT, 1, 2);
		Utils.pause(1000);
	}

	/** function create new file with full input
	 * @author lientm
	 * @param name
	 * @param cont
	 * @param title
	 * @param desc
	 * @param creator
	 * @param source
	 */
	public void createNewFullFile(String name, String cont, String title, String desc, String creator, String source){

		click(ELEMENT_NEWFILE_LINK);	
		type(ELEMENT_NEWFILE_NAME_TEXTBOX, name, true);
		if(isElementPresent(ELEMENT_NEWFILE_CONTENT_FRAME))
			inputDataToFrame(ELEMENT_NEWFILE_CONTENT_FRAME, cont, true);
		else
			inputDataToFrame(ELEMENT_NEWFILE_CONTENT_FRAME_41, cont, true);
		switchToParentWindow();
		if (title != ""){
			type(ELEMENT_NEWFILE_TITLE_TEXTBOX, title, true);
		}
		if (desc != ""){
			type(ELEMENT_NEWFILE_DESCRIPTION_TEXTBOX, desc, true);
		}
		if (creator != ""){
			type(ELEMENT_NEWFILE_CREATOR_TEXTBOX, creator, true);
		}
		if (source != ""){
			type(ELEMENT_NEWFILE_SOURCE_TEXTBOX, source, true);
		}
		button.saveAndClose();
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		//waitForAndGetElement(By.xpath("//*[contains(text(), '" + title +"')]"));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_FILE_CONTENT.replace("${content}", cont)), DEFAULT_TIMEOUT, 1, 2);
		Utils.pause(1000);
	}

	//add new Picture on head layout webcontent
	//exo:pictureOnHeadWebContent is changed to Illustrated Web Content
	public void createNewIllustratedWebContent(String name, String content, String file, String illustrationImage, 
			String illustrationSummary, String css, String js, Object...params){
		info("-- Creating a new Illustrated Web Content --");
		//String imageWidth = (String) (params.length > 0 ? params[0]: "");
		//String imageHeight = (String) (params.length > 1 ? params[1]: "");
		String optionLang = (String) (params.length > 0 ? params[0] : "");
		Boolean verify = (Boolean) (params.length > 1 ? params[1] : true);

		click(ELEMENT_HEAD_LAYOUT_LINK);
		type(ELEMENT_HEAD_LAYOUT_NAME_TEXTBOX, name, false);
		if(this.plfVersion.equalsIgnoreCase("4.0"))
			inputDataToFrame(ELEMENT_WEBCONTENT_CONTENT_FRAME, content);
		else
			inputDataToFrame(ELEMENT_WEBCONTENT_CONTENT_FRAME_41,content);
		switchToParentWindow();
		if (file != ""){
			WebElement upload = waitForAndGetElement(ELEMENT_UPLOAD_NAME, DEFAULT_TIMEOUT, 0, 2);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
			upload.sendKeys(Utils.getAbsoluteFilePath(file));
			////
			switchToParentWindow();
			String links[] = file.split("/");
			int length = links.length;
			waitForAndGetElement(By.xpath("//*[contains(@class, 'select')]//*[contains(text(),'" + links[length-1]+ "')]"));
		}

		if (!optionLang.isEmpty()){
			selectOption(ELEMENT_PIC_LANG, optionLang);
		}

		//Illustration tab
		if (illustrationSummary!="" || illustrationImage !=""){
			click(ELEMENT_WEBCONTENT_ILLUSTRATION_TAB);
			if (illustrationImage!=""){			
				WebElement upload = waitForAndGetElement(ELEMENT_UPLOAD_NAME, DEFAULT_TIMEOUT, 0, 2);
				((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
				upload.sendKeys(Utils.getAbsoluteFilePath(illustrationImage));
				////
				switchToParentWindow();
				String links[] = illustrationImage.split("/");
				int length = links.length;
				waitForAndGetElement(By.xpath("//div[contains(text(),'" + links[length-1]+ "')]"));
			}
			if(this.plfVersion.equals("4.0"))
				inputDataToFrame(ELEMENT_WEBCONTENT_SUMMARY_FRAME, illustrationSummary);
			else
				inputDataToFrame(ELEMENT_WEBCONTENT_SUMMARY_FRAME_41, illustrationSummary);
			switchToParentWindow();
		}
		//Advanced tab
		if(css!="" || js !=""){
			click(ELEMENT_WEBCONTENT_ADVANCE_TAB);
			type(ELEMENT_WEBCONTENT_CSS_TEXTAREA, css, false);
			type(ELEMENT_WEBCONTENT_JS_TEXTAREA, js, false);
		}
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		if (verify){
			waitForAndGetElement(By.xpath(ELEMENT_VERIFY_FILE_CONTENT.replace("${content}", content)));
		}
		Utils.pause(1000);
	}

	//add new product
	public void createNewProduct (String name, String title){
		click(ELEMENT_PRODUCT_LINK);
		type(ELEMENT_PRODUCT_NAME_TEXTBOX, name, false);
		//type(ELEMENT_PRODUCT_TITLE_TEXTBOX, title, false);
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(1000);
	}

	/** function add new Product content with full inputs
	 * @author lientm
	 * @param name
	 * @param illus
	 * @param sum
	 * @param benefit
	 * @param feature
	 * @param multiLine
	 */
	public void createFullNewProduct(String name, String illus, String sum, String benefit, String feature, boolean...multiLine){		
		boolean lines = multiLine.length > 0 ? multiLine[0]: false;
		click(ELEMENT_PRODUCT_LINK);
		type(ELEMENT_PRODUCT_NAME_TEXTBOX, name, false);
		if (illus != ""){
			WebElement upload = waitForAndGetElement(ELEMENT_UPLOAD_NAME, DEFAULT_TIMEOUT, 0, 2);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
			upload.sendKeys(Utils.getAbsoluteFilePath(illus));
			switchToParentWindow();
			String links[] = illus.split("/");
			int length = links.length;
			waitForAndGetElement(By.xpath("//div[contains(text(),'" + links[length-1]+ "')]"));
		}
		if (sum != ""){
			if (! lines){
				if(waitForAndGetElement(ELEMENT_PRODUCT_SUMMARY_FRAME,5000,0,2)!=null)
					inputDataToFrame(ELEMENT_PRODUCT_SUMMARY_FRAME, sum, true);
				else //if(waitForAndGetElement(ELEMENT_PRODUCT_SUMMARY_FRAME_41,5000,0,2)!=null)
					inputDataToFrame(ELEMENT_PRODUCT_SUMMARY_FRAME_41, sum, true);
				switchToParentWindow();
			}else {
				if(waitForAndGetElement(ELEMENT_PRODUCT_SUMMARY_FRAME,5000,0,2)!=null)
					typeMultiLineInCkeContent(ELEMENT_PRODUCT_SUMMARY_FRAME, sum);
				else //if(waitForAndGetElement(ELEMENT_PRODUCT_SUMMARY_FRAME_41,5000,0,2)!=null)
					typeMultiLineInCkeContent(ELEMENT_PRODUCT_SUMMARY_FRAME_41, sum);
			}
		}
		if (benefit != ""){
			if(waitForAndGetElement(ELEMENT_PRODUCT_BENEFIT_FRAME,5000,0,2)!=null)
				inputDataToFrame(ELEMENT_PRODUCT_BENEFIT_FRAME, sum, true);
			else //if(waitForAndGetElement(ELEMENT_PRODUCT_BENEFIT_FRAME_41,5000,0,2)!=null)
				inputDataToFrame(ELEMENT_PRODUCT_BENEFIT_FRAME_41, sum, true);
			switchToParentWindow();
		}
		if (feature != ""){
			if(waitForAndGetElement(ELEMENT_PRODUCT_FEATURE_FRAME,5000,0,2)!=null)
				inputDataToFrame(ELEMENT_PRODUCT_FEATURE_FRAME, sum, true);
			else //if(waitForAndGetElement(ELEMENT_PRODUCT_FEATURE_FRAME_41,5000,0,2)!=null)
				inputDataToFrame(ELEMENT_PRODUCT_FEATURE_FRAME_41, sum, true);
			switchToParentWindow();
		}
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
	}

	//Folder type 
	public enum folderType{
		None, Content, Document, Css;
	}

	//add new Content Folder
	public void createNewFolder(String title, folderType type, Object...params) {
		Boolean checkFolder = (Boolean) (params.length > 0 ? params[0]:true); 

		info("-- Creating a new folder --");
		actBar.goToAddNewFolder();
		WebElement fType = waitForAndGetElement(ELEMENT_USE_CUSTOM_TYPE_FOLDER, 5000, 0);
		if (fType != null && !fType.isSelected()){
			click(ELEMENT_USE_CUSTOM_TYPE_FOLDER, 2);
		}
		switch (type) {
		case Content:
			type(ELEMENT_FOLDER_TITLE_TEXTBOX, title, false);
			selectOption(ELEMENT_FOLDER_TYPE_OPTION, ELEMENT_CONTENT_FOLDER_TYPE);
			break;
		case Document:
			selectOption(ELEMENT_FOLDER_TYPE_OPTION, ELEMENT_DOCUMENT_FOLDER_TYPE);
			type(ELEMENT_FOLDER_TITLE_TEXTBOX, title, false);
			break;
		case Css:
			selectOption(ELEMENT_FOLDER_TYPE_OPTION, ELEMENT_CSS_FOLDER_TYPE);
			type(ELEMENT_FOLDER_TITLE_TEXTBOX, title, false);
			break;	
		case None:
			type(ELEMENT_FOLDER_TITLE_TEXTBOX, title, false);
			if (fType == null){
				info("-- Add a new folder without selecting folder's type --");
			}else if (fType != null && fType.isSelected()){
				click(ELEMENT_USE_CUSTOM_TYPE_FOLDER, 2);
			}
			break;
		default:
			break;
		}
		click(ELEMENT_CREATE_FOLDER_BUTTON);
		//waitForElementPresent(By.xpath("//*[contains(text(),'"+ title +"')]"));
		if (checkFolder){
			waitForAndGetElement(By.xpath(ELEMENT_VERIFY_FILE_CONTENT.replace("${content}", title)));
		}
		Utils.pause(1000);
	}

	//Create a new Css file
	public void createNewCssFile(String name, String priority, String data, Object...params){		
		Boolean active = (Boolean) (params.length > 0 ? params[0]: false);
		String lang = (String) (params.length > 1 ? params[1]: "");

		info("-- Creating a new Css File --");
		type(ELEMENT_CSS_NAME, name, false);
		if (!active){
			select(ELEMENT_CSS_ACTIVE, "False");
		}else{
			select(ELEMENT_CSS_ACTIVE, "True");
		}	
		type(ELEMENT_CSS_PRIORITY, priority, false);
		if (!lang.isEmpty()){
			select(ELEMENT_PIC_LANG, lang);
		}	
		type(ELEMENT_CSS_DATA, data, true);
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(1000);
	}

	//Create a new JS file
	public void createNewJsFile(String name, String priority, String data, Object... params){
		Boolean active = (Boolean) (params.length > 0 ? params[0]: false);
		String lang = (String) (params.length > 1 ? params[1]: "");

		info("-- Creating a new Js File --");
		//actBar.goToAddNewContent();
		type(ELEMENT_JS_NAME, name, true);
		if (!active){
			select(ELEMENT_JS_ACTIVE, "False");
		}else{
			select(ELEMENT_JS_ACTIVE, "True");
		}
		type(ELEMENT_JS_PRIORITY, priority, true);
		if (!lang.isEmpty()){
			selectOption(ELEMENT_JS_LANGUAGE, lang);
		}
		type(ELEMENT_JS_DATA, data, true);
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(1000);
	}

	/**
	 * @author phuongdt
	 * @date 30/08/2013
	 * @function create new HTML File	
	 * @param name
	 * @param language
	 * @param content
	 */
	public void createNewHtmlFile(String name, Object... params){
		click(ELEMENT_NEW_HTML_FILE_LINK);
		String lang = (String) (params.length > 0 ? params[0]: "");
		String content = (String) (params.length > 1 ? params[1]: "");
		info("-- Creating a new HTML File --");
		type(ELEMENT_HTML_FILE_NAME, name, true);
		if (!lang.isEmpty()){
			selectOption(ELEMENT_HTML_FILE_LANGUAGE, lang);
		}
		if (!content.isEmpty()){
			/*switch to ckeditor frame*/
			/*driver.switchTo().frame(driver.findElement(ELEMENT_HTML_FILE_CKEDITOR_FRAME));
			locator body of ckeditor
			type(ELEMENT_HTML_FILE_CKEDITOR_FRAME_BODY, content, false);
			return main frame
			driver.switchTo().defaultContent();*/
			if(this.plfVersion.equalsIgnoreCase("4.0"))
				inputDataToFrame(ELEMENT_HTML_FILE_CKEDITOR_FRAME, content, true);
			else
				inputDataToFrame(ELEMENT_HTML_FILE_CKEDITOR_FRAME_41, content, true);
			switchToParentWindow();
			//			
			//			/*switch to ckeditor frame*/
			//			driver.switchTo().frame(driver.findElement(ELEMENT_HTML_FILE_CKEDITOR_FRAME));
			//			/*locator body of ckeditor*/
			//			type(ELEMENT_HTML_FILE_CKEDITOR_FRAME_BODY, content, false);
			//			/*return main frame*/
			//			driver.switchTo().defaultContent();
		}		
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(1000);
	}

	/**
	 * @author phuongdt
	 * @date 30/08/2013
	 * @function create new Web Link File	
	 * @param name
	 * @param language
	 * @param content
	 */
	public void createNewWebLink(String name, String url, Object... params){
		click(ELEMENT_WEBLINK_LINK);
		String lang = (String) (params.length > 0 ? params[0]: "");
		String description = (String) (params.length > 1 ? params[1]: "");
		info("-- create new Web Link File --");
		type(ELEMENT_LINK_NAME, name, true);
		type(ELEMENT_LINK_URL, url, true);
		if (!lang.isEmpty()){
			selectOption(ELEMENT_LINK_LANGUAGE, lang);
		}
		if (!description.isEmpty()){
			type(ELEMENT_LINK_DESC, description, true);
		}		
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(1000);
	}

	/**
	 * @author phuongdt
	 * @date 03/09/2013
	 * @function create new Contact Us document
	 */
	public void createNewContactUs(Object... params){
		click(ELEMENT_CONTACTUS_LINK);
		String recipient = (String) (params.length > 0 ? params[0]: "");
		String name = (String) (params.length > 1 ? params[1]: "");
		String address = (String) (params.length > 2 ? params[2]: "");
		String email = (String) (params.length > 3 ? params[3]: "");
		String phone = (String) (params.length > 4 ? params[4]: "");
		String lang = (String) (params.length > 5 ? params[5]: "");
		String msg = (String) (params.length > 6 ? params[6]: "");
		info("-- Creating a Contact Us document --");
		if (!recipient.isEmpty()){
			selectOption(ELEMENT_CONTACTUS_RECIPIENT, recipient);
		}
		if (!name.isEmpty()){
			type(ELEMENT_CONTACTUS_YOURNAME, name, true);
		}	
		if (!address.isEmpty()){
			type(ELEMENT_CONTACTUS_YOURADDRESS, address, true);
		}
		if (!email.isEmpty()){
			type(ELEMENT_CONTACTUS_EMAIL, email, true);
		}
		if (!phone.isEmpty()){
			type(ELEMENT_CONTACTUS_PHONE, phone, true);
		}
		if (!lang.isEmpty()){
			selectOption(ELEMENT_CONTACTUS_LANGUAGE, lang);
		}
		if (!msg.isEmpty()){
			type(ELEMENT_CONTACTUS_MSG, msg, true);
		}
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(1000);
	}

	/**
	 * @author phuongdt
	 * @date 17/09/2013
	 * @functin create new Accessible Media
	 */
	public void createNewAccessibleMedia(String name,Object... params){
		click(ELEMENT_ACCESSIBLEMEDIA_LINK);
		String title = (String) (params.length > 0 ? params[0]: "");
		String lang = (String) (params.length > 1 ? params[1]: "");
		String content = (String) (params.length > 2 ? params[2]: "");

		info("-- new Accessible Media --");
		type(ELEMENT_ACCESSIBLE_MAIN_TAB_NAME, name, true);

		if (!title.isEmpty()){
			type(ELEMENT_ACCESSIBLE_MAIN_TAB_TITLE, title, true);
		}

		if (!lang.isEmpty()){
			selectOption(ELEMENT_ACCESSIBLE_MAIN_TAB_LANGUAGE, lang);
		}

		if (!content.isEmpty()){
			inputDataToFrame(ELEMENT_HTML_FILE_CKEDITOR_FRAME, content, true);
			switchToParentWindow();
		}


		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(1000);
	}

	/**
	 * @author phuongdt
	 * @date 03/09/2013
	 * @function create new Accessible Breadcrumb document, Accessible Navigator, Accessible Search box
	 * @param name
	 */
	public void createNewAccessibleDocument(String name,Object... params){
		waitForAndGetElement(ELEMENT_ACCESSIBLE_MAIN_TAB);
		click(ELEMENT_ACCESSIBLE_MAIN_TAB);
		String title = (String) (params.length > 0 ? params[0]: "");
		String lang = (String) (params.length > 1 ? params[1]: "");
		String content = (String) (params.length > 2 ? params[2]: "");

		info("-- new Accessible Breadcrumb document	 --");
		type(ELEMENT_ACCESSIBLE_MAIN_TAB_NAME, name, true);

		if (!title.isEmpty()){
			type(ELEMENT_ACCESSIBLE_MAIN_TAB_TITLE, title, true);
		}

		if (!lang.isEmpty()){
			selectOption(ELEMENT_ACCESSIBLE_MAIN_TAB_LANGUAGE, lang);
		}

		if (!content.isEmpty()){
			inputDataToFrame(ELEMENT_HTML_FILE_CKEDITOR_FRAME, content, true);
			switchToParentWindow();
		}


		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(1000);
	}

	//Create content folder and check
	public void createAndCheckContentFolder(String name, By path){
		info("Create new content folder with name: "+name);
		createNewFolder(name, folderType.Content);
		waitForAndGetElement(path);
		info("Create new content folder successfully"); 
	}


	//Create a new link 
	public void createNewLink(String linkName, String url, Object...params){
		String lang = (String) (params.length > 0 ? params[0]: "");
		String description = (String) (params.length > 1 ? params[1]: "");

		info("Create a new link" + linkName);
		type(ELEMENT_LINK_NAME, linkName, false);
		type(ELEMENT_LINK_URL, url, false);

		if (!lang.isEmpty()){
			selectOption(ELEMENT_PIC_LANG, lang);
		}

		if (!description.isEmpty()){
			type(ELEMENT_LINK_DESC, description, false);
		}

		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(1000);
	}

	//Create a new Contact Us
	public void createNewContactUs(String name, String address, String email, String phone, String message){

		info("Create a new Contact Us");

		click(ELEMENT_CONTACT_US_LINK);	

		type(ELEMENT_YOUR_NAME, name, true);

		type(ELEMENT_YOUR_ADDRESS, address, true);

		type(ELEMENT_YOUR_EMAIL, email, true);

		type(ELEMENT_YOUR_PHONE, phone, true);

		type(ELEMENT_YOUR_MESSAGE, message, true);

		button.saveAndClose();
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_MSG_CONTENT.replace("${content}", message)), DEFAULT_TIMEOUT, 1, 2);
		Utils.pause(1000);
	}

	/*=================== Edit a new document/article/file ===================*/

	/* 
	 * Edit content template 
	 * == REMOVED IN PLF 4 ==
	 */

	//Edit a Css File
	public void editCssFile(String name, String priority, String data, Object...params){
		Boolean active = (Boolean) (params.length > 0 ? params[0]: false);
		String lang = (String) (params.length > 1 ? params[1]: "");

		info("-- Editing Css File --");
		actBar.goToEditDocument(name); 
		if (!active){
			select(ELEMENT_CSS_ACTIVE, "False");
		}else{
			select(ELEMENT_CSS_ACTIVE, "True");
		}
		type(ELEMENT_CSS_PRIORITY, priority, true);
		if (!lang.isEmpty()){
			selectOption(ELEMENT_PIC_LANG, lang);
		}
		type(ELEMENT_CSS_DATA, data, true);
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
	}

	//Edit a Js File
	public void editJsFile(String name, String priority, String data, Object...params){
		Boolean active = (Boolean) (params.length > 0 ? params[0]: false);
		String lang = (String) (params.length > 1 ? params[1]: "");

		info("-- Editing Js File --");
		actBar.goToEditDocument(name);
		if (!active){
			select(ELEMENT_JS_ACTIVE, "False");
		}else{
			select(ELEMENT_JS_ACTIVE, "True");
		}
		type(ELEMENT_JS_PRIORITY, priority, true);
		if (!lang.isEmpty()){
			select(ELEMENT_JS_LANGUAGE, lang);
		}
		type(ELEMENT_JS_DATA, data, true);
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
	}

	//Edit File document
	public void editFile(String titleFile, String titleEdit, String contentEdit, Object... params){
		int option = (Integer) (params.length > 0 ? params[0] : 2);
		actBar.goToEditDocument(titleFile);
		Utils.pause(500);
		//type(ELEMENT_NEWFILE_NAME_TEXTBOX, nameEdit, true);
		if (contentEdit != ""){
			if (waitForAndGetElement(ELEMENT_NEWFILE_CONTENT_FRAME, 5000, 0,2) != null){
				inputDataToFrame(ELEMENT_NEWFILE_CONTENT_FRAME, contentEdit, true);
			}else if (waitForAndGetElement(ELEMENT_NEWFILE_TEXTAREA_ID, 3000, 0) != null){
				type(ELEMENT_NEWFILE_TEXTAREA_ID, contentEdit, true);
			} else{// if(waitForAndGetElement(ELEMENT_NEWFILE_CONTENT_FRAME_41, 3000, 0) != null){
				inputDataToFrame(ELEMENT_NEWFILE_CONTENT_FRAME_41, contentEdit, true);
			}
			switchToParentWindow();
		}
		if (titleEdit != ""){
			type(ELEMENT_NEWFILE_TITLE_TEXTBOX, titleEdit, true);
		}

		if (option == 0){
			button.save();
			//waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
			waitForAndGetElement(button.ELEMENT_SAVE_CLOSE_BUTTON);
		} else if (option == 1){
			magAlert.acceptAlert();
			button.ok();
			button.close();
			Utils.pause(500);
		} else{
			button.saveAndClose();
			waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		}
		//waitForAndGetElement(By.xpath("//body/p[contains(text(), '" + contentEdit +"')]"));
	}

	//Edit Illustrated Web Content
	public void editIllustratedWebContent(String title, String contentToEdit, String file, String illustrationImage, 
			String illustrationSummary, String css, String js, Object...params){
		String imageWidth = (String) (params.length > 0 ? params[0]: "");
		String imageHeight = (String) (params.length > 1 ? params[1]: "");
		String lang = (String) (params.length > 2 ? params[2]: "");

		info("-- Editing Illustrated Web Content -- " + title);
		actBar.goToEditDocument(title);
		inputDataToFrame(ELEMENT_WEBCONTENT_CONTENT_FRAME, contentToEdit, true);
		switchToParentWindow();	

		//Main Content tab
		if (file != ""){
			//driver.switchTo().frame(waitForAndGetElement(ELEMENT_HEAD_LAYOUT_UPLOAD_FRAME));
			//type(ELEMENT_HEAD_LAYOUT_UPLOAD_FILE, Utils.getAbsoluteFilePath(file), false);
			WebElement upload = waitForAndGetElement(ELEMENT_UPLOAD_NAME, DEFAULT_TIMEOUT, 0, 2);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
			upload.sendKeys(Utils.getAbsoluteFilePath(file));
			////
			switchToParentWindow();
			String links[] = file.split("/");
			int length = links.length;
			waitForAndGetElement(By.xpath("//div[contains(text(),'" + links[length-1]+ "')]"));
		}
		if (!imageWidth.isEmpty())
			type(ELEMENT_PIC_IMGWIDTH, imageWidth,true);
		if (!imageHeight.isEmpty())
			type(ELEMENT_PIC_IMGHEIGHT, imageHeight, true);
		if (!lang.isEmpty())
			selectOption(ELEMENT_PIC_LANG, lang);

		//Illustration tab
		if (illustrationSummary != "" || illustrationImage != ""){
			click(ELEMENT_WEBCONTENT_ILLUSTRATION_TAB);
			waitForAndGetElement(By.xpath("//*[@class='active']//*[contains(text(), 'Illustration')]"));
			if (illustrationImage != ""){
				//WebElement removeIcon = waitForAndGetElement(ELEMENT_UPLOAD_REMOVE);
				if (waitForAndGetElement(ELEMENT_UPLOAD_REMOVE, 3000, 0) != null)
				{
					click(ELEMENT_UPLOAD_REMOVE);
				}
				Utils.pause(1000);
				click(ELEMENT_WEBCONTENT_ILLUSTRATION_TAB);
				WebElement upload = waitForAndGetElement(ELEMENT_UPLOAD_NAME, DEFAULT_TIMEOUT, 0, 2);
				((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
				upload.sendKeys(Utils.getAbsoluteFilePath(illustrationImage));
				////
				switchToParentWindow();
				String links[] = illustrationImage.split("/");
				int length = links.length;
				waitForAndGetElement(By.xpath("//div[contains(text(),'" + links[length-1]+ "..." +"')]"));
			}
			inputDataToFrame(ELEMENT_WEBCONTENT_SUMMARY_FRAME, illustrationSummary, true);
			switchToParentWindow();
		}

		//Advanced tab
		if(css!="" || js !=""){
			click(ELEMENT_WEBCONTENT_ADVANCE_TAB);
			type(ELEMENT_WEBCONTENT_CSS_TEXTAREA, css, true);
			type(ELEMENT_WEBCONTENT_JS_TEXTAREA, js, true);
		}

		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_FILE_CONTENT.replace("${content}", contentToEdit)));
	}

	//Edit WebContent Inline
	public void editWebContentInline(String openField, By inputField, String value, By accept)
	{
		enableEditMode(true);
		doubleClickOnElement(openField);
		inputDataToFrame(inputField, value, true);
		switchToParentWindow();
		click(accept);
		waitForAndGetElement(By.xpath("//p[contains(text(),'"+value+"')]"));
	}

	//Edit a WebContent
	public void editWebContent(String title, String contentToEdit, String img, String sum, String css, String js) 
	{   
		info("-- Editing Web Content --" + title);
		actBar.goToEditDocument(title);
		//type(ELEMENT_WEBCONTENT_TITLE_TEXTBOX, title_edit, true);
		//		waitForAndGetElement(ELEMENT_WEBCONTENT_NAME_TEXTBOX).clear();
		if (contentToEdit != ""){
			if(this.plfVersion.equalsIgnoreCase("4.0"))
				inputDataToFrame(ELEMENT_WEBCONTENT_CONTENT_FRAME, contentToEdit, true);      
			else
			inputDataToFrame(ELEMENT_WEBCONTENT_CONTENT_FRAME_EDIT_41, contentToEdit, true);
			
			switchToParentWindow();
		}

		if (sum != "" || img != ""){
			click(ELEMENT_WEBCONTENT_ILLUSTRATION_TAB);
			waitForAndGetElement(By.xpath("//*[@class='active']//*[contains(text(), 'Illustration')]"));
			if (img != ""){
				WebElement removeIcon = waitForAndGetElement(ELEMENT_UPLOAD_REMOVE);
				if (removeIcon != null)
				{
					click(ELEMENT_UPLOAD_REMOVE);
				}
				Utils.pause(1000);
				//driver.switchTo().frame(waitForAndGetElement(ELEMENT_WEBCONTENT_UPLOAD_FRAME));
				//type(ELEMENT_WEBCONTENT_FILE_IMAGE, Utils.getAbsoluteFilePath(img), false);
				click(ELEMENT_WEBCONTENT_ILLUSTRATION_TAB);
				WebElement upload = waitForAndGetElement(ELEMENT_UPLOAD_NAME, DEFAULT_TIMEOUT, 0, 2);
				((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
				upload.sendKeys(Utils.getAbsoluteFilePath(img));
				////
				switchToParentWindow();
				String links[] = img.split("/");
				int length = links.length;
				waitForAndGetElement(By.xpath("//div[contains(text(),'" + links[length-1]+ "..." +"')]"));
			}
			if(waitForAndGetElement(ELEMENT_WEBCONTENT_SUMMARY_FRAME_41,5000,0,1)!=null)
				typeMultiLineInCkeContent(ELEMENT_WEBCONTENT_SUMMARY_FRAME_41, sum);
			else //if(waitForAndGetElement(ELEMENT_WEBCONTENT_SUMMARY_FRAME,5000,0,1)!=null)
				typeMultiLineInCkeContent(ELEMENT_WEBCONTENT_SUMMARY_FRAME, sum);		
			switchToParentWindow();
		}

		if(css!="" || js !=""){
			click(ELEMENT_WEBCONTENT_ADVANCE_TAB);
			type(ELEMENT_WEBCONTENT_CSS_TEXTAREA, css, true);
			type(ELEMENT_WEBCONTENT_JS_TEXTAREA, js, true);
		}
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);	
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		//waitForElementPresent(ELEMENT_VERIFY_FILE_CONTENT.replace("${content}", contentToEdit));
	}

	/**
	 * @author lientm
	 * @param name
	 * @param language
	 */
	public void editLanguageForDocument(String name, String language){
		actBar.goToEditDocument(name);

		select(ELEMENT_SELECT_LANGUAGE, language);
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(ELEMENT_SELECT_LANGUAGE);
	}

	/** Create an accessible media document
	 * @author thuntn
	 * @param name
	 * @param lang
	 * @param title
	 * @param desc
	 * @param alter
	 */
	public void createAccessibleMedia(String name, String lang, String title, String desc, String alter){

		info("Create an accessible media" + name);
		click(ELEMENT_ACCESSIBLE_MEDIA_LINK);

		if(name != null)
			type(ELEMENT_LINK_NAME, name, false);
		if(lang != null)
			select(ELEMENT_JS_LANGUAGE, lang);
		if(title != null)
			type(ELEMENT_ACCESSIBLE_MEDIA_TITLE,title,true);
		if(desc != null)
			type(ELEMENT_ACCESSIBLE_MEDIA_DESC,desc,true);

		if(alter != null)
			inputDataToFrame(ELEMENT_ACCESSIBLE_MEDIA_ALTERNATIVE_FRAME,alter,true);

		switchToParentWindow();
		click(button.ELEMENT_SAVE_CLOSE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(1000);
	}
}