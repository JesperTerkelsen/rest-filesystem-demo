package com.tradeshift.app.filesystem.demo.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "File")
public class FileDTO {
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Directory")
    private boolean directory;
    @XmlElement(name = "Size")
    private long size;
    @XmlElement(name = "Lastmodified")
    private long lastmodified;

    public FileDTO() {
    }

    public FileDTO(String name, boolean directory, long size, long lastmodified) {
        this.name = name;
        this.directory = directory;
        this.size = size;
        this.lastmodified = lastmodified;
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return directory;
    }

    public long getSize() {
        return size;
    }

    public long getLastmodified() {
        return lastmodified;
    }


}
