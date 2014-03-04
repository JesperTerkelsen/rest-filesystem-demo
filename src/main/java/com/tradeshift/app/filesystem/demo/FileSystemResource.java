
package com.tradeshift.app.filesystem.demo;

import com.tradeshift.app.filesystem.demo.dto.FileListDTO;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("")
public class FileSystemResource {

    private FileSystemService fsService = new FileSystemService();

    @GET
    @Path("files/")
    public FileListDTO listRoot(){
        return fsService.list();
    }

    @GET
    @Path("files/{path:.+}")
    public FileListDTO listFiles(@PathParam("path") String path){
        FileListDTO result = fsService.list(path);
        if (result == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return result;
    }

    @PUT
    @Path("file/{path:.+}")
    public void upload(@PathParam("path") String path, byte[] data){
        
    }

}
