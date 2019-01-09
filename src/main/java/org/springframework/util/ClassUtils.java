package org.springframework.util;

public abstract class ClassUtils {

    public static final String ARRAY_SUFFIX = "[]";

    /** Prefix for internal array class names: "[" */
    private static final String INTERNAL_ARRAY_PREFIX = "[";

    /** Prefix for internal non-primitive array class names: "[L" */
    private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";

    /** The package separator character: '.' */
    private static final char PACKAGE_SEPARATOR = '.';

    /** The path separator character: '/' */
    private static final char PATH_SEPARATOR = '/';

    /** The inner class separator character: '$' */
    private static final char INNER_CLASS_SEPARATOR = '$';

    /** The CGLIB class separator: "$$" */
    public static final String CGLIB_CLASS_SEPARATOR = "$$";

    /** The ".class" file suffix */
    public static final String CLASS_FILE_SUFFIX = ".class";

    public static String convertResourcePathToClassName(String resourcePath) {
        return resourcePath.replace(PATH_SEPARATOR, PACKAGE_SEPARATOR);
    }
}
