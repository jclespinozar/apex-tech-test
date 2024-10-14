package com.worker.apex.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class FailedOrder implements Serializable {
    private String orderId;
    private String message;
    private int retryCount;
}
