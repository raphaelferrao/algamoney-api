package com.example.algamoney.api.util;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	
	private Utils() {
		
	}

	public static boolean empty(final String text){
		if ( text!=null ){
			String textTrim = text.trim();
			return ("".equals(textTrim));
		}
		return true;
	}
	
	public static boolean empty(final Collection<?> collection) {
		boolean empty = false;
		
		if (collection == null) {
			empty = true;
		} else if (collection.isEmpty()) {
			empty = true;
		} else {
			boolean isAllNull = true;
			for (Object obj : collection) {
				if (obj!=null) {
					isAllNull = false;
					break;
				}
			}
			if (isAllNull) {
				empty = true;
			}
		}
		return empty;
	}	
	
	public static boolean empty(final Map<?, ?> map) {
		boolean empty = false;
		
		if ( (map == null) || (map.isEmpty()) )  {
			empty = true;
		}
		
		return empty;
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
