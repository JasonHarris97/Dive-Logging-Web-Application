package app.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, 
    		@RequestParam("diveId") long diveId, @RequestParam("fileUse") String fileUse, 
    			Principal principal) {
        DBFile dbFile = dbFileService.generateDBFile(file);
        
        User currentUser = userService.findByUsername(principal.getName());
        dbFile.setFileOwner(currentUser);
        dbFile.setFileUse(fileUse);
        
        if(fileUse.equals("diveImage")) {
        	dbFile.setAssociatedDive(diveService.findById(diveId));
        }
        
        dbFileService.saveDBFile(dbFile);
        userService.save(currentUser);

        return new UploadFileResponse(dbFile.getFileName(),file.getContentType(), file.getSize());
    }
	
	@RequestMapping(value = {"/uploadSingleFile"}, method = RequestMethod.POST)
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, 
    		@RequestParam("fileUse") String fileUse, Principal principal) {
        DBFile dbFile = dbFileService.generateDBFile(file);
        
        User currentUser = userService.findByUsername(principal.getName());
        dbFile.setFileOwner(currentUser);
        dbFile.setFileUse(fileUse);
        
        if(fileUse.equals("profilePicture")) {
        	currentUser.setProfilePicture(dbFile);
        } else if(fileUse.equals("profileBanner")) {
        	currentUser.setProfileBanner(dbFile);
        }
        
        dbFileService.saveDBFile(dbFile);
        userService.save(currentUser);

        return new UploadFileResponse(dbFile.getFileName(),file.getContentType(), file.getSize());
    }

    @RequestMapping(value = {"/uploadMultipleFiles"}, method = RequestMethod.POST)
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, 
    		@RequestParam("diveId") String diveId, @RequestParam("fileUse") String fileUse,
    			Principal principal) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, Long.parseLong(diveId), fileUse, principal))
                .collect(Collectors.toList());
    }
    
    @RequestMapping(value = {"/getImage/{id}"}, method = RequestMethod.GET)
    public void getImage(@PathVariable("id") Long id, HttpServletResponse response, 
    		HttpServletRequest request) throws ServletException, IOException {
    	DBFile file = dbFileService.findById(id);
    	response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(file.getData());

        response.getOutputStream().close();
    }
	

}
