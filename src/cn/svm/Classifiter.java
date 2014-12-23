package cn.svm;

import java.io.IOException;

/**
 * 分类器基本操作，模板模式
 * 
 * @author epleone
 * 
 */
public abstract class Classifiter {
	protected double accuracy = 0;

	/**
	 * 完成分类的所有操作，包括初始化数据集，建立模型，分析测试集数据
	 * 
	 * @throws IOException
	 */
	public void progress() throws IOException {
		initDataSet();
		buildModel();
		analyzeTestSet();
	}

	protected abstract void initDataSet() throws IOException;

	protected abstract void buildModel() throws IOException;

	protected abstract void analyzeTestSet() throws IOException;

	/**
	 * 测试精度
	 * 
	 * @return 模型精度
	 */
	public double getAccuracy() {
		return accuracy;
	};
}
