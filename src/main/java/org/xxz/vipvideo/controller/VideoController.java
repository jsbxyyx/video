package org.xxz.vipvideo.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xxz.vipvideo.config.Result;
import org.xxz.vipvideo.constant.CodeConstant;
import org.xxz.vipvideo.exception.NotParseException;
import org.xxz.vipvideo.model.ParseResult;
import org.xxz.vipvideo.service.VideoService;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author tt
 */
@Slf4j
@RestController
public class VideoController {

    @Resource
    private VideoService videoService;

    @RequestMapping(value = "/getUrl", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getUrl(String url) {
        if (url.indexOf("sohu.com") > -1) {
            throw new NotParseException("无法解析");
        }
        ParseResult pr = videoService.parseVideo(url);
        if (Objects.equals(pr.getMsg(), CodeConstant.OK)) {
            return Result.success("解析成功", pr.getUrl());
        }
        throw new NotParseException(pr.getMsg());
    }

}
