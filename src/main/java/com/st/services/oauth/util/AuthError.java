package com.st.services.oauth.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.common.util.StringUtils;

@XmlRootElement()
public class AuthError {
    
    private String code;
    private String message;
    private String description;
    
    public AuthError(){}

    public AuthError(String code, String message) {
        this.code = code;
        this.message = message;
    }
    @XmlElement()
    public String getDescription() {
    	if(StringUtils.isEmpty(description)) {
    		return message;
    	}
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @XmlElement()
    public String getCode() {
        return code;
    }
    @XmlElement()
    public String getMessage() {
        return message;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }    

}
