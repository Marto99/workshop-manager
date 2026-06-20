package app.web.equipment;

import app.model.dto.equipment.EquipmentDto;
import app.service.equipment.EquipmentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.UUID;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("equipment-list");
        modelAndView.addObject("equipment", equipmentService.listAll());
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView newForm() {
        ModelAndView modelAndView = new ModelAndView("equipment-form");
        modelAndView.addObject("equipmentForm", EquipmentDto.builder().build());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(@Valid @ModelAttribute("equipmentForm") EquipmentDto equipmentDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("equipment-form");
            modelAndView.addObject("equipmentForm", equipmentDto);
            modelAndView.addObject("org.springframework.validation.BindingResult.equipmentForm", bindingResult);
            return modelAndView;
        }

        equipmentService.create(equipmentDto);
        return new ModelAndView("redirect:/equipment");
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable UUID id) {
        ModelAndView mv = new ModelAndView("equipment-detail");
        mv.addObject("item", equipmentService.getById(id));
        return mv;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editForm(@PathVariable UUID id) {
        ModelAndView modelAndView = new ModelAndView("equipment-form");
        modelAndView.addObject("equipmentForm", equipmentService.getById(id));
        return modelAndView;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView update(@PathVariable UUID id, @Valid @ModelAttribute("equipmentForm") EquipmentDto equipmentDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("equipment-form");
            modelAndView.addObject("equipmentForm", equipmentDto);
            modelAndView.addObject("org.springframework.validation.BindingResult.equipmentForm", bindingResult);
            return modelAndView;
        }

        equipmentService.update(id, equipmentDto);
        return new ModelAndView("redirect:/equipment");
    }

    @PostMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable UUID id) {
        equipmentService.delete(id);
        return new ModelAndView("redirect:/equipment");
    }
}
