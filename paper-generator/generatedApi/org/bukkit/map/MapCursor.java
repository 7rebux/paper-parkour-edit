package org.bukkit.map;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a cursor on a map.
 */
public final class MapCursor {
    private byte x, y;
    private byte direction, type;
    private boolean visible;
    private net.kyori.adventure.text.Component caption; // Paper

    /**
     * Initialize the map cursor.
     *
     * @param x The x coordinate, from -128 to 127.
     * @param y The y coordinate, from -128 to 127.
     * @param direction The facing of the cursor, from 0 to 15.
     * @param type The type (color/style) of the map cursor.
     * @param visible Whether the cursor is visible by default.
     * @deprecated Magic value
     */
    @Deprecated
    public MapCursor(byte x, byte y, byte direction, byte type, boolean visible) {
        this(x, y, direction, type, visible, (String) null); // Paper
    }

    /**
     * Initialize the map cursor.
     *
     * @param x The x coordinate, from -128 to 127.
     * @param y The y coordinate, from -128 to 127.
     * @param direction The facing of the cursor, from 0 to 15.
     * @param type The type (color/style) of the map cursor.
     * @param visible Whether the cursor is visible by default.
     */
    public MapCursor(byte x, byte y, byte direction, @NotNull Type type, boolean visible) {
        this(x, y, direction, type, visible, (String) null); // Paper
    }

    /**
     * Initialize the map cursor.
     *
     * @param x The x coordinate, from -128 to 127.
     * @param y The y coordinate, from -128 to 127.
     * @param direction The facing of the cursor, from 0 to 15.
     * @param type The type (color/style) of the map cursor.
     * @param visible Whether the cursor is visible by default.
     * @param caption cursor caption
     * @deprecated Magic value. Use {@link #MapCursor(byte, byte, byte, byte, boolean, net.kyori.adventure.text.Component)}
     */
    @Deprecated
    public MapCursor(byte x, byte y, byte direction, byte type, boolean visible, @Nullable String caption) {
        this.x = x;
        this.y = y;
        setDirection(direction);
        setRawType(type);
        this.visible = visible;
        this.caption = caption == null ? null : net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().deserialize(caption); // Paper
    }
    // Paper start
    /**
     * Initialize the map cursor.
     *
     * @param x The x coordinate, from -128 to 127.
     * @param y The y coordinate, from -128 to 127.
     * @param direction The facing of the cursor, from 0 to 15.
     * @param type The type (color/style) of the map cursor.
     * @param visible Whether the cursor is visible by default.
     * @param caption cursor caption
     * @deprecated Magic value
     */
    @Deprecated
    public MapCursor(byte x, byte y, byte direction, byte type, boolean visible, net.kyori.adventure.text.@Nullable Component caption) {
        this.x = x; this.y = y; this.visible = visible; this.caption = caption;
        setDirection(direction);
        setRawType(type);
    }
    /**
     * Initialize the map cursor.
     *
     * @param x The x coordinate, from -128 to 127.
     * @param y The y coordinate, from -128 to 127.
     * @param direction The facing of the cursor, from 0 to 15.
     * @param type The type (color/style) of the map cursor.
     * @param visible Whether the cursor is visible by default.
     * @param caption cursor caption
     */
    public MapCursor(byte x, byte y, byte direction, @NotNull Type type, boolean visible, net.kyori.adventure.text.@Nullable Component caption) {
        this.x = x; this.y = y; this.visible = visible; this.caption = caption;
        setDirection(direction);
        setType(type);
    }
    // Paper end

    /**
     * Initialize the map cursor.
     *
     * @param x The x coordinate, from -128 to 127.
     * @param y The y coordinate, from -128 to 127.
     * @param direction The facing of the cursor, from 0 to 15.
     * @param type The type (color/style) of the map cursor.
     * @param visible Whether the cursor is visible by default.
     * @param caption cursor caption
     */
    public MapCursor(byte x, byte y, byte direction, @NotNull Type type, boolean visible, @Nullable String caption) {
        this.x = x;
        this.y = y;
        setDirection(direction);
        setType(type);
        this.visible = visible;
        this.caption = caption == null ? null : net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().deserialize(caption); // Paper
    }

