package com.project.blog.controllers.s3;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.blog.models.dto.s3.PersonalFileDTO;
import com.project.blog.models.entities.s3.PersonalFile;
import com.project.blog.models.entities.s3.PersonalFileKey;
import com.project.blog.services.impl.aws.s3.PersonalFileServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blog/files")
public class PersonalFileController {
	
	@Autowired
	private PersonalFileServiceImpl fileService;
	
	@GetMapping("/folder/")
	public ResponseEntity<List<PersonalFileDTO>> getFilesByFolder(@RequestParam String folderPath){
		
		List<PersonalFileDTO> listFilesDto = new ArrayList<PersonalFileDTO>();
				
		List<PersonalFile> listFilesEntity = fileService.getObjectsByFolder(folderPath);
		
		if(listFilesEntity != null && !listFilesEntity.isEmpty()) {
			for(PersonalFile fileEntity : listFilesEntity) {
				listFilesDto.add(convertoFileToDto(fileEntity));
			}
			return ResponseEntity.ok().body(listFilesDto);
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/")
	public ResponseEntity<?> addFile(MultipartFile upload, @RequestParam String path) {
		if(upload != null) {
			String name = upload.getOriginalFilename();
			PersonalFile newFile = fileService.addFile(upload, name, path);
			if(newFile != null) {
				return ResponseEntity.ok().body(convertoFileToDto(newFile));
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/")
	public ResponseEntity<?> deleteFile(@RequestBody PersonalFileDTO fileDto){
		if(fileDto != null) {
			fileService.deleteFile(convertFileToEntity(fileDto));
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	private PersonalFileDTO convertoFileToDto(PersonalFile fileEntity) {
		PersonalFileDTO fileDto = new PersonalFileDTO();
		fileDto.setName(fileEntity.getFileKey().getName());
		fileDto.setPath(fileEntity.getFileKey().getFolderPath());
		fileDto.setUrl(fileEntity.getUrl());
		return fileDto;
	}
	
	private PersonalFile convertFileToEntity(PersonalFileDTO fileDto) {
		PersonalFile fileEntity = new PersonalFile();
		PersonalFileKey entityKey = new PersonalFileKey(fileDto.getName(), fileDto.getPath());
		fileEntity.setFileKey(entityKey);
		fileEntity.setUrl(fileDto.getUrl());
		return fileEntity;
	}

}
