package com.gistlabs.mechanize.elements;

import com.gistlabs.mechanize.requestor.RequestBuilderFactory;

public class Link<Page extends RequestBuilderFactory<Page>> extends AbstractElement<Page> implements Element<Page> {

	public Page click() {
		if(hasAttribute("href"))
			return getPage().doRequest(href()).get();
		return null;
	}
	
	public String href() {
		return getPage().absoluteUrl(getAttribute("href"));
	}
}
