package com.decimatech.tarim.controller.validation;

import com.decimatech.tarim.model.dto.MaintainFormDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MaintainFormValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(MaintainFormDto.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MaintainFormDto maintainFormDto = (MaintainFormDto) o;

        System.out.println("================== validation ===========================");
        ValidationUtils.rejectIfEmpty(errors, "maintain.distanceCost", "distanceCost.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.machineSerialNo", "machineSerialNo.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.totalDistance", "totalDistance.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.startTime", "startTime.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.finishTime", "finishTime.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.totalTime", "totalTime.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.invoiceDate", "invoiceDate.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.process", "process.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.maintainDesc", "maintainDesc.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.reportOwnerSig", "reportOwnerSig.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.customerSig", "customerSig.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.vendorManagerSig", "vendorManagerSig.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.laborCost", "laborCost.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.totalCost", "totalCost.required");
        ValidationUtils.rejectIfEmpty(errors, "maintain.reportDate", "reportDate.required");

    }
}
