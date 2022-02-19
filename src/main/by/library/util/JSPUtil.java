package main.by.library.util;

public final class JSPUtil {

    private static final String USER_PREFIX = "/page/user/";
    private static final String ADMIN_PREFIX = "/page/admin/";
    private static final String LIBRARIAN_PREFIX = "/page/librarian/";
    private static final String SUFFIX = ".jsp";
    public static final String ERROR_PAGE = "/page/errorPage.jsp";
    public static final String BAN_PAGE = "/page/banPage.jsp";
    public static final String CONTROLLER_COMMAND = "/controller?command=";
    public static final String START_PAGE = "/";

    private JSPUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the full user path to the entered file
     * @param jspName String JSP name without suffix
     * @return String path
     */
    public static String getUserJSPPath(String jspName) {
        return USER_PREFIX + jspName + SUFFIX;
    }

    /**
     * Returns the full admin path to the entered file
     * @param jspName String JSP name without suffix
     * @return String path
     */
    public static String getAdminJSPPath(String jspName) {
        return ADMIN_PREFIX + jspName + SUFFIX;
    }

    /**
     * Returns the full librarian path to the entered file
     * @param jspName String JSP name without suffix
     * @return String path
     */
    public static String getLibrarianJSPPath(String jspName) {
        return LIBRARIAN_PREFIX + jspName + SUFFIX;
    }

    /**
     * Returns the full command path to the entered file
     * @param command String name command
     * @return String path
     */
    public static String getControllerCommandPath(String command) {
        return CONTROLLER_COMMAND + command;
    }
}
