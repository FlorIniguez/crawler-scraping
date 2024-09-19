package com.crawler.buscador;

import com.crawler.buscador.crawler.GarbarinoScraper;
import com.crawler.buscador.crawler.MlibreScraper;
import com.crawler.buscador.crawler.RodoScraper;
import com.crawler.buscador.models.Product;
import com.crawler.buscador.utils.PriceComparator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.crawler.buscador.utils.PriceComparator.priceComparator;

@SpringBootApplication
public class BuscadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuscadorApplication.class, args);

        RodoScraper rodoScraper = new RodoScraper();
        MlibreScraper mlibreScraper = new MlibreScraper();
        GarbarinoScraper garbarinoScraper = new GarbarinoScraper();


        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del producto que desea buscar:");
        String productName = scanner.nextLine();

        // Buscar el producto en Rodo
        System.out.println("Resultados en Rodo:");
        List<Product> rodoProducts = rodoScraper.searchProduct(productName);
        printResults(rodoProducts);
        System.out.println("-----------------------------------");

        // Buscar el producto en Mercado Libre
        System.out.println("Resultados en Mercado Libre:");
        List<Product> mlibreProducts = mlibreScraper.searchProduct(productName);
        printResults(mlibreProducts);
        System.out.println("-----------------------------------");

        // Buscar el producto en Garbarino
        System.out.println("Resultados en Garbarino:");
        List<Product> garbarinoProducts = garbarinoScraper.searchProduct(productName);
        printResults(garbarinoProducts);
        System.out.println("-----------------------------------");

       Optional<Product> easierProduct = priceComparator(rodoProducts,mlibreProducts,garbarinoProducts);
        System.out.println("El producto más barato encontrado para: " + productName);
        System.out.println(easierProduct.get());
    }

    private static void printResults(List<Product> products) {
        for (Product product : products) {
            System.out.println("Nombre: " + product.getName());
            System.out.println("Precio: $" + product.getPrice());
            System.out.println("Enlace: " + product.getLink());
            System.out.println("-----------------------------------");
        }
    }
}