package com.Cardinal.PMC;

import java.io.IOException;

import com.Cardinal.PMC.Forums.Thread;
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

	public static void main(String[] args) throws IOException {
		PlanetMinecraft pmc = new PlanetMinecraft();
		ThreadLoader loader = pmc.getForums();
		Thread t = loader.load("https://www.planetminecraft.com/forums/archive/games/you-are-banned-585413/");
		System.out.println(t.toPrettyString());
	}

	static {
		System.err.println(
				"--- PMC-API ---\n(https://github.com/TheCardinalSystem/PMC-API/)\nAuthor: Cardinal System\nVersion: 0.0.1 BETA\nCredits: Powered by JSoup (https://jsoup.org/)\n--- PMC-API ---");
	}

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
