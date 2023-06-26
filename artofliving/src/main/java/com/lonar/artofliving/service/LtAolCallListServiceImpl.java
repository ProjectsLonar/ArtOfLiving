package com.lonar.artofliving.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dao.LtAolCallListMasterDao;
import com.lonar.artofliving.dto.ResponseDto;
import com.lonar.artofliving.model.AssignedOrderDto;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtAolCallListMaster;
import com.lonar.artofliving.model.LtAolProductMaster;
import com.lonar.artofliving.model.LtMasterCallingListErrorDto;
import com.lonar.artofliving.model.LtMasterCallingListRequestDto;
import com.lonar.artofliving.model.RequestDto;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.utils.UtilsMaster;

@Service
public class LtAolCallListServiceImpl implements LtAolCallListService,CodeMaster {

	@Autowired
	private LtAolCallListMasterDao ltAolCallListMasterDao;
	
	String reportDateTime = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(Calendar.getInstance().getTime());
	
	@Autowired
	private Environment env;
	
	@Override
	public Status getAllCallListById(RequestDto requestDto) throws ServiceException, IOException {
		
			Status status =new Status();
			List<ResponseDto> responseDto= ltAolCallListMasterDao.getAllCallListById(requestDto);
			if(responseDto!= null) {
				status.setCode(RECORD_FOUND);
				status.setMessage("Record Found.");
				status.setData(responseDto);
				return status;
			}else {
			status.setCode(RECORD_NOT_FOUND);
		status.setMessage("Record Not Found.");
		status.setData(null);
			}
		return status;
	}
	
