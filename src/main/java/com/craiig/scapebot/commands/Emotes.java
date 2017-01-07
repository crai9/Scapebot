package com.craiig.scapebot.commands;

import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.craiig.scapebot.utilities.CommonUtilities.participantToUser;


public class Emotes extends Command {

    public String getName(){
        return "emotes";
    }

    public String[] getAliases(){
        return new String[] {"Kappa", "PJSalt", "FeelsGoodMan", "FeelsBadMan", "PogChamp", "TriHard", "KappaHD",
                "BibleThump", "BrokeBack", "DansGame", "HeyGuys", "NotLikeThis", "G", "WutFace", "ResidentSleeper",
                "FailFish", "ANELE", "CoolStoryBob", "KappaPride", "4Head", "haHAA", "LUL"};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        if(trigger.equals(getName())){

            String commandList = "";
            for (String name: getAliases()) {
                commandList += "!" + name + ", ";
            }
            commandList = commandList.substring(0, commandList.length()-2);

            msg.getChat().sendMessage("To use an emote, try the following commands: " + commandList + " (not case sensitive)");
            return;
        }

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

        if(pairs.containsKey(trigger)){

            String path = pairs.get(trigger);

            File emote = new File(path);

            try {
                msg.getChat().sendImage(emote);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
