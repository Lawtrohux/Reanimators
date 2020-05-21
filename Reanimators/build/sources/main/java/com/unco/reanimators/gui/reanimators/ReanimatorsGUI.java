package com.unco.reanimators.gui.reanimators;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.unco.reanimators.Reanimators;
import com.unco.reanimators.command.Command;
import com.unco.reanimators.gui.reanimators.component.ActiveModules;
import com.unco.reanimators.gui.reanimators.component.SettingsPanel;
import com.unco.reanimators.gui.reanimators.theme.reanimators.ReanimatorsTheme;
import com.unco.reanimators.gui.rgui.GUI;
import com.unco.reanimators.gui.rgui.component.container.use.Frame;
import com.unco.reanimators.gui.rgui.component.container.use.Scrollpane;
import com.unco.reanimators.gui.rgui.component.listen.MouseListener;
import com.unco.reanimators.gui.rgui.component.listen.TickListener;
import com.unco.reanimators.gui.rgui.component.use.CheckButton;
import com.unco.reanimators.gui.rgui.component.use.Label;
import com.unco.reanimators.gui.rgui.render.theme.Theme;
import com.unco.reanimators.gui.rgui.util.ContainerHelper;
import com.unco.reanimators.gui.rgui.util.Docking;
import com.unco.reanimators.module.Module;
import com.unco.reanimators.module.ModuleManager;
import com.unco.reanimators.util.ColourHolder;
import com.unco.reanimators.util.LagCompensator;
import com.unco.reanimators.util.Pair;
import com.unco.reanimators.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ReanimatorsGUI extends GUI {

    public static final RootFontRenderer fontRendererBig = new RootFontRenderer(1.0f);
    public static final RootFontRenderer fontRenderer = new RootFontRenderer(1.0f);
    public Theme theme;

    public static ColourHolder primaryColour = new ColourHolder(29, 29, 29);

    public ReanimatorsGUI() {
        super(new ReanimatorsTheme());
        theme = getTheme();
    }

    @Override
    public void drawGUI() {
        super.drawGUI();
    }

    @Override
    public void initializeGUI() {
        HashMap<Module.Category, Pair<Scrollpane, SettingsPanel>> categoryScrollpaneHashMap = new HashMap<>();
        for (Module module : ModuleManager.getModules()) {
            if (module.getCategory().isHidden()) continue;
            Module.Category moduleCategory = module.getCategory();
            if (!categoryScrollpaneHashMap.containsKey(moduleCategory)) {
                Stretcherlayout stretcherlayout = new Stretcherlayout(1);
                stretcherlayout.setComponentOffsetWidth(0);
                Scrollpane scrollpane = new Scrollpane(getTheme(), stretcherlayout, 300, 260);
                scrollpane.setMaximumHeight(300);
                categoryScrollpaneHashMap.put(moduleCategory, new Pair<>(scrollpane, new SettingsPanel(getTheme(), null)));
            }

            Pair<Scrollpane, SettingsPanel> pair = categoryScrollpaneHashMap.get(moduleCategory);
            Scrollpane scrollpane = pair.getKey();
            CheckButton checkButton = new CheckButton(module.getName());
            checkButton.setToggled(module.isEnabled());

            checkButton.addTickListener(() -> {
                checkButton.setToggled(module.isEnabled());
                checkButton.setName(module.getName());
            });

            checkButton.addMouseListener(new MouseListener() {
                @Override
                public void onMouseDown(MouseButtonEvent event) {
                    if (event.getButton() == 1) { 
                        pair.getValue().setModule(module);
                        pair.getValue().setX(event.getX() + checkButton.getX());
                        pair.getValue().setY(event.getY() + checkButton.getY());
                    }
                }

                @Override
                public void onMouseRelease(MouseButtonEvent event) {

                }

                @Override
                public void onMouseDrag(MouseButtonEvent event) {

                }

                @Override
                public void onMouseMove(MouseMoveEvent event) {

                }

                @Override
                public void onScroll(MouseScrollEvent event) {

                }
            });
            checkButton.addPoof(new CheckButton.CheckButtonPoof<CheckButton, CheckButton.CheckButtonPoof.CheckButtonPoofInfo>() {
                @Override
                public void execute(CheckButton component, CheckButtonPoofInfo info) {
                    if (info.getAction().equals(CheckButton.CheckButtonPoof.CheckButtonPoofInfo.CheckButtonPoofInfoAction.TOGGLE)) {
                        module.setEnabled(checkButton.isToggled());
                    }
                }
            });
            scrollpane.addChild(checkButton);
        }

        int x = 10;
        int y = 10;
        int nexty = y;
        for (Map.Entry<Module.Category, Pair<Scrollpane, SettingsPanel>> entry : categoryScrollpaneHashMap.entrySet()) {
            Stretcherlayout stretcherlayout = new Stretcherlayout(1);
            stretcherlayout.COMPONENT_OFFSET_Y = 1;
            Frame frame = new Frame(getTheme(), stretcherlayout, entry.getKey().getName());
            Scrollpane scrollpane = entry.getValue().getKey();
            frame.addChild(scrollpane);
            frame.addChild(entry.getValue().getValue());
            scrollpane.setOriginOffsetY(0);
            scrollpane.setOriginOffsetX(0);
            frame.setCloseable(false);

            frame.setX(x);
            frame.setY(y);

            addChild(frame);

            nexty = Math.max(y + frame.getHeight() + 10, nexty);
            x += frame.getWidth() + 10;
            if (x > Wrapper.getMinecraft().displayWidth / 1.2f) {
                y = nexty;
                nexty = y;
            }
        }

        this.addMouseListener(new MouseListener() {
            private boolean isBetween(int min, int val, int max) {
                return !(val > max || val < min);
            }

            @Override
            public void onMouseDown(MouseButtonEvent event) {
                List<SettingsPanel> panels = ContainerHelper.getAllChildren(SettingsPanel.class, ReanimatorsGUI.this);
                for (SettingsPanel settingsPanel : panels) {
                    if (!settingsPanel.isVisible()) continue;
                    int[] real = GUI.calculateRealPosition(settingsPanel);
                    int pX = event.getX() - real[0];
                    int pY = event.getY() - real[1];
                    if (!isBetween(0, pX, settingsPanel.getWidth()) || !isBetween(0, pY, settingsPanel.getHeight()))
                        settingsPanel.setVisible(false);
                }
            }

            @Override
            public void onMouseRelease(MouseButtonEvent event) {

            }

            @Override
            public void onMouseDrag(MouseButtonEvent event) {

            }

            @Override
            public void onMouseMove(MouseMoveEvent event) {

            }

            @Override
            public void onScroll(MouseScrollEvent event) {

            }
        });

        ArrayList<Frame> frames = new ArrayList<>();

        Frame frame = new Frame(getTheme(), new Stretcherlayout(1), "Users");
        frame.setCloseable(false);
        frame.setPinneable(true);

        Label users = new Label("");
        users.addTickListener(() -> {
            Minecraft mc = Minecraft.getMinecraft();

            users.setText(ChatFormatting.BLUE + mc.player.getName() + ChatFormatting.RED + " Welcome to Reanimators " + Reanimators.MODVER);
            users.addLine(ChatFormatting.BLUE + "FPS: " + ChatFormatting.RED + Wrapper.getMinecraft().debugFPS);
        });
        frame.addChild(users);
        users.setFontRenderer(fontRendererBig);
        users.setShadow(true);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Active modules");
        frame.setCloseable(false);
        frame.addChild(new ActiveModules());
        frame.setPinneable(true);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Coordinates");
        frame.setCloseable(false);
        frame.setPinneable(true);

        Label coordsLabel = new Label("");
        coordsLabel.addTickListener(() -> {
            Minecraft mc = Minecraft.getMinecraft();

            boolean on_nether = (mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell"));

            float value = !on_nether ? 0.125f : 8;
            
            int posX = (int) (mc.player.posX);
            int posY = (int) (mc.player.posY);
            int posZ = (int) (mc.player.posZ);

            int poshX = (int) (mc.player.posX * value);
            int poshZ = (int) (mc.player.posZ * value);

            coordsLabel.setText("Coords:");
            coordsLabel.addLine(String.format(ChatFormatting.RED + "Nether: " + poshX + "x " + Integer.toString(posY) + "y " + poshZ + "z"));
            coordsLabel.addLine(String.format(ChatFormatting.BLUE + "Overworld: " + Integer.toString(posX) + "x " + Integer.toString(posY) + "y " + Integer.toString(posZ) + "z"));
        });
        frame.addChild(coordsLabel);
        coordsLabel.setFontRenderer(fontRendererBig);
        coordsLabel.setShadow(true);
        frame.setHeight(20);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Counts");
        frame.setCloseable(false);
        frame.setPinneable(true);

        Label count = new Label("");
        count.addTickListener(() -> {
            Minecraft mc = Minecraft.getMinecraft();

            count.setText(ChatFormatting.BLUE + "Counts");
            count.addLine(ChatFormatting.BLUE + "Totems: " + ChatFormatting.RED + mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum());
            count.addLine(ChatFormatting.BLUE + "Crystal: " + ChatFormatting.RED + mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum());
            count.addLine(ChatFormatting.BLUE + "Gapples: " + ChatFormatting.RED + mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum());
        });
        frame.addChild(count);
        count.setFontRenderer(fontRendererBig);
        count.setShadow(true);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Time");
        frame.setCloseable(false);
        frame.setPinneable(true);

        Label time = new Label("");
        time.addTickListener(() -> {
            DateTimeFormatter time_format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            time.setText(ChatFormatting.BLUE + "Time: " + ChatFormatting.RED + time_format.format(now));
        });

        frame.addChild(time);
        time.setFontRenderer(fontRendererBig);
        time.setShadow(true);
        frames.add(frame);

        for (Frame frame1 : frames) {
            frame1.setY(x);
            frame1.setY(y);

            nexty = Math.max(y + frame1.getHeight() + 10, nexty);
            x += (frame1.getWidth() + 10);
            if (x * DisplayGuiScreen.getScale() > Wrapper.getMinecraft().displayWidth / 1.2f) {
                y = nexty;
                nexty = y;
                x = 10;
            }

            addChild(frame1);
        }
    }

    private static String getEntityName(@Nonnull Entity entity) {
        if (entity instanceof EntityItem) {
            return TextFormatting.DARK_AQUA + ((EntityItem) entity).getItem().getItem().getItemStackDisplayName(((EntityItem) entity).getItem());
        }
        if (entity instanceof EntityWitherSkull) {
            return TextFormatting.DARK_GRAY + "Wither skull";
        }
        if (entity instanceof EntityEnderCrystal) {
            return TextFormatting.LIGHT_PURPLE + "End crystal";
        }
        if (entity instanceof EntityEnderPearl) {
            return "Thrown ender pearl";
        }
        if (entity instanceof EntityMinecart) {
            return "Minecart";
        }
        if (entity instanceof EntityItemFrame) {
            return "Item frame";
        }
        if (entity instanceof EntityEgg) {
            return "Thrown egg";
        }
        if (entity instanceof EntitySnowball) {
            return "Thrown snowball";
        }

        return entity.getName();
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>(map.entrySet());
        Collections.sort(list, Comparator.comparing(o -> (o.getValue())));

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override
    public void destroyGUI() {
        kill();
    }

    private static final int DOCK_OFFSET = 0;

    public static void dock(Frame component) {
        Docking docking = component.getDocking();
        if (docking.isTop())
            component.setY(DOCK_OFFSET);
        if (docking.isBottom())
            component.setY((Wrapper.getMinecraft().displayHeight / DisplayGuiScreen.getScale()) - component.getHeight() - DOCK_OFFSET);
        if (docking.isLeft())
            component.setX(DOCK_OFFSET);
        if (docking.isRight())
            component.setX((Wrapper.getMinecraft().displayWidth / DisplayGuiScreen.getScale()) - component.getWidth() - DOCK_OFFSET);
        if (docking.isCenterHorizontal())
            component.setX((Wrapper.getMinecraft().displayWidth / (DisplayGuiScreen.getScale() * 2) - component.getWidth() / 2));
        if (docking.isCenterVertical())
            component.setY(Wrapper.getMinecraft().displayHeight / (DisplayGuiScreen.getScale() * 2) - component.getHeight() / 2);

    }
}
