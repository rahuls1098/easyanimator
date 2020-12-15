# The Model


The *model package* is composed of an **interface**: *IAnimatorModel*, its implementation **class**: *AnimatorModelImpl*, and 3 other **packages**: *animation*, *shape* and *util*.

## Packages

**Overview**

The shape package carry the representation of a shape, the animation package carry the representation of an animation and the util package contains classes and interfaces that take care of the specificities of how the atomic data carried by shape and animation should be stored. The shape and the animation interact and inter-connect via the data structures in the model implementation. For example, we have a map that maps shape names to their corresponding list of animations.

### *util*
- **IColor** - stores the color information as *rgb* values.
- **IDimensions** - stores the values needed to specify any shape in a *double[]*
- **IDuration** - stores a duration as initial and final time values
- **IPosition** - stores a position as x and y values

### *shape*

Shape fields are fully abstracted onto **AShape** as:
- **name** : String - stores the unique name of the shape
- **type** : ShapeType - which shape type is this shape?
- **color** : IColor - the color of this shape
- **position** : IPosition - the position of this shape
- **dimensions** : IDimensions - the dimensions of this shape
- **duration** : IDuration - the duration of this shape

#### IShape
Currently supports Rectangle and Ellipse. 
 - **textView() : String** - text view
 - **textView(double) : String** - text view as modified by *speed*
 - **getName() : String** - gets shape name
 - **getType() : ShapeType** - gets shape type
 - **getColor() : IColor** - gets color
 - **getPosition() : IPosition** - gets position
 - **getDimensions() : IDimensions** - gets duration
 - **getDuration() : IDuration** - sets duration
 - **setPosition(IPosition) : void** - sets position
 - **setColor(IColor) : void** - sets color
 - **setDimensions(IDimensions) : void** - sets dimensions
 - **copy() : IShape** - copies the shape
 - **svgView(double) : String** - gets the *svg* information for the shape

### *animation*

The **AAnimation** represents Move, ChangeColor, and Scale, and stores the following fields: 
- **type** : AnimationType - the type of the animation
- **shape** : IShape - the associated shape object *(this is how shape and animation are connected)*
- **duration** : IDuration - the duration


#### IAnimation

- **getShape() : IShape**  - gets the associated shape
- **getDuration() : IDuration**  - gets the duration of the animation
- **getType() : AnimationType**  - gets the type of the animation
- **textView() : String**  - gets the text view of the animation
- **textView(double) : String**  - gets the text view speeded up by *speed*
- **svgView(double) : String**  - gets the *svg* view of the animation
- **editShape(double) : void**  - edits the shape (for tweening)
- **tween(double, double, double) : double** - tweens

### IAnimatorModel
The methods of interest in IAnimatorModel are

- **addAnimation** (IAnimation) : void - adds an animation to the model
- **getShapeFromName** (String) : void - gets a shape from it's name
- **deleteShape** (IShape) : void - deletes a shape
- **deleteAnimation** (IAnimation) : void - deletes an animation
- **textView** (String) : String - gets the text view
- **textView** (double) : String - gets the text view with speed considered
- **svgView** (double, boolean) : String - gets the svg view with speed/looping considered
- *various getters*

### *AnimatorModelImpl*

- **animationList** : List\<IAnimation\> - the list of animations
- **shapeList** : List\<IShape\> - the list of shapes
- **shapeCopyList** : List\<IShape\> - a copy of the list of shapes
- **nameOrig** : Map<String, IShape> - a map of the name of the shapes to the original shapes
- **nameShape** : Map<String, IShape> - a map of the name of the shapes to the current shapes
- **nameAnimation** : Map<String, List\<IAnimation\>> - a map of the name of the shape to their associated animation list
- **startAnimation** : Map<Double, List\<IAnimation\>> - a map of start time to the animation list that starts at that time
- **endAnimation** : Map<Double, List\<IAnimation\>> - a map of end time to the animation list that ends at that time

As shapes and animations are added to the model the various data structures are filled and the sortedness (of lists) and the uniqueness (of shapes), etc. are maintained. 

See: class diagram of the model package.



### Controller and the View
The controller consists of the methods to parse command line arguments. With these arguments, the respective view is constructed and run.
Has error message dialogue boxes to warn user of incorrect command line arguments. The handlers consist
of methods to dispatch action events to their respective methods in the view.
The view is one of four:
- SVG: Outputs SVG code to console or to a file
- TextView: Outputs animation description to console or file
- Visual: Outputs animation to a JPanel
- Interactive: Outputs animation to JPanel with buttons set with action listeners

