package org.spauk.weatheralert.weatherprovider.openweathermap.model;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenWeatherMapForecast {

    private List<ListElement> list;

    private City city;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListElement {

        private Instant dt;

        private Main main;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Main {

            private double temp;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class City {

        private String name;
    }
}