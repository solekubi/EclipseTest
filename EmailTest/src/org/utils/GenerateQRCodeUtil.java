package org.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public class GenerateQRCodeUtil {

	private final static String  FILE_SEPARATOR = File.separator;
			
	public static String generateQRCode(String myText,String filePath){
		
		if(StringUtils.isBlank(myText)){
			myText = "http://www.baidu.com";
		}
		if(StringUtils.isBlank(filePath)){
			filePath = "F:"+FILE_SEPARATOR+"JSoft"+FILE_SEPARATOR+"QRCode";
		}
		
		int size = 200;
		String fileName = DateUtils.getDate(DateUtils.YYMMDDHHMMSS)+".jpg";
		String fileType = "jpg";
		File myFile  = new File(filePath,fileName); 
		if(!myFile.exists()){
			File root = new File(filePath);
			if(!root.exists()){
				root.mkdirs();
			}
			myFile.mkdirs();
		}
		
		try {
			Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
			hintMap.put(EncodeHintType.MARGIN, 1);
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(myText, BarcodeFormat.QR_CODE, size, size,hintMap);
			int GeneratorWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(GeneratorWidth, GeneratorWidth, BufferedImage.TYPE_INT_BGR);
			image.createGraphics();
			
			Graphics2D graphics2d = (Graphics2D)image.getGraphics();
			graphics2d.setColor(Color.white);
			graphics2d.fillRect(0, 0, GeneratorWidth, GeneratorWidth);
			graphics2d.setColor(Color.red);
			
            for (int i = 0; i < GeneratorWidth; i++) {
                for (int j = 0; j < GeneratorWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics2d.fillRect(i, j, 1, 1);
                    }
                }
            }
			ImageIO.write(image, fileType, myFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n\nYou have successfully created QR Code.");
		return filePath + fileName;
	}
}
