package br.com.cdb.bancodigital_api.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class H2ConsoleLinkPrinter {
    @EventListener(ApplicationReadyEvent.class)
    public void printH2ConsoleLink() {
        String url = "http://localhost:8081/h2-console";
        System.out.println("H2 Console disponível em: " + url);
    }
}
