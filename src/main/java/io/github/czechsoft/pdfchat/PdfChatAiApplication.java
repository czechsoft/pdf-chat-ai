package io.github.czechsoft.pdfchat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan
@ConfigurationPropertiesScan({"io.github.czechsoft.pdfchat.configuration"})
public class PdfChatAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfChatAiApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> System.out.println("Hello ");
    }

}
