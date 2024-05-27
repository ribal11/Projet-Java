import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTree;
import javax.swing.JComboBox;

public class App extends JFrame {
    static final String[] allTasks = { "conception", "rawMaterial", "fabrication", "assemblage", "test" };

    ResourceManager resourceManager;
    ProjectManager projectManager;

    int ResourceId = 0;
    int ProjId = 0;
    File f;
    InputStream is;
    OutputStream os;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    Material mat;
    HumanResource humanRc;
    JTabbedPane mainTabbedPane, secondaryTabbedPane;
    JRadioButton humanResourceRdb, materialResourceRdb, rawMaterialRdb, miscellaneousMaterialRdb;
    ButtonGroup resourceGroup, materialGroup;
    JLabel humanResourceLbl, materialResourceLbl, rawMaterialLbl, miscellaneousMaterialLbl, resourceNameLbl,
            taskAllowdLbl, specialityLbl, jobLbl, hourlyPayLbl, materialCostLbl, conceptionLbl, fabricationLbl,
            assemblageLbl, testLbl, materialDescLbl;
    JTextField humanNameTxt, materialNameTxt, specialityTxt, jobTxt, hourlyPayTxt, materialCostTxt, materialDescTxt;
    JCheckBox conceptionCheckBox, fabricationCheckBox, assemblageCheckBox, testCheckBox, rawMaterialCheckBox;
    JList<Resource> resourcesLst;
    DefaultListModel<Resource> humanResourceLstMdl, materialResourceLstMdl;
    JButton saveResourceBtn, newResourceBtn, writeButton;
    JPanel firstTabbedPanel, resourcePanel, humanResourcePanel, materialResourcePanel, resourceTypePanel,
            resourceListPanel, allowedTaskPanel, actionsResourcePanel;

    Set<HumanResource> humanResourceSet;
    Set<Material> materialResourceSet;
    List<JCheckBox> allTaskcheckBoxes;

    // end Resources form
    // ###############################################################################################################################
     String title = "Create Project";
    private JPanel mainPanel, projectFormPanel,  createProjectPanel, formTitlePanel, projectListPanel,
            projectActionPanel, createTaskPanel, formTaskPanel, taskListPanel, taskActionPanel, taskGoBackPanel,
            taskTitlePanel;
    private JLabel prjNameLbl, prjStateLbl, prjCostLbl, prjDurationLbl, projectTitleLbl, projIdLbl, taskNameLbl, taskStateLbl,
            taskCostLbl, taskDurationLbl, taskIdLbl, taskTitleLbl;
    private JTextField prjNameTxt, prjStateTxt, prjCostTxt, prjDurationTxt, projIdTxt, taskNameTxt, taskStateTxt, taskCostTxt,
            taskDurationTxt, taskIdTxt;
    private JButton savePrjBtn, newPrjBtn, writePrjBtn, createTaskBtn, saveTaskBtn, newTaskBtn, fromTaskToProjBtn;

    private JList<Projet> projectLst;
    private JList<Task> taskLst;
    private DefaultListModel<Projet> projectLstMdl;
    private DefaultListModel<Task> tasksLstMdl;
    private Set<Projet> projectsSet, removedProjectsSet;
    private Set<Task> tasksSet, removedTasksSet;
    private Projet prj;
    private Task task;
    
    private JPanel taskOptionsPanel;
    private JButton createProcessBtn,deleteProjectBtn;
    private JPanel createProcessusPanel;
    private JRadioButton conceptionTypeRdb;
    private JRadioButton fabricationTypeRdb;
    private JRadioButton rawMaterialTypeRdb;
    private JRadioButton assemblageTypeRdb;
    private JRadioButton testTypeRdb;
    private List<JRadioButton> taskTypes ;
    private final ButtonGroup taskTypebuttonGroup = new ButtonGroup();
    //####################################################################################################################################
    
    private JPanel titleProcessesPanel,processFormPanel;
    private JTextField processNameTxt;
    private JTextField processIdTxt;
    private JTextField processCostTxt;
    private JTextField processDurationTxt;
    private JLabel titleProcessLbl,processNameLbl, processStateLbl,processIdLbl,processCostLbl,processDurationLbl;
    private JList<Process> processLst;
    private DefaultListModel<Process> processLstMdl;
    private Set<Process> processesSet, removedProcessesSet;
    private JPanel processActionPanel,processListPanel;
    private JButton processSaveBtn, processNewBtn;
    private JButton processAddResourcesBtn;
    private JButton processGoBackBtn;
    private Process process;
    private JRadioButton processingStateRdb, finishedStateRdb;
    private final ButtonGroup processStateGroup = new ButtonGroup();
    private JButton deleteTaskBtn;
    private JButton deleteProcessBtn;
    //#######################################################################################################################################
    
