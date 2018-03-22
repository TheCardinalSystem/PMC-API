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
 * A class used to represent a project submission.
 * 
 * @author Cardinal System
 *
 */
public class Project extends Submission {

	private String downloadurl;

	/**
	 * Constructs a new {@link Project} with the given data.
	 * 
	 * @param url
	 *            the project's URL.
	 * @param title
	 *            the project's title.
	 * @param downloadurl
	 *            the project's download URL.
	 * @param description
	 *            the project's description.
	 * @param tags
	 *            the project's tags/keywords.
	 * @param author
	 *            the project's author.
	 * @param diamonds
	 *            the project's diamonds/votes.
	 * @param views
	 *            the project's views.
	 * @param viewsToday
	 *            the project's views today.
	 * @param favorites
	 *            the project's favorites.
	 * @param iD
	 *            the project's ID.
	 * @param comments
	 *            the project's comments.
	 * @param timestamp
	 *            the project's submission date.
	 */
	public Project(String url, String title, String downloadurl, Element description, String[] tags, User author,
			int diamonds, int views, int viewsToday, int favorites, int iD, List<Comment> comments,
			LocalDateTime timestamp) {
		super(url);
		this.type = Type.PROJECTS;
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
		this.downloadurl = ((Project) loader.getSubmission(url)).getDownload();
		return this;
	}

	@Override
	public String toString() {
		return "ID: " + ID + "\nType: Project\nURL: " + url + "\nTitle: " + title + "\nAuthor: " + author + "\nTime: "
				+ timestamp.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) + "\nDiamonds: " + diamonds
				+ "\nViews: " + views + " | " + viewsToday + " today\nFavorites: " + favorites + "\nDownload: "
				+ downloadurl + "\nTags: " + Arrays.toString(tags) + "\nDesc: [\n\t"
				+ description.text().replaceAll("\n", "\n\t") + "\n]\nComments: {\n\t"
				+ comments.stream().map(c -> c.toString()).collect(Collectors.joining("\n\n")).replaceAll("\n", "\n\t")
				+ "\n}";

	}
}
