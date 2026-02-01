package org.services;

import org.data.TrainRepository;
import org.entities.Train;

import java.util.List;

public class TrainService {

    private final TrainRepository repo;

    public TrainService(TrainRepository repo) {
        this.repo = repo;
    }

    public List<Train> search(String source, String destination) {
        // algorithm here
        List<Train> allTrains = repo.getAllTrains();
        return allTrains.stream().filter(
            train -> {
                int src = train.stations.indexOf(source);
                int dest = train.stations.indexOf(destination);
                return src!=-1 && dest!=-1 && src < dest;
            }
        ).toList();
    }
}
