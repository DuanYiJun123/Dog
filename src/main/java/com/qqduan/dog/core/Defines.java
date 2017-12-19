package com.qqduan.dog.core;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.qqduan.dog.util.FileUtil;

public class Defines {
	public static final String resource=FileUtil.getAppRoot()+File.separator+"mysql.properties";
	public static List<PreparedStatement> list=new ArrayList<>();
}
