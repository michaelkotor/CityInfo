# CityInfo

### Telegram bot to store and receive infomation about cities.

[t.me/michaelkotor_cityinfobot](https://t.me/michaelkotor_cityinfobot)

#### To run:

- Edit application.properties (real token)

- ```mvn clean install```

- ```docker-compose up --build```

#### To debug:

- Edit ```docker-compose.yml``` (delete the telega service)

- In ```SendRequest.java``` change ***api*** to ***localhost*** in url

Or attach to running docker contains directly.

#### Explore Mysql database

- ```docker ps``` - find your running container 

- ```docker exec -it CONTAINER_ID bash```

- ```mysql -u root -p1234```

- ```use cities;```

- Have fun!
