package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.AreaBehavior;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

import java.util.Arrays;

public class ICMonBehavior extends AreaBehavior {

    public ICMonBehavior(Window window, String name) {
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ICMonCellType color = ICMonCellType.toType(getRGB(height - 1 - y, x));
                setCell(x, y, new ICMonCell(x, y, color));
            }
        }
    }

    public enum AllowedWalkingType {
        NONE,
        SURF,
        FEET,
        ALL
    }

    public enum ICMonCellType {
        NULL(0, AllowedWalkingType.NONE),
        WALL(-16777216, AllowedWalkingType.NONE),
        BUILDING(-8750470, AllowedWalkingType.NONE),
        INTERACT(-256, AllowedWalkingType.NONE),
        DOOR(-195580, AllowedWalkingType.ALL),
        INDOOR_WALKABLE(-1, AllowedWalkingType.FEET),
        OUTDOOR_WALKABLE(-14112955, AllowedWalkingType.FEET),
        WATER(-16776961, AllowedWalkingType.SURF),
        GRASS(-16743680, AllowedWalkingType.FEET);

        final int type;

        final AllowedWalkingType allowedWalkingType;

        ICMonCellType(int type, AllowedWalkingType allowedWalkingType) {
            this.type = type;
            this.allowedWalkingType = allowedWalkingType;
        }

        public static ICMonCellType toType(int type) {
            return Arrays.stream(ICMonCellType.values())
                    .filter(ict -> ict.type == type).findFirst().orElse(NULL);
        }

        public boolean isWalkable(Interactable entity) {
            return !allowedWalkingType.equals(AllowedWalkingType.NONE);
        }
    }

    public class ICMonCell extends Cell {
        /// Type of the cell following the enum
        private final ICMonCellType type;

        /**
         * Default Tuto2Cell Constructor
         *
         * @param x    (int): x coordinate of the cell
         * @param y    (int): y coordinate of the cell
         * @param type (EnigmeCellType), not null
         */
        public ICMonCell(int x, int y, ICMonCellType type) {
            super(x, y);
            this.type = type;
        }

        public ICMonCellType getType() {
            return type;
        }

        public AllowedWalkingType getWalkingType() {
            return type.allowedWalkingType;
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        @Override
        protected boolean canEnter(Interactable entity) {
            // TODO: Allow entities that does not take space to enter
            return type.isWalkable(entity) && !takeCellSpace();
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