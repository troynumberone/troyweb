package com.troy.web;

import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;

import java.time.LocalDateTime;

public class WorkerExecutorDemo
{
	public static void main(String[] args)
	{
		Vertx vertx = Vertx.vertx();
		EventBus eventBus = vertx.eventBus();
		WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-worker-pool");

		eventBus.consumer("com.troy.web.WorkerExecutorDemo", event -> {
			event.reply("收到" + event.body());
			System.out.println(event.body() + "run at" + LocalDateTime.now());
			executor.executeBlocking(future -> {
				long timeId=vertx.setTimer(5000, id -> {
					System.out.println("And one second later this is printed" + id);
					System.out.println("处理完成");
				});
				System.out.println("timeId" + timeId);
			}, false, re -> {
			});

		});

		eventBus.send("com.troy.web.WorkerExecutorDemo", "test", re -> {
			if (re.succeeded())
			{
				System.out.println("re success" + re.result().body());
			}
			else
			{
				System.out.println("re fail" + re.result().body());
			}
		});

		eventBus.send("com.troy.web.WorkerExecutorDemo", "test2", re -> {
			if (re.succeeded())
			{
				System.out.println("re success" + re.result().body());
			}
			else
			{
				System.out.println("re fail" + re.result().body());
			}
		});




	}
}
