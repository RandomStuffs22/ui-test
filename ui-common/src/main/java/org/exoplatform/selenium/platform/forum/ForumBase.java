package org.exoplatform.selenium.platform.forum;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.PlatformPermission;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Migrate from plf3.5
 * @author lientm
 * @date 19 Aug 2013
 */

public class ForumBase extends PlatformBase {

	public PageEditor pageE ;
	public NavigationToolbar navTool ;
	public PlatformPermission per ;

	public final By ELEMENT_FORUM_LINK = By.linkText("Forums");
	public final By ELEMENT_COMMUNITY_FORUM_LINK = By.xpath("//span[@data-original-title='Support Forums']");
	public final By ELEMENT_OK_INFOR_POPUP = By.xpath("//div[@class='UIPopupWindow UIDragObject uiPopup']/.//a[text()='OK']");
	public final By ELEMENT_OK_DELETE = By.xpath("//*[@id='UIForumPopupConfirmation']//*[text()='OK']");
	public final By ELEMENT_CANCEL_DELETE = By.xpath("//*[@id='UIForumPopupConfirmation']//*[text()='Cancel']");

	//-----------------Forum Home screen--------------------------------------------
	public final By ELEMENT_ADD_CATEGORY = By.linkText("Add Category");
	public final By ELEMENT_FORUM_STATE = By.id("UIForumIconState");
	public final By ELEMENT_WHAT_GOING_ON = By.xpath("//div[contains(text(),'Going on?')]");
	public final By ELEMENT_ADD_FORUM = By.linkText("Add Forum");
	public final String ELEMENT_BREAD_FORUM = "//li[text()='${forum}']";
	public final By ELEMENT_ALERT = By.xpath("//div[@class='UIPopupWindow UIDragObject ExoMessageDecorator']");
	public final By ELEMENT_INFO = By.xpath("//span[@class='PopupIcon InfoMessageIcon']");
	public final By ELEMENT_JUMP_TO = By.id("forumLink");
	public final By ELEMENT_GO_BUTTON = By.linkText("Go");
	public final By ELEMENT_MODERATION = By.className("uiIconForumModerator");
	public final By ELEMENT_MODERTATION_DELETE_BUTTON = By.xpath("//*[@id='ModerationMenu']//a[contains(text(),'Delete')]");
	public final By ELEMENT_WATCH = By.xpath("//*[@class='actionIcon' and contains(@href, 'AddWatching')]");
	public final By ELEMENT_UNWATCH = By.xpath("//*[@class='actionIcon' and contains(@href, 'UnWatch')]");
	public final By ELEMENT_BOOKMARKS = By.xpath("//a[@class='actionIcon']//i[@class='uiIconBookmark uiIconLightGray']");
	public final By ELEMENT_EXPORT_CATEGORY = By.xpath("//*[@id='Administrations']//*[@class='uiIconExport']");
	public final By ELEMENT_EXPORT_FORUM = By.linkText("Export Forum");
	public final By ELEMENT_LEGEN_PANEL = By.id("UIForumIconState");
	public final By ELEMENT_MODERATOR_PANEL = By.id("uicomponent.id");
	public final By ELEMENT_STATISTIC_PANEL = By.id("UICategoryInfo");//By.xpath("//*[text()='Forums Statistics']");
	public final By ELEMENT_HOME_BUTTON = By.xpath("//*[@id='UIBreadcumbs']//*[text()='Home']");
	public final String ELEMENT_HOME_FORUM = "Forum Home";
	public final By ELEMENT_USER_MANAGEMENT = By.xpath("//*[@id='ManageModerator']//*[@class='uiIconUser uiIconLightGray']");
	public final By ELEMENT_MORE_BUTTON = By.xpath("//li[@class='dropdown moreItem pull-right']");
	public final By ELEMENT_PENDING = By.id("PendingJob");

	public String ELEMENT_FORUM_BREADCUMB = "//*[@id='UIBreadcumbs']/div[@class='pull-left']//a[@class='Selected' and contains(text(),'${forumName}')]";
	public String ELEMENT_LATEST_TOPIC = "//a[contains(text(),'${forum}')]/ancestor::tr[@class='oncontextmenu']//a[contains(text(),'${topic}')]";
	public String ELEMENT_FORUM_ON_HOMEPAGE = "//a[contains(text(),'${category}')]/ancestor::div[@id='UICategories']//tr[@class='oncontextmenu']//a[contains(text(),'${forum}')]";

	//Administration menu
	//	public final By ELEMENT_ADMINISTRATION = By.className("uiIconForumAdmin");
	//	public final By ELEMENT_SORT_SETTING = By.linkText("Sort Settings");
	//	public final By ELEMENT_CENSOR_KEYWORDS = By.linkText("Censor Keywords");
	//	public final By ELEMENT_NOTIFICATIONS= By.linkText("Notifications");
	//	public final By ELEMENT_BAN_IP = By.linkText("Banned IPs");		
	//	public final By ELEMENT_BBCODE = By.linkText("BBCodes");
	//	public final By ELEMENT_PRUNE = By.linkText("Pruning");
	//	public final By ELEMENT_IMPORT = By.linkText("Import");
	//	public final By ELEMENT_EXPORT = By.linkText("Export");

	public final By ELEMENT_ADMINISTRATION = By.xpath("//*[@id='Administrations']//*[@class='uiIconForumAdmin uiIconForumLightGray']");
	public final By ELEMENT_SORT_SETTING = By.xpath("//span[text()='Sort Settings']");
	public final By ELEMENT_CENSOR_KEYWORDS = By.xpath("//*[@id='Administrations']//*[@class='uiIconForumCensor']");
	public final By ELEMENT_BAN_IP = By.xpath("//*[@id='Administrations']//*[@class='uiIconForumBanIp']");
	public final By ELEMENT_BBCODE = By.xpath("//*[@id='Administrations']//*[@class='uiIconForumBBCode']");
	public final By ELEMENT_PRUNE = By.xpath("//*[@id='Administrations']//*[@class='Pruning']");
	public final By ELEMENT_IMPORT = By.xpath("//*[@id='Administrations']//*[@class='uiIconImport']");

	//-----------------Watch/Unwatch screen-------------------------------------------
	public final String MESSAGE_WATCH = "You are now watching this item.";

	public final String MESSAGE_UNWATCH = "You are no longer watching this item.";	
	public static String REGISTER_MAIL_CONTENT = "Hi, you received this email because you registered for the Forum and Topic Watching notification.";

