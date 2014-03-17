package org.exoplatform.selenium.platform.plf.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.GroupNavigation;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 04/11/2013
 *
 */
public class PLF_Navigation_LeftNavigation extends GroupNavigation {
	//Platform
	ManageAccount magAcc;
	SpaceManagement spaceMag;
	NavigationToolbar navToolbar;
	PageManagement pageMag;
	NavigationManagement navMag;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver, this.plfVersion);
		spaceMag = new SpaceManagement(driver, this.plfVersion);
		navToolbar = new NavigationToolbar(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		pageMag = new PageManagement(driver, this.plfVersion);
		navMag = new NavigationManagement(driver, this.plfVersion);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);

	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Show applications in "COMPANY" list ==
	 * Test case ID: 74371
	 * Step 1: - Connect to Intranet
	 */
	@Test
	public void test01_ShowApplicationsInCompanyList(){
		/*Step 1: - Connect to Intranet*/ 
		//- Login as a user
		//- Connect to Intranet
		//- The left Navigation is displayed
		//- The "COMPANY" list is displayed with applications in the following order:
		//* Home
		//* Connections
		//* Wiki
		//* Documents
		//* Forums
		//* Calendar
		//* Other personal pages
		info("-- Verify order of company navigation --");
		if(this.plfVersion.contains("4.1")){

			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "2").replace("${menuItem}", "/portal/intranet/connexions"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "3").replace("${menuItem}", "/portal/intranet/wiki"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "4").replace("${menuItem}", "/portal/intranet/documents"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "5").replace("${menuItem}", "/portal/intranet/forum"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "6").replace("${menuItem}", "/portal/intranet/calendar"));
		}
		else{// if (plfVersion =="4.0"){

			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "2").replace("${menuItem}", "Connections"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "3").replace("${menuItem}", "Wiki"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "4").replace("${menuItem}", "Documents"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "5").replace("${menuItem}", "Forums"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "6").replace("${menuItem}", "Calendar"));
		}
	}
	/**
	 * == Display Subnavigations in Group Navigation panel ==
	 * Test case ID: 74372
	 * Step 1: Add a node to group navigation
	 * Step 2: Add sub-node to the group navigation
	 */
	@Test
	public void test02_DisplaySubnavigationsInGroupNavigationPanel(){
		/*Declare variables*/
		String groupAdminDisplayName = "Administration";
		String nodePortalAdministration = "";
		String nodeName = "nodeTest74372";
		String subNodeName = "subNodeTest74372";
		String pageSelectorName = "test74372pageSelector";
		String pageSelectorName1 = "subtest74372pageSelector";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		/*Step 1: Add a node to group navigation*/ 
		//- Login as amdin
		//- Connect to Intranet
		//- From Administration/Portal/Group Sites",
		navToolbar.goToGroupSites();

		//- Click on Edit navigation of one group
		//- Right Click on a node, Select "add node", input valid data, select page for node and click [Save]		
		//- In the Left Navigation, the Group Navigation is displayed before "MY SPACES" panel
		//- the node is displayed in the panel
		addNodeForGroup(groupAdminDisplayName, nodePortalAdministration, true, 
				nodeName, true, languages, nodeName, 
				pageSelectorName, pageSelectorName, true, false);
		button.save();	
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		waitForAndGetElement(ELEMENT_NAVIGATION_NODE.replace("${nodeName}", nodeName));

		/*Step 2: Add sub-node to the group navigation*/
		//- Add sub-node of the above node by the same way as above
		//- In the Group Navigation panel, a small button is displayed to fold/unfold sub nodes
		addNodeForGroup(groupAdminDisplayName, nodeName, false, 
				subNodeName, true, languages, subNodeName, 
				pageSelectorName1, pageSelectorName1, true, false);
		button.save();	
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		if(this.plfVersion.contains("4.1")){
			click(ELEMENT_GROUP_NAVIGATION_ICON_LEFT_PANEL_PLF41.replace("${groupName}", nodeName));
		}
		else{// if (plfVersion =="4.0"){
			click(ELEMENT_GROUP_NAVIGATION_ICON_LEFT_PANEL_PLF41.replace("${groupName}", nodeName));
		}
		waitForAndGetElement(ELEMENT_NAVIGATION_NODE.replace("${nodeName}", subNodeName));

		/*Clear data*/
		info("-- Clear data --");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, pageSelectorName1);
		pageMag.deletePage(PageType.GROUP, pageSelectorName);
		navToolbar.goToGroupSites();
		navMag.deleteNode(groupAdminDisplayName, "", nodeName, true);
	}

	/**
	 * == Open a Space ==
	 * Test case ID: 74373
	 * Step 1: Connect to intranet
	 * Step 2: Open a space
	 */
	@Test
	public void test03_OpenASpace(){
		/*Declare variables*/
		String spaceName = "space74373";

		/*Create data*/
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spaceName, "");

		/*Step 1: Connect to intranet*/ 
		//- Login as a normal user
		//- Connect to Intranet
		//- The left Navigation is displayed
		info("-- Verify order of company navigation --");
		if(this.plfVersion.contains("4.1")){

			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "2").replace("${menuItem}", "/portal/intranet/connexions"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "3").replace("${menuItem}", "/portal/intranet/wiki"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "4").replace("${menuItem}", "/portal/intranet/documents"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "5").replace("${menuItem}", "/portal/intranet/forum"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "6").replace("${menuItem}", "/portal/intranet/calendar"));
		}
		else{// if (plfVersion =="4.0"){

			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "2").replace("${menuItem}", "Connections"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "3").replace("${menuItem}", "Wiki"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "4").replace("${menuItem}", "Documents"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "5").replace("${menuItem}", "Forums"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "6").replace("${menuItem}", "Calendar"));
		}
		//- Open an application from "COMPANY"
		//- The application is opened
		navToolbar.goToPersonalDocuments();

		/*Step 2: Open a space*/
		//- Open a space from "MY SPACES"
		//The space is opened in the Home space's stream
		spaceMag.goToSpaceFromMySpaceNavigation(spaceName);

		/*Clear data*/
		info("-- Clear data --");
		spaceMag.goToAllSpaces();
		spaceMag.deleteSpace(spaceName,300000);
	}

	/**
	 * == Display list of spaces ordered by the last browsed ==
	 * Test case ID: 74375
	 * Step 1: Connect to intranet
	 * Step 2: Open a space
	 * Step 3: Show last browsed spaces on the top
	 */
	@Test
	public void test04_DisplayListOfSpacesOrderedByTheLastBrowsed(){
		/*Declare variables*/
		String spaceName1 = "space743751";
		String spaceName2 = "space743752";

		/*Create data*/
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spaceName1, "");
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spaceName2, "");

		/*Step 1: Connect to intranet*/ 
		//- Login as john
		//- Connect to Intranet
		//- The Left Navigation is displayed
		//- The "MY SPACES" panel is displayed
		info("-- Verify order of company navigation --");
		if(this.plfVersion.contains("4.1")){

			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "2").replace("${menuItem}", "/portal/intranet/connexions"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "3").replace("${menuItem}", "/portal/intranet/wiki"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "4").replace("${menuItem}", "/portal/intranet/documents"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "5").replace("${menuItem}", "/portal/intranet/forum"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "6").replace("${menuItem}", "/portal/intranet/calendar"));
		}
		else{// if (plfVersion =="4.0"){

			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "2").replace("${menuItem}", "Connections"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "3").replace("${menuItem}", "Wiki"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "4").replace("${menuItem}", "Documents"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "5").replace("${menuItem}", "Forums"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "6").replace("${menuItem}", "Calendar"));
		}
		waitForAndGetElement(ELEMENT_SPACE_NAVIGATION);
		if(this.plfVersion.contains("4.1")){

			waitForAndGetElement(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM_INDEX_PLF41.replace("${index}", "1").replace("${spaceName}", spaceName2));
			waitForAndGetElement(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM_INDEX_PLF41.replace("${index}", "2").replace("${spaceName}", spaceName1));
		}
		else{
			waitForAndGetElement(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM_INDEX.replace("${index}", "1").replace("${spaceName}", spaceName2));
			waitForAndGetElement(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM_INDEX.replace("${index}", "2").replace("${spaceName}", spaceName1));
		}

		/*Step 2: Open a space*/
		//- Open a space from the list, not the first one
		//- The space is opened
		spaceMag.goToSpaceFromMySpaceNavigation(spaceName1);

		/*Step 3: Show last browsed spaces on the top*/
		//- Back to the homepage
		navToolbar.goToHomePage();

		//- The page is refreshed
		//- In "MY SPACES" The last browsed space jump to the top of the list


		if(this.plfVersion.contains("4.1")){

			waitForAndGetElement(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM_INDEX_PLF41.replace("${index}", "1").replace("${spaceName}", spaceName1));
			waitForAndGetElement(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM_INDEX_PLF41.replace("${index}", "2").replace("${spaceName}", spaceName2));
		}
		else{
			waitForAndGetElement(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM_INDEX.replace("${index}", "1").replace("${spaceName}", spaceName1));
			waitForAndGetElement(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM_INDEX.replace("${index}", "2").replace("${spaceName}", spaceName2));
		}
		/*Clear data*/
		info("-- Clear data --");
		spaceMag.goToAllSpaces();
		spaceMag.deleteSpace(spaceName1,300000);
		spaceMag.deleteSpace(spaceName2,300000);
	}

	/**
	 * == Search space in "MY SPACES" ==
	 * Test case ID: 74376
	 * Step 1: Connect to intranet
	 * Step 2: Search by inputting one letter
	 * Step 3: Search by inputting two letters
	 */
	@Test
	public void test05_SearchSpaceInMySpaces(){
		/*Declare variables*/
		String spaceName1 = "aspace743761";
		String spaceName2 = "abspace743762";
		String searchText1 = "a";
		String searchText2 = "ab";

		/*Create data*/
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spaceName1, "");
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spaceName2, "");

		/*Step 1: Connect to intranet*/ 
		//- Login as john
		//- Connect to Intranet
		//- The Left Navigation is displayed
		//- The "MY SPACES" panel is displayed
		//- Search filter is displayed
		info("-- Verify order of company navigation --");
		if(this.plfVersion.contains("4.1")){

			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "2").replace("${menuItem}", "/portal/intranet/connexions"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "3").replace("${menuItem}", "/portal/intranet/wiki"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "4").replace("${menuItem}", "/portal/intranet/documents"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "5").replace("${menuItem}", "/portal/intranet/forum"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "6").replace("${menuItem}", "/portal/intranet/calendar"));
		}
		else{// if (plfVersion =="4.0"){

			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "2").replace("${menuItem}", "Connections"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "3").replace("${menuItem}", "Wiki"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "4").replace("${menuItem}", "Documents"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "5").replace("${menuItem}", "Forums"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "6").replace("${menuItem}", "Calendar"));
		}
		waitForAndGetElement(ELEMENT_SPACE_NAVIGATION);
		waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_SEARCH_SPACE);

		/*Step 2: Search by inputting one letter*/
		//- Input a letter "a" in the search box under My spaces label
		type(ELEMENT_LEFT_NAVIGATION_SEARCH_SPACE,searchText1,true);

		//- All spaces having a word containing with the inputed letter are displayed
		waitForAndGetElement(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM.replace("${spaceName}", spaceName1));
		waitForAndGetElement(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM.replace("${spaceName}", spaceName2));

		/*Step 3: Search by inputting two letters*/
		//- Input a second letter "b"
		type(ELEMENT_LEFT_NAVIGATION_SEARCH_SPACE,searchText2,true);
		//- Only spaces containing "ab" are displayed
		waitForElementNotPresent(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM.replace("${spaceName}", spaceName1));
		waitForAndGetElement(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM.replace("${spaceName}", spaceName2));

		/*Clear data*/
		info("-- Clear data --");
		spaceMag.goToAllSpaces();
		spaceMag.deleteSpace(spaceName1,300000);
		spaceMag.deleteSpace(spaceName2,300000);
	}

	/**
	 * == Display "Left Navigation" for Social Intranet ==
	 * Test case ID: 74718
	 * Step 1: Check Left navigation
	 * Step 2: Check Left navigation on other portal
	 */
	@Test
	public void test06_DisplayLeftNavigationForSocialIntranet(){
		/*Step 1: Check Left navigation*/ 
		//- Login as a user
		//- Connect to Intranet
		//- The Left Navigation is displayed: Company List" and "My Spaces" panels
		info("-- Verify order of company navigation --");
		if(this.plfVersion.contains("4.1")){

			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "2").replace("${menuItem}", "/portal/intranet/connexions"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "3").replace("${menuItem}", "/portal/intranet/wiki"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "4").replace("${menuItem}", "/portal/intranet/documents"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "5").replace("${menuItem}", "/portal/intranet/forum"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "6").replace("${menuItem}", "/portal/intranet/calendar"));
		}
		else{// if (plfVersion =="4.0"){

			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "2").replace("${menuItem}", "Connections"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "3").replace("${menuItem}", "Wiki"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "4").replace("${menuItem}", "Documents"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "5").replace("${menuItem}", "Forums"));
			waitForAndGetElement(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "6").replace("${menuItem}", "Calendar"));
		}
		waitForAndGetElement(ELEMENT_SPACE_NAVIGATION);

		/*Step 2: Check Left navigation on other portal*/
		//- Connect to other sites. Go to via url: host:port/portal/acme/.
		String url = baseUrl+"/acme/";
		driver.get(url);

		//- The Left Navigation isn't displayed
		info("-- Verify order of company navigation --");
		if(this.plfVersion.contains("4.1")){

			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "2").replace("${menuItem}", "/portal/intranet/connexions"));
			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "3").replace("${menuItem}", "/portal/intranet/wiki"));
			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "4").replace("${menuItem}", "/portal/intranet/documents"));
			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "5").replace("${menuItem}", "/portal/intranet/forum"));
			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX_PLF41.replace("${index}", "6").replace("${menuItem}", "/portal/intranet/calendar"));
		}
		else{// if (plfVersion =="4.0"){

			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "1").replace("${menuItem}", "/portal/intranet/home"));
			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "2").replace("${menuItem}", "Connections"));
			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "3").replace("${menuItem}", "Wiki"));
			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "4").replace("${menuItem}", "Documents"));
			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "5").replace("${menuItem}", "Forums"));
			waitForElementNotPresent(ELEMENT_LEFT_NAVIGATION_ITEM_INDEX.replace("${index}", "6").replace("${menuItem}", "Calendar"));
		}
		waitForElementNotPresent(ELEMENT_SPACE_NAVIGATION);

	}

}
