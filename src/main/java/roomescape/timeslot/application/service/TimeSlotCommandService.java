package roomescape.timeslot.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.common.domain.DomainTerm;
import roomescape.common.exception.DuplicateException;
import roomescape.common.exception.NotFoundException;
import roomescape.timeslot.application.dto.CreateTimeSlotRequest;
import roomescape.timeslot.domain.TimeSlot;
import roomescape.timeslot.domain.TimeSlotId;
import roomescape.timeslot.domain.TimeSlotRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TimeSlotCommandService {

    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotQueryService timeSlotQueryService;

    public TimeSlot create(final CreateTimeSlotRequest request) {
        if (timeSlotQueryService.existsByStartAt(request.startAt())) {
            throw new DuplicateException(DomainTerm.TIME_SLOT, request.startAt());
        }
        return timeSlotRepository.save(
                request.toDomain());
    }

    public void delete(final TimeSlotId id) {
        if (!timeSlotQueryService.existById(id)) {
            throw new NotFoundException(DomainTerm.TIME_SLOT, id);
        }
        timeSlotRepository.deleteById(id);
    }
}
