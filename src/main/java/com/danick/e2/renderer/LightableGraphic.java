package com.danick.e2.renderer;

import com.danick.e2.AssetLoader.AssetLoader;

public class LightableGraphic extends Graphic {
	public Graphic normalMap, colorMap, colorRamp = null;
	public Shader shader = Shader.diffuse();
	public static double[] LightVec = {0, 1, 0};
	public LightableGraphic(Graphic normalMap, Graphic colorMap, Graphic colorRamp) {
		super(normalMap.pixelWidth, normalMap.pixelHeight);
		this.normalMap = normalMap;
		this.colorMap = colorMap;
		this.colorRamp = colorRamp;
	}
	
	public LightableGraphic(String normalMap, String colorMap, String colorRamp) {
		super(0, 0);
		AssetLoader.Init();
		this.normalMap = AssetLoader.getGraphicAsset(normalMap);
		this.colorMap = AssetLoader.getGraphicAsset(colorMap);
		this.colorRamp = AssetLoader.getGraphicAsset(colorRamp);
		
		if (this.normalMap == null) this.normalMap = Graphic.fromImage(normalMap);
		if (this.colorMap == null) this.colorMap = Graphic.fromImage(colorMap);
		if (this.colorRamp == null) this.colorRamp = Graphic.fromImage(colorRamp);
		this.pBuffer = this.normalMap.pBuffer.clone();
		this.zBuffer = this.normalMap.zBuffer.clone();
		this.pixelWidth = this.normalMap.pixelWidth;
		this.pixelHeight = this.normalMap.pixelHeight;
	}
	
	public Graphic render() {
		return render(LightVec);
	}
	
	public Graphic render(double[] LightVec) {
		if (shader == null) {
			return renderNoShader(LightVec);
		}
		shader.fragmentBuf = normalMap.pBuffer;
		shader.gpBuf4 = LightVec;
		shader.gpBuf1 = colorMap.pBuffer;
		shader.gpBuf2 = colorRamp.pBuffer;
		int s;
		for (s = 256; s > 1; s--) {
			if (shader.fragmentBuf.length % s == 0) break;
		}
		shader.gpBuf0 = new int[] {s, normalMap.pixelHeight, normalMap.pixelHeight};
		shader.execute();
		this.pBuffer = shader.outputBuf;
		return this;
	}
	
	public Graphic renderNoShader(double[] LightVec) {
		for (int i = 0; i < pBuffer.length; i++) {
			int a = (normalMap.pBuffer[i] >> 24) & 0xff;
			int x = (normalMap.pBuffer[i] >> 16) & 0xff;
			int y = (normalMap.pBuffer[i] >> 8) & 0xff;
			int z = (normalMap.pBuffer[i] >> 0) & 0xff;
			
			if (a <= 0) continue; 
			
			double _x = (double) x / 0xff * 2 - 1;
			double _y = (double) y / 0xff * 2 - 1;
			double _z = (double) z / 0xff * 2 - 1;
			double dotProd = LightVec[0]*_x+LightVec[1]*_y+LightVec[2]*_z;
			
			int colorY = colorMap.pBuffer[i] & 0xff;
			if (colorY > colorRamp.pixelHeight-1 || colorY < 0) colorY = 0; 
			
			double value = dotProd > -1 ? dotProd < 1 ? dotProd : 1 : -1;
			value+=1;
			value/=2;
			value-=(1-LightVec[2])*0.125;
			int rampIndex = (int)(value * 19);
			if (rampIndex >= 20) rampIndex = 19;
			if (rampIndex <= 0) rampIndex = 0;
			int c = colorRamp.pBuffer[rampIndex+colorY*20];
			pBuffer[i] = c; //(a<<24) + ((int)(value*r) << 16) + ((int)(value*g) << 8) + ((int)(value*b) << 0);
		}
		return this;
	}
	
}
