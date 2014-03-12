package org.exoplatform.selenium.platform.social;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;

/**
 *@author HangNTT
 * @date: 07/11/2012
 */
public class SocialBase extends PlatformBase {

	//SpaceManagement spaceMag = new SpaceManagement();

	public final By ELEMENT_JOIN_SPACE_LINK = By.xpath("//div[@class='uiSpaceNavigationPortlet']/div/a/i[contains(@class, 'uiIconPLFMan')]");
	//public final By ELEMENT_JOIN_SPACE_LINK = By.xpath("//div[@class='uiSpaceNavigationPortlet']/..//div/a[contains(text(),'Join a space')]");

	// Go to My space link
//	public final By ELEMENT_MY_SPACES_LINK = By.linkText("My Spaces");
	public final By ELEMENT_MY_SPACES_LINK  = By.xpath("//a[text()='My Spaces']");
	public final By ELEMENT_ALL_SPACE_LINK = By.linkText("All Spaces");
	public final By ELEMENT_INVATATION_RECEIVES_LINK = By.linkText("Invitations Received");
	public final By ELEMENT_REQUESTS_PENDING_LINK = By.linkText("Requests Pending");

	// Go to My Space > All Spaces Tab
	public final String ELEMENT_SEND_REQUEST_LINK = "//*[@id='UIManageAllSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Request to Join']";
	public final String ELEMENT_INVITATION_LEAVE_LINK_IN_ALL_SPACES = "//*[@id='UIManageAllSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Leave']";
	public final String ELEMENT_INVITATION_ACCEPT_LINK_IN_ALL_SPACES = "//*[@id='UIManageAllSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Accept']";
	public final String ELEMENT_INVITATION_IGNORE_LINK_IN_ALL_SPACES = "//*[@id='UIManageAllSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Ignore']";

	// Go to My Space > My Spaces Tab
	public final String ELEMENT_INVITATION_LEAVE_LINK = "//*[@id='UIManageMySpaces']//*[text()='${spaceName}']/../../ul//*[text()='Leave']";

	// Go to My Space > Invitation Receives Tab
	public final String ELEMENT_INVITATION_ACCEPT_LINK = "//*[@id='UIManageInvitationSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Accept']";
	public final String ELEMENT_INVITATION_IGNORE_LINK = "//*[@id='UIManageInvitationSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Ignore']";

	// Go to My Space > Requests Pending Tab
	public final String ELEMENT_CANCEL_LINK = "//*[@id='UIManagePendingSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Cancel']";

	// Go to My Space -> Search Button
	public final By ELEMENT_SEARCH_BUTTON = By.id("SearchButton");

	// Go to My Space -> Select a space > click setting icon
	public final By ELEMENT_SETTINGS = By.xpath("//*[@title='Settings']");

	// Go to My Space -> Select a space > click Navigation tab
	public final By ELEMENT_NAVIGATION_TAB = By.xpath("//a[text()='Navigations']");

	// Go to My Space -> Select a space > click Access & Edit tab
	public final By  ELEMENT_ACCESS_AND_EDIT_TAB = By.xpath("//div[text()='Access & Edit']");

	// Go to My Space -> Select a space > click Member tab
	public final By ELEMENT_MEMBERS_TAB = By.xpath("//div[@id='UISpaceSetting']//a[text()='Members']");
	public final By ELEMENT_MEMBERS_TAB_IN_SPACE_MENU = By.xpath("//*[@id='UISpaceMenu']//*[text()='Members']");

	// Go to My Space -> Select a space > click Application tab
	public final By ELEMENT_APPLICATIONS_TAB = By.linkText("Applications");

	// Go to Account Name link	
	//	public final By ELEMENT_MY_PROFILE_LINK = By.linkText("My Profile");
	//	public final By ELEMENT_FIND_CONNECTIONS_LINK = By.linkText("Find Connections");
	//	public final By ELEMENT_MY_CONNECTIONS_LINK = By.linkText("My Connections");

	//	public final By ELEMENT_TOOLBAR_PROFILE_ICON = By.xpath("//*[@id='UserNavigationTabsContainer']/ul/li[2]/ul/li[1]/a[@class='ToolBarProfileIcon']");
	//	public final By ELEMENT_TOOLBAR_NETWORKS_ICON = By.xpath("//*[@id='UserNavigationTabsContainer']/ul/li[2]/ul/li[1]/a[@class='ToolBarNetworkIcon']");
	//	public final By ELEMENT_FIND_PEOPLE_ICON = By.xpath("//*[@id='UserNavigationTabsContainer']/ul/li[2]/ul/li[2]/a[@class='FindPeopleIcon']");
	//	public final By ELEMENT_TOOLBAR_ACTIVITY_ICON = By.xpath("//*[@id='UserNavigationTabsContainer']/ul/li[2]/ul/li[2]/a[@class='ToolBarActivityIcon']");
	public final By ELEMENT_TITLEBAR_BOLD = By.xpath("//div[@class='TitleBar Bold']");

