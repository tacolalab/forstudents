import java.awt.Color;
import javax.swing.text.MutableAttributeSet;

public interface ConnectActions
{
	//file menu
	/**
	 * Close the currently opened presentation if any and create a new
	 * blank presentation.
	 * @see #CloseFile
	 * @see #ConfirmClose
	 */
	public void NewFile();

	/**
	 * Close the currently opened presentation if any and open a
	 * JFileChooser dialog to select a file for opening.
	 * @see #ConfirmClose
	 * @see ArangamFileFilter
	 * @see ArangamFileView
	 * @see ActionsImpl.AFileChooser
	 */
	public void OpenFile();

	/**
	 * Close the currently opened presentation if any and open
	 * a file indicated by path. Used to open a file from recent file history.
	 * @param path the file path
	 */
	public void OpenFile(String path);

	/**
	 * Closes the <code>Slideshow</code> without exiting the program. If the Slideshow
	 * contains any unsaved changes, the user will be asked if the wants
	 * to save the Slideshow before closing.
	 * @see #NewFile
	 * @see #ConfirmClose
	 */
	public void CloseFile();

	/**
	 * Saves the  <code>Slideshow</code> to disk with its current file name
	 * @see #SaveAsFile()
	 */
	public void SaveFile();

	/**
	 * Save the presentation which is not saved yet or Saves the <code>Slideshow</code>
	 * to disk with a different name.Open a <code>JFileChooser</code> Dialog to select
	 * the path for saving file.
	 * @see ActionsImpl.AFileChooser
	 */
	public void SaveAsFile();

	/**
	 * Sets margin, paper source, paper size, page orientation,
	 * and other layout options for printing of the <code>Slideshow</code>.
	 * @see Slideshow#pageSetup
	 */
	public void PageSetupFile();

	/**
	 * Prints the <code>Slideshow</code> or part of it. Also, lets the user select
	 * print options.
	 * @see Slideshow#printSlideshow()
	 */
	public void PrintFile();

	/**
	 * Closes the application after asking the user if the wants to save any
	 * changes to the <code>Slideshow</code>.
	 */
	public void ExitFile();

	//edit menu
	/**
	 * Removes the selected object from the <code>Slide</code> and places it
	 * on the Clipboard.
	 * @see #CutEdit
	 * @see #CopyEdit
	 */
	public void CutEdit();

	/**
	 * Copies the selected object to the <code>Clipboard</code>.
	 * @see Slide#copyComponent
	 */
	public void CopyEdit();

	/**
	 * Inserts the contents of the <code>Clipboard</code> to the current <code>Slide</code>.
	 * This action is available only in the user has cut or copied an object.
	 * @see Slide#pasteComponent
	 */
	public void PasteEdit();

	/**
	 * Deletes the selected object without putting it on the Clipboard.
	 * @see Slide#clearComponent
	 */
	public void DeleteEdit();

	/**
	 * Deletes the current <code>Slide</code>
	 * @see Slideshow#removeSlide()
	 */
	public void DeleteSlideEdit();

	/**
	 * Open up a <code>FindReplaceDialog</code>
	 * @see FindReplaceDialog
	 */
	public void FindReplaceDialog();

	/**
	 * Places the selected object in front of other overlapping objects
	 * @see Slide#bringComponentToFront
	 */
	public void BringToFrontEdit();

	/**
	 * Places the selected object behind other overlapping objects
	 * @see Slide#sendComponentToBack
	 */
	public void SendToBackEdit();


	//view menu
	/**
	 * Switches to "Slide view" mode, where one can work on one <code>Slide</code> at a time.
	 */
	public void SlideView();

	/**
	 * Displays miniature versions of all slides the <code>Slideshow</code>
	 * @see Slideshow#showSlideSorter
	 */
	public void SlideSorterView();

	/**
	 * Runs the Slideshow, beginning with the first Slide
	 * @see Show#run
	 */
	public void SlideShowView();

	/**
	 * Displays or hides the <code>ActionsToolbar</code>
	 * @see ActionsToolbar#setVisible
	 */
	public void ActionsToolbarView();

	/**
	 * Displays or hides the <code>ViewModeToolbar</code>
	 * @see ViewModeToolbar#setVisible
	 */
	public void ViewModeToolbarView();

	/**
	 * Displays or hides the <code>Ruler</code>
	 * @see #setRulerVisible
	 */
	public void RulerView();

	/**
	 * Set the visibility of rulers
	 * @param visible <code>true</code> if the ruler is visible, otherwise <code>false</code>
	 */
	public void setRulerVisible(boolean visible);

