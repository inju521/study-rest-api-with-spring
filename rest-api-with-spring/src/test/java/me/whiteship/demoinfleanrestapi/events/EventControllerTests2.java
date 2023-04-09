package me.whiteship.demoinfleanrestapi.events;

import me.whiteship.demoinfleanrestapi.common.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTests2 extends BaseTest {

    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("queryEvents - findAll(page 1) 쿼리 조회")
    public void queryEventFindAll() throws Exception {
        // Given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // When & Then
        this.mockMvc.perform(get("/api/events")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))
        ;
    }

    @Test
    @DisplayName("queryEvents - findByNameContaining(name) 쿼리 조회")
    public void queryEventFindByNameContaining() throws Exception {
        // Given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // When & Then
        this.mockMvc.perform(get("/api/events")
                        .param("name", "1")
                        .param("sort", "name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))
        ;
    }

    @Test
    @DisplayName("queryEvents - findByBasePriceBetween(basePriceFilter : true) 쿼리 조회")
    public void queryEventFindByBasePriceBetween() throws Exception {
        // Given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // When & Then
        this.mockMvc.perform(get("/api/events")
                        .param("basePriceFilter", "true")
                        .param("sort", "basePrice,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))
        ;
    }

    @Test
    @DisplayName("queryEvents - findByCloseEnrollmentDateTimeAfter(closeEnrollmentDateTimeFilter : true ) 쿼리 조회")
    public void queryEventFindByCloseEnrollmentDateTimeAfterr() throws Exception {
        // Given
        IntStream.range(0, 30).forEach(this::generateEvent);
        IntStream.range(0, 10).forEach(this::generateEvent2);

        // When & Then
        this.mockMvc.perform(get("/api/events")
                        .param("closeEnrollmentDateTimeFilter", "true")
                        .param("sort", "id,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))
        ;
    }


    private Event generateEvent(int index) {
        Event event = buildEvent(index);
        return this.eventRepository.save(event);
    }

    private Event generateEvent2(int index) {
        Event event = buildEvent2(index);
        return this.eventRepository.save(event);
    }

    private Event buildEvent(int index) {
        return Event.builder()
                    .name("event " + index)
                    .description("test event")
                    .beginEnrollmentDateTime(LocalDateTime.of(2022, 11, 23, 14, 21))
                    .closeEnrollmentDateTime(LocalDateTime.of(2022, 11, 24, 14, 21))
                    .beginEventDateTime(LocalDateTime.of(2022, 11, 25, 14, 21))
                    .endEventDateTime(LocalDateTime.of(2022, 11, 26, 14, 21))
                    .basePrice(80 + index)
                    .maxPrice(200)
                    .limitOfEnrollment(100)
                    .location("강남역 D2 스타텁 팩토리")
                    .free(false)
                    .offline(true)
                    .eventStatus(EventStatus.DRAFT)
                    .build();
    }

    private Event buildEvent2(int index) {
        return Event.builder()
                .name("event " + (100 + index))
                .description("test event")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 11, 23, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 11, 24, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2023, 11, 25, 14, 21))
                .endEventDateTime(LocalDateTime.of(2023, 11, 26, 14, 21))
                .basePrice(50)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .free(false)
                .offline(true)
                .eventStatus(EventStatus.DRAFT)
                .build();
    }


}
