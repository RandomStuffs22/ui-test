package org.exoplatform.selenium.platform.plf.functional.branding;

import static org.exoplatform.selenium.TestLogger.info;
import junit.framework.Assert;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.BrandingManagement;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author havtt
 */
public class PLF_Branding extends PlatformBase{
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
	 * caseID 79316: Branding portlet should be accessible by Administration menu
	 * caseID 79317: The layout should be composed of 4 parts
	 */

	@Test
	public void test01_CheckDisplayOfBrandingPortlet() {
		naviToolbar.goToPortalBranding();
	}

	/**
	 * caseID 79318: User should be able to upload a logo
	 */

	@Test
	public void test02_CheckUploadLogoPNGInBrandingPortlet() {
		String urlFile = "FNC_ECMS_FEX_ACTION_09_1.png";

		info("Go to Branding portlet");
		naviToolbar.goToPortalBranding();

		info("Upload a valid logo");
		brandMag.uploadLogo(urlFile, true, true);	

	}

	/**
	 * caseID 79319: Logo image uploaded must be png format
	 */

	@Test
	public void test03_CheckUploadCorrectLogoFormatInBrandingPortlet() {
		String urlFile = "ECMS_DMS_SE_Upload_imgfile.jpg";
		
		info("Go to Branding portlet");
		naviToolbar.goToPortalBranding();

		info("Upload an invalid logo");
		brandMag.uploadLogo(urlFile, false, false);
		waitForMessage(brandMag.INCORRECT_LOGO_MSG);

	}

	/**
	 * caseID 79320: Loaders should be displayed in preview boxes during upload is processing
	 */
	//This case must be checked by manual test
	@Test(groups={"pending"})
	public void test04_CheckLoaderWhenUploadLogo() {

	}

	/**
	 * caseID 79321: Image larger than 300*34 should be resized
	 */
	
	@Test
	public void test05_CheckImgResizeWhenUploadLogo() {

		String urlFile = "FNC_ECMS_FEX_ACTION_09_1.png";
		int size = 34;

		info("Go to Branding portlet");
		naviToolbar.goToPortalBranding();

		info("Upload a valid logo");
		brandMag.uploadLogo(urlFile, true, true);	
		
		info("Check image dimension in Branding");
		int select = driver.findElement(brandMag.ELEMENT_LOGO_CONTAINER_PREVIEW).getSize().getHeight();
		int preview = driver.findElement(brandMag.ELEMENT_PREVIEW_LOGO).getSize().getHeight();
		Assert.assertEquals(size,select);
		Assert.assertEquals(preview, size);
	}

	/**
	 * caseID 79322: A new logo uploaded is displayed in preview boxes
	 */

	@Test
	public void test06_CheckPreviewAfterUploadLogo() {
		String urlFile = "FNC_ECMS_FEX_ACTION_09_1.png";

		info("Go to Branding portlet");
		naviToolbar.goToPortalBranding();

		info("Upload a valid logo");
		brandMag.uploadLogo(urlFile, true, false);	
	}

	/**
	 * caseID 79323: User is able to save branding settings
	 */

	@Test
	public void test07_CheckSaveAfterUploadLogo() {
		String urlFile = "FNC_ECMS_FEX_ACTION_09_1.png";

		info("Go to Branding portlet");
		naviToolbar.goToPortalBranding();

		info("Upload a valid logo");
		brandMag.uploadLogo(urlFile, true, true);	
		waitForMessage(brandMag.SAVE_INFO_MSG);
	}

	/**
	 * caseID 79324: User is able to cancel branding settings
	 */

	@Test
	public void test08_CheckCancelAfterUploadLogo() {
		String urlFile = "FNC_ECMS_FEX_ACTION_09_1.png";

		info("Go to Branding portlet");
		naviToolbar.goToPortalBranding();

		info("Upload a valid logo");
		brandMag.uploadLogo(urlFile, true, false);	
		button.cancel();
		waitForMessage(brandMag.CANCEL_INFO_MSG);
	}

	/**
	 * caseID 79325: Default navigation bar style should be dark
	 */

	@Test
	public void test09_DefaultNavigationIsDarkStyle() {
		info("Go to Branding portlet");
		naviToolbar.goToPortalBranding();

		info("Check default navigation style");
		waitForAndGetElement(brandMag.ELEMENT_NAVIGATION_STYLE_CURRENT.replace("${optionStyle}","Dark"));
	}

	/**
	 * caseID 79326: User should be able to select light style
	 */

	@Test
	public void test10_LightStyleCanBeSelected() {
		info("Go to Branding portlet");
		naviToolbar.goToPortalBranding();

		info("Select Light style");
		brandMag.selectNavigationBarStyle("Light");
	}

	/**
	 * caseID 79327: User should be able to select dark style
	 */

	@Test
	public void test11_DarkStyleCanBeSelected() {
		info("Go to Branding portlet");
		naviToolbar.goToPortalBranding();

		info("Select Dark style");
		brandMag.selectNavigationBarStyle("Dark");
	}

	/**
	 * caseID 79328: Changing navigation bar style should have 
	 * 				 no impacts on sub menu of the navigation bar
	 */

	@Test
	public void test12_CheckImpactOfChangeStyle() {
		info("Check style of Admin sub-menu before change");
		driver.navigate().refresh();
		click(ELEMENT_LINK_SETUP);
		String before = driver.findElement(By.xpath("//*[@id='UISetupPlatformToolBarPortlet']/ul[@class='dropdown-menu']")).getCssValue("background-color"); 

		info("Go to Branding portlet");
		naviToolbar.goToPortalBranding();

		info("Select Light style");
		brandMag.selectNavigationBarStyle("Light");

		info("Check style of Admin sub-menu after change");
		driver.navigate().refresh();
		Utils.pause(2000);
		click(ELEMENT_LINK_SETUP);
		String after = driver.findElement(By.xpath("//*[@id='UISetupPlatformToolBarPortlet']/ul[@class='dropdown-menu']")).getCssValue("background-color");

		assert before.equals(after);
	}

	/**
	 * caseID 79329: Logo defined should be 34 pixels height in the final navigation bar
	 * 
	 */

	@Test
	public void test13_CheckLogoSizeAfterSavedOnFinalNavBar() {
		String urlFile = "FNC_ECMS_FEX_ACTION_09_1.png";
		String size = "34px";

		info("Go to Branding portlet");
		naviToolbar.goToPortalBranding();

		info("Upload a valid logo");
		brandMag.uploadLogo(urlFile, true, true);

		info("Go to Intranet Homepage");
		naviToolbar.goToHomePage();
		String logo = driver.findElement(brandMag.ELEMENT_LOGO_CONTAINER_TOOLBAR).getCssValue("height");
		assert logo.equals(size);
	}
}
