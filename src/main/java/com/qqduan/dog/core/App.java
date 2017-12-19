package com.qqduan.dog.core;

import com.qqduan.dog.data.TestData2;

public class App {
	public static void main(String[] args) {
		MySqlManager m=new MySqlManager();
		TestData2 t=new TestData2();
		//m.addItem(t);
		m.queryTable("testdata1");
	}
}
