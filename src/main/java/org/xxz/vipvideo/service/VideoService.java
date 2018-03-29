package org.xxz.vipvideo.service;

import org.xxz.vipvideo.model.ParseResult;

/**
 * @author tt
 */
public interface VideoService {


    /**
     * 根据视频源地址查询可播放地址
     * @param videoSourceUrl 视频源地址
     * @return 视频可播放地址
     */
    ParseResult parseVideo(String videoSourceUrl);

}
