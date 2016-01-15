package lk.simplecode.kz.cityhunter.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuOfOrganization {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MenuOfOrganization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
