package com.unco.reanimators.impl.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.config.Configurable;
import com.unco.reanimators.api.util.FileUtil;
import com.unco.reanimators.impl.module.render.XrayModule;

import java.io.File;

/**
 * @author noil
 */
public final class XrayConfig extends Configurable {

    private XrayModule xrayModule;

    public XrayConfig(File dir) {
        super(FileUtil.createJsonFile(dir, "Xray"));
        this.xrayModule = (XrayModule) Seppuku.INSTANCE.getModuleManager().find("Xray");
    }

    @Override
    public void onLoad() {
        super.onLoad();

        if (this.xrayModule == null)
            return;

        final JsonArray xrayIdsJsonArray = this.getJsonObject().get("BlockIds").getAsJsonArray();

        for (JsonElement jsonElement : xrayIdsJsonArray) {
            this.xrayModule.add(jsonElement.getAsInt());
        }
    }

    @Override
    public void onSave() {
        if (this.xrayModule == null)
            return;

        JsonObject save = new JsonObject();

        JsonArray xrayIdsJsonArray = new JsonArray();
        for (Integer i : this.xrayModule.getIds())
            xrayIdsJsonArray.add(i);

        save.add("BlockIds", xrayIdsJsonArray);

        this.saveJsonObjectToFile(save);
    }
}
