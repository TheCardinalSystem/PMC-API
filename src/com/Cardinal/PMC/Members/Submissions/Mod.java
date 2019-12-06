package com.Cardinal.PMC.Members.Submissions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;

import com.Cardinal.PMC.Members.User;

/**
 * A class used to represent a mod submission.
 * 
 * @author Cardinal System
 *
 */
public class Mod extends DownloadableSubmission {

	/**
	 * Constructs a new {@link Mod} with the given data.
	 * 
	 * @param url             the mod's URL.
	 * @param title           the mod's title.
	 * @param media           URLs to this project's video(s)/thumbnail(s)
	 * @param downloadurl     the mod's download URL.
	 * @param mirrorDownloads alternative download URLs.
	 * @param description     the mod's description.
	 * @param tags            the mod's tags/keywords.
	 * @param author          the mod's author.
	 * @param diamonds        the mod's diamonds/votes.
	 * @param views           the mod's views.
	 * @param viewsToday      the mod's views today.
	 * @param favorites       the mod's favorites.
	 * @param iD              the mod's ID.
	 * @param comments        the mod's comments.
	 * @param timestamp       the mod's submission date.
	 */
	public Mod(String url, String title, String[] media, String downloadurl, String[] mirrorDownloads,
			Element description, String[] tags, User author, int diamonds, int views, int viewsToday, int favorites,
			int iD, List<Comment> comments, LocalDateTime timestamp) {
		super(url);
		this.type = Type.MODS;
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
	}

	@Override
	public String toString() {
		return "ID: " + ID + "\nType: Mod\nURL: " + url + "\nMedia: "
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
