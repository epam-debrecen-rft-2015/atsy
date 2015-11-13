package com.epam.rft.atsy.persistence.entities;

import javax.persistence.*;

/**
 * Created by mates on 10/22/2015.
 */
@Entity
@Table(name = "Channels", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ChannelEntity implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "channelId")
    private Long channelId;
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
     * @param name       is the name of the position
     */

    public ChannelEntity(String name) {
        this.name = name;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
