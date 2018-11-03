package com.nazer.pojomodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatInboxModel {
    String senderName;
    String lastMessage;
    String lastTime;
    String image;
}
