package onlineshop.error;

import onlineshop.model.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Duplicate bicycle")
public class ProductAlreadyExistException extends BaseException {

    public ProductAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }
}
