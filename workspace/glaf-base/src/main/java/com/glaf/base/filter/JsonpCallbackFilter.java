/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.base.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.base.servlet.*;

public class JsonpCallbackFilter implements Filter {

  private static Logger log = LoggerFactory.getLogger(JsonpCallbackFilter.class);

  public void init(FilterConfig fConfig) throws ServletException {}

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    Map<String, String[]> parms = httpRequest.getParameterMap();

    if (parms.containsKey("callback")) {
      if (log.isDebugEnabled())
          log.debug("Wrapping response with JSONP callback '" + parms.get("callback")[0] + "'");

      OutputStream out = httpResponse.getOutputStream();

      GenericResponseWrapper wrapper = new GenericResponseWrapper(httpResponse);

      chain.doFilter(request, wrapper);

      //handles the content-size truncation
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
      outputStream.write(new String(parms.get("callback")[0] + "(").getBytes() );
      outputStream.write(wrapper.getData());
      outputStream.write(new String(");").getBytes());
      byte jsonpResponse[] = outputStream.toByteArray( );

      wrapper.setContentType("text/javascript;charset=UTF-8");
      wrapper.setContentLength(jsonpResponse.length);

      out.write(jsonpResponse);

      out.close();
    
    } else {
      chain.doFilter(request, response);
    }
  }

  public void destroy() {}
}
