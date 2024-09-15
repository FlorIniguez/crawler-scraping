package com.crawler.buscador.crawler;

import com.crawler.buscador.models.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class RodoScraper implements Scraper {
    private static final String BASE_URL = "https://www.rodo.com.ar/catalogsearch/result/?q=";

        @Override
        public List<Product> scrape(String productName) {
            List<Product> products = new ArrayList<>();
            try {
                String searchUrl= BASE_URL + productName;
                //System.out.println("Buscando en URL: " + searchUrl);  // Para depuración
                // Conectar y obtener el documento
                Document doc = Jsoup.connect(searchUrl).get();

                // Imprimir el HTML completo para depuración
               //System.out.println(doc.html());

                // Seleccionar elementos que contienen información del producto
                Elements productElements = doc.select("li.products.items.product-items.item");

                // Procesar los elementos del producto
                for (Element productElement : productElements) {
                    // Extraer nombre del producto y enlace
                    String name = productElement.select("div.product-name a.product-item-link").text();
                    String link = productElement.select("div.product-name a.product-item-link").attr("href");

                    // Encontrar el precio dentro del contenedor de precio
                    Element priceBox = productElement.selectFirst("div.price-box");

                    String price = "Precio no disponible";
                    if (priceBox != null) {
                        Element priceElement = priceBox.selectFirst("span.price");
                        if (priceElement != null) {
                            price = priceElement.text();
                        }
                    }

                    // Añadir el producto a la lista
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
