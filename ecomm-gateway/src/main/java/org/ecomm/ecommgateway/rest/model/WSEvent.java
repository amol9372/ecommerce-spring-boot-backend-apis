package org.ecomm.ecommgateway.rest.model;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WSEvent {

    String eventType;
    String eventSubType;
    String message;

}