    private JPanel  usableResourceFormPanel;
    private JTextField employeeNameTxt;
    private JTextField employeeHourlyTxt;
    private JTextField workingHoursTxt;
    private final ButtonGroup ResourceUsableOptbuttonGroup = new ButtonGroup();
    private JPanel resourceUsableListPanel;
    private JPanel actionResourceUsablePanel;
    private JButton resourceUsableNewBtn;
    private JButton resourceUsableSaveBtn;
    private JButton btnNewButton;
    private JButton resourceUsableBackBtn;
    private JComboBox<HumanResource> EmployeeComboBox;
    private JComboBox<Material> materialComboBox;
    private DefaultListModel<HumanResourceUsage> employeeLstMdl;
    private DefaultListModel<MaterialUsage> materialLstMdl;
    private JList<HumanResourceUsage> employeeLst;
    private JList<MaterialUsage> materialLst;
    public App(ResourceManager resourceManager, ProjectManager projectManager) {

        super("Project Management");
        this.resourceManager = resourceManager;
        this.projectManager = projectManager;
        allTaskcheckBoxes = new ArrayList<>();
        taskTypes = new ArrayList<>();
        humanResourceLstMdl = new DefaultListModel<>();
        materialResourceLstMdl = new DefaultListModel<>();
        projectLstMdl = new DefaultListModel<>();
        tasksLstMdl = new DefaultListModel<>();
        processLstMdl = new DefaultListModel<>();
        humanResourceSet = new TreeSet<>();
        materialResourceSet = new TreeSet<>();
        materialResourceSet = new TreeSet<>();
        projectsSet = new TreeSet<>();
        removedProjectsSet = new TreeSet<>();
        tasksSet = new TreeSet<>();
        removedTasksSet = new TreeSet<>();
        processesSet = new TreeSet<>();
        removedProcessesSet = new TreeSet<>();
        
        readAll();
        mainTabbedPane = new JTabbedPane();

        getContentPane().add(mainTabbedPane);

        // Initialize the resourcePanel and its components
        resourcePanel = new JPanel();
        resourcePanel.setLayout(null);
        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        projectFormPanel = new JPanel();
        projectFormPanel.setLayout(null);
        projectFormPanel.setVisible(true);

        resourceTypePanel = new JPanel();
        resourceTypePanel.setLayout(null);
        resourceTypePanel.setBounds(5, 10, 260, 35);

        humanResourceRdb = new JRadioButton();
        humanResourceRdb.setBounds(0, 0, 15, 15);
        humanResourceRdb.setSelected(true);
        humanResourceRdb.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (humanResourceRdb.isSelected()) {
                    if (resourcesLst.getSelectedIndex() >= 0) {
                        resourcesLst.clearSelection();

                    }
                    resourcesLst.setModel(humanResourceLstMdl);

                    materialNameTxt.setText("");
                    materialCostTxt.setText("");
                    materialDescTxt.setText("");

                    rawMaterialCheckBox.setSelected(false);
                    rawMaterialCheckBox.setEnabled(true);
                    conceptionCheckBox.setSelected(false);
                    fabricationCheckBox.setSelected(false);
                    assemblageCheckBox.setSelected(false);
                    testCheckBox.setSelected(false);

                    materialResourcePanel.setVisible(false);
                    humanResourcePanel.setVisible(true);
                }
            }
        });

        resourceTypePanel.add(humanResourceRdb);

        humanResourceLbl = new JLabel("Human Resource");
        humanResourceLbl.setBounds(20, 0, 100, 15);
        resourceTypePanel.add(humanResourceLbl);

        materialResourceRdb = new JRadioButton();
        materialResourceRdb.setBounds(130, 0, 15, 15);
        resourceTypePanel.add(materialResourceRdb);
        materialResourceRdb.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (materialResourceRdb.isSelected()) {
                    if (resourcesLst.getSelectedIndex() >= 0) {
                        resourcesLst.clearSelection();

                    }
                    resourcesLst.setModel(materialResourceLstMdl);
                    humanNameTxt.setText("");
                    specialityTxt.setText("");
                    jobTxt.setText("");
                    hourlyPayTxt.setText("");

                    rawMaterialRdb.setSelected(true);
                    rawMaterialCheckBox.setEnabled(false);
                    rawMaterialCheckBox.setSelected(true);
                    conceptionCheckBox.setSelected(false);
                    fabricationCheckBox.setSelected(false);
                    assemblageCheckBox.setSelected(false);
                    testCheckBox.setSelected(false);

                    humanResourcePanel.setVisible(false);
                    materialResourcePanel.setVisible(true);
                }
            }
        });

        materialResourceLbl = new JLabel("Material Resource");
        materialResourceLbl.setBounds(150, 0, 115, 15);
        resourceTypePanel.add(materialResourceLbl);
        resourceGroup = new ButtonGroup();
        resourceGroup.add(materialResourceRdb);
        resourceGroup.add(humanResourceRdb);
        // adding resourceTypePanel
        resourcePanel.add(resourceTypePanel);

        // human Resource panel
        humanResourcePanel = new JPanel();
        humanResourcePanel.setLayout(null);

        humanResourcePanel.setBounds(10, 45, 250, 140);
        humanResourcePanel.setVisible(true);

        resourceNameLbl = new JLabel("Name");
        resourceNameLbl.setBounds(5, 20, 50, 25);
        humanResourcePanel.add(resourceNameLbl);

        humanNameTxt = new JTextField();
        humanNameTxt.setBounds(75, 20, 160, 25);
        humanResourcePanel.add(humanNameTxt);

        specialityLbl = new JLabel("Speciality");
        specialityLbl.setBounds(5, 50, 70, 25);
        humanResourcePanel.add(specialityLbl);

        specialityTxt = new JTextField();
        specialityTxt.setBounds(75, 50, 160, 25);
        humanResourcePanel.add(specialityTxt);

        jobLbl = new JLabel("Job");
        jobLbl.setBounds(5, 80, 50, 25);
        humanResourcePanel.add(jobLbl);

        jobTxt = new JTextField();
        jobTxt.setBounds(75, 80, 160, 25);
        humanResourcePanel.add(jobTxt);

        hourlyPayLbl = new JLabel("Hourly Pay");
        hourlyPayLbl.setBounds(5, 110, 70, 25);
        humanResourcePanel.add(hourlyPayLbl);

        hourlyPayTxt = new JTextField();
        hourlyPayTxt.setBounds(75, 110, 80, 25);
        humanResourcePanel.add(hourlyPayTxt);

        resourcePanel.add(humanResourcePanel);

        // Material resource Panel

        materialResourcePanel = new JPanel();

        materialResourcePanel.setLayout(null);
        materialResourcePanel.setBounds(5, 45, 250, 140);
        materialResourcePanel.setVisible(false);

        rawMaterialRdb = new JRadioButton();
        rawMaterialRdb.setBounds(5, 10, 15, 25);
        rawMaterialRdb.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (rawMaterialRdb.isSelected()) {
                    rawMaterialCheckBox.setSelected(true);
                    rawMaterialCheckBox.setEnabled(false);
                }
            }
        });

        materialResourcePanel.add(rawMaterialRdb);

        rawMaterialLbl = new JLabel("Raw");
        rawMaterialLbl.setBounds(25, 10, 40, 25);
        materialResourcePanel.add(rawMaterialLbl);
        miscellaneousMaterialRdb = new JRadioButton();
        miscellaneousMaterialRdb.setBounds(60, 10, 15, 25);
        miscellaneousMaterialRdb.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (miscellaneousMaterialRdb.isSelected()) {
                    rawMaterialCheckBox.setSelected(false);
                    rawMaterialCheckBox.setEnabled(true);
                }
            }
        });
        materialResourcePanel.add(miscellaneousMaterialRdb);

        miscellaneousMaterialLbl = new JLabel("Miscellaneous");
        miscellaneousMaterialLbl.setBounds(80, 10, 90, 25);
        materialResourcePanel.add(miscellaneousMaterialLbl);

        materialGroup = new ButtonGroup();
        materialGroup.add(miscellaneousMaterialRdb);
        materialGroup.add(rawMaterialRdb);

        resourceNameLbl = new JLabel("Name");
        resourceNameLbl.setBounds(10, 45, 70, 25);
        materialResourcePanel.add(resourceNameLbl);

        materialNameTxt = new JTextField();
        materialNameTxt.setBounds(85, 45, 160, 25);
        materialResourcePanel.add(materialNameTxt);

        materialDescLbl = new JLabel("Description");
        materialDescLbl.setBounds(10, 75, 70, 25);
        materialResourcePanel.add(materialDescLbl);

        materialDescTxt = new JTextField();
        materialDescTxt.setBounds(85, 75, 160, 25);
        materialResourcePanel.add(materialDescTxt);

        materialCostLbl = new JLabel("Cost");
        materialCostLbl.setBounds(10, 105, 70, 25);
        materialResourcePanel.add(materialCostLbl);

        materialCostTxt = new JTextField();
        materialCostTxt.setBounds(85, 105, 80, 25);
        materialResourcePanel.add(materialCostTxt);

        resourcePanel.add(materialResourcePanel);

        resourceListPanel = new JPanel();
        resourceListPanel.setLayout(new BorderLayout());
        resourceListPanel.setBounds(270, 45, 250, 140);

        resourcesLst = new JList<>(humanResourceLstMdl);
        resourcesLst.setBorder(new TitledBorder("resources"));
        resourcesLst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resourcesLst.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                handleResourceListChanges();
            }
        });
        JScrollPane scrollPane = new JScrollPane(resourcesLst);
        resourceListPanel.add(scrollPane, BorderLayout.CENTER);
        resourcePanel.add(resourceListPanel);

        // checkBoxes
        allowedTaskPanel = new JPanel();
        allowedTaskPanel.setBorder(new TitledBorder("Allowed Tasks"));
        allowedTaskPanel.setLayout(null);
        allowedTaskPanel.setBounds(5, 200, 530, 50);

        conceptionCheckBox = new JCheckBox("Conception");
        conceptionCheckBox.setBounds(5, 15, 90, 25);
        allowedTaskPanel.add(conceptionCheckBox);

        fabricationCheckBox = new JCheckBox("Fabrication");
        fabricationCheckBox.setBounds(100, 15, 90, 25);
        allowedTaskPanel.add(fabricationCheckBox);

        rawMaterialCheckBox = new JCheckBox("Raw Material");
        rawMaterialCheckBox.setBounds(200, 15, 110, 25);
        allowedTaskPanel.add(rawMaterialCheckBox);

        assemblageCheckBox = new JCheckBox("Assemblage");
        assemblageCheckBox.setBounds(320, 15, 100, 25);
        allowedTaskPanel.add(assemblageCheckBox);

        testCheckBox = new JCheckBox("Test");
        testCheckBox.setBounds(430, 15, 90, 25);
        allowedTaskPanel.add(testCheckBox);

        resourcePanel.add(allowedTaskPanel);

        allTaskcheckBoxes.add(conceptionCheckBox);
        allTaskcheckBoxes.add(rawMaterialCheckBox);
        allTaskcheckBoxes.add(fabricationCheckBox);
        allTaskcheckBoxes.add(assemblageCheckBox);
        allTaskcheckBoxes.add(testCheckBox);
        // save and new buttons

        actionsResourcePanel = new JPanel();
        actionsResourcePanel.setLayout(null);
        actionsResourcePanel.setBounds(5, 260, 320, 45);

        newResourceBtn = new JButton("NEW");
        newResourceBtn.setBounds(5, 5, 70, 40);
        newResourceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleMaterialNewBtn();
            }
        });
        actionsResourcePanel.add(newResourceBtn);

        saveResourceBtn = new JButton("SAVE");
        saveResourceBtn.setBounds(85, 5, 70, 40);
        saveResourceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSaveMaterialBtn();
            }
        });
        actionsResourcePanel.add(saveResourceBtn);

        writeButton = new JButton("WRITE ON DISK");
        writeButton.setBounds(165, 5, 130, 40);
        writeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeOnDisk();
            }
        });
        actionsResourcePanel.add(writeButton);
        resourcePanel.add(actionsResourcePanel);

        // starting with the project Form

        formTitlePanel = new JPanel();
        formTitlePanel.setLayout(new FlowLayout());
        formTitlePanel.setBounds(5, 11, 515, 35);
        Font font = new Font("Serif", Font.BOLD, 24);
        projectTitleLbl = new JLabel("CREATE PROJECT");
        projectTitleLbl.setFont(font);
        formTitlePanel.add(projectTitleLbl);
        projectFormPanel.add(formTitlePanel);

        
        createProjectPanel = new JPanel();
        createProjectPanel.setLayout(null);
        createProjectPanel.setBounds(10, 55, 250, 176);

        prjNameLbl = new JLabel("Name");
        prjNameLbl.setBounds(5, 20, 70, 25);
        createProjectPanel.add(prjNameLbl);

        prjNameTxt = new JTextField();
        prjNameTxt.setBounds(75, 20, 160, 25);
        createProjectPanel.add(prjNameTxt);

        prjStateLbl = new JLabel("State");
        prjStateLbl.setBounds(5, 50, 70, 25);
        createProjectPanel.add(prjStateLbl);

        prjStateTxt = new JTextField();
        prjStateTxt.setBounds(75, 50, 100, 25);
        prjStateTxt.setEnabled(false);
        createProjectPanel.add(prjStateTxt);

        projIdLbl = new JLabel("Id");
        projIdLbl.setBounds(5, 80, 70, 25);
        createProjectPanel.add(projIdLbl);

        projIdTxt = new JTextField();
        projIdTxt.setBounds(75, 80, 80, 25);
        projIdTxt.setEnabled(false);
        createProjectPanel.add(projIdTxt);

        prjCostLbl = new JLabel("Cost");
        prjCostLbl.setBounds(5, 110, 50, 25);
        createProjectPanel.add(prjCostLbl);

        prjCostTxt = new JTextField();
        prjCostTxt.setBounds(75, 110, 80, 25);
        prjCostTxt.setEnabled(false);
        createProjectPanel.add(prjCostTxt);

        prjDurationLbl = new JLabel("Duration");
        prjDurationLbl.setBounds(5, 140, 70, 25); // y-coordinate adjusted to 140
        createProjectPanel.add(prjDurationLbl);

        prjDurationTxt = new JTextField();
        prjDurationTxt.setBounds(75, 140, 80, 25); // y-coordinate adjusted to 140
        prjDurationTxt.setEnabled(false);
        createProjectPanel.add(prjDurationTxt);
        projectFormPanel.add(createProjectPanel);

        // creating the List

        projectListPanel = new JPanel();
        projectListPanel.setLayout(new BorderLayout());
        projectListPanel.setBounds(270, 60, 250, 170);

        projectLst = new JList<>(projectLstMdl);
        projectLst.setBorder(new TitledBorder("Projects"));
        projectLst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectLst.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (projectLst.getSelectedIndex() >= 0) {
                    prj = projectLst.getSelectedValue();
                    prjNameTxt.setText(prj.name);
                    prjCostTxt.setText(String.valueOf(prj.cost));
                    prjDurationTxt.setText(String.valueOf(prj.duration));
                    projIdTxt.setText(String.valueOf(prj.projId));
                    prjStateTxt.setText(prj.state);
                    createTaskBtn.setEnabled(true);
                    deleteProjectBtn.setEnabled(true);

                }
            }
        });
        JScrollPane projectScrollPane = new JScrollPane(projectLst);
        projectListPanel.add(projectScrollPane, BorderLayout.CENTER);
        projectFormPanel.add(projectListPanel);

        // create the buttons
        projectActionPanel = new JPanel();
        projectActionPanel.setLayout(null);
        projectActionPanel.setBounds(5, 242, 515, 45);

        newPrjBtn = new JButton("NEW");
        newPrjBtn.setBounds(5, 5, 70, 40);
        newPrjBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearProjectFields();
            }
        });
        projectActionPanel.add(newPrjBtn);

        savePrjBtn = new JButton("SAVE");
        savePrjBtn.setBounds(80, 5, 70, 40);
        savePrjBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = prjNameTxt.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Fill All The Fields", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (projectLst.getSelectedIndex() >= 0) {
                    prj = projectLst.getSelectedValue();
                    prj.setName(name);
                    clearProjectFields();
                } else {
                    prj = new Projet(name);
                    if (projectsSet.add(prj)) {
                        projectLstMdl.addElement(prj);
                        projectManager.addProject(prj);
                        clearProjectFields();

                    }
                }

            }
        });

        projectActionPanel.add(savePrjBtn);

        createTaskBtn = new JButton("CREATE TASK >>");
        createTaskBtn.setBounds(385, 5, 130, 40);
        createTaskBtn.setEnabled(false);

        projectActionPanel.add(createTaskBtn);

        projectFormPanel.add(projectActionPanel);
        
         deleteProjectBtn = new JButton("DELETE");
         deleteProjectBtn.setEnabled(false);
        deleteProjectBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		deleteProject();
        	}
        });
        deleteProjectBtn.setBounds(155, 5, 77, 40);
        projectActionPanel.add(deleteProjectBtn);

        mainPanel.add(projectFormPanel, "Projects");
        writePrjBtn = new JButton("WRITE ON DISK");
        writePrjBtn.setBounds(10, 298, 130, 40);
        projectFormPanel.add(writePrjBtn);
        writePrjBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeOnDisk();
            }
        });
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        cardLayout.show(mainPanel, "Projects");
        createTaskBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Tasks");
                prj = projectLst.getSelectedValue();
                if (prj.tasks.size() > 0) {
                    Iterator<Task> it = prj.tasks.iterator();
                    while (it.hasNext()) {
                        tasksLstMdl.addElement(it.next());
                    }
                }
                
                
                	checkTypeAuthorization();
                
            }
        });
        // begin create Task Page

        createTaskPanel = new JPanel();
        createTaskPanel.setLayout(null);
        createTaskPanel.setVisible(true);
        taskTitlePanel = new JPanel();
        taskTitlePanel.setLayout(null);
        taskTitlePanel.setBounds(5, 10, 515, 35);
        taskTitleLbl = new JLabel("CREATE TASK");
        taskTitleLbl.setFont(font);
        taskTitleLbl.setBounds(185, 0, 176, 35);
        taskTitlePanel.add(taskTitleLbl);
        createTaskPanel.add(taskTitlePanel);

        formTaskPanel = new JPanel();
        formTaskPanel.setLayout(null);
        formTaskPanel.setBounds(10, 55, 250, 170);

        taskNameLbl = new JLabel("Name");
        taskNameLbl.setBounds(5, 20, 70, 25);
        formTaskPanel.add(taskNameLbl);

        taskNameTxt = new JTextField();
        taskNameTxt.setBounds(75, 20, 160, 25);
        formTaskPanel.add(taskNameTxt);

        taskStateLbl = new JLabel("State");
        taskStateLbl.setBounds(5, 50, 70, 25);
        formTaskPanel.add(taskStateLbl);

        taskStateTxt = new JTextField();
        taskStateTxt.setBounds(75, 50, 160, 25);
        taskStateTxt.setEnabled(false);
        formTaskPanel.add(taskStateTxt);

        taskIdLbl = new JLabel("Id");
        taskIdLbl.setBounds(5, 80, 70, 25);
        formTaskPanel.add(taskIdLbl);

        taskIdTxt = new JTextField();
        taskIdTxt.setBounds(75, 80, 80, 25);
        taskIdTxt.setEnabled(false);
        formTaskPanel.add(taskIdTxt);

        taskCostLbl = new JLabel("Cost");
        taskCostLbl.setBounds(5, 110, 50, 25);
        formTaskPanel.add(taskCostLbl);

        taskCostTxt = new JTextField();
        taskCostTxt.setBounds(75, 110, 80, 25);
        taskCostTxt.setEnabled(false);
        formTaskPanel.add(taskCostTxt);

        taskDurationLbl = new JLabel("Duration");
        taskDurationLbl.setBounds(5, 140, 70, 25); // y-coordinate adjusted to 140
        formTaskPanel.add(taskDurationLbl);

        taskDurationTxt = new JTextField();
        taskDurationTxt.setBounds(75, 140, 80, 25); // y-coordinate adjusted to 140
        taskDurationTxt.setEnabled(false);
        formTaskPanel.add(taskDurationTxt);

        createTaskPanel.add(formTaskPanel);

        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BorderLayout());
        taskListPanel.setBounds(270, 60, 250, 170);

        taskLst = new JList<>(tasksLstMdl);
        taskLst.setBorder(new TitledBorder("Tasks"));
        taskLst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskLst.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                handleTaskListSelection();
                
            }
        });
        JScrollPane taskScrollPane = new JScrollPane(taskLst);
        taskListPanel.add(taskScrollPane, BorderLayout.CENTER);
        createTaskPanel.add(taskListPanel);
        
        //begin option panel
        taskOptionsPanel = new JPanel();
        taskOptionsPanel.setBounds(5, 236, 513, 28);
        createTaskPanel.add(taskOptionsPanel);
        taskOptionsPanel.setLayout(null);
        
        conceptionTypeRdb = new JRadioButton("Conception");
        taskTypebuttonGroup.add(conceptionTypeRdb);
        conceptionTypeRdb.setBounds(6, 0, 102, 23);
        taskOptionsPanel.add(conceptionTypeRdb);
        
        fabricationTypeRdb = new JRadioButton("Fabrication");
        taskTypebuttonGroup.add(fabricationTypeRdb);
        fabricationTypeRdb.setBounds(110, 0, 102, 23);
        taskOptionsPanel.add(fabricationTypeRdb);
        
        rawMaterialTypeRdb = new JRadioButton("Raw Material");
        taskTypebuttonGroup.add(rawMaterialTypeRdb);
        rawMaterialTypeRdb.setBounds(214, 0, 107, 23);
        taskOptionsPanel.add(rawMaterialTypeRdb);
        
        assemblageTypeRdb = new JRadioButton("Assemblage");
        taskTypebuttonGroup.add(assemblageTypeRdb);
        assemblageTypeRdb.setBounds(323, 0, 102, 23);
        taskOptionsPanel.add(assemblageTypeRdb);
        
        testTypeRdb = new JRadioButton("Test");
        taskTypebuttonGroup.add(testTypeRdb);
        testTypeRdb.setBounds(427, 0, 58, 23);
        taskOptionsPanel.add(testTypeRdb);
        
        taskTypes.add(conceptionTypeRdb);
        taskTypes.add(fabricationTypeRdb);
        taskTypes.add(rawMaterialTypeRdb);
        taskTypes.add(assemblageTypeRdb);
        taskTypes.add(testTypeRdb);
        // create actions buttons for tasks
        taskActionPanel = new JPanel();
        taskActionPanel.setLayout(null);
        taskActionPanel.setBounds(5, 275, 525, 45);

        fromTaskToProjBtn = new JButton("<< BACK");
        fromTaskToProjBtn.setBounds(339, 5, 83, 40);
        fromTaskToProjBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAllTaskPage();
                projectLst.clearSelection();
                prjNameTxt.setText("");
                prjCostTxt.setText("");
                prjDurationTxt.setText("");
                prjStateTxt.setText("");
                projIdTxt.setText("");
                createTaskBtn.setEnabled(false);
                deleteProjectBtn.setEnabled(false);
                cardLayout.show(mainPanel, "Projects");
            }
        });
        taskActionPanel.add(fromTaskToProjBtn);
        newTaskBtn = new JButton("NEW");
        newTaskBtn.setBounds(10, 5, 70, 40);
        newTaskBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearTasksFields();
            }
        });
        taskActionPanel.add(newTaskBtn);

        saveTaskBtn = new JButton("SAVE");
        saveTaskBtn.setBounds(90, 5, 70, 40);
        saveTaskBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	handleTaskSaveBtn();
            }
        });

        taskActionPanel.add(saveTaskBtn);
        createTaskPanel.add(taskActionPanel);
        
         createProcessBtn = new JButton("NEXT >>");
         createProcessBtn.setEnabled(false);
       
        createProcessBtn.setBounds(432, 5, 83, 40);
        createProcessBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		task = taskLst.getSelectedValue();
        		if (task.processes.size() > 0) {
        			Iterator<Process> it = task.processes.iterator();
        			while(it.hasNext()) {
        				processLstMdl.addElement(it.next());
        				
        			}
        			
        			
        		}
        		clearProcessForm();
        	cardLayout.show(mainPanel, "Processus");
        	}
        });
        taskActionPanel.add(createProcessBtn);
        
        deleteTaskBtn = new JButton("DELETE");
        deleteTaskBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		deleteTask();
        	}
        });
        deleteTaskBtn.setEnabled(false);
        deleteTaskBtn.setBounds(167, 5, 89, 40);
        taskActionPanel.add(deleteTaskBtn);

        mainPanel.add(createTaskPanel, "Tasks");
        
        
        
        // begin processes page
        
        createProcessusPanel = new JPanel();
        mainPanel.add(createProcessusPanel, "Processus");
        createProcessusPanel.setLayout(null);
        
        titleProcessesPanel = new JPanel();
        titleProcessesPanel.setBounds(10, 5, 531, 40);
        createProcessusPanel.add(titleProcessesPanel);
        
         titleProcessLbl = new JLabel("Processes");
        titleProcessLbl.setFont(new Font("Serif", Font.BOLD, 23));
        titleProcessesPanel.add(titleProcessLbl);
        
         processFormPanel = new JPanel();
        processFormPanel.setBounds(10, 55, 250, 170);
        createProcessusPanel.add(processFormPanel);
        processFormPanel.setLayout(null);
        
         processNameLbl = new JLabel("Name");
        processNameLbl.setBounds(5, 20, 70, 25);
        processFormPanel.add(processNameLbl);
        
        processNameTxt = new JTextField();
        processNameTxt.setBounds(75, 20, 160, 25);
        processFormPanel.add(processNameTxt);
        processNameTxt.setColumns(10);
        
         processStateLbl = new JLabel("State");
        processStateLbl.setBounds(5, 140, 41, 25);
        processFormPanel.add(processStateLbl);
        
         processIdLbl = new JLabel("Id");
        processIdLbl.setBounds(5, 50, 46, 25);
        processFormPanel.add(processIdLbl);
        
        processIdTxt = new JTextField();
        processIdTxt.setEnabled(false);
        processIdTxt.setBounds(75, 50, 70, 25);
        processFormPanel.add(processIdTxt);
        processIdTxt.setColumns(10);
        
         processCostLbl = new JLabel("Cost");
        processCostLbl.setBounds(5, 80, 46, 25);
        processFormPanel.add(processCostLbl);
        
        processCostTxt = new JTextField();
        processCostTxt.setEnabled(false);
        processCostTxt.setBounds(75, 80, 70, 25);
        processFormPanel.add(processCostTxt);
        processCostTxt.setColumns(10);
        
         processDurationLbl = new JLabel("Duration");
        processDurationLbl.setBounds(5, 110, 60, 25);
        processFormPanel.add(processDurationLbl);
        
        processDurationTxt = new JTextField();
        processDurationTxt.setBounds(75, 110, 70, 25);
        processFormPanel.add(processDurationTxt);
        processDurationTxt.setColumns(10);
        
         processingStateRdb = new JRadioButton("Processing");
        processStateGroup.add(processingStateRdb);
        processingStateRdb.setBounds(50, 141, 104, 23);
        processFormPanel.add(processingStateRdb);
        
         finishedStateRdb = new JRadioButton("Finished");
        processStateGroup.add(finishedStateRdb);
        finishedStateRdb.setBounds(156, 141, 79, 23);
        processFormPanel.add(finishedStateRdb);
        
         processListPanel = new JPanel();
        processListPanel.setBounds(270, 56, 243, 170);
        createProcessusPanel.add(processListPanel);
        processListPanel.setLayout(new BorderLayout());
        
        processLst = new JList<>(processLstMdl);
        processLst.setBorder(new TitledBorder("Processes"));
        processLst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        processLst.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent e) {
        		 handleProcessListSelection();
        	}
        });
        JScrollPane processScrollPane = new JScrollPane(processLst);
        
        processListPanel.add(processScrollPane, BorderLayout.CENTER);
        
        processActionPanel = new JPanel();
        processActionPanel.setBounds(10, 245, 502, 57);
        createProcessusPanel.add(processActionPanel);
        processActionPanel.setLayout(null);
        
         processNewBtn = new JButton("NEW");
         processNewBtn.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		clearProcessForm();
         	}
         });
        processNewBtn.setBounds(10, 11, 62, 40);
        processActionPanel.add(processNewBtn);
        
        processSaveBtn = new JButton("SAVE");
        processSaveBtn.setBounds(85, 11, 69, 40);
        processSaveBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		handleProcessSaveBtn();
        	}
        });
        processActionPanel.add(processSaveBtn);
        
        processAddResourcesBtn = new JButton("NEXT >>");
        processAddResourcesBtn.setBounds(409, 11, 83, 40);
        processAddResourcesBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		cardLayout.show(mainPanel,"usableHumanFormPanel" );
        	}
        });
        processActionPanel.add(processAddResourcesBtn);
        
        processGoBackBtn = new JButton("<< BACK");
        processGoBackBtn.setBounds(322, 11, 83, 40) ;
        processGoBackBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		{
        			clearTasksFields();
        			processLstMdl.clear();
                	cardLayout.show(mainPanel, "Tasks");
                };
        	}
        });
        processActionPanel.add(processGoBackBtn);
        
        deleteProcessBtn = new JButton("DELETE");
        deleteProcessBtn.setBounds(164, 11, 83, 40);
        processActionPanel.add(deleteProcessBtn);
        // end process page
        
        
        usableResourceFormPanel = new JPanel();
        usableResourceFormPanel.setLayout(null);
        mainPanel.add(usableResourceFormPanel, "Resources");
        
        
        JPanel titleHumanUsagePanel = new JPanel();
        titleHumanUsagePanel.setBounds(10, 11, 531, 42);
        usableResourceFormPanel.add(titleHumanUsagePanel);
        
        JLabel resourcesUsageTitleLbl = new JLabel("Resources");
        resourcesUsageTitleLbl.setFont(new Font("Segoe UI Symbol", Font.BOLD, 24));
        titleHumanUsagePanel.add(resourcesUsageTitleLbl);
        
        JPanel HumanUsablePanel = new JPanel();
        HumanUsablePanel.setBounds(10, 110, 269, 182);
        usableResourceFormPanel.add(HumanUsablePanel);
        HumanUsablePanel.setLayout(null);
        
        JLabel EmployeesLbl = new JLabel("Choose Employee");
        EmployeesLbl.setBounds(10, 14, 91, 23);
        HumanUsablePanel.add(EmployeesLbl);
        
         EmployeeComboBox = new JComboBox();
        EmployeeComboBox.setBounds(111, 11, 148, 28);
        HumanUsablePanel.add(EmployeeComboBox);
        
        JLabel employeeNameLbl = new JLabel("Name");
        employeeNameLbl.setBounds(10, 63, 46, 23);
        HumanUsablePanel.add(employeeNameLbl);
        
        employeeNameTxt = new JTextField();
        employeeNameTxt.setBounds(111, 64, 148, 25);
        HumanUsablePanel.add(employeeNameTxt);
        employeeNameTxt.setColumns(10);
        
        JLabel employeeHourlyLbl = new JLabel("Hourly Pay");
        employeeHourlyLbl.setBounds(10, 99, 69, 25);
        HumanUsablePanel.add(employeeHourlyLbl);
        
        employeeHourlyTxt = new JTextField();
        employeeHourlyTxt.setBounds(111, 99, 86, 25);
        HumanUsablePanel.add(employeeHourlyTxt);
        employeeHourlyTxt.setColumns(10);
        
        JLabel workingHoursLbl = new JLabel("Working Hours");
        workingHoursLbl.setBounds(10, 135, 91, 25);
        HumanUsablePanel.add(workingHoursLbl);
        
        workingHoursTxt = new JTextField();
        workingHoursTxt.setBounds(111, 135, 86, 25);
        HumanUsablePanel.add(workingHoursTxt);
        workingHoursTxt.setColumns(10);
        
        JPanel ResourceUsableOptionPanel = new JPanel();
        ResourceUsableOptionPanel.setBounds(10, 64, 269, 42);
        usableResourceFormPanel.add(ResourceUsableOptionPanel);
        ResourceUsableOptionPanel.setLayout(null);
        
        JRadioButton humanUsableRdb = new JRadioButton("Human");
        ResourceUsableOptbuttonGroup.add(humanUsableRdb);
        humanUsableRdb.setBounds(6, 12, 72, 23);
        ResourceUsableOptionPanel.add(humanUsableRdb);
        
        JRadioButton materialUsableRdb = new JRadioButton("Material");
        ResourceUsableOptbuttonGroup.add(materialUsableRdb);
        materialUsableRdb.setBounds(80, 12, 78, 23);
        ResourceUsableOptionPanel.add(materialUsableRdb);
        
        resourceUsableListPanel = new JPanel();
        resourceUsableListPanel.setBounds(302, 110, 239, 182);
        usableResourceFormPanel.add(resourceUsableListPanel);
        
        actionResourceUsablePanel = new JPanel();
        actionResourceUsablePanel.setBounds(10, 302, 531, 48);
        usableResourceFormPanel.add(actionResourceUsablePanel);
        actionResourceUsablePanel.setLayout(null);
        
        resourceUsableNewBtn = new JButton("NEW");
        resourceUsableNewBtn.setBounds(10, 0, 70, 40);
        actionResourceUsablePanel.add(resourceUsableNewBtn);
        
        resourceUsableSaveBtn = new JButton("SAVE");
        resourceUsableSaveBtn.setBounds(90, 0, 70, 40);
        actionResourceUsablePanel.add(resourceUsableSaveBtn);
        
        btnNewButton = new JButton("DELETE");
        btnNewButton.setBounds(170, 0, 70, 40);
        actionResourceUsablePanel.add(btnNewButton);
        
        resourceUsableBackBtn = new JButton("<< BACK");
        resourceUsableBackBtn.setBounds(432, 0, 89, 40);
        actionResourceUsablePanel.add(resourceUsableBackBtn);
        
        
        
        
        // Initialize the secondaryTabbedPane and add the resourcePanel to it

        secondaryTabbedPane = new JTabbedPane();
        secondaryTabbedPane.addTab("Resources", resourcePanel);
        secondaryTabbedPane.addTab("Project Form", mainPanel);
        
        

        // Add the secondaryTabbedPane to the mainTabbedPane
        mainTabbedPane.addTab("Form Tab", secondaryTabbedPane);

    }

    public static void main(String[] args) throws Exception {
        ResourceManager resourceManager = new ResourceManager();
        ProjectManager projectManager = new ProjectManager();
        App frame = new App(resourceManager, projectManager);
        frame.setSize(600, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void handleMaterialNewBtn() {
        rawMaterialCheckBox.setEnabled(true);
        rawMaterialCheckBox.setSelected(false);
        conceptionCheckBox.setSelected(false);
        fabricationCheckBox.setSelected(false);
        assemblageCheckBox.setSelected(false);
        testCheckBox.setSelected(false);
        if (resourcesLst.getSelectedIndex() >= 0) {
            resourcesLst.clearSelection();
        }
        if (materialResourceRdb.isSelected()) {
            materialNameTxt.setText("");
            materialCostTxt.setText("");
            materialDescTxt.setText("");

            if (rawMaterialRdb.isSelected()) {
                rawMaterialCheckBox.setEnabled(false);
                rawMaterialCheckBox.setSelected(true);
            }
        } else {
            humanNameTxt.setText("");
            specialityTxt.setText("");
            jobTxt.setText("");
            hourlyPayTxt.setText("");

        }
    }

    public void handleSaveMaterialBtn() {
        if (humanResourceRdb.isSelected()) {

            String name = humanNameTxt.getText().trim();
            String spec = specialityTxt.getText().trim();
            String job = jobTxt.getText().trim();
            String pay = hourlyPayTxt.getText().trim();
            if (name.isEmpty() || spec.isEmpty() || job.isEmpty() || pay.isEmpty()) {
                JOptionPane.showMessageDialog(null, "please fill all the fields", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                double payNum = 0;
                try {
                    payNum = Double.parseDouble(pay);
                    if (payNum <= 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a positive number", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "please enter a number", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean noCheckboxChecked = true;
                Set<String> tasks = new TreeSet<>();

                for (int i = 0; i < allTaskcheckBoxes.size(); i++) {
                    if (allTaskcheckBoxes.get(i).isSelected()) {
                        tasks.add(allTasks[i]);
                        noCheckboxChecked = false;
                    }
                }
                if (noCheckboxChecked) {
                    JOptionPane.showMessageDialog(null, "Please choose a task", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (resourcesLst.getSelectedIndex() >= 0) {
                    Resource rec = resourcesLst.getSelectedValue();
                    humanRc = (HumanResource) rec;
                    humanRc.updateHumanResources(name, spec, job, payNum, tasks);
                    resourcesLst.clearSelection();
                } else {
                    humanRc = new HumanResource(name, spec, job, payNum, tasks);
                    if (humanResourceSet.add(humanRc)) {
                        humanResourceLstMdl.addElement(humanRc);
                        resourceManager.addEmployee(humanRc);
                    }
                }
                humanNameTxt.setText("");
                specialityTxt.setText("");
                jobTxt.setText("");
                hourlyPayTxt.setText("");
                for (int i = 0; i < allTaskcheckBoxes.size(); i++) {
                    allTaskcheckBoxes.get(i).setSelected(false);
                }

            }

        } else {
            String name = materialNameTxt.getText().trim();
            String desc = materialDescTxt.getText().trim();
            String cost = materialCostTxt.getText().trim();
            if (name.isEmpty() || desc.isEmpty() || cost.isEmpty()) {
                JOptionPane.showMessageDialog(null, "please fill all the fields", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                double costNum = 0;
                try {
                    costNum = Double.parseDouble(cost);
                    if (costNum <= 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a positive number", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "please enter a number", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean noCheckboxChecked = true;
                Set<String> tasks = new TreeSet<>();

                for (int i = 0; i < allTaskcheckBoxes.size(); i++) {
                    if (allTaskcheckBoxes.get(i).isSelected()) {
                        tasks.add(allTasks[i]);
                        noCheckboxChecked = false;
                    }
                }

                if (noCheckboxChecked) {
                    JOptionPane.showMessageDialog(null, "Please choose a task", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String type = rawMaterialRdb.isSelected() ? "Raw material" : "miscellaneous";
                if (resourcesLst.getSelectedIndex() >= 0) {
                    Resource rec = resourcesLst.getSelectedValue();
                    mat = (Material) rec;

                    mat.updateMaterialResources(name, type, costNum, desc, tasks);

                    resourcesLst.clearSelection();
                } else {
                    mat = new Material(name, type, costNum, desc, tasks);
                    if (materialResourceSet.add(mat)) {
                        materialResourceLstMdl.addElement(mat);
                        resourceManager.addMaterial(mat);
                    }
                }

                materialNameTxt.setText("");
                materialCostTxt.setText("");
                materialDescTxt.setText("");

                for (int i = 0; i < allTaskcheckBoxes.size(); i++) {
                    if (type == "Raw material") {
                        if (allTasks[i] == "rawMaterial") {
                            allTaskcheckBoxes.get(i).setSelected(true);
                            allTaskcheckBoxes.get(i).setEnabled(false);
                        } else {
                            allTaskcheckBoxes.get(i).setSelected(false);
                        }
                    } else {
                        allTaskcheckBoxes.get(i).setSelected(false);
                    }
                }

            }
        }
    }

    public void handleResourceListChanges() {
        if (resourcesLst.getSelectedIndex() >= 0) {
            Resource resource = resourcesLst.getSelectedValue();
            if (resource.type.equals("Human")) {
                HumanResource humanResource = (HumanResource) resource;
                if (!humanResourceRdb.isSelected()) {
                    humanResourceRdb.setSelected(true);
                }

                humanNameTxt.setText(resource.name);
                specialityTxt.setText(humanResource.speciality);
                jobTxt.setText(humanResource.job);
                hourlyPayTxt.setText(String.valueOf(humanResource.hourlyRate));

                for (int i = 0; i < allTasks.length; i++) {
                    if (humanResource.taskAllowed.contains(allTasks[i])) {
                        allTaskcheckBoxes.get(i).setSelected(true);
                    } else {
                        allTaskcheckBoxes.get(i).setSelected(false);
                    }
                }
            } else {
                Material materialResource = (Material) resource;
                if (!materialResourceRdb.isSelected()) {
                    materialResourceRdb.setSelected(true);
                }

                materialNameTxt.setText(materialResource.name);
                materialDescTxt.setText(materialResource.description);
                materialCostTxt.setText(String.valueOf(materialResource.unitCost));
                if (materialResource.materialType == "Raw material") {
                    rawMaterialRdb.setSelected(true);
                } else {
                    miscellaneousMaterialRdb.setSelected(true);
                }
                for (int i = 0; i < allTasks.length; i++) {
                    if (materialResource.taskAllowed.contains(allTasks[i])) {
                        allTaskcheckBoxes.get(i).setSelected(true);
                    } else {
                        allTaskcheckBoxes.get(i).setSelected(false);
                    }
                }
            }
        }
    }

    private void readMaterialResources() {
        ;

        try {
            File materialFile = new File("material.dat");
            if (!materialFile.exists()) {
                materialFile.createNewFile();
            }
            if (materialFile.length() == 0) {
                // Handle empty file scenario
                return;
            }

            try (FileInputStream fis = new FileInputStream(materialFile);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {

                materialResourceSet = ((Set<Material>) ois.readObject());

                Iterator<Material> it = materialResourceSet.iterator();
                while (it.hasNext()) {
                    Material material = it.next();
                    materialResourceLstMdl.addElement(material);
                    ResourceId = Math.max(ResourceId, material.id); // Update id with the maximum value
                    resourceManager.addMaterial(material);
                }
            } catch (EOFException eof) {
                // Handle EOFException (file is empty or not in the expected format)
                eof.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            ex.printStackTrace();
        }

        // Set the next id to be used

    }

    private void readHumanResource() {
        try {
            File humanFile = new File("human.dat");
            if (!humanFile.exists()) {
                humanFile.createNewFile();
            }
            if (humanFile.length() == 0) {
                // Handle empty file scenario
                return;
            }

            try (FileInputStream fis = new FileInputStream(humanFile);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {

                humanResourceSet = ((Set<HumanResource>) ois.readObject());

                Iterator<HumanResource> it = humanResourceSet.iterator();
                while (it.hasNext()) {
                    HumanResource humanRec = it.next();
                    humanResourceLstMdl.addElement(humanRec);
                    ResourceId = Math.max(ResourceId, humanRec.id); // Update id with the maximum value
                    resourceManager.addEmployee(humanRec);
                }
            } catch (EOFException eof) {
                // Handle EOFException (file is empty or not in the expected format)
                eof.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            ex.printStackTrace();
        }

    }

    private void readProjects() {
        try {
            File projectFile = new File("project.dat");
            if (!projectFile.exists()) {
                projectFile.createNewFile();
            }
            if (projectFile.length() == 0) {
                // Handle empty file scenario
                return;
            }

            try (FileInputStream fis = new FileInputStream(
                    projectFile);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {

                projectsSet = ((Set<Projet>) ois.readObject());

                Iterator<Projet> it = projectsSet.iterator();
                while (it.hasNext()) {
                    Projet projet = it.next();
                    projectLstMdl.addElement(projet);
                    projet.addObserverToTasks();
                    ProjId = Math.max(ResourceId, projet.projId); // Update id with the maximum value
                    projectManager.addProject(projet);
                }
            } catch (EOFException eof) {
                // Handle EOFException (file is empty or not in the expected format)
                eof.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            ex.printStackTrace();
        }
    }
    
    private void readAll() {
        readHumanResource();
        readMaterialResources();
        readProjects();
        Resource.next = ResourceId + 1;
        Projet.next = ProjId + 10;
    }

    private void writeOnDisk() {
        try {
            // Writing materialResourceSet
            File materialFile = new File("material.dat");
            if (!materialFile.exists()) {
                materialFile.createNewFile();
            }
            if (materialResourceSet.size() != 0) {
                FileOutputStream fosMaterial = new FileOutputStream(materialFile);
                ObjectOutputStream oosMaterial = new ObjectOutputStream(fosMaterial);
                oosMaterial.writeObject(materialResourceSet);
                oosMaterial.flush(); // Flush before closing
                oosMaterial.close(); // Close the stream
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            // Writing humanResourceSet
            File humanFile = new File("human.dat");
            if (!humanFile.exists()) {
                humanFile.createNewFile();
            }
            if (humanResourceSet.size() != 0) {
                FileOutputStream fosHuman = new FileOutputStream(humanFile);
                ObjectOutputStream oosHuman = new ObjectOutputStream(fosHuman);
                oosHuman.writeObject(humanResourceSet);
                oosHuman.flush(); // Flush before closing
                oosHuman.close(); // Close the stream
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        try {

            File projectFile = new File("project.dat");
            if (!removedProcessesSet.isEmpty() || !removedTasksSet.isEmpty() || !removedProjectsSet.isEmpty()) {
            	if (projectFile.exists()) {
            		projectFile.delete();
            	}
            }
            if (!projectFile.exists()) {
                projectFile.createNewFile();
            }
            if (projectsSet.size() != 0) {
                FileOutputStream fosProject = new FileOutputStream(projectFile);
                ObjectOutputStream oosMaterial = new ObjectOutputStream(fosProject);
                oosMaterial.writeObject(projectsSet);
                
                oosMaterial.flush(); // Flush before closing
                oosMaterial.close(); // Close the stream
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void clearProjectFields() {
        prjNameTxt.setText("");
        prjCostTxt.setText("");
        prjDurationTxt.setText("");
        prjStateTxt.setText("");
        projIdTxt.setText("");
        conceptionTypeRdb.setSelected(false);
        fabricationTypeRdb.setSelected(false);
        rawMaterialTypeRdb.setSelected(false);
        assemblageTypeRdb.setSelected(false);
        testTypeRdb.setSelected(false);
        projectLst.clearSelection();
        createTaskBtn.setEnabled(false);
        deleteProjectBtn.setEnabled(false);
    }
    
    private void deleteProject() {
    	prj = projectLst.getSelectedValue();
    	
    	Iterator<Task> itTask = prj.tasks.iterator();
    	Iterator<Process> itProcess;
    	while(itTask.hasNext()) {
    		task = itTask.next();
    		itProcess = task.processes.iterator();
    		while(itProcess.hasNext()) {
    			process = itProcess.next();
    			process.cleanUp();
    			if (processesSet.remove(process)) {
    				processLstMdl.removeElement(process);
    				removedProcessesSet.add(process);
    			}
    			
    		}
    		task.cleanUp();
    		if (tasksSet.remove(task)) {
    			tasksLstMdl.removeElement(task);
    			removedTasksSet.add(task);
    		}
    		
    	}
    	
    	prj.cleanUp();
    	if(projectsSet.remove(prj)) {
    		projectLstMdl.removeElement(prj);
    		removedProjectsSet.add(prj);
    		projectManager.removeProject(prj);
    	}
    	clearProjectFields();
    	File projectFile = new File("project.dat");
        if (projectFile.exists()) {
            projectFile.delete();
        }
    	
    }

    private void clearTasksFields() {
        taskNameTxt.setText("");
        taskCostTxt.setText("");
        taskDurationTxt.setText("");
        taskStateTxt.setText("");
        taskIdTxt.setText("");
        checkTypeAuthorization();
        
        taskLst.clearSelection();
        createProcessBtn.setEnabled(false);
        deleteTaskBtn.setEnabled(false);
        // createTaskBtn.setEnabled(false);
    };
    
    //for when we want to go back 
    private void clearAllTaskPage() {
    	clearTasksFields();
        for (int i = 0; i < taskTypes.size(); i++) {
        	taskTypes.get(i).setEnabled(true);
        }
        tasksLstMdl.clear();
    }
    
    //check which task option is still in the possible task enable them and desable the rest
    private void checkTypeAuthorization() {
    	for(int i = 0; i < allTasks.length; i++) {
    		
    		if (!prj.possibleTasks.get(allTasks[i])) {
    			taskTypes.get(i).setEnabled(false);
    		} else {
    			taskTypes.get(i).setEnabled(true);
    		}
    		
    	}
    	taskTypebuttonGroup.clearSelection();
    }
    
    private void deleteTask() {
    	task = taskLst.getSelectedValue();
    	prj = projectLst.getSelectedValue();
    	Iterator<Process> itProcess = task.processes.iterator();
    	while(itProcess.hasNext()) {
    		process = itProcess.next();
			process.cleanUp();
			if (processesSet.remove(process)) {
				processLstMdl.removeElement(process);
				removedProcessesSet.add(process);
			}   		
    	}
    	task.cleanUp();
		if (tasksSet.remove(task)) {
			tasksLstMdl.removeElement(task);
			removedTasksSet.add(task);
		}
		prj.removeTask(task);	
		clearTasksFields();
    }
    
    private void clearProcessForm() {
    	processNameTxt.setText("");
    	processingStateRdb.setSelected(true);
    	finishedStateRdb.setEnabled(false);
    	processDurationTxt.setText("");
    	processCostTxt.setText("");
    	processIdTxt.setText("");
    	processLst.clearSelection();
    }
    
    private void handleTaskListSelection() {
    	if (taskLst.getSelectedIndex() >= 0) {
            task = taskLst.getSelectedValue();
            taskNameTxt.setText(task.name);
            taskStateTxt.setText(task.state);
            taskCostTxt.setText(String.valueOf(task.cost));
            taskDurationTxt.setText(String.valueOf(task.duration));
            taskIdTxt.setText(String.valueOf(task.id));
            for(int i = 0; i< taskTypes.size(); i++) {
            	if (task.type.equals(allTasks[i])) {
            		taskTypes.get(i).setSelected(true);
            	}
            	taskTypes.get(i).setEnabled(false);
            }
            createProcessBtn.setEnabled(true);
            deleteTaskBtn.setEnabled(true);
        }
    }
    
    private void handleTaskSaveBtn() {
    	 String name = taskNameTxt.getText();
         boolean isSelect = false;
         String type = "";
         for (int i = 0; i< taskTypes.size(); i++) {
         	if (taskTypes.get(i).isSelected()) {
         		isSelect = true;
         		type = allTasks[i];
         	}
         }
         if (name.isEmpty() || !isSelect) {
             JOptionPane.showMessageDialog(null, "Please Fill All The Fields", "Error",
                     JOptionPane.ERROR_MESSAGE);
             return;
         }
         
         
         if (taskLst.getSelectedIndex() >= 0) {
             task = taskLst.getSelectedValue();
             task.setName(name);
             clearTasksFields();
             
         } else {
             task = new Task(name,type);
             if (tasksSet.add(task)) {
                 tasksLstMdl.addElement(task);
                 Projet project = projectLst.getSelectedValue();
                 project.addTask(task);
                 for (int i = 0; i < taskTypes.size(); i++) {
                 	if (taskTypes.get(i).isSelected()) {
                 		prj.removePossibleTask(allTasks[i]);
                 		
                 	}
                 }
                                        
             }
             clearTasksFields();
         }

     
    }
    
    private void handleProcessListSelection() {
    	if (processLst.getSelectedIndex() >= 0) {
			process = processLst.getSelectedValue();
			processNameTxt.setText(process.name);
			processDurationTxt.setText(String.valueOf(process.duration));
			processCostTxt.setText(String.valueOf(process.cost));
			if (process.finished) {
				finishedStateRdb.setSelected(true);
			}
			processIdTxt.setText(String.valueOf(process.id));
			
			if (process.humanResources.size() > 0 || process.materials.size() > 0) {
				finishedStateRdb.setEnabled(true);
			}
		}
    }
    
    private void handleProcessSaveBtn() {
    	String name = processNameTxt.getText();
		boolean finishedState;
		if (processingStateRdb.isSelected()) finishedState = false;
		else  finishedState = true;
		
		if (name.isEmpty()  || processDurationTxt.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please Fill All The Fields", "Error",
                    JOptionPane.ERROR_MESSAGE);
			return;
		}
		int duration = 0;
		try {
			 duration = Integer.parseInt(processDurationTxt.getText());
			 if (duration <= 0) {
				 JOptionPane.showMessageDialog(null, "Duration Higher than 0", "Error",
                         JOptionPane.ERROR_MESSAGE);
				 return;
			 }
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Duration Must Be Number", "Error",
                    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (processLst.getSelectedIndex() >= 0) {
			process = processLst.getSelectedValue();
			process.setDuration(duration);
			if (finishedState) {
				process.finishProcess();
			} else {
				process.stillProcessing();
			}
			process.setName(name);
		} else {
			process = new Process(name, duration);
			if (processesSet.add(process)) {
				task.addProcess(process);
				processLstMdl.addElement(process);
			}
		}
		
		clearProcessForm();
		
	
    }
}
