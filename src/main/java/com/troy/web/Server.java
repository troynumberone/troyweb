package com.troy.web;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

public class Server extends AbstractVerticle
{
	@Override
	public void stop(Future<Void> future) throws Exception
	{

	}

	@Override
	public void start(Future<Void> future) throws Exception
	{
		HttpServer server = getVertx().createHttpServer();

		server.requestHandler(request -> {

			// This handler gets called for each request that arrives on the server
			HttpServerResponse response = request.response();
			response.putHeader("content-type", "text/plain");

			// Write to the response and end it
			response.end("Hello World!");
		});

		server.listen(8080);
	}
}
