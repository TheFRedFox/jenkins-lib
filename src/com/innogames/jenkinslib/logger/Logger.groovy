package com.innogames.jenkinslib.logger

import com.innogames.jenkinslib.container.Autowire
import com.innogames.jenkinslib.container.Value
import org.codehaus.groovy.runtime.DefaultGroovyMethods
import org.jenkinsci.plugins.workflow.cps.CpsScript

class Logger {

	CpsScript script

	LogLevel level

	@Autowire
	Logger(CpsScript script, @Value(value = 'logging.level') LogLevel logLevel) {
		this.script = script
		this.level = logLevel ?: LogLevel.INFO
	}

	def log(Object message, LogLevel level = LogLevel.INFO) {
		if (level < this.level) {
			return
		}

		def msg = message
		if (!msg instanceof String) {
			msg = String.valueOf(msg)
		}
		DefaultGroovyMethods.println(msg)
		script.println(msg)
	}

	def error(Object message, LogLevel level = LogLevel.ERROR) {
		if (level < this.level) {
			return
		}

		def msg = message
		if (!msg instanceof String) {
			msg = String.valueOf(msg)
		}

		script.sh("echo '${msg}' 1>&2")
	}

}