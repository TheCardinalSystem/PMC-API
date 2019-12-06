package com.Cardinal.PMC.Members.Submissions;

import java.time.LocalDateTime;
import java.util.List;

import org.jsoup.nodes.Element;

import com.Cardinal.PMC.Members.User;

/**
 * A class used to represent a blog submission.
 * 
 * @author Cardinal System
 *
 */
public class Blog extends Submission {

	/**
	 * Constructs a new {@link Blog} with the given data.
	 * 
	 * @param url         the blog's URL.
	 * @param title       the blog's title.
	 * @param media       URLs to this project's video(s)/thumbnail(s)
	 * @param description the blog's description/content.
	 * @param tags        the blog's tags/keywords.
	 * @param author      the blog's author.
	 * @param diamonds    the blog's diamonds/votes.
	 * @param views       the blog's views.
	 * @param viewsToday  the blog's views today.
	 * @param favorites   the blog's favorites.
	 * @param iD          the block's ID.
	 * @param comments    the block's comments.
	 * @param timestamp   the block's submission date.
	 */
	public Blog(String url, String title, String[] media, Element description, String[] tags, User author, int diamonds,
			int views, int viewsToday, int favorites, int iD, List<Comment> comments, LocalDateTime timestamp) {
		super(url);
		this.type = Type.BLOGS;
		this.title = title;
		this.media = media;
		this.description = description;
		this.tags = tags;
		this.author = author;
		this.diamonds = diamonds;
		this.views = views;
		this.viewsToday = viewsToday;
		this.favorites = favorites;
		ID = iD;
		this.comments = comments;
		this.timestamp = timestamp;
	}

	public enum Category {
		ALL, ARTICLE, TUTORIAL, REVIEW, INTERVIEW, STORY, LETSPLAY, ART, COMIC, OTHER;

		@Override
		public String toString() {
			return this.equals(ALL) ? "" : super.toString().toLowerCase();
		}
	}
}
