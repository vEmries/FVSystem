package App.Config;

import App.Service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:Config/FVSystemConfig.xml")
public class BasicConfig {

    @Bean
    AuthenticationConfig loginConfig() { return new AuthenticationConfig(); }

    @Bean
    FVService fvService() {
        return new FVService();
    }

    @Bean
    PaymentService paymentService() {
        return new PaymentService();
    }

    @Bean
    ContractorService contractorService() {
        return new ContractorService();
    }

    @Bean
    ArchiveService archiveService() {
        return new ArchiveService();
    }

    @Bean
    UserService userService() { return new UserService(); }
}