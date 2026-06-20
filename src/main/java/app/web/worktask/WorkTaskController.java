package app.web.worktask;

import app.model.dto.worktask.WorkTaskDto;
import app.model.entity.user.UserRole;
import app.service.worktask.WorkTaskService;
import app.repository.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class WorkTaskController {

    private final WorkTaskService workTaskService;
    private final UserRepository userRepository;

    @Autowired
    public WorkTaskController(WorkTaskService workTaskService, UserRepository userRepository) {
        this.workTaskService = workTaskService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("tasks-list");
        modelAndView.addObject("tasks", workTaskService.listAll());
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView newForm() {
        ModelAndView modelAndView = new ModelAndView("task-form");
        modelAndView.addObject("taskForm", WorkTaskDto.builder().build());

        // fetch technicians for select
        var techs = userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == UserRole.TECHNICIAN)
                .collect(Collectors.toList());
        modelAndView.addObject("technicians", techs);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(@Valid @ModelAttribute("taskForm") WorkTaskDto taskDto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("task-form");
            modelAndView.addObject("taskForm", taskDto);
            modelAndView.addObject("org.springframework.validation.BindingResult.taskForm", bindingResult);

            var techs = userRepository.findAll()
                    .stream()
                    .filter(u -> u.getRole() == UserRole.TECHNICIAN)
                    .collect(Collectors.toList());
            modelAndView.addObject("technicians", techs);
            return modelAndView;
        }

        workTaskService.create(taskDto);
        return new ModelAndView("redirect:/tasks");
    }

    @PostMapping("/{id}/start")
    public ModelAndView start(@PathVariable UUID id) {
        var dto = workTaskService.getById(id);
        dto.setStatus(app.model.entity.worktask.TaskStatus.IN_PROGRESS);
        workTaskService.update(id, dto);
        return new ModelAndView("redirect:/tasks");
    }

    @PostMapping("/{id}/complete")
    public ModelAndView complete(@PathVariable UUID id) {
        var dto = workTaskService.getById(id);
        dto.setStatus(app.model.entity.worktask.TaskStatus.COMPLETED);
        workTaskService.update(id, dto);
        return new ModelAndView("redirect:/tasks");
    }

    @PostMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable UUID id) {
        workTaskService.delete(id);
        return new ModelAndView("redirect:/tasks");
    }
}
