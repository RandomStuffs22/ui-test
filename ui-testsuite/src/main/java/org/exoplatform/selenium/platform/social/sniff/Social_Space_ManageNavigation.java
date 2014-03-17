package org.exoplatform.selenium.platform.social.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SocialBase;
import org.exoplatform.selenium.platform.social.SpaceNavigation;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 07/10/2013
 *
 */
public class Social_Space_ManageNavigation extends SocialBase {
	//Platform
	ManageAccount magAcc;
	ManageMember magMember;

	//Space
	SpaceNavigation spaceNav;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		magMember = new ManageMember(driver);
		spaceNav = new SpaceNavigation(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Add new node ==
	 * Test case ID: 67673
	 * Step 1: Add new node
	 */
	@Test
	public void test01_AddNewNode(){
		//Declare variable
		String spaceName = "space67673";
		String nodeName = "nodeName67673";
		String nodeLabel = "nodeLabel67673";
		String pageName = "pageName67673";
		String pageTitle = "pageTitle67673";

		//Create data
		//Add new space
		info("Create data");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");

		/*Step 1: Add new node*/ 
		//- Access Space, select Setting tab/ Navigation tab
		goToNavigation(spaceName);

		//  + Click on Add node, enter Node name and other information and click on save
		//- Node is added and is showed on navigation of space.
		spaceNav.addNodeWhenSelectpage(spaceName,true,nodeName,false,"",nodeLabel,pageName,pageTitle,false);

		/*Clear data*/
		info("clear data");
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * == Edit a node ==
	 * Test case ID: 67897
	 * Step 1: Edit a node
	 */
	@Test
	public void test02_EditANode(){
		//Declare variable
		String spaceName = "space67689";
		String nodeName = "nodeName67689";
		String nodeLabel = "nodeLabel67689";
		String pageName = "pageName67689";
		String newNodeLabel = "newNodeLabel67689";
		String newPageTitle = "Register";

		/*Step 1: Edit a node*/ 
		//- Go to spaces page
		//- Add new space
		//- Access space and select Space settings portlet or Click on Space setting icon
		//- Select Space navigation tab
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		goToNavigation(spaceName);
		spaceNav.addNodeDoNotSelectPage(nodeName, nodeLabel, pageName);

		//Right click on node and select Edit this node
		//Change the value of node and click on save
		spaceNav.editNode(spaceName, nodeLabel, newNodeLabel, newPageTitle);

		//- Node/Page's node is update with all changed value
		/*Clear data*/
		info("clear data");
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName,300000);
	}
	
	/**
	 * == Delete a node ==
	 * Test case ID: 67898
	 * Step 1: Delete a node
	 */
	@Test
	public void test03_DeleteANode(){
		//Declare variable
		String spaceName = "space67898";
		String nodeName = "nodeName67898";
		String nodeLabel = "nodeLabel67898";

		/*Step 1: Delete a node*/ 
		//- Go to spaces page
		//- Add new space
		//- Access space and select Space settings portlet or Click on Space setting icon
		//- Select Space navigation tab
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		goToNavigation(spaceName);
		spaceNav.addNodeDoNotSelectPage(nodeName, nodeLabel);
		
		//- chose a node
		//- Right click on node and select Delete node
		//- Click OK to confirm
		//- Node is deleted and is not showed on space's navigation
		spaceNav.deleteNode(spaceName, nodeLabel);
		 
		/*Clear data*/
		info("clear data");
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName,300000);
	}
}
