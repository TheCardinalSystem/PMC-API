package com.Cardinal.PMC.lang;

/**
 * An exception thrown when a requested resource has not been loaded.
 * 
 * @author Cardinal System
 *
 */
public class UnloadedResourceExcpetion extends IllegalStateException {

	private static final long serialVersionUID = -3728221093482926022L;

	/**
	 * Constructs a new {@link UnloadedResourceExcpetion} with the given resource
	 * URL.
	 * 
	 * @param url
	 *            the URL of the unloaded resource.
	 * @param request
	 *            the requested resource data.
	 */
	public UnloadedResourceExcpetion(String url, String request) {
		super("Cannot obtain value \"" + request + "\" from the unloaded resource: " + url);
	}
}
