package com.geopokrovskiy.repository.gson;

import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.SkillRepository;
import com.geopokrovskiy.сonstants.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GsonSkillRepositoryImpl implements SkillRepository {
    private final String filename = Constants.filenameSkills;

    private Long generateId(List<Skill> skills){
        Skill maxIdSkill = skills.stream().max(Comparator.comparingLong(Skill::getId)).orElse(null);
        return Objects.nonNull(maxIdSkill) ? maxIdSkill.getId() + 1  :  1L;
    }
    @Override
    public Skill addNew(Skill value) {
        List<Skill> skills = getSkillsFromJson();
        Long maxId = generateId(skills);
        value.setId(maxId);
        skills.add(value);
        writeSkillsToJson(skills);
        return value;
    }

    @Override
    public Skill getById(Long id) {
        return getSkillsFromJson().stream().filter(o -> o.getId().equals(id) && o.getStatus() != Status.DELETED).findFirst().orElse(null);
    }

    @Override
    public List<Skill> getAll() {
        return getSkillsFromJson();
    }

    @Override
    public Skill update(Skill value) {
        List<Skill> skills = getSkillsFromJson();
        List<Skill> updatedSkills = skills.stream().map(s -> {
            if(s.getId().equals(value.getId())){
                return value;
            }
            return s;
        }).toList();
        writeSkillsToJson(updatedSkills);
        return value;
    }

    @Override
    public boolean delete(Long aLong) {
        AtomicBoolean isDeleted = new AtomicBoolean(false);
        List<Skill> skills = getSkillsFromJson();
        List<Skill> skillsAfterDelete = skills.stream().peek(s -> {
            if(s.getId().equals(aLong)){
                s.setStatus(Status.DELETED);
                isDeleted.set(true);
            }
        }).toList();
        writeSkillsToJson(skillsAfterDelete);
        return isDeleted.get();
    }

    private void writeSkillsToJson(List<Skill> skills) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            String json = new Gson().toJson(skills);
            writer.write(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<Skill> getSkillsFromJson() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
            Type typeToken = new TypeToken<List<Skill>>() {
            }.getType();
            return new Gson().fromJson(reader, typeToken);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
