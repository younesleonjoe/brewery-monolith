package com.younesleonjoe.brewerymonolith.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class BeerPagedDTO extends PageImpl<BeerDTO> {

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public BeerPagedDTO(
      @JsonProperty("content") List<BeerDTO> content,
      @JsonProperty("number") int number,
      @JsonProperty("size") int size,
      @JsonProperty("totalElements") Long totalElements,
      @JsonProperty("pageable") JsonNode pageable,
      @JsonProperty("last") boolean last,
      @JsonProperty("totalPages") int totalPages,
      @JsonProperty("sort") JsonNode sort,
      @JsonProperty("first") boolean first,
      @JsonProperty("numberOfElements") int numberOfElements) {
    super(content, PageRequest.of(number, size), totalElements);
  }

  public BeerPagedDTO(List<BeerDTO> content, Pageable pageable, long total) {
    super(content, pageable, total);
  }

  public BeerPagedDTO(List<BeerDTO> content) {
    super(content);
  }
}
