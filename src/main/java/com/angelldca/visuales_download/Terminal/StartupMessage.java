package com.angelldca.visuales_download.Terminal;

import jakarta.annotation.PostConstruct;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;

import java.io.PrintWriter;


@ShellComponent
public class StartupMessage {

    private final Terminal terminal;

    @Autowired
    public StartupMessage(Terminal terminal) {
        this.terminal = terminal;
    }

    @PostConstruct
    public void displayWelcomeMessage() {
        PrintWriter writer = terminal.writer();
        writer.println("Ingrese el comando 'download --arg' seguido de la url de la carpeta que deseas descargar");
        writer.println("Ejemplo:download https://visuales.uclv.cu/Cursos/Jenkins/1%20-%20Introduction/");
        writer.println("ejecute el comando 'help' para ver la lista de comandos disponibles.");
        writer.flush();
    }
}