package com.lazycoder.account;

import com.lazycoder.account.dto.AccountContactRecordInfo;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountContactRecordInfo.class})
@OpenAPIDefinition(
        info = @Info(title = "Accounts Microservice REST API documentation",
        description = "LazyCoder Accounts Microservice REST API documentation",
        version = "v1",
        contact = @Contact (
                name = "Abhishek",
                email = "learning@lazycoder.com",
                url = "http://www.lazycoder.com")
        ),
        externalDocs = @ExternalDocumentation(
                description = "LazyCoder Accounts Microservice Wiki Documentation",
                url = "http://www.lazycoder.com/wiki"
        )
)
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

}
