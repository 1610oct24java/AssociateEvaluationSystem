package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "AES_CATEGORY")
public class Category implements Serializable {
    /**
     * @serialVersionUID An auto-generated field that is used for serialization.
     */
    private static final long serialVersionUID = 1609968850692884482L;
    /**
     * @cateogryId A unique Identifier for the Class.
     */
    @Id
    @GeneratedValue(generator = "AES_CATEGORIES_SEQ", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "AES_CATEGORIES_SEQ", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "AES_CATEGORIES_SEQ"),
            @Parameter(name = "optimizer", value = "hilo"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")
    })
    @Column(name = "CATEGORY_ID")
    private int categoryId;

    /**
     * @name A String representation of the name of the Category.
     */
    @Column(name = "CATEGORY_NAME")
    private String name;

    public Category() {
        super();
    }

    public Category(String cat) {
        this.name = cat;
    }

    public Category(int id, String cat) {
        this.name = cat;
        this.categoryId = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + categoryId;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        if (categoryId != other.categoryId)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Category [categoryId=" + categoryId + ", name=" + name + "]";
    }
}