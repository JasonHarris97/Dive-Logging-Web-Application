package app.services;

import org.springframework.web.multipart.MultipartFile;

import app.models.DBFile;

public interface DBFileService {
	DBFile generateDBFile(MultipartFile file);
	void saveDBFile(DBFile file);
	
	DBFile storeFile(MultipartFile file);
	DBFile getFile(String fileId);
	DBFile findById(Long id);
}
