package com.efe13.mvc.commons.api.util;

public final class Utils {
	
	private Utils() {
	}

	public static boolean isNegative( short number ) {
		return isNegative( (int) number );
	}
	
	public static boolean isNegative( int number ) {
		return number < 0;
	}
	
	public static boolean isNegative( float number ) {
		return number < 0;
	}
	
	public static <T> boolean isNull( T object ) {
		return object == null;
	}
	
	public static boolean isEmpty( String str ) {
		return isNull( str ) || str.trim().isEmpty();
	}
	
	public static int lengthCheck( String str, int minLength, int maxLength ) {
		if( isEmpty( str ) || str.length() < minLength ) {
			return -1;
		}
		else if( str.length() > maxLength ) {
			return 1;
		}
		
		return 0;
	}
	
	public static String toUpperCase( String str ) {
		if( isEmpty( str ) ) {
			return str;
		}
		
		return str.toUpperCase();
	}
}