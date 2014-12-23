package cn.common;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 网络操作，可以获取网页源代码，下载网络文件
 * 
 * @author epleone
 * 
 */
public class UrlUtil {

	/**
	 * 得到网络地址对应的字符串，也即得到网页源代码
	 * 
	 * @param address
	 *            网址
	 * @return 网页源代码
	 * @throws IOException
	 *             IO异常
	 */
	public String getString(String address) throws IOException {
		StringBuffer netString = new StringBuffer();
		URL url = new URL(address);
		URLConnection conn = url.openConnection();
		BufferedReader buff = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line;
		while ((line = buff.readLine()) != null) {
			netString.append(line);
		}
		buff.close();
		return netString.toString();
	}

	/**
	 * 按照指定编码得到网络地址对应的字符串，也即得到网页源代码
	 * 
	 * @param address
	 *            网址
	 * @param encoding
	 *            编码
	 * @return 网页源代码
	 * @throws IOException
	 *             IO异常
	 */
	public String getString(String address, String encoding) throws IOException {
		StringBuffer netString = new StringBuffer();
		URL url = new URL(address);
		URLConnection conn = url.openConnection();
		BufferedReader buff = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), encoding));
		String line;
		while ((line = buff.readLine()) != null) {
			netString.append(line);
		}
		buff.close();
		return netString.toString();
	}

	/**
	 * 保存指定位置的文件
	 * 
	 * @param address
	 *            网络地址
	 * @param filePath
	 *            保存文件路径
	 * @throws IOException
	 *             IO异常
	 */
	public void saveNetFile(String address, String filePath) throws IOException {
		URL url = new URL(address);
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream() ;
		File desFile = new File(filePath);
		OutputStream os = new FileOutputStream(desFile);
		byte[] b = new byte[1024] ;
		int len ;
		while ((len = is.read(b)) != -1){
			os.write(b, 0, len);
		}
		os.close();
		is.close();
	}
}
