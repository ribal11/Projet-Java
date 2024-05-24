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
    JPanel mainPanel, projectFormPanel, projectPanel, createProjectPanel, formTitlePanel, projectListPanel,
            projectActionPanel, createTaskPanel, formTaskPanel, taskListPanel, taskActionPanel, taskGoBackPanel,
            taskTitlePanel;
    JLabel prjNameLbl, prjStateLbl, prjCostLbl, prjDurationLbl, projectTitleLbl, projIdLbl, taskNameLbl, taskStateLbl,
            taskCostLbl, taskDurationLbl, taskIdLbl, taskTitleLbl;
    JTextField prjNameTxt, prjStateTxt, prjCostTxt, prjDurationTxt, projIdTxt, taskNameTxt, taskStateTxt, taskCostTxt,
            taskDurationTxt, taskIdTxt;
    JButton savePrjBtn, newPrjBtn, writePrjBtn, createTaskBtn, saveTaskBtn, newTaskBtn, fromTaskToProjBtn;

    JList<Projet> projectLst;
    JList<Task> taskLst;
    DefaultListModel<Projet> projectLstMdl;
    DefaultListModel<Task> tasksLstMdl;
    Set<Projet> projectsSet;
    Set<Task> tasksSet;
    Projet prj;
    Task task;

    public App(ResourceManager resourceManager, ProjectManager projectManager) {

        super("Project Management");
        this.resourceManager = resourceManager;
        this.projectManager = projectManager;
        allTaskcheckBoxes = new ArrayList<>();
        humanResourceLstMdl = new DefaultListModel<>();
        materialResourceLstMdl = new DefaultListModel<>();
        projectLstMdl = new DefaultListModel<>();
        tasksLstMdl = new DefaultListModel<>();
        humanResourceSet = new TreeSet<>();
        materialResourceSet = new TreeSet<>();
        materialResourceSet = new TreeSet<>();
        projectsSet = new TreeSet<>();
        tasksSet = new TreeSet<>();
        readAll();
        mainTabbedPane = new JTabbedPane();

        this.add(mainTabbedPane);

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

        // Material resource Pannel

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
        formTitlePanel.setBounds(5, 10, 350, 35);
        Font font = new Font("Serif", Font.BOLD, 24);
        projectTitleLbl = new JLabel("CREATE TASK");
        projectTitleLbl.setFont(font);
        formTitlePanel.add(projectTitleLbl);
        projectFormPanel.add(formTitlePanel);

        projectPanel = new JPanel();
        createProjectPanel = new JPanel();
        createProjectPanel.setLayout(null);
        createProjectPanel.setBounds(10, 55, 250, 170);

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

                }
            }
        });
        JScrollPane projectScrollPane = new JScrollPane(projectLst);
        projectListPanel.add(projectScrollPane, BorderLayout.CENTER);
        projectFormPanel.add(projectListPanel);

        // create the buttons
        projectActionPanel = new JPanel();
        projectActionPanel.setLayout(null);
        projectActionPanel.setBounds(5, 240, 525, 45);

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
        writePrjBtn = new JButton("WRITE ON DISK");
        writePrjBtn.setBounds(155, 5, 130, 40);
        writePrjBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeOnDisk();
            }
        });
        projectActionPanel.add(writePrjBtn);

        createTaskBtn = new JButton("CREATE TASK >>");
        createTaskBtn.setBounds(385, 5, 130, 40);
        createTaskBtn.setEnabled(false);

        projectActionPanel.add(createTaskBtn);

        projectFormPanel.add(projectActionPanel);

        mainPanel.add(projectFormPanel, "Projects");
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
            }
        });
        // begin create Task Page

        createTaskPanel = new JPanel();
        createTaskPanel.setLayout(null);
        createTaskPanel.setVisible(true);
        taskTitlePanel = new JPanel();
        taskTitlePanel.setLayout(null);
        taskTitlePanel.setBounds(5, 10, 350, 35);
        taskTitleLbl = new JLabel("CREATE TASK");
        taskTitleLbl.setFont(font);
        taskTitleLbl.setBounds(150, 5, 200, 35);
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
        taskStateTxt.setBounds(75, 50, 100, 25);
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
                if (taskLst.getSelectedIndex() >= 0) {
                    task = taskLst.getSelectedValue();
                    taskNameTxt.setText(task.name);
                    taskCostTxt.setText(String.valueOf(task.cost));
                    taskDurationTxt.setText(String.valueOf(task.duration));
                    taskIdTxt.setText(String.valueOf(task.id));
                    taskStateTxt.setText(task.state);

                }
            }
        });
        JScrollPane taskScrollPane = new JScrollPane(taskLst);
        taskListPanel.add(taskScrollPane, BorderLayout.CENTER);
        createTaskPanel.add(taskListPanel);

        // create actions buttons for tasks
        taskActionPanel = new JPanel();
        taskActionPanel.setLayout(null);
        taskActionPanel.setBounds(5, 240, 525, 45);

        fromTaskToProjBtn = new JButton("GO BACK");
        fromTaskToProjBtn.setBounds(5, 5, 100, 40);
        fromTaskToProjBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAllTaskPage();
                cardLayout.show(mainPanel, "Projects");
            }
        });
        taskActionPanel.add(fromTaskToProjBtn);
        newTaskBtn = new JButton("NEW");
        newTaskBtn.setBounds(110, 5, 70, 40);
        newTaskBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearTasksFields();
            }
        });
        taskActionPanel.add(newTaskBtn);

        saveTaskBtn = new JButton("SAVE");
        saveTaskBtn.setBounds(185, 5, 70, 40);
        saveTaskBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = taskNameTxt.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Fill All The Fields", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (taskLst.getSelectedIndex() >= 0) {
                    task = taskLst.getSelectedValue();
                    task.setName(name);
                    clearTasksFields();
                } else {
                    task = new Task(name);
                    if (tasksSet.add(task)) {
                        tasksLstMdl.addElement(task);
                        Projet project = projectLst.getSelectedValue();
                        project.addTask(task);
                        clearTasksFields();

                    }
                }

            }
        });

        taskActionPanel.add(saveTaskBtn);
        createTaskPanel.add(taskActionPanel);

        mainPanel.add(createTaskPanel, "Tasks");

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
        projectLst.clearSelection();
        createTaskBtn.setEnabled(false);
    }

    private void clearTasksFields() {
        taskNameTxt.setText("");
        taskCostTxt.setText("");
        taskDurationTxt.setText("");
        taskStateTxt.setText("");
        taskIdTxt.setText("");
        taskLst.clearSelection();
        // createTaskBtn.setEnabled(false);
    };

    private void clearAllTaskPage() {
        clearProjectFields();
        tasksLstMdl.clear();
    }
}
