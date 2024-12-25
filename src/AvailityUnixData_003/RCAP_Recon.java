package AvailityUnixData_003;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.jcraft.jsch.*;

public class RCAP_Recon {

	public String getCredentials() {
		Properties property = new Properties();
		String propertyFileName = "Credentials.properties";
		InputStream inputStream;
		inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);
		if (inputStream != null) {
			try {
				property.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new FileNotFoundException("Property file '" + propertyFileName + "' not found in the classpath");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		String Username = property.getProperty("Username");
		String password = property.getProperty("Password");
		return Username + " " + password;
	}

	public static void main(String[] args) throws JSchException, SftpException, SecurityException, IOException {
		
		RCAP_Recon se = new RCAP_Recon();
		String credentials = se.getCredentials();
		String credentialsplit[] = credentials.split(" ");
		String Username = credentialsplit[0];
		String password = credentialsplit[1];
		String hosts[] = { "va10puvwbs018" };

		JSch jsch = new JSch();
		String strtmp = null;
		Session session = null;
		Channel channel = null;
		Channel sftpChannel = null;
		ChannelSftp sftp = null;

		LocalDateTime currentdate = LocalDateTime.now();
		DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("MMddyyyy");
		DateTimeFormatter dateformat2 = DateTimeFormatter.ofPattern("MMM d");
		String date = dateformat.format(currentdate);
		String date2 = dateformat2.format(currentdate.minusDays(1));
		
		//unixcommands
		String unixZips = " && find . -name '*.zip' -mtime -2 -ls|awk '{print $8,$9,$10 \" | \" $11}' | sed 's/\\.\\///g' | grep '" + date2 + "' > /fnbackup/cebi/DIGITAL/ARCHIVE/";
		String unixEobs = " && find . -path ./SmartCapture -prune -o -name '*.eob' -mtime 1 -ls|awk '{print $8,$9,$10 \" | \" $11}' | sed 's/\\.\\///g' > /fnbackup/cebi/DIGITAL/ARCHIVE/";
		String unixLck = " && find . -path ./SmartCapture -prune -o -name '*.lck' -mtime 1 -ls|awk '{print $8,$9,$10 \" | \" $11}' | sed 's/\\.\\///g' > /fnbackup/cebi/DIGITAL/ARCHIVE/";
		String unixRpt = " && find . -path ./SmartCapture -prune -o -name '*.rpt' -mtime -2 -ls|awk '{print $8,$9,$10 \" | \" $11}' | sed 's/\\.\\///g' | grep '" + date2 + "' > /fnbackup/cebi/DIGITAL/ARCHIVE/";
		String unixPass = " && find . -path ./SmartCapture -prune -o -name '*.pass' -mtime -2 -ls|awk '{print $8,$9,$10 \" | \" $11}' | sed 's/\\.\\///g' | grep '" + date2 + "' > /fnbackup/cebi/DIGITAL/ARCHIVE/";
		String unixConfirm = " && find . -path ./SmartCapture -prune -o -name '*.confirm' -mtime -2 -ls|awk '{print $8,$9,$10 \" | \" $11}' | sed 's/\\.\\///g' | grep '" + date2 + "' > /fnbackup/cebi/DIGITAL/ARCHIVE/";
		String unixTxt = " && find . -name '*.txt' -mtime -2 -ls|awk '{print $8,$9,$10 \" | \" $11}' | sed 's/\\.\\///g' | grep '" + date2 + "' > /fnbackup/cebi/DIGITAL/ARCHIVE/";
		
		//created files on windows directory
		File RCINcoming = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\RCINcoming_" + date + ".txt");
		File WESTEOB = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\WESTEOB_" + date + ".txt");
		File EASTEOB = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\EASTEOB_" + date + ".txt");
		File CENTRALEOB = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\CENTRALEOB_" + date + ".txt");
		File FEP_TIMEEOB = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\FEP_TIMEEOB_" + date + ".txt");
		File WESTRPT = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\WESTRPT_" + date + ".txt");
		File WESTPass = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\WESTPass_" + date + ".txt");
		File WESTConfirm = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\WESTConfirm_" + date + ".txt");
		File WESTLck = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\WESTLck_" + date + ".txt");
		File EASTRPT = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\EASTRPT_" + date + ".txt");
		File EASTPass = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\EASTPass_" + date + ".txt");
		File EASTConfirm = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\EASTConfirm_" + date + ".txt");
		File EASTLck = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\EASTLck_" + date + ".txt");
		File CENTRALRPT = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\CENTRALRPT_" + date + ".txt");
		File CENTRALPass = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\CENTRALPass_" + date + ".txt");
		File CENTRALConfirm = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\CENTRALConfirm_" + date + ".txt");
		File CENTRALLck = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\CENTRALLck_" + date + ".txt");
		File FEP_TIMERPT = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\FEP_TIMERPT_" + date + ".txt");
		File FEP_TIMEPass = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\FEP_TIMEPass_" + date + ".txt");
		File FEP_TIMEConfirm = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\FEP_TIMEConfirm_" + date + ".txt");
		File FEP_TIMELck = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\FEP_TIMELck_" + date + ".txt");
		File AUMIPAYLOAD = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\AUMIPAYLOAD_" + date + ".txt");
		File AUMIFINAL = new File("\\\\Va10p51589\\mocha\\DATA\\RCAP_WORKING_FILES\\AUMIFINAL_" + date + ".txt");

		//unixpaths declaration
		String originpaths[] = new String[] { "/fnbackup/cebi/parent/WestClaims_time/SmartCapture/zips/archive",
				"/fnbackup/cebi/parent/WestClaims_time",
				"/fnbackup/cebi/parent/EastClaims_time",
				"/fnbackup/cebi/parent/CentralClaims_time",
				"/fnbackup/cebi/parent/FEP_time",
				"/fnbackup/cebi/parent/WestClaims_time/SmartCapture/aumipath/ARCHIVE",
				"/fnbackup/cebi/parent/WestClaims_time/SmartCapture/final_AUMI" };

		//command declaration
		String command = "cd " + originpaths[0] + unixZips + "RCINcoming_" + date + ".txt"
		+ " && cd " + originpaths[1] + unixRpt + "WESTRPT_" + date + ".txt"
		+ " && cd " + originpaths[1] + unixPass + "WESTPass_" + date + ".txt"
		+ " && cd " + originpaths[1] + unixConfirm + "WESTConfirm_" + date + ".txt"
		+ " && cd " + originpaths[1] + unixEobs + "WESTEOB_" + date + ".txt"
		+ " && cd " + originpaths[1] + unixLck + "WESTLck_" + date + ".txt"
		+ " && cd " + originpaths[2] + unixRpt + "EASTRPT_" + date + ".txt"
		+ " && cd " + originpaths[2] + unixPass + "EASTPass_" + date + ".txt"
		+ " && cd " + originpaths[2] + unixConfirm + "EASTConfirm_" + date + ".txt"
		+ " && cd " + originpaths[2] + unixEobs + "EASTEOB_" + date + ".txt"
		+ " && cd " + originpaths[2] + unixLck + "EASTLck_" + date + ".txt"
		+ " && cd " + originpaths[3] + unixRpt + "CENTRALRPT_" + date + ".txt"
		+ " && cd " + originpaths[3] + unixPass + "CENTRALPass_" + date + ".txt"
		+ " && cd " + originpaths[3] + unixConfirm + "CENTRALConfirm_" + date + ".txt"
		+ " && cd " + originpaths[3] + unixEobs + "CENTRALEOB_" + date + ".txt"
		+ " && cd " + originpaths[3] + unixLck + "CENTRALLck_" + date + ".txt"
		+ " && cd " + originpaths[4] + unixRpt + "FEP_TIMERPT_" + date + ".txt"
		+ " && cd " + originpaths[4] + unixPass + "FEP_TIMEPass_" + date + ".txt"
		+ " && cd " + originpaths[4] + unixConfirm + "FEP_TIMEConfirm_" + date + ".txt"
		+ " && cd " + originpaths[4] + unixEobs + "FEP_TIMEEOB_" + date + ".txt"
		+ " && cd " + originpaths[4] + unixLck + "FEP_TIMELck_" + date + ".txt"
		+ " && cd " + originpaths[5] + unixTxt + "AUMIPAYLOAD_" + date + ".txt"
		+ " && cd " + originpaths[6] + unixTxt + "AUMIFINAL_" + date + ".txt";

		
		for (int index = 0; index < 1; index++) {
			try {
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
				session = jsch.getSession(Username, hosts[index], 22);
				session.setPassword(password);
				session.setConfig(config);
				session.connect();
				System.out.println("Connected to " + hosts[index]);

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
					}
				}
				channel.disconnect();
				System.out.println("EXEC Channel disconnected!!");

				// SFTP get to download files to local path
				sftpChannel = session.openChannel("sftp");
				sftpChannel.connect();
				sftp = (ChannelSftp) sftpChannel;
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/RCINcoming_" + date + ".txt", RCINcoming.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/WESTEOB_" + date + ".txt", WESTEOB.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/EASTEOB_" + date + ".txt", EASTEOB.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/CENTRALEOB_" + date + ".txt", CENTRALEOB.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/FEP_TIMEEOB_" + date + ".txt", FEP_TIMEEOB.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/WESTLck_" + date + ".txt", WESTLck.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/EASTLck_" + date + ".txt", EASTLck.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/CENTRALLck_" + date + ".txt", CENTRALLck.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/FEP_TIMELck_" + date + ".txt", FEP_TIMELck.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/WESTRPT_" + date + ".txt", WESTRPT.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/WESTPass_" + date + ".txt", WESTPass.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/WESTConfirm_" + date + ".txt", WESTConfirm.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/EASTRPT_" + date + ".txt", EASTRPT.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/EASTPass_" + date + ".txt", EASTPass.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/EASTConfirm_" + date + ".txt", EASTConfirm.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/CENTRALRPT_" + date + ".txt", CENTRALRPT.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/CENTRALPass_" + date + ".txt", CENTRALPass.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/CENTRALConfirm_" + date + ".txt", CENTRALConfirm.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/FEP_TIMERPT_" + date + ".txt", FEP_TIMERPT.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/FEP_TIMEPass_" + date + ".txt", FEP_TIMEPass.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/FEP_TIMEConfirm_" + date + ".txt", FEP_TIMEConfirm.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/AUMIPAYLOAD_" + date + ".txt", AUMIPAYLOAD.toString());
				sftp.get("/fnbackup/cebi/DIGITAL/ARCHIVE/AUMIFINAL_" + date + ".txt", AUMIFINAL.toString());
					
				
				// To verify there are no 0kb files
				
				// cleaning up files form unix
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/RCINcoming_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/WESTEOB_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/EASTEOB_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/CENTRALEOB_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/FEP_TIMEEOB_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/WESTRPT_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/WESTPass_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/WESTConfirm_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/WESTLck_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/EASTRPT_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/EASTPass_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/EASTConfirm_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/EASTLck_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/CENTRALRPT_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/CENTRALPass_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/CENTRALConfirm_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/CENTRALLck_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/FEP_TIMERPT_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/FEP_TIMEPass_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/FEP_TIMEConfirm_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/FEP_TIMELck_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/AUMIPAYLOAD_" + date + ".txt");
				sftp.rm("/fnbackup/cebi/DIGITAL/ARCHIVE/AUMIFINAL_" + date + ".txt");
				
				System.out.println("All temp files were removed from unix server va10puvwbs018.");
				sftp.exit();
				sftpChannel.disconnect();
				System.out.println("SFTPChannel disconnected!!");
				session.disconnect();
				System.out.println("Session Disconnected for VA10PUVWBS018!!");

			} // end of try
			catch (Exception e) {
				System.out.println(e);
				}
		}
	}

}
