package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * Provides all methods of editing a wiki page directly in the wiki markup language.
 * 
 * 
 */
public class RichTextMode extends Template {
	Button but = new Button(driver);

	//Link menu
	public By ELEMENT_LINK = By.xpath("//*[text()='Link']");
	public By ELEMENT_WIKI_PAGE_LINK = By.xpath("//*[text()='Wiki Page...']");

	//Add wiki page link popup
	public By ELEMENT_SEARCH_TAB = By.xpath("//div[text()='Search']");
	public By ELEMENT_SEARCH_TEXTBOX = By.xpath("//input[@title='Type a keyword to search for a wiki page']");
	public By ELEMENT_SEARCH_BUTTON = By.xpath("//button[text()='Search']");
	public String ELEMENT_PAGE_SELECTED = "//*[@class='xPagePreview' and @title='${page}']";
	public String ELEMENT_PAGE_SELECTED_PLF41 = "//*[@class='xPagesSelector xPagesSearch' and @aria-hidden='false']//*[@class='xPagePreview' and @title='${page}']";
	public By ELEMENT_LABEL_LINK_TEXTBOX = By.xpath("//input[@title='Type the label of the created link.']");
	public By ELEMENT_TOOLTIP_LINK_TEXTBOX = By.xpath("//input[@title='Type the tooltip of the created link, which appears when mouse is over the link.']");
	public By ELEMENT_REMOVE_LINK = By.xpath("//div[text()='Remove Link']");
	public By ELEMENT_EDIT_LINK = By.xpath("//div[text()='Edit Link...']");
	public By ELEMENT_ADD_NEW_LINKPAGE_TEXTBOX =  By.xpath("//input[@title='Type the name of the page to be created. The final name of the page may vary since some characters are filtered.']");
	public By ELEMENT_ALL_PAGE_TAB = By.xpath("//div[contains(text(), 'All pages')]");
	public By ELEMENT_ADD_NEW_PAGE_LINK = By.xpath("//*[@class='gwt-Label xNewPagePreview']");

	//Table
	public By ELEMENT_TABLE_LINK = By.xpath("//*[text()='Table']");
	public By ELEMENT_INSERT_TABLE_LINK = By.xpath("//*[text()='Insert Table...']");
	public By ELEMENT_ROW_TEXTBOX = By.xpath("//*[@title='Row count']");
	public By ELEMENT_COLUMN_TEXTBOX = By.xpath("//*[@title='Column count']");

	//Macro
	public By ELEMENT_MACRO_LINK = By.xpath("//*[text()='Macro']");
	public By ELEMENT_INSERT_MACRO_LINK = By.xpath("//*[text()='Insert Macro...']");
	public By ELEMENT_MACRO_CATEGORY_SELECT = By.xpath("//select[@title='Select a macro category']");
	public String ELEMENT_MACRO_LABEL = "//*[text()='${macro}']";

	//Macro: Tip message
	public By ELEMENT_CONTENT_MESSAGE_TEXTAREA = By.id("pd-content-input");
	public String ELEMENT_TIP_MESSAGE_MACRO = "//*[@class='box tipmessage' and text()='${message}']";

	//Macro: Color
	public By ELEMENT_COLOR_TEXTBOX = By.id("pd-name-input");
	public By ELEMENT_COLOR_MESSAGE = By.id("pd-content-input");

	//Image
	public By ELEMENT_IMAGE_LINK = By.xpath("//*[text()='Image']");
	public By ELEMENT_IMAGE_LINK_ATTACH = By.xpath("//*[text()='Attached Image...']");
	public By ELEMENT_IMAGE_LINK_REMOVE = By.xpath("//*[text()='Remove Image']");
	public By ELEMENT_IMAGE_EDIT_LINK = By.xpath("//div[text()='Edit Image...']");
	public By ELEMENT_IMAGE_WIDTH = By.xpath("//div[contains(text(), 'Width')]/..//input[1]");
	public By ELEMENT_IMAGE_HEIGHT = By.xpath("//div[contains(text(), 'Height')]/..//input[2]");
	public By ELEMENT_IMAGE_ALTERNATIVE_TEXT = By.xpath("//div[contains(text(), 'Alternative text')]/..//input[1]");
	public final By ELEMENT_IMAGE_INSERT_BUTTON = By.xpath("//*[text()='Insert Image']");
	public By ELEMENT_IMAGE_EXTERNAL_LINK = By.xpath("//*[text()='External Image...']");
	public By ELEMENT_IMAGE_SETTING_BUTTON = By.xpath("//*[text()='Image Settings']");
	public By ELEMENT_IMAGE_LOCATION = By.xpath("//input[@title='Image location']");

