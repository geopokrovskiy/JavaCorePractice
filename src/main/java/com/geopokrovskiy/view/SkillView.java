package com.geopokrovskiy.view;

import com.geopokrovskiy.сonstants.Constants;
import com.geopokrovskiy.controller.SkillController;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.repository.gson.GsonSkillRepositoryImpl;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SkillView {
    public static void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You are in skills menu. What would you like to do?");
        System.out.println("1) Add new skill");
        System.out.println("2) Update data of a skill");
        System.out.println("3) Delete a skill");
        System.out.println("4) To Main Menu");
        List<Skill> skillList = new GsonSkillRepositoryImpl(Constants.filenameSkills).getAll().
                stream().filter(skill -> skill.getStatus() != Status.DELETED).toList();
        System.out.println("List of skills: " + skillList);
        while (true) {
            int skillOption = 4;
            try {
                skillOption = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Incorrect input!");
            }
            if (skillOption == 1) {
                String skillName = "";
                while (skillName.length() < 1) {
                    System.out.println("Enter the name of the skill: ");
                    skillName = scanner.nextLine();
                }
                SkillController.addNewSkill(skillName);
                break;
            } else if (skillOption == 2) {
                Skill oldSkill = null;
                while (oldSkill == null) {
                    System.out.println("Which skill would you like to update? Select its ID.");
                    for (Skill skill : skillList) {
                        System.out.println(skill.getId() + ") " + skill.getName());
                    }
                    long id;
                    try {
                        id = scanner.nextLong();
                        oldSkill = skillList.stream().filter(skill -> skill.getId() == id).findFirst().orElse(null);
                    } catch (InputMismatchException e){
                        System.out.println("Incorrect input!");
                    }
                }
                String newSkill = "";
                while (newSkill.length() < 1) {
                    System.out.println("Enter the new name of the skill.");
                    newSkill = scanner.nextLine();
                }
                SkillController.updateSkill(oldSkill, newSkill);
                break;
            } else if (skillOption == 3) {
                Skill toDelete = null;
                while (toDelete == null) {
                    System.out.println("Which skill would you like to delete? Select its ID.");
                    skillList.forEach(skill -> {
                        System.out.println(skill.getId() + ") " + skill.getName());
                    });
                    long id;
                    try {
                        id = scanner.nextLong();
                        toDelete = skillList.stream().filter(skill -> skill.getId() == id).findFirst().orElse(null);
                    } catch (InputMismatchException e){
                        System.out.println("Incorrect input!");
                    }
                }
                SkillController.deleteSkill(toDelete);
                break;
            } else if (skillOption == 4) {
                break;
            }
        }
    }
}
