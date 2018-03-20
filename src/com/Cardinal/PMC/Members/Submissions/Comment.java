package com.Cardinal.PMC.Members.Submissions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.Cardinal.PMC.Members.User;

/**
 * A class used to represent a comment on a {@link Submission}.
 * 
 * @author Cardinal System
 *
 */
public class Comment {
	private static HashMap<Integer, Comment> comments = new HashMap<Integer, Comment>();

	private User author;

	private int ID;
	private String content;
	private LocalDateTime timestamp;
	private List<Integer> children;

	public Comment(User author, int iD, String content, LocalDateTime timestamp) {
		this.author = author;
		ID = iD;
		this.content = content;
		this.timestamp = timestamp;
		comments.put(iD, this);
	}

	/**
	 * Add's a reply to this comment.
	 * 
	 * @param ID
	 *            the reply ID.
	 */
	public void addChild(int ID) {
		if (children == null)
			children = new ArrayList<Integer>();
		children.add(ID);
	}

	/**
	 * @return the reply's author.
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * @return the reply message.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Gets this comment's ID.
	 * 
	 * @return the ID.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return the reply's timestamp.
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "ID: " + ID + "\nAuthor: " + author.toString() + "\nTime: "
				+ timestamp.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) + "\nContent: [\n\t" + content
				+ "\n]";
	}

	/**
	 * Gets the comment who matches the given ID.
	 * 
	 * @param ID
	 *            the comment ID.
	 * @return the matching comment, or null if it hasn't been loaded.
	 */
	public static Comment getCommentByID(int ID) {
		return comments.get(ID);
	}
}