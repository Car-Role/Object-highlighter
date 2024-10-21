package com.objecthighlighter;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@PluginDescriptor(
    name = "Object Highlighter",
    description = "Highlights game objects based on ID or name",
    tags = {"highlight", "object", "tile", "hull", "clickbox"}
)
public class ObjectHighlighterPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private ObjectHighlighterConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ObjectHighlighterOverlay overlay;

    public List<Integer> tileObjectIds;
    public List<Integer> outlineObjectIds;
    public List<Integer> clickboxObjectIds;
    public List<String> tileObjectNames;
    public List<String> outlineObjectNames;
    public List<String> clickboxObjectNames;

    static {
        System.out.println("ObjectHighlighterPlugin class loaded");
    }

    @Override
    protected void startUp() throws Exception
    {
        log.info("Object Highlighter started!");
        overlayManager.add(overlay);
        updateObjectIdsToHighlight();
    }

    @Override
    protected void shutDown() throws Exception
    {
        log.info("Object Highlighter stopped!");
        overlayManager.remove(overlay);
        tileObjectIds = null;
        outlineObjectIds = null;
        clickboxObjectIds = null;
        tileObjectNames = null;
        outlineObjectNames = null;
        clickboxObjectNames = null;
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged)
    {
        if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
        {
            updateObjectIdsToHighlight();
        }
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (client.getGameState() == GameState.LOGGED_IN)
        {
            updateObjectIdsToHighlight();
            overlay.updateConfig(tileObjectIds, outlineObjectIds, clickboxObjectIds,
                                 tileObjectNames, outlineObjectNames, clickboxObjectNames);
        }
    }

    private void updateObjectIdsToHighlight()
    {
        tileObjectIds = parseObjectIds(config.tileObjectIds());
        outlineObjectIds = parseObjectIds(config.hullObjectIds());
        clickboxObjectIds = parseObjectIds(config.clickboxObjectIds());

        tileObjectNames = parseObjectNames(config.tileObjectNames());
        outlineObjectNames = parseObjectNames(config.hullObjectNames());
        clickboxObjectNames = parseObjectNames(config.clickboxObjectNames());
    }

    private List<Integer> parseObjectIds(String objectIdsString)
    {
        return Arrays.stream(objectIdsString.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    }

    private List<String> parseObjectNames(String objectNamesString)
    {
        return Arrays.stream(objectNamesString.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
    }

    @Provides
    ObjectHighlighterConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ObjectHighlighterConfig.class);
    }
}
