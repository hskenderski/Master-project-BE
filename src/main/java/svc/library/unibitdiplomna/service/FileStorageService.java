package svc.library.unibitdiplomna.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import svc.library.unibitdiplomna.exceptions.InvalidException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService
{
  private final Path root = Paths.get("C:\\Users\\Hristiyan\\Desktop\\master-project-FE\\src\\upload");

  public void init()
  {
    if (!Files.exists(root)) {
      try {
        Files.createDirectory(root);
      }
      catch (IOException e) {
        throw new InvalidException("Could not initialize folder for upload!");
      }
    }
  }

  public void save(MultipartFile file, Integer fileId)
  {
    try {
      Files.copy(file.getInputStream(), root.resolve(String.valueOf(fileId)));
    }
    catch (Exception e) {
      throw new InvalidException("Could not store the file. Error:" + e.getMessage());
    }
  }

  public Resource load(String filename)
  {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      }
      else {
        throw new InvalidException("Could not read the file!");
      }
    }
    catch (MalformedURLException e) {
      throw new InvalidException("Error: " + e.getMessage());
    }
  }
}
