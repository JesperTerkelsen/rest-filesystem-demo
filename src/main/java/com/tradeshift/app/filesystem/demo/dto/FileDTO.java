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

    public FileDTO() {
    }

    public FileDTO(String name, boolean directory) {
        this.name = name;
        this.directory = directory;
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return directory;
    }


}
