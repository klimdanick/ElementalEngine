package com.danick.e2.renderer;

import java.util.ArrayList;

import com.aparapi.Kernel;
import com.aparapi.Range;
import com.aparapi.device.Device;
import com.aparapi.internal.kernel.KernelManager;

public abstract class Shader extends Kernel {
	@Constant public int[] gpBuf0, gpBuf1, gpBuf2, gpBuf3;
	@Constant public double[] gpBuf4, gpBuf5;
	@Constant public int[] fragmentBuf = null;
	public int[] outputBuf = null;
	Range range = null;
	public int batchSize = 10;
	
	public void execute() {
		if (fragmentBuf == null) System.err.append("[SHADER] fragmentShader is empty! cant run shader");
		range = Range.create(KernelManager.instance().bestDevice(), fragmentBuf.length, gpBuf0[0]);
		//System.out.println(range.getDevice());
		if (outputBuf == null) outputBuf = fragmentBuf.clone();
		execute(range);
		//this.dispose();
	}
	
	
	public static Shader diffuse() {return new Shader() {
		@Override
	    public void run() {
	        int i_ = getGlobalId();
	        for (int _i = 0; _i < 1; _i++) {
	        	int i = i_*1+_i;
		        int a = (fragmentBuf[i] >> 24) & 0xff;
				int x = (fragmentBuf[i] >> 16) & 0xff;
				int y = (fragmentBuf[i] >> 8) & 0xff;
				int z = (fragmentBuf[i] >> 0) & 0xff;
				
				if (a <= 0) continue; 
				
				double _x = (double) x / 0xff * 2 - 1;
				double _y = (double) y / 0xff * 2 - 1;
				double _z = (double) z / 0xff * 2 - 1;
				double dotProd = gpBuf4[0]*_x+gpBuf4[1]*_y+gpBuf4[2]*_z;
				
				int colorY = gpBuf1[i] & 0xff;
				//if (colorY > 3 || colorY < 0) colorY = 0; 
				
				double value = dotProd > -1 ? dotProd < 1 ? dotProd : 1 : -1;
				value+=1;
				value/=2;
				int rampIndex = (int)(value * 19);
				if (rampIndex >= 20) rampIndex = 19;
				if (rampIndex <= 0) rampIndex = 0;
				int c = gpBuf2[rampIndex+colorY*20];
				outputBuf[i] = c; //(a<<24) + ((int)(value*r) << 16) + ((int)(value*g) << 8) + ((int)(value*b) << 0);
	        }
	    }
	};}
}
