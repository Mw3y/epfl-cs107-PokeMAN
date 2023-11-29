package ch.epfl.cs107.icmon.graphics;

import ch.epfl.cs107.play.engine.actor.GraphicsEntity;
import ch.epfl.cs107.play.engine.actor.TextGraphics;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public final class ICMonFightTextGraphics extends ICMonFightInteractionGraphics {

    private static final float FONT_SIZE = .6f;
    private static final int MAX_LINE_SIZE = 30;

    private final GraphicsEntity[] lines;

    public ICMonFightTextGraphics(float scaleFactor, String text) {
        super(scaleFactor);
        // HR : Prepare the lines
        lines = new GraphicsEntity[2];
        lines[0] = new GraphicsEntity(new Vector(1.5f, scaleFactor / 3 - 2f * FONT_SIZE), new TextGraphics("", FONT_SIZE, Color.WHITE, null, 0.0f, false, false, Vector.ZERO, TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE,  1.0f, 1003));
        lines[1] = new GraphicsEntity(new Vector(1.5f, scaleFactor / 3 - 4f * FONT_SIZE), new TextGraphics("", FONT_SIZE, Color.WHITE, null, 0.0f, false, false, Vector.ZERO, TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE, 1.0f, 1003));
        // HR : Fill the placeholders with the actual text
        textInPlaceholders(text);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // HR : Draw the lines
        for (var line : lines)
            line.draw(canvas);
    }

    // ============================================================================================
    // ==================================== UTILITY FUNCTIONS =====================================
    // ============================================================================================

    private void textInPlaceholders(String text){
        if(text == null)
            text = "";
        var cursor = 0;
        var lengthToPush = text.length()-cursor;
        // For each line
        for(int i = 0; i<2; i++){
            // If some text still need to be pushed : fill the next line
            if(lengthToPush <= 0)
                ((TextGraphics)lines[i].getGraphics()).setText("");
            else if(lengthToPush <= MAX_LINE_SIZE) {
                ((TextGraphics)lines[i].getGraphics()).setText(text.substring(cursor));
                cursor += lengthToPush;
            }
            else{
                var maxSize = MAX_LINE_SIZE;
                var toConcat = "";
                if(i == 1){
                    maxSize -= 4;
                    toConcat += " ...";
                }
                var sub = text.substring(cursor, cursor+maxSize+1);
                var last = sub.lastIndexOf(' ');
                if(last == -1)
                    System.out.println("Error: You get a Word longer than " + MAX_LINE_SIZE);
                ((TextGraphics)lines[i].getGraphics()).setText(sub.substring(0, last)+toConcat);
                cursor = cursor+last+1;
            }
            lengthToPush = text.length()-cursor;
        }
    }

}
