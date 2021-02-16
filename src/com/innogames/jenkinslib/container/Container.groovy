package com.innogames.jenkinslib.container


import com.innogames.jenkinslib.debdrop.DebDropService
import com.innogames.jenkinslib.io.EnvService
import com.innogames.jenkinslib.logger.Logger
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.cps.CpsScript

import java.lang.reflect.Constructor
import java.lang.reflect.Parameter

class Container {

	static def instance = null

	CpsScript scriptInstance = null

	Map<String, Object> config = null

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

	def init(CpsScript script, Map<String, Object> config = [:]) {
		if (script == null) {
			throw new NullPointerException('script has to be specified')
		}

		this.scriptInstance = script
		this.config = config
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
		def parameters = constructor.getParameters()
		def gottenParameters = []
		for (parameter in parameters) {
			gottenParameters.add(autowire(parameter))
		}

		return constructor.newInstance(gottenParameters.toArray())
	}

	def autowire(Parameter parameter) {
		if (parameter.isAnnotationPresent(Value.class)) {
			def annotation = parameter.getAnnotation(Value.class)
			def configName = annotation.value()
			if (!this.config.containsKey(configName)) {
				return null
			}

			def value = this.config.get(configName)
			return convert(parameter.type, value)
		}

		return getComponent(parameter.type)
	}

	def convert(Class type, Object value) {

		if (type == String.class) {
			return value as String
		}

		if (type.isEnum()) {
			return type.invokeMethod('valueOf', value as String)
		}

		if (type == Integer.class) {
			return Integer.valueOf(value as String)
		}

		if (type == int.class) {
			return Integer.parseInt(value as String)
		}


	}

	static Container getInstance() {
		return instance
	}

	static Container newInstance(CpsScript script, Map<String, Object> config = [:]) {
		def container = new Container()
		container.init(script, config)
		instance = container
		return container
	}

}
