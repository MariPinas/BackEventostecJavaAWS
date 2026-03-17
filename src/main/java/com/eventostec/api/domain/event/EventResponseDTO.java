package com.eventostec.api.domain.event;

import java.util.Date;

public record EventResponseDTO(String id, String title, String description, Date date, String city, String uf, String eventUrl, Boolean remote, String image) {
}
