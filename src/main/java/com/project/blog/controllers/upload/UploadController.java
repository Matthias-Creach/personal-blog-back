package com.project.blog.controllers.upload;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.project.blog.models.dto.PhotographDTO;
import com.project.blog.services.impl.aws.ImageServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blog/upload")
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Value("${app.aws.s3.url}")
	private String s3Url;
	
	@Autowired
	ImageServiceImpl imageService;
	
	@PostMapping("/articles/{title}")
	public ResponseEntity<?> uploadArticleFile(@PathVariable("title") String title, @RequestBody MultipartFile upload) throws IOException{

	    String url = imageService.putImageToS3(upload, "articles", title);
	    PhotographDTO dto = new PhotographDTO();
	    dto.setUrl(url);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<?>> getAllPhotographs(@RequestParam String prefix){
		
		List<S3ObjectSummary> result = imageService.getAllImages(prefix);
		List<PhotographDTO> listPhotographsDto = new ArrayList<PhotographDTO>();
		
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/bucket/{bucket}")
	public ResponseEntity<?> createBucket(@PathVariable("bucket") String bucketName){
		
		Bucket bucket = imageService.createBucket(bucketName);
		
		return ResponseEntity.ok().body(bucket);
	}
	
	private static File[] getResourceFolderFiles (String folder) {
	    ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    URL url = loader.getResource(folder);
	    String path = url.getPath();
	    return new File(path).listFiles();
	  }
	
	private PhotographDTO convertToDto(S3ObjectSummary s3Object) {
		PhotographDTO photoDto = new PhotographDTO();
		photoDto.setUrl(s3Url + s3Object.getKey());
		return photoDto;
	}

}
