package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author HangNTT
 * date: 23/10/2013
 */
public class HomePageGadget extends PlatformBase{

	//-------Invitation Gadget --------
	public By ELEMENT_INVITATION_GADGET = By.id("InvitationsPortlet");
	public String ELEMENT_SHOW_CONNECTIONS_REQUEST_USER = "//div[@id='InvitationsPortlet']//div[@class='peopleInviteName']//div[text()='${nameinvitation}']";
	public String ELEMENT_INVITATION_GADGET_USER_41 = "//div[@id='InvitationsPortlet']//div[@class='peopleInviteName']//a[contains(text(),'${nameinvitation}')]";
	public String ELEMENT_SHOW_CONNECTIONS_REQUEST_USER_PLF41="//div[@id='InvitationsPortlet']//div[@class='peopleInvitePicture pull-left avatarXSmall']//a[@href='/portal/intranet/profile/${peopleName}']";
	public String ELEMENT_SHOW_CONNECTIONS_REQUEST_SPACE= "//div[@id='InvitationsPortlet']//div[@class='spaceInviteInfo']//div[text()='${namespace}']";
	public String ELEMENT_VERIFY_STATUS_SPACE = "//div[@id='InvitationsPortlet']//div[@class='spaceInviteInfo']//div[text()='${namespace}']/../div[@class='spaceproperties']/div[@class='spacevisibility' and contains(text(),'${statusspace}')]";
	public String ELEMENT_SHOW_ACCEPTS_BUTTON = "//div[@id='InvitationsPortlet']//div[@class='peopleInviteInfo']//div[text()='${peopleName}']/..//a[text()='Accept']";
	public String ELEMENT_INVITATION_GADGET_USER_ACCEPT_41 = "//div[@id='InvitationsPortlet']//div[@class='peopleInviteInfo']//a[text()='${peopleName}']/../..//a[contains(text(),'Accept')]";
	public String ELEMENT_REMOVE_INVITATION_BUTTON = "//div[@id='InvitationsPortlet']//div[@class='peopleInviteInfo']//div[text()='${peopleName}']/..//i[@class='uiIconClose']";
	public String ELEMENT_REMOVE_INVITATION_BUTTON_41 = "//div[@id='InvitationsPortlet']//div[@class='peopleInviteInfo']//a[text()='${peopleName}']/../..//i[@class='uiIconClose']";
	public String ELEMENT_TITLE_OF_GAGDET = "//div[@id='InvitationsPortlet']/..//span[text()='${number}']";
	public By ELEMENT_PROFILE_PICTURE_GADGET = By.xpath("//div[@class='peopleInvitePicture pull-left avatarXSmall']");
	public String ELEMENT_SPACE_ACCEPT_BUTTON = "//div[@id='InvitationsPortlet']//div[@class='spaceInviteInfo']//div[text()='${namespace}']/..//a[text()='Accept']";
	public String ELEMENT_SPACE_REMOVE_BUTTON = "//div[@id='InvitationsPortlet']//div[@class='spaceInviteInfo']//div[text()='${namespace}']/..//i[@class='uiIconClose']";
	public String ELEMENT_CONNECTIONS_REQUEST_USER_INDEX = "//ul[@id='requests']//li['${index}']/../../..//div[@class='peopleInviteName']//div[text()='${nameinvitation}']";
	public String ELEMENT_CONNECTIONS_REQUEST_SPACE_INDEX = "//ul[@id='requests']//li['${index}']/../../..//div[@class='spaceInviteInfo']//div[text()='${namespace}']";
	public By ELEMENT_ICON_SPACE_GADGET = By.xpath("//div[@class='spaceInvitePicture pull-left avatarXSmall']");

