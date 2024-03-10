package enset.ma.thymeleaf;

import ch.qos.logback.core.encoder.EncoderBase;
import enset.ma.thymeleaf.entities.Patient;
import enset.ma.thymeleaf.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class ThymeleafApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(ThymeleafApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Patient patient1 = new Patient();
        patient1.setId(null);
        patient1.setNom("OUBALI");
        patient1.setDateNaissance(new Date());
        patient1.setMalade(true);
        patient1.setScore(300);

        Patient patient2 = new Patient(null,"HAFDE",new Date(),false,123);

        Patient patient3 = Patient.builder()
                .nom("HASSOUNI")
                .dateNaissance(new Date())
                .malade(true)
                .score(200)
                .build();

        //patientRepository.save(patient1);
        //patientRepository.save(patient2);
        //patientRepository.save(patient3);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
