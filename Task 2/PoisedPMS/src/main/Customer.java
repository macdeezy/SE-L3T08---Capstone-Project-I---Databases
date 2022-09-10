package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;

public class Customer {

	/**
	 * Method to insert a Customer
	 * 
	 * @param statement
	 * @param customerName
	 * @throws SQLException
	 */
	public static void enterNewCustomer(Statement statement, String customerName) throws SQLException {
		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {

				System.out.println("\nAdding Customer information:\n");

				// Get variables to add to Customer
				if (customerName == null) {
					System.out.print("Please enter Customer Name (Name & Surname):");
					customerName = Main.scan.nextLine();
				}

				// Check if Name exist in the Customer database, if not, capture into database
				ResultSet customerNameExist = statement
						.executeQuery("SELECT * FROM Customer WHERE Name = '" + customerName + "'");
				if (customerNameExist.next() == false) {

					System.out.print("Please enter Customer Telephone number:");
					int telephone = Main.scan.nextInt();
					Main.scan.nextLine();

					System.out.print("Please enter Customer Email:");
					String email = Main.scan.nextLine();

					System.out.print("Please enter the Address of the Customer:");
					String address = Main.scan.nextLine();
					System.out.println();

					// INSERT INTO Architect
					statement.executeUpdate("INSERT INTO Customer VALUES('" + customerName + "'," + telephone + ",'"
							+ email + "','" + address + "')");
				} else {
					System.out.println("Customer name already exists");
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
	 * Method to delete a Customer
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void deleteCustomer(Statement statement) throws SQLException {

		// Ask if user knows which Customer they want to delete
		System.out.println("Do you know which Customer you want to Delete? \n1. YES \n2. NO");
		int userKnowsId = Main.scan.nextInt();
		Main.scan.nextLine();

		// If No, display all Customers
		if (userKnowsId != 1) {
			ResultSet showAllCustomer = statement.executeQuery("SELECT * FROM Customer");
			Main.displayContactDetailsResults(showAllCustomer);
		}

		// User enters Customer
		System.out.print("\nPlease enter Customer Name & Surname to be deleted:");
		String name = Main.scan.nextLine();

		ResultSet results = statement.executeQuery("SELECT * FROM Customer WHERE Name = '" + name + "'");

		// If Customer exits, delete it
		if (results.next() == true) {
			// Display Customer that is being deleted
			// If user made a mistake information is temporarily still available in console
			System.out.println("\nThe Customer is deleted:");
			ResultSet resultShow = statement.executeQuery("SELECT * FROM Customer WHERE Name = '" + name + "'");
			Main.displayContactDetailsResults(resultShow);

			// Delete the Customer in database
			statement.executeUpdate("DELETE FROM Customer WHERE Name ='" + name + "'");

			// Search if a project contains this Customer
			ResultSet resultsProject = statement.executeQuery("SELECT * FROM Project WHERE Customer = '" + name + "'");
			if (resultsProject.next() == true) {

				/**
				 * Display the Customer in the project and ask if user wants to delete the
				 * project also
				 */
				System.out.println("\nThe below Project was found with this Customer Name:");
				ResultSet resultsProjectDislay = statement
						.executeQuery("SELECT * FROM Project WHERE Customer = '" + name + "'");
				Main.displayProjectResults(resultsProjectDislay);

				System.out.println("\nDo you want to delete the above Projects for this Customer?: \n1. Yes \n2. No");
				int itemUpdate = Main.scan.nextInt();
				Main.scan.nextLine();

				if (itemUpdate == 1) {
					// Delete the Project in database
					statement.executeUpdate("DELETE FROM Project WHERE Customer ='" + name + "'");
				}
			}

		} else {
			System.out.println("\nCustomer was not found");
		}

	}

	/**
	 * Method to update Customer
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public static void updateCustomer(Statement statement) throws SQLException {
		// USE TRY AND CATCH DO WHILE LOOP UNTILL ALL THE INPUTS ARE CAPTURED CORRECT
		boolean continueInput = true;
		do {
			try {

				// Ask if user knows which Customer they want to amend
				System.out.println("Do you know which Customer you want to update by Name? \n1. YES \n2. NO");
				int userKnows = Main.scan.nextInt();
				Main.scan.nextLine();

				// If No, display all customers
				if (userKnows != 1) {
					ResultSet showAllCustomer = statement.executeQuery("SELECT * FROM Customer");
					Main.displayContactDetailsResults(showAllCustomer);
				}

				// User enters Customer Name
				System.out.print("\nPlease enter Customer Name:");
				String customerName = Main.scan.nextLine();

				ResultSet results = statement
						.executeQuery("SELECT * FROM Customer WHERE Name = '" + customerName + "'");

				// If Name exits, can make amendments
				if (results.next() == true) {

					ResultSet resultShow = statement
							.executeQuery("SELECT * FROM Customer WHERE Name = '" + customerName + "'");
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
						System.out.print("Please enter new Telephone number for the Customer:");
						int telephoneNumber = Main.scan.nextInt();
						Main.scan.nextLine();
						statement.executeUpdate("UPDATE Customer SET Telephone =" + telephoneNumber + " WHERE Name = '"
								+ customerName + "'");
						break;

					case "2":
						// Update Email
						System.out.print("Please enter Customer Email:");
						String email = Main.scan.nextLine();
						statement.executeUpdate(
								"UPDATE Customer SET Email = '" + email + "' WHERE Name = '" + customerName + "'");
						break;

					case "3":
						// Update Address
						System.out.print("Please enter the Address of the Customer:");
						String address = Main.scan.nextLine();
						statement.executeUpdate(
								"UPDATE Customer SET Address = '" + address + "' WHERE Name = '" + customerName + "'");
						break;

					// Default if user entered a wrong character
					default:
						System.out.println("\nCustomer was not ammended\n");
						break;

					}
				}else {
					System.out.println("\nCustomer was not found\n");
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