	//-------Getting Started Gadget -------- 
	public By ELEMENT_GETTING_STARTED_GADGET_FORM = By.className("GettingStartedContainer");
	public By ELEMENT_PROFILE_PICTURE = By.linkText("Add a profile picture");
	public By ELEMENT_CONNECT_TO_COWORKERS = By.linkText("Connect to coworkers");
	public By ELEMENT_JOIN_A_SPACE = By.linkText("Join a space");
	public By ELEMENT_POST_AN_ACTIVITY = By.linkText("Post an activity");
	public By ELEMENT_UPLOAD_A_DOCUMENT = By.linkText("Upload a document");
	public By ELEMENT_PROFILE_PAGE = By.id("UIProfilePortlet");
	public By ELEMENT_ALL_PEOPLE = By.id("UIAllPeoplePortlet");
	public By ELEMENT_ALL_SPACE = By.id("UIAllSpacesPortlet");
	public By ELEMENT_PERSONAL_DOCUMENT = By.id("UIJCRExplorer");
	public String ELEMENT_FINISH_UPLOAD_FILE = "//ul[@id='gsList']//li[@class='${status}']/a[text()='Upload a document']";
	public String ELEMENT_FINISH_JOIN_TO_SPACE = "//ul[@id='gsList']//li[@class='${status}']/a[text()='Join a space']";
	public By ELEMENT_INPROGRESS_COMPLETE = By.xpath("//div[@id='progress-block']//div[@id='progress-rate']/../../div[@id='progress-label' and contains(text(),'100 %')]");
	public By ELEMENT_CLOSE_GADGET_GETTING_STARTED = By.xpath("//div[@id='DeleteLink']//button[text()='Close']");
	public By ELEMENT_X_CLOSE_BUTTON_GADGET_GETTING_STARTED = By.xpath("//div[@class='UIGadgetThemes uiBox uiGettingStarted']//a[@class='uiIconClose pull-right']");

	//-------Suggestions Gadget -------- 
	public By ELEMENT_SUGGESTION_GADGET_FORM = By.xpath("//div[@class='uiBox uiSuggestions']//h6[@class='title center' and text()='Suggestions']");
	public String ELEMENT_VERIFY_USER_SUGGESTIONS = "//*[@id='peopleSuggest']//*[@class='peopleName']/a[contains(text(),'${peopleName}')]";
	public String ELEMENT_VERIFY_SPACE_SUGGESTIONS = "//*[@id='spaceSuggest']//*[@class='spaceInfo']/div[@class='spaceName' and text()='${spaceName}']";
	public String ELEMENT_CONNECT_USER_SUGGESTIONS = "//*[@id='peopleSuggest']//*[@class='peopleName']/a[contains(text(),'${peopleName}')]/../..//a[text()='Connect']";
	public String ELEMENT_REQUEST_SPACE_SUGGESTIONS = "//*[@id='spaceSuggest']//*[@class='spaceInfo']/div[@class='spaceName' and contains(text(),'${spaceName}')]/../..//a[text()='Request']";
	public String ELEMENT_REMOVE_USER_SUGGESTIONS = "//*[@id='peopleSuggest']//*[@class='peopleName']/a[contains(text(),'${peopleName}')]/../..//i[@class='uiIconClose']";
	public String ELEMENT_REMOVE_SPACE_SUGGESTIONS="//*[@id='spaceSuggest']//*[@class='spaceInfo']/div[@class='spaceName' and contains(text(),'${spaceName}')]/../..//i[@class='uiIconClose']";
	public String ELEMENT_VERIFY_SPACE_SUGGESTIONS_INDEX = "//*[@id='spaceSuggest']//li['${index}']/../..//div[@class='spaceName' and text()='${spaceName}']";
	public String ELEMENT_VERIFY_STATUS_SPACE_SUGGESTION = "//*[@class='spaceInfo']/div[@class='spaceName' and text()='${spaceName}']/../../../..//div[@class='spaceCommon']";
	public String ELEMENT_JOIN_SPACE_SUGGESTIONS = "//*[@id='spaceSuggest']//*[@class='spaceInfo']/div[@class='spaceName' and contains(text(),'${spaceName}')]/../..//a[text()='Join']";
	public String ELEMENT_VERIFY_USER_SUGGESTIONS_INDEX = "//*[@id='peopleSuggest']//li['${index}']//*[@class='peopleName']/a[contains(text(),'${peopleName}')]";

