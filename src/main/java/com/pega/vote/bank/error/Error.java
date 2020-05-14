package com.pega.vote.bank.error;

import java.util.List;

public class Error {
    List<ErrorProperty> errors;

    public List<ErrorProperty> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorProperty> errors) {
        this.errors = errors;
    }
}