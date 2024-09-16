package org.example;

import org.example.gptClient.GptClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        GptClient client = new GptClient();

        String response = client.getResponse("Wygeneruj 5 losowych słów po angielsku razem z polskim tłumaczeniem oraz przykłądem zastosowania w zdaniu." +
                " Pozion słów powininen być zróżnicowany, powinny pojawaiać się słowa łątwe oraz zaawansowane");

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("Oto wygenerowane słowa do nauki \n " +
                "===================================== \n " +
                response +"\n" +
                "=====================================");

    }
}