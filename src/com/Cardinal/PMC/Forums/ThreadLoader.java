package com.Cardinal.PMC.Forums;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.Cardinal.PMC.ElementIdentifiers;
import com.Cardinal.PMC.Forums.Thread.Feed;
import com.Cardinal.PMC.Members.User;
import com.Cardinal.PMC.lang.MissingPostException;

/**
 * A class used for storing and loading instances of {@link Thread} objects.
 * 
 * @author Cardinal System
 *
 */
public class ThreadLoader {
	/**
	 * A map of all threads loaded with this loader.
	 */
	private HashMap<String, Thread> loadedThreads = new HashMap<String, Thread>();
	private Set<String> ignoredUsers = new HashSet<String>();

	/**
	 * Searches the given category for the given keywords.
	 * 
	 * @param search   search keywords.
	 * @param category the category to search under.
	 * @param amount   the amount of threads to get.
	 * @return the unloaded threads.
	 * @throws IOException there was an error searching the forums.
	 */
	public List<Thread> searchCategoryThreads(String search, Category category, int amount) throws IOException {
		String url = "https://www.planetminecraft.com" + category.toHREF() + "/search/?keywords=" + search;
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		List<Thread> threadsList = new ArrayList<Thread>();
		int p = 1;
		while (threadsList.size() < amount) {
			p++;
			threadsList.addAll(getThreadPage(doc, amount - threadsList.size()));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return threadsList;
	}

	/**
	 * Searches the forums for the given keywords.
	 * 
	 * @param search search keywords.
	 * @param amount the amount of threads to get.
	 * @return the unloaded threads.
	 * @throws IOException there was an error searching the forums.
	 */
	public List<Thread> searchThreads(String search, int amount) throws IOException {
		String url = "https://www.planetminecraft.com/forums/search/?keywords=" + search;
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		List<Thread> threadsList = new ArrayList<Thread>();
		int p = 1;
		while (threadsList.size() < amount) {
			p++;
			threadsList.addAll(getThreadPage(doc, amount - threadsList.size()));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return threadsList;
	}

	/**
	 * Gets the given number of threads from the given category in order of newest
	 * to oldest.
	 * 
	 * @param category the category.
	 * @param amount   the number of threads to get.
	 * @return the threads.
	 * @throws IOException there was an error loading the threads.
	 */
	public List<Thread> getCategory(Category category, int amount) throws IOException {
		String url = "https://www.planetminecraft.com" + category.toHREF() + "?thread_sort=newest";
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		List<Thread> threadsList = new ArrayList<Thread>();
		int p = 1;
		while (threadsList.size() < amount) {
			p++;
			threadsList.addAll(getThreadPage(doc, amount - threadsList.size()));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return threadsList;
	}

	/**
	 * Gets all the threads under the given category in the specified page range.
	 * 
	 * @param startPage the start page.
	 * @param endPage   the end page.
	 * @param category  the category to get from.
	 * @return the unloaded threads.
	 * @throws IOException there was an error getting the threads.
	 */
	public List<Thread> getCateoryPages(int startPage, int endPage, Category category) throws IOException {
		String url = "https://www.planetminecraft.com" + category.toHREF();

		Document doc = Jsoup.connect(url + "&p=" + startPage).userAgent("PMCAPI").post();

		List<Thread> threadsList = new ArrayList<Thread>();
		int p = startPage;
		while (p < endPage) {
			p++;
			threadsList.addAll(getThreadPage(doc, Integer.MAX_VALUE));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return threadsList;
	}

	/**
	 * Gets the given number of threads from the given category in the order
	 * specified by the the {@linkplain Feed}.
	 * 
	 * @param category the category.
	 * @param feedType used to sort the thread feed.
	 * @param amount   the number of threads to get.
	 * @return the threads.
	 * @throws IOException there was an error loading the threads.
	 */
	public List<Thread> getFeedCategory(Category category, Feed feedType, int amount) throws IOException {
		String url = "https://www.planetminecraft.com" + category.toHREF() + feedType.toString();
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		List<Thread> threadsList = new ArrayList<Thread>();
		int p = 1;
		while (threadsList.size() < amount) {
			p++;
			threadsList.addAll(getThreadPage(doc, amount - threadsList.size()));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return threadsList;
	}

	/**
	 * Gets all the threads under the given category in the specified page range in
	 * order as defined by given feed.
	 * 
	 * @param startPage the start page.
	 * @param endPage   the end page.
	 * @param feedType  used to sort the thread feed.
	 * @param category  the category to get from.
	 * @return the unloaded threads.
	 * @throws IOException there was an error getting the threads.
	 */
	public List<Thread> getFeedCategoryPages(int startPage, int endPage, Feed feedType, Category category)
			throws IOException {
		String url = "https://www.planetminecraft.com" + category.toHREF() + feedType.toString();

		Document doc = Jsoup.connect(url + "&p=" + startPage).userAgent("PMCAPI").post();

		List<Thread> threadsList = new ArrayList<Thread>();
		int p = startPage;
		while (p < endPage) {
			p++;
			threadsList.addAll(getThreadPage(doc, Integer.MAX_VALUE));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return threadsList;
	}

	/**
	 * Gets all the threads in the specified page range ordered as defined by the
	 * given feed.
	 * 
	 * @param startPage the start page.
	 * @param endPage   the end page.
	 * @param feedType  used to sort the thread feed.
	 * @return the unloaded threads.
	 * @throws IOException there was an error getting the threads.
	 */
	public List<Thread> getFeedPages(int startPage, int endPage, Thread.Feed feedType) throws IOException {
		String url = "https://www.planetminecraft.com/forums" + feedType.toString();
		Document doc = Jsoup.connect(url + "&p=" + startPage).userAgent("PMCAPI").post();

		List<Thread> threadsList = new ArrayList<Thread>();
		int p = startPage;
		while (p < endPage) {
			p++;
			threadsList.addAll(getThreadPage(doc, Integer.MAX_VALUE));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return threadsList;
	}

	/**
	 * Gets the given number of threads from all categories in the order specified
	 * by the the {@linkplain Feed}.
	 * 
	 *
	 * @param feedType used to sort the thread feed.
	 * @param amount   the number of threads to get.
	 * @return the threads.
	 * @throws IOException there was an error loading the threads.
	 */
	public List<Thread> getFeedThreads(Feed feedType, int amount) throws IOException {
		String url = "https://www.planetminecraft.com/forums" + feedType.toString();
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		List<Thread> threadsList = new ArrayList<Thread>();
		int p = 1;
		while (threadsList.size() < amount) {
			p++;
			threadsList.addAll(getThreadPage(doc, amount - threadsList.size()));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return threadsList;
	}

	/**
	 * Gets the set of ignored users.
	 * 
	 * @return the users.
	 */
	public Set<String> getIgnoredUsernames() {
		return ignoredUsers;
	}

	/**
	 * Gets all the threads in the specified page range.
	 * 
	 * @param startPage the page to start on.
	 * @param endPage   the page to end on.
	 * @return the unloaded threads.
	 * @throws IOException there was an error getting the threads.
	 */
	public List<Thread> getPages(int startPage, int endPage) throws IOException {
		String url = "https://www.planetminecraft.com/forums/?thread_sort=newest";
		Document doc = Jsoup.connect(url + "&p=" + startPage).userAgent("PMCAPI").post();

		List<Thread> threadsList = new ArrayList<Thread>();
		int p = startPage;
		while (p < endPage) {
			p++;
			threadsList.addAll(getThreadPage(doc, Integer.MAX_VALUE));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return threadsList;
	}

	/**
	 * Gets the thread with the given URL. If the thread is not already loaded,
	 * {@link ThreadLoader#load(String)} will be invoked.
	 * 
	 * @param url the URL of the thread to load.
	 * @return the {@link Thread} representation of the given thread.
	 * @throws IOException there was an error loading the thread.
	 */
	public Thread getThread(String url) throws IOException {
		return loadedThreads.containsKey(url) ? loadedThreads.get(url) : load(url);
	}

	/**
	 * Checks to see if the given URL has been loaded into a {@link Thread} object.
	 * 
	 * @param url the URL of the thread.
	 * @return true: the URL has been loaded into a thread.<br>
	 *         false: the URL has not been loaded.
	 */
	public boolean hasThreadLoaded(String url) {
		return loadedThreads.containsKey(url);
	}

	/**
	 * Will not load threads authored by the specified users.
	 * 
	 * @param usernames the users to ignore.
	 * @return a loader that will ignore the specified users.
	 */
	public ThreadLoader ignoreUsers(String... usernames) {
		for (String user : usernames) {
			ignoredUsers.add(user);
		}
		return this;
	}

	/**
	 * Will not load threads authored by the specified users.
	 * 
	 * @param users the users to ignore.
	 * @return a loader that will ignore the specified users.
	 */
	public ThreadLoader ignoreUsers(User... users) {
		for (User user : users) {
			ignoredUsers.add(user.getName());
		}
		return this;
	}

	/**
	 * Loads the given URL into a {@link Thread} object.
	 * 
	 * @param url the location of the thread.
	 * @return the {@link Thread} representation of the given thread.
	 * @throws IOException there was an error loading the thread.
	 * 
	 */
	public Thread load(String url) throws IOException {
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		try {
			User author = getAuthor(doc);
			Object[] stats = getDetails(doc);
			int id = getThreadID(doc);
			List<Reply> replies = getReplies(doc);
			Category category = getCategory(doc);
			String title = getTitle(doc);
			Element content = getContent(doc);
			boolean locked = getLockedStatus(doc);

			Thread thread = new Thread(url, category, locked, title, content, author, stats, id, replies);
			loadedThreads.put(url, thread);

			return thread;
		} catch (IndexOutOfBoundsException e) {

			throw new MissingPostException(url, e);
		}
	}

	/**
	 * Loads all the threads by the given user.
	 * 
	 * @param user the user who's threads to load.
	 * @return the threads.
	 * @throws IOException there was an error loading the threads.
	 */
	public List<Thread> loadUserThreads(User user) throws IOException {
		String userURL = user.getURL();
		userURL += userURL.endsWith("/") ? "forum/" : "/forum/";
		Document doc = Jsoup.connect(userURL).userAgent("PMCAPI").post();

		List<Thread> threads = new ArrayList<Thread>();
		for (Element thread : doc.getElementsByClass(ElementIdentifiers.THREADLINK)) {
			String link = thread.absUrl("href");
			if (loadedThreads.containsKey(link)) {
				threads.add(loadedThreads.get(link));
			} else {
				threads.add(load(link));
			}
		}

		return threads;
	}

	/**
	 * Removes a user from the ignored list.
	 * 
	 * @param username the name of the user to remove.
	 */
	public void removeIgnoredUser(String username) {
		this.ignoredUsers.remove(username);
	}

	/**
	 * Removes a user from the ignored list.
	 * 
	 * @param user the user to remove.
	 */
	public void removeIgnoredUser(User user) {
		this.ignoredUsers.remove(user.getName());
	}

	/**
	 * Gets the thread author for the given forums thread.
	 * 
	 * @param doc the forums thread document.
	 * @return the thread author.
	 */
	private User getAuthor(Document doc) {
		Element miniInfo = doc.getElementsByClass(ElementIdentifiers.MINIINFO).first();
		Elements loc = miniInfo.getElementsByTag("a");
		Element link = loc.first();
		return new User(link.absUrl("href"));
	}

	/**
	 * Gets the thread category for the given thread.
	 * 
	 * @param doc the forums thread.
	 * @return the thread category.
	 */
	private Category getCategory(Document doc) {
		return Category.parseCategory(
				doc.getElementById(ElementIdentifiers.CONTEXT_HEADER).getElementsByClass(ElementIdentifiers.CRUMB)
						.first().getElementsByClass(ElementIdentifiers.CATEGORY).get(2).attr("href"));
	}

	private boolean getLockedStatus(Document doc) {
		return !doc.getElementsByAttributeValue("title", "locked").isEmpty();
	}

	/**
	 * Gets the thread description/content for the given forums thread.
	 * 
	 * @param doc the forums thread document.
	 * @return the thread content.
	 */
	private Element getContent(Document doc) {
		Elements content = doc.getElementsByClass(ElementIdentifiers.CONTENT);
		if (content.isEmpty()) {
			return new Element(ElementIdentifiers.CONTENT);
		}

		return content.first();
	}

	/**
	 * Gets the emeralds, vies, and timestamp for the given forums thread.
	 * 
	 * @param doc the forums thread document.
	 * @return an object array of thread's details in this form: <br>
	 *         <code>[emeralds, views, timestamp]</code>
	 */
	private Object[] getDetails(Document doc) {
		Element data = doc.getElementsByClass(ElementIdentifiers.STATS).first();
		Elements stats = data.getElementsByTag("span");
		int emeralds = Integer.parseInt(stats.first().ownText());
		int views = Integer.parseInt(stats.get(2).ownText().replaceAll(",", ""));
		LocalDateTime time = parseDateTime(data.getElementsByTag(ElementIdentifiers.DATETIME).first().attr("title"));
		return new Object[] { emeralds, views, time };
	}

	/**
	 * Gets the comments/replies on the given forums thread.
	 * 
	 * @param doc the forums thread document.
	 * @return the thread replies.
	 */
	private List<Reply> getReplies(Document doc) {
		Element cont = doc.getElementsByClass(ElementIdentifiers.REPLYCONTAINER).first();
		Elements replies = cont.getElementsByClass(ElementIdentifiers.REPLY);
		List<Reply> list = new ArrayList<Reply>();
		for (Element reply : replies) {
			int emeralds;
			try {
				emeralds = Integer.parseInt(reply.getElementsByClass(ElementIdentifiers.SCORECONTAINER).first()
						.getElementsByClass(ElementIdentifiers.SCOREBOX).first()
						.getElementsByClass(ElementIdentifiers.SCORE).first().ownText());
			} catch (NullPointerException e) {
				break;
			}
			Element content = reply.getElementsByClass(ElementIdentifiers.CONTENTBOX).first();
			Element member = content.getElementsByClass(ElementIdentifiers.MEMBERBOX).first();
			Element link = member.getElementsByTag("a").first();

			LocalDateTime time = parseDateTime(member.getElementsByClass(ElementIdentifiers.TIMEBOX).first()
					.getElementsByTag(ElementIdentifiers.DATETIME).first().attr("title"));

			User user = new User(link.absUrl("href"));

			Element mes = content.getElementsByClass(ElementIdentifiers.REPLYMESSAGE).first();
			mes.select("br").append("\n");
			int parent = Integer.parseInt(reply.attr(ElementIdentifiers.PARENTIDATTR)),
					ID = Integer.parseInt(reply.attr(ElementIdentifiers.IDATTR));

			Reply rep = new Reply(user, emeralds, ID, parent, mes, time);

			list.add(rep);
		}
		return list;
	}

	/**
	 * Gets the ID for the given thread.
	 * 
	 * @param doc the thread document.
	 * @return the ID.
	 */
	private int getThreadID(Document doc) {
		Element visit = doc.getElementById(ElementIdentifiers.THREADID);
		return Integer.parseInt(visit.attr(ElementIdentifiers.IDATTR));
	}

	/**
	 * Gets the all the thread URLs and their authors from the given page.
	 * 
	 * @param doc   the threads page document.
	 * @param limit the maximum amount of threads to get.
	 * @return the unloaded threads.
	 */
	private List<Thread> getThreadPage(Document doc, int limit) {
		List<Thread> threadsList = new ArrayList<Thread>();
		Elements boxes = doc.getElementsByClass(ElementIdentifiers.THREADBOX);
		for (int i = 0; i <= limit && i < boxes.size(); i++) {
			Element box = boxes.get(i);
			Elements hrefs = box.getElementsByTag("a");
			Element titleUrl = hrefs.get(0);
			String threadUrl = titleUrl.absUrl("href");
			String title = titleUrl.ownText();
			Element auth = box.getElementsByClass(ElementIdentifiers.STATS).get(0).getElementsByTag("a").first();
			User user = new User(auth.absUrl("href"));
			if (ignoredUsers.contains(auth.ownText())) {
				limit++;
			} else {
				threadsList.add(new Thread(threadUrl, title, user, Category.parseCategory(hrefs.get(1).attr("href"))));
			}
		}
		return threadsList;
	}

	/**
	 * Gets the thread title for the given forums thread.
	 * 
	 * @param doc the forums thread document.
	 * @return the thread title.
	 */
	private String getTitle(Document doc) {
		Elements title = doc.getElementsByTag(ElementIdentifiers.FTITLE);
		return title.first().ownText();
	}

	/**
	 * Converts the given date-time string into a {@link LocalDateTime} object.
	 * 
	 * @param datetime the date-time string to convert.
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
