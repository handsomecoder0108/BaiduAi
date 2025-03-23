package com.hj.controller;


import com.baidu.aip.face.AipFace;
import com.hj.pojo.Result;
import com.hj.service.UserBaiduAIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author 胡军
 * @Version 1.0
 **/

@RestController
@Slf4j
@RequestMapping("/baiduai")
public class UserBaiduAIController {
    @Autowired
    private UserBaiduAIService userBaiduAIService;

    @PostMapping("/login")
    public Result loginBaiduAi(@RequestParam String userName,
                               @RequestParam MultipartFile file)throws Exception {
        log.info("开始进行人脸识别：{}",userName);
        Result rs = userBaiduAIService.loginBaiduAi(userName, file);

        return rs;

    }

    @PostMapping("/register")
    public Result registerBaiduAi(@RequestParam String userName,
                                  @RequestParam MultipartFile file)throws Exception {
        log.info("开始进行人脸注册:{}",userName);
        Result rs = userBaiduAIService.registerBaiduAi(userName, file);

        return rs;

    }
}
