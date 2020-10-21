package com.optaretalk.util;

import com.google.inject.Inject;
import io.reactivex.Single;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;

public class HttpClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class.getName());

	private final WebClient webClient;

	@Inject
	public HttpClient(Vertx vertx) {
		WebClientOptions webClientOptions = new WebClientOptions();
		webClientOptions.setMaxPoolSize(200);
		this.webClient = WebClient.create(vertx, webClientOptions);
	}

	public Single<HttpResponse<Buffer>> get(String host, String servicePath, String path) {
		LOGGER.info("Sending request to http://" + host + servicePath + path);
		webClient.get(host, servicePath);
		return webClient.get(host, servicePath + path).rxSend().retry(2)
				.doOnSuccess(response -> LOGGER.info("Received response with status code: " + response.statusCode()));
	}
}
