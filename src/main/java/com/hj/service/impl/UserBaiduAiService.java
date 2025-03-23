package com.hj.service.impl;


import com.aliyun.oss.AliOSSUtils;
import com.baidu.aip.face.AipFace;
import com.hj.compents.CodeCompents;
import com.hj.config.BaiduAiConfig;
import com.hj.mapper.UserMapper;
import com.hj.pojo.Result;
import com.hj.pojo.User;
import com.hj.service.UserBaiduAIService;
import com.hj.utils.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import com.hj.utils.faceComparsionUtil;

import java.util.HashMap;

/**
 * @Author 胡军
 * @Version 1.0
 **/
@Slf4j
@Service
public class UserBaiduAiService implements UserBaiduAIService {


    String AppId = BaiduAiConfig.AppId;
    String AK = BaiduAiConfig.AK;
    String SK = BaiduAiConfig.SK;

    // 传入百度云的AppId、AK、SK
    AipFace client = new AipFace(AppId, AK, SK);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Override
    public Result loginBaiduAi(String userName, MultipartFile file)throws Exception {


        //图片转换成Base64
        String loginImageBase64 = Base64Util.multipartFileToBase64(file);

        User user = userMapper.findByName(userName);

        if(!ObjectUtils.isEmpty(user.getPhoto())){
            // 用户注册的人脸的照片存储路径

            String comparedImageUrl = user.getPhoto();

            //传入参数，进行人脸验证
            Double faceComparsion = faceComparsionUtil.faceComparison(client,loginImageBase64,comparedImageUrl);

            if(faceComparsion>85){
                return Result.success( user.getUsername()+"人脸识别登录成功"+"相似度:"+faceComparsion);
            }else {
                return Result.error("人脸识别登录失败") ;
            }
        }else {
            return Result.error("请先录入人脸信息") ;
        }


    }

    @Override
    public Result registerBaiduAi(String userName, MultipartFile file) throws Exception {

        User user = userMapper.findByName(userName);
        if(user!=null&&!ObjectUtils.isEmpty(user.getPhoto())){
            String comparedImageUrl = user.getPhoto();
            aliOSSUtils.delete(comparedImageUrl);
        }
        String registerImageBase64 = Base64Util.multipartFileToBase64(file);
        // 传入可选参数调用接口

        HashMap<String,String> options = new HashMap<>();
        options.put("user_id", "01");
        options.put("user_info",userName.toString());
        options.put("quality_control","NORMAL");
        options.put("liveness_control","LOW");
        options.put("action_type", "REPLACE");

        JSONObject res = client.addUser(registerImageBase64, CodeCompents.baidu_ai_imageType,CodeCompents.baidu_ai_groupId
        ,userName,options);
        System.out.println(res.toString());
        if(res.getInt("error_code")==0){
            String url = aliOSSUtils.upload(file);
            if(userMapper.findByName(userName)!=null){
                userMapper.updateUserPhoto(userName,url);
            }else {
                userMapper.addUser(userName,url);
            }
            return Result.success("人脸注册成功");
        }else {
            return Result.error("人脸注册失败");
        }



    }
}
