package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

        List<Integer> disponibilita = new ArrayList<>();
        disponibilita.add(10); // Gold
        disponibilita.add(30); // Pit
        disponibilita.add(60); // Parterre

        ServerSocket ss = new ServerSocket(3000);
        Socket s = ss.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("qualcuno si è collegato");
        out.println("welcome");
        String linea = in.readLine(); // esempio di input: "AVAIL Gold:5 Pit:30 Parterre:60"
        String[] parti = linea.split(" ", 2); // parti[0] = "AVAIL", parti[1] = "Gold:5 Pit:30 Parterre:60"

        in.close();
        out.close();
        s.close();
        ss.close();
    }
}