package com.pega.vote.bank.error;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorProperty {
    private String code;
    
    private String message;
    
    private String traceId;

    private int status;

}