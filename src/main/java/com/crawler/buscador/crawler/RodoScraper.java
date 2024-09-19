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

public class RodoScraper implements Scraper {
    private static final String BASE_URL = "https://www.rodo.com.ar/catalogsearch/result/?q=";

    @Override
    public List<Product> scrape(String productName) {
        List<Product> products = new ArrayList<>();
        try {
            String encodedProductName = URLEncoder.encode(productName, StandardCharsets.UTF_8);
            String searchUrl = BASE_URL + encodedProductName;
            // Conectar y obtener el documento
            Document doc = Jsoup.connect(searchUrl).get();

            //System.out.println(doc.html());

            // Seleccionar elementos que contienen información del producto
            Elements productElements = doc.select("li.products");
            // Procesar los elementos del producto
            for (Element productElement : productElements) {
                String name = productElement.select("strong.product-item-name a").text();
                String link = productElement.select("strong.product-item-name a").attr("href");
                String price = productElement.select("span.price-wrapper span.price").text();

                double priceDouble = ConvertPrice.convertPriceDouble(price);
                Product product = new Product(name, priceDouble, link);
                products.add(product);
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
