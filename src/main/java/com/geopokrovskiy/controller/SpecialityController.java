package com.geopokrovskiy.controller;

import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.SpecialityRepository;
import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.repository.gson.GsonSpecialityRepositoryImpl;

import java.util.List;

public class SpecialityController {

    private final SpecialityRepository specialityRepository = new GsonSpecialityRepositoryImpl();

    public Speciality addNewSpec(String name) {
        Speciality speciality = new Speciality(name);
        specialityRepository.addNew(speciality);
        return speciality;
    }

    public List<Speciality> getAllSpecs(){
        return specialityRepository.getAll().stream().filter(spec -> spec.getStatus() != Status.DELETED).toList();
    }

    public Speciality updateSpec(Speciality oldSpec, String newSpec) {
        Speciality newSpecObj = new Speciality(newSpec);
        newSpecObj.setId(oldSpec.getId());
        return specialityRepository.update(newSpecObj);
    }

    public boolean deleteSpec(Speciality toDelete) {
        Long id = toDelete.getId();
        return specialityRepository.delete(id);
    }
}

