package AvailityUnixData_003;

import java.util.Base64;

public class EncryptionAndDecryption {
	public static void main(String[] args) {
	
	String password = "s$#Dva9DRx3jdWeR2DQH"; //enter your password here and encoded string will be printed on console
	byte[] encodedBytes = Base64.getEncoder().encode(password.getBytes());
	System.out.println("encodedBytes " + new String(encodedBytes));
	byte[] decodedBytes = Base64.getDecoder().decode(encodedBytes);
	System.out.println("decodedBytes " + new String(decodedBytes));
	}
}
	
	

