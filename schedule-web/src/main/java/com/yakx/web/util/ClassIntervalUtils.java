package com.yakx.web.util;

import com.yakx.web.domain.ClassInterval;
import com.yakx.web.service.ClassIntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yakx
 */
@Component
@RequiredArgsConstructor
public class ClassIntervalUtils {
    public static Map<Integer, ClassInterval> classIntervalMap = new HashMap<>();

    private final ClassIntervalService classIntervalService;

    @PostConstruct
    public void init(){
        List<ClassInterval> list = classIntervalService.list();
        list.forEach(item -> {
            LocalTime startTime = LocalTime.of(item.getStartHour(), item.getStartMinute());
            LocalTime endTime = LocalTime.of(item.getEndHour(), item.getEndMinute());
            item.setStartTime(startTime);
            item.setEndTime(endTime);
            classIntervalMap.put(item.getSequence(), item);
        });
    }

    public static ClassInterval getClassInterval(Integer sequence){
        return classIntervalMap.get(sequence);
    }

}
