package com.crawler.buscador.crawler;

import com.crawler.buscador.models.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class GarbarinoScraper implements Scraper {
    private static final String URL_BASE = "https://www.garbarino.com/shop?search=";

    @Override
    public List<Product> scrape(String productName) {
        List<Product> products = new ArrayList<>();
        try {
            String searchUrl = URL_BASE + productName;
            Document doc = Jsoup.connect(searchUrl).get();
            Elements productElements = doc.select("div.product-card-design6-vertical");


            //proecesar elementos
            for (Element productElement : productElements) {
                // Extraer nombre del producto, precio y enlace
                String name = productElement.select("div.product-card-design6-vertical__name").text();
                String link = productElement.select("a.card-anchor").attr("href");
                // Extraer precio del producto
                String price = productElement.select("div.product-card-design6-vertical__price span:last-child").text();

                products.add(new Product(name, price, link));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> searchProduct(String productName) {
        return scrape(productName);
    }
}
