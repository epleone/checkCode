package verifyCode;

import java.awt.FileDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import cn.common.FileUtil;
import cn.svm.Svm;

/**
 * Verification code recognition
 * @author epleone 2014 August 3
 * 验证码识别主函数
 */
public class codeMain extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private final static String imageCheckcodePath = "checkcode/checkcode_training/";
	private final static String trainingSetPath = "checkcode1/checkCodedataset";
	private final static String modelPath = "checkcode1/checkCodeModel";
	private final static String testImagePath = "checkcode/checkcode_test/";
	
	public codeMain(){
		
	}
	
	public  BufferedImage getImg(){
		String lastDir = "";
		BufferedImage image = null;
		FileDialog d = new FileDialog(this,"Open image file",FileDialog.LOAD);
        //d.setFile("*.jpg");
        d.setDirectory(lastDir);
        d.setVisible(true);
        String file = d.getFile();
        lastDir = d.getDirectory();
        if(file!=null)
        	{
        	try{
    			image = ImageIO.read(new FileInputStream(lastDir+file));
    		}catch (IOException e) {
    			e.printStackTrace();
    		}
            } 
        return image;
	}
	
	public codeMain(BufferedImage img,String s){
		super(s);
		JLabel label =new JLabel(new ImageIcon(img));
	    add(label);
	    this.setSize(220,120);
		this.setLocation(500,300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setVisible(true);
	}
	
	/**
	 * 	训练SVM
	 *@param imageDirectoryPath
	 *            验证码文件夹
	 * @param outFilePath
	 *            输出文件路径
	 */
	public void train(String imageDirectoryPath,String outFilePath) throws IOException{
		File imagedirectory = new File(imageDirectoryPath);
		if (imagedirectory.isFile())
			throw new IOException("It is a file but a directory");

		File[] images = imagedirectory.listFiles();
		StringBuffer buff = new StringBuffer();

		for (File image : images) {
			String imageName = image.getName();
			Img im = new Img(image);
			
			for (int j = 0; j < 4; j++) {
				buff.append(Integer.valueOf(imageName.substring(j, j + 1)));
				for (int i = 0; i < 60; i++) {
					buff.append(" ");
					buff.append(i+1);
					buff.append(':');
					buff.append(im.data[j][i]);
					buff.append(' ');
				}
				buff.append('\n');
			}
		}

		FileUtil fileUtil = new FileUtil();
		fileUtil.saveContentToFile(buff.toString(), outFilePath);
	}
	
	public static void main(String[] args) throws IOException{
		
		Svm svm = null;
		if (new File(modelPath).isFile()){
			svm = new Svm(modelPath); //已经训练过不需要产生了
			System.out.println("加载完成svm配置！");
		}else{
			(new codeMain()).train(imageCheckcodePath, trainingSetPath); //产生ustc_dataset文件
			svm = new Svm(trainingSetPath, modelPath);  //产生 ustc_model 文件
			System.out.println("svm 训练完成！");
		}
		
		BufferedImage img = null;
		
//		try{
//			img = ImageIO.read(new File("/home/eple/Desktop/code/checkcode_test/1621"));
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
		
		img = (new codeMain()).getImg();
		Img t = new Img(img);
		StringBuffer fileNewName = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			int number = (int) svm.getClassification(t.data[i]);
			fileNewName.append(number);
		}
		
		System.out.println("该验证码为："+fileNewName);
		String msg = "验证码为：" + fileNewName;
		new codeMain(img,msg);
	}
}