The **controller package** consists of the IController interface, AnimatorController,
ButtonHandler, and InputController. 

**AnimatorController**
Parses command line inputs and constructs view and model accordingly.
- dialogue : JPanel      - error message window
- view : IView           - view rendered
- viewType : ViewType    - type of view rendered
- speed : double         - speed of animation
- animationFile : String - name of input file
- outputFile : String    - method of viewing
- error : JOptionPane    - error box
- inputController : InputController - class for event handling

**IController**
Represents the instantiation of the model and view by the controller. 
 - go : void - runs the model ie. calls the view
 - parseCommandLine(String[]) : void - parses the user input from the CLI

**ButtonHandler**
Handles the action events associated with the buttons in the interactive view.
 - setButtonHandlerMap : void - initializes map representing names of actions to Runnables.
 - actionPerformed(ActionEvent) : dispatches commands to Runnable
 
**InputController**
Receives ActionEvents and consists of classes representing the Runnable event.
Instantiates view and sets up the ActionEvent map for the buttons.
 - setView(IHybridView) : void - Sets the view for the controller and instantiates buttons
 - setUpButtonCommands() : void - maps buttons' String actions to their Runnable
 - Runnables 
    - PausePlayAction : Pauses and resumes animation accordingly
    - RestartAction : Restarts animation and resets shapes
    - ToggleLoopAction : Toggles on and off the animation looping
    - SetAction : Sets the speed of the animation
    - ExportAction : Exports the animation to an SVG file with speed/looping accounted for
   
 
**

---------

The **readbuild package** consists of the TweenModelBuilder interface and the AnimationFileReader class. (provided)
- AnimationFileReader - reads the file
- TweenModelBuilder - creates objects of our model

---------
The **view package** consists of :
- **AnimationPanel**: 
  - shapeCopies : List<IShape> - copy list of original shapes
  - shapeOriginal : List<IShape> - original shapes to duplicate upon restarting
  - tick : double              - current tick
  - speed : double             - speed of animation rendering
- **AView**: 
Contains abstraction for all view types, and especially for the visual 
and interactive views. Consolidates updating of animation and resetting of 
objects into one place. 
  - aniPanel : AnimationPanel - renders animation
  - model : IAnimatorModel  - animation
  - speed : double          - ticks per second
  - output : String         - the output file
  - tick : double           - current tick
  - rate : int              - delay (frequency of updating animation)
  - isLoop : boolean        - is animation looping or not
  - viewType : ViewType     - represents type of view
- **IView** : implemented by various view representations, has the following methods:
  - run : void - runs the animation
  - makeVisible : void - makes the view visible
  - refresh : void - signals the view to draw itself
  - getViewString : - gets the String representation 
  - *(some methods may be suppressed in other views)*
- **IViewFactory** : interface that represents a view creation method
- **IHybridView** : interface that represents an interactive view
- **SVGViewImpl** : outputs the svg view data
- **TextViewImpl** : outputs the text-view data
- **ViewFactoryImpl** : dispatches to each view appropriately
- **ViewType** : Enum representing the view types
- **VisualViewImpl** : outputs the rendering of the Visual
- **HybridViewImpl** : outputs the animation rendering and allows for user interaction

---------

### Changes to the model
**(from the previous assignment)**
- removed the animation sorting bug
- made the copy of the shape list to not mutate the original shape
- added a sortshape method
- fixed the delete methods
- added SVG view to each aspect of the model to return an svg string
- changed the RGB to handle [0, 1] range but render as [0, 255]
- limitation: the speed is valid only from 0 to 1000

### Changes to views and controller
- VisualViewImpl methods and fields abstracted to AView so they can be 
accessed by HybridViewImpl which is extending AView
- Added constructor to SVGViewImpl to take in looping parameter and added
the looping parameter to SVG methods
- Added creation of input controller and setting of its views in animator controller

#### Command line instructions
Any combination of the four inputs *tags* are valid, or just -if and -iv. 
**format**: `<-if ...> <-iv ...> <-o ...> <-speed ...> `
- `-if` "name-of-animation-file"
- `-iv` "type-of-view"
    - visual, interactive, svg, text
- `-o` "where-output-show-go" (optional, default "out")
- `-speed` "integer-ticks-per-second" (optional, default is 1 tick per second)