	@Override
	public Status saveAssignedTo(AssignedOrderDto assignedOrderDto) throws ServiceException,IOException, JSONException {
		Status status = new Status();
		List<LtAolCallListMaster> ltAolCallList = new ArrayList<LtAolCallListMaster>();
		//assigned 
		if(assignedOrderDto.getAssignedTo() !=null) {	
			for(String mobileNumberList: assignedOrderDto.getMobileNumber()) {
				LtAolCallListMaster  ltAolCallListMaster = ltAolCallListMasterDao.getAolCallListByMobileNumber(mobileNumberList); 
				if(ltAolCallListMaster !=null) {
					if((ltAolCallListMaster.getAssignedTo() == null) || (ltAolCallListMaster.getAssignedTo() == 0) ) {
						ltAolCallListMaster.setAssignedTo(assignedOrderDto.getAssignedTo());
						ltAolCallListMaster.setLastUpdatedBy(assignedOrderDto.getUserId());
						ltAolCallListMaster.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
						ltAolCallListMaster.setLastUpdateLogin(assignedOrderDto.getUserId());
					}
				}
				LtAolCallListMaster ltAolCallListSaved =ltAolCallListMasterDao.save(ltAolCallListMaster);
						ltAolCallList.add(ltAolCallListSaved);
			}
		}//unassigned
		else {
			
			for(String mobileNumberList: assignedOrderDto.getMobileNumber()) {
				LtAolCallListMaster  ltAolCallListMaster = ltAolCallListMasterDao.getAolCallListByMobileNumber(mobileNumberList); 
				if(ltAolCallListMaster !=null) {
					if((ltAolCallListMaster.getAssignedTo() != null) || (ltAolCallListMaster.getAssignedTo() != 0) ) {
						ltAolCallListMaster.setAssignedTo(null);
						ltAolCallListMaster.setLastUpdatedBy(assignedOrderDto.getUserId());
						ltAolCallListMaster.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
						ltAolCallListMaster.setLastUpdateLogin(assignedOrderDto.getUserId());
					}
				}
				LtAolCallListMaster ltAolCallListSaved =ltAolCallListMasterDao.save(ltAolCallListMaster);
						ltAolCallList.add(ltAolCallListSaved);
			}
		
		}
		
		if(!ltAolCallList.isEmpty()) {
			status.setCode(UPDATE_SUCCESSFULLY); 
			status.setMessage("Update Successfully.");
			status.setData(ltAolCallList);
			return status;
			}else {
			status.setCode(UPDATE_FAIL);
			status.setMessage("Update Fail.");
			status.setData(null);
			return status;
			}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Status uploadMasterCallingList(Long userId, MultipartFile file) throws ServiceException, IOException {

		Status status = new Status();
		String fileUploadPath = env.getProperty("fileUploadPathForMasterCallingList");
		String fileDownloadPath = env.getProperty("fileDownloadPathForExcelMasterCallingList");
		String imgDownloadPath = fileDownloadPath + "" + reportDateTime + "_" + file.getOriginalFilename();
		// System.out.println("filePath-->" + imgDownloadPath);
		File dir = new File(fileUploadPath);
		if (!dir.exists()) {
			dir.mkdirs();
			if (!dir.isDirectory()) {
				status.setCode(NO_DIRECTIVE_EXISTS);
				status.setMessage("No Directive Exists");
				return status;
			}
		}

		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			BufferedOutputStream buffStream = new BufferedOutputStream(
					new FileOutputStream(new File(fileUploadPath + reportDateTime + "_" + file.getOriginalFilename())));
			buffStream.write(bytes);
			buffStream.close();
		}

		status = readExcelFile(userId, imgDownloadPath);
		
		// TODO Auto-generated method stub
		if (status.getCode() == SUCCESS) {
			List<LtAolCallListMaster> list = (List<LtAolCallListMaster>) status.getData();
			if (list != null) {

				if (!list.isEmpty()) {
					List<LtAolCallListMaster> updatedList = ltAolCallListMasterDao.saveAll(list);
					if (!updatedList.isEmpty()) {
						status.setCode(INSERT_SUCCESSFULLY);
						status.setData(updatedList);
						status.setMessage("File Uploaded Successfully.");
					} else {
						status.setCode(INSERT_FAIL);
						status.setMessage("Fail to Upload File.");
					}

				}

			} else {
				status.setCode(INSERT_FAIL);
				status.setMessage("Please Fill Details In Proper Format.");

			}

		} 
		if (status.getCode() == FAIL) {
			if (status.getData() != null) {
				System.out.println("-----fail data------" + status.getData().toString());
				Map<Integer, Map<LtMasterCallingListRequestDto, List<String>>> data = (Map<Integer, Map<LtMasterCallingListRequestDto, List<String>>>) status
						.getData();
				List<LtMasterCallingListErrorDto> errorList = getLtMasterCalingListErrorRecordList(data);
				if (errorList != null) {
					try {
						status = generateMasterCAllingListErrorReport(errorList, null);
						if (status.getCode() == SUCCESS) {
							status.setCode(FILE_DOWNLOAD);
						}
					} catch (IOException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return status;
	}

	
	public Status readExcelFile(Long userId, String filePath) {
		List<LtAolCallListMaster> ltMastPriceList = new ArrayList<LtAolCallListMaster>();
		Status status = new Status();
		//List<LtAolCallListMaster> histroryList = new ArrayList<LtAolCallListMaster>();
		Map<Integer, Map<LtMasterCallingListRequestDto, List<String>>> errorcallingListmap = new HashMap<Integer, Map<LtMasterCallingListRequestDto, List<String>>>();
		String exceptionField = "";
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			if (sheet.getRow(0) != null) {
				status = checkHeaderRow(sheet.getRow(0));
			} else {
				status.setCode(FAIL);
				status.setMessage("Invalid File Format.");
				return status;
			}
			if (status.getCode() == FAIL) {
				return status;
			}

			for (Row row : sheet) // iteration over row using for each loop
			{
				Map<LtMasterCallingListRequestDto, List<String>> rowMap = new HashMap<LtMasterCallingListRequestDto, List<String>>();
				List<String> errorList = new ArrayList<String>();
				System.out.println(sheet.getLastRowNum());
				if (row.getRowNum() > 0 && row.getRowNum() <= sheet.getLastRowNum()) {

					if (row.getCell(0) == null) {
						row.createCell(0);
						row.getCell(0).setCellValue("");
					}

					if (row.getCell(1) == null) {
						row.createCell(1);
						row.getCell(1).setCellValue("");
					}
					if (row.getCell(2) == null) {
						row.createCell(2);
						row.getCell(2).setCellValue("");
					}
					if (row.getCell(3) == null) {
						row.createCell(3);
						row.getCell(3).setCellValue("");
					}

					if (row.getCell(4) == null) {
						row.createCell(4);
						row.getCell(4).setCellValue("");
					}
					if (row.getCell(5) == null) {
						row.createCell(5);
						row.getCell(5).setCellValue("");
					}

					if (row.getCell(7) == null || row.getCell(7).getCellType().equals(CellType.BLANK)) {
						row.createCell(7);
						row.getCell(7).setCellValue("");
					}
					if (row.getCell(8) == null) {
						row.createCell(8);
						row.getCell(8).setCellValue("");
					}
					if (row.getCell(9) == null) {
						row.createCell(9);
						row.getCell(9).setCellValue("");
					}
					LtAolCallListMaster ltMastCallingListData = new LtAolCallListMaster();
					LtMasterCallingListRequestDto excelLtMasterCallingListRequestDto = new LtMasterCallingListRequestDto();
					status = validateData(row);
					// System.out.println("-->"+status);
					if (status.getCode() == FAIL) {
						// System.out.println(row.getRowNum());
						errorList.addAll((List<String>) status.getData());
						excelLtMasterCallingListRequestDto = getErrorMasterCallingListData(row);
						exceptionField = "";
//						if (!excelLtMasterCallingListRequestDto.getProductCode().isEmpty())
//							ltMastCallingListData.setProductCode(excelLtMastPriceListRequestDto.getProductCode());
//						if (!excelLtMasterCallingListRequestDto.getPriceList().isEmpty())
//							ltMastCallingListData.setPriceList(excelLtMastPriceListRequestDto.getPriceList());
//						if ((!excelLtMasterCallingListRequestDto.getListPrice().isEmpty())
//								&& (row.getCell(5).getCellType() == CellType.NUMERIC))
//							ltMastCallingListData
//									.setListPrice(new Double(excelLtMasterCallingListRequestDto.getListPrice().toString()));
//						if (!excelLtMasterCallingListRequestDto.getStatus().isEmpty())
//							ltMastCallingListData.setStatus(excelLtMastPriceListRequestDto.getStatus());
					} else {

						if (!row.getCell(0).toString().isEmpty()) {
							///ltMastCallingListData.setf(new Double(row.getCell(0).getNumericCellValue()).longValue());

						}
						///ltMastPriceListData.setPriceList(row.getCell(1).toString().toUpperCase());
						///ltMastPriceListData.setPriceListDesc(row.getCell(2).toString());
						///ltMastPriceListData.setCurrency(row.getCell(3).toString().toUpperCase());
					///	ltMastPriceListData.setProductCode(row.getCell(4).toString().toUpperCase());
						if (!row.getCell(5).toString().isEmpty()) {

							///ltMastPriceListData.setListPrice(new Double(row.getCell(5).toString()));
						}
					///	ltMastPriceListData.setStatus(row.getCell(6).toString().toUpperCase());
						try {
							exceptionField = "Start Date";
							if (!row.getCell(7).toString().isEmpty()) {
								if (row.getCell(7).getCellType() == CellType.NUMERIC) {
									///ltMastPriceListData.setStartDate(UtilsMaster.convertDate(row.getCell(7).getDateCellValue()));
								} else {
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
									Date startDate = sdf.parse(row.getCell(7).toString());
								///	ltMastPriceListData.setStartDate(UtilsMaster.convertDate(startDate));

								}
							}
							exceptionField = "End Date";
							if (!row.getCell(8).toString().isEmpty()) {
								if (row.getCell(7).getCellType() == CellType.NUMERIC) {

									///ltMastPriceListData.setEndDate(UtilsMaster.convertDate(row.getCell(8).getDateCellValue()));
								} else {
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
									Date endDate = sdf.parse(row.getCell(8).toString());
									///ltMastPriceListData.setEndDate(UtilsMaster.convertDate(endDate));

								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							status.setCode(FAIL);

							status.setMessage("Date Format Should be " + " In DD-MM-YYYY For " + exceptionField);
							errorList.add(status.getMessage());

							// return status;
							// TODO: handle exception
						}
						if (!row.getCell(9).toString().isEmpty()) {
							///ltMastPriceListData.setPtrPrice(new Double(row.getCell(9).toString()));
						}
					}
					exceptionField = "";
		///			if ((ltMastPriceListData.getProductCode() != null)
		///					&& (!ltMastPriceListData.getProductCode().isEmpty())) {
		///				status = checkPriceListAgainstProductCode(ltMastPriceListData);

		///				if (status.getCode() == FAIL) {
		///					errorList.addAll((List<String>) status.getData());
		///					excelLtMastPriceListRequestDto = getErrorPriceListData(row);
							// return status;
		///				}

						if (status.getCode() != FAIL) {
							if (status.getCode() == RECORD_FOUND) {
								// update priceList
					///			LtMastPriceList updatedltMastPriceList = (LtMastPriceList) status.getData();
					///			histroryList.add(updatedltMastPriceList);
					///			if (updatedltMastPriceList != null) {
					///				ltMastPriceListData.setPriceListId(updatedltMastPriceList.getPriceListId());
					///				ltMastPriceListData.setCreationDate(updatedltMastPriceList.getCreationDate());
					///				ltMastPriceListData.setCreatedBy(updatedltMastPriceList.getCreatedBy());
								}
							}
							if (status.getCode() == RECORD_NOT_FOUND) {
								// insert priceList
							///	ltMastPriceListData.setCreatedBy(userId);
							///	ltMastPriceListData.setLastUpdatedBy(userId);
							///	ltMastPriceListData.setCreationDate(UtilsMaster.getCurrentDateTime());
							}
							/// ltMastPriceListData.setLastUpdatedBy(userId);
							/// ltMastPriceListData.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
							/// ltMastPriceListData.setLastUpdateLogin(userId);
							///ltMastPriceList.add(ltMastPriceListData);
							status.setCode(SUCCESS);
							status.setData(ltMastPriceList);

						}
					}
				///	if (errorList != null && (!errorList.isEmpty())) {
				///		rowMap.put(excelLtMastPriceListRequestDto, errorList);
						// if (rowMap != null) {
				///		errorPriceListmap.put(row.getRowNum(), rowMap);
						// System.out.println(errorPriceListmap.toString());

						// }
				///	}

			///	}

	///		}

		///	if (errorPriceListmap != null && (!errorPriceListmap.isEmpty())) {
		///		status.setCode(FAIL);
				// System.out.println(errorPriceListmap.keySet());
		///		TreeMap<Integer, Map<ExcelLtMastPriceListRequestDto, List<String>>> sortedMap = new TreeMap<>();
		///		sortedMap.putAll(errorPriceListmap);
				// System.out.println(sortedMap);
		///		status.setData(sortedMap);
		///	}
//			} else {
//				if (histroryList != null && !histroryList.isEmpty()) {
//					Status historyStatus = saveHistoryPriceList(histroryList, userId);
//					if (historyStatus.getCode() != INSERT_SUCCESSFULLY) {
//						status.setCode(UPDATE_FAIL);
//						status.setMessage("Unable To Insert Record In PriceList History Table");
//					}
//				}
//
//			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(FAIL);
			status.setMessage(e.getMessage());

		}
		return status;
	}
	
	private Status checkHeaderRow(Row headerRow) {
		Status status = new Status();
		if (!headerRow.getCell(0).getStringCellValue().isEmpty()) {
			if ((headerRow.getCell(0).getStringCellValue().equalsIgnoreCase("First Name"))
					&& (headerRow.getCell(1).getStringCellValue().equalsIgnoreCase("Last Name"))
					&& (headerRow.getCell(2).getStringCellValue().equalsIgnoreCase("DOB"))
					&& (headerRow.getCell(3).getStringCellValue().equalsIgnoreCase("Gender"))
					&& (headerRow.getCell(4).getStringCellValue().equalsIgnoreCase("City"))
					&& (headerRow.getCell(5).getStringCellValue().equalsIgnoreCase("Mobile"))
					&& (headerRow.getCell(6).getStringCellValue().equalsIgnoreCase("Email"))
					&& (headerRow.getCell(7).getStringCellValue().equalsIgnoreCase("Address"))
					&& (headerRow.getCell(8).getStringCellValue().equalsIgnoreCase("Pincode"))) {
				status.setCode(SUCCESS);
			} else {
				status.setCode(FAIL);
				status.setMessage("Invalid File Format.");
			}
		} else {
			status.setCode(FAIL);
			status.setMessage("Invalid File Format.");

		}
		return status;
	}
	
	private List<LtMasterCallingListErrorDto> getLtMasterCalingListErrorRecordList(
			Map<Integer, Map<LtMasterCallingListRequestDto, List<String>>> map) {
		List<LtMasterCallingListErrorDto> list = new ArrayList<LtMasterCallingListErrorDto>();
		for (Map.Entry<Integer, Map<LtMasterCallingListRequestDto, List<String>>> entry : map.entrySet()) {
			LtMasterCallingListErrorDto dto = new LtMasterCallingListErrorDto();
			dto.setRowNo(entry.getKey());
			System.out.println(dto.toString());
			Map<LtMasterCallingListRequestDto, List<String>> subMap = entry.getValue();
			if (subMap != null) {
				for (Map.Entry<LtMasterCallingListRequestDto, List<String>> subEntry : subMap.entrySet()) {
					dto.setLtMastcallingList(subEntry.getKey());
					dto.setErrorList(subEntry.getValue());
				}
				list.add(dto);
			}
		}
		return list;
	}

	private Status generateMasterCAllingListErrorReport(List<LtMasterCallingListErrorDto> responceDataList, RequestDto requestDto)
			throws FileNotFoundException, IOException, ParseException {
		Status status = new Status();
		String saveDirectory = null;
		Workbook workbook = new SXSSFWorkbook();
		String fileName = null;

		String reportCreationPath = env.getProperty("reportCreationPath");
		String reportShowPath = env.getProperty("reportShowPath");
		saveDirectory = reportCreationPath;

		File dir = new File(saveDirectory);
		if (!dir.isDirectory()) {
			if (dir.mkdirs()) {
				System.out.println("Directory created");
			} else {
				System.out.println("Error in directory creation");
			}
		}
		///Sheet sheet = workbook.createSheet("PriceList_Error_Report");

		try {
		///	PriceListReport.excelErrorData(workbook, sheet, responceDataList, requestDto);
			fileName = "PriceList_Error_Report" + "_" + reportDateTime + ".xlsx";
			String filePath = saveDirectory + fileName;
			String fileShowPath = reportShowPath + fileName;
			System.out.println(fileShowPath);
			FileOutputStream fileOut;
			fileOut = new FileOutputStream(filePath);
			workbook.write(fileOut);
			fileOut.close();
			status.setCode(SUCCESS);
			status.setMessage("Error File Generated Successfully");
		///	status.setUrl(fileShowPath);
			return status;

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return status;
	}

	
	private Status validateData(Row row) {
		Status status = new Status();
		List<String> errorList = new ArrayList<String>();
		try {
			if (row.getCell(0).toString().isEmpty()) {
				status.setCode(FAIL);
				status.setMessage("First Name Is Empty.");
				errorList.add(status.getMessage());
			}

			if (row.getCell(1).toString().isEmpty()) {
				status.setCode(FAIL);
				status.setMessage("Last Name Is Empty.");
				errorList.add(status.getMessage());
			}
			if (row.getCell(2).toString().isEmpty()) {
				status.setCode(FAIL);
				status.setMessage("Date of Birth Is Empty.");
				errorList.add(status.getMessage());
			}
			if (row.getCell(3).toString().isEmpty()) {
				status.setCode(FAIL);
				status.setMessage("Gender Is Empty.");
				errorList.add(status.getMessage());
			}
			if (row.getCell(4).toString().isEmpty()) {
				status.setCode(FAIL);
				status.setMessage("City Is Empty.");
				errorList.add(status.getMessage());
			}
			// start date manadatory check
			if (row.getCell(5).toString().isEmpty()) {
				// System.out.println("---"+row.getCell(7).toString());
				status.setCode(FAIL);
				status.setMessage("Mobile Number Is Empty.");
				errorList.add(status.getMessage());
			}
			if (row.getCell(6).toString().isEmpty()) {
				status.setCode(FAIL);
				status.setMessage("Email Is Empty.");
				errorList.add(status.getMessage());
			}
			/*
			 * Status status1 = validateDataByTypeAndLength(row); if (status1.getCode() ==
			 * FAIL) { errorList.addAll((List<String>) status1.getData()); }
			 */
			// System.out.println(status1);
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(FAIL);
			status.setMessage(e.getMessage());
			errorList.add(status.getMessage());
			// TODO: handle exception
		}
		if ((errorList != null) && (!errorList.isEmpty())) {
			status.setCode(FAIL);
			status.setData(errorList);
		}

		return status;

	}
	
	private LtMasterCallingListRequestDto getErrorMasterCallingListData(Row row) {

		LtMasterCallingListRequestDto excelLtMasterCallingListRequestDto = new LtMasterCallingListRequestDto();
		excelLtMasterCallingListRequestDto.setFirstName(row.getCell(0).toString());
		excelLtMasterCallingListRequestDto.setLastName(row.getCell(1).toString());
		//excelLtMasterCallingListRequestDto.setDob(row.getCell(2).toString());
		excelLtMasterCallingListRequestDto.setGender(row.getCell(3).toString());
		excelLtMasterCallingListRequestDto.setCity(row.getCell(4).toString());
		excelLtMasterCallingListRequestDto.setMobileNumber(row.getCell(5).toString());
		excelLtMasterCallingListRequestDto.setEmail(row.getCell(6).toString());
		excelLtMasterCallingListRequestDto.setAddress(row.getCell(7).toString());
		excelLtMasterCallingListRequestDto.setPincode(row.getCell(8).toString());
			
		if (!row.getCell(2).toString().isEmpty() && (row.getCell(8).getCellType() == CellType.NUMERIC)) {
			///excelLtMasterCallingListRequestDto.setDob(UtilsMaster.convertDateFormat(row.getCell(2).getDateCellValue().toString(),"dd-MM-yyyy"));
		} else {
		///	excelLtMasterCallingListRequestDto.setDob(row.getCell(2).toString());
		}
		
		return excelLtMasterCallingListRequestDto;

	}
	
@Override
public Status getMyQueueList(RequestDto requestDto) throws ServiceException, IOException{
	Status status =new Status();
	List<ResponseDto> responseDto= ltAolCallListMasterDao.getMyQueueList(requestDto);
	if(responseDto!= null) {
		status.setCode(RECORD_FOUND);
		status.setMessage("Record Found Successfully.");
		status.setData(responseDto);
		return status;
	}
	else {	
status.setCode(RECORD_NOT_FOUND);
status.setMessage("Record Not Found.");
status.setData(null);
	}
return status;
}



@Override
public Status getAllCourses(RequestDto requestDto) throws ServiceException, IOException{
	Status status =new Status();
	List<LtAolProductMaster> responseDto= ltAolCallListMasterDao.getAllCourses(requestDto);
	if(responseDto!= null) {
		status.setCode(RECORD_FOUND);
		status.setMessage("Record Found Successfully.");
		status.setData(responseDto);
		return status;
	}
	else {	
status.setCode(RECORD_NOT_FOUND);
status.setMessage("Record Not Found.");
status.setData(null);
	}
return status;
}
}
