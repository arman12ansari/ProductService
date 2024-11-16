package dev.arman.productservice.controlleradvices;

import dev.arman.productservice.dtos.ExceptionDto;
import dev.arman.productservice.exceptions.ProductNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author mdarmanansari
 */
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotExistsException.class)
    public ResponseEntity<ExceptionDto> handleProductNotExistsException(ProductNotExistsException exception) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(exception.getMessage());
        exceptionDto.setDetails("Product not found");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }
}
