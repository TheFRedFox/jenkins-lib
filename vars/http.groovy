#!/usr/bin/groovy
import com.innogames.jenkinslib.container.Container
import com.innogames.jenkinslib.http.HttpService

def String get(String url) {
	def httpService = Container.getInstance().getComponent(HttpService)
	return httpService.httpGet(url)
}

def String post(String url, String payload) {
	def httpService = Container.getInstance().getComponent(HttpService)
	return httpService.httpPost(url, payload)
}

def String put(String url, String payload) {
	def httpService = Container.getInstance().getComponent(HttpService)
	return httpService.httpPut(url, payload)
}

def String delete(String url) {
	def httpService = Container.getInstance().getComponent(HttpService)
	return httpService.httpDelete(url)
}
