package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;

public class Project {

	/**
	 * Method to insert a Project
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void enterNewProject(Statement statement) throws SQLException {

		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {

				// Get variables to add to Project
				System.out.print("Please enter Project Number:");
				int projectNumber = Main.scan.nextInt();
				Main.scan.nextLine();

				// Check if Project Number of the Project exist in the Project database, if not,
				// capture into database
				ResultSet ProjectNumberExist = statement
						.executeQuery("SELECT ProjectNumber FROM Project WHERE ProjectNumber = " + projectNumber);
				if (ProjectNumberExist.next() == false) {

					System.out.print("Please enter Project Name:");
					String projectName = Main.scan.nextLine();

					System.out.print("Please enter Building Type:");
					String buildingType = Main.scan.nextLine();

					System.out.print("Please enter Address of the Project:");
					String projectAddress = Main.scan.nextLine();

					System.out.print("Please enter ERF Number:");
					int erfNumber = Main.scan.nextInt();
					Main.scan.nextLine();

					System.out.print("Please enter Total Project Fee:");
					double totalFee = Main.scan.nextDouble();

					System.out.print("Please enter Total amount paid:");
					double amountPaid = Main.scan.nextDouble();

					System.out.print("Please enter Deadline date (YYYMMDD):");
					int deadlineDate = Main.scan.nextInt();
					Main.scan.nextLine();

					System.out.print("Please enter Engineer Name for this Project:");
					String engineer = Main.scan.nextLine();

					System.out.print("Please enter Project Manager Name for this Project:");
					String projectManager = Main.scan.nextLine();

					// New project will not be finalized, so leave as false Finalized.
					boolean finalised = false;

					System.out.print("Please enter Architect Name (Name & Surname):");
					String architect = Main.scan.nextLine();

					/**
					 * Check if the Name of the Architect exist in the Architect database, if not,
					 * capture into database
					 */
					ResultSet architectExist = statement
							.executeQuery("SELECT Name FROM Architect WHERE Name = '" + architect + "'");
					if (architectExist.next() == false) {
						Architect.enterNewArchitect(statement, architect);
					}

					System.out.print("Please enter Contractor Name (Name & Surname):");
					String contractor = Main.scan.nextLine();

					/**
					 * Check if the Name of the Contractor exist in the Contractor database, if not,
					 * capture into database
					 */
					ResultSet contractorExist = statement
							.executeQuery("SELECT Name FROM Contractor WHERE Name = '" + contractor + "'");
					if (contractorExist.next() == false) {
						Contractor.enterNewContractor(statement, contractor);
					}

					// If projectName is empty, it should take the building type and Surname of the
					// Customer
					String customer;
					if (projectName == "" || projectName == " ") {

						System.out.print("Please enter ONLY Customer SURNAME:");
						String customerSurname = Main.scan.nextLine();

						System.out.print("Please enter ONLY Customer NAME:");
						String customerName = Main.scan.nextLine();

						customer = customerName + " " + customerSurname;
						projectName = buildingType + " " + customerSurname;
					} else {
						System.out.print("Please enter Customer Name (Name & Surname):");
						customer = Main.scan.nextLine();
					}

					/**
					 * Check if the Name of the Customer exist in the Customer database, if not,
					 * capture into database
					 */
					ResultSet customerExist = statement
							.executeQuery("SELECT Name FROM Customer WHERE Name = '" + customer + "'");
					if (customerExist.next() == false) {
						Customer.enterNewCustomer(statement, customer);
					}

