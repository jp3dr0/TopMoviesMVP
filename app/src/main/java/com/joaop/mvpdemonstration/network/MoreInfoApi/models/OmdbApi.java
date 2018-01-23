
package com.joaop.mvpdemonstration.network.MoreInfoApi.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OmdbApi {

    @SerializedName("Actors")
    private String mActors;
    @SerializedName("Awards")
    private String mAwards;
    @SerializedName("Country")
    private String mCountry;
    @SerializedName("Director")
    private String mDirector;
    @SerializedName("Genre")
    private String mGenre;
    @SerializedName("imdbID")
    private String mImdbID;
    @SerializedName("imdbRating")
    private String mImdbRating;
    @SerializedName("imdbVotes")
    private String mImdbVotes;
    @SerializedName("Language")
    private String mLanguage;
    @SerializedName("Metascore")
    private String mMetascore;
    @SerializedName("Plot")
    private String mPlot;
    @SerializedName("Poster")
    private String mPoster;
    @SerializedName("Rated")
    private String mRated;
    @SerializedName("Ratings")
    private List<Rating> mRatings;
    @SerializedName("Released")
    private String mReleased;
    @SerializedName("Response")
    private String mResponse;
    @SerializedName("Runtime")
    private String mRuntime;
    @SerializedName("Title")
    private String mTitle;
    @SerializedName("totalSeasons")
    private String mTotalSeasons;
    @SerializedName("Type")
    private String mType;
    @SerializedName("Writer")
    private String mWriter;
    @SerializedName("Year")
    private String mYear;

    public String getActors() {
        return mActors;
    }

    public void setActors(String Actors) {
        mActors = Actors;
    }

    public String getAwards() {
        return mAwards;
    }

    public void setAwards(String Awards) {
        mAwards = Awards;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String Country) {
        mCountry = Country;
    }

    public String getDirector() {
        return mDirector;
    }

    public void setDirector(String Director) {
        mDirector = Director;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String Genre) {
        mGenre = Genre;
    }

    public String getImdbID() {
        return mImdbID;
    }

    public void setImdbID(String imdbID) {
        mImdbID = imdbID;
    }

    public String getImdbRating() {
        return mImdbRating;
    }

    public void setImdbRating(String imdbRating) {
        mImdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return mImdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        mImdbVotes = imdbVotes;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String Language) {
        mLanguage = Language;
    }

    public String getMetascore() {
        return mMetascore;
    }

    public void setMetascore(String Metascore) {
        mMetascore = Metascore;
    }

    public String getPlot() {
        return mPlot;
    }

    public void setPlot(String Plot) {
        mPlot = Plot;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String Poster) {
        mPoster = Poster;
    }

    public String getRated() {
        return mRated;
    }

    public void setRated(String Rated) {
        mRated = Rated;
    }

    public List<Rating> getRatings() {
        return mRatings;
    }

    public void setRatings(List<Rating> Ratings) {
        mRatings = Ratings;
    }

    public String getReleased() {
        return mReleased;
    }

    public void setReleased(String Released) {
        mReleased = Released;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String Response) {
        mResponse = Response;
    }

    public String getRuntime() {
        return mRuntime;
    }

    public void setRuntime(String Runtime) {
        mRuntime = Runtime;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        mTitle = Title;
    }

    public String getTotalSeasons() {
        return mTotalSeasons;
    }

    public void setTotalSeasons(String totalSeasons) {
        mTotalSeasons = totalSeasons;
    }

    public String getType() {
        return mType;
    }

    public void setType(String Type) {
        mType = Type;
    }

    public String getWriter() {
        return mWriter;
    }

    public void setWriter(String Writer) {
        mWriter = Writer;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String Year) {
        mYear = Year;
    }

}
