/**
 * Copyright (C) 2012 Gist Labs, LLC. (http://gistlabs.com)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.gistlabs.mechanize.document.query;

import java.util.Arrays;

class QueryPart {
	private final boolean isAnd;
	private final String [] attributeNames;
	private final Pattern pattern;
	
	public QueryPart(boolean isAnd, String [] attributeNames, Pattern pattern) {
		this.isAnd = isAnd;
		this.attributeNames = attributeNames;
		this.pattern = pattern;
	}
	
	public QueryPart(boolean isAnd, String attributeName, Pattern pattern) {
		this(isAnd, AbstractQueryBuilder.attributes(attributeName), pattern);
	}
	
	@Override
	public String toString() {
		return "<" + pattern.toString() + "," + 
				((attributeNames.length == 1) ? "[" + attributeNames[0] + "]" : Arrays.toString(attributeNames))  + ">";
	}
	
	public boolean matches(QueryStrategy queryStrategy, Object object) {
		for(String attributeName : attributeNames) {
			if(!attributeName.equals("*")) {
				if(doesMatch(queryStrategy, object, attributeName))
					return true;
			} 
			else {
				for(String name : queryStrategy.getAttributeNames(object))
					if(!name.startsWith("${") && doesMatch(queryStrategy, object, name))
						return true;
			}
		}

		return false; 
	}

	private boolean doesMatch(QueryStrategy queryStrategy, Object object, String attributeName) {
		String value = queryStrategy.getAttributeValue(object, attributeName);
		if(queryStrategy.isMultipleValueAttribute(object, attributeName)) {
			for(String valueItem : value.split("\\,"))
				if(pattern.doesMatch(valueItem))
					return true;
			return false;
		}
		else 
			return pattern.doesMatch(value);
	}
	
	public boolean isAnd() {
		return isAnd;
	}
}