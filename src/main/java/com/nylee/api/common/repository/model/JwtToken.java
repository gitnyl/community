package com.nylee.api.common.repository.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class JwtToken {
    private String id;
    private Integer idx;
}
