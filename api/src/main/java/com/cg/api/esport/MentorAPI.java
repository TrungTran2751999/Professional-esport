package com.cg.api.esport;

import com.cg.exception.DataInputException;
import com.cg.utils.driver.GoogleDriveConfig;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/api/mentor")
public class MentorAPI {
    @Autowired
    private GoogleDriveConfig googleDriveConfig;
    @PostMapping("/upload/video")
    public ResponseEntity<?> uploadVideo(MultipartFile file) throws IOException {
        try {
            if (file != null) {

                File fileMetadata = new File();
//                fileMetadata.setParents(Collections.singletonList(folderId));
                fileMetadata.setName(file.getOriginalFilename());
                File uploadFile = googleDriveConfig.getInstance()
                        .files()
                        .create(fileMetadata, new InputStreamContent(
                                file.getContentType(),
                                new ByteArrayInputStream(file.getBytes()))
                        )
                        .setFields("id").execute();

//                if (!type.equals("private") && !role.equals("private")){
//                    // Call Set Permission drive
                    googleDriveConfig.getInstance().permissions().create(uploadFile.getId(), setPermission("anyone", "reader")).execute();
//                }

                return new ResponseEntity<>(uploadFile.getId(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>("FAILED", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataInputException("FAIL");
        }
    }
    private Permission setPermission(String type, String role){
        Permission permission = new Permission();
        permission.setType(type);
        permission.setRole(role);
        return permission;
    }
}
