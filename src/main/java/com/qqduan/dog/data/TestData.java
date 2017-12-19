package com.qqduan.dog.data;

import com.qqduan.dog.annotation.Column;
import com.qqduan.dog.annotation.Table;

@Table(name = "TestData")
public class TestData {
	@Column
	String name = "test";
	@Column
	Integer number = 123;
}
