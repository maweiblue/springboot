package org.springframework.util;


import com.sun.istack.internal.Nullable;

import java.lang.reflect.Array;
import java.util.*;

/**
 *
 */
public abstract class ObjectUtils {
    private  static final  int INITIAL_HASH=7;
    private static final  int MULTIPLIER=31;

    private static final String EMPTY_STRING="";
    private static final String NULL_STRING="null";
    private static final String ARRAY_START="{";
    private static final String ARRAY_END="}";

    private static final String EMPTY_ARRAY=ARRAY_START+ARRAY_END;
    private static final String ARRAY_ELEMENT_SEPARATOR=", ";

    public static boolean isCheckException(Throwable ex){
        return !(ex instanceof RuntimeException || ex instanceof Error);
    }
    public  static boolean isCompatibleWithThrowsClause(Throwable ex,Class<?>... declaredExceptions){
        if(!isCheckException(ex)){
            return true;
        }
        if(declaredExceptions!=null){
            for (Class<?> declaredException:declaredExceptions){
                if(declaredException.isInstance(ex)){
                    return true;
                }
            }
        }
        return  false;
    }
    public static boolean isArray(Object object){
        return (object!=null&&object.getClass().isArray());
    }

    public static boolean isEmpty(Object[] array){
        return (array==null||array.length==0);
    }

    public static boolean isEmpty(Object object){
        if(object==null){
            return true;
        }
        if(object instanceof Optional){
            return !((Optional)object).isPresent();
        }
        if(object instanceof  CharSequence){
            return ((CharSequence)object).length()==0;
        }
        if(object.getClass().isArray()){
            return Array.getLength(object)==0;
        }
        if(object instanceof Collection){
            return ((Collection)object).isEmpty();
        }
        if(object instanceof Map){
            return ((Map)object).isEmpty();
        }
        return  false;
    }

    public static Object unwrapOptional(Object object){
        if(object instanceof Optional){
            Optional<?> optional=(Optional<?>) object;
            if(!optional.isPresent()){
                return  null;
            }
            Object result=optional.get();
            return result;
        }
        return object;
    }
    public static boolean containsElement(Object[] array,Object object){
        if(array==null){
            return false;
        }
        for (Object arrayEle:array){
            if(nullSafeEquals(arrayEle,object)){
                return true;
            }
        }
        return false;
    }

    public static boolean containsConstant(Enum<?>[] enumValue,String constant){
        return  containsConstant(enumValue,constant,false);
    }

    public static boolean containsConstant(Enum<?>[] enumValue, String constant, boolean caseSensitive) {
        for (Enum<?> candidate :
                enumValue) {
            if(caseSensitive?candidate.toString().equals(constant):candidate.toString().equalsIgnoreCase(constant)){
                return true;
            }

        }
        return false;
    }
    public static <E extends Enum<?>> E caseInsensitiveValueOf(E[] enumValues,String constant){
        for (E candidate:enumValues){
            if(candidate.toString().equalsIgnoreCase(constant)){
                return candidate;
            }
        }
        throw new IllegalArgumentException(
                String.format("constant [%s] does not exist in enum type %s",
                        constant, enumValues.getClass().getComponentType().getName()));
    }

    public static <A,O extends A> A[] addObjectToArray(A[] array,O obj){
        Class<?> compType=Object.class;
        if(array!=null){
            compType=array.getClass().getComponentType();
        }else if(obj!=null){
            compType=obj.getClass();
        }
        int newArrLength=(array!=null?array.length+1:1);
        A[] newArr=(A[]) Array.newInstance(compType,newArrLength);
        if(array!=null){
            System.arraycopy(array,0,newArr,0,array.length);
        }
        newArr[newArr.length-1]=obj;
        return newArr;
    }

    public static Object[] toObjectArray(Object source){
        if(source instanceof  Object[]){
            return (Object[]) source;
        }
        if(source==null){
            return new Object[0];
        }
        if(!source.getClass().isArray()){
            throw new IllegalArgumentException("Source is not an array: " + source);
        }
        int length=Array.getLength(source);
        if(length==0){
            return new Object[0];
        }
        Class<?> wrapperType=Array.get(source,0).getClass();
        Object[] newArray=(Object[]) Array.newInstance(wrapperType,length);
        for(int i=0;i<length;i++){
            newArray[i]=Array.get(source,i);
        }
        return newArray;

    }

    public static boolean nullSafeEquals(Object o1, Object o2) {
        if(o1==o2){
            return true;
        }
        if(o1==null||o2==null){
            return false;
        }
        if(o1.equals(o2)){
            return true;
        }
        if(o1.getClass().isArray()&&o2.getClass().isArray()){
            return arrayEquals(o1,o2);
        }
        return false;
    }

