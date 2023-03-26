package com.dev.SseEvent;

public class SseBasicEvent {

        private String dataType;

        public SseBasicEvent(String eventType) {
            this.dataType = eventType;
        }

        public String getEventType() {
            return dataType;
        }

        public void setEventType(String eventType) {
            this.dataType = eventType;
        }

}
