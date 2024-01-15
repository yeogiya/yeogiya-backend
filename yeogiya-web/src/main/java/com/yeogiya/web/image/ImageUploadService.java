package com.yeogiya.web.image;

import com.yeogiya.web.util.CustomMultipartFile;
import com.yeogiya.web.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageUploadService {

    private final S3Uploader s3Uploader;

    @Transactional
    public String upload(MultipartFile multipartFile) {
        try {
            return s3Uploader.uploadFile(multipartFile, createFileName(multipartFile.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO: 추후 커스텀 에러로 변경
        }
    }

    public String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기위해, "."의 존재 유무만 판단하였습니다.
    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e){
            throw new IllegalArgumentException("잘못된 형식의 파일입니다.");
        }
    }

    @Transactional
    public String uploadThumbnail(MultipartFile multipartFile) {
        try {
            String serverFileName = createFileName(multipartFile.getOriginalFilename());
            String thumbnailFileName = getThumbnailFileName(serverFileName);
            return s3Uploader.uploadFile(resizeAttachment(thumbnailFileName, getFileFormatName(multipartFile), multipartFile, 100, 100), thumbnailFileName);
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO: 추후 커스텀 에러로 변경
        }
    }

    private String getThumbnailFileName(String serverFileName) {
        return "s_" + serverFileName;
    }

    private MultipartFile resizeAttachment(String fileName, String fileFormatName, MultipartFile multipartFile,
                                           int targetWidth, int targetHeight) {

        try {
            // MultipartFile -> BufferedImage Convert
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());

            // 원하는 px로 Width와 Height 수정
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            // origin 이미지가 resizing될 사이즈보다 작을 경우 resizing 작업 안 함
            if (originWidth < targetWidth && originHeight < targetHeight)
                return multipartFile;

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", targetWidth);
            scale.setAttribute("newHeight", targetHeight);
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, fileFormatName, baos);
            baos.flush();

            return new CustomMultipartFile(fileName, fileName, fileFormatName, baos.toByteArray());

        } catch (IOException e) {
            // 파일 리사이징 실패시 예외 처리
            throw new IllegalArgumentException("파일 리사이징 실패");
        }
    }

    private String getFileFormatName(MultipartFile file) {
        return file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);
    }
}
