
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
import javax.swing.JButton;

public class View extends JFrame implements MyObserver{
	private ProjectManager projectManager;
	private ResourceManager resourceManager;
	private JScrollPane projectTableScrollPane, resourceTableScrollPane ;
	private JTabbedPane tabbedPane;
	private JPanel projectPanel, resourcePanel,optionsProjPanel;
	private JPanel resourceOptionPanel, resourceTablePanel;
	private JRadioButton humanResourceRdb, materialResourceRdb; 
	private JTable resourceTable;
	private DefaultTableModel  resourceTableMdl;
	private final ButtonGroup resourceOptionBtnGrp = new ButtonGroup();
	private String[] humanResourceCols = {"id","Name", "Specialite", "Job", "Hourly Rate"};
	private String[] materialResourceCols = {"id", "Name", "Material Type", "Description", "Unit Cost"};
	private Object[][] ResourceDataRows;
	
	private Set<Projet> projectSet;
	private Set<HumanResource> humanResourceSet;
	private Set<Material> materialResourceSet;
	public View(ProjectManager projectManager, ResourceManager resourceManager) {
		this.projectManager = projectManager;
		this.resourceManager = resourceManager;
		projectManager.addObserver(this);
		resourceManager.addObserver(this);
		projectSet = new TreeSet<>();
		humanResourceSet = new TreeSet<>();
		materialResourceSet = new TreeSet<>();
		
		
		tabbedPane = new JTabbedPane();
		
		resourceTableMdl = new DefaultTableModel(ResourceDataRows,humanResourceCols );
		
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
		humanResourceRdb.setBounds(6, 7, 115, 23);
		resourceOptionPanel.add(humanResourceRdb);
		
		materialResourceRdb = new JRadioButton("Material Resource");
		resourceOptionBtnGrp.add(materialResourceRdb);
		materialResourceRdb.setBounds(123, 7, 115, 23);
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
		resourceTablePanel.setBounds(20, 58, 415, 176);
		resourceTablePanel.setBorder(new TitledBorder("Reources Table"));
		resourceTable = new JTable(resourceTableMdl) {
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
	 // Assuming the button column is at index 2
		
		resourceTableScrollPane = new JScrollPane(resourceTable);
		
		
		resourceTablePanel.add(resourceTableScrollPane, BorderLayout.CENTER);
		resourcePanel.add(resourceTablePanel);
		
		
		
		
		projectPanel = new JPanel();
		projectPanel.setLayout(null);
		optionsProjPanel = new JPanel();
		optionsProjPanel.setBounds(10, 11, 425, 47);
		projectPanel.add(optionsProjPanel);
		
		//setting up the frame
		tabbedPane.addTab("Resource View", resourcePanel);
		
		
		
		
		tabbedPane.addTab("Project View", projectPanel);
		getContentPane().add(tabbedPane);
		
	}
	public void update() {
		projectSet.clear();
		projectSet.addAll(projectManager.myProjects);
		
		humanResourceSet.clear();
		humanResourceSet.addAll(resourceManager.employeeManager);
		
		materialResourceSet.clear();
		materialResourceSet.addAll(resourceManager.materialManager);
		
		populateResourceTable();
		
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
}

 
