package com.angelldca.visuales_download.command;

import com.angelldca.visuales_download.Exceptions.ExceptionConnect;
import com.angelldca.visuales_download.Scrap.LinkExtractor;
import com.angelldca.visuales_download.Terminal.AnsiColors;
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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static ch.qos.logback.core.pattern.color.ANSIConstants.GREEN_FG;
import static ch.qos.logback.core.pattern.color.ANSIConstants.RESET;

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
        String saveDirectory = System.getProperty("user.home") + "/Downloads/visuales/";
        try {
            this.connect();

            LinkExtractor linkExtractor = new LinkExtractor();
            List<String> links =  linkExtractor.getAllLinks(url);
            if (!links.isEmpty()) {
                for (String link : links) {
                    String fileName = URLDecoder.decode(link.substring(link.lastIndexOf("/") + 1), StandardCharsets.UTF_8.name());
                    this.downloadFile(link, saveDirectory+fileName);
                }
            } else {
                System.out.println(AnsiColors.RED+"No video or subtitle links found.");
                System.out.println(AnsiColors.RESET);
            }
            return linkExtractor.getAllLinks(url);
        } catch (IOException e) {
            throw new RuntimeException(" No se puede acceder al sitio "+ url+ "\n compruebe su conexion",e);
        }
    }

    public void downloadFile(String fileUrl, String saveDir) {

        File dir = new File(saveDir).getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();  //
            System.out.println("Directory created: " + dir.getAbsolutePath());
        }
        try (InputStream inputStream = new URL(fileUrl).openStream();
             FileOutputStream outputStream = new FileOutputStream(saveDir)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            System.out.println("Descargando: " + fileUrl);
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println(AnsiColors.GREEN +"Download completed: " + saveDir);
            System.out.println(AnsiColors.RESET);
        } catch (IOException e) {
            System.err.println(AnsiColors.RED +"Error downloading the file: " + e.getMessage());
            System.out.println(AnsiColors.RESET);
        }
    }



}
