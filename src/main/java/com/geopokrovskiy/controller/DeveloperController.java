package com.geopokrovskiy.controller;

import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.DeveloperRepository;
import com.geopokrovskiy.сonstants.Constants;
import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.repository.gson.GsonDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperController {

    private final DeveloperRepository developerRepository = new GsonDeveloperRepositoryImpl();

    public Developer addDeveloper(String firstName, String lastName, Speciality speciality, List<Skill> skillList) {
        Developer developer = new Developer(firstName, lastName, skillList, speciality);
        developerRepository.addNew(developer);
        return developer;
    }

    public List<Developer> getAllDevs(){
        return developerRepository.getAll().stream().filter(skill -> skill.getStatus() != Status.DELETED).toList();
    }

    public Developer updateFirstNameDeveloper(Developer oldDev, String newFirstName){
        Long id = oldDev.getId();
        String lastName = oldDev.getLastName();
        Speciality speciality = oldDev.getSpeciality();
        List<Skill> skillList = oldDev.getSkills();
        Developer newDevObj = new Developer(newFirstName, lastName, skillList, speciality);
        newDevObj.setId(id);
        return developerRepository.update(newDevObj);
    }

    public Developer updateLastNameDeveloper(Developer oldDev, String newLastName){
        Long id = oldDev.getId();
        String firstName = oldDev.getFirstName();
        Speciality speciality = oldDev.getSpeciality();
        List<Skill> skillList = oldDev.getSkills();
        Developer newDevObj = new Developer(firstName, newLastName, skillList, speciality);
        newDevObj.setId(id);
        return developerRepository.update(newDevObj);
    }

    public Developer updateListSkills(Developer oldDev, List<Skill> newSkills){
        Long id = oldDev.getId();
        String firstName = oldDev.getFirstName();
        String lastName = oldDev.getLastName();
        Speciality speciality = oldDev.getSpeciality();
        Developer newDevObj = new Developer(firstName, lastName, newSkills, speciality);
        newDevObj.setId(id);
        return developerRepository.update(newDevObj);
    }

    public Developer updateSpecialityDeveloper(Developer oldDev, Speciality newSpeciality){
        Long id = oldDev.getId();
        String firstName = oldDev.getFirstName();
        String lastName = oldDev.getLastName();
        List<Skill> skillList = oldDev.getSkills();
        Developer newDevObj = new Developer(firstName, lastName, skillList, newSpeciality);
        newDevObj.setId(id);
        return developerRepository.update(newDevObj);
    }

    public boolean deleteDeveloper(Developer toDelete) {
        Long id = toDelete.getId();
        return developerRepository.delete(id);
    }
}
