package com.nylee.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpRequestWrapper extends HttpServletRequestWrapper {
    public HttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }
}
