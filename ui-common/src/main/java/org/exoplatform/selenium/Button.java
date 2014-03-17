package org.exoplatform.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * <li> This class provides simple way to manage all type of buttons in PLF4 </li>
 * @author vuna2
 * @Date January, 30th, 2013
 *
 */
public class Button extends TestBase{
	
	public Button(WebDriver dr,String...plfVersion) {
		driver = dr;
		this.plfVersion = plfVersion.length > 0 ? plfVersion[0]:"4.0";
	}

	//public final By ELEMENT_CONFIRM_BUTTON = By.xpath("//*[text()='Confirm']");
	public final By ELEMENT_SAVE_CLOSE_BUTTON = By.xpath("//*[text()='Save & Close']");
	public final By ELEMENT_SAVE_CLOSE_BUTTON_2 = By.xpath("//*[text()='Save And Close']");
	//By.linkText("Save & Close");

	public final By ELEMENT_OK_BUTTON = By.xpath("//*[text()='OK']");
	public final By ELEMENT_NO_BUTTON = By.xpath("//*[text()='No']");
	public final By ELEMENT_NO_BUTTON_AUX = By.xpath("//*[contains(@class, 'popup')]//*[contains(text(),'No')]");
	public final By ELEMENT_YES_BUTTON = By.xpath("//*[text()='Yes']");
	public final By ELEMENT_YES_BUTTON_AUX = By.xpath("//*[contains(@class, 'popup')]//*[contains(text(),'Yes')]");
	public final By ELEMENT_APPLY_FRENCH_BUTTON = By.linkText("Appliquer");
	public final By ELEMENT_APPLY_GERMAN_BUTTON = By.linkText("Anwenden");
	public final By ELEMENT_APPLY_BUTTON = By.xpath("//*[text()='Apply']");
			//By.linkText("Apply");
	public final By ELEMENT_SAVE_BUTTON = By.xpath("//*[text()='Save']"); 
	public final By ELEMENT_SAVE_BUTTON_POPUP_WINDOWS = By.xpath("//*[contains(@class, 'popup')]//*[text()='Save']");
	public final By ELEMENT_CANCEL_BUTTON = By.xpath("//*[contains(text(),'Cancel')]");
	public final By ELEMENT_CANCEL_BUTTON_AUX = By.xpath("//*[@data-original-title='Cancel']");
	public final By ELEMENT_CLOSE_BUTTON = By.xpath("//*[normalize-space(text())='Close']");
	public final By ELEMENT_ADD_BUTTON = By.xpath("//*[text()='Add']");
	public final By ELEMENT_SELECT_BUTTON = By.xpath("//*[text()='Select']");
	public final By ELEMENT_CONFIRM_BUTTON = By.xpath("//*[text()='Confirm']");
	public final By ELEMENT_CLOSE_WINDOW = By.xpath("//*[contains(@class, 'uiIconClose') and @title = 'Close Window']");
			//By.className("uiIconClose"); 
	public final By ELEMENT_MOVE_BUTTON = By.xpath("//button[contains(text(), 'Move')]");
	public final By ELEMENT_FINISH_ICON = By.xpath("//a[@title='Finish']"); //Finish editing portlet icon
	public final By ELEMENT_NEXT_BUTTON = By.xpath("//*[text()='Next']");	
	public final By ELEMENT_ABORT_BUTTON = By.xpath("//*[text()='Abort']");
	public final By ELEMENT_RESET_BUTTON = By.xpath("//*[text()='Reset']");
	public final By ELEMENT_PREVIOUS_BUTTON = By.xpath("//*[text()='Previous']");
	public final By ELEMENT_CREATE_LINK_BUTTON = By.xpath("//*[text()='Create Link']");
	public final By ELEMENT_CREATE_MACRO_BUTTON = By.xpath("//button[text()='Insert Macro']");
	public final By ELEMENT_INSERT_TABLE = By.xpath("//button[text()='Insert Table']");

	//ECMS Admin > Category > Add Category
	public final By ELEMENT_PREVIOUS_BUTTON_ADMIN_4 = By.xpath("//*[@class='UITaxonomyTreeCreateChild']//*[text()='Previous']");
	public final By ELEMENT_PREVIOUS_BUTTON_ADMIN_3 = By.xpath("//*[@class='UIActionTaxonomyManager']//*[text()='Previous']");

	public final By ELEMENT_RESTORE_BUTTON = By.xpath("//*[text()='Restore']");
	//Content explorer 
	public final By ELEMENT_REFRESH_BUTTON = By.xpath("//*[text()='refresh']");
	public final By ELEMENT_RENAME_BUTTON = By.xpath("//*[text()='Rename']");
	public final By ELEMENT_NEXT_PAGE_BUTTON = By.xpath("//*[@title='Next Page']");

	/*
	 * General
	 * 
	 */
	public void confirm() {
		waitForAndGetElement(ELEMENT_CONFIRM_BUTTON);
		click(ELEMENT_CONFIRM_BUTTON);
		Utils.pause(500);
	}
	public void save() {
		waitForAndGetElement(ELEMENT_SAVE_BUTTON);
		click(ELEMENT_SAVE_BUTTON);
		Utils.pause(500);
	}
	public void ok() {
		waitForAndGetElement(ELEMENT_OK_BUTTON);
		click(ELEMENT_OK_BUTTON);
		Utils.pause(500);
	}
	public void no() {
		waitForAndGetElement(ELEMENT_NO_BUTTON);
		click(ELEMENT_NO_BUTTON);
		Utils.pause(500);
	}
	public void yes() {		
		if (waitForAndGetElement(ELEMENT_YES_BUTTON, 3000, 0) != null){
			click(ELEMENT_YES_BUTTON);
		}else if (waitForAndGetElement(ELEMENT_YES_BUTTON_AUX, 3000, 0) != null){
			click(ELEMENT_YES_BUTTON_AUX);
		}
		Utils.pause(500);
	}
	
	public void close(){
		waitForAndGetElement(ELEMENT_CLOSE_BUTTON);
		click(ELEMENT_CLOSE_BUTTON);
		waitForElementNotPresent(ELEMENT_CLOSE_BUTTON);
		Utils.pause(500);
	}

	public void cancel(){
		if (waitForAndGetElement(ELEMENT_CANCEL_BUTTON, 3000, 0) != null){
			click(ELEMENT_CANCEL_BUTTON);
		}else {
			click(ELEMENT_CANCEL_BUTTON_AUX);
		}
	}
	
	public void add(){
		waitForAndGetElement(ELEMENT_ADD_BUTTON);
		click(ELEMENT_ADD_BUTTON);
		waitForElementNotPresent(ELEMENT_ADD_BUTTON);
		Utils.pause(500);
	}

	public void saveAndClose(){
		if (waitForAndGetElement(ELEMENT_SAVE_CLOSE_BUTTON, 3000, 0) != null){
			click(ELEMENT_SAVE_CLOSE_BUTTON);
		}else if (waitForAndGetElement(ELEMENT_SAVE_CLOSE_BUTTON_2, 3000, 0) != null){
			click(ELEMENT_SAVE_CLOSE_BUTTON_2);
		}
		Utils.pause(500);
	}

	public void apply(){
		waitForAndGetElement(ELEMENT_APPLY_BUTTON);
		click(ELEMENT_APPLY_BUTTON);
	}

	public void next(){
		waitForAndGetElement(ELEMENT_NEXT_BUTTON);
		click(ELEMENT_NEXT_BUTTON);	
		Utils.pause(500);
	}

	public void refresh(){	
		waitForAndGetElement(ELEMENT_REFRESH_BUTTON);
		click(ELEMENT_REFRESH_BUTTON);
		Utils.pause(500);
	}

	public void rename(){
		waitForAndGetElement(ELEMENT_RENAME_BUTTON);
		click(ELEMENT_RENAME_BUTTON);
		Utils.pause(500);
	}

	public void previous(){
		waitForAndGetElement(ELEMENT_PREVIOUS_BUTTON);
		click(ELEMENT_PREVIOUS_BUTTON);	
		Utils.pause(500);
	}

	public void closeWindow(){
		if (isElementPresent(ELEMENT_CLOSE_WINDOW)){
			click(ELEMENT_CLOSE_WINDOW);
		}else if (isElementPresent(By.xpath("//*[contains(@class, 'wikiPreviewHeader')]//*[contains(@class, 'uiIconClose')]"))){
			click(By.xpath("//*[contains(@class, 'wikiPreviewHeader')]//*[contains(@class, 'uiIconClose')]"));
		}
		Utils.pause(1000);
	}
	
}
