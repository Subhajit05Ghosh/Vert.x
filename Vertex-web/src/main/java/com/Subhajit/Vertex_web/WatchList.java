package com.Subhajit.Vertex_web;

import java.util.List;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchList {
	List<Asset> asset;

	JsonObject toJsonObject() {
		return JsonObject.mapFrom(this);
	}
	// Maps the watchlist object to vertex json object
}
