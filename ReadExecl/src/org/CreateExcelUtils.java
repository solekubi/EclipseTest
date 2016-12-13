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
	            // 创建新的Excel 工作簿
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            HSSFSheet sheet = workbook.createSheet();
	            HSSFRow row = null;
	            HSSFCell cell = null;
	            int rowIndex = 0;
	            int cellIndex = 0;


	            //样式1：标题样式
	            CellStyle cellStyle = workbook.createCellStyle();
	            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	            // 设置单元格字体
	            HSSFFont headerFont = workbook.createFont(); // 字体
	            headerFont.setFontHeightInPoints((short) 12);
	            cellStyle.setFont(headerFont);
	            cellStyle.setWrapText(true);
	            //填充的背景颜色
	            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	            HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
	            palette.setColorAtIndex(HSSFColor.BROWN.index, (byte) 206, (byte) 190, (byte) 156);
	            cellStyle.setFillForegroundColor(HSSFColor.BROWN.index);//前景色
	            cellStyle.setFillBackgroundColor(HSSFCellStyle.THICK_FORWARD_DIAG);
	            // 设置单元格边框及颜色
	            cellStyle.setBorderBottom((short) 1);
	            cellStyle.setBorderLeft((short) 1);
	            cellStyle.setBorderRight((short) 1);
	            cellStyle.setBorderTop((short) 1);
	            cellStyle.setWrapText(true);
	            HSSFPalette cellPalette = ((HSSFWorkbook) workbook).getCustomPalette(); // 创建颜色 这里创建的是绿色边框
	            cellStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index); // 设置边框颜色
	            cellStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
	            cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
	            cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);

	            //样式2：内容样式
	            CellStyle contentStyle = workbook.createCellStyle();
	            contentStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
	            contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	            //内容背景颜色
	            contentStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	            HSSFPalette contentPalette = ((HSSFWorkbook) workbook).getCustomPalette();
	            contentPalette.setColorAtIndex(HSSFColor.YELLOW.index, (byte) 255, (byte) 251, (byte) 231);
	            contentStyle.setFillForegroundColor(HSSFColor.YELLOW.index);//前景色
	            contentStyle.setFillBackgroundColor(HSSFCellStyle.THICK_FORWARD_DIAG);
	            // 设置单元格边框及颜色
	            contentStyle.setBorderBottom((short) 1);
	            contentStyle.setBorderLeft((short) 1);
	            contentStyle.setBorderRight((short) 1);
	            contentStyle.setBorderTop((short) 1);
	            contentStyle.setWrapText(true);
	            contentStyle.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);   //设置边框颜色
	            contentStyle.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
	            contentStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
	            contentStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);


	            //生成标题
	            row = sheet.createRow(rowIndex++); 
	            if(titles != null && titles.size() > 0){
	            	for(String s : titles){
	            		cell = row.createCell(cellIndex, HSSFCell.CELL_TYPE_STRING);
	            		sheet.setColumnWidth(cellIndex++, 100*36);
	            		cell.setCellValue(s);
	                    cell.setCellStyle(cellStyle);   
	            	}
	            }
	            
	            //生成下拉
                if(cellListMap != null && cellListMap.size()>0){
                	for(Entry<String, List<String>> entry:cellListMap.entrySet()){
                		String key  = entry.getKey();
                		int index = titles.indexOf(key);
                		if(index > -1){
                			createCellList(sheet,entry.getValue(),index);
                		}
                	}
                }
	            
	            //生成表内容
	            if (datas != null && datas.size() > 0) {
	            	for (int i = 0; i < datas.size(); i++) {
		            	String [] values = datas.get(i);
		                if(values != null && titles != null && titles.size() == values.length && values.length > 0){
		                    if(rowIndex >= 65500){
		                        sheet = workbook.createSheet();
		                        rowIndex = 1;
		                    }
		                    row = sheet.createRow(rowIndex++);            //建立新行
		                    cellIndex = 0;                                //从列0开始
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
	            
	// 操作结束，关闭文件
	            webexcel.close();
	            System.out.println("文件生成...");


	        } catch (Exception e) {
	            System.out.println("已运行 xlCreate() : " + e);
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
	          // 生成下拉框内容  
	          DVConstraint constraint = DVConstraint.createExplicitListConstraint(str);  
	          // 绑定下拉框和作用区域  
	          HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);  
	          // 对sheet页生效  
	          sheet.addValidationData(data_validation);  
		 }	
	  }
	 
	    public static void main(String[] args) {
	     List<String> titles  = new ArrayList<>();
	     titles.add("姓名");
	     titles.add("性别");
	     List<String> values = new ArrayList<>();
	     values.add("小红");
	     values.add("小明");
	     List<String> values2 = new ArrayList<>();
	     values2.add("男");
	     values2.add("女");
	     Map<String, List<String>> cellListMap = new HashMap<>();
	     cellListMap.put("姓名", values);
	     cellListMap.put("性别", values2);
	     try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("E:\\test.xlsx")));
			OutputExcel(bos,titles,null,cellListMap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }

}
