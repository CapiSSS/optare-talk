package com.optaretalk.service;

import com.google.inject.Inject;
import com.optaretalk.domain.swapi.Character;
import com.optaretalk.domain.swapi.Film;
import com.optaretalk.util.HttpClient;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.ext.web.client.HttpResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StarWarsService {

	public static final Logger LOGGER = LoggerFactory.getLogger(StarWarsService.class.getName());

	public HttpClient httpClient;

	@Inject
	public StarWarsService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public Single<Film> getNameOfCharactersAppearingInFilm(Integer episodeId) {
		return Single.zip(
				fetchFilm(episodeId),
				fetchCharacters(),
				(this::zipResponse));
	}

	private Single<Film> fetchFilm(Integer episodeId) {
		LOGGER.info("Preparing request to fetch film");
		return httpClient.get("swapi.dev", "/api", "/films/" + episodeId + "/")
				.map(HttpResponse::bodyAsJsonObject)
				.map(jsonObject -> jsonObject.mapTo(Film.class));
	}

	private Single<List<Character>> fetchCharacters() {
		LOGGER.info("Preparing request to fetch characters");
		return httpClient.get("swapi.dev", "/api", "/people/")
				.map(HttpResponse::bodyAsJsonObject)
				.map(jsonObject -> jsonObject.getJsonArray("results").stream()
						.map(json -> ((JsonObject) json).mapTo(Character.class))
						.collect(Collectors.toList()));
	}

	private Film zipResponse(Film film, List<Character> characterList) {
		LOGGER.info("Zipping response...");
		List<String> characterNameList = new ArrayList<>();
		Map<String, String> urlToName = characterList.stream()
				.collect(Collectors.toMap(
						Character::getUrl,
						Character::getName,
						(x, y) -> x + ", " + y,
						HashMap::new));
		film.getCharacterList().forEach(characterUrl -> {
			if (urlToName.containsKey(characterUrl)) {
				characterNameList.add(urlToName.get(characterUrl));
			}
		});
		film.setCharacterList(characterNameList);
		return film;
	}

}
