package com.luog.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	public static List<List<String>> readExcel(String filePath, int sheetIndex, int startRow) {
		// 用来存放表中数据
		List<List<String>> list = new ArrayList<>();

		if (StringUtils.isEmpty(filePath)) {
			return list;
		}
		Workbook wb = getWorkbook(filePath);
		if (wb == null || wb.getNumberOfSheets() < sheetIndex) {
			return list;
		}

		// 获取第一个sheet
		Sheet sheet = wb.getSheetAt(sheetIndex);
		// 获取最大行数
		int rownum = sheet.getPhysicalNumberOfRows();
		// 获取第一行
		Row row = sheet.getRow(startRow);
		// 获取最大列数
		int colnum = row.getPhysicalNumberOfCells();
		for (int i = startRow; i < rownum; i++) {
			List<String> rowList = new ArrayList();
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			for (int j = 0; j < colnum; j++) {
				String cellData = (String) getCellFormatValue(row.getCell(j));
				rowList.add(cellData);
			}
			list.add(rowList);
		}

		return list;
	}

	private static Workbook getWorkbook(String filePath) {
		Workbook wb = null;
		String extString = filePath.substring(filePath.lastIndexOf("."));
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			if (".xls".equals(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(extString)) {
				return wb = new XSSFWorkbook(is);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}

	private static Object getCellFormatValue(Cell cell) {
		Object cellValue = null;
		if (cell == null) {
			return "";
		}
		// 判断cell类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: {
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		}
		case Cell.CELL_TYPE_FORMULA: {
			// 判断cell是否为日期格式
			if (DateUtil.isCellDateFormatted(cell)) {
				// 转换为日期格式YYYY-mm-dd
				cellValue = cell.getDateCellValue();
			} else {
				// 数字
				cellValue = String.valueOf(cell.getNumericCellValue());
			}
			break;
		}
		case Cell.CELL_TYPE_STRING: {
			cellValue = cell.getRichStringCellValue().getString();
			break;
		}
		default:
			cellValue = "";
		}

		return cellValue;
	}

	public static void writeExcel(List<List<String>> dataList, String[] titles, String path) {
		// 创建工作薄 xlsx
		XSSFWorkbook xssWorkbook = new XSSFWorkbook();

		try {
			// 创建工作表
			XSSFSheet sheet = xssWorkbook.createSheet("sheet1");

			int row = 0;
			int col = 0;
			XSSFRow rows = sheet.createRow(row);
			for (String title : titles) {
				// 向工作表中添加数据
				rows.createCell(col).setCellValue(title);
				col++;
			}
			row++;

			for (List<String> list : dataList) {
				rows = sheet.createRow(row);
				col = 0;
				for (String val : list) {
					// 向工作表中添加数据
					rows.createCell(col).setCellValue(val);
					col++;
				}
				row++;
			}
			//
			File xlsFile = new File(path);

			try {
				FileOutputStream xlsStream = new FileOutputStream(xlsFile);
				xssWorkbook.write(xlsStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				xssWorkbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
