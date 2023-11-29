package ch.epfl.cs107.play.engine.actor;


import ch.epfl.cs107.play.engine.Updatable;
import ch.epfl.cs107.play.engine.actor.Graphics;
import ch.epfl.cs107.play.engine.actor.ImageGraphics;
import ch.epfl.cs107.play.engine.actor.TextGraphics;
import ch.epfl.cs107.play.io.*;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * XML Dialog
 * Shou
 */
public final class Dialog implements Updatable, Graphics {

    private final Queue<String> dialog = new ArrayDeque<>();

    /// Static variables for positioning the text and the dialog window
    private static final float FONT_SIZE = 0.6f;

    /// Number max of char per line of text
    private static final int MAX_LINE_SIZE = 35;

    private static final String BACKGROUND_NAME = "dialog";

    /// Sprite and text graphics line
    private final ImageGraphics sprite;
    private final TextGraphics[] lines;

    private boolean isCompleted = false;

    public Dialog(String path){

        final float height = 13f / 4;
        final float width = 13f;

        final Vector firstLineAnchor = new Vector(0.5f, height - FONT_SIZE);
        final Vector secondLineAnchor = new Vector(0.5f, height - 2.5f * FONT_SIZE);
        final Vector thirdLineAnchor = new Vector(0.5f, height - 4 * FONT_SIZE);

        sprite = new ImageGraphics(ResourcePath.getSprite(BACKGROUND_NAME), width, height, null, Vector.ZERO, 1.0f, 3000);

        lines = new TextGraphics[3];

        lines[0] = new TextGraphics("", FONT_SIZE, Color.BLACK, null, 0.0f, false, false, firstLineAnchor, TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE,  1.0f, 3001);
        lines[1] = new TextGraphics("", FONT_SIZE, Color.BLACK, null, 0.0f, false, false, secondLineAnchor, TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE, 1.0f, 3001);
        lines[2] = new TextGraphics("", FONT_SIZE, Color.BLACK, null, 0.0f, false, false, thirdLineAnchor, TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE, 1.0f, 3001);

        initialize(new ResourceFileSystem(DefaultFileSystem.INSTANCE), ResourcePath.getDialog(path));
        update(0); // HR : Update to the first text
    }

    private void initialize(FileSystem fs, String fileName){

        try(var input = fs.read(fileName)) {
            // Set up the document builder
            var db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // Change the entity resolver
            db.setEntityResolver((publicId, systemId) -> new InputSource(new StringReader("")));

            // Parse the xml document
            var doc = db.parse(input);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            // Fetch all the string resources forming the dialog
            var nList = doc.getElementsByTagName("string");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    var id = eElement.getAttribute("id");
                    var value = eElement.getTextContent();
                    if (!value.isBlank() && id.isBlank()){
                        dialog.add(value);
                    } else if (value.isBlank() && !id.isBlank()) {
                        // TODO HR : Fetch the corresponding value using the id
                    } else if (value.isBlank() && id.isBlank()){
                        throw new IllegalStateException("Cannot configure the dialog, either give the value or the id to a string resource");
                    } else {
                        throw new IllegalStateException("Cannot configure the dialog, either give the value or the id to a string resource, not both");
                    }
                }
            }
        } catch (IOException e) {
            // Empty on purpose, will return null as an error
            System.out.printf("Dialog File : %s not found", fileName);
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(float deltaTime) {
        int cursor;
        cursor = 0;
        var text = dialog.poll();
        // HR : If this dialog completes, do not update since there is nothing to update
        if (text == null) {
            isCompleted = true;
            return;
        }
        int lengthToPush = text.length()-cursor;

        // For each line
        for(int i = 0; i<3; i++){

            // If some text still need to be pushed : fill the next line
            if(lengthToPush <= 0) {
                lines[i].setText("");
            }
            else if(lengthToPush <= MAX_LINE_SIZE) {
                lines[i].setText(text.substring(cursor));
                cursor += lengthToPush;
            }
            else{
                int maxSize = MAX_LINE_SIZE;
                String toConcat = "";

                if(i == 2){
                    maxSize -= 4;
                    toConcat += " ...";
                }

                String sub = text.substring(cursor, cursor+maxSize+1);
                int last = sub.lastIndexOf(' ');
                if(last == -1){
                    System.out.println("Error: You get a Word longer than " + MAX_LINE_SIZE);
                }

                lines[i].setText(sub.substring(0, last)+toConcat);
                cursor = cursor+last+1;
            }

            lengthToPush = text.length()-cursor;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // Compute width, height and anchor
        float width = canvas.getTransform().getX().getX();
        float height = canvas.getTransform().getY().getY();

        float ratio = canvas.getWidth()/(float)canvas.getHeight();
        if(ratio > 1)
            height = width / ratio;
        else
            width = height * ratio;

        final Transform transform = Transform.I.translated(canvas.getPosition().sub(width/2, height/2));
        sprite.setRelativeTransform(transform);
        sprite.setWidth(width);
        sprite.setHeight(height/4);
        sprite.draw(canvas);

        lines[0].setAnchor(new Vector(0.5f, height/4 - FONT_SIZE));
        lines[1].setAnchor(new Vector(0.5f, height/4 - 2.5f * FONT_SIZE));
        lines[2].setAnchor(new Vector(0.5f, height/4 - 4 * FONT_SIZE));
        for(int i = 0; i < 3; i++){
            lines[i].setRelativeTransform(transform);
            lines[i].draw(canvas);
        }
    }

    public boolean isCompleted(){
        return isCompleted;
    }

}
