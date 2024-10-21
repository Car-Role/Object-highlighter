package com.objecthighlighter;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Scene;
import net.runelite.api.Tile;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.GameState;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.api.ObjectComposition;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectHighlighterOverlay extends Overlay
{
    private final Client client;
    private final ObjectHighlighterConfig config;
    private List<Integer> tileObjectIds;
    private List<Integer> outlineObjectIds;
    private List<Integer> clickboxObjectIds;
    private List<String> tileObjectNames;
    private List<String> outlineObjectNames;
    private List<String> clickboxObjectNames;

    @Inject
    private ObjectHighlighterOverlay(Client client, ObjectHighlighterConfig config)
    {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    public void updateConfig(List<Integer> tileObjectIds, List<Integer> outlineObjectIds, List<Integer> clickboxObjectIds,
                             List<String> tileObjectNames, List<String> outlineObjectNames, List<String> clickboxObjectNames)
    {
        this.tileObjectIds = tileObjectIds;
        this.outlineObjectIds = outlineObjectIds;
        this.clickboxObjectIds = clickboxObjectIds;
        this.tileObjectNames = tileObjectNames;
        this.outlineObjectNames = outlineObjectNames;
        this.clickboxObjectNames = clickboxObjectNames;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            return null;
        }

        Scene scene = client.getScene();
        Tile[][][] tiles = scene.getTiles();

        for (int z = 0; z < tiles.length; z++)
        {
            for (int x = 0; x < tiles[z].length; x++)
            {
                for (int y = 0; y < tiles[z][x].length; y++)
                {
                    Tile tile = tiles[z][x][y];
                    if (tile == null)
                    {
                        continue;
                    }

                    renderTileObjects(graphics, tile);
                }
            }
        }

        return null;
    }

    private void renderTileObjects(Graphics2D graphics, Tile tile)
    {
        for (GameObject object : tile.getGameObjects())
        {
            if (object == null)
            {
                continue;
            }

            ObjectComposition objectComposition = client.getObjectDefinition(object.getId());
            String objectName = objectComposition.getName();
            
            if (objectName == null || objectName.isEmpty())
            {
                continue;
            }

            if (shouldHighlightTile(object, objectName))
            {
                renderTile(graphics, object);
            }
            if (shouldHighlightHull(object, objectName))
            {
                renderOutline(graphics, object);
            }
            if (shouldHighlightClickbox(object, objectName))
            {
                renderClickbox(graphics, object);
            }
        }
    }

    private boolean shouldHighlightTile(GameObject object, String objectName)
    {
        return tileObjectIds.contains(object.getId()) || 
               tileObjectNames.stream().anyMatch(name -> objectName.toLowerCase().contains(name.toLowerCase()));
    }

    private boolean shouldHighlightHull(GameObject object, String objectName)
    {
        return outlineObjectIds.contains(object.getId()) || 
               outlineObjectNames.stream().anyMatch(name -> objectName.toLowerCase().contains(name.toLowerCase()));
    }

    private boolean shouldHighlightClickbox(GameObject object, String objectName)
    {
        return clickboxObjectIds.contains(object.getId()) || 
               clickboxObjectNames.stream().anyMatch(name -> objectName.toLowerCase().contains(name.toLowerCase()));
    }

    private void renderTile(Graphics2D graphics, GameObject object)
    {
        Polygon tilePoly = object.getCanvasTilePoly();
        if (tilePoly != null)
        {
            renderPolygon(graphics, tilePoly, config.tileHighlightColor(), config.tileBorderColor(), config.tileBorderWidth());
        }
    }

    private void renderOutline(Graphics2D graphics, GameObject object)
    {
        Shape objectShape = object.getConvexHull();
        if (objectShape != null)
        {
            renderPolygon(graphics, objectShape, config.hullHighlightColor(), config.hullBorderColor(), config.hullBorderWidth());
        }
    }

    private void renderClickbox(Graphics2D graphics, GameObject object)
    {
        Shape clickboxShape = object.getClickbox();
        if (clickboxShape != null)
        {
            renderPolygon(graphics, clickboxShape, config.clickboxHighlightColor(), config.clickboxBorderColor(), config.clickboxBorderWidth());
        }
    }

    private void renderPolygon(Graphics2D graphics, Shape shape, Color fillColor, Color borderColor, int borderWidth)
    {
        graphics.setColor(fillColor);
        graphics.fill(shape);

        graphics.setColor(borderColor);
        graphics.setStroke(new BasicStroke(borderWidth));
        graphics.draw(shape);
    }
}
