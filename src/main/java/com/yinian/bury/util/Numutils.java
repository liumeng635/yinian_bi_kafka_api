package com.yinian.bury.util;

public class Numutils {
	public static int parse2num(Object o) {
		if( o == null )return 0;
		String s = String.valueOf(o);
		int rs = 0;
		try {
			rs = Integer.valueOf(s);
		} catch (NumberFormatException e) {
			rs = 0;
		}
		return rs;
	}
	
	public static void main(String[] args) {
		System.out.println(Numutils.parse2num("null"));
	}
}
