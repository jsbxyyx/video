package org.xxz.vipvideo.util;

import lombok.extern.slf4j.Slf4j;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class JsUtil {

    private static ScriptEngine engine;
    private static Invocable invoke;
    static {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("js");
        InputStream in = JsUtil.class.getResourceAsStream("/maoyun_md5.js");
        try {
            engine.eval(new InputStreamReader(in));
        } catch (ScriptException e) {
            log.error(e.getMessage(), e);
        }
        if(engine instanceof Invocable) {
            invoke = (Invocable) engine;
        }
    }

    public static String md5(String str) {
        try {
            String sign = (String) invoke.invokeFunction("sign", str);
            return sign;
        } catch (ScriptException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
