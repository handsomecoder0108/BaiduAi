package com.hj.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 胡军
 * @Version 1.0
 **/

@Configuration
public class BaiduAiConfig {


    public static final String AppId="***";

    public static final String AK="****" ;

    public static  final String SK="****" ;
}
