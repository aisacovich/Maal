/* 
 *
 * Swing 1.1 version (compatible with both JDK 1.1 and Java 2).
 */
package mac.gui.guiLauncher;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.net.*;
import java.lang.reflect.*;
import java.util.*;
import mac.steering.parser.*;
import mac.eventRecognizer.pedlParser.*;
import mac.runtimeChecker.medlParser.*;
import mac.eventRecognizer.interpreter.*;
import mac.runtimeChecker.interpreter.*;

// DJava
import classfile.ClassFile;
import classfile.view.JasminView;

public class MacGUI extends JApplet
                        implements ActionListener {

    public static JFrame frame;
    public static JMacButton bPgm,bPEDL,bMEDL,bSADL,
	bInstrumentor,bPEDLCompiler,bMEDLCompiler, bSADLCompiler,
	bInstrumentedPgm, bEventRecognizer,bChecker, bQuit;

    public static JLabel instrumentationOut, actionsOut,medlOut,pedlOut; 
    public static JLabel statusLine = new JLabel("Status: MacGUI is successfully launched!"); 

    // Existing components
    public static boolean 
	existSADL_OUT, existINSTRUMENTATION_OUT, existMEDL_OUT, existPEDL_OUT,
	existPgm, existInstrumentedPgm,
	readyChecker,readyEventRecognizer;

    public static final int MAX_SIZE_DISASSEMBLED = 10000;

    // Margin of Canvas
    final int leftMargin = 25;
    final int rightMargin = leftMargin;
    final int topMargin = 25;
    final int bottomMargin = topMargin;

    // Used for instrumentor, MEDL/PEDL Compiler
    final int widthCompilerIcon = 130;	
    final int heightCompilerIcon = 120;	

    // Used for MEDL/PEDL compiler output(object code), lists of monitored 
    // entity
    final int widthCompiledIcon = 60;	
    final int heightCompiledIcon = 60;	

    // Used for PEDL/MEDL description
    final int widthMonScrIcon = 100;
    final int heightMonScrIcon = 120;

    // Used for program and instrumented program 
    final int widthPgmIcon = 100;
    final int heightPgmIcon = 100;

    // Used for event recognizer and checker 
    final int widthInterpreterIcon = 120;
    final int heightInterpreterIcon = 120;

    // horizontal space for compiler icons
    final int horizontalSpaceCompilerIcon = 140;
    // vertical space for compiler icons
    final int verticalSpaceCompilerIcon = 30;   

    private boolean inAnApplet = true;
    URL codeBase; //used for applet version only

    public static String pedlScript, medlScript, sadlScript;
    protected String pgmClassfiles;
    protected String mainClassfileName, parameterNames;
    protected String tempDisassembledFilename;

    // Following address should be a form of "saul.cis.upenn.edu:8044" or
    // "saul:8045"
    protected static String injectorAddress, eventRecognizerAddress, runtimeCheckerAddress;
    // Following port address should be a form of "8044"
    protected static String portInjector, portEventRecognizer, portRuntimeChecker;

    // Local host name
    protected static String localHost = null;
    
    // Target program is only monitored or steered;
    protected static boolean monitorOnly = true;

    protected static ResourceBundle resource;
    


    public MacGUI(boolean inAnApplet) {
	 
	try{ localHost = InetAddress.getLocalHost().getHostName(); }
	catch(Exception ee) { System.err.println(ee);}
        this.inAnApplet = inAnApplet;
        if (inAnApplet) {
            getRootPane().putClientProperty("defeatSystemEventQueueCheck",
                                            Boolean.TRUE);
        }
    }

    public void init() {
        setContentPane(makeContentPane());
    }

    public Container makeContentPane() {
        final JPanel pane = new JPanel();

	// Large right arrow indicating information flow from ER to CHECKER
        //ImageIcon rightIcon = new ImageIcon(getClass().getResource("images/right.gif"));
        ImageIcon rightIcon = new ImageIcon(
		getClass().getResource("images/right.gif"));

	// Small right arrow indicating inserting SADLcode to instrumentor 
        ImageIcon rightIcon2 = new ImageIcon(getClass().getResource("images/right2.gif"));
        ImageIcon leftIcon= new ImageIcon(getClass().getResource("images/left.gif"));
        ImageIcon leftIcon2= new ImageIcon(getClass().getResource("images/ll.gif"));
        ImageIcon downIcon= new ImageIcon(getClass().getResource("images/down.gif"));

	final JLabel d1 = new JLabel(downIcon);
	final JLabel d2 = new JLabel(downIcon);
	final JLabel d3 = new JLabel(downIcon);
	final JLabel d4 = new JLabel(downIcon);
	final JLabel d5 = new JLabel(downIcon);
	final JLabel d6 = new JLabel(downIcon);
	final JLabel d7 = new JLabel(downIcon);
	final JLabel d8 = new JLabel(downIcon);
	final JLabel d9 = new JLabel(downIcon);

	final JLabel r1 = new JLabel(rightIcon);
	final JLabel r2 = new JLabel(rightIcon);
	final JLabel r3 = new JLabel(rightIcon2);
	final JLabel r4 = new JLabel(rightIcon2);

	final JLabel l1 = new JLabel(leftIcon);
	final JLabel l2 = new JLabel(leftIcon);
	final JLabel l3 = new JLabel(leftIcon2);

	// Checking existing components
	if((new File("instrumentation.out")).exists()) existINSTRUMENTATION_OUT= true;
	if((new File("pedl.out")).exists())  existPEDL_OUT= true; 
	if((new File("medl.out")).exists())  existMEDL_OUT= true;
	if((new File("actions.out")).exists()) existSADL_OUT= true;

	pedlOut = new JLabel("pedl.out",
		new ImageIcon(getClass().getResource("images/compiled.gif")),JLabel.CENTER);
	pedlOut.setVerticalTextPosition(JLabel.BOTTOM);
	pedlOut.setHorizontalTextPosition(JLabel.CENTER);
	pedlOut.setVisible(existPEDL_OUT);
	d4.setVisible(existPEDL_OUT);
	d5.setVisible(true);


	medlOut = new JLabel("medl.out",
		new ImageIcon(getClass().getResource("images/compiled.gif")),JLabel.CENTER);
	medlOut.setVerticalTextPosition(JLabel.BOTTOM);
	medlOut.setHorizontalTextPosition(JLabel.CENTER);
	medlOut.setVisible(existMEDL_OUT);
	d7.setVisible(existMEDL_OUT);
	d8.setVisible(true);

	actionsOut = new JLabel("actions.out",
		new ImageIcon(getClass().getResource("images/compiled.gif")),JLabel.CENTER);
	actionsOut.setVerticalTextPosition(JLabel.BOTTOM);
	actionsOut.setHorizontalTextPosition(JLabel.CENTER);
	actionsOut.setVisible(existSADL_OUT);
	r3.setVisible(existSADL_OUT);
	r4.setVisible(existSADL_OUT);

	instrumentationOut = new JLabel("instrumentation.out",
		new ImageIcon(getClass().getResource("images/compiled.gif")), SwingConstants.CENTER);
	instrumentationOut.setVerticalTextPosition(JLabel.BOTTOM);
	instrumentationOut.setHorizontalTextPosition(JLabel.CENTER);
	instrumentationOut.setVisible(existINSTRUMENTATION_OUT);
	l1.setVisible(true);
	l2.setVisible(existINSTRUMENTATION_OUT);





	bPgm = new JMacButton("Program",
		new ImageIcon(getClass().getResource("images/pgm.gif")),
		new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

			final JFrame frame = new JFrame("Target Classfiles");
			frame.setSize(350,90);

			final JTextField textField = new JTextField(30);
			final JLabel label = new JLabel(
			    "Classfiles (separated with ;)");
			final JButton ok = new JButton("OK");
			ok.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e1) {
				    pgmClassfiles = textField.getText();
				    existPgm = true;
				    frame.dispose();
				}
			    } );
			final JButton cancel = new JButton("CANCEL");
			cancel.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e1) {
				    frame.dispose();
				}
			    } );
			final JPanel buttons = new JPanel();
			buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
			buttons.add(ok);
			buttons.add(Box.createRigidArea(new Dimension (10,0)));
			buttons.add(cancel);
			textField.addActionListener(
			    new ActionListener() {
				public void actionPerformed(ActionEvent e1) {
				    pgmClassfiles = textField.getText();  
				    existPgm = true;
				    frame.dispose(); 
				}
			    });

			JPanel panel = new JPanel();
			panel.add(label);
			panel.add(textField);
			panel.add(buttons);
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			frame.getContentPane().add( panel, BorderLayout.CENTER);
			frame.setVisible(true);
		    }
		    });
	bPgm.setEnabled(true);
	bPgm.setToolTipText("CHOOSE A TARGET PROGRAM TO BE INSTRUMENTED"); 

	bPEDL= new JMacButton("PEDL Script",
		new ImageIcon(getClass().getResource("images/monscr.gif")),
		new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    /*
			final JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new ExampleFileFilter("pedl") );
			fc.setDialogTitle("PEDL script");
			int returnVal = fc.showOpenDialog(MacGUI.this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
			} else {
			}
			*/
			try{
			mac.gui.notepad.Notepad.main(new 
			    String[] {null,"PEDLscript"} );
			} catch(Exception e2) {System.err.println(e2);}
		    }});
	bPEDL.setEnabled(true);
	bPEDL.setToolTipText("CHOOSE PEDL SCRIPT FROM EDITOR MENU");

	bMEDL= new JMacButton("MEDL Script",
		new ImageIcon(getClass().getResource("images/monscr.gif")),
		new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			mac.gui.notepad.Notepad.main(new 
			    String[] {null,"MEDLscript"});
		    }});
	bMEDL.setEnabled(true);
	bMEDL.setToolTipText("CHOOSE MEDL SCRIPT FROM FILE MENU");



	bInstrumentor = new JMacButton("Instrumentor",
	    new ImageIcon(getClass().getResource("images/compiler.gif")),
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {

		    final JFrame frame = new JFrame("Instrumentor Parameters");
		    frame.setSize(350,100);

		    final JTextField classfiles = new JTextField(30);
		    final JTextField textFieldEV= new JTextField(30);
		    final JTextField textFieldInjector = new JTextField(30);

		    final JLabel labelClassfiles = new JLabel(
			"Classfiles: "); 
		    labelClassfiles.setLabelFor(classfiles);
		    final JLabel labelEV=new JLabel("Address of Event Recognizer");
		    final JLabel labelParameters=new JLabel("Address of Injector");

		    final JPanel labelTextField = new JPanel();
		    labelTextField.setLayout(new GridLayout(3,2));
		    labelTextField.add(labelClassfiles);
		    labelTextField.add(classfiles);
		    labelTextField.add(labelEV);
		    labelTextField.add(textFieldEV);
		    labelTextField.add(labelParameters);
		    labelTextField.add(textFieldInjector);


		    final JButton ok = new JButton("OK");
		    ok.addActionListener( new ActionListener() {
			    public void actionPerformed(ActionEvent e1) {
				String addr = null;
				boolean error = false;
				int loc = 0;
				pgmClassfiles = classfiles.getText();


				/* Event Recognizer Address */
				addr = textFieldEV.getText();
				try{	// 8043
				    Integer.parseInt(addr);
				    eventRecognizerAddress=localHost+":"+addr;
				    portEventRecognizer = addr;
				}catch(Exception e_){//saul.cis.upenn.edu:8044
				    try{
					loc = addr.lastIndexOf(":");
					eventRecognizerAddress=addr;
					portEventRecognizer =
					    addr.substring(loc+1);
				    } catch(Exception e2) {
					error = true;
				    }

				}
				/* Injector Address */
				addr = textFieldInjector.getText();
				try{	// 8043
				    Integer.parseInt(addr);
				    injectorAddress=localHost+":"+addr;
				    portInjector = addr;
				}catch(Exception e__){//saul.cis.upenn.edu:8044
				    try{
					loc = addr.lastIndexOf(":");
					injectorAddress=addr;
					portInjector =
					    addr.substring(loc+1);
				    } catch(Exception e4) {
					error = true;
				    }

				}

				existPgm = true;
				try{
				if(existSADL_OUT && !injectorAddress.trim().equals("")){
				    String[] args = new String[9];
				    args[0] = "-monitor"; args[1] = "instrumentation.out";
				    args[2] = "-ports"; args[3] = eventRecognizerAddress;
				    args[4] = portInjector;

				    args[5] = "-steer"; args[6] = "actions.out";
				    args[7] = "-classfiles"; args[8] = pgmClassfiles;
				    mac.steering.SteeringInstrumentor.main(args);
				    error = mac.steering.SteeringInstrumentor.error;
				    monitorOnly = false;
				} else {
				    monitorOnly = true;
				    String[] args = new String[3];
				    args[0] = "instrumentation.out";
				    args[1] = pgmClassfiles;
				    args[2] = eventRecognizerAddress;

				    mac.filter.instrumentor.Instrumentor.main(args);
				    error=mac.filter.instrumentor.Instrumentor.error; 
				}
				} catch(Exception ex) {System.err.println(ex);error=true;}
				if(!error) {
				    String be = "is";
				    be = (pgmClassfiles.indexOf(";") >= 0 )?
					" are " : " is ";
				    showStatusLine(pgmClassfiles + be +
					"successfully instrumented" +
					(monitorOnly?"!":" with steering!") 
					);
				    frame.dispose();
				} else {
				    showStatusLine( "Instrumentation " +
					(monitorOnly ? "":"with steering ")+
					"failed !"); 
				}
			    }
			} );

		    final JButton cancel = new JButton("CANCEL");
		    cancel.addActionListener( new ActionListener() {
			    public void actionPerformed(ActionEvent e1) {
				frame.dispose();
			    }
			} );
		    final JPanel buttons = new JPanel();
		    buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		    buttons.add(ok);
		    buttons.add(Box.createRigidArea(new Dimension (10,0)));
		    buttons.add(cancel);

		    classfiles.setText(pgmClassfiles);
		    textFieldEV.setText(eventRecognizerAddress);
		    textFieldInjector.setText( injectorAddress);

		    JPanel panel = new JPanel();
		    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		    panel.add(labelTextField);
		    panel.add(buttons);

		    frame.getContentPane().add( panel, BorderLayout.CENTER);
		    frame.setVisible(true);
		}
	    } );
	bInstrumentor.setToolTipText("INSTRUMENT CLASSFILES");
	bInstrumentor.setEnabled(existINSTRUMENTATION_OUT);



	bPEDLCompiler = new JMacButton("PEDL Compiler",
		new ImageIcon(getClass().getResource("images/compiler.gif")),
		new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			String[] args = new String[1]; 

			final JFileChooser fc = new JFileChooser(
			System.getProperty("user.dir"));
			fc.setFileFilter(new ExampleFileFilter("pedl") );
			fc.setDialogTitle("PEDL Script");

			int returnVal = fc.showOpenDialog(MacGUI.this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
			    pedlScript= fc.getSelectedFile().getAbsolutePath();
			} 
			args[0] = pedlScript;




			// when File.getPath() returns just file name 
			// when File is created by "filename.ext", not
			// including path
			try{
			    if(PedlParser.pedlParser == null ) 
				PedlParser.pedlParser.main(args);
			    else {
				PedlParser.pedlParser.ReInit(
				    PedlParser.fis == null ?
				    System.in:
				    (PedlParser.fis = new FileInputStream(
					pedlScript)));
				PedlParser.init();
				PedlParser.parseTree = 
				    PedlParser.pedlParser.MonitoringScript();
				if (!PedlParser.error)
				    System.out.println(
				    "PEDL Parser Version 0.1:  " + 
				    "PEDL script parsed successfully.");
				else {
				    System.out.println(
				    "PEDL Parser Version 0.1:  " + 
				    " Encountered errors during parse.");
				}
				PedlParser.parseTree.computeHeight();
				PedlParser.outputs();
			    }
			} catch(Exception e4) {
			    PedlParser.error = true;
			    System.err.println(e4);
			    System.err.println(
			    "PEDL Parser Version 0.1:  " + 
			    " Encountered errors during parse.");
			}

			if(!PedlParser.error){
			    existINSTRUMENTATION_OUT = true;
			    instrumentationOut.setVisible(true);
			    l1.setVisible(true);
			    l2.setVisible(true);
			    existPEDL_OUT = true;
			    d4.setVisible(true);
			    d5.setVisible(true);
			    pedlOut.setVisible(true);
			    showStatusLine(pedlScript + " is successfully compiled!");
			    bEventRecognizer.setEnabled(true);
			    bInstrumentor.setEnabled(true);
			} else 
			    showStatusLine("Compilation of " + pedlScript 
				    + " failed!");
		    }
		});
	bPEDLCompiler.setToolTipText("COMPILE PEDL SCRIPT INTO \"pedl.out\"");

	bMEDLCompiler = new JMacButton("MEDL Compiler",
		new ImageIcon(getClass().getResource("images/compiler.gif")),
		new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			String[] args = new String[1]; 

			final JFileChooser fc = new JFileChooser(
			System.getProperty("user.dir"));
			fc.setFileFilter(new ExampleFileFilter("medl") );
			fc.setDialogTitle("MEDL Script");

			int returnVal = fc.showOpenDialog(MacGUI.this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
			    medlScript= fc.getSelectedFile().getAbsolutePath();
			} 
			args[0] = medlScript;

			try{
			    if(MedlParser.medlParser == null ) 
				MedlParser.main(args);
			    else {
				MedlParser.medlParser.ReInit(
				    MedlParser.fis == null ?
				    System.in:
				    new FileInputStream(medlScript)
				);
				MedlParser.init();
				MedlParser.parseTree = 
				    MedlParser.medlParser.RequirementScript();
				MedlParser.typeCheck();
				if (!MedlParser.error) {
				    System.out.println(
					"MEDL Parser Version 0.1: " + 
					"MEDL script parsed successfully.");

				    MedlParser.outputs();
				}
			    }
			} catch(Exception e12) {
			    System.err.println(e12);
			    MedlParser.error = true;
			    System.out.println(
				"MEDL Parser Version 0.1:" + 
				"  Encountered errors during parse.");
			}

			if(!mac.runtimeChecker.medlParser.MedlParser.error){
			    showStatusLine(medlScript + 
				" is successfully compiled!");
			    existMEDL_OUT = true;
			    bChecker.setEnabled(true);
			    d7.setVisible(true);
			    d8.setVisible(true);
			    medlOut.setVisible(true);
			} else {
			    showStatusLine("Compilation of " + medlScript + 
				" failed!");
		    }}});
	bMEDLCompiler.setToolTipText("COMPILE MEDL SCRIPT INTO \"medl.out\"");

	bInstrumentedPgm = new JMacButton("Program'",
	    new ImageIcon(getClass().getResource("images/pgm.gif")),
	    new ActionListener() {
		String text=null;
		public void actionPerformed(ActionEvent e) {
		    final JFrame frame = new JFrame(
			"Executing Instrumented Program");
		    frame.setSize(420,100);

		    final JTextField mainClass = new JTextField(30);
		    final JTextField parameters = new JTextField(30);

		    final JLabel labelMainClass=new JLabel("Main classfile: ");
		    final JLabel labelParameters=new JLabel("Parameters: ");

		    final JPanel labelTextField = new JPanel();
		    labelTextField.setLayout(new GridLayout(2,2));
		    labelTextField.add(labelMainClass);
		    labelTextField.add(mainClass);
		    labelTextField.add(labelParameters);
		    labelTextField.add(parameters);

		    mainClass.setText(mainClassfileName);
		    parameters.setText(parameterNames);

		    final JButton ok = new JButton("OK");
		    ok.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e1) {
			mainClassfileName = mainClass.getText();
			parameterNames = parameters.getText();
			int loc = 0;
			if(!readyEventRecognizer) {
			    showStatusLine( "Execute Event Recognizer before Intrumented Program!");
			    return;
			}
			try {
			    StringTokenizer stArgs = new StringTokenizer(parameterNames);
			    final String[] args = new String[stArgs.countTokens()];
			    for(int i=0;stArgs.hasMoreTokens(); i++) {
				args[i]= stArgs.nextToken();
				System.err.println(args[i]);
			    }
			    // Reflection Method for getting main()
			    Class c = Class.forName(mainClassfileName); 
			    Class[] parameterTypes = 
				{Class.forName("[Ljava.lang.String;")};
			    final Method m =c.getMethod("main",parameterTypes);
			    new Thread() {
				public void run() {
				    try{
				    m.invoke(null , new Object[] {args});
				    System.err.println(m);
				    }catch(InvocationTargetException e5) { 
					System.err.println(e5);
					e5.getTargetException().printStackTrace();
					showStatusLine( mainClassfileName + " failed to launch!");}
				    catch(Exception e6 ) {
					System.err.println(e6);
				    }
				}
			    }.start();
			    frame.dispose();
			    //showStatusLine(mainClassfileName + " is running!");

			    } catch(Exception e4) { 
				System.err.println(e4); 
			    showStatusLine(mainClassfileName + " failed to start!"); 
			    }
		    }}); 
		    final JButton cancel = new JButton("CANCEL");
		    cancel.addActionListener( new ActionListener() {
			    public void actionPerformed(ActionEvent e1) {
				frame.dispose();
			    }
			} );
		    final JPanel buttons = new JPanel();
		    buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		    buttons.add(ok);
		    buttons.add(Box.createRigidArea(new Dimension (10,0)));
		    buttons.add(cancel);

		    mainClass.setText(mainClassfileName);
		    parameters.setText(parameterNames);

		    JPanel panel = new JPanel();
		    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		    panel.add(labelTextField);
		    panel.add(buttons);

		    frame.getContentPane().add( panel, BorderLayout.CENTER);
		    frame.setVisible(true);
		} 
	    
	    });
	bInstrumentedPgm.setEnabled(true);	

	bEventRecognizer = new JMacButton("Event Recognizer",
	    new ImageIcon(getClass().getResource("images/interpreter.gif")),
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    final JFrame frame = new JFrame(
			"Event Recognizer Parameters");
		    frame.setSize(420,100);

		    final JTextField listenPort = new JTextField(30);
		    final JTextField checker = new JTextField(30);

		    final JLabel labelMainClass=new JLabel("Address of Event Recognizer");
		    final JLabel labelChecker=new JLabel("Address of Checker");

		    final JPanel labelTextField = new JPanel();
		    labelTextField.setLayout(new GridLayout(2,2));
		    labelTextField.add(labelMainClass);
		    labelTextField.add(listenPort);
		    labelTextField.add(labelChecker);
		    labelTextField.add(checker);

		    listenPort.setText(eventRecognizerAddress);
		    checker.setText(runtimeCheckerAddress);

		    final JButton ok = new JButton("OK");
		    ok.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e1) {
			boolean error = false;
			String addr = null;
			int loc = 0;
			try {
			    /* Event Recognizer Address */
			    addr = listenPort.getText();
			    try{	// 8043
				Integer.parseInt(addr);
				eventRecognizerAddress=localHost+":"+addr;
				portEventRecognizer = addr;
			    }catch(Exception e_){//saul.cis.upenn.edu:8044
				try{
				    loc = addr.lastIndexOf(":");
				    eventRecognizerAddress=addr;
				    portEventRecognizer =
					addr.substring(loc+1);
				} catch(Exception e3) {
				    error = true;
				}

			    }
			    /* Checker Address */
			    addr = checker.getText();
			    try{	// 8043
				Integer.parseInt(addr);
				runtimeCheckerAddress=localHost+":"+addr;
				portRuntimeChecker = addr;
			    }catch(Exception _e){//saul.cis.upenn.edu:8044
				try{
				    loc = addr.lastIndexOf(":");
				    runtimeCheckerAddress=addr;
				    portRuntimeChecker =
					addr.substring(loc+1);
				} catch(Exception e9) {
				    error = true;
				}

			    }
			    if(!error) {
				if(readyChecker) {
				    new Thread() {
					public void run() {
					EventRecognizerMain.main(
					new String[] { portEventRecognizer, 
					runtimeCheckerAddress, 
					"pedl.out","instrumentation.out"}); 
					}
				    }.start();
				    frame.dispose();
				    showStatusLine("Event Recognizer is running at " + eventRecognizerAddress);
				    readyEventRecognizer = true;
				} else
				    showStatusLine("Execute Run-time Checker before Event Recognizer !");
				
			    } else 
				showStatusLine("Event Recognizer failed to start!"); 
			} catch(Exception e2) { 
			    showStatusLine(
				    "Event Recognizer failed to start: " +
				    e2.toString());
			} 
		    }
			} );
		    final JButton cancel = new JButton("CANCEL");
		    cancel.addActionListener( new ActionListener() {
			    public void actionPerformed(ActionEvent e1) {
				frame.dispose();
			    }
			} );
		    final JPanel buttons = new JPanel();
		    buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		    buttons.add(ok);
		    buttons.add(Box.createRigidArea(new Dimension (10,0)));
		    buttons.add(cancel);

		    listenPort.setText(eventRecognizerAddress);
		    checker.setText( runtimeCheckerAddress);

		    JPanel panel = new JPanel();
		    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		    panel.add(labelTextField);
		    panel.add(buttons);

		    frame.getContentPane().add( panel, BorderLayout.CENTER);
		    frame.setVisible(true);
		}
		});
	bEventRecognizer.setEnabled(existPEDL_OUT);
		
	bChecker = new JMacButton("Runtime Checker",
	    new ImageIcon(getClass().getResource("images/interpreter.gif")),
	    new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		final JFrame frame = new JFrame(
		    "Event Recognizer Parameters");
		frame.setSize(420,100);

		final JTextField listenPort = new JTextField(30);
		final JTextField injector = new JTextField(30);

		final JLabel labelListenPort=new JLabel("Address of Checker");
		final JLabel labelInjector=new JLabel("Address of Injector");

		final JPanel labelTextField = new JPanel();
		labelTextField.setLayout(new GridLayout(2,2));
		labelTextField.add(labelListenPort);
		labelTextField.add(listenPort);
		labelTextField.add(labelInjector);
		labelTextField.add(injector);

		listenPort.setText(runtimeCheckerAddress);
		injector.setText(injectorAddress);

		final JButton ok = new JButton("OK");
		ok.addActionListener( new ActionListener() {
		public void actionPerformed(ActionEvent e1) {
		    boolean error = false;
		    injectorAddress = injector.getText();
		    int loc = 0;
		    String addr = null;
		    try {

			/* Checker Address */
			addr = listenPort.getText();
			try{	// 8043
			    Integer.parseInt(addr);
			    runtimeCheckerAddress=localHost+":"+addr;
			    portRuntimeChecker = addr;
			}catch(Exception e_){//saul.cis.upenn.edu:8044
			    try{
				loc = addr.lastIndexOf(":");
				runtimeCheckerAddress=addr;
				portRuntimeChecker =
				    addr.substring(loc+1);
			    } catch(Exception e9) {
				error = true;
			    }

			}

			/* Injector Address */
			addr = injector.getText();
			try{	// 8043
			    Integer.parseInt(addr);
			    injectorAddress=localHost+":"+addr;
			    portInjector = addr;
			}catch(Exception _e){//saul.cis.upenn.edu:8044
			    try{
				loc = addr.lastIndexOf(":");
				injectorAddress=addr;
				portInjector =
				    addr.substring(loc+1);
			    } catch(Exception e4) {
				error = true;
			    }

			}

			if(!error) {
			    if(!monitorOnly){
				final String[] args = new String[4];
				args[0] = "-s";
				args[1] = portRuntimeChecker;
				args[2] = injectorAddress; 
				args[3] = "medl.out";
				new Thread() {
				    public void run() {
				    CheckerMain.main(args);
				    showStatusLine("Runtime Checker " + 
					    (monitorOnly?"":"with steering" ) +
					   " is running at "+runtimeCheckerAddress );
				    }
				}.start();
				frame.dispose();
				readyChecker = true;
			    } else {
				final String args[] = new String[2];
				args[0] = portRuntimeChecker;
				args[1] = "medl.out";
				new Thread() {
				    public void run() {
				    CheckerMain.main(args);
				    }
				}.start();
				showStatusLine("Runtime Checker " + 
					(monitorOnly?"":"with steering" ) +
				       " is running at "+runtimeCheckerAddress );
				frame.dispose();
				readyChecker = true;
			    }
			} else showStatusLine("Runtime Checker failed to start!");
		    } catch(Exception e4) { 
			showStatusLine("Runtime Checker failed to start: " +
				e4.toString());

		    }
		}}); 
		final JButton cancel = new JButton("CANCEL");
		cancel.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
			    frame.dispose();
			}
		    } );
		final JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(ok);
		buttons.add(Box.createRigidArea(new Dimension (10,0)));
		buttons.add(cancel);


		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(labelTextField);
		panel.add(buttons);

		frame.getContentPane().add( panel, BorderLayout.CENTER);
		frame.setVisible(true);
	    } });
	bChecker.setEnabled(existMEDL_OUT);


	bSADL = new JMacButton("SADL Script",
		new ImageIcon(getClass().getResource("images/monscr.gif")),
		new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			mac.gui.notepad.Notepad.main(new 
			    String[] {null,"SADLscript"} );
		    }});

	bSADL.setEnabled(true);
	bSADL.setToolTipText("CHOOSE SADL SCRIPT FROM EDITOR MENU");

	// CHANGE IT LATER CONSULTING OLEG
	bSADLCompiler = new JMacButton("SADL Compiler",
		new ImageIcon(getClass().getResource("images/compiler.gif")),
		new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			String[] args = new String[1] ;
			final JFileChooser fc = new JFileChooser(
			System.getProperty("user.dir"));
			fc.setFileFilter(new ExampleFileFilter("sadl") );
			fc.setDialogTitle("SADL Script");

			int returnVal = fc.showOpenDialog(MacGUI.this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
			    sadlScript= fc.getSelectedFile().getAbsolutePath();
			} 
			args[0] = sadlScript ;

			try{
			    if( SadlParser.parser == null) 
				SadlParser.main(args);
			    else {
				SadlParser.parser.ReInit(
				    SadlParser.fis == null?
				    System.in:
				    (SadlParser.fis= 
				    new FileInputStream(args[0])));
			    
				SadlParser.init();
				SadlParser.parser.SteeringScript();
				SadlParser.outputs();
				SadlParser.error = true;
			    }
			}catch(Exception e5) {
			    System.err.println(e5);
			    SadlParser.error = true;
			}


			if(!SadlParser.error) {
			    showStatusLine(sadlScript + " is successfully compiled!");
			    existSADL_OUT= true;
			    r3.setVisible(true);
			    r4.setVisible(true);
			    actionsOut.setVisible(true);
			} else 
			    showStatusLine("Compilation of "+ sadlScript + 
				    "  failed!");
		    }});
	bSADLCompiler.setToolTipText("COMPILE SADL SCRIPT INTO \"action.out\"");


	// Just check whether a button is enabled well or not
	bPgm.setActionCommand(	 	  "Pgm"        ); 
	bPEDL.setActionCommand(           "PEDL"        );   
	bMEDL.setActionCommand(           "MEDL"          ); 
	bInstrumentor.setActionCommand(   "Instrumentor"   );
	bPEDLCompiler.setActionCommand(   "PEDLCompiler"  ); 
	bMEDLCompiler.setActionCommand(   "MEDLCompiler" );   
	bInstrumentedPgm.setActionCommand("InstrumentedPgm"); 
	bEventRecognizer.setActionCommand("EventRecognizer"); 
	bChecker.setActionCommand(        "Checker"        ); 


	
	
	
	
	
	
	
	
	
	
	
	



        //Add Components to a JPanel, using the default FlowLayout. 
	pane.setLayout(null);
	pane.add(bPgm);
	pane.add(bPEDL);
	pane.add(bMEDL);
	pane.add(bInstrumentor);
	pane.add(bPEDLCompiler);
	pane.add(bMEDLCompiler);
	pane.add(medlOut);
	pane.add(bEventRecognizer);
	pane.add(bChecker);
	pane.add(bInstrumentedPgm);
	pane.add(instrumentationOut);
	pane.add(pedlOut);
	pane.add(bSADL);
	pane.add(bSADLCompiler);
	pane.add(actionsOut);


        pane.setBackground(new Color(255,255,204));
        //pane.setBorder(BorderFactory.createMatteBorder(1,1,2,2,Color.black));

	Insets insets = pane.getInsets();

	final int x1= insets.left + leftMargin + 
		widthCompilerIcon + horizontalSpaceCompilerIcon; 
	final int x2 = x1 + widthCompilerIcon + horizontalSpaceCompilerIcon; 
	final int x3 = x2 + widthCompilerIcon + horizontalSpaceCompilerIcon;

	final int y1 = topMargin+insets.top+heightMonScrIcon+
		verticalSpaceCompilerIcon;
	final int y2= y1 + verticalSpaceCompilerIcon + heightCompilerIcon ;
	final int y3 = y2+ heightCompiledIcon + verticalSpaceCompilerIcon;

	final int z1 = leftMargin + 40;
	final int z2 = x1 + 40;
	final int z3 = x2 + 40;
	final int z4 = x3 + 40;

	// PGM, PEDL, MEDL
	/*bPgm.setBounds(leftMargin + insets.left + 10, topMargin+insets.top+10,
		widthPgmIcon,heightPgmIcon); */
	bPgm.setBounds(x1 +10, topMargin+insets.top+10,
		widthPgmIcon,heightPgmIcon);
	bPEDL.setBounds(x2 + 10, topMargin + insets.top,
		widthMonScrIcon,heightMonScrIcon);
	bMEDL.setBounds(x3 + 10, topMargin + insets.top,
		widthMonScrIcon,heightMonScrIcon);

	// Instrumentor, PEDL compiler, MEDL compiler
	/*bInstrumentor.setBounds( leftMargin + insets.left, y1,
		widthCompilerIcon,heightCompilerIcon);*/
	bInstrumentor.setBounds( x1, y1,
		widthCompilerIcon,heightCompilerIcon);
	bPEDLCompiler.setBounds( x2, y1, 
		widthCompilerIcon,heightCompilerIcon);
	bMEDLCompiler.setBounds( x3,y1,
		widthCompilerIcon,heightCompilerIcon);

	// PEDL compiled code, MEDL compiled code, Monitored List
	pedlOut.setBounds( x2 +30, y2-5,
		widthCompiledIcon, heightCompiledIcon);
	medlOut.setBounds( x3 + 30, y2-5,
		widthCompiledIcon, heightCompiledIcon);
	instrumentationOut.setBounds(x1-30+insets.right+leftMargin +widthCompilerIcon+40,
		y1+30, widthCompiledIcon, heightCompiledIcon);

	// Instrumented Program, Event Recongnizer, Run-time Checker
	/*bInstrumentedPgm.setBounds(leftMargin + insets.left + 10, y3 + 10,
		widthPgmIcon,heightPgmIcon); */
	bInstrumentedPgm.setBounds(x1, y3 + 10,
		widthPgmIcon,heightPgmIcon); 
	bEventRecognizer.setBounds( x2, y3,
		widthCompilerIcon,heightCompilerIcon);
	bChecker.setBounds(x3,y3,
		widthCompilerIcon,heightCompilerIcon);

	// SADL buttons
	bSADL.setBounds( leftMargin + insets.left + 10, topMargin+insets.top+10,
		widthPgmIcon,heightPgmIcon); 
	bSADLCompiler.setBounds( leftMargin + insets.left, y1,
		widthCompilerIcon,heightCompilerIcon);
	actionsOut.setBounds( insets.right+leftMargin +widthCompilerIcon+40,
		y1+30, widthCompiledIcon, heightCompiledIcon);

	// Down Arrow Buttons
	d1.setBounds(z2,y1-40,40,40);
	d2.setBounds(z2,y2+20,40,40);
	d3.setBounds(z3,y1-40,40,40);
	d4.setBounds(z3,y2-40,40,40);
	d5.setBounds(z3,y3-40,40,40);
	d6.setBounds(z4,y1-40,40,40);
	d7.setBounds(z4,y2-40,40,40);
	d8.setBounds(z4,y3-40,40,40);
	d9.setBounds(z1,y1-40,40,40);

	l1.setBounds(x1 -30 + leftMargin + widthCompilerIcon,y1 + 40 ,40,40);
	l2.setBounds(x1 -30 + leftMargin + widthCompilerIcon+ 
		widthCompiledIcon + 45 , y1+40,40,40);
	l3.setBounds(x1 + widthCompilerIcon/2, 520,600,70 );
	//l3.setBounds(10,10,560,70 );
	//l3.setVisible(true);


	r1.setBounds(x2-120,y3+40,100,60);
	r2.setBounds(x3-120,y3+40,100,60);

	r3.setBounds(leftMargin + widthCompilerIcon,y1 + 40 ,40,40);
	r4.setBounds(leftMargin + widthCompilerIcon+ widthCompiledIcon + 45 ,
				y1+40,40,40);



	pane.add(d1);
	pane.add(d2);
	pane.add(d3);
	pane.add(d4);
	pane.add(d5);
	pane.add(d6);
	pane.add(d7);
	pane.add(d8);
	pane.add(d9);
	pane.add(l1);
	pane.add(l2);
	pane.add(l3);
	pane.add(r1);
	pane.add(r2);
	pane.add(r3);
	pane.add(r4);

	JButton bQuit = new JButton("QUIT");
	bQuit.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    frame.dispose();
		    System.out.println("MacGUI is terminated normally!");
		    System.exit(-1);
		}
	    }
	    );
	pane.add(bQuit);
	bQuit.setBounds(10,600,100,30);
	statusLine.setBounds(130,600,800,30);

	pane.add(bQuit);
	pane.add(statusLine);

        return pane;
    }
    public static void showStatusLine(String msg) {
	statusLine.setText("Status: " + msg);
    }


    public void actionPerformed(ActionEvent e) {
	Frame f = null;
        if (e.getActionCommand().equals("bPgm")) {
        } else if (e.getActionCommand().equals("bPEDLCompiler" )){
	}

	System.out.println(e.getActionCommand());
	
    }
    
    /* One day, JApplet will make this method obsolete. */
    protected URL getURL(String filename) {
        URL url = null;
        if (codeBase == null) {
            codeBase = getCodeBase();
        }

        try {
            url = new URL(codeBase, filename);
        } catch (java.net.MalformedURLException e) {
            System.out.println("Couldn't create image: badly specified URL");
            return null;
        }

        return url;
    }
    
    /** First argument contains the directory of image files ending with 
     * '/' or '\' depending on the platform.
     * UNIX : '/'
     * WINDOWS: '\' 
     **/
    public static void main(String[] args) {
	System.out.println("---------------------------------------------");
	System.out.println("MaC GUI: Graphical User Interface for MaCware");
	System.out.println("Version 0.9 (April 14, 2000)");
	System.out.println("For more info:");
	System.out.println("    e-mail:macware@saul.cis.upenn.edu");
	System.out.println("    http://www.cis.upenn.edu/~rtg/mac");
	System.out.println("---------------------------------------------");
        frame = new JFrame("Monitoring and Checking GUI");

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
		return;
            }
        });

        MacGUI applet = new MacGUI(false);
        frame.setContentPane(applet.makeContentPane());
        frame.pack();
	frame.setSize(1000,630);
        frame.setVisible(true);
    }
}
