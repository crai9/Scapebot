package com.craiig.scapebot.commands;

import com.craiig.scapebot.models.*;
import com.craiig.scapebot.utilities.CommonUtilities;
import com.google.gson.Gson;
import com.samczsun.skype4j.chat.messages.ChatMessage;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

import java.io.IOException;
import java.util.List;

public class Weather extends Command {

    public String getName(){
        return "weather";
    }

    public String[] getAliases(){
        return new String[] {};
    }

    public void run(ChatMessage msg, List<Command> commands, String trigger) throws ConnectionException {

        String query = msg.getContent().asPlaintext().replace("!" + trigger, "").trim().replace(" ", "+");
        if(query.length() < 1){
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("No weather location queried, use "))
                    .with(Text.rich("!weather <location>").withBold())
            );
            return;
        }

        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?appid=5c3e4522d66a32d7924c5f0357a8c48c&units=metric&q=" + query;
        String response = "";
        String status = "";
        String tempEmoji = "";

        try {

            response = CommonUtilities.getTextFromUrl(apiUrl);

            Gson gson = new Gson();
            WeatherInfo details = gson.fromJson(response, WeatherInfo.class);

            if(details.getWeather().get(0).getMain().equals("Rain")) {
                status = "(rain)";
            } else if (details.getWeather().get(0).getMain().equals("Clear")){
                status = "(sun)";
            } else {
                status = details.getWeather().get(0).getMain();
            }

            if(details.getMain().getTemp() < -5){
                tempEmoji = "(shivering)";
            } else if (details.getMain().getTemp() > 35){
                tempEmoji = "(sweating)";
            }

            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("Current Weather for "))
                    .with(Text.rich(details.getName() + ", " + details.getSys().getCountry()).withBold())
                    .with(Text.NEW_LINE)
                    .with(Text.rich("Status: "))
                    .with(Text.rich(status + " (" + details.getWeather().get(0).getDescription() + ")"))
                    .with(Text.NEW_LINE)
                    .with(Text.rich("Temp: "))
                    .with(Text.rich(details.getMain().getTemp().toString() + "°C " + tempEmoji))
                    .with(Text.NEW_LINE)
                    .with(Text.rich("Humidity: "))
                    .with(Text.rich(details.getMain().getHumidity().toString() + "%"))
                    .with(Text.NEW_LINE)
                    .with(Text.rich("Clouds: "))
                    .with(Text.rich(details.getClouds().getAll() + "%"))
                    .with(Text.NEW_LINE)
                    .with(Text.rich("Wind: "))
                    .with(Text.rich(details.getWind().getSpeed().toString() + "m/s"))
            );


        } catch (Exception ex){

            System.out.println(ex.getMessage());
            msg.getChat().sendMessage(Message.create()
                    .with(Text.rich("Couldn't find weather for '" + query + "'.").withBold())
            );

        }
        return;
    }
}
