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
    private final File root = new File(System.getProperty("user.home")+"/tmp");

    public FileListDTO list(){
        return list(root.listFiles());
    }

    public FileListDTO list(String path){
        return list(new File(root, path));
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
        mkdirs(parent);
        try (OutputStream out = new FileOutputStream(file, false)) {
            out.write(bytes);
            out.flush();
        }
    }

    public void mkdirs(String path){
        mkdirs(new File(root, path));
    }

    public boolean rmdir(String path){
        return rmdir(new File(root, path));
    }

    public void rename(String path, String newFileName){
        File file = new File(root, path);
        if (!file.exists() || file.isDirectory()){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        File parent = file.getParentFile();
        File renamed = new File(parent, newFileName);
        if (renamed.exists()){
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        file.renameTo(renamed);
    }

    public boolean rmdir(File file){
        if (!file.exists()){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        if (!file.isDirectory()){
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        if (file.list().length == 0){
            return file.delete();
        }
        else {
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
    }

    public void mkdirs(File file){
        if (!file.exists()){
            file.mkdirs();
        }
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
