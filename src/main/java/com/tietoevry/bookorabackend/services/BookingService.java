package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.BookingDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;

public interface BookingService {

    MessageDTO bookOneZoneOfOneDay(BookingDTO bookingDTO);
}
