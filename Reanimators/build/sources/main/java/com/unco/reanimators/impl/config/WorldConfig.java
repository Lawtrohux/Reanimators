package com.unco.reanimators.impl.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.config.Configurable;
import com.unco.reanimators.api.util.FileUtil;
import com.unco.reanimators.impl.management.WorldManager;

import java.io.File;

/**
 * @author noil
 */
public final class WorldConfig extends Configurable {

    public WorldConfig(File dir) {
        super(FileUtil.createJsonFile(dir, "Worlds"));
    }

    @Override
    public void onLoad() {
        super.onLoad();

        this.getJsonObject().entrySet().forEach(entry -> {
            final String host = entry.getKey();
            final String seed = entry.getValue().getAsJsonArray().get(0).getAsString();
            Seppuku.INSTANCE.getWorldManager().getWorldDataList().add(new WorldManager.WorldData(host, Long.parseLong(seed)));
        });
    }

    @Override
    public void onSave() {
        JsonObject worldListJsonObject = new JsonObject();
        Seppuku.INSTANCE.getWorldManager().getWorldDataList().forEach(worldData -> {
            final JsonArray array = new JsonArray();
            array.add(worldData.getSeed());
            worldListJsonObject.add(worldData.getHost(), array);
        });
        this.saveJsonObjectToFile(worldListJsonObject);
    }
}