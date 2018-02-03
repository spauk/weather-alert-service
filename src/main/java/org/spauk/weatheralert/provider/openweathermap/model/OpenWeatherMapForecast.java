package org.spauk.weatheralert.provider.openweathermap.model;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class OpenWeatherMapForecast {

    private List<ListElement> list;

    private City city;

    @Data
    public static class ListElement {

        private Instant dt;

        private Main main;

        @Data
        public static class Main {

            private double temp;
        }
    }

    @Data
    public static class City {

        private String name;
    }
}