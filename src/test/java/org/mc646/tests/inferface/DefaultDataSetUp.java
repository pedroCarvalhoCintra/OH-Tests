package org.mc646.tests.inferface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.isf.disease.model.Disease;
import org.isf.distype.model.DiseaseType;
import org.isf.opd.model.Opd;
import org.isf.patient.model.Patient;

public class DefaultDataSetUp {
	private DataSetUp setup;
	private List<Patient> patients;
	private List<DiseaseType> diseaseTypes;
	private List<Disease> diseases;
	private List<Opd> opds;

	public DefaultDataSetUp() {
		GregorianCalendar yesterday = new GregorianCalendar();
		yesterday.add(Calendar.DAY_OF_MONTH, -1);
		setup = new DataSetUp();
		patients = new ArrayList<>();
		diseaseTypes = new ArrayList<>();
		diseases = new ArrayList<>();
		opds = new ArrayList<>();
		patients.add(setup.newPatient("Bob", 0, 'M'));
		patients.add(setup.newPatient("Alice", 31, 'F'));
		patients.add(setup.newPatient("Craig", 100, 'M'));
		patients.add(setup.newPatient("Daphine", 21, 'F'));
		diseaseTypes.add(setup.newDiseaseType("L00-L99 Skin and subcutaneous tissue", " "));
		diseaseTypes.add(setup.newDiseaseType("J00-J99 Respiratory system", " "));
		diseases.add(setup.newDisease("Acne", " ", diseaseTypes.get(0), true, true, true));
		diseases.add(setup.newDisease("Influenza with pneumonia, seazonal influenza virus detected", " ",
				diseaseTypes.get(1), true, true, true));
		diseases.add(setup.newDisease("Vitiligo", " ", diseaseTypes.get(0), true, true, true));
		opds.add(setup.newOpd(0, 'M', 0, diseases.get(0), null, null, new GregorianCalendar(2003, 2, 2), 'n', "",
				patients.get(0)));
		opds.add(setup.newOpd(0, 'F', 31, diseases.get(1), diseases.get(0), null, new GregorianCalendar(2010, 3, 9),
				'R', "", patients.get(1)));
		opds.add(setup.newOpd(0, 'M', 100, diseases.get(0), null, null, yesterday, 'R', "", patients.get(2)));
		opds.add(setup.newOpd(1, 'F', 21, diseases.get(1), null, diseases.get(2), new GregorianCalendar(),
				'n', "", patients.get(3)));
	}

	public List<Patient> getDefaultPatients() {
		return patients;
	}

	public List<DiseaseType> getDefaultDiseaseTypes() {
		return diseaseTypes;
	}

	public List<Disease> getDefaultDiseases() {
		return diseases;
	}

	public List<Opd> getDefaultOpds() {
		return opds;
	}
}
