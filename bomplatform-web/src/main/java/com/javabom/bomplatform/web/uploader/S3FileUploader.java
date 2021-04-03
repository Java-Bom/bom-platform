package com.javabom.bomplatform.web.uploader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3FileUploader implements FileUploader {

    private static final String UPLOAD_DIR = "javabom";

    private final AmazonS3Client amazonS3Client;
    private final S3BucketProperties bucketProperties;

    @Override
    public String upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new NullPointerException("Upload file does not exist");
        }
        File convertedFile = convert(file);
        return upload(convertedFile);
    }

    private File convert(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return convertFile;
        }
        throw new IllegalArgumentException(String.format("Could not convert multipartFile to File. file name: %s", file.getName()));
    }

    private String upload(File uploadFile) {
        String fileName = UPLOAD_DIR + "/" + uploadFile.getName();
        String uploadImagePath = uploadS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImagePath;
    }

    private String uploadS3(File uploadFile, String fileName) {
        String bucket = bucketProperties.getBucket();
        amazonS3Client.puObject(new PutObjectRequest(bucket, fileName, uploadFile));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            return;
        }
        log.info(String.format("Could not delete file. file name: %s", targetFile.getName()));
    }
}
