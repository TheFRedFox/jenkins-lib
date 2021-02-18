import com.innogames.jenkinslib.container.Container
import com.innogames.jenkinslib.logger.LogLevel

def static call(Object message, LogLevel level) {
	def container = Container.getInstance()
	def logger = container.getLogger()
	logger.log(message, level)
}

def static call(Object message, String level) {
	call(message, LogLevel.valueOf(level))
}

def static trace(Object message) {
	call(message, LogLevel.TRACE)
}

def static debug(Object message) {
	call(message, LogLevel.DEBUG)
}

def static info(Object message) {
	call(message, LogLevel.INFO)
}

def static warn(Object message) {
	call(message, LogLevel.WARN)
}

def static error(Object message) {
	call(message, LogLevel.ERROR)
}

def static critical(Object message) {
	call(message, LogLevel.CRITICAL)
}
