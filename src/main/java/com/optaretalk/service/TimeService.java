package com.optaretalk.service;

import com.google.inject.Inject;
import com.optaretalk.domain.time.Time;
import com.optaretalk.util.HttpClient;
import io.reactivex.Single;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.ext.web.client.HttpResponse;

public class TimeService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TimeService.class.getName());

	public HttpClient httpClient;

	@Inject
	public TimeService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public Single<Time> fetchTime(String timezone) {
		LOGGER.info("Preparing request to fetch time");
		return httpClient.get("worldtimeapi.org", "/api", "/timezone/" + timezone)
			.map(HttpResponse::bodyAsJsonObject)
			.map(jsonObject -> jsonObject.mapTo(Time.class));
	}

}
