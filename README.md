# Proyecto de Scraping de Productos

Este proyecto consiste en un scraper en Java para buscar y comparar productos en diferentes sitios web de e-commerce. Hasta ahora, se han implementado scrapers para tres sitios web: Rodo, Mercado Libre y Garbarino.

## **Objetivo del Proyecto**
El objetivo del proyecto es implementar una funcionalidad de comparación de precios para los productos buscados por el cliente. El scraper extrae datos relevantes, como el nombre del producto, el precio y el enlace de compra, permitiendo al usuario tomar decisiones informadas basadas en el precio.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación utilizado para desarrollar el scraper.
- **JSoup**: Biblioteca para realizar scraping de contenido HTML.
- **Maven**: Herramienta de gestión de proyectos y automatización de construcción.
- **Docker**: Plataforma utilizada para contenerizar la aplicación.

## Instrucciones para Ejecutar la Aplicación

Para correr la aplicación de scraping, sigue estos pasos:

1. **Clona el repositorio** (si no lo has hecho):
 
   ```bash
   git clone https://github.com/FlorIniguez/crawler-scraping.git
   cd crawler-scraping

2. **Construir la imagen de Docker:**

      ```bash
      docker build -t buscador .
3. **Ejecutar el contenedor: Puedes elegir el nombre que desees para el contenedor:**
    ```bash
    docker run -d -p 8080:8080 --name nombre-del-contenedor buscador
4. **Acceder a la aplicación:**
    Una vez que el contenedor esté en funcionamiento, puedes acceder a la aplicación en tu navegador a través de http://localhost:8080.





   
   
