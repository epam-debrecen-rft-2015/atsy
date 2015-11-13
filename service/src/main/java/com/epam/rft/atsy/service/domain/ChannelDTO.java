package com.epam.rft.atsy.service.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Ikantik.
 */
public class ChannelDTO implements Serializable {
    private Long channelId;
    @NotNull
    @Size(min = 1)
    private String name;

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
