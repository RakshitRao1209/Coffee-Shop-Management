package au.com.cba.CoffeeShopManagement_api.exceptions;

public class CustomOptimisticLockingFailureException extends RuntimeException {
    public CustomOptimisticLockingFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
