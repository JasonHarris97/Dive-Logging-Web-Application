package app.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.models.DBFile;
import app.models.User;
import app.services.DBFileService;
import app.services.DiveService;
import app.services.UserService;
import app.web.UploadFileResponse;

@RestController
public class DBFileController {
	
	@Autowired
    private DBFileService dbFileService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DiveService diveService;
	
    public UploadFileResponse uploadFile(MultipartFile file, long diveId, Principal principal) {
        DBFile dbFile = dbFileService.generateDBFile(file);
        
        User currentUser = userService.findByUsername(principal.getName());
        dbFile.setFileOwner(currentUser);
        dbFile.setAssociatedDive(diveService.findById(diveId));
        dbFileService.saveDBFile(dbFile);

        return new UploadFileResponse(dbFile.getFileName(),file.getContentType(), file.getSize());
    }

    @RequestMapping(value = {"/uploadMultipleFiles"}, method = RequestMethod.POST)
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, 
    		@RequestParam("diveId") String diveId, Principal principal) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, Long.parseLong(diveId), principal))
                .collect(Collectors.toList());
    }
	

}
