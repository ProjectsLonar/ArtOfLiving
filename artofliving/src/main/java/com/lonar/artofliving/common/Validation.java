package com.lonar.artofliving.common;

public class Validation {

	public static boolean validatePhoneNumber(String phoneNo) {
		if (phoneNo.matches("^[6-9]\\d{9}$"))
		return true;
		return false;
	}
	
	public static boolean validateOTP(Long otp) {
		String otps = String.valueOf(otp);
		if (otps.matches("[0-9]{4}")) return true;
		return false;
	}
}
