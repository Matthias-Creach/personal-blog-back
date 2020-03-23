package com.project.blog.services.itf.file_manager;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.blog.models.entities.s3.PersonalFile;

public interface PersonalFileService {
	
	List<PersonalFile> getObjectsByFolder(String folderPath);
	
	PersonalFile addFile(MultipartFile file, String name, String path);
	
	void deleteFile(PersonalFile fileKey);
	
}
