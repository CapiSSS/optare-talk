package com.optaretalk;

import com.optaretalk.verticle.MainVerticle;
import io.netty.channel.DefaultChannelId;
import io.vertx.reactivex.core.Vertx;

import java.util.function.Consumer;

public class Main {

	public static void main(String[] args) {
		DefaultChannelId.newInstance();
		Consumer<Vertx> runner = (vertx) -> vertx.deployVerticle(MainVerticle.class.getName());
		runner.accept(Vertx.vertx());
	}

}