    /**
     * Get the X position of this cursor.
     *
     * @return The X coordinate.
     */
    public byte getX() {
        return x;
    }

    /**
     * Get the Y position of this cursor.
     *
     * @return The Y coordinate.
     */
    public byte getY() {
        return y;
    }

    /**
     * Get the direction of this cursor.
     *
     * @return The facing of the cursor, from 0 to 15.
     */
    public byte getDirection() {
        return direction;
    }

    /**
     * Get the type of this cursor.
     *
     * @return The type (color/style) of the map cursor.
     */
    @NotNull
    public Type getType() {
        // It should be impossible to set type to something without appropriate Type, so this shouldn't return null
        return Type.byValue(type);
    }

    /**
     * Get the type of this cursor.
     *
     * @return The type (color/style) of the map cursor.
     * @apiNote Internal Use Only
     */
    @org.jetbrains.annotations.ApiStatus.Internal // Paper
    public byte getRawType() {
        return type;
    }

    /**
     * Get the visibility status of this cursor.
     *
     * @return True if visible, false otherwise.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set the X position of this cursor.
     *
     * @param x The X coordinate.
     */
    public void setX(byte x) {
        this.x = x;
    }

    /**
     * Set the Y position of this cursor.
     *
     * @param y The Y coordinate.
     */
    public void setY(byte y) {
        this.y = y;
    }

    /**
     * Set the direction of this cursor.
     *
     * @param direction The facing of the cursor, from 0 to 15.
     */
    public void setDirection(byte direction) {
        if (direction < 0 || direction > 15) {
            throw new IllegalArgumentException("Direction must be in the range 0-15");
        }
        this.direction = direction;
    }

    /**
     * Set the type of this cursor.
     *
     * @param type The type (color/style) of the map cursor.
     */
    public void setType(@NotNull Type type) {
        setRawType(type.value);
    }

    /**
     * Set the type of this cursor.
     *
     * @param type The type (color/style) of the map cursor.
     * @deprecated use {@link #setType(Type)}
     */
    @Deprecated(forRemoval = true, since = "1.20.2") // Paper
    public void setRawType(byte type) {
        if (type < 0 || type > Type.UPPER_MAP_CURSOR_TYPE_BOUND) { // Paper
            throw new IllegalArgumentException("Type must be in the range 0-" + Type.UPPER_MAP_CURSOR_TYPE_BOUND); // Paper
        }
        this.type = type;
    }

    /**
     * Set the visibility status of this cursor.
     *
     * @param visible True if visible.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // Paper start
    /**
     * Gets the caption on this cursor.
     *
     * @return caption
     */
    public net.kyori.adventure.text.@Nullable Component caption() {
        return this.caption;
    }
    /**
     * Sets the caption on this cursor.
     *
     * @param caption new caption
     */
    public void caption(net.kyori.adventure.text.@Nullable Component caption) {
        this.caption = caption;
    }
    // Paper end
    /**
     * Gets the caption on this cursor.
     *
     * @return caption
     * @deprecated in favour of {@link #caption()}
     */
    @Nullable
    @Deprecated // Paper
    public String getCaption() {
        return this.caption == null ? null : net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().serialize(this.caption); // Paper
    }

    /**
     * Sets the caption on this cursor.
     *
     * @param caption new caption
     * @deprecated in favour of {@link #caption(net.kyori.adventure.text.Component)}
     */
    @Deprecated // Paper
    public void setCaption(@Nullable String caption) {
        this.caption = caption == null ? null : net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().deserialize(caption); // Paper
    }

