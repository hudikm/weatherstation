//package sk.fri.uniza.api;
//
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DynamicTest;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Iterator;
//import java.util.List;
//import java.util.logging.Logger;
//
//public class WeatherDataTest {
//@Test
//public void testTimeAndDate(){
//    ObjectMapper mapper = new ObjectMapper();
//    InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("weather.json");
//    try {
//
//        List<WeatherData> weatherData = mapper.readValue(resourceAsStream, new TypeReference<List<WeatherData>>() {
//        });
//
//        Iterator<WeatherData> iterator = weatherData.iterator();
//        WeatherData oldData = iterator.next();
//        while(iterator.hasNext()){
//            WeatherData newData = iterator.next();
//            if(oldData.getStationName().equals(newData.getStationName())) {
//                boolean equal = oldData.getDateTime().plusHours(1).isEqual(newData.getDateTime());
//                if (!equal) {
//                    Logger.getLogger("Chyba").severe(newData.getStationName() + " -> " +oldData.getDateTime().minusYears(4).toString() + " : " +  newData.getDateTime().minusYears(4).toString());
//                }
//            }
//            oldData = newData;
//        }
//
//
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}
//}
