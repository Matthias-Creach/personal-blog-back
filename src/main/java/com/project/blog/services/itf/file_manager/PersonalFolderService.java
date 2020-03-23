package com.project.blog.services.itf.file_manager;

import java.util.List;

import com.project.blog.models.entities.s3.PersonalFolder;

public interface PersonalFolderService{
	
	List<PersonalFolder> getRootFolders();
	PersonalFolder getFolderByPath(String path);
	PersonalFolder createFolder(PersonalFolder s3Architecture, String parentPath);
	void deleteFolderByPath(String path);
	
}
