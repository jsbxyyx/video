package org.xxz.vipvideo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.xxz.vipvideo.constant.CodeConstant;
import org.xxz.vipvideo.model.ParseResult;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tt
 */
@Slf4j
@Service("maoyunService")
public class MaoyunServiceImpl implements ParseService {

    private static final String maoyun_parse_url = "http://jx.maoyun.tv/index.php?id=%s";
    private static final String maoyun_get_url = "http://jx.maoyun.tv/url.php";

    private static final Pattern eval_pattern = Pattern.compile("eval\\(\"(.*)\"\\)");

    @Override
    public ParseResult parse(String videoSourceUrl) {

        Assert.notNull(videoSourceUrl, "videoSourceUrl must not be null");

        ParseResult pr = new ParseResult();

        String vid = getQueryString(videoSourceUrl, "id");
        if (videoSourceUrl.indexOf("#") > 0 && videoSourceUrl.indexOf("bilibili") > 0) {
            pr.setMsg(CodeConstant.OK);
            pr.setUrl(videoSourceUrl.replace("#", "_"));
            return pr;
        }
//        else if (vid.indexOf(".mp4") > 0) {
//            return vid;
//        } else if (vid.indexOf(".m3u8") > 0) {
//            return vid;
//        }

        String result = HttpUtil.get(String.format(maoyun_parse_url, videoSourceUrl));
        Matcher matcher = eval_pattern.matcher(result);
        if (matcher.find()) {
            String hexString = matcher.group(1);
            hexString = hex2Str(hexString);
            final String s = "val('";
            final String e = "');";
            hexString = hexString.substring(hexString.indexOf(s) + (s.length()), hexString.lastIndexOf(e));
            String md5 = JsUtil.md5(hexString);
            Map<String, String> params = Maps.newHashMap();
            params.put("id", videoSourceUrl);
            params.put("md5", md5);
            params.put("type", "auto");
            params.put("siteuser", "");
            params.put("hd", "");
            params.put("lg", "");
            String post = HttpUtil.post(maoyun_get_url, params);
            JSONObject json = JSON.parseObject(post);
            String msg = json.getString("msg");

            if (Objects.equals(msg, "200")) {
                pr.setMsg(CodeConstant.OK);
                pr.setUrl(json.getString("url"));
                return pr;
            } else {
                pr.setMsg(msg);
                return pr;
            }
        }

        return pr;
    }

    private String getQueryString(String url, String name) {

        int pos = url.indexOf("?");
        if (pos < 0) {
            return null;
        }

        url = url.substring(pos + 1);

        String[] split = url.split("\\&");
        for (String s : split) {
            if (s.startsWith(name + "=")) {
                String result = s.substring(s.indexOf(name + "=") + (name.length() + 1));
                try {
                    return URLDecoder.decode(result, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        String a = "eval(\"\\x24\\x28\\x27\\x23\\x68\\x64\\x4d\\x64\\x35\\x27\\x29\\x2e\\x76\\x61\\x6c\\x28\\x27\\x33\\x61\\x64\\x35\\x61\\x65\\x33\\x65\\x31\\x37\\x65\\x32\\x33\\x35\\x31\\x34\\x31\\x36\\x62\\x39\\x65\\x62\\x61\\x61\\x61\\x66\\x63\\x35\\x38\\x65\\x38\\x38\\x27\\x29\\x3b\")";

        Matcher matcher = eval_pattern.matcher(a);
        if (matcher.find()) {
            String group = matcher.group(1);
            System.out.println(group);
            System.out.println(hex2Str(group));
        }
    }

    private static String hex2Str(String str) {
        // 分割拿到形如 xE9 的16进制数据
        String[] strArr = str.split("\\\\");
        byte[] byteArr = new byte[strArr.length - 1];
        for (int i = 1; i < strArr.length; i++) {
            Integer hexInt = Integer.decode("0" + strArr[i]);
            byteArr[i - 1] = hexInt.byteValue();
        }
        try {
            return new String(byteArr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private static String str2Hex(String str) {
        String hexRaw = null;
        try {
            hexRaw = String.format("%x", new BigInteger(1, str.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        char[] hexRawArr = hexRaw.toCharArray();
        StringBuilder hexFmtStr = new StringBuilder();
        final String SEP = "\\x";
        for (int i = 0; i < hexRawArr.length; i++) {
            hexFmtStr.append(SEP).append(hexRawArr[i]).append(hexRawArr[++i]);
        }
        return hexFmtStr.toString();
    }

}
