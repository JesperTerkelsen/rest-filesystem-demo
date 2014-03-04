package com.tradeshift.app.filesystem.demo;

import com.tradeshift.app.filesystem.demo.dto.FileDTO;
import com.tradeshift.app.filesystem.demo.dto.FileListDTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class FileSystemService {
    private final File root = new File(System.getProperty("user.home"));

    public FileListDTO list(){
        return list(root.listFiles());
    }

    public boolean delete(String path){
        File file = new File(root, path);
        if (!file.exists() || file.isDirectory()){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return file.delete();
    }

    public InputStream read(String path){
        File file = new File(root, path);
        if (!file.exists() || file.isDirectory()){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    public void create(String path, byte[] bytes) throws IOException {
        File file = new File(root, path);
        if (file.exists() && file.isDirectory()){
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        File parent = file.getParentFile();
        if (!parent.exists()){
            parent.mkdirs();
        }
        try (OutputStream out = new FileOutputStream(file, false)) {
            out.write(bytes);
            out.flush();
        }
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
