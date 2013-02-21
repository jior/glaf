package com.glaf.apps.trip.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.apps.trip.model.*;
import com.glaf.apps.trip.query.*;

@Component
public interface TripMapper {

	void deleteTrips(TripQuery query);

	void deleteTripById(String id);

	Trip getTripById(String id);

	int getTripCount(TripQuery query);

	List<Trip> getTrips(TripQuery query);

	void insertTrip(Trip model);

	void updateTrip(Trip model);

}
