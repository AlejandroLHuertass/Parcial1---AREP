package edu.escuelaing.arem.ASE.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ReflexCalculator {

    public List<Integer> A = new ArrayList<Integer>();
    public int inicio, fin;

    public ReflexCalculator(List<Integer> A, int inicio, int fin) {
        this.A = A;
        this.inicio = inicio;
        this.fin = fin;
    }

    public static int partition(List<Integer> A, int inicio, int fin) {

        int pivote = A.get(fin), posicion = fin;

        for (int i = 0; i < A.size(); i++) {
            if (pivote < A.get(i) && posicion > i) {
                int temp = A.get(i);
                A.set(i, pivote);
                A.set(posicion, temp);
                posicion = i;
            } else if (pivote > A.get(i) && posicion < i) {
                int temp1 = A.get(i);
                A.set(i, pivote);
                A.set(posicion, temp1);
                posicion = i;
            }
        }
        System.out.println();

        return posicion;
    }

    public static List<Integer> QuickSort(List<Integer> A, int inicio, int fin) {
        if (inicio < fin) {
            int p;
            p = partition(A, inicio, fin);
            QuickSort(A, inicio, p - 1);
            QuickSort(A, p + 1, fin);
        }
        return A;
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        boolean running = true;
        while (running) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibí: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            outputLine = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<meta charset=\"UTF-8\">" +
                    "<title>Title of the document</title>\n" +
                    "</head>" +
                    "<body>" +
                    "<h1>Mi propio mensaje</h1>" +
                    "</body>" +
                    "</html>";

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();

    }

}
