package com.msi.gestordocumental.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import com.msi.gestordocumental.entities.FileProperties;

@Service
public class FileService {
    private final Path location;

    @Autowired 
    public FileService(FileProperties properties) { // Es para guardar el file de forma local pero no se usa
        this.location = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();
        try{
            Files.createDirectories(location);
        }catch(Exception e){
            throw new RuntimeException("Directory couldnt be created.",e);
        }
    }

    public Boolean saveFile(MultipartFile file, Integer version){
        String name = StringUtils.cleanPath(file.getOriginalFilename()); // Gets file name
        File fileUpload = new File(location.toString() + '/' + name);
        String fileName = FilenameUtils.removeExtension(name);
        try{
            if(fileUpload.exists()){
                Path locationT = location.resolve(fileName+"("+version+")."+getFileExtension(name));
                Files.copy(file.getInputStream(),locationT,StandardCopyOption.REPLACE_EXISTING); // Copy file & replace existing
                   return true;
            }
            Path locationT = location.resolve(name); // Resolve paths, Location + filename
            Files.copy(file.getInputStream(),locationT,StandardCopyOption.REPLACE_EXISTING); // Copy file & replace existing
            return true; // Returns true if the file was saved successfully.
        } catch(Exception e){
            throw new RuntimeException("Could not save file "+name);
        }

    }


    public String getFileExtension(String filename){
        return FilenameUtils.getExtension(filename);
    }
}
