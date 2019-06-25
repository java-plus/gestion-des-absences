package fr.gda.service;

import com.google.gson.Gson;

import fr.gda.model.Data;

public class DataGenerator {

	public DataGenerator() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] a) {

		/** Creating object of Organisation **/
		Data data = new Data();

		/** Insert the data into the object **/
		data = getObjectData(data);

		// In the below line
		// we have created a New Gson Object
		// and call it's toJson inbuid function
		// and passes the object of organisation
		System.out.println(new Gson().toJson(data));
	}

	/** Get the data to be inserted into the object **/
	public static Data getObjectData(Data data) {

		/** insert the data **/
		// data.setOrganisation_name("GeeksforGeeks");
		// data.setDescription("A computer Science portal for Geeks");
		// data.setEmployees(2000);

		/** Return Object **/
		return data;
	}

}
