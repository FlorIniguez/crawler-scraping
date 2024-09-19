package com.crawler.buscador.crawler;

import com.crawler.buscador.models.Product;
import com.crawler.buscador.utils.ConvertPrice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GarbarinoScraper implements Scraper {
    private static final String URL_BASE = "https://www.garbarino.com/shop?search=";
    private static final String URL_PRODUCT = "https://www.garbarino.com";


    @Override
    public List<Product> scrape(String productName) {
        List<Product> products = new ArrayList<>();
        try {
            // Codificar el nombre del producto para que sea válido en la URL
            String encodedProductName = URLEncoder.encode(productName, StandardCharsets.UTF_8);
            String searchUrl = URL_BASE + encodedProductName;
            Document doc = Jsoup.connect(searchUrl).get();
            Elements productElements = doc.select("div.product-card-design6-vertical");

            //proecesar elementos
            for (Element productElement : productElements) {
                // Extraer nombre del producto, precio y enlace
                String name = productElement.select("div.product-card-design6-vertical__name").text();
                String relativeLink = productElement.select("a.card-anchor").attr("href");
                String link = URL_PRODUCT + relativeLink;
                String price = productElement.select("div.product-card-design6-vertical__price span:last-child").text();

                double priceDouble = ConvertPrice.convertPriceDouble(price);
                if (!name.isEmpty() && !price.isEmpty() && !link.isEmpty()) {
                    products.add(new Product(name, priceDouble, link));
                }

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
