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
import java.util.Objects;

public class MlibreScraper implements Scraper {
    private static final String URL_BASE = "https://listado.mercadolibre.com.ar/";

    @Override
    public List<Product> scrape(String productName) {
        List<Product> products = new ArrayList<>();
        try {
            String encodedProductName = URLEncoder.encode(productName, StandardCharsets.UTF_8);
            String searchUrl = URL_BASE + encodedProductName + "#D[A:" + encodedProductName + "]";
            Document doc = Jsoup.connect(searchUrl).get();
            Elements productElements = doc.select("li.ui-search-layout__item");

            for (Element productElement : productElements) {
                // Extraer nombre del producto, precio y enlace
                String name = productElement.select("h2.poly-box a").text();
                String link = productElement.select("h2.poly-box a").attr("href");
                String price = Objects.requireNonNull(productElement.select("span.andes-money-amount__fraction").first()).text();

                double priceDouble = ConvertPrice.convertPriceDouble(price);
                products.add(new Product(name, priceDouble, link));
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
