package com.qf.nauth;

import java.math.BigDecimal;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*System.out.println("first!");
		System.out.println("second!");
		System.out.println("third!");
		System.out.println("fourth!");
		System.out.println("fifth!");
		System.out.println("sixth!");
		System.out.println("eight!");
		System.out.println("seventh!");
		System.out.println("eleven!");
		System.out.println("ten!");
		System.out.println("twelve!");
		System.out.println("thirteen!");
		System.out.println("sixteen!");
		System.out.println("fourteen!");
		System.out.println("test!");
		System.out.println("啛啛喳喳33!");
		System.out.println("啛啛喳喳22!");
		System.out.println("啛啛喳喳44!");
		System.out.println("seventeen!");*/
		BigDecimal pmtAmtMoth = new BigDecimal(6);
		if(pmtAmtMoth.compareTo(new BigDecimal(7))==0){
			System.out.println("相等");
		}else if(pmtAmtMoth.compareTo(new BigDecimal(7))==1){
			System.out.println("大于");
		}else if(pmtAmtMoth.compareTo(new BigDecimal(7))==-1){
			System.out.println("小于");
		}else {
			System.out.println("异常");
		}
	}

}
