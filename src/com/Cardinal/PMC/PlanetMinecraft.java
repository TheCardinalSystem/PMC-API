package com.Cardinal.PMC;

import com.Cardinal.PMC.Forums.ThreadLoader;
import com.Cardinal.PMC.Members.MemberManager;
import com.Cardinal.PMC.Members.Submissions.SubmissionLoader;

/**
 * A class used to represent
 * <a href='https://www.planetminecraft.com/'>PlanetMinecraft</a>.
 * 
 * @author Cardinal System
 *
 */
public class PlanetMinecraft {

	private ThreadLoader threadLoader;
	private SubmissionLoader subLoader;
	private MemberManager memberManager;

	/**
	 * Constructs a new {@link PlanetMinecraft}.
	 */
	public PlanetMinecraft() {
		this.threadLoader = new ThreadLoader();
		this.subLoader = new SubmissionLoader();
		this.memberManager = new MemberManager();
	}

	/**
	 * Gets the forum manager for this PMC instance.
	 * 
	 * @return the forum manager.
	 */
	public ThreadLoader getForums() {
		return threadLoader;
	}

	/**
	 * Gets the submissions manager for this PMC instance.
	 * 
	 * @return the submissions manager.
	 */
	public SubmissionLoader getSubmissions() {
		return subLoader;
	}

	/**
	 * Gets the user manager for this PMC instance.
	 * 
	 * @return the user manager.
	 */
	public MemberManager getMemberManager() {
		return memberManager;
	}

}
