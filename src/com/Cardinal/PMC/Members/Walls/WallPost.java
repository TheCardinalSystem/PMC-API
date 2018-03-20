package com.Cardinal.PMC.Members.Walls;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.Cardinal.PMC.Members.User;
import com.Cardinal.PMC.Members.Submissions.Comment;

/**
 * A class used to represent a wall post.
 * 
 * @author Cardinal System
 *
 */
public class WallPost {

	private User author;
	private int ID;
	private String content, URL;
	private int likes;
	private LocalDateTime timestamp;
	private List<Comment> comments;

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
		return author;
	}

	/**
	 * Gets the ID of this wall post.
	 * 
	 * @return the ID.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Gets the content of this wall post.
	 * 
	 * @return the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * Gets this wall post's likes.
	 * 
	 * @return the likes.
	 */
	public int getLikes() {
		return likes;
	}

	/**
	 * Gets the timestamp of this wall post.
	 * 
	 * @return the timestamp.
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * Gets the comments on this wall post.
	 * 
	 * @return the comments.
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * Sets the author of this wall post.
	 * 
	 * @param author
	 *            the author to set.
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * Sets the ID of this wall post.
	 * 
	 * @param ID
	 *            the ID to set
	 */
	public void setID(int ID) {
		this.ID = ID;
	}

	/**
	 * Sets the content of this wall post.
	 * 
	 * @param content
	 *            the content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Sets the likes on this wall post.
	 * 
	 * @param likes
	 *            the likes to set.
	 */
	public void setLikes(int likes) {
		this.likes = likes;
	}

	/**
	 * Sets the timestamp of this wall post.
	 * 
	 * @param timestamp
	 *            the timestamp to set.
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Sets the comments on this wall post.
	 * 
	 * @param comments
	 *            the comments to set.
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
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
