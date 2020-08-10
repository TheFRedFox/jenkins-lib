import com.innogames.jenkinslib.container.Container
import org.jenkinsci.plugins.workflow.cps.CpsScript

def call(CpsScript script = null) {
	def container = Container.getInstance()
	if (container == null) {
		container = new Container()
		container.init(script)
	}

	return container
}
