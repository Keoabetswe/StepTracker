package com.example.keo.steptracker;

import java.io.Serializable;

public class Account //implements Serializable
{
    private static int id;
    private static String name;
    private static String surname;
    private static String email;
    private static String password;
    private static String dob;
    private static String weight;
    private static String height;
    private static String steps_goal;
    private static String weight_goal;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Account.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Account.name = name;
    }

    public static String getSurname() {
        return surname;
    }

    public static void setSurname(String surname) {
        Account.surname = surname;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Account.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Account.password = password;
    }

    public static String getDob() {
        return dob;
    }

    public static void setDob(String dob) {
        Account.dob = dob;
    }

    public static String getWeight() {
        return weight;
    }

    public static void setWeight(String weight) {
        Account.weight = weight;
    }

    public static String getHeight() {
        return height;
    }

    public static void setHeight(String height) {
        Account.height = height;
    }

    public static String getSteps_goal() {
        return steps_goal;
    }

    public static void setSteps_goal(String steps_goal) {
        Account.steps_goal = steps_goal;
    }

    public static String getWeight_goal() {
        return weight_goal;
    }

    public static void setWeight_goal(String weight_goal) {
        Account.weight_goal = weight_goal;
    }
}
