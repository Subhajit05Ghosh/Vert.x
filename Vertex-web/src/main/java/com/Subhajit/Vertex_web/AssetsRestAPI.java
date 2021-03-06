package com.Subhajit.Vertex_web;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class AssetsRestAPI {
	private static final Logger LOG = LoggerFactory.getLogger(AssetsRestAPI.class);
	public static final List<String> ASSETS=Arrays.asList("APPL","AMZN","FB","GGL","MSFT","NTFLX","TSLA");

	static void attach(Router parent) {
		parent.get("/assests").handler(context -> {
			final JsonArray response = new JsonArray();
			response.add(new JsonObject().put("symbol", "FB")).add(new JsonObject().put("symbol", "GGL"))
					.add(new JsonObject().put("symbol", "NTFLX")).add(new JsonObject().put("symbol", "AMZ"));
			LOG.info("Path {} responds with {}", context.normalizedPath(), response);
			context.response().end(response.toBuffer());

		});
	}
	
	static void attachObj(Router parent) {
		parent.get("/assests").handler(context -> {
			final JsonArray response = new JsonArray();
			ASSETS.stream().map(Asset::new).forEach(response::add);
			response.add(new Asset("FB"))
			.add(new Asset("GGL"))
			.add(new Asset("NTFLX"))
			.add(new Asset("AMZ"));
			LOG.info("Path {} responds with {}", context.normalizedPath(), response);
			context.response().end(response.toBuffer());

		});
	}
}
