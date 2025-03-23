package com.hj.service;

import com.hj.pojo.Result;
import org.springframework.web.multipart.MultipartFile;

public interface UserBaiduAIService {

    Result loginBaiduAi(String userName, MultipartFile file)throws Exception;

    Result registerBaiduAi(String userName, MultipartFile file)throws Exception;
}
