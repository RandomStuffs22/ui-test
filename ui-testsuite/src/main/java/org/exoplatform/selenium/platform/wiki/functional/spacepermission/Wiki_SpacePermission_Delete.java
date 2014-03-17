package org.exoplatform.selenium.platform.wiki.functional.spacepermission;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Permission;

/**
 *
 * @author HangNTT
 * @date: 18/12/2012
 */
public class Wiki_SpacePermission_Delete extends Permission {

	ManageAccount magAc;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magAc.signIn("john", DATA_PASS);
	}

	//Qmetry ID: 69746
	//Delete permission for space
	@Test
	public void test01_DeletePermissionForSpace() {

		String[] user2= {"james"};

		goToWiki();

		addSpacePermission(0, user2);

		deleteSpacePermission("james");
	}

	@AfterMethod
	public void afterTest(){
		//signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}