package com.optaretalk;

import com.optaretalk.verticle.MainVerticle;
import io.netty.channel.DefaultChannelId;
import io.vertx.core.VertxOptions;
import io.vertx.reactivex.core.Vertx;

import java.util.function.Consumer;

public class Main {

	public static void main(String[] args) {
		DefaultChannelId.newInstance();

		VertxOptions vertxOptions = new VertxOptions();
		vertxOptions.setEventLoopPoolSize(10);
		vertxOptions.setWorkerPoolSize(10);
		vertxOptions.setInternalBlockingPoolSize(10);

		Consumer<Vertx> runner = (vertx) -> vertx.deployVerticle(MainVerticle.class.getName());
		runner.accept(Vertx.vertx(vertxOptions));
	}

}