	//-------------------Who'sOnline Gadget-----------------------------------
	public By ELEMENT_WHOISONLINE_GADGET = By.id("onlineContent");
	public String ELEMENT_ONLINE_USER_ACC_IMG = "//*[@id='tipName']//a[@href='/portal/intranet/activities/${acc}']/img";
	public String ELEMENT_ONLINE_USER_TITLE = "//*[@id='tipName']//td[2]/a[@href='/portal/intranet/activities/${acc}']";
	public String ELEMENT_ONLINE_USER_AVATAR = "//ul[@id='onlineList']//a[@class='avatarXSmall' and @href='/portal/intranet/profile/${acc}']";
	public String ELEMENT_ONLINE_USER_STATUS = ".//*[@id='tiptip_content']/blockquote[contains(text(),'${status}')]";
	public String ELEMENT_ONLINE_USER_STATUS_TRUNCATED = "//*[@id='tiptip_content']/blockquote/span[@class='truncate_ellipsis']";
	public String ELEMENT_WHOISONLINE_CONNECT_BUTTON_INVITE = "//*[@id='tiptip_content']//div[@data-action='Invite:${acc}']";
	public String ELEMENT_WHOISONLINE_CONNECT_BUTTON_ACCEPT = "//*[@id='tiptip_content']//div[@data-action='Accept:${acc}']";
	//My Profile tab
	public String ELEMENT_PROFILE_TAB_USER_INFO = "//*[@id='UIUserNavigationPortlet']/ul[@class='nav nav-tabs userNavigation']//a[@href='/portal/intranet/profile/${acc}']";
	//My activity stream tab
	public String ELEMENT_MY_AS_TAB = "//*[@id='UIUserNavigationPortlet']//a[@href='/portal/intranet/activities/${acc}']";
	
	//-------------------Bookmarks gadget-----------------------------------
	public final By ELEMENT_APPLICATION_BOOKMARKS = By.id("Gadgets/Bookmark");
	public By ELEMENT_BOOKMARKS_GADGET_CONTENT_LIST = By.id("BookmarkList");
	public By ELEMENT_BOOKMARKS_GADGET_ADDBOOKMARK_ICON = By.xpath("//*[@data-original-title='Add Bookmark']");
	public static By ELEMENT_BOOKMARKS_GADGET_ADDNAME = By.xpath("//*[@class='editName' and @placeholder='Bookmarks']");
	public static By ELEMENT_BOOKMARKS_GADGET_ADDURL = By.xpath("//*[@class='editLink' and @placeholder='URL']");
	public String ELEMENT_BOOKMARKS_GADGET_DELETE_ICON = "//*[text()='${bookmarkName}']/..//*[@ data-original-title='Delete']/i";
	
	/*Feature Poll porlet*/
	public final By ELEMENT_APPLICATION_POLL = By.id("Gadgets/FeaturedPoll"); 
	public final By ELEMENT_SETTING_POLL_GADGET = By.xpath("//*[contains(text(),'Featured Poll')]//*[@class='uiIconSetting']");
	public final By ELEMENT_SELECT_BOX_FEATURED_POLL = By.xpath("//*[@class='selectbox']");
	public final String ELEMENT_SELECT_BOX_FEATURED_ITEM = "//option[text()='${pollName}']";
	public final String ELEMENT_POLL_NAME_ITEM = "//*[@title='${pollOption}' or @data-original-title='${pollOption}']";
	
	/*My profile gadget*/
	public final By ELEMENT_APPLICATION_MY_PROFILE = By.id("Gadgets/Profile");
	public final By ELEMENT_PROFILE_PICTURE_IN_MY_PROFILE_GADGET = By.xpath("//*[@class='GadCont ProfilePicture']");
	public final By ELEMENT_PROFILE_INFO_IN_MY_PROFILE_GADGET = By.xpath("//*[@class='GadCont ProfileInfo']");

