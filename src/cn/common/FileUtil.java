package cn.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 文件操作
 * @author epleone
 *
 */
public class FileUtil {

	/**
	 * 按照UTF-8编码得到文件内容
	 * @param filePath 源文件路径
	 * @return 文件内容
	 * @throws IOException 
	 */
	public String getFileContent(String filePath) throws IOException{
		return getFileContent(filePath, "UTF-8");
	}

	/**
	 * 
	 * 按照指定编码得到文件内容
	 * @param filePath 源文件路径
	 * @param encoding 文件编码
	 * @return 文件内容
	 * @throws IOException 
	 */
	public String getFileContent(String filePath,String encoding) throws IOException{
		BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),encoding));
		String line ;
		StringBuffer content = new StringBuffer();
		while((line = buff.readLine()) != null){
			content.append('\n');
			content.append(line);
		}
		buff.close();
		return content.substring(1).toString();
	}


	/**
	 * 按照UTF-8编码保存相应内容到指定路径的文件
	 * @param content 文件内容
	 * @param filePath 文件路径
	 * @throws IOException 
	 */
	public void saveContentToFile(String content, String filePath) throws IOException{
		saveContentToFile(content, filePath, "UTF-8");
	}

	/**
	 * 按照指定编码保存相应内容到指定路径的文件
	 * @param content 文件内容
	 * @param filePath 文件路径
	 * @param encoding 编码
	 * @throws IOException 
	 */
	
	public void saveContentToFile(String content, String filePath, String encoding) throws IOException{
		BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),encoding));
		buff.write(content);
		buff.flush(); 
		buff.close();
	}
	
}
