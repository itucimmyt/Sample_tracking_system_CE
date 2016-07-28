package com.cimmyt.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextTransfer implements ClipboardOwner {

	private String returnCharacter = "\n";
	public static void main(String...  aArguments ){
    TextTransfer textTransfer = new TextTransfer();

    String str = "0945,980,67\n";
    
    Pattern patron = Pattern.compile("[0-9]|([0-9,]*)");
    Matcher encaja = patron.matcher(str);
    if (encaja.matches()){
    	System.out.println("todo bien ");
    }else 
    	System.out.println("Error macher");
    
    
    //display what is currently on the clipboard
    System.out.println("Clipboard contains:" + textTransfer.getClipboardContents());

    System.out.println(textTransfer.getClipboardContents());
    //change the contents and then re-display
    textTransfer.setClipboardContents("blah, blah, blah");
    System.out.println("Clipboard contains:" + textTransfer.getClipboardContents());
  }

   /**
   * Empty implementation of the ClipboardOwner interface.
   */
   @Override public void lostOwnership(Clipboard aClipboard, Transferable aContents){
     //do nothing
   }

  /**
  * Place a String on the clipboard, and make this class the
  * owner of the Clipboard's contents.
  */
  public void setClipboardContents(String aString){
    StringSelection stringSelection = new StringSelection(aString);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, this);
  }

  /**
  * Get the String residing on the clipboard.
  *
  * @return any text found on the Clipboard; if none found, return an
  * empty String.
  */
  public String getClipboardContents() {
    String result = "";
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    //odd: the Object param of getContents is not currently used
    Transferable contents = clipboard.getContents(null);
    boolean hasTransferableText =
      (contents != null) &&
      contents.isDataFlavorSupported(DataFlavor.stringFlavor)
    ;
    if (hasTransferableText) {
      try {
        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
      }
      catch (UnsupportedFlavorException | IOException ex){
        System.out.println(ex);
        ex.printStackTrace();
      }
    }
    if (result.contains("GID"))
    	result = result.replace("GID", "");
    if (result.contains("gid"))
    	result = result.replace("gid", "");
    if (result.contains(" "))
    	result = result.replace(" ", "");
    if (result.contains("	"))
    	result = result.replace("	", "");
    if (result.contains(returnCharacter))
    	if (result.contains(","))
    		result = result.replace(returnCharacter, "");
    	else
    		result = result.replace(returnCharacter, ",");
    
    return result;
  }
} 