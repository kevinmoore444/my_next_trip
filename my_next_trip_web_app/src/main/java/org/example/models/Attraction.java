package org.example.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class Attraction {
    //Attributes
    private int id;
    @NotBlank(message = "Attraction name is required.")
    @Size(max = 50, message = "Attraction name cannot be greater than 50 characters.")
    private String name;

    @NotBlank(message = "Attraction name is required.")
    @Size(max = 500, message = "Attraction name cannot be greater than 50 characters.")
    private String description;
    @NotBlank(message = "Image is required.")
    @Size(max = 255, message = "Image cannot be greater than 50 characters.")
    private String image;

    @NotNull(message = "Must be associated with a country ID")
    @Min(value = 1, message = "Id must be a positive integer")
    private int countryId;


    //Constructors
    public Attraction(){}

    public Attraction(int id, String name, String description, String image, int countryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.countryId = countryId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    //Equals and Hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attraction that = (Attraction) o;
        return id == that.id && countryId == that.countryId && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image, countryId);
    }
}
