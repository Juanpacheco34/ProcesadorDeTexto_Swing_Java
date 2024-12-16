package procesadortexto.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.StyledEditorKit;

@SuppressWarnings({ "FieldMayBeFinal" })
public class Procesador extends JFrame {
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem menuItem;
  private JTextPane textPane;
  private JPopupMenu popupMenu;
  private JToolBar toolBar;
  private JButton button;
  private String[] FONTS = { "Arial", "Garamond", "Verdana", "French Script MT" };
  private String[] STYLES = { "Negrita", "Cursiva" };
  private String[] SIZES = { "12", "16", "20", "24", "28", "32", "36", "40" };
  private String[] POPUP_ITEM = { "Copiar", "Pegar" };

  public Procesador() {
    setLayout(new BorderLayout());
    setTitle("Procesador de Texto");
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int w = screen.width;
    int h = screen.height;
    setBounds(w / 4, h / 4, w / 2, h / 2);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.textPane = new JTextPane();
    this.textPane.setComponentPopupMenu(addPopUpMenu(POPUP_ITEM));

    add(new ProcessorBar(), BorderLayout.NORTH);

    add(new ProcessorBody(), BorderLayout.CENTER);

    setVisible(true);
  }

  private class ProcessorBar extends JPanel {

    public ProcessorBar() {

      menuBar = new JMenuBar();
      menuBar.add(addMenuFonts(FONTS, "Fuentes"));
      menuBar.add(addMenuStyles(STYLES, "Estilos"));
      menuBar.add(addMenuSizes(SIZES, "Tamaños"));

      add(menuBar);

    }

  }

  private class ProcessorBody extends JPanel {
    public ProcessorBody() {
      setLayout(new BorderLayout());
      add(addToolsBar(), BorderLayout.WEST);
      add(textPane, BorderLayout.CENTER);
    }

  }

  private JMenu addMenuFonts(String[] fonts, String nameMenu) {
    this.menu = new JMenu(nameMenu);

    Arrays.asList(fonts).forEach(name -> {
      menuItem = new JMenuItem(name);
      menuItem.addActionListener(new StyledEditorKit.FontFamilyAction(name, name));

      menu.add(menuItem);
    });

    return menu;
  }

  private JMenu addMenuStyles(String[] styles, String nameMenu) {
    this.menu = new JMenu(nameMenu);

    Arrays.asList(styles).forEach(name -> {
      menuItem = new JMenuItem(name);
      if ("negrita".equalsIgnoreCase(name))
        menuItem.addActionListener(new StyledEditorKit.BoldAction());
      if ("cursiva".equalsIgnoreCase(name))
        menuItem.addActionListener(new StyledEditorKit.ItalicAction());

      menu.add(menuItem);
    });

    return this.menu;
  }

  private JMenu addMenuSizes(String[] sizes, String nameMenu) {
    this.menu = new JMenu(nameMenu);

    Arrays.asList(sizes).forEach(name -> {
      menuItem = new JMenuItem(name);
      menuItem.addActionListener(new StyledEditorKit.FontSizeAction(name, Integer.parseInt(name)));

      menu.add(menuItem);
    });

    return this.menu;
  }

  private JPopupMenu addPopUpMenu(String[] popupItems) {
    this.popupMenu = new JPopupMenu();

    Arrays.asList(popupItems).forEach(name -> {
      if ("copiar".equalsIgnoreCase(name)) {
        this.menuItem = new JMenuItem(name);
        this.menuItem.addActionListener(new StyledEditorKit.CopyAction());
      }
      if ("pegar".equalsIgnoreCase(name)) {
        this.menuItem = new JMenuItem(name);
        this.menuItem.addActionListener(new StyledEditorKit.PasteAction());
      }

      popupMenu.add(this.menuItem);
    });

    return this.popupMenu;
  }

  private JToolBar addToolsBar() {
    this.toolBar = new JToolBar(1);
    this.toolBar
        .add(createToolButton("/assets/icons/left.png", new StyledEditorKit.AlignmentAction("A la Izquierda", 0),
            "Alinear a la izquierda"));
    this.toolBar.add(createToolButton("/assets/icons/center.png", new StyledEditorKit.AlignmentAction("Centrar", 1),
        "Centrar texto"));
    this.toolBar.add(createToolButton("/assets/icons/right.png", new StyledEditorKit.AlignmentAction("A la Derecha", 2),
        "Alinear a la derecha"));
    this.toolBar
        .add(createToolButton("/assets/icons/justified.png", new StyledEditorKit.AlignmentAction("Justificar Texto", 3),
            "Justificar Texto"));
    this.toolBar.add(createToolButton("/assets/icons/underline.png", new StyledEditorKit.UnderlineAction(),
        "Subrayar Texto"));

    this.toolBar.add(createToolButton("/assets/icons/red.png", new StyledEditorKit.ForegroundAction("Rojo", Color.RED),
        "Rojo"));
    this.toolBar
        .add(createToolButton("/assets/icons/black.png", new StyledEditorKit.ForegroundAction("Negro", Color.BLACK),
            "Negro"));

    this.toolBar.setEnabled(false);
    return toolBar;
  }

  private JButton createToolButton(String icon, Action action, String name) {
    try {
      this.button = new JButton(new ImageIcon(getClass().getResource(icon)));
    } catch (NullPointerException e) {
      System.err.println("Icono no encontrado: " + icon);
      this.button = new JButton(name);
    }
    this.button.addActionListener(action);
    this.button.setToolTipText(name);
    this.toolBar.addSeparator();
    return button;
  }

}
