package com.project.blog.services.impl.file_manager;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.models.entities.s3.PersonalFolder;
import com.project.blog.repositories.s3.PersonalFolderRepository;
import com.project.blog.services.itf.file_manager.PersonalFolderService;

@Service
public class PersonalFolderServiceImpl implements PersonalFolderService {

	@Autowired
	PersonalFolderRepository folderRepository;
	
	@Override
	public List<PersonalFolder> getRootFolders() {
		List<PersonalFolder> rootFolders = folderRepository.findByParent(null);
		if(rootFolders != null && !rootFolders.isEmpty()) {
			return rootFolders;
		}
		return null;
	}
	
	@Override
	public PersonalFolder getFolderByPath(String path) {
		Optional<PersonalFolder> s3Architecture = folderRepository.findById(path);
		if(s3Architecture.isPresent()) {
			return s3Architecture.get();
		}else {
			return null;
		}
	}

	@Override
	public PersonalFolder createFolder(PersonalFolder folderEntity, String parentPath) {
		
		Optional<PersonalFolder> parentFolder = folderRepository.findById(parentPath);
		if(parentFolder.isPresent()) {
			
			folderEntity.setParent(parentFolder.get());			
			
			String path = parentPath + folderEntity.getName() + "/";
			
			if(folderRepository.existsById(path)) {
				return null;	//Already exists
			}else {
				folderEntity.setPath(path);
				folderRepository.save(folderEntity);
				return getFolderByPath(folderEntity.getPath());
			}
			
		}else {
			return null;	// Parent doesn't exists
		}
		
	}

	@Override
	public void deleteFolderByPath(String path) {
		PersonalFolder folder = folderRepository.getOne(path);
		if(!folder.getChildren().isEmpty()) {
			//TODO Return Error Message
		}else if(!folder.getFiles().isEmpty()) {
			//TODO Return Error Message
		}else {
			folderRepository.deleteById(path);
		}
	}
	
}
