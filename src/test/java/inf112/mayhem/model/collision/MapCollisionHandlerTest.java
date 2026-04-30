package inf112.mayhem.model.collision;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MapCollisionHandlerTest {
    private TiledMap map;
    private TiledMapTileLayer groundLayer;
    private TiledMapTileLayer platformLayer;
    private MapLayers layers;
    private MapCollisionHandler handler;

    @BeforeEach
    void setUp() {
        map = mock(TiledMap.class);
        layers = mock(MapLayers.class);
        groundLayer = mock(TiledMapTileLayer.class);
        platformLayer = mock(TiledMapTileLayer.class);

        when(map.getLayers()).thenReturn(layers);
        when(layers.get("ground")).thenReturn(groundLayer);
        when(groundLayer.getName()).thenReturn("ground");
        when(platformLayer.getName()).thenReturn("platform1");

        List<MapLayer> layerList = new ArrayList<>();
        layerList.add(groundLayer);
        layerList.add(platformLayer);
        when(layers.iterator()).thenReturn(layerList.iterator());

        handler = new MapCollisionHandler(map);
    }

    @Test
    void testIsGroundedOnGround() {
        Rectangle bounds = new Rectangle(32, 32, 30, 30);
        float previousY = 32;

        when(groundLayer.getCell(1, 0)).thenReturn(mock(TiledMapTileLayer.Cell.class));

        assertTrue(handler.isGrounded(bounds, previousY, false));
    }

    @Test
    void testIsGroundedOnPlatform() {
        Rectangle bounds = new Rectangle(32, 32, 30, 30);
        float previousY = 32;

        when(groundLayer.getCell(anyInt(), anyInt())).thenReturn(null);
        when(platformLayer.getCell(1, 0)).thenReturn(mock(TiledMapTileLayer.Cell.class));

        assertTrue(handler.isGrounded(bounds, previousY, false));
    }

    @Test
    void testIsGroundedIgnorePlatforms() {
        Rectangle bounds = new Rectangle(32, 32, 30, 30);
        float previousY = 32;

        when(groundLayer.getCell(anyInt(), anyInt())).thenReturn(null);
        when(platformLayer.getCell(1, 0)).thenReturn(mock(TiledMapTileLayer.Cell.class));

        assertFalse(handler.isGrounded(bounds, previousY, true));
    }

    @Test
    void testIsNotGroundedWhenMovingUp() {
        Rectangle bounds = new Rectangle(32, 32, 30, 30);
        float previousY = 31;

        when(groundLayer.getCell(1, 0)).thenReturn(mock(TiledMapTileLayer.Cell.class));

        assertFalse(handler.isGrounded(bounds, previousY, false));
    }

    @Test
    void testIsNotGroundedInAir() {
        Rectangle bounds = new Rectangle(32, 32, 30, 30);
        float previousY = 64;

        when(groundLayer.getCell(anyInt(), anyInt())).thenReturn(null);
        when(platformLayer.getCell(anyInt(), anyInt())).thenReturn(null);

        assertFalse(handler.isGrounded(bounds, previousY, false));
    }

    @Test
    void testIsGroundedOnEdges() {
        Rectangle boundsOnLeftEdge = new Rectangle(32, 32, 30, 30);
        float previousY = 32;
        when(groundLayer.getCell(1, 0)).thenReturn(mock(TiledMapTileLayer.Cell.class));
        assertTrue(handler.isGrounded(boundsOnLeftEdge, previousY, false), "Should be grounded on left edge of tile");

        Rectangle boundsOnRightEdge = new Rectangle(34, 32, 30, 30);
        assertTrue(handler.isGrounded(boundsOnRightEdge, previousY, false), "Should be grounded on right edge of tile");
        
        Rectangle boundsSpanningTwoTiles = new Rectangle(33, 32, 32, 30);
        when(groundLayer.getCell(1, 0)).thenReturn(null);
        when(groundLayer.getCell(2, 0)).thenReturn(mock(TiledMapTileLayer.Cell.class));
        assertTrue(handler.isGrounded(boundsSpanningTwoTiles, previousY, false), "Should be grounded if right side is on a tile");
    }

    @Test
    void testIsGroundedGroundLayerNull() {
        when(layers.get("ground")).thenReturn(null);
        MapCollisionHandler handlerNoGround = new MapCollisionHandler(map);
        
        Rectangle bounds = new Rectangle(32, 32, 30, 30);
        float previousY = 32;
        
        assertFalse(handlerNoGround.isGrounded(bounds, previousY, false), "Should not be grounded if groundLayer is null and no platforms");
    }

    @Test
    void testGetGroundY() {
        Rectangle bounds = new Rectangle(32, 32, 30, 30);
        assertEquals(32, handler.getGroundY(bounds, false));

        Rectangle bounds2 = new Rectangle(32, 65, 30, 30);
        assertEquals(96, handler.getGroundY(bounds2, false));
    }
}
