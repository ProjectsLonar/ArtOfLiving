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
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
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
import com.lonar.artofliving.common.Validation;
import com.lonar.artofliving.dao.LtAolCallListMasterDao;
import com.lonar.artofliving.dto.ResponseDto;
import com.lonar.artofliving.model.AssignedOrderDto;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtAolCallListMaster;
import com.lonar.artofliving.model.LtAolCallListStatus;
import com.lonar.artofliving.model.LtAolCallNotes;
import com.lonar.artofliving.model.LtAolProductMaster;
import com.lonar.artofliving.model.LtAolUserProducts;
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
			
			List<ResponseDto> responseDto = null;
			Long totalCount = (long) 0;
			if(requestDto.getRole().equalsIgnoreCase("Admin")) {
				responseDto = ltAolCallListMasterDao.getAllCallListById(requestDto);
				totalCount = ltAolCallListMasterDao.getCallListCount(requestDto);
			}else {
			 responseDto= ltAolCallListMasterDao.getAllCallListByIdExceptAdmin(requestDto);
			 totalCount = ltAolCallListMasterDao.getCallListCountExceptAdmin(requestDto);
			}
			//List<ResponseDto> responseDto= ltAolCallListMasterDao.getAllCallListById(requestDto);
			 
 //System.out.println("responseDto"+responseDto);

