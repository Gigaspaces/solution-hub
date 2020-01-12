package com.gigaspaces.droolsintegration.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class IterableMapWrapper implements Iterable<Object>, Serializable {

	private static final long serialVersionUID = 7263210561692508552L;
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public Object get(String key) {
		return map.get(key);
	}
	
	public void put(String key, Object value) {
		map.put(key, value);
	}
	
	public void clear() {
		map.clear();
	}

	public Iterator<Object> iterator() {
		return map.values().iterator();
	}
	
	public Set<String> keySet() {
		return map.keySet();
	}
	
	public void remove(String key) {
		map.remove(key);
	}
	
	public int size() {
		return map.size();
	}
	
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}
	
	public boolean containsValue(String value) {
		return map.containsValue(value);
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
}