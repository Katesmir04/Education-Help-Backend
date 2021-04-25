package com.kate.eduhelp.models;

import java.util.ArrayList;

public class User {
    public String id;
    public String favoriteSubject;
    public String name;
    public String status;
    public ArrayList<String> favorites;
    public ArrayList<String> passedQuizes;
    public ArrayList<String> recentlyViewed;
    public int grade;
    public int totalBonuses;
}