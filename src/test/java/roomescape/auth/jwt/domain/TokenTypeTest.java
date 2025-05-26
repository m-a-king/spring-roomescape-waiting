package roomescape.auth.jwt.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenTypeTest {

    @Test
    @DisplayName("ACCESS 토큰의 기간이 밀리초 단위로 반환된다")
    void accessTokenPeriodIsReturnedInMillis() {
        // given
        final TokenType accessTokenType = TokenType.ACCESS;

        // when
        final int periodInMillis = accessTokenType.getPeriodInMillis();

        // then
        assertThat(periodInMillis).isEqualTo(1_800_000);
    }

    @Test
    @DisplayName("ACCESS 토큰의 기간이 초 단위로 반환된다")
    void accessTokenPeriodIsReturnedInSeconds() {
        // given
        final TokenType accessTokenType = TokenType.ACCESS;

        // when
        final int periodInSeconds = accessTokenType.getPeriodInSeconds();

        // then
        assertThat(periodInSeconds).isEqualTo(1800);
    }

    @Test
    @DisplayName("DELETE 토큰의 기간이 밀리초 단위로 반환된다")
    void deleteTokenPeriodIsReturnedInMillis() {
        // when
        final int periodInMillis = TokenType.DELETE.getPeriodInMillis();

        // then
        assertThat(periodInMillis).isEqualTo(0);
    }

    @Test
    @DisplayName("DELETE 토큰의 기간이 초 단위로 반환된다")
    void deleteTokenPeriodIsReturnedInSeconds() {
        // when
        final int periodInSeconds = TokenType.DELETE.getPeriodInSeconds();

        // then
        assertThat(periodInSeconds).isEqualTo(0);
    }
}
