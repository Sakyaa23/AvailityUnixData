package AvailityUnixData_003;

import java.io.*;
import java.time.*;
import java.util.*;
import com.jcraft.jsch.*;
import java.time.format.DateTimeFormatter;

public class UnixFileListing_018 {
	public static ReadProperties props = new ReadProperties();
	public static Properties prop;

	public static void main(String[] args) throws JSchException, SftpException, SecurityException, IOException {

		prop = props.readPropertiesFile();
		//String Username = prop.getProperty("Username");
		String Username = "srcfnauto";
		//byte[] decodedpassword = Base64.getDecoder().decode(prop.getProperty("Password"));
		//String password = new String(decodedpassword);
		String password = "Srcfn#2Autm4356";
		String host = prop.getProperty("host");
		String Logpath = prop.getProperty("Logpath");
		String DestinationFolder = prop.getProperty("destinationMainDir");
		String unixzipFilesCommand = prop.getProperty("unixzipFilesCommand");
		String unixDirectoriesCommand = prop.getProperty("unixDirectoriesCommand");
		String unixConfirmFilesCommand = prop.getProperty("unixConfirmFilesCommand");
		String DigitalSentpath = prop.getProperty("DigitalSentpath");
		String DigitalProcessedpath1 = prop.getProperty("DigitalProcessedpath1");
		String DigitalProcessedpath2 = prop.getProperty("DigitalProcessedpath2");
		String DigitalProcessedCentralPath = prop.getProperty("DigitalProcessedCentralPath");
		String EPADONEpath = prop.getProperty("EPADONEpath");
		String PSDProcessedpath = prop.getProperty("PSDProcessedpath");
		String unixFilesPath=prop.getProperty("unixFilesPath");

		LocalDateTime currentdate = LocalDateTime.now();
		DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("MMddyyyy");
		String date = dateformat.format(currentdate);

		File DigitalSent = new File(DestinationFolder+"DigitalSent\\DigitalSent_" + date + ".txt");
		File DigitalProcessed = new File(DestinationFolder + "DigitalProcessed\\DigitalProcessed_" + date + ".txt");
		File DigitalProcessedCentral = new File(DestinationFolder + "DigitalProcessed\\DigitalProcessed_Central_" + date + ".txt");
		File EPADONE = new File(DestinationFolder + "EPADONE\\EPADONE_" + date + ".txt");
		File PSDProcessed = new File(DestinationFolder + "PSDProcessed\\PSDProcessed_" + date + ".txt");

		File file = new File(Logpath);
		file.delete();
		FileWriter fileWriter = null;
		fileWriter = new FileWriter(file, true);

		JSch jsch = new JSch();
		String strtmp = null;
		Session session = null;
		Channel channel = null;
		Channel sftpChannel = null;
		ChannelSftp sftp = null;

		String command = "cd " + DigitalSentpath + " && " + unixzipFilesCommand+ " > " + unixFilesPath + "DigitalSent_" + date + ".txt"
		+ " && cd " + DigitalProcessedpath1 + " && " + unixDirectoriesCommand + " > " + unixFilesPath+ "DigitalProcessed__" + date + ".txt" 
		+ " && cd " + DigitalProcessedpath2 + " && " + unixDirectoriesCommand + " > " + unixFilesPath+ "DigitalProcessed_TEMP_" + date + ".txt"
		+ " && cd " + DigitalSentpath + " && cat DigitalProcessed__" + date + ".txt DigitalProcessed_TEMP_" + date + ".txt >> DigitalProcessed_" + date + ".txt"
		+ " && cd " + DigitalProcessedCentralPath + " && " + unixDirectoriesCommand + " > " + unixFilesPath+ "DigitalProcessed_Central_" + date + ".txt"
		+ " && cd " + EPADONEpath + " && " + unixConfirmFilesCommand + " > " + unixFilesPath+ "EPADONE_" + date + ".txt"
		+ " && cd " + PSDProcessedpath + " && " + unixConfirmFilesCommand + " > " + unixFilesPath + "PSDProcessed_" + date + ".txt";

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
			fileWriter.write(new Date() + "\t Finished collecting  Digital(Sent & Processed), EPA, PSD data in va10puvwbs018 \n");
			channel.disconnect();
			System.out.println("EXEC Channel disconnected!!");

			// SFTP get to download files to local path
			sftpChannel = session.openChannel("sftp");
			sftpChannel.connect();
			sftp = (ChannelSftp) sftpChannel;
			sftp.get(unixFilesPath + "DigitalSent_" + date + ".txt", DigitalSent.toString());
			fileWriter.write(new Date() + "\t Finished downloading DigitalSent.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "DigitalProcessed_" + date + ".txt", DigitalProcessed.toString());
			fileWriter.write(new Date() + "\t Finished downloading DigitalProcessed.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "DigitalProcessed_Central_" + date + ".txt", DigitalProcessedCentral.toString());
			fileWriter.write(new Date() + "\t Finished downloading DigitalProcessedCentral.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "EPADONE_" + date + ".txt", EPADONE.toString());
			fileWriter.write(new Date() + "\t Finished downloading EPADONE.txt to VA10P51589 \n");
			sftp.get(unixFilesPath + "PSDProcessed_" + date + ".txt", PSDProcessed.toString());
			fileWriter.write(new Date() + "\t Finished downloading PSDProcessed.txt to VA10P51589 \n");

			// To verify there are no 0kb files
			if (DigitalSent.length() > 0 && DigitalProcessed.length() > 0 && EPADONE.length() > 0 && PSDProcessed.length() > 0)
				fileWriter.write(new Date() + "\t Program completed successfully!!\n");
			else
				fileWriter.write(new Date() + "\t Program did not complete successfully. One of the files size is 0kb. Please Validate. \n");

			// cleaning up files form unix
			sftp.rm(unixFilesPath + "DigitalSent_" + date + ".txt");
			sftp.rm(unixFilesPath + "DigitalProcessed__" + date + ".txt");
			sftp.rm(unixFilesPath + "DigitalProcessed_TEMP_" + date + ".txt");
			sftp.rm(unixFilesPath + "DigitalProcessed_" + date + ".txt");
			sftp.rm(unixFilesPath + "DigitalProcessed_Central_" + date + ".txt");
			sftp.rm(unixFilesPath + "EPADONE_" + date + ".txt");
			sftp.rm(unixFilesPath + "PSDProcessed_" + date + ".txt");
			System.out.println("All temp files were removed from unix server va10puvwbs018.");
			sftp.exit();
			sftpChannel.disconnect();
			System.out.println("SFTPChannel disconnected!!");
			session.disconnect();
			System.out.println("Session Disconnected for VA10PUVWBS018!!");
			fileWriter.close();

			} // end of try
			catch (Exception e) {
				System.out.println(e);
				fileWriter.write(new Date() + "\t Program did not complete successfully. Please check. \n");
				fileWriter.close();
			}
	}
}
