package main.by.library.util;

public interface LoggerUtil {

    String ENTER_METHOD_MESSAGE = "Entering the method";
    String PARAM_REQUEST_MESSAGE = "Param request";
    String PARAM_RECEIVE_MESSAGE = "Param receive";
    String ERROR_BLOCK_MESSAGE = "Error block";
    String SET_ATTRIBUTE_MESSAGE = "Set attribute";
    String DAO_METHODS_EXCEPTION_MESSAGE = "Error during DAO method processing";
    String ERROR_DURING_LOAD_DRIVER_MESSAGE = "Error during load driver";
    String INIT_CONNECTION_POOL_ERROR_MESSAGE = "Init connection pool error";
    String ERROR_DURING_PROPERTIES_LOADING_MESSAGE = "Error during properties loading";
    String DO_GET_METHOD_PROCESSING_MESSAGE = "doGet method processing";
    String DO_POST_METHOD_PROCESSING_MESSAGE = "doPost method processing";
    String DO_POST_METHOD_EXCEPTION_MESSAGE = "Error during doPost method processing";
    String DO_GET_METHOD_EXCEPTION_MESSAGE = "Error during doGet method processing";
}
