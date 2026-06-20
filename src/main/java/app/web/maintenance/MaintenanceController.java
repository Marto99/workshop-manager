package app.web.maintenance;

import app.model.dto.maintenance.MaintenanceDto;
import app.service.equipment.EquipmentService;
import app.service.maintenance.MaintenanceService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;
    private final EquipmentService equipmentService;

    @Autowired
    public MaintenanceController(MaintenanceService maintenanceService, EquipmentService equipmentService) {
        this.maintenanceService = maintenanceService;
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("maintenance-list");
        modelAndView.addObject("maintenanceRequests", maintenanceService.listAll());
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView newForm() {
        ModelAndView modelAndView = new ModelAndView("maintenance-form");
        modelAndView.addObject("maintenanceForm", MaintenanceDto.builder().build());
        modelAndView.addObject("equipment", equipmentService.listAll());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(@Valid @ModelAttribute("maintenanceForm") MaintenanceDto dto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("maintenance-form");
            modelAndView.addObject("maintenanceForm", dto);
            modelAndView.addObject("org.springframework.validation.BindingResult.maintenanceForm", bindingResult);
            modelAndView.addObject("equipment", equipmentService.listAll());
            return modelAndView;
        }

        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        dto.setReporterId((UUID) userId);
        maintenanceService.create(dto);
        return new ModelAndView("redirect:/maintenance");
    }

    @PostMapping("/{id}/resolve")
    public ModelAndView resolve(@PathVariable UUID id) {
        maintenanceService.resolve(id);
        return new ModelAndView("redirect:/maintenance");
    }

}
