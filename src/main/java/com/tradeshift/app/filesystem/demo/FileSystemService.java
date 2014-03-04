package com.tradeshift.app.filesystem.demo;

import com.tradeshift.app.filesystem.demo.dto.FileDTO;
import com.tradeshift.app.filesystem.demo.dto.FileListDTO;
import java.io.File;
import java.util.ArrayList;

public class FileSystemService {
    private final File root = new File(System.getProperty("user.home"));

    public FileListDTO list(){
        return list(root.listFiles());
    }

    public FileListDTO list(String path){
        return list(new File(root, path));
    }

    private FileListDTO list(File file){
        if (file.exists() && file.isDirectory()){
            return list(file.listFiles());
        }
        else {
            return null;
        }
    }

    private FileListDTO list(File[] files){
        ArrayList<FileDTO> result = new ArrayList<>();
        for (File file : files) {
            result.add(convert(file));
        }
        FileListDTO fileListDTO = new FileListDTO();
        fileListDTO.setFiles(result);
        return fileListDTO;
    }

    private FileDTO convert(File file){
        return new FileDTO(file.getName(), file.isDirectory());
    }
}
