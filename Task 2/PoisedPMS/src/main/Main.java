package main;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	/** Initialize the scanner function */
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		// Use a try statement to login and work on database
		try {
			/**
			 * Connect to the poisedPMS database, via the jdbc:mysql:channel on localhost
			 * (this PC) Use username "otheruser", password "swordfish".
			 */
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false",
					"otheruser", "swordfish");

			// Create a direct line to the database for running our queries
			Statement statement = connection.createStatement();

			/**
			 * Run a do While loop for all the options to call methods to action on the
			 * database
			 */
			// Declare true for do loop to run
			boolean continueDo = true;

			do {
				System.out.println("\nWhat would you like to do? \n" + "1. Enter a New Project\n"
						+ "2. Enter a New Architect\n" + "3. Enter a New Contractor\n" + "4. Enter a New Customer\n"
						+ "5. Update information\n" + "6. Delete information\n" + "7. Search information\n"
						+ "0. Exit");

				// Scan the answer given
				String choice = scan.nextLine();

				// Switch statement to execute method according to answer given
				switch (choice) {

				case "0":
					// End the while loop
					continueDo = false;
					break;
				case "1":
					Project.enterNewProject(statement);
					break;
				case "2":
					String architectName = null;
					Architect.enterNewArchitect(statement, architectName);
					break;
				case "3":
					String contractorName = null;
					Contractor.enterNewContractor(statement, contractorName);
					break;
				case "4":
					String customerName = null;
					Customer.enterNewCustomer(statement, customerName);
					break;
				case "5":
					updateData(statement);
					break;
				case "6":
					deleteInformation(statement);
					break;
				case "7":
					searchInformation(statement);
					break;

				// Default if user entered a wrong character
				default:
					System.out.println("\nEnter a correct number:");
					break;
				}

			}
			// Continue the "do" function N or n is not entered.
			while (continueDo == true);

			// Close up our connections
			statement.close();
			connection.close();

		} catch (SQLException e) {
			// We only want to catch a SQLException - anything else is off-limits for now.
			e.printStackTrace();
		}

		System.out.println("Goodbye!");

		// Close scanner
		scan.close();

	}

	/**
	 * Method to update information
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void updateData(Statement statement) throws SQLException {

		// Ask user which table they want to update
		System.out.println(
				"\nWhich data do you want to update? \n1. Project by Project Number\n2. Project by Project Name \n3. Architect \n4. Contractor \n5. Customer \n0. Back to Main Screen");
		String itemUpdate = scan.nextLine();

		/**
		 * Switch statement according to answer given Use the method to update the
		 * selected table to update
		 */
		switch (itemUpdate) {

		case "0":
			// Back to Main Screen
			break;
		case "1":
			Project.updateProjectNumber(statement);
			break;
		case "2":
			Project.updateProjectName(statement);
			break;
		case "3":
			Architect.updateArchitect(statement);
			break;
		case "4":
			Contractor.updateContractor(statement);
			break;
		case "5":
			Customer.updateCustomer(statement);
			break;

		// Default if user entered a wrong character
		default:
			System.out.println("\nData was not ammended\n");
			break;
		}

	}

	/**
	 * Method to delete information
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void deleteInformation(Statement statement) throws SQLException {

		System.out.println(
				"\nFrom which database do you want to delete? \n1. Projects \n2. Architect \n3. Contractor \n4. Customer \n0. Back to Main Screen");
		String itemUpdate = scan.nextLine();

		// Switch statement according to answer given
		switch (itemUpdate) {

		case "0":
			// Back to Main Screen
			break;

		case "1":
			// Delete a Project
			Project.deleteProject(statement);
			break;

		case "2":
			// Delete a Architect
			Architect.deleteArchitect(statement);
			break;

		case "3":
			// Delete a Contractor
			Contractor.deleteContractor(statement);
			break;

		case "4":
			// Delete a Customer
			Customer.deleteCustomer(statement);
			break;

		// Default if user entered a wrong character
		default:
			System.out.println("\nNo Information was Deleted\n");
			break;
		}

	}

	/**
	 * Method to Search for information
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void searchInformation(Statement statement) throws SQLException {
		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {

				/** Ask user which item they want to search for */
				System.out.println(
						"\nSearching: \n1. Project by Project Number \n2. Project by Project Name \n3. All Projects \n4. All Detailed linked Projects"
								+ "\n5. Architect by Name \n6. Display all Architects \n7. Contractor by Name \n8. Display all Contractors "
								+ "\n9. Customer by Name \n10.Display all Customers \n11.All Finished Projects \n12.All UnFinished Projects "
								+ "\n13.Past Due date & Not finalised \n0. Back to Main Screen");
				String itemUpdate = scan.nextLine();

				/**
				 * Switch statement according to answer given Search according to selected
				 * option
				 * 
				 * @param itemUpdate
				 */
				switch (itemUpdate) {

				case "0":
					// Back to Main Screen
					break;

				case "1":
					// Search by project Number
					System.out.print("Enter Project Number:");
					int projectNumber = scan.nextInt();
					scan.nextLine();
					ResultSet projectByNumber = statement.executeQuery(
							"SELECT Project.ProjectNumber, Project.ProjectName, Project.BuildingType, Project.Address, Project.ErfNumber, Project.TotalFee"
									+ ", Project.AmountPaid, Project.Deadline, Project.CompletionDate, Project.Finalised, Project.Architect, Project.Contractor, Project.Customer, Project.Engineer, Project.ProjectManager"
									+ ", Architect.Name, Architect.Telephone, Architect.Email, Architect.Address"
									+ ", Contractor.Name, Contractor.Telephone, Contractor.Email, Contractor.Address"
									+ ", Customer.Name, Customer.Telephone, Customer.Email, Customer.Address"
									+ " FROM Project" + " INNER JOIN Architect ON Project.Architect = Architect.Name"
									+ " INNER JOIN Contractor ON Project.Contractor = Contractor.Name"
									+ " INNER JOIN Customer ON Project.Customer = Customer.Name"
									+ " WHERE ProjectNumber =" + projectNumber);
					if (projectByNumber.next() == true) {
						ResultSet showProjectByNumber = statement.executeQuery(
								"SELECT Project.ProjectNumber, Project.ProjectName, Project.BuildingType, Project.Address, Project.ErfNumber, Project.TotalFee"
										+ ", Project.AmountPaid, Project.Deadline, Project.CompletionDate, Project.Finalised, Project.Architect, Project.Contractor, Project.Customer, Project.Engineer, Project.ProjectManager"
										+ ", Architect.Name, Architect.Telephone, Architect.Email, Architect.Address"
										+ ", Contractor.Name, Contractor.Telephone, Contractor.Email, Contractor.Address"
										+ ", Customer.Name, Customer.Telephone, Customer.Email, Customer.Address"
										+ " FROM Project"
										+ " INNER JOIN Architect ON Project.Architect = Architect.Name"
										+ " INNER JOIN Contractor ON Project.Contractor = Contractor.Name"
										+ " INNER JOIN Customer ON Project.Customer = Customer.Name"
										+ " WHERE ProjectNumber =" + projectNumber);
						displayFullProjectResults(showProjectByNumber);
					} else {
						ResultSet showProjectByNumberElse = statement
								.executeQuery("SELECT * FROM Project WHERE ProjectNumber =" + projectNumber);
						displayProjectResults(showProjectByNumberElse);
					}

					break;

				case "2":
					// Search by project Name
					System.out.print("Enter Project Name:");
					String projectName = scan.nextLine();
					ResultSet projectByName = statement.executeQuery(
							"SELECT Project.ProjectNumber, Project.ProjectName, Project.BuildingType, Project.Address, Project.ErfNumber, Project.TotalFee"
									+ ", Project.AmountPaid, Project.Deadline, Project.CompletionDate, Project.Finalised, Project.Architect, Project.Contractor, Project.Customer, Project.Engineer, Project.ProjectManager"
									+ ", Architect.Name, Architect.Telephone, Architect.Email, Architect.Address"
									+ ", Contractor.Name, Contractor.Telephone, Contractor.Email, Contractor.Address"
									+ ", Customer.Name, Customer.Telephone, Customer.Email, Customer.Address"
									+ " FROM Project" + " INNER JOIN Architect ON Project.Architect = Architect.Name"
									+ " INNER JOIN Contractor ON Project.Contractor = Contractor.Name"
									+ " INNER JOIN Customer ON Project.Customer = Customer.Name"
									+ " WHERE ProjectName = '" + projectName + "'");
					if (projectByName.next() == true) {
						ResultSet showProjectByName = statement.executeQuery(
								"SELECT Project.ProjectNumber, Project.ProjectName, Project.BuildingType, Project.Address, Project.ErfNumber, Project.TotalFee"
										+ ", Project.AmountPaid, Project.Deadline, Project.CompletionDate, Project.Finalised, Project.Architect, Project.Contractor, Project.Customer, Project.Engineer, Project.ProjectManager"
										+ ", Architect.Name, Architect.Telephone, Architect.Email, Architect.Address"
										+ ", Contractor.Name, Contractor.Telephone, Contractor.Email, Contractor.Address"
										+ ", Customer.Name, Customer.Telephone, Customer.Email, Customer.Address"
										+ " FROM Project"
										+ " INNER JOIN Architect ON Project.Architect = Architect.Name"
										+ " INNER JOIN Contractor ON Project.Contractor = Contractor.Name"
										+ " INNER JOIN Customer ON Project.Customer = Customer.Name"
										+ " WHERE ProjectName = '" + projectName + "'");
						displayFullProjectResults(showProjectByName);
					} else {
						ResultSet showProjectByNameElse = statement
								.executeQuery("SELECT * FROM Project WHERE ProjectName = '" + projectName + "'");
						displayProjectResults(showProjectByNameElse);
					}

					break;

				case "3":
					// Confirm if user wants to see all the Projects
					System.out.println("\nAre you sure you want to show all the Projects? \n1. YES \n2. NO");
					int confirmSeeProjects = scan.nextInt();
					scan.nextLine();
					if (confirmSeeProjects == 1) {
						ResultSet showAllProject = statement.executeQuery("SELECT * FROM Project");
						displayProjectResults(showAllProject);
					}

					break;

				case "4":
					// Confirm if user wants to see all the Projects
					System.out.println(
							"\nAre you sure you want to show all the Detailed linked Projects? \n1. YES \n2. NO");
					int confirmSeeProjectsDetailed = scan.nextInt();
					scan.nextLine();
					if (confirmSeeProjectsDetailed == 1) {
						ResultSet showAllProject = statement.executeQuery(
								"SELECT Project.ProjectNumber, Project.ProjectName, Project.BuildingType, Project.Address, Project.ErfNumber, Project.TotalFee"
										+ ", Project.AmountPaid, Project.Deadline, Project.CompletionDate, Project.Finalised, Project.Architect, Project.Contractor, Project.Customer, Project.Engineer, Project.ProjectManager"
										+ ", Architect.Name, Architect.Telephone, Architect.Email, Architect.Address"
										+ ", Contractor.Name, Contractor.Telephone, Contractor.Email, Contractor.Address"
										+ ", Customer.Name, Customer.Telephone, Customer.Email, Customer.Address"
										+ " FROM Project"
										+ " INNER JOIN Architect ON Project.Architect = Architect.Name"
										+ " INNER JOIN Contractor ON Project.Contractor = Contractor.Name"
										+ " INNER JOIN Customer ON Project.Customer = Customer.Name");
						displayFullProjectResults(showAllProject);
					}
					break;

				case "5":
					// User enters Architect Name
					System.out.print("\nPlease enter Architect Name:");
					String architectName = scan.nextLine();
					ResultSet resultShowArchitect = statement
							.executeQuery("SELECT * FROM Architect WHERE Name = '" + architectName + "'");
					displayContactDetailsResults(resultShowArchitect);
					break;

				case "6":
					// Confirm if user wants to see all the Architects
					System.out.println("\nAre you sure you want to show all the Architects? \n1. YES \n2. NO");
					int confirmSeeArchitect = scan.nextInt();
					scan.nextLine();
					if (confirmSeeArchitect == 1) {
						ResultSet showAllArchitect = statement.executeQuery("SELECT * FROM Architect");
						displayContactDetailsResults(showAllArchitect);
					}
					break;

				case "7":
					// User enters Contractor Name
					System.out.print("\nPlease enter Contractor Name:");
					String contractorName = scan.nextLine();
					ResultSet resultShowContractor = statement
							.executeQuery("SELECT * FROM Contractor WHERE Name = '" + contractorName + "'");
					displayContactDetailsResults(resultShowContractor);
					break;

				case "8":
					// Confirm if user wants to see all the Contractors
					System.out.println("\nAre you sure you want to show all the Contractors? \n1. YES \n2. NO");
					int confirmSeeContractor = scan.nextInt();
					scan.nextLine();
					if (confirmSeeContractor == 1) {
						ResultSet showAllContractor = statement.executeQuery("SELECT * FROM Contractor");
						displayContactDetailsResults(showAllContractor);
					}
					break;

				case "9":
					// User enters Customer Name
					System.out.print("\nPlease enter Customer Name:");
					String CustomerName = scan.nextLine();
					ResultSet resultShowCustomer = statement
							.executeQuery("SELECT * FROM Customer WHERE Name = '" + CustomerName + "'");
					displayContactDetailsResults(resultShowCustomer);
					break;

				case "10":
					// Confirm if user wants to see all the Customers
					System.out.println("\nAre you sure you want to show all the Customers? \n1. YES \n2. NO");
					int confirmSeeCustomer = scan.nextInt();
					scan.nextLine();
					if (confirmSeeCustomer == 1) {
						ResultSet showAllCustomer = statement.executeQuery("SELECT * FROM Customer");
						displayContactDetailsResults(showAllCustomer);
					}
					break;

				case "11":
					// Show all Finalized projects
					boolean projectFinalised = true;
					ResultSet showProjectByFinalised = statement
							.executeQuery("SELECT * FROM Project WHERE Finalised =" + projectFinalised);
					displayProjectResults(showProjectByFinalised);
					break;

				case "12":
					// Show all Not finalized projects
					boolean projectNotFinalised = false;
					ResultSet showProjectByNotFinalised = statement
							.executeQuery("SELECT * FROM Project WHERE Finalised =" + projectNotFinalised);
					displayProjectResults(showProjectByNotFinalised);
					break;

				case "13":
					// All project that is past due date and not finalized
					ResultSet showProjectPastDue = statement
							.executeQuery("SELECT * FROM Project WHERE Deadline < DATE(NOW()) AND Finalised = FALSE");
					displayProjectResults(showProjectPastDue);
					break;

				// Default if user entered a wrong character
				default:
					System.out.println("\nInvalid number input\n");
					break;
				}
				// STOP THE WHILE LOOP
				continueInput = false;
			} catch (InputMismatchException ex) {
				System.out.println("\nIncorrect format input entered, try again.");
				scan.nextLine();
			}
		} while (continueInput);

	}

	/**
	 * Method to display the results of selected items in Project database
	 * 
	 * @param results
	 * @throws SQLException
	 * @return void display Project information
	 */
	public static void displayProjectResults(ResultSet results) throws SQLException {

		// Display Fields
		System.out.println(
				"ProjectNumber, ProjectName, BuildingType, Address, ErfNumber, TotalFee, AmountPaid, Deadline, CompletionDate, Finalised, Architect, Contractor, Customer, Engineer, Project Manager");
		// Display selected data rows
		while (results.next()) {
			System.out.println(results.getInt("ProjectNumber") + ", " + results.getString("ProjectName") + ", "
					+ results.getString("BuildingType") + ", " + results.getString("Address") + ", "
					+ results.getInt("ErfNumber") + ", " + results.getDouble("TotalFee") + ", "
					+ results.getDouble("AmountPaid") + ", " + results.getDate("Deadline") + ", "
					+ results.getDate("CompletionDate") + ", " + results.getBoolean("Finalised") + ", "
					+ results.getString("Architect") + ", " + results.getString("Contractor") + ", "
					+ results.getString("Customer") + ", " + results.getString("Engineer") + ", "
					+ results.getString("ProjectManager"));
		}
	}

	/**
	 * Method to display linked results of selected items in project, architect,
	 * contractor and customer database
	 * 
	 * @param results
	 * @throws SQLException
	 * @return void display Project information
	 */
	public static void displayFullProjectResults(ResultSet results) throws SQLException {

		System.out.println("All results:");
		// Display selected data rows
		while (results.next()) {
			// Display Fields
			System.out.println(
					"\nProjectNumber, ProjectName, BuildingType, Address, ErfNumber, TotalFee, AmountPaid, Deadline, CompletionDate, Finalised, Engineer, Project Manager");
			System.out.println(results.getInt("ProjectNumber") + ", " + results.getString("ProjectName") + ", "
					+ results.getString("BuildingType") + ", " + results.getString("Address") + ", "
					+ results.getInt("ErfNumber") + ", " + results.getDouble("TotalFee") + ", "
					+ results.getDouble("AmountPaid") + ", " + results.getDate("Deadline") + ", "
					+ results.getDate("CompletionDate") + ", " + results.getBoolean("Finalised") + ", "
					+ results.getString("Engineer") + ", " + results.getString("ProjectManager"));
			// Architect detail
			System.out.println("- Architect: Telephone, Email, Address");
			System.out.println(results.getString("Architect.Name") + ", " + results.getInt("Architect.Telephone") + ", "
					+ results.getString("Architect.Email") + ", " + results.getString("Architect.Address"));
			// Contractor detail
			System.out.println("- Contractor: Telephone, Email, Address");
			System.out.println(results.getString("Contractor.Name") + ", " + results.getInt("Contractor.Telephone")
					+ ", " + results.getString("Contractor.Email") + ", " + results.getString("Contractor.Address"));
			// Customer detail
			System.out.println("- Customer: Telephone, Email, Address");
			System.out.println(results.getString("Customer.Name") + ", " + results.getInt("Customer.Telephone") + ", "
					+ results.getString("Customer.Email") + ", " + results.getString("Customer.Address"));
		}
	}

	/**
	 * Method to display the results of Architect, Contractor or Customer items in
	 * database
	 * 
	 * @param results
	 * @throws SQLException
	 * @return void display contact information
	 */
	public static void displayContactDetailsResults(ResultSet results) throws SQLException {

		// Display Fields
		System.out.println("Name, Telephone Number, Email, Address");
		// Display selected data rows
		while (results.next()) {
			System.out.println(results.getString("Name") + ", " + results.getInt("Telephone") + ", "
					+ results.getString("Email") + ", " + results.getString("Address"));
		}
	}

}
