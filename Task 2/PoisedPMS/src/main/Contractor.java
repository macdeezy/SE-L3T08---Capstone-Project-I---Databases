package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;

public class Contractor {

	/**
	 * Method to insert a Contractor
	 * 
	 * @param statement
	 * @param contractorName
	 * @throws SQLException
	 */
	public static void enterNewContractor(Statement statement, String contractorName) throws SQLException {
		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {
				System.out.println("\nAdding Contractor information:\n");

				// Get variables to add to Architect
				if (contractorName == null) {
					System.out.print("Please enter Contractor Name (Name & Surname):");
					contractorName = Main.scan.nextLine();
				}

				// Check if Name exist in the Contractor database, if not, capture into database
				ResultSet contractorNameExist = statement
						.executeQuery("SELECT * FROM Contractor WHERE Name = '" + contractorName + "'");
				if (contractorNameExist.next() == false) {

					System.out.print("Please enter Contractor Telephone number:");
					int telephone = Main.scan.nextInt();
					Main.scan.nextLine();

					System.out.print("Please enter Contractor Email:");
					String email = Main.scan.nextLine();

					System.out.print("Please enter the Address of the Contractor:");
					String address = Main.scan.nextLine();
					System.out.println();

					// INSERT INTO Contractor
					statement.executeUpdate("INSERT INTO Contractor VALUES('" + contractorName + "'," + telephone + ",'"
							+ email + "','" + address + "')");
				} else {
					System.out.println("Contractor name already exists");
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
	 * Method to delete a contractor
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void deleteContractor(Statement statement) throws SQLException {
		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {
				// Ask if user knows which Contractor they want to delete
				System.out.println("Do you know which Contractor you want to Delete? \n1. YES \n2. NO");
				int userKnowsId = Main.scan.nextInt();
				Main.scan.nextLine();

				// If No, display all contractors
				if (userKnowsId != 1) {
					ResultSet showAllContractor = statement.executeQuery("SELECT * FROM Contractor");
					Main.displayContactDetailsResults(showAllContractor);
				}

				// User enters Architect
				System.out.print("\nPlease enter Contractor Name & Surname to be deleted:");
				String name = Main.scan.nextLine();

				ResultSet results = statement.executeQuery("SELECT * FROM Contractor WHERE Name = '" + name + "'");

				// If Contractor exits, delete it
				if (results.next() == true) {
					// Display Contractor that is being deleted
					// If user made a mistake information is temporarily still available in console
					System.out.println("\nThe Contractor is deleted:");
					ResultSet resultShow = statement
							.executeQuery("SELECT * FROM Contractor WHERE Name = '" + name + "'");
					Main.displayContactDetailsResults(resultShow);

					// Delete the Contractor in database
					statement.executeUpdate("DELETE FROM Contractor WHERE Name ='" + name + "'");

					// Search if a project contains this Contractor
					ResultSet resultsProject = statement
							.executeQuery("SELECT * FROM Project WHERE Contractor = '" + name + "'");
					if (resultsProject.next() == true) {

						/**
						 * Display the Contractor in the project and ask if user wants to delete the
						 * project also
						 */
						System.out.println("\nThe below Project was found with this Contractor Name:");
						ResultSet resultsProjectDislay = statement
								.executeQuery("SELECT * FROM Project WHERE Contractor = '" + name + "'");
						Main.displayProjectResults(resultsProjectDislay);

						System.out.println(
								"\nDo you want to delete the above Projects for this Contractor?: \n1. Yes \n2. No");
						int itemUpdate = Main.scan.nextInt();
						Main.scan.nextLine();

						if (itemUpdate == 1) {
							// Delete the Project in database
							statement.executeUpdate("DELETE FROM Project WHERE Contractor ='" + name + "'");
						}
					}

				} else {
					System.out.println("\nContractor was not found");
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
	 * Method to update Contractor
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void updateContractor(Statement statement) throws SQLException {
		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {

				// Ask if user knows which Contractor they want to amend
				System.out.println("Do you know which Contractor you want to update by Name? \n1. YES \n2. NO");
				int userKnows = Main.scan.nextInt();
				Main.scan.nextLine();

				// If No, display all contractors
				if (userKnows != 1) {
					ResultSet showAllContractor = statement.executeQuery("SELECT * FROM Contractor");
					Main.displayContactDetailsResults(showAllContractor);
				}

				// User enters Contractor Name
				System.out.print("\nPlease enter Contractor Name:");
				String contractorName = Main.scan.nextLine();

				ResultSet results = statement
						.executeQuery("SELECT * FROM Contractor WHERE Name = '" + contractorName + "'");

				// If Name exits, can make amendments
				if (results.next() == true) {

					ResultSet resultShow = statement
							.executeQuery("SELECT * FROM Contractor WHERE Name = '" + contractorName + "'");
					Main.displayContactDetailsResults(resultShow);

					System.out.println(
							"\nWhich item do you want to update? \n1. Telephone \n2. Email \n3. Address \n0. Back to Main Screen");
					String itemUpdate = Main.scan.nextLine();

					// Switch statement according to answer given
					switch (itemUpdate) {

					case "0":
						// Back to Main Screen
						break;

					case "1":
						// UPDATE the Telephone Number:
						System.out.print("Please enter new Telephone number for the Contractor:");
						int telephoneNumber = Main.scan.nextInt();
						Main.scan.nextLine();
						statement.executeUpdate("UPDATE Contractor SET Telephone =" + telephoneNumber
								+ " WHERE Name = '" + contractorName + "'");
						break;

					case "2":
						// Update Email
						System.out.print("Please enter Contractor Email:");
						String email = Main.scan.nextLine();
						statement.executeUpdate(
								"UPDATE Contractor SET Email = '" + email + "' WHERE Name = '" + contractorName + "'");
						break;

					case "3":
						// Update Address
						System.out.print("Please enter the Address of the Contractor:");
						String address = Main.scan.nextLine();
						statement.executeUpdate("UPDATE Contractor SET Address = '" + address + "' WHERE Name = '"
								+ contractorName + "'");
						break;

					// Default if user entered a wrong character
					default:
						System.out.println("\nContractor was not ammended\n");
						break;

					}
				} else {
					System.out.println("\nContractor was not found\n");
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
