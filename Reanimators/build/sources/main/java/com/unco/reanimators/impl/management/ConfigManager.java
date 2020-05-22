package com.unco.reanimators.impl.management;

import com.unco.reanimators.Seppuku;
import com.unco.reanimators.api.config.Configurable;
import com.unco.reanimators.api.gui.hud.component.DraggableHudComponent;
import com.unco.reanimators.impl.config.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author noil
 */
public final class ConfigManager {

    private File configDir;
    private File moduleConfigDir;
    private File hudComponentConfigDir;

    private boolean firstLaunch = false;

    private List<Configurable> configurableList = new ArrayList<>();

    public static final String CONFIG_PATH = "Seppuku/Config/";

    public ConfigManager() {
        this.generateDirectories();
    }

    private void generateDirectories() {
        this.configDir = new File(CONFIG_PATH);
        if (!this.configDir.exists()) {
            this.setFirstLaunch(true);
            this.configDir.mkdirs();
        }

        this.moduleConfigDir = new File(CONFIG_PATH + "Modules" + "/");
        if (!this.moduleConfigDir.exists()) {
            this.moduleConfigDir.mkdirs();
        }

        this.hudComponentConfigDir = new File(CONFIG_PATH + "HudComponents" + "/");
        if (!this.hudComponentConfigDir.exists()) {
            this.hudComponentConfigDir.mkdirs();
        }
    }

    public void init() {
        Seppuku.INSTANCE.getModuleManager().getModuleList().forEach(module -> {
            this.configurableList.add(new ModuleConfig(this.moduleConfigDir, module));
        });

        Seppuku.INSTANCE.getHudManager().getComponentList().stream().filter(hudComponent -> hudComponent instanceof DraggableHudComponent).forEach(hudComponent -> {
            this.configurableList.add(new HudConfig(this.hudComponentConfigDir, (DraggableHudComponent) hudComponent));
        });

        this.configurableList.add(new FriendConfig(configDir));
        this.configurableList.add(new XrayConfig(configDir));
        this.configurableList.add(new MacroConfig(configDir));
        this.configurableList.add(new WaypointsConfig(configDir));
        this.configurableList.add(new WorldConfig(configDir));
        this.configurableList.add(new IgnoreConfig(configDir));
        this.configurableList.add(new AutoIgnoreConfig(configDir));

        if (this.firstLaunch) {
            this.saveAll();
        } else {
            this.loadAll();
        }
    }

    public void saveAll() {
        new Thread(() -> {
            for (Configurable cfg : configurableList) {
                cfg.onSave();
            }
        }).start();
    }

    public void loadAll() {
        new Thread(() -> {
            for (Configurable cfg : configurableList) {
                cfg.onLoad();
            }
        }).start();
    }

    public File getConfigDir() {
        return configDir;
    }

    public File getModuleConfigDir() {
        return moduleConfigDir;
    }

    public File getHudComponentConfigDir() {
        return hudComponentConfigDir;
    }

    public boolean isFirstLaunch() {
        return firstLaunch;
    }

    public void setFirstLaunch(boolean firstLaunch) {
        this.firstLaunch = firstLaunch;
    }

    public void addConfigurable(Configurable configurable) {
        this.configurableList.add(configurable);
    }

    public List<Configurable> getConfigurableList() {
        return configurableList;
    }

    public void setConfigurableList(List<Configurable> configurableList) {
        this.configurableList = configurableList;
    }
}
