package com.nylee.api.common.service;
import com.nylee.api.common.repository.model.JwtToken;

import java.util.Map;

public interface JwtService {

    String create(String key, JwtToken data, String subject);
    boolean isUsable(String jwt,String claimKey);
    Map<String, Object> get(String key);
    String resignToken(String key, JwtToken data, String subject);
}
