package com.project.blog.controllers.s3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.models.entities.s3.PersonalFile;
import com.project.blog.models.entities.s3.PersonalFolder;
import com.project.blog.services.impl.aws.ImageServiceImpl;
import com.project.blog.services.itf.aws.s3.PersonalFolderService;

@CrossOrigin(origins = "*", maxAge = 3600 )
@RestController
@RequestMapping("/blog/file-manager/")
public class FileManagerController {
	
	private final static Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
	
	@Autowired
	private PersonalFolderService folderService;
	
	@Value("${server.home.directory}")
	private String SERVER_PATH;
	
	@GetMapping("/sync/")
	public ResponseEntity<?> synchroniseS3TOServer(){
		
		List<PersonalFolder> listFolders = folderService.getRootFolders();
		createFolder(listFolders);
		return ResponseEntity.ok().build();
		
	}
	
	private void createFolder(List<PersonalFolder> folders) {
		
		if(folders != null && folders.size() > 0) {
			for(PersonalFolder folder : folders) {
				String path = SERVER_PATH + folder.getPath();
				File file = new File(path);
				
				if(file.mkdir()) {
					logger.info("Create folder: %s", path);
				}else {
					logger.info("Cannot create folder: %s", path);
				}
				
				createFolder(folder.getChildren().stream().collect(Collectors.toList()));
				createFiles(path, folder.getFiles().stream().collect(Collectors.toList()));
			}
		}
	}
	
	private void createFiles(String pathParent, List<PersonalFile> files) {
		if(files != null && files.size() > 0) {
			for(PersonalFile file : files) {
				String path = pathParent + file.getFileKey().getName();
				
				try {
					URL url = new URL(file.getUrl());
					BufferedImage img = ImageIO.read(url);
					
					String[] splitFile = file.getFileKey().getName().split("\\.");
					String ext = splitFile[splitFile.length - 1];
					
					File f = new File(path);
					ImageIO.write(img, ext, f);
					
					if(f.exists()) {
						logger.info("Create File : " + f.getAbsolutePath());
					}
					
				} catch (MalformedURLException e) {
					logger.error(e.getMessage());
				} catch (IOException e) {
					logger.error(e.getMessage());
				}

			}
		}
	}
}