					// INSERT INTO Project
					statement.executeUpdate(
							"INSERT INTO Project (ProjectNumber, ProjectName, BuildingType, Address, ErfNumber, TotalFee, AmountPaid, Deadline, Finalised, Architect, Contractor, Customer, Engineer, ProjectManager) VALUES"
									+ "(" + projectNumber + ",'" + projectName + "','" + buildingType + "','"
									+ projectAddress + "'," + erfNumber + "," + totalFee + "," + amountPaid + ","
									+ deadlineDate + "," + finalised + ",'" + architect + "','" + contractor + "','"
									+ customer + "','" + engineer + "','" + projectManager + "')");

				} else {
					System.out.println("\n- Project Number already exist");
				}

				// STOP THE WHILE LOOP
				continueInput = false;
			} catch (InputMismatchException ex) {
				System.out.println("\nIncorrect format input entered, try again.");
				Main.scan.nextLine();
			}
		} while (continueInput);

	}

	/**
	 * Method to delete a project
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void deleteProject(Statement statement) throws SQLException {

		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {

				// Ask if user knows which Project they want to delete
				System.out.println("Do you know which project you want to Delete by Project Number? \n1. YES \n2. NO");
				int userKnowsId = Main.scan.nextInt();
				Main.scan.nextLine();

				// If No, display all projects basic information
				if (userKnowsId != 1) {
					ResultSet showAllProject = statement.executeQuery("SELECT * FROM Project");
					Main.displayProjectResults(showAllProject);
				}

				// User enters project number
				System.out.print("\nPlease enter Project Number:");
				int projectNumber = Main.scan.nextInt();
				Main.scan.nextLine();

				ResultSet results = statement
						.executeQuery("SELECT * FROM Project WHERE ProjectNumber =" + projectNumber);

				// If project number exits, delete it
				if (results.next() == true) {
					// Display project that is being deleted
					// If user made a mistake information is temporarily still available in console
					System.out.println("\nThe project is deleted:");
					ResultSet resultShow = statement
							.executeQuery("SELECT * FROM Project WHERE ProjectNumber =" + projectNumber);
					Main.displayProjectResults(resultShow);

					// Delete the project in database
					statement.executeUpdate("DELETE FROM Project WHERE ProjectNumber =" + projectNumber);

				} else {
					System.out.println("\nProject number not found");
				}
				// STOP THE WHILE LOOP
				continueInput = false;
			} catch (InputMismatchException ex) {
				System.out.println("\nIncorrect format input entered, try again.");
				Main.scan.nextLine();
			}
		} while (continueInput);

	}

	/**
	 * Method to update by Project Name
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void updateProjectName(Statement statement) throws SQLException {
		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {

				// Ask if user knows which Project Name they want to amend
				System.out.println("Do you know which project you want to update by Project Name? \n1. YES \n2. NO");
				int userKnowsId = Main.scan.nextInt();
				Main.scan.nextLine();

				// If No, display all projects basic information
				if (userKnowsId != 1) {
					ResultSet showAllProject = statement.executeQuery("SELECT * FROM Project");
					Main.displayProjectResults(showAllProject);
				}

				// User enters project Name
				System.out.print("\nPlease enter Project Name:");
				String projectName = Main.scan.nextLine();

				ResultSet results = statement
						.executeQuery("SELECT * FROM Project WHERE ProjectName = '" + projectName + "'");

				// If project number exits, can make amendments
				if (results.next() == true) {

					ResultSet resultShow = statement
							.executeQuery("SELECT * FROM Project WHERE ProjectName = '" + projectName + "'");
					Main.displayProjectResults(resultShow);

					System.out.println(
							"\nWhich item do you want to update? \n1. ProjectNumber \n2. BuildingType \n3. Address \n4. ERF Number \n5. TotalFee \n6. AmountPaid \n7. Deadline \n8. CompletionDate \n9. Architect \n10. Contractor \n11. Customer \n12. Engineer \n13. Project Manager \n14. Finalised \n0. Back to Main Screen");
					String itemUpdate = Main.scan.nextLine();

					// Switch statement according to answer given
					switch (itemUpdate) {

					case "0":
						// Back to Main Screen
						break;

					case "1":
						// UPDATE the Project Number:
						System.out.print("Please enter new Project Number:");
						int projectNumber = Main.scan.nextInt();
						Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET ProjectNumber =" + projectNumber
								+ " WHERE ProjectName = '" + projectName + "'");
						break;

					case "2":
						// UPDATE the Building Type:
						System.out.print("Please enter new Building Type:");
						String buildingType = Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET BuildingType = '" + buildingType
								+ "' WHERE ProjectName = '" + projectName + "'");
						break;

					case "3":
						// UPDATE the Address:
						System.out.print("Please enter new project Address:");
						String address = Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET Address = '" + address + "' WHERE ProjectName = '"
								+ projectName + "'");
						break;

					case "4":
						// UPDATE the ERF Number:
						System.out.print("Please enter new ERF Number:");
						int erfNumber = Main.scan.nextInt();
						Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET ErfNumber = " + erfNumber + " WHERE ProjectName = '"
								+ projectName + "'");
						break;

					case "5":
						// UPDATE the Total Fee:
						System.out.print("Please enter new Total Fee:");
						double totalFee = Main.scan.nextDouble();
						statement.executeUpdate("UPDATE Project SET TotalFee = " + totalFee + " WHERE ProjectName = '"
								+ projectName + "'");
						break;

					case "6":
						// UPDATE the Amount Paid:
						System.out.print("Please enter new Total Amount Paid:");
						double amountPaid = Main.scan.nextDouble();
						statement.executeUpdate("UPDATE Project SET AmountPaid = " + amountPaid
								+ " WHERE ProjectName = '" + projectName + "'");
						break;

					case "7":
						// UPDATE the Deadline:
						System.out.print("Please enter the new Deadline date (YYYMMDD):");
						int deadlineDate = Main.scan.nextInt();
						Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET Deadline = " + deadlineDate
								+ " WHERE ProjectName = '" + projectName + "'");
						break;

					case "8":
						// UPDATE the completion date:
						System.out.print("Please enter the new CompletionDate date (YYYMMDD):");
						int completionDate = Main.scan.nextInt();
						Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET CompletionDate = " + completionDate
								+ " WHERE ProjectName = '" + projectName + "'");

						// UPDATE finalized to True if project is completed
						boolean finalised = true;
						statement.executeUpdate("UPDATE Project SET Finalised = " + finalised + " WHERE ProjectName = '"
								+ projectName + "'");
						break;

					case "9":
						// UPDATE the architect:
						System.out.print("Please enter new Architect Name (Name & Surname):");
						String architect = Main.scan.nextLine();

						// Check if the Name of the Architect exist in the Architect database, if not,
						// capture into database
						ResultSet architectExist = statement
								.executeQuery("SELECT Name FROM Architect WHERE Name = '" + architect + "'");
						if (architectExist.next() == false) {
							Architect.enterNewArchitect(statement, architect);
						}

						statement.executeUpdate("UPDATE Project SET Architect = '" + architect
								+ "' WHERE ProjectName = '" + projectName + "'");
						break;

					case "10":
						// UPDATE the Contractor:
						System.out.print("Please enter new Contractor Name (Name & Surname):");
						String contractor = Main.scan.nextLine();

						// Check if the Name of the Contractor exist in the Contractor database, if not,
						// capture into database
						ResultSet contractorExist = statement
								.executeQuery("SELECT Name FROM Contractor WHERE Name = '" + contractor + "'");
						if (contractorExist.next() == false) {
							Contractor.enterNewContractor(statement, contractor);
						}

						statement.executeUpdate("UPDATE Project SET Contractor = '" + contractor
								+ "' WHERE ProjectName = '" + projectName + "'");
						break;

					case "11":
						// UPDATE the Customer:
						System.out.print("Please enter new Customer Name (Name & Surname):");
						String customer = Main.scan.nextLine();

						// Check if the Name of the Customer exist in the Customer database, if not,
						// capture into database
						ResultSet customerExist = statement
								.executeQuery("SELECT Name FROM Customer WHERE Name = '" + customer + "'");
						if (customerExist.next() == false) {
							Customer.enterNewCustomer(statement, customer);
						}

						statement.executeUpdate("UPDATE Project SET Customer = '" + customer + "' WHERE ProjectName = '"
								+ projectName + "'");
						break;

						
					case "12":
						// UPDATE the Engineer:
						System.out.print("Please enter Name of the new project Engineer:");
						String engineer = Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET Engineer = '" + engineer + "' WHERE ProjectName = '"
								+ projectName + "'");
						break;
						
					case "13":
						// UPDATE the Project Manager:
						System.out.print("Please enter Name of the new Project Manager:");
						String projectManager = Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET ProjectManager = '" + projectManager + "' WHERE ProjectName = '"
								+ projectName + "'");
						break;
						
					case "14":
						// Update Finalized:
						System.out.println("Update Project to Finalised: \n1. YES \n2. NO");
						int finalisedAnswer = Main.scan.nextInt();
						Main.scan.nextLine();

						// Based on answer, update finalized for the project
						boolean finased;
						if (finalisedAnswer == 1) {
							finased = true;
							statement.executeUpdate("UPDATE Project SET Finalised = " + finased
									+ " WHERE ProjectName = '" + projectName + "'");
						} else if (finalisedAnswer == 2) {
							finased = false;
							statement.executeUpdate("UPDATE Project SET Finalised = " + finased
									+ " WHERE ProjectName = '" + projectName + "'");
						} else {
							System.out.println("Finalised was not updated");
						}

						break;

					// Default if user entered a wrong character
					default:
						System.out.println("\nProject was not ammended\n");
						break;
					}
				} else {
					System.out.println("\n- Selected project Name was not found  in the Database");
				}
				// STOP THE WHILE LOOP
				continueInput = false;
			} catch (InputMismatchException ex) {
				System.out.println("\nIncorrect format input entered, try again.");
				Main.scan.nextLine();
			}
		} while (continueInput);

	}

	/**
	 * Method to update information by Project Number
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void updateProjectNumber(Statement statement) throws SQLException {
		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {

				// Ask if user knows which Project they want to amend
				System.out.println("Do you know which project you want to update by Project Number? \n1. YES \n2. NO");
				int userKnowsId = Main.scan.nextInt();
				Main.scan.nextLine();

				// If No, launch search method
				if (userKnowsId != 1) {
					ResultSet showAllProject = statement.executeQuery("SELECT * FROM Project");
					Main.displayProjectResults(showAllProject);
				}

				// User enters project number
				System.out.print("\nPlease enter Project Number:");
				int projectNumber = Main.scan.nextInt();
				Main.scan.nextLine();

				ResultSet results = statement
						.executeQuery("SELECT * FROM Project WHERE ProjectNumber =" + projectNumber);

				// If project number exits, can make amendments
				if (results.next() == true) {

					ResultSet resultShow = statement
							.executeQuery("SELECT * FROM Project WHERE ProjectNumber =" + projectNumber);
					Main.displayProjectResults(resultShow);

					System.out.println(
							"\nWhich item do you want to update? \n1. ProjectName \n2. BuildingType \n3. Address \n4. ERF Number \n5. TotalFee \n6. AmountPaid \n7. Deadline \n8. CompletionDate \n9. Architect \n10. Contractor \n11. Customer \n12. Engineer \n13. Project Manager \n14. Finalised \n0. Back to Main Screen");
					String itemUpdate = Main.scan.nextLine();

					// Switch statement according to answer given
					switch (itemUpdate) {

					case "0":
						// Back to Main Screen
						break;

					case "1":
						// UPDATE the Project Name:
						System.out.print("Please enter new Project Name:");
						String projectName = Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET ProjectName = '" + projectName
								+ "' WHERE ProjectNumber = " + projectNumber);
						break;

					case "2":
						// UPDATE the Building Type:
						System.out.print("Please enter new Building Type:");
						String buildingType = Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET BuildingType = '" + buildingType
								+ "' WHERE ProjectNumber = " + projectNumber);
						break;

					case "3":
						// UPDATE the Address:
						System.out.print("Please enter new project Address:");
						String address = Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET Address = '" + address + "' WHERE ProjectNumber = "
								+ projectNumber);
						break;

					case "4":
						// UPDATE the ERF Number:
						System.out.print("Please enter new ERF Number:");
						int erfNumber = Main.scan.nextInt();
						Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET ErfNumber = " + erfNumber
								+ " WHERE ProjectNumber = " + projectNumber);
						break;

					case "5":
						// UPDATE the Total Fee:
						System.out.print("Please enter new Total Fee:");
						double totalFee = Main.scan.nextDouble();
						statement.executeUpdate("UPDATE Project SET TotalFee = " + totalFee + " WHERE ProjectNumber = "
								+ projectNumber);
						break;

					case "6":
						// UPDATE the Amount Paid:
						System.out.print("Please enter new Total Amount Paid:");
						double amountPaid = Main.scan.nextDouble();
						statement.executeUpdate("UPDATE Project SET AmountPaid = " + amountPaid
								+ " WHERE ProjectNumber = " + projectNumber);
						break;

					case "7":
						// UPDATE the Deadline:
						System.out.print("Please enter the new Deadline date (YYYMMDD):");
						int deadlineDate = Main.scan.nextInt();
						Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET Deadline = " + deadlineDate
								+ " WHERE ProjectNumber = " + projectNumber);
						break;

					case "8":
						// UPDATE the completion date:
						System.out.print("Please enter the new CompletionDate date (YYYMMDD):");
						int completionDate = Main.scan.nextInt();
						Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET CompletionDate = " + completionDate
								+ " WHERE ProjectNumber = " + projectNumber);

						// UPDATE finalized to True if project is completed
						boolean finalised = true;
						statement.executeUpdate("UPDATE Project SET Finalised = " + finalised
								+ " WHERE ProjectNumber = " + projectNumber);
						break;

					case "9":
						// UPDATE the architect:
						System.out.print("Please enter new Architect Name (Name & Surname):");
						String architect = Main.scan.nextLine();

						// Check if the Name of the Architect exist in the Architect database, if not,
						// capture into database
						ResultSet architectExist = statement
								.executeQuery("SELECT Name FROM Architect WHERE Name = '" + architect + "'");
						if (architectExist.next() == false) {
							Architect.enterNewArchitect(statement, architect);
						}

						statement.executeUpdate("UPDATE Project SET Architect = '" + architect
								+ "' WHERE ProjectNumber = " + projectNumber);
						break;

					case "10":
						// UPDATE the Contractor:
						System.out.print("Please enter new Contractor Name (Name & Surname):");
						String contractor = Main.scan.nextLine();

						// Check if the Name of the Contractor exist in the Contractor database, if not,
						// capture into database
						ResultSet contractorExist = statement
								.executeQuery("SELECT Name FROM Contractor WHERE Name = '" + contractor + "'");
						if (contractorExist.next() == false) {
							Contractor.enterNewContractor(statement, contractor);
						}

						statement.executeUpdate("UPDATE Project SET Contractor = '" + contractor
								+ "' WHERE ProjectNumber = " + projectNumber);
						break;

					case "11":
						// UPDATE the Customer:
						System.out.print("Please enter new Customer Name (Name & Surname):");
						String customer = Main.scan.nextLine();

						// Check if the Name of the Customer exist in the Customer database, if not,
						// capture into database
						ResultSet customerExist = statement
								.executeQuery("SELECT Name FROM Customer WHERE Name = '" + customer + "'");
						if (customerExist.next() == false) {
							Customer.enterNewCustomer(statement, customer);
						}

						statement.executeUpdate("UPDATE Project SET Customer = '" + customer
								+ "' WHERE ProjectNumber = " + projectNumber);
						break;
						
					case "12":
						// UPDATE the Engineer:
						System.out.print("Please enter new Engineer:");
						String engineer = Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET Engineer = '" + engineer + "' WHERE ProjectNumber = "
								+ projectNumber);
						break;
						
					case "13":
						// UPDATE the Address:
						System.out.print("Please enter new Project Manager:");
						String projectManager = Main.scan.nextLine();
						statement.executeUpdate("UPDATE Project SET ProjectManager = '" + projectManager + "' WHERE ProjectNumber = "
								+ projectNumber);
						break;

					case "14":
						// Update Finalized:
						System.out.println("Update Project to Finalised: \n1. YES \n2. NO");
						int finalisedAnswer = Main.scan.nextInt();
						Main.scan.nextLine();

						// Based on answer, update finalized for the project
						boolean finased;
						if (finalisedAnswer == 1) {
							finased = true;
							statement.executeUpdate("UPDATE Project SET Finalised = " + finased
									+ " WHERE ProjectNumber = " + projectNumber);
						} else if (finalisedAnswer == 2) {
							finased = false;
							statement.executeUpdate("UPDATE Project SET Finalised = " + finased
									+ " WHERE ProjectNumber = " + projectNumber);
						} else {
							System.out.println("Finalised was not updated");
						}

						break;

					// Default if user entered a wrong character
					default:
						System.out.println("\nProject was not ammended\n");
						break;
					}
				} else {
					System.out.println("\n- Selected project number was not found  in the Database");
				}
				// STOP THE WHILE LOOP
				continueInput = false;
			} catch (InputMismatchException ex) {
				System.out.println("\nIncorrect format input entered, try again.");
				Main.scan.nextLine();
			}
		} while (continueInput);

	}

}
