package svc.library.unibitdiplomna;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import svc.library.unibitdiplomna.security.RsaKeyProperties;
import svc.library.unibitdiplomna.service.FileStorageService;

import javax.annotation.Resource;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class UnibitDiplomnaApplication implements CommandLineRunner
{
  @Resource
  FileStorageService storageService;

  public static void main(String[] args)
  {
    SpringApplication.run(UnibitDiplomnaApplication.class, args);
  }

  @Override
  public void run(String... args)
  {
    storageService.init();
  }
}
