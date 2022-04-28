package com.jewelry.api.gdrive.manager;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.jewelry.api.gdrive.manager.itf.GDriveFileManager;
import com.jewelry.api.gdrive.util.GDriveUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Service
public class GDriveFileManagerImpl implements GDriveFileManager {

    private GDriveUtils gDriveUtils;

    @Override
    public List<String> uploadFile(String folderName, List<MultipartFile> fileDatas) {
        List<String> idList = new LinkedList<>();
        try {
            for(MultipartFile fileData : fileDatas) {
                if(fileData != null) {
                    File fileMetadata = new File();
                    fileMetadata.setParents(Collections.singletonList(folderName));
                    fileMetadata.setName(fileData.getOriginalFilename());
                    File uploadFile = gDriveUtils.getInstance()
                            .files()
                            .create(fileMetadata,
                                    new InputStreamContent(fileData.getContentType(),
                                            new ByteArrayInputStream(fileData.getBytes()))
                            )
                            .setFields("id")
                            .execute();
                    Permission permission = new Permission();
                    permission.setType("anyone");
                    permission.setRole("reader");
                    gDriveUtils.getInstance().permissions().create(uploadFile.getId(), permission).execute();;
                    idList.add(uploadFile.getId());
                }
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        return idList;
    }

    @Override
    public void deleteFile(List<String> gDriveIds){
        for(String gDriveId : gDriveIds) {
            try {
                gDriveUtils.getInstance().files().delete(gDriveId).execute();
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
    }
}
