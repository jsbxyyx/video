package org.xxz.vipvideo.util;

import org.xxz.vipvideo.model.ParseResult;

/**
 * @author tt
 */
public interface ParseService {

    /**
     * 解析服务
     * @param videoSourceUrl
     * @return
     */
    ParseResult parse(String videoSourceUrl);
}
