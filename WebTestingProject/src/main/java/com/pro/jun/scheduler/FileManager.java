package com.pro.jun.scheduler;

import java.io.File;
import java.io.FilenameFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// @EnableScheduling
@Controller
@RequestMapping("file")
public class FileManager {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	String dirPath = "";

	int minusDay = 10;

	// @Scheduled(fixedRate = 3000)
	@RequestMapping("del")
	public void deleteFileScheduled() {
		Date nowDate = new Date();
		LOGGER.info("DATE => {}", nowDate.toString());
		// throw new NullPointerException();
		String path = "D://hieForm_dev/";
		File filePath = new File(path);

		File[] fileListDepth01 = filePath.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().startsWith("eform");
			}
		});
		
		String fileName = fileListDepth01[0].getName();
		File[] fileListDepth02 = fileListDepth01[0].listFiles();
		if(fileName.equals("eformAll")) {
			for(int j = 0; j < fileListDepth02.length; j++) {
				File[] fileListDepth03 = fileListDepth02[j].listFiles();
				File file = fileListDepth03[0];
				if(file.exists() && file.isFile()) {
					Date delFileDate = new Date(file.lastModified());
					long diff = nowDate.getTime() - delFileDate.getTime();
					long diffDays = diff / 86400000L;
					LOGGER.info("DIFFDAYS => {}", diffDays);
					if ((diffDays >= minusDay) && (diffDays >= 0L)) {
						int Idx = file.getName().lastIndexOf(".");
						String docId = file.getName().substring(0, Idx);
						// this.deleteDirectory(fileListDepth02[j].getAbsolutePath());
						// this.deleteDirectory(eformTEMP + docId);
						// this.deleteDirectory(eformXMLTEMP + docId);
						LOGGER.info("DOC_ID => {}", docId);
					}
				}
			}
		} else {
			LOGGER.error("DIR NOT EXISTS {}", fileListDepth01[0].getAbsolutePath());
		}
	}

	public String deleteInDirectory(String dirDeletePath, int minusDay) throws Exception {
		String dfp = nvl(dirDeletePath, "");
		String result = "";
		if ((dfp == null) || ("".equals(dfp))) {
			result = "";
		} else {
			File file = new File(filePathBlackList(dirDeletePath));
			if (file.isDirectory()) {
				String[] fileList = file.list();
				for (int i = 0; i < fileList.length; i++) {
					try {
						if (checkName(fileList[i], minusDay)) {
							File f = new File(filePathBlackList(dirDeletePath) + "/" + fileList[i]);
							if (f.isFile()) {
								f.delete();
							} else {
								deleteDirectory(dirDeletePath + "/" + fileList[i]);
							}
						}
					} catch (Exception localException) {}
				}
				result = dirDeletePath;
			} else {
				result = "";
			}
		}
		return result;
	}

	public long diffOfDate(String begin, String end) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		Date beginDate = formatter.parse(begin);
		Date endDate = formatter.parse(end);

		long diff = endDate.getTime() - beginDate.getTime();
		long diffDays = diff / 86400000L;

		return diffDays;
	}

	public boolean checkPattern(String input) {
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	public boolean checkName(String fileName, int referenceDate) throws ParseException {
		if (fileName.length() >= 8) {
			String beginDate = fileName.substring(0, 8);
			if (!checkPattern(beginDate)) {
				return false;
			}
			long time = System.currentTimeMillis();
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMdd");
			String endDate = dayTime.format(new Date(time));

			long diffDays = diffOfDate(beginDate, endDate);
			if (diffDays >= referenceDate) {
				return true;
			}
		}
		return false;
	}

	public String nvl(String str, String defStr) {
		if (str == null) {
			return defStr;
		}
		if ("".equals(str.trim())) {
			return defStr;
		}
		if ("NULL".equals(str.toUpperCase())) {
			return defStr;
		}
		if ("UNKNOWN".equals(str.toUpperCase())) {
			return defStr;
		}
		if ("UNDEFINED".equals(str.toUpperCase())) {
			return defStr;
		}
		return trim(str);
	}

	public String trim(String str) {
		if (str != null) {
			return str.trim();
		}
		return str;
	}

	public String deleteDirectory(String dirDeletePath) {
		if ((dirDeletePath == null) || (dirDeletePath.equals(""))) {
			return "";
		}
		String result = "";
		File file = new File(filePathBlackList(dirDeletePath));
		if (file.isDirectory()) {
			String[] fileList = file.list();
			for (int i = 0; i < fileList.length; i++) {
				File f = new File(filePathBlackList(dirDeletePath) + "/" + fileList[i]);
				if (f.isFile()) {
					f.delete();
				} else {
					deleteDirectory(dirDeletePath + "/" + fileList[i]);
				}
			}
			result = deletePath(dirDeletePath);
		} else {
			result = "";
		}
		return result;
	}

	public String deletePath(String filePath) {
		File file = new File(filePathBlackList(filePath));
		String result = "";
		try {
			if (file.exists()) {
				result = file.getAbsolutePath();
				if (!file.delete()) {
					result = "";
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public String filePathBlackList(String value) {
		String returnValue = value;
		if ((returnValue == null) || (returnValue.trim().equals(""))) {
			return "";
		}
		returnValue = returnValue.replaceAll("\\.\\./", "");
		returnValue = returnValue.replaceAll("\\.\\.\\\\", "");

		return returnValue;
	}
}
