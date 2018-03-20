package com.Cardinal.PMC.Forums;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.Cardinal.PMC.Members.User;

/**
 * A class used to represent a reply/comment on a {@link Thread}.
 * 
 * @author Cardinal System
 *
 */
public class Reply {

	private static HashMap<Integer, Reply> replies = new HashMap<Integer, Reply>();

	private User author;
	private int emeralds, ID, parentID;
	private String content;
	private LocalDateTime timestamp;
	private List<Integer> children;

	/**
	 * Constructs a new {@link Reply} object.
	 * 
	 * @param author
	 *            the reply's author.
	 * @param emeralds
	 *            the reply's emeralds/likes.
	 * @param iD
	 *            the reply's ID.
	 * @param parentID
	 *            the ID of this reply's parent.
	 * @param content
	 *            the reply's content.
	 * @param timestamp
	 *            the reply's timestamp.
	 */
	public Reply(User author, int emeralds, int iD, int parentID, String content, LocalDateTime timestamp) {
		this.author = author;
		this.emeralds = emeralds;
		this.ID = iD;
		this.parentID = parentID;
		this.content = content;
		this.timestamp = timestamp;

		children = new ArrayList<Integer>();

		replies.put(ID, this);
		if (parentID != 0) {
			replies.get(parentID).addChild(ID);
		}
	}

	/**
	 * @return the reply's author.
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * Gets this reply's ID.
	 * 
	 * @return the ID.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Gets the ID of this' reply's parent.
	 * 
	 * @return the parent ID, or 0 if there is no parent.
	 */
	public int getParentID() {
		return this.parentID;
	}

	/**
	 * @return the reply's emerald count.
	 */
	public int getEmeralds() {
		return emeralds;
	}

	/**
	 * @return the reply message.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the reply's timestamp.
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * Adds a child reply to this reply.
	 * 
	 * @param ID
	 *            the ID of the child.
	 */
	public void addChild(int ID) {
		this.children.add(ID);
	}

	/**
	 * Gets all replies to this reply.
	 * 
	 * @return replies to this reply.
	 */
	public List<Integer> getChildren() {
		return this.children;
	}

	@Override
	public String toString() {
		return "ID: " + ID + "\nAuthor: " + author.toString() + "\nEmeralds: " + emeralds + "\nTime: "
				+ timestamp.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) + "\nContent: [\n\t" + content
				+ "\n]"
				+ (children.isEmpty() ? ""
						: "\nReplies: {\n\t" + children.stream().map(replies::get).map(Reply::toString)
								.collect(Collectors.joining("\n\n")).replaceAll("\n", "\n\t") + "\n}");
	}

	/**
	 * (Statically) Gets the reply that matches the given ID.
	 * 
	 * @param ID
	 *            the reply ID.
	 * @return the matching reply.
	 */
	public static Reply getReplyByID(int ID) {
		return replies.get(ID);
	}

}