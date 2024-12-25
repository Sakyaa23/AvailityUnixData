package AvailityUnixData_003;

import java.io.*;
import java.util.Properties;

public class ReadProperties {
	public Properties readPropertiesFile() throws IOException {
		
		String filepath="C:\\Users\\AG78632\\OneDrive - Anthem\\Desktop\\Sakya\\FileNet_Automations\\Source Code_Anuhya\\AvailityUnixData\\src\\Availity018Config.properties";
		
		Properties prop=null;
		FileInputStream fis = new FileInputStream(filepath);
		try {
			prop = new Properties();
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}

public Properties readPropertiesFile1() throws IOException {
		
		
		String filepath1="C:\\Users\\AG78632\\OneDrive - Anthem\\Desktop\\Sakya\\FileNet_Automations\\Source Code_Anuhya\\AvailityUnixData\\src\\Availity003Config.properties";
	//String filepath1="D:\\AvailityDataListingAutomation\\UnixDataListing\\Availity003Config.properties";
		Properties prop1=null;
		FileInputStream fis1 = new FileInputStream(filepath1);
		try {
			prop1 = new Properties();
			prop1.load(fis1);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis1.close();
		}
		return prop1;
	}
}
