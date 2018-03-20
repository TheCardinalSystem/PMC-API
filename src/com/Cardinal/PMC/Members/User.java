package com.Cardinal.PMC.Members;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.Cardinal.PMC.Forums.Thread;
import com.Cardinal.PMC.Forums.ThreadLoader;
import com.Cardinal.PMC.Members.Submissions.Submission;
import com.Cardinal.PMC.Members.Submissions.SubmissionLoader;
import com.Cardinal.PMC.lang.UnloadedResourceExcpetion;

/**
 * A class used to represent a PMC member.
 * 
 * @author Cardinal System
 *
 */
public class User {

	private String name, url, about, clazz;
	private int xp = -1, ID, profileviews = -1, level = -1;
	private List<User> subscribers, subscriptions;
	private LocalDateTime joined;
	private HashMap<String, Submission> submissions = new HashMap<String, Submission>();
	private HashMap<String, Thread> threads = new HashMap<String, Thread>();

	/**
	 * Constructs a new {@link User} object.
	 * 
	 * @param name
	 *            the user's name.
	 * @param url
	 *            the user's profile URL.
	 * @param about
	 *            the user's "About Me" info.
	 * @param clazz
	 *            the user's rank class.
	 * @param xp
	 *            the user's experience points.
	 * @param iD
	 *            the user's ID.
	 * @param profileviews
	 *            the user's profile views.
	 * @param level
	 *            the user's level.
	 * @param joined
	 *            the user's join date.
	 * @param subscribers
	 *            the user's subscribers (typically unloaded).
	 * @param subscriptions
	 *            the user's subscriptions (typically unloaded).
	 */
	public User(String name, String url, String about, String clazz, int xp, int iD, int profileviews, int level,
			LocalDateTime joined, List<User> subscribers, List<User> subscriptions) {
		this.name = name;
		this.url = url;
		this.about = about;
		this.clazz = clazz;
		this.xp = xp;
		ID = iD;
		this.subscribers = subscribers;
		this.profileviews = profileviews;
		this.level = level;
		this.subscriptions = subscriptions;
		this.joined = joined;
	}

	/**
	 * Constructs a new, unloaded user.
	 * 
	 * @param url
	 *            the user's profile URL.
	 */
	public User(String url) {
		this.url = url;
	}

	/**
	 * Gets a list of submissions by this user.
	 * 
	 * @return this user's submissions.
	 * 
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user's submissions have not been loaded.
	 */
	public List<Submission> getSubmissions() throws UnloadedResourceExcpetion {
		if (submissions.isEmpty())
			throw new UnloadedResourceExcpetion(url.endsWith("/") ? url + "submissions" : url + "/submissions",
					"userSubmission");
		else
			return new ArrayList<Submission>(submissions.values());
	}

	/**
	 * Gets the submission <i>by this user</i> that matched the given URL.
	 * 
	 * @param url
	 *            the URL.
	 * 
	 * @return the submission with the given URL. Returns null if no submissions
	 *         match the given URL.
	 * 
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user's submissions have not been loaded.
	 */

	public Submission getSubmissionByURL(String url) throws UnloadedResourceExcpetion {
		if (submissions.isEmpty())
			throw new UnloadedResourceExcpetion(url.endsWith("/") ? url + "submissions" : url + "/submissions",
					"userSubmission");
		else
			return submissions.get(url);
	}

	/**
	 * Gets all submissions <i>by this user</i> that match the given title.
	 * 
	 * @param title
	 *            the title.
	 * 
	 * @return the matching submissions.
	 * 
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user's submissions have not been loaded.
	 */
	public List<Submission> getSubmissionByTitle(String title) throws UnloadedResourceExcpetion {
		if (submissions.isEmpty())
			throw new UnloadedResourceExcpetion(url.endsWith("/") ? url + "submissions" : url + "/submissions",
					"userSubmission");
		else
			return submissions.values().stream().filter(s -> s.getTitle().equals(title)).collect(Collectors.toList());
	}

	/**
	 * Gets a list of threads by this user.
	 * 
	 * @return this user's threads.
	 * 
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user's threads have not been loaded.
	 */
	public List<Thread> getThreads() throws UnloadedResourceExcpetion {
		if (threads.isEmpty())
			throw new UnloadedResourceExcpetion(url.endsWith("/") ? url + "forum" : url + "/forum", "userThread");
		else
			return new ArrayList<Thread>(threads.values());
	}

	/**
	 * Gets the thread <i>by this user</i> that matches the given URL.
	 * 
	 * @param url
	 *            the URL.
	 * 
	 * @return the thread with the given URL. Returns null if no threads match the
	 *         given URL.
	 * 
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user's threads have not been loaded.
	 */

	public Thread getThreadByURL(String url) throws UnloadedResourceExcpetion {
		if (threads.isEmpty())
			throw new UnloadedResourceExcpetion(url.endsWith("/") ? url + "forum" : url + "/forum", "userThread");
		else
			return threads.get(url);
	}

	/**
	 * Gets all threads <i>by this user</i> that match the given title.
	 * 
	 * @param title
	 *            the title.
	 * 
	 * @return the matching threads.
	 * 
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user's threads have not been loaded.
	 */
	public List<Thread> getThreadByTitle(String title) throws UnloadedResourceExcpetion {
		if (threads.isEmpty())
			throw new UnloadedResourceExcpetion(url.endsWith("/") ? url + "forum" : url + "/forum", "userThread");
		else
			return threads.values().stream().filter(s -> s.getTitle().equals(title)).collect(Collectors.toList());
	}

	/**
	 * Gets this user's profile page URL.
	 * 
	 * @return the URL.
	 */
	public String getURL() {
		return this.url;
	}

