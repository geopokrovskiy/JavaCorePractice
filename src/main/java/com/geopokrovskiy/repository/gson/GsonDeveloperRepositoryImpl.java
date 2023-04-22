package com.geopokrovskiy.repository.gson;

import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.DeveloperRepository;
import com.geopokrovskiy.сonstants.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class GsonDeveloperRepositoryImpl implements DeveloperRepository {
    private final String filename = Constants.filenameDevs;


    private Long generateId(List<Developer> developers){
        Developer maxIdDeveloper = developers.stream().max(Comparator.comparingLong(Developer::getId)).orElse(null);
        return Objects.nonNull(maxIdDeveloper) ? maxIdDeveloper.getId() + 1  :  1L;
    }
    @Override
    public Developer addNew(Developer value) {
        List<Developer> developerList = getDevsFromJson();
        Long maxId = generateId(developerList);
        value.setId(maxId);
        developerList.add(value);
        writeDevsToJson(developerList);
        return value;
    }

    @Override
    public Developer getById(Long aLong) {
        return getDevsFromJson().stream().filter(o -> (o.getId().equals(aLong))
                && o.getStatus() != Status.DELETED).findFirst().orElse(null);
    }

    @Override
    public List<Developer> getAll() {
        return getDevsFromJson();
    }

    @Override
    public Developer update(Developer value) {
        List<Developer> developers = getDevsFromJson();
        List<Developer> updatedDevelopers = developers.stream().map(developer -> {
            if(developer.getId().equals(value.getId())){
                return value;
            }
            return developer;
        }).toList();
        writeDevsToJson(updatedDevelopers);
        return value;
    }

    @Override
    public boolean delete(Long aLong) {
        AtomicBoolean isDeleted = new AtomicBoolean(false);
        List<Developer> developers = getDevsFromJson();
        List<Developer> developersAfterDelete = developers.stream().peek( developer -> {
            if(developer.getId().equals(aLong)){
                developer.setStatus(Status.DELETED);
                isDeleted.set(true);
            }
        }).toList();
        writeDevsToJson(developersAfterDelete);
        return isDeleted.get();
    }

    private void writeDevsToJson(List<Developer> developers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename))) {
            String json = new Gson().toJson(developers);
            writer.write(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<Developer> getDevsFromJson() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
            Type typeToken = new TypeToken<List<Developer>>() {
            }.getType();
            return new Gson().fromJson(reader, typeToken);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
