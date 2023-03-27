package model;

public class Plant {
    private int id;
    private String name;
    private String type;
    private String species;
    private String zone;
    private boolean is_carnivorous;

    public Plant() {
    }

    public Plant(int id, String name, String type, String species, String zone, boolean is_carnivorous) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.species = species;
        this.zone = zone;
        this.is_carnivorous = is_carnivorous;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", species='" + species + '\'' +
                ", zone='" + zone + '\'' +
                ", is_carnivorous=" + is_carnivorous +
                '}';
    }

    public boolean isIs_carnivorous() {
        return is_carnivorous;
    }

    public void setIs_carnivorous(boolean is_carnivorous) {
        this.is_carnivorous = is_carnivorous;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getZone() {
        return zone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}

