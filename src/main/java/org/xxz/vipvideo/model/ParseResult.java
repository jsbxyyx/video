package org.xxz.vipvideo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tt
 */
@Setter
@Getter
@ToString
public class ParseResult {

    private String msg;
    private String url;

    public ParseResult() {}

    public ParseResult(String msg, String url) {
        this.msg = msg;
        this.url = url;
    }

}
