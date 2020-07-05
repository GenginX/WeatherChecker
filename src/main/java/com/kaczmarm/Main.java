package com.kaczmarm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Pogoda z Interii:");
        InteriaWeather();

    }

    private static void InteriaWeather() throws IOException {
        downloadWebsite("https://pogoda.interia.pl/prognoza-szczegolowa-lodz,cId,19059", "interia_Pogoda");

        List<String> dataFromWebsite = new ArrayList<>();
        List<String> receivedData = new ArrayList<>();

        dataFromWebsite.add("span.weather-currently-info-item-date-label");
        dataFromWebsite.add("span.weather-currently-info-item-date");
        dataFromWebsite.add("span.weather-currently-info-item-time");
        dataFromWebsite.add("div[class=weather-currently-temp-strict]");

        Document doc = Jsoup.parse(new File(".\\interia_Pogoda.htlm"), "UTF-8");

        for(String s : dataFromWebsite){
            for(Element e : doc.select(s)){
                receivedData.add(e.text());
            }
        }

        System.out.print(receivedData.get(1) + " ");
        System.out.print(receivedData.get(2) + " ");
        System.out.println(receivedData.get(3));
    }

    private static void downloadWebsite(String site, String name) throws IOException {
        URL website = new URL(site);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(name + ".htlm");
        fos.getChannel().transferFrom(rbc,0,Long.MAX_VALUE);
    }

}
