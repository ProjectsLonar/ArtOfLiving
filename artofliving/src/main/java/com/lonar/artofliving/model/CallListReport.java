package com.lonar.artofliving.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lonar.artofliving.utils.UtilsMaster;

public class CallListReport {
	public static void excelAllData(Workbook workbook, Sheet sheet, List<LtAolCallListMaster> excelDataList,
			RequestDto requestDto) throws FileNotFoundException, IOException, ParseException {
		int rowIndex = 0;
		rowIndex = insertHeaderFilters(excelDataList, sheet, rowIndex, workbook, requestDto);
		++rowIndex;
		insertReportHeader(sheet, rowIndex, workbook);
		int srNo = 0;
		CellStyle styleTableRow = workbook.createCellStyle();

		styleTableRow.setBorderLeft(BorderStyle.THIN);
		styleTableRow.setBorderRight(BorderStyle.THIN);
		styleTableRow.setBorderTop(BorderStyle.THIN);
		styleTableRow.setBorderBottom(BorderStyle.THIN);
		styleTableRow.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		styleTableRow.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		styleTableRow.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		styleTableRow.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		CellStyle styleTableRowAmount = workbook.createCellStyle();
		styleTableRowAmount.setAlignment(HorizontalAlignment.LEFT);
		styleTableRowAmount.setBorderLeft(BorderStyle.THIN);
		styleTableRowAmount.setBorderRight(BorderStyle.THIN);
		styleTableRowAmount.setBorderTop(BorderStyle.THIN);
		styleTableRowAmount.setBorderBottom(BorderStyle.THIN);

		for (LtAolCallListMaster excelResponseDataDto : excelDataList) {
			++rowIndex;
			++srNo;
			insertReportData(sheet, rowIndex, excelResponseDataDto, srNo, workbook, styleTableRow, styleTableRowAmount);
			// totalRevenu = totalRevenu + excelResponseDataDto.getAmount();
		}

	}

	public static void excelErrorData(Workbook workbook, Sheet sheet, List<LtMasterCallingListErrorDto> excelDataList,
			RequestDto requestDto) throws FileNotFoundException, IOException, ParseException {
		int rowIndex = 0;
		insertErrorReportHeader(sheet, rowIndex, workbook);
		int srNo = 0;
		CellStyle styleTableRow = workbook.createCellStyle();
		styleTableRow.setAlignment(HorizontalAlignment.LEFT);
		styleTableRow.setBorderLeft(BorderStyle.THIN);
		styleTableRow.setBorderRight(BorderStyle.THIN);
		styleTableRow.setBorderTop(BorderStyle.THIN);
		styleTableRow.setBorderBottom(BorderStyle.THIN);

		for (LtMasterCallingListErrorDto excelResponseDataDto : excelDataList) {
			++rowIndex;
			++srNo;
			insertErrorReportData(sheet, rowIndex, excelResponseDataDto, srNo, workbook, styleTableRow);
			// totalRevenu = totalRevenu + excelResponseDataDto.getAmount();
		}

	}

	private static int insertHeaderFilters(List<LtAolCallListMaster> excelData, Sheet sheet, int rowIndex,
			Workbook workbook, RequestDto requestDto) throws ParseException {
		CellStyle styleTitle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 16);
		font.setUnderline(XSSFFont.U_SINGLE);
		font.setBold(true);
		styleTitle.setFont(font);
		styleTitle.setAlignment(HorizontalAlignment.CENTER);

		Row row = null;
		Cell cell = null;
		row = sheet.createRow(rowIndex);
		/*
		 * cell = row.createCell(0); cell.setCellValue("Price List");
		 * cell.setCellStyle(styleTitle); sheet.addMergedRegion(new
		 * CellRangeAddress(rowIndex, rowIndex+1, 0, 3));
		 */

		CellStyle styleTable1 = workbook.createCellStyle();
		Font fontTable1 = workbook.createFont();
		fontTable1.setFontHeightInPoints((short) 12);
		fontTable1.setBold(true);
		styleTable1.setFont(fontTable1);

		CellStyle styleTableNumber = workbook.createCellStyle();
		styleTableNumber.setAlignment(HorizontalAlignment.RIGHT);

