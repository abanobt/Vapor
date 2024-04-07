package main;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.Hashtable;

public class PercentLayout implements LayoutManager2 {
    protected Hashtable<Component, PercentConstraints> constraintsTable = new Hashtable<>();

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof PercentConstraints) {
            constraintsTable.put(comp, (PercentConstraints) constraints);
        } else {
            throw new IllegalArgumentException("Argument constraints must be of type PercentConstraints");
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(target.getWidth(), target.getHeight());
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {}

    @Override
    public void addLayoutComponent(String name, Component comp) {}

    @Override
    public void removeLayoutComponent(Component comp) {
        constraintsTable.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(parent.getWidth(), parent.getHeight());
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(parent.getWidth(), parent.getHeight());
    }

    @Override
    public void layoutContainer(Container parent) {
        Rectangle parentBounds = parent.getBounds();
        for (Component comp : parent.getComponents()) {
            PercentConstraints constraints = constraintsTable.get(comp);
            if (constraints != null) {
                comp.setBounds(
                    new Rectangle(
                        (int)Math.ceil(parentBounds.getWidth() * constraints.x()),
                        (int)Math.ceil(parentBounds.getHeight() * constraints.y()),
                        (int)Math.ceil(parentBounds.getWidth() * constraints.w()),
                        (int)Math.ceil(parentBounds.getHeight() * constraints.h())
                    )
                );
            }
        }
    }
}
