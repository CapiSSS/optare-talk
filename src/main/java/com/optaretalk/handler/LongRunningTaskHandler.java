package com.optaretalk.handler;

import com.google.inject.Inject;
import com.optaretalk.service.LongRunningTaskService;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;

public class LongRunningTaskHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(LongRunningTaskHandler.class.getName());

	private final LongRunningTaskService longRunningTaskService;

	@Inject
	public LongRunningTaskHandler(LongRunningTaskService longRunningTaskService) {
		this.longRunningTaskService = longRunningTaskService;
	}

	public void performLongRunningTask(RoutingContext routingContext) {
		long init = System.currentTimeMillis();
		Vertx.currentContext().owner().executeBlocking(promise -> {
			longRunningTaskService.sleep();
			promise.complete();
		}, false, asyncResult -> LOGGER.info("Finished after " + ((System.currentTimeMillis() - init) / 1000)));
		routingContext.response()
			.setStatusCode(202)
			.end();
	}

}
