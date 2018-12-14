# Rezepte Generator

[![Build Status](https://travis-ci.org/dp-dev/RezepteGenerator.svg?branch=master)](https://travis-ci.org/dp-dev/RezepteGenerator) [![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=de.studware%3Arezeptegenerator&metric=alert_status)](https://sonarcloud.io/dashboard?id=de.studware%3Arezeptegenerator)

A small JAVA Tool to parse website content (recipes) from the following sites to a PDF file:

  - [***Rezeptewelt***](https://www.rezeptwelt.de/)
  - [***Danis Thermomix Rezepte***](http://danis-treue-kuechenfee.de/)
  - [***Food With Love***](http://www.foodwithlove.de/)

Logging messages will be displayed in English to the user to follow every single step. Everything else on the screen is written in German.

### Run Rezepte Generator:
Either double click the executable JAR file or open the command and type the following command to start the program: 
```cmd
java -jar RezepteGenerator.jar
```

### Packages include:
  - [**Jsoup**](https://jsoup.org/) - Java HTML Parser
  - [**iText PDF**](https://itextpdf.com/) - easy PDF generation for Java

Please report any issues with the core functionality here in this repository. Thank you!