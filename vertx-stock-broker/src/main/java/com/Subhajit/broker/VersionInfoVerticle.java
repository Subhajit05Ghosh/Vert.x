package com.Subhajit.broker;

import com.Subhajit.broker.config.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VersionInfoVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(VersionInfoVerticle.class);

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    ConfigLoader.load(vertx)
      .onFailure(startPromise::fail)
      .onSuccess(configuration -> {
        LOG.info("Current Application Version is: {}", configuration.getVersion());
        startPromise.complete();
      });
  }
}
