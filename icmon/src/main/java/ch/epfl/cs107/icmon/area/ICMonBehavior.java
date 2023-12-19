package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.AreaBehavior;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

import java.awt.Color;
import java.util.Arrays;

public class ICMonBehavior extends AreaBehavior {

    /**
     * The behavior of an ICMon area.
     * @param window (Window): graphic context, not null
     * @param name (String): name of the behavior image, not null
     */
    public ICMonBehavior(Window window, String name) {
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        // Create the cell matrix
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ICMonCellType color = ICMonCellType.toType(getRGB(height - 1 - y, x));
                setCell(x, y, new ICMonCell(x, y, color));
            }
        }
    }

    /**
     * Represents the conditions to walk on a cell.
     */
    public enum AllowedWalkingType {
        NONE,
        SURF,
        FEET,
        ALL
    }

    /**
     * Represents a cell type based on its color on the behavior map and its walking type.
     */
    public enum ICMonCellType {
        NULL(0, AllowedWalkingType.NONE),
        WALL(-16777216, AllowedWalkingType.NONE),
        BUILDING(-8750470, AllowedWalkingType.NONE),
        INTERACT(-256, AllowedWalkingType.NONE),
        DOOR(-195580, AllowedWalkingType.ALL),
        INDOOR_WALKABLE(-1, AllowedWalkingType.FEET),
        OUTDOOR_WALKABLE(-14112955, AllowedWalkingType.FEET),
        WATER(-16776961, AllowedWalkingType.SURF),
        GRASS(-16743680, AllowedWalkingType.FEET),
        TALL_GRASS(new Color(8, 69, 0).getRGB(), AllowedWalkingType.FEET);

        final int type;
        final AllowedWalkingType allowedWalkingType;

        ICMonCellType(int type, AllowedWalkingType allowedWalkingType) {
            this.type = type;
            this.allowedWalkingType = allowedWalkingType;
        }

        /**
         * Returns the cell type based on the rgb int color of the cell.
         * 
         * @param type - The rgb int color of the cell
         * @return the cell type.
         */
        public static ICMonCellType toType(int type) {
            return Arrays.stream(ICMonCellType.values())
                    .filter(ict -> ict.type == type).findFirst().orElse(NULL);
        }

        /**
         * Checks whether a cell is walkable or not by an entity.
         *
         * @param entity - The entity that wants to walk on this cell
         * @return true if the entity can walk on that cell.
         */
        public boolean isWalkable(Interactable entity) {
            // TODO: More complex behavior
            return !allowedWalkingType.equals(AllowedWalkingType.NONE);
        }
    }

    public class ICMonCell extends Cell {
        /// Type of the cell following the enum
        private final ICMonCellType type;

        /**
         * A cell of the ICMon area game.
         *
         * @param x    (int): x coordinate of the cell
         * @param y    (int): y coordinate of the cell
         * @param type (ICMonCellType), not null
         */
        private ICMonCell(int x, int y, ICMonCellType type) {
            super(x, y);
            this.type = type;
        }

        /**
         * Gets the type of the cell.
         * @return the type object of the cell.
         */
        public ICMonCellType getType() {
            return type;
        }

        /**
         * Gets the allowed walking mode of the cell.
         * @return the allowed walking type object of the cell.
         */
        public AllowedWalkingType getWalkingType() {
            return type.allowedWalkingType;
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        @Override
        protected boolean canEnter(Interactable entity) {
            // Allow an entity that doesn't take space to be registered everywhere
            return (type.isWalkable(entity) && !takeCellSpace()) || !entity.takeCellSpace();
        }

        @Override
        protected void enter(Interactable entity) {
            super.enter(entity);
        }

        @Override
        public boolean takeCellSpace() {
            return entities.stream().anyMatch(Interactable::takeCellSpace);
        }

        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return false;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
            ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
        }
    }
}