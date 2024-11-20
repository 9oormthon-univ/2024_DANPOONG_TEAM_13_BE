package com.daon.onjung.event.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;

@UseCase
public interface ProcessCompletedEventUseCase {
    void execute(Long eventId);
}
