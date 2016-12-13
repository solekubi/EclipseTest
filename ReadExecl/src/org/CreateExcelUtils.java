package org;

import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;


public class CreateExcelUtils {
	
	
	public static void OutputExcel(OutputStream webexcel,List<String> titles,List<String []> datas,Map<String, List<String>> cellListMap) {
	        try {
	            // �����µ�Excel ������
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            HSSFSheet sheet = workbook.createSheet();
	            HSSFRow row = null;
	            HSSFCell cell = null;
	            int rowIndex = 0;
	            int cellIndex = 0;


	            //��ʽ1��������ʽ
	            CellStyle cellStyle = workbook.createCellStyle();
	            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	            // ���õ�Ԫ������
	            HSSFFont headerFont = workbook.createFont(); // ����
	            headerFont.setFontHeightInPoints((short) 12);
	            cellStyle.setFont(headerFont);
	            cellStyle.setWrapText(true);
	            //���ı�����ɫ
	            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	            HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
	            palette.setColorAtIndex(HSSFColor.BROWN.index, (byte) 206, (byte) 190, (byte) 156);
	            cellStyle.setFillForegroundColor(HSSFColor.BROWN.index);//ǰ��ɫ
	            cellStyle.setFillBackgroundColor(HSSFCellStyle.THICK_FORWARD_DIAG);
	            // ���õ�Ԫ��߿���ɫ
	            cellStyle.setBorderBottom((short) 1);
	            cellStyle.setBorderLeft((short) 1);
	            cellStyle.setBorderRight((short) 1);
	            cellStyle.setBorderTop((short) 1);
	            cellStyle.setWrapText(true);
	            HSSFPalette cellPalette = ((HSSFWorkbook) workbook).getCustomPalette(); // ������ɫ ���ﴴ��������ɫ�߿�
	            cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index); // ���ñ߿���ɫ
	            cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
	            cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
	            cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);

	            //��ʽ2��������ʽ
	            CellStyle contentStyle = workbook.createCellStyle();
	            contentStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
	            contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	            //���ݱ�����ɫ
	            contentStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	            HSSFPalette contentPalette = ((HSSFWorkbook) workbook).getCustomPalette();
	            contentPalette.setColorAtIndex(HSSFColor.YELLOW.index, (byte) 255, (byte) 251, (byte) 231);
	            contentStyle.setFillForegroundColor(HSSFColor.YELLOW.index);//ǰ��ɫ
	            contentStyle.setFillBackgroundColor(HSSFCellStyle.THICK_FORWARD_DIAG);
	            // ���õ�Ԫ��߿���ɫ
	            contentStyle.setBorderBottom((short) 1);
	            contentStyle.setBorderLeft((short) 1);
	            contentStyle.setBorderRight((short) 1);
	            contentStyle.setBorderTop((short) 1);
	            contentStyle.setWrapText(true);
	            contentStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);   //���ñ߿���ɫ
	            contentStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
	            contentStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
	            contentStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);


	            //���ɱ���
	            row = sheet.createRow(rowIndex++); 
	            if(titles != null && titles.size() > 0){
	            	for(String s : titles){
	            		cell = row.createCell(cellIndex, HSSFCell.CELL_TYPE_STRING);
	            		sheet.setColumnWidth(cellIndex++, 100*36);
	            		cell.setCellValue(s);
	                    cell.setCellStyle(cellStyle);   
	            	}
	            }
	            
	            //��������
                if(cellListMap != null && cellListMap.size()>0){
                	for(Entry<String, List<String>> entry:cellListMap.entrySet()){
                		String key  = entry.getKey();
                		int index = titles.indexOf(key);
                		if(index > -1){
                			createCellList(sheet,entry.getValue(),index);
                		}
                	}
                }
	            
	            //���ɱ�����
	            if (datas != null && datas.size() > 0) {
	            	for (int i = 0; i < datas.size(); i++) {
		            	String [] values = datas.get(i);
		                if(values != null && titles != null && titles.size() == values.length && values.length > 0){
		                    if(rowIndex >= 65500){
		                        sheet = workbook.createSheet();
		                        rowIndex = 1;
		                    }
		                    row = sheet.createRow(rowIndex++);            //��������
		                    cellIndex = 0;                                //����0��ʼ
		                    for(String s : values){
		                    	cell = row.createCell(cellIndex++);
		                    	cell.setCellValue(s);
		                    	cell.setCellStyle(contentStyle);
		                    }
		                }
					}
				}
	            
	            workbook.write(webexcel);
	            webexcel.flush();
	            
	// �����������ر��ļ�
	            webexcel.close();
	            System.out.println("�ļ�����...");


	        } catch (Exception e) {
	            System.out.println("������ xlCreate() : " + e);
	        }

	    }


	@SuppressWarnings("deprecation")
	public static void createCellList(HSSFSheet sheet,List<String> values,int columNum){
		 if(values != null && values.size()>0){
			 String[] str = new String[values.size()];
			 int index = 0;
			 for(String s : values){
				 str[index++] = s;
			 }
	          CellRangeAddressList regions = new CellRangeAddressList(1, 65535, columNum, columNum);  
	          // ��������������  
	          DVConstraint constraint = DVConstraint.createExplicitListConstraint(str);  
	          // �����������������  
	          HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);  
	          // ��sheetҳ��Ч  
	          sheet.addValidationData(data_validation);  
		 }	
	  }
	 
	    public static void main(String[] args) {
	     List<String> titles  = new ArrayList<>();
	     titles.add("����");
	     titles.add("�Ա�");
	     List<String> values = new ArrayList<>();
	     values.add("С��");
	     values.add("С��");
	     List<String> values2 = new ArrayList<>();
	     values2.add("��");
	     values2.add("Ů");
	     Map<String, List<String>> cellListMap = new HashMap<>();
	     cellListMap.put("����", values);
	     cellListMap.put("�Ա�", values2);
	     try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("E:\\test.xlsx")));
			OutputExcel(bos,titles,null,cellListMap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }

}
