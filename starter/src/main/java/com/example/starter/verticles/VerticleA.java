package com.example.starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleA extends AbstractVerticle {
	
	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		System.out.println(" Start " + getClass().getName());
		//super.start(startPromise);
		vertx.deployVerticle(new VerticleAA(), whenDeployed->{
			System.out.println(" Deployed "+VerticleAA.class.getName());
			vertx.undeploy(whenDeployed.result());
		});
		vertx.deployVerticle(new VerticleAB());
		startPromise.complete();
	}

}
