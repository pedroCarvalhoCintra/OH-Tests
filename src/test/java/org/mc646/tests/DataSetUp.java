package org.mc646.tests;

import org.isf.patient.model.*;

import java.util.List;

import org.isf.disease.model.*;
import org.isf.distype.model.*;
import org.isf.opd.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataSetUp {
	
	private static String secondName = "Emeritus";
	private static Date birthDate = new GregorianCalendar(1984, Calendar.AUGUST, 14).getTime();
	private static String agetype = "Date";
	private static String address = "TestAddress";
	private static String city = "TestCity";
	private static String nextKin = "TestNextKin";
	private static String telephone = "TestTelephone";
	private static String mother_name = "TestMotherName";
	private static char mother = 'A'; // D=dead, A=alive
	private static String father_name = "TestFatherName"; // father's name
	private static char father = 'A'; // D=dead, A=alive
	private static String bloodType = "0-/+"; // (0-/+, A-/+ , B-/+, AB-/+)
	private static char hasInsurance = 'Y'; // Y=Yes, N=no
	private static char parentTogether = 'Y'; // parents together: Y or N
	private static String taxCode = "TestTaxCode";
	private static String maritalStatus = "divorced";
	private static String profession = "business";
	
	public Patient newPatient(String firstName, int age, char sex) {
		Patient newPatient = new Patient(firstName, secondName, birthDate, age, agetype, sex,
				address, city, nextKin, telephone, mother_name, mother, father_name, father,
				bloodType, hasInsurance, parentTogether, taxCode, maritalStatus, profession);
		newPatient.setAge(newPatient.getAge());
		newPatient.setPatientProfilePhoto(new PatientProfilePhoto());
		return newPatient;
	}

	public DiseaseType newDiseaseType(String code, String description) {
		DiseaseType newType = new DiseaseType(code, description);
		return newType;
	}

	public Disease newDisease(String code, String description, DiseaseType type, boolean opdInclude,
			boolean ipdInInclude, boolean ipdOutInclude) {
		Disease newDisease = new Disease(code, description, type);
		newDisease.setOpdInclude(opdInclude);
		newDisease.setIpdInInclude(ipdInInclude);
		newDisease.setIpdOutInclude(ipdOutInclude);
		return newDisease;
	}

	public Opd newOpd(int progYear, char sex, int age, Disease disease, Disease disease2, Disease disease3, GregorianCalendar date, char newPatient, String note, Patient patient) {
		Opd newOpd = new Opd(progYear, sex, age, new Disease("Abe", "abs", new DiseaseType("Aaa", "")));
		newOpd.setDate(date);
		newOpd.setDisease(disease);
		newOpd.setDisease2(disease2);
		newOpd.setDisease3(disease3);
		newOpd.setNewPatient(newPatient);
		newOpd.setNote(note);
		newOpd.setPatient(patient);
		newOpd.setVisitDate(new GregorianCalendar());
		newOpd.setReferralFrom("R");
		newOpd.setReferralTo("R");
		newOpd.setUserID("User");
		return newOpd;
	}
}
