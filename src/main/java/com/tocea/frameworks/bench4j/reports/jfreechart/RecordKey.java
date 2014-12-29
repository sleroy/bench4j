package com.tocea.frameworks.bench4j.reports.jfreechart;

import java.util.Collections;
import java.util.Map;

import org.junit.runners.model.FrameworkMethod;

public class RecordKey {
	private final String	          className;

	private String	                  methodName;

	private final Map<String, Object>	parameters;

	public RecordKey(FrameworkMethod _description, Map<String, Object> _parameterKey) {
		methodName = _description.getName();
		className = _description.getDeclaringClass().getName();
		parameters = Collections.unmodifiableMap(_parameterKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		final RecordKey other = (RecordKey) obj;
		if (className == null) {
			if (other.className != null) { return false; }
		} else if (!className.equals(other.className)) { return false; }
		if (methodName == null) {
			if (other.methodName != null) { return false; }
		} else if (!methodName.equals(other.methodName)) { return false; }
		if (parameters == null) {
			if (other.parameters != null) { return false; }
		} else if (!parameters.equals(other.parameters)) { return false; }
		return true;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (className == null ? 0 : className.hashCode());
		result = prime * result + (methodName == null ? 0 : methodName.hashCode());
		result = prime * result + (parameters == null ? 0 : parameters.hashCode());
		return result;
	}

	public void setMethodName(String _methodName) {
		methodName = _methodName;
	}

	@Override
	public String toString() {
		return "RecordKey [className=" + className + ", methodName=" + methodName + ", parameters=" + parameters + "]";
	}
}
