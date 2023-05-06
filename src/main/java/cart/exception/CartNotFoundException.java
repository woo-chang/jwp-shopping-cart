package cart.exception;

import org.springframework.http.HttpStatus;

public class CartNotFoundException extends HttpException {

    public CartNotFoundException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
