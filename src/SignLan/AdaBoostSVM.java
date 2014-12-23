package SignLan;

import java.io.IOException;
import cn.svm.Svm;

public class AdaBoostSVM {
	
	private static final long serialVersionUID = 1L;
	private final static String trainingSetPath = "dataSet/data";
	private final static String modelPath = "model/model";
	
	private final static String testSetPath = "data/TestSet";
	private final static String trainresultPath = "Res/res"; //训练集结果
	private final static String testresultPath = "data/TestSetRes"; //测试集结果
	private final static int SVMnum = 1;//生成svm的个数
	public AdaBoostSVM(){
		
	}
	
	public void CreateABSVM(){
		
		for(int i = 1;i < SVMnum + 1;i++){
			String trainPathString = trainingSetPath + String.valueOf(i);
			String modelPathString = modelPath + String.valueOf(i);
			new Svm(trainPathString,modelPathString);
			System.out.println("第"+i+"个svm 训练完成！");
		}
		
		System.out.println("所有的svm 训练完成！");
	}
	
	public void getAccuracy(){
		Svm svm = null;
		
		for(int i = 1;i<SVMnum + 1;i++){
			String modelPathString = modelPath + String.valueOf(i);
			String ResPath = trainresultPath + String.valueOf(i);
			try {
				svm = new Svm(modelPathString);
				svm.getAccuracy(testSetPath, ResPath);
				System.out.println("第"+i+"个svm 测试完成！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("所有的svm 测试完成！");
	}
	
	public static void main(String[] args) throws IOException{
		
		(new AdaBoostSVM()).CreateABSVM();
		(new AdaBoostSVM()).getAccuracy();
//		Svm svm = null;
//		if (new File(modelPath).isFile()){
//			svm = new Svm(modelPath); //已经训练过不需要产生了
//			System.out.println("加载完成svm配置！");
//		}else{
//			(new SLmain()).train(imageCheckcodePath, trainingSetPath); //产生ustc_dataset文件
//			svm = new Svm(trainingSetPath, modelPath);  //产生 ustc_model 文件
//			System.out.println("svm 训练完成！");
//		}
		
//		double[] vecter = {8.6696E-06,1.52318E-05,0.000195419,0.000197798,1,0.002640903,0,0.872098411,
//						   0.872091888,0.872769551,0.872742415,0,0.871954769,0.827811819,0.007090121,
//						   0.037626899,0.029199922,0.028560822,1,0.188877704,0};
//		
//		System.out.println(svm.getClassification(vecter));
//		
//		double trainAccuracy = svm.getAccuracy(trainingSetPath, trainresultPath); //训练集上
//		System.out.println("训练集上的正确率：" + trainAccuracy);
//		
//		double testAccuracy = svm.getAccuracy(testSetPath, testresultPath); //测试集上
//		System.out.println("测试集上的正确率：" + testAccuracy);
	}
}