	//WebPage
	public By ELEMENT_WEBPAGE_LINK = By.xpath("//*[text()='Web Page...']");
	public By ELEMENT_WEBPAGE_TEXTBOX = By.xpath("//input[@title='Web page address']");

	//WebEmail
	public By ELEMENT_EMAIL_LINK = By.xpath("//*[text()='Email Address...']");
	public By ELEMENT_EMAIL_TEXTBOX = By.xpath("//input[@title='Email address']");

	//Attach file 
	public By ELEMENT_ATTACH_FILE_LINK = By.xpath("//*[text()='Attached File...']");
	public By ELEMENT_ATTACH_FILE_PATH = By.xpath("//input[@name='filepath']");
	public String ELEMENT_ATTACH_FILE_CEL = "//*[@class='cell']//*[contains(text(), '${file}')]/../..//img[1]";

	/**
	 * Add link to a Wiki page
	 * 
	 * @param search
	 * 			parameter to choose whether to search page link or not
	 * @param page
	 * 			Wiki page that will be the target link
	 * @param label
	 * 			label of link that will be added into Wiki page
	 * @param tooltip
	 * 			
	 */
	public void insertPageLink2WikiPage(boolean search, String page, String label, String tooltip,Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_WIKI_PAGE_LINK);
		Utils.pause(500);
		info("Create link to the page " + page);
		if (search){
			click(ELEMENT_SEARCH_TAB);
			type(ELEMENT_SEARCH_TEXTBOX, page, true);
			click(ELEMENT_SEARCH_BUTTON);
		}
		if(waitForAndGetElement(ELEMENT_PAGE_SELECTED.replace("${page}", page), 5000,0)!=null)
			click(ELEMENT_PAGE_SELECTED.replace("${page}", page));
		else
			click(ELEMENT_PAGE_SELECTED_PLF41.replace("${page}", page));
		click(but.ELEMENT_SELECT_BUTTON);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));
			switchToParentWindow();
		}
	}

	/**
	 * Select a macro in a Wiki page editor
	 * 
	 * @param cat
	 * 			category to which a macro that will be chosen belongs
	 * @param macro
	 * 			macro name that will be chosen
	 * 
	 * @see #createTipMessageMacro(String)
	 * @see #createColorMacro(String, String)
	 */
	public void goToSelectAMacro(String cat, String macro){
		info("Go to a macro: " + macro);
		mouseOverAndClick(ELEMENT_MACRO_LINK);
		mouseOverAndClick(ELEMENT_INSERT_MACRO_LINK);
		select(ELEMENT_MACRO_CATEGORY_SELECT, cat);
		Utils.pause(1000);
		click(ELEMENT_MACRO_LABEL.replace("${macro}", macro));
		click(but.ELEMENT_SELECT_BUTTON);
		Utils.pause(3000);
	}

	/**
	 * Add macro: "Tip Message"
	 * 
	 * @param message
	 * 			 message that will be displayed in macro
	 */
	public void createTipMessageMacro(String message){
		goToSelectAMacro("Formatting", "Tip Message");
		type(ELEMENT_CONTENT_MESSAGE_TEXTAREA, message, true);
		click(but.ELEMENT_CREATE_MACRO_BUTTON);
		waitForAndGetElement(ELEMENT_TIP_MESSAGE_MACRO.replace("${message}", message));
	}

	/**
	 * Add macro: "color" into a Wiki page
	 * 
	 * @param color
	 * 			color setting of macro
	 * @param message
	 * 			message setting of macro
	 */
	public void createColorMacro(String color, String message){
		goToSelectAMacro("Formatting", "Color");
		type(ELEMENT_COLOR_TEXTBOX, color, true);
		type(ELEMENT_COLOR_MESSAGE, message, true);
		click(but.ELEMENT_CREATE_MACRO_BUTTON);
	}

	/**
	 * Add table to a Wiki page
	 * 
	 * @param rows
	 * 			Number of rows that will be added in the table
	 * @param columns
	 * 			Number of columns that will be added in the table
	 */
	public void insertTable2WikiPage(String rows, String columns){
		mouseOverAndClick(ELEMENT_TABLE_LINK);
		mouseOverAndClick(ELEMENT_INSERT_TABLE_LINK);
		type(ELEMENT_ROW_TEXTBOX, rows, true);
		type(ELEMENT_COLUMN_TEXTBOX, columns, true);
		click(but.ELEMENT_INSERT_TABLE);
		waitForAndGetElement("//table");
	}

	/**
	 * Click End then Enter in content frame in Rich text mode of Wiki page editor
	 */
	public void typeEnterInRichText(){
		try {
			WebElement inputsummary = null;
			driver.switchTo().frame(waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME));
			inputsummary = driver.switchTo().activeElement();
			inputsummary.click();
			inputsummary.sendKeys(Keys.END);
			inputsummary.sendKeys(Keys.ENTER);
			inputsummary.sendKeys("</br>");
			switchToParentWindow();
			Utils.pause(1000);
			driver.switchTo().defaultContent();
		}catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			driver.switchTo().defaultContent();
			typeEnterInRichText();
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			driver.switchTo().defaultContent();
			typeEnterInRichText();
		}catch (WebDriverException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			driver.switchTo().defaultContent();
			typeEnterInRichText();
		}
		finally {
			loopCount = 0;
		}
	}

	/**
	 * @author ChinhDT
	 * Add webpage link to a Wiki page
	 * 
	 * @param webpage
	 * 			web page that will be the target link
	 * @param label
	 * 			label of link that will be added into Wiki page
	 * @param tooltip
	 * 			
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertwebpageLink2WikiPage(String webpage, String label, String tooltip,Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_WEBPAGE_LINK);
		Utils.pause(500);
		info("Create link to the webpage " + webpage);
		if(webpage!=null && webpage!="")
			type(ELEMENT_WEBPAGE_TEXTBOX,webpage,true);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));
			if(webpage!=null && webpage!="")
				waitForAndGetElement(By.xpath("//*[contains(@href,'"+webpage+"')]"));
			switchToParentWindow();
		}
	}

	/**
	 * @author ChinhDT
	 * Add email link to a Wiki page
	 * 
	 * @param email
	 * 			email address that will be the target link
	 * @param label
	 * 			label of link that will be added into Wiki page
	 * @param tooltip
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertEmailLink2WikiPage(String email, String label, String tooltip, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_EMAIL_LINK);
		Utils.pause(500);
		info("Create link to the email " + email);
		type(ELEMENT_EMAIL_TEXTBOX, email, true);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));
			switchToParentWindow();
		}
	}

	/**
	 * edit a link (attach link, web page link, email link) on wiki: go to richtext mode -> Link -> Edit link
	 * @param file
	 * @param label
	 * @param tooltip
	 * @param webpage
	 * @param email
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void editLink(String file, String label, String tooltip, String webpage, String email, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_EDIT_LINK);
		if(file!=null && file!=""){
			click(By.xpath("//*[text()='"+file+"']"));
			Utils.pause(500);
			click(but.ELEMENT_SELECT_BUTTON);
			waitForElementNotPresent(but.ELEMENT_SELECT_BUTTON);
		}
		if(email!=null && email!="")
			type(ELEMENT_EMAIL_TEXTBOX,email,true);
		if(webpage!=null && webpage!="")
			type(ELEMENT_WEBPAGE_TEXTBOX,webpage,true);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));
			if(webpage!=null && webpage!="")
				waitForAndGetElement(By.xpath("//*[contains(@href,'"+webpage+"')]"));
			if(email!=null && email!="")
				waitForAndGetElement(By.xpath("//*[contains(@href,'"+webpage+"')]"));
			switchToParentWindow();
		}
	}

	/**
	 * add an image file to wiki
	 * @param file
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertImageFile(String file, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		String path = Utils.getAbsoluteFilePath("TestData/"+file);
		mouseOverAndClick(ELEMENT_IMAGE_LINK);
		mouseOverAndClick(ELEMENT_IMAGE_LINK_ATTACH);
		Utils.pause(500);
		click(but.ELEMENT_SELECT_BUTTON);
		waitForElementNotPresent(but.ELEMENT_SELECT_BUTTON);
		click(ELEMENT_ATTACH_FILE_PATH);
		WebElement upload = waitForAndGetElement(ELEMENT_ATTACH_FILE_PATH, 5000, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; " +
				"arguments[0].style.width = '1px'; arguments[0].style.opacity = 1", upload);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
		upload.sendKeys(path);

		Utils.pause(500);
		click(ELEMENT_IMAGE_INSERT_BUTTON);
		waitForElementNotPresent(ELEMENT_IMAGE_INSERT_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			String src = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("alt");
			assert src.contains(file);
			switchToParentWindow();
		}
	}

	/**
	 * Edit page link on wiki
	 * @param search
	 * @param page
	 * @param label
	 * @param tooltip
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void editPageLink2WikiPage(boolean search, String page, String label, String tooltip,Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_EDIT_LINK);
		Utils.pause(500);
		info("Create link to the page " + page);
		if (search){
			click(ELEMENT_SEARCH_TAB);
			type(ELEMENT_SEARCH_TEXTBOX, page, true);
			click(ELEMENT_SEARCH_BUTTON);
		}
		if(waitForAndGetElement(ELEMENT_PAGE_SELECTED.replace("${page}", page), 5000,0)!=null)
			click(ELEMENT_PAGE_SELECTED.replace("${page}", page));
		else
			click(ELEMENT_PAGE_SELECTED_PLF41.replace("${page}", page));
		click(but.ELEMENT_SELECT_BUTTON);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));
			switchToParentWindow();
		}
	}

	/**
	 * Edit image link on wiki
	 * @param file
	 * @param width
	 * @param height
	 * @param text
	 * @param alignment
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void editImage(String file, String width, String height, String text, alignmentType alignment, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_IMAGE_LINK);
		mouseOverAndClick(ELEMENT_IMAGE_EDIT_LINK);
		click(By.xpath("//*[@title='"+file+"']"));
		Utils.pause(500);
		click(but.ELEMENT_SELECT_BUTTON);
		waitForElementNotPresent(but.ELEMENT_SELECT_BUTTON);
		if(width!=null && width!="")
			type(ELEMENT_IMAGE_WIDTH,width,true);
		if(height!=null && height!="")
			type(ELEMENT_IMAGE_HEIGHT,height,true);
		if(text!=null && text!="")
			type(ELEMENT_IMAGE_ALTERNATIVE_TEXT,text,true);
		if(alignment!=null)
			click(By.xpath("//*[@value='"+String.valueOf(alignment)+"']"));
		click(ELEMENT_IMAGE_INSERT_BUTTON);
		waitForElementNotPresent(ELEMENT_IMAGE_INSERT_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(width!=null && width!=""){
				String widthImage = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("width");
				assert widthImage.equalsIgnoreCase(width);
			}
			if(height!=null && height!=""){
				String heightImage = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("height");
				assert heightImage.equalsIgnoreCase(height);
			}
			if(text!=null && text!=""){
				String alt = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("alt");
				assert alt.equalsIgnoreCase(text);
			}
			if(alignment!=null){
				String alignmentImage = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("style");
				assert alignmentImage.toLowerCase().contains(String.valueOf(alignment).toLowerCase());
			}
			switchToParentWindow();
		}
	}

	/**
	 * Define a alignment of image link 
	 * LEFT
	 * CENTER
	 * RIGHT 
	 * TOP
	 * MIDDLE
	 * BOTTOM 
	 */
	public enum alignmentType {
		LEFT, CENTER, RIGHT,TOP, MIDDLE, BOTTOM;
	}

	/**
	 * Add link to a new Wiki page
	 * 
	 * @param pageName
	 * 			pageName that will be the target link
	 * @param label
	 * 			label of link that will be added into Wiki page
	 * @param tooltip
	 * 			
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertnewPageLink2WikiPage(String pageName, String label, String tooltip,Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_WIKI_PAGE_LINK);
		Utils.pause(500);
		info("Create link to the webpage " + pageName);
		if(pageName!=null && pageName!=""){
			click(ELEMENT_ADD_NEW_PAGE_LINK);
			Utils.pause(500);
			click(button.ELEMENT_SELECT_BUTTON);
			Utils.pause(500);
			type(ELEMENT_ADD_NEW_LINKPAGE_TEXTBOX,pageName,true);
		}
		else
			click(button.ELEMENT_SELECT_BUTTON);
		Utils.pause(500);
		click(button.ELEMENT_SETTING_LINK_BUTTON);
		Utils.pause(500);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));		
			switchToParentWindow();
		}
	}

	/**
	 * attach a file to wiki page
	 * @param file
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertAttachFileExits(String file, String fileattach, String tooltip, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_LINK);
		Utils.pause(500);
		click(ELEMENT_ALL_PAGE_TAB);
		Utils.pause(1000);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_CEL.replace("${file}", "WikiHome"));
		Utils.pause(500);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_CEL.replace("${file}", file));
		Utils.pause(500);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_CEL.replace("${file}", "Attachments"));
		Utils.pause(500);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_CEL.replace("${file}", fileattach));
		Utils.pause(500);
		click(but.ELEMENT_SELECT_BUTTON);
		waitForElementNotPresent(but.ELEMENT_SELECT_BUTTON);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			waitForAndGetElement(By.linkText(fileattach));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));	
			switchToParentWindow();
		}
	}

	/**
	 * attach a file to wiki page
	 * @param file
	 * @param label
	 * 			label of link that will be added into Wiki page
	 * @param tooltip
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertAttachNewFile(String file, String label, String tooltip,Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		String path = Utils.getAbsoluteFilePath("TestData/"+file);
		mouseOverAndClick(ELEMENT_LINK);
		mouseOverAndClick(ELEMENT_ATTACH_FILE_LINK);
		Utils.pause(500);
		click(but.ELEMENT_SELECT_BUTTON);
		waitForElementNotPresent(but.ELEMENT_SELECT_BUTTON);
		click(ELEMENT_ATTACH_FILE_PATH);
		WebElement upload = waitForAndGetElement(ELEMENT_ATTACH_FILE_PATH, 5000, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; " +
				"arguments[0].style.width = '1px'; arguments[0].style.opacity = 1", upload);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
		upload.sendKeys(path);
		Utils.pause(500);
		click(button.ELEMENT_SETTING_LINK_BUTTON);
		Utils.pause(500);
		if(label!=null && label!="")
			type(ELEMENT_LABEL_LINK_TEXTBOX,label,true);
		if(tooltip!=null && tooltip!="")
			type(ELEMENT_TOOLTIP_LINK_TEXTBOX,tooltip,true);
		Utils.pause(500);
		click(but.ELEMENT_CREATE_LINK_BUTTON);
		waitForElementNotPresent(but.ELEMENT_CREATE_LINK_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(label!=null && label!="")
				waitForAndGetElement(By.linkText(label));
			if(tooltip!=null && tooltip!="")
				waitForAndGetElement(By.xpath("//*[@title='"+tooltip+"']"));	
			switchToParentWindow();
		}
	}

	/**
	 * add an image file to wiki
	 * @param imageLocation
	 * @param opParam
	 * 			 verify - true: verify result
	 * 			 veriry - false: not verify result 
	 */
	public void insertExternalImage(String imageLocation, String width, String height, String text, alignmentType alignment, Object...opParam){
		Boolean verify =(Boolean)(opParam.length>0?opParam[0]:false);
		mouseOverAndClick(ELEMENT_IMAGE_LINK);
		mouseOverAndClick(ELEMENT_IMAGE_EXTERNAL_LINK);
		Utils.pause(500);
		if(imageLocation!=null && imageLocation!="")
			type(ELEMENT_IMAGE_LOCATION,imageLocation,true);
		Utils.pause(500);
		click(ELEMENT_IMAGE_SETTING_BUTTON);
		info("input width and height");
		if(width!=null && width!="")
			type(ELEMENT_IMAGE_WIDTH,width,true);
		if(height!=null && height!="")
			type(ELEMENT_IMAGE_HEIGHT,height,true);
		if(text!=null && text!="")
			type(ELEMENT_IMAGE_ALTERNATIVE_TEXT,text,true);
		if(alignment!=null)
			click(By.xpath("//*[@value='"+String.valueOf(alignment)+"']"));
		click(ELEMENT_IMAGE_INSERT_BUTTON);
		waitForElementNotPresent(ELEMENT_IMAGE_INSERT_BUTTON);	
		if(verify){
			WebElement e = waitForAndGetElement(ELEMENT_CONTENT_WIKI_FRAME,DEFAULT_TIMEOUT,1,2);
			driver.switchTo().frame(e);
			if(width!=null && width!=""){
				String widthImage = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("width");
				assert widthImage.equalsIgnoreCase(width);
			}
			if(text!=null && text!=""){
				String alt = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("alt");
				assert alt.equalsIgnoreCase(text);
			}
			if(alignment!=null){
				String alignmentImage = waitForAndGetElement(By.xpath("//*[@id='body']/img")).getAttribute("style");
				assert alignmentImage.toLowerCase().contains(String.valueOf(alignment).toLowerCase());
			}
			switchToParentWindow();
		}
	}	
}
