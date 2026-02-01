package org.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.entities.SeatAvailability;
import org.entities.Train;

import java.io.InputStream;
import java.util.List;

public class TrainRepository {
    private final List<Train> trainList;
    public TrainRepository(){
        this.trainList = fetchTrains();
    }
    private List<Train> fetchTrains() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("trains.json");

            if (is == null) {
                throw new RuntimeException("trains.json not found");
            }

            List<Train> trains =
                    mapper.readValue(
                            is,
                            new com.fasterxml.jackson.core.type.TypeReference<
                                    List<Train>>() {}
                    );

            if (trains == null || trains.isEmpty()) {
                throw new RuntimeException("train list is not present");
            }

            return trains;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Train> getAllTrains(){
        return trainList;
    }
    public String getTrainName(Integer trainNo){
        return getAllTrains().stream()
                .filter(t->t.trainNo == trainNo)
                .map(t->t.name)
                .findFirst()
                .orElseThrow();
    }
}
