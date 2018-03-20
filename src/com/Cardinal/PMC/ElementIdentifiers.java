package com.Cardinal.PMC;

/**
 * A class of Strings used for HTML parsing.
 * 
 * @author Cardinal System
 *
 */
public class ElementIdentifiers {

	// Universal Strings
	public static final String MINIINFO = "mini-info", DATETIME = "abbr";

	// Thread Strings
	public static final String FTITLE = "title", CONTENT = "thread_content", STATS = "thread_subtitle",
			REPLYCONTAINER = "forum_reply_container", REPLY = "forum_reply", SCORECONTAINER = "score_container",
			SCOREBOX = "score_box", SCORE = "score", CONTENTBOX = "content_box", MEMBERBOX = "member_box",
			TIMEBOX = "time_box", REPLYMESSAGE = "contents pmc_readmore", CRUMB = "thread_crumb_context",
			CATEGORY = "category", IDATTR = "data-id", PARENTIDATTR = "data-parent-id", AUTHID = "author_id",
			THREADID = "visiting_thread", THREADBOX = "thread_box";

	// Submission Strings
	public static final String DETAILS = "resource-info", STITLE = "h1", TEXT = "r-text-block",
			DATEDIV = "post_date txt-subtle", DETAILSBOX = "resource-statistics", COMMENTS = "comment-list",
			COMMENTITEM = "comment-item", COMMENTHEADER = "comment-header", COMMENTTEXT = "comment-text",
			TYPE = "post_context", DOWNLOAD = "content-actions", COMMENTID = "comment_id hidden", SUBID = "resource_id",
			TAGS = "item_tags", SERVERIP = "server_form", RESOURCE = "resource ", RINFO = "r-info",
			AUTHOR = "contributed";

	// Wall Strings

	public static final String WALLCONTENT = "wall_content ", HEADERTABLE = "wall_header table",
			LIKES = "counter likes_count", CMTPAGE = "cmt_page", WALLCOMMENTID = "data-comment-id",
			COMMENTCONTENT = "item_comment_contents", OPTIONS = "post_options", POSTID = "post-id",
			WALLCOMMENTITEM = "item_comment", DIRECTURL = "direct-url";

	// User Strings

	public static final String ABOUT = "memberpage-about", ABOUTMORE = "about_more", INFOPANE = "info_pane",
			CARDSTATS = "member_card_stats", STATSTABLE = "statistics", TABLESTAT = "stat", PROFILEBAR = "profile-bar",
			MEMBERTITLE = "member-title", PROFILEID = "profile-id", THREADLINK = "title", TEAMCELL = "team_cell";
}
