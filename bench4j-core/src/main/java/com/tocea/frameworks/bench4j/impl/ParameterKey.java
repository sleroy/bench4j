/**
 *
 */
package com.tocea.frameworks.bench4j.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author sleroy
 *
 */
public class ParameterKey {

	private final Map<String, Object>	parameters;

	/**
	 * @param _parameters
	 */
	public ParameterKey(Map<String, Object> _parameters) {
		parameters = _parameters;

	}

	/**
	 * Returns a key representing the parameters.
	 *
	 * @return the key.
	 */

	public String get() {
		/**
		 * Gets the map as a String.
		 *
		 * @return a string version of the map
		 */
		if (parameters.size() == 0) { return "default"; }
		final StringBuilder buf = new StringBuilder(32 * parameters.size());

		final Iterator<Entry<String, Object>> it = parameters.entrySet().iterator();
		boolean hasNext = it.hasNext();
		while (hasNext) {
			final Entry<String, Object> iter = it.next();
			final Object value = iter.getValue();
			final String key = iter.getKey();
			buf.append(key).append('=').append(value == parameters ? "(this)" : value);

			hasNext = it.hasNext();
			if (hasNext) {
				buf.append(',');
			}
		}

		return buf.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return get();
	}
}