//System.out.println("count"+totalCount);
			if(requestDto.getCallListId() !=null ) {
				List<LtAolUserProducts> listOfCourses =  ltAolCallListMasterDao.getAllCoursesAgainstListId(requestDto.getCallListId());
				
				responseDto.get(0).setCoursesList(listOfCourses);
				//System.out.println("responseDto addres course" +responseDto);
				List<LtAolCallNotes> listOfNotes = ltAolCallListMasterDao.getAllNOtesAgainstNoteId(requestDto.getCallListId());
				
				responseDto.get(0).setNotesHistoryList(listOfNotes);
				//System.out.println("responseDto addes note" +responseDto);
				
			}
			if(responseDto!= null) {
				status.setCode(RECORD_FOUND);
				status.setMessage("Record Found.");
				status.setData(responseDto);
				status.setRecordCount((long)responseDto.size());
				status.setTotalCount(totalCount);
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
			for(Long mobileNumberList: assignedOrderDto.getMobileNumber()) {
				LtAolCallListMaster  ltAolCallListMaster = ltAolCallListMasterDao.getAolCallListByMobileNumber(mobileNumberList); 
				if(ltAolCallListMaster !=null) {
					if((ltAolCallListMaster.getAssignedTo() == null) || (ltAolCallListMaster.getAssignedTo() == 0) ) {
						ltAolCallListMaster.setAssignedTo(assignedOrderDto.getAssignedTo());
						ltAolCallListMaster.setStatus("Assigned");
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
			
			for(Long mobileNumberList: assignedOrderDto.getMobileNumber()) {
				LtAolCallListMaster  ltAolCallListMaster = ltAolCallListMasterDao.getAolCallListByMobileNumber(mobileNumberList); 
				if(ltAolCallListMaster !=null) {
					if((ltAolCallListMaster.getAssignedTo() != null)  ) {
						ltAolCallListMaster.setAssignedTo(null);
						ltAolCallListMaster.setStatus("New Contact");
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
		System.out.println("filePath-->" + imgDownloadPath);
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
		/*
		 * if (status.getCode() == FAIL) { if (status.getData() != null) {
		 * System.out.println("-----fail data------" + status.getData().toString());
		 * Map<Integer, Map<LtMasterCallingListRequestDto, List<String>>> data =
		 * (Map<Integer, Map<LtMasterCallingListRequestDto, List<String>>>) status
		 * .getData(); List<LtMasterCallingListErrorDto> errorList =
		 * getLtMasterCalingListErrorRecordList(data); if (errorList != null) { try {
		 * status = generateMasterCAllingListErrorReport(errorList, null); if
		 * (status.getCode() == SUCCESS) { status.setCode(FILE_DOWNLOAD); } } catch
		 * (IOException | ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } } }
		 */
		
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
						row.getCell(2).setCellValue("`");
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

					if (row.getCell(6) == null ) {
						row.createCell(6);
						row.getCell(6).setCellValue("");
					}
					
					if (row.getCell(7) == null ) {
						row.createCell(7);
						row.getCell(7).setCellValue("");
					}
					/*
					 * if (row.getCell(8) == null) { row.createCell(8);
					 * row.getCell(8).setCellValue(""); }
					 */
					/*
					 * if (row.getCell(9) == null) { row.createCell(9);
					 * row.getCell(9).setCellValue(""); }
					 */
					LtAolCallListMaster ltMastCallingListData = new LtAolCallListMaster();
					LtMasterCallingListRequestDto excelLtMasterCallingListRequestDto = new LtMasterCallingListRequestDto();
					status = validateData(row);
					// System.out.println("-->"+status);
					if (status.getCode() == FAIL) {
						// System.out.println(row.getRowNum());
						///errorList.addAll((List<String>) status.getData());
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
							
							ltMastCallingListData.setMobileNumber(new Double(row.getCell(0).getNumericCellValue()).longValue());
							
						}
						if (!row.getCell(1).toString().isEmpty()) {
							ltMastCallingListData.setStudentName(row.getCell(1).toString());
						}
						
						if (!row.getCell(3).toString().isEmpty()) {
							ltMastCallingListData.setEmail(row.getCell(3).toString());
						}
						if (!row.getCell(4).toString().isEmpty()) {
							ltMastCallingListData.setGender(row.getCell(4).toString());
						}
						if (!row.getCell(5).toString().isEmpty()) {
							ltMastCallingListData.setAddress(row.getCell(5).toString());
						}
						if (!row.getCell(6).toString().isEmpty()) {
							ltMastCallingListData.setCity(row.getCell(6).toString());
						}
					
						if (!row.getCell(7).toString().isEmpty()) {
							
							ltMastCallingListData.setPinCode(new Double(row.getCell(7).getNumericCellValue()).longValue());
						}
						
						try {
							exceptionField = "Date Of Birth";
							if (!row.getCell(2).toString().isEmpty()) {
								if (row.getCell(2).getCellType() == CellType.NUMERIC) {
									ltMastCallingListData.setDob(UtilsMaster.convertDate(row.getCell(2).getDateCellValue()));
								} else {
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
									Date startDate = sdf.parse(row.getCell(2).toString());
									ltMastCallingListData.setDob(UtilsMaster.convertDate(startDate));

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
						
					}
					exceptionField = "";
					if (ltMastCallingListData.getMobileNumber() != null) {
						//status = checkListAgainstMobileNumber(ltMastCallingListData);

						if (status.getCode() == FAIL) {
							errorList.addAll((List<String>) status.getData());
							excelLtMasterCallingListRequestDto = getErrorMasterCallingListData(row);
							// return status;
						}

						if (status.getCode() != FAIL) {
							if (status.getCode() == RECORD_FOUND) {
								// update priceList
								LtAolCallListMaster updatedltMastAolCallList = (LtAolCallListMaster) status.getData();
								//histroryList.add(updatedltMastAolCallList);
								if (updatedltMastAolCallList != null) {
									ltMastCallingListData.setCallListId(updatedltMastAolCallList.getCallListId());
									ltMastCallingListData.setCreationDate(updatedltMastAolCallList.getCreationDate());
									ltMastCallingListData.setCreatedBy(updatedltMastAolCallList.getCreatedBy());
									
								}
							}
							if (status.getCode() == RECORD_NOT_FOUND) {
								// insert priceList
								ltMastCallingListData.setCreatedBy(userId);
								ltMastCallingListData.setLastUpdatedBy(userId);
								ltMastCallingListData.setCreationDate(UtilsMaster.getCurrentDateTime());
							}
							ltMastCallingListData.setCreationDate(UtilsMaster.getCurrentDateTime());
							ltMastCallingListData.setCreatedBy(userId);
							ltMastCallingListData.setLastUpdatedBy(userId);
							ltMastCallingListData.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
							ltMastCallingListData.setLastUpdateLogin(userId);
							ltMastCallingListData.setCallSource("Excel");
							ltMastCallingListData.setStatus("New Contact");
							ltMastCallingListData.setStatusId(1l);
							ltMastPriceList.add(ltMastCallingListData);
							status.setCode(SUCCESS);
							status.setData(ltMastPriceList);

						}
					}
					if (errorList != null && (!errorList.isEmpty())) {
						rowMap.put(excelLtMasterCallingListRequestDto, errorList);
						// if (rowMap != null) {
						errorcallingListmap.put(row.getRowNum(), rowMap);
						// System.out.println(errorPriceListmap.toString());

						// }
					}

				}

			}

			if (errorcallingListmap != null && (!errorcallingListmap.isEmpty())) {
				status.setCode(FAIL);
				// System.out.println(errorPriceListmap.keySet());
				TreeMap<Integer, Map<LtMasterCallingListRequestDto, List<String>>> sortedMap = new TreeMap<>();
				sortedMap.putAll(errorcallingListmap);
				// System.out.println(sortedMap);
				status.setData(sortedMap);
			}
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
			if ((headerRow.getCell(1).getStringCellValue().equalsIgnoreCase("Name"))
					&& (headerRow.getCell(2).getStringCellValue().equalsIgnoreCase("DOB"))
					&& (headerRow.getCell(3).getStringCellValue().equalsIgnoreCase("Email"))
					&& (headerRow.getCell(0).getStringCellValue().equalsIgnoreCase("Mobile Number"))
					&& (headerRow.getCell(5).getStringCellValue().equalsIgnoreCase("Address"))
					&& (headerRow.getCell(4).getStringCellValue().equalsIgnoreCase("Gender"))
					&& (headerRow.getCell(6).getStringCellValue().equalsIgnoreCase("City"))
					&& (headerRow.getCell(7).getStringCellValue().equalsIgnoreCase("Pincode"))) {
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
	
	/*
	 * private List<LtMasterCallingListErrorDto>
	 * getLtMasterCalingListErrorRecordList( Map<Integer,
	 * Map<LtMasterCallingListRequestDto, List<String>>> map) {
	 * List<LtMasterCallingListErrorDto> list = new
	 * ArrayList<LtMasterCallingListErrorDto>(); for (Map.Entry<Integer,
	 * Map<LtMasterCallingListRequestDto, List<String>>> entry : map.entrySet()) {
	 * LtMasterCallingListErrorDto dto = new LtMasterCallingListErrorDto();
	 * dto.setRowNo(entry.getKey()); System.out.println(dto.toString());
	 * Map<LtMasterCallingListRequestDto, List<String>> subMap = entry.getValue();
	 * if (subMap != null) { for (Map.Entry<LtMasterCallingListRequestDto,
	 * List<String>> subEntry : subMap.entrySet()) {
	 * dto.setLtMastcallingList(subEntry.getKey());
	 * dto.setErrorList(subEntry.getValue()); } list.add(dto); } } return list; }
	 */

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
			fileName = "CallList_Error_Report" + "_" + reportDateTime + ".xlsx";
			String filePath = saveDirectory + fileName;
			String fileShowPath = reportShowPath + fileName;
			System.out.println(fileShowPath);
			FileOutputStream fileOut;
			fileOut = new FileOutputStream(filePath);
			workbook.write(fileOut);
			fileOut.close();
			status.setCode(SUCCESS);
			status.setMessage("Error File Generated Successfully.");
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
				status.setMessage("Mobile Number Is Empty.");
				errorList.add(status.getMessage());
			}
			
			//System.out.println("mobile number"+row.getCell(0).toString());
			//System.out.println("mobile number type"+row.getCell(0).getCellType());
			//row.getCell(0).setCellType(CellType.NUMERIC);
			//System.out.println("mobile number after type"+row.getCell(0).getCellType());
			Long mobileNumber = (new Double(row.getCell(0).getNumericCellValue()).longValue());
			//System.out.println("mobileNo."+mobileNumber);
			//Long mobileNumber = new Long(row.getCell(0).toString());
			//System.out.println("long"+mobileNumber);
			if (Validation.validatePhoneNumber(mobileNumber.toString())) {
				System.out.println("valid mobile number.");
		}else {
			//System.out.println("hi in else");
			status.setCode(FAIL);
			status.setMessage("Enter Valid Mobile Number. ");
			errorList.add(status.getMessage());
			//return status;
		}
			
			
			//Long mobileNumber = (new Double(row.getCell(0).getNumericCellValue())).longValue();
			 status = checkDuplicate(mobileNumber);
		     if (status.getCode() == FAIL) {
		    	 errorList.add(status.getMessage());
			   return status;
		      }

			/*
			 * if (row.getCell(1).toString().isEmpty()) { status.setCode(FAIL);
			 * status.setMessage("Last Name Is Empty."); errorList.add(status.getMessage());
			 * } if (row.getCell(2).toString().isEmpty()) { status.setCode(FAIL);
			 * status.setMessage("Date Of Birth Is Empty.");
			 * errorList.add(status.getMessage()); } if
			 * (row.getCell(3).toString().isEmpty()) { status.setCode(FAIL);
			 * status.setMessage("Gender Is Empty."); errorList.add(status.getMessage()); }
			 * if (row.getCell(4).toString().isEmpty()) { status.setCode(FAIL);
			 * status.setMessage("City Is Empty."); errorList.add(status.getMessage()); } //
			 * start date manadatory check if (row.getCell(5).toString().isEmpty()) { //
			 * System.out.println("---"+row.getCell(7).toString()); status.setCode(FAIL);
			 * status.setMessage("Mobile Number Is Empty.");
			 * errorList.add(status.getMessage()); } if
			 * (row.getCell(6).toString().isEmpty()) { status.setCode(FAIL);
			 * status.setMessage("Email Is Empty."); errorList.add(status.getMessage()); }
			 */
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
		excelLtMasterCallingListRequestDto.setMobileNumber(row.getCell(0).toString());
		excelLtMasterCallingListRequestDto.setFirstName(row.getCell(2).toString());
		//excelLtMasterCallingListRequestDto.setDob(row.getCell(2).toString());
		excelLtMasterCallingListRequestDto.setGender(row.getCell(4).toString());
		excelLtMasterCallingListRequestDto.setCity(row.getCell(6).toString());
		//excelLtMasterCallingListRequestDto.setMobileNumber(row.getCell(5).toString());
		excelLtMasterCallingListRequestDto.setEmail(row.getCell(3).toString());
		excelLtMasterCallingListRequestDto.setAddress(row.getCell(5).toString());
		excelLtMasterCallingListRequestDto.setPincode(row.getCell(7).toString());
			
		if (!row.getCell(1).toString().isEmpty() ) {
			excelLtMasterCallingListRequestDto.setDob(row.getCell(1).toString());
		} else {
			excelLtMasterCallingListRequestDto.setDob(row.getCell(1).toString());
		}
		
		return excelLtMasterCallingListRequestDto;

	}
	
@Override
public Status getMyQueueList(RequestDto requestDto) throws ServiceException, IOException{
	Status status =new Status();
	List<ResponseDto> responseDto= ltAolCallListMasterDao.getMyQueueList(requestDto);
	
	Long totalCount = ltAolCallListMasterDao.getMyQueueListCount(requestDto); 
	if(responseDto!= null) {
		status.setCode(RECORD_FOUND);
		status.setMessage("Record Found Successfully.");
		status.setData(responseDto);
		status.setRecordCount((long) responseDto.size());
		status.setTotalCount(totalCount);
		return status;
	}
	else {	
status.setCode(RECORD_NOT_FOUND);
status.setMessage("Record Not Found.");
status.setRecordCount((long)0);
status.setData(null);
	}
return status;
}



@Override
public Status getAllCourses(RequestDto requestDto) throws ServiceException, IOException{
	Status status =new Status();
	List<LtAolProductMaster> responseDto= ltAolCallListMasterDao.getAllCourses(requestDto);
	Long totalCount = ltAolCallListMasterDao.getAllCoursesTotalCount(requestDto);
	if(responseDto!= null) {
		status.setCode(RECORD_FOUND);
		status.setMessage("Record Found Successfully.");
		status.setData(responseDto);
		status.setRecordCount((long)responseDto.size());
		status.setTotalCount(totalCount);
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
public Status saveCourseDetails (LtAolUserProducts ltAolUserProducts)throws ServiceException, IOException{
	Status status = new Status();
	if(ltAolUserProducts.getUserCourseId() == null) {
	ltAolUserProducts.setCreatedBy(ltAolUserProducts.getUserId());
	ltAolUserProducts.setCreationDate(UtilsMaster.getCurrentDateTime());
	ltAolUserProducts.setLastUpdatedBy(ltAolUserProducts.getUserId());
	ltAolUserProducts.setLastUpdateLogin(ltAolUserProducts.getUserId());
	ltAolUserProducts.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
	
	LtAolUserProducts ltAolUserProductsUpdated = ltAolCallListMasterDao.saveCourseDetails(ltAolUserProducts);
	if (ltAolUserProductsUpdated.getUserCourseId() != null) {
		status.setCode(INSERT_SUCCESSFULLY);
		status.setMessage("Course Details Added Successfully.");
		status.setData(ltAolUserProductsUpdated);

	} else {
		status.setCode(INSERT_FAIL);
		status.setMessage("Unable To Add Details.");
	}
	}else {
		LtAolUserProducts ltAolUserProduct = ltAolCallListMasterDao.getCourseListAgainstId(ltAolUserProducts.getUserCourseId());
		
		ltAolUserProducts.setCreatedBy(ltAolUserProduct.getCreatedBy());
		ltAolUserProducts.setCreationDate(ltAolUserProduct.getCreationDate());
		ltAolUserProducts.setLastUpdatedBy(ltAolUserProducts.getUserId());
		ltAolUserProducts.setLastUpdateLogin(ltAolUserProducts.getUserId());
		ltAolUserProducts.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
		LtAolUserProducts ltAolUserProductsUpdated = ltAolCallListMasterDao.saveCourseDetails(ltAolUserProducts);
		if (ltAolUserProductsUpdated.getUserCourseId() != null) {
			status.setCode(UPDATE_SUCCESSFULLY);
			status.setMessage("Course Details Update Successfully.");
			status.setData(ltAolUserProductsUpdated);

		} else {
			status.setCode(UPDATE_FAIL);
			status.setMessage("Unable To Update Details.");
		}
	}
	return status;
}

@Override
public Status saveNote (LtAolCallNotes ltAolCallNotes)throws ServiceException, IOException{
	Status status = new Status();
	if(ltAolCallNotes.getCallNoteId() == null) {
		ltAolCallNotes.setCreatedBy(ltAolCallNotes.getUserId());
		ltAolCallNotes.setCreationDate(UtilsMaster.getCurrentDateTime());
		ltAolCallNotes.setLastUpdatedBy(ltAolCallNotes.getUserId());
		ltAolCallNotes.setLastUpdateLogin(ltAolCallNotes.getUserId());
		ltAolCallNotes.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
	
		LtAolCallNotes ltAolCallNotesUpdated = ltAolCallListMasterDao.saveNote(ltAolCallNotes);
	if (ltAolCallNotesUpdated.getCallNoteId() != null) {
		status.setCode(INSERT_SUCCESSFULLY);
		status.setMessage("Note Added Successfully.");
		status.setData(ltAolCallNotesUpdated);

	} else {
		status.setCode(INSERT_FAIL);
		status.setMessage("Unable To Add Note.");
	}
	}else {
		LtAolCallNotes ltAolCallNote = ltAolCallListMasterDao.getNoteAgainstId(ltAolCallNotes.getCallNoteId());
		
		ltAolCallNotes.setCreatedBy(ltAolCallNote.getCreatedBy());
		ltAolCallNotes.setCreationDate(ltAolCallNote.getCreationDate());
		ltAolCallNotes.setLastUpdatedBy(ltAolCallNotes.getUserId());
		ltAolCallNotes.setLastUpdateLogin(ltAolCallNotes.getUserId());
		ltAolCallNotes.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
		LtAolCallNotes ltAolCallNotesUpdated = ltAolCallListMasterDao.saveNote(ltAolCallNotes);
		if (ltAolCallNotes.getCallNoteId() != null) {
			status.setCode(UPDATE_SUCCESSFULLY);
			status.setMessage("Course Details Update Successfully.");
			status.setData(ltAolCallNotesUpdated);

		} else {
			status.setCode(UPDATE_FAIL);
			status.setMessage("Unable To Update Details.");
		}
	}
	return status;
}

/*
 * private Status checkListAgainstMobileNumber(LtAolCallListMaster
 * ltAolCallListMaster) throws JSONException, IOException { LtAolCallListMaster
 * updatedList = null; List<String> errorList = new ArrayList<String>(); Status
 * status = new Status(); try { // get Products against ProductCode
 * LtAolCallListMaster ltAolCallList =
 * ltAolCallListMasterDao.getAolCallListByMobileNumber(ltAolCallListMaster.
 * getMobileNumber()); if (ltAolCallList != null) { if
 * (ltAolCallList.getStatus().equals(ACTIVE)) {
 * 
 * status.setCode(RECORD_FOUND); status.setData(updatedList); } else {
 * status.setCode(FAIL); status.setMessage("Mobile Number Is Inactive."); }
 * 
 * } else {
 * 
 * status.setCode(FAIL); status.setMessage("Mobile Number Not Found.");
 * 
 * } } catch (ServiceException e) { status.setCode(FAIL);
 * status.setMessage(e.getMessage()); e.printStackTrace(); } if
 * (status.getCode() == FAIL) { errorList.add(status.getMessage());
 * status.setData(errorList); } return status; }
 */



@Override
public Status getAllStatus() throws ServiceException, IOException{
	Status status =new Status();
	List<LtAolCallListStatus> responseDto= ltAolCallListMasterDao.getAllStatus();
	Long totalCount = ltAolCallListMasterDao.getAllStatusTotalCount();
	if(responseDto!= null) {
		status.setCode(RECORD_FOUND);
		status.setMessage("Record Found Successfully.");
		status.setData(responseDto);
		status.setRecordCount((long)responseDto.size());
		status.setTotalCount(totalCount);
		return status;
	}
	else {	
status.setCode(RECORD_NOT_FOUND);
status.setMessage("Record Not Found.");
status.setData(null);
	}
return status;
}

private Status checkDuplicate(Long mobileNumber) throws ServiceException, JSONException, IOException {
	Status status = new Status();
	System.out.println("mobileNumber in checkduplicate"+mobileNumber);
	LtAolCallListMaster ltAolCallListMasters = ltAolCallListMasterDao.getAolCallListByMobileNumber(mobileNumber);
	System.out.println("ltAolCallListMasters"+ltAolCallListMasters);
	if (ltAolCallListMasters != null) {
		if (ltAolCallListMasters.getMobileNumber().equals(mobileNumber)) {
			status.setCode(FAIL);
			status.setMessage("Mobile Number Already Exists.");
			return status;
		}		
	}
	status.setCode(SUCCESS);
	return status;
}

}
