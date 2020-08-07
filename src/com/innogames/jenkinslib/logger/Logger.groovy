package com.innogames.jenkinslib.logger

import com.innogames.jenkinslib.container.Autowire
import org.codehaus.groovy.runtime.DefaultGroovyMethods
import org.jenkinsci.plugins.workflow.cps.CpsScript

class Logger {

	CpsScript script

	@Autowire
	Logger(CpsScript script) {
		this.script = script
	}

	def log(Object message) {
		def msg = message
		if(!msg instanceof String) {
			msg = String.valueOf(msg)
		}
		DefaultGroovyMethods.println(msg)
		script.println msg
	}

	def error(Object message) {
		def msg = message
		if(!msg instanceof String) {
			msg = String.valueOf(msg)
		}

		script.sh "echo '${msg}' 1>&2"
	}

}