	//----------------Book Marks form------------------------------------------
	public final By ELEMENT_BOOKMARKS_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='My Bookmarks']");
	public final String ELEMENT_BOOKMARKS_OPTION = ".//a[text()='${item}']/..//*[@class='bookmark']";
	public final String ELEMENT_BOOKMARKS_ITEM = "//form[@id='UIShowBookMarkForm']//a[@class='actionOpenLink' and text()='${item}']";
	public final String ELEMENT_BOOKMARKS_REMOVE = "//a[@class='ActionLink' and text()='${item}']/../../*//div[@class='DeleteIcon']";
	public final By ELEMENT_BOOKMARKS_CANCEL_BUTTON = By.xpath("//form[@id='UIShowBookMarkForm']//*[text()='Cancel']");

	//-----------------simple search screen-----------------------------------
	public final By ELEMENT_SIMPLE_SEARCH_TEXTBOX = By.id("inputValue");
	public final By ELEMENT_SIMPLE_SEARCH_BUTTON = By.xpath("//a[@class='SearchLink SearchForumIcon']");
	public final By ELEMENT_SIMPLE_SEARCH_FORM = By.id("UIForumListSearch");
	public final String VERIFY_MESSAGE_SEARCH = "No matches.";

	//-----------------Advanced Search form--------------------------------
	public final By ELEMENT_ADVANCED_SEARCH_ICON = By.xpath("//button[text()='Advanced Search']");
	public final By ELEMENT_ADVANCED_SEARCH_FORM = By.id("UISearchForm");
	public final By ELEMENT_ADVANCED_SEARCH_TERMS = By.id("SearchValue");
	public final By ELEMENT_ADVANCED_SEARCH_IN = By.name("SearchType");
	public final By ELEMENT_ADVANCED_SEARCH_SCOPE_FULL = By.xpath("//input[@value='entire']");
	public final By ELEMENT_ADVANCED_SEARCH_SCOPE_TITLES = By.xpath("//input[@value='title']");
	public final By ELEMENT_ADVANCED_SEARCH_USER = By.id("SearchUser");
	public final By ELEMENT_ADVANCED_SEARCH_CREATED_FROM = By.id("FromDateCreated");
	public final By ELEMENT_ADVANCED_SEARCH_CREATED_TO = By.id("ToDateCreated");
	public final By ELEMENT_ADVANCED_SEARCH_MODERATOR = By.id("Moderator");
	public final By ELEMENT_ADVANCED_SEARCH_BUTTON = By.xpath("//*[@id='UISearchForm']//*[text()='Search']");
	public final By ELEMENT_ADVANCED_SEARCH_STATUS_LOCKED = By.id("IsLock");
	public final By ELEMENT_ADVANCED_SEARCH_STATUS_UNLOCKED = By.id("IsUnLock");
	public final By ELEMENT_ADVANCED_SEARCH_STATE_OPEN = By.id("IsOpen");
	public final By ELEMENT_ADVANCED_SEARCH_STATE_CLOSED = By.id("IsClosed");
	public final By ELEMENT_ADVANCED_SEARCH_LAST_POST_FROM = By.id("FromDateCreatedLastPost");
	public final By ELEMENT_ADVANCED_SEARCH_LAST_POST_TO = By.id("ToDateCreatedLastPost");

	//-----------------Sort setting form-----------------------------------
	public final By ELEMENT_SORT_SETTING_POPUP = By.xpath("//span[@class='PopupTitle' and text()='Sort Settings']");
	public final By ELEMENT_SORT_FORUM_BY = By.id("forumSortBy");
	public final By ELEMENT_SORT_FORUM_DIRECTION = By.id("forumSortByType");
	public final By ELEMENT_SORT_TOPIC_BY = By.id("topicSortBy");
	public final By ELEMENT_SORT_TOPIC_DIRECTION = By.id("topicSortByType");

	//----------------Set Censor keywords form----------------------------------
	public final By ELEMENT_CENSOR_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Censor Keywords']");
	public final By ELEMENT_CENSOR_POPUP_PLF4_1 = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Censor Keywords']");
	public final By ELEMENT_CENSORED_KEYWORDS =  By.id("censorKeyword");

	//----------------Set Ban IP form--------------------------------------------
	public final By ELEMENT_BAN_IP_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Banned IPs']");
	public final By ELEMENT_BAN_IP_FILTER = By.id("searchIpBan");
	public final By ELEMENT_BAN_IP_ADD = By.linkText("Add");
	public final String ELEMENT_BAN_IP_ADDRESS = "newIpBan${No}";
	public final String ELEMENT_BAN_IP_DELETE = "//*[contains(@data-action, '${ip}')  and @data-original-title='Delete']";
	public final String MSG_DELETE_BAN_IP = "This IP will no longer be banned. Do you confirm ?";
	public final By ELEMENT_START_TOPIC_DISABLE = By.xpath("//*[@id='UITopicContainer']//*[@data-original-title='Forum is closed for posting.']");
	public final By ELEMENT_POST_DISABLE = By.xpath("//*[@data-original-title='You cannot reply to this topic.' and text()='Post Reply']");
	public final String MSG_BLOCK_CREATE_TOPIC = "You cannot create topics.";
	public final String MSG_BLOCK_POST = "You cannot post replies.";
	public final String MSG_BLOCK_POST_ATTACHMENT = "You cannot post attachments.";
	public final String MSG_BLOCK_EDIT_YOUR_POST = "You cannot edit your posts.";	

	//----------------Set BB Code form-------------------------------------
	public final By ELEMENT_BBCODE_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='BBCode Manager']");
	public final String ELEMENT_BBCODE_ACTIVE_NO_OPTION = "//input[@type='checkbox' and @id='${tag}']";
	public final String ELEMENT_BBCODE_ACTIVE_HAVE_OPTION = "//input[@type='checkbox' and contains(@id,'${tag}" + "opt" + "')]";
	public final By ELEMENT_BBCODE_ADD_BUTTON = By.xpath("//button[text()='Add BBCode']");
	public final By ELEMENT_BBCODE_SAVE_BUTTON = By.xpath("//button[text()='Save']");
	public final By ELEMENT_BBCODE_RESET_BUTTON = By.xpath("//button[text()='Reset']");
	public final By ELEMENT_BBCODE_CLOSE_BUTTON = By.xpath("//*[@id='BBCodeManagerForm']//button[text()='Close']");
	public final String ELEMENT_BBCODE_EDIT_ICON = "//*[contains(@href,'${tag}') and @data-original-title='Edit BBCode']";
	public final String ELEMENT_BBCODE_DELETE_ICON = "//*[contains(@data-action,'${tag}') and @data-original-title='Delete BBCode']";
	public final By ELEMENT_BBCODE_DELETE_MESSAGE = By.xpath("//span[contains(text(),'Are you sure you want to delete this BB Code ?')]");

	public final By ELEMENT_BBCODE_ADD_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and contains(text(),'Add BBCode')]");
	public final By ELEMENT_BBCODE_EDIT_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Edit BBCode']");
	public final By ELEMENT_BBCODE_TAG	= By.id("TagName");
	public final By ELEMENT_BBCODE_REPLACEMENT = By.id("Replacement");
	public final By ELEMENT_BBCODE_DESCRIPTION = By.id("Description");
	public final By ELEMENT_BBCODE_EXAMPLE = By.id("Example");
	public final By ELEMENT_BBCODE_OPTION = By.id("UseOption");
	public final By ELEMENT_BBCODE_ALERT_POPUP = By.className("UIPopupWindow");

	public final String MSG_BBCODE_BLANK_FIELD = "The field ${field} is required.";

	//Action click code for add BBCode. 
	public enum ADDBBCODE_ACTION{
		ADDBBCODE,
		SAVE,
		RESET,
		CLOSE
	}

	//----------------------Prune management form---------------------------------
	public final By ELEMENT_PRUNE_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Auto Prune']");
	public final String ELEMENT_PRUNE_ACTIVE = "//td[text()='${category}']/../*//input[@type='checkbox']";
	public final String ELEMENT_PRUNE_ACTIVED = "//td[text()='${category}']/../*//input[@type='checkbox' and @checked='checked']";
	public final String ELEMENT_PRUNE_SETTING = "//td[text()='${category}']/../td[text()='{$forum}']/..//*[@data-original-title='Prune Settings']";
	public final String ELEMENT_PRUNE_RUN = "//td[text()='${category}']/../td[text()='{$forum}']/..//*[@data-original-title='Run']";


	public final By ELEMENT_PRUNE_SETTING_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Prune Settings']");
	public final By ELEMENT_PRUNE_ACTIVE_DAY = By.id("inActiveDay");
	public final By ELEMENT_PRUNE_ACTIVE_DAY_TYPE = By.name("inActiveDayType");
	public final By ELEMENT_PRUNE_JOB_DAY = By.id("jobDay");
	public final By ELEMENT_PRUNE_JOB_DAY_TYPE = By.name("jobDayType");
	public final String MSG_PRUNE_INVALID_INACTIVE_DAY = "Invalid number format in field \"Clear topics that have been inactive for\".";
	public final String MSG_PRUNE_INVALID_JOB_DAY = "Invalid number format in field \"Run prune job every\".";
	public final String MSG_PRUNE_NOT_CONFIG = "Please configure the prune settings for this item.";
	public final By ELEMENT_PRUNE_DRY_RUN = By.linkText("Dry Run");
	public final By ELEMENT_PRUNE_CLOSE_BUTTON = By.xpath("//form[@id='UIAutoPruneForm']//*[text()='Close']");

	public final By ELEMENT_PRUNE_INVALID_INACTIVE_DAY_OK = By.xpath("//span[contains(text(),'Clear topics that have been inactive for')]/../../..//*[text()='OK']");
	public final By ELEMENT_PRUNE_INVALID_RUN_JOB_OK = By.xpath("//span[contains(text(),'Run prune job every')]/../../..//*[text()='OK']");
	public final By ELEMENT_PRUNE_NOT_CONFIG_OK = By.xpath("//span[contains(text(),'Please configure the prune settings for this item.')]/../../..//*[text()='OK']");

	//--------------------Profile setting form------------------------------------
	public final By ELEMENT_SETTING = By.xpath("//*[@id='EditProfile']//*[@class='uiIconSetting uiIconLightGray']");
	public final By ELEMENT_SETTING_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Settings']");

	//Profile tab
	public final By ELEMENT_SCREEN_NAME = By.id("ScreenName");
	public final By ELEMENT_SIGNATURE = By.id("Signature");
	public final By ELEMENT_IS_DISPLAY_SIGNATURE = By.id("IsDisplaySignature");
	public final By ELEMENT_IS_DISPLAY_AVATAR = By.id("IsDisplayAvatar");
	public final By ELEMENT_WATCH_TOPIC_START = By.id("AutoWatchMyTopics");
	public final By ELEMENT_WATCH_TOPIC_POST =  By.id("AutoWatchTopicIPost");
	public final By ELEMENT_AVATAR_UPDATE_BUTTON = By.xpath("//*[@id='Avatar']//button");

	//Forum settings tab
	public final By ELEMENT_SETTING_FORUM_TAB = By.linkText("Forum Settings");
	public final By ELEMENT_SETTING_TIMEZONE = By.name("TimeZone");
	public final By ELEMENT_SETTING_SHORT_DATE_FORMAT = By.name("ShortDateformat");
	public final By ELEMENT_SETTTING_LONG_DATE_FORMAT = By.name("LongDateformat");
	public final By ELEMENT_SETTING_TIME_FORMAT = By.name("Timeformat");
	public final By ELEMENT_SETTTING_TOPIC_PER_PAGE = By.name("MaximumThreads");
	public final By ELEMENT_SETTING_POST_PER_PAGE = By.name("MaximumPosts");

	//My subcription tab
	public final By ELEMENT_SETTING_MYSCRIPTIONS_TAB = By.linkText("My Subscriptions");
	public final By ELEMENT_FEED_URL_TEXTBOX = By.id("RSSLink");
	public final String ELEMENT_SETTING_EMAIL_CHECKBOX = "//a[contains(text(), '${forum}')]/../../../*//input[contains(@id, 'EMAILforum')]"; 
	public final String ELEMENT_SETTING_MAIL_DELETE = "//a[contains(text(), '${forum}')]/../../../*//div[@class='DeleteIcon']";
	public final By ELEMENT_SETTING_EMAIL_ADDRESS = By.id("EmailAddress");
	public final String ELEMENT_CHECKBOX_EMAIL = "//*[contains(text(), '${name}')]/../..//input[contains(@id, 'EMAILforumCategory')]";
	public final By ELEMENT_SETTING_EMAIL_UPDATE = By.xpath("//button[text()='Update']");
	public final String ELEMENT_DELETE_WATCH = "//a[contains(text(),'${item}')]/../..//i[@class='uiIconDelete uiIconLightGray']";
	public final String ELEMENT_FEED_URL = "//a[contains(text(),'${item}')]/../..//input[contains(@id,'RSS')]";
	public final By ELEMENT_RSS_PAGE = By.id("feedSubscribeLine");
	public final By ELEMENT_FEED_TITLE_TEXT = By.id("feedTitleText");
	public final By ELEMENT_FEED_SUBTITLE_TEXT = By.id("feedSubtitleText");
	public final By ELEMENT_FEED_CONTENT = By.id("feedContent");
	public final String ELEMENT_MY_SUBSCRIPTION_OBJECT_WATCH = "//div[@id='UIForumUserSettingForm']//a[contains(text(),'${object}')]" ;
	public final String ELEMENT_RSS_TOPIC_LINK = "//xhtml:span[text()='${topic}']";

	public final By ELEMENT_MY_SUBSCRIPTION_NEXT_PAGE = By.xpath("//div[@id='ForumUserWatches-tab']//*[@data-original-title='Next Page']");
	public final By ELEMENT_MY_SUBSCRIPTION_TOTAL_PAGE = By.xpath("//div[@id='ForumUserWatches-tab']//span[@class='pagesTotalNumber']");

	//---------------------Notifications------------------
	public final By ELEMENT_NOTIFICATION_LINK = By.xpath("//*[@id='Administrations']//*[@class='uiIconNotification']"); 
	public final By ELEMENT_NOTIFY_FRAME=By.xpath("//*[@id='xEditingArea']/iframe");
	public final String ELEMENT_NOTIFY_TEXT= "New Posts Notification";
	public final By ELEMENT_NOTIFY_FRAME_UP=By.id("notifyEmail___Frame");
	public final By ELEMENT_NOTIFY_PREFIX = By.id("enableHeaderSubject");
	public final By ELEMENT_NOTIFY_SUBJECT = By.id("headerSubject");
	public final By ELEMENT_NOTIFY_RESET = By.xpath("//img[@title='Reset']");
	public final By MSG_NOTIFY_BLANK = By.xpath("//*[contains(text(),'The notification email is required.')]");
	public final By ELEMENT_NOTIFY_MOVE_FRAME_UP= By.id("notifyEmailMoved___Frame");
	public final By ELEMENT_NOTIFY_MOVE_TAB = By.xpath("//a[contains(text(),'Moved Notification')]");
	public final By ELEMENT_NOTIFY_MOVE_RESET = By.xpath("//div[@id='notifyEmailMoveTab']//img[@title='Reset']");

	//--------------------------Forum portlet setting form------------------------------------
	public final By ELEMENT_FORUM_PORTLET = By.xpath("//*[@class='CPortletLayoutDecorator' and contains(text(), 'Forum Portlet')]");
	public final By ELEMENT_FORUM_PORTLET_EDIT_ICON = By.xpath("//div[text()='Forum Portlet']/../a[@class='EditIcon']");
	public final By ELEMENT_FORUM_PORTLET_EDITMODE_TAB = By.linkText("Edit Mode");
	public final By ELEMENT_FORUM_PORTLET_PANEL_TAB = By.xpath("//*[contains(text(), 'Panels')]");
	public final By ELEMENT_FORUM_PORTLET_OPTIONS_TAB = By.xpath("//*[contains(text(), 'Options')]");
	public final By ELEMENT_SHOW_MODERATOR_CHECKBOX = By.id("isShowModerator");
	public final By ELEMENT_SHOW_POLL_CHECKBOX = By.id("IsShowPoll");
	public final By ELEMENT_SHOW_QUICK_REPLY_CHECKBOX = By.id("isShowQuickReply");
	public final By ELEMENT_SHOW_ICON_LEGEND_CHECKBOX = By.id("isShowIconsLegend");
	public final By ELEMENT_SHOW_RULE_CHECKBOX = By.id("isShowRules");
	public final By ELEMENT_SHOW_STATISTIC_CHECKBOX = By.id("isShowStatistic");
	public final By ELEMENT_USE_AJAX_CHECKBOX = By.id("isUseAjax");
	public final String ELEMENT_SELECT_DISPLAY_CHECKBOX = "//span[@class='uiCheckbox']//span[contains(text(), '${name}')]/..//input";
	//public final String ELEMENT_SELECT_DISPLAY_CHECKBOX = "//span[contains(text(), '${name}')]/..//input";
	public final By ELEMENT_FORUM_PORTLET_CLOSE_BUTTON = By.id("Close");
	public final By ELEMENT_FORUM_PORTLET_SAVE_BUTTON = By.cssSelector("#UISettingEditModeForm.UIForm div.uiAction button.btn");

	//attach file popup
	//	public final By ELEMENT_POPUP_UPLOAD_FILE = By.xpath("//span[@class='PopupTitle' and text()='Attach File']");
	public final By ELEMENT_POPUP_UPLOAD_FILE = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Attach File']");
	//	public final By ELEMENT_ATTACH_FILE = By.linkText("Attach Files");
	//	public final By ELEMENT_ATTACH_FILE =By.xpath("//button[@class='btn' and text()='Attach Files']");
	public final By ELEMENT_ATTACH_FILE =By.xpath("//*[@id='ThreadContent']/div/div[3]/div/button");
	public final By ELEMENT_ATTACHMENT_FILE_INPUT = By.name("file");
	//	public final By ELEMENT_ATTACHMENT_SAVE_BUTTON = By.xpath("//*[@id='UIAttachmentForm']//*[text()='Save']");
	public final By ELEMENT_ATTACHMENT_SAVE_BUTTON = By.xpath("//button[@class='btn' and text()='Save'");

	//-----------------------User Management--------------------------------------------//
	public final By ELEMENT_USER_MANAGEMENT_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='User Management']");
	public final String ELEMENT_USER_EDIT_ICON = "//*[text()='${user}']/..//*[contains(@href, 'EditProfile')]";
	public final String ELEMENT_VIEW_USER_PROFILE_ICON = "//*[text()='${user}']/..//*[@class='uiIconPreview uiIconLightGray']";
	public final By ELEMENT_SEARCH_USER_INPUT = By.id("SearchUser");
	public final By ELEMENT_USER_MANAGEMENT_PROFILE_TAB = By.linkText("Profile");
	public final By ELEMENT_USER_MANAGEMENT_TITLE_USER = By.id("ForumUserTitle");
	public final By ELEMENT_USER_MANAGEMENT_FORUM_ADMIN = By.id("isAdmin");
	public final By ELEMENT_ADD_MODERATOR_CATEGORY_ICON = By.xpath("//*[@id='ForumUserProfile']//*[@data-original-title='Select Categories']");
	public final By ELEMENT_ADD_MODERATOR_FORUM_ICON = By.xpath("//*[@id='ForumUserProfile']//*[@data-original-title='Select Forums']");
	public final String ELEMENT_CATEGORY_SELECT_CHECKBOX = "//*[contains(text(),'${cat}')]/..//*[@type='checkbox']";
	public final By ELEMENT_USER_MANAGEMENT_SETTING_TAB = By.xpath("//*[@id='UIModeratorManagementForm']//a[text()='Settings']");
	public final By ELEMENT_USER_MANAGEMENT_BAN_USER_TAB = By.linkText("Ban User");
	public final By ELEMENT_BAN_USER_CHECKBOX = By.id("IsBanned");
	public final By ELEMENT_BAN_USER_TIME = By.name("BanUntil");
	public final By ELEMENT_BAN_USER_REASON = By.id("BanReason");
	public final By ELEMENT_USER_MANAGEMENT_TOPIC_TAB = By.linkText("Topics");
	public final By ELEMENT_USER_MANAGEMENT_POST_TAB = By.linkText("Posts");
	public final String ELEMENT_USER_NUMBER_POST = "//*[@class='infoMember']/div[contains(text(),'Posts')]";
	public final By ELEMENT_CLOSE_USER_PROFILE_BUTTON = By.xpath("//*[@id='ViewUserProfile']//*[text()='Close']");

	//Private message
	public final By ELEMENT_PRIVATE_MESSAGE_ICON = By.xpath("//a[@class='actionIcon']/i[@class='uiIconMail uiIconLightGray']");
	public final By ELEMENT_COMPOSE_MESSAGE_TAB = By.linkText("Compose New Message");
	public final By ELEMENT_PRIVATE_MESSAGE_SENDTO_INPUT = By.id("SendTo");
	public final By ELEMENT_PRIVATE_MESSAGE_TITLE_INPUT = By.id("MailTitle");
	public final By ELEMENT_PRIVATE_MESSAGE_FRAME1 = By.id("MailMessage___Frame");
	public final By ELEMENT_POST_MESSAGE_FRAME_2 = By.xpath("//*[@id='xEditingArea']/iframe");
	public final By ELEMENT_PRIVATE_MESSAGE_FRAME_41 = By.xpath("//*[@id='cke_MailMessage']//iframe");
	public final By ELEMENT_PRIVATE_MESSAGE_SEND_BUTTON = By.xpath("//button[@class='btn' and text()='Send']");
	public final By ELEMENT_PRIVATE_MESSAGE_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Private Messages']");
	public final By ELEMENT_PRIVATE_MESSAGE_CANCEL_BUTTON = By.xpath("//form[@id='UIPrivateMessageForm']//button[text()='Cancel']");
	public final By ELEMENT_PRIVATE_MESSAGE_SENT_TAB = By.linkText("Sent Messages");
	public final By ELEMENT_PRIVATE_MESSAGE_COMPOSE_OK = By.xpath("//span[contains(text(),'Your message was sent successfully.')]/../../..//*[text()='OK']");
	public final By ELEMENT_PRIVATE_MESSAGE_DELETE_OK = By.xpath("//span[contains(text(),'Are you sure you want to delete this message ?')]/../../..//*[text()='OK']");

	public final String ELEMENT_PRIVATE_MESSAGE_FORWARD_ICON = "//*[text()='${message}']//ancestor::tr//i[@class='uiIconForumForward uiIconForumLightGray' and @data-original-title='Forward Message']";
	public final String ELEMENT_PRIVATE_MESSAGE_REPLY_ICON = "//*[text()='${message}']//ancestor::tr//i[@class='uiIconReply uiIconLightGray' and @data-original-title='Reply Message']";
	public final String ELEMENT_PRIVATE_MESSAGE_DELETE_ICON = "//*[text()='${message}']//ancestor::td/../td/a/i[@class='uiIconDelete uiIconLightGray' and @data-original-title='Delete Message']";
	public final String ELEMENT_PRIVATE_MESSAGE = "//form[@id='UIPrivateMessageForm']//td//*[text()='${message}']";
	public final String ELEMENT_PRIVATE_MESSAGE_CONTENT = "//div[@class='uiContentBox']//p[contains(text(),'${message}')]";

	public final String MSG_PRIVATE_MESSAGE_COMPOSE = "Your message was sent successfully.";
	public final String MSG_PRIVATE_MESSAGE_DELETE = "Are you sure you want to delete this message ?";

	//Gmail
	public String ELEMENT_GMAIL_EMAIL = "//span/b[text()='[${category}][${forum}] ${topic}']";

	//Pending job
	public final By ELEMENT_PENDING_JOB_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Waiting for Approval']");
	public By ELEMENT_APPROVE_PENDING_JOB = By.xpath("//*[text()='Approve']");
	public final By ELEMENT_CLOSE_PENDING_POPUP = By.className("ClosePopup");
	/*-----------------------------common function-------------------------------------*/

	/** function: input data to a frame in other frame
	 * @author lientm
	 * @param frame1
	 * @param frame2
	 * @param data
	 * @param validate = null: clear data before
	 *                 = true: clear data before
	 *                 = false: not clear data before
	 */
	public void inputDataToFrameInFrame(By frame1, By frame2, String data, boolean...validate){
		boolean valid = validate.length > 0 ? validate[0]: true;
		boolean verify = validate.length > 1 ? validate[1]: true;
		try {
			WebElement inputsummary = null;

			for (int repeat = 0;; repeat++) {
				if (repeat >= DEFAULT_TIMEOUT/WAIT_INTERVAL) {
					info("Fail to input data to frame");
					break;
				}
				driver.switchTo().frame(waitForAndGetElement(frame1));
				driver.switchTo().frame(waitForAndGetElement(frame2));
				inputsummary = driver.switchTo().activeElement();
				if (valid){
					((JavascriptExecutor) driver).executeScript("document.body.innerHTML='" + data + "'");
				}else {
					inputsummary.sendKeys(data); break;
				}
				if(verify){
					if (data.equals(inputsummary.getText())) 
						break;
				}else break;
				switchToParentWindow();
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			inputDataToFrameInFrame(frame1, frame2, data, validate);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			inputDataToFrameInFrame(frame1, frame2, data, validate);
		} finally {
			loopCount = 0;
		}
	}

	/** function: Attach file in attach popup
	 * @author lientm
	 * @param number: number of upload container that need upload file
	 * @param filePath: path to file upload
	 */
	public void attachFile(String filePath){
		String[] file = filePath.split("/");
		for (int i = 0; i < file.length; i ++){
			WebElement element = waitForAndGetElement(ELEMENT_ATTACHMENT_FILE_INPUT, DEFAULT_TIMEOUT, 1, 2);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", element);
			element.sendKeys(Utils.getAbsoluteFilePath("TestData/" + file[i]));
			waitForAndGetElement("//*[@class='fileNameLabel' and contains(text(),'" + file[i] + "')]");
		}
		//		click(ELEMENT_ATTACHMENT_SAVE_BUTTON);
		button.save();
		//		waitForElementNotPresent(ELEMENT_ATTACHMENT_SAVE_BUTTON);
	}

	public void goToForums(){
		info("--Go to Forums--");
		if(this.plfVersion.equals("com"))
			click(ELEMENT_COMMUNITY_FORUM_LINK);
		else
			click(ELEMENT_FORUM_LINK);
		waitForAndGetElement(ELEMENT_FORUM_STATE,DEFAULT_TIMEOUT,0);
	}

	public void goToForumHome(){
		info("---Go to Forum home---");
		click(ELEMENT_HOME_BUTTON);
		waitForElementNotPresent(ELEMENT_HOME_BUTTON);
	}

	public void watchItem(boolean watch){		
		if (watch){
			if (waitForAndGetElement(ELEMENT_WATCH, DEFAULT_TIMEOUT, 0) != null){
				info("Watch item");
				click(ELEMENT_WATCH);
				waitForMessage(MESSAGE_WATCH);
				click(ELEMENT_OK_INFOR_POPUP);
				waitForAndGetElement(ELEMENT_UNWATCH);
				info("Watch item successfully");
			}else {
				info("Not found watch link");
			}			
		} else {
			if (waitForAndGetElement(ELEMENT_UNWATCH, DEFAULT_TIMEOUT, 0) != null) {
				info("Unwatch item");
				click(ELEMENT_UNWATCH);
				waitForMessage(MESSAGE_UNWATCH);
				click(ELEMENT_OK_INFOR_POPUP);
				waitForAndGetElement(ELEMENT_WATCH);
				info("Unwatch item successfully");
			} else {
				info("Not found unwatch link");
			}
		}
	}

	public void addBookmarksItem(String item){
		By element_item = By.xpath(ELEMENT_BOOKMARKS_ITEM.replace("${item}", item));

		//set bookmark
		info("Add bookmark");
		waitForAndGetElement(By.linkText(item));
		for(int repeat=0;; repeat ++)
		{
			if (repeat >= ACTION_REPEAT) {
				Assert.fail("Cannot bookmark this item after " + ACTION_REPEAT + " tries");
			}
			rightClickOnElement(By.linkText(item));
			if (waitForAndGetElement(ELEMENT_BOOKMARKS_OPTION.replace("${item}", item)) != null) break;
			Utils.pause(WAIT_INTERVAL);
			info("Retry...[" + repeat + "]");
		}
		click(ELEMENT_BOOKMARKS_OPTION.replace("${item}", item));
		//check bookmarks display
		WebElement bookmark = waitForAndGetElement(ELEMENT_BOOKMARKS,DEFAULT_TIMEOUT,0);
		if (bookmark == null)
			click(ELEMENT_MORE_BUTTON);
		click(ELEMENT_BOOKMARKS);
		waitForAndGetElement(ELEMENT_BOOKMARKS_POPUP);
		waitForAndGetElement(element_item);

		click(ELEMENT_BOOKMARKS_CANCEL_BUTTON);
		info("Add bookmark successfully");
	}

	/** function: delete bookmark
	 * @author lientm
	 * @param item: name of bookmark
	 */
	public void deleteBookmarkItem(String item){
		By element_delete = By.xpath(ELEMENT_BOOKMARKS_REMOVE.replace("${item}", item));

		info("Delete bookmark");
		waitForAndGetElement(element_delete);
		click(element_delete);
		waitForElementNotPresent(element_delete);
		info("Delete bookmark successfully");
	}

	//-------------------------------Manage Search-----------------------------//

	/** function: simple search
	 * @author lientm
	 * @param key: key to search
	 */
	public void quickSearch(String key){
		info("Quick search with key " + key);
		type(ELEMENT_SIMPLE_SEARCH_TEXTBOX, key, true);
		String id = waitForAndGetElement(By.xpath("//*[@id='UIPage']/div/div")).getAttribute("id");
		((JavascriptExecutor)driver).executeScript("javascript:eXo.webui.UIForm.submitForm('" + id + "#QuickSearchForm','Search',true);");	
		waitForAndGetElement(ELEMENT_SIMPLE_SEARCH_FORM);
	}

	/** function: execute an advanced search
	 * @author lientm
	 * @param scope
	 * @param key
	 * @return = true: have result return
	 * 		   = false: not have result return
	 */
	public void advancedSearch(String searchIn, String key, boolean scope, String...opts){
		per = new PlatformPermission(driver);

		info("Do advance search");
		quickSearch("Advance search");
		click(ELEMENT_ADVANCED_SEARCH_ICON);
		waitForAndGetElement(ELEMENT_ADVANCED_SEARCH_FORM);

		if (searchIn != null){
			select(ELEMENT_ADVANCED_SEARCH_IN, searchIn);
		}
		if (key != null){
			type(ELEMENT_ADVANCED_SEARCH_TERMS, key, true);
		}	
		if (scope){
			click(ELEMENT_ADVANCED_SEARCH_SCOPE_FULL, 2);
		} else {
			click(ELEMENT_ADVANCED_SEARCH_SCOPE_TITLES, 2);
		}

		if (opts.length > 0){
			if (opts[0] != null){
				type(ELEMENT_ADVANCED_SEARCH_USER, opts[0], true);
			}
		}
		if (opts.length > 1){
			if (opts[1] != null){
				type(ELEMENT_ADVANCED_SEARCH_CREATED_FROM, opts[1], true);
			}
		}
		if (opts.length > 2){
			if (opts[2] != null){
				type(ELEMENT_ADVANCED_SEARCH_CREATED_TO, opts[2], true);
			}
		}
		if (opts.length > 3){
			if (opts[3] != null){
				type(ELEMENT_ADVANCED_SEARCH_MODERATOR, opts[3], true);
			}
		}
		click(ELEMENT_ADVANCED_SEARCH_BUTTON);
		waitForElementNotPresent(ELEMENT_ADVANCED_SEARCH_BUTTON);
	}

	//-----------------------------------Administration---------------------------------//
	/** function: setup a sort
	 * @author lientm
	 * @param forumBy: the way sort forum
	 * @param dirForum: direction of sort for forum
	 * @param topicBy: the way sort topic
	 * @param dirTopic: direction of sort for topic
	 */
	public void sortSetting(String forumBy, int dirForum, String topicBy, int dirTopic){
		button = new Button(driver);
		info("Setting sort");
		waitForAndGetElement(ELEMENT_ADMINISTRATION);
		click(ELEMENT_ADMINISTRATION);
		waitForAndGetElement(ELEMENT_SORT_SETTING);
		click(ELEMENT_SORT_SETTING);
		waitForAndGetElement(ELEMENT_SORT_SETTING_POPUP);
		if (forumBy != "" && forumBy != null){
			select(ELEMENT_SORT_FORUM_BY, forumBy);
		}
		if (dirForum == 1){
			select(ELEMENT_SORT_FORUM_DIRECTION, "Ascending");
		} else {
			select(ELEMENT_SORT_FORUM_DIRECTION, "Descending");
		}
		if (topicBy != "" && topicBy != null){
			select(ELEMENT_SORT_FORUM_BY, forumBy);
		}
		if (dirTopic == 1){
			select(ELEMENT_SORT_FORUM_DIRECTION, "Ascending");
		} else {
			select(ELEMENT_SORT_FORUM_DIRECTION, "Descending");
		}
		button.save();
		waitForElementNotPresent(ELEMENT_SORT_SETTING_POPUP);
		info("setting sort successfully");
	}

	/** function: set sensor keyword
	 * @author lientm
	 * @param key: key to setup
	 */
	public void setCensorKeywords(String key){
		button = new Button(driver);
		info("Set censor keywords " + key);
		waitForAndGetElement(ELEMENT_ADMINISTRATION);
		click(ELEMENT_ADMINISTRATION);
		waitForAndGetElement(ELEMENT_CENSOR_KEYWORDS);
		click(ELEMENT_CENSOR_KEYWORDS);
		if(this.plfVersion.equalsIgnoreCase("4.0"))
			waitForAndGetElement(ELEMENT_CENSOR_POPUP);
		else 
			waitForAndGetElement(ELEMENT_CENSOR_POPUP_PLF4_1);
		if (key != null){
			type(ELEMENT_CENSORED_KEYWORDS, key, true);
		}
		button.save();
		waitForElementNotPresent(ELEMENT_CENSOR_POPUP);
		info("Set censor keyword successfully");
	}

	/**
	 * function go to BanIP management
	 * @author lientm
	 */
	public void goToBanIp(){
		info("Go to BanIP Management");
		click(ELEMENT_ADMINISTRATION);
		click(ELEMENT_BAN_IP);
		waitForAndGetElement(ELEMENT_BAN_IP_POPUP);
	}

	/**function: set ban IP
	 * @author lientm
	 * @param ban: group of some IP address
	 */
	public void setBanIp(String... ban){
		button = new Button(driver);
		if (ban.length > 0){
			info("Set Ban Ip");
			goToBanIp();
			inputBanIP(ban);
			button.cancel();
		}
	}

	public void inputBanIP(String...ban){
		if (ban.length > 0){
			for (int i = 0; i < ban.length; i ++){ 
				String temp[] = ban[i].split("\\.");
				for (int j = 0; j < temp.length; j ++){
					type(By.id(ELEMENT_BAN_IP_ADDRESS.replace("${No}", Integer.toString(j + 1))), temp[j], true);
				}
				click(ELEMENT_BAN_IP_ADD);
				waitForAndGetElement(By.xpath("//*[text()='" + ban[i] + "']"));
			}
		}
	}

	/**
	 * function delete Ban Ip
	 * @param ip: ip to delete
	 */
	public void deleteBanIp(String ip){
		button = new Button(driver);
		By element_delete = By.xpath(ELEMENT_BAN_IP_DELETE.replace("${ip}", ip));

		goToBanIp();
		click(element_delete);
		waitForMessage(MSG_DELETE_BAN_IP);
		click(ELEMENT_OK_DELETE);
		waitForElementNotPresent(element_delete);
		button.cancel();
	}

	/** function: go to BB code management
	 * @author lientm
	 */
	public void goToBBCodeManagement(){
		info("Go to BB code management");
		if(waitForAndGetElement(ELEMENT_ADMINISTRATION,10000,0) == null)
			click(ELEMENT_MORE_BUTTON);
		click(ELEMENT_ADMINISTRATION);
		click(ELEMENT_BBCODE);
		waitForAndGetElement(ELEMENT_BBCODE_POPUP);
	}

	/**
	 * Act an action with BBCODE Window, such save, reset, close, add bbcode.
	 * Created by khanhnt at Nov 19, 2013
	 * @param action: ADDBBCODE or SAVE or RESET or CLOSE
	 */
	public void actionBBCode(ADDBBCODE_ACTION action){
		switch (action) {
		case ADDBBCODE:
			click(ELEMENT_BBCODE_ADD_BUTTON);
			break;
		case SAVE:
			click(ELEMENT_BBCODE_SAVE_BUTTON);
			break;
		case RESET :
			click(ELEMENT_BBCODE_REPLACEMENT);
			break;
		case CLOSE:
			click(ELEMENT_BBCODE_CLOSE_BUTTON);
			waitForElementNotPresent(ELEMENT_BBCODE_ADD_POPUP);
			break;
		default:
			break;
		}			
	}

	/** function: modify a BBCode
	 * @author lientm
	 * @param tag: tag of BBCode
	 * @param replace: htmp code that replaces the user-entered BBCode
	 * @param description: text to describe the BBCode tag
	 * @param example: an example for this particular BBCode
	 * @param option: choose option or not
	 */
	public void modifyBBcodeInfo(String tag, String replace, String description, String example, boolean option){
		button = new Button(driver);
		if (tag != null){
			type(ELEMENT_BBCODE_TAG, tag, true);
		}
		if (replace != null){
			type(ELEMENT_BBCODE_REPLACEMENT, replace, true);
		}
		if (description != null){
			type(ELEMENT_BBCODE_DESCRIPTION, description, true);
		}
		if (example != null){
			type(ELEMENT_BBCODE_EXAMPLE, example, true);
		}
		if (option){
			check(ELEMENT_BBCODE_OPTION, 2);
		}else {
			uncheck(ELEMENT_BBCODE_OPTION, 2);
		}
		button.save();
	}

	/** function: add a BBCode
	 * @author lientm
	 * @param tag: tag of BBCode
	 * @param replace: htmp code that replaces the user-entered BBCode
	 * @param description: text to describe the BBCode tag
	 * @param example: an example for this particular BBCode
	 * @param option: choose option or not
	 */
	public void addBBCode(String tag, String replace, String description,
			String example, boolean option, ADDBBCODE_ACTION... action){
		info("Add new BBcode");
		click(ELEMENT_BBCODE_ADD_BUTTON);
		waitForAndGetElement(ELEMENT_BBCODE_ADD_POPUP);
		if(action.length>0){
			type(ELEMENT_BBCODE_TAG, tag, true);
			type(ELEMENT_BBCODE_REPLACEMENT, replace, true);
			type(ELEMENT_BBCODE_DESCRIPTION, description, true);
			type(ELEMENT_BBCODE_EXAMPLE, example, true);
			if (option){
				check(ELEMENT_BBCODE_OPTION, 2);
			}else {
				uncheck(ELEMENT_BBCODE_OPTION, 2);
			}
			actionBBCode(action[0]);
			if(replace.equalsIgnoreCase("")){
				waitForMessage(MSG_BBCODE_BLANK_FIELD.replace("${field}", "\"Replacement\""));
			}

			if(example.equalsIgnoreCase("")){
				waitForMessage(MSG_BBCODE_BLANK_FIELD.replace("${field}", "\"Example\""));
			}

			if(tag.equalsIgnoreCase("")){
				waitForMessage(MSG_BBCODE_BLANK_FIELD.replace("${field}", "\"Tag\""));
			}

		}else{
			modifyBBcodeInfo(tag, replace, description, example, option);
			waitForElementNotPresent(ELEMENT_BBCODE_ADD_POPUP);
			//			waitForTextPresent(tag.toUpperCase());
			waitForAndGetElement("//*[contains(text(),'"+tag.toUpperCase()+"')]");
			waitForElementNotPresent(ELEMENT_ALERT);
			info("Add BBcode successfully");
		}			
	}

	/**
	 * Return String of Alert Window.
	 * Created by khanhnt at Nov 19, 2013
	 * @return
	 */
	public String getBBCodeAlert(){
		return waitForAndGetElement(By.className("warningIcon")).getText();

	}

	/** function: active/deactive a BBCode
	 * @author lientm
	 * @param tag: tag of BBCode
	 * @param active: set active for BBCode
	 * @param option = true: BBCode have option
	 * 				 = false: BBCode does not have option
	 */
	public void activeBBcode(String tag, boolean active, boolean option){
		By ELEMENT_ACTIVE_OPTION = By.xpath(ELEMENT_BBCODE_ACTIVE_HAVE_OPTION.replace("${tag}", tag));
		By ELEMENT_ACTIVE_NOT_OPTION = By.xpath(ELEMENT_BBCODE_ACTIVE_NO_OPTION.replace("${tag}", tag));
		button = new Button(driver);
		info("set active/deactive for BBcode");
		if (tag != null && tag != ""){
			if (option){				
				WebElement act = waitForAndGetElement(ELEMENT_ACTIVE_OPTION, 10000, 1,2);
				if ((active && act.isSelected() == false) || (!active && act.isSelected() == true)){
					if (option) {
						check(ELEMENT_ACTIVE_OPTION, 2);
					} else {
						uncheck(ELEMENT_ACTIVE_OPTION, 2);
					}	
				}
			}else {
				WebElement act_no = waitForAndGetElement(ELEMENT_ACTIVE_NOT_OPTION, 10000, 1,2);
				if ((active && act_no.isSelected() == false) || (!active && act_no.isSelected() == true)){
					if (option) {
						check(ELEMENT_ACTIVE_NOT_OPTION, 2);
					} else {
						uncheck(ELEMENT_ACTIVE_NOT_OPTION, 2);
					}	
				}
			}
			button.save();
			waitForElementNotPresent(ELEMENT_BBCODE_POPUP);
		}
	}

	/** function: edit a BBCode
	 * @author lientm
	 * @param tagold: old tag of BBCode
	 * @param tagnew: new tag of BBCode
	 * @param replace: htmp code that replaces the user-entered BBCode
	 * @param description: text to describe the BBCode tag
	 * @param example: an example for this particular BBCode
	 * @param option: choose option or not
	 */
	public void editBBcode(String tagold, String tagnew, String replace, String description, String example, boolean option){
		By ELEMENT_EDIT = By.xpath(ELEMENT_BBCODE_EDIT_ICON.replace("${tag}", tagold));

		info("Edit a BBcode have tag " + tagold);
		click(ELEMENT_EDIT);
		waitForAndGetElement(ELEMENT_BBCODE_EDIT_POPUP);
		modifyBBcodeInfo(tagnew, replace, description, example, option);
		waitForElementNotPresent(ELEMENT_BBCODE_EDIT_POPUP);
		waitForElementNotPresent(ELEMENT_ALERT);
		waitForAndGetElement("//label[contains(text(),'"+tagnew.toUpperCase()+"')]");
		//		waitForTextPresent(tagnew.toUpperCase());
		info("Edit BBcode successfully");
	}

	/**function: delete a BBCode
	 * @author lientm
	 * @param tag
	 * @param option = true: BBCode have option
	 * 				 = false: BBCode does not have option
	 */
	public void deleteBBcode(String tag, boolean...isDelete){
		By ELEMENT_DELETE = By.xpath(ELEMENT_BBCODE_DELETE_ICON.replace("${tag}", tag.toUpperCase()));

		alert = new ManageAlert(driver);
		info("Delete a BBcode have tag " + tag);
		click(ELEMENT_DELETE);
		waitForAndGetElement(ELEMENT_BBCODE_DELETE_MESSAGE);

		boolean tryOthers = isDelete.length>0 ? isDelete[0]:true;
		if (tryOthers){

			click(ELEMENT_OK_DELETE);
			waitForElementNotPresent(ELEMENT_DELETE);
			info("Delete BBcode successfully");
		}else{
			click(ELEMENT_CANCEL_DELETE);
			waitForElementNotPresent(ELEMENT_CANCEL_DELETE);
		}
	}

	/** function: go to Prune Management
	 * @author lientm
	 */
	public void goToPruneManagement(){
		info("Go to prune management");
		waitForAndGetElement(ELEMENT_ADMINISTRATION);
		click(ELEMENT_ADMINISTRATION);
		waitForAndGetElement(ELEMENT_PRUNE);
		click(ELEMENT_PRUNE);
		waitForAndGetElement(ELEMENT_PRUNE_POPUP);
	}

	/** function: set a Prune
	 * @author lientm
	 * @param category: title of category
	 * @param forum: title of forum
	 * @param activeDay: number of day active
	 * @param dayType: type 
	 * @param jobDay: number of job day
	 * @param jobDayType: type
	 */
	public void pruneSetting(String category, String forum, String activeDay, String dayType, String jobDay, String jobDayType){
		By ELEMENT_SETTING = By.xpath(ELEMENT_PRUNE_SETTING.replace("${category}", category).replace("{$forum}",forum));

		button = new Button(driver);
		if (category != null && category != ""){
			info("Setting prune for category " + category);
			waitForAndGetElement(ELEMENT_SETTING);
			click(ELEMENT_SETTING);
			waitForAndGetElement(ELEMENT_PRUNE_SETTING_POPUP);
			if (activeDay != "" && activeDay != null){
				type(ELEMENT_PRUNE_ACTIVE_DAY, activeDay, true);
				select(ELEMENT_PRUNE_ACTIVE_DAY_TYPE, dayType);
			}
			if (jobDay != "" && jobDay != null){
				type(ELEMENT_PRUNE_JOB_DAY, jobDay, true);
				select(ELEMENT_PRUNE_JOB_DAY_TYPE, jobDayType);
			}
			button.save();

		}
	}

	/** function: active a prune
	 * @author lientm
	 * @param category: title of category
	 * @param active = true: active
	 * 				 = false: deactive
	 */
	public void activePrune(String category, boolean active){
		By ELEMENT_ACTIVE = By.xpath(ELEMENT_PRUNE_ACTIVE.replace("${category}", category));
		By ELEMENT_ACTIVED = By.xpath(ELEMENT_PRUNE_ACTIVED.replace("${category}", category));

		WebElement act = waitForAndGetElement(ELEMENT_ACTIVE);
		if (active && act.isSelected() == false){
			info("Active prune for category " + category);
			click(ELEMENT_ACTIVE);
			waitForAndGetElement(ELEMENT_ACTIVED);
		}
		if (active == false && act.isSelected() == true){
			info("Deactive prune for category " + category);
			click(ELEMENT_ACTIVE);
			waitForElementNotPresent(ELEMENT_ACTIVED);
		}	
	}

	/**
	 * Go to Notifications
	 * @author thuntn
	 */
	public void goToNotifications(){
		info("--Go to notifications--");

		click(ELEMENT_ADMINISTRATION);
		click(ELEMENT_NOTIFICATION_LINK);
		waitForTextPresent(ELEMENT_NOTIFY_TEXT);
	}

	/**Change content of notifications
	 * @author thuntn
	 * @param prefix: = true if check Add a prefix to notification field
	 * 				  = false if else
	 * @param subject: text to add to Subject notification field
	 * @param content: text to add to content field
	 */
	public void changeNotifications(boolean prefix,String subject, String content){

		info("--Change content of notifications--");
		button = new Button(driver);
		goToNotifications();
		if(prefix)
			check(ELEMENT_NOTIFY_PREFIX,2);
		else
			uncheck(ELEMENT_NOTIFY_PREFIX,2);
		if (subject != null){
			type(ELEMENT_NOTIFY_SUBJECT,subject,true);
		}

		inputDataToFrameInFrame( ELEMENT_NOTIFY_FRAME_UP, ELEMENT_NOTIFY_FRAME,content,true);
		switchToParentWindow();
		button.save();
	}

	/** Reset Notification as default
	 * @author thuntn
	 */
	public void resetNotification(){

		info("--Reset Notification--");
		button = new Button(driver);
		goToNotifications();
		type(ELEMENT_NOTIFY_SUBJECT,"[$CATEGORY][$FORUM] $TOPIC",true);
		click(ELEMENT_NOTIFY_RESET);
		button.save();
	}

	/** Function compare 2 paragraphs (compare in specific line)
	 * @author lientm
	 * @param temp: paragraphs 1
	 * @param content: paragraphs 2
	 */
	public void compareString(String[] temp, String[] content){
		for (int i = 0; i < content.length; i ++){
			info("String1: " + temp[i] + "--- String2: " + content[i]);
			assert temp[i].contains(content[i]): "Fail! 2 Strings are different from each other"; 
		}
	}
	/**Remove empty element of array 
	 * @author thuntn
	 * @param arr
	 * @return
	 */
	public String[] removeEmptyElementOfArray(String[] arr){
		String[] temp = new String[arr.length];
		int len = 0;

		for(int i = 0; i < arr.length; i++){
			if (arr[i] != ""){
				temp[len] = arr[i];
				len ++;
			}
		}
		info("length of ..." + temp.length);
		return temp;
	}

	/**function check fomat of content mail
	 * @author lientm
	 * @param contentMail: content of mail (has '/' to separate paragraphs into lines)
	 */
	public void checkContentMail(String contentMail){
		String[] content = contentMail.split("/");
		if(waitForAndGetElement(ELEMENT_MAIL_CONTENT,DEFAULT_TIMEOUT,0) == null)
			click(ELEMENT_FIRST_MAIL);
		String[] temp = getText(ELEMENT_MAIL_CONTENT).split(System.getProperty("line.separator"));

		compareString(removeEmptyElementOfArray(temp), removeEmptyElementOfArray(content));
		info("Email content is true");
	}

	//------------------------------------------Settings----------------------------------//
	/**
	 * function go to setting form
	 * @author lientm
	 */
	public void goToSetting(){
		info("---Go to settings---");
		if(waitForAndGetElement(ELEMENT_SETTING,DEFAULT_TIMEOUT,0) == null){
			click(ELEMENT_MORE_BUTTON);
		}
		click(ELEMENT_SETTING);
		waitForAndGetElement(ELEMENT_SETTING_POPUP);
	}

	/**
	 * function setting User profile
	 * @author lientm
	 * @param screenName: data input to Screen Name
	 * @param sign: data input to Signature
	 * @param opt: option check checkbox group: display signature, display avatar, watch to topic start, watch topic post
	 */
	public void settingProfileUser(String screenName, String sign, boolean...opt){
		button = new Button(driver);
		goToSetting();
		if (screenName != null){
			type(ELEMENT_SCREEN_NAME, screenName, true);
		}
		if (sign != null){
			type(ELEMENT_SIGNATURE, sign, true);
		}
		if (opt.length > 0){
			if (opt[0]){
				check(ELEMENT_IS_DISPLAY_SIGNATURE, 2);			
			}else {
				uncheck(ELEMENT_IS_DISPLAY_SIGNATURE, 2);
			}
		}
		if (opt.length > 1){
			if (opt[1]){
				check(ELEMENT_IS_DISPLAY_AVATAR, 2);

			} else {
				uncheck(ELEMENT_IS_DISPLAY_AVATAR, 2);
			}
		}
		if (opt.length > 2){
			if (opt[2]){
				check(ELEMENT_WATCH_TOPIC_START, 2);
			} else {
				uncheck(ELEMENT_WATCH_TOPIC_START, 2);
			}
		}
		if (opt.length > 3){
			if (opt[3]){
				check(ELEMENT_WATCH_TOPIC_POST, 2);
			}else {
				uncheck(ELEMENT_WATCH_TOPIC_POST, 2);
			}
		}
		button.save();
		waitForElementNotPresent(ELEMENT_SETTING_POPUP);
	}

	public void forumsSettings(String...opts){
		info("Settings for forum");
		if (waitForAndGetElement(ELEMENT_SETTING_FORUM_TAB, 5000, 0) != null){
			click(ELEMENT_SETTING_FORUM_TAB);
		}else {
			click(ELEMENT_USER_MANAGEMENT_SETTING_TAB);
		}
		if (opts.length > 0){
			if (opts[0] != null){
				selectOption(ELEMENT_SETTING_TIMEZONE, opts[0]);
			}
		}
		if (opts.length > 1){
			if (opts[1] != null){
				selectOption(ELEMENT_SETTING_SHORT_DATE_FORMAT, opts[1]);
			}
		}
		if (opts.length > 2){
			if (opts[2] != null){
				selectOption(ELEMENT_SETTTING_LONG_DATE_FORMAT, opts[2]);
			}
		}
		if (opts.length > 3){
			if (opts[3] != null){
				select(ELEMENT_SETTING_TIME_FORMAT, opts[3]);
			}
		}
		if (opts.length > 4){
			if (opts[4] != null){
				select(ELEMENT_SETTTING_TOPIC_PER_PAGE, opts[4]);
			}
		}
		if (opts.length > 5){
			if (opts[5] != null){
				select(ELEMENT_SETTING_POST_PER_PAGE, opts[5]);
			}
		}
		String save = opts.length > 6 ? opts[6] : "true";
		if (Boolean.valueOf(save)){
			button.save();
			waitForElementNotPresent(ELEMENT_SETTING_POPUP);
		}
	}

	/**function go to RSS for any item (by rightclick on item -> choose RSS)
	 * @author lientm
	 * @param itemName: text of item that need view RSS
	 */
	public void goToRSS(String itemName){
		By rss = By.xpath("//a[text()='" + itemName + "']/..//a[text()='RSS']");

		rightClickOnElement(By.linkText(itemName));
		click(rss);
		Utils.pause(2000);
	}

	/**
	 * @author thaopth
	 * Date: 26 Dec 2012
	 */
	public void goToMySubscriptions() {
		goToSetting();
		click(ELEMENT_SETTING_MYSCRIPTIONS_TAB);
		waitForAndGetElement(ELEMENT_SETTING_EMAIL_ADDRESS);
	}

	public void deleteWatches (String item) {
		alert = new ManageAlert(driver);
		click(ELEMENT_DELETE_WATCH.replace("${item}", item));
		alert.waitForConfirmation("Are you sure you want to delete this subscription?");
		waitForElementNotPresent(ELEMENT_DELETE_WATCH.replace("${item}", item));

	}

	public void settingMailForUser(String...email){
		button = new Button(driver);
		String user = email.length > 0 ? email[0] : EMAIL_ADDRESS1;
		if(waitForAndGetElement(ELEMENT_SETTING,10000,0) == null)
			click(ELEMENT_MORE_BUTTON);
		click(ELEMENT_SETTING);
		waitForAndGetElement(ELEMENT_SETTING_POPUP);
		click(ELEMENT_SETTING_MYSCRIPTIONS_TAB);
		waitForAndGetElement(ELEMENT_SETTING_EMAIL_ADDRESS);
		info("Set mail address for forum");
		type(ELEMENT_SETTING_EMAIL_ADDRESS, user, true);
		click(ELEMENT_SETTING_EMAIL_UPDATE);
		Utils.pause(1000);
		button.save();
		waitForElementNotPresent(ELEMENT_SETTING_POPUP);
	}

	//------------------------Forum edit portlet--------------------------------------//

	/**Function select option show panels in forum portlet setting
	 * @author lientm
	 * @param show: array option to show panels
	 *        show[0]: show poll
	 *        show[1]: show moderator
	 *        show[2]: show quick reply
	 *        show[3]: show legend icon reply
	 *        show[4]: show rule panel
	 *        show[5]: show statistic panel
	 */
	public void selectPanel(boolean... show){
		button = new Button(driver);
		click(ELEMENT_FORUM_PORTLET_PANEL_TAB);
		if (show.length > 0){
			if (show[0]){
				check(ELEMENT_SHOW_POLL_CHECKBOX, 2);
			} else {
				uncheck(ELEMENT_SHOW_POLL_CHECKBOX, 2);
			}
		}
		if (show.length > 1){
			if (show[1]){
				check(ELEMENT_SHOW_MODERATOR_CHECKBOX, 2);
			}else {
				uncheck(ELEMENT_SHOW_MODERATOR_CHECKBOX, 2);
			}
		}
		if (show.length > 2){
			if (show[2]){
				check(ELEMENT_SHOW_QUICK_REPLY_CHECKBOX, 2);
			} else {
				uncheck(ELEMENT_SHOW_QUICK_REPLY_CHECKBOX, 2);
			}
		}
		if (show.length > 3){
			if (show[3]){
				check(ELEMENT_SHOW_ICON_LEGEND_CHECKBOX, 2);
			} else {
				uncheck(ELEMENT_SHOW_ICON_LEGEND_CHECKBOX, 2);
			}
		}
		if (show.length > 4){
			if (show[4]){
				check(ELEMENT_SHOW_RULE_CHECKBOX, 2);
			} else {
				uncheck(ELEMENT_SHOW_RULE_CHECKBOX, 2);
			}
		}
		if (show.length > 5){
			if (show[5]){
				check(ELEMENT_SHOW_STATISTIC_CHECKBOX, 2);
			} else {
				uncheck(ELEMENT_SHOW_STATISTIC_CHECKBOX, 2);
			}
		}
		button.save();
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(1000);
	}

	/**Function check or uncheck Category or forum in Forum portlet setting
	 * @author lientm
	 * @param itemName: name of category/forum
	 * @param isCategory: = true: category
	 * 					  = false: forum
	 * @param display: = true: check to display
	 * 				   = false: uncheck
	 */
	public void selectDisplayCategoryAndForum(String itemName, boolean display){
		button = new Button(driver);
		String[] items = itemName.split("/");

		click("//*[contains(text(), '" + items[0] + "')]/../..");
		//click(ELEMENT_SELECT_DISPLAY_CHECKBOX.replace("${name}", itemName), 2);

		if (display){
			check(ELEMENT_SELECT_DISPLAY_CHECKBOX.replace("${name}", items[items.length-1]), 2);
		}else {
			uncheck(ELEMENT_SELECT_DISPLAY_CHECKBOX.replace("${name}", items[items.length-1]), 2);
		}
		//button.save();
		click(ELEMENT_FORUM_PORTLET_SAVE_BUTTON);
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(1000);
	}	

	public void selectOptions(boolean ajax){
		button = new Button(driver);
		click(ELEMENT_FORUM_PORTLET_OPTIONS_TAB);
		if (ajax){
			check(ELEMENT_USE_AJAX_CHECKBOX, 2);
		}else {
			uncheck(ELEMENT_USE_AJAX_CHECKBOX, 2);
		}
		button.save();
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(1000);
	}

	//--------------------------------User Management-----------------------------------//

	public void goToUserManagement(String user){
		info("---Go to User management---");
		click(ELEMENT_USER_MANAGEMENT);
		waitForAndGetElement(ELEMENT_USER_MANAGEMENT_POPUP);
		type(ELEMENT_SEARCH_USER_INPUT, user, true);
		String id = waitForAndGetElement(By.xpath("//*[@id='UIPage']/div/div")).getAttribute("id");
		((JavascriptExecutor)driver).executeScript("javascript:eXo.webui.UIForm.submitForm('" + id + "#UIModeratorManagementForm','SearchUser',true);");
		click(ELEMENT_USER_EDIT_ICON.replace("${user}", user));
		waitForAndGetElement(ELEMENT_USER_MANAGEMENT_PROFILE_TAB);
	}

	public void settingUserManagementProfile(String screenName, String titleUser, String category, String forum, String sign, boolean...opt){
		button = new Button(driver);
		if (screenName != null){
			type(ELEMENT_SCREEN_NAME, screenName, true);
		}
		if (titleUser != null){
			type(ELEMENT_USER_MANAGEMENT_TITLE_USER, titleUser, true);
		}
		if (category != null){
			click(ELEMENT_ADD_MODERATOR_CATEGORY_ICON);
			check(ELEMENT_CATEGORY_SELECT_CHECKBOX.replace("${cat}", category), 2);
			click(button.ELEMENT_ADD_BUTTON);
			Utils.pause(1000);
		}
		if (forum != null){
			click(ELEMENT_ADD_MODERATOR_FORUM_ICON);
			check(ELEMENT_CATEGORY_SELECT_CHECKBOX.replace("${cat}", forum), 2);
			click(button.ELEMENT_ADD_BUTTON);
			Utils.pause(1000);
		}
		if (sign != null){
			type(ELEMENT_SIGNATURE, sign, true);
		}
		if (opt.length > 0){
			if (opt[0]){
				check(ELEMENT_USER_MANAGEMENT_FORUM_ADMIN, 2);			
			}else {
				uncheck(ELEMENT_USER_MANAGEMENT_FORUM_ADMIN, 2);

			}
		}
		if (opt.length > 1){
			if (opt[1]){
				check(ELEMENT_IS_DISPLAY_SIGNATURE, 2);			
			}else {
				uncheck(ELEMENT_IS_DISPLAY_SIGNATURE, 2);
			}
		}
		if (opt.length > 2){
			if (opt[2]){
				check(ELEMENT_IS_DISPLAY_AVATAR, 2);

			} else {
				uncheck(ELEMENT_IS_DISPLAY_AVATAR, 2);
			}
		}
	}

	public void banUser(boolean ban, String time, String reason){
		click(ELEMENT_USER_MANAGEMENT_BAN_USER_TAB);
		if (ban){
			check(ELEMENT_BAN_USER_CHECKBOX, 2);
			if (time != null){
				selectOption(ELEMENT_BAN_USER_TIME, time);
			}
			if (reason != null){
				type(ELEMENT_BAN_USER_REASON, reason, true);
			}
		}else {
			uncheck(ELEMENT_BAN_USER_CHECKBOX, 2);
		}
	}
	/** Open form "Private message"
	 * @author thuntn
	 */
	public void goToPrivateMessage(){
		click(ELEMENT_PRIVATE_MESSAGE_ICON);
		waitForAndGetElement(ELEMENT_PRIVATE_MESSAGE_POPUP);
	}
	/** Input a private message form
	 * 
	 * @param receiver: send message to this receiver
	 * @param title
	 * @param message
	 */
	public void inputPrivateMessage(String receiver, String title, String message){
		if(receiver != null)
			type(ELEMENT_PRIVATE_MESSAGE_SENDTO_INPUT,receiver,true);
		if(title != null)
			type(ELEMENT_PRIVATE_MESSAGE_TITLE_INPUT,title,true);
		if(message != null){
			if(this.plfVersion.equalsIgnoreCase("4.0"))
				inputDataToFrameInFrame(ELEMENT_PRIVATE_MESSAGE_FRAME1,ELEMENT_POST_MESSAGE_FRAME_2 , message, false);
			else
				inputDataToFrame(ELEMENT_PRIVATE_MESSAGE_FRAME_41, message);
		}
		switchToParentWindow();
		click(ELEMENT_PRIVATE_MESSAGE_SEND_BUTTON);

	}

	/** Compose a private message
	 * @author thuntn
	 * @param receiver: send message to this receiver
	 * @param title
	 * @param message
	 */
	public void composePrivateMessage(String receiver, String title, String message){
		info("Compose a private message");

		goToPrivateMessage();
		click(ELEMENT_COMPOSE_MESSAGE_TAB);
		inputPrivateMessage(receiver, title, message);
		waitForMessage(MSG_PRIVATE_MESSAGE_COMPOSE);
		click(ELEMENT_PRIVATE_MESSAGE_COMPOSE_OK);
		waitForElementNotPresent(ELEMENT_PRIVATE_MESSAGE_COMPOSE_OK);
		waitForAndGetElement(ELEMENT_PRIVATE_MESSAGE.replace("${message}", title));
	}

	/**Delete message after Private message and tabs are opened
	 * 
	 * @author thuntn
	 * @param titleMessage
	 */
	public void deletePrivateMessage(String titleMessage){
		info("Delete message");
		click(ELEMENT_PRIVATE_MESSAGE_DELETE_ICON.replace("${message}",titleMessage ));
		waitForMessage(MSG_PRIVATE_MESSAGE_DELETE);
		click(ELEMENT_PRIVATE_MESSAGE_DELETE_OK);
		waitForElementNotPresent(ELEMENT_PRIVATE_MESSAGE_DELETE_OK);
		waitForElementNotPresent(ELEMENT_PRIVATE_MESSAGE_DELETE_ICON.replace("${message}",titleMessage ));
	}

	/** Forward a private message
	 * @author thuntn
	 * @param titleMessage
	 * @param receiver
	 */
	public void forwardPrivateMessage(String titleMessage, String receiver,String fwdMessage){
		info("Forward a private message");

		click(ELEMENT_PRIVATE_MESSAGE_SENT_TAB);
		click(ELEMENT_PRIVATE_MESSAGE_FORWARD_ICON.replace("${message}", titleMessage));
		inputPrivateMessage(receiver, null, fwdMessage);
		waitForMessage(MSG_PRIVATE_MESSAGE_COMPOSE);
		click(ELEMENT_PRIVATE_MESSAGE_COMPOSE_OK);
		waitForElementNotPresent(ELEMENT_PRIVATE_MESSAGE_COMPOSE_OK);
		waitForAndGetElement(ELEMENT_PRIVATE_MESSAGE.replace("${message}", "Forward:" + titleMessage));
	}

	/**Reply a private message
	 * @author thuntn
	 * @param titleMessage
	 * @param replyMessage
	 */
	public void replyPrivateMessage(String titleMessage, String replyMessage){
		info("Reply a private message");
		goToPrivateMessage();
		click(ELEMENT_PRIVATE_MESSAGE_REPLY_ICON.replace("${message}", titleMessage));
		inputPrivateMessage(null, null, replyMessage);
		waitForMessage(MSG_PRIVATE_MESSAGE_COMPOSE);
		click(ELEMENT_PRIVATE_MESSAGE_COMPOSE_OK);
		waitForElementNotPresent(ELEMENT_PRIVATE_MESSAGE_COMPOSE_OK);
		waitForAndGetElement(ELEMENT_PRIVATE_MESSAGE.replace("${message}", "Reply:"+titleMessage));
	}
	/**Check private message
	 * @author thuntn
	 * @param titleMessage
	 * @param contentMessage
	 */
	public void checkPrivateMessage(String titleMessage, String contentMessage){
		//verify title of message
		waitForAndGetElement(ELEMENT_PRIVATE_MESSAGE.replace("${message}", titleMessage));

		click(ELEMENT_PRIVATE_MESSAGE.replace("${message}", titleMessage));
		waitForAndGetElement(ELEMENT_PRIVATE_MESSAGE_CONTENT.replace("${message}", contentMessage));

	}

	//--------------------------------Pending job-----------------------------------//
	/**
	 * Open pending job form.
	 * Created by khanhnt at Dec 11, 2013
	 */
	public void goToPendingJob(){
		click(ELEMENT_PENDING);
		waitForAndGetElement(ELEMENT_PENDING_JOB_POPUP);
	}

	/**
	 * Approve a pending job.
	 * Created by khanhnt at Dec 11, 2013
	 * @param jobTitle: title of job.
	 * @param jobContent: content of job.
	 * @param isApprove: true -> approve, false -> no and delete.
	 */
	public void approvePendingJob(String jobTitle,String jobContent,boolean isApprove){
		info("Approve a peding job");
		click(By.linkText(jobTitle));
		waitForTextPresent(jobContent);
		click(ELEMENT_APPROVE_PENDING_JOB);
		waitForAndGetElement(ELEMENT_PENDING_JOB_POPUP);
		click(ELEMENT_CLOSE_PENDING_POPUP);
		waitForElementNotPresent(ELEMENT_PENDING_JOB_POPUP);
		info("Approve a peding job successfully");
	}

	/**
	 * Switch page to show Object on My subscription
	 * @param object
	 */
	public void checkObjectOnMySubscriptionPresent(String object){

		int page = 0;
		if(waitForAndGetElement(ELEMENT_MY_SUBSCRIPTION_TOTAL_PAGE,5000,0) != null){
			page = Integer.parseInt(waitForAndGetElement(ELEMENT_MY_SUBSCRIPTION_TOTAL_PAGE).getText());
			for(int i = 1; i < page; i++){
				if(waitForAndGetElement(ELEMENT_MY_SUBSCRIPTION_OBJECT_WATCH.replace("${object}", object),5000,0) == null){

					if(waitForAndGetElement(ELEMENT_MY_SUBSCRIPTION_NEXT_PAGE,5000,0) != null){
						click(ELEMENT_MY_SUBSCRIPTION_NEXT_PAGE);
					}
				}else break;
			}
		}
		waitForAndGetElement(ELEMENT_MY_SUBSCRIPTION_OBJECT_WATCH.replace("${object}", object));

	}

}
