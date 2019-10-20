/* Copyright 2012 by Michael R. Clements
 * This software is open source and free to distribute and create derivative works.
*/
package com.mrc.util;

import java.io.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;

// This is the home of various utility functions.
public class Util {
	// If you use the file functions, you MUST set this to your app's dir name (not full path)
	public static String	APP_DIR;
	static File				extDir	= null,
							appDir	= null;

	public static void dialogMessage(Context ctx, String title, String msg) {
		new AlertDialog.Builder(ctx)
			.setTitle(title)
			.setMessage(msg)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					// finish();
				}
			})
			.show();
	}

	public static Bitmap getBitmap(String fName, AssetManager am)
			throws IOException {
		return BitmapFactory.decodeStream(am.open(fName));
	}

	public static File getFile(String fName) {
		File fil = initFiles();
		if(fil != null)
			fil = new File(fil.getAbsolutePath() + File.separator + fName);
		else
			fil = null;
		return fil;
	}

	// returns T/F whether the given file exists for the given asset manager
	public static boolean assetExists(String fName, AssetManager am) {
		InputStream		str;

		try {
			str = am.open(fName);
		}
		catch(Exception e) {
			return false;
		}
		try {
			str.close();
		}
		catch(Exception e) {
		}
		return true;
	}

	// gets this app's home directory, creates if necessary
	protected static File initFiles() {
		if(appDir == null) {
			if((extDir == null) && (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())))
				extDir = Environment.getExternalStorageDirectory();
			if(extDir != null)
				appDir = new File(extDir.getAbsolutePath() + File.separator + APP_DIR);
			if(!appDir.exists())
				appDir.mkdirs();
		}
		return appDir;
	}

	public static String getDefaultDownloadPath() {
		return Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS
			).getAbsolutePath();
	}

	public static BufferedReader readFile(String fName) throws IOException {
		return new BufferedReader(new FileReader(getFile(fName)));
	}

	public static BufferedWriter writeFile(String fName) throws IOException {
		return new BufferedWriter(new FileWriter(getFile(fName)));
	}

	// create a file for writing, with auto-generated filename based on date/time
	public static BufferedWriter writeFile() throws IOException {
		Calendar		cal;
		StringBuilder	fName;
		String			dateTime;

		cal = Calendar.getInstance();
		fName = new StringBuilder();
		dateTime = String.format("%02d%02d%02d%02d%02d%02d",
				cal.get(Calendar.YEAR) - 2000,
				cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DAY_OF_MONTH),
				cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND));
		fName.append("gforceData-")
				.append(dateTime)
				.append(".txt");
		return Util.writeFile(fName.toString());
	}

	// WARNING: assumes the bitmap is square
	public static Bitmap resizeBitmap(Bitmap bm, int newSiz) {
		if(bm.getWidth() != newSiz)
			bm = Bitmap.createScaledBitmap(bm, newSiz, newSiz, false);
		return bm;
	}

	// Resizes the given bitmap as large as possible to fit the given dimensions
	// No distortion - maintains aspect ratio
	public static Bitmap resizeBitmap(Bitmap bm, int sizX, int sizY) {
		int		bitmapY, bitmapX;
		double	ratio;
		
		ratio = getBitmapZoom(bm, sizX, sizY);
		bitmapX = (int)((double)bm.getWidth() * ratio + 0.5);
		bitmapY = (int)((double)bm.getHeight() * ratio + 0.5);
		bm = Bitmap.createScaledBitmap(bm, bitmapX, bitmapY, false);
		return bm;
	}

	public static double getBitmapZoom(Bitmap bm, int sizX, int sizY) {
		double	ratioX, ratioY;

		ratioX = (double)sizX / (double)bm.getWidth();
		ratioY = (double)sizY / (double)bm.getHeight();
		return (ratioX < ratioY ? ratioX : ratioY);
	}

	public static void closeFile(BufferedWriter outStream) {
		try {
			outStream.close();
		}
		catch(Exception e) {
			// TODO: something about this?
		}
	}

	public static void closeFile(BufferedReader inStream) {
		try {
			inStream.close();
		}
		catch(Exception e) {
			// TODO: something about this?
		}
	}

	public static void writeBoolean(BufferedWriter outStream, boolean val) throws IOException {
		outStream.write(Boolean.toString(val));
		outStream.newLine();
	}

	public static void writeInt(BufferedWriter outStream, int val) throws IOException {
		outStream.write(Integer.toString(val));
		outStream.newLine();
	}

	public static void writeLong(BufferedWriter outStream, long val) throws IOException {
		outStream.write(Long.toString(val));
		outStream.newLine();
	}

	public static void writeDouble(BufferedWriter outStream, double val) throws IOException {
		outStream.write(Double.toString(val));
		outStream.newLine();
	}

	public static void writeString(BufferedWriter outStream, String txt) throws IOException {
		outStream.write(txt);
		outStream.newLine();
	}

	public static boolean readBoolean(BufferedReader inStream) throws IOException {
		return Boolean.parseBoolean(inStream.readLine());
	}

	public static int readInt(BufferedReader inStream) throws IOException {
		return Integer.parseInt(inStream.readLine());
	}

	public static long readLong(BufferedReader inStream) throws IOException {
		return Long.parseLong(inStream.readLine());
	}

	public static double readDouble (BufferedReader inStream) throws IOException {
		return Double.parseDouble(inStream.readLine());
	}
	
	public static String readString(BufferedReader inStream) throws IOException {
		return inStream.readLine();
	}

	public static int random(int range) {
		return (int)(Math.random() * (double)range);
	}
	
	public static void sleep() {
		sleep(0L);
	}

	public static void sleep(long msecs) {
		try {
			Thread.sleep(msecs);
		}
		catch(InterruptedException localInterruptedException) {
			// do nothing - we don't care
		}
	}
}
