package com.example.starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleN extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		System.out.println(" Start " + getClass().getName() + " on thread " + Thread.currentThread().getName());
		System.out.println(" Start " + getClass().getName() + " with config " + config().toString());
		// super.start(startPromise);
		startPromise.complete();
	}

}
