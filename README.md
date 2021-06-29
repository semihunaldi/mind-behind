# mind-behind

## Steps to build and run on local machine/IDE
-   on project root run:
```shell
  mvn spring-boot:run 
```

-   on mind-behind-frontend run:
```shell
  ng serve
```

## Steps to build and run on docker

-   on project root run:
```shell
  mvn clean install
```

-   on mind-behind-frontend run:
```shell
  ng build --prod=true
```

-on project root run:
```shell
  docker-compose up
```


