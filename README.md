# Frontend
vite frontend/ --host

# Backend

## Build the Application
./gradlew clean bootJar

## DEV
### Using Gradle task
./gradlew runWithDevProfile

### Running the JAR directly
java -jar build/libs/petclinic.jar --spring.profiles.active=dev

### Using Spring Boot Maven plugin
./gradlew bootRun --args='--spring.profiles.active=dev'

## Prod
### Using Gradle task
./gradlew runWithProdProfile

### Running the JAR directly
java -jar build/libs/petclinic.jar --spring.profiles.active=prod

### Using Spring Boot Maven plugin
./gradlew bootRun --args='--spring.profiles.active=prod'