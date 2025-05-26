package roomescape.theme.application;

import roomescape.theme.application.dto.CreateThemeRequest;
import roomescape.theme.application.dto.ThemeResponse;
import roomescape.theme.domain.ThemeId;

import java.util.List;

public interface ThemeFacade {

    List<ThemeResponse> getAll();

    List<ThemeResponse> getRanking();

    ThemeResponse create(CreateThemeRequest request);

    void delete(ThemeId id);
}
