# Detector API
`Detector API` es un API qyudará a Magneto a reclutar de forma eficaz la mayor cantidad de mutantes para poder luchar contra los X-Men.

### <i> NOTA: Asegurarse de tener instalado [Git](https://git-scm.com), [Maven](http://maven.apache.org) y [Docker](https://www.docker.com)</i> 

## Compilado y pruebas unitarias
```bash
mvn package
```

## Instalación y ejecución local
``` bash
git clone https://github.com/Roldanhollow/detector-api.git
cd detector-api
docker-compose up --build
```

## Endpoints del API
<i>NOTA: La aplicación ha sido desplegada en [https://detector--api.herokuapp.com](https://detector--api.herokuapp.com/) para facilitar el llamado de los endpoints</i>
- [X] `Detector de mutantes`: Permite conocer mediante el ADN si una persona es mutante o no. Su endpoint es:
```/mutant [POST]```
``` json
{
"dna":[<Array de String>]
}
```
Donde `dna` representa cada fila de una tabla de NxN con la secuencia del ADN. Retorna OK-200 si es un mutante, en otro caso, 403-Forbidden.
>>Nota: Las letras solo pueden ser: (A,T,C,G), las cuales representan cada base nitrogenada del ADN.

- [X] `Estadísticas`: Permite conocer las estadísticas de las verificaciones de ADN. Su endpoint es:
```/stats [GET]```

Obtendrá una respuesta de este tipo:
``` json
{
    "ratio": <double>,
    "count_mutant_dna": <int>,
    "count_human_dna": <int>
}
```

### Jacoco
- [x] Cobertura: 0.80
- [x] Reporte: /target/site/jacoco/index.html 

#### Herramientas
En esta API se usó lo siguiente:
|  |
| ------ |
| [Spring Boot](https://start.spring.io) |
| [Micrometer](https://micrometer.io/docs/installing) |
| [Liquibase](https://www.liquibase.org) |
| [PostgreSQL](https://www.postgresql.org) |
| [Jacoco](https://www.jacoco.org/jacoco/trunk/index.html) |
| [Docker](https://www.docker.com) |
