package com.Subhajit.Vertex_web;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
public class TestAssestsRestApi {
	private static final Logger LOG = LoggerFactory.getLogger(TestAssestsRestApi.class);

	@BeforeEach
	void deploy_verticle(Vertx vertx, VertxTestContext context) {
		vertx.deployVerticle(new MainVerticle(), context.succeeding(id -> context.completeNow()));
	}

	@Test
	void returns_all_assets(Vertx vertx, VertxTestContext context) throws Throwable {
		var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(MainVerticle.PORT));
		client.get("/assets").send().onComplete(context.succeeding(response -> {
			var json = response.bodyAsJsonArray();
			LOG.info("response {}", json);
			assertEquals("", json.encode());
			assertEquals(200, response.statusCode());
			context.completeNow();
		}));
	}
}
