## Problem sample
The codebase was the original one of this official [guide](https://guides.micronaut.io/latest/micronaut-data-hibernate-reactive-gradle-kotlin.html#testing-the-application) on micronaut.

Modifications:
- [build.gradle](build.gradle): added a runtime mysql driver at the end of the dependencies
- [resources/application.yml](application.yml): extra section which is currently commented out.

## How to reproduce the problem
1. Build the code `./gradlew build` or run it locally `./gradlew run`. [https://micronaut-projects.github.io/micronaut-test-resources/snapshot/guide/#modules-databases-r2dbc](test resources) will do its job;
2. Try to run as production. I personally follow this path:
    ```
   ./runw buildDocker
   # copy the image id
   
   # run a local mysql
   docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_DATABASE=test -e MYSQL_USER=test -e MYSQL_PASSWORD=test -p 3306:3306 -d mysql:8.0.30
   
   # export the expected env vars
   export JDBC_URL=jdbc:mysql://<localhost-or-your-ip>:3306/test
   export JDBC_USER=test
   export JDBC_PASSWORD=test
   export JDBC_DRIVER=com.mysql.jdbc.Driver
   
    docker run --rm -e DATASOURCES_DEFAULT_URL=$JDBC_URL -e DATASOURCES_DEFAULT_USERNAME=$JDBC_USER -e DATASOURCES_DEFAULT_PASSWORD=$JDBC_PASSWORD -it -p 8080:8080 <your-generated-image-id>
   
   # now you should stumble into the error!
   Caused by: org.hibernate.HibernateError: The configuration property 'hibernate.connection.url' was not provided, or is in invalid format. This is required when using the default DefaultSqlClientPool: either provide the configuration setting or integrate with a different SqlClientPool implementation
   ```

To fix this on prod you can uncomment the extra section I've added on application.yml.

My guess: Either I'm not passing all the expected env vars, or the test resources is not working properly?

## Micronaut 3.7.5 Documentation

- [User Guide](https://docs.micronaut.io/3.7.5/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.7.5/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.7.5/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
## Feature reactor documentation

- [Micronaut Reactor documentation](https://micronaut-projects.github.io/micronaut-reactor/snapshot/guide/index.html)


## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


## Feature test-resources documentation

- [Micronaut Test Resources documentation](https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/)


## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)


