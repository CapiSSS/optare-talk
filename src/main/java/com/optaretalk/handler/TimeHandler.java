package com.optaretalk.handler;

import com.google.inject.Inject;
import com.optaretalk.service.TimeService;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.ext.web.RoutingContext;

public class TimeHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeHandler.class.getName());

	private TimeService timeService;

	@Inject
	public TimeHandler(TimeService timeService) {
		this.timeService = timeService;
	}

	public void getTime(RoutingContext routingContext) {
		String area = routingContext.pathParam("area");
		String location = routingContext.pathParam("location");
		String timezone = area + "/" + location;
		LOGGER.info("Getting time in timezone " + timezone);
		timeService.getTime(timezone)
				.subscribe(response -> routingContext.response()
						.setStatusCode(200)
						.putHeader("Content-Type", "application/json")
						.end(Json.encodePrettily(response)), throwable -> {
					throwable.printStackTrace();
					routingContext.fail(throwable);
				});
	}

}
