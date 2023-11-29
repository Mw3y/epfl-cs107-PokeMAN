package ch.epfl.cs107.play.areagame;

import ch.epfl.cs107.play.engine.Game;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

import java.util.*;
import java.util.Map;


/**
 * AreaGames are a concept of Game which is displayed in a (MxN) Grid which is called an Area
 * An AreaGame has multiple Areas
 */
abstract public class AreaGame implements Game, PauseMenu.Pausable {

    // Context objects
    private Window window;
    private FileSystem fileSystem;
    /// A map containing all the Area of the Game
    private Map<String, Area> areas;
    /// The current area the game is in
    private Area currentArea;
    /// pause mechanics and menu to display. May be null
    private boolean paused, requestPause;
    private PauseMenu menu;

    /**
     * Add an Area to the AreaGame list
     * @param a (Area): The area to add, not null
     */
    public final void addArea(Area a){
        areas.put(a.getTitle(), a);
    }

    /**
     * Setter for the current area: Select an Area in the list from its key
     * - the area is then begin or resume depending if the area is already started or not and if it is forced
     * @param key (String): Key of the Area to select, not null
     * @param forceBegin (boolean): force the key area to call begin even if it was already started
     * @return (Area): after setting it, return the new current area
     */
    protected final Area setCurrentArea(String key, boolean forceBegin){
    	Area newArea = areas.get(key);
    	
    	if(newArea == null) {
            System.out.println("New Area not found, keep previous one");
    	}else {
    		// Stop previous area if it exist
            if(currentArea != null){
                currentArea.suspend();
                currentArea.purgeRegistration(); // Is useful?
            }
            
            currentArea = newArea;
            
            // Start/Resume the new one
            if (forceBegin || !currentArea.isStarted()) {
                currentArea.begin(window, fileSystem);
            } else {
                currentArea.resume(window, fileSystem);
            }
    	}
    	
        return currentArea;
    }

    /**
     * Set the pause menu
     * @param menu (PauseMenu) : the new pause menu, not null
     * @return (PauseMenu): the new pause menu, not null
     */
    protected final PauseMenu setPauseMenu(PauseMenu menu){
        this.menu = menu;
        this.menu.begin(window, fileSystem);
        this.menu.setOwner(this);
        return menu;
    }

    /**@return (Window) : the Graphic and Audio context*/
    protected final Window getWindow(){
        return window;
    }

    /**@return (FIleSystem): the linked file system*/
    protected final FileSystem getFileSystem(){
        return fileSystem;
    }

    /**
     * Getter for the current area
     * @return (Area)
     */
    protected final Area getCurrentArea(){
        return this.currentArea;
    }

    /// AreaGame implements Playable

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {

        // Keep context
        this.window = window;
        this.fileSystem = fileSystem;

        areas = new HashMap<>();
        paused = false;
        return true;
    }


    @Override
    public void update(float deltaTime) {
        // HR : Check if pause was not requested on the previous interaction
        paused = requestPause;

        if(paused && menu != null) {
            menu.update(deltaTime);
        }
        else{
            currentArea.update(deltaTime);
        }
    }

    @Override
    public void end() {
        // TODO save the game states somewhere
    }

    @Override
    public void requestPause(){
        requestPause = true;
    }

    @Override
    public void requestResume(){
        requestPause = false;
    }

    @Override
    public boolean isPaused(){
       return paused;
    }
}
