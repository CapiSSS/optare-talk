package com.optaretalk.domain.swapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Film {

	@JsonProperty("title")
	private String title;
	@JsonProperty("url")
	private String url;
	@JsonProperty("characters")
	private List<String> characterList;

	public Film(@JsonProperty("title") String title,
				@JsonProperty("url") String url,
				@JsonProperty("characters") List<String> characterList) {
		this.title = title;
		this.url = url;
		this.characterList = characterList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getCharacterList() {
		return characterList;
	}

	public void setCharacterList(List<String> characterList) {
		this.characterList = characterList;
	}

}
