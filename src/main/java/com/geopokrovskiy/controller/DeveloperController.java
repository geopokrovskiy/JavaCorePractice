package com.geopokrovskiy.controller;

import com.geopokrovskiy.сonstants.Constants;
import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.repository.DeveloperRepository;

import java.util.List;

public class DeveloperController {

    public static void addDeveloper(String firstName, String lastName, Speciality speciality, List<Skill> skillList) {
        Developer developer = new Developer(firstName, lastName, skillList, speciality);
        if (!new DeveloperRepository(Constants.filenameDevs).addNew(developer)) {
            System.out.println("Impossible to add the developer");
        } else {
            System.out.println("The developer has been successfully added!");
        }
    }

    public static void updateFirstNameDeveloper(Developer oldDev, String newFirstName){
        long id = oldDev.getId();
        String lastName = oldDev.getLastName();
        Speciality speciality = oldDev.getSpeciality();
        List<Skill> skillList = oldDev.getSkills();
        Developer newDevObj = new Developer(newFirstName, lastName, skillList, speciality);
        newDevObj.setId(id);
        if (!new DeveloperRepository(Constants.filenameDevs).update(id, newDevObj)) {
            System.out.println("The developer has been deleted or does not exist");
        } else {
            System.out.println("The developer has been successfully updated!");
        }
    }

    public static void updateLastNameDeveloper(Developer oldDev, String newLastName){
        long id = oldDev.getId();
        String firstName = oldDev.getFirstName();
        Speciality speciality = oldDev.getSpeciality();
        List<Skill> skillList = oldDev.getSkills();
        Developer newDevObj = new Developer(firstName, newLastName, skillList, speciality);
        newDevObj.setId(id);
        if (!new DeveloperRepository(Constants.filenameDevs).update(id, newDevObj)) {
            System.out.println("The developer has been deleted or does not exist");
        } else {
            System.out.println("The developer has been successfully updated!");
        }
    }

    public static void updateListSkills(Developer oldDev, List<Skill> newSkills){
        long id = oldDev.getId();
        String firstName = oldDev.getFirstName();
        String lastName = oldDev.getLastName();
        Speciality speciality = oldDev.getSpeciality();
        Developer newDevObj = new Developer(firstName, lastName, newSkills, speciality);
        newDevObj.setId(id);
        if (!new DeveloperRepository(Constants.filenameDevs).update(id, newDevObj)) {
            System.out.println("The developer has been deleted or does not exist");
        } else {
            System.out.println("The developer has been successfully updated!");
        }
    }

    public static void updateSpecialityDeveloper(Developer oldDev, Speciality newSpeciality){
        long id = oldDev.getId();
        String firstName = oldDev.getFirstName();
        String lastName = oldDev.getLastName();
        List<Skill> skillList = oldDev.getSkills();
        Developer newDevObj = new Developer(firstName, lastName, skillList, newSpeciality);
        newDevObj.setId(id);
        if (!new DeveloperRepository(Constants.filenameDevs).update(id, newDevObj)) {
            System.out.println("The developer has been deleted or does not exist");
        } else {
            System.out.println("The developer has been successfully updated!");
        }
    }

    public static void deleteDeveloper(Developer toDelete) {
        long id = toDelete.getId();
        if (!new DeveloperRepository(Constants.filenameDevs).delete(id)) {
            System.out.println("The developer has been already deleted or does not exist.");
        } else {
            System.out.println("The developer has been successfully deleted!");
        }
    }
}
