package com.daon.onjung.core.utility;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.security.domain.type.EImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class S3Util {
    private final AmazonS3Client amazonS3Client;

    private final String IMAGE_CONTENT_PREFIX = "image/";

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.url}")
    private String bucketUrl;

    public String uploadImageFile(MultipartFile file, String id, EImageType eImageType) {
        try {
            String fileName = UUID.randomUUID().toString();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            String key = switch (eImageType) {
                case BANNER_IMG -> "account_" + id + "/banner_" + fileName;
                case LOGO_IMG -> "account_" + id + "/logo_" + fileName;
                case COMPANY_IMG -> "company_" + id + "/image_" + fileName;
                case BOARD_IMG -> "board_" + id + "/image_" + fileName;
            };

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), objectMetadata);

            amazonS3Client.putObject(putObjectRequest);

            return bucketUrl + key;
        } catch (SdkClientException | IOException e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
    }

}
