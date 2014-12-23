package verifyCode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 用于保存图片特征
 * @author epleone 2014 August 3
 *
 */
public class Img {
	
	public int h;
	public int w;
	public int[][] data;
	
	public Img(BufferedImage img){
		getData(img);
	}
	
	public Img(File image) throws IOException{
		this(ImageIO.read(image));
	}
	
	private void getData(BufferedImage img){
		this.h = img.getHeight();
		this.w = img.getWidth();
		this.data =new  int[4][60];
		
		int[][] data1 = new int[h][w];
		int[] d = img.getRGB(0, 0, w, h, null, 0, w);
		
		for(int i =0;i<h;i++){
			for(int j=0;j<w;j++){
				int G = (d[j+i*w] >> 8) & 0xFF;//对于该验证码可以使用的方法
				data1[i][j] = G>127?0:1;
			}
		}
		
		//过滤特征点
		for(int i =1;i<h-1;i++){
			for(int j=1;j<w-1;j++){
				if(data1[i][j] == 0)
					continue;
				if(data1[i-1][j]==0&&data1[i][j-1]==0&&data1[i][j+1]==0&&data1[i+1][j]==0)
					data1[i][j]=0;
			}
		}
		
		int index =0;
		for(int i=2;i<8;i++){
			for(int j=0;j<10;j++){
				this.data[0][index++] = data1[j][i];
			}
		}
		
		index =0;
		for(int i=12;i<18;i++){
			for(int j=0;j<10;j++){
				this.data[1][index++] = data1[j][i];
			}
		}
		
		index =0;
		for(int i=22;i<28;i++){
			for(int j=0;j<10;j++){
				this.data[2][index++] = data1[j][i];
			}
		}
		
		index =0;
		for(int i=32;i<38;i++){
			for(int j=0;j<10;j++){
				this.data[3][index++] = data1[j][i];
			}
		}
	}
}
