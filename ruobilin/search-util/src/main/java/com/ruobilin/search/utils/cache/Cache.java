package com.ruobilin.search.utils.cache;

public interface Cache {
	void put(String key, Object data, int timeout);
	
	Object get(String key);
}
