package com.Subhajit.Vertex_web;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
public class TestWatchListRestApi {
	private static final Logger LOG = LoggerFactory.getLogger(TestWatchListRestApi.class);

	@BeforeEach
	void deploy_verticle(Vertx vertx, VertxTestContext context) {
		vertx.deployVerticle(new MainVerticle(), context.succeeding(id -> context.completeNow()));
	}

	@Test
	void adds_and_returns_watchlist_for_account(Vertx vertx, VertxTestContext context) throws Throwable {
		var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(MainVerticle.PORT));
		var accountId=UUID.randomUUID();
		client
		.put("/account/watchlist/"+ accountId.toString())
		.sendJsonObject(body())
		.onComplete(context.succeeding(response -> {
			var json = response.bodyAsJsonObject();
			LOG.info("response {}", json);
			//assertEquals("", json.encode());
			assertEquals("{\"name\":\"AMZN\"}", json.getJsonObject("asset").encode());
			assertEquals(200, response.statusCode());
			context.completeNow();
		}));
	}

	private JsonObject body() {
		return new WatchList(Arrays.asList(
				new Asset("AMZN"),
				new Asset("TSLA"))
				).toJsonObject();
	}
}
