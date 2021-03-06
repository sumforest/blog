package com.sen.blog.entity;

import com.sen.blog.constant.Regx;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: Sen
 * @Date: 2019/9/23 01:04
 * @Description: 评论实体类
 */
@Data
public class Comment implements Serializable {
    private static final long serialVersionUID = -363459263929928113L;

    private int commentId;

    /**
     * 父评论id
     */
    private int commentPid;

    /**
     * 父评论作者
     */
    private String commentPname;

    private int commentArticleId;
    @NotNull(message = "昵称不能为空")
    private String commentAuthorName;

    @Pattern(regexp = Regx.CHECK_MAIL,message = "邮箱格式不正确")
    private String commentAuthorEmail;

    private String commentAuthorUrl;

    private String commentAuthorAvatar;

    private String commentContent;

    private String commentAgent;

    private String commentIp;

    private Date commentCreateTime;
    /**
     * 角色(管理员1，访客0)
     */
    private int commentRole;
    /**
     * 非数据库字段
     */
    private Article article;

    public Comment() {
    }

    public Comment(int commentId) {
        this.commentId = commentId;
    }
}
