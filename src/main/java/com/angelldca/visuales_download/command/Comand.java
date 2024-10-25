package com.angelldca.visuales_download.command;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.command.CommandRegistration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Comand {
    @Autowired
    private Download download;

    @Bean
    CommandRegistration commandRegistration() {
        return  CommandRegistration.builder()
                .command("download")
                .withOption()
                .longNames("arg")
                .required()
                .type(String.class)
                .position(0)
                .and()
                .withTarget()
                .function(ctx -> {
                    String arg = ctx.getOptionValue("arg");
                    List<String> links = download.desg(arg);
                    for (String link : links) {
                        System.out.println(link);
                    }

                    return "\u001B[32m" + " Descarga completada";
                })
                .and()
                .build();
    }


}
