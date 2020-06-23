package cn.pbj2019.demo;

import cn.pbj2019.demo.springboot_mail.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SpringbootMailTest
 * @Author: pbj
 * @Date: 2019/8/8 23:44
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMailTest {

    @Autowired
    MailService mailService;

    @Test
    public void TestSendTextMail() {
        mailService.sendTextMail("测试邮件发送主题", "测试邮件发送内容",
                "1784675177@qq.com", "pengbingjiang4@163.com");
    }

    @Test
    public void TestSendTextMailAndAttach() {
        mailService.sendAttachmentMail("测试邮件和附件发送主题", "测试邮件和附件发送内容",
                "d:/", "testemailattach.txt", "1234@qq.com", "12345@qq.com", "123456@qq.com");
    }

    @Test
    public void TestSendTextMailAndManyCC() {
        List<String> cc = new ArrayList<>();
        cc.add("抄送人1");
        cc.add("抄送人2");
        cc.add("抄送人3");
        mailService.sendTextMailPlus("测试邮件发送和多抄送人主题", "测试邮件发送和多抄送人内容",
                cc, "1234@qq.com", "12345@qq.com", "123456@qq.com");
    }

    @Test
    public void testSendMailWithImg() {
        mailService.sendTextMailWithImg("测试邮件发送和图片",
                "<div>hello,这是一封带图片的资源的邮件："+
                        "这个图片1：<div><img src='cid:p01'/></div>"+
                        "这是图片2：<div><img src='cid:p02'/></div>"+"</div>",
                new String[]{"C:\\test\\1.png","E:\\test\\2.png"},
                new String[]{"p01","p02"});
    }
}
