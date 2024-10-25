package com.angelldca.visuales_download.command;

import com.angelldca.visuales_download.Exceptions.ExceptionConnect;
import com.angelldca.visuales_download.Scrap.LinkExtractor;
import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.Availability;
import org.springframework.shell.AvailabilityProvider;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.shell.command.CommandRegistration;
import org.springframework.shell.command.annotation.ExceptionResolver;
import org.springframework.shell.command.annotation.ExitCode;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class Download {
    private boolean connected = false;
    private String url = "https://visuales.uclv.cu"; //http://localhost:4200 https://www.google.com

    public void connect() throws IOException  {


            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                connected = true;

            } else {
                connected = false;

            }

    }


    public List<String> desg(String url)  {
        this.url = url;
        try {
            this.connect();

            LinkExtractor linkExtractor = new LinkExtractor();

            return linkExtractor.getAllLinks(url);
        } catch (IOException e) {
            throw new RuntimeException(" No se puede acceder al sitio "+ url+ "\n compruebe su conexion",e);
        }
    }



}
