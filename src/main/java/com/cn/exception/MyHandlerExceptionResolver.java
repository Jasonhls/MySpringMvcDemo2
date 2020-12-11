package com.cn.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-09 11:51
 **/
@Component
public class MyHandlerExceptionResolver implements HandlerExceptionResolver, MessageSourceAware {

    private MessageSource messageSource;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if(ex instanceof MyException) {
                ModelAndView mav = resolveMyException((MyException)ex, request, response, handler);
                return mav;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private ModelAndView resolveMyException(MyException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        int status = ex.getStatus().value();
        String reason = ex.getReason();
        /*if(StringUtils.isBlank(reason)) {
            response.sendError(status);
        }*/
        String resolvedReason = this.messageSource != null ? this.messageSource.getMessage(reason, null, reason, LocaleContextHolder.getLocale()) : null;
//        response.sendError(status, resolvedReason);
        ModelAndView mav = new ModelAndView();
        mav.addObject("status", status);
        mav.addObject("reason", resolvedReason);
        //如果发生异常，跳转到指定的视图
        mav.setViewName("myError");
        return mav;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