    /**
     * Represents the standard types of map cursors. More may be made
     * available by resource packs - the value is used by the client as an
     * index in the file './assets/minecraft/textures/map/map_icons.png' from minecraft.jar or from a
     * resource pack.
     */
    public enum Type implements Keyed {
        // Paper start - Generated/MapCursorType
        // @GeneratedFrom 1.20.6
        BANNER_BLACK(25, "banner_black"),
        BANNER_BLUE(21, "banner_blue"),
        BANNER_BROWN(22, "banner_brown"),
        BANNER_CYAN(19, "banner_cyan"),
        BANNER_GRAY(17, "banner_gray"),
        BANNER_GREEN(23, "banner_green"),
        BANNER_LIGHT_BLUE(13, "banner_light_blue"),
        BANNER_LIGHT_GRAY(18, "banner_light_gray"),
        BANNER_LIME(15, "banner_lime"),
        BANNER_MAGENTA(12, "banner_magenta"),
        BANNER_ORANGE(11, "banner_orange"),
        BANNER_PINK(16, "banner_pink"),
        BANNER_PURPLE(20, "banner_purple"),
        BANNER_RED(24, "banner_red"),
        BANNER_WHITE(10, "banner_white"),
        BANNER_YELLOW(14, "banner_yellow"),
        BLUE_MARKER(3, "blue_marker"),
        FRAME(1, "frame"),
        @org.bukkit.MinecraftExperimental(org.bukkit.MinecraftExperimental.Requires.TRADE_REBALANCE)
        @org.jetbrains.annotations.ApiStatus.Experimental
        JUNGLE_TEMPLE(32, "jungle_temple"),
        MANSION(8, "mansion"),
        MONUMENT(9, "monument"),
        PLAYER(0, "player"),
        PLAYER_OFF_LIMITS(7, "player_off_limits"),
        PLAYER_OFF_MAP(6, "player_off_map"),
        RED_MARKER(2, "red_marker"),
        RED_X(26, "red_x"),
        @org.bukkit.MinecraftExperimental(org.bukkit.MinecraftExperimental.Requires.TRADE_REBALANCE)
        @org.jetbrains.annotations.ApiStatus.Experimental
        SWAMP_HUT(33, "swamp_hut"),
        TARGET_POINT(5, "target_point"),
        TARGET_X(4, "target_x"),
        @org.bukkit.MinecraftExperimental(org.bukkit.MinecraftExperimental.Requires.UPDATE_1_21)
        @org.jetbrains.annotations.ApiStatus.Experimental
        TRIAL_CHAMBERS(34, "trial_chambers"),
        @org.bukkit.MinecraftExperimental(org.bukkit.MinecraftExperimental.Requires.TRADE_REBALANCE)
        @org.jetbrains.annotations.ApiStatus.Experimental
        VILLAGE_DESERT(27, "village_desert"),
        @org.bukkit.MinecraftExperimental(org.bukkit.MinecraftExperimental.Requires.TRADE_REBALANCE)
        @org.jetbrains.annotations.ApiStatus.Experimental
        VILLAGE_PLAINS(28, "village_plains"),
        @org.bukkit.MinecraftExperimental(org.bukkit.MinecraftExperimental.Requires.TRADE_REBALANCE)
        @org.jetbrains.annotations.ApiStatus.Experimental
        VILLAGE_SAVANNA(29, "village_savanna"),
        @org.bukkit.MinecraftExperimental(org.bukkit.MinecraftExperimental.Requires.TRADE_REBALANCE)
        @org.jetbrains.annotations.ApiStatus.Experimental
        VILLAGE_SNOWY(30, "village_snowy"),
        @org.bukkit.MinecraftExperimental(org.bukkit.MinecraftExperimental.Requires.TRADE_REBALANCE)
        @org.jetbrains.annotations.ApiStatus.Experimental
        VILLAGE_TAIGA(31, "village_taiga");
        // Paper end - Generated/MapCursorType

        static final int UPPER_MAP_CURSOR_TYPE_BOUND = Type.values().length - 1; // Paper - cached max value of Type

        private byte value;
        private final NamespacedKey key;

        private Type(int value, String key) {
            this.value = (byte) value;
            this.key = NamespacedKey.minecraft(key);
        }

        @NotNull
        @Override
        public NamespacedKey getKey() {
            return key;
        }

        /**
         * Gets the internal value of the cursor.
         *
         * @return the value
         * @apiNote Internal Use Only
         */
        @org.jetbrains.annotations.ApiStatus.Internal // Paper
        public byte getValue() {
            return value;
        }

        /**
         * Get a cursor by its internal value.
         *
         * @param value the value
         * @return the matching type
         * @apiNote Internal Use Only
         */
        @org.jetbrains.annotations.ApiStatus.Internal // Paper
        @Nullable
        public static Type byValue(byte value) {
            for (Type t : values()) {
                if (t.value == value) return t;
            }
            return null;
        }
    }

}