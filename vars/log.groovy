import com.innogames.jenkinslib.container.Container
import com.innogames.jenkinslib.logger.LogLevel

def call(Object message, String level) {
	def container = Container.getInstance()
	println container
	def logger = container.getLogger()
	logger.log(message, LogLevel.valueOf(level))
}

def trace(Object message) {
	call(message, 'TRACE')
}

def debug(Object message) {
	call(message, 'DEBUG')
}

def info(Object message) {
	call(message, 'INFO')
}

def warn(Object message) {
	call(message, 'WARN')
}

def error(Object message) {
	call(message, 'ERROR')
}

def critical(Object message) {
	call(message, 'CRITICAL')
}
