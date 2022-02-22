package main.by.library.util;

public final class LoggerUtil {

    private LoggerUtil() {
        throw new UnsupportedOperationException();
    }

    public static final String ENTER_METHOD_MESSAGE = "Entering the method";
    public static final String PARAM_REQUEST_MESSAGE = "Param request";
    public static final String PARAM_RECEIVE_MESSAGE = "Param receive";
    public static final String ERROR_BLOCK_MESSAGE = "Error block";
    public static final String SET_ATTRIBUTE_MESSAGE = "Set attribute";
    public static final String DAO_METHODS_EXCEPTION_MESSAGE = "Error during DAO method processing";
    public static final String ERROR_DURING_LOAD_DRIVER_MESSAGE = "Error during load driver";
    public static final String INIT_CONNECTION_POOL_ERROR_MESSAGE = "Init connection pool error";
    public static final String ERROR_DURING_PROPERTIES_LOADING_MESSAGE = "Error during properties loading";
    public static final String DO_GET_METHOD_PROCESSING_MESSAGE = "doGet method processing";
    public static final String DO_POST_METHOD_PROCESSING_MESSAGE = "doPost method processing";
    public static final String DO_POST_METHOD_EXCEPTION_MESSAGE = "Error during doPost method processing";
    public static final String DO_GET_METHOD_EXCEPTION_MESSAGE = "Error during doGet method processing";
}
