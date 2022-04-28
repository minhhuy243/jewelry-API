package com.jewelry.api.gdrive.manager.itf;

public interface GDriveFolderManager {

    String findIdByName(String parentId, String name);

    String create(String parentId, String name);

    void deleteFolder(String folderId);
}
