package org.exoplatform.selenium.platform.plf.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.BrandingManagement;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 21/10/2013
 *
 */
public class PLF_Branding extends PlatformBase{
	//Platform
	ManageAccount magAcc;
	NavigationToolbar naviToolbar;
	BrandingManagement brandMag;
	Button btn;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		naviToolbar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		brandMag = new BrandingManagement(driver);
		btn = new Button(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Check display of branding portlet ==
	 * Test case ID: 74827
	 * Step 1: Open Branding page
	 */
	@Test
	public void test01_CheckDisplayOfBrandingPortlet(){
		//Declare variable

		/*Step 1: Open Branding page*/ 
		//- Login as an admin
		//- Go to intranet home page
		//- Intranet home page is opened
		//- Click on Administration -> Portal -> Branding
		//- The Branding page is shown. There is 4 parts on the layout : 
		//   + Select Logo
		//   + Select Navigation Bar Style
		//   + Preview
		//   + Validation buttons
		//- See attachment. 
		naviToolbar.goToPortalBranding();
	}

	/**
	 * == Select logo ==
	 * Test case ID: 74357
	 * Step 1: Open branding page
	 * Step 2: Upload a logo
	 * Step 3: Preview
	 * Step 4: Save
	 */
	@Test
	public void test02_SelectLogo(){
		//Declare variable
		String urlFile = "FNC_ECMS_FEX_ACTION_09_1.png";
		
		/* Step 1: Open branding page*/
		//- Login as admin
		//- Connect to intranet home page
		//- Go to Administration button -> Portal -> Branding 
		//- Branding portlet is displayed
		naviToolbar.goToPortalBranding();
		
		/* Step 2: Upload a logo*/
		//- Upload a new logo image
		//- The new logo image is uploaded

		/* Step 3: Preview*/
		//- Check preview boxes
		//- The logo must be displayed in :
		//	* preview boxe for the logo
		//	* preview of the full navigation bar

		/* Step 4: Save*/
		//-Click on Save
		//New Logo is shown as preview
		brandMag.uploadLogo(urlFile, true, true);		
	}
	
	/**
	 * == Select navigation bar style ==
	 * Test case ID: 74356
	 * Step 1: Open branding page
	 * Step 2: Show a theme select box
	 * Step 3: Select one theme
	 * Step 4: Save
	 * Note: Need to update qmetry (please remove step 3)
	 */
	@Test
	public void test03_SelectNavigationBarStyle(){
		//Declare variable
		String optionStyle1 = "Dark";
		String optionStyle2 = "Light";
		
		/* Step 1: Open branding page*/
		//- Login as admin
		//- Connect to intranet home page
		//- Go to Administration button -> Portal -> Branding 
		naviToolbar.goToPortalBranding();
		//- Branding portlet is displayed
		/* Step 2: Show a theme select box*/
		//- Go to the part to select the theme
		//- Click on the select box
		//- The select box is opened with two choices:
		//* Dark
		//* Light
		
		/* Step 4: Save*/
		//-Click on Save
		//- The navigation bar of the intranet is displayed as preview
		brandMag.selectNavigationBarStyle(optionStyle1);
		naviToolbar.goToPortalBranding();
		brandMag.selectNavigationBarStyle(optionStyle2);
	}
}