package edu.handong.csee;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.io.FileNotFoundException;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelReader {

	public ArrayList<String> getData(String path) {	
		ArrayList<String> values = new ArrayList<String>();

		System.out.println(path);

		try (InputStream inp = new FileInputStream(path)) {
			//InputStream inp = new FileInputStream("workbook.xlsx");

			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(2);
			Cell cell = row.getCell(1);
			if (cell == null)
				cell = row.createCell(3);

			values.add(cell.getStringCellValue());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return values;
	}

	public HashMap<String,ArrayList<ArrayList<String>>> getData(InputStream is, String fileName) {

		HashMap<String,ArrayList<ArrayList<String>>> Data = new HashMap<String,ArrayList<ArrayList<String>>>();
		
		String stuNum =fileName;
		ArrayList<ArrayList<String>> bbb = new ArrayList<ArrayList<String>>();

		try (InputStream inp = is) {
		
			//InputStream inp = new FileInputStream("workbook.xlsx");	
			Workbook wb = WorkbookFactory.create(inp);
			Sheet  sheet = wb.getSheetAt(0); 

			int rowNums=sheet.getPhysicalNumberOfRows();
			int cellNums = sheet.getRow(2).getPhysicalNumberOfCells();
			for(int i =0; i<rowNums;i++) {
				ArrayList<String> values = new ArrayList<String>();

				Row row = sheet.getRow(i);

				for(int j=0; j<cellNums;j++) {
					Cell	cell = row.getCell(j);
					String value="";
					if(cell==null) {
						continue;
						
					}else {
					switch(cell.getCellType()){
					case FORMULA:
						value = cell.getCellFormula();
						break;

					case NUMERIC:
						// 숫자일 경우, String형으로 변경하여 값을 읽는다.
						cell.setCellType(CellType.STRING);
						value = cell.getStringCellValue();
						break;
					case STRING:
						value = cell.getStringCellValue();
						break;
					case BLANK:
						//value = cell.getBooleanCellValue()+"";
						value = " ";
						break;
					case ERROR:
						value = cell.getErrorCellValue()+"";
						break;

					}	
					

				}
				values.add(cell.getStringCellValue());
					
					
				}
				if(Data.containsKey(stuNum)) {
					Data.get(stuNum).add(values);
				}
				else {
					bbb.add(values);
					Data.put(stuNum, bbb);
			} 
				

			wb.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return Data;
	}
}
