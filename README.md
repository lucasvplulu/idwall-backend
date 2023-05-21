# idwall-backend

# Ferramentas
* Spring-data
* Spring-boot
* QueryDSL
* PostgreSQL
* Lombok
* Swagger
* * url: http://localhost:8001/swagger-ui.html


# Funcionalidades
* API de migração dos dados aleatórios (interpoll/FBI)
* * url: http://localhost:8001/criminoso/migrate
* * Por padrão a migração inicial deve inserir 10 registros, podendo ser alterada a quantidade no arquivo "application.properties" > "quantidade.registros"
* API para listar os dados dos criminosos com/sem filtro.
* * filtro: CriminosoDTO
* * url: http://localhost:8001/criminoso
