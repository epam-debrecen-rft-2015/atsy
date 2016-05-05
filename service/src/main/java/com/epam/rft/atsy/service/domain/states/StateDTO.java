package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Date;

public class StateDTO extends AbstractStateDTO{

    private Date creationDate;
    private ChannelDTO channel;

    public StateDTO() {
    }

    public StateDTO(Long id, Long candidateId, PositionDTO position, ApplicationDTO applicationDTO, Short languageSkill, String description, String result, Long offeredMoney, Long claim, Date feedbackDate, String stateType, Integer stateIndex, Date creationDate, ChannelDTO channel) {
        super(id, candidateId, position, applicationDTO, languageSkill, description, result, offeredMoney, claim, feedbackDate, stateType, stateIndex);
        this.creationDate = creationDate;
        this.channel = channel;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public ChannelDTO getChannel() {
        return channel;
    }

    public void setChannel(ChannelDTO channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateDTO)) return false;
        if (!super.equals(o)) return false;

        StateDTO stateDTO = (StateDTO) o;

        if (creationDate != null ? !creationDate.equals(stateDTO.creationDate) : stateDTO.creationDate != null)
            return false;
        return !(channel != null ? !channel.equals(stateDTO.channel) : stateDTO.channel != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StateDTO{" +
                "creationDate=" + creationDate +
                ", channel=" + channel +
                '}';
    }
}
