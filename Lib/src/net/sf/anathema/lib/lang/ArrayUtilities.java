package net.sf.anathema.lib.lang;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArrayUtilities {

  public static int[] createIndexArray(int length) {
    int[] indexArray = new int[length];
    for (int index = 0; index < length; index++) {
      indexArray[index] = index;
    }
    return indexArray;
  }

  public static Integer[] createIntegerArray(int maximalValue) {
    Integer[] ranks = new Integer[Math.abs(maximalValue) + 1];
    for (int index = 0; index < ranks.length; index++) {
      ranks[index] = Integer.signum(maximalValue) * index;
    }
    return ranks;
  }

  public static Integer[] createIntegerArray(int minimalValue, int maximalValue) {
    Preconditions.checkState(minimalValue < maximalValue, "MinimalValue must be lower than mximalValue"); //$NON-NLS-1$
    Integer[] ranks = new Integer[maximalValue - minimalValue + 1];
    for (int index = 0; index < ranks.length; index++) {
      ranks[index] = minimalValue + index;
    }
    return ranks;
  }

  public static <T> T find(Predicate<T> predicate, T[] inputArray) {
    for (T input : inputArray) {
      if (predicate.apply(input)) {
        return input;
      }
    }
    return null;
  }

  public static <R> int indexOf(R[] array, R value) {
    int index = ArrayUtils.indexOf(array, value);
    if (index != ArrayUtils.INDEX_NOT_FOUND){
      return index;
    }
    throw new IllegalArgumentException("Value not contained in array: " + value); //$NON-NLS-1$
  }

  public static int max(int[] array) {
    int[] arrayCopy = new int[array.length];
    System.arraycopy(array, 0, arrayCopy, 0, array.length);
    Arrays.sort(arrayCopy);
    return arrayCopy[arrayCopy.length - 1];
  }

  public static void moveObject(Object[] array, int originalIndex, int targetIndex) {
    Object object = array[originalIndex];
    if (originalIndex < targetIndex) {
      System.arraycopy(array, originalIndex + 1, array, originalIndex, targetIndex - originalIndex);
      array[targetIndex] = object;
    }
    else {
      System.arraycopy(array, targetIndex, array, targetIndex + 1, originalIndex - targetIndex);
      array[targetIndex] = object;
    }
  }

  public static <T> void reorder(T[] objects, int[] originalIndices, int[] newIndices) {
    if (originalIndices.length <= 1) {
      return;
    }
    Map<Integer, T> nodesByOriginalIndex = new HashMap<Integer, T>();
    for (int element : originalIndices) {
      nodesByOriginalIndex.put(element, objects[element]);
    }
    for (int indexIndex = 0; indexIndex < originalIndices.length; indexIndex++) {
      int originalIndex = originalIndices[indexIndex];
      int newIndex = newIndices[indexIndex];
      objects[newIndex] = nodesByOriginalIndex.get(originalIndex);
    }
  }
}