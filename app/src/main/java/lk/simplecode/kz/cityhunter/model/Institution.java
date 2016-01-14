package lk.simplecode.kz.cityhunter.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Institution {

    @SerializedName("id")
    private Long id;
    @SerializedName("title")
    private String title;
    @SerializedName("date")
    private Date date;
    @SerializedName("thumb")
    private String imageUrl;
    @SerializedName("short_description")
    private String description;
    @SerializedName("address")
    private String address;
    @SerializedName("is_rec")
    private boolean isRec;

    public boolean isRec() {
        return isRec;
    }

    public void setIsRec(boolean isRec) {
        this.isRec = isRec;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Institution{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", isRec=" + isRec +
                '}';
    }
}
