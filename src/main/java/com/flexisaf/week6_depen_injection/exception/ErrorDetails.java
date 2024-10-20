package com.flexisaf.week6_depen_injection.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private LocalDateTime timeStamp;
    private String message;
    private String path;
    private String errorCode;
}
