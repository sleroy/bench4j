package com.tocea.frameworks.bench4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Returns the list of junit parameters.
 *
 * @author sleroy
 *
 */
public class GetJUnitParameters {

	private final FrameworkMethod	description;
	private final Object	      target;

	private static final Logger	  LOGGER	= LoggerFactory.getLogger(GetJUnitParameters.class);

	public GetJUnitParameters(FrameworkMethod _description, Object _target) {
		description = _description;
		target = _target;

	}

	public Map<String, Object> searchParameters() {
		final Map<String, Object> fields = new HashMap<String, Object>();
		for (final Field field : description.getDeclaringClass().getFields()) {
			if (field.isAnnotationPresent(Parameter.class)) {
				try {
					fields.put(field.getName(), field.get(target));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.error("Could not obtain the field {} in the junit test case, marks it public",
							field.getName(), e);
				}
			}
		}
		return fields;
	}

}
