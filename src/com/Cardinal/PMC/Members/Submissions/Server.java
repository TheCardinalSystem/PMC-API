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
 * A class used to represent a server submission.
 * 
 * @author Cardinal System
 *
 */
public class Server extends Submission {

	private String ip;

	/**
	 * Constructs a new {@link Server} with the given data.
	 * 
	 * @param url
	 *            the server's URL.
	 * @param title
	 *            the server's title.
	 * @param IP
	 *            the server's IP.
	 * @param description
	 *            the server's description.
	 * @param tags
	 *            the server's tags/keywords.
	 * @param author
	 *            the server's author.
	 * @param diamonds
	 *            the server's diamonds/votes.
	 * @param views
	 *            the server's views.
	 * @param viewsToday
	 *            the server's views today.
	 * @param favorites
	 *            the server's favorites.
	 * @param iD
	 *            the server's ID.
	 * @param comments
	 *            the server's comments.
	 * @param timestamp
	 *            the server's submission date.
	 */
	public Server(String url, String title, String IP, Element description, String[] tags, User author, int diamonds,
			int views, int viewsToday, int favorites, int iD, List<Comment> comments, LocalDateTime timestamp) {
		super(url);
		this.url = url;
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.author = author;
		this.diamonds = diamonds;
		this.views = views;
		this.viewsToday = viewsToday;
		this.favorites = favorites;
		this.ID = iD;
		this.ip = IP;
		this.comments = comments;
		this.timestamp = timestamp;
		this.type = Type.SERVERS;
	}

	/**
	 * Gets this server's IP.
	 * 
	 * @return the IP.
	 */
	public String getIP() {
		if (ip == null)
			throw new UnloadedResourceExcpetion(url, "serverIP");
		return ip;
	}

	@Override
	public Submission load(SubmissionLoader loader) throws IOException {
		super.load(loader);
		this.ip = ((Server) loader.getSubmission(url)).getIP();
		return this;
	}

	@Override
	public String toString() {
		return "ID: " + ID + "\nType: Server\nURL: " + url + "\nTitle: " + title + "\nIP: " + ip + "\nAuthor: " + author
				+ "\nTime: " + timestamp.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) + "\nDiamonds: "
				+ diamonds + "\nViews: " + views + " | " + viewsToday + " today\nFavorites: " + favorites + "\nTags: "
				+ Arrays.toString(tags) + "\nDesc: [\n\t" + description.text().replaceAll("\n", "\n\t")
				+ "\n]\nComments: {\n\t"
				+ comments.stream().map(c -> c.toString()).collect(Collectors.joining("\n\n")).replaceAll("\n", "\n\t")
				+ "\n}";

	}
}
