package com.optaretalk.handler;

import com.google.inject.Inject;
import com.optaretalk.service.StarWarsService;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.util.Map;

public class StarWarsHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(StarWarsHandler.class);
	private static final Map<Integer, String> INT_TO_ROMAN_MAP = Map.of(
		1, "I",
		2, "II",
		3, "III",
		4, "IV",
		5, "V",
		6, "VI"
	);

	StarWarsService starWarsService;

	@Inject
	public StarWarsHandler(StarWarsService starWarsService) {
		this.starWarsService = starWarsService;
	}

	public void getCharactersInFilm(RoutingContext routingContext) {
		Integer episodeId = Integer.parseInt(routingContext.pathParam("filmId"));
		LOGGER.info("Getting characters appearing in episode " + INT_TO_ROMAN_MAP.get(episodeId));
		starWarsService.getNameOfCharactersAppearingInFilm(episodeId)
			.subscribe(response -> {
					routingContext.response()
						.setStatusCode(200)
						.putHeader("Content-Type", "application/json")
						.end(Json.encodePrettily(response));
					LOGGER.info("HTTP response returned.");
				},
				throwable -> {
					throwable.printStackTrace();
					routingContext.fail(throwable);
				});
	}

}
