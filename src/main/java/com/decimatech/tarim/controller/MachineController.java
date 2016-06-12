package com.decimatech.tarim.controller;

import com.decimatech.tarim.model.entity.Machine;
import com.decimatech.tarim.model.entity.MachineTable;
import com.decimatech.tarim.model.entity.MachineType;
import com.decimatech.tarim.repository.MachineRepository;
import com.decimatech.tarim.repository.MachineTypeRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "/machines")
@PreAuthorize("hasAuthority('ADMIN')")
public class MachineController {

    private Log log = LogFactory.getLog(MachineController.class);

    @Autowired
    private MachineTypeRepository machineTypeRepository;

    @Autowired
    private MachineRepository machineRepository;

    @RequestMapping(value = "/types")
    public String getTypeList(Model model) {
        List<MachineType> machineTypes = machineTypeRepository.findAll();
        model.addAttribute("machineTypes", machineTypes);
        return "admin/machineTypeList";
    }

    @RequestMapping(value = "/types/create", method = RequestMethod.GET)
    public String getTypeForm(@ModelAttribute MachineType machineType) {
        return "admin/machineTypeCreateForm";
    }

    @RequestMapping(value = "/types/create", method = RequestMethod.POST)
    public String createMachineType(@Valid @ModelAttribute MachineType machineType, BindingResult result) {

        if (result.hasErrors()) {
            return "admin/machineTypeCreateForm";
        }
        machineTypeRepository.save(machineType);
        return "redirect:/machines/types";
    }

    @RequestMapping(value = "/types/details/{id}", method = RequestMethod.GET)
    public String getMachineTypeDetails(@PathVariable("id") Long id, Model model) {
        MachineType machineType = machineTypeRepository.findOne(id);
        model.addAttribute("machineType", machineType);
        return "admin/machineTypeUpdateForm";
    }

    @RequestMapping(value = "/types/details/{id}", method = RequestMethod.POST)
    public String updateMachineType(@PathVariable("id") Long id, @Valid @ModelAttribute MachineType machineType, BindingResult result, Model model) {

        if (result.hasErrors()) {
            machineType.setMachineTypeId(id);
            model.addAttribute("machineType", machineType);
            return "admin/machineTypeUpdateForm";
        }
        machineType.setMachineTypeId(id);
        machineTypeRepository.save(machineType);
        return "redirect:/machines/types";
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getMachineForm(@ModelAttribute Machine machine, Model model) {
        List<MachineType> machineTypes = machineTypeRepository.findAll();
        model.addAttribute("machineTypes", machineTypes);

        return "admin/machineCreateForm";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createMachine(@Valid @ModelAttribute Machine machine, BindingResult result, Model model) {

        if (result.hasErrors()) {
            List<MachineType> machineTypes = machineTypeRepository.findAll();
            model.addAttribute("machineTypes", machineTypes);
            return "admin/machineCreateForm";
        } else {
            machineRepository.save(machine);
            return "redirect:/machines";
        }

    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getMachineList(Model model) {
        Long startTime = System.nanoTime();

        List<Machine> machines = machineRepository.findAll();

        List<MachineType> machineTypes = machineTypeRepository.findAll();
        List<MachineTable> machineTables = new ArrayList<>();


        // Makine liste tablosunda makine turunun id'si yerine makine turunun adinin yazilmasi icin gerekli
        // butun makineleri dolasarak tur id'leri esitse MachineTable objesine aliyor
        for (Machine machine : machines){
            MachineTable machineTable = new MachineTable();

            for(MachineType machineType : machineTypes){
                if(Objects.equals(machine.getMachineType(), machineType.getMachineTypeId())){
                    machineTable.setMachineTypeName(machineType.getMachineTypeName());
                    machineTable.setMachineId(machine.getMachineId());
                    machineTable.setMachineName(machine.getMachineName());
                    break;
                }
            }
            machineTables.add(machineTable);
        }

        Long elapsedTime = System.nanoTime() - startTime;

        model.addAttribute("machines", machineTables);
        log.info("MachineList " + TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS) + " ms");

        return "admin/machineList";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String getMachineDetails(@PathVariable("id") Long id, Model model) {
        List<MachineType> machineTypes = machineTypeRepository.findAll();
        Machine machine = machineRepository.findOne(id);

        model.addAttribute("machineTypes", machineTypes);
        model.addAttribute("machine", machine);
        return "admin/machineUpdateForm";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.POST)
    public String updateMachine(@PathVariable("id") Long id, @Valid @ModelAttribute Machine machine, BindingResult result, Model model) {

        if (result.hasErrors()) {
            machine.setMachineId(id);
            List<MachineType> machineTypes = machineTypeRepository.findAll();
            model.addAttribute("machineTypes", machineTypes);
            return "admin/machineUpdateForm";
        } else {
            machine.setMachineId(id);
            machineRepository.save(machine);
            return "redirect:/machines";
        }
    }




}
