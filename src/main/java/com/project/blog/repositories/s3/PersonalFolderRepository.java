package com.project.blog.repositories.s3;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.models.entities.s3.PersonalFolder;

public interface PersonalFolderRepository extends JpaRepository<PersonalFolder, String> {
	
	List<PersonalFolder> findByParent(PersonalFolder parent);

}
