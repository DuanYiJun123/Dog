package com.qqduan.dog.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import com.alibaba.simpleimage.render.ScaleParameter.Algorithm;

/**
 * 文件工具类
 * 
 * @author xue.wen
 *
 */
public class FileUtil {
	public static void fileCopy(String resource, String target) {
		File folder = new File(target.substring(0, target.lastIndexOf(File.separator)));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File file = new File(target);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (FileInputStream fis = new FileInputStream(resource);
				FileOutputStream fos = new FileOutputStream(target);) {

			byte[] buf = new byte[1024];
			int by = 0;
			while ((by = fis.read(buf)) != -1) {
				fos.write(buf, 0, by);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getAppRoot() {
		try {
			return new File("").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void createFileIfExitsDelete(File file) {
		if (file.exists()) {
			file.delete();
		}
		createDirAndFileIfNotExits(file);
	}

	public static void createDirAndFileIfNotExits(File file) {
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void base64ToFile(String base64, File outFile) {
		try {
			byte[] buffer = Base64.getDecoder().decode(base64);
			buffer = allToJpg(buffer);
			DataOutputStream out = new DataOutputStream(new FileOutputStream(outFile));
			out.write(buffer);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String FileToBase64(File file) {
		try (FileInputStream inputFile = new FileInputStream(file);) {
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return new String(Base64.getEncoder().encode(buffer));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String FileToBase64(String path) {
		File file = new File(path);
		if (!file.exists() || file.isDirectory()) {
			return "";
		}
		try (FileInputStream inputFile = new FileInputStream(file);) {
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return new String(Base64.getEncoder().encode(buffer));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteFileAll(File root, boolean flg) {
		deleteFile(root, flg);
	}

	private static void deleteFile(File f, boolean flg) {
		subForlder(f).forEach(item -> {
			deleteFile(item, true);
		});

		listFiles(f).forEach(item -> {
			item.delete();
		});
		if (flg) {
			f.delete();
		}
	}

	public static List<File> subForlder(File parent) {
		File[] listFiles = parent.listFiles(file -> {
			return file.isDirectory();
		});
		if (listFiles != null) {

			return Arrays.asList(listFiles);
		}
		return new ArrayList<>();
	}

	public static List<File> listFiles(File parent) {
		File[] listFiles = parent.listFiles(file -> {
			return file.isFile();
		});
		if (listFiles != null) {

			return Arrays.asList(listFiles);
		}
		return new ArrayList<>();
	}

	public static List<File> listJarFiles(File parent) {
		File[] listFiles = parent.listFiles(file -> {
			return file.isFile() && file.getName().endsWith(".jar");
		});
		if (listFiles != null) {

			return Arrays.asList(listFiles);
		}
		return new ArrayList<>();
	}

	public static String getImage(String imgName) {
		return getImage(imgName, false);
	}

	@SuppressWarnings("deprecation")
	public static String getImage(String imgName, boolean flg) {
		if (flg) {
			return URLEncoder
					.encode(FileToBase64(getAppRoot() + File.separator + "resources" + File.separator + imgName));
		} else {
			return FileToBase64(getAppRoot() + File.separator + "resources" + File.separator + imgName);
		}
	}

	public static byte[] allToJpg(byte[] jpg) {
		InputStream inStream = new ByteArrayInputStream(jpg);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ScaleParameter scaleParam = new ScaleParameter(6000, 6000);
		WriteRender wr = null;
		byte[] out = null;
		try {
			ImageRender rr = new ReadRender(inStream);
			ImageRender sr = new ScaleRender(rr, scaleParam);
			wr = new WriteRender(sr, outStream);
			wr.render();
			out = outStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inStream);
			IOUtils.closeQuietly(outStream);
			if (wr != null) {
				try {
					wr.dispose();
				} catch (SimpleImageException ignore) {

				}
			}
		}

		return out;
	}

	public static byte[] allToJpg(byte[] jpg, int max, int min) {
		InputStream inStream = new ByteArrayInputStream(jpg);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ScaleParameter scaleParam = new ScaleParameter(max, min);
		WriteRender wr = null;
		byte[] out = null;
		try {
			ImageRender rr = new ReadRender(inStream);
			ImageRender sr = new ScaleRender(rr, scaleParam);
			wr = new WriteRender(sr, outStream);
			wr.render();
			out = outStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inStream);
			IOUtils.closeQuietly(outStream);
			if (wr != null) {
				try {
					wr.dispose();
				} catch (SimpleImageException ignore) {

				}
			}
		}

		return out;
	}

	public static Integer getHeight(Integer w, Integer h, Integer width) {
		return h * width / w;
	}

	public static Integer getWidth(Integer w, Integer h, Integer height) {
		return w * height / h;
	}

	public static byte[] allToJpg(byte[] jpg, int width) {
		InputStream inStream = new ByteArrayInputStream(jpg);
		ImageRender rr = new ReadRender(inStream);
		ImageWrapper imageWrapper = null;
		try {
			imageWrapper = rr.render();
		} catch (SimpleImageException e1) {
			return null;
		}
		int w = imageWrapper.getWidth();
		int h = imageWrapper.getHeight();
		ScaleParameter scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS); // 缩放参数
		if (w < width) {
			scaleParam = new ScaleParameter(w, h, Algorithm.LANCZOS);
		} else {
			int newHeight = getHeight(w, h, width);
			scaleParam = new ScaleParameter(width, newHeight + 1, Algorithm.LANCZOS);
		}

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		WriteRender wr = null;
		byte[] out = null;
		try {
			ImageRender sr = new ScaleRender(imageWrapper, scaleParam);
			wr = new WriteRender(sr, outStream);
			wr.render();
			out = outStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inStream);
			IOUtils.closeQuietly(outStream);
			if (wr != null) {
				try {
					wr.dispose();
				} catch (SimpleImageException ignore) {

				}
			}
		}

		return out;
	}

}
