package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.Machine;
import com.decimatech.tarim.model.Part;
import com.decimatech.tarim.repository.MachineRepository;
import com.decimatech.tarim.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/parts")
@PreAuthorize("hasAuthority('ADMIN')")
public class PartController {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private PartRepository partRepository;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getPartForm(@ModelAttribute Part part, Model model) {
        List<Machine> machines = machineRepository.findAll();
        model.addAttribute("machines", machines);

        return "partCreateForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPart(@Valid @ModelAttribute Part part, BindingResult result, Model model) {

        if (result.hasErrors()) {
            List<Machine> machines = machineRepository.findAll();
            model.addAttribute("machines", machines);
            return "partCreateForm";
        } else {
            partRepository.save(part);
            return "redirect:/parts";
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getPartList(Model model) {
        List<Part> parts = partRepository.findAll();
        model.addAttribute("parts", parts);

        return "partList";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String getPart(@PathVariable("id") Long id, Model model) {

        List<Machine> machines = machineRepository.findAll();
        Part part = partRepository.findOne(id);
        model.addAttribute("part", part);
        model.addAttribute("machines", machines);
        return "partUpdateForm";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST)
    public String updatePart(@PathVariable("id") Long id, @Valid @ModelAttribute Part part, BindingResult result, Model model) {

        if (result.hasErrors()) {
            part.setPartId(id);
            List<Machine> machines = machineRepository.findAll();
            model.addAttribute("machines", machines);
            return "partUpdateForm";
        }else {
            part.setPartId(id);
            partRepository.save(part);
            return "redirect:/parts";
        }
    }

}
