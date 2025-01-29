package Scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class bookScrapping {
    public static void main(String[] args) {
        String url = "https://books.toscrape.com/";
        String filePath = "E:\\programing\\Data Science\\Scrapping Java\\Book Scrapping Results\\books.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Document document = Jsoup.connect(url).get();
            Elements books = document.select(".product_pod");

            System.out.println("Web Scraping Results:");
            writer.write("Book Title _ Price\n");
            writer.write("----------------------\n");

            for (Element book : books) {
                String title = book.select("h3 > a").attr("title");
                String price = book.select(".price_color").text();

                System.out.println(title + " _ " + price);
                writer.write(title + " _ " + price + "\n");
            }

            System.out.println("\nScraped data saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
