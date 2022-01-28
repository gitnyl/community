package com.nylee.common.filter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class HttpResponseWrapper extends HttpServletResponseWrapper {
    public HttpResponseWrapper(HttpServletResponse response) {
        super(response);
    }
}
