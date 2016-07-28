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
package com.cimmyt.bean;


public class LocationWinBean {
	private Integer imslocid;
    private String lsname;
    private String lname;
    private String comments;
    private Integer imslocidparent;

    public Integer getImslocid() {
        return imslocid;
    }

    public void setImslocid(Integer imslocid) {
        this.imslocid = imslocid;
    }

    public String getLsname() {
        return lsname;
    }

    public void setLsname(String lsname) {
        this.lsname = lsname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getImslocidparent() {
        return imslocidparent;
    }

    public void setImslocidparent(Integer imslocidparent) {
        this.imslocidparent = imslocidparent;
    }

}
