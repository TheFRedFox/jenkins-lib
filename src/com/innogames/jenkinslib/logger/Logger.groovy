package com.innogames.jenkinslib.logger

import com.innogames.jenkinslib.container.Autowire
import com.innogames.jenkinslib.container.Value
import org.codehaus.groovy.runtime.DefaultGroovyMethods
import org.jenkinsci.plugins.workflow.cps.CpsScript

class Logger {

	CpsScript script

	public LogLevel level

	@Autowire
	Logger(CpsScript script, @Value(value = 'logging.level', defaultValue = LogLevel.INFO) LogLevel logLevel) {
		this.script = script
		this.level = logLevel
	}

	def log(Object message) {
		def msg = message
		if (!msg instanceof String) {
			msg = String.valueOf(msg)
		}
		DefaultGroovyMethods.println(msg)
		script.println msg
	}

	def error(Object message) {
		def msg = message
		if (!msg instanceof String) {
			msg = String.valueOf(msg)
		}

		script.sh "echo '${msg}' 1>&2"
	}

	def getLevel() {
		return this.level
	}

}