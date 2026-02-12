package com.niftymods.plugin.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class ServerConfig {

    private boolean disabledByDefault = false;

    public static final BuilderCodec<ServerConfig> CODEC = BuilderCodec.builder(ServerConfig.class, ServerConfig::new)
            .append(new KeyedCodec<>("DisabledByDefault", Codec.BOOLEAN),
                    ((config, flag) -> config.disabledByDefault = flag),
                    (ServerConfig::isDisabledByDefault)).add()
            .build();

    public boolean isDisabledByDefault() {
        return disabledByDefault;
    }

    public void setDisabledByDefault(boolean disabledByDefault) {
        this.disabledByDefault = disabledByDefault;
    }
}
