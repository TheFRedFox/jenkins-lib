package com.innogames.jenkinslib.container


import com.innogames.jenkinslib.debdrop.DebDropService
import com.innogames.jenkinslib.io.EnvService
import com.innogames.jenkinslib.logger.Logger
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.cps.CpsScript

import java.lang.reflect.Constructor

class Container {

	static def instance = null

	CpsScript scriptInstance = null

	static Jenkins jenkinsInstance = Jenkins.get()

	def identifier = [
		'debug'  : Logger.class,
		'debDrop': DebDropService.class,
		'script' : CpsScript.class
	]

	def classes = [
		Logger.class,
		DebDropService.class,
		CpsScript
	]

	def instances = [:]

	def init(CpsScript script) {
		if (script == null) {
			throw new NullPointerException('script has to be specified')
		}

		scriptInstance = script
	}

	def <T> T getComponent(Class<T> clazz) {
		if (clazz.isAssignableFrom(CpsScript.class)) {
			return this.getScript()
		}

		if (instances.containsKey(clazz)) {
			return instances.get(clazz)
		}

		def service = autowire(clazz)

		return service
	}

	def getLogger() {
		return getComponent(Logger.class)
	}

	def getDebDropService() {
		return getComponent(DebDropService.class)
	}

	def getEnvService() {
		return getComponent(EnvService.class)
	}

	def getScript() {
		return this.scriptInstance
	}

	def autowire(Class clazz) {
		def constructors = clazz.getConstructors()
		for (def constructor in constructors) {
			if (constructor.isAnnotationPresent(Autowire.class)) {
				return autowire(constructor)
			}
		}

		throw new IllegalArgumentException("Service class with name '${clazz.name}' could not be found.")
	}

	def autowire(Constructor constructor) {
		def parameters = constructor.getParameterTypes()
		def gottenParameters = []
		for (parameter in parameters) {
			gottenParameters.add(getComponent(parameter))
		}

		return constructor.newInstance(gottenParameters.toArray())
	}

	static Container getInstance() {
		return instance
	}

	static Container newInstance(CpsScript script) {
		def container = new Container()
		container.init(script)
		instance = container
		return container
	}

}
