package com.unco.reanimators.gui.rgui.component;

import com.unco.reanimators.gui.rgui.component.container.Container;
import com.unco.reanimators.gui.rgui.component.listen.*;
import com.unco.reanimators.gui.rgui.poof.IPoof;
import com.unco.reanimators.gui.rgui.poof.PoofInfo;
import com.unco.reanimators.gui.rgui.render.ComponentUI;
import com.unco.reanimators.gui.rgui.render.theme.Theme;

import java.util.ArrayList;

public interface Component {
    public int getX();
    public int getY();
    public int getWidth();
    public int getHeight();

    public void setX(int x);
    public void setY(int y);
    public void setWidth(int width);
    public void setHeight(int height);

    public Component setMinimumWidth(int width);
    public Component setMaximumWidth(int width);
    public Component setMinimumHeight(int height);
    public Component setMaximumHeight(int height);

    public int getMinimumWidth();
    public int getMaximumWidth();
    public int getMinimumHeight();
    public int getMaximumHeight();

    public float getOpacity();
    public void setOpacity(float opacity);

    public boolean doAffectLayout();
    public void setAffectLayout(boolean flag);

    public Container getParent();
    public void setParent(Container parent);
    public boolean liesIn(Component container);

    public boolean isVisible();
    public void setVisible(boolean visible);

    public void setFocussed(boolean focus);
    public boolean isFocussed();

    public ComponentUI getUI();
    public Theme getTheme();
    public void setTheme(Theme theme);

    public boolean isHovered();
    public boolean isPressed();

    public ArrayList<MouseListener> getMouseListeners();
    public void addMouseListener(MouseListener listener);
    public ArrayList<RenderListener> getRenderListeners();
    public void addRenderListener(RenderListener listener);
    public ArrayList<KeyListener> getKeyListeners();
    public void addKeyListener(KeyListener listener);
    public ArrayList<UpdateListener> getUpdateListeners();
    public void addUpdateListener(UpdateListener listener);
    public ArrayList<TickListener> getTickListeners();
    public void addTickListener(TickListener listener);

    public void addPoof(IPoof poof);
    public void callPoof(Class<? extends IPoof> target, PoofInfo info);

    public int getPriority();

    public void kill();
}
