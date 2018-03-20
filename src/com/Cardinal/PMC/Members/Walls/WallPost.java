package com.Cardinal.PMC.Members.Walls;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.Cardinal.PMC.Members.User;
import com.Cardinal.PMC.Members.Submissions.Comment;
import com.Cardinal.PMC.lang.UnloadedResourceExcpetion;

/**
 * A class used to represent a wall post.
 * 
 * @author Cardinal System
 *
 */
public class WallPost {

	private User author;
	private int ID = -1;
	private String content, URL;
	private int likes = -1;
	private LocalDateTime timestamp;
	private List<Comment> comments;

	/**
	 * Constructs a new {@link WallPost} with the given data.
	 * 
	 * @param author
	 *            the posts' author.
	 * @param iD
	 *            the post's ID.
	 * @param content
	 *            the post's description.
	 * @param uRL
	 *            the post's URL.
	 * @param likes
	 *            the post's likes.
	 * @param timestamp
	 *            the post's timestamp.
	 * @param comments
	 *            the post's comments.
	 */
	public WallPost(User author, int iD, String content, String uRL, int likes, LocalDateTime timestamp,
			List<Comment> comments) {
		this.author = author;
		ID = iD;
		this.content = content;
		URL = uRL;
		this.likes = likes;
		this.timestamp = timestamp;
		this.comments = comments;
	}

	/**
	 * Constructs a new wall post with the given URL.
	 * 
	 * @param URL
	 *            the URL of the wall post.
	 */
	public WallPost(String URL) {
		this.URL = URL;
	}

	/**
	 * Gets the URL of this wall post.
	 * 
	 * @return the URL.
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * Gets the author of this wall post.
	 * 
	 * @return the author.
	 */
	public User getAuthor() {
		if (author == null)
			throw new UnloadedResourceExcpetion(URL, "wallpostAuthor");
		return author;
	}

	/**
	 * Gets the ID of this wall post.
	 * 
	 * @return the ID.
	 */
	public int getID() {
		if (ID == -1)
			throw new UnloadedResourceExcpetion(URL, "wallpostID");
		return ID;
	}

	/**
	 * Gets the content of this wall post.
	 * 
	 * @return the content.
	 */
	public String getContent() {
		if (comments == null)
			throw new UnloadedResourceExcpetion(URL, "wallpostDesc");
		return content;
	}

	/**
	 * 
	 * Gets this wall post's likes.
	 * 
	 * @return the likes.
	 */
	public int getLikes() {
		if (likes == -1)
			throw new UnloadedResourceExcpetion(URL, "wallpostVotes");
		return likes;
	}

	/**
	 * Gets the timestamp of this wall post.
	 * 
	 * @return the timestamp.
	 */
	public LocalDateTime getTimestamp() {
		if (timestamp == null)
			throw new UnloadedResourceExcpetion(URL, "wallpostDate");
		return timestamp;
	}

	/**
	 * Gets the comments on this wall post.
	 * 
	 * @return the comments.
	 */
	public List<Comment> getComments() {
		if (comments == null)
			throw new UnloadedResourceExcpetion(URL, "wallpostComments");
		return comments;
	}

	/**
	 * Loads this wall post from URL provided in the constructor.
	 * 
	 * @param loader
	 *            the {@link WallPostLoader} to load from.
	 * @return this, once it's loaded.
	 * @throws IOException
	 *             there was an error loading this.
	 */
	public WallPost load(WallPostLoader loader) throws IOException {
		WallPost post = loader.loadPost(URL);
		this.author = post.getAuthor();
		this.comments = post.getComments();
		this.content = post.getContent();
		this.ID = post.getID();
		this.likes = post.getLikes();
		this.timestamp = post.getTimestamp();
		return this;
	}

	@Override
	public String toString() {
		return "ID: " + ID + "\nURL: " + URL + "\nAuthor: " + author.toString() + "\nLikes: " + likes + "\nTime: "
				+ timestamp.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) + "\nContent: [\n\t" + content
				+ "\n]\nComments: {\n\t"
				+ comments.stream().map(c -> c.toString()).collect(Collectors.joining("\n\n")).replaceAll("\n", "\n\t")
				+ "\n}";
	}

}
