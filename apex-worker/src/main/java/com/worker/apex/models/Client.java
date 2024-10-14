package com.worker.apex.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
    @JsonProperty("id")
    private Long clientId;
    @JsonProperty("nombre")
    private String name;
    @JsonProperty("direccion")
    private String address;
}
