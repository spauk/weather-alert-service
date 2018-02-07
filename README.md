# Weather Alerting System

#### Building the service 
> mvn clean package

#### Running the service
> cd target && java -jar weather-alert-service-1.0-SNAPSHOT.jar

#### Running with additional parameters
> cd target && java -Dservice.weather-provider.polling-interval-ms=30000 -Dservice.alert-summary-log-file=alert_log.json -Dservice.alert-settings-file=file:alert_settings.json -jar weather-alert-service-1.0-SNAPSHOT.jar
