package com.innogames.jenkinslib.container

class Config {

	@Delegate
	Map<String, Object> config

	Config(Map<String, Object> config) {
		this.config = config
	}

}
