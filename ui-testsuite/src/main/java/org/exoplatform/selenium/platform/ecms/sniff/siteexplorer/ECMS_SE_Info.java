package org.exoplatform.selenium.platform.ecms.sniff.siteexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 23/05/2013
 * @author thuntn
 *
 */
public class ECMS_SE_Info extends PlatformBase {
	NavigationToolbar navToolBar;
	ActionBar actBar;
	ContentTemplate cTemplate;
	EcmsBase ecms;
	ContextMenu cMenu;
	ManageAccount magAcc;
	SitesExplorer siteExp;
	EcmsPermission ePerm;
	Button btn;

	/**CaseID: 65858 Add Permission
	 * Step 1: Add Permission
	 */
	@Test
	public void test01_AddPermission() {
		String node1= "test01AddPermission1";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}",node1));
		String user = DATA_USER2;

		info("Add permisison for a node");

		//Create node1, node2
		cTemplate.createNewFolder(node1, folderType.Content);
		actBar.goToNode(bNode1);

		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add permission "read", "write" for mary
		actBar.goToNodePermissionManagement();
		ePerm.removeDefaultPermissionOfNode();
		ePerm.selectUser(user);
		ePerm.setPermissionForNode(true, true, false);
		btn.save();
		btn.close();

		magAcc.signOut();
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//Check if mary has edit, read on node1
		actBar.goToNode(bNode1);
		waitForAndGetElement(actBar.ELEMENT_NEW_CONTENT_LINK);

		//Delete data
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode1);
	}

	/**CaseID: 67853 Edit Permission
	 * Step 1: Edit Permission
	 */
	@Test
	public void test02_EditPermission() {
		String node1= "test01EditPermission1";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}",node1));
		String user = "*:/platform/web-contributors";
		WebElement eRemoveCheck ;
		WebElement eModifyCheck ;

		info("Edit permisison for a node");

		//Create node1, node2
		cTemplate.createNewFolder(node1, folderType.Content);
		actBar.goToNode(bNode1);

		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add permission "remove", "write" for *:/platform/web-contributors
		actBar.goToNodePermissionManagement();
		eRemoveCheck = waitForAndGetElement(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}",user),DEFAULT_TIMEOUT,1,2);
		eModifyCheck = waitForAndGetElement(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}",user),DEFAULT_TIMEOUT,1,2);
		if (!eRemoveCheck.isSelected())
			click(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}",user),2);
		if (!eModifyCheck.isSelected())
			click(ePerm.ELEMENT_MODIFY_CHECK.replace("{$user}",user),2);
		btn.close();

		magAcc.signOut();
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//Check if mary has edit, read on node1
		actBar.goToNode(bNode1);
		waitForAndGetElement(actBar.ELEMENT_NEW_CONTENT_LINK);
		rightClickOnElement(bNode1);
		waitForAndGetElement(cMenu.ELEMENT_MENU_DELETE);

		//Delete data
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();

		cMenu.deleteDocument(bNode1);
	}
	/**CaseID: 67854 Add Permission
	 * Step 1: Add Permission
	 */
	@Test
	public void test03_DeletePermission() {
		String node1= "test03DeletePermission1";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}",node1));
		String user = DATA_USER2;

		info("Delete permisison for a node");

		//Create node1, node2
		cTemplate.createNewFolder(node1, folderType.Content);
		actBar.goToNode(bNode1);

		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add permission "read", "write" for mary
		actBar.goToNodePermissionManagement();
		ePerm.removeDefaultPermissionOfNode();
		ePerm.selectUser(user);
		ePerm.setPermissionForNode(true, true, false);
		btn.save();
		btn.close();

		magAcc.signOut();
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//Check if mary has edit, read on node1
		actBar.goToNode(bNode1);
		waitForAndGetElement(actBar.ELEMENT_NEW_CONTENT_LINK);

		//Delete permission
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		actBar.goToNode(bNode1);
		actBar.goToNodePermissionManagement();
		ePerm.deletePermission(DATA_USER2, true);

		//Check if mary can see this  node
		magAcc.signOut();
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();
		waitForElementNotPresent(bNode1);

		//Delete data
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode1);
	}

	/**CaseID: 65880 View metadata
	 * Step 1: View metadata
	 */
	@Test
	public void test04_ViewMetadata() {
		String name = "ECMS_DMS_SE_Upload_odsfile.ods";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}",name));
		String link = "TestData/" + name;


		info("View metadata for a node");

		//Create node1, node2
		ecms.uploadFile(link);
		cMenu.goToNode(bNode1);
		//Add View metadata icon to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewMetadatas", actBar.ELEMENT_VIEW_METADATA_ICON);

		//View metadata of node1
		cMenu.goToNode(bNode1);
		actBar.viewMetadata();

		cMenu.deleteDocument(bNode1);
	}

	@BeforeMethod
	public void beforeMethod() {
		initSeleniumTest();
		driver.close();
		getDriverAutoOpenWindow();
		driver.get(baseUrl);
		navToolBar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		ecms = new EcmsBase(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		ePerm = new EcmsPermission(driver);
		btn = new Button(driver);

	}
	@AfterMethod
	public void afterMethods() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}
