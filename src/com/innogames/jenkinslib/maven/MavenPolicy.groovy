package com.innogames.jenkinslib.maven

import groovy.transform.EqualsAndHashCode
import groovy.xml.MarkupBuilder

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = 'policy')
@EqualsAndHashCode
class MavenPolicy {

	Boolean enabled

	// updatePolicy always, daily, interval:X, never
	String updatePolicy

	// checksumPolicy always, daily, interval:X, never
	String checksumPolicy

	def toXML() {
		def writer = new StringWriter()

		JAXBContext context = JAXBContext.newInstance(MavenPolicy.class)
		Marshaller m = context.createMarshaller()
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE)
		m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE)
		m.marshal(this, writer)

		return writer.toString()

		def markup = new MarkupBuilder(writer)
		markup."$root" {
			markup.enabled(this.enabled)
			markup.updatePolicy(this.updatePolicy)
			markup.checksumPolicy(this.checksumPolicy)
		}

		return writer.toString()
	}

}
