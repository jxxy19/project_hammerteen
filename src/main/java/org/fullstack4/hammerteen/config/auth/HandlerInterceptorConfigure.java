package org.fullstack4.hammerteen.config.auth;

import org.fullstack4.hammerteen.interceptor.LoginCheckInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HandlerInterceptorConfigure implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;
    @Autowired
    public HandlerInterceptorConfigure(LoginCheckInterceptor loginCheckInterceptor) {
        super();
        this.loginCheckInterceptor = loginCheckInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**/regist")
                .addPathPatterns("/**/modify")
                .addPathPatterns("/**/delete")
                .addPathPatterns("/mypage/**")
                .addPathPatterns("/mystudy/**")
                .addPathPatterns("/payment/**")
                .addPathPatterns("/lecture/addCart")
                .addPathPatterns("/lecture/addGood")
                .excludePathPatterns("/member/regist")
                .excludePathPatterns("/board/view/**");
    }
}
