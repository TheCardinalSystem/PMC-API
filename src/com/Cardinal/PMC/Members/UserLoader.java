package com.Cardinal.PMC.Members;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.Cardinal.PMC.ElementIdentifiers;
import com.Cardinal.PMC.lang.MissingPostException;

public class UserLoader {

	private HashMap<String, User> loadedUsers = new HashMap<String, User>();

	/**
	 * Gets a pre-loaded {@link User} that matches the given URL or loads a new
	 * instance.
	 * 
	 * @param url
	 *            the user's profile URL.
	 * @return the user instance.
	 * @throws IOException
	 *             there was an error loading the user.
	 * @throws ParseException
	 *             there was an error loading the user's join date.
	 */
	public User getUser(String url) throws IOException, ParseException {
		return loadedUsers.containsKey(url) ? loadedUsers.get(url) : loadUser(url);
	}

	/**
	 * Loads the given URL into a {@link User} object.
	 * 
	 * @param url
	 *            the URL to load.
	 * @return the user object.
	 * @throws IOException
	 *             there was an error loading the user.
	 * @throws ParseException
	 *             there was an error loading the user's join date.
	 */
	public User loadUser(String url) throws IOException, ParseException {
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		try {
			String name = getName(doc);
			int ID = getID(doc);
			Object[] details = getDetails(url);
			String about = (String) details[0];
			int views = (int) details[1], xp = (int) details[2], level = (int) details[3];
			List<User> subs = getSubscribers(url), subbed = getSubscriptions(url);
			String clazz = (String) details[4];
			LocalDateTime joined = (LocalDateTime) details[5];

			User user = new User(name, url, about, clazz, xp, ID, views, level, joined, subs, subbed);
			loadedUsers.put(url, user);
			return user;
		} catch (IndexOutOfBoundsException e) {
			throw new MissingPostException(url, e);
		}
	}

	/**
	 * Loads details on the given user.
	 * 
	 * @param url
	 *            the URL of the user.
	 * @return the details in this order: <br>
	 *         <code>[aboutMe, subscribers, profile-views, xp, level, class joinDate]</code>
	 * @throws IOException
	 *             there was an error loading the details.
	 * @throws ParseException
	 *             there was an error getting the join date.
	 */
	private Object[] getDetails(String url) throws IOException, ParseException {
		url += url.endsWith("/") ? "about/" : "/about/";
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();
		Element about = doc.getElementById(ElementIdentifiers.ABOUT);

		String aboutMe = about.ownText();
		String rank = doc.getElementsByClass("member-rank-class").first().ownText();
		int level = Integer.parseInt(doc.getElementsByClass("stat level submenu_trigger").first().ownText()
				.replaceFirst("Level", "").trim());

		Element table = about.getElementById(ElementIdentifiers.ABOUTMORE)
				.getElementsByClass(ElementIdentifiers.INFOPANE).first()
				.getElementsByClass(ElementIdentifiers.CARDSTATS).first()
				.getElementsByClass(ElementIdentifiers.STATSTABLE).first().getElementsByTag("tbody").first();

		Elements stats = table.getElementsByClass(ElementIdentifiers.TABLESTAT);

		int views = Integer.parseInt(stats.get(1).ownText().replaceAll(",", ""));
		int xp = Integer.parseInt(stats.get(2).ownText().replaceAll(",", ""));

		LocalDateTime timestamp = LocalDateTime.of(
				LocalDate.parse(stats.get(12).ownText(), DateTimeFormatter.ofPattern("MMM d, yyyy")), LocalTime.MIN);

		return new Object[] { aboutMe, views, xp, level, rank, timestamp };
	}

	/**
	 * Gets the given user's ID.
	 * 
	 * @param doc
	 *            the user document.
	 * @return the ID.
	 */
	private int getID(Document doc) {
		return Integer.parseInt(doc.getElementById(ElementIdentifiers.PROFILEBAR).attr(ElementIdentifiers.PROFILEID));
	}

	/**
	 * Gets the given user's name.
	 * 
	 * @param doc
	 *            the user document.
	 * @return the username.
	 */
	private String getName(Document doc) {
		return doc.getElementById(ElementIdentifiers.MEMBERTITLE).getElementsByTag("h1").first().ownText();
	}

	/**
	 * Loads all the subscriber URLs for the given user URL.
	 * 
	 * @param url
	 *            the user profile URL.
	 * @return the unloaded subscribers.
	 * @throws IOException
	 *             there was an error loading the subscriber URLs.
	 */
	private List<User> getSubscribers(String url) throws IOException {
		url += url.endsWith("/") ? "subscribers/" : "/subscribers/";
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();
		List<User> subs = new ArrayList<User>();
		for (Element team : doc.getElementsByClass(ElementIdentifiers.TEAMCELL)) {
			subs.add(new User(team.getElementsByTag("a").first().absUrl("href")));
		}
		return subs;
	}

	/**
	 * Loads all the subscription URLs for the given user URL.
	 * 
	 * @param url
	 *            the user profile URL.
	 * @return the unloaded subscriptions.
	 * @throws IOException
	 *             there was an error loading the subscriptions URLs.
	 */
	private List<User> getSubscriptions(String url) throws IOException {
		url += url.endsWith("/") ? "subscriptions/" : "/subscriptions/";
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();
		List<User> subs = new ArrayList<User>();
		for (Element team : doc.getElementsByClass(ElementIdentifiers.TEAMCELL)) {
			subs.add(new User(team.getElementsByTag("a").first().absUrl("href")));
		}
		return subs;
	}
}
