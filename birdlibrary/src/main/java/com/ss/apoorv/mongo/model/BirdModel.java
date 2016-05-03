package com.ss.apoorv.mongo.model;

import com.ss.apoorv.util.SSConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by apoorv on 03/05/16.
 */
@Document(collection= SSConstants.BIRD_COLLECTION)
public class BirdModel implements Serializable {

    private static final long serialVersionUID = 7033568859833621879L;

    @Id
    private String id;
    private String name;
    private String family;
    private Set<String> continents;
    private Date added = new Date();
    private boolean visible = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Set<String> getContinents() {
        return continents;
    }

    public void setContinents(Set<String> continents) {
        this.continents = continents;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
