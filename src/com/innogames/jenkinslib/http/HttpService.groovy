package com.innogames.jenkinslib.http

import com.cloudbees.groovy.cps.NonCPS
import com.innogames.jenkinslib.http.auth.AuthInterface
import com.innogames.jenkinslib.logger.Logger
import org.apache.http.HttpException
import org.apache.http.HttpMessage
import org.apache.http.HttpResponse
import org.apache.http.StatusLine
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpDelete
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.beans.factory.annotation.Autowired

class HttpService {

	Logger log

	@Autowired
	HttpService(Logger log) {
		this.log = log
	}

	String httpGet(String url, AuthInterface auth = null, int timeout = 30) {
		log.log("Http::httpGet for $url")

		HttpGet request = new HttpGet(url)
		prepareRequest(request, auth)

		RequestConfig config = retrieveTimeoutConfig(timeout)

		HttpClient client = HttpClientBuilder
			.create()
			.setDefaultRequestConfig(config)
			.setRetryHandler(new DefaultHttpRequestRetryHandler(5, true))
			.build()

		HttpResponse response = this.sendRequest(request, client)
		log.log(response.statusLine.toString())

		throwExceptionWhenRestFailed(response.getStatusLine(), url, "GET")

		response.entity.content.text
	}

	String httpPut(String url, String json, AuthInterface auth = null, int timeout = 30) {
		log.log("HttpRequest::httpPut for $url wit content $json")

		HttpPut request = new HttpPut(url)
		prepareRequest(request, auth)
		request.setEntity(new StringEntity(json, "UTF-8"))

		RequestConfig config = retrieveTimeoutConfig(timeout)

		HttpClient client = HttpClientBuilder
			.create()
			.setDefaultRequestConfig(config)
			.setRetryHandler(new DefaultHttpRequestRetryHandler(5, true))
			.build()

		HttpResponse response = this.sendRequest(request, client)

		throwExceptionWhenRestFailed(response.getStatusLine(), url, "PUT")

		response.entity.content.text
	}

	String httpPost(String url, String json, AuthInterface auth = null, int timeout = 30) {
		log.log("HttpRequest::httpPost for $url with content $json")

		HttpPost request = new HttpPost(url)
		prepareRequest(request, auth)
		request.setEntity(new StringEntity(json, "UTF-8"))

		RequestConfig config = retrieveTimeoutConfig(timeout)

		HttpClient client = HttpClientBuilder
			.create()
			.setDefaultRequestConfig(config)
			.setRetryHandler(new DefaultHttpRequestRetryHandler(5, true))
			.build()

		HttpResponse response = this.sendRequest(request, client)

		throwExceptionWhenRestFailed(response.getStatusLine(), url, "POST")

		response.entity.content.text
	}

	String httpDelete(String url, AuthInterface auth = null, int timeout = 30) {
		log.log("HttpRequest::httpDelete for $url")

		HttpDelete request = new HttpDelete(url)
		prepareRequest(request, auth)

		RequestConfig config = retrieveTimeoutConfig(timeout)

		HttpClient client = HttpClientBuilder
			.create()
			.setDefaultRequestConfig(config)
			.setRetryHandler(new DefaultHttpRequestRetryHandler(5, true))
			.build()

		HttpResponse response = this.sendRequest(request, client)

		throwExceptionWhenRestFailed(response.getStatusLine(), url, "DELETE")

		response.entity.content.text
	}

	String request(String method, String url) {
		HttpURLConnection connection = new URL(url).openConnection()
		connection.setRequestMethod(method)
		connection.outputStream
		if (connection.getResponseCode() != 200) {
			throw new RuntimeException('Could not fetch versions')
		}
	}

	private RequestConfig retrieveTimeoutConfig(int timeout) {
		RequestConfig.custom()
			.setConnectTimeout(timeout * 1000)
			.setConnectionRequestTimeout(timeout * 1000)
			.build()
	}

	void prepareRequest(HttpMessage request, AuthInterface auth = null) {
		if (auth) {
			auth.prepareRequest(request)
		}
	}

	private void throwExceptionWhenRestFailed(StatusLine status, url, method) {
		if (status.getStatusCode() < 200 || status.getStatusCode() >= 300) {
			throw new HttpException("""
				[HttpRequest Exception] Rest call failed (code ${status.getStatusCode()}) for ${url} for method ${
				method
			}.\n
				Message: ${status.getReasonPhrase()}
			""")
		}
	}

	@NonCPS
	private HttpResponse sendRequest(HttpUriRequest request, HttpClient client) {
		return client.execute(request)
	}

}