package com.hj.utils;


import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.hj.compents.CodeCompents;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @Author 胡军
 * @Version 1.0
 **/


public class faceComparsionUtil {

    public static Double faceComparison(AipFace client, String loginImageBase64, String comparedImageUrl) throws Exception {
        // 将图片的URL传递给百度API
        MatchRequest req2 = new MatchRequest(comparedImageUrl, "URL");
        // 将前端传过来的图片传递给百度API
        MatchRequest req1 = new MatchRequest(loginImageBase64, CodeCompents.baidu_ai_imageType);
        // 讲MatchRequest信息存入list集合中
        ArrayList<MatchRequest> requests = new ArrayList<>();
        requests.add(req1);
        requests.add(req2);

        // 进行人脸比对 返回值是json串
        JSONObject match = client.match(requests);
        System.out.println(match.toString(2));
        // 返回两张照片的相似度
        return match.getJSONObject("result").getDouble("score");
    }

}
