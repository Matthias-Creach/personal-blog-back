package com.project.blog.services.impl.aws;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.project.blog.services.itf.aws.ImagesService;

@Service
public class ImageServiceImpl implements ImagesService {
	
	private final static Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
	private final static String BUCKET_NAME = "blog-manon";
	private static final String REGION = "eu-west-3";
	
	@Value("${server.home.directory}")
	private String SERVER_HOME_DIRECTORY;
	
	private String AWS_S3_URL = "https://blog-manon.s3.eu-west-3.amazonaws.com/";
	
	@Override
	public List<S3ObjectSummary> getAllImages(String prefix) {
		
		logger.info("Object in S3 bucket %s: ", BUCKET_NAME);
		
		AmazonS3 s3 = getAmazonClient();
		
		ListObjectsV2Request req = new ListObjectsV2Request()
				.withBucketName(BUCKET_NAME)
				.withPrefix(prefix)
				.withStartAfter(prefix)
				.withMaxKeys(3);
		
		ListObjectsV2Result result = s3.listObjectsV2(req);
		
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		
		for(S3ObjectSummary object : objects) {
			logger.info("* " + object.getKey());
		}
		
		return objects;
	}

	/**
	 * users -> {username} -> profile.png
	 * articles -> {title} -> ... .png
	 * photographs -> YYYY/MM -> *.png
	 */
	@Override
	public String putImageToS3(MultipartFile file, String s3FolderKey, String s3FolderName) {
		
		//s3Path (articles - users - photographs
		String s3Key = s3FolderKey  + s3FolderName;
		logger.info("Uploading %s to S3 bucket %s...\n", s3Key, BUCKET_NAME);
		
		try {
			String location = SERVER_HOME_DIRECTORY + file.getOriginalFilename();
			File localFile = new File(location);
		    file.transferTo(localFile);
		    
		    AmazonS3 s3 = getAmazonClient();
		    
		    PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, s3Key, new File(location));
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
            s3.putObject(putObjectRequest); // upload file

		} catch (AmazonServiceException e) {
		    logger.error(e.getErrorMessage());
		    System.exit(1);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return AWS_S3_URL + s3Key;
	}

	@Override
	public Bucket createBucket(String bucketName) {
		Bucket b = null;
		AmazonS3 s3 = getAmazonClient();
		if (s3.doesBucketExistV2(bucketName)) {
		    System.out.format("Bucket %s already exists.\n", bucketName);
		    b = getBucket(bucketName);
		} else {
		    try {
		        b = s3.createBucket(BUCKET_NAME + "/" + bucketName);
		    } catch (AmazonS3Exception e) {
		        logger.error(e.getErrorMessage());
		    }
		}
		return b;
	}

	@Override
	public void deleteObjectOnS3(String objectPath) {
		AmazonS3 s3 =  getAmazonClient();
		try {
		    s3.deleteObject(BUCKET_NAME, objectPath);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
	}
	
	private Bucket getBucket(String bucketName) {
        AmazonS3 s3 = getAmazonClient();
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucketName)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }
	
	private AmazonS3 getAmazonClient() {
		AWSCredentialsProvider awsCredentials = DefaultAWSCredentialsProviderChain.getInstance();
		final AmazonS3 s3 = AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials.getCredentials()))
				.withRegion(REGION)
				.build();
		return s3;
	}

}
