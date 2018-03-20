package com.Cardinal.PMC.lang;

import java.io.IOException;

/**
 * Thrown when a PMC post is unable to be loaded from a URL.
 * 
 * @author Cardinal System
 *
 */
public class MissingPostException extends IOException {

	private static final long serialVersionUID = 5549552015558186707L;

	/**
	 * Constructs a new {@link MissingPostException} for the given thread URL.
	 * 
	 * @param url
	 *            the URL of the invalid or missing thread.
	 */
	public MissingPostException(String url) {
		super("Missing or invalid post: " + url);
	}

	/**
	 * Constructs a new {@link MissingPostException} for the given thread URL.
	 * 
	 * @param url
	 *            the URL of the invalid or missing thread.
	 * @param cause
	 *            the exception that caused this exception.
	 */
	public MissingPostException(String url, Throwable cause) {
		super("Missing or invalid post: " + url, cause);
	}

}
