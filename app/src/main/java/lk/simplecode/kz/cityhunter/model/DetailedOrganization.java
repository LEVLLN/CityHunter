package lk.simplecode.kz.cityhunter.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DetailedOrganization extends Organization {

    @SerializedName("images")
    private String[] images;

    @SerializedName("info")
    List<Info> info;

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "DetailedOrganization{" + super.getTitle() +
                "images=" + Arrays.toString(images) +
                ", info=" + info +
                '}';
    }
}
