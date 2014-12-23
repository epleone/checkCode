package SignLan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.common.FileUtil;
import cn.svm.Svm;

/**
 * Verification code recognition
 * @author epleone 2014 August 3
 * 验证码识别主函数
 */
public class SLmain{
	
	private static final long serialVersionUID = 1L;
	private final static String imageCheckcodePath = "data/train_set.txt";
	private final static String trainingSetPath = "data/dataset";
	private final static String modelPath = "data/model1";
	private final static String testSetPath = "data/TestSet";
	private final static String trainresultPath = "data/trainSetRes"; //训练集结果
	private final static String testresultPath = "data/TestSetRes"; //测试集结果
	
	public SLmain(){
		
	}
	
	
	/**
	 * 	训练SVM
	 *@param imageDirectoryPath
	 *            验证码文件夹
	 * @param outFilePath
	 *            输出文件路径
	 */
	public void train(String imageDirectoryPath,String outFilePath) throws IOException{
		FileReader fr=new FileReader(imageDirectoryPath);
		BufferedReader br = new BufferedReader(fr);
        String line="";
        String[] arrs=null;
        StringBuffer buff = new StringBuffer();
        while ((line = br.readLine())!=null) {
            arrs = line.split("	");
            buff.append(Integer.valueOf(arrs[0]));
            for(int i=1;i<arrs.length;i++){
				buff.append(" ");
				buff.append(i);
				buff.append(':');
				buff.append(getNum(arrs[i]));
				buff.append(' ');
            }
            buff.append('\n'); //换行
        }

		FileUtil fileUtil = new FileUtil();
		fileUtil.saveContentToFile(buff.toString(), outFilePath);
		System.out.println("生成向量结束！");
	}
	
	
	/*
	 * 将科学记数法转成普通计数法
	 */
	private float getNum(String s){
		float db = (float) Double.parseDouble(s);
		return db;
	}
	
	public static void main(String[] args) throws IOException{
		
		Svm svm = null;
//		if (new File(modelPath).isFile()){
//			svm = new Svm(modelPath); //已经训练过不需要产生了
//			System.out.println("加载完成svm配置！");
//		}else{
//			(new SLmain()).train(imageCheckcodePath, trainingSetPath); //产生ustc_dataset文件
			svm = new Svm(trainingSetPath, modelPath);  //产生 ustc_model 文件
			System.out.println("svm 训练完成！");
//		}
		
//		double[] vecter = {8.6696E-06,1.52318E-05,0.000195419,0.000197798,1,0.002640903,0,0.872098411,
//						   0.872091888,0.872769551,0.872742415,0,0.871954769,0.827811819,0.007090121,
//						   0.037626899,0.029199922,0.028560822,1,0.188877704,0};
//		
//		System.out.println(svm.getClassification(vecter));
		
		double trainAccuracy = svm.getAccuracy(trainingSetPath, trainresultPath); //训练集上
		System.out.println("训练集上的正确率：" + trainAccuracy);
		
		double testAccuracy = svm.getAccuracy(testSetPath, testresultPath); //测试集上
		System.out.println("测试集上的正确率：" + testAccuracy);
	}
}