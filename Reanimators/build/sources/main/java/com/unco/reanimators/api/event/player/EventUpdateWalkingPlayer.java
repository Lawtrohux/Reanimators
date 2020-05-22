package com.unco.reanimators.api.event.player;

import com.unco.reanimators.api.event.EventCancellable;

/**
 * Author Seth
 * 4/8/2019 @ 3:50 AM.
 */
public class EventUpdateWalkingPlayer extends EventCancellable {

    public EventUpdateWalkingPlayer(EventStage stage) {
        super(stage);
    }

}
