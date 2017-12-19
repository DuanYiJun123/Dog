package com.qqduan.dog.throwout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class Throwout {
	private long start;
	private AtomicLong count;
	private ExecutorService pool;

	public Throwout(long time, int number, Test run) {
		this.count = new AtomicLong();
		this.pool = Executors.newFixedThreadPool(number);

		this.start = System.currentTimeMillis();
		for (int i = 0; i < number; i++) {
			this.pool.execute(() -> {
				while (System.currentTimeMillis() - start < time) {
					run.run();
					count.incrementAndGet();
				}
			});
		}
		pool.shutdown();
		getCount();

	}

	public long getCount() {
		while (!pool.isTerminated()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this.count.get();
	}
}