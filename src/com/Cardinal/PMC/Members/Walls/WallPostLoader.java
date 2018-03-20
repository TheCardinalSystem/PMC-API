package com.Cardinal.PMC.Members.Walls;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.Cardinal.PMC.ElementIdentifiers;
import com.Cardinal.PMC.Members.User;
import com.Cardinal.PMC.Members.Submissions.Comment;
import com.Cardinal.PMC.lang.MissingPostException;

/**
 * A class used to load wall posts.
 * 
 * @author Cardinal System
 *
 */
public class WallPostLoader {

	private HashMap<String, WallPost> loadedPosts = new HashMap<String, WallPost>();

	/**
	 * Gets a pre-loaded {@link WallPost} that matches the given URL or loads a new
	 * instance.
	 * 
	 * @param url
	 *            the wall post URL.
	 * @return the wall post.
	 * @throws IOException
	 *             there as an error loading the post.
	 */
	public WallPost getPost(String url) throws IOException {
		return loadedPosts.containsKey(url) ? loadedPosts.get(url) : loadPost(url);
	}

	/**
	 * Loads all the wall posts of the given user,
	 * 
	 * @param user
	 *            the user who's posts to load.
	 * @return the posts.
	 * @throws IOException
	 *             there was an error loading the posts.
	 */
	public List<WallPost> getUserWallPosts(User user) throws IOException {
		String userUrl = user.getURL();
		userUrl += userUrl.endsWith("/") ? "wall/" : "/wall/";
		Document doc = Jsoup.connect(userUrl).userAgent("PMCAPI").post();

		List<WallPost> posts = new ArrayList<WallPost>();
		for (Element post : doc.getElementsByClass(ElementIdentifiers.OPTIONS)) {
			posts.add(getPost(post.absUrl(ElementIdentifiers.DIRECTURL)));
		}

		return posts;
	}

	/**
	 * Loads the given URL into a {@link WallPost} object.
	 * 
	 * @param url
	 *            the URL to load.
	 * @return the {@linkplain WallPost} object.
	 * @throws IOException
	 *             there was an error loading the post.
	 */
	public WallPost loadPost(String url) throws IOException {
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();
		WallPost post = new WallPost(url);

		try {
			post.setAuthor(getAuthor(doc));
			post.setComments(getComments(doc));
			post.setContent(getContent(doc));
			post.setLikes(getLikes(doc));
			post.setTimestamp(getTimestamp(doc));
			post.setID(getID(doc));
		} catch (IndexOutOfBoundsException e) {
			throw new MissingPostException(url, e);
		}

		loadedPosts.put(url, post);
		return post;
	}

	/**
	 * Gets the author of the given wall post.
	 * 
	 * @param doc
	 *            the wall post document.
	 * @return the author.
	 */
	private User getAuthor(Document doc) {
		Element header = doc.getElementsByClass(ElementIdentifiers.HEADERTABLE).first();
		Element user = header.getElementsByTag("a").get(1);

		return new User(user.absUrl("href"));
	}

	/**
	 * Gets the comments for the given wall post.
	 * 
	 * @param doc
	 *            the wall post document.
	 * @return the comments.
	 */
	private List<Comment> getComments(Document doc) {
		List<Comment> comments = new ArrayList<Comment>();
		for (Element item : doc.getElementsByClass(ElementIdentifiers.WALLCOMMENTITEM)) {
			Element user = item.getElementsByTag("a").first();
			Element time = item.getElementsByTag("abbr").first();
			Element content = item.getElementsByClass(ElementIdentifiers.COMMENTCONTENT).first()
					.getElementsByClass("text").first();
			content.select("br").append("\n");
			for (Element image : content.select("img[src]")) {
				image.appendText(" (" + image.attr("src") + ")");
			}
			for (Element hyper : content.select("a[href]")) {
				hyper.appendText(" (" + hyper.attr("href") + ")");
			}

			User author = new User(user.absUrl("href"));
			LocalDateTime date = parseDateTime(time.attr("title"));
			int id = Integer.parseInt(item.attr(ElementIdentifiers.WALLCOMMENTID));
			String desc = content.text();

			Comment comment = new Comment(author, id, desc, date);

			comments.add(comment);
		}
		return comments;
	}

	/**
	 * Gets the content for the given wall post.
	 * 
	 * @param doc
	 *            the wall post document.
	 * @return the content.
	 */
	private String getContent(Document doc) {
		Element content = doc.getElementsByClass(ElementIdentifiers.WALLCONTENT).first();
		content.select("br").append("\n");
		for (Element image : content.select("img[src]")) {
			image.appendText(" (" + image.attr("src") + ")");
		}
		for (Element hyper : content.select("a[href]")) {
			hyper.appendText(" (" + hyper.attr("href") + ")");
		}
		return content.text();
	}

	/**
	 * Gets the ID of the give wall post.
	 * 
	 * @param doc
	 *            the wall post document.
	 * @return the ID.
	 */
	private int getID(Document doc) {
		Element options = doc.getElementsByClass(ElementIdentifiers.OPTIONS).first();
		return Integer.parseInt(options.attr(ElementIdentifiers.POSTID));
	}

	/**
	 * Gets the given wall post's likes.
	 * 
	 * @param doc
	 *            the wall post document.
	 * @return the likes.
	 */
	private int getLikes(Document doc) {
		return Integer.parseInt(doc.getElementsByClass(ElementIdentifiers.LIKES).first().ownText());
	}

	/**
	 * Gets the timestamp of the given wall post.
	 * 
	 * @param doc
	 *            the wall post document.
	 * @return the timestamp.
	 */
	private LocalDateTime getTimestamp(Document doc) {
		Element header = doc.getElementsByClass(ElementIdentifiers.HEADERTABLE).first();
		Element time = header.getElementsByTag("abbr").first();
		return parseDateTime(time.attr("title"));
	}

	/**
	 * Converts the given date-time string into a {@link LocalDateTime} object.
	 * 
	 * @param datetime
	 *            the date-time string to convert.
	 * @return the {@link LocalDateTime} representation of the given date-time
	 *         string.
	 */
	private LocalDateTime parseDateTime(String datetime) {
		String[] dateAndTime = datetime.split("T");
		String[] date = dateAndTime[0].split("-");
		String[] time = dateAndTime[1].split(":");

		int year = Integer.parseInt(date[0]);
		int month = Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[2]);
		int hour = Integer.parseInt(time[0]);
		int minute = Integer.parseInt(time[1]);

		return LocalDateTime.of(year, month, day, hour, minute);
	}
}
