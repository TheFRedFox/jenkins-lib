package com.innogames.jenkinslib.container

class Config implements Map<String, Object> {

	Map<String, Object> config

	Config(Map<String, Object> config) {
		this.config = config
	}

	@Override
	int size() {
		return this.config.size()
	}

	@Override
	boolean isEmpty() {
		return this.config.isEmpty()
	}

	@Override
	boolean containsKey(Object key) {
		return this.config.containsKey(key)
	}

	@Override
	boolean containsValue(Object value) {
		return this.config.containsValue(value)
	}

	@Override
	Object get(Object key) {
		return this.config.get(key)
	}

	@Override
	Object put(String key, Object value) {
		return this.config.put(key, value)
	}

	@Override
	Object remove(Object key) {
		return this.config.remove(key)
	}

	@Override
	void putAll(Map<? extends String, ?> m) {
		this.config.putAll(m)
	}

	@Override
	void clear() {
		this.config.clear()
	}

	@Override
	Set<String> keySet() {
		return this.config.keySet()
	}

	@Override
	Collection<Object> values() {
		return this.config.values()
	}

	@Override
	Set<Entry<String, Object>> entrySet() {
		return this.config.entrySet()
	}

}
