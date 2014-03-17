package org.exoplatform.selenium.platform.ecms.functional.admin.advanced;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageQuery;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * @date April, 23th, 2013
 */
public class ECMS_Admin_ManageQueries extends PlatformBase{

	//Platform
	ManageAccount magAcc;
	
	//Ecms
	ECMainFunction ecMain;
	ManageQuery magQuery;

	public final String DATA_USER = "john";
	//public final String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER);
		magAcc = new ManageAccount(driver);
		ecMain = new ECMainFunction(driver); 
		magQuery = new ManageQuery(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	 * Add queries When input valid data into all required fields
	 * <li>Step 1: Open 'Manage Queries' form</li>
	 * <li>Step 2: Open 'Add query' pop-up</li>
	 * <li>Step 3: Add new query</li>
	 */
	@Test
	public void test01_AddQueriesWhenInputValidDataIntoAllRequiredFields(){
		String queryName = "All Articles"; 
		String queryType = "xPath";
		boolean enableCacheResult = true;
		String group = "Platform/Users";
		String membership = "*";
		String statement = "//element(*,exo:article)[not(@jcr:mixinTypes = 'exo:restoreLocation')] order by @exo:dateCreated descending";
		
		ecMain.goToQueriesTabInContentAdmin();
		
		magQuery.addQuery(queryName, queryType, enableCacheResult, group, membership, true, statement);			
	
		magQuery.deleteQuery(queryName);
	}
	
	/**
	 * == Edit a Query ==
	 * == Delete a Query ==
	 */
	@Test
	public void test02_03_EditAndDeleteQuery(){
		String queryName = "Created Documents"; 
		String queryType = "SQL";
		boolean enableCacheResult = false;
		String group = "Platform/Users";
		String membership = "*";
		String statement = "//*[(@jcr:primaryType = 'exo:article' or @jcr:primaryType = 'nt:file') " +
				"and @exo:owner='${UserId}$' and not(@jcr:mixinTypes = 'exo:restoreLocation')] order by @exo:dateCreated descending";
		
		ecMain.goToQueriesTabInContentAdmin();
		
		magQuery.addQuery(queryName, queryType, enableCacheResult, group, membership);
		
		magQuery.editQuery(queryName, "xPath", true, true, statement, true, "Platform/Administration", "*");
		
		Utils.captureScreen("ECMS_Admin_Edit_Query");
		
		magQuery.deleteQuery(queryName);
	}
}