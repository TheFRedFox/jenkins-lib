package com.innogames.jenkinslib.io

import com.innogames.jenkinslib.container.Autowire
import org.jenkinsci.plugins.workflow.cps.CpsScript

class ShellService {

	CpsScript script

	@Autowire
	ShellService(CpsScript script) {
		this.script = script
	}

	int sh(String command) {
		return script.sh(script: command, returnStatus: true)
	}

	String shVerbose(String command) {
		return script.sh(script: command, returnStdout: true)
	}

	String pwd() {
		return shVerbose('pwd')
	}

}
