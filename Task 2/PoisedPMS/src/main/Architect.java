package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;

public class Architect {

	/**
	 * Method to insert an Architect
	 * 
	 * @param statement
	 * @param architectName
	 * @throws SQLException
	 */
	public static void enterNewArchitect(Statement statement, String architectName) throws SQLException {

		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {
				System.out.println("\nAdding Architect information:\n");

				// Get variables to add to Architect
				if (architectName == null) {
					System.out.print("Please enter Achitect Name (Name & Surname):");
					architectName = Main.scan.nextLine();
				}

				// Check if Name exist in the Architect database, if not, capture into database
				ResultSet architectNameExist = statement
						.executeQuery("SELECT * FROM Architect WHERE Name = '" + architectName + "'");
				if (architectNameExist.next() == false) {

					System.out.print("Please enter Achitect Telephone number:");
					int telephone = Main.scan.nextInt();
					Main.scan.nextLine();

					System.out.print("Please enter Achitect Email:");
					String email = Main.scan.nextLine();

					System.out.print("Please enter the Address of the Achitect:");
					String address = Main.scan.nextLine();
					System.out.println();

					// INSERT INTO Architect
					statement.executeUpdate("INSERT INTO Architect VALUES('" + architectName + "'," + telephone + ",'"
							+ email + "','" + address + "')");
				} else {
					System.out.println("Architect name already exists");
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
	 * Method to delete a Architect
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void deleteArchitect(Statement statement) throws SQLException {

		// Ask if user knows which Architect they want to delete
		System.out.println("Do you know which Architect you want to Delete? \n1. YES \n2. NO");
		int userKnowsId = Main.scan.nextInt();
		Main.scan.nextLine();

		// If No, display all the architect
		if (userKnowsId != 1) {
			ResultSet showAllArchitect = statement.executeQuery("SELECT * FROM Architect");
			Main.displayContactDetailsResults(showAllArchitect);
		}

		// User enters Architect
		System.out.print("\nPlease enter Architect Name & Surname to be deleted:");
		String name = Main.scan.nextLine();

		ResultSet results = statement.executeQuery("SELECT * FROM Architect WHERE Name = '" + name + "'");

		// If Architect exits, delete it
		if (results.next() == true) {
			// Display Architect that is being deleted
			// If user made a mistake information is temporarily still available in console
			System.out.println("\nThe Architect is deleted:");
			ResultSet resultShow = statement.executeQuery("SELECT * FROM Architect WHERE Name = '" + name + "'");
			Main.displayContactDetailsResults(resultShow);

			// Delete the Architect in database
			statement.executeUpdate("DELETE FROM Architect WHERE Name ='" + name + "'");

			// Search if a project contains this Architect
			ResultSet resultsProject = statement.executeQuery("SELECT * FROM Project WHERE Architect = '" + name + "'");
			if (resultsProject.next() == true) {

				/**
				 * Display the Architect in the project and ask if user wants to delete the
				 * project also
				 */
				System.out.println("\nThe below Project was found with this Architect Name:");
				ResultSet resultsProjectDislay = statement
						.executeQuery("SELECT * FROM Project WHERE Architect = '" + name + "'");
				Main.displayProjectResults(resultsProjectDislay);

				System.out.println("\nDo you want to delete the above Projects for this Architect?: \n1. Yes \n2. No");
				int itemUpdate = Main.scan.nextInt();
				Main.scan.nextLine();

				if (itemUpdate == 1) {
					// Delete the Project in database
					statement.executeUpdate("DELETE FROM Project WHERE Architect ='" + name + "'");
				}
			}

		} else {
			System.out.println("\nArchitect was not found");
		}
	}

	/**
	 * Method to update Architect
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void updateArchitect(Statement statement) throws SQLException {
		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {

				// Ask if user knows which Architect they want to amend
				System.out.println("Do you know which Architect you want to update by Name? \n1. YES \n2. NO");
				int userKnows = Main.scan.nextInt();
				Main.scan.nextLine();

				// If No, display all the architect
				if (userKnows != 1) {
					ResultSet showAllArchitect = statement.executeQuery("SELECT * FROM Architect");
					Main.displayContactDetailsResults(showAllArchitect);
				}

				// User enters Architect Name
				System.out.print("\nPlease enter Architect Name:");
				String architectName = Main.scan.nextLine();

				ResultSet results = statement
						.executeQuery("SELECT * FROM Architect WHERE Name = '" + architectName + "'");

				// If Name exits, can make amendments
				if (results.next() == true) {

					ResultSet resultShow = statement
							.executeQuery("SELECT * FROM Architect WHERE Name = '" + architectName + "'");
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
						System.out.print("Please enter new Telephone number for the Architect:");
						int telephoneNumber = Main.scan.nextInt();
						Main.scan.nextLine();
						statement.executeUpdate("UPDATE Architect SET Telephone =" + telephoneNumber + " WHERE Name = '"
								+ architectName + "'");
						break;

					case "2":
						// Update Email
						System.out.print("Please enter Achitect Email:");
						String email = Main.scan.nextLine();
						statement.executeUpdate(
								"UPDATE Architect SET Email = '" + email + "' WHERE Name = '" + architectName + "'");
						break;

					case "3":
						// Update Address
						System.out.print("Please enter the Address of the Achitect:");
						String address = Main.scan.nextLine();
						statement.executeUpdate("UPDATE Architect SET Address = '" + address + "' WHERE Name = '"
								+ architectName + "'");
						break;

					// Default if user entered a wrong character
					default:
						System.out.println("\nArchitect was not ammended\n");
						break;

					}
				} else {
					System.out.println("\nArchitect was not found\n");
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
