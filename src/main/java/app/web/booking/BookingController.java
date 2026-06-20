package app.web.booking;

import app.model.dto.booking.BookingDto;
import app.service.booking.BookingService;
import app.service.equipment.EquipmentService;
import app.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final EquipmentService equipmentService;
    private final UserService userService;

    @Autowired
    public BookingController(BookingService bookingService, EquipmentService equipmentService, UserService userService) {
        this.bookingService = bookingService;
        this.equipmentService = equipmentService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("bookings-list");
        modelAndView.addObject("bookings", bookingService.listAll());
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView newForm() {
        ModelAndView modelAndView = new ModelAndView("booking-form");
        modelAndView.addObject("bookingForm", BookingDto.builder().build());
        modelAndView.addObject("availableEquipment", equipmentService.listAll());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(@Valid @ModelAttribute("bookingForm") BookingDto bookingDto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("booking-form");
            modelAndView.addObject("bookingForm", bookingDto);
            modelAndView.addObject("availableEquipment", equipmentService.listAll());
            modelAndView.addObject("org.springframework.validation.BindingResult.bookingForm", bindingResult);
            return modelAndView;
        }

        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        bookingDto.setUserId((UUID) userId);
        bookingService.create(bookingDto);
        return new ModelAndView("redirect:/bookings");
    }

    @PostMapping("/{id}/cancel")
    public ModelAndView cancel(@PathVariable UUID id) {
        bookingService.cancel(id);
        return new ModelAndView("redirect:/bookings");
    }

}
