import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Filtrage extends JFrame implements Observer {
	JPanel cmbPanel, optionPanel, tablePanel;
	JComboBox<Projet> projectCmb;
	JComboBox<Task> taskCmb;
	JComboBox<Process> processCmb;
	DefaultComboBoxModel<Projet> projetCmbMdl;
	DefaultComboBoxModel<Task> taskCmbMdl;
	DefaultComboBoxModel<Process> processCmbMdl;
	JRadioButton humanRdb, materialRdb;
	ButtonGroup ResourceGroup = new ButtonGroup();
	JTable resourceTbl;
	DefaultTableModel resourceTblMdl;
	ProjectManager projectManager;
	ResourceManager resourceManager;
	private String[] humanUsableCols = {"id", "Employee Name", "Employee Hourly Pay", "Working Hours"};
	private String[] materialUsableCols = {"id", "Material Name", "Material Type", "Unit Cost", "Quantity"};
	private Object[][] resourceDataRow;
	Set<Projet> projectSet;
	public Filtrage(ProjectManager pm, ResourceManager rm) {
		this.projectManager = pm;
		this.projectManager.addObserver(this);
		this.resourceManager = rm;
		this.resourceManager.addObserver(this);
		getContentPane().setLayout(null);
		resourceTblMdl = new DefaultTableModel(resourceDataRow, humanUsableCols);
		projetCmbMdl = new DefaultComboBoxModel<>();
		taskCmbMdl = new DefaultComboBoxModel<>();
		processCmbMdl = new DefaultComboBoxModel<>();
		projectSet = new TreeSet<>();
		cmbPanel = new JPanel();
		cmbPanel.setBounds(10, 11, 634, 41);
		getContentPane().add(cmbPanel);
		cmbPanel.setLayout(null);
		
		JLabel projectLbl = new JLabel("Project:");
		projectLbl.setBounds(10, 11, 56, 19);
		cmbPanel.add(projectLbl);
		
		projectCmb = new JComboBox<>();
		projectCmb.setBounds(59, 9, 120, 22);
		cmbPanel.add(projectCmb);
		
		JLabel taskLbl = new JLabel("Task:");
		taskLbl.setBounds(207, 12, 56, 17);
		cmbPanel.add(taskLbl);
		
	    taskCmb = new JComboBox<>();
		taskCmb.setBounds(273, 9, 120, 22);
		cmbPanel.add(taskCmb);
		
		JLabel ProcessLbl = new JLabel("Process:");
		ProcessLbl.setBounds(438, 12, 56, 17);
		cmbPanel.add(ProcessLbl);
		
	    processCmb = new JComboBox<>();
		processCmb.setBounds(504, 9, 120, 22);
		cmbPanel.add(processCmb);
		
		 optionPanel = new JPanel();
		optionPanel.setBounds(10, 63, 283, 29);
		getContentPane().add(optionPanel);
		optionPanel.setLayout(null);
		
		 humanRdb = new JRadioButton("Human Resource");
		 ResourceGroup.add(humanRdb);
		humanRdb.setBounds(6, 0, 109, 23);
		optionPanel.add(humanRdb);
		
		materialRdb = new JRadioButton("Material Resource");
		ResourceGroup.add(materialRdb);
		materialRdb.setBounds(131, 0, 146, 23);
		optionPanel.add(materialRdb);
		
		tablePanel = new JPanel();
		tablePanel.setBounds(10,100, 634, 255);
		tablePanel.setLayout(new BorderLayout());
		resourceTbl = new JTable(resourceTblMdl);
		JScrollPane scroll = new JScrollPane(resourceTbl);
		tablePanel.add(scroll, BorderLayout.CENTER);
		getContentPane().add(tablePanel);
	}
	
	public void update() {
		projectSet.clear();
		projectSet.addAll(projectManager.myProjects);
		
	}
}
