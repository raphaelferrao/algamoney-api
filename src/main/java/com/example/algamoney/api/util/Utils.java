package com.example.algamoney.api.util;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	public static boolean empty(final String text){
		if ( text!=null ){
			String textTrim = text.trim();
			if (!"".equals(textTrim)){
				return false;
			}
			return true;
		}
		return false;
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
		
		if (map == null) {
			empty = true;
		} else if (map.isEmpty()) {
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
