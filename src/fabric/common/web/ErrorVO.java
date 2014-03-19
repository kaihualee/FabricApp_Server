/**
 * @(#)ErrorVO.java, 2013-6-30. Copyright 2013 Netease, Inc. All rights
 *                   reserved. NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject
 *                   to license terms.
 */
package fabric.common.web;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;


/**
 * @author nisonghai
 */
public class ErrorVO extends BaseVO{

    protected int errorCode = 0;

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode
     *            the errorCode to set
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    protected String message;

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorVO(ErrorCode errorCode) {
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ErrorVO(ErrorCode errorCode, String message) {
        this.errorCode = errorCode.getCode();
        this.message = message;
    }
    
    @Override
    public String toString() {
        return errorCode + " " + message;
    }
    
    @Override
    public Representation representation() {
        return new JsonRepresentation(new JSONObject(this));
    }
}
