package com.optaretalk.domain.swapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Character {

	@JsonProperty("name")
	private String name;
	@JsonProperty("url")
	private String url;

	public Character(@JsonProperty("name") String name, @JsonProperty("url") String url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
