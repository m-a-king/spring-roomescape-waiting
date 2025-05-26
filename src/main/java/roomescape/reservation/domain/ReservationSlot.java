package roomescape.reservation.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import roomescape.common.domain.DomainTerm;
import roomescape.common.validate.Validator;
import roomescape.theme.domain.Theme;
import roomescape.timeslot.domain.ReservationTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Getter
@ToString
public class ReservationSlot {

    private final ReservationDate date;
    private final ReservationTime time;
    private final Theme theme;

    public static ReservationSlot of(final ReservationDate date, final ReservationTime time, final Theme theme) {
        validate(date, time, theme);
        return new ReservationSlot(date, time, theme);
    }

    private static void validate(final ReservationDate date, final ReservationTime time, final Theme theme) {
        Validator.of(ReservationSlot.class)
                .validateNotNull(Fields.date, date, DomainTerm.RESERVATION_DATE.label())
                .validateNotNull(Fields.time, time, DomainTerm.RESERVATION_TIME.label())
                .validateNotNull(Fields.theme, theme, DomainTerm.THEME.label());
    }
}
