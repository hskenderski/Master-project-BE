package svc.library.unibitdiplomna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import svc.library.unibitdiplomna.security.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class UnibitDiplomnaApplication
{

  public static void main(String[] args)
  {
    SpringApplication.run(UnibitDiplomnaApplication.class, args);
  }

}
