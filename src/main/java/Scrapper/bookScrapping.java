package Scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class bookScrapping {
    public static void main(String[] args) {
        String url = "https://books.toscrape.com/";
        String filePath = "books.txt"; // File to store book details, You can choose yours
        String imageFolderPath = "images\\"; // Folder to store images ,You can choose yours

        new File(imageFolderPath).mkdirs();

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            Document document = Jsoup.connect(url).get();
            Elements books = document.select(".product_pod");

            System.out.println("Web Scraping Results:");
            writer.write("Book Title _ Price _ Image File\n");
            writer.write("-----------------------------------\n");

            for (Element book : books) {
                String title = book.select("h3 > a").attr("title");
                String price = book.select(".price_color").text();
                String imageUrl = url + book.select(".image_container img").attr("src").replace("../", "");

                String imageFileName = title.replaceAll("[^a-zA-Z0-9]", "_") + ".jpg";
                String imagePath = imageFolderPath + imageFileName;
                saveImage(imageUrl, imagePath);
                System.out.println(title + " _ " + price + " _ " + imageFileName);
                writer.write(title + " _ " + price + " _ " + imageFileName + "\n");
            }

            System.out.println("\nScraped data and images saved ");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Method to download and save images
    private static void saveImage(String imageUrl, String destinationFile) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, Paths.get(destinationFile));
        } catch (IOException e) {
            System.out.println("Failed to download image: " + imageUrl);
        }
    }
}
