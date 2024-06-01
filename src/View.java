
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractCellEditor;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class View extends JFrame implements MyObserver{
	private ProjectManager projectManager;
	private ResourceManager resourceManager;
	private JScrollPane projectTableScrollPane, resourceTableScrollPane ;
	private JTabbedPane tabbedPane;
	private JPanel projectPanel, resourcePanel,optionsProjPanel;
	private JPanel resourceOptionPanel, resourceTablePanel,usableOptionPanel;
	private JRadioButton humanResourceRdb, materialResourceRdb; 
	private JTable resourceTable, projectTable;
	private DefaultTableModel  resourceTableMdl, projectTableMdl;
	private JComboBox<Projet> projectCmb;
	private JComboBox<Task> taskCmb;
	private JComboBox<Process> processCmb;
//	private JComboBox<ResourceUsage> resourceCmb;
	private DefaultComboBoxModel<Projet> projetCmbMdl;
	private DefaultComboBoxModel<Task> taskCmbMdl;
	private DefaultComboBoxModel<Process> processCmbMdl;
//	private DefaultComboBoxModel<ResourceUsage> resourceCmbMdl;
	private final ButtonGroup resourceOptionBtnGrp = new ButtonGroup();
	private String[] humanResourceCols = {"id","Name", "Specialite", "Job", "Hourly Rate"};
	private String[] materialResourceCols = {"id", "Name", "Material Type", "Description", "Unit Cost"};
	private String[] projectAndProcessCols = {"id", "Name", "Cost", "Duration", "State"};
	private String[] taskCols = {"id", "Name", "Cost", "Duration", "Type", "State"};
	private String[] humanUsableCols = {"id", "Employee Name", "Employee Hourly Pay", "Working Hours"};
	private String[] materialUsableCols = {"id", "Material Name", "Material Type", "Unit Cost", "Quantity"};
	private JRadioButton humanUsableRdb, materialUsableRdb;
	private Object[][] ResourceDataRows, projectDataRows;
	private Projet projet;
	private Task task;
	private Process process;
	private ResourceUsage resource;
	private Set<Projet> projectSet;
	private Set<HumanResource> humanResourceSet;
	private Set<Material> materialResourceSet;
	private final ButtonGroup resourceUsableBtnGroup = new ButtonGroup();
	private JPanel projectTablePanel;
	private boolean updateProject, updateTask,updateProcess;
	private Projet defaultProject = new Projet(" ");
	private Task defaultTask = new Task(" "," ");
	private Process defaultProcess = new Process(" ", 0);
	public View(ProjectManager projectManager, ResourceManager resourceManager) {
		this.projectManager = projectManager;
		this.resourceManager = resourceManager;
		projectManager.addObserver(this);
		resourceManager.addObserver(this);
		projectSet = new TreeSet<>();
		humanResourceSet = new TreeSet<>();
		materialResourceSet = new TreeSet<>();
		projetCmbMdl = new DefaultComboBoxModel<>();
		taskCmbMdl = new DefaultComboBoxModel<>();
		processCmbMdl = new DefaultComboBoxModel<>();
//		resourceCmbMdl = new DefaultComboBoxModel<>();
		
		updateProject = false;
		updateTask = false;
		updateProcess = false;
		
		tabbedPane = new JTabbedPane();
		
		resourceTableMdl = new DefaultTableModel(ResourceDataRows,humanResourceCols );
		projectTableMdl = new DefaultTableModel(projectDataRows, projectAndProcessCols);
		resourcePanel =new JPanel();
		resourcePanel.setLayout(null);
		
		resourceOptionPanel = new JPanel();
		resourceOptionPanel.setBounds(10, 11, 425, 36);
		resourcePanel.add(resourceOptionPanel);
		resourceOptionPanel.setLayout(null);
		
		humanResourceRdb = new JRadioButton("Human Resource");
		humanResourceRdb.setSelected(true);
		humanResourceRdb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (humanResourceRdb.isSelected()) {
					resourceTableMdl.setColumnIdentifiers(humanResourceCols);
					populateResourceTable();
				}
			}
		});
		resourceOptionBtnGrp.add(humanResourceRdb);
		humanResourceRdb.setBounds(6, 7, 131, 23);
		resourceOptionPanel.add(humanResourceRdb);
		
		materialResourceRdb = new JRadioButton("Material Resource");
		resourceOptionBtnGrp.add(materialResourceRdb);
		materialResourceRdb.setBounds(150, 7, 131, 23);
		materialResourceRdb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (materialResourceRdb.isSelected()) {
					resourceTableMdl.setColumnIdentifiers(materialResourceCols);
					populateResourceTable();
				}
			}
		});
		resourceOptionPanel.add(materialResourceRdb);
		
		
		
		resourceTablePanel = new JPanel();
		resourceTablePanel.setLayout(new BorderLayout());
		resourceTablePanel.setBounds(41, 58, 550, 251);
		resourceTablePanel.setBorder(new TitledBorder("Reources Table"));
		resourceTable = new JTable(resourceTableMdl) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        
        resourceTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent e) {
        		if (!e.getValueIsAdjusting() && !resourceTable.getSelectionModel().isSelectionEmpty()) {
        			int selectedRow = resourceTable.getSelectedRow();
        			
        			if (humanResourceRdb.isSelected()) {
        				HumanResource human = null;
        				Iterator<HumanResource> itHuman = humanResourceSet.iterator();
        				while(itHuman.hasNext()) {
        					HumanResource humanIt = itHuman.next();
        					if (humanIt.id == Integer.parseInt(String.valueOf(resourceTable.getValueAt(selectedRow, 0)))) {
        						human = humanIt;
        					}
        				}
        				String msg = "";
        				Iterator<String> iterator = human.taskAllowed.iterator();

        		        // Iterate over the map entries using the iterator
        		        while (iterator.hasNext()) {
        		            msg += iterator.next() + "\n";
        		        }
        		        JOptionPane.showMessageDialog(null, msg);
        		        resourceTable.clearSelection();
        			} else {
        				String msg = "";
        				Material material = null;
        				Iterator<Material> itMaterial = materialResourceSet.iterator();
        				while(itMaterial.hasNext()) {
        					Material materialIt = itMaterial.next();
        					if (materialIt.id == Integer.parseInt(String.valueOf(resourceTable.getValueAt(selectedRow, 0)))) {
        						material = materialIt;
        					}
        				}
        				Iterator<String> iterator = material.taskAllowed.iterator();
        				while(iterator.hasNext()) {
        					msg += iterator.next() + "\n";
        				}
        				
        				JOptionPane.showMessageDialog(null, msg,"Tasks Allowed",JOptionPane.INFORMATION_MESSAGE);
        		        resourceTable.clearSelection();
        			}
        			
        		}
        	}
        });
        resourceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	 
		
		resourceTableScrollPane = new JScrollPane(resourceTable);
		
		
		resourceTablePanel.add(resourceTableScrollPane, BorderLayout.CENTER);
		resourcePanel.add(resourceTablePanel);
		
		
		
		
		projectPanel = new JPanel();
		projectPanel.setLayout(null);
		optionsProjPanel = new JPanel();
		optionsProjPanel.setBounds(0, 11, 636, 36);
		projectPanel.add(optionsProjPanel);
		optionsProjPanel.setLayout(null);
		
		JLabel projectLbl = new JLabel("Tasks Of");
		projectLbl.setBounds(9, 11, 57, 14);
		optionsProjPanel.add(projectLbl);
		
		projectCmb = new JComboBox<>(projetCmbMdl);
		projectCmb.setRenderer(new ProjectRenderer());
		projectCmb.addItemListener(new ComboBoxEventHandler() );
		projectCmb.setBounds(75, 7, 105, 22);
		optionsProjPanel.add(projectCmb);
		
		JLabel tasksLbl = new JLabel("Processes Of");
		tasksLbl.setBounds(203, 11, 79, 14);
		optionsProjPanel.add(tasksLbl);
		
		 taskCmb = new JComboBox<>(taskCmbMdl);
		 taskCmb.setRenderer(new TaskRenderer());
		 taskCmb.addItemListener(new ComboBoxEventHandler());
		 taskCmb.setEnabled(false);
		taskCmb.setBounds(292, 7, 105, 22);
		optionsProjPanel.add(taskCmb);
		
		JLabel processLbl = new JLabel("Resources Of");
		processLbl.setBounds(424, 11, 87, 14);
		optionsProjPanel.add(processLbl);
		
		processCmb = new JComboBox<>(processCmbMdl);
		processCmb.setRenderer(new ProcessRenderer());
		processCmb.addItemListener(new ComboBoxEventHandler());
		processCmb.setEnabled(false);
		processCmb.setBounds(521, 7, 105, 22);
		optionsProjPanel.add(processCmb);
		

		
		//setting up the frame
		tabbedPane.addTab("Resource View", resourcePanel);
		
		
		
		
		tabbedPane.addTab("Project View", projectPanel);
		
		 usableOptionPanel = new JPanel();
		usableOptionPanel.setBounds(0, 51, 447, 27);
		usableOptionPanel.setVisible(false);
		projectPanel.add(usableOptionPanel);
		usableOptionPanel.setLayout(null);
		
		 humanUsableRdb = new JRadioButton("Human");
		resourceUsableBtnGroup.add(humanUsableRdb);
		humanUsableRdb.setBounds(6, 0, 73, 23);
		humanUsableRdb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (humanUsableRdb.isSelected()) {
					process = (Process) processCmb.getSelectedItem();
					populateTable(humanUsableCols, process.humanResources);
				}
				
			}
		});
		usableOptionPanel.add(humanUsableRdb);
		
		materialUsableRdb = new JRadioButton("Material");
		resourceUsableBtnGroup.add(materialUsableRdb);
		materialUsableRdb.setBounds(81, 0, 80, 23);
		materialUsableRdb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (materialUsableRdb.isSelected()) {
					process = (Process) processCmb.getSelectedItem();
					populateTable(materialUsableCols, process.materials);
				}
				
				
			}
		});
		usableOptionPanel.add(materialUsableRdb);
		
		projectTablePanel = new JPanel();
		projectTablePanel.setLayout(new BorderLayout());
		projectTablePanel.setBorder(new TitledBorder("Projects Table"));
		
		projectTablePanel.setBounds(25, 89, 580, 229);
		projectTable = new JTable(projectTableMdl) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        
		projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		projectTableScrollPane = new JScrollPane(projectTable);
		projectTablePanel.add(projectTableScrollPane, BorderLayout.CENTER);
		projectPanel.add(projectTablePanel);
		getContentPane().add(tabbedPane);
		
	}
	public void update() {
		
		
		humanResourceSet.clear();
		humanResourceSet.addAll(resourceManager.employeeManager);
		
		materialResourceSet.clear();
		materialResourceSet.addAll(resourceManager.materialManager);
		projectSet.clear();
		projectSet.addAll(projectManager.myProjects);
		populateResourceTable();
		populateProjectPanel();
		
	}
	
	private void populateResourceTable() {
		resourceTableMdl.setRowCount(0);
		if(humanResourceRdb.isSelected()) {
			
			Iterator<HumanResource> itHuman = humanResourceSet.iterator();
			while(itHuman.hasNext()) {
				HumanResource human = itHuman.next();
				resourceTableMdl.addRow(new Object[] {human.id,human.name, human.speciality, human.job, human.hourlyRate});
			}
		} else {
			Iterator<Material> itMaterial = materialResourceSet.iterator();
			while(itMaterial.hasNext()) {
				Material material = itMaterial.next();
				resourceTableMdl.addRow(new Object[] {material.id, material.name, material.materialType, material.description, material.unitCost});
			}
		}
	}
	
	private void populateProjectPanel() {
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
		
		resetPage();
		updateComboBox(projetCmbMdl, projectSet, defaultProject );
		
//		if (projet == null || !projectSet.contains(projet) ) {
//			populateTable(projectAndProcessCols, projectSet);
//		}
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
					
					usableOptionPanel.setVisible(true);
					humanUsableRdb.setSelected(true);
				} else {
					populateTable(projectAndProcessCols, task.processes);
				}
			} else {
				populateTable(taskCols, projet.tasks);
			}
		}
		
		
		
		

		
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
					populateTable(taskCols, projet.tasks);
				} else {
					taskCmb.setEnabled(false);
					populateTable(projectAndProcessCols, projectSet);
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
					populateTable(projectAndProcessCols, task.processes);
				} else {
					processCmb.setEnabled(false);
					projet = (Projet)projectCmb.getSelectedItem();
					populateTable(taskCols, projet.tasks);}
				}
			 else if (e.getSource() == processCmb) {
				if (updateProcess) {
					
					updateProcess = false;
					return;
				}
				if (processCmb.getSelectedIndex() > 0) {
					usableOptionPanel.setVisible(true);
					humanUsableRdb.setSelected(true);
					
				} else  {
					usableOptionPanel.setVisible(false);
					resourceUsableBtnGroup.clearSelection();
					processCmb.setEnabled(true);
					task = (Task) taskCmb.getSelectedItem();
					
					populateTable(projectAndProcessCols, task.processes);
				}
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
	
	
	
	private void resetPage() {
		projetCmbMdl.removeAllElements();
		taskCmbMdl.removeAllElements();
		processCmbMdl.removeAllElements();
		
		taskCmb.setEnabled(false);
		processCmb.setEnabled(false);
		
		resourceUsableBtnGroup.clearSelection();
		usableOptionPanel.setVisible(false);
		projectTableMdl.setRowCount(0);
		projectTableMdl.setColumnIdentifiers(projectAndProcessCols);
		
	}
	
	private void resetTaskAndProcess() {
		taskCmbMdl.removeAllElements();
		taskCmb.setEnabled(false);
		resetProcess();
	}
	
	private void resetProcess() {
		processCmbMdl.removeAllElements();
		processCmb.setEnabled(false);
		resourceUsableBtnGroup.clearSelection();
		usableOptionPanel.setVisible(false);
		projectTableMdl.setRowCount(0);
	}
	
	private<T> void populateTable(String[] cols, Set<T> data) {
		projectTableMdl.setColumnIdentifiers(cols);
		projectTableMdl.setRowCount(0);
		Iterator<T> it = data.iterator();
		while(it.hasNext()) {
			T el = it.next();
			if (el instanceof Projet) {
				Projet p = (Projet)el;
				projectTableMdl.addRow(new Object[] {p.projId,p.name, p.cost, p.duration, p.state });	
			}
			
			else if (el instanceof Task) {
				Task t = (Task)el;
				projectTableMdl.addRow(new Object[] {t.id,t.name,t.cost,t.duration, t.type, t.state});
			} else if (el instanceof Process) {
				Process p = (Process)el;
				projectTableMdl.addRow(new Object[] {p.id,p.name, p.cost, p.duration, p.state } );
			} else if (el instanceof HumanResourceUsage) {
				HumanResourceUsage humanRc = (HumanResourceUsage) el;
				projectTableMdl.addRow(new Object[] {humanRc.id, humanRc.labor.name, humanRc.labor.hourlyRate, humanRc.workingHour});
			} else {
				MaterialUsage materialRc = (MaterialUsage) el;
				projectTableMdl.addRow(new Object[] {materialRc.id, materialRc.material.name, materialRc.material.type, materialRc.material.unitCost, materialRc.qty});
			}
			
		}
	}
}

	
 
