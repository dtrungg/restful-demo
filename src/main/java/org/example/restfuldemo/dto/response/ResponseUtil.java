package org.example.restfuldemo.dto.response;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for creating standardized response objects.
 * Provides static factory methods to create success and error responses with consistent structure.
 */
public class ResponseUtil {
    /**
     * Creates a success response with the provided message and no data.
     * Sets success flag to true, includes a timestamp, and marks data and errors as null.
     *
     * @param <T>      Generic type parameter for response data
     * @param message  Message to include in the response
     * @return A success ResponseData object with the given message
     */
    public static <T> ResponseData<T> success(String message) {
        ResponseData<T> response = new ResponseData<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(null);
        response.setErrors(null);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }

    /**
     * Creates a success response with the provided data and message.
     * Sets success flag to true, includes a timestamp, and marks errors as null.
     *
     * @param <T>      Generic type parameter for response data
     * @param data     Data to include in the response
     * @param message  Message to include in the response
     * @return A success ResponseData object with the given data and message
     */
    public static <T> ResponseData<T> success(T data, String message) {
        ResponseData<T> response = new ResponseData<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setErrors(null);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }

    /**
     * Creates an error response with the provided list of errors and message.
     * Sets success flag to false, includes a timestamp, and marks data as null.
     *
     * @param <T>      Generic type parameter for response data
     * @param errors   List of error messages
     * @param message  Message to include in the response
     * @return An error ResponseData object with the given errors and message
     */
    public static <T> ResponseData<T> error(List<String> errors, String message) {
        ResponseData<T> response = new ResponseData<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setData(null);
        response.setErrors(errors);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }

    /**
     * Creates an error response with a single error message and the provided message.
     * Converts the single error string into a list and delegates to the error(List, String) method.
     *
     * @param <T>      Generic type parameter for response data
     * @param error    Single error message
     * @param message  Message to include in the response
     * @return An error ResponseData object with the given error and message
     */
    public static <T> ResponseData<T> error(String error, String message) {
        return error(Arrays.asList(error), message);
    }
}