	/*Tools application*/
	public final By ELEMENT_APPLICATION_FAVORITEDOCUMENT = By.id("Tools/FavoriteDocument");
	public final By ELEMENT_RIGHT_CONTAINER = By.xpath("//div[@id='OfficeRight']//div[@class='NormalContainerBlock UIComponentBlock']");
	public final By ELEMENT_MIDDLE_CONTAINER = By.xpath("//div[@id='OfficeMiddle']//div[@class='NormalContainerBlock UIComponentBlock']");
	public final By ELEMENT_FAVORITEDOCUMENT_ICON_HOMEPAGE = By.xpath("//i[@class='uiIconFavoriteDocument uiIconLightGray']");
	public final By ELEMENT_GADGET_PORLET_IN_MIDDLE_HOME_PAGE = By.xpath("//div[@id='OfficeMiddle']//div[@class='NormalContainerBlock UIComponentBlock']//*[@id='UIGadgetPortlet']");
	public final By ELEMENT_DELETE_ICON_GADGET_PORLET_IN_MIDDLE_HOME_PAGE = By.xpath("//div[@id='OfficeMiddle']//div[@class='NormalContainerBlock UIComponentBlock']//*[@id='UIGadgetPortlet']/../../../..//*[@data-original-title='Delete Portlet']");
	public final By ELEMENT_SUGGESTION_GADGET = By.xpath("//div[@id='OfficeRight']//div[@class='NormalContainerBlock UIComponentBlock']//*[@id='content']");

	//-------------------------------------------------------------//

	public HomePageGadget(WebDriver dr, String...plfVersion){
		driver = dr;
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
		button = new Button(driver, this.plfVersion);
	}

	/**
	 * Accept an invitation
	 * @param peopleName
	 */
	public void acceptInvitationGadget(String peopleName) {
		info("-- Accept an invitation --");
		String elementAccept = "";
		if(this.plfVersion.equalsIgnoreCase("4.0"))
			elementAccept = ELEMENT_SHOW_ACCEPTS_BUTTON;
		else
			elementAccept = ELEMENT_INVITATION_GADGET_USER_ACCEPT_41;
		mouseOver(elementAccept.replace("${peopleName}", peopleName),true);
		WebElement element = waitForAndGetElement(elementAccept.replace("${peopleName}", peopleName), DEFAULT_TIMEOUT,1,2);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		//click(SHOW_ACCETPS_BUTTON.replace("${peopleName}", peopleName));
		waitForElementNotPresent(elementAccept.replace("${peopleName}", peopleName));
		//waitForAndGetElement(REMOVE_INVITATION_BUTTON.replace("${peopleName}", peopleName));
	}

	/**
	 * Remove an invitation
	 * @param peopleName
	 */
	public void removeInvitationGadget(String peopleName){
		info("-- Remove an invitation --");
		String eRemove = "";
		if(this.plfVersion.equalsIgnoreCase("4.0"))
			eRemove = ELEMENT_REMOVE_INVITATION_BUTTON;
		else
			eRemove = ELEMENT_REMOVE_INVITATION_BUTTON_41;
		//mouseOver(REMOVE_INVITATION_BUTTON.replace("${peopleName}", peopleName),true);
		WebElement element = waitForAndGetElement(eRemove.replace("${peopleName}", peopleName), DEFAULT_TIMEOUT,1,2);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		//click(REMOVE_INVITATION_BUTTON.replace("${peopleName}", peopleName));
		waitForElementNotPresent(eRemove.replace("${peopleName}", peopleName));
	}


