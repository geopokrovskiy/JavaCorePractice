package com.geopokrovskiy.controller;

import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.SkillRepository;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.repository.gson.GsonSkillRepositoryImpl;

import java.util.List;

public class SkillController {
    private final SkillRepository skillRepository = new GsonSkillRepositoryImpl();

    public Skill addNewSkill(String name) {
        Skill skill = new Skill(name);
        skillRepository.addNew(skill);
        return skill;
    }

    public List<Skill> getAllSkills(){
        return skillRepository.getAll().stream().filter(skill -> skill.getStatus() != Status.DELETED).toList();
    }

    public Skill updateSkill(Skill oldSkill, String newSkill) {
        Skill newSkillObj = new Skill(newSkill);
        newSkillObj.setId(oldSkill.getId());
        return skillRepository.update(newSkillObj);
    }

    public boolean deleteSkill(Skill toDelete) {
        Long id = toDelete.getId();
        return skillRepository.delete(id);
    }
}
