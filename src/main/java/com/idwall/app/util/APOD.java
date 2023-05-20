package com.idwall.app.util;
import com.fasterxml.jackson.annotation.JsonProperty;

public class APOD {
    public final String expand;
    public final String id;
    public final String key;
    public final String self;

    public APOD(@JsonProperty("expand") String expand,
                @JsonProperty("id") String id,
                @JsonProperty("key") String key,
                @JsonProperty("self") String self) {
        this.expand = expand;
        this.id = id;
        this.key = key;
        this.self = self;

    }
}