package com.Cardinal.PMC.Forums;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;

import com.Cardinal.PMC.Members.User;
import com.Cardinal.PMC.lang.UnloadedResourceExcpetion;

/**
 * A class used to represent a forums thread.
 * 
 * @author Cardinal System
 *
 */
public class Thread {

	private String title, url;
	private Element content;
	private User author;
	private int emeralds = -1, views = -1, ID = -1;
	private Category category;
	private LocalDateTime timestamp;
	private List<Reply> replies;

	/**
	 * Constructs a new {@link Thread} object.
	 * 
	 * @param url
	 *            the thread URL.
	 * @param category
	 *            the thread category.
	 * @param title
	 *            the thread title.
	 * @param content
	 *            the thread content.
	 * @param author
	 *            the thread author.
	 * @param details
	 *            the thread details in this order:<br>
	 *            <code>[emeralds, views, timestamp]</code>
	 * @param ID
	 *            the thread ID.
	 * @param replies
	 *            the thread {@linkplain Reply replies}.
	 */
	public Thread(String url, Category category, String title, Element content, User author, Object[] details, int ID,
			List<Reply> replies) {
		this.url = url;
		this.title = title;
		this.content = content;
		this.author = author;
		this.category = category;
		this.emeralds = (int) details[0];
		this.views = (int) details[1];
		this.timestamp = (LocalDateTime) details[2];
		this.ID = ID;
		this.replies = replies;
	}

	/**
	 * Constructs an unloaded thread object.
	 * 
	 * @param url
	 *            the URL of this thread.
	 * @param title
	 *            the title of this thread.
	 * @param author
	 *            the thread author.
	 * @param category
	 *            the thread category.
	 */
	public Thread(String url, String title, User author, Category category) {
		this.url = url;
		this.title = title;
		this.author = author;
		this.category = category;
	}

	/**
	 * Gets the reply with the given ID.
	 * 
	 * @param ID
	 *            the reply ID.
	 * @return The reply. Null if this thread is not loaded or does not contain a
	 *         reply with the given ID.
	 */
	public Reply getReplyByID(int ID) {
		return replies.stream().filter(t -> t.getID() == ID).findAny().get();
	}

	/**
	 * Gets the URL of this thread.
	 * 
	 * @return the URL.
	 */
	public String getURL() {
		return url;
	}

	/**
	 * Gets this thread's ID.
	 * 
	 * @return the ID.
	 */
	public int getID() {
		if (ID == -1)
			throw new UnloadedResourceExcpetion(url, "threadID");
		else
			return ID;
	}

	/**
	 * @return the thread title.
	 */
	public String getTitle() {
		if (title == null)
			throw new UnloadedResourceExcpetion(url, "threadTitle");
		else
			return title;
	}

	/**
	 * @return the thread description.
	 */
	public Element getContent() {
		if (content == null)
			throw new UnloadedResourceExcpetion(url, "threadContent");
		else
			return content;
	}

	/**
	 * @return the thread's author.
	 */
	public User getAuthor() {
		if (author == null)
			throw new UnloadedResourceExcpetion(url, "threadAuthor");
		else
			return author;
	}

	/**
	 * @return the thread's emerald count;
	 */
	public int getEmeralds() {
		if (emeralds == -1)
			throw new UnloadedResourceExcpetion(url, "threadVotes");
		else
			return emeralds;
	}

	/**
	 * @return the thread's views.
	 */
	public int getViews() {
		if (views == -1)
			throw new UnloadedResourceExcpetion(url, "threadViews");
		else
			return views;
	}

	/**
	 * @return the thread's category.
	 */
	public Category getCategory() {
		if (category == null)
			throw new UnloadedResourceExcpetion(url, "threadCategory");
		else
			return category;
	}

	/**
	 * @return the thread's timestamp.
	 */
	public LocalDateTime getTimestamp() {
		if (timestamp == null)
			throw new UnloadedResourceExcpetion(url, "threadTimestamp");
		else
			return timestamp;
	}

	/**
	 * @return the thread's replies.
	 */
	public List<Reply> getReplies() {
		if (replies == null)
			throw new UnloadedResourceExcpetion(url, "threadReplies");
		else
			return replies;
	}

	/**
	 * Used to check whether or not this thread is loaded.
	 * 
	 * @return true: the thread is loaded.<br>
	 *         false: the thread is not loaded.
	 */
	public boolean isLoaded() {
		return title != null && content != null && author != null && emeralds != -1 && views != -1 && ID != -1
				&& category != null && timestamp != null && replies != null;
	}

	/**
	 * Loads this thread from its URL using the given {@link ThreadLoader}.
	 * 
	 * @param loader
	 *            the thread loader.
	 * @throws IOException
	 *             there was an error loading this thread.
	 */
	public void load(ThreadLoader loader) throws IOException {
		Thread t = loader.getThread(url);
		this.author = t.getAuthor();
		this.category = t.getCategory();
		this.content = t.getContent();
		this.emeralds = t.getEmeralds();
		this.ID = t.getID();
		this.replies = t.getReplies();
		this.timestamp = t.getTimestamp();
		this.views = t.getViews();
	}

	@Override
	public String toString() {
		return this.url;
	}

	public String toPrettyString() {
		try {
			return "ID: " + ID + "\nCategory: " + category.toString() + "\nURL: " + url + "\nTitle: " + title
					+ "\nAuthor: " + author.toString() + "\nEmeralds: " + emeralds + "\nViews: " + views + "\nTime: "
					+ timestamp.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) + "\nContent: [\n\t"
					+ content.text().replaceAll("\n", "\n\t") + "\n]" + "\nReplies: {\n\t"
					+ replies.stream().filter(r -> r.getParentID() == 0).map(t -> t.toString())
							.collect(Collectors.joining("\n\n")).replaceAll("\n", "\n\t")
					+ "\n}";
		} catch (NullPointerException e) {
			return "Category: " + category.toString() + "\nURL: " + url + "\nTitle: " + title + "\nAuthor: "
					+ author.toString();
		}
	}

	@Override
	public boolean equals(Object obj) {
		return obj.toString().equals(this.toString());
	}

	/**
	 * Removes any duplicate threads from the given list.
	 * 
	 * @param threads
	 *            the distinct threads.
	 */
	public static void distinct(Collection<Thread> threads) {
		HashMap<String, Thread> dist = new HashMap<String, Thread>();
		threads.forEach(t -> dist.put(t.getURL(), t));
		threads.clear();
		threads.addAll(dist.values());
	}

	/**
	 * Used to specify how threads will be fed to the API.
	 * 
	 * @author Cardinal System
	 *
	 */
	public enum Feed {
		HOT, NEWEST, ACTIVE, BEST;
		@Override
		public String toString() {
			switch (this) {
			case HOT:
				return "?thread_sort=hotness";
			case NEWEST:
				return "?thread_sort=newest";
			case ACTIVE:
				return "?thread_sort=active";
			case BEST:
				return "?thread_sort=score";
			}
			return null;
		}
	}
}
