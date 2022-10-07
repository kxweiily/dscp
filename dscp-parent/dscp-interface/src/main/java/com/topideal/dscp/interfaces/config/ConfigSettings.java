package com.topideal.dscp.interfaces.config;

import org.springframework.context.annotation.Configuration;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;

/**
 * 公共配置获取工具类
 * (从nacos上获取配置)
 * kongxj
 */
@Configuration
@EnableNacosConfig
public class ConfigSettings {

    /* --------  AWS S3  -------- */
    // S3公钥
    private static String awsS3AccessKey;

    // S3秘钥
    private static String awsS3SecretKey;

    // S3上传路径
    private static String awsS3UploadPath;

    // S3 文档/旧图片 存储桶
    private static String awsS3FileBucket;

    // S3 图片存储桶 存储桶
    private static String awsS3ImgBucket;

    // S3 文档/旧图片 访问域名
    private static String awsS3FileRealmUrl;

    // S3 文档/旧图片 访问域名
    private static String awsS3ImgRealmUrl;


    /* --------  玄武  -------- */
    // 玄武短信 访问域名
    private static String xuanwuUrl;

    // 玄武短信 用户名
    private static String xuanwuUserName;

    // 玄武短信 密码
    private static String xuanwuPassword;



    public static String getAwsS3AccessKey() {
        return awsS3AccessKey;
    }

    @NacosValue(value = "${aws.s3.accessKey:}", autoRefreshed = true)
    public void setAwsS3AccessKey(String awsS3AccessKey) {
        this.awsS3AccessKey = awsS3AccessKey;
    }

    public static String getAwsS3SecretKey() {
        return awsS3SecretKey;
    }

    @NacosValue(value = "${aws.s3.secretKey:}", autoRefreshed = true)
    public void setAwsS3SecretKey(String awsS3SecretKey) {
        this.awsS3SecretKey = awsS3SecretKey;
    }

    public static String getAwsS3UploadPath() {
        return awsS3UploadPath;
    }

    @NacosValue(value = "${aws.s3.uploadPath:}", autoRefreshed = true)
    public void setAwsS3UploadPath(String awsS3UploadPath) {
        this.awsS3UploadPath = awsS3UploadPath;
    }

    public static String getAwsS3FileBucket() {
        return awsS3FileBucket;
    }

    @NacosValue(value = "${aws.s3.file.bucket:}", autoRefreshed = true)
    public void setAwsS3FileBucket(String awsS3FileBucket) {
        this.awsS3FileBucket = awsS3FileBucket;
    }

    public static String getAwsS3ImgBucket() {
        return awsS3ImgBucket;
    }

    @NacosValue(value = "${aws.s3.img.bucket:}", autoRefreshed = true)
    public void setAwsS3ImgBucket(String awsS3ImgBucket) {
        this.awsS3ImgBucket = awsS3ImgBucket;
    }

    public static String getAwsS3FileRealmUrl() {
        return awsS3FileRealmUrl;
    }

    @NacosValue(value = "${aws.s3.file.realmUrl:}", autoRefreshed = true)
    public void setAwsS3FileRealmUrl(String awsS3FileRealmUrl) {
        this.awsS3FileRealmUrl = awsS3FileRealmUrl;
    }

    public static String getAwsS3ImgRealmUrl() {
        return awsS3ImgRealmUrl;
    }


    @NacosValue(value = "${aws.s3.img.realmUrl:}", autoRefreshed = true)
    public void setAwsS3ImgRealmUrl(String awsS3ImgRealmUrl) {
        this.awsS3ImgRealmUrl = awsS3ImgRealmUrl;
    }


    public static String getXuanwuUrl() {
        return xuanwuUrl;
    }

    @NacosValue(value = "${xuanwu.url:}", autoRefreshed = true)
    public void setXuanwuUrl(String xuanwuUrl) {
        this.xuanwuUrl = xuanwuUrl;
    }

    public static String getXuanwuUserName() {
        return xuanwuUserName;
    }

    @NacosValue(value = "${xuanwu.userName:}", autoRefreshed = true)
    public void setXuanwuUserName(String xuanwuUserName) {
        this.xuanwuUserName = xuanwuUserName;
    }

    public static String getXuanwuPassword() {
        return xuanwuPassword;
    }

    @NacosValue(value = "${xuanwu.password:}", autoRefreshed = true)
    public void setXuanwuPassword(String xuanwuPassword) {
        this.xuanwuPassword = xuanwuPassword;
    }

}
