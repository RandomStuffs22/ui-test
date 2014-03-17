package org.exoplatform.selenium.platform.gatein.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.Map;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PortalManagement;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 29 July 2013
 * @author lientm
 */

public class Gatein_Manage_Portal extends PortalManagement{

	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement user;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		user = new UserGroupManagement(driver);
		
		magAc.signIn("john", DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 68885 + 68887 + 68886 -> Add, switch sites and delete site
	 * 
	 */
	
	@Test
	public void test01_AddDeletePortal(){
		String portalName = "gateinsniffportal01";
		
		info("Add new portal");
		Map<String, String> permissions = null;
		String editGroupId = "Platform /Content Management ";
		String editMembership = "*" ;
		navTool.goToPortalSites();
		addNewPortal(portalName, null, null, "French", null, "Always", true, permissions, editGroupId, editMembership);
		waitForAndGetElement("//*[@class='siteName' and text()='" + portalName + "']");
		
		info("Switch to new portal");
		driver.get("http://localhost:8080/portal/" + portalName);
		waitForAndGetElement("//img[contains(@src, 'sites/" + portalName + "')]");
		
		info("Delete portal");
		driver.get(baseUrl);
		navTool.goToPortalSites();
		deletePortal(portalName);
	}
}
