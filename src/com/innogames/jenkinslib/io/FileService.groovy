package com.innogames.jenkinslib.io

import com.innogames.jenkinslib.container.Autowire
import hudson.FilePath
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.cps.CpsScript

class FileService {

	CpsScript script

	EnvService envService

	@Autowire
	FileService(CpsScript script, EnvService envService) {
		this.script = script
		this.envService = envService
	}

	def localFile(String localPath) {
		String path = script.env.WORKSPACE + '/' + localPath
		return file(path)
	}

	def file(String path) {
		def nodeName = script.env['NODE_NAME']
		if (nodeName == null) {
			throw new IllegalArgumentException("envvar NODE_NAME is not set, probably not inside an node {} or running an older version of Jenkins!")
		}

		if (nodeName.equals("master")) {
			return new FilePath(new File(path))
		} else {
			return new FilePath(Jenkins.get().getComputer(nodeName).getChannel(), path)
		}
	}

}
