package com.geopokrovskiy.controller;

import com.geopokrovskiy.Constants.Constants;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.repository.SkillRepository;

public class SkillController {

    public static void addNewSkill(String name) {
        Skill skill = new Skill(name);
        if (!new SkillRepository(Constants.filenameSkills).addNew(skill)) {
            System.out.println("Impossible to add the skill");
        } else {
            System.out.println("The skill has been successfully added!");
        }
    }

    public static void updateSkill(Skill oldSkill, String newSkill) {
        long id = oldSkill.getId();
        Skill newSkillObj = new Skill(newSkill);
        newSkillObj.setId(id);
        if (!new SkillRepository(Constants.filenameSkills).update(id, newSkillObj)) {
            System.out.println("The skill has been deleted or does not exist");
        } else {
            System.out.println("The skill has been successfully updated!");
        }
    }

    public static void deleteSkill(Skill toDelete) {
        long id = toDelete.getId();
        if (!new SkillRepository(Constants.filenameSkills).delete(id)) {
            System.out.println("The skill has been already deleted or does not exist.");
        } else {
            System.out.println("The skill has been successfully deleted!");
        }
    }
}