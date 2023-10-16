package com.yeogiya.web.awss3.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import com.yeogiya.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;
    public static final String CLOUD_FRONT_DOMAIN_NAME = "dh4i8qj952q7.cloudfront.net";

    public List<String> uploadFile(List<MultipartFile> multipartFiles, String dirName){
        List<String> fileUrlList = new ArrayList<>();

        multipartFiles.forEach(file -> {
            String fileName = dirName + "/" + createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()){
                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e){
                throw new ServerException.InternalServerError(EnumErrorCode.FAIL_FILE_UPLOAD);
            }
            fileUrlList.add("https://" + CLOUD_FRONT_DOMAIN_NAME + "/" + fileName);

        });

        return fileUrlList;
    }


    private String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }


    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e){
            throw new ClientException.Conflict(EnumErrorCode.INCORRECTLY_FORMATTED_FILE);
        }
    }


    public void deleteFile(String fileName){
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