	/**
	 * Open the <code>GotoSlideDialog</code>, where the user can enter a number of
	 * the <code>Slide</code> to be displayed.
	 * @see GotoSlideDialog
	 */
	public void GotoSlideView();

	/**
	 * Sets the current Slide to a <code>Slide</code> indicated by slideNumberStr
	 * @param slideNumberStr the <code>Slide</code> to display
	 * @see #setCurrentSlideNum
	 */
	public void GotoSlide(String slideNumberStr);

	/**
	 * Displays the previous <code>Slide</code> in the "Slideshow view" and "Slide view", or
	 * the previous set of miniature slides, in "Sorter View".
	 * @return <code>true</code> if there is any previous <code>Slide</code>
	 * @see Slideshow#showSlideSorter
	 */
	public boolean PrevSlideView();

	/**
	 * Displays the next <code>Slide</code> in the "Slideshow view" and "Slide view", or the
	 * next set of miniature slides, in "Sorter view".
	 */
	public boolean NextSlideView();


	//insert menu
	/**
	 * Inserts a new <code>Slide</code> after the current Slide and shows it
	 * @see Slideshow#addSlide
	 */
	public void NewSlideInsert();

	/**
	 * Inserts an existing picture in the <code>Slide</code>.
	 * Open a <code>JFileChooser</code> Dialog to select a picture file(jpg or gif)
	 * @see Slide#addArangamImage
	 * @see ImageFileFilter
	 * @see ImageFileView
	 * @see ImagePreview
	 * @see ActionsImpl.AFileChooser
	 */
	public void PictureInsert();

	/**
	 * Draws a new text box
	 * @see Slide#addArangamText
	 */
	public void TextBoxInsert();

	/**
	 * Draws a new </code>AShape</code>.
	 * @param shapeType an integer indicating the Shape viz diamond, oval,
	 * rectangle, round rectangle or square
	 * @see Slide#addArangamShape
	 */
	public void ShapeInsert(int shapeType);


	//format menu
	/**
	 * Open a <code>FontDialog</code>, where one can change the font formats of the
	 * selected text and of new one to come.
	 * @see FontDialog
	 */
	public void FontFormat();

	/**
	 * Open a <code>ShapeFormatDialog</code>, where one can change the shape formats of the
	 * selected <code>AShape</code>
	 */
	public void ShapeFormat();

	/**
	 * Open a <code>BackgroundDialog</code>, where one can set the background color and
	 * texture for Slide
	 */
	public void BackgroundFormat();

	//tool menu
	/**
	 * Sets the locale to Tamil
	 * @see Arangam#setLocale
	 */
	public void tamilLang();

	/**
	 * Sets the locale to English
	 * @see Arangam#setLocale
	 */
	public void englishLang();

	/**
	 * Sets the locale to Tamil-English
	 * @see Arangam#setLocale
	 */
	public void tamEnglishLang();

	//help menu
	/**
	 * Displays the version number of this program and copyright notices
	 */
	public void AboutHelp();

	/**
	 * Open up the help file in help window
	 */
	public void ArangamHelp();

	/**
	 * Confirm closing of Slideshow when not saved
	 */
	public boolean ConfirmClose();

	//Actions toolbar and dialog boxes

	/**
	 * Shows the particular <code>Slide</code>
	 * @param slideNumberStr number of <code>Slide</code> to display
	 */
	public void setCurrentSlideNum(String str);

	/**
	 * Sets Slide Sorter horizontal and vertical dimensions
	 * @param dimension horizontal and vertical dimension
	 */
	public void setCurrentSorterDim(int dimension);

	/**
	 * Updates the text attributes for text component which has current focus.
	 * If no component has focus, the attributes are added and applied
	 * when new one is created.
	 * @param mattr the MutableAttributeSet for applying to text
	 * @param setParagraphAttributes whether the mattr is applied
	 * to paragraph or not
	 * @see javax.swing.JTextPane#setCharacterAttributes
	 */
	public void updateTextAttr(MutableAttributeSet attr,
		boolean setParagraphAttributes);

	/**
	 * Sets outline color for focused component.
	 * @param outlineColor the outline or border color
	 * @see AShape#setOutlineColor
	 */
	public void setOutlineColor(Color oulineColor);

	/**
	 * Sets background color for focused component.
	 * @param bgColor the background color
	 */
	public void setBgColor(Color c);

	/**
	 * End the <code>Slideshow</code> if it is in "show" mode
	 * @see Show#stop
	 */
	public void EndShow();

	/*** Checks the clipboard for any content. */
	public boolean isPasteAvailable();

	/**
	 * Returns the currently focused component
	 */
	public ConnectComponent getFocusedComponent();
}
