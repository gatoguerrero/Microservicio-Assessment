# Microservicio-Assessment
Repositorio para microservicio


El pipeline se ejecuta automáticamente ante cambios en la rama main.
Flujo del Pipeline

Tests → SonarCloud → Build → Docker → Azure Container Registry

1. Tests

Ejecuta las pruebas automatizadas mediante Maven y genera el reporte de cobertura con JaCoCo.
mvn clean verify

2. Análisis Estático
Utiliza SonarCloud para analizar la calidad del código, incluyendo:

    Bugs
    Vulnerabilidades
    Code Smells
    Cobertura
    Mantenibilidad

El stage continúa únicamente si las pruebas anteriores fueron exitosas.

3. Build
Construye el microservicio utilizando Java 17 y Maven, generando el archivo JAR.
mvn clean package -DskipTests

El JAR generado se publica como artefacto del pipeline.

4. Docker
Descarga el JAR generado y construye la imagen Docker.

La imagen se etiqueta con:

devops-svc:<BuildId>
devops-svc:latest

Finalmente, ambas etiquetas se publican en Azure Container Registry (ACR).

Tecnologías

    Azure DevOps
    Java 17
    Maven
    SonarCloud
    JaCoCo
    Docker
    Azure Container Registry

    Nota: El pipeline actual finaliza con la publicación de la imagen Docker en ACR. El despliegue hacia AKS no está incluido en este pipeline.

