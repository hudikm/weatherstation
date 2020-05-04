# WeatherStation


### How to create Docker image:
`sudo docker build -t hudikm/dropwizard-weatherstation .`

### How to push Docker image:
`sudo docker login -u "hudikm" docker.io`

`sudo docker push hudikm/dropwizard-weatherstation:latest`

### How to pull Docker image:
`docker pull hudikm/dropwizard-weatherstation` 
###  How to run
`sudo docker run  -e TZ='Europe/Bratislava' -it -p 9000:8080 -p 9001:8081 hudikm/dropwizard-weatherstation`

