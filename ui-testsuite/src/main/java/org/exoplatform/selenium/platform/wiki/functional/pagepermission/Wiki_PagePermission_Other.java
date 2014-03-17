package org.exoplatform.selenium.platform.wiki.functional.pagepermission;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 18/12/2012
 * @author lientm
 *
 */
public class Wiki_PagePermission_Other extends BasicAction{

	ManageAccount magAc;
	ManageMember magMember;

	public String DATA_USER_ADMIN = "john";
	public String DATA_PASS_ADMIN = DATA_PASS;

	@BeforeMethod
	public void beforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magMember = new ManageMember(driver);
		magAc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);	
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		//signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/*public void checkNotHaveEditPermission(String spaceName, String user, By element_page, String content){
		info("Check user can view page but does not have edit page");
		magAc.signIn(user, DATA_PASS_ADMIN);
		goToWikiFromSpace(spaceName);
		click(element_page);
		waitForTextPresent(content);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		magAc.signOut();
	}*/

	public void userJoinSpaceAndCheckPagePermission(String user, String spaceName, By element_space, By element_page, String content){
		magAc.signIn(user, DATA_PASS_ADMIN);
		magMember.joinOpenSpace(spaceName);
		click(element_space);
		click(ELEMENT_WIKI_LINK_IN_SPACE);

		info("User can view page but can not edit page");
		click(element_page);
		waitForTextPresent(content);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		magAc.signOut();
	}

	public void setPagePermission(String[] userGroup, int option){
		info("Add user has permission default");
		if (isTextPresent("any")){
			deletePagePermission("any");
		}
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		deletePagePermission("*:/spaces");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		Utils.pause(2000);
		deletePagePermission("manager:/spaces");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		Utils.pause(2000);
		addPagePermission(option, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
	}

	/*case01: Check when user/group does not permission to view page
	 */
	@Test
	public void test01_CheckUserNotHaveViewPagePermission_UserPermission(){
		String title = "KS_Wiki_PagePermission_Other_Page_01_1";
		String content = "KS_Wiki_PagePermission_Other_Content_01_1";
		By element_page = By.linkText(title);
		String user = "mary";
		String[] userGroup = {user};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(1, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAc.signOut();

		//check user james does not have view permission
		checkViewPage(ManageAccount.userType.AUTHOR, element_page);

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	@Test
	public void test01_CheckUserNotHaveViewPagePermission_GroupPermission(){
		String title = "KS_Wiki_PagePermission_Other_Page_01_2";
		String content = "KS_Wiki_PagePermission_Other_Content_01_2";
		By element_page = By.linkText(title);
		String[] userGroup = {"Platform/Administration"};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(2, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAc.signOut();

		//check user demo does not have view permission because it does not belong to group
		checkViewPage(ManageAccount.userType.AUTHOR, element_page);

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	@Test
	public void test01_CheckUserNotHaveViewPagePermission_MembershipPermission(){
		String title = "KS_Wiki_PagePermission_Other_Page_01_3";
		String content = "KS_Wiki_PagePermission_Other_Content_01_3";
		By element_page = By.linkText(title);
		String[] userGroup = {"Platform/Administration", "*"};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(3, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAc.signOut();

		//check user mary does not have view permission because it has membership =  publisher/editor
		checkViewPage(ManageAccount.userType.PUBLISHER, element_page);

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/*case02: Check when user/group does not permission to edit page
	 */
	@Test
	public void test02_CheckUserNotHaveEditPage_UserPermission(){
		String title = "KS_Wiki_PagePermission_Other_Page_02_1";
		String content = "KS_Wiki_PagePermission_Other_Content_02_1";
		By element_page = By.linkText(title);
		String user = "james";
		String[] userGroup = {user};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(1, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAc.signOut();

		//check user demo have view page but does not have edit page
		checkEditPage(ManageAccount.userType.AUTHOR, element_page, content);

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	@Test
	public void test02_CheckUserNotHaveEditPage_GroupPermission(){
		String title = "KS_Wiki_PagePermission_Other_Page_02_2";
		String content = "KS_Wiki_PagePermission_Other_Content_02_2";
		By element_page = By.linkText(title);
		String[] userGroup = {"Platform/Content Management"};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add group has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(2, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAc.signOut();

		//check user james belong to group which have view page but does not have edit page
		checkEditPage(ManageAccount.userType.AUTHOR, element_page, content);

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	@Test
	public void test02_CheckUserNotHaveEditPage_MembershipPermission(){
		String title = "KS_Wiki_PagePermission_Other_Page_02_3";
		String content = "KS_Wiki_PagePermission_Other_Content_02_3";
		By element_page = By.linkText(title);
		String[] userGroup = {"Platform/Content Management", "redactor"};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add membership has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(3, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAc.signOut();

		//check user james (belong to membership) which have view page but does not have edit page
		checkEditPage(ManageAccount.userType.AUTHOR, element_page, content);

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/*case03: Check when user/group does not permission to edit page (add space)
	 * add new space
	 * add page
	 * add view page permission for user/group/membership
	 */
	@Test
	public void test03_CheckUserNotHaveEditPagePermission_AddSpace(){
		String spaceName = "pagespace031";
		String spaceDescription = "KS_Wiki_PagePermission_Other_space_description_03_1";
		By element_space = By.linkText(spaceName);
		String title = "KS_Wiki_PagePermission_Other_Page_03_1";
		String content = "KS_Wiki_PagePermission_Other_Content_03_1";
		By element_page = By.linkText(title);
		String user = "james";
		String[] userGroup = {user};

		//add new space visible and open
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, spaceDescription, "Visible", "Open", "", "");

		info("Add a wiki page");
		goToWikiFromSpace(spaceName);
		addBlankWikiPage(title, content, 0);
		click(element_page);
		setPagePermission(userGroup, 1);
		magAc.signOut();

		info("user joins space -> check page permission");
		userJoinSpaceAndCheckPagePermission(user, spaceName, element_space, element_page, content);

		//delete space
		magAc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);
		magMember.restoreData(spaceName, 100000);
	}

	@Test
	public void test03_CheckGroupNotHaveEditPagePermission_AddSpace(){
		String spaceName = "pagespace032";
		String spaceDescription = "KS_Wiki_PagePermission_Other_space_description_03_2";
		By element_space = By.linkText(spaceName);
		String title = "KS_Wiki_PagePermission_Other_Page_03_2";
		String content = "KS_Wiki_PagePermission_Other_Content_03_2";
		By element_page = By.linkText(title);
		String[] userGroup = {"Platform/Content Management"};

		//add new space visible and open
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, spaceDescription, "Visible", "Open", "", "");

		info("Add a wiki page");
		goToWikiFromSpace(spaceName);
		addBlankWikiPage(title, content, 0);
		click(element_page);
		setPagePermission(userGroup, 2);
		magAc.signOut();

		info("user james belongs to group that joins space -> check page permission");
		userJoinSpaceAndCheckPagePermission("james", spaceName, element_space, element_page, content);

		//delete space
		magAc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);
		magMember.restoreData(spaceName, 100000);
	}

	@Test
	public void test03_CheckMembershipNotHaveEditPagePermission_AddSpace(){
		String spaceName = "pagespace033";
		String spaceDescription = "KS_Wiki_PagePermission_Other_space_description_03_3";
		By element_space = By.linkText(spaceName);
		String title = "KS_Wiki_PagePermission_Other_Page_03_3";
		String content = "KS_Wiki_PagePermission_Other_Content_03_3";
		By element_page = By.linkText(title);
		String[] userGroup = {"Platform/Content Management", "redactor"};

		//add new space visible and open
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, spaceDescription, "Visible", "Open", "", "");

		info("Add a wiki page");
		goToWikiFromSpace(spaceName);
		addBlankWikiPage(title, content, 0);
		click(element_page);
		setPagePermission(userGroup, 3);
		magAc.signOut();

		info("user james belongs to membership that joins space -> check page permission");
		userJoinSpaceAndCheckPagePermission("james", spaceName, element_space, element_page, content);

		//delete space
		magAc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);
		magMember.restoreData(spaceName, 100000);
	}
}