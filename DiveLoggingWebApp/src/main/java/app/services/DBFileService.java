package app.services;

import org.springframework.web.multipart.MultipartFile;

import app.models.DBFile;

public interface DBFileService {
	DBFile storeFile(MultipartFile file);
	DBFile getFile(String fileId);
}
