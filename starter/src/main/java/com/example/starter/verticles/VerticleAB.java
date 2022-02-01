package com.example.starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleAB extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		System.out.println(" Start " + getClass().getName());
		// super.start(startPromise);
		startPromise.complete();
	}

	@Override
	public void stop(Promise<Void> stopPromise) throws Exception {
		System.out.println(" Stop " + getClass().getName());
		// super.start(startPromise);
		stopPromise.complete();
	}

}
