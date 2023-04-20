package com.geopokrovskiy.repository.gson;

import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.GenericRepository;
import com.geopokrovskiy.repository.SkillRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GsonSkillRepositoryImpl implements SkillRepository {

    private List<Skill> skills;
    private final String filename;

    public GsonSkillRepositoryImpl(String filename) {
        this.filename = filename;
        try {
            List<Skill> skills = this.fromJson(filename);
            if(skills == null){
                this.skills = new ArrayList<>();
            }
            else{
                this.skills = skills;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public boolean addNew(Skill value) {
        Skill maxIdSkill = this.skills.stream().max(Comparator.comparingLong(Skill::getId)).orElse(null);
        if(maxIdSkill == null){
            value.setId(1);
        } else {
            value.setId(maxIdSkill.getId() + 1);
        }
        this.skills.add(value);
        try {
            this.toJson(this.filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Skill getById(Long aLong) {
        return this.skills.stream().filter(o -> o.getId() == aLong).findFirst().orElse(null);
    }

    @Override
    public List<Skill> getAll() {
        return this.skills;
    }

    @Override
    public boolean update(Long aLong, Skill value) {
        Skill oldSkill = this.getById(aLong);
        if(oldSkill == null || oldSkill.getStatus() == Status.DELETED){
            return false;
        }
        int idx = this.skills.indexOf(oldSkill);
        this.skills.set(idx, value);
        try {
            this.toJson(this.filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(Long aLong) {
        Skill skill = this.getById(aLong);
        if(skill == null || skill.getStatus() == Status.DELETED) return false;
        skill.setStatus(Status.DELETED);
        try {
            this.toJson(this.filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private void toJson(String filename) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            String json = new Gson().toJson(this.skills);
            writer.write(json);
        }
    }

    private List<Skill> fromJson(String filename) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            Type typeToken = new TypeToken<List<Skill>>() {}.getType();
            return new Gson().fromJson(reader, typeToken);
        }
    }
}
