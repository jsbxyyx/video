package org.xxz.vipvideo.util;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author tt
 */
@Slf4j
public class HttpUtil {

    public static String get(String urlString) {

        InputStream in = null;
        InputStreamReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            conn.setRequestMethod("GET");

            int code = conn.getResponseCode();
            log.info("url:{}, code:{}", urlString, code);
            if (code == HttpURLConnection.HTTP_OK) {
                in = conn.getInputStream();
                reader = new InputStreamReader(in);
                String s = CharStreams.toString(reader);
                return s;
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtil.close(in, reader);
        }
        return null;
    }

    public static String post(String urlString, Map<String, String> params) {
        InputStream in = null;
        OutputStream out = null;
        InputStreamReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(URLEncoder.encode(entry.getKey(), "utf-8"))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "utf-8"))
                        .append("&");
            }

            out = conn.getOutputStream();
            out.write(sb.toString().getBytes(Charsets.UTF_8));
            out.flush();
            out.close();

            int code = conn.getResponseCode();
            log.info("url:{}, code:{}", urlString, code);
            if (code == HttpURLConnection.HTTP_OK) {
                in = conn.getInputStream();
                reader = new InputStreamReader(in);
                String s = CharStreams.toString(reader);
                return s;
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtil.close(in, reader);
        }
        return null;
    }

}
