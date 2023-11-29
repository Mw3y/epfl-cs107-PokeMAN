package ch.epfl.cs107.icmon.graphics;

import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.play.engine.Updatable;
import ch.epfl.cs107.play.engine.actor.Graphics;
import ch.epfl.cs107.play.engine.actor.GraphicsEntity;
import ch.epfl.cs107.play.engine.actor.TextGraphics;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Objects.nonNull;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public final class ICMonFightActionSelectionGraphics extends ICMonFightInteractionGraphics implements Updatable {

    private static final float FONT_SIZE = .6f;

    private final Keyboard keyboard;
    private final float scalefactor;
    private final ICMonFightAction[] actions;

    private final GraphicsEntity[] selectors;

    private final Graphics header;

    private ICMonFightAction choice;

    private int currentChoice;

    public ICMonFightActionSelectionGraphics(float scaleFactor, Keyboard keyboard, List<ICMonFightAction> actions) {
        super(scaleFactor);
        assert !actions.isEmpty();
        this.keyboard = keyboard;
        this.scalefactor = scaleFactor;
        this.actions = actions.toArray(new ICMonFightAction[0]);
        selectors = new GraphicsEntity[3];
        header = new GraphicsEntity(new Vector(scaleFactor / 2f, scaleFactor / 3 - 1f), new TextGraphics("Please, select an action", FONT_SIZE, Color.WHITE, null, 0.0f, true, false, Vector.ZERO, TextAlign.Horizontal.CENTER, TextAlign.Vertical.MIDDLE,  1f, 1003));
        currentChoice = 0;
    }

    @Override
    public void update(float deltaTime) {
        // HR : Keyboard management
        if (keyboard.get(Keyboard.LEFT).isPressed()){
            currentChoice = max(0, currentChoice - 1);
        } else if (keyboard.get(Keyboard.RIGHT).isPressed())
            currentChoice = min(currentChoice + 1, actions.length - 1);
        else if (keyboard.get(Keyboard.ENTER).isPressed())
            choice = actions[currentChoice];
        // HR : Prepare the left selector
        if (currentChoice == 0){
            selectors[0] = null;
        } else {
            selectors[0] = new GraphicsEntity(new Vector(scalefactor / 3 - 3f, scalefactor / 3 - 2f), new TextGraphics(actions[currentChoice - 1].name(), FONT_SIZE, Color.WHITE, Color.BLACK, 0.0f, false, false, Vector.ZERO, TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE,  .6f, 1003));
        }
        // HR : Prepare the middle selector
        selectors[1] = new GraphicsEntity(new Vector(scalefactor  * 2 / 3 - 3f, scalefactor / 3 - 2f), new TextGraphics(actions[currentChoice].name(), FONT_SIZE, Color.WHITE, null, 0.0f, true, false, Vector.ZERO, TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE,  1.0f, 1003));
        // HR : Prepare the Right selector
        if (currentChoice == actions.length - 1 ){
            selectors[2] = null;
        } else {
            selectors[2] = new GraphicsEntity(new Vector(scalefactor * 2 / 3 + 1f, scalefactor / 3 - 2f), new TextGraphics(actions[currentChoice + 1].name(), FONT_SIZE, Color.WHITE, null, 0.0f, false, false, Vector.ZERO, TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE,  .6f, 1003));
        }
    }

    public ICMonFightAction choice(){
        return choice;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // HR : Draw the header
        header.draw(canvas);
        // HR : Draw the selectors that are visible (not null)
        for (var selector : selectors)
            if(nonNull(selector))
                selector.draw(canvas);
    }
}
