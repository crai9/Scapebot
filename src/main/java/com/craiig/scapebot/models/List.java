
package com.craiig.scapebot.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("thumbs_up")
    @Expose
    private Integer thumbsUp;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("defid")
    @Expose
    private Integer defid;
    @SerializedName("current_vote")
    @Expose
    private String currentVote;
    @SerializedName("example")
    @Expose
    private String example;
    @SerializedName("thumbs_down")
    @Expose
    private Integer thumbsDown;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Integer getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Integer thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getDefid() {
        return defid;
    }

    public void setDefid(Integer defid) {
        this.defid = defid;
    }

    public String getCurrentVote() {
        return currentVote;
    }

    public void setCurrentVote(String currentVote) {
        this.currentVote = currentVote;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Integer getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(Integer thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

}
