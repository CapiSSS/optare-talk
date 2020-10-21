package com.optaretalk.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;

import java.util.Properties;

public class GuiceModule extends AbstractModule {

	private final Vertx vertx;

	public GuiceModule(Vertx vertx) {
		this.vertx = vertx;
	}

	@Override
	protected void configure() {
		Names.bindProperties(binder(), extractToProperties(vertx.getOrCreateContext().config()));

		bind(Vertx.class).toInstance(vertx);
	}

	private Properties extractToProperties(JsonObject config) {
		Properties properties = new Properties();
		config.getMap().keySet()
				.forEach((String key) -> properties.setProperty(key, "" + config.getValue(key)));
		return properties;
	}
}
