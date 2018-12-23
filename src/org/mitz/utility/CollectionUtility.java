package org.mitz.utility;

import java.util.Arrays;
import java.util.List;

public class CollectionUtility {

	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(T[][] arr){
		Object[] array = Arrays.stream(arr).flatMap(Arrays::stream).toArray();
		T[] copyOf = (T[]) Arrays.copyOf(array, array.length);
		return Arrays.asList(copyOf);
	}
}
