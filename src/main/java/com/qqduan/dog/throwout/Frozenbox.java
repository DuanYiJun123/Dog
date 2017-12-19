package com.qqduan.dog.throwout;

import java.util.Collections;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class Frozenbox<T> {
	private long time;
	private Set<T> datas;
	private AtomicLong count;
	
	public Frozenbox(long time) {
		super();
		this.time = time;
		this.datas = Collections.synchronizedSet(datas);
		this.count=new AtomicLong();
	}

	public void add(T data) {
		this.datas.add(data);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				datas.remove(data);
			}
		}, this.time);
	}

	public int getCount() {
		return datas.size();
	}

	public long getCount(Alive<T> alive) {
		
		datas.forEach(p->{
			if(alive.alive(p)){
				count.incrementAndGet();
			}
		});
		return count.get();
	}
}
