package com.unco.reanimators.impl.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.config.Configurable;
import com.unco.reanimators.api.util.FileUtil;
import com.unco.reanimators.impl.module.misc.AutoIgnoreModule;

import java.io.File;

/**
 * @author noil
 */
public final class AutoIgnoreConfig extends Configurable {

    private AutoIgnoreModule autoIgnoreModule;

    public AutoIgnoreConfig(File dir) {
        super(FileUtil.createJsonFile(dir, "AutoIgnored"));
        this.autoIgnoreModule = (AutoIgnoreModule) Seppuku.INSTANCE.getModuleManager().find(AutoIgnoreModule.class);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        final JsonArray autoIgnoredJsonArray = this.getJsonObject().get("AutoIgnored").getAsJsonArray();

        for (JsonElement jsonElement : autoIgnoredJsonArray) {
            final String blacklistedName = jsonElement.getAsString();
            this.autoIgnoreModule.getBlacklist().add(blacklistedName);
        }
    }

    @Override
    public void onSave() {
        JsonObject save = new JsonObject();
        JsonArray autoIgnoreListJsonArray = new JsonArray();
        this.autoIgnoreModule.getBlacklist().forEach(autoIgnoreListJsonArray::add);
        save.add("AutoIgnored", autoIgnoreListJsonArray);
        this.saveJsonObjectToFile(save);
    }
}