package org.exoplatform.selenium.platform;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import org.exoplatform.selenium.TestBase;
import org.exoplatform.selenium.platform.ManageAccount;

/**
 * 
 * @author vuna2
 *
 */
public class LoginTest extends TestBase{
	
	//ManageAccount magAcc = new ManageAccount(driver);
	ManageAccount magAcc;
	
	@BeforeGroups(groups = {"platform"})
	public void beforeTest()  {
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();	
		magAcc = new ManageAccount(driver);
	}

	@AfterGroups(groups = {"platform"})
	public void afterTest() {
		driver.quit();
	}
	
	@Test(groups = {"platform", "LoginTest"})
	public void testLoginasRoot(){
		magAcc.signIn("root", "gtn");
		waitForTextPresent("Root");
		magAcc.signOut();
	}
	@Test(groups = {"platform", "LoginTest"})
	public void testLoginasJack(){
		magAcc.signIn("demo", "gtn");
		waitForTextPresent("Jack Miller");
		magAcc.signOut();
	}
	@Test(groups = {"platform", "LoginTest"})
	public void testLoginasMary(){
		magAcc.signIn("mary", "gtn");
		waitForTextPresent("Mary Williams");
		magAcc.signOut();
	}
	@Test(groups = {"platform", "LoginTest"})
	public void testLoginasJames(){
		magAcc.signIn("james", "gtn");
		waitForTextPresent("James Davis");
		magAcc.signOut();
	}	
	@Test(groups = {"platform", "LoginTest"})
	public void testLoginasJohn(){	
		magAcc.signIn("john", "gtn");
		waitForTextPresent("John Smith");
		magAcc.signOut();		
	}	
}