    private static boolean arrayEquals(Object o1, Object o2) {
        if(o1 instanceof  Object[]&&o2 instanceof Object[]){
            return Arrays.equals((Object[])o1,(Object[])o2);
        }
        if(o1 instanceof boolean[]&&o2 instanceof boolean[]){
            return Arrays.equals((boolean[])o1,(boolean[])o2);
        }
        if(o1 instanceof byte[] &&o2 instanceof byte[]){
            return Arrays.equals((byte[])o1,(byte[])o2);
        }
        if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[]) o1, (char[]) o2);
        }
        if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[]) o1, (double[]) o2);
        }
        if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[]) o1, (float[]) o2);
        }
        if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[]) o1, (int[]) o2);
        }
        if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[]) o1, (long[]) o2);
        }
        if (o1 instanceof short[] && o2 instanceof short[]) {
            return Arrays.equals((short[]) o1, (short[]) o2);
        }
        return false;
    }

    public static int nullSafeHashCode(Object object){
        if(object==null){
            return 0;
        }
        if(object.getClass().isArray()){
            if(object instanceof Object[]){
                return nullSafeHashCode((Object[]) object);
            }
            if(object instanceof  boolean[]){
                return nullSafeHashCode((boolean[])object);
            }
            if(object instanceof byte[]){
                return nullSafeHashCode((byte[]) object);
            }
            if(object instanceof char[]){
                return nullSafeHashCode((char[])object);
            }
            if(object instanceof double[]){
                return nullSafeHashCode((double[])object);
            }
            if (object instanceof float[]) {
                return nullSafeHashCode((float[]) object);
            }
            if (object instanceof int[]) {
                return nullSafeHashCode((int[]) object);
            }
            if (object instanceof long[]) {
                return nullSafeHashCode((long[]) object);
            }
            if (object instanceof short[]) {
                return nullSafeHashCode((short[]) object);
            }
        }
        return object.hashCode();
    }
    public static int nullSafeHashCode(Object[] array){
        if(array==null){
            return 0;
        }
        int hash=INITIAL_HASH;
        for (Object elem:array){
            hash=MULTIPLIER*hash+nullSafeHashCode(elem);
        }
        return hash;
    }
    public static int nullSafeHashCode( boolean[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (boolean element : array) {
            hash = MULTIPLIER * hash + Boolean.hashCode(element);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array.
     * If {@code array} is {@code null}, this method returns 0.
     */
    public static int nullSafeHashCode( byte[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (byte element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array.
     * If {@code array} is {@code null}, this method returns 0.
     */
    public static int nullSafeHashCode( char[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (char element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array.
     * If {@code array} is {@code null}, this method returns 0.
     */
    public static int nullSafeHashCode( double[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (double element : array) {
            hash = MULTIPLIER * hash + Double.hashCode(element);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array.
     * If {@code array} is {@code null}, this method returns 0.
     */
    public static int nullSafeHashCode( float[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (float element : array) {
            hash = MULTIPLIER * hash + Float.hashCode(element);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array.
     * If {@code array} is {@code null}, this method returns 0.
     */
    public static int nullSafeHashCode( int[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (int element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array.
     * If {@code array} is {@code null}, this method returns 0.
     */
    public static int nullSafeHashCode( long[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (long element : array) {
            hash = MULTIPLIER * hash + Long.hashCode(element);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array.
     * If {@code array} is {@code null}, this method returns 0.
     */
    public static int nullSafeHashCode( short[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (short element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    public static String identityToString(Object object){
        if(object==null){
            return EMPTY_STRING;
        }
        return object.getClass().getName()+"@"+getIdentityHexString(object);
    }

    public static String getIdentityHexString(Object object){
        return Integer.toHexString(System.identityHashCode(object));
    }

    public static String getDisplayString(Object object){
        if(object==null){
            return EMPTY_STRING;
        }
        return nullSafeToString(object);
    }
    public static String nullSafeClassName(Object object){
        return (object!=null?object.getClass().getName():NULL_STRING);
    }

    private static String nullSafeToString(Object object) {
        if(object==null){
            return NULL_STRING;
        }
        if(object instanceof String){
            return (String) object;
        }
        if(object instanceof Object[]){
            return nullSafeToString((Object[]) object);
        }
        if(object instanceof boolean[]){
            return nullSafeToString((boolean[]) object);
        }
        if(object instanceof byte[]){
            return nullSafeToString((byte[]) object);
        }
        if (object instanceof char[]) {
            return nullSafeToString((char[]) object);
        }
        if (object instanceof double[]) {
            return nullSafeToString((double[]) object);
        }
        if (object instanceof float[]) {
            return nullSafeToString((float[]) object);
        }
        if (object instanceof int[]) {
            return nullSafeToString((int[]) object);
        }
        if (object instanceof long[]) {
            return nullSafeToString((long[]) object);
        }
        if (object instanceof short[]) {
            return nullSafeToString((short[]) object);
        }
        String str = object.toString();
        return (str != null ? str : EMPTY_STRING);
    }

    public static String nullSafeToString(Object[] array){
        if(array==null){
            return NULL_STRING;
        }
        int length=array.length;
        if(length==0){
            return EMPTY_ARRAY;
        }
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            if(i==0){
                sb.append(ARRAY_START);
            }else{
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(String.valueOf(array[i]));
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    public static String nullSafeToString( boolean[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            }
            else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }

            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space). Returns
     * {@code "null"} if {@code array} is {@code null}.
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString( byte[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            }
            else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space). Returns
     * {@code "null"} if {@code array} is {@code null}.
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString( char[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            }
            else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append("'").append(array[i]).append("'");
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space). Returns
     * {@code "null"} if {@code array} is {@code null}.
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString( double[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            }
            else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }

            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space). Returns
     * {@code "null"} if {@code array} is {@code null}.
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString( float[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            }
            else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }

            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space). Returns
     * {@code "null"} if {@code array} is {@code null}.
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString( int[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            }
            else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space). Returns
     * {@code "null"} if {@code array} is {@code null}.
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString( long[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            }
            else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>The String representation consists of a list of the array's elements,
     * enclosed in curly braces ({@code "{}"}). Adjacent elements are separated
     * by the characters {@code ", "} (a comma followed by a space). Returns
     * {@code "null"} if {@code array} is {@code null}.
     * @param array the array to build a String representation for
     * @return a String representation of {@code array}
     */
    public static String nullSafeToString( short[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            }
            else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }
}
