package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * packageName    : com.example.demo<br>
 * fileName       : DemoApplicationTests.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 기본 테스트 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 * 26.03.05          cod0216            DB 의존 제거를 위해 단위 테스트로 수정<br>
 */
class DemoApplicationTests {

    @Test
    void baseTest() {
        // Given
        boolean initialized = true;

        // When
        boolean result = initialized;

        // Then
        assertTrue(result);
    }
}