	/**
	 * Gets this user's name.
	 * 
	 * @return the name.
	 */
	public String getName() throws UnloadedResourceExcpetion {
		if (this.name == null)
			throw new UnloadedResourceExcpetion(url, "username");
		else
			return name;
	}

	/**
	 * Gets the user's "About Me" info.
	 * 
	 * @return the about.
	 */
	public String getAbout() throws UnloadedResourceExcpetion {
		if (this.about == null)
			throw new UnloadedResourceExcpetion(url, "userAbout");
		else
			return about;
	}

	/**
	 * Gets this user's experience points.
	 * 
	 * @return the experience points.
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user has not been loaded.
	 */
	public int getXp() throws UnloadedResourceExcpetion {
		if (this.xp == -1)
			throw new UnloadedResourceExcpetion(url, "userXp");
		else
			return xp;
	}

	/**
	 * Gets the user's level.
	 * 
	 * @return the level.
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user has not been loaded.
	 */
	public int getLevel() throws UnloadedResourceExcpetion {
		if (this.level == -1)
			throw new UnloadedResourceExcpetion(url, "userLevel");
		else
			return level;
	}

	/**
	 * Gets the user's rank class. For example, <code>Journeyman Network</code>
	 * 
	 * @return the class.
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user has not been loaded.
	 */
	public String getRankClass() throws UnloadedResourceExcpetion {
		if (this.clazz == null)
			throw new UnloadedResourceExcpetion(url, "userClass");
		else
			return clazz;
	}

	/**
	 * Gets this user's ID.
	 * 
	 * @return the ID.
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user has not been loaded.
	 */
	public int getID() throws UnloadedResourceExcpetion {
		if (this.ID == 0)
			throw new UnloadedResourceExcpetion(url, "userID");
		else
			return ID;
	}

	/**
	 * Gets this's user's subscribers.
	 * 
	 * @return the subscribers.
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user has not been loaded.
	 */
	public List<User> getSubscribers() throws UnloadedResourceExcpetion {
		if (this.subscribers == null)
			throw new UnloadedResourceExcpetion(url, "userSubscribers");
		else
			return subscribers;
	}

	/**
	 * Gets this's user's subscriptions.
	 * 
	 * @return the subscriptions.
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user has not been loaded.
	 */
	public List<User> getSubscriptions() throws UnloadedResourceExcpetion {
		if (this.subscriptions == null)
			throw new UnloadedResourceExcpetion(url, "userSubscriptions");
		else
			return subscriptions;
	}

	/**
	 * Gets this user's profile views.
	 * 
	 * @return the profile views.
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user has not been loaded.
	 */
	public int getProfileViews() throws UnloadedResourceExcpetion {
		if (this.profileviews == -1)
			throw new UnloadedResourceExcpetion(url, "userViews");
		else
			return profileviews;
	}

	/**
	 * Gets the time when this user joined PMC.
	 * 
	 * @return the time joined.
	 * @throws UnloadedResourceExcpetion
	 *             thrown if this user has not been loaded.
	 */
	public LocalDateTime getJoined() throws UnloadedResourceExcpetion {
		if (this.joined == null)
			throw new UnloadedResourceExcpetion(url, "userTimestamp");
		else
			return joined;
	}

	/**
	 * Loads this user object from its URL using the provided {@link UserLoader}.
	 * 
	 * @param loader
	 *            the user loader to load this user with.
	 * @throws IOException
	 *             there was an error loading the user.
	 * @throws ParseException
	 *             there was an error loading the user's join date.
	 */
	public void load(UserLoader loader) throws IOException, ParseException {
		User u = loader.getUser(url);
		this.name = u.getName();
		this.about = u.getAbout();
		this.clazz = u.getRankClass();
		this.xp = u.getXp();
		this.ID = u.getID();
		this.subscribers = u.getSubscribers();
		this.profileviews = u.getProfileViews();
		this.level = u.getLevel();
		this.joined = u.getJoined();
	}

	/**
	 * Loads all of this user's submissions using the given
	 * {@link SubmissionLoader}.
	 * 
	 * @param loader
	 *            the submission loader.
	 * @throws IOException
	 *             there was an error loading the submissions.
	 */
	public void loadSubmissions(SubmissionLoader loader) throws IOException {
		List<Submission> subs = loader.loadUserSubmissions(this);
		subs.forEach(s -> submissions.put(s.getURL(), s));
	}

	/**
	 * Loads all of this user's threads using the given {@link ThreadLoader}.
	 * 
	 * @param loader
	 *            the thread loader.
	 * @throws IOException
	 *             there was an error loading the threads.
	 */
	public void loadThreads(ThreadLoader loader) throws IOException {
		List<Thread> thr = loader.loadUserThreads(this);
		thr.forEach(t -> threads.put(t.getURL(), t));
	}

	@Override
	public String toString() {
		return url;
	}

	/**
	 * Returns a fancy string representation of this object.
	 * 
	 * @return a fancy string.
	 */
	public String toVisualString() {
		return "ID: " + ID + "\nURL: " + url + "\nName: " + name + "\nClass: " + clazz + "\nLevel: " + level
				+ "\nExperience Points: " + xp + "\nProfile Views: " + profileviews + "\nJoined Date: "
				+ joined.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) + "\nSubscribers: [\n\t"
				+ subscribers.stream().map(User::toString).collect(Collectors.joining(", ")) + "\n]"
				+ "\nSubscriptions: [\n\t"
				+ subscriptions.stream().map(User::toString).collect(Collectors.joining(", ")) + "\n]";
	}
}
