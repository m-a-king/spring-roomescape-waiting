package roomescape.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.auth.aop.ForbiddenException;
import roomescape.auth.session.UserSession;
import roomescape.reservation.application.dto.CreateReservationRequest;
import roomescape.reservation.application.dto.ReservationResponse;
import roomescape.reservation.application.dto.ReservationSearchFilterRequest;
import roomescape.reservation.application.dto.SlotSequenceResponse;
import roomescape.reservation.application.service.ReservationCommandService;
import roomescape.reservation.application.service.ReservationQueryService;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationId;
import roomescape.theme.application.service.ThemeQueryService;
import roomescape.theme.domain.Theme;
import roomescape.timeslot.application.service.TimeSlotQueryService;
import roomescape.timeslot.domain.TimeSlot;
import roomescape.user.application.service.UserQueryService;
import roomescape.user.domain.User;
import roomescape.user.domain.UserId;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationFacadeImpl implements ReservationFacade {

    private final ReservationQueryService reservationQueryService;
    private final ReservationCommandService reservationCommandService;
    private final UserQueryService userQueryService;
    private final TimeSlotQueryService timeSlotQueryService;
    private final ThemeQueryService themeQueryService;

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getAll() {
        final List<Reservation> reservations = reservationQueryService.getAll();
        final List<UserId> userIds = reservations.stream()
                .map(Reservation::getUserId)
                .toList();

        final List<User> users = userQueryService.getAllByIds(userIds);
        return ReservationResponse.from(reservations, users);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getAllBySearchFilter(final ReservationSearchFilterRequest request) {
        final List<Reservation> reservations = reservationQueryService.getAllBySearchFilter(request);
        final List<UserId> userIds = reservations.stream()
                .map(Reservation::getUserId)
                .toList();

        final List<User> users = userQueryService.getAllByIds(userIds);
        return ReservationResponse.from(reservations, users);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SlotSequenceResponse> getAllSlotSequenceByUserId(final UserId userId) {
        return reservationQueryService.getAllSlotSequenceResponseByUserId(userId);
    }

    @Override
    @Transactional
    public ReservationResponse create(final CreateReservationRequest request) {
        final TimeSlot timeSlot = timeSlotQueryService.get(request.timeId());
        final Theme theme = themeQueryService.get(request.themeId());

        final Reservation reservation = reservationCommandService.create(
                request.toDomain(timeSlot, theme));

        final User user = userQueryService.getById(reservation.getUserId());
        return ReservationResponse.from(reservation, user);
    }

    @Override
    @Transactional
    public void delete(final ReservationId id, final UserSession userSession) {
        final Reservation target = reservationQueryService.getById(id);
        if (userSession.canManage(target.getUserId())) {
            reservationCommandService.delete(target);
            return;
        }
        throw new ForbiddenException(userSession.id(), userSession.role(), target.getUserId());
    }
}
