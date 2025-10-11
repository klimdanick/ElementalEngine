package com.danick.e2.renderer;

public class Font {
	
	public static final Font STANDARD = new Font("font.png");
	
	public Graphic fontImage;
	private int[] offsets;
	private int[] widths;
	
	public Font(String path) {
		fontImage = Graphic.fromImage(path);
		
		offsets = new int[59];
		widths = new int[59];
		
		int unicode = 0;
		
		for(int i = 0; i < fontImage.pixelWidth; i++) {
			if (fontImage.pBuffer[i] == 0xff0000ff) {
				offsets[unicode] = i;
			}
			
			if (fontImage.pBuffer[i] == 0xffffff00) {
				widths[unicode] = i - offsets[unicode];
				unicode++;
			}
		}
	}

	public Graphic getFontImage() {
		return fontImage;
	}

	public void setFontImage(Graphic fontImage) {
		this.fontImage = fontImage;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public void setOffsets(int[] offsets) {
		this.offsets = offsets;
	}

	public int[] getWidths() {
		return widths;
	}

	public void setWidths(int[] widths) {
		this.widths = widths;
	}

	public int getHeight() {
		return fontImage.pixelWidth;
	}
}
