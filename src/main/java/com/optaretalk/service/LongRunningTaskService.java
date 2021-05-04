package com.optaretalk.service;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.math.BigInteger;
import java.util.Random;

public class LongRunningTaskService {

	public static final Logger LOGGER = LoggerFactory.getLogger(LongRunningTaskService.class.getName());

	public LongRunningTaskService() {

	}

	public void sleep() {
		BigInteger bigInteger = new BigInteger(2000, new Random());
		bigInteger.nextProbablePrime();
	}

}
