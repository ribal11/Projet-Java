import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
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
    int ResourceId = 0;
    File f;
    InputStream is;
    OutputStream os;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ResourceManager resourceManager;
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
            resourceListPanel, allowedTaskPanel, actionsResourcePanel, projectFormPanel;

    Set<HumanResource> humanResourceSet;
    Set<Material> materialResourceSet;
    List<JCheckBox> allTaskcheckBoxes;
    // end Resources form
    // ###############################################################################################################################

    public App(ResourceManager resourceManager) {

        super("Project Management");
        this.resourceManager = resourceManager;
        allTaskcheckBoxes = new ArrayList<>();
        humanResourceLstMdl = new DefaultListModel<>();
        materialResourceLstMdl = new DefaultListModel<>();
        humanResourceSet = new TreeSet<>();
        materialResourceSet = new TreeSet<>();
        materialResourceSet = new TreeSet<>();
        readAll();
        mainTabbedPane = new JTabbedPane();

        this.add(mainTabbedPane);

        // Initialize the resourcePanel and its components
        resourcePanel = new JPanel();
        resourcePanel.setLayout(null);
        projectFormPanel = new JPanel();
        projectFormPanel.setLayout(null);

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
                writeResources();
            }
        });
        actionsResourcePanel.add(writeButton);
        resourcePanel.add(actionsResourcePanel);

        // Initialize the secondaryTabbedPane and add the resourcePanel to it
        secondaryTabbedPane = new JTabbedPane();
        secondaryTabbedPane.addTab("Resources", resourcePanel);
        secondaryTabbedPane.addTab("Project Form", projectFormPanel);

        // Add the secondaryTabbedPane to the mainTabbedPane
        mainTabbedPane.addTab("Form Tab", secondaryTabbedPane);

    }

    public static void main(String[] args) throws Exception {
        ResourceManager resourceManager = new ResourceManager();
        App frame = new App(resourceManager);
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
            if (resource.type == "Human") {
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

    private void readAll() {
        readHumanResource();
        readMaterialResources();
        Resource.next = ResourceId + 1;
    }

    private void writeResources() {
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
    }

}
