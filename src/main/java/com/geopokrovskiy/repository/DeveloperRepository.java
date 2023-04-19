package com.geopokrovskiy.repository;

import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Status;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DeveloperRepository implements GenericRepository<Developer, Long> {

    private List<Developer> developers;
    private String filename;

    public DeveloperRepository(String filename) {
        this.filename = filename;
        try {
            List<Developer> developers = this.fromJson(filename);
            if(developers == null){
                this.developers = new ArrayList<>();
            }
            else{
                this.developers = developers;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addNew(Developer value) {
        Developer maxIdDev = this.developers.stream().max(Comparator.comparingLong(Developer::getId)).orElse(null);
        if(maxIdDev == null){
            value.setId(1);
        } else {
            value.setId(maxIdDev.getId() + 1);
        }
        this.developers.add(value);
        try {
            this.toJson(this.filename);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public Developer getById(Long aLong) {
        return this.developers.stream().
                filter(o -> (o.getId() == aLong)
                && o.getStatus() == Status.ACTIVE).
                findFirst().
                orElse(null);
    }

    @Override
    public List<Developer> getAll() {
        return this.developers;
    }

    @Override
    public boolean update(Long aLong, Developer value) {
        Developer oldDev = this.getById(aLong);
        if(oldDev == null || oldDev.getStatus() == Status.DELETED) return false;
        int idx = this.developers.indexOf(oldDev);
        this.developers.set(idx, value);
        try {
            this.toJson(this.filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(Long aLong) {
        Developer developer = this.getById(aLong);
        if(developer == null || developer.getStatus() == Status.DELETED) return false;
        developer.setStatus(Status.DELETED);
        try {
            this.toJson(this.filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private void toJson(String filename) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            String json = new Gson().toJson(this.developers);
            writer.write(json);
        }
    }

    private List<Developer> fromJson(String filename) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            Type typeToken = new TypeToken<List<Developer>>() {}.getType();
            return new Gson().fromJson(reader, typeToken);
        }
    }
}
