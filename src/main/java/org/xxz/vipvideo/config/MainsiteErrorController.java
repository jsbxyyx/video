package org.xxz.vipvideo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
public class MainsiteErrorController extends AbstractErrorController {

    private static final String ERROR_PATH = "/error";

    @Autowired
    public MainsiteErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(value = ERROR_PATH)
    public ResponseEntity<Object> handleError(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, false);
        final String error = "No handler found for " + request.getMethod() + " " + body.get("path");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.fail(error));
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}