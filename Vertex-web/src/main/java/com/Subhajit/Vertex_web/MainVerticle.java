package com.Subhajit.Vertex_web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.ReplyFailure;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {
    static final int PORT = 8888;
	private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

	public static void main(String[] args) {
		var vertx = Vertx.vertx();
		vertx.deployVerticle(new MainVerticle(), ar -> {
			if (ar.failed()) {
				LOG.error("Failed to deploy", ar.cause());
				return;
			}
			LOG.info("Deployed{} ", MainVerticle.class.getName());
		});
		vertx.exceptionHandler(error -> {
			LOG.error("Unhandled: {}", error);
		});
	}

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		final Router restApi = Router.router(vertx);
		restApi.route()
		.handler(BodyHandler.create())
		.failureHandler(errorContext->{
		if(errorContext.response().ended()) {
		//Ignore completed response;
			return;
		}
		LOG.error("Route Error: ",errorContext.failure());
		errorContext.response().setStatusCode(500)
		.end(new JsonObject().put("message", "something went wrong: (").toBuffer());
		});
		//AssetsRestAPI.attach(restApi);
		AssetsRestAPI.attachObj(restApi);
		
		QuotesRestApi.attach(restApi);
		WatchListRestApi.attach(restApi);
		/*
		 * restApi.get("/assests").handler(context -> { final JsonArray response = new
		 * JsonArray(); response.add(new JsonObject().put("symbol", "FB")) .add(new
		 * JsonObject().put("symbol", "GGL")) .add(new JsonObject().put("symbol",
		 * "NTFLX")) .add(new JsonObject().put("symbol", "AMZ"));
		 * LOG.info("Path {} responds with {}",context.normalizedPath(),response);
		 * context.response().end(response.toBuffer());
		 * 
		 * });
		 */
		/*
		 * vertx.createHttpServer().requestHandler(req -> {
		 * req.response().putHeader("content-type",
		 * "text/plain").end("Hello from Vert.x!"); }).listen(8888, http -> { if
		 * (http.succeeded()) { startPromise.complete();
		 * LOG.info("HTTP server started on port 8888"); } else {
		 * startPromise.fail(http.cause()); } });
		 */
		
		
		
		vertx.createHttpServer().requestHandler(restApi)
		.exceptionHandler(error->LOG.error("HTTP server error ",error))
		.listen(PORT, http -> {
			if (http.succeeded()) {
				startPromise.complete();
				LOG.info("HTTP server started on port 8888");
			} else {
				startPromise.fail(http.cause());
			}
		});
	}
}
