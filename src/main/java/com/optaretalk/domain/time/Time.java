package com.optaretalk.domain.time;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Time {

	@JsonProperty("datetime")
	private String offsetDateTime;

	public Time(@JsonProperty("datetime") String offsetDateTime) {
		this.offsetDateTime = offsetDateTime;
	}

	public String getOffsetDateTime() {
		return offsetDateTime;
	}

	public void setOffsetDateTime(String offsetDateTime) {
		this.offsetDateTime = offsetDateTime;
	}
}
