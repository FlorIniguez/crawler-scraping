package com.crawler.buscador.crawler;

import com.crawler.buscador.models.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MlibreScraper implements Scraper {
    private static final String URL_BASE = "https://listado.mercadolibre.com.ar/";

    @Override
    public List<Product> scrape(String productName) {
        List <Product> products = new ArrayList<>();
        try {
            //creo url de busqueda
            String searchUrl = URL_BASE + productName + "#D[A:" + productName + "]";
            //conecto y obtengo el docuemnto
            Document doc = Jsoup.connect(searchUrl).get();

            //seleccionar elementos que tienen la info
            Elements productElements = doc.select("li.ui-search-layout__item");

            //proecesar elementos
            for(Element productElement : productElements){
                // Extraer nombre del producto, precio y enlace
                String name = productElement.select("h2.poly-box a").text();
                String link = productElement.select("h2.poly-box a").attr("href");
                String price = productElement.select("span.andes-money-amount__fraction").text();

                // Formatear el precio si es necesario
                if (price.isEmpty()) {
                    price = "Precio no disponible";
                } else {
                    price = "$" + price;
                }


                products.add(new Product(name, price,link));
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
