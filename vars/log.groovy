import com.innogames.jenkinslib.container.Container
import com.innogames.jenkinslib.logger.LogLevel

def static call(Object message, String level) {
	def container = Container.getInstance()
	def logger = container.getLogger()
	logger.log(message, LogLevel.valueOf(level))
}

def static debug(Object message) {
	call(message, 'DEBUG')
}

def static info(Object message) {
	call(message, 'INFO')
}

def static warn(Object message) {
	call(message, 'WARN')
}

def static error(Object message) {
	call(message, 'ERROR')
}

def static critical(Object message) {
	call(message, 'CRITICAL')
}
