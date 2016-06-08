package com.itlong.whatsmars.tomcat.connector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shenhongxi on 16/4/14.
 */
public interface Container {

    public void invoke(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
