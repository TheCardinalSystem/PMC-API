package com.Cardinal.PMC.Forums;

/**
 * An enumeration of the different forums thread categories.
 * 
 * @author Cardinal System
 *
 */
public enum Category {

	/**
	 * Introduce yourself. You're among mining and crafting addicts. Welcome.
	 */
	INTRODUCTIONS,
	/**
	 * Requests and discussions about the site.
	 */
	PMCDISCUSSION,
	/**
	 * Set up shop to offer your free creative services to the community. Skins,
	 * Projects, Textures, Art and other wares.
	 */
	WORKSHOP,
	/**
	 * Challenge the community creativity with your own event.
	 */
	CHALLENGES,
	/**
	 * Discuss the game, stories, screenshots, videos, ideas, creations, top score!?
	 */
	MCDISCUSSION,
	/**
	 * Did you mess up your Minecraft? Ask for help here!
	 */
	HELP,
	/**
	 * Are you starting a new project and are looking for members, or are you
	 * looking for something to join? Here's the spot!
	 */
	TEAMS,
	/**
	 * Discuss the various console versions here.
	 */
	CONSOLE,
	/**
	 * Discuss the Pocket edition of Minecraft here!
	 */
	PORTABLE,
	/**
	 * Made an awesome fortress? Guess where you can talk about it! (psst: here)
	 */
	PROJECTS,
	/**
	 * Discuss your skinning adventures here!
	 */
	SKINS,
	/**
	 * Discuss your Texture Packs here!
	 */
	RESOURCEPACKS,
	/**
	 * Find and Share Minecraft Servers.
	 */
	SERVERS,
	/**
	 * Such an intricate part of Minecraft that it deserves a whole section
	 * dedicated to it. Post your most amazing Redstone creations here!
	 */
	REDSTONE,
	/**
	 * Calling all programmers: your mods go here!
	 */
	MODS,
	/**
	 * So popular, we had to create a section for it! Post your hubs here.
	 */
	HUBS,
	/**
	 * We tend to be a creative breed of gamer. Show off your artistic side.
	 */
	ARTISTS,
	/**
	 * Have you created or found an amazing YouTube video? Have you recently
	 * discovered an amazing YouTuber? Post it here!
	 */
	YOUTUBE,
	/**
	 * Roleplaying is a large part of PMC. Post your roleplays here!
	 */
	ROLEPLAYS,
	/**
	 * Any other forum-related games should go here.
	 */
	GAMES,
	/**
	 * Anything that isn't Minecraft-related or doesn't go anywhere above goes here.
	 */
	GENERAL,
	/**
	 * Is there a new computer coming out that you are getting? New games? Post
	 * here!
	 */
	GAMING,
	/**
	 * Have you made something for a different game or in real life? Post here!
	 */
	CREATIONS;

	/**
	 * Gets the {@link Category} instance of the given category location.<br>
	 * For example:<br>
	 * <code>"/forums/discussions/creations/" = Category.CREATIONS</code><br>
	 * <code>"/forums/pmc/introductions/" = Category.INTRODUCTIONS</code>
	 * 
	 * @param s
	 *            the location of the category.
	 * @return the category instance.
	 */
	public static Category parseCategory(String s) {
		switch (s) {
		case "/forums/pmc/introductions/":
			return INTRODUCTIONS;
		case "/forums/pmc/discussion/":
			return PMCDISCUSSION;
		case "/forums/pmc/workshop/":
			return WORKSHOP;
		case "/forums/pmc/challenges/":
			return CHALLENGES;
		case "/forums/minecraft/discussion/":
			return MCDISCUSSION;
		case "/forums/minecraft/help/":
			return HELP;
		case "/forums/minecraft/teams/":
			return TEAMS;
		case "/forums/minecraft/console/":
			return CONSOLE;
		case "/forums/minecraft/portable/":
			return PORTABLE;
		case "/forums/minecraft/projects/":
			return PROJECTS;
		case "/forums/minecraft/skins/":
			return SKINS;
		case "/forums/minecraft/resourcepacks/":
			return RESOURCEPACKS;
		case "/forums/minecraft/servers/":
			return SERVERS;
		case "/forums/minecraft/redstone/":
			return REDSTONE;
		case "/forums/minecraft/mods/":
			return MODS;
		case "/forums/communities/hubs/":
			return HUBS;
		case "/forums/communities/artists/":
			return ARTISTS;
		case "/forums/communities/youtube/":
			return YOUTUBE;
		case "/forums/communities/roleplays/":
			return ROLEPLAYS;
		case "/forums/communities/games/":
			return GAMES;
		case "/forums/discussions/general/":
			return GENERAL;
		case "/forums/discussions/gaming/":
			return GAMING;
		case "/forums/discussions/creations/":
			return CREATIONS;
		}
		return null;
	}

	@Override
	public String toString() {
		switch (this) {
		case INTRODUCTIONS:
			return "Forums / PMC / Introductions";
		case PMCDISCUSSION:
			return "Forums / PMC / Discussion";
		case WORKSHOP:
			return "Forums / PMC / Workshop";
		case CHALLENGES:
			return "Forums / PMC / Challenges";
		case MCDISCUSSION:
			return "Forums / Minecraft / Discussion";
		case HELP:
			return "Forums / Minecraft / Help";
		case TEAMS:
			return "Forums / Minecraft / Teams";
		case CONSOLE:
			return "Forums / Minecraft / Console/";
		case PORTABLE:
			return "Forums / Minecraft / Portable/";
		case PROJECTS:
			return "Forums / Minecraft / Projects/";
		case SKINS:
			return "Forums / Minecraft / Skins";
		case RESOURCEPACKS:
			return "Forums / Minecraft  / Resourcepacks";
		case SERVERS:
			return "Forums / Minecraft / Servers";
		case REDSTONE:
			return "Forums / Minecraft / Redstone";
		case MODS:
			return "Forums / Minecraft / Mods";
		case HUBS:
			return "Forums / Communities / Hubs";
		case ARTISTS:
			return "Forums / Communities / Artists";
		case YOUTUBE:
			return "Forums / Communities / Youtube";
		case ROLEPLAYS:
			return "Forums / Communities / Roleplays";
		case GAMES:
			return "Forums / Communities / Games";
		case GENERAL:
			return "Forums / Discussions / General";
		case GAMING:
			return "Forums / Discussions / Gaming";
		case CREATIONS:
			return "Forums / Discussions / Creations";
		}
		return null;
	}

	/**
	 * Gets this category in the form of a <code>href</code> attribute value.
	 * 
	 * @return Example:<br>
	 *         <code>Category.CREATIONS = "/forums/discussions/creations"</code><br>
	 *         <code>Category.INTRODUCTIONS = "/forums/pmc/introductions"</code>
	 */
	public String toHREF() {
		return "/" + toString().toLowerCase().replaceAll("\\s", "");
	}

}