	/**
	 * Connect user Suggestions
	 * @param peopleName
	 */
	public void connectUserSuggestionsGadget(String peopleName) {
		info("-- Connect Suggestions user --");
		mouseOver(ELEMENT_CONNECT_USER_SUGGESTIONS.replace("${peopleName}", peopleName),true);
		WebElement element = waitForAndGetElement(ELEMENT_CONNECT_USER_SUGGESTIONS.replace("${peopleName}", peopleName), DEFAULT_TIMEOUT,1,2);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		waitForElementNotPresent(ELEMENT_CONNECT_USER_SUGGESTIONS.replace("${peopleName}", peopleName));

	}
	/**
	 * Remove user suggestions
	 * @param peopleName
	 */
	public void removeUserSuggestionsGadget(String peopleName){
		info("-- Remove an invitation --");
		mouseOver(ELEMENT_REMOVE_USER_SUGGESTIONS.replace("${peopleName}", peopleName),true);
		WebElement element = waitForAndGetElement(ELEMENT_REMOVE_USER_SUGGESTIONS.replace("${peopleName}", peopleName), DEFAULT_TIMEOUT,1,2);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		waitForElementNotPresent(ELEMENT_REMOVE_USER_SUGGESTIONS.replace("${peopleName}", peopleName));
		Utils.pause(5000);
	}

	/**
	 * Connect space Suggestions
	 * @param peopleName
	 */
	public void connectSpaceSuggestionsGadget(String spaceName) {
		info("-- Connect Suggestions user --"); 
//		mouseOver(ELEMENT_VERIFY_SPACE_SUGGESTIONS.replace("${spaceName}", spaceName),true);
		WebElement element = waitForAndGetElement(ELEMENT_REQUEST_SPACE_SUGGESTIONS.replace("${spaceName}", spaceName), DEFAULT_TIMEOUT,1,2);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		waitForElementNotPresent(ELEMENT_REQUEST_SPACE_SUGGESTIONS.replace("${spaceName}", spaceName));

	}
	/**
	 * Remove space suggestions
	 * @param peopleName
	 */
	public void removeSpaceSuggestionsGadget(String spaceName){
		info("-- Remove an invitation --");
		mouseOver(ELEMENT_VERIFY_SPACE_SUGGESTIONS.replace("${Name}", spaceName),true);
		WebElement element = waitForAndGetElement(ELEMENT_REMOVE_SPACE_SUGGESTIONS.replace("${spaceName}", spaceName), DEFAULT_TIMEOUT,1,2);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		waitForElementNotPresent(ELEMENT_REMOVE_SPACE_SUGGESTIONS.replace("${spaceName}", spaceName));
	}

	/**
	 * Connect to another account from Who's Online gadget
	 * @author havtt
	 * @date 06-Nov-2013
	 * 
	 * @param username
	 */
	public void connectPeoplefromWhoisOnlineGadget(String username){
		info("--Connecting from Who's Online gadget--");
		mouseOver(ELEMENT_ONLINE_USER_AVATAR.replace("${acc}",username),true);
		WebElement element = waitForAndGetElement(ELEMENT_WHOISONLINE_CONNECT_BUTTON_INVITE.replace("${acc}",username), DEFAULT_TIMEOUT,1,2);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		Utils.pause(3000);
	}

	/**
	 * Access to another account's activity stream from Who's Online gadget
	 * @author havtt
	 * @date 06-Nov-2013
	 * 
	 * @param username
	 */
	public void accessASfromWhoisOnlineGadget(String username){
		info("--Accessing AS of others from Who's Online gadget--");
		//mouseOver(ELEMENT_ONLINE_USER_AVATAR.replace("${acc}",username),true);
		//click(ELEMENT_ONLINE_USER_AVATAR.replace("${acc}",username));
		WebElement element = waitForAndGetElement(By.xpath(ELEMENT_ONLINE_USER_AVATAR.replace("${acc}",username)));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
	}

	/**
	 * Check info of user displayed on Who's online Gadget
	 * 
	 * @param userName 
	 * 				name of user who is online
	 * @param activity
	 * 				value is "true" if online user has his activity posted on AS
	 * @param activityPosted
	 * 				activity content posted on AS of online user
	 * @param connected
	 * 				connection status of online user with the logged-in user. 
	 * 				value is "true" if online user and logged-in user are connected already.
	 */
	public void checkUserInfoOnWhoisOnlineGadget(String userName, boolean activity, String activityPosted, boolean connected, boolean invited){
		waitForAndGetElement(ELEMENT_WHOISONLINE_GADGET);
		mouseOver(ELEMENT_ONLINE_USER_AVATAR.replace("${acc}",userName),true);
		info("Confirm user avatar");
		waitForAndGetElement(ELEMENT_ONLINE_USER_ACC_IMG.replace("${acc}",userName), DEFAULT_TIMEOUT,1,2);
		info("Confirm user name");
		waitForAndGetElement(ELEMENT_ONLINE_USER_TITLE.replace("${acc}",userName), DEFAULT_TIMEOUT,1,2);
	}	

