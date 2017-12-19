package com.qqduan.dog.util;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtil {
	public static Element get(String path) {
		try {
			SAXReader saxReader = new SAXReader();
			File file = new File(path);
			FileUtil.createDirAndFileIfNotExits(file);
			Document document;
			document = saxReader.read(file);
			return document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
