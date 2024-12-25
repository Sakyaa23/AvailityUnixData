package AvailityUnixData_003;

import java.io.*;
import java.util.*;
import com.jcraft.jsch.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UnixFileListing_003 {
	public static ReadProperties props = new ReadProperties();
	public static Properties prop;

	public static void main(String[] args) throws JSchException, SftpException, SecurityException, IOException {
		//reading properties from Availity003Config file
		prop = props.readPropertiesFile1();
		//String Username = prop.getProperty("Username");
		String Username = "srcfnauto";
		//byte[] decodedpassword = Base64.getDecoder().decode(prop.getProperty("Password"));
		//String password = new String(decodedpassword);
		String password ="Srcfn#2Autm4356";
		String host = prop.getProperty("host");
		String Logpath=prop.getProperty("Logpath");
		String DestinationFolder=prop.getProperty("destinationMainDir");		
		String unixZIPFilesCommand = prop.getProperty("unixZIPFilesCommand");
		String unixzipFilesCommand = prop.getProperty("unixzipFilesCommand");
		String unixConfirmFilesCommand3 = prop.getProperty("unixConfirmFilesCommand3");
		String FEVVADONEpath = prop.getProperty("FEVVADONEpath");
		String FEVNEDONEpath = prop.getProperty("FEVNEDONEpath");
		String FEVNYDONEpath = prop.getProperty("FEVNYDONEpath");
		String FEVWESTpath = prop.getProperty("FEVWESTpath");
		String FEVCENTRALpath = prop.getProperty("FEVCENTRALpath");
		String FEVSCWCADONEpath = prop.getProperty("FEVSCWCADONEpath");
		String FEVSCWCONVDONEpath = prop.getProperty("FEVSCWCONVDONEpath");
		String FEVSCDONEpath = prop.getProperty("FEVSCDONEpath");
		String PSDFEPpath = prop.getProperty("PSDFEPpath");
		String unixFilesPath=prop.getProperty("unixFilesPath");
		
		LocalDateTime currentdate = LocalDateTime.now();
		DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("MMddyyyy");
		String date = dateformat.format(currentdate);
		
		// creating files in destination folder
		File FEVVADONE = new File(DestinationFolder + "FEVVADONE\\FEVVADONE_" + date + ".txt");
		File FEVNEDONE = new File(DestinationFolder + "FEVNEDONE\\FEVNEDONE_" + date + ".txt");
		File FEVNYDONE = new File(DestinationFolder + "FEVNYDONE\\FEVNYDONE_" + date + ".txt");
		File FEVWESTDONE = new File(DestinationFolder + "FEVWESTDONE\\FEVWESTDONE_" + date + ".txt");
		File FEVCENTRALDONE = new File(DestinationFolder + "FEVCENTRALDONE\\FEVCENTRALDONE_" + date + ".txt");
		File FEVSCWCADONE = new File(DestinationFolder + "FEVSCWCADONE\\FEVSCWCADONE_" + date + ".txt");
		File FEVSCWCONVDONE = new File(DestinationFolder + "FEVSCWCONVDONE\\FEVSCWCONVDONE_" + date + ".txt");
		File FEVSCDONE = new File(DestinationFolder + "FEVSCDONE\\FEVSCDONE_" + date + ".txt");
		File PSDProcessedFEP = new File(DestinationFolder + "PSDProcessed\\PSDProcessed_FEP_" + date + ".txt");
		
		File file = new File(Logpath);
		file.delete();
		FileWriter fileWriter=null;
		fileWriter = new FileWriter(file, true);
		
		JSch jsch = new JSch();
		String strtmp=null;
		Session session = null;
		Channel channel = null;
		Channel sftpChannel = null;
		ChannelSftp sftp = null;
		
		
		//framing command to be run on unix
		String command ="cd " + FEVVADONEpath + " && " + unixzipFilesCommand + " > " + unixFilesPath + "FEVVADONE_" + date + ".txt"
		+" && cd " + FEVNEDONEpath + " && " + unixzipFilesCommand + " > " + unixFilesPath + "FEVNEDONE_" + date + ".txt"
		+" && cd " + FEVNYDONEpath + " && " + unixzipFilesCommand +  " > " + unixFilesPath + "FEVNYDONE_" + date + ".txt"
		+" && cd " + FEVWESTpath + " && " + unixzipFilesCommand +  " > " + unixFilesPath + "FEVWESTDONE_" + date + ".txt"
		+" && cd " + FEVCENTRALpath + " && " + unixzipFilesCommand +  " > " + unixFilesPath + "FEVCENTRALDONE_" + date + ".txt"
		+" && cd " + FEVSCWCADONEpath + " && " + unixZIPFilesCommand +  " > " + unixFilesPath + "FEVSCWCADONE_" + date + ".txt"
		+" && cd " + FEVSCWCONVDONEpath + " && " + unixZIPFilesCommand +  " > " + unixFilesPath + "FEVSCWCONVDONE_" + date +".txt"
		+" && cd " + FEVSCDONEpath + " && " + unixZIPFilesCommand +  " > " + unixFilesPath + "FEVSCDONE_" + date + ".txt"
		+" && cd " + PSDFEPpath + " && " + unixConfirmFilesCommand3 +  " > " + unixFilesPath + "PSDProcessed_FEP_" + date + ".txt";

		try {
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session = jsch.getSession(Username, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected to " + host);

			channel = session.openChannel("exec");
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			((ChannelExec) channel).setCommand(command);
			Thread.sleep(1000);
			channel.connect();

			byte[] tmp = new byte[819200];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 819200);
					if (i < 0)
						break;
					strtmp = new String(tmp, 0, i);
					String strArr[] = strtmp.split("\n");
					for (int ind = 0; ind < strArr.length; ind++) {
						System.out.println(strArr[ind]);
					}
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
					System.out.println(ee);
					fileWriter.write(new Date() + "\t Program did not complete successfully. Please check \n");
				}
			}
			fileWriter.write(new Date() + "\t Finished collecting FEVDONE data in va10puvfns003 \n");
			channel.disconnect();
			System.out.println("EXEC Channel disconnected!!");

			// SFTP get to download files to local path
			sftpChannel = session.openChannel("sftp");
			sftpChannel.connect();
			sftp = (ChannelSftp) sftpChannel;
			sftp.get(unixFilesPath + "FEVVADONE_" + date + ".txt", FEVVADONE.toString());
			fileWriter.write(new Date() + "\t Finished downloading FEVVADONE.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "FEVNEDONE_" + date + ".txt", FEVNEDONE.toString());
			fileWriter.write(new Date() + "\t Finished downloading FEVNEDONE.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "FEVNYDONE_" + date + ".txt", FEVNYDONE.toString());
			fileWriter.write(new Date() + "\t Finished downloading FEVNYDONE.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "FEVWESTDONE_" + date + ".txt", FEVWESTDONE.toString());
			fileWriter.write(new Date() + "\t Finished downloading FEVWESTDONE.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "FEVCENTRALDONE_" + date + ".txt", FEVCENTRALDONE.toString());
			fileWriter.write(new Date() + "\t Finished downloading FEVCENTRALDONE.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "FEVSCWCADONE_" + date + ".txt", FEVSCWCADONE.toString());
			fileWriter.write(new Date() + "\t Finished downloading FEVSCWCADONE.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "FEVSCWCONVDONE_" + date + ".txt", FEVSCWCONVDONE.toString());
			fileWriter.write(new Date() + "\t Finished downloading FEVSCWCONVDONE.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "FEVSCDONE_" + date + ".txt", FEVSCDONE.toString());
			fileWriter.write(new Date() + "\t Finished downloading FEVSCDONE.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "PSDProcessed_FEP_" + date + ".txt", PSDProcessedFEP.toString());
			fileWriter.write(new Date() + "\t Finished downloading PSDProcessedFEP.txt to VA10P51589 \n");

			// To verify there are no 0kb files
			if (FEVVADONE.length() > 0 && FEVNEDONE.length() > 0 && FEVNYDONE.length() > 0 && FEVWESTDONE.length() > 0
					&& FEVCENTRALDONE.length() > 0 && FEVSCWCADONE.length() > 0 && FEVSCWCONVDONE.length() > 0
					&& FEVSCDONE.length() > 0)
				fileWriter.write(new Date() + "\t Program completed successfully!!\n");
			else
				fileWriter.write(new Date()	+ "\t Program did not complete successfully. One of the files size is 0kb. Please Validate. \n");

			// cleaning up files form unix
			sftp.rm(unixFilesPath + "FEVVADONE_" + date + ".txt");
			sftp.rm(unixFilesPath + "FEVNEDONE_" + date + ".txt");
			sftp.rm(unixFilesPath + "FEVNYDONE_" + date + ".txt");
			sftp.rm(unixFilesPath + "FEVWESTDONE_" + date + ".txt");
			sftp.rm(unixFilesPath + "FEVCENTRALDONE_" + date + ".txt");
			sftp.rm(unixFilesPath + "FEVSCWCADONE_" + date + ".txt");
			sftp.rm(unixFilesPath + "FEVSCWCONVDONE_" + date + ".txt");
			sftp.rm(unixFilesPath + "FEVSCDONE_" + date + ".txt");
			sftp.rm(unixFilesPath + "PSDProcessed_FEP_" + date + ".txt");
			System.out.println("All temp files were removed from unix server va10puvfns003");
			sftp.exit();
			System.out.println("SFTP exited!!");
			sftpChannel.disconnect();
			System.out.println("SFTPChannel disconnected!!");
			session.disconnect();
			System.out.println("Session Disconnected for VA10PUVFNS003!!");
			fileWriter.close();

		} // end of try
		catch (Exception e) {
			System.out.println(e);
			fileWriter.write(new Date() + "\t Program did not complete successfully. Please check \n");
			fileWriter.close();
		}
	}
}
