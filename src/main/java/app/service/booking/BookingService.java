package app.service.booking;

import app.mapper.booking.BookingMapper;
import app.model.dto.booking.BookingDto;
import app.model.entity.booking.Booking;
import app.model.entity.equipment.Equipment;
import app.model.entity.user.User;
import app.repository.booking.BookingRepository;
import app.repository.equipment.EquipmentRepository;
import app.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          EquipmentRepository equipmentRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
    }

    public List<BookingDto> listAll() {
        return bookingRepository.findAll()
                .stream()
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookingDto getById(UUID id) {
        Booking b = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));
        return BookingMapper.toDto(b);
    }

    public BookingDto create(BookingDto dto) {
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found: " + dto.getEquipmentId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUserId()));

        Booking booking = Booking.builder()
                .equipment(equipment)
                .user(user)
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .note(dto.getNote())
                .status(dto.getStatus() != null ? dto.getStatus() : app.model.entity.booking.BookingStatus.ACTIVE)
                .build();

        Booking saved = bookingRepository.save(booking);
        return BookingMapper.toDto(saved);
    }

    public BookingDto update(UUID id, BookingDto dto) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));

        if (dto.getEquipmentId() != null) {
            Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                    .orElseThrow(() -> new RuntimeException("Equipment not found: " + dto.getEquipmentId()));
            existing.setEquipment(equipment);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUserId()));
            existing.setUser(user);
        }

        existing.setStartTime(dto.getStartTime());
        existing.setEndTime(dto.getEndTime());
        existing.setNote(dto.getNote());
        existing.setStatus(dto.getStatus());

        Booking saved = bookingRepository.save(existing);
        return BookingMapper.toDto(saved);
    }

    public void cancel(UUID id) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));
        existing.setStatus(app.model.entity.booking.BookingStatus.CANCELLED);
        bookingRepository.save(existing);
    }

    public void delete(UUID id) {
        bookingRepository.deleteById(id);
    }
}