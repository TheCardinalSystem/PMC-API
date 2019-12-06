package com.Cardinal.PMC.Members.Submissions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.Cardinal.PMC.ElementIdentifiers;
import com.Cardinal.PMC.Members.User;
import com.Cardinal.PMC.Members.Submissions.Blog.Category;
import com.Cardinal.PMC.Members.Submissions.Submission.Feed;
import com.Cardinal.PMC.Members.Submissions.Submission.Type;
import com.Cardinal.PMC.lang.MissingPostException;

public class SubmissionLoader {

	/**
	 * A map of all submissions loaded with this loader.
	 */
	private HashMap<String, Submission> loadedSubmissions = new HashMap<String, Submission>();
	private Set<String> ignoredUsers = new HashSet<String>();

	/**
	 * Searches under the specified type of submission for the given keywords as
	 * defined by the given feed.
	 * 
	 * @param search   search keywords.
	 * @param type     the type of submission to search.
	 * @param feedType used to sort the blogs.
	 * @param amount   the amount of submissions to get.
	 * @return the unloaded submissions.
	 * @throws IOException there was an error getting the submissions.
	 */
	public List<Submission> searchSubmissionsFeed(String search, Type type, Feed feedType, int amount)
			throws IOException {
		String url = "https://www.planetminecraft.com/resources/" + type.toString() + feedType.toString() + "&keywords="
				+ search;
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		List<Submission> submissions = new ArrayList<Submission>();
		int p = 1;
		while (submissions.size() < amount) {
			p++;
			submissions.addAll(getSubmissionPage(doc, amount - submissions.size()));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return submissions;
	}

	/**
	 * Searches under the specified type of submission for the given keywords.
	 * 
	 * @param search search keywords.
	 * @param type   the type of submission to search.
	 * @param amount the amount of submissions to get.
	 * @return the unloaded submissions.
	 * @throws IOException there was an error getting the submissions.
	 */
	public List<Submission> searchSubmissions(String search, Type type, int amount) throws IOException {
		String url = "https://www.planetminecraft.com/resources/" + type.toString() + "?keywords=" + search;
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		List<Submission> submissions = new ArrayList<Submission>();
		int p = 1;
		while (submissions.size() < amount) {
			p++;
			submissions.addAll(getSubmissionPage(doc, amount - submissions.size()));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return submissions;
	}

	/**
	 * Gets all the blogs under the given category in the specified page range as
	 * defined by the given feed.
	 * 
	 * @param startPage the start page.
	 * @param endPage   the end page.
	 * @param category  the category.
	 * @param feedType  used to sort the blogs.
	 * @return the unloaded blogs.
	 * @throws IOException there was an error getting the blogs.
	 */
	public List<Submission> getBlogFeedPages(int startPage, int endPage, Blog.Category category, Feed feedType)
			throws IOException {
		String url = "https://www.planetminecraft.com/resources/blogs/" + category.toString() + feedType.toString();
		Document doc = Jsoup.connect(url + "&p=" + startPage).userAgent("PMCAPI").post();

		List<Submission> submissions = new ArrayList<Submission>();
		int p = startPage;
		while (p < endPage) {
			p++;
			submissions.addAll(getSubmissionPage(doc, Integer.MAX_VALUE));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return submissions;
	}

	/**
	 * Gets all the blogs under the given category in the specified page range.
	 * 
	 * @param startPage the start page.
	 * @param endPage   the end page.
	 * @param category  the category.
	 * @return the unloaded blogs.
	 * @throws IOException there was an error getting the blogs.
	 */
	public List<Submission> getBlogPages(int startPage, int endPage, Blog.Category category) throws IOException {
		String url = "https://www.planetminecraft.com/resources/blogs/" + category.toString() + Feed.NEW.toString();
		Document doc = Jsoup.connect(url + "&p=" + startPage).userAgent("PMCAPI").post();

		List<Submission> submissions = new ArrayList<Submission>();
		int p = startPage;
		while (p < endPage) {
			p++;
			submissions.addAll(getSubmissionPage(doc, Integer.MAX_VALUE));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return submissions;
	}

	/**
	 * Gets the specified number of unloaded blogs from the given blog category in
	 * order of newest to oldest.
	 * 
	 * @param category the blog category.
	 * @param amount   the amount of blogs to get.
	 * @return the unloaded blogs.
	 * @throws IOException there was an error loading the blogs.
	 */
	public List<Submission> getBlogs(Blog.Category category, int amount) throws IOException {
		String url = "https://www.planetminecraft.com/resources/blogs/" + category.toString() + Feed.NEW.toString();
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		List<Submission> submissions = new ArrayList<Submission>();
		int p = 1;
		while (submissions.size() < amount) {
			p++;
			submissions.addAll(getSubmissionPage(doc, amount - submissions.size()));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}
		return submissions;
	}

	/**
	 * Gets the specified number of unloaded blogs from the given blog category in
	 * the specified order.
	 * 
	 * @param category the blog category.
	 * @param feedType used to sort the blog feed.
	 * @param amount   the amount of blogs to get.
	 * @return the unloaded blogs.
	 * @throws IOException there was an error loading the blogs.
	 */
	public List<Submission> getBlogsFeed(Blog.Category category, Submission.Feed feedType, int amount)
			throws IOException {
		String url = "https://www.planetminecraft.com/resources/blogs/" + category.toString() + feedType.toString();
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		List<Submission> submissions = new ArrayList<Submission>();
		int p = 1;
		while (submissions.size() < amount) {
			p++;
			submissions.addAll(getSubmissionPage(doc, amount - submissions.size()));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return submissions;
	}

	/**
	 * Gets all the submissions under the given submission type in the specified
	 * page range ordered as defined by the given feed. Use
	 * {@link SubmissionLoader#getBlogFeedPages(int, int, Category, Feed)} for
	 * blogs.
	 * 
	 * @param startPage the start page.
	 * @param endPage   the end page.
	 * @param type      the submissions type.
	 * @param feedType  used to sort the blog feed.
	 * @return the unloaded submissions.
	 * @throws IOException there was an error getting the submissions.
	 */
	public List<Submission> getFeedTypePages(int startPage, int endPage, Submission.Type type, Feed feedType)
			throws IOException {
		if (type.equals(Submission.Type.BLOGS))
			throw new IllegalArgumentException("SubmissionLoader#getType cannot be used to get blogs!");

		String url = "https://www.planetminecraft.com/resources/" + type.toString() + feedType.toString();
		Document doc = Jsoup.connect(url + "&p=" + startPage).userAgent("PMCAPI").post();

		List<Submission> submissions = new ArrayList<Submission>();
		int p = startPage;
		while (p < endPage) {
			p++;
			submissions.addAll(getSubmissionPage(doc, Integer.MAX_VALUE));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return submissions;
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
	 * Gets the submission with the given URL. If the submission is not already
	 * loaded, {@link SubmissionLoader#load(String)} will be invoked.
	 * 
	 * @param url the URL of the submission to load.
	 * @return the {@link Submission} representation of the given submission.
	 * @throws IOException there was an error loading the submission.
	 */
	public Submission getSubmission(String url) throws IOException {
		return loadedSubmissions.containsKey(url) ? loadedSubmissions.get(url) : load(url);
	}

	/**
	 * Gets the unloaded submissions of the given type in order of newest to oldest.
	 * 
	 * @param type   the type of submission to get.
	 * @param amount the amount of submissions to get.
	 * @return the unloaded submissions.
	 * @throws IOException there was an error loading the submissions.
	 */
	public List<Submission> getType(Submission.Type type, int amount) throws IOException {
		if (type.equals(Submission.Type.BLOGS))
			throw new IllegalArgumentException("SubmissionLoader#getType cannot be used to get blogs!");

		String url = "https://www.planetminecraft.com/resources/" + type.toString() + "?order=order_latest";
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		List<Submission> submissions = new ArrayList<Submission>();
		int p = 1;
		while (submissions.size() < amount) {
			p++;
			submissions.addAll(getSubmissionPage(doc, amount - submissions.size()));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return submissions;
	}

	/**
	 * Gets the unloaded submissions of the given type in the order specified by the
	 * given {@link Feed}. Use {@link SubmissionLoader#getBlogs(Category, int)} for
	 * blogs.
	 * 
	 * @param type     the type of submission to get.
	 * @param feedType used to sort the submission feed.
	 * @param amount   the amount of submissions to get.
	 * @return the unloaded submissions.
	 * @throws IOException there was an error loading the submissions.
	 */
	public List<Submission> getTypeFeed(Submission.Type type, Submission.Feed feedType, int amount) throws IOException {
		if (type.equals(Submission.Type.BLOGS))
			throw new IllegalArgumentException("SubmissionLoader#getTypeFeed cannot be used to get blogs!");

		String url = "https://www.planetminecraft.com/resources/" + type.toString() + feedType.toString();
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();

		List<Submission> submissions = new ArrayList<Submission>();
		int p = 1;
		while (submissions.size() < amount) {
			p++;
			submissions.addAll(getSubmissionPage(doc, amount - submissions.size()));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return submissions;
	}

	/**
	 * Gets all the submissions under the given submission type in the specified
	 * page range. Use {@link SubmissionLoader#getBlogPages(int, int, Category)} for
	 * blogs.
	 * 
	 * @param startPage the start page.
	 * @param endPage   the end page.
	 * @param type      the submissions type.
	 * @return the unloaded submissions.
	 * @throws IOException there was an error getting the submissions.
	 */
	public List<Submission> getTypePages(int startPage, int endPage, Submission.Type type) throws IOException {
		if (type.equals(Submission.Type.BLOGS))
			throw new IllegalArgumentException("SubmissionLoader#getTypePages cannot be used to get blogs!");

		String url = "https://www.planetminecraft.com/resources/" + type.toString() + "?order=order_latest";
		Document doc = Jsoup.connect(url + "&p=" + startPage).userAgent("PMCAPI").post();

		List<Submission> submissions = new ArrayList<Submission>();
		int p = startPage;
		while (p < endPage) {
			p++;
			submissions.addAll(getSubmissionPage(doc, Integer.MAX_VALUE));
			doc = Jsoup.connect(url + "&p=" + p).userAgent("PMCAPI").post();
		}

		return submissions;
	}

	/**
	 * Will not load submissions authored by the specified users.
	 * 
	 * @param usernames the users to ignore.
	 * @return a loader that will ignore the specified users.
	 */
	public SubmissionLoader ignoreUsers(String... usernames) {
		for (String user : usernames) {
			ignoredUsers.add(user);
		}
		return this;
	}

	/**
	 * Will not load submissions authored by the specified users.
	 * 
	 * @param users the users to ignore.
	 * @return a loader that will ignore the specified users.
	 */
	public SubmissionLoader ignoreUsers(User... users) {
		for (User user : users) {
			ignoredUsers.add(user.getName());
		}
		return this;
	}

	/**
	 * Loads the given submission using its URL.
	 * 
	 * @param submission
	 * @return the loded {@link Submission}
	 * @throws IOException there was an error loading the submission.
	 */
	public Submission load(Submission submission) throws IOException {
		return load(submission.getURL());
	}

	/**
	 * Loads the given URL into a {@link Submission} object.
	 * 
	 * @param url the URL to load.
	 * @return the {@link Submission} representation.
	 * @throws IOException there was an error loading the submission.
	 */

	public Submission load(String url) throws IOException {
		Document doc = Jsoup.connect(url).userAgent("PMCAPI").post();
		try {
			String type = getType(doc);
			switch (type) {
			case "Projects": {
				User author = getAuthor(doc);
				List<Comment> comments = getComments(doc);
				String title = getTitle(doc);

				String[] media = getMedia(doc, type);

				Object[] details = getDetails(doc);

				String[] downloads = getDownloads(doc, false);
				String download = downloads[0];
				String[] mirrors = new String[downloads.length - 1];
				System.arraycopy(downloads, 1, mirrors, 0, mirrors.length);

				int id = getID(doc);
				String[] tags = getTags(doc);
				Element desc = getDescription(doc);

				Project project = new Project(url, title, media, download, mirrors, desc, tags, author,
						(int) details[0], (int) details[1], (int) details[2], (int) details[3], id, comments,
						(LocalDateTime) details[4]);

				loadedSubmissions.put(url, project);
				return project;
			}
			case "Skins": {
				User author = getAuthor(doc);
				List<Comment> comments = getComments(doc);
				String title = getTitle(doc);

				String[] media = getMedia(doc, type);

				Object[] details = getDetails(doc);

				String[] downloads = getDownloads(doc, false);
				String download = downloads[0];
				String[] mirrors = new String[downloads.length - 1];
				System.arraycopy(downloads, 1, mirrors, 0, mirrors.length);

				int id = getID(doc);
				String[] tags = getTags(doc);
				Element desc = getDescription(doc);

				Skin skin = new Skin(url, title, media, download, mirrors, desc, tags, author, (int) details[0],
						(int) details[1], (int) details[2], (int) details[3], id, comments, (LocalDateTime) details[4]);

				loadedSubmissions.put(url, skin);
				return skin;
			}
			case "Texture Packs": {
				User author = getAuthor(doc);
				List<Comment> comments = getComments(doc);
				String title = getTitle(doc);

				String[] media = getMedia(doc, type);

				Object[] details = getDetails(doc);

				String[] downloads = getDownloads(doc, false);
				String download = downloads[0];
				String[] mirrors = new String[downloads.length - 1];
				System.arraycopy(downloads, 1, mirrors, 0, mirrors.length);

				int id = getID(doc);
				String[] tags = getTags(doc);
				Element desc = getDescription(doc);

				TexturePack pack = new TexturePack(url, title, media, download, mirrors, desc, tags, author,
						(int) details[0], (int) details[1], (int) details[2], (int) details[3], id, comments,
						(LocalDateTime) details[4]);
				loadedSubmissions.put(url, pack);
				return pack;
			}
			case "Servers": {

				User author = getAuthor(doc);
				List<Comment> comments = getComments(doc);
				String title = getTitle(doc);

				String[] media = getMedia(doc, type);

				Object[] details = getDetails(doc);
				int id = getID(doc);
				String[] tags = getTags(doc);
				Element desc = getDescription(doc);
				String ip = getServerIP(doc);

				Server server = new Server(url, title, ip, media, desc, tags, author, (int) details[0],
						(int) details[1], (int) details[2], (int) details[3], id, comments, (LocalDateTime) details[4]);

				loadedSubmissions.put(url, server);
				return server;
			}
			case "Mods": {
				User author = getAuthor(doc);
				List<Comment> comments = getComments(doc);
				String title = getTitle(doc);

				String[] media = getMedia(doc, type);

				Object[] details = getDetails(doc);

				String[] downloads = getDownloads(doc, false);
				String download = downloads[0];
				String[] mirrors = new String[downloads.length - 1];
				System.arraycopy(downloads, 1, mirrors, 0, mirrors.length);

				int id = getID(doc);
				String[] tags = getTags(doc);
				Element desc = getDescription(doc);

				Mod mod = new Mod(url, title, media, download, mirrors, desc, tags, author, (int) details[0],
						(int) details[1], (int) details[2], (int) details[3], id, comments, (LocalDateTime) details[4]);

				loadedSubmissions.put(url, mod);
				return mod;
			}
			case "Blogs": {
				User author = getAuthor(doc);
				List<Comment> comments = getComments(doc);
				String title = getTitle(doc);

				String[] media = getMedia(doc, type);

				Object[] details = getDetails(doc);
				int id = getID(doc);
				String[] tags = getTags(doc);
				Element desc = getDescription(doc);

				Blog blog = new Blog(url, title, media, desc, tags, author, (int) details[0], (int) details[1],
						(int) details[2], (int) details[3], id, comments, (LocalDateTime) details[4]);

				loadedSubmissions.put(url, blog);
				return blog;
			}
			}
		} catch (IndexOutOfBoundsException e) {
			throw new MissingPostException(url, e);
		}
		return null;
	}

	/**
	 * Loads all the given user's submissions.
	 * 
	 * @param user the user who's submissions to load.
	 * @return the submissions.
	 * @throws IOException there was an error loading the submissions.
	 */
	public List<Submission> loadUserSubmissions(User user) throws IOException {
		String userUrl = user.getURL();
		userUrl += userUrl.endsWith("/") ? "submissions/" : "/submissions/";
		Document doc = Jsoup.connect(userUrl).userAgent("PMCAPI").post();
		List<String> urls = getSubmissionURLs(doc);
		List<Submission> submissions = new ArrayList<Submission>();
		for (String url : urls) {
			if (loadedSubmissions.containsKey(url)) {
				submissions.add(loadedSubmissions.get(url));
			} else {
				submissions.add(getSubmission(url));
			}
		}

		return submissions;
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
	 * Get's the URLs to any media (videos/thumbnails) in the given submission's
	 * header.
	 * 
	 * @param doc  the submission document
	 * @param type the submission type as specified by
	 *             {@link SubmissionLoader#getType(Document)}.
	 * @return the media URLs.
	 */
	private String[] getMedia(Document doc, String type) {
		if (type.equalsIgnoreCase("Blogs")) {
			Element embed = doc.getElementById(ElementIdentifiers.RESOURCE_EMBED);
			Elements els = embed.getElementsByAttributeValue("name", ElementIdentifiers.EMBED_CODE);
			Element textarea = els.get(0);
			String text = textarea.text();
			Document d = Jsoup.parse(text);
			Elements els2 = d.getElementsByTag("img");
			Element img = els2.get(0);
			String thumbnail = img.attr("src");
			return new String[] { thumbnail };
		} else if (type.equalsIgnoreCase("Skins")) {
			Elements angels = doc.getElementsByClass(ElementIdentifiers.ANGELS);
			Element angel = angels.get(0);
			Elements imgs = angel.getElementsByTag("img");
			Element img = imgs.get(0);
			return new String[] { img.attr("src") };
		} else if (type.equalsIgnoreCase("Projects") || type.equalsIgnoreCase("Servers")
				|| type.equalsIgnoreCase("Projects") || type.equalsIgnoreCase("Mods")) {
			Elements imgs = doc.getElementsByClass(ElementIdentifiers.RESOURCE_IMG);
			if (!imgs.isEmpty()) {
				String[] thumbnails = imgs.stream().map(e -> e.attr("src")).toArray(String[]::new);
				Elements vids = doc.getElementsByClass(ElementIdentifiers.RESOURCE_NO_DRAG);
				if (!vids.isEmpty()) {
					String[] videos = vids.stream().map(e -> e.attr("data-rsVideo")).toArray(String[]::new);
					return Stream.of(thumbnails, videos).distinct().toArray(String[]::new);
				}
				return thumbnails;
			}
			Element gallery = doc.getElementById(ElementIdentifiers.VIDEO_GALLERY);
			if (gallery != null) {
				Elements vids = gallery.getElementsByTag("iframe");
				return vids.stream().map(e -> e.attr("src")).toArray(String[]::new);
			}
		}

		return new String[1];
	}

	/**
	 * Gets the comments on the given submission.
	 * 
	 * @param doc the submission document.
	 * @return the comments.
	 */
	private List<Comment> getComments(Document doc) {
		List<Comment> replies = new ArrayList<Comment>();

		Element container = doc.getElementById(ElementIdentifiers.COMMENTS);
		if (container != null) {
			for (Element comment : container.getElementsByClass(ElementIdentifiers.COMMENTITEM)) {
				Element header = comment.getElementsByClass(ElementIdentifiers.COMMENTHEADER).first();
				Element user = header.getElementsByTag("a").get(1);
				Element text = comment.getElementsByClass(ElementIdentifiers.COMMENTTEXT).first();
				text.select("br").append("\n");

				int id = Integer.parseInt(comment.getElementsByClass(ElementIdentifiers.COMMENTID).first().ownText());
				User author = new User(user.absUrl("href"));
				String content = text.text();
				LocalDateTime time = parseDateTime(header.getElementsByTag("abbr").first().attr("title"));

				Comment reply = new Comment(author, id, content, time);

				replies.add(reply);
			}
		}

		return replies;
	}

	/**
	 * Gets the description of the given submission.
	 * 
	 * @param doc the submission document.
	 * @return the description.
	 */
	private Element getDescription(Document doc) {
		return doc.getElementById(ElementIdentifiers.TEXT);
	}

	/**
	 * Gets the details of the given submission.
	 * 
	 * @param doc the submission details.
	 * @return an object array with the submission details in this order:<br>
	 *         <code>[diamonds, views, viewsToday, favorites, timestamp]</code>
	 */
	private Object[] getDetails(Document doc) {
		Element resourceInfo = doc.getElementById(ElementIdentifiers.DETAILS);
		Element data = resourceInfo.getElementsByClass(ElementIdentifiers.DATEDIV).first();

		LocalDateTime time = parseDateTime(data.getElementsByTag(ElementIdentifiers.DATETIME).first().attr("title"));

		Elements details = resourceInfo.getElementsByClass(ElementIdentifiers.DETAILSBOX).first()
				.getElementsByTag("span");

		int diamonds = Integer.parseInt(details.first().ownText());
		int views = Integer.parseInt(details.get(1).ownText().replaceAll(",", ""));
		int viewsToday = Integer.parseInt(details.get(2).ownText());
		int favorites = Integer.parseInt(details.get(4).ownText());

		return new Object[] { diamonds, views, viewsToday, favorites, time };
	}

	/**
	 * Gets the download URLs for the given submission.
	 * 
	 * @param doc  the submission document.
	 * @param skin used to indicate whether or not the invoker is requesting a skin
	 *             download URL.
	 * @return the download URL.
	 */
	private String[] getDownloads(Document doc, boolean skin) {
		Element download = doc.getElementsByClass(ElementIdentifiers.DOWNLOAD).first();
		if (download == null)
			return new String[] { "No download", "" };
		return download.getElementsByTag("a").stream().map(e -> e.absUrl("href")).toArray(String[]::new);
	}

	/**
	 * Gets the submission ID for the given submission.
	 * 
	 * @param doc the submission document.
	 * @return the ID.
	 */
	private int getID(Document doc) {
		return Integer.parseInt(doc.getElementById(ElementIdentifiers.SUBID).ownText());
	}

	/**
	 * Gets this submission's server IP (Assuming it's a server).
	 * 
	 * @param doc the submission's document.
	 * @return the IP.
	 */
	private String getServerIP(Document doc) {
		Element e = doc.getElementsByAttributeValue("name", ElementIdentifiers.SERVERIP).first();
		return e.getElementsByTag("input").attr("value");
	}

	/**
	 * Gets the unloaded submissions on the given page.
	 * 
	 * @param doc    the page document.
	 * @param amount the amount of URLs to get.
	 * @return the unloaded submissions.
	 */
	private List<Submission> getSubmissionPage(Document doc, int amount) {
		Elements submissions = doc.getElementsByClass(ElementIdentifiers.RINFO);
		List<Submission> subs = new ArrayList<Submission>();
		for (Element submission : submissions) {
			String author = submission.getElementsByClass(ElementIdentifiers.AUTHOR).first().getElementsByTag("a")
					.first().ownText();
			if (!ignoredUsers.contains(author)) {
				subs.add(new Submission(submission.getElementsByTag("a").first().absUrl("href")) {
				});
			}
		}
		return subs;
	}

	/**
	 * Gets the submission URLs from the given user submissions page.
	 * 
	 * @param doc the submissions document.
	 * @return the URLs.
	 */
	private List<String> getSubmissionURLs(Document doc) {
		Elements submissions = doc.getElementsByClass(ElementIdentifiers.RESOURCE);
		List<String> urls = new ArrayList<String>();
		for (Element submission : submissions) {
			urls.add(submission.getElementsByTag("a").first().absUrl("href"));
		}
		return urls;
	}

	/**
	 * Gets the given submission's tags.
	 * 
	 * @param doc the submission document.
	 * @return the tags.
	 */
	private String[] getTags(Document doc) {
		Element tags = doc.getElementById(ElementIdentifiers.TAGS);
		Elements names = tags.getElementsByTag("a");
		String[] strTags = new String[names.size()];
		for (int i = 0; i < names.size(); i++) {
			strTags[i] = names.get(i).ownText();
		}
		return strTags;
	}

	/**
	 * Gets the title of the given submission.
	 * 
	 * @param doc the submission document.
	 * @return the submission title.
	 */
	private String getTitle(Document doc) {
		return doc.getElementsByTag(ElementIdentifiers.STITLE).first().ownText();
	}

	/**
	 * Gets the category for the given submission.
	 * 
	 * @param doc the submission document.
	 * @return the category.
	 */
	private String getType(Document doc) {
		Elements category = doc.getElementsByClass(ElementIdentifiers.TYPE);
		return category.get(0).getElementsByTag("a").get(1).ownText();
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

	public static void main(String[] args) throws IOException {

	}
}
