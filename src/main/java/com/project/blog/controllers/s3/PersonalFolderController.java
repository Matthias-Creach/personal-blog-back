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

import com.project.blog.models.dto.s3.PersonalFileDTO;
import com.project.blog.models.dto.s3.PersonalFolderDTO;
import com.project.blog.models.entities.s3.PersonalFile;
import com.project.blog.models.entities.s3.PersonalFolder;
import com.project.blog.services.itf.file_manager.PersonalFolderService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/blog/folders")
public class PersonalFolderController {
	
	@Autowired
	PersonalFolderService folderService;
	
	@GetMapping("/root/")
	public ResponseEntity<List<PersonalFolderDTO>> getRootFolders(){
		List<PersonalFolderDTO> rootFoldersDto = new ArrayList<>();
		
		List<PersonalFolder> rootFoldersEntity = folderService.getRootFolders();
		if(rootFoldersEntity != null) {
			for(PersonalFolder folderEntity : rootFoldersEntity) {
				rootFoldersDto.add(convertToDto(folderEntity));
			}
			return ResponseEntity.ok().body(rootFoldersDto);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/")
	public ResponseEntity<PersonalFolderDTO> getFolder(@RequestParam String path){
		PersonalFolder architecture = folderService.getFolderByPath(path);
		if(architecture != null) {
			return ResponseEntity.ok().body(convertToDto(architecture));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<PersonalFolderDTO> createFolder(@RequestBody PersonalFolderDTO folderDto){
		
		PersonalFolder entity = convertToEntity(folderDto);
		
		if(entity == null) {
			return ResponseEntity.badRequest().build();
		}else {
			PersonalFolder newS3Architecture = folderService.createFolder(entity, folderDto.getParent());
			if(newS3Architecture != null) {
				return ResponseEntity.ok().body(convertToDto(newS3Architecture));
			}else {
				return ResponseEntity.badRequest().build();
			}
		}
		
	}
	
	@DeleteMapping("/")
	public ResponseEntity<?> deleteFolder(@RequestParam String path){
		folderService.deleteFolderByPath(path);
		return ResponseEntity.ok().build();
	}
	
	/** DTO <--> Entity **/
	
	private PersonalFolderDTO convertToDto(PersonalFolder entityParent) {
		PersonalFolderDTO parentDto = new PersonalFolderDTO();
		parentDto.setName(entityParent.getName());
		parentDto.setPath(entityParent.getPath());
		parentDto.setChildren(new ArrayList<PersonalFolderDTO>());
		
		parentDto.setFiles(new ArrayList<PersonalFileDTO>());
		if(entityParent.getFiles() != null && !entityParent.getFiles().isEmpty()) {
			for(PersonalFile fileEntity : entityParent.getFiles()) {
				parentDto.getFiles().add(convertoFileToDto(fileEntity));
			}
		}
		
		if(entityParent.getChildren() != null) {
			for(PersonalFolder childEntity: entityParent.getChildren()) { 
				PersonalFolderDTO childDto = convertToDto(childEntity);
				childDto.setParent(entityParent.getName());
				parentDto.getChildren().add(childDto);
			}
		}
		
		return parentDto;
	}
	
	
	private PersonalFolder convertToEntity(PersonalFolderDTO s3ArchitectureDto) {
		PersonalFolder s3Architecture =  new PersonalFolder();
		s3Architecture.setName(s3ArchitectureDto.getName());
		s3Architecture.setChildren(new ArrayList<PersonalFolder>());
		return s3Architecture;
	}
	
	private PersonalFileDTO convertoFileToDto(PersonalFile fileEntity) {
		PersonalFileDTO fileDto = new PersonalFileDTO();
		fileDto.setName(fileEntity.getFileKey().getName());
		fileDto.setPath(fileEntity.getFileKey().getFolderPath());
		fileDto.setUrl(fileEntity.getUrl());
		return fileDto;
	}

}
