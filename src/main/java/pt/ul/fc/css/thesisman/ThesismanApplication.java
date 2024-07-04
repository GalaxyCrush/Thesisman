package pt.ul.fc.css.thesisman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Classe simples para verificar as tabelas das entidades e a relação entre elas
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@SpringBootApplication
public class ThesismanApplication {

  private static final Logger log = LoggerFactory.getLogger(ThesismanApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ThesismanApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo() {
    return (args) -> {
    };
  }

}