		// rowIndex++;
		// rowIndex++;
		// rowIndex++;
		row = sheet.createRow(rowIndex);
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("1");
		cell.setCellStyle(styleTableNumber);
		cell = row.createCell(1);
		cell.setCellValue("Mobile Number");
		cell.setCellStyle(styleTable1);
		cell = row.createCell(2);
		if (requestDto.getMobileNumber() != null) {
			cell.setCellValue(requestDto.getMobileNumber());

		} else {
			cell.setCellValue("ALL");

		}
		// sheet.autoSizeColumn(2);

		rowIndex++;
		/*
		 * row = sheet.createRow(rowIndex); cell = row.createCell(0);
		 * cell.setCellValue("2"); cell.setCellStyle(styleTableNumber); cell =
		 * row.createCell(1); cell.setCellValue("Start Date");
		 * cell.setCellStyle(styleTable1); cell = row.createCell(2); if
		 * (requestDto.getStartDate() != null) {
		 * cell.setCellValue(requestDto.getStartDate());
		 * 
		 * } else { cell.setCellValue("ALL");
		 * 
		 * } // sheet.autoSizeColumn(2);
		 * 
		 * rowIndex++; row = sheet.createRow(rowIndex); cell = row.createCell(0);
		 * cell.setCellValue("3"); cell.setCellStyle(styleTableNumber); cell =
		 * row.createCell(1); cell.setCellValue("End Date");
		 * cell.setCellStyle(styleTable1); cell = row.createCell(2); if
		 * (requestDto.getEndDate() != null) {
		 * cell.setCellValue(requestDto.getEndDate());
		 * 
		 * } else { cell.setCellValue("ALL");
		 * 
		 * }
		 * 
		 * rowIndex++; row = sheet.createRow(rowIndex); cell = row.createCell(0);
		 * cell.setCellValue("4"); cell.setCellStyle(styleTableNumber); cell =
		 * row.createCell(1); cell.setCellValue("Status");
		 * cell.setCellStyle(styleTable1); cell = row.createCell(2); if
		 * (requestDto.getStatus() != null) { cell.setCellValue(requestDto.getStatus());
		 * 
		 * } else { cell.setCellValue("ALL");
		 * 
		 * }
		 * 
		 * if(requestDto.getPriceListDesc() != null) { rowIndex++; row =
		 * sheet.createRow(rowIndex); cell = row.createCell(0); cell.setCellValue("5");
		 * cell.setCellStyle(styleTableNumber); cell = row.createCell(1);
		 * cell.setCellValue("PriceList Description"); cell.setCellStyle(styleTable1);
		 * cell = row.createCell(2); if (requestDto.getPriceListDesc() != null) {
		 * cell.setCellValue(requestDto.getPriceListDesc());
		 * 
		 * } else { cell.setCellValue("");
		 * 
		 * } }
		 */
		rowIndex++;
		row = sheet.createRow(rowIndex);
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell = row.createCell(2);

