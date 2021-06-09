import com.innogames.jenkinslib.container.Container
import com.innogames.jenkinslib.logger.LogLevel

def call(Object message, LogLevel level) {
	def container = Container.getInstance()
	def logger = container.getLogger()
	logger.log(message, level)
}

def call(Object message) {
	call(message, (LogLevel) null as LogLevel)
}

def call(Object message, String level) {
	LogLevel logLevel = null
	if (level != null) {
		logLevel = LogLevel.valueOf(level)
	}
	call(message, (LogLevel) logLevel as LogLevel)
}

def trace(Object message) {
	call(message, LogLevel.TRACE)
}

def debug(Object message) {
	call(message, LogLevel.DEBUG)
}

def info(Object message) {
	call(message, LogLevel.INFO)
}

def warn(Object message) {
	call(message, LogLevel.WARN)
}

def error(Object message) {
	call(message, LogLevel.ERROR)
}

def critical(Object message) {
	call(message, LogLevel.CRITICAL)
}
