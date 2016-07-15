package com.sfy.common;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * Description: 自定义数据绑定
 * Author: shifengyuan@jd.com
 * Date: 2016/2/2
 */
public class DoubleEditor extends PropertiesEditor {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals("")) {
            text = "0";
        }
        setValue(Double.parseDouble(text));
    }

    @Override
    public String getAsText() {
        return getValue().toString();
    }
}