package com.decimatech.tarim.config;

import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThymeleafLayoutInterceptor extends HandlerInterceptorAdapter {

    private static final String DEFAULT_LAYOUT = "layouts/main";
    private static final String DEFAULT_VIEW_ATTRIBUTE_NAME = "view";

    private String defaultLayout = DEFAULT_LAYOUT;
    private String viewAttributeName = DEFAULT_VIEW_ATTRIBUTE_NAME;

    public void setDefaultLayout(String defaultLayout) {
        Assert.hasLength(defaultLayout);
        this.defaultLayout = defaultLayout;
    }

    public void setViewAttributeName(String viewAttributeName) {
        Assert.hasLength(this.defaultLayout);
        this.viewAttributeName = viewAttributeName;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }

        String originalViewName = modelAndView.getViewName();
        if (this.isRedirectOrForward(originalViewName)) {
            return;
        }

        // When using custom login page with Spring-Security, login page's controller is of type ParameterizableViewController
        // We'd better render it without any layout as it's just the login page.
        // Note that this behavior is project dependent.
        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        String layoutName = this.getLayoutName((HandlerMethod) handler);
        modelAndView.setViewName(layoutName);
        modelAndView.addObject(this.viewAttributeName, originalViewName);
    }

    private boolean isRedirectOrForward(String viewName) {
        return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
    }

    private String getLayoutName(HandlerMethod handlerMethod) {
        Layout layout = this.getMethodOrTypeAnnotation(handlerMethod);
        if (layout == null) {
            return this.defaultLayout;
        } else {
            return layout.value();
        }
    }

    private Layout getMethodOrTypeAnnotation(HandlerMethod handlerMethod) {
        Layout layout = handlerMethod.getMethodAnnotation(Layout.class);
        if (layout == null) {
            return handlerMethod.getBeanType().getAnnotation(Layout.class);
        }
        return layout;
    }
}