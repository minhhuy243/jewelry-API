package com.jewelry.api.gdrive.controller;

import com.jewelry.api.gdrive.manager.itf.GDriveFileManager;
import com.jewelry.api.gdrive.manager.itf.GDriveFolderManager;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/api/gdrive")
public class GDriveController {

    private GDriveFileManager gDriveFileManager;

    private GDriveFolderManager gDriveFolderService;

    @PostMapping(value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> uploadFile(@RequestParam(value = "files[]") MultipartFile[] files) {
        //String folderId = gDriveFolderService.findIdByName("", "Sản phẩm/Sản phẩm 01");
        //System.out.println("ten: " + test123.getName());
        System.out.println("file: " + files[0] + files[1]);
        //System.out.println("folder id: " + folderId);
        //List<String> fileId = gDriveFileManager.uploadFile(folderId, test123.getFiles());

        return ResponseEntity.ok("File is uploaded!");
    }
}
