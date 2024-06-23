import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
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
	private Projet defaultProject = new Projet(" "), projet;
	private Task defaultTask = new Task(" "," "), task;
	private Process defaultProcess = new Process(" ", 0), process;
	boolean updateProject = false, updateTask = false, updateProcess = false;
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
		
		projectCmb = new JComboBox<>(projetCmbMdl);
		projectCmb.setBounds(59, 9, 120, 22);
		projectCmb.setRenderer(new ProjectRenderer());
		projectCmb.addItemListener(new ComboBoxEventHandler());
		cmbPanel.add(projectCmb);
		
		JLabel taskLbl = new JLabel("Task:");
		taskLbl.setBounds(207, 12, 56, 17);
		cmbPanel.add(taskLbl);
		
	    taskCmb = new JComboBox<>(taskCmbMdl);
		taskCmb.setBounds(273, 9, 120, 22);
		taskCmb.setRenderer(new TaskRenderer());
		taskCmb.addItemListener(new ComboBoxEventHandler());
		cmbPanel.add(taskCmb);
		
		JLabel ProcessLbl = new JLabel("Process:");
		ProcessLbl.setBounds(438, 12, 56, 17);
		cmbPanel.add(ProcessLbl);
		
	    processCmb = new JComboBox<>(processCmbMdl);
		processCmb.setBounds(504, 9, 120, 22);
		processCmb.setRenderer(new ProcessRenderer());
		processCmb.addItemListener(new ComboBoxEventHandler());
		cmbPanel.add(processCmb);
		
		 optionPanel = new JPanel();
		optionPanel.setBounds(10, 63, 283, 29);
		getContentPane().add(optionPanel);
		optionPanel.setLayout(null);
		
		 humanRdb = new JRadioButton("Human Resource");
		 humanRdb.setSelected(true);
		 humanRdb.addItemListener(new ItemListener() {
			 public void itemStateChanged(ItemEvent e) {
				 if (humanRdb.isSelected()) {
					 if(processCmb.getSelectedIndex() > 0) {
						 process = (Process) processCmb.getSelectedItem();
						 populateTable(humanUsableCols, process.humanResources);
					 } else if (taskCmb.getSelectedIndex() > 0) {
						 task = (Task) taskCmb.getSelectedItem();
						 populateTable(humanUsableCols,getHumanResourceUsageForTask(task) );
					 } else if (projectCmb.getSelectedIndex() > 0) {
						 projet = (Projet) projectCmb.getSelectedItem();
						 populateTable(humanUsableCols,getHumanResourceUsageForProj(projet) );
					 }
				 }
			 }
		 });
		 ResourceGroup.add(humanRdb);
		humanRdb.setBounds(6, 0, 109, 23);
		optionPanel.add(humanRdb);
		
		materialRdb = new JRadioButton("Material Resource");
		ResourceGroup.add(materialRdb);
		materialRdb.setBounds(131, 0, 146, 23);
		materialRdb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				 if (materialRdb.isSelected()) {
					 if(processCmb.getSelectedIndex() > 0) {
						 process = (Process) processCmb.getSelectedItem();
						 populateTable(materialUsableCols, process.materials);
					 } else if (taskCmb.getSelectedIndex() > 0) {
						 task = (Task) taskCmb.getSelectedItem();
						 populateTable(materialUsableCols,getMaterialResourceUsageForTask(task) );
					 } else if (projectCmb.getSelectedIndex() > 0) {
						 projet = (Projet) projectCmb.getSelectedItem();
						 populateTable(materialUsableCols,getMaterialResourceUsageForProj(projet) );
					 }
				 }
			 }
		});
		optionPanel.add(materialRdb);
		
		tablePanel = new JPanel();
		tablePanel.setBounds(10,100, 634, 255);
		tablePanel.setBorder(new TitledBorder(""));
		tablePanel.setLayout(new BorderLayout());
		resourceTbl = new JTable(resourceTblMdl);
		JScrollPane scroll = new JScrollPane(resourceTbl);
		tablePanel.add(scroll, BorderLayout.CENTER);
		getContentPane().add(tablePanel);
	}
	
	public void update() {
		projectSet.clear();
		projectSet.addAll(projectManager.myProjects);
		populatePage();
	}
	
	private void populatePage() {
		projet = null;
		task = null;
		process = null;
		
		if(projectSet.isEmpty()) {
			resetPage();	
			return;
		}
		if(projectCmb.getSelectedIndex() > 0) {
			projet = (Projet)projectCmb.getSelectedItem();
			
			if (taskCmb.getSelectedIndex() > 0) {
				task = (Task) taskCmb.getSelectedItem();
				
				if(processCmb.getSelectedIndex() > 0) {
					process = (Process) processCmb.getSelectedItem();

				}
			}
		}
		
		changeTableTitle("No Project Selected");
		resetPage();
		
		updateComboBox(projetCmbMdl, projectSet, defaultProject );
		
		if (projet != null && projectSet.contains(projet)) {
			updateProject = true;
			projectCmb.setSelectedItem(projet);
			updateComboBox(taskCmbMdl, projet.tasks, defaultTask);
			taskCmb.setEnabled(true);
			if (task != null && projet.tasks.contains(task)) {
				updateTask = true;
				taskCmbMdl.setSelectedItem(task);
				updateComboBox(processCmbMdl, task.processes, defaultProcess);
				processCmb.setEnabled(true);
				if (process != null && task.processes.contains(process)) {
					updateProcess = true;
					processCmbMdl.setSelectedItem(process);
					
					
					if (humanRdb.isSelected()) {	
						changeTableTitle("Process");
						populateTable(humanUsableCols,process.humanResources );
					} else {
						changeTableTitle("Material Resource Usage");
						populateTable(materialUsableCols, process.materials);
					}
					
				} else {
					
					changeTableTitle("Task Resource");
					if (humanRdb.isSelected()) {
						populateTable(humanUsableCols, getHumanResourceUsageForTask(task));
					} else {
						populateTable(humanUsableCols, getMaterialResourceUsageForTask(task));
					}
					
				}
			} else {
				changeTableTitle("Project Resource");
				if (humanRdb.isSelected()) {
					populateTable(humanUsableCols, getHumanResourceUsageForProj(projet));
				} else {
					populateTable(humanUsableCols, getMaterialResourceUsageForProj(projet));
				}
				
			}
		}
	}
	private<T> void updateComboBox(DefaultComboBoxModel<T> cmbMdl, Set<T> set, T defaultItem) {
		if (cmbMdl.getSize() > 0) {
			cmbMdl.removeAllElements();
		}
		
		cmbMdl.addElement(defaultItem);
		Iterator<T> it = set.iterator();
		while(it.hasNext()) {
			cmbMdl.addElement(it.next());
		}
	}
	private<T> void populateTable(String[] cols, Set<T> data) {
		resourceTblMdl.setColumnIdentifiers(cols);
		resourceTblMdl.setRowCount(0);
		Iterator<T> it = data.iterator();
		while(it.hasNext()) {
			T el = it.next();
			 if (el instanceof HumanResourceUsage) {
				HumanResourceUsage humanRc = (HumanResourceUsage) el;
				resourceTblMdl.addRow(new Object[] {humanRc.id, humanRc.labor.name, humanRc.labor.hourlyRate, humanRc.workingHour});
			} else {
				MaterialUsage materialRc = (MaterialUsage) el;
				resourceTblMdl.addRow(new Object[] {materialRc.id, materialRc.material.name, materialRc.material.materialType, materialRc.material.unitCost, materialRc.qty});
			}
			
		}
	}
	private void resetPage() {
		projetCmbMdl.removeAllElements();
		taskCmbMdl.removeAllElements();
		processCmbMdl.removeAllElements();
		
		taskCmb.setEnabled(false);
		processCmb.setEnabled(false);
		
		resourceTblMdl.setRowCount(0);
		
	}
	
	private void resetTaskAndProcess() {
		taskCmbMdl.removeAllElements();
		taskCmb.setEnabled(false);
		resetProcess();
	}
	
	private void resetProcess() {
		processCmbMdl.removeAllElements();
		processCmb.setEnabled(false);
		
		resourceTblMdl.setRowCount(0);
	}
	private void changeTableTitle(String title) {
        title = title + " Table";
        TitledBorder border = (TitledBorder) tablePanel.getBorder();
        border.setTitle(title);
        tablePanel.repaint();  // Repaint the panel to reflect the new title
    }
	
	public Set<HumanResourceUsage> getHumanResourceUsageForProj(Projet prj) {
	    Iterator<Task> itTasks = prj.tasks.iterator();
	    Set<HumanResourceUsage> resourceSet = new HashSet<>();

	    while (itTasks.hasNext()) {
	        Task task = itTasks.next();
	        Iterator<Process> itProcess = task.processes.iterator();
	        
	        while (itProcess.hasNext()) {
	            Process process = itProcess.next();
	            Iterator<HumanResourceUsage> itResource = process.humanResources.iterator();
	                
	            while (itResource.hasNext()) {
	                resourceSet.add(itResource.next());
	            }
	        }
	    }
	    return resourceSet;
	}
	public Set<MaterialUsage> getMaterialResourceUsageForProj(Projet prj) {
	    Iterator<Task> itTasks = prj.tasks.iterator();
	    Set<MaterialUsage> resourceSet = new HashSet<>();

	    while (itTasks.hasNext()) {
	        Task task = itTasks.next();
	        Iterator<Process> itProcess = task.processes.iterator();
	        
	        while (itProcess.hasNext()) {
	            Process process = itProcess.next();
	            Iterator<MaterialUsage> itResource = process.materials.iterator();
	                
	            while (itResource.hasNext()) {
	                resourceSet.add(itResource.next());
	            }
	        }
	    }
	    return resourceSet;
	}
	
	public Set<HumanResourceUsage> getHumanResourceUsageForTask(Task task) {
	    Set<HumanResourceUsage> resourceSet = new HashSet<>();

	    Iterator<Process> itProcess = task.processes.iterator();
	        
	    while (itProcess.hasNext()) {
	        Process process = itProcess.next();
	        Iterator<HumanResourceUsage> itResource = process.humanResources.iterator();
	                
	        while (itResource.hasNext()) {
	            resourceSet.add(itResource.next());
	        }
	    }
	    
	    return resourceSet;
	}
	
	public Set<MaterialUsage> getMaterialResourceUsageForTask(Task task) {
	    Set<MaterialUsage> resourceSet = new HashSet<>();

	    Iterator<Process> itProcess = task.processes.iterator();
	        
	    while (itProcess.hasNext()) {
	        Process process = itProcess.next();
	        Iterator<MaterialUsage> itResource = process.materials.iterator();
	                
	        while (itResource.hasNext()) {
	            resourceSet.add(itResource.next());
	        }
	    }
	    
	    return resourceSet;
	}
	
	class ComboBoxEventHandler implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			
			if (e.getStateChange() == ItemEvent.SELECTED) {
				
			if (e.getSource() == projectCmb) {
				
				if (updateProject) {
					updateProject = false;
					return;
				}
				
					resetTaskAndProcess();					
				if (projectCmb.getSelectedIndex() > 0) {
					
					taskCmb.setEnabled(true);
					projet = (Projet) projectCmb.getSelectedItem();
					updateComboBox(taskCmbMdl, projet.tasks, defaultTask);
					if (humanRdb.isSelected()) {
						populateTable(humanUsableCols, getHumanResourceUsageForProj(projet));	
					} else {
						populateTable(materialUsableCols, getMaterialResourceUsageForProj(projet));
					}
					
					
					changeTableTitle("Project Resource");
				} else {
					taskCmb.setEnabled(false);
				}
			} else if (e.getSource() == taskCmb) {
				if (updateTask) {
					updateTask = false;
					return;
				}
				resetProcess();
				if (taskCmb.getSelectedIndex() > 0) {
					processCmb.setEnabled(true);
					task = (Task) taskCmb.getSelectedItem();
					updateComboBox(processCmbMdl, task.processes, defaultProcess);
					if (humanRdb.isSelected()) {
						populateTable(humanUsableCols, getHumanResourceUsageForTask(task));
					} else {
						populateTable(materialUsableCols, getMaterialResourceUsageForTask(task));
					}
					
					changeTableTitle("Task Resource");
					
				} else {
					processCmb.setEnabled(false);
					projet = (Projet)projectCmb.getSelectedItem();
					if (humanRdb.isSelected()) {
						populateTable(humanUsableCols, getHumanResourceUsageForProj(projet));	
					} else {
						populateTable(materialUsableCols, getMaterialResourceUsageForProj(projet));
					}
					
					
					changeTableTitle("Project Resource");
				    
				}
				}
			 else if (e.getSource() == processCmb) {
				if (updateProcess) {
					
					updateProcess = false;
					return;
				}
				if (processCmb.getSelectedIndex() > 0) {
					process = (Process) processCmb.getSelectedItem();
					if (humanRdb.isSelected()) {
					populateTable(humanUsableCols,process.humanResources );	
					} else {
						populateTable(materialUsableCols,process.materials);	
					}
					
					
					
				} else  {
					processCmb.setEnabled(true);
					task = (Task) taskCmb.getSelectedItem();
					
					if (humanRdb.isSelected()) {
						populateTable(humanUsableCols, getHumanResourceUsageForTask(task));
					} else {
						populateTable(materialUsableCols, getMaterialResourceUsageForTask(task));
					}
					
					changeTableTitle("Task Resource");
				}
			 }
			}
		}
		}
}
