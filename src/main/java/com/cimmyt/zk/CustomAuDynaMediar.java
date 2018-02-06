/*
Copyright 2013 International Maize and Wheat Improvement Center
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.cimmyt.zk;

import java.io.IOException;
import javax.servlet.ServletException; import javax.servlet.http.HttpServletRequest; import javax.servlet.http.HttpServletRequestWrapper; import javax.servlet.http.HttpServletResponse;
import org.zkoss.zk.au.http.AuDynaMediar;

public class CustomAuDynaMediar extends AuDynaMediar {

@Override
public void service(HttpServletRequest request, HttpServletResponse response, String pi) throws ServletException, IOException {
    super.service(new CustomHttpRequest(request), response, pi);
}

private static class CustomHttpRequest extends HttpServletRequestWrapper {

    public CustomHttpRequest(HttpServletRequest request) {
        super(request);
    }

    public StringBuffer getRequestURL() {
        HttpServletRequest request = (HttpServletRequest) super.getRequest();
        StringBuffer url = request.getRequestURL();

        int idx = url.indexOf(";jsessionid=" + request.getSession().getId());

        if (idx != -1) {
            url = url.delete(idx, url.length());
        }

        return url;
    }
}
}