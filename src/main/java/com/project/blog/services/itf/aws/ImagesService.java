package com.project.blog.services.itf.aws;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public interface ImagesService {
	
	//Bucket actions
	Bucket createBucket(String bucketName);
	void deleteObjectOnS3(String bucketName);

	String putImageToS3(MultipartFile file, String s3Path, String s3FileName);
	List<S3ObjectSummary> getAllImages(String prefix);
}
