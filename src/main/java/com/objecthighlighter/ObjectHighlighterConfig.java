package com.objecthighlighter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Alpha;
import java.awt.Color;

@ConfigGroup("objecthighlighter")
public interface ObjectHighlighterConfig extends Config
{
    @ConfigSection(
        name = "Tile Highlight",
        description = "Settings for tile highlighting",
        position = 0
    )
    String tileSection = "tileSection";

    @ConfigSection(
        name = "Hull Highlight",
        description = "Settings for hull (outline) highlighting",
        position = 1
    )
    String hullSection = "hullSection";

    @ConfigSection(
        name = "Clickbox Highlight",
        description = "Settings for clickbox highlighting",
        position = 2
    )
    String clickboxSection = "clickboxSection";

    // Tile Highlight Settings
    @ConfigItem(
        keyName = "tileObjectIds",
        name = "Tile Object IDs",
        description = "Comma-separated list of object IDs to highlight the tile",
        section = tileSection
    )
    default String tileObjectIds() { return ""; }

    @ConfigItem(
        keyName = "tileObjectNames",
        name = "Tile Object Names",
        description = "Comma-separated list of object names to highlight the tile",
        section = tileSection
    )
    default String tileObjectNames() { return ""; }

    @Alpha
    @ConfigItem(
        keyName = "tileHighlightColor",
        name = "Tile Color",
        description = "Color used to highlight tiles",
        section = tileSection
    )
    default Color tileHighlightColor() { return new Color(255, 0, 0, 50); }

    @Alpha
    @ConfigItem(
        keyName = "tileBorderColor",
        name = "Tile Border Color",
        description = "Color used for the border of highlighted tiles",
        section = tileSection
    )
    default Color tileBorderColor() { return new Color(255, 0, 0, 255); }

    @ConfigItem(
        keyName = "tileBorderWidth",
        name = "Tile Border Width",
        description = "Width of the border for highlighted tiles",
        section = tileSection
    )
    default int tileBorderWidth() { return 2; }

    // Hull Highlight Settings
    @ConfigItem(
        keyName = "hullObjectIds",
        name = "Hull Object IDs",
        description = "Comma-separated list of object IDs to highlight the hull",
        section = hullSection
    )
    default String hullObjectIds() { return ""; }

    @ConfigItem(
        keyName = "hullObjectNames",
        name = "Hull Object Names",
        description = "Comma-separated list of object names to highlight the hull",
        section = hullSection
    )
    default String hullObjectNames() { return ""; }

    @Alpha
    @ConfigItem(
        keyName = "hullHighlightColor",
        name = "Hull Color",
        description = "Color used to highlight object hulls",
        section = hullSection
    )
    default Color hullHighlightColor() { return new Color(0, 255, 0, 50); }

    @Alpha
    @ConfigItem(
        keyName = "hullBorderColor",
        name = "Hull Border Color",
        description = "Color used for the border of highlighted hulls",
        section = hullSection
    )
    default Color hullBorderColor() { return new Color(0, 255, 0, 255); }

    @ConfigItem(
        keyName = "hullBorderWidth",
        name = "Hull Border Width",
        description = "Width of the border for highlighted hulls",
        section = hullSection
    )
    default int hullBorderWidth() { return 2; }

    // Clickbox Highlight Settings
    @ConfigItem(
        keyName = "clickboxObjectIds",
        name = "Clickbox Object IDs",
        description = "Comma-separated list of object IDs to highlight the clickbox",
        section = clickboxSection
    )
    default String clickboxObjectIds() { return ""; }

    @ConfigItem(
        keyName = "clickboxObjectNames",
        name = "Clickbox Object Names",
        description = "Comma-separated list of object names to highlight the clickbox",
        section = clickboxSection
    )
    default String clickboxObjectNames() { return ""; }

    @Alpha
    @ConfigItem(
        keyName = "clickboxHighlightColor",
        name = "Clickbox Color",
        description = "Color used to highlight object clickboxes",
        section = clickboxSection
    )
    default Color clickboxHighlightColor() { return new Color(0, 0, 255, 50); }

    @Alpha
    @ConfigItem(
        keyName = "clickboxBorderColor",
        name = "Clickbox Border Color",
        description = "Color used for the border of highlighted clickboxes",
        section = clickboxSection
    )
    default Color clickboxBorderColor() { return new Color(0, 0, 255, 255); }

    @ConfigItem(
        keyName = "clickboxBorderWidth",
        name = "Clickbox Border Width",
        description = "Width of the border for highlighted clickboxes",
        section = clickboxSection
    )
    default int clickboxBorderWidth() { return 2; }
}
