/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmondskarp.Gui;

import java.awt.Color;

/**
 *
 * @author gabriele
 */
public class Config {

    private Color defaultArrow;
    private Color selectedArrow;
    private Color usedArrow;
    private Color filledArrow;
    private int dimText;
    private int dimCircle;
    private int posText;
    private int fixedCapacity;
    private float strokeCircle;
    private float strokeArrow;
    private boolean randomCapacity;
    private boolean needUpdate;
    private boolean residualMode;
    private static Config config = new Config();

    private Config() {
        defaultArrow = Color.BLACK;
        selectedArrow = Color.GREEN;
        usedArrow = Color.BLUE;
        filledArrow = Color.RED;
        dimCircle = 30;
        dimText = 15;
        posText = 50;
        fixedCapacity = 10;
        randomCapacity = true;
        needUpdate = false;
        residualMode = false;
        strokeArrow = 10;
        strokeCircle = 10;
    }

    public static Config getConfig() {
        return config;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public Color getDefaultArrow() {
        return defaultArrow;
    }

    public void setDefaultArrow(Color defaultArrow) {
        this.defaultArrow = defaultArrow;
    }

    public Color getSelectedArrow() {
        return selectedArrow;
    }

    public void setSelectedArrow(Color selectedArrow) {
        this.selectedArrow = selectedArrow;
    }

    public Color getUsedArrow() {
        return usedArrow;
    }

    public void setUsedArrow(Color usedArrow) {
        this.usedArrow = usedArrow;
    }

    public Color getFilledArrow() {
        return filledArrow;
    }

    public void setFilledArrow(Color filledArrow) {
        this.filledArrow = filledArrow;
    }

    public int getDimText() {
        return dimText;
    }

    public void setDimText(int dimText) {
        needUpdate = true;
        this.dimText = dimText;
    }

    public int getDimCircle() {
        return dimCircle;
    }

    public void setDimCircle(int dimCircle) {
        needUpdate = true;
        this.dimCircle = dimCircle;
    }

    public int getPosText() {
        return posText;
    }

    public void setPosText(int posText) {
        needUpdate = true;
        this.posText = posText;

    }

    public int getFixedCapacity() {
        return fixedCapacity;
    }

    public void setFixedCapacity(int fixedCapacity) {
        this.fixedCapacity = fixedCapacity;
    }

    public boolean isRandomCapacity() {
        return randomCapacity;
    }

    public void setRandomCapacity(boolean randomCapacity) {
        this.randomCapacity = randomCapacity;
    }

    public float getStrokeCircle() {
        return strokeCircle;
    }

    public void setStrokeCircle(float strokeCircle) {
        this.strokeCircle = strokeCircle;
    }

    public float getStrokeArrow() {
        return strokeArrow;
    }

    public void setStrokeArrow(float strokeArrow) {
        this.strokeArrow = strokeArrow;
    }

    public boolean getResidualMode() {
        return residualMode;
    }

    public void setResitualMode(boolean residualMode) {
        this.residualMode = residualMode;
    }
}
