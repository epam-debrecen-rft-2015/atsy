package com.epam.rft.atsy.persistence.entities;

import javax.persistence.*;

@Entity
@Table(name = "channels", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ChannelEntity extends SuperEntity implements java.io.Serializable {

    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;

    /**
     * Empty constructor.
     */
    public ChannelEntity() {
    }

    /**
     * Full constructor.
     *
     * @param name is the name of the position
     */

    public ChannelEntity(String name) {
        this.name = name;
    }

    public ChannelEntity(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ChannelEntity that = (ChannelEntity) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChannelEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
