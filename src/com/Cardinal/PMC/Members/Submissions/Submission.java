package com.Cardinal.PMC.Members.Submissions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;

import com.Cardinal.PMC.Members.User;
import com.Cardinal.PMC.lang.UnloadedResourceExcpetion;

/**
 * A class used to represent a submission.
 * 
 * @author Cardinal System
 *
 */
public class Submission {

	protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
	protected String url, title, tags[];
	protected Element description;
	protected User author;
	protected int diamonds = -1, views = -1, viewsToday = -1, favorites = -1, ID = -1;
	protected List<Comment> comments;
	protected LocalDateTime timestamp;
	protected Type type;
	protected String[] media;

	/**
	 * Constructs a new (unloaded) submission with the given URL.
	 * 
	 * @param url the URL of the submission.
	 */
	public Submission(String url) {
		this.url = url;
	}

	/**
	 * Gets this submission's URL.
	 * 
	 * @return the submission URL.
	 */
	public String getURL() {
		return url;
	}

	/**
	 * Gets this submission's ID.
	 * 
	 * @return the ID.
	 */
	public int getID() {
		if (ID == -1)
			throw new UnloadedResourceExcpetion(url, "submissionID");
		return ID;
	}

	/**
	 * Gets this submission's author.
	 * 
	 * @return the author.
	 */
	public User getAuthor() {
		if (author == null)
			throw new UnloadedResourceExcpetion(url, "submissionAuthor");
		return author;
	}

	/**
	 * Gets this submision's title.
	 * 
	 * @return the title.
	 */
	public String getTitle() {
		if (title == null)
			throw new UnloadedResourceExcpetion(url, "submissionTitle");
		return title;
	}

	/**
	 * Get's the URLs to any media (videos/thumbnails) in this submission's header.
	 * 
	 * @return media URLs.
	 */
	public String[] getMedia() {
		if (media == null)
			throw new UnloadedResourceExcpetion(url, "submissionMedia");
		return media;
	}

	/**
	 * Gets the description of this submission.
	 * 
	 * @return the description.
	 */
	public Element getDescription() {
		if (description == null)
			throw new UnloadedResourceExcpetion(url, "submissionDesc");
		return description;
	}

	/**
	 * Gets this submittion's diamond count.
	 * 
	 * @return the diamond count.
	 */
	public int getDiamonds() {
		if (diamonds == -1)
			throw new UnloadedResourceExcpetion(url, "submissionVotes");
		return diamonds;
	}

	/**
	 * Gets this submission's views.
	 * 
	 * @return the views.
	 */
	public int getViews() {
		if (views == -1)
			throw new UnloadedResourceExcpetion(url, "submissionViews");
		return views;
	}

	/**
	 * Gets this submission's views from today.
	 * 
	 * @return the views from today.
	 */
	public int getViewsToday() {
		if (viewsToday == -1)
			throw new UnloadedResourceExcpetion(url, "submissionViewsToday");
		return viewsToday;
	}

	/**
	 * Gets this submission's favorites.
	 * 
	 * @return the favorites.
	 */
	public int getFavorites() {
		if (favorites == -1)
			throw new UnloadedResourceExcpetion(url, "submissionFavorites");
		return favorites;
	}

	/**
	 * Get's this submission's replies.
	 * 
	 * @return the replies.
	 */
	public List<Comment> getComments() {
		if (comments == null)
			throw new UnloadedResourceExcpetion(url, "submissionComments");
		return comments;
	}

	/**
	 * Gets this submission's timestamp.
	 * 
	 * @return the timestamp.
	 */
	public LocalDateTime getTimestamp() {
		if (timestamp == null)
			throw new UnloadedResourceExcpetion(url, "submissionDate");
		return timestamp;
	}

	/**
	 * Gets this submission's tags.
	 * 
	 * @return the tags.
	 */
	public String[] getTags() {
		if (tags == null)
			throw new UnloadedResourceExcpetion(url, "submissionTags");
		return tags;
	}

	/**
	 * Gets this submission's type.
	 * 
	 * @return the type.
	 */
	public Type getType() {
		if (type == null)
			throw new UnloadedResourceExcpetion(url, "submissionType");
		return type;
	}

	/**
	 * Uses the given loader to load this submission.<br>
	 * <br>
	 * NOTE: Do not use this method if you want to load DownloadableSubmissions. You
	 * will not be able to cast {@link DownloadableSubmission} to {@link Submission}
	 * unless you use {@link SubmissionLoader#load(String)} or
	 * {@link SubmissionLoader#load(Submission)}.
	 * 
	 * @param loader the loader.
	 * @return this, once it is loaded.
	 * @throws IOException there was an error loading the submission.
	 */
	public Submission load(SubmissionLoader loader) throws IOException {
		Submission sub = loader.getSubmission(url);
		this.author = sub.getAuthor();
		this.comments = sub.getComments();
		this.media = Arrays.stream(sub.getMedia())
				.map(s -> s != null && s.contains("youtube") ? s.replaceAll("embed\\/", "watch?v=") : s)
				.toArray(String[]::new);
		this.description = sub.getDescription();
		this.diamonds = sub.getDiamonds();
		this.favorites = sub.getFavorites();
		this.ID = sub.getID();
		this.tags = sub.getTags();
		this.timestamp = sub.getTimestamp();
		this.title = sub.getTitle();
		this.views = sub.getViews();
		this.viewsToday = sub.getViewsToday();
		return this;
	}

	@Override
	public String toString() {
		try {
			return "ID: " + ID + "\nType: " + type.toString().toUpperCase() + "\nURL: " + url + "\nMedia: "
					+ Arrays.toString(media != null ? media : new String[1]) + "\nTitle: " + title + "\nAuthor: "
					+ author + "\nTime: " + timestamp.format(FORMATTER) + "\nDiamonds: " + diamonds + "\nViews: "
					+ views + " | " + viewsToday + " today\nFavorites: " + favorites + "\nTags: "
					+ Arrays.toString(tags) + "\nDesc: [\n\t" + description.text().replaceAll("\n", "\n\t")
					+ "\n]\nComments: {\n\t" + comments.stream().map(c -> c.toString())
							.collect(Collectors.joining("\n\n")).replaceAll("\n", "\n\t")
					+ "\n}";
		} catch (NullPointerException e) {
			return url;
		}
	}

	/**
	 * Used to specify submission types.
	 * 
	 * @author Cardinal System
	 *
	 */
	public enum Type {
		PROJECTS, SKINS, PACKS, SERVERS, MODS, BLOGS;

		@Override
		public String toString() {
			return this.equals(PACKS) ? "texture_packs" : super.toString().toLowerCase();
		}
	}

	/**
	 * Used to specify how submissions are fed into the API.
	 * 
	 * @author Cardinal System
	 *
	 */
	public enum Feed {
		TRENDING, UPDATED, NEW, BEST, VIEWS, DOWNLOADS;

		@Override
		public String toString() {
			switch (this) {
			case TRENDING:
				return "?order=order_hot";
			case UPDATED:
				return "?order=order_updated";
			case NEW:
				return "?order=order_latest";
			case BEST:
				return "?order=order_popularity";
			case VIEWS:
				return "?order=order_views";
			case DOWNLOADS:
				return "?order=order_downloads";
			}
			return null;
		}
	}
}