	///
	public final By ELEMENT_TOOLBAR_PROFILE_ICON = By.linkText("My Profile");
	public final By ELEMENT_TOOLBAR_NETWORKS_ICON = By.linkText("My Connections");
	public final By ELEMENT_FIND_PEOPLE_ICON = By.linkText("Find Connections");
	public final By ELEMENT_TOOLBAR_ACTIVITY_ICON = By.linkText("My Activity Stream");

	// Go to Account Name link > My Profile 
	//public final By ELEMENT_ACTIVITY_STREAM_LINK = By.linkText("Activity Stream");
	public final By ELEMENT_PROFILE_LINK = By.linkText("Profile");
	public final By ELEMENT_CONNECTIONS_LINK = By.linkText("Connections");
	public final By ELEMENT_ALL_UPDATES_TAB = By.linkText("All Updates");
	public final By ELEMENT_MY_SPACES_TAB = By.xpath("//a[@class='ActionButton LightBlueStyle' and text()='My Spaces']");
	public final By ELEMENT_MY_STATUS_TAB = By.linkText("My Status");
	public final By ELEMENT_CHANGE_PICTURE = By.linkText("Change Picture");
	public String ELEMENT_PROFILE_NAME_LINK = "//a[contains(@class,'CommunityName') and text()='${name}']";
	public String ELEMENT_PROFILE_FULLNAME = "//div[@id='UIProfile']//h2[contains(text(),'${name}')]";
	public String ELEMENT_ACTIVITYSTREAM_TITLE = "//div[contains(@id,'ActivityContextBox')]/h5/a[@title='${name}']";
	public String ELEMENT_ACTION_USER_ON_SPACE = "//a[text()='${spaceName}']/../../..//button[text()='${action}']";

	// Activity Stream tab	
	public final By ELEMENT_ACTIVITY_STREAM_TAB = By.xpath("//div[@id='UIProfileNavigationPortlet']//a[text()='Activity Stream']");

	/*------------------- End of parameters ---------------------*/	

	/**
	 * Common functions for Social
	 */
	// Go to My Space Page
	/**
	 * Migrate to PLF 4
	 * <li> Update by @author vuna2 </li>
	 */
	
	public void goToMySpacePage(){
		info("--Go to My Space Page--");
		//waitForElementPresent(ELEMENT_MY_SPACES_LINK);
		//click(ELEMENT_MY_SPACES_LINK);
		//		Utils.pause(500);
		//		click(ELEMENT_JOIN_SPACE_LINK);
		//		waitForTextPresent("Add New Space");
		//		goToMySpacePage();
		goToAllSpaces();
		waitForAndGetElement(ELEMENT_MY_SPACES_LINK);
		click(ELEMENT_MY_SPACES_LINK);
		Utils.pause(1000);
	}

	// Go to All Spaces
	public void goToAllSpaces(){
		info("Go to All Spaces");
		click(ELEMENT_JOIN_SPACE_LINK);
		if(waitForAndGetElement("//*[contains(text(),'Add New Space')]",DEFAULT_TIMEOUT,0)== null){
			clearCache();
			waitForAndGetElement("//*[contains(text(),'Add New Space')]");
		}
	}

	// Go to Invitation Receives
	public void goToInvitationReceives(){
		info("Go to Invitation Receives");
		goToMySpacePage();
		waitForAndGetElement(ELEMENT_INVATATION_RECEIVES_LINK);
		click(ELEMENT_INVATATION_RECEIVES_LINK);	
	}
	// Go to Request Pending
	public void goToRequestsPeding(){
		info("Go to Requests Pending");
		goToMySpacePage();
		waitForAndGetElement(ELEMENT_REQUESTS_PENDING_LINK);
		click(ELEMENT_REQUESTS_PENDING_LINK);	
	}

	// Go to Settings
	public void goToSettings(){
		click(ELEMENT_SETTINGS);
		waitForAndGetElement("//*[text()='Change Picture']");
	}

	// Go to Access and Edit tab
	public void goToAccessAndEdit(){
		click(ELEMENT_ACCESS_AND_EDIT_TAB);
		waitForAndGetElement("//*[text()='Visible']");
	}

