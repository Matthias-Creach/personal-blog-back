package com.project.blog.repositories.s3;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.models.entities.s3.PersonalFile;
import com.project.blog.models.entities.s3.PersonalFileKey;
import com.project.blog.models.entities.s3.PersonalFolder;

public interface PersonalFileRepository extends JpaRepository<PersonalFile, String> {
	PersonalFile findByFileKey(PersonalFileKey fileKey);

	List<PersonalFile> findByFolder(PersonalFolder folder);
}
