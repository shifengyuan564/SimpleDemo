package com.sfy.common;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * Description:
 * Author: shifengyuan@jd.com
 * Date: 2016/2/2
 */
public class IntegerEditor extends PropertiesEditor {

    public String getAsText() {
        Integer value = (Integer) getValue();
        if(null == value){
            value = new Integer(0);
        }
        return value.toString();
    }

    public void setAsText(String text) throws IllegalArgumentException {
        Integer value = null;
        if(null != text && !text.equals("")){
            value = Integer.valueOf(text);
        }
        setValue(value);
    }
}
