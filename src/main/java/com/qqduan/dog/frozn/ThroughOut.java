package com.qqduan.dog.frozn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class ThroughOut {
	private long time;

	private AtomicLong success;
	private ExecutorService service;
	private long start;

	public ThroughOut(int threadCount, long time, Runnable runnable) {
		this.service = Executors.newFixedThreadPool(threadCount);
		this.success = new AtomicLong();
		this.start = System.currentTimeMillis();
		this.time = time;

		for (int i = 0; i < threadCount; i++) {
			this.service.execute(() -> {
				while (System.currentTimeMillis() - this.start <= time) {
					runnable.run();
					this.success.incrementAndGet();
				}
			});
		}
		this.service.shutdown();
	}

	public long getSuccess() {
		while (!this.service.isTerminated()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this.success.get();
	}

	public double getThroughOut() {
		double l = getSuccess();
		return l / time * 1000;
	}

}
