package cz.uhk.fim.bs.pickngo_web_be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UnauthorizedAccesException extends RuntimeException {

    private String resourceName;

    public UnauthorizedAccesException(String resourceName) {
        super(String.format("%s has no access", resourceName));
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }
}