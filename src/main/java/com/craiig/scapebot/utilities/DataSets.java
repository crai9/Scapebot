package com.craiig.scapebot.utilities;

import java.util.HashMap;

public class DataSets {

    public static HashMap<String, Integer> getSkillID(){

        HashMap<String, Integer> skills = new HashMap();

        skills.put("attack", 0);
        skills.put("defence", 1);
        skills.put("strength", 2);
        skills.put("constitution", 3);
        skills.put("ranged", 4);
        skills.put("prayer", 5);
        skills.put("magic", 6);
        skills.put("cooking", 7);
        skills.put("woodcutting", 8);
        skills.put("fletching", 9);
        skills.put( "fishing", 10);
        skills.put( "firemaking", 11);
        skills.put( "crafting", 12);
        skills.put( "smithing", 13);
        skills.put( "mining", 14);
        skills.put( "herblore", 15);
        skills.put( "agility", 16);
        skills.put( "thieving", 17);
        skills.put( "slayer", 18);
        skills.put( "farming", 19);
        skills.put( "runecrafting", 20);
        skills.put( "hunter", 21);
        skills.put( "construction", 22);
        skills.put( "summoning", 23);
        skills.put( "dungeoneering", 24);
        skills.put( "divination", 25);
        skills.put( "invention", 26);

        return skills;
    }

    public static HashMap<Integer, String> skillShortNames(){

        HashMap<Integer, String> skills = new HashMap();

        skills.put(0, "Attack");
        skills.put(1, "Def");
        skills.put(2, "Str");
        skills.put(3, "HP");
        skills.put(4, "Range");
        skills.put(5, "Pray");
        skills.put(6, "Magic");
        skills.put(7, "Cook");
        skills.put(8, "WC");
        skills.put(9, "Fletch");
        skills.put(10, "Fish");
        skills.put(11, "FM");
        skills.put(12, "Craft");
        skills.put(13, "Smith");
        skills.put(14, "Mine");
        skills.put(15, "Herb");
        skills.put(16, "Agil");
        skills.put(17, "Thieve");
        skills.put(18, "Slay");
        skills.put(19, "Farm");
        skills.put(20, "RC");
        skills.put(21, "Hunt");
        skills.put(22, "Con");
        skills.put(23, "Summ");
        skills.put(24, "DG");
        skills.put(25, "Div");
        skills.put(26, "Invent");

        return skills;
    }

    public static HashMap<String, String> emotes(){

        HashMap<String, String> pairs = new HashMap();

        pairs.put("kappa", "img/kappa.png");
        pairs.put("pjsalt", "img/pjsalt.png");
        pairs.put("feelsgoodman", "img/feelsgoodman.png");
        pairs.put("feelsbadman", "img/feelsbadman.png");
        pairs.put("pogchamp", "img/pogchamp.jpg");
        pairs.put("trihard", "img/trihard.jpg");
        pairs.put("kappahd", "img/kappahd.jpg");
        pairs.put("biblethump", "img/biblethump.jpg");
        pairs.put("brokeback", "img/brokeback.png");
        pairs.put("dansgame", "img/dansgame.png");
        pairs.put("heyguys", "img/heyguys.png");
        pairs.put("notlikethis", "img/notlikethis.png");
        pairs.put("g", "img/parents.jpg");
        pairs.put("wutface", "img/wutface.png");
        pairs.put("residentsleeper", "img/residentsleeper.png");
        pairs.put("failfish", "img/failfish.png");
        pairs.put("anele", "img/anele.png");
        pairs.put("coolstorybob", "img/coolstorybob.png");
        pairs.put("4head", "img/4head.jpg");
        pairs.put("kappapride", "img/kappapride.jpg");
        pairs.put("hahaa", "img/hahaa.jpg");
        pairs.put("lul", "img/lul.png");
        pairs.put("thinking", "img/thinking.png");

        return pairs;
    }
}
