package com.project.blog.services.impl.file_manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.blog.models.entities.s3.PersonalFile;
import com.project.blog.models.entities.s3.PersonalFileKey;
import com.project.blog.models.entities.s3.PersonalFolder;
import com.project.blog.repositories.s3.PersonalFileRepository;
import com.project.blog.services.impl.aws.PersonalS3ServiceImpl;
import com.project.blog.services.itf.file_manager.PersonalFileService;
import com.project.blog.services.itf.file_manager.PersonalFolderService;

@Service
public class PersonalFileServiceImpl implements PersonalFileService {
	
	@Autowired
	private PersonalFileRepository fileRepository;
	
	@Autowired
	private PersonalFolderService folderService;
	
	@Autowired
	private PersonalS3ServiceImpl imageService;

	@Override
	public List<PersonalFile> getObjectsByFolder(String folderPath) {
		
		PersonalFolder folder = folderService.getFolderByPath(folderPath);
		if(folder != null) {
			return fileRepository.findByFolder(folder);
		}
		return null;
	}

	@Override
	public PersonalFile addFile(MultipartFile file, String name, String path) {
		PersonalFile newFile = new PersonalFile();
		PersonalFileKey fileKey = new PersonalFileKey(name, path);
		newFile.setFileKey(fileKey);
		
		PersonalFolder folder = folderService.getFolderByPath(path);
		if(folder != null) {
			
			newFile.setFolder(folder);
			String url = imageService.putImageToS3(file, path, name);
			if(url != null) {
				newFile.setUrl(url);
			}
			
			PersonalFile test = fileRepository.save(newFile);
			return test;
		}
		return null;
	}

	@Override
	public void deleteFile(PersonalFile fileKey) {
		
		// Delete on S3
		String objectPath = fileKey.getFileKey().getFolderPath() + fileKey.getFileKey().getName();
		imageService.deleteObjectOnS3(objectPath);
		
		// Delete on database
		fileRepository.delete(fileKey);
	}

}
