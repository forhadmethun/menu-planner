package com.forhadmethun.process;

import com.forhadmethun.bo.Calendar;
import com.forhadmethun.bo.User;
import com.forhadmethun.bo.UserType;
import com.forhadmethun.exception.ValidationException;
import com.forhadmethun.util.DateUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.forhadmethun.bo.UserType.isValidUserType;
import static com.forhadmethun.exception.ValidationException.isTrue;
import static com.forhadmethun.util.DateUtil.isValidDate;
import static com.forhadmethun.util.ErrorMessage.*;
import static com.forhadmethun.util.FileUtil.readAllUserContents;
import static java.util.Objects.nonNull;

@Component
public class AnalyzeAppUsage implements CommandLineRunner {

    private static final long ACTIVE_THRESHOLD = 5;
    private static final long SUPER_ACTIVE_THRESHOLD = 10;

    private final Logger logger = LoggerFactory.getLogger(AnalyzeAppUsage.class);
    private final Gson gson;

    public AnalyzeAppUsage(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        validateAndAnalyze(args);
    }

    private void validateAndAnalyze(String... args) throws ValidationException, IOException {
        if (args.length == 0) {
            return;
        }
        validate(args);
        analyze(args);
    }

    public List<String> analyze(String... args) throws IOException {
        var userType = UserType.fromString(args[0]);
        var startDate = LocalDate.parse(args[1]);
        var endDate = LocalDate.parse(args[2]);

        List<String> userIdsForGivenUserType = readAllUserContents().entrySet()
            .stream().filter(u -> {
                var calendar = gson.fromJson(u.getValue(), User.class).getCalendar();
                var dateIds = calendar.getDateToDayId().keySet().stream()
                    .filter(date -> DateUtil.isWithinRange(date, startDate, endDate))
                    .map(date -> calendar.getDateToDayId().get(date)).toList();
                var mealCount = calendar.getMealIdToDayId().entrySet().stream()
                    .filter(e -> dateIds.contains(e.getValue())).count();
                return returnIfUserIsInGivenType(userType, startDate, calendar, mealCount);
            })
            .map(Map.Entry::getKey).toList();

        logger.info("User of type {} are follows: {}", userType,
            nonNull(userIdsForGivenUserType) && !userIdsForGivenUserType.isEmpty()
                ? String.join(", ", userIdsForGivenUserType)
                : "[]"
            );
        return userIdsForGivenUserType;
    }

    private void validate(String[] args) throws ValidationException {
        isTrue(args.length == 3, INVALID_ARGUMENTS);
        isTrue(isValidUserType(args[0]), INVALID_USER_TYPE);
        isTrue(isValidDate(args[1]), INVALID_DATE);
        isTrue(isValidDate(args[2]), INVALID_DATE);
        isTrue(LocalDate.parse(args[1]).isEqual(LocalDate.parse(args[2]))
            || LocalDate.parse(args[1]).isBefore(LocalDate.parse(args[2])), INVALID_DATE_RANGE);
    }

    private boolean isActiveBefore(Calendar calendar, LocalDate startDate) {
        List<Long> dateIds = calendar.getDateToDayId().keySet().stream()
            .filter(date -> date.isBefore(startDate))
            .map(date -> calendar.getDateToDayId().get(date)).toList();
        return isActive(calendar.getMealIdToDayId().entrySet()
            .stream().filter(e -> dateIds.contains(e.getValue())).count());
    }

    private boolean isActive(long orderCount) {
        return orderCount >= ACTIVE_THRESHOLD;
    }

    private boolean isSuperActive(long orderCount) {
        return orderCount >= SUPER_ACTIVE_THRESHOLD;
    }

    private boolean returnIfUserIsInGivenType(
        UserType userType, LocalDate startDate, Calendar calendar, long mealCount) {
        return switch(userType) {
            case ACTIVE -> isActive(mealCount);
            case SUPER_ACTIVE -> isSuperActive(mealCount);
            case BORED -> isActiveBefore(calendar, startDate) && !isActive(mealCount);
        };
    }
}
