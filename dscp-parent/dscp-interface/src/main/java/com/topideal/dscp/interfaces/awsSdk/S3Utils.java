package com.topideal.dscp.interfaces.awsSdk;


import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.util.CodeGeneratorUtils;
import com.topideal.dscp.interfaces.config.ConfigSettings;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * AWS S3 工具类
 * @Author: kongxj
 * @Date: 2021/11/9 15:39
 */
public class S3Utils {

    private static final Logger log= LoggerFactory.getLogger(S3Utils.class);

    private static final String PATH_SEPARATOR = "/";

    /**
     * S3Client
     */
    private static S3Client s3Client;

    // 创建 S3Client
    private static S3Client getS3Client() {
        if (s3Client == null) {
            // Create the S3Client object
            Region region = Region.CN_NORTHWEST_1;
            s3Client = S3Client.builder().credentialsProvider(() -> new AwsCredentials() {
                @Override
                public String accessKeyId() {
                    return ConfigSettings.getAwsS3AccessKey();
                }

                @Override
                public String secretAccessKey() {
                    return ConfigSettings.getAwsS3SecretKey();
                }
            }).region(region).build();
        }
        return s3Client;
    }

    /**
     * 上传 文档
     * @param inputstream               输入流
     * @param fileSuffix                文件名称后缀
     * @return
     */
    public static String uploadFile(InputStream inputstream, String fileSuffix) {
        // 自生成文件名称(保持唯一性)
        String fileName = CodeGeneratorUtils.generateFileKey();
        if (StringUtils.isBlank(fileSuffix)) {
            throw new BizException("上传文档失败 文档后缀为空！");
        }
        fileName = fileName + "." + fileSuffix;

        return upload(inputstream, fileName, Boolean.FALSE);
    }

    /**
     * 上传 图片
     * @param inputstream               输入流
     * @param fileSuffix                文件名称后缀
     * @return
     */
    public static String uploadImg(InputStream inputstream, String fileSuffix) {
        // 自生成文件名称(保持唯一性)
        String fileName = CodeGeneratorUtils.generateFileKey();
        if (StringUtils.isBlank(fileSuffix)) {
            fileName = fileName + ".jpg";
        } else {
            fileName = fileName + "." + fileSuffix;
        }

        return upload(inputstream, fileName, Boolean.TRUE);
    }


    private static String upload(InputStream inputstream, String fileName, Boolean isPic) {

        // 图片 / 文档 访问域名
        String realmUrl = BooleanUtils.isTrue(isPic) ? ConfigSettings.getAwsS3ImgRealmUrl() : ConfigSettings.getAwsS3FileRealmUrl();
        // 图片 / 文档  存储桶名
        String bucketName = BooleanUtils.isTrue(isPic) ? ConfigSettings.getAwsS3ImgBucket() : ConfigSettings.getAwsS3FileBucket();
        // acl 图片是私有域
        ObjectCannedACL acl = BooleanUtils.isTrue(isPic) ? ObjectCannedACL.PRIVATE : ObjectCannedACL.PUBLIC_READ_WRITE;

        // 拼接请求路径  目录地址/文件名
        String objectKey = ConfigSettings.getAwsS3UploadPath() + PATH_SEPARATOR + fileName;
        // 拼接返回路径  访问域名/目录地址/文件名
        String returnUrl = realmUrl + PATH_SEPARATOR + objectKey;

        try {
            //Put a file into the bucket
            PutObjectResponse response = getS3Client().putObject(PutObjectRequest.builder().acl(acl)
                            .bucket(bucketName)
                            .key(ConfigSettings.getAwsS3UploadPath() + PATH_SEPARATOR + fileName)
                            .build(),
                    RequestBody.fromBytes(getByteArray(inputstream)));

            return response.sdkHttpResponse().isSuccessful() ? returnUrl : null;
        } catch (S3Exception e) {
            log.error(e.getMessage());
            throw new BizException(e.getMessage());
        }
    }

    private static byte[] getByteArray(InputStream is) {
        byte[] bytesArray = new byte[1024];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            int n = 0;
            while (-1 != (n = is.read(bytesArray))) {
                output.write(bytesArray, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    output.close();
                }
            } catch (IOException e) {
                log.warn("Unable to close InputStream :", e);
            }
        }
        return output.toByteArray();
    }

}
