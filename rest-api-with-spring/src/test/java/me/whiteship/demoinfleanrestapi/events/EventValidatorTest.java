package me.whiteship.demoinfleanrestapi.events;

import me.whiteship.demoinfleanrestapi.common.ErrorsResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.ResponseEntity.badRequest;

class EventValidatorTest {

    /*
     * 2023-02-29 클라우드개발팀 이인주 과제
     */

    // TODO Price
    @Test
    public void priceValidate() {
        // Given
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 11, 23, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 11, 24, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2023, 11, 25, 14, 21))
                .endEventDateTime(LocalDateTime.of(2023, 11, 26, 14, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();

        // When
        boolean basePriceCompareMaxPrice = event.getBasePrice() > event.getMaxPrice();
        boolean maxPriceCompareZero =  event.getMaxPrice() > 0;

        assertThat(basePriceCompareMaxPrice).isEqualTo(false);
        assertThat(maxPriceCompareZero).isEqualTo(true);

        // Then
        assertThat(basePriceCompareMaxPrice && maxPriceCompareZero).isEqualTo(false);

    }

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
    @Test
    public void dateTimeValidate() {
        // Given
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 11, 23, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 11, 24, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2023, 11, 25, 14, 21))
                .endEventDateTime(LocalDateTime.of(2023, 11, 26, 14, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();

        // When
        LocalDateTime endEventDateTime = event.getEndEventDateTime();  // 이벤트 마지막 시간
        boolean endEventIsBeforeBeginEvent =  endEventDateTime.isBefore(event.getBeginEventDateTime());  // 이벤트 시작 시간
        boolean endEventIsBeforeBeginEnrollment =  endEventDateTime.isBefore(event.getBeginEnrollmentDateTime()); // 등록 시작 시간
        boolean endEventIsBeforeCloseEnrollment =  endEventDateTime.isBefore(event.getCloseEnrollmentDateTime()); // 등록 마감 시간

        assertThat(endEventIsBeforeBeginEvent).isEqualTo(false);
        assertThat(endEventIsBeforeCloseEnrollment).isEqualTo(false);
        assertThat(endEventIsBeforeBeginEnrollment).isEqualTo(false);

        // Then - 인자보다 과거 일떄 true
        assertThat(endEventIsBeforeBeginEnrollment ||
                endEventIsBeforeBeginEnrollment ||
                endEventIsBeforeCloseEnrollment ).isEqualTo(false);
    }


}