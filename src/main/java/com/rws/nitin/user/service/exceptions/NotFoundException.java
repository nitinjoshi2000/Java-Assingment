package com.rws.nitin.user.service.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {
    String resourceName;
    String fieldName;
    String fieldValue;

    public NotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s  not found with %s : %s", resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
