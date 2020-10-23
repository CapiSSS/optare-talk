package com.optaretalk.verticle;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.optaretalk.guice.GuiceModule;
import com.optaretalk.routing.Routing;
import io.vertx.core.Promise;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

	@Inject
	private Routing router;

	@Override
	public void start(Promise<Void> startPromise) {
		Guice.createInjector(new GuiceModule(vertx)).injectMembers(this);
		router.getRouter(vertx)
			.subscribe(router -> startHttpServer("localhost", 8080, router, startPromise));
	}

	private void startHttpServer(String httpHost, Integer httpPort, Router router, Promise<Void> started) {
		vertx.createHttpServer()
			.requestHandler(router)
			.rxListen(httpPort, httpHost)
			.subscribe(httpServer -> {
				LOGGER.info("HTTP server started on http://{0}:{1}",
					httpHost, httpPort.toString());
				started.complete();
			}, error -> {
				LOGGER.error(error);
				started.fail(error);
			});
		initJsonModules();
	}

	private void initJsonModules() {
		DatabindCodec.mapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		DatabindCodec.prettyMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
