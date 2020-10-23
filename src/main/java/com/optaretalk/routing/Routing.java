package com.optaretalk.routing;

import com.google.inject.Inject;
import com.optaretalk.handler.StarWarsHandler;
import com.optaretalk.handler.TimeHandler;
import io.reactivex.Single;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
import io.vertx.reactivex.ext.web.handler.ResponseTimeHandler;

public class Routing {

	private static final Logger LOGGER = LoggerFactory.getLogger(Routing.class);

	private final StarWarsHandler starWarsHandler;
	private final TimeHandler timeHandler;

	@Inject
	public Routing(StarWarsHandler starWarsHandler, TimeHandler timeHandler) {
		this.starWarsHandler = starWarsHandler;
		this.timeHandler = timeHandler;
	}

	public Single<Router> getRouter(Vertx vertx) {
		return Single.create(singleEmitter -> {
			LOGGER.info("Creating router from OpenApi3 swagger file...");
			getRouterFromOpenApi3(vertx).subscribe(singleEmitter::onSuccess, singleEmitter::onError);
		});
	}

	private Single<Router> getRouterFromOpenApi3(Vertx vertx) {
		return OpenAPI3RouterFactory.rxCreate(vertx, "swagger.yaml")
			.map(openAPI3RouterFactory -> {
				openAPI3RouterFactory.addGlobalHandler(ResponseTimeHandler.create());
				openAPI3RouterFactory.addHandlerByOperationId("getCharactersInFilm",
					starWarsHandler::getCharactersInFilm);
				openAPI3RouterFactory.addHandlerByOperationId("getTimeInTimezone", timeHandler::getTime);
				Router router = openAPI3RouterFactory.getRouter();
				LOGGER.info("Router created.");
				return router;
			});
	}

}
