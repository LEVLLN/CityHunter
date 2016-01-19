package lk.simplecode.kz.cityhunter.model;

import com.google.gson.annotations.SerializedName;

public class Info {
    @SerializedName("caption")
    private String caption;
    @SerializedName("value")
    private String value;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Info{" +
                "caption='" + caption + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
