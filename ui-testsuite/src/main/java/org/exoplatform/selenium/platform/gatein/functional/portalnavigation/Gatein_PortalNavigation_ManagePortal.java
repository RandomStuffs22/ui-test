package org.exoplatform.selenium.platform.gatein.functional.portalnavigation;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PortalManagement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author hangntt
 * @date 25-Nov-2013
 *
 */
public class Gatein_PortalNavigation_ManagePortal extends PortalManagement{

	//General
	Button button;

	//Platform
	ManageAccount magAc;
	NavigationToolbar navToolbar;
	PortalManagement portalMag;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		button = new Button(driver);
		magAc = new ManageAccount(driver);
		navToolbar = new NavigationToolbar(driver);
		portalMag = new PortalManagement();
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		driver.navigate().refresh();
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**CaseId: 73501,73502 -> Add and delete site
	 * 
	 */
	@Test
	public void test01_AddDeletePortal(){
		String portalName = "gateinfunctportal73501";

		info("Add new portal");
		Map<String, String> permissions = null;
		String editGroupId = "Platform /Content Management ";
		String editMembership = "*" ;
		navToolbar.goToPortalSites();
		addNewPortal(portalName, null, null, "French", null, "Always", true, permissions, editGroupId, editMembership);
		waitForAndGetElement(ELEMENT_PORTAL_EDIT_CONFIGURATION.replace("${siteName}", portalName));

		info("Switch to new portal");
		driver.get(DEFAULT_BASEURL+"/" + portalName);
		waitForAndGetElement(ELEMENT_PORTAL_NAME_SITE_ACCESS.replace("${portalName}", portalName));

		info("Delete portal");
		driver.get(baseUrl);
		navToolbar.goToPortalSites();
		deletePortal(portalName);
	}
	/**CaseId: 73793,73819 -> Add portal with name is upper/lower case and existing
	 * 
	 */
	@Test
	public void test02_AddPortalIsTheSammeWithExisting(){
		String portalName = "gateinfunctportal73793";
		String portalName1= portalName.toUpperCase();

		info("Add new portal");
		Map<String, String> permissions = null;
		String editGroupId = "Platform /Content Management ";
		String editMembership = "*" ;
		navToolbar.goToPortalSites();
		addNewPortal(portalName, null, null, "French", null, "Always", true, permissions, editGroupId, editMembership);
		waitForAndGetElement(ELEMENT_PORTAL_EDIT_CONFIGURATION.replace("${siteName}", portalName));

		info("Add new portal is the same with exitings but Upper case");
		Map<String, String> permissions2 = null;
		String editGroupId2 = "Platform /Content Management ";
		String editMembership2 = "manager" ;
		navToolbar.goToPortalSites();
		addNewPortal(portalName1, null, null, "French", null, "Always", true, permissions2, editGroupId2, editMembership2);
		waitForAndGetElement(ELEMENT_PORTAL_EDIT_CONFIGURATION.replace("${siteName}", portalName1));

		info("Add new portal is the same with exitings");
		Map<String, String> permissions1 = null;
		String editGroupId1 = "Platform /Content Management ";
		String editMembership1 = "manager" ;
		navToolbar.goToPortalSites();
		addNewPortal(portalName, null, null, "French", null, "Always", true, permissions1, editGroupId1, editMembership1);
		waitForAndGetElement(ELEMENT_PORTAL_NAME_EXIST);
		button.ok();
		button.cancel();

		info("Delete portal");
		driver.get(baseUrl);
		navToolbar.goToPortalSites();
		deletePortal(portalName);
		deletePortal(portalName1);
	}
	/**CaseId: 73568,101005 -> Add portal with name is empty and have template 
	 * 
	 */
	@Test
	public void test03_AddPortalWitTemplateAndPortalNameIsEmpty(){
		String portalName = "gateinfunctportal101005";
		String portalSkin ="Empty Portal";

		info("Add new portal with blank name");
		Map<String, String> permissions = null;
		String editGroupId = "Platform /Content Management ";
		String editMembership = "*" ;
		navToolbar.goToPortalSites();
		addNewPortal(null, null, null, "French", null, "Always", true, permissions, editGroupId, editMembership);
		waitForMessage(MESSAGE_PORTAL_NAME_REQUIRED_FIELD);
		button.ok();
		button.cancel();

		info("Add new portal with blank name");
		Map<String, String> permissions1 = null;
		String editGroupId1 = "Platform /Content Management ";
		String editMembership1 = "*" ;
		navToolbar.goToPortalSites();
		addNewPortal(portalName, null, null, "French", null, "Always", true, permissions1, editGroupId1, editMembership1,portalSkin);
		waitForAndGetElement(ELEMENT_PORTAL_EDIT_CONFIGURATION.replace("${siteName}", portalName));

		info("Switch to new portal");
		driver.get(DEFAULT_BASEURL+"/" + portalName);
		waitForAndGetElement(ELEMENT_PORTAL_NAME_SITE_ACCESS.replace("${portalName}", portalName));

		info("Delete portal");
		driver.get(baseUrl);
		navToolbar.goToPortalSites();
		deletePortal(portalName);
	}
}
