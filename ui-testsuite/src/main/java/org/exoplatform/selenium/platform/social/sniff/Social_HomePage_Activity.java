package org.exoplatform.selenium.platform.social.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.exoplatform.selenium.platform.social.PeopleProfile;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 14/10/2013
 *
 */
public class Social_HomePage_Activity extends Activity {
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	HomePageActivity activity;
	ManageMember magMember;
	ActionBar actBar;

	//ecms
	EcmsBase ecms;
	ContentTemplate conTemp;
	ContextMenu cMenu;
	SitesExplorer siteExp;

	//social
	PeopleConnection peoConn;
	PeopleProfile peoPro;

	String user = "John Smith";
	String user1="Mary Williams";

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER1);
		magAcc = new ManageAccount(driver,this.plfVersion);
		activity = new HomePageActivity(driver,this.plfVersion);
		navToolBar = new NavigationToolbar(driver,this.plfVersion);
		peoConn = new PeopleConnection(driver,this.plfVersion);
		magMember = new ManageMember(driver,this.plfVersion);
		peoPro = new PeopleProfile(driver);
		actBar = new ActionBar(driver,this.plfVersion);
		ecms = new EcmsBase(driver,this.plfVersion);
		conTemp = new ContentTemplate(driver,this.plfVersion);
		cMenu= new ContextMenu(driver,this.plfVersion);
		siteExp = new SitesExplorer(driver,this.plfVersion);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Check Layout of Activities ==
	 * Test case ID: 74725
	 * Step 1: Check layout of activity
	 */
	@Test(priority = 0)
	public void test01_CheckLayoutOfActivities(){
		//Declare variable
		String activity1 = "activity747251";
		String comment1 = "comment747251";
		String spaceName = "space74725";
		String nameWebContent="fileName74725";
		By bNameWebContent = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", nameWebContent));

		//Create data
		info("-- Create space --");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");

		info("-- Create new Webcontent --");
		navToolBar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(nameWebContent, nameWebContent, "");
		navToolBar.goToHomePage();
		
		info("-- Create activity --");
		addActivity(true, activity1, false,"");

		/*Step 1: Check layout of activity*/ 
		//- log in and goto intranet hompage
		//- Select an activity to check its layout

		//An activity is made out of different parts:
		//	(see attached)
		//	  1.  the author
		info("-- Verify author name --");
		waitForAndGetElement(activity.ELEMENT_ACTIVITY_AUTHOR_NAME.replace("${index}", "1").replace("${author}", user));
		//	  2.  the author's avatar
		info("-- Verify author avatar --");
		waitForAndGetElement(activity.ELEMENT_ACTIVITY_AUTHOR_AVATAR.replace("${index}", "1").replace("${author}", user));
		//	  3.  the space (optional)
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "3").replace("${content}", spaceName));
		//	  4.  the type (optional)
		//	  5.  the activity message
		info("-- Verify activity message --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "1").replace("${content}", activity1));
		//	  6.  the featured content (optional)
		info("-- Verify the featured content --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "2").replace("${content}", nameWebContent));
		//	  7.  the Action bar (Comment and Like links + custom actions)
		info("-- Verify action bar --");
		waitForAndGetElement(activity.ELEMENT_ICON_COMMENT.replace("${title}", activity1));
		waitForAndGetElement(activity.ELEMENT_LIKE_ICON.replace("${activityText}", activity1));
		//	  8.  the like section (optional)
		info("-- Verify like section --");
		waitForElementNotPresent(ELEMENT_AVATAR_LIST_LIKER_INDEX.replace("${activityText}", activity1).replace("${index}", "1"));
		activity.likeOrUnlikeActivity(activity1);
		waitForAndGetElement(ELEMENT_AVATAR_LIST_LIKER_INDEX.replace("${activityText}", activity1).replace("${index}", "1"));
		//	  9.  the comment section (optional)
		info("-- Verify comment section --");
		waitForElementNotPresent(ELEMENT_COMMENT_LIST.replace("${activityText}", activity1).replace("${commentText}", comment1), DEFAULT_TIMEOUT,1,2);
		addComment(activity1, comment1);
		waitForAndGetElement(ELEMENT_DELETE_COMMENT_BUTTON.replace("${activityText}", activity1).replace("${commentText}", comment1), DEFAULT_TIMEOUT,1,2);

		//Clear data
		info("clear data");
		activity.deleteActivity(activity1);

		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);

		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_SITES_MANAGEMENT_DRIVE);
		cMenu.deleteData(bNameWebContent);
	}

	/**
	 * == Check order of the activities ==
	 * Test case ID: 95701
	 * Step 1: Check the order of activities
	 */
	@Test(priority = 0)
	public void test02_CheckOrderOfTheActivities(){
		//Declare variable
		String activity1 = "activity95701";
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String uploadFileName1 = "ECMS_DMS_SE_Upload_imgfile.jpg";
		By elementfile = By.linkText(uploadFileName1);
		String folder = "folder95701";
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		String link = "https://www.google.com.vn/";
		String fileDocument="filedocument95701";
		String spaceName = "space95701";
		String nameWebContent="fileName95701";
		By bNameWebContent = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", nameWebContent));
		String email = "john1.smith1@acme.exoplatform.com";
		String oldEmail = "john.smith@acme.exoplatform.com";
		String node = "node957011";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String node1 = "node957012";
		By bNode1 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));

		/*Step 1: Check the order of activities*/ 
		//- Login to the platform
		//- Create activities of different kinds 
		//(ECMS webcontents, ECMS symlinks, share files to Personal Documents/Public, create space ...etc), 
		//at least 10 activities		
		info("-- 1. Add a link --");
		addActivity(false, "", true, link);

		info("-- 2. Add New folder and upload file --");
		selectFile(driverName,true,folderPath,"",uploadFileName,folder);

		info("-- 3. Add a document --");
		info("Go to Persional Documents");
		navToolBar.goToPersonalDocuments();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.goToAddNewContent();
		conTemp.createNewFile(fileDocument, fileDocument, "");
		navToolBar.goToHomePage();
		selectFile(driverName,false,folderPath,fileDocument,""); 

		info("-- 4. Create activity --");
		navToolBar.goToHomePage();
		addActivity(true, activity1, false,"");

		info("-- 5. Create space --");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");

		info("-- 6. Create new Webcontent --");
		navToolBar.goToSiteExplorer();
		//Check if create content button is shown on action bar
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(nameWebContent, nameWebContent, "");

		info("-- 7. Upload file --");
		actBar.goToNodeByAddressPath("/");
		ecms.uploadFile("TestData/"+uploadFileName1);

		info("-- 8. Add a new content: Product --");
		actBar.goToAddNewContent();
		conTemp.createNewWebLink(node,node);

		info("-- 9. Add Accessible Media content --");
		actBar.goToNodeByAddressPath("/");
		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();
		conTemp.createNewAccessibleMedia(node1);

		info("-- 10. Update user profile --");
		navToolBar.goToMyProfile();
		peoPro.editUserBasicInformation("", "", email);

		//- The activities should be displayed in the good order (newest at the top)
		//- We have only 1 activity per kind (no duplication)
		info("-- The activities should be displayed in the good order (newest at the top) --");
		navToolBar.goToHomePage();
		info("-- Verify activity 1 --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "1").replace("${content}", user)+"/../../..//p[@class='userEmail']");
		info("-- Verify activity 2 --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "2").replace("${content}", node1));
		info("-- Verify activity 3 --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "3").replace("${content}", node));
		info("-- Verify activity 4 --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "4").replace("${content}", uploadFileName1));
		info("-- Verify activity 5 --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "5").replace("${content}", nameWebContent));
		info("-- Verify activity 6 --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "6").replace("${content}", spaceName));
		info("-- Verify activity 7 --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "7").replace("${content}", activity1));
		info("-- Verify activity 8 --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "8").replace("${content}", fileDocument));
		info("-- Verify activity 9 --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "9").replace("${content}", uploadFileName));
		info("-- Verify activity 10 --");
		waitForAndGetElement(activity.ELEMENT_CONTENT_ACTIVITY_INDEX.replace("${index}", "10").replace("${content}", link));

		//Clear data
		info("clear data");
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_SITES_MANAGEMENT_DRIVE);
		cMenu.deleteData(bNameWebContent);
		cMenu.deleteDocument(bNode);
		cMenu.deleteData(elementfile);	
		cMenu.deleteDocument(bNode1);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
		navToolBar.goToMyProfile();
		peoPro.editUserBasicInformation("", "", oldEmail);
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE,true,true);
		actBar.actionsOnElement(fileDocument, actionType.DELETE,true,true);
		navToolBar.goToHomePage();
		activity.deleteActivity(activity1);
		activity.deleteActivity(link);
	}

	/**
	 * == Add comment ==
	 * Test case ID: 67890
	 * Step 1: Add comment for activity
	 */
	@Test
	public void test03_AddComment(){
		//Declare variable
		String activity1 = "activity 67890";
		String comment1 = "comment 67890";

		//Create data
		info("-- Create activity --");
		addActivity(true, activity1, false,"");

		/*Step 1: Add comment for activity*/ 
		//- Go to Intranet home
		//- Select the activity
		//- Click comment icon to show input text field
		//- input the comment and click comment
		//- Comment will be shown in comment section of activity
		info("-- Add comment --");
		addComment(activity1, comment1);

		//Clear data
		info("clear data");
		activity.deleteActivity(activity1);
	}

	/**
	 * == Delete comment ==
	 * Test case ID: 74724
	 * Step 1: - Delete a comment
	 */
	@Test
	public void test04_DeleteComment(){
		//Declare variable
		String activity1 = "activity 74724";
		String comment1 = "comment 747241";
		String comment2 = "comment 747242";

		//Create data
		info("-- Create activity --");
		addActivity(true, activity1, false,"");
		info("-- Add comment --");
		addComment(activity1, comment1);
		addComment(activity1, comment2);

		/*Step 1: - Delete a comment*/ 
		//- Go to Intranet home
		//- Select the activity that has some comment
		//- mouse over the comment you want to delete
		//- Click the (x) icon to delete comment
		deleteComment(activity1,comment1);

		//Clear data
		info("clear data");
		activity.deleteActivity(activity1);
	}

	/**
	 * == Mention a user in comment ==
	 * Test case ID: 74729
	 * Step 1: Add comment with mention
	 */
	@Test
	public void test05_MentionAUserInComment(){
		//Declare variable
		String activity1 = "activity 74729";

		//Create data
		info("-- Create activity --");
		addActivity(true, activity1, false,"");

		/*Step 1: Add comment with mention*/ 
		//- Select the activity that has some comments
		//- input the comment with mention and click comment
		//- Input text field is displayed in activity, click comment button to show comment textbox
		//- Comment will be added into comment section of activity
		info("-- Mention a user in comment --");
		mentionActivity(false,activity1,user1);

		//Clear data
		info("clear data");
		activity.deleteActivity(activity1);
	}

	/**
	 * == View Comments ==
	 * Test case ID: 74728
	 * Step 1: - Check view of comment
	 */
	@Test
	public void test06_ViewComments(){
		//Declare variable
		String activity1 = "activity 74728";
		String commentindex = "commentindex";
		int index=1;

		//Create data
		info("-- Create activity --");
		addActivity(true, activity1, false,"");
		info("-- Create 10 comments --");
		for(int i = 1; i<11; i++){
			addComment(activity1, commentindex+" "+String.valueOf(i));
		}

		/*Step 1: - Check view of comment*/ 
		Utils.pause(1000);
		navToolBar.goToHomePage();
		//- select an activity with more than 2 comments on it (fx: 10 comments)
		//- only  the latest (most recently posted) two comments are displayed below the activity.
		waitForAndGetElement(ELEMENT_DELETE_COMMENT_BUTTON.replace("${activityText}", activity1).replace("${commentText}", commentindex + " 10"), DEFAULT_TIMEOUT,1,2);
		waitForAndGetElement(ELEMENT_DELETE_COMMENT_BUTTON.replace("${activityText}", activity1).replace("${commentText}", commentindex + " 9"), DEFAULT_TIMEOUT,1,2);
		waitForElementNotPresent(ELEMENT_DELETE_COMMENT_BUTTON.replace("${activityText}", activity1).replace("${commentText}", commentindex + " 8"), DEFAULT_TIMEOUT,1,2);

		//- click on the message "View all 10 comments"
		//- "View all 10 comments" message is shown 
		//- all comments is displayed, in the time order (oldest at the top)
		showHideComments(activity1,true,false,"10");
		for(int i = 1; i<11; i++){
			info("-- Verify comment "+String.valueOf(i) +"--");
			waitForAndGetElement(ELEMENT_DELETE_COMMENT_BUTTON_INDEX.replace("${activityText}", activity1).replace("${commentText}", commentindex +" "+String.valueOf(i)).replace("${index}", String.valueOf(index)), DEFAULT_TIMEOUT,1,2);
			index++;
		}

		//Clear data
		info("clear data");
		activity.deleteActivity(activity1);
	}

	/**
	 * == Like Activity ==
	 * Test case ID: 67659
	 * Step 1: Like/Unlike Activity
	 * Step 2: Check Likes part
	 */
	@Test
	public void test07_LikeActivity(){
		//Declare variable
		String activity1 = "activity 67659";

		//Create data
		info("-- Create activity --");
		addActivity(true, activity1, false,"");

		//Connect people
		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();

		//Display list of people
		//Click on Connect button to invite about 2 users
		peoConn.connectPeople(user1);

		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.PUBLISHER);
		//An user click on Confirm button
		peoConn.acceptInvitation(user);

		//- Go to Intranet home
		//- Click on Like activity in action bar part of an activity
		//- Like button is highlighted and the number of likers is updated
		navToolBar.goToHomePage();
		activity.likeOrUnlikeActivity(activity1);

		/*Step 1: Like/Unlike Activity*/ 
		//- Go to Intranet home
		//- Click on Like activity in action bar part of an activity
		//- Like button is highlighted and the number of likers is updated
		magAcc.userSignIn(userType.ADMIN);

		activity.likeOrUnlikeActivity(activity1);

		/*Step 2: Check Likes part*/
		//- Check avatar
		//- Avatar of liker is added into likes part, the oldest liker is displayed at the right and the newest at the left.
		info("-- Check mouse over avatar --");
		String avatarName = waitForAndGetElement(ELEMENT_AVATAR_LIST_LIKER_INDEX.replace("${activityText}", activity1).replace("${index}", "1")).getAttribute("alt");
		String avatarName1 = waitForAndGetElement(ELEMENT_AVATAR_LIST_LIKER_INDEX.replace("${activityText}", activity1).replace("${index}", "2")).getAttribute("alt");
		assert(avatarName.contains(user));
		assert(avatarName1.contains(user1));

		//- Mouse over the avatar
		mouseOver(ELEMENT_AVATAR_LIST_LIKER_INDEX.replace("${activityText}", activity1).replace("${index}", "1"),true);
		//- Profile pictures of users popup
		waitForAndGetElement(ELEMENT_USER_PROFILE_POPUP.replace("${userName}", user));

		//Clear data
		info("clear data");
		peoConn.removeConnection(user1);
		navToolBar.goToHomePage();
		activity.deleteActivity(activity1);
	}

	/**
	 * == Relation Activity ==
	 * Test case ID: 74765
	 * Step 1: - Invite another user
	 * Step 2: - Accept request
	 */
	@Test
	public void test08_RelationActivity(){
		//Declare variable
		String activity1 = "activity 74765";

		//Create data
		info("-- Create activity --");
		addActivity(true, activity1, false,"");

		/*Step 1: - Invite another user*/ 
		//- Login with User A and go to Intranet
		//- Go to Connections 
		navToolBar.goToConnectionPage();
		//- Find user B and send a request
		//- Request is sent to the user B
		peoConn.connectPeople(user1);

		/*Step 2: - Accept request*/
		//- Login as user B and goto my Connection
		magAcc.userSignIn(userType.PUBLISHER);
		//- Accept the request from user A
		peoConn.acceptInvitation(user);

		//- A Relation activity is displayed to the activity stream
		navToolBar.goToHomePage();
		waitForAndGetElement(activity.ELEMENT_LIKE_ICON.replace("${activityText}", activity1));

		//Clear data
		info("clear data");
		magAcc.userSignIn(userType.ADMIN);
		peoConn.removeConnection(user1);
		navToolBar.goToHomePage();
		activity.deleteActivity(activity1);
	}

	/**
	 * == Create a new space ==
	 * Test case ID: 74789
	 * Step 1: - Create a new Space
	 */
	@Test
	public void test09_CreateANewSpace(){
		//Declare variable
		String spaceName = "space74789";
		String spaceDesc = "spacedes74789";

		/*Step 1: - Create a new Space*/ 
		//- Goto homepage
		//- Click [Join a space] on left Navigation
		//- Click [Add new space] button
		//- Fill the information and click create
		//- a new space is created
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, spaceDesc);

		//- Check homepage
		navToolBar.goToHomePage();
		//- an activity is added into activity stream
		waitForAndGetElement(ELEMENT_ACTIVITY_AUTHOR_SPACENAME.replace("${activityText}", spaceName));

		//- Informations displayed in the featured content are :
		//1. Space's avatar
		waitForAndGetElement(ELEMENT_AVATAR_SPACE_ACTIVITY.replace("${activityText}", spaceName));

		//2. Space's description
		waitForAndGetElement(ELEMENT_DESCRIPTION_SPACE_ACTIVITY.replace("${activityText}", spaceName).replace("${description}", spaceDesc));

		//3. Number of members.
		waitForAndGetElement(ELEMENT_NUMBER_MEMBER_SPACE_ACTIVITY.replace("${activityText}", spaceName).replace("${numbermember}", "1"));

		//Clear data
		info("clear data");
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * == Promote a member as manager ==
	 * Test case ID: 74792
	 * Step 1: Promote a member as manager
	 */
	@Test
	public void test10_PromoteAMemberAManager(){
		//Declare variable
		String spaceName = "space74792";
		String spaceDesc = "spacedes75792";

		//Create data
		info("-- Create space --");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, spaceDesc);

		//- Check homepage
		navToolBar.goToHomePage();
		//- an activity is added into activity stream
		waitForAndGetElement(ELEMENT_ACTIVITY_AUTHOR_SPACENAME.replace("${activityText}", spaceName));

		//- Informations displayed in the featured content are :
		//1. Space's avatar
		waitForAndGetElement(ELEMENT_AVATAR_SPACE_ACTIVITY.replace("${activityText}", spaceName));

		//2. Space's description
		waitForAndGetElement(ELEMENT_DESCRIPTION_SPACE_ACTIVITY.replace("${activityText}", spaceName).replace("${description}", spaceDesc));

		//3. Number of members.
		waitForAndGetElement(ELEMENT_NUMBER_MEMBER_SPACE_ACTIVITY.replace("${activityText}", spaceName).replace("${numbermember}", "1"));

		//- Invite a user become member of space
		magMember.managerInviteUserToJoinSpace(userType.ADMIN,spaceName,userType.PUBLISHER,false);

		//- User accept invitation
		//- User become member of Space
		magMember.userAcceptInvitationToJoinSpace(true,userType.PUBLISHER,spaceName);

		/*Step 1: Promote a member as manager*/ 
		//- Connect to Intranet
		magAcc.userSignIn(userType.ADMIN);

		//- Open a space
		//- Click [Settings] -> Members
		//- Click Grant Manager to promote the user we want
		magMember.grantManagerForUser(spaceName,user1);

		//- Back to the Homepage
		navToolBar.goToHomePage();
		driver.navigate().refresh();

		//- The Space activity content isn't updated in the activity stream
		//- Informations displayed in the featured content are :
		//1. Space's avatar
		waitForAndGetElement(ELEMENT_AVATAR_SPACE_ACTIVITY.replace("${activityText}", spaceName));

		//2. Space's description
		waitForAndGetElement(ELEMENT_DESCRIPTION_SPACE_ACTIVITY.replace("${activityText}", spaceName).replace("${description}", spaceDesc));

		//3. Number of members.
		waitForAndGetElement(ELEMENT_NUMBER_MEMBER_SPACE_ACTIVITY.replace("${activityText}", spaceName).replace("${numbermember}", "2"));

		//- A comment is added: $user has been promoted as space's manager.
		String comment = waitForAndGetElement(ELEMENT_COMMENT_BOX_SPACE_ACTIVITY.replace("${activityText}", spaceName)+"/..//a[text()='"+user+"']/../../p").getText();
		info(comment);
		assert comment.contains(user1+" has been promoted as space's manager");

		//Clear data
		info("clear data");
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * == Rename a space ==
	 * Test case ID: 74791
	 * Step 1: - Rename space
	 */
	@Test
	public void test11_RenameASpace(){
		//Declare variable
		String spaceName = "space74791";
		String spaceDesc = "spacedes74791";
		String newSpaceName = "newspace74791";

		//Create data
		info("-- Create space --");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, spaceDesc);

		//- Check homepage
		navToolBar.goToHomePage();
		//- an activity is added into activity stream
		waitForAndGetElement(ELEMENT_ACTIVITY_AUTHOR_SPACENAME.replace("${activityText}", spaceName));

		//- Informations displayed in the featured content are :
		//1. Space's avatar
		waitForAndGetElement(ELEMENT_AVATAR_SPACE_ACTIVITY.replace("${activityText}", spaceName));

		//2. Space's description
		waitForAndGetElement(ELEMENT_DESCRIPTION_SPACE_ACTIVITY.replace("${activityText}", spaceName).replace("${description}", spaceDesc));

		//3. Number of members.
		waitForAndGetElement(ELEMENT_NUMBER_MEMBER_SPACE_ACTIVITY.replace("${activityText}", spaceName).replace("${numbermember}", "1"));

		/*Step 1: - Rename space*/ 
		//- Connect to Intranet and goto space
		//- Click [setting] to edit space
		//- In the setting form, rename space
		magMember.goToMySpacePage();
		magMember.editSpace(spaceName, newSpaceName, "", false, "");

		//- Back to the Homepage
		navToolBar.goToHomePage();

		//- The Space activity is updated in the activity stream withy the new title
		waitForAndGetElement(ELEMENT_AVATAR_SPACE_ACTIVITY.replace("${activityText}", newSpaceName));

		//- A comment is added: Name has been updated to: $value.
	
	waitForAndGetElement(activity.ELEMENT_CONTENT_COMMENT_RENAME_SPACE.replace("${spacename}", newSpaceName));
		
		//Clear data
		info("clear data");
		magMember.goToAllSpaces();
		magMember.deleteSpace(newSpaceName,300000);
	}

	/**
	 * == User join a space ==
	 * Test case ID: 74790
	 * Step 1: - Create a new Space
	 * Step 2: - Invite other user
	 * Step 3: - User B join space
	 */
	@Test
	public void test12_UserJoinASpace(){
		//Declare variable
		String spaceName = "space74790";
		String spaceDesc = "spacedes74790";

		/*Step 1: - Create a new Space*/
		//- Login with user A and Goto homepage
		//- Click [Join a space] on left Navigation
		//- Click [Add new space] button
		//- Fill the information and click [create] to create a space
		info("-- Create space --");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, spaceDesc);

		//- Check homepage
		navToolBar.goToHomePage();
		//- an activity is added into activity stream
		waitForAndGetElement(ELEMENT_ACTIVITY_AUTHOR_SPACENAME.replace("${activityText}", spaceName));

		//- Informations displayed in the featured content are :
		//1. Space's avatar
		waitForAndGetElement(ELEMENT_AVATAR_SPACE_ACTIVITY.replace("${activityText}", spaceName));

		//2. Space's description
		waitForAndGetElement(ELEMENT_DESCRIPTION_SPACE_ACTIVITY.replace("${activityText}", spaceName).replace("${description}", spaceDesc));

		//3. Number of members.
		waitForAndGetElement(ELEMENT_NUMBER_MEMBER_SPACE_ACTIVITY.replace("${activityText}", spaceName).replace("${numbermember}", "1"));

		/*Step 2: - Invite other user*/
		//- goto Space setting -> member 
		//- Click [select user] icon and select  user B  to invite
		//- Click [Invite] Icon
		//- User is added into the table below and status in [Actions] column is [Cancel request]
		magMember.managerInviteUserToJoinSpace(userType.ADMIN,spaceName,userType.PUBLISHER,false);

		/*Step 3: - User B join space*/
		//- Log in as user B
		//- Click [Join a space]
		//- Click [accept] to join the space
		magMember.userAcceptInvitationToJoinSpace(true,userType.PUBLISHER,spaceName);

		//- Check homepage
		navToolBar.goToHomePage();

		//- A comment is added into activity
		//- Message: "Has joined the space." is shown
		waitForAndGetElement(activity.ELEMENT_CONTENT_COMMENT_USER_JOIN_SPACE.replace("${spacename}", spaceName).replace("${username}", user1));

		//Clear data
		info("clear data");
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * == Update Profile - change of avatar ==
	 * Test case ID: 74794
	 * Step 1: - Change Avatar
	 */
	@Test
	public void test13_UpdateProfileChangeOfAvatar(){
		//Declare variable
		String file = "ECMS_DMS_SE_Upload_imgfile.jpg";

		/*Step 1: - Change Avatar*/ 
		//- Connect to Intranet
		//- Click username on Top Navitgation -> My Profile
		navToolBar.goToMyProfile();
		//- Click [Change the avatar] and upload new avatar
		peoPro.changeAvatar("TestData/"+file);

		//- Check homepage
		navToolBar.goToHomePage();

		//- A user profile activity is updated in the activity stream
		//- A comment is added: Avatar has been updated.
		waitForAndGetElement(activity.ELEMENT_CONTENT_COMMENT_USER_CHANGE_AVATAR.replace("${username}", user));
	}

	/**
	 * == Update Profile - Update Basic information ==
	 * Test case ID: 74795
	 * Step 1: - Change Avatar
	 */
	@Test
	public void test14_UpdateProfileUpdateBasicInformation(){
		//Declare variable
		String firstName="John update";
		String lastName = "Smith update";
		String email = "john1.smith1@acme.exoplatform.com";
		String oldFirstnName = "John";
		String oldLastName = "Smith";
		String oldEmail = "john.smith@acme.exoplatform.com";

		/*Step 1: - Change Avatar*/ 
		//- Connect to Intranet
		//- Click username on Top Navitgation -> My Profile
		navToolBar.goToMyProfile();

		//- Click Edit to edit basic information
		peoPro.editUserBasicInformation(firstName, lastName, email);

		//- Check homepage
		navToolBar.goToHomePage();

		//- A user profile activity is updated in the activity stream
		String emailactivity = waitForAndGetElement(activity.ELEMENT_ACTIVITY_AUTHOR_NAME.replace("${index}", "1").replace("${author}", firstName+" "+lastName)+"/../../..//p[@class='userEmail']").getText();
		assert emailactivity.contains(email);

		//- A comment is added: 	Basic informations has been updated.
		waitForAndGetElement(activity.ELEMENT_CONTENT_COMMENT_USER_EDIT_BASIC_INFO.replace("${username}", firstName+" "+lastName));

		//Clear data
		info("clear data");
		navToolBar.goToMyProfile();
		peoPro.editUserBasicInformation(oldFirstnName, oldLastName, oldEmail);
	}
}
