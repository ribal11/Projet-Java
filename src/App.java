import javax.swing.JFrame;

public class App {
	public static void main(String[] args) {
		ProjectManager projectManager = new ProjectManager();
		ResourceManager resourceManager = new ResourceManager();
		View view = new View(projectManager, resourceManager);
		view.setSize(670, 430);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Filtrage filtrage = new Filtrage(projectManager, resourceManager);
        filtrage.setSize(670, 430);
        filtrage.setVisible(true);
        filtrage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Controller controller = new Controller(resourceManager, projectManager);
		controller.setSize(600, 430);
		controller.setVisible(true);
		controller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
}
