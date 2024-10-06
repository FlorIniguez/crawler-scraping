package com.crawler.buscador.service;

import com.crawler.buscador.Exceptions.ProductNotFoundException;
import com.crawler.buscador.crawler.GarbarinoScraper;
import com.crawler.buscador.crawler.MlibreScraper;
import com.crawler.buscador.crawler.RodoScraper;
import com.crawler.buscador.models.Product;
import com.crawler.buscador.utils.PriceComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.crawler.buscador.utils.PriceComparator.priceComparator;

@Service
public class ScraperService {

    @Autowired
    RodoScraper rodoScraper;
    @Autowired
    MlibreScraper mlibreScraper;
    @Autowired
    GarbarinoScraper garbarinoScraper;
    @Autowired
    PriceComparator priceComparator;

    public Map<String, Object> searchAndDisplay(String productName) {
        List<Product> garbarinoProducts = garbarinoScraper.searchProduct(productName);
        List<Product> mlibreProducts = mlibreScraper.searchProduct(productName);
        List<Product> rodoProducts = rodoScraper.searchProduct(productName);

        if (rodoProducts.isEmpty() && mlibreProducts.isEmpty() && garbarinoProducts.isEmpty()) {
            throw new ProductNotFoundException(productName);
        }

        Optional<Product> cheapestProduct = priceComparator(garbarinoProducts, mlibreProducts, rodoProducts);

        //devuelvo producto mas barato y la lista entera ordenada por precio
        Map<String, Object> response = new HashMap<>();
        cheapestProduct.ifPresent(product -> response.put("CheapestProduct", product));
        //Stream.of convierte cada lista en un flujo de datos, flujo de 3 elementos
        response.put("productList", Stream.of(rodoProducts, mlibreProducts, garbarinoProducts)
                //con cada lista de flujo, creo un flujo de productos
                .flatMap(List::stream)
                .sorted(Comparator.comparing(Product::getPrice))
                .collect(Collectors.toList()));
        return response;
    }
}


