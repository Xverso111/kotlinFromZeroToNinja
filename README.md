### Pre-requisitos:
    - Docker >= 2.0.0.0
    - JDK = 1.8
    - Kotlin = 1.3.41


### Database:
```
docker run -d --rm --name postgres-db \
        -p 5432:5432 \
        -e POSTGRES_PASSWORD=12345678 \
        -e POSTGRES_USER=javaday-api \
        -e POSTGRES_DB=javaday-db \
        postgres
```
