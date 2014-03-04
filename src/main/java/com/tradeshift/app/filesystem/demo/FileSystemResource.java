
package com.tradeshift.app.filesystem.demo;

import com.tradeshift.app.filesystem.demo.dto.FileListDTO;
import java.io.IOException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
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
    @Path("files/{path:.+}")
    public void createDirectory(@PathParam("path") String path){
        fsService.mkdirs(path);
    }

    @DELETE
    @Path("files/{path:.+}")
    public void deleteDirectory(@PathParam("path") String path){
        fsService.rmdir(path);
    }
    // move

    @POST
    @Path("file/{path:.+}")
    public void renameFile(@PathParam("path") String path, @QueryParam("renameto") String fileName){
        if (fileName == null || fileName.isEmpty()){
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        fsService.rename(path, fileName);
    }

    @PUT
    @Path("file/{path:.+}")
    public void upload(@PathParam("path") String path, byte[] data){
        try {
            fsService.create(path, data);
        } catch (IOException ex) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("file/{path:.+}")
    public Response download(@PathParam("path") String path){
        return Response.ok(fsService.read(path), MediaType.APPLICATION_OCTET_STREAM).build();
    }

    @DELETE
    @Path("file/{path:.+}")
    public void delete(@PathParam("path") String path){
        fsService.delete(path);
    }

}
