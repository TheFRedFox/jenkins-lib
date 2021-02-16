import com.innogames.jenkinslib.container.Container
import com.innogames.jenkinslib.logger.LogLevel

def static log(Object message, String level) {
	def container = Container.getInstance()
	def logger = container.getLogger()
	logger.log(message, LogLevel.valueOf(level))
}

def static debug(Object message) {
	log(message, 'DEBUG')
}

def static info(Object message) {
	log(message, 'INFO')
}

def static warn(Object message) {
	log(message, 'WARN')
}

def static error(Object message) {
	log(message, 'ERROR')
}

def static critical(Object message) {
	log(message, 'CRITICAL')
}