		return rowIndex;
	}

	private static int insertReportHeader(Sheet sheet, int rowIndex, Workbook workbook) {
		CellStyle styleTablehead = workbook.createCellStyle();
		Font fontTablehead = workbook.createFont();
		fontTablehead.setFontHeightInPoints((short) 12);
		fontTablehead.setColor(IndexedColors.WHITE.getIndex());
		fontTablehead.setBold(true);
		styleTablehead.setFont(fontTablehead);
		styleTablehead.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
		styleTablehead.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleTablehead.setBorderBottom(BorderStyle.MEDIUM);
		styleTablehead.setBorderTop(BorderStyle.MEDIUM);
		styleTablehead.setBorderLeft(BorderStyle.MEDIUM);
		styleTablehead.setBorderRight(BorderStyle.MEDIUM);

		Row row = null;
		Cell cell = null;
		row = sheet.createRow(rowIndex);
		// sheet.autoSizeColumn(0);
		cell = row.createCell(0);
		cell.setCellValue("Mobile Number");
		cell.setCellStyle(styleTablehead);
		// sheet.autoSizeColumn(1);
		// sheet.autoSizeColumn(2);
		cell = row.createCell(1);
		cell.setCellValue("Name");
		cell.setCellStyle(styleTablehead);
		// sheet.autoSizeColumn(3);
		cell = row.createCell(2);
		cell.setCellValue("DOB");
		cell.setCellStyle(styleTablehead);

		// sheet.autoSizeColumn(4);
		cell = row.createCell(3);
		cell.setCellValue("Email");
		cell.setCellStyle(styleTablehead);

		cell = row.createCell(4);
		cell.setCellValue("Gender");
		cell.setCellStyle(styleTablehead);

		cell = row.createCell(5);
		cell.setCellValue("Address");
		cell.setCellStyle(styleTablehead);
		
		
		cell = row.createCell(6);
		cell.setCellValue("City");
		cell.setCellStyle(styleTablehead);

		cell = row.createCell(7);
		cell.setCellValue("Pincode");
		cell.setCellStyle(styleTablehead);

		

		return rowIndex;
	}

	private static int insertErrorReportHeader(Sheet sheet, int rowIndex, Workbook workbook) {
		CellStyle styleTablehead = workbook.createCellStyle();
		Font fontTablehead = workbook.createFont();
		fontTablehead.setFontHeightInPoints((short) 12);
		fontTablehead.setColor(IndexedColors.WHITE.getIndex());
		fontTablehead.setBold(true);
		styleTablehead.setFont(fontTablehead);
		styleTablehead.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
		styleTablehead.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleTablehead.setBorderBottom(BorderStyle.MEDIUM);
		styleTablehead.setBorderTop(BorderStyle.MEDIUM);
		styleTablehead.setBorderLeft(BorderStyle.MEDIUM);
		styleTablehead.setBorderRight(BorderStyle.MEDIUM);

		Row row = null;
		Cell cell = null;
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("Mobile Number");
		cell.setCellStyle(styleTablehead);

		cell = row.createCell(1);
		cell.setCellValue("Name");
		cell.setCellStyle(styleTablehead);
		// sheet.autoSizeColumn(3);
		cell = row.createCell(2);
		cell.setCellValue("DOB");
		cell.setCellStyle(styleTablehead);

		// sheet.autoSizeColumn(4);
		cell = row.createCell(3);
		cell.setCellValue("Email");
		cell.setCellStyle(styleTablehead);

		cell = row.createCell(4);
		cell.setCellValue("Gender");
		cell.setCellStyle(styleTablehead);

		cell = row.createCell(5);
		cell.setCellValue("Address");
		cell.setCellStyle(styleTablehead);

		cell = row.createCell(6);
		cell.setCellValue("City");
		cell.setCellStyle(styleTablehead);

		cell = row.createCell(7);
		cell.setCellValue("Pincode");
		cell.setCellStyle(styleTablehead);
		
		cell = row.createCell(8);
		cell.setCellValue("Row No");
		cell.setCellStyle(styleTablehead);

		cell = row.createCell(9);
		cell.setCellValue("Error");
		cell.setCellStyle(styleTablehead);

		return rowIndex;
	}

	public static CellStyle setAlignRight(XSSFWorkbook workbook) {
		CellStyle styleTableRow = workbook.createCellStyle();
		styleTableRow = workbook.createCellStyle();
		styleTableRow.setBorderBottom(BorderStyle.MEDIUM);
		styleTableRow.setBorderTop(BorderStyle.MEDIUM);
		styleTableRow.setBorderLeft(BorderStyle.MEDIUM);
		styleTableRow.setBorderRight(BorderStyle.MEDIUM);
		styleTableRow.setAlignment(HorizontalAlignment.RIGHT);
		return styleTableRow;
	}

	private static int insertReportData(Sheet sheet, int rowIndex, LtAolCallListMaster excelResponseDataDto, int srNo,
			Workbook workbook, CellStyle styleTableRow, CellStyle styleTableRowAmount) {
		Row row = null;
		Cell cell = null;
		row = sheet.createRow(rowIndex);

		cell = row.createCell(0);
		cell.setCellStyle(styleTableRowAmount);
		cell.setCellValue(excelResponseDataDto.getMobileNumber());
		// sheet.autoSizeColumn(1);

		
		cell = row.createCell(1);
		cell.setCellStyle(styleTableRowAmount);
		cell.setCellValue(excelResponseDataDto.getStudentName());
		// sheet.autoSizeColumn(3);

		cell = row.createCell(3);
		cell.setCellStyle(styleTableRowAmount);
		cell.setCellValue(excelResponseDataDto.getEmail());

		cell = row.createCell(4);
		cell.setCellStyle(styleTableRowAmount);
		cell.setCellValue(excelResponseDataDto.getGender());

		cell = row.createCell(5);
		cell.setCellStyle(styleTableRowAmount);
		cell.setCellValue(excelResponseDataDto.getAddress());

		cell = row.createCell(6);
		cell.setCellStyle(styleTableRowAmount);
		cell.setCellValue(excelResponseDataDto.getCity());

		cell = row.createCell(7);
		cell.setCellStyle(styleTableRowAmount);
		cell.setCellValue(excelResponseDataDto.getPinCode());

		cell = row.createCell(2);
		cell.setCellStyle(styleTableRowAmount);
		if (excelResponseDataDto.getDob() != null) {
			cell.setCellValue(UtilsMaster.convertCustomDateFormat(excelResponseDataDto.getDob().toString(),
					"yyyy-MM-dd hh:mm:ss", "dd-MM-yyyy"));

		}

		return rowIndex;

	}

	private static int insertErrorReportData(Sheet sheet, int rowIndex, LtMasterCallingListErrorDto excelResponseDataDto,
			int srNo, Workbook workbook, CellStyle styleTableRow) {
		Row row = null;
		Cell cell = null;
		row = sheet.createRow(rowIndex);
		LtMasterCallingListRequestDto ltMastCallList = excelResponseDataDto.getLtMastcallingList();

		cell = row.createCell(0);
		cell.setCellStyle(styleTableRow);
		if (ltMastCallList.getMobileNumber() != null) {
			cell.setCellValue(ltMastCallList.getMobileNumber());
		} // sheet.autoSizeColumn(1);

		cell = row.createCell(1);
		cell.setCellStyle(styleTableRow);
		if(ltMastCallList.getFirstName() !=null) {
		cell.setCellValue(ltMastCallList.getFirstName());
		}
		// sheet.autoSizeColumn(3);

		cell = row.createCell(2);
		cell.setCellStyle(styleTableRow);
		if (ltMastCallList.getDob() != null && (!ltMastCallList.getDob().isEmpty())) {
			cell.setCellValue(UtilsMaster.convertCustomDateFormat(ltMastCallList.getDob(), "dd-MM-yyyy","dd-MM-yyyy"));

		}

		cell = row.createCell(3);
		cell.setCellStyle(styleTableRow);
		if(ltMastCallList.getEmail() !=null) {
		cell.setCellValue(ltMastCallList.getEmail());
		}

		cell = row.createCell(4);
		cell.setCellStyle(styleTableRow);
		if(ltMastCallList.getGender() !=null) {
		cell.setCellValue(ltMastCallList.getGender());
		}

		cell = row.createCell(5);
		cell.setCellStyle(styleTableRow);
		if (ltMastCallList.getAddress() != null) {
			cell.setCellValue(ltMastCallList.getAddress());
		}
		cell = row.createCell(6);
		cell.setCellStyle(styleTableRow);
		if(ltMastCallList.getCity() !=null) {
		cell.setCellValue(ltMastCallList.getCity());
		}
		
		cell = row.createCell(7);
		cell.setCellStyle(styleTableRow);
		if(ltMastCallList.getPincode() !=null) {
		cell.setCellValue(ltMastCallList.getPincode());
		}
	
		cell = row.createCell(8);
		cell.setCellStyle(styleTableRow);
		cell.setCellValue(excelResponseDataDto.getRowNo());
		System.out.println(excelResponseDataDto.getErrorList().toString());
		cell = row.createCell(9);
		cell.setCellStyle(styleTableRow);
		cell.setCellValue(excelResponseDataDto.getErrorList().toString().replace("[", "").replace("]", ""));

		return rowIndex;

	}
}
