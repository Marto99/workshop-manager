package app.model.dto.booking;

import app.model.entity.booking.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BookingDto {
    private UUID id;
    private UUID equipmentId;
    private String equipmentName;
    private UUID userId;
    private String userFullName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String note;
    private BookingStatus status;
}
