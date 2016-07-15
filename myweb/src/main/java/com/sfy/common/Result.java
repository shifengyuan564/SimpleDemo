//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sfy.common;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private boolean success;
    private String errorCode;
    private String localizedMessage;
    private String errorStack;
    private String totalSize;
    private String currentPage;
    private String totalPage;
    private T result;
    private Object syncResult;
    private boolean isSystemAdmin;

    public Result(boolean success) {
        this.success = success;
    }

    public Result(T result) {
        this.success = true;
        this.result = result;
    }

    public Result(boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorStack() {
        return this.errorStack;
    }

    public void setErrorStack(String errorStack) {
        this.errorStack = errorStack;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getLocalizedMessage() {
        return this.localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public Object getSyncResult() {
        return syncResult;
    }

    public void setSyncResult(Object syncResult) {
        this.syncResult = syncResult;
    }

    public boolean isSystemAdmin() {
        return isSystemAdmin;
    }

    public void setSystemAdmin(boolean systemAdmin) {
        isSystemAdmin = systemAdmin;
    }
}
