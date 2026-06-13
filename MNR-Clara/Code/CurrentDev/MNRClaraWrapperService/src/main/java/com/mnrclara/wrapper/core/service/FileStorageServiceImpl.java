package com.mnrclara.wrapper.core.service;

import com.mnrclara.wrapper.core.config.PropertiesConfig;
import com.mnrclara.wrapper.core.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileStorageServiceImpl implements MultipleFileStorageService {

    @Autowired
    PropertiesConfig propertiesConfig;

    private Path fileStorageLocation = null;

    private final Path root = Paths.get("uploads");


    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            // Normalize file name
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            log.info("filename before: " + fileName);
            fileName = fileName.replace(" ", "_");
            log.info("filename after: " + fileName);

            String locationPath = propertiesConfig.getDocStorageBasePath() + "/" + root;
            this.fileStorageLocation = Paths.get(locationPath).toAbsolutePath().normalize();

            if (!Files.exists(fileStorageLocation)) {
                try {
                    Files.createDirectories(this.fileStorageLocation);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new BadRequestException("Could not create the directory where the uploaded files will be stored.");
                }
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

//            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
