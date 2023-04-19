package com.geopokrovskiy.controller;

import com.geopokrovskiy.сonstants.Constants;
import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.repository.SpecialityRepository;

public class SpecialityController {

    public static void addNewSpec(String name) {
        Speciality spec = new Speciality(name);
        if (!new SpecialityRepository(Constants.filenameSpecs).addNew(spec)) {
            System.out.println("Impossible to add the speciality");
        } else {
            System.out.println("The speciality has been successfully added!");
        }
    }

    public static void updateSpec(Speciality oldSpec, String newSpec) {
        long id = oldSpec.getId();
        Speciality newSpecObj = new Speciality(newSpec);
        newSpecObj.setId(id);
        if (!new SpecialityRepository(Constants.filenameSpecs).update(id, newSpecObj)) {
            System.out.println("The speciality has been deleted or does not exist");
        } else {
            System.out.println("The speciality has been successfully updated!");
        }
    }

    public static void deleteSpec(Speciality toDelete) {
        long id = toDelete.getId();
        if (!new SpecialityRepository(Constants.filenameSpecs).delete(id)) {
            System.out.println("The speciality has been already deleted or does not exist.");
        } else {
            System.out.println("The speciality has been successfully deleted!");
        }
    }
}

