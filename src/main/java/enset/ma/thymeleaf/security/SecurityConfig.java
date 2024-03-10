package enset.ma.thymeleaf.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private PasswordEncoder passwordEncoder;

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        return new InMemoryUserDetailsManager(
                User.builder().username("amine").password(passwordEncoder.encode("1234")).roles("USER").build(),
                //User.builder().username("admin").password("{noop}1234").roles("USER","ADMIN").build()
                User.builder().username("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
        );
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin().loginPage("/login").permitAll();
        httpSecurity.rememberMe();
        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**").permitAll();
        //httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        //httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
        return httpSecurity.build();
    }
}
