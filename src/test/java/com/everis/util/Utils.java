package com.everis.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

	public static void waitForFileExistsInPath(String dir, int timeOutInSeconds) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});

		int contador = 0;
		boolean atingiuTimeout = false;
		while (files.length == 0 && !atingiuTimeout) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			contador++;
			atingiuTimeout = timeOutInSeconds == contador / 5;
			files = fl.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return file.isFile();
				}
			});
		}
		if (atingiuTimeout) {
			System.err.println("Nao foi gerado arquivo no caminho - " + dir + " apos " + timeOutInSeconds + " segundos");
		}
	}

	public static String getDateTime(String formato) {
		DateFormat dateFormat = new SimpleDateFormat(formato);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getDateTime(String formato, int intAcrescimoDias) {
		DateFormat dateFormat = new SimpleDateFormat(formato);
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.add(Calendar.DAY_OF_MONTH, intAcrescimoDias);
		return dateFormat.format(dataAtual.getTime());
	}

	public static String getDate() {
		SimpleDateFormat sdfDataAtual = new SimpleDateFormat("dd/MM/yyyy");
		Date now = new Date();
		String strDate = sdfDataAtual.format(now);
		return strDate;
	}

	public static String getHour() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static String getDateHour() {
		SimpleDateFormat sdfDataAtual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();
		String strDate = sdfDataAtual.format(now);
		return strDate;
	}

	public static String readFileToString(String path, Charset encoding) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded, encoding);
	}

}