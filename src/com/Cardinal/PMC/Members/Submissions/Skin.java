package com.Cardinal.PMC.Members.Submissions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;

import com.Cardinal.PMC.Members.User;

/**
 * A class used to represent a skin submission.
 * 
 * @author Cardinal System
 *
 */
public class Skin extends DownloadableSubmission {

	/**
	 * Constructs a new {@link Skin} with the given data.
	 * 
	 * @param url             the skin's URL.
	 * @param title           the skin's title.
	 * @param media           URLs to this project's video(s)/thumbnail(s)
	 * @param downloadurl     the skin's download URL.
	 * @param mirrorDownloads alternative download URLs.
	 * @param description     the skin's description.
	 * @param tags            the skin's tags/keywords.
	 * @param author          the skin's author.
	 * @param diamonds        the skin's diamonds/votes.
	 * @param views           the skin's views.
	 * @param viewsToday      the skin's views today.
	 * @param favorites       the skin's favorites.
	 * @param iD              the skin's ID.
	 * @param comments        the skin's comments.
	 * @param timestamp       the skin's submission date.
	 */
	public Skin(String url, String title, String[] media, String downloadurl, String[] mirrorDownloads,
			Element description, String[] tags, User author, int diamonds, int views, int viewsToday, int favorites,
			int iD, List<Comment> comments, LocalDateTime timestamp) {
		super(url);
		this.url = url;
		this.title = title;
		this.media = media;
		this.downloadUrl = downloadurl;
		this.mirrorDownloads = mirrorDownloads;
		this.description = description;
		this.tags = tags;
		this.author = author;
		this.diamonds = diamonds;
		this.views = views;
		this.viewsToday = viewsToday;
		this.favorites = favorites;
		ID = iD;
		this.comments = comments;
		this.timestamp = timestamp;
		this.type = Type.SKINS;
	}

	@Override
	public String toString() {
		return "ID: " + ID + "\nType: Skin\nURL: " + url + "\nMedia: "
				+ Arrays.toString(media != null ? media : new String[1]) + "\nTitle: " + title + "\nAuthor: " + author
				+ "\nTime: " + timestamp.format(FORMATTER) + "\nDiamonds: " + diamonds + "\nViews: " + views + " | "
				+ viewsToday + " today\nFavorites: " + favorites + "\nDownload: " + downloadUrl + "\nDownload Mirrors:"
				+ Arrays.toString(mirrorDownloads != null ? mirrorDownloads : new String[1]) + "\nTags: "
				+ Arrays.toString(tags) + "\nDesc: [\n\t" + description.text().replaceAll("\n", "\n\t")
				+ "\n]\nComments: {\n\t"
				+ comments.stream().map(c -> c.toString()).collect(Collectors.joining("\n\n")).replaceAll("\n", "\n\t")
				+ "\n}";

	}
}