	/**
	 * Go to Access and Edit tab
	 * @author vuna2
	 * @param spaceName: name of space (String)
	 */
	public void goToAccessAndEdit(String spaceName){
		goToMySpacePage();
		doAction("Edit", spaceName);
		goToAccessAndEdit();
	}

	// Go to Member tab
	public void goToMembers(){
		click(ELEMENT_MEMBERS_TAB);
		waitForAndGetElement(By.id("user"));
	}

	/**
	 * Go to Member tab of space
	 * @author vuna2
	 * @param spaceName: name of space (String)
	 */
	public void goToMembers(String spaceName){
		goToMySpacePage();
		doAction("Edit", spaceName);
		goToMembers();
	}

	// Go to Applications tab
	public void goToApplications(){
		info("-- Go to applications --");
		click(ELEMENT_APPLICATIONS_TAB);
		waitForAndGetElement("//*[text()='Add Application']");
	}

	/**
	 * Go to Applications tab
	 * @author vuna2
	 * @param spaceName: name of space (String)
	 */
	public void goToApplications(String spaceName){
		goToMySpacePage();
		doAction("Edit", spaceName);
		goToApplications();
	}

	// Go to Navigation tab
	public void goToNavigation(){
		waitForAndGetElement(ELEMENT_NAVIGATION_TAB);
		click(ELEMENT_NAVIGATION_TAB);
		waitForAndGetElement("//*[text()='Add Node']");
	}

	/**
	 * Go to Navigation tab
	 * @author vuna2
	 * @param spaceName: name of space (String)
	 */
	public void goToNavigation(String spaceName){
		goToMySpacePage();
		doAction("Edit", spaceName);
		goToNavigation();
	}

	// Access a space
	public void accessSpace(String spaceName) {
		goToMySpacePage();
		click(By.xpath("//*[@id='UIManageMySpaces']//*[text()= '"+ spaceName +"']"));
		waitForElementNotPresent("//*[contains(text(),'Add New Space')]");
	}

	// Go to Find Connections
	public void goToFindConnections(){
		info("--Go to Find Connections--");	
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
				info("--Error mouse over and click: can't mouseover, need to use mouse over and click --");
				break;
			}
			mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
			if (waitForAndGetElement(ELEMENT_FIND_PEOPLE_ICON, 5000, 0) != null){
				info("Element " + ELEMENT_FIND_PEOPLE_ICON + "... is displayed");
				break;
			}
			info("Retry...[" + repeat + "]");
		}
		click(ELEMENT_FIND_PEOPLE_ICON);
		waitForAndGetElement("//*[contains(text(),'Contacts Directory')]");
	}	

	// Go to My Connections
	public void goToMyConnections(){
		info("--Go to My Connections--");		
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
				info("--Error mouse over and click: can't mouseover, need to use mouse over and click --");
				break;
			}
			mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
			if (waitForAndGetElement(ELEMENT_TOOLBAR_NETWORKS_ICON, 5000, 0) != null){
				info("Element " + ELEMENT_TOOLBAR_NETWORKS_ICON + "... is displayed");
				break;
			}
			info("Retry...[" + repeat + "]");
		}
		click(ELEMENT_TOOLBAR_NETWORKS_ICON);
		waitForAndGetElement(ELEMENT_MY_CONNECTIONS_TAB);
	}	

	// Go to Activity stream
	public void goToActivityStream(){
		info("--Go to Activity Stream--");
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
				info("--Error mouse over and click: can't mouseover, need to use mouse over and click --");
				break;
			}
			mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
			if (waitForAndGetElement(ELEMENT_TOOLBAR_ACTIVITY_ICON, 5000, 0) != null){
				info("Element " + ELEMENT_TOOLBAR_ACTIVITY_ICON + "... is displayed");
				break;
			}
			info("Retry...[" + repeat + "]");
		}
		click(ELEMENT_TOOLBAR_ACTIVITY_ICON);
		waitForAndGetElement("//*[contains(text(),'What are you working on?')]");
	}	

	////////////
	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li>
	 * Do a action on space such as Edit, delete, ...
	 * 
	 * @param action : Action name 
	 * @param spaceName : Space name
	 */
	public void doAction(String action, String spaceName){
		By actionLink = By.xpath(ELEMENT_ACTION_USER_ON_SPACE.replace("${spaceName}", spaceName).replace("${action}", action));
		waitForAndGetElement(actionLink, DEFAULT_TIMEOUT,1);
		click(actionLink);
		Utils.pause(1000);
	}

}