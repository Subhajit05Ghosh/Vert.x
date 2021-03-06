package com.Subhajit.Vertex_web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.internal.ThreadLocalRandom;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
public class QuotesRestApi {
	private static final Logger LOG = LoggerFactory.getLogger(QuotesRestApi.class);

	public static void attach(Router parent) {
		final Map<String,Quote> cachedQuotes=new HashMap<>();
		AssetsRestAPI.ASSETS.forEach(symbol->{
			cachedQuotes.put(symbol, initRandomQuote(symbol));
		});
		parent.get("/quotes/:asset").handler(context->{
			final String assetParam=context.pathParam("asset");
			LOG.debug("Asset parameter: {}",assetParam);
			//final Quote quote=new Quote();
			/*
			 * var quote=Quote.builder() .asset(new Asset(assetParam)) .ask(randomValue())
			 * .bid(randomValue()) .lastPrice(randomValue()) .volume(randomValue())
			 * .build();
			 */
			//var quote = initRandomQuote(assetParam);
			//var quote = cachedQuotes.get(assetParam);
			var maybeQuote = Optional.ofNullable(cachedQuotes.get(assetParam));
			if(maybeQuote.isEmpty()) {
				context.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code())
				.end(new JsonObject()
				.put("message", "quote for asset "+assetParam+" not available! ")
				.put("path", context.normalizedPath())
				.toBuffer());
			}
			final JsonObject response = maybeQuote.get().toJsonObject();
			LOG.info("Path {} responds with {}", context.normalizedPath(), response);
			context.response().end(response.toBuffer());
		});

	}

	private static Quote initRandomQuote(final String assetParam) {
		var quote=Quote.builder()
				.asset(new Asset(assetParam))
				.ask(randomValue())
				.bid(randomValue())
				.lastPrice(randomValue())
				.volume(randomValue())
				.build();
		return quote;
	}

	private static BigDecimal randomValue() {
		return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 100));
	}

}
