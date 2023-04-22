package com.geopokrovskiy.repository.gson;


import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.SpecialityRepository;
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

public class GsonSpecialityRepositoryImpl implements SpecialityRepository {
    private final String filename = Constants.filenameSpecs;

    private Long generateId(List<Speciality> specialities){
        Speciality maxIdSpeciality = specialities.stream().max(Comparator.comparingLong(Speciality::getId)).orElse(null);
        return Objects.nonNull(maxIdSpeciality) ? maxIdSpeciality.getId() + 1  :  1L;
    }

    @Override
    public Speciality addNew(Speciality value) {
        List<Speciality> specialities = getSpecsFromJson();
        Long maxId = generateId(specialities);
        value.setId(maxId);
        specialities.add(value);
        writeSpecsToJson(specialities);
        return value;
    }

    @Override
    public Speciality getById(Long aLong) {
        return getSpecsFromJson().stream().filter(o -> o.getId().equals(aLong) && o.getStatus() != Status.DELETED).findFirst().orElse(null);
    }

    @Override
    public List<Speciality> getAll() {
        return getSpecsFromJson();
    }

    @Override
    public Speciality update(Speciality value) {
        List<Speciality> specialities = getSpecsFromJson();
        List<Speciality> updatedSpecialities = specialities.stream().map(speciality -> {
            if(speciality.getId().equals(value.getId())){
                return value;
            }
            return speciality;
        }).toList();
        writeSpecsToJson(updatedSpecialities);
        return value;
    }

    @Override
    public boolean delete(Long aLong) {
        AtomicBoolean isDeleted = new AtomicBoolean(false);
        List<Speciality> specialities = getSpecsFromJson();
        List<Speciality> specialitiesAfterDelete = specialities.stream().peek(speciality -> {
            if(speciality.getId().equals(aLong)){
                speciality.setStatus(Status.DELETED);
                isDeleted.set(true);
            }
        }).toList();
        writeSpecsToJson(specialitiesAfterDelete);
        return isDeleted.get();
    }

    private void writeSpecsToJson(List<Speciality> specs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename))) {
            String json = new Gson().toJson(specs);
            writer.write(json);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private List<Speciality> getSpecsFromJson() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
            Type typeToken = new TypeToken<List<Speciality>>() {
            }.getType();
            return new Gson().fromJson(reader, typeToken);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
