package org.exoplatform.selenium.platform.ecms.functional.admin.advanced;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageScript;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * @date April, 26th, 2013
 */
public class ECMS_Admin_ManageScripts extends PlatformBase{
	//Platform
	ManageAccount magAcc;

	//Ecms
	ECMainFunction ecMain;
	ManageScript magScript;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER1);
		magAcc = new ManageAccount(driver);
		ecMain = new ECMainFunction(driver); 
		magScript = new ManageScript(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	 * == Add a Script when input valid data in required fields ==
	 * == Delete Script == 
	 * <li>Step 1: Open [Manage Scripts] form</li>
	 * <li>Step 2: Open Add/Edit script form</li>
	 * <li>Step 3: Add new script</li>
	 */
	@Test
	public void test01_02_AddScriptWhenInputValidDataInRequiredFields(){
		String scriptFileContent = "TestData/ECMS_Admin_SendMailScript_Template.txt"; 
		String scriptLabel = "SendMailScript"; 
		String scriptName = "SendMailScript";
		
		ecMain.goToScriptsTabInContentAdmin();
		
		magScript.addScript(scriptFileContent, scriptLabel, scriptName);
		
		magScript.deleteScript(scriptLabel);
	}
}
