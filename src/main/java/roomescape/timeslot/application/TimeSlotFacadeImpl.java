package roomescape.timeslot.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.timeslot.application.dto.CreateTimeSlotRequest;
import roomescape.timeslot.application.dto.TimeSlotResponse;
import roomescape.timeslot.application.service.TimeSlotCommandService;
import roomescape.timeslot.application.service.TimeSlotQueryService;
import roomescape.timeslot.domain.TimeSlotId;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotFacadeImpl implements TimeSlotFacade {

    private final TimeSlotQueryService timeSlotQueryService;
    private final TimeSlotCommandService timeSlotCommandService;

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlotResponse> getAll() {
        return TimeSlotResponse.from(
                timeSlotQueryService.getAll());
    }

    @Override
    @Transactional
    public TimeSlotResponse create(final CreateTimeSlotRequest request) {
        return TimeSlotResponse.from(
                timeSlotCommandService.create(request));
    }

    @Override
    @Transactional
    public void delete(final TimeSlotId id) {
        timeSlotCommandService.delete(id);
    }
}
