package com.Cardinal.PMC.Members.Submissions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.Cardinal.PMC.Members.User;
import com.Cardinal.PMC.lang.UnloadedResourceExcpetion;

/**
 * A class used to represent a resource pack submission.
 * 
 * @author Cardinal System
 *
 */
public class TexturePack extends Submission {

	private String downloadurl;

	/**
	 * Constructs a new {@link TexturePack} with the given data.
	 * 
	 * @param url
	 *            the pack's URL.
	 * @param title
	 *            the pack's title.
	 * @param downloadurl
	 *            the pack's download URL.
	 * @param description
	 *            the pack's description.
	 * @param tags
	 *            the pack's tags/keywords.
	 * @param author
	 *            the pack's author.
	 * @param diamonds
	 *            the pack's diamonds/votes.
	 * @param views
	 *            the pack's views.
	 * @param viewsToday
	 *            the pack's views today.
	 * @param favorites
	 *            the pack's favorites.
	 * @param iD
	 *            the pack's ID.
	 * @param comments
	 *            the pack's comments.
	 * @param timestamp
	 *            the pack's submission date.
	 */
	public TexturePack(String url, String title, String downloadurl, String description, String[] tags, User author,
			int diamonds, int views, int viewsToday, int favorites, int iD, List<Comment> comments,
			LocalDateTime timestamp) {
		super(url);
		this.url = url;
		this.title = title;
		this.downloadurl = downloadurl;
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
		this.type = Type.PACKS;
	}

	/**
	 * Gets the download URL for this project.
	 * 
	 * @return the download URL.
	 */
	public String getDownload() {
		if (downloadurl == null)
			throw new UnloadedResourceExcpetion(url, "submissionDownload");
		return downloadurl;
	}

	@Override
	public Submission load(SubmissionLoader loader) throws IOException {
		super.load(loader);
		this.downloadurl = ((TexturePack) loader.getSubmission(url)).getDownload();
		return this;
	}

	@Override
	public String toString() {
		return "ID: " + ID + "\nType: Resource Pack\nURL: " + url + "\nTitle: " + title + "\nAuthor: " + author
				+ "\nTime: " + timestamp.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) + "\nDiamonds: "
				+ diamonds + "\nViews: " + views + " | " + viewsToday + " today\nFavorites: " + favorites
				+ "\nDownload: " + downloadurl + "\nTags: " + Arrays.toString(tags) + "\nDesc: [\n\t"
				+ description.replaceAll("\n", "\n\t") + "\n]\nComments: {\n\t"
				+ comments.stream().map(c -> c.toString()).collect(Collectors.joining("\n\n")).replaceAll("\n", "\n\t")
				+ "\n}";

	}
}
