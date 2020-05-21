package me.rigamortis.seppuku.api.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.rigamortis.seppuku.api.util.FileUtil;

import java.io.File;
import java.io.FileReader;

/**
 * @author noil
 */
public abstract class Configurable {

    private File file;

    private JsonObject jsonObject;

    public Configurable(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void onLoad() {
        this.jsonObject = this.convertJsonObjectFromFile();
    }

    public void onSave() {

    }

    protected void saveJsonObjectToFile(JsonObject object) {
        File newFile = FileUtil.recreateFile(this.getFile());
        FileUtil.saveJsonFile(newFile, object);
    }

    protected JsonObject convertJsonObjectFromFile() {
        if (!this.getFile().exists())
            return new JsonObject();

        FileReader reader = FileUtil.createReader(this.getFile());
        if (reader == null)
            return new JsonObject();

        JsonElement element = new JsonParser().parse(reader);
        if (!element.isJsonObject())
            return new JsonObject();

        FileUtil.closeReader(reader);

        return element.getAsJsonObject();
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }
}