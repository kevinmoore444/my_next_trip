package org.example.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

public class Country {

    //Attributes
    private int id;
    @NotBlank(message = "Country name is required.")
    @Size(max = 40, message = "Country name cannot be greater than 40 characters.")
    private String name;
    @NotBlank(message = "Country Image is required.")
    @Size(max = 255, message = "Customer name cannot be greater than 255 characters.")
    private String image;
    @NotBlank(message = "Cost of Living is required.")
    private CostOfLiving costOfLiving;
    @NotBlank(message = "Description is required")
    @Size(max = 300, message = "Customer name cannot be greater than 255 characters.")
    private String description;
    @NotBlank(message = "Region is required.")
    private Region region;
    private List<Tag> tags;
    private Season seasonToAvoid;

    //Constructors
    public Country(){}

    public Country(int id, String name, String image, CostOfLiving costOfLiving, String description, Region region, List<Tag> tags, Season seasonToAvoid) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.costOfLiving = costOfLiving;
        this.description = description;
        this.region = region;
        this.tags = tags;
        this.seasonToAvoid = seasonToAvoid;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CostOfLiving getCostOfLiving() {
        return costOfLiving;
    }

    public void setCostOfLiving(CostOfLiving costOfLiving) {
        this.costOfLiving = costOfLiving;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Season getSeasonToAvoid() {
        return seasonToAvoid;
    }

    public void setSeasonToAvoid(Season seasonToAvoid) {
        this.seasonToAvoid = seasonToAvoid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Hashcode and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id == country.id && Objects.equals(name, country.name) && Objects.equals(image, country.image) && Objects.equals(costOfLiving, country.costOfLiving) && Objects.equals(region, country.region) && Objects.equals(tags, country.tags) && Objects.equals(seasonToAvoid, country.seasonToAvoid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, costOfLiving, region, tags, seasonToAvoid);
    }
}
