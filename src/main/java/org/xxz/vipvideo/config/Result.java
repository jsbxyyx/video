package org.xxz.vipvideo.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tt
 */
@Setter
@Getter
@ToString
public class Result implements java.io.Serializable {

    private String message;
    private Object data;

    public static Result success(String message, Object data) {
        Result result = new Result();
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result success(String message) {
        Result result = new Result();
        result.setMessage(message);
        return result;
    }


    public static Result fail(String message) {
        Result r = new Result();
        r.setMessage(message);
        return r;
    }

}
