package com.qqduan.dog.throwout;

public class Client implements Test{
	
	public static void main(String[] args) {
		Throwout th=new Throwout(10_000, 2, new Client());
		System.out.println(th.getCount());
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("ok");
	}
}
