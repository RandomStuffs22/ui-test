package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class NavigationToolbar extends PlatformBase {

	ManageAccount acc;
	BrandingManagement brandMag;
	PeopleConnection peoCon;
	HomePageActivity hpAct;

	public final By ELEMENT_MENU_EDIT_LINK = By.xpath("//i[@class='uiIconPLF24x24Edit']");
	public final By ELEMENT_MENU_EDIT_CONTENT = By.xpath("//i[@class='quickEditChecked']");
	public final By ELEMENT_MENU_EDIT_CONTENT_UNCHECK = By.xpath("//i[@class='quickEditUnchecked']");
	public final By ELEMENT_EDIT_MENU_ID = By.xpath("//*[@id='UIAdminToolbarPortlet']/../..");
	public final By ELEMENT_SEO_MENU = By.xpath("//span[contains(text(),'SEO')]");
	public final By ELEMENT_PAGE_ID = By.xpath("//*[contains(@id, 'UIPage-')]");
	public final By ELEMENT_ADD_PAGE_MENU = By.xpath("//a[contains(text(),'Add Page')]");

	public NavigationToolbar(WebDriver dr, String...plfVersion){
		driver = dr;
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
		brandMag = new BrandingManagement(driver);
		button = new Button(driver);
		peoCon = new PeopleConnection(driver);
		
	} 

	//Go to portal sites
	public void goToPortalBranding() {
		info("--Go to Portal Branding Management--");
		String url = DEFAULT_BASEURL + "/g/:platform:administrators/branding";
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				driver.get(url);
				break;
			}
			mouseOver(ELEMENT_LINK_SETUP, true);
			if (waitForAndGetElement(ELEMENT_LINK_PORTAL, 5000, 0)!= null) {	
				mouseOver(ELEMENT_LINK_PORTAL, false);
				if (waitForAndGetElement(ELEMENT_LINK_BRANDING, 5000, 0)!= null){
					click(ELEMENT_LINK_BRANDING);
					break;
				}
			}
			info("Retry...[" + repeat + "]");
		}
		waitForAndGetElement(brandMag.ELEMENT_PREVIEW_LOGO);
		waitForAndGetElement(brandMag.ELEMENT_UPLOAD_BUTTON);
		waitForAndGetElement(brandMag.ELEMENT_NAVIGATION_STYLE);
		waitForAndGetElement(brandMag.ELEMENT_TABLE_COLUMN_CONTAINER);
		waitForAndGetElement(button.ELEMENT_CANCEL_BUTTON);
		waitForAndGetElement(button.ELEMENT_SAVE_BUTTON);
	}

	//Go to portal sites
	public void goToPortalSites() {
		info("--Go to Portal Site Management--");
		String url = DEFAULT_BASEURL + "/g/:platform:administrators/portalnavigation";
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				driver.get(url);
				break;
			}
			//mouseOverAndClick(ELEMENT_LINK_SETUP);
			mouseOver(ELEMENT_LINK_SETUP, true);
			if (waitForAndGetElement(ELEMENT_LINK_PORTAL, 5000, 0)!= null) {	
				mouseOver(ELEMENT_LINK_PORTAL, false);
				if (waitForAndGetElement(ELEMENT_LINK_SITES, 5000, 0)!= null){
					click(ELEMENT_LINK_SITES);
					break;
				}
			}
			info("Retry...[" + repeat + "]");
		}
		waitForAndGetElement(ELEMENT_MANAGE_SITE_TITLE);
	}

	//Go to Portal Manage Pages	
	public void goToManagePages() {
		info("--Go to Page Management--");
		String url = DEFAULT_BASEURL + "/g/:platform:administrators/administration/pageManagement";
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				driver.get(url);
				break;
			}
			//mouseOverAndClick(ELEMENT_LINK_SETUP);
			mouseOver(ELEMENT_LINK_SETUP, true);
			if (waitForAndGetElement(ELEMENT_LINK_PORTAL, 5000, 0)!= null) {	
				mouseOver(ELEMENT_LINK_PORTAL, false);
				if (waitForAndGetElement(ELEMENT_LINK_PAGES, 5000, 0)!= null){
					click(ELEMENT_LINK_PAGES);
					break;
				}
			}
			info("Retry...[" + repeat + "]");
		}
		//waitForTextPresent("Page Id");
		waitForAndGetElement(ELEMENT_PAGE_MANAGEMENT_SEARCH_BUTTON);
	}

	//Go to Dashboard
	public void goToDashboard(){
		info("--Go to Dashboard page--");
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
				break;
			}
			mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
			if (waitForAndGetElement(ELEMENT_DASHBROARD_LINK, 5000, 0) != null){
				info("Element " + ELEMENT_DASHBROARD_LINK + "... is displayed");
				break;
			}
			info("Retry...[" + repeat + "]");
		}
		//mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
		click(ELEMENT_DASHBROARD_LINK);
		//waitForTextPresent("My Dashboard");
		Utils.pause(1000);
	}

	//Go to User management page
	public void goToNewStaff() {
		info("Go to New Staff");
		//goToPage(ELEMENT_SEARCH_ICON_REGISTER, ELEMENT_LINK_SETUP, ELEMENT_LINK_USERS, ELEMENT_LINK_ADD_USERS);
		//mouseOverAndClick(ELEMENT_LINK_SETUP);
		//mouseOver(ELEMENT_LINK_SETUP, true);
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				mouseOverAndClick(ELEMENT_LINK_SETUP);
				break;
			}
			mouseOver(ELEMENT_LINK_SETUP, true);
			if (waitForAndGetElement(ELEMENT_LINK_USERS, 5000, 0) != null){
				info("Element " + ELEMENT_LINK_USERS + "... is displayed");
				break;
			}
			info("Retry...[" + repeat + "]");
		}
		click(ELEMENT_LINK_USERS);
		Utils.pause(1000);
	}


	//Function go to My Setting
	public void goToMySetting(){
		info("---Go to My Setting ---");
		mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
		if (waitForAndGetElement(ELEMENT_MY_SETTING, 5000, 0) == null){
			info("Cannot use [mouseOver] on Navigation Tool Bar");
			mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
		}
		mouseOverAndClick(ELEMENT_MY_SETTING);
		Utils.pause(1000);
	}

	//Go to Portal/Group Sites
	public void goToGroupSites(){
		info("--Go to Group Site Management--");
		String url = DEFAULT_BASEURL + "/g/:platform:administrators/groupnavigation";
		//driver.get(url);
		Utils.pause(1000);
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				driver.get(url);
				break;
			}
			//mouseOverAndClick(ELEMENT_LINK_SETUP);
			mouseOver(ELEMENT_LINK_SETUP, true);
			if (waitForAndGetElement(ELEMENT_LINK_PORTAL, 5000, 0)!= null) {	
				mouseOver(ELEMENT_LINK_PORTAL, false);
				if (waitForAndGetElement(ELEMENT_LINK_GROUP, 5000, 0)!= null){
					click(ELEMENT_LINK_GROUP);
					break;
				}
			}
			info("Retry...[" + repeat + "]");
		}
	}

	//Go to Portal/Sites
	public void goToSites(){
		info("--Go to Sites --");
		String url = DEFAULT_BASEURL + "/g/:platform:administrators/portalnavigation";
		//driver.get(url);
		Utils.pause(1000);
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				driver.get(url);
				break;
			}
			//mouseOverAndClick(ELEMENT_LINK_SETUP);
			mouseOver(ELEMENT_LINK_SETUP, true);
			if (waitForAndGetElement(ELEMENT_LINK_PORTAL, 5000, 0)!= null) {	
				mouseOver(ELEMENT_LINK_PORTAL, false);
				if (waitForAndGetElement(ELEMENT_LINK_SITES, 5000, 0)!= null){
					click(ELEMENT_LINK_SITES);
					break;
				}
			}
			info("Retry...[" + repeat + "]");
		}
	}


	//Go to add page locator with Editor
	//	public void goToAddPageEditor(){
	//		info("Go to add page editor");
	//		((JavascriptExecutor)driver).executeScript("javascript:ajaxGet(eXo.env.server.createPortalURL('UIWorkingWorkspace', 'PageCreationWizard', true));");
	//		waitForTextPresent("Page Creation Wizard");
	//	}

	public void goToUsersAndGroupsManagement() {
		info("--Go to Users and groups management--");
		mouseOver(ELEMENT_LINK_SETUP, true);
		WebElement element = waitForAndGetElement(By.xpath(ELEMENT_GROUP_AND_ROLE_LINK), DEFAULT_TIMEOUT, 1, 2);		
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		Utils.pause(500);
	}

	//Go to Portal Application Registry
	public void goToApplicationRegistry() {
		info("--Go to Portal Application Registry--");
		for (int repeat = 0;; repeat ++){
			if (repeat > 4){
				mouseOverAndClick(ELEMENT_LINK_SETUP);
				break;
			}
			mouseOver(ELEMENT_LINK_SETUP, false);
			if (waitForAndGetElement(ELEMENT_APPLICATIONS_LINK, 3000, 0) != null){
				info("Link application is displayed");
				break;
			}
			info("Retry..." + repeat + "...");
		}
		click(ELEMENT_APPLICATIONS_LINK);
		Utils.pause(500);
	}

	public void goToEditPageEditor () {
		info("----Go to Edit page editor----");
		String id = waitForAndGetElement(By.xpath("//*[@class='UIPage']")).getAttribute("id").replace("UIPage-", "");
		((JavascriptExecutor)driver).executeScript("javascript:ajaxGet(eXo.env.server.createPortalURL('" + id + "', 'EditCurrentPage', true));");
		Utils.pause(500);
	}

	//Go to change language for user interface
	public void goToChangeLanguageForUserInterface(){
		Actions actions = new Actions(driver);
		info("--Go to change language for user interface--");
		WebElement UI = driver.findElement(By.id("UserNavigationTabsContainer"));
		actions.moveToElement(UI).build().perform();
		driver.findElement(By.linkText("Change Language")).click();	
		Utils.pause(500);
	}

	//Go to register page in public mode
	public void goToRegisterPageInPublicMode(WebDriver driverTest){
		String registerPageLink = baseUrl.concat("/portal/intranet/Register");
		driverTest.get(registerPageLink);
		waitForTextPresent("Create a New Account");
	}

	// Go to content administration
	public void goToContentAdministration()
	{
		String url = DEFAULT_BASEURL + "/g/:platform:web-contributors/wcmAdmin";
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				driver.get(url);
				break;
			}
			//mouseOverAndClick(ELEMENT_LINK_SETUP);
			mouseOver(ELEMENT_LINK_SETUP, true);
			if (waitForAndGetElement(ELEMENT_MENU_CONTENT_LINK, 5000, 0)!= null) {	
				mouseOver(ELEMENT_MENU_CONTENT_LINK, true);
				if (waitForAndGetElement(ELEMENT_LINK_CONTENT_ADMIN, 5000, 0)!= null){
					click(ELEMENT_LINK_CONTENT_ADMIN);
					break;
				}
			}
			info("Retry...[" + repeat + "]");
		}
		//waitForTextPresent("Manage ECM Main Functions");
		Utils.pause(1000);
	}

	//Enter Sites Management Form 
	public void goToSiteExplorer(){
		Utils.pause(500);
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				mouseOverAndClick(ELEMENT_LINK_SETUP);
				break;
			}
			mouseOver(ELEMENT_LINK_SETUP, true);
			if (waitForAndGetElement(ELEMENT_MENU_CONTENT_LINK, 5000, 0) != null){
				info("Element " + ELEMENT_MENU_CONTENT_LINK + "... is displayed");
				break;
			}
			info("Retry...[" + repeat + "]");
		}
		//mouseOverAndClick(ELEMENT_LINK_SETUP);
		//mouseOver(ELEMENT_LINK_SETUP, true);
		mouseOverAndClick(ELEMENT_MENU_CONTENT_LINK);
		//click(ELEMENT_MENU_SITE_EXPLORER);
		Utils.pause(2000);
	}

	//Enter Search Form  (Administration > Content > Search menu)
	public void goToSearch()
	{
		String url = DEFAULT_BASEURL + "/g/:platform:administrators/search";
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				driver.get(url);
				break;
			}
			mouseOver(ELEMENT_LINK_SETUP, true);
			if (waitForAndGetElement(ELEMENT_MENU_CONTENT_LINK, 5000, 0)!= null) {	
				mouseOver(ELEMENT_MENU_CONTENT_LINK, true);
				if (waitForAndGetElement(ELEMENT_MENU_SEARCH, 5000, 0)!= null){
					click(ELEMENT_MENU_SEARCH);
					break;
				}
			}
			info("Retry...[" + repeat + "]");
		}
		waitForAndGetElement(ELEMENT_SEARCH_FORM_CONTENT_TYPE_COLUMN);
		waitForAndGetElement(ELEMENT_SEARCH_FORM_DESCRIPTION_COLUMN);
		waitForAndGetElement(ELEMENT_SEARCH_FORM_ACTION_COLUMN);
	}

	/**
	 * @author lientm
	 */
	public void goToPersonalDocuments(){
		info("Go to Intranet/Documents");
		Utils.pause(500);
		click(ELEMENT_PERSONAL_DOCUMENTS);
		waitForAndGetElement(By.id("UIFileViewCheckBox"), 3000, 1, 2);
		//waitForTextPresent("Personal Documents");
	}

	//Go to Page Creation Wizard
	public void goToPageCreationWizard(){
		info("Go to add page wizard");
		mouseOverAndClick(ELEMENT_MENU_EDIT_LINK);
		mouseOver(ELEMENT_MENU_PAGE_LINK, true);
//		click(ELEMENT_ADD_PAGE_MENU);
		((JavascriptExecutor)driver).executeScript("javascript:ajaxGet(eXo.env.server.createPortalURL('UIWorkingWorkspace', 'PageCreationWizard', true));");

		//waitForTextPresent("Page Creation Wizard");
		Utils.pause(500);
	}

	//Go To Content Administration / Advanced Configuration / Manage Lock Tab
	/*public void goToLockTabInContentAdmin(){
		goToContentAdministration();
		click(ELEMENT_ADVANCED_CONFIGURATION_TAB);
		click(ELEMENT_MANAGE_LOCKS);
		click(ELEMENT_MANAGE_LOCK_TAB);
		Utils.pause(1000);
	}*/

	//Function to go to SEO management
	public void goToSeoManagement(){
		info("Go to SEO management form");
		Utils.pause(1000);
		click(ELEMENT_MENU_EDIT_LINK);
		mouseOver(ELEMENT_MENU_PAGE_LINK, true);
		WebElement seo = waitForAndGetElement(ELEMENT_SEO_MENU,10000,1,2);
		((JavascriptExecutor)driver).executeScript("arguments[0].click()",seo);		
		Utils.pause(1000);
	}

	/** Go to Edit/Page/Add Page
	 * @author phuongdt
	 */
	public void goToAddPageManagement(){
		info("Go to add page form");
		for(int repeat=0;; repeat ++){
			if (repeat > 4){
				mouseOverAndClick(ELEMENT_MENU_EDIT_LINK);
				break;
			}
			mouseOver(ELEMENT_MENU_EDIT_LINK, true);
			if (waitForAndGetElement(ELEMENT_MENU_PAGE_LINK, 5000, 0)!= null) {	
				mouseOver(ELEMENT_MENU_PAGE_LINK, true);
				if (waitForAndGetElement(ELEMENT_MENU_ADD_PAGE_LINK, 5000, 0)!= null){
					click(ELEMENT_MENU_ADD_PAGE_LINK);
					break;
				}
			}
			info("Retry...[" + repeat + "]");
		}
		Utils.pause(1000);
	}

	/** Go to Edit/Page/Add Page
	 * @author phuongdt
	 */
	public void goToEditLayout(){
		info("Go to Edit layout form");
		for(int repeat=0;; repeat ++){
			if (repeat > 4){
				mouseOverAndClick(ELEMENT_MENU_EDIT_LINK);
				break;
			}
			mouseOver(ELEMENT_MENU_EDIT_LINK, true);
			if (waitForAndGetElement(ELEMENT_MENU_PAGE_LINK, 5000, 0)!= null) {
				info("-- Click Pagemenu --");
				mouseOver(ELEMENT_MENU_PAGE_LINK,true);
				if (waitForAndGetElement(ELEMENT_MENU_EDIT_LAYOUT, 5000, 0)!= null){
					click(ELEMENT_MENU_EDIT_LAYOUT);
					break;
				}
			}
			else{
				String editPageRequest = "ajaxGet(eXo.env.server.createPortalURL('" + getPageId() + "', 'EditCurrentPage', true))";
				((JavascriptExecutor)driver).executeScript(editPageRequest);
				Utils.pause(1000);
				break;
			}
			info("Retry...[" + repeat + "]");
		}
		Utils.pause(1000);
	}

	//Function go to Home Page
	public void goToHomePage(){
		info("-- Go to home page --");
		click(ELEMENT_HOME_PAGE);
		Utils.pause(1000);
		driver.navigate().refresh();
		waitForAndGetElement(ELEMENT_REFRESH);
	}

	/**
	 * Function: Go to connection page
	 * @author phuongdt
	 * @date 24/09/2013
	 */
	public void goToConnectionPage(){
		hpAct = new HomePageActivity(driver, this.plfVersion);
		click(ELEMENT_CONNECTION_PAGE);
		waitForAndGetElement(peoCon.ELEMENT_EVERYONE_TAB);
		if(waitForElementNotPresent(hpAct.ELEMENT_ACTIVITY_TEXTBOX,DEFAULT_TIMEOUT,0) != null){
			clearCache();
			waitForAndGetElement(peoCon.ELEMENT_EVERYONE_TAB);
		}
	}

	public void changeEditMode()
	{
		mouseOver(ELEMENT_MENU_EDIT_LINK,true);
		mouseOverAndClick(ELEMENT_MENU_EDIT_CONTENT);
	}

	// Go to My Profile
	public void goToMyProfile(){
		info("--Go to My Profile--");		
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
				info("--Error mouse over and click: can't mouseover, need to use mouse over and click --");
				break;
			}
			mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
			if (waitForAndGetElement(ELEMENT_MY_PROFILE_LINK, 5000, 0) != null){
				info("Element " + ELEMENT_MY_PROFILE_LINK + "... is displayed");
				break;
			}
			info("Retry...[" + repeat + "]");
		}
		click(ELEMENT_MY_PROFILE_LINK);
		waitForAndGetElement(ELEMENT_MY_PROFILE_TAB);
	}	

	/** Go to IDE Page
	 * @author phuongdt
	 */
	public void goToIDEPage(){
		info("--Go to IDE Page--");
		String url = DEFAULT_BASEURL + "/g/:developers/ide";
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				driver.get(url);
				break;
			}
			mouseOver(ELEMENT_LINK_SETUP, true);
			if (waitForAndGetElement(ELEMENT_LINK_IDE, 5000, 0)!= null) {        
				click(ELEMENT_LINK_IDE, false);
				break;
			}
			info("Retry...[" + repeat + "]");
		}
		Utils.pause(2000);
	}

	/** Go to Event/Task
	 * Mouse over on the button "Create" (+)
	 * Select the item "Event/Task"
	 * @author phuongdt
	 */
	public void goToEventTask(){
		info("--Go to Add Event/Task--");		
		goToCreateMenu();
		click(ELEMENT_ADD_EVENT_TASK_ICON);
		waitForAndGetElement(ELEMENT_ADD_EVENT_TASK_FORM);
	}

	/** Go to Poll
	 * Mouse over on the button "Create" (+)
	 * Select the item "Poll"
	 * @author phuongdt
	 */
	public void goToPoll(){
		info("--Go to Add Poll--");		
		goToCreateMenu();
		click(ELEMENT_ADD_POLL_ICON);
		waitForAndGetElement(ELEMENT_ADD_POLL_FORM);
		button.next();
	}

	/** Go to Topic
	 * Mouse over on the button "Create" (+)
	 * Select the item "Topic"
	 * @author phuongdt
	 */
	public void goToTopic(Object... option){
		String forumName = (String)(option.length > 0 ? option[0]: "");
		info("--Go to Add Topic--");		
		goToCreateMenu();
		click(ELEMENT_ADD_TOPIC_ICON);
		waitForAndGetElement(ELEMENT_ADD_TOPIC_FORM);
		button.next();
		if(isElementPresent(ELEMENT_SELECT_FORUM)){
			info("Select forum");
			click(ELEMENT_SELECT_FORUM);
			if(forumName!="")
				click(ELEMENT_SELECT_FORUM_ITEM.replace("${forumName}", forumName));
			Utils.pause(500);
			button.next();
		}
	}

	/** Go to Wiki
	 * Mouse over on the button "Create" (+)
	 * Select the item "Wiki"
	 * @author phuongdt
	 */
	public void goToWiki(){
		info("--Go to Add Wiki Page--");		
		goToCreateMenu();
		click(ELEMENT_ADD_WIKI_ICON);
		waitForAndGetElement(ELEMENT_ADD_WIKI_FORM);
		button.next();
	}

	/** Go to upload file
	 * Mouse over on the button "Create" (+)
	 * Select the item "Upload File"
	 * @author phuongdt
	 */
	public void goToUploadFile(){
		info("--Go to Upload File--");		
		goToCreateMenu();
		click(ELEMENT_ADD_UPLOAD_FILE_ICON);
		waitForAndGetElement(ELEMENT_UPLOAD_FILE_FORM);
	}

	/** Go to upload file
	 * Mouse over on the button "Create" (+)
	 */
	public void goToCreateMenu(){
		info("--Go to Create icon --");		
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				mouseOverAndClick(ELEMENT_ADD_ICON);
				info("--Error mouse over and click: can't mouseover, need to use mouse over and click --");
				break;
			}
			mouseOver(ELEMENT_ADD_ICON, true);
			if (waitForAndGetElement(ELEMENT_ADD_EVENT_TASK_ICON, 5000, 0) != null){
				break;
			}
			info("Retry...[" + repeat + "]");
		}
	}

	/**
	 * Get pageID to edit layout
	 * @author phuongdt
	 */
	public String getPageId(){
		String pageElement = waitForAndGetElement(ELEMENT_PAGE_ID).getAttribute("id");
		int beginIndex = pageElement.indexOf("-");
		return pageElement.substring(beginIndex+1);
	}
}