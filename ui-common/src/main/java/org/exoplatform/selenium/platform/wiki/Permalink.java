package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.platform.ManageAccount;
import org.openqa.selenium.By;

/**
 * Provides all methods to share/restrict a page's content with other users. 
 * 
 *
 */
public class Permalink extends BasicAction {
	ManageAccount magAc;
	Dialog dialog;
	
	public final By ELEMENT_PERMALINK_TEXT = By.id("PermalinkText");
	public final By ELEMENT_PERMALINK_NOTIFY = By.xpath("//*[@id='UIWikiPermalinkForm']/*[@class='permalinkNotify']");
	public final By ELEMENT_MAKE_PUBLIC_BUTTON = By.xpath("//*[text() = 'Make Public']");
	public final By ELEMENT_MAKE_RESTRICT_BUTTON = By.xpath("//*[text()= 'Restrict']");
	public final By ELEMENT_PERMISSION_MANAGEMENT = By.xpath("//*[text() = 'Manage Permissions']");
	public final String DATA_NOTIFY_RESTRICT = "This page is restricted. Only authorized users can view and edit it.";
	public final String DATA_NOTIFY_PUBLIC = "This page is public. Anybody can view and edit it.";
	
	/**
	 * Gets a permanent link by a given value.
	 * 
	 * @return The value.
	 */
	public String getPermalink(){
		return getValue(ELEMENT_PERMALINK_TEXT);
	}
	
	/**
	 * Goes to a Wiki page by a permanent link.
	 * 
	 * 
	 * @param user The username to log into Platform.
	 * @param permalink The URL of a permalink.
	 * @param permission Choose an option for user to view a Wiki page.
	 * @param content The content of Wiki page.
	 */
	public void goToWikiByPermalink(String user, String permalink, boolean permission, String content){
		magAc = new ManageAccount(driver,this.plfVersion);
		
		info("Check permalink with user " + user);
		driver.get(permalink);
		magAc.signIn(user, DATA_PASS);
		
		if (permission){
			waitForTextPresent(content);
		}else {
			waitForTextPresent("Page Not Found");
			waitForTextNotPresent(content);
		}
		magAc.signOut();
	}
	
	/** 
	 * Makes public to the page which is needed to change into the public status.
	 * 
	 * 
	 * @param opParams Choose an option to make Wiki page public. 
	 */
	public void makePublicPage(Boolean...opParams){
		//update by phuongdt
		//(useRestrictLink = true: make public by Restrict link)
		
		info("Make public page");
		Boolean useRestrictLink = (Boolean)(opParams.length>0 ? opParams[0]:false);
		if(useRestrictLink){
			waitForAndGetElement(ELEMENT_RESTRICTED_WIKI);
			click(ELEMENT_RESTRICTED_WIKI);
		}
		else{
			goToPermalink();
		}
		dialog = new Dialog(driver);
		click(ELEMENT_MAKE_PUBLIC_BUTTON);
		waitForAndGetElement(ELEMENT_MAKE_RESTRICT_BUTTON);
		assert getText(ELEMENT_PERMALINK_NOTIFY).contains(DATA_NOTIFY_PUBLIC);
		dialog.closeMessageDialog();
	}
	
	/**
	 * Makes restriction to the page which is needed to change into the restricted status.
	 * 
	 */
	public void makeRestrictedPage(){
		dialog = new Dialog(driver);
		info("Make restrict page");
		goToPermalink();
		click(ELEMENT_MAKE_RESTRICT_BUTTON);
		waitForAndGetElement(ELEMENT_MAKE_PUBLIC_BUTTON);
		assert getText(ELEMENT_PERMALINK_NOTIFY).contains(DATA_NOTIFY_RESTRICT);
		dialog.closeMessageDialog();
	}
	
	/**
	 * Goes to the permissions of Wiki page.
	 * 
	 */
	public void goToPermissionFromPermalink(){
		goToPermalink();
		click(ELEMENT_PERMISSION_MANAGEMENT);	
	}
}