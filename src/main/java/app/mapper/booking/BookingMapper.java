package app.mapper.booking;

import app.model.dto.booking.BookingDto;
import app.model.entity.booking.Booking;
import app.model.entity.equipment.Equipment;
import app.model.entity.user.User;

public class BookingMapper {

    public static BookingDto toDto(Booking booking) {
        if (booking == null) return null;

        BookingDto.BookingDtoBuilder builder = BookingDto.builder()
                .id(booking.getId())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .note(booking.getNote())
                .status(booking.getStatus());

        if (booking.getEquipment() != null) {
            builder.equipmentId(booking.getEquipment().getId())
                   .equipmentName(booking.getEquipment().getName());
        }

        if (booking.getUser() != null) {
            builder.userId(booking.getUser().getId())
                   .userFullName(booking.getUser().getFullName());
        }

        return builder.build();
    }

    public static Booking toEntity(BookingDto dto) {
        if (dto == null) return null;

        Equipment equipment = dto.getEquipmentId() != null ? Equipment.builder().id(dto.getEquipmentId()).build() : null;
        User user = dto.getUserId() != null ? User.builder().id(dto.getUserId()).build() : null;

        return Booking.builder()
                .id(dto.getId())
                .equipment(equipment)
                .user(user)
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .note(dto.getNote())
                .status(dto.getStatus())
                .build();
    }
}
