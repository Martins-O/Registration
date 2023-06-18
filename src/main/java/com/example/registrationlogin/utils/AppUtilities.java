package com.example.registrationlogin.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class AppUtilities {
	
	private static final String USER_VERIFICATION_BASE_URL = "http://localhost:9092/api/v1/registration/confirm";
	private static final String VERIFIED_MAIL_TEMPLATE_FOR_VOTERS_LOCATION = "/home/martins-o/IdeaProjects/registrationLogin/src/main/resources/static/Activate.html";
	public static String getVerifiedMessage() throws FileNotFoundException {
		try (BufferedReader reader = new BufferedReader (new FileReader (
				VERIFIED_MAIL_TEMPLATE_FOR_VOTERS_LOCATION
		))){
			return reader.lines ().collect(Collectors.joining());
		}
		catch (IOException e) {
			throw new FileNotFoundException (e.getMessage ());
		}
	}
	
	public static String getUserVerificationLink(String token){
		return USER_VERIFICATION_BASE_URL+"?token="+token;
	}
}
