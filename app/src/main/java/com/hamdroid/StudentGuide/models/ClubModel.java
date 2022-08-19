package com.hamdroid.StudentGuide.models;

/**
 * Created by hisham on 9/6/2015.
 */
public class ClubModel {
    private String club;
    private int year;
    private float rating;
    private String duration;
    private String director;
    private String tagline;
    //@SerializedName("cast")
   // private List<Cast> castList;
    private String image;
    private String story;

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

  //  public List<Cast> getCastList() {
    //    return castList;
  //  }

   // public void setCastList(List<Cast> castList) {
      //  this.castList = castList;
  //  }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

   // public static class Cast {
    //    private String name;

     //   public String getName() {
         //   return name;
    //    }

     //   public void setName(String name) {
     //       this.name = name;
      //  }
    //}
}
