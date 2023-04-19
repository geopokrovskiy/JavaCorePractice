package com.geopokrovskiy.repository;

import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.model.Status;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpecialityRepository implements GenericRepository<Speciality, Long> {

    private List<Speciality> specs;
    private String filename;

    public SpecialityRepository(String filename) {
        this.filename = filename;
        try {
            List<Speciality> specs = this.fromJson(filename);
            if(specs == null){
                this.specs = new ArrayList<>();
            }
            else{
                this.specs = specs;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public boolean addNew(Speciality value) {
        Speciality maxIdSpec = this.specs.stream().max(Comparator.comparingLong(Speciality::getId)).orElse(null);
        if(maxIdSpec == null){
            value.setId(1);
        } else {
            value.setId(maxIdSpec.getId() + 1);
        }
        this.specs.add(value);
        try {
            this.toJson(this.filename);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public Speciality getById(Long aLong) {
        return this.specs.stream().filter(o -> o.getId() == aLong).findFirst().orElse(null);
    }

    @Override
    public List<Speciality> getAll() {
        return this.specs;
    }

    @Override
    public boolean update(Long aLong, Speciality value) {
        Speciality oldSpec = this.getById(aLong);
        if(oldSpec == null || oldSpec.getStatus() == Status.DELETED){
            return false;
        }
        int idx = this.specs.indexOf(oldSpec);
        this.specs.set(idx, value);
        try {
            this.toJson(this.filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(Long aLong) {
        Speciality speciality = this.getById(aLong);
        if(speciality == null || speciality.getStatus() == Status.DELETED) return false;
        speciality.setStatus(Status.DELETED);
        try {
            this.toJson(this.filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private void toJson(String filename) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            String json = new Gson().toJson(this.specs);
            writer.write(json);
        }
    }

    private List<Speciality> fromJson(String filename) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            Type typeToken = new TypeToken<List<Speciality>>() {}.getType();
            return new Gson().fromJson(reader, typeToken);
        }
    }
}
