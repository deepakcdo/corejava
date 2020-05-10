package v2ch11.transferText;


import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 * This frame has a text area and buttons for copying and pasting text.
 */
public class TextTransferFrame extends JFrame
{
   private JTextArea textArea;
   private static final int TEXT_ROWS = 20;
   private static final int TEXT_COLUMNS = 60;

   public TextTransferFrame()
   {
      textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
      add(new JScrollPane(textArea), BorderLayout.CENTER);
      JPanel panel = new JPanel();

      var copyButton = new JButton("Copy");
      panel.add(copyButton);
      copyButton.addActionListener(event -> copy());

      var pasteButton = new JButton("Paste");
      panel.add(pasteButton);
      pasteButton.addActionListener(event -> paste());

      add(panel, BorderLayout.SOUTH);
      pack();
   }

   /**
    * Copies the selected text to the system clipboard.
    */
   private void copy()
   {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      String text = textArea.getSelectedText();
      if (text == null) text = textArea.getText();
      StringSelection selection = new StringSelection(text);
      clipboard.setContents(selection, null);
   }

   /**
    * Pastes the text from the system clipboard into the text area.
    */
   private void paste()
   {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      DataFlavor flavor = DataFlavor.stringFlavor;
      if (clipboard.isDataFlavorAvailable(flavor))
      {
         try
         {
            var text = (String) clipboard.getData(flavor);
            textArea.replaceSelection(text);
         }
         catch (UnsupportedFlavorException e | IOException ex)
         {
            JOptionPane.showMessageDialog(this, ex);
         }
      }
   }
}
