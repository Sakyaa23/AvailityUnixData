package AvailityUnixData_003;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;


public class FileNetDataCheck {
	public static ReadProperties props = new ReadProperties();
	public static Properties prop;
	public static void main(String[] args) throws IOException, MessagingException {
		// TODO Auto-generated method stub
		
		prop = props.readPropertiesFile();
		EmailSender emailSender = new EmailSender();
		String path= prop.getProperty("pathToBeVerified");
		String subject="";
		String todayFile= "No File has been generated  for todays' date. Please check with FileNet team";
		int count=0;
		File files = new File(path);
		String str[] = files.list();
		for(String s : str)
		{
			File file = new File(files,s);
			long MINUTES = 24 * 60 * 60 * 1000;
			long MinutesOld= System.currentTimeMillis()-MINUTES;
			long searchTimeStamp = file.lastModified();
			if(file.isFile()&& searchTimeStamp > MinutesOld)
			{
				count++;
				todayFile=file.toString();
			}
		}
		if(count==0)
		{
			subject = " Action Required : No File Generated for Availity FileNetData";
		}
		else
		{
			subject= "File Generated for Availity FileNetData";
		}
		emailSender.Email(subject, todayFile);
	}

}
