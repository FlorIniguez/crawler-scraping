package com.crawler.buscador;

import com.crawler.buscador.crawler.GarbarinoScraper;
import com.crawler.buscador.crawler.MlibreScraper;
import com.crawler.buscador.crawler.RodoScraper;
import com.crawler.buscador.models.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class BuscadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuscadorApplication.class, args);

        RodoScraper rodoScraper = new RodoScraper();
        MlibreScraper mlibreScraper = new MlibreScraper();
        GarbarinoScraper garbarinoScraper = new GarbarinoScraper();

        // Pedir al usuario que ingrese el producto a buscar
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del producto que desea buscar:");
        String productName = scanner.nextLine();

        // Buscar el producto en Rodo
        System.out.println("Resultados en Rodo:");
        List<Product> rodoProducts = rodoScraper.searchProduct(productName);
        printResults(rodoProducts);

        // Buscar el producto en M.libre
        System.out.println("Resultados en Mercado libre:");
        List<Product> mlibreProducts = mlibreScraper.searchProduct(productName);
        printResults(mlibreProducts);


        // Buscar el producto en Garbarino
        System.out.println("Resultados en garbarino:");
        List <Product> garbarinoProducts = garbarinoScraper.searchProduct(productName);
        printResults(garbarinoProducts);
    }

    // Método para imprimir los resultados en consola
    private static void printResults(List<Product> products) {
        for (Product product : products) {
            System.out.println("Nombre: " + product.getName());
            System.out.println("Precio: " + product.getPrice());
            System.out.println("Enlace: " + product.getLink());
            System.out.println("-----------------------------------");
        }
    }
}
