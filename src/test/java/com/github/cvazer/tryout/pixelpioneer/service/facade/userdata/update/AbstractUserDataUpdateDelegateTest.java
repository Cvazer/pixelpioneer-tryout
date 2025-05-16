package com.github.cvazer.tryout.pixelpioneer.service.facade.userdata.update;

import com.github.cvazer.tryout.pixelpioneer.api.dto.UserDto;
import com.github.cvazer.tryout.pixelpioneer.dao.entity.UserEntity;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AbstractUserDataUpdateDelegateTest {

    @ParameterizedTest
    @CsvSource({
            "1:2:3,2:3:4,4,1",
            "1:2:3:3,2:3:4,4,1",
            ",2:3:4,2:3:4,",
            "1,9:9:9,9,1",
    })
    public void givenASetOfElements_whenCalled_correctNonIntersectingElementsReturned(
            String thenString,
            String nowString,
            String addedString,
            String removedString
    ) {
        var then = splitStringForList(thenString);
        var now = splitStringForList(nowString);
        var expectedAdded = splitStringForList(addedString);
        var expectedRemoved = splitStringForList(removedString);

        var delegate = new Impl();

        var result = delegate.elementDifference(then, now);

        assertEquals(expectedAdded.size(), result.getLeft().size());
        assertEquals(expectedRemoved.size(), result.getRight().size());
        assertTrue(expectedAdded.containsAll(result.getLeft()));
        assertTrue(expectedRemoved.containsAll(result.getRight()));
    }

    private List<String> splitStringForList(String data) {
        return Optional.ofNullable(data)
                .map(it -> Arrays.stream(it.split(":")).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private static class Impl extends AbstractUserDataUpdateDelegate {
        @Override
        public void update(UserEntity user, UserDto dto) {
            throw new NotImplementedException();
        }
    }
}