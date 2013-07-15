package pksimulator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import pkparameter.DefNum;

public class OutputCSV implements DefNum {
	Date date1;
	SimpleDateFormat sdf1;
	File file, dir;
	PrintWriter pw;
	String dirstr;
	File mfile;
	PrintWriter pw2, pw3;

	public OutputCSV() {
	}

	public void mkdir(){
		date1 = new Date();

		sdf1 = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
		dirstr = "data/" + sdf1.format(date1);
		dir = new File(dirstr);

		if (dir.mkdir()) {
			System.out.println("mkdir done");
		} else {
			System.out.println("mkdir error");
		}
	}
	
	public void mkfile(String str){
		try {
			//file = new File(dirstr + "/" + str);
			OutputStreamWriter osw =
					new OutputStreamWriter(new FileOutputStream(dirstr + "/" + str),"SJIS");
			//pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			pw = new PrintWriter(new BufferedWriter(osw));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void memoWrite(double srate, double avgtime, double stdtime, double mintime, double medtime) {
		
		try {
			mfile = new File("data/memo/memo.csv");
			pw2 = new PrintWriter(new BufferedWriter(new FileWriter(mfile, true)));	// trueで追記モード
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		pw2.println(sdf1.format(date1) + "," + VPARA + "," + AVPARA + "," + FLAPPING_EFFECT + "," + srate + "," + avgtime + ',' + stdtime
				 + "," + mintime + "," + medtime);

		pw2.close();
		System.out.println("( ..)φメモメモ");
	}
	
	public void resultWrite(){
		try {
			mfile = new File("data/memo/result" + (int)(AVPARA * 10)+ ".csv");
			pw3 = new PrintWriter(new BufferedWriter(new FileWriter(mfile, true)));	// trueで追記モード
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if(CPTSimulator.count != LIM_TIME){
			pw3.println(CPTSimulator.count/10.0);
			pw3.close();
		}
		System.out.println("( ..)φメモメモ");
	}
	
	public void writeData(double data) {
		pw.print(data + ",");
	}
	
	public void writeLine(){
		pw.println();
	}

	public void close() {
		pw.close();
	}
	
}