	/**
	 * Accept a space invitation 
	 * @author chinhdtt
	 * @date 07 Feb 2014
	 * @param spaceName
	 */
	public void acceptSpaceInvitationGadget(String spaceName) {
		info("-- Accept a space invitation --");
		mouseOver(ELEMENT_SHOW_CONNECTIONS_REQUEST_SPACE.replace("${namespace}", spaceName), true);
		waitForAndGetElement(ELEMENT_SPACE_ACCEPT_BUTTON.replace("${namespace}", spaceName));
		waitForAndGetElement(ELEMENT_SPACE_REMOVE_BUTTON.replace("${namespace}", spaceName));
		Utils.pause(1000);
		click(ELEMENT_SPACE_ACCEPT_BUTTON.replace("${namespace}", spaceName));
		waitForElementNotPresent(ELEMENT_SHOW_CONNECTIONS_REQUEST_SPACE.replace("${namespace}", spaceName));		
	} 

	/**
	 * Remove a space invitation 
	 * @author chinhdtt
	 * @date 07 Feb 2014
	 * @param spaceName
	 */
	public void removeSpaceInvitationGadget(String spaceName) {
		info("-- Remove a space invitation --");
		mouseOver(ELEMENT_SHOW_CONNECTIONS_REQUEST_SPACE.replace("${namespace}", spaceName), true);
		waitForAndGetElement(ELEMENT_SPACE_ACCEPT_BUTTON.replace("${namespace}", spaceName));
		waitForAndGetElement(ELEMENT_SPACE_REMOVE_BUTTON.replace("${namespace}", spaceName));
		Utils.pause(1000);
		click(ELEMENT_SPACE_REMOVE_BUTTON.replace("${namespace}", spaceName));
		waitForElementNotPresent(ELEMENT_SHOW_CONNECTIONS_REQUEST_SPACE.replace("${namespace}", spaceName));		
	} 


	/**
	 * Check status truncated of user on WhoIsOnline gadget
	 * 
	 * @param username
	 * 				Name of user who is online on the gadget
	 */
	public void checkTruncatedStatusOnWhoIsOnlineGadget(String username) {
		mouseOver(ELEMENT_ONLINE_USER_AVATAR.replace("${acc}",username),true);
		waitForAndGetElement(ELEMENT_ONLINE_USER_STATUS_TRUNCATED);
	}
	
	/**
	 * and new bookmark list gadget
	 * @param name
	 * @param url
	 * @opParams: isAdd (true: click button "Add", fale: click button "Cancel")
	 */
	public void addNewBookmarkListGadget(String name, String url, Object...opParams){
		Boolean isAdd = (Boolean) (opParams.length > 0 ? opParams[0]: true);
		click(ELEMENT_BOOKMARKS_GADGET_ADDBOOKMARK_ICON);
		if(name!="")
			type(ELEMENT_BOOKMARKS_GADGET_ADDNAME,name, true);
		if(url!="")
			type(ELEMENT_BOOKMARKS_GADGET_ADDURL,url, true);
		if(isAdd)
			button.add();
		else
			button.cancel();
	}
	
	/**
	 * Delete bookmark item
	 * @param bookmarName
	 */
	public void deleteBookmarkListGadget(String bookmarkName){
		mouseOver(By.linkText(bookmarkName),true);
		waitForAndGetElement(ELEMENT_BOOKMARKS_GADGET_DELETE_ICON.replace("${bookmarkName}", bookmarkName)).click();
		waitForElementNotPresent(By.linkText(bookmarkName));
	}
}