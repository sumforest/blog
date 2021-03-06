package com.sen.blog.common;

import cn.hutool.http.HtmlUtil;
import com.sen.blog.dto.ArticleDto;
import com.sen.blog.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: Sen
 * @Date: 2019/9/26 03:22
 * @Description:
 */
public class CommonValidatorMethod<T> {
    /**
     * 验证字段合法性
     * @param redirectAttributes
     * @param articleDto
     * @param url
     * @param response
     * @return
     */
    public static User validateArticle(RedirectAttributes redirectAttributes, ArticleDto articleDto, String url, HttpServletResponse response) {
        //验证articleDto是否合法
        String vaildateMessage = BeanValidator.validator(articleDto);
        if (vaildateMessage != null) {
            String cleanMsg = HtmlUtil.cleanHtmlTag(vaildateMessage);
            redirectAttributes.addFlashAttribute("vaildateMessage", cleanMsg);
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        //从shiro获取当前登录的用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    /**
     * 通用的验证字段合法性
     * @param redirectAttributes
     * @param t
     * @param url
     * @param response
     * @return
     */
    public boolean validate(RedirectAttributes redirectAttributes, T t, String url, HttpServletResponse response) {
        //验证T是否合法
        String vaildateMessage = BeanValidator.validator(t);
        if (vaildateMessage != null) {
            String cleanMsg = HtmlUtil.cleanHtmlTag(vaildateMessage);
            redirectAttributes.addFlashAttribute("vaildateMessage", cleanMsg);
            try {
                if (response != null) {
                    response.sendRedirect(url);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

}
