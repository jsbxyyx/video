package org.xxz.vipvideo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xxz.vipvideo.constant.CodeConstant;
import org.xxz.vipvideo.model.ParseResult;
import org.xxz.vipvideo.service.VideoService;
import org.xxz.vipvideo.util.ParseService;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author tt
 */
@Service
public class VideoServiceImpl implements VideoService {


    @Resource(name = "maoyunService")
    private ParseService parseService;

    @Override
    public ParseResult parseVideo(String videoSourceUrl) {
        return parseService.parse(videoSourceUrl);
    }
}
