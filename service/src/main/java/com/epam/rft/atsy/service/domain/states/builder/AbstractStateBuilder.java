package com.epam.rft.atsy.service.domain.states.builder;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.AbstractStateHistoryDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;

import java.util.Date;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractStateBuilder<B extends AbstractStateBuilder<B, T>, T extends AbstractStateHistoryDTO>
    implements Builder<T> {

  protected String name;
  protected T object;


  public B id(Long id) {
    object.setId(id);
    return (B) this;
  }

  public B candidateId(Long candidateId) {
    object.setCandidateId(candidateId);
    return (B) this;
  }

  public B position(PositionDTO position) {
    object.setPosition(position);
    return (B) this;
  }

  public B channel(ChannelDTO channel) {
    object.setChannel(channel);
    return (B) this;
  }

  public B applicationDTO(ApplicationDTO applicationDTO) {
    object.setApplicationDTO(applicationDTO);
    return (B) this;
  }

  public B description(String description) {
    object.setDescription(description);
    return (B) this;
  }

  public B result(Short result) {
    object.setResult(result);
    return (B) this;
  }

  public B offeredMoney(Long offeredMoney) {
    object.setOfferedMoney(offeredMoney);
    return (B) this;
  }

  public B claim(Long claim) {
    object.setClaim(claim);
    return (B) this;
  }

  public B feedbackDate(Date feedbackDate) {
    object.setFeedbackDate(feedbackDate);
    return (B) this;
  }

  public B dayOfStart(Date dayOfStart) {
    object.setDayOfStart(dayOfStart);
    return (B) this;
  }

  public B dateOfEnter(Date dateOfEnter) {
    object.setDateOfEnter(dateOfEnter);
    return (B) this;
  }

  public B stateDTO(StateDTO stateDTO) {
    object.setStateDTO(stateDTO);
    return (B) this;
  }

  public B recommendation(Boolean recommendation) {
    object.setRecommendation(recommendation);
    return (B) this;
  }

  public B reviewerName(String reviewerName) {
    object.setReviewerName(reviewerName);
    return (B) this;
  }

  public B recommendedPositionLevel(Short recommendedPositonLevel) {
    object.setRecommendedPositionLevel(recommendedPositonLevel);
    return (B) this;
  }

  @Override
  public T build() {
    return object;
  }

}