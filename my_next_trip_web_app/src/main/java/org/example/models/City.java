package org.example.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class City {
    //Attributes
    private int id;
    @NotBlank(message = "City name is required.")
    @Size(max = 40, message = "City name cannot be greater than 40 characters.")
    private String name;
    @NotBlank(message = "Description is required.")
    @Size(max = 40, message = "Description cannot be greater than 40 characters.")
    private String description;
    @NotBlank(message = "Image URL is required.")
    @Size(max = 40, message = "Image cannot be greater than 255 characters.")
    private String image;

    //Constructors
    public City(){};

    public City(int id, String name, String description, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
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


    //HashCode and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id && Objects.equals(name, city.name) && Objects.equals(description, city.description) && Objects.equals(image, city.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image);
    }
}
