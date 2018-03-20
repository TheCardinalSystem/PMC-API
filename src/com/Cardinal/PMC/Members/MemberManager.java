package com.Cardinal.PMC.Members;

import com.Cardinal.PMC.Members.Walls.WallPostLoader;

/**
 * A class used to organize user related loading.
 * 
 * @author Cardinal System
 *
 */
public class MemberManager {

	private UserLoader userLoader;
	private WallPostLoader wallLoader;

	/**
	 * Constructs a new {@link MemberManager}.
	 */
	public MemberManager() {
		userLoader = new UserLoader();
		wallLoader = new WallPostLoader();
	}

	/**
	 * Gets the user manager.
	 * 
	 * @return the user manager.
	 */
	public UserLoader getUserManager() {
		return userLoader;
	}

	/**
	 * Gets the wall post manager.
	 * 
	 * @return the wall post manager.
	 */
	public WallPostLoader getWallManager() {
		return wallLoader;
	}

}
