package org.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.entities.SeatAvailability;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonSeatRepository implements SeatRepository{

    private final Map<String, SeatAvailability> store =
            new ConcurrentHashMap<>();

    public JsonSeatRepository(){
        loadData();
    }
    private void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("seat_availability.json");

            if (is == null)
                throw new RuntimeException("seat_availability.json not found");

            List<SeatAvailability> list =
                    mapper.readValue(
                            is,
                            new com.fasterxml.jackson.core.type.TypeReference<
                                    List<SeatAvailability>>() {}
                    );

            if (list == null || list.isEmpty()) {
                throw new RuntimeException("seat availability list is empty");
            }

            for (SeatAvailability s : list) {
                store.put(
                        s.trainNo + "_" + s.date,
                        s
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


@Override
    public SeatAvailability find(int TrainNo, String date) {
        return store.get(key(TrainNo,date));
    }

    @Override
    public void save(SeatAvailability seatAvailability) {
        store.put(key(seatAvailability.trainNo, seatAvailability.date),
                seatAvailability);
    }

    @Override
    public SeatAvailability showSeats(int TrainNo, String date) {
        return store.get(key(TrainNo,date));
    }

    private String key(int trainNo, String date) {
        return trainNo + "_" + date;
    }
}
