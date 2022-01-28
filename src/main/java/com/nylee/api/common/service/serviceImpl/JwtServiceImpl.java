package com.nylee.api.common.service.serviceImpl;

import com.nylee.api.common.repository.model.JwtToken;
import org.springframework.stereotype.Service;
import com.nylee.api.common.service.JwtService;

import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String create(String key, JwtToken data, String subject) {
        return null;
    }

    @Override
    public boolean isUsable(String jwt, String claimKey) {
        return false;
    }

    @Override
    public Map<String, Object> get(String key) {
        return null;
    }

    @Override
    public String resignToken(String key, JwtToken data, String subject) {
        return null;
    }
